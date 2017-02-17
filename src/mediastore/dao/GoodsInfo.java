// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsInfo.java

package mediastore.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mediastore.common.DBConnection;
import mediastore.util.StrUtility;
import mediastore.util.oConvert;
import mediastore.web.form.GoodsInfoForm;
import mediastore.web.form.ResultsetList;

// Referenced classes of package mediastore.dao:
//            GoodsTypeCode, GoodsClassCode, GoodsShelfIDCode, GoodsImportGoods

public class GoodsInfo
{

    public GoodsInfo()
    {
    }


    public int insertGoodsInfo(GoodsInfoForm gif) throws Exception
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
            strSQL="select * from TabGoodsInfo where goodsid='"+gif.getGoodsId()+"'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("已存在此编号的产品");
            rs.close();
            
        	strSQL = "insert into TabGoodsInfo(GoodsID, Goodstype, purchaseUnitPrice,guige,caizhi,danwei,xishu,chandi,jianxishu)"+
        	" values ('" + gif.getGoodsId()+ "'," + gif.getGoodsType()+","+gif.getPurchaseUnitPrice()+",'"+
        	gif.getGuige()+"','"+gif.getCaizhi()+"','"+gif.getDanwei()+"',"+gif.getXishu()+",'"+gif.getChandi()+"',"+
        	+gif.getJianxishu()+")";
        	nRet = stmt.executeUpdate(strSQL);
        	if(nRet != 1)
                throw new Exception("插入新记录错误");
            
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
            catch(SQLException ex) { }
        }
        return nRet;
    }
    public int UpdateGoodsInfo(GoodsInfoForm gif) throws Exception
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
            if(!gif.getOldGoodsId().equals(gif.getGoodsId()))
            {
            	/*
            	strSQL="select * from TabGoodsImportGoods where goodsid='"+oldGoodsId+"'";
                rs=stmt.executeQuery(strSQL);
                if(rs.next())
                	throw new Exception("此产品已经存在入库记录，产品编号无法再修改");
                rs.close();
                */
                strSQL="select * from TabGoodsInfo where goodsid='"+gif.getGoodsId()+"' and goodsid<>'"+gif.getOldGoodsId()+"'";
                rs=stmt.executeQuery(strSQL);
                if(rs.next())
                	throw new Exception("已存在此编号的产品");
                rs.close();
            }
            
            strSQL = "update TabGoodsInfo set GoodsID='"+gif.getGoodsId()+"',Goodstype="+gif.getGoodsType()+","+
            "purchaseUnitPrice="+gif.getPurchaseUnitPrice()+",guige='"+gif.getGuige()+"',caizhi='"+gif.getCaizhi()+
            "',danwei='"+gif.getDanwei()+"',xishu="+gif.getXishu()+",chandi='"+gif.getChandi()+"',jianxishu="+gif.getJianxishu()+
            " where goodsid='"+gif.getOldGoodsId()+"'";
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
                throw new Exception("更新新记录错误");
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
            catch(SQLException ex) { }
        }
        return nRet;
    }
    public int UpdateGoodsPrice(GoodsInfoForm gif) throws Exception
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            if(gif.getPurchaseUnitPrice()<0)
            	throw new Exception("销售价格必须大于0");
            strSQL = "update TabGoodsInfo set purchaseUnitPrice="+gif.getPurchaseUnitPrice()+" where goodsid='"+gif.getGoodsId()+"'";
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
                throw new Exception("更新新记录错误");
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
            catch(SQLException ex) { }
        }
        return nRet;
    }
    public int DeleteGoodsInfo(String goodsId) throws Exception
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
            strSQL="select * from TabGoodsImportGoods where goodsid='"+goodsId+"'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("此产品已经存在入库记录，无法删除");
            rs.close();
            strSQL="select * from TabKaiPingGoods where goodsid='"+goodsId+"' and kind=1";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("此产品已经存在二次加工记录，无法删除");
            rs.close();
            strSQL = "delete from TabGoodsInfo where goodsid='"+goodsId+"'";
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
                throw new Exception("删除新记录错误");
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
            catch(SQLException ex) { }
        }
        return nRet;
    }
    //商品是否有过入库
    public boolean ifGoodsInUse(String goodsId) throws Exception
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
            strSQL="select count(*) as allnum from TabGoodsImportGoods where goodsid='"+goodsId+"'";
            rs=stmt.executeQuery(strSQL);
            rs.next();
            if(rs.getInt("allnum")>0)
            	return true;
            else
            	return false;
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
            catch(SQLException ex) { }
        }
    }
    
