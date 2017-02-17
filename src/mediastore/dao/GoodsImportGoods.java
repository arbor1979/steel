// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsImportGoods.java

package mediastore.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mediastore.common.DBConnection;
import mediastore.rule.ImportItemSearchRule;
import mediastore.util.StrUtility;
import mediastore.util.oConvert;
import mediastore.web.form.GoodsImportGoodsInfo;
import mediastore.web.form.GoodsInfoForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.global.Globals;

// Referenced classes of package mediastore.dao:
//            GoodsImportInfo, GoodsInfo

public class GoodsImportGoods
{

    public GoodsImportGoods()
    {
    }

    public int insertNewGoodsRec(GoodsImportGoodsInfo gif) throws Exception
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL="select * from TabGoodsImportInfo where confirmflage='1' and billid="+gif.getBillId()+" and deptid="+gif.getDeptid();
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("此单号已经存在");
            rs.close();
            strSQL="select * from TabGoodsImportGoods where billid="+gif.getBillId()+" and goodsid='"+gif.getGoodsId()+"' and storeid="+gif.getStoreId()+" and deptid="+gif.getDeptid();
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("此单中已包含同一仓库编号为"+gif.getGoodsId()+"的商品");
            rs.close();
            
        	strSQL = "insert into TabGoodsImportGoods( BillID, GoodsID, GoodsName,ImportNum, ImportAmount, ImportUnitPrice,"+
        	"CreatePerson, CreateTime, ConfirmFlage,storeid,deptid) values (" + gif.getBillId() + ", '" + 
        	StrUtility.replaceString(gif.getGoodsId(), "'", "''") + "', '" + StrUtility.replaceString(gif.getGoodsName(), "'", "''") +
        	"',"+gif.getImportNum()+","+gif.getImportAmount()+"," + gif.getImportUnitPrice() + ", '" + StrUtility.replaceString(gif.getCreatePerson(), "'", "''") + 
        	"', getdate(), '0',"+gif.getStoreId()+","+gif.getDeptid()+")";
        	nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
            {
                nRet = -1;
                throw new Exception("插入新记录错误");
            }
            
        }
        catch(Exception e)
        {
            nRet = -1;
            throw e;
        }
        finally
        {
            try
            {
            	if(rs != null)
                    rs.close();
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException ex) { }
        }
        return nRet;
    }

    
	@SuppressWarnings("unchecked")
	public List getGoodsList(int billId,int deptid) throws Exception
    {
        List goodsList;
        goodsList = new ArrayList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "SELECT a.*,b.name,c.guige,c.chandi,c.caizhi,c.xishu,c.danwei,c.jianxishu FROM TabGoodsImportGoods a inner join TabStore b on a.storeid=b.id "+
            "inner join TabGoodsInfo c on a.goodsid=c.goodsid WHERE a.BillID=" + billId + " and a.deptid="+deptid+" Order by a.createtime";
            GoodsImportGoodsInfo gigi;
            GoodsInfo gi=new GoodsInfo();
            for(rs = stmt.executeQuery(strSQL); rs.next(); goodsList.add(gigi))
            {
                gigi = new GoodsImportGoodsInfo();
                gigi.setId(rs.getInt("id"));
                gigi.setBillId(rs.getInt("billid"));
                gigi.setGoodsId(rs.getString("goodsid"));
                gigi.setGoodsName(rs.getString("goodsname"));
                gigi.setCurRepertoryAmount(gi.getRepertoryAmount(rs.getString("goodsid"),rs.getInt("storeid")));
                gigi.setImportNum(rs.getInt("importNum"));
                gigi.setImportAmount(rs.getDouble("importAmount"));
                gigi.setImportUnitPrice(rs.getDouble("importUnitPrice"));
                gigi.setCreatePerson(rs.getString("createPerson"));
                gigi.setCreateTime(rs.getString("createTime"));
                gigi.setConfirmFlage(rs.getString("confirmFlage"));
                gigi.setStoreName(rs.getString("name"));
                gigi.setStoreId(rs.getInt("storeid"));
                gigi.setGuige(rs.getString("guige"));
                gigi.setCaizhi(rs.getString("caizhi"));
                gigi.setChandi(rs.getString("chandi"));
                gigi.setXishu(rs.getDouble("xishu"));
                gigi.setDanwei(rs.getString("danwei"));
                gigi.setJianxishu(rs.getDouble("jianxishu"));
                gigi.setEvaPrice(rs.getDouble("evaPrice"));
                if(rs.getDouble("jianxishu")>0)
                	gigi.setImportJianNum(rs.getDouble("importAmount")*1000/rs.getDouble("jianxishu"));
                gigi.setMemo(oConvert.getString(rs.getString("memo"),""));
            }
            rs.close();
            
        }
        catch(Exception e) {throw  e;}
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) {}
        }
        return goodsList;
    }

    public void deleteGoodsRec(GoodsImportGoodsInfo gif) throws Exception
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            String strSQL = "DELETE FROM TabGoodsImportGoods WHERE id=" + gif.getId() + "  and createPerson='" + gif.getCreatePerson() +"' and confirmFlage='0'"; 
            stmt = conn.createStatement();
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
                throw new Exception("删除入库记录失败");
        }
        catch(Exception e)
        {
        	throw e;
        }
        finally
        {
            try
            {
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
    }
    public int updateImportJianNum(GoodsImportGoodsInfo geg) throws Exception
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            strSQL = "select * from TabGoodsImportGoods WHERE id = " + geg.getId();
            rs=stmt.executeQuery(strSQL); 
            if(rs.next())
            {
            	geg.setGoodsId(rs.getString("goodsid"));
            	geg.setStoreId(rs.getInt("storeid"));
            	geg.setImportAmount(rs.getDouble("importamount"));
            	geg.setImportNum(rs.getDouble("importNum"));
            }
            else
            	throw new Exception("找不到记录");
            rs.close();
            
            GoodsInfo gi=new GoodsInfo();
            GoodsInfoForm g=gi.getGoodsInfoByGoodsId(geg.getGoodsId());
            geg.setImportJianNum(geg.getImportJianNum()*geg.getKind());
            geg.setImportAmount(geg.getImportJianNum()*g.getJianxishu()/1000);
            if(g.getXishu()>0)
            	geg.setImportNum(geg.getImportJianNum()*g.getJianxishu()/g.getXishu());
            
        	double kucun=gi.getRepertoryAmount(geg.getGoodsId(),geg.getStoreId());
            if(geg.getImportAmount()<0 && kucun+geg.getImportAmount()<0)
            	throw new Exception("编号为"+geg.getGoodsId()+"的产品库存不足");
            
            strSQL = "UPDATE TabGoodsImportGoods SET ImportNum="+geg.getImportNum()+",ImportAmount = " + geg.getImportAmount() + " WHERE id = " + geg.getId() + " and ConfirmFlage='0' and createPerson='" + StrUtility.replaceString(geg.getCreatePerson(), "'", "''") + "'";
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("更新件数失败");
            conn.commit();
        }
        catch(Exception e)
        {
        	conn.rollback();
            nRet = -1;
            throw e;
        }
        finally
        {
            try
            {
            	if(rs != null)
                    rs.close();
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return nRet;
    }
    public int updateImportNum(GoodsImportGoodsInfo geg) throws Exception
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            strSQL = "select * from TabGoodsImportGoods WHERE id = " + geg.getId();
            rs=stmt.executeQuery(strSQL); 
            if(rs.next())
            {
            	geg.setGoodsId(rs.getString("goodsid"));
            	geg.setStoreId(rs.getInt("storeid"));
            	geg.setImportAmount(rs.getFloat("importamount"));
            }
            else
            	throw new Exception("找不到记录");
            rs.close();
            
            GoodsInfo gi=new GoodsInfo();
            GoodsInfoForm g=gi.getGoodsInfoByGoodsId(geg.getGoodsId());
            geg.setImportNum(geg.getImportNum()*geg.getKind());
            if(g.getXishu()>0)
	        {
	            int numPerJian=(int)oConvert.getRound(g.getJianxishu()/g.getXishu(),0);
	            if(numPerJian>0)
	            	geg.setImportAmount(Math.floor(geg.getImportNum()/numPerJian)*g.getJianxishu()/1000+geg.getImportNum()%numPerJian*g.getXishu()/1000);
	            else
	            	geg.setImportAmount(geg.getImportNum()*g.getXishu()/1000);
	           
	            double kucun=gi.getRepertoryAmount(geg.getGoodsId(),geg.getStoreId());
	            if(geg.getImportAmount()<0 && kucun+geg.getImportAmount()<0)
	            	throw new Exception("编号为"+geg.getGoodsId()+"的产品库存不足");
	            
	            strSQL = "UPDATE TabGoodsImportGoods SET ImportNum="+geg.getImportNum()+",ImportAmount = " + geg.getImportAmount() + " WHERE id = " + geg.getId() + " and ConfirmFlage='0' and createPerson='" + StrUtility.replaceString(geg.getCreatePerson(), "'", "''") + "'";
	            nRet=stmt.executeUpdate(strSQL);
	            if(nRet!=1)
	            	throw new Exception("更新入库数量失败");
	        }
            else
            {
            	strSQL = "UPDATE TabGoodsImportGoods SET ImportNum="+geg.getImportNum()+" WHERE id = " + geg.getId() + " and ConfirmFlage='0' and createPerson='" + StrUtility.replaceString(geg.getCreatePerson(), "'", "''") + "'";
	            nRet=stmt.executeUpdate(strSQL);
            }
            conn.commit();
        }
        catch(Exception e)
        {
        	conn.rollback();
            nRet = -1;
            throw e;
        }
        finally
        {
            try
            {
            	if(rs != null)
                    rs.close();
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return nRet;
    }
    public void updateAmount(GoodsImportGoodsInfo gif) throws Exception
    {
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "select * from TabGoodsImportGoods WHERE id = " + gif.getId();
            rs=stmt.executeQuery(strSQL); 
            if(rs.next())
            {
            	gif.setGoodsId(rs.getString("goodsid"));
            	gif.setStoreId(rs.getInt("storeid"));
            	gif.setImportNum(rs.getDouble("importNum"));
            }
            else
            	throw new Exception("找不到记录");
            rs.close();

            GoodsInfo gi=new GoodsInfo();
            GoodsInfoForm g=gi.getGoodsInfoByGoodsId(gif.getGoodsId());
            gif.setImportAmount(gif.getImportAmount()*gif.getKind());
            if(g.getXishu()>0)
            	gif.setImportNum(gif.getImportAmount()*1000/g.getXishu());
            
            
            double kucun=gi.getRepertoryAmount(gif.getGoodsId(),gif.getStoreId());
            if(gif.getImportAmount()<0 && kucun+gif.getImportAmount()<0)
            	throw new Exception("编号为"+gif.getGoodsId()+"的产品库存不足");
            
            strSQL = "UPDATE TabGoodsImportGoods SET ImportNum="+gif.getImportNum()+",ImportAmount = " + gif.getImportAmount() + " WHERE id = " + gif.getId() +
            " and ConfirmFlage='0' and createperson='" + gif.getCreatePerson() + "'";
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新入库数量失败");
        }
        catch(Exception e)
        {
            throw e;
        }
        finally
        {
            try
            {
            	if(rs != null)
                    rs.close();
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
    }
    public void updatePrice(GoodsImportGoodsInfo gif) throws Exception
    {
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "UPDATE TabGoodsImportGoods SET ImportUnitPrice = " + gif.getImportUnitPrice() + " WHERE id = " + gif.getId() +
            " and ConfirmFlage='0' and createPerson='" + gif.getCreatePerson() + "'";
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新入库单价失败");
        }
        catch(Exception e)
        {
            throw e;
        }
        finally
        {
            try
            {
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
    }
    public void updateMoney(GoodsImportGoodsInfo gif) throws Exception
    {
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "select * from TabGoodsImportGoods WHERE id = " + gif.getId() +
            " and createPerson='" + gif.getCreatePerson() + "'";
            rs=stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("找不到此记录");
            if(rs.getFloat("importAmount")==0)
            	throw new Exception("重量不能为0");
            double unitPrice=oConvert.getRound(gif.getImportMoney()/rs.getDouble("importAmount")*gif.getKind(),4);
            if(unitPrice<0)
            	throw new Exception("价格不能为负数");
            strSQL = "UPDATE TabGoodsImportGoods SET ImportUnitPrice = " + unitPrice + " WHERE id = " + gif.getId() +
            " and ConfirmFlage='0' and createPerson='" + gif.getCreatePerson() + "'";
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新入库单价失败");
        }
        catch(Exception e)
        {
            throw e;
        }
        finally
        {
            try
            {
            	if(rs != null)
                    rs.close();
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
    }
    public void updateMemo(GoodsImportGoodsInfo gif) throws Exception
    {
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "UPDATE TabGoodsImportGoods SET memo ='" + gif.getMemo() + "' WHERE id = " + gif.getId() +
            " and ConfirmFlage='0' and createPerson='" + gif.getCreatePerson() + "'";
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新备注失败");
        }
        catch(Exception e)
        {
            throw e;
        }
        finally
        {
            try
            {
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
    }
   
    public int deleteAllGoods(GoodsImportGoodsInfo gif)
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            String strSQL = "DELETE FROM TabGoodsImportGoods WHERE BillID=" + gif.getBillId() + " and CreatePerson='"+gif.getCreatePerson()+"' and confirmFlage='0'";
            stmt = conn.createStatement();
            nRet = stmt.executeUpdate(strSQL);
            if(nRet<=0)
            	throw new Exception("删除失败");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return nRet;
    }

    
    
	@SuppressWarnings("unchecked")
	public List getBillIdList()
    {
        List billIdList;
        billIdList = new ArrayList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "SELECT DISTINCT BillID FROM TabGoodsImportGoods Order by BillID desc";
            String billIdStr;
            for(rs = stmt.executeQuery(strSQL); rs.next(); billIdList.add(billIdStr))
                billIdStr = String.valueOf(rs.getInt(1));

            rs.close();
            rs = null;
        }
        catch(Exception exception) { }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return billIdList;
    }
	
	@SuppressWarnings("unchecked")
	public List getCreatePersonList()
    {
        List createPersonList;
        createPersonList = new ArrayList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "SELECT DISTINCT CreatePerson FROM TabGoodsImportGoods Order by CreatePerson";
            String curSendPersons;
            for(rs = stmt.executeQuery(strSQL); rs.next(); createPersonList.add(curSendPersons))
                curSendPersons = rs.getString(1);

            rs.close();
            rs = null;
        }
        catch(Exception exception) { }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return createPersonList;
    }
    
	@SuppressWarnings("unchecked")
	public ResultsetList getItemListByRule(ImportItemSearchRule iisr)
    {
        ResultsetList iisri;
        iisri = new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
        int curpage = iisr.getCurpage();
        int pagesize = Globals.REC_NUM_OF_PAGE;
        String orderStr = iisr.getOrderStr();
        GoodsImportGoodsInfo gif=iisr.getGif();
        
        strSQL = " SELECT a.*,b.name,c.guige,c.chandi,e.shortname FROM TabGoodsImportGoods a ";
        strSQL1 = " SELECT sum(a.importamount) as allnum,sum(a.importamount*a.importunitprice) as allprice FROM TabGoodsImportGoods a ";
        String joinstr="inner join TabStore b on a.storeid=b.id inner join TabGoodsInfo c on"+
        " a.goodsid=c.goodsid inner join TabGoodsTypeCode d on c.goodstype=d.goodstype inner join TabDepartInfo e "+
        " on a.deptid=e.id where a.confirmflage='1'";
        strSQL=strSQL+joinstr;
        strSQL1=strSQL1+joinstr;
        
        String whereStr = "";
        if(gif.getBillId()!= 0)
            whereStr = whereStr + " and a.BillID=" + gif.getBillId();
        if(!gif.getGuige().equals(""))
            whereStr = whereStr + " and c.guige like '%" + StrUtility.replaceString(gif.getGuige(), "'", "''") + "%'";
        if(!gif.getGoodsId().equals(""))
            whereStr = whereStr + " and a.GoodsID like '%" + StrUtility.replaceString(gif.getGoodsId(), "'", "''") + "%'";
        if(!gif.getCreatePerson().equals(""))
            whereStr = whereStr + " and a.CreatePerson ='" + gif.getCreatePerson() + "'";
        if(gif.getStoreId()!= 0)
            whereStr = whereStr + " and a.storeid=" + gif.getStoreId();
        if(!iisr.getOper().equals(""))
            whereStr = whereStr + " and a.importunitprice "+iisr.getOper()+ gif.getImportUnitPrice();
        if(!iisr.getImportTime1().equals(""))
            whereStr = whereStr + " and a.createtime>='" + iisr.getImportTime1() + "'";
        if(!iisr.getImportTime2().equals(""))
            whereStr = whereStr + " and a.createtime<='" + iisr.getImportTime2()+"'";
        if(!iisr.getGoodsClass().equals("0"))
            whereStr = whereStr + " and d.classid=" + iisr.getGoodsClass();
        if(!iisr.getGoodsType().equals("0"))
            whereStr = whereStr + " and c.goodstype=" + iisr.getGoodsType();
        if(gif.getChandi().length()>0)
            whereStr = whereStr + " and c.chandi like '%" + gif.getChandi()+"%'";
        if(gif.getDeptid()>0)
            whereStr = whereStr + " and a.deptid=" + gif.getDeptid();
        if(gif.getMemo().length()>0)
            whereStr = whereStr + " and a.memo like '%" + gif.getMemo()+"%'";
        strSQL=strSQL+whereStr;
        if(orderStr.length()>0)
        	strSQL = strSQL + whereStr+ " Order by " + orderStr;
        strSQL1 = strSQL1 + whereStr;
        try
        {
        	dbc = new DBConnection();
        	conn = dbc.getDBConnection();
        	stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        	rs = stmt.executeQuery(strSQL);
        	if(rs.last())
            {
            	int RowCount=rs.getRow();
            	iisri.allnum=RowCount;
            	iisri.curpage=curpage;
            	iisri.pagesize=pagesize;
            	int PageCount = ((RowCount % iisri.pagesize) == 0 ?(RowCount/iisri.pagesize) : (RowCount/iisri.pagesize)+1);
            	iisri.pagecount=PageCount;
            	if(iisri.curpage > PageCount)  
            	{
            		iisri.curpage = PageCount; 
            	}
            	else if(iisri.curpage <= 0)
            	{
            		iisri.curpage = 1;  
            	}
            	rs.absolute((iisri.curpage - 1) * iisri.pagesize + 1);  
            	for(int i=0;i<iisri.pagesize;i++)
            	{
            		GoodsImportGoodsInfo giii = new GoodsImportGoodsInfo();
                    giii.setBillId(rs.getInt("billid"));
                    giii.setGoodsId(rs.getString("goodsid"));
                    giii.setGoodsName(rs.getString("goodsname"));
                    giii.setImportAmount(rs.getDouble("ImportAmount"));
                    giii.setImportUnitPrice(rs.getDouble("importUnitPrice"));
                    giii.setCreatePerson(rs.getString("createperson"));
                    giii.setCreateTime(oConvert.FormDateTimeShort(rs.getTimestamp("createTime")));
                    giii.setStoreId(rs.getInt("storeid"));
                    giii.setStoreName(rs.getString("name"));
                    giii.setChandi(rs.getString("chandi"));
                    giii.setDeptid(rs.getInt("deptid"));
                    giii.setShortname(rs.getString("shortname"));
                    giii.setGuige(rs.getString("guige"));
                    giii.setMemo(oConvert.getString(rs.getString("memo"),""));
                    iisri.rslist.add(giii);
                    if(!rs.next())
	            		break;
                }

            }
        	rs.close();
        	rs = stmt.executeQuery(strSQL1);
        	if(rs.next())
        	{
        		iisri.allsumnum=oConvert.getRound(rs.getDouble(1),4);
        		iisri.allmoney=oConvert.getRound(rs.getDouble(2),2);
        	}
        	rs.close();
        		
        }
        catch(Exception e) {System.out.println(e.getMessage()); }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return iisri;
    }


    public int getImportAmount(int billId, String goodsId)
    {
        int importAmount;
        importAmount = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "SELECT ImportAmount FROM TabGoodsImportGoods WHERE BillID=" + billId + " AND GoodsID='" + StrUtility.replaceString(goodsId, "'", "''") + "' ";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
                importAmount = rs.getInt(1);
            rs.close();
            rs = null;
        }
        catch(Exception exception) { }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return importAmount;
    }

    public float getImportUnitPrice(int billId, String goodsId)
    {
        float importUnitPrice;
        importUnitPrice = 0.0F;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "SELECT ImportUnitPrice FROM TabGoodsImportGoods WHERE BillID=" + billId + " AND GoodsID='" + StrUtility.replaceString(goodsId, "'", "''") + "' ";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
                importUnitPrice = rs.getFloat(1);
            rs.close();
            rs = null;
        }
        catch(Exception exception) { }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return importUnitPrice;
    }
    public double getLastImportUnitPrice(int deptid, String goodsId) throws Exception
    {
    	double importUnitPrice;
        importUnitPrice =0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "SELECT top 1 ImportUnitPrice FROM TabGoodsImportGoods WHERE deptID=" + deptid + " AND GoodsID='" + goodsId + 
            "' order by createtime desc";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
                importUnitPrice = rs.getDouble(1);
            rs.close();
            rs = null;
        }
        catch(Exception e) {throw e; }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return importUnitPrice;
    }
    public boolean ifConflict(int kind, String createPerson)
    {
        float importAmount=0.0F;
        boolean flag=false;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "SELECT * FROM TabGoodsImportGoods WHERE createPerson='" + createPerson + "' and confirmFlage='0'";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
                importAmount = rs.getFloat("importAmount");
                if(importAmount>0 && kind==-1)
                	flag=true;
                else if(importAmount<0 && kind==1)
                	flag=true;
            }
            rs.close();
            rs = null;
        }
        catch(Exception exception) { }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return flag;
    }
    public void updateEvaPrice(GoodsImportGoodsInfo gif) throws Exception
    {
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "UPDATE TabGoodsImportGoods SET evaPrice = " + gif.getEvaPrice() + " WHERE id = " + gif.getId() +
            " and deptid=" + gif.getDeptid();
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新实际单价失败");
        }
        catch(Exception e)
        {
            throw e;
        }
        finally
        {
            try
            {
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
    }
    
    public void updateEvaMoney(GoodsImportGoodsInfo gif) throws Exception
    {
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "select * from TabGoodsImportGoods WHERE id = " + gif.getId() +
            " and deptid=" + gif.getDeptid();
            rs=stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("找不到此记录");
            if(rs.getFloat("importAmount")==0)
            	throw new Exception("重量不能为0");
            double unitPrice=oConvert.getRound(gif.getEvaMoney()/rs.getDouble("importAmount"),4);
            if(unitPrice<0)
            	throw new Exception("价格不能为负数");
            strSQL = "UPDATE TabGoodsImportGoods SET EvaPrice = " + unitPrice + " WHERE id = " + gif.getId() +
            " and deptid='" + gif.getDeptid() + "'";
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新实际单价失败");
        }
        catch(Exception e)
        {
            throw e;
        }
        finally
        {
            try
            {
            	if(rs != null)
                    rs.close();
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
    }
    public int deleteAllEvaPricce(GoodsImportGoodsInfo gif)
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            String strSQL = "Update TabGoodsImportGoods set evaPrice=0 WHERE BillID=" + gif.getBillId() + " and deptid="+gif.getDeptid()+" and CreatePerson='"+gif.getCreatePerson()+"'";
            stmt = conn.createStatement();
            nRet = stmt.executeUpdate(strSQL);
            if(nRet<=0)
            	throw new Exception("清除估价失败");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return nRet;
    }
}
