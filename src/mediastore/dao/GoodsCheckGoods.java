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
import mediastore.rule.CheckItemSearchRule;
import mediastore.util.StrUtility;
import mediastore.util.oConvert;
import mediastore.web.form.GoodsCheckGoodsInfo;
import mediastore.web.form.GoodsInfoForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.global.Globals;

// Referenced classes of package mediastore.dao:
//            GoodsImportInfo, GoodsInfo

public class GoodsCheckGoods
{

    public GoodsCheckGoods()
    {
    }

    public int insertNewGoodsRec(GoodsCheckGoodsInfo gif) throws Exception
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
            strSQL="select * from TabGoodsCheckInfo where billid="+gif.getBillId()+" and deptid="+gif.getDeptid()+" and confirmFlage='1'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("此损溢单已经存在");
            rs.close();
            strSQL="select * from TabGoodsCheckGoods where billid="+gif.getBillId()+" and goodsid='"+gif.getGoodsId()+"' and storeid="+gif.getStoreId()+" and deptid="+gif.getDeptid();
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("此单中已包含同一仓库编号为"+gif.getGoodsId()+"的商品");
            rs.close();
            if(gif.getCheckAmount()<0)
            {
            	GoodsInfo gi=new GoodsInfo();
            	double kucun=gi.getRepertoryAmount(gif.getGoodsId(),gif.getStoreId());
                if(kucun+gif.getCheckAmount()<0)
                	throw new Exception("编号为"+gif.getGoodsId()+"的产品库存不足，仅剩"+kucun+"吨");
            }
            GoodsInfo gi=new GoodsInfo();
            gif.setCheckUnitPrice(gi.getAvgInPrice(conn,gif.getGoodsId(),gif.getDeptid()));
        	strSQL = "insert into TabGoodsCheckGoods( BillID, GoodsID, GoodsName,CheckNum,curRepertoryAmount, CheckAmount, CheckUnitPrice,"+
        	"CreatePerson, CreateTime, ConfirmFlage,storeid,deptid) values (" + gif.getBillId() + ", '" + 
        	StrUtility.replaceString(gif.getGoodsId(), "'", "''") + "', '" + StrUtility.replaceString(gif.getGoodsName(), "'", "''") +
        	"',"+gif.getCheckNum()+","+gif.getCurRepertoryAmount()+","+gif.getCheckAmount()+"," + gif.getCheckUnitPrice() + ", '" + StrUtility.replaceString(gif.getCreatePerson(), "'", "''") + 
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
        Statement stmt1 = null;
        ResultSet rs = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            stmt1 = conn.createStatement();
            
            strSQL = "SELECT a.*,b.name,c.guige,c.chandi,c.caizhi,c.xishu,c.danwei FROM TabGoodsCheckGoods a inner join TabStore b on a.storeid=b.id "+
            "inner join TabGoodsInfo c on a.goodsid=c.goodsid WHERE a.BillID=" + billId + " and a.deptid="+deptid+" Order by a.id";
            GoodsCheckGoodsInfo gigi;