//取得当前库存
    public double getRepertoryAmount(String goodsId,int storeid)
    {
    	DBConnection dbc=null;
    	Connection conn=null;
    	double ret=0;
    	try
        {
    		dbc = new DBConnection();
    		conn = dbc.getDBConnection();
    		ret=getRepertoryAmount(goodsId,storeid,conn);
    		
        }
    	catch(Exception exception) { }
    	finally
        {
            try
            {
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
    	return ret;
    }
    public double getRepertoryAmount(String goodsId,int storeid,Connection conn)
    {
    	double nRet=0;
        DBConnection dbc = null;
        
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        try
        {

            stmt = conn.createStatement();
            strSQL = "SELECT RepertoryAmount FROM TabGoodsRepertory WHERE GoodsID='" + 
            StrUtility.replaceString(goodsId, "'", "''") + "' and storeid="+storeid;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	nRet = oConvert.getRound(rs.getDouble(1),4);
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
            }
            catch(SQLException e) { }
        }
        return nRet;
    }
    public double getRepertoryAmountGroup(String goodsId,int deptid)
    {
    	double nRet=0;
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
            strSQL = "SELECT sum(RepertoryAmount) FROM TabGoodsRepertory WHERE GoodsID='" + 
            StrUtility.replaceString(goodsId, "'", "''") + "' and storeid in (select id from TabStore where deptid="+deptid+")";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	nRet = oConvert.getRound(rs.getDouble(1),3);
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
        return nRet;
    }
    public float getPurchaseUnitPrice(String goodsId)
    {
        float purchaseUnitPrice;
        purchaseUnitPrice = 0.0F;
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
            strSQL = "SELECT PurchaseUnitPrice FROM TabGoodsInfo WHERE GoodsID='" + StrUtility.replaceString(goodsId, "'", "''") + "'";
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            {
                throw new Exception("获得商品价格失败");
            }
            purchaseUnitPrice = rs.getFloat(1);
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
        return purchaseUnitPrice;
    }
    public double getAvgInPrice(Connection conn,String goodsId,int deptid) throws Exception
    {
        double AvgInPrice;
        AvgInPrice = 0;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        try
        {
            stmt = conn.createStatement();
            strSQL="select * from TabGoodsRepertory where goodsid='"+goodsId+"' and storeid in (select id from TabStore where deptid="+deptid+")";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            	AvgInPrice=rs.getDouble("avgprice");
            else
            {
            	rs.close();
            	strSQL="select Round(sum(importUnitPrice*importAmount),4),Round(sum(importAmount),4) from TabGoodsImportGoods where goodsid='"+
            	goodsId+"' and deptid="+deptid+" and ConfirmFlage = '1'";
                rs = stmt.executeQuery(strSQL);
                rs.next();
                if(rs.getDouble(2)==0)
                	throw new Exception("此商品历史入库总数为0，无法计算平均入库价");
                else
                	AvgInPrice = oConvert.getRound(rs.getDouble(1)/rs.getDouble(2),4);
            }
               
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
            }
            catch(SQLException e) { }
        }
        return AvgInPrice;
    }
    //取得销售价格
    public float getSellPrice(String goodsId,int deptid)
    {
        float purchaseUnitPrice;
        purchaseUnitPrice = 0.0F;
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
            strSQL = "SELECT sellprice FROM TabSellPrice WHERE GoodsID='" + 
            StrUtility.replaceString(goodsId, "'", "''") + "' and deptid="+deptid;
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            {
                throw new Exception("获得商品价格失败");
            }
            purchaseUnitPrice = rs.getFloat(1);
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
        return purchaseUnitPrice;
    }
    public int updateGoodsName(String goodsId, String newGoodsName)
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "UPDATE TabGoodsInfo SET GoodsName = '" + StrUtility.replaceString(newGoodsName, "'", "''") + "' WHERE GoodsID = '" + StrUtility.replaceString(goodsId, "'", "''") + "'";
            stmt.executeUpdate(strSQL);
            strSQL = "insert into sqlLog (sqltext) values ('" + StrUtility.replaceString(strSQL, "'", "''")+ "')";
            stmt.executeUpdate(strSQL);
        }
        catch(Exception e)
        {
            nRet = -1;
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

    
    public int updateGoodsType(String goodsId, String newGoodsType)
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "UPDATE TabGoodsInfo SET GoodsType = '" + StrUtility.replaceString(newGoodsType, "'", "''") + "' WHERE GoodsID = '" + StrUtility.replaceString(goodsId, "'", "''") + "'";
            stmt.executeUpdate(strSQL);
            strSQL = "insert into sqlLog (sqltext) values ('" + StrUtility.replaceString(strSQL, "'", "''")+ "')";
            stmt.executeUpdate(strSQL);
        }
        catch(Exception e)
        {
            nRet = -1;
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

    public int updatePurchaseUnitPrice(String goodsId, float newPurchaseUnitPrice)
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "UPDATE TabGoodsInfo SET PurchaseUnitPrice = " + newPurchaseUnitPrice + " WHERE GoodsID = '" + StrUtility.replaceString(goodsId, "'", "''") + "'";
            stmt.executeUpdate(strSQL);
            strSQL = "insert into sqlLog (sqltext) values ('" + StrUtility.replaceString(strSQL, "'", "''")+ "')";
            stmt.executeUpdate(strSQL);
        }
        catch(Exception e)
        {
            nRet = -1;
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
	public List getGoodsTypeList()
    {
        List goodsTypeList;
        goodsTypeList = new ArrayList();
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
            strSQL = "SELECT DISTINCT TabGoodsTypeCode.GoodsType, TabGoodsTypeCode.GoodsTypeName FROM TabGoodsInfo,TabGoodsTypeCode WHERE TabGoodsInfo.GoodsType=TabGoodsTypeCode.GoodsType ";
            String tmp[];
            for(rs = stmt.executeQuery(strSQL); rs.next(); goodsTypeList.add(tmp))
            {
                tmp = new String[2];
                tmp[0] = rs.getString(1);
                tmp[1] = rs.getString(2);
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
        return goodsTypeList;
    }
	@SuppressWarnings("unchecked")
	public List getChanDiList()
    {
        List goodsTypeList;
        goodsTypeList = new ArrayList();
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
            strSQL = "SELECT DISTINCT chandi FROM TabGoodsInfo order by chandi";
            String tmp[];
            for(rs = stmt.executeQuery(strSQL); rs.next(); goodsTypeList.add(tmp))
            {
                tmp = new String[1];
                tmp[0] = rs.getString(1);
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
        return goodsTypeList;
    }
	@SuppressWarnings("unchecked")
	public ResultsetList getGoodsListByRule(GoodsInfoForm gif,int curpage,int pagesize,String orderby) throws Exception
    {
		ResultsetList gsri;
		gsri = new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        strSQL = " SELECT a.*,b.goodstypename,b.classid,c.goodsClassName FROM TabGoodsInfo a inner join TabGoodsTypeCode b on "+
        "a.goodstype=b.goodstype inner join TabGoodsClassCode c on b.Classid=c.GoodsClass where 1=1";
        String whereStr="";
        if(gif.getGoodsId()!= null && gif.getGoodsId().length()>0)
            whereStr = whereStr + " and a.Goodsid like '%" + StrUtility.replaceString(gif.getGoodsId(), "'", "''") + "%'";
        if(oConvert.getInt(gif.getGoodsClass(),0)!=0)
            whereStr = whereStr + " and b.classid=" + StrUtility.replaceString(gif.getGoodsClass(), "'", "''");
        if(oConvert.getInt(gif.getGoodsType(),0)!=0)
            whereStr = whereStr + " and a.GoodsType=" + StrUtility.replaceString(gif.getGoodsType(), "'", "''");
        if(gif.getChandi().length()>0)
            whereStr = whereStr + " and a.chandi like '%" + StrUtility.replaceString(gif.getChandi(), "'", "''")+"%'";
        strSQL=strSQL+whereStr+" order by "+orderby;
      
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(strSQL);
            if(rs.last())
            {
            	int RowCount=rs.getRow();
            	gsri.allnum=RowCount;
            	gsri.curpage=curpage;
            	gsri.pagesize=pagesize;
            	int PageCount = ((RowCount % gsri.pagesize) == 0 ?(RowCount/gsri.pagesize) : (RowCount/gsri.pagesize)+1);
            	gsri.pagecount=PageCount;
            	if(gsri.curpage > PageCount)  
            	{
            		gsri.curpage = PageCount; 
            	}
            	else if(gsri.curpage <= 0)
            	{
            		gsri.curpage = 1;  
            	}
            	rs.absolute((gsri.curpage - 1) * gsri.pagesize + 1);  
            	for(int i=0;i<gsri.pagesize;i++)
            	{
            		GoodsInfoForm gio = new GoodsInfoForm();
	            	gio.setGoodsClass(rs.getString("classid"));
	            	gio.setGoodsId(rs.getString("goodsid"));
	            	gio.setGoodsType(rs.getString("goodstype"));
	            	gio.setGoodsTypeName(rs.getString("goodstypename"));
	            	gio.setPurchaseUnitPrice(rs.getFloat("purchaseUnitPrice"));
	            	gio.setGuige(oConvert.getString(rs.getString("guige"),""));
	            	gio.setCaizhi(oConvert.getString(rs.getString("caizhi"),""));
	            	gio.setXishu(rs.getFloat("xishu"));
	            	gio.setChandi(rs.getString("chandi"));
	            	gio.setGoodsClass(rs.getString("classid"));
	            	gio.setGoodsClassName(rs.getString("goodsclassname"));
	            	gio.setJianxishu(rs.getFloat("jianxishu"));
	            	gsri.rslist.add(gio);
	            	if(!rs.next())
	            		break;
            	}
            }
            rs.close();
        
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
        return gsri;
    }
	@SuppressWarnings("unchecked")
	public ResultsetList getRepertoryListByRule(GoodsInfoForm gif,String orderby,int factid,int kind) throws Exception
    {
		ResultsetList gsri;
		gsri = new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        String strSQL = null;
        String strSQL1 = null;
        try
        {
        	dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
           
	        strSQL = " SELECT a.repertoryAmount,a.repertoryNum,a.storeid,a.avgprice,a.memo,b.*,c.name,c.deptid,d.goodstypename,d.classid,"+
	        	"e.shortname,case when f.sellprice>0 then f.sellprice else b.purchaseUnitPrice end as realprice FROM TabGoodsRepertory a inner join TabGoodsInfo b on "+
	        	"a.goodsid=b.goodsid inner join TabStore c on a.storeid=c.id inner join TabGoodsTypeCode d "+
	        	"on b.goodstype=d.goodstype inner join TabDepartInfo e on c.deptid=e.id left join TabSellPrice f "+
	        	"on (a.goodsid=f.goodsid and c.deptid=f.deptid) where a.repertoryAmount<>0";
	        strSQL1="select sum(repertoryAmount) as allnum,sum(repertoryAmount*avgprice) as allmoney from TabGoodsRepertory a inner join TabGoodsInfo b on "+
	        "a.goodsid=b.goodsid inner join TabStore c on a.storeid=c.id inner join TabGoodsTypeCode d "
	        	+"on b.goodstype=d.goodstype where a.repertoryAmount<>0";
	        String whereStr="";
	        if(gif.getGoodsId()!= null && gif.getGoodsId().length()>0)
	            whereStr = whereStr + " and a.Goodsid like '%" + StrUtility.replaceString(gif.getGoodsId(), "'", "''") + "%'";
	        if(oConvert.getInt(gif.getGoodsClass(),0)!=0)
	            whereStr = whereStr + " and d.classid=" + StrUtility.replaceString(gif.getGoodsClass(), "'", "''");
	        if(oConvert.getInt(gif.getGoodsType(),0)!=0)
	            whereStr = whereStr + " and b.GoodsType=" + StrUtility.replaceString(gif.getGoodsType(), "'", "''");
	        if(gif.getStoreid()!=0)
	        	whereStr = whereStr + " and a.storeid=" + gif.getStoreid();
	        if(gif.getChandi().length()>0)
	        	whereStr = whereStr + " and b.chandi like '%" + gif.getChandi()+"%'";
	        if(gif.getDeptid()>0)
	        	whereStr = whereStr + " and c.deptid=" + gif.getDeptid();
	        strSQL=strSQL+whereStr;
	        if(orderby.length()>0)
	        	strSQL=strSQL+" order by "+orderby;
	        strSQL1=strSQL1+whereStr;
        
	        rs = stmt.executeQuery(strSQL);
            GoodsInfoForm gio=null;
            while(rs.next())
            {
        		gio = new GoodsInfoForm();
            	gio.setGoodsClass(rs.getString("classid"));
            	gio.setGoodsId(rs.getString("goodsid"));
            	gio.setGoodsType(rs.getString("goodstype"));
            	gio.setGoodsTypeName(rs.getString("goodstypename"));
            	gio.setPurchaseUnitPrice(rs.getDouble("realprice"));
            	
            	gio.setGuige(oConvert.getString(rs.getString("guige"),""));
            	gio.setCaizhi(oConvert.getString(rs.getString("caizhi"),""));
            	gio.setXishu(rs.getFloat("xishu"));
            	gio.setChandi(rs.getString("chandi"));
            	gio.setCurRepertory(rs.getDouble("repertoryAmount"));
            	gio.setCurRepertoryNum(rs.getInt("repertoryNum"));
            	gio.setDanwei(rs.getString("danwei"));
            	gio.setStoreName(rs.getString("name"));
            	gio.setStoreid(rs.getInt("storeid"));
            	gio.setJianxishu(rs.getFloat("jianxishu"));
            	gio.setDeptid(rs.getInt("deptid"));
            	gio.setShortname(rs.getString("shortname"));
            	gio.setAvgprice(rs.getDouble("avgprice"));
            	gio.setMemo(oConvert.getString(rs.getString("memo"),""));
            	gsri.rslist.add(gio);
	          
            }
            rs.close();
            rs = stmt.executeQuery(strSQL1);
            rs.next();
            gsri.allsumnum=oConvert.getRound(rs.getDouble(1),3);
            gsri.allmoney=oConvert.getRound(rs.getDouble(2), 2);
            if(factid!=0)
            {
            	for(int i=0;i<gsri.rslist.size();i++)
            	{
            		gio=(GoodsInfoForm)gsri.rslist.get(i);
            		if(kind==1)
            			strSQL="select top 1 exportAmount,exportunitprice,createtime from TabGoodsExportGoods a"+
            			" inner join TabGoodsExportInfo b on (a.billid=b.billid and a.deptid=b.deptid) where goodsid='"+
            			gio.getGoodsId()+"'"+" and factory="+factid+" and b.confirmflage='1' order by createtime desc";
            		else if (kind==-1)
            			strSQL="select top 1 importAmount,importunitprice,createtime from TabGoodsImportGoods a"+
            			" inner join TabGoodsImportInfo b on (a.billid=b.billid and a.deptid=b.deptid) where goodsid='"+
            			gio.getGoodsId()+"'"+" and factory="+factid+" and b.confirmflage='1' order by createtime desc";
            		rs=stmt.executeQuery(strSQL);
            		if(rs.next())
            		{
            			gio.setLastamount(rs.getDouble(1));
            			gio.setLastprice(rs.getDouble(2));
            			gio.setLasttime(oConvert.FormDateShort(rs.getTimestamp(3)));
            		}
            	}
            }
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
                if(rs1 != null)
                    rs1.close();
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return gsri;
    }
    public int getGoodsTypeCount(String goodsType)
    {
        int count;
        count = 0;
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
            strSQL = "SELECT count(*) FROM TabGoodsInfo WHERE GoodsType='" + StrUtility.replaceString(goodsType, "'", "''") + "'";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
                count = rs.getInt(1);
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
        return count;
    }

    public int getGoodsClassCount(String goodsClass)
    {
        int count;
        count = 0;
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
            strSQL = "SELECT count(*) FROM TabGoodsTypeCode WHERE Classid='" + StrUtility.replaceString(goodsClass, "'", "''") + "'";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
                count = rs.getInt(1);
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
        return count;
    }

    public int getGoodsShelfIdCount(String goodsShelfId)
    {
        int count;
        count = 0;
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
            strSQL = "SELECT count(*) FROM TabGoodsInfo WHERE storeid='" + goodsShelfId + "'";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
                count = rs.getInt(1);
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
        return count;
    }

    public int updateRepertoryAmount(String goodsId, int exportAmount, int repertoryAmount)
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "UPDATE TabGoodsInfo SET RepertoryAmount = " + (repertoryAmount - exportAmount) + " WHERE GoodsID = '" + StrUtility.replaceString(goodsId, "'", "''") + "'";
            stmt.executeUpdate(strSQL);
            strSQL = "insert into sqlLog (sqltext) values ('" + StrUtility.replaceString(strSQL, "'", "''")+ "')";
            stmt.executeUpdate(strSQL);
        }
        catch(Exception e)
        {
            nRet = -1;
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

    public GoodsInfoForm getGoodsInfoByGoodsId(String goodsId)
    {
    	GoodsInfoForm gio= null;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        strSQL = " SELECT a.*,b.goodstypename,b.classid,c.goodsclassname FROM TabGoodsInfo a inner join TabGoodsTypeCode b "
        	+"on a.goodstype=b.goodstype inner join TabGoodsClassCode c on b.classid=c.goodsclass"
        	+" where goodsid='"+goodsId+"'";
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	gio=new GoodsInfoForm();
            	gio.setGoodsClass(rs.getString("classid"));
            	gio.setGoodsClassName(rs.getString("goodsclassname"));
            	gio.setGoodsId(rs.getString("goodsid"));
            	gio.setGoodsType(rs.getString("goodstype"));
            	gio.setGoodsTypeName(rs.getString("goodstypename"));
            	gio.setPurchaseUnitPrice(rs.getDouble("purchaseUnitPrice"));
            	gio.setGuige(oConvert.getString(rs.getString("guige"),""));
            	gio.setCaizhi(oConvert.getString(rs.getString("caizhi"),""));
            	gio.setDanwei(oConvert.getString(rs.getString("danwei"),""));
            	gio.setXishu(rs.getFloat("xishu"));
            	gio.setChandi(oConvert.getString(rs.getString("chandi"),""));
            	gio.setJianxishu(rs.getFloat("jianxishu"));
            }
            rs.close();
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
        return gio;
    }
    public double CountAvgPrice(String goodsid,int deptid,double newAmount,double newPrice,Connection conn) throws Exception
    {
    	double avgprice=0;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        try
        {
            stmt = conn.createStatement();
        	strSQL="select sum(RepertoryAmount),sum(RepertoryAmount*avgprice) from TabGoodsRepertory where goodsid='"+goodsid+
            "' and storeid in (select id from TabStore where deptid="+deptid+")";
        	rs=stmt.executeQuery(strSQL);
        	rs.next();
        	
        	if(oConvert.getRound(rs.getDouble(1)+newAmount,4)==0)
        	{
        		if(oConvert.getRound(rs.getDouble(2)+newAmount*newPrice,4)!=0)
        			throw new Exception("编号为"+goodsid+"的商品库存为零，但库存金额不为零，无法计算加权平均价！");
        		else
        			avgprice=newPrice;
        	}
        	else
        		avgprice=oConvert.getRound((rs.getDouble(2)+newAmount*newPrice)/(rs.getDouble(1)+newAmount),4);
        	if(avgprice<0)
        		throw new Exception("加权平均价不能为负数，如有负库存请先报溢");
        	rs.close();
            
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
                
            }
            catch(SQLException e) { }
        }
        return avgprice;
    }
    public double getRepertMoney(String goodsid,int deptid) throws Exception
    {
    	double avgprice=0;
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
        	strSQL="select sum(RepertoryAmount*avgprice) from TabGoodsRepertory where goodsid='"+goodsid+
            "' and storeid in (select id from TabStore where deptid="+deptid+")";
        	rs=stmt.executeQuery(strSQL);
        	rs.next();
        	avgprice=rs.getDouble(1);
        	rs.close();
            
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
        return avgprice;
    }
    public int changeSellPrice(String goodsid,int deptid,double sellprice) throws Exception
    {
    	int Ret=0;
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
            strSQL="select * from TabSellPrice where goodsid='"+goodsid+"' and deptid="+deptid;
            rs=stmt.executeQuery(strSQL);
            if(!rs.next())
            {
            	strSQL="insert into TabSellPrice (goodsid,deptid,sellprice) values('"+goodsid+"',"+deptid+","+sellprice+")";
            }
            else
            {
            	strSQL="update TabSellPrice set sellprice="+sellprice+" where goodsid='"+goodsid+"' and deptid="+deptid;
            }
            Ret=stmt.executeUpdate(strSQL);
        	if(Ret!=1)
        		throw new Exception("更新单价失败");
            
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
        return Ret;
    }
    public List<String[]> getNameGuigeList()
    {
        List<String[]> goodsTypeList;
        goodsTypeList = new ArrayList<String[]>();
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
            strSQL = "SELECT goodstypename,guige FROM TabGoodsInfo a inner join TabGoodsTypecode b on a.goodstype=b.goodstype"+
            " group by goodstypename,guige order by goodstypename,guige";
            String tmp[];
            for(rs = stmt.executeQuery(strSQL); rs.next(); goodsTypeList.add(tmp))
            {
                tmp = new String[2];
                tmp[0] = rs.getString(1);
                tmp[1] = rs.getString(2);
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
        return goodsTypeList;
    }
    
    //库存备注
    public int UpdateRepertoryMemo(GoodsInfoForm gif) throws Exception
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "update TabGoodsRepertory set memo='"+gif.getMemo()+"' where goodsid='"+gif.getGoodsId()+"' and storeid="+gif.getStoreid();
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
                throw new Exception("更新新记录错误");
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
            catch(SQLException ex) { }
        }
        return nRet;
    }
    @SuppressWarnings("unchecked")
	public List getRepertoryGroupByType(int deptid) throws Exception
    {
		List gsri = new ArrayList();
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
            
	        strSQL = " SELECT sum(a.repertoryAmount*a.avgprice),d.goodstypename"+
	        	" FROM TabGoodsRepertory a inner join TabGoodsInfo b on "+
	        	"a.goodsid=b.goodsid inner join TabStore c on a.storeid=c.id inner join TabGoodsTypeCode d "+
	        	"on b.goodstype=d.goodstype  where c.deptid="+deptid+" group by d.goodstypename";
	        rs = stmt.executeQuery(strSQL);
            GoodsInfoForm gio=null;
            while(rs.next())
            {
        		gio = new GoodsInfoForm();
            	
            	gio.setGoodsTypeName(rs.getString("goodstypename"));
            	gio.setPurchaseUnitPrice(rs.getDouble(1));
            	gsri.add(gio);
	          
            }
            rs.close();
            
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
        return gsri;
    }
}
