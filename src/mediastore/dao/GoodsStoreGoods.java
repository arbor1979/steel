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
import mediastore.rule.ChangeStoreItemSearchRule;
import mediastore.user.StoreManager;
import mediastore.util.StrUtility;
import mediastore.util.oConvert;
import mediastore.web.form.ChangeStoreGoodsForm;
import mediastore.web.form.GoodsCheckGoodsInfo;
import mediastore.web.form.GoodsInfoForm;
import mediastore.web.form.KaiPingForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.global.Globals;

// Referenced classes of package mediastore.dao:
//            GoodsImportInfo, GoodsInfo

public class GoodsStoreGoods
{

    public GoodsStoreGoods()
    {
    }

    public int insertNewGoodsRec(ChangeStoreGoodsForm gif) throws Exception
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
            strSQL="select * from TabChangeStoreInfo where billid="+gif.getBillId()+" and deptid="+gif.getDeptid()+" and confirmFlage='1'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("此转仓单已经存在");
            rs.close();
            strSQL="select * from TabChangeStoreInfo where billid="+gif.getBillId()+" and deptid="+gif.getDeptid();
            rs=stmt.executeQuery(strSQL);
            rs.next();
            if(rs.getInt("fromstore")!=gif.getFromstore())
            	throw new Exception("转出仓库不一致");
            rs.close();
            strSQL="select * from TabChangeStoreGoods where billid="+gif.getBillId()+" and goodsid='"+gif.getGoodsId()+"' and deptid="+gif.getDeptid();
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("此单中已包含同一仓库编号为"+gif.getGoodsId()+"的商品");
            rs.close();
            GoodsInfo gi=new GoodsInfo();
        	double kucun=gi.getRepertoryAmount(gif.getGoodsId(),gif.getFromstore());
            if(kucun-gif.getChangeAmount()<0)
            	throw new Exception("编号为"+gif.getGoodsId()+"的产品库存不足，仅剩"+kucun+"吨");
            gif.setChangeUnitPrice(gi.getAvgInPrice(conn,gif.getGoodsId(),gif.getDeptid()));
        	strSQL = "insert into TabChangeStoreGoods( BillID, GoodsID, GoodsName,ChangeNum,curRepertory, ChangeAmount, ChangeUnitPrice,"+
        	"CreatePerson, deptid) values (" + gif.getBillId() + ", '" + 
        	gif.getGoodsId() + "', '" + gif.getGoodsName() +"',"+gif.getChangeNum()+","+gif.getCurRepertory()+
        	","+gif.getChangeAmount()+"," + gif.getChangeUnitPrice() + ", '" + gif.getCreatePerson() + 
        	"',"+gif.getDeptid()+")";
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
            strSQL = "SELECT a.*,c.guige,c.chandi,c.caizhi,c.xishu,c.danwei FROM TabChangeStoreGoods a  "+
            "inner join TabGoodsInfo c on a.goodsid=c.goodsid WHERE a.BillID=" + billId + " and a.deptid="+deptid+" Order by a.id";
            ChangeStoreGoodsForm gigi;
            for(rs = stmt.executeQuery(strSQL); rs.next(); goodsList.add(gigi))
            {
                gigi = new ChangeStoreGoodsForm();
                gigi.setId(rs.getInt("id"));
                gigi.setBillId(rs.getInt("billid"));
                gigi.setGoodsId(rs.getString("goodsid"));
                gigi.setGoodsName(rs.getString("goodsname"));
                gigi.setChangeNum(rs.getInt("changeNum"));
                gigi.setCurRepertory(rs.getDouble("CurRepertory"));
                gigi.setChangeAmount(rs.getDouble("changeAmount"));
                gigi.setChangeUnitPrice(rs.getDouble("changeUnitPrice"));
                gigi.setCreatePerson(rs.getString("createPerson"));
                gigi.setCreateTime(rs.getString("createTime"));
                gigi.setConfirmFlage(rs.getString("confirmFlage"));
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
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) {}
        }
        return goodsList;
    }

    public void deleteGoodsRec(ChangeStoreGoodsForm gif) throws Exception
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
            String strSQL = "DELETE FROM TabChangeStoreGoods WHERE id=" + gif.getId() + " and createPerson='" + gif.getCreatePerson() +"'"; 
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
    public int updateChangeNum(ChangeStoreGoodsForm geg) throws Exception
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
            strSQL = "select a.*,b.fromstore from TabChangeStoreGoods a inner join TabChangeStoreInfo b on (a.billid=b.billid and a.deptid=b.deptid) WHERE a.id = " + geg.getId();
            rs=stmt.executeQuery(strSQL); 
            if(rs.next())
            {
            	geg.setGoodsId(rs.getString("goodsid"));
            	geg.setFromstore(rs.getInt("fromstore"));
            	geg.setChangeAmount(rs.getDouble("changeAmount"));
            }
            else
            	throw new Exception("找不到记录");
            rs.close();
            
            GoodsInfo gi=new GoodsInfo();
            GoodsInfoForm g=gi.getGoodsInfoByGoodsId(geg.getGoodsId());
            if(g.getXishu()>0)
            {
	            int numPerJian=(int)oConvert.getRound(g.getJianxishu()/g.getXishu(),0);
	            if(numPerJian>0)
	            	geg.setChangeAmount(Math.floor(geg.getChangeNum()/numPerJian)*g.getJianxishu()/1000+geg.getChangeNum()%numPerJian*g.getXishu()/1000);
	            else
	            	geg.setChangeAmount(geg.getChangeNum()*g.getXishu()/1000);
	            
	            double kucun=gi.getRepertoryAmount(geg.getGoodsId(),geg.getFromstore());
	            if(kucun-geg.getChangeAmount()<0)
	            	throw new Exception("编号为"+geg.getGoodsId()+"的产品库存不足，仅剩"+kucun+"吨");
            }
            strSQL = "UPDATE TabChangeStoreGoods SET ChangeNum="+geg.getChangeNum()+",ChangeAmount = (" + geg.getChangeAmount() + ") WHERE id = " + geg.getId() + " and createPerson='" + StrUtility.replaceString(geg.getCreatePerson(), "'", "''") + "'";
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("更新数量失败");
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
    public void updateChangeAmount(ChangeStoreGoodsForm gif) throws Exception
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
            strSQL = "select a.*,b.fromstore from TabChangeStoreGoods a inner join TabChangeStoreInfo b on (a.billid=b.billid and a.deptid=b.deptid) WHERE a.id = " + gif.getId();
            rs=stmt.executeQuery(strSQL); 
            if(rs.next())
            {
            	gif.setGoodsId(rs.getString("goodsid"));
            	gif.setFromstore(rs.getInt("fromstore"));
            	gif.setChangeNum(rs.getInt("changenum"));
            }
            else
            	throw new Exception("找不到记录");
            rs.close();
            GoodsInfo gi=new GoodsInfo();
            double kucun=gi.getRepertoryAmount(gif.getGoodsId(),gif.getFromstore());
            if(kucun-gif.getChangeAmount()<0)
            	throw new Exception("编号为"+gif.getGoodsId()+"的产品库存不足，仅剩"+kucun+"吨");
            
            GoodsInfoForm g=gi.getGoodsInfoByGoodsId(gif.getGoodsId());
            if(g.getXishu()>0)
            	gif.setChangeNum(gif.getChangeAmount()*1000/g.getXishu());
            strSQL = "UPDATE TabChangeStoreGoods SET ChangeNum="+gif.getChangeNum()+",ChangeAmount = " + gif.getChangeAmount() + " WHERE id = " + gif.getId() +
            " and createperson='" + gif.getCreatePerson() + "'";
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新重量失败");
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
    
    public int deleteAllGoods(ChangeStoreGoodsForm gif)
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
            String strSQL = "DELETE FROM TabChangeStoreGoods WHERE BillID=" + gif.getBillId() + " and deptid="+gif.getDeptid()+" and CreatePerson='"+gif.getCreatePerson()+"'";
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
	public ResultsetList getItemListByRule(ChangeStoreItemSearchRule iisr)
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
        ChangeStoreGoodsForm gif=iisr.getGif();
        
        strSQL = " SELECT a.*,b.fromstore,b.tostore,c.chandi,e.shortname FROM TabChangeStoreGoods a ";
        strSQL1 = " SELECT sum(a.changeamount) as allnum,sum(a.changeamount*a.changeunitprice) as allprice FROM TabChangeStoreGoods a ";
        String joinstr="inner join TabChangeStoreInfo b on (a.billid=b.billid and a.deptid=b.deptid) inner join TabGoodsInfo c on"+
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
        if(gif.getFromstore()!= 0)
            whereStr = whereStr + " and fromstore=" + gif.getFromstore();
        if(gif.getTostore()!= 0)
            whereStr = whereStr + " and tostore=" + gif.getTostore();
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
            	StoreManager sm=new StoreManager();
            	for(int i=0;i<iisri.pagesize;i++)
            	{
            		ChangeStoreGoodsForm giii = new ChangeStoreGoodsForm();
                    giii.setBillId(rs.getInt("billid"));
                    giii.setGoodsId(rs.getString("goodsid"));
                    giii.setGoodsName(rs.getString("goodsname"));
                    giii.setChangeAmount(rs.getDouble("changeAmount"));
                    giii.setChangeUnitPrice(rs.getDouble("changeUnitPrice"));
                    giii.setCreatePerson(rs.getString("createperson"));
                    giii.setCreateTime(oConvert.FormDateTimeShort(rs.getTimestamp("createTime")));
                    giii.setFromstore(rs.getInt("fromstore"));
                    giii.setTostore(rs.getInt("tostore"));
                    giii.setFromname(sm.getDepartByID(giii.getFromstore()).getName());
                    giii.setToname(sm.getDepartByID(giii.getTostore()).getName());
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

    //新插入一个库存商品变更明细
    public int insertNewKaiPingGoods(KaiPingForm gif) throws Exception
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
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            strSQL="select * from TabKaiPingGoods where billid="+gif.getBillid()+" and goodsid='"+gif.getFromGoodsId()+
            "' and storeid="+gif.getFromStore()+" and deptid="+gif.getDeptid();
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("此单中已包同样编号的商品");
            rs.close();
            if(gif.getKind()==-1)
            {
            	GoodsInfo gi=new GoodsInfo();
				gif.setFromPrice(gi.getAvgInPrice(conn,gif.getFromGoodsId(),gif.getDeptid()));
            }
        	strSQL = "insert into TabKaiPingGoods (billid,deptid,goodsid,storeid,curRepertory,Price,"+
        	"createPerson,kind) values (" + gif.getBillid() + ","+gif.getDeptid()+",'" +gif.getFromGoodsId()+
        	"',"+gif.getFromStore()+","+gif.getCurRepertory()+","+gif.getFromPrice()+",'"+gif.getCreatePerson()+"',"+gif.getKind()+")";
        	nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
            {
                nRet = -1;
                throw new Exception("插入新记录错误");
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
            catch(SQLException ex) { }
        }
        return nRet;
    }
    //删除开平记录
    public void deleteKaiPingGoods(KaiPingForm gif) throws Exception
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
            String strSQL = "DELETE FROM TabKaiPingGoods WHERE id=" + gif.getId() + " and createPerson='" + gif.getCreatePerson() +"'"; 
            stmt = conn.createStatement();
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
                throw new Exception("删除记录失败");
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
    
    public int updateFromNum(KaiPingForm geg) throws Exception
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
            strSQL = "select * from TabKaiPingGoods WHERE id = " + geg.getId();
            rs=stmt.executeQuery(strSQL); 
            if(rs.next())
            {
            	geg.setFromGoodsId(rs.getString("goodsid"));
            	geg.setFromStore(rs.getInt("storeid"));
            	geg.setFromAmount(rs.getDouble("Amount"));
            	geg.setKind(rs.getInt("kind"));
            	if(geg.getKind()==-1)
            	{
            		GoodsInfo gi=new GoodsInfo();
            		double avgprice=0;
            		if(geg.getKind()==-1)
            			avgprice=gi.getAvgInPrice(conn,rs.getString("goodsid"), rs.getInt("deptid"));
            		geg.setFromPrice(avgprice);
            	}
            	else
            		geg.setFromPrice(rs.getDouble("price"));
            		
            	
            }
            else
            	throw new Exception("找不到记录");
            rs.close();
            
            GoodsInfo gi=new GoodsInfo();
            GoodsInfoForm g=gi.getGoodsInfoByGoodsId(geg.getFromGoodsId());
            if(g.getXishu()>0)
            {
	            int numPerJian=(int)oConvert.getRound(g.getJianxishu()/g.getXishu(),0);
	            if(numPerJian>0)
	            	geg.setFromAmount(Math.floor(geg.getFromNum()/numPerJian)*g.getJianxishu()/1000+geg.getFromNum()%numPerJian*g.getXishu()/1000);
	            else
	            	geg.setFromAmount(geg.getFromNum()*g.getXishu()/1000);
	            
	            double kucun=gi.getRepertoryAmount(geg.getFromGoodsId(),geg.getFromStore());
	            if(geg.getKind()==-1 && kucun-geg.getFromAmount()<0)
	            	throw new Exception("编号为"+geg.getFromGoodsId()+"的产品库存不足，仅剩"+kucun+"吨");
            }
            strSQL = "UPDATE TabKaiPingGoods SET Num="+geg.getFromNum()+",Amount = (" + geg.getFromAmount() + "),price="+geg.getFromPrice()+
            ",jine="+oConvert.getRound(geg.getFromAmount()*geg.getFromPrice(),2)+" WHERE id = " + geg.getId() + " and createPerson='" + StrUtility.replaceString(geg.getCreatePerson(), "'", "''") + "'";
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("更新数量失败");
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
    
    public void updateFromAmount(KaiPingForm gif) throws Exception
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
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            strSQL = "select * from TabKaiPingGoods  WHERE id = " + gif.getId();
            rs=stmt.executeQuery(strSQL); 
            if(rs.next())
            {
            	gif.setFromGoodsId(rs.getString("goodsid"));
            	gif.setFromStore(rs.getInt("storeid"));
            	gif.setFromNum(rs.getInt("num"));
            	gif.setKind(rs.getInt("kind"));
            	GoodsInfo gi=new GoodsInfo();
            	double avgprice=0;
            	if(gif.getKind()==-1)
            	{
            		avgprice=gi.getAvgInPrice(conn,rs.getString("goodsid"), rs.getInt("deptid"));
            		gif.setFromPrice(avgprice);
            	}
            	else
            		gif.setFromPrice(rs.getDouble("price"));
            }
            else
            	throw new Exception("找不到记录");
            rs.close();
            GoodsInfo gi=new GoodsInfo();
            double kucun=gi.getRepertoryAmount(gif.getFromGoodsId(),gif.getFromStore());
            if(gif.getKind()==-1 && kucun-gif.getFromAmount()<0)
            	throw new Exception("编号为"+gif.getFromGoodsId()+"的产品库存不足，仅剩"+kucun+"吨");
            
            GoodsInfoForm g=gi.getGoodsInfoByGoodsId(gif.getFromGoodsId());
            if(g.getXishu()>0)
            	gif.setFromNum((int)oConvert.getRound(gif.getFromAmount()*1000/g.getXishu(),0));
            strSQL = "UPDATE TabKaiPingGoods SET Num="+gif.getFromNum()+",Amount = " + gif.getFromAmount() + 
            ",price="+gif.getFromPrice()+",jine="+oConvert.getRound(gif.getFromAmount()*gif.getFromPrice(),2)+" WHERE id = " + gif.getId() +
            " and createperson='" + gif.getCreatePerson() + "'";
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新重量失败");
            conn.commit();
        }
        catch(Exception e)
        {
        	conn.rollback();
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
    
    public void updateJine(KaiPingForm gif) throws Exception
    {
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        int billid=0;
        int deptid=0;
        double amount=0;
        double jine=0;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            strSQL = "select * from TabKaiPingGoods  WHERE id = " + gif.getId();
            rs=stmt.executeQuery(strSQL); 
            if(rs.next())
            {
            	billid=rs.getInt("billid");
            	deptid=rs.getInt("deptid");
            	amount=rs.getDouble("amount");
            }
            else
            	throw new Exception("找不到记录");
            if(amount<=0)
            	throw new Exception("请先填写重量！");
            rs.close();
            strSQL="select sum(amount*price) from TabKaiPingGoods where kind=-1 and billid="+billid+" and deptid="+deptid;
            rs=stmt.executeQuery(strSQL);
            rs.next();
            jine=rs.getDouble(1);
            strSQL="select count(*) from TabKaiPingGoods where kind=1 and billid="+billid+" and deptid="+deptid;
            rs=stmt.executeQuery(strSQL);
            rs.next();
            if(rs.getInt(1)==1 || gif.getJine()>=jine)
            {
            	strSQL = "UPDATE TabKaiPingGoods SET jine="+oConvert.getRound(jine,2)+",price = " + oConvert.getRound(jine/amount, 2)+" WHERE id = " + gif.getId() +
                " and createperson='" + gif.getCreatePerson() + "'";
            }
            else if(rs.getInt(1)==2)
            {
            	strSQL = "select * from TabKaiPingGoods where kind=1 and billid="+billid+" and deptid="+deptid+" and id<>"+gif.getId();
            	rs=stmt.executeQuery(strSQL);
            	rs.next();
            	int id=rs.getInt("id");
            	double amount1=rs.getDouble("amount");
            	strSQL = "UPDATE TabKaiPingGoods SET jine="+(jine-gif.getJine())+",price = " + oConvert.getRound((jine-gif.getJine())/amount1, 2)+" WHERE id = " + id +
                " and createperson='" + gif.getCreatePerson() + "'";
            	stmt.executeUpdate(strSQL);
            	strSQL = "UPDATE TabKaiPingGoods SET jine="+oConvert.getRound(gif.getJine(),2)+",price = " + oConvert.getRound(gif.getJine()/amount, 2)+" WHERE id = " + gif.getId() +
                " and createperson='" + gif.getCreatePerson() + "'";
            }
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新金额失败");
            conn.commit();
        }
        catch(Exception e)
        {
        	conn.rollback();
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
    
    
    public int deleteAllKaiPingGoods(KaiPingForm gif)
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
            String strSQL = "DELETE FROM TabKaiPingGoods WHERE deptid="+gif.getDeptid()+" and CreatePerson='"+gif.getCreatePerson()+"' and confirmflage='0'";
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
	public List getKaiPingGoodsList(KaiPingForm gif) throws Exception
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
            GoodsInfo gi=new GoodsInfo();
            GoodsInfoForm g=null;
            strSQL = "select * from TabKaiPingGoods WHERE billid="+gif.getBillid()+" and deptid="+gif.getDeptid()+" order by kind,id";
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
                KaiPingForm gigi = new KaiPingForm();
                gigi.setId(rs.getInt("id"));
                gigi.setBillid(rs.getInt("billid"));
                gigi.setFromGoodsId(rs.getString("goodsid"));
                g=gi.getGoodsInfoByGoodsId(gigi.getFromGoodsId());
                gigi.setFromGuige(g.getGuige());
                gigi.setFromGoodsName(g.getGoodsTypeName());
                gigi.setFromNum(rs.getInt("Num"));
                gigi.setCurRepertory(rs.getDouble("CurRepertory"));
                gigi.setFromAmount(rs.getDouble("Amount"));
                gigi.setFromPrice(rs.getDouble("Price"));
                gigi.setJine(rs.getDouble("jine"));
                gigi.setKind(rs.getInt("kind"));
                gigi.setCreatePerson(rs.getString("createPerson"));
                gigi.setGuige(g.getGuige());
                gigi.setChandi(g.getChandi());
                gigi.setDanwei(g.getDanwei());
                gigi.setCaizhi(g.getCaizhi());
                goodsList.add(gigi);
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
}