            for(rs = stmt.executeQuery(strSQL); rs.next(); goodsList.add(gigi))
            {
                gigi = new GoodsCheckGoodsInfo();
                gigi.setId(rs.getInt("id"));
                gigi.setBillId(rs.getInt("billid"));
                gigi.setGoodsId(rs.getString("goodsid"));
                gigi.setGoodsName(rs.getString("goodsname"));
                gigi.setCheckNum(rs.getInt("CheckNum"));
                gigi.setCurRepertoryAmount(rs.getDouble("CurRepertoryAmount"));
                gigi.setCheckAmount(rs.getDouble("CheckAmount"));
                gigi.setCheckUnitPrice(rs.getDouble("CheckUnitPrice"));
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
                if(stmt1 != null)
                    stmt1.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) {}
        }
        return goodsList;
    }

    public void deleteGoodsRec(GoodsCheckGoodsInfo gif) throws Exception
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
            String strSQL = "DELETE FROM TabGoodsCheckGoods WHERE id=" + gif.getId() + " and createPerson='" + gif.getCreatePerson() +"'"; 
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
    public int updateCheckNum(GoodsCheckGoodsInfo geg) throws Exception
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
            strSQL = "select * from TabGoodsCheckGoods WHERE id = " + geg.getId();
            rs=stmt.executeQuery(strSQL); 
            if(rs.next())
            {
            	geg.setGoodsId(rs.getString("goodsid"));
            	geg.setStoreId(rs.getInt("storeid"));
            	geg.setCheckAmount(rs.getDouble("checkAmount"));
            }
            else
            	throw new Exception("找不到记录");
            rs.close();
            
            GoodsInfo gi=new GoodsInfo();
            GoodsInfoForm g=gi.getGoodsInfoByGoodsId(geg.getGoodsId());
            geg.setCheckNum(geg.getCheckNum()*geg.getKind());
            if(g.getXishu()>0)
            {
	            int numPerJian=(int)oConvert.getRound(g.getJianxishu()/g.getXishu(),0);
	            if(numPerJian>0)
	            	geg.setCheckAmount(Math.floor(geg.getCheckNum()/numPerJian)*g.getJianxishu()/1000+geg.getCheckNum()%numPerJian*g.getXishu()/1000);
	            else
	            	geg.setCheckAmount(geg.getCheckNum()*g.getXishu()/1000);
	            
	            if(geg.getCheckAmount()<0)
	            {
	            	double kucun=gi.getRepertoryAmount(geg.getGoodsId(),geg.getStoreId());
	                if(geg.getCheckAmount()+kucun<0)
	                	throw new Exception("编号为"+geg.getGoodsId()+"的产品库存不足，仅剩"+kucun+"吨");
	            }
            }
            strSQL = "UPDATE TabGoodsCheckGoods SET CheckNum="+geg.getCheckNum()+",CheckAmount = (" + geg.getCheckAmount() + ") WHERE id = " + geg.getId() + " and createPerson='" + StrUtility.replaceString(geg.getCreatePerson(), "'", "''") + "'";
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("更新盘点数量失败");
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
    public void updateCheckAmount(GoodsCheckGoodsInfo gif) throws Exception
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
            strSQL = "select * from TabGoodsCheckGoods WHERE id = " + gif.getId();
            rs=stmt.executeQuery(strSQL); 
            if(rs.next())
            {
            	gif.setGoodsId(rs.getString("goodsid"));
            	gif.setStoreId(rs.getInt("storeid"));
            	gif.setCheckNum(rs.getInt("checknum"));
            }
            else
            	throw new Exception("找不到记录");
            rs.close();
            gif.setCheckAmount(gif.getCheckAmount()*gif.getKind());
            if(gif.getCheckAmount()<0)
            {
            	GoodsInfo gi=new GoodsInfo();
                double kucun=gi.getRepertoryAmount(gif.getGoodsId(),gif.getStoreId());
                if(gif.getCheckAmount()+kucun<0)
                	throw new Exception("编号为"+gif.getGoodsId()+"的产品库存不足，仅剩"+kucun+"吨");
            }
            GoodsInfo gi=new GoodsInfo();
            GoodsInfoForm g=gi.getGoodsInfoByGoodsId(gif.getGoodsId());
            if(g.getXishu()>0)
            	gif.setCheckNum(gif.getCheckAmount()*1000/g.getXishu());
            
            strSQL = "UPDATE TabGoodsCheckGoods SET CheckNum="+gif.getCheckNum()+",CheckAmount = " + gif.getCheckAmount() + " WHERE id = " + gif.getId() +
            " and createperson='" + gif.getCreatePerson() + "'";
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新盘点重量失败");
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
    public void updateCheckPrice(GoodsCheckGoodsInfo gif) throws Exception
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
            strSQL = "UPDATE TabGoodsCheckGoods SET checkUnitPrice = " + gif.getCheckUnitPrice() + " WHERE id = " + gif.getId() +
            " and createPerson='" + gif.getCreatePerson() + "'";
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新盘点单价失败");
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
    
    public int deleteAllGoods(GoodsCheckGoodsInfo gif)
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
            String strSQL = "DELETE FROM TabGoodsCheckGoods WHERE BillID=" + gif.getBillId() + " and deptid="+gif.getDeptid()+" and CreatePerson='"+gif.getCreatePerson()+"'";
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
	public ResultsetList getItemListByRule(CheckItemSearchRule iisr)
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
        GoodsCheckGoodsInfo gif=iisr.getGif();
        
        strSQL = " SELECT a.*,b.name,c.chandi,e.shortname FROM TabGoodsCheckGoods a ";
        strSQL1 = " SELECT sum(a.checkamount) as allnum,sum(a.checkamount*a.checkunitprice) as allprice FROM TabGoodsCheckGoods a ";
        String joinstr="inner join TabStore b on a.storeid=b.id inner join TabGoodsInfo c on"+
        " a.goodsid=c.goodsid inner join TabGoodsTypeCode d on c.goodstype=d.goodstype inner join TabdepartInfo e on a.deptid=e.id where a.confirmflage=1";
        strSQL=strSQL+joinstr;
        strSQL1=strSQL1+joinstr;
        
        String whereStr = "";
        if(gif.getBillId()!= 0)
            whereStr = whereStr + " and a.BillID=" + gif.getBillId();
        if(!gif.getGoodsName().equals(""))
            whereStr = whereStr + " and a.GoodsName like '%" + StrUtility.replaceString(gif.getGoodsName(), "'", "''") + "'";
        if(!gif.getGoodsId().equals(""))
            whereStr = whereStr + " and a.GoodsID like '%" + StrUtility.replaceString(gif.getGoodsId(), "'", "''") + "%'";
        if(!gif.getCreatePerson().equals(""))
            whereStr = whereStr + " and a.CreatePerson ='" + gif.getCreatePerson() + "'";
        if(gif.getStoreId()!= 0)
            whereStr = whereStr + " and a.storeid=" + gif.getStoreId();
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
        if(iisr.getKind()>0)
        	whereStr = whereStr + " and a.checkAmount>0";
        if(iisr.getKind()<0)
        	whereStr = whereStr + " and a.checkAmount<0";
        if(iisr.getGif().getDeptid()>0)
        	whereStr = whereStr + " and a.deptid="+iisr.getGif().getDeptid();
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
            		GoodsCheckGoodsInfo giii = new GoodsCheckGoodsInfo();
                    giii.setBillId(rs.getInt("billid"));
                    giii.setGoodsId(rs.getString("goodsid"));
                    giii.setGoodsName(rs.getString("goodsname"));
                    giii.setCheckAmount(rs.getFloat("checkAmount"));
                    giii.setCheckUnitPrice(rs.getFloat("checkUnitPrice"));
                    giii.setCreatePerson(rs.getString("createperson"));
                    giii.setCreateTime(oConvert.FormDateTimeShort(rs.getTimestamp("createTime")));
                    giii.setStoreId(rs.getInt("storeid"));
                    giii.setStoreName(rs.getString("name"));
                    giii.setChandi(rs.getString("chandi"));
                    giii.setDeptid(rs.getInt("deptid"));
                    giii.setShortname(rs.getString("shortname"));
                    iisri.rslist.add(giii);
                    if(!rs.next())
	            		break;
                }

            }
        	rs.close();
        	rs = stmt.executeQuery(strSQL1);
        	if(rs.next())
        	{
        		iisri.allsumnum=oConvert.getRound(rs.getDouble(1),3);
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

    
}
