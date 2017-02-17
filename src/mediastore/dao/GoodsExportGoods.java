// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsExportGoods.java

package mediastore.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mediastore.common.DBConnection;
import mediastore.rule.ExportItemSearchRule;
import mediastore.util.StrUtility;
import mediastore.util.oConvert;
import mediastore.web.form.GoodsExportGoodsInfo;
import mediastore.web.form.GoodsInfoForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.global.Globals;

// Referenced classes of package mediastore.dao:
//            GoodsInfo, RepertoryGoodsPriceCurve, GoodsExportInfo, MemberInfoDAO

public class GoodsExportGoods
{

    public GoodsExportGoods()
    {
    }

   
	@SuppressWarnings("unchecked")
	public List getGoodsList(int billId,int deptid) throws Exception
    {
        List goodsList;
        GoodsInfo gi = new GoodsInfo();
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
            strSQL = "SELECT a.*,b.name,c.guige,c.chandi,c.caizhi,c.xishu,c.danwei,c.jianxishu FROM TabGoodsExportGoods a inner join TabStore b on a.storeid=b.id "+
            "inner join TabGoodsInfo c on a.goodsid=c.goodsid WHERE a.BillID=" + billId + " and a.deptid="+deptid+ " Order by createtime";
            GoodsExportGoodsInfo gegi;
            for(rs = stmt.executeQuery(strSQL); rs.next(); goodsList.add(gegi))
            {
                gegi = new GoodsExportGoodsInfo();
                gegi.setBillId(rs.getInt("billid"));
                gegi.setGoodsId(rs.getString("goodsid"));
                gegi.setGoodsName(rs.getString("goodsname"));
                gegi.setExportNum(rs.getInt("exportnum"));
                gegi.setExportAmount(rs.getDouble("exportamount"));
                gegi.setExportUnitPrice(rs.getDouble("exportunitprice"));
                gegi.setSalesPerson(rs.getString("salesperson"));
                gegi.setCreateTime(rs.getString("createtime"));
                gegi.setConfirmFlage(rs.getString("confirmflage"));
                gegi.setCurRepertoryAmount(gi.getRepertoryAmount(rs.getString("goodsid"),rs.getInt("storeid")));
                gegi.setStoreId(rs.getInt("storeid"));
                gegi.setStoreName(rs.getString("name"));
                gegi.setGuige(rs.getString("guige"));
                gegi.setCaizhi(rs.getString("caizhi"));
                gegi.setChandi(rs.getString("chandi"));
                gegi.setId(rs.getInt("id"));
                gegi.setXishu(rs.getFloat("xishu"));
                gegi.setDanwei(rs.getString("danwei"));
                gegi.setJianxishu(rs.getFloat("jianxishu"));
                gegi.setAvgprice(rs.getDouble("importavgprice"));
                if(rs.getDouble("jianxishu")>0)
                	gegi.setExportJianNum(rs.getDouble("exportAmount")*1000/rs.getDouble("jianxishu"));
                gegi.setJiagong(rs.getDouble("jiagong"));
                gegi.setMemo(oConvert.getString(rs.getString("memo"),""));
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
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return goodsList;
    }

    public int insertNewGoodsRec(GoodsExportGoodsInfo geg) throws Exception
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
            strSQL="select * from TabGoodsExportInfo where confirmFlage='1' and billid="+geg.getBillId()+" and deptid="+geg.getDeptid();
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("此单据已经存在");
            rs.close();
            strSQL="select * from TabGoodsExportGoods where billid="+geg.getBillId()+" and goodsid='"+geg.getGoodsId()+"' and storeid="+geg.getStoreId()+" and deptid="+geg.getDeptid();
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("此单中已包含同一仓库编号为"+geg.getGoodsId()+"的商品");
            rs.close();
                        
            GoodsInfo gi=new GoodsInfo();
            double avgprice=gi.getAvgInPrice(conn,geg.getGoodsId(), geg.getDeptid());
            
        	strSQL = "insert into TabGoodsExportGoods( BillID, GoodsID, GoodsName,ExportNum, ExportAmount, ExportUnitPrice,"+
        	"SalesPerson, ConfirmFlage, storeid,deptid,importavgprice) values (" + geg.getBillId() + ",'" + 
        	StrUtility.replaceString(geg.getGoodsId(), "'", "''") + "', '" + StrUtility.replaceString(geg.getGoodsName(), "'", "''") + 
        	"', "+geg.getExportNum()+","+geg.getExportAmount()+", " + geg.getExportUnitPrice() + ", '" + StrUtility.replaceString(geg.getSalesPerson(), "'", "''") + 
        	"','0',"+geg.getStoreId()+","+geg.getDeptid()+","+avgprice+")";
        	nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
            {
                nRet = -1;
                throw new Exception("插入新记录错误");
            }
            rs.close();
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
    public int updateExportJianNum(GoodsExportGoodsInfo geg) throws Exception
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        double avgprice=0;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            strSQL = "select * from TabGoodsExportGoods WHERE id = " + geg.getId()+ " and salesPerson='" + geg.getSalesPerson() + "'";
            rs=stmt.executeQuery(strSQL); 
            if(rs.next())
            {
            	geg.setGoodsId(rs.getString("goodsid"));
            	geg.setStoreId(rs.getInt("storeid"));
            	geg.setExportAmount(rs.getDouble("exportamount"));
            	geg.setExportNum(rs.getInt("exportNum"));
            	geg.setAvgprice(rs.getDouble("importAvgPrice"));
            }
            else
            	throw new Exception("找不到记录");
            rs.close();
            GoodsInfo gi=new GoodsInfo();
            GoodsInfoForm g=gi.getGoodsInfoByGoodsId(geg.getGoodsId());
            geg.setExportJianNum(geg.getExportJianNum()*geg.getKind());

            if(g.getXishu()>0)
            	geg.setExportNum(geg.getExportJianNum()*g.getJianxishu()/g.getXishu());
            double newAmount=oConvert.getRound(geg.getExportJianNum()*g.getJianxishu()/1000-geg.getExportAmount(),3);
            if(geg.getKind()==1)
            {
	    		avgprice=gi.CountAvgPrice(geg.getGoodsId(), geg.getDeptid(), geg.getExportAmount(), geg.getAvgprice(),conn);
	            strSQL="select * from TabGoodsRepertory where goodsid='"+geg.getGoodsId()+"' and storeid="+geg.getStoreId();
	        	rs=stmt.executeQuery(strSQL);
	        	if(rs.next())
	        	{
	        		strSQL = "UPDATE TabGoodsRepertory SET RepertoryAmount=RepertoryAmount-(" + newAmount + 
	            	") WHERE GoodsID='" +geg.getGoodsId()+ "' and storeid="+geg.getStoreId();
	        	}
	        	else
	        	{
	        		strSQL="insert into TabGoodsRepertory (goodsid,RepertoryAmount,storeid,avgprice) values('"+geg.getGoodsId()+
	        		"',-("+newAmount+"),"+geg.getStoreId()+","+avgprice+")";
	        	}
	        	nRet=stmt.executeUpdate(strSQL);	
	        	if(nRet != 1)
	            {
	                nRet = -1;
	                throw new Exception("更新库存失败");
	            }
	        	if(g.getXishu()>0)
	        	{
	        		strSQL= "UPDATE TabGoodsRepertory SET RepertoryNum=Round(RepertoryAmount*1000/" + g.getXishu() + 
	            	",0) WHERE GoodsID='" +geg.getGoodsId()+ "' and storeid="+geg.getStoreId();
	        		stmt.executeUpdate(strSQL);
	        	}
	            strSQL = "update TabGoodsRepertory set RepertoryAmount=0 where Round(RepertoryAmount,3)=0";
	            nRet=stmt.executeUpdate(strSQL);
	            //更新加权平均价
	        	strSQL="update TabGoodsRepertory set avgprice="+avgprice+" where goodsid='"+geg.getGoodsId()+
	        	"' and storeid in (select id from TabStore where deptid="+geg.getDeptid()+")";
	        	nRet=stmt.executeUpdate(strSQL);
            }
        	newAmount=oConvert.getRound(newAmount+geg.getExportAmount(),3);
            strSQL = "UPDATE TabGoodsExportGoods SET ExportNum="+geg.getExportNum()+",ExportAmount = (" + newAmount + ")";
            if(geg.getKind()==1)
            	strSQL=strSQL+",importavgprice="+avgprice;
            strSQL=strSQL+" WHERE id = " + geg.getId() + " and salesPerson='" + StrUtility.replaceString(geg.getSalesPerson(), "'", "''") + "'";
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("更新销售数量失败");
            
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
    public int updateExportNum(GoodsExportGoodsInfo geg) throws Exception
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        double avgprice=0;
        int oldnum=0;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            strSQL = "select * from TabGoodsExportGoods WHERE id = " + geg.getId()+ " and salesPerson='" + geg.getSalesPerson() + "'";
            rs=stmt.executeQuery(strSQL); 
            if(rs.next())
            {
            	geg.setGoodsId(rs.getString("goodsid"));
            	geg.setStoreId(rs.getInt("storeid"));
            	geg.setExportAmount(rs.getDouble("exportamount"));
            	geg.setAvgprice(rs.getDouble("importAvgPrice"));
            	oldnum=rs.getInt("exportNum");
            }
            else
            	throw new Exception("找不到记录");
            rs.close();
            GoodsInfo gi=new GoodsInfo();
            GoodsInfoForm g=gi.getGoodsInfoByGoodsId(geg.getGoodsId());
            geg.setExportNum(geg.getExportNum()*geg.getKind());
            if(g.getXishu()>0)
            {
	            int numPerJian=(int)oConvert.getRound(g.getJianxishu()/g.getXishu(),0);
	            double amount=0;
	            if(numPerJian>0)
	            	amount=Math.floor(geg.getExportNum()/numPerJian)*g.getJianxishu()/1000+geg.getExportNum()%numPerJian*g.getXishu()/1000;
	            else
	            	amount=geg.getExportNum()*g.getXishu()/1000;
	            
	            double newAmount=oConvert.getRound(amount-geg.getExportAmount(),3);
	            
	            if(geg.getKind()==1)
	            {
		    		avgprice=gi.CountAvgPrice(geg.getGoodsId(), geg.getDeptid(), geg.getExportAmount(), geg.getAvgprice(),conn);
		            strSQL="select * from TabGoodsRepertory where goodsid='"+geg.getGoodsId()+"' and storeid="+geg.getStoreId();
		        	rs=stmt.executeQuery(strSQL);
		        	if(rs.next())
		        	{
		        		strSQL = "UPDATE TabGoodsRepertory SET RepertoryAmount=RepertoryAmount-(" + newAmount + 
		            	") WHERE GoodsID='" +geg.getGoodsId()+ "' and storeid="+geg.getStoreId();
		        	}
		        	else
		        	{
		        		strSQL="insert into TabGoodsRepertory (goodsid,RepertoryAmount,storeid,avgprice) values('"+geg.getGoodsId()+
		        		"',-("+newAmount+"),"+geg.getStoreId()+","+avgprice+")";
		        	}
		        	nRet=stmt.executeUpdate(strSQL);	
		        	if(nRet != 1)
		            {
		                nRet = -1;
		                throw new Exception("更新库存失败");
		            }
		        	strSQL= "UPDATE TabGoodsRepertory SET RepertoryNum=Round(RepertoryAmount*1000/" + g.getXishu() + 
	            	",0) WHERE GoodsID='" +geg.getGoodsId()+ "' and storeid="+geg.getStoreId();
		        	nRet=stmt.executeUpdate(strSQL);
		            strSQL = "update TabGoodsRepertory set RepertoryAmount=0 where Round(RepertoryAmount,3)=0";
		            nRet=stmt.executeUpdate(strSQL);
		            //更新加权平均价
		        	strSQL="update TabGoodsRepertory set avgprice="+avgprice+" where goodsid='"+geg.getGoodsId()+
		        	"' and storeid in (select id from TabStore where deptid="+geg.getDeptid()+")";
		        	nRet=stmt.executeUpdate(strSQL);
	            }
	            newAmount=oConvert.getRound(newAmount+geg.getExportAmount(),3);
	            strSQL = "UPDATE TabGoodsExportGoods SET ExportNum="+geg.getExportNum()+",ExportAmount = (" + newAmount + ")";
	            if(geg.getKind()==1)
	            	strSQL=strSQL+",importavgprice="+avgprice;
	            strSQL=strSQL+" WHERE id = " + geg.getId() + " and salesPerson='" + StrUtility.replaceString(geg.getSalesPerson(), "'", "''") + "'";
	            nRet=stmt.executeUpdate(strSQL);
	            if(nRet!=1)
	            	throw new Exception("更新销售数量失败");
            }
            else
            {
            	int newNum=geg.getExportNum()-oldnum;
            	strSQL = "UPDATE TabGoodsRepertory SET RepertoryNum=RepertoryNum-(" + newNum + 
            	") WHERE GoodsID='" +geg.getGoodsId()+ "' and storeid="+geg.getStoreId();
            	nRet=stmt.executeUpdate(strSQL);
            	 strSQL = "UPDATE TabGoodsExportGoods SET ExportNum="+geg.getExportNum()+" WHERE id = " + geg.getId() + " and salesPerson='" + StrUtility.replaceString(geg.getSalesPerson(), "'", "''") + "'";
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
    public int updateExportAmount(GoodsExportGoodsInfo geg) throws Exception
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        double avgprice=0;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            double newAmount=0;
            double oldAmount=0;
            conn.setAutoCommit(false);
            geg.setExportAmount(geg.getExportAmount()*geg.getKind());
            strSQL = "select * from TabGoodsExportGoods WHERE id = " + geg.getId()+ " and salesPerson='" + geg.getSalesPerson() + "'";
            rs=stmt.executeQuery(strSQL); 
            if(rs.next())
            {
            	geg.setGoodsId(rs.getString("goodsid"));
            	geg.setStoreId(rs.getInt("storeid"));
            	oldAmount=rs.getDouble("exportAmount");
            	geg.setAvgprice(rs.getDouble("importAvgPrice"));
            	geg.setExportNum(rs.getDouble("exportnum"));
            	newAmount=oConvert.getRound(geg.getExportAmount()-oldAmount,3);
            }
            else
            	throw new Exception("找不到记录");
            rs.close();
            GoodsInfo gi=new GoodsInfo();
            GoodsInfoForm g=gi.getGoodsInfoByGoodsId(geg.getGoodsId());
            
            if(g.getXishu()>0)
            	geg.setExportNum(geg.getExportAmount()*1000/g.getXishu());
            
            if(geg.getKind()==1)
            {
	    		avgprice=gi.CountAvgPrice(geg.getGoodsId(), geg.getDeptid(), oldAmount, geg.getAvgprice(),conn);
	            strSQL="select * from TabGoodsRepertory where goodsid='"+geg.getGoodsId()+"' and storeid="+geg.getStoreId();
	        	rs=stmt.executeQuery(strSQL);
	        	if(rs.next())
	        	{
	        		strSQL = "UPDATE TabGoodsRepertory SET RepertoryAmount=Round(RepertoryAmount-(" + newAmount + 
	            	"),4) WHERE GoodsID='" +geg.getGoodsId()+ "' and storeid="+geg.getStoreId();
	        	}
	        	else
	        	{
	        		strSQL="insert into TabGoodsRepertory (goodsid,RepertoryAmount,storeid,avgprice) values('"+geg.getGoodsId()+
	        		"',-("+newAmount+"),"+geg.getStoreId()+","+avgprice+")";
	        	}
	        	nRet=stmt.executeUpdate(strSQL);	
	        	if(nRet != 1)
	            {
	                nRet = -1;
	                throw new Exception("更新库存失败");
	            }
	        	if(g.getXishu()>0)
	        	{
	        		strSQL= "UPDATE TabGoodsRepertory SET RepertoryNum=Round(RepertoryAmount*1000/" + geg.getXishu() + 
	            	",0) WHERE GoodsID='" +geg.getGoodsId()+ "' and storeid="+geg.getStoreId();
	        	}
	            strSQL = "update TabGoodsRepertory set RepertoryAmount=0 where Round(RepertoryAmount,3)=0";
	            nRet=stmt.executeUpdate(strSQL);
	            //更新加权平均价
	        	strSQL="update TabGoodsRepertory set avgprice="+avgprice+" where goodsid='"+geg.getGoodsId()+
	        	"' and storeid in (select id from TabStore where deptid="+geg.getDeptid()+")";
	        	nRet=stmt.executeUpdate(strSQL);
            }
            strSQL = "UPDATE TabGoodsExportGoods SET ExportNum="+geg.getExportNum()+",ExportAmount = " + geg.getExportAmount();
            if(geg.getKind()==1)
            	strSQL=strSQL+",importavgprice="+avgprice;
            strSQL=strSQL+" WHERE id = " + geg.getId() + " and salesPerson='" + StrUtility.replaceString(geg.getSalesPerson(), "'", "''") + "'";
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("更新出库数量失败");
            
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

    public int updatePrice(GoodsExportGoodsInfo geg)
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
            strSQL = "UPDATE TabGoodsExportGoods SET ExportUnitPrice = " + geg.getExportUnitPrice() + " WHERE id = " + geg.getId() + " and salesperson='" + StrUtility.replaceString(geg.getSalesPerson(), "'", "''") + "'";
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("更新出库单价失败");
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
    public int updateJiaGong(GoodsExportGoodsInfo geg)
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
            strSQL = "UPDATE TabGoodsExportGoods SET jiagong = " + geg.getJiagong() + " WHERE id = " + geg.getId() + " and salesperson='" + StrUtility.replaceString(geg.getSalesPerson(), "'", "''") + "'";
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("更新加工费失败");
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
    public int updateMemo(GoodsExportGoodsInfo geg)
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
            strSQL = "UPDATE TabGoodsExportGoods SET memo ='" + geg.getMemo() + "' WHERE id = " + geg.getId() + " and salesperson='" + StrUtility.replaceString(geg.getSalesPerson(), "'", "''") + "'";
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("更新备注失败");
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
    
    public String getSalesPerson(int billId, String goodsId)
    {
        String salesPerson;
        salesPerson = "";
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
            strSQL = "SELECT SalesPerson FROM TabGoodsExportGoods WHERE BillID=" + billId + " AND GoodsID='" + StrUtility.replaceString(goodsId, "'", "''") + "' ";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
                salesPerson = rs.getString(1);
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
        return salesPerson;
    }

    public int deleteGoodsRec(GoodsExportGoodsInfo geg) throws Exception
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
            strSQL = "select * FROM TabGoodsExportGoods WHERE id=" + geg.getId() + " and confirmFlage='0' and salesPerson='" + geg.getSalesPerson() + "'";
            rs=stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("找不到此记录，或单据状态已改变");
            else
            {
            	geg.setGoodsId(rs.getString("goodsid"));
            	geg.setStoreId(rs.getInt("storeid"));
            	geg.setExportAmount(rs.getFloat("exportamount"));
            	geg.setExportNum(rs.getInt("exportNum"));
            	geg.setAvgprice(rs.getDouble("importAvgPrice"));
            }
            rs.close();
            if(geg.getKind()==1)
            {
	            GoodsInfo gi=new GoodsInfo();
        		double avgprice=gi.CountAvgPrice(geg.getGoodsId(), geg.getDeptid(), geg.getExportAmount(), geg.getAvgprice(),conn);
	            strSQL="select * from TabGoodsRepertory where goodsid='"+geg.getGoodsId()+"' and storeid="+geg.getStoreId();
	        	rs=stmt.executeQuery(strSQL);
	        	if(rs.next())
	        	{
	        		strSQL = "UPDATE TabGoodsRepertory SET RepertoryAmount=RepertoryAmount+(" + geg.getExportAmount() + 
	            	"),RepertoryNum=RepertoryNum+(" + geg.getExportNum() + 
	            	") WHERE GoodsID='" +geg.getGoodsId()+ "' and storeid="+geg.getStoreId();
	        	}
	        	else
	        	{
	        		strSQL="insert into TabGoodsRepertory (goodsid,RepertoryAmount,RepertoryNum,storeid,avgprice) values('"+geg.getGoodsId()+
	        		"',"+geg.getExportAmount()+","+geg.getExportNum()+","+geg.getStoreId()+","+avgprice+")";
	        	}
	        	nRet=stmt.executeUpdate(strSQL);	
	        	if(nRet != 1)
	            {
	                nRet = -1;
	                throw new Exception("更新库存失败");
	            }
	            strSQL = "update TabGoodsRepertory set RepertoryAmount=0 where Round(RepertoryAmount,3)=0";
	            nRet=stmt.executeUpdate(strSQL);
	            //更新加权平均价
	        	strSQL="update TabGoodsRepertory set avgprice="+avgprice+" where goodsid='"+geg.getGoodsId()+
	        	"' and storeid in (select id from TabStore where deptid="+geg.getDeptid()+")";
	        	nRet=stmt.executeUpdate(strSQL);
            }
            strSQL = "DELETE FROM TabGoodsExportGoods WHERE id=" + geg.getId() + " and confirmFlage='0' and salesPerson='" + geg.getSalesPerson() + "'";
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
                throw new Exception("删除销售记录失败");
            
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

    public int updateMemberId(int billId, int memberId) throws Exception
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        int memid;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL="select * from tabmemberinfo where memberid="+memberId;
            rs=stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("没有此卡号的会员！");
            else
               	memid=rs.getInt("memberid");
            rs.close();
            strSQL="select count(*),sum(totaldisprice-realmoney-oddment-daijin) from tabGoodsExportInfo where memberid="+memberId+" and PaymentTime is null and fahuo=0";
            rs=stmt.executeQuery(strSQL);
            rs.next();
            if(rs.getString(1)!=null && rs.getFloat(2)>0 && memid!=0)
            	throw new Exception("有 "+rs.getInt(1)+" 欠款未归还！还欠 "+rs.getFloat(2)+" 元");
            rs.close();
            strSQL = "UPDATE TabGoodsExportGoods SET MemberId = " + memberId + " WHERE BillID = " + billId;
            stmt.executeUpdate(strSQL);
            strSQL = "insert into sqlLog (sqltext) values ('" + StrUtility.replaceString(strSQL, "'", "''")+ "')";
            stmt.executeUpdate(strSQL);
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
            catch(SQLException e) { }
        }
        return nRet;
    }

    public int updateConfirmFlageToOne(int billId)
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
            strSQL = "UPDATE TabGoodsExportGoods SET ConfirmFlage = '1' WHERE BillID = " + billId;
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

    public int deleteAllGoods(GoodsExportGoodsInfo geg) throws Exception
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        Statement stmt1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            stmt1 = conn.createStatement();
            conn.setAutoCommit(false);
            
            String strSQL = "select * FROM TabGoodsExportGoods WHERE billid=" + geg.getBillId() + " and confirmFlage='0' and deptid="+geg.getDeptid();
            rs = stmt.executeQuery(strSQL);
            GoodsInfo gi=new GoodsInfo();
            while(rs.next() && geg.getKind()==1)
            {
        		double avgprice=gi.CountAvgPrice(rs.getString("goodsid"), rs.getInt("deptid"), rs.getDouble("exportamount"),rs.getDouble("importAvgPrice"),conn);
            	strSQL="select * from TabGoodsRepertory where goodsid='"+rs.getString("goodsid")+"' and storeid="+rs.getString("storeid");
            	rs1=stmt1.executeQuery(strSQL);
            	if(rs1.next())
            	{
            		strSQL = "UPDATE TabGoodsRepertory SET RepertoryAmount=RepertoryAmount+(" + rs.getDouble("exportAmount") + 
                	"),RepertoryNum=RepertoryNum+(" + rs.getInt("exportNum") + 
                	") WHERE GoodsID='" +rs.getString("goodsid")+ "' and storeid="+rs.getString("storeid");
            	}
            	else
            	{           		
            		strSQL="insert into TabGoodsRepertory (goodsid,RepertoryAmount,RepertoryNum,storeid,avgprice) values('"+rs.getString("goodsid")+
            		"',"+rs.getDouble("exportAmount")+","+rs.getInt("exportNum")+","+rs.getString("storeid")+","+avgprice+")";
            	}
            	nRet = stmt1.executeUpdate(strSQL);            	
            	if(nRet!=1)
                	throw new Exception("更新库存失败");

	            //更新加权平均价
	        	strSQL="update TabGoodsRepertory set avgprice="+avgprice+" where goodsid='"+rs.getString("goodsid")+
	        	"' and storeid in (select id from TabStore where deptid="+rs.getInt("deptid")+")";
	        	nRet=stmt1.executeUpdate(strSQL);
            	
            }
            rs.close();
            strSQL = "update TabGoodsRepertory set RepertoryAmount=0 where Round(RepertoryAmount,3)=0";
            nRet=stmt.executeUpdate(strSQL);
            
            if(geg.getBillId()>0)
            {
            	strSQL = "delete from TabGoodsExportInfo where billid=" + geg.getBillId() + " and confirmFlage='0' and deptid="+geg.getDeptid();
                nRet = stmt.executeUpdate(strSQL);
                if(nRet<1)
                	throw new Exception("删除失败");
            }
            strSQL = "DELETE FROM TabGoodsExportGoods WHERE billid=" + geg.getBillId() + " and confirmFlage='0' and deptid="+geg.getDeptid();
            nRet = stmt.executeUpdate(strSQL);
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
            	if(rs1 != null)
                    rs1.close();
                if(stmt != null)
                    stmt.close();
                if(stmt1 != null)
                    stmt1.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return nRet;
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
            strSQL = "UPDATE TabGoodsExportGoods SET GoodsName = '" + StrUtility.replaceString(newGoodsName, "'", "''") + "' WHERE GoodsID = '" + StrUtility.replaceString(goodsId, "'", "''") + "'";
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
            strSQL = "SELECT DISTINCT BillID FROM TabGoodsExportGoods Order by BillID desc";
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
	public List getSalesPersonList()
    {
        List salesPersonList;
        salesPersonList = new ArrayList();
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
            strSQL = "SELECT DISTINCT SalesPerson FROM TabGoodsExportGoods Order by SalesPerson";
            String curSendPersons;
            for(rs = stmt.executeQuery(strSQL); rs.next(); salesPersonList.add(curSendPersons))
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
        return salesPersonList;
    }

   
	@SuppressWarnings("unchecked")
	public List getSalesPersonList(int billId)
    {
        List tmpList;
        tmpList = new ArrayList();
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
            strSQL = "SELECT DISTINCT SalesPerson FROM TabGoodsExportGoods WHERE BillID=" + billId;
            String curSendPersons;
            for(rs = stmt.executeQuery(strSQL); rs.next(); tmpList.add(curSendPersons))
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
        return tmpList;
    }

    
	@SuppressWarnings("unchecked")
	public ResultsetList getItemListByRule(ExportItemSearchRule iisr)
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
        GoodsExportGoodsInfo gif=iisr.getGif();
        
        strSQL = " SELECT a.*,b.name,c.chandi,c.guige,e.shortname FROM TabGoodsExportGoods a ";
        strSQL1 = " SELECT sum(a.Exportamount) as allnum,sum(a.exportamount*a.exportunitprice) as allprice FROM TabGoodsExportGoods a ";
        String joinstr="inner join TabStore b on a.storeid=b.id inner join TabGoodsInfo c on"+
        " a.goodsid=c.goodsid inner join TabGoodsTypeCode d on c.goodstype=d.goodstype inner join TabDepartInfo e"+
        " on a.deptid=e.id where a.confirmflage='1'";
        strSQL=strSQL+joinstr;
        strSQL1=strSQL1+joinstr;
        
        String whereStr = "";
        if(gif.getBillId()!= 0)
            whereStr = whereStr + " and a.BillID=" + gif.getBillId();
        if(!gif.getGoodsName().equals(""))
            whereStr = whereStr + " and a.GoodsName like '%" + StrUtility.replaceString(gif.getGoodsName(), "'", "''") + "%'";
        if(!gif.getGoodsId().equals(""))
            whereStr = whereStr + " and a.GoodsID like '%" + StrUtility.replaceString(gif.getGoodsId(), "'", "''") + "%'";
        if(!gif.getSalesPerson().equals(""))
            whereStr = whereStr + " and a.salesPerson ='" + gif.getSalesPerson() + "'";
        if(gif.getStoreId()!= 0)
            whereStr = whereStr + " and a.storeid=" + gif.getStoreId();
        if(!iisr.getOper().equals(""))
            whereStr = whereStr + " and a.exportunitprice "+iisr.getOper()+ gif.getExportUnitPrice();
        if(!iisr.getExportTime1().equals(""))
            whereStr = whereStr + " and a.createtime>='" + iisr.getExportTime1() + "'";
        if(!iisr.getExportTime2().equals(""))
            whereStr = whereStr + " and a.createtime<='" + iisr.getExportTime2()+"'";
        if(!iisr.getGoodsClass().equals("0"))
            whereStr = whereStr + " and d.classid=" + iisr.getGoodsClass();
        if(!iisr.getGoodsType().equals("0"))
            whereStr = whereStr + " and c.goodstype=" + iisr.getGoodsType();
        if(gif.getChandi().length()>0)
            whereStr = whereStr + " and c.chandi like '%" + gif.getChandi() + "%'";
        if(gif.getDeptid()>0)
            whereStr = whereStr + " and a.deptid=" + gif.getDeptid();
        strSQL = strSQL + whereStr;
        if(orderStr.length()>0)
        	strSQL = strSQL + " Order by " + orderStr;
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
            		GoodsExportGoodsInfo giii = new GoodsExportGoodsInfo();
                    giii.setBillId(rs.getInt("billid"));
                    giii.setGoodsId(rs.getString("goodsid"));
                    giii.setGoodsName(rs.getString("goodsname"));
                    giii.setExportAmount(rs.getFloat("exportAmount"));
                    giii.setExportUnitPrice(rs.getFloat("exportUnitPrice"));
                    giii.setSalesPerson(rs.getString("salesperson"));
                    giii.setCreateTime(oConvert.FormDateTimeShort(rs.getTimestamp("createTime")));
                    giii.setStoreId(rs.getInt("storeid"));
                    giii.setStoreName(rs.getString("name"));
                    giii.setChandi(rs.getString("chandi"));
                    giii.setGuige(rs.getString("guige"));
                    giii.setDeptid(rs.getInt("deptid"));
                    giii.setShortname(rs.getString("shortname"));
                    giii.setMemo(oConvert.getString(rs.getString("memo"),""));
                    giii.setJiagong(rs.getDouble("jiagong"));
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

    

    public String getConfirmFlage(int billId, String goodsId)
    {
        String tmp;
        tmp = "0";
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
            strSQL = "SELECT ConfirmFlage FROM TabGoodsExportGoods WHERE BillID=" + billId + " AND GoodsID='" + StrUtility.replaceString(goodsId, "'", "''") + "' ";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
                tmp = rs.getString(1);
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
        return tmp;
    }

    public String getCreateTime(int billId, String goodsId)
    {
        String tmp;
        tmp = "1978-06-01 00:00:00";
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
            strSQL = "SELECT CreateTime FROM TabGoodsExportGoods WHERE BillID=" + billId + " AND GoodsID='" + StrUtility.replaceString(goodsId, "'", "''") + "' ";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
                tmp = rs.getString(1);
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
        return tmp;
    }

    public int getExportAmount(int billId, String goodsId)
    {
        int exportAmount;
        exportAmount = 0;
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
            strSQL = "SELECT ExportAmount FROM TabGoodsExportGoods WHERE BillID=" + billId + " AND GoodsID='" + StrUtility.replaceString(goodsId, "'", "''") + "' ";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
                exportAmount = rs.getInt(1);
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
        return exportAmount;
    }

    public float getExportUnitPrice(int billId, String goodsId)
    {
        float exportUnitPrice;
        exportUnitPrice = 0.0F;
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
            strSQL = "SELECT ExportUnitPrice FROM TabGoodsExportGoods WHERE BillID=" + billId + " AND GoodsID='" + StrUtility.replaceString(goodsId, "'", "''") + "' ";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
                exportUnitPrice = rs.getFloat(1);
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
        return exportUnitPrice;
    }

    public int getMemberId(int billId)
    {
        int tmp;
        tmp = 0;
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
            strSQL = "SELECT MemberId FROM TabGoodsExportGoods WHERE BillID=" + billId;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
                tmp = rs.getInt(1);
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
        return tmp;
    }

    public float getTotalPaymentByMemberId(int memberId)
    {
        float totalPayment;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        totalPayment = 0.0F;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "SELECT ExportAmount,ExportUnitPrice FROM TabGoodsExportGoods WHERE MemberId=" + memberId + " AND ConfirmFlage='1'";
            for(rs = stmt.executeQuery(strSQL); rs.next();)
                totalPayment += (float)rs.getInt("ExportAmount") * rs.getFloat("ExportUnitPrice");

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
        return totalPayment;
    }

    public String getRecentBuyTimeOfMember(int memberId)
    {
        String recentTime;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        recentTime = "";
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "SELECT top 1 exportTime FROM TabGoodsExportInfo WHERE MemberId=" + memberId + " Order by exportTime desc";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
                recentTime = rs.getString("exportTime");
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
        return recentTime;
    }
    public int getGoodsNumbyBillIdGoodsId(int BillId,String GoodsId)
    {
    	DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        int ret = 0;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "SELECT sum(exportAmount) as allnum FROM TabGoodsExportGoods WHERE billId=" +String.valueOf(BillId) + " AND goodsid='"+GoodsId+"'";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
                ret = rs.getInt(1);
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
        return ret;
    }
    
    @SuppressWarnings("unchecked")
	public List getGoodsHoldList(String salesPerson)
    {
        List goodsList;
        goodsList = new ArrayList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            sql = "SELECT id,sum(exportamount),sum(exportamount*exportunitprice),min(a.createtime),min(b.membername) FROM ";
            sql=sql+"TabTempHoldGoods a left join TabMemberInfo b on a.memberid=b.memberid WHERE salesperson='"+salesPerson+"' group by id";
            String gegi[];
            for(rs = stmt.executeQuery(sql); rs.next(); goodsList.add(gegi))
            {
            	gegi = new String[5];
                gegi[0]=rs.getString(1);            
            	gegi[1]=rs.getString(2);
            	gegi[2]=rs.getString(3);
            	gegi[3]=oConvert.FormDateTimeShort(rs.getTimestamp(4));
            	gegi[4]=oConvert.getString(rs.getString(5),"");
            }
            rs.close();
            rs = null;
        }
        catch(Exception e) {System.out.print(e.getMessage()); }
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
        return goodsList;
    }
    
    @SuppressWarnings("unchecked")
	public List getMemberIdList()
    {
        List MemberList;
        MemberList = new ArrayList();
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
            strSQL = "SELECT DISTINCT a.memberid,b.membername FROM TabGoodsExportGoods a inner join "+
            " tabmemberinfo b on a.memberid=b.memberid Order by b.membername";
            String curSendPersons;
            for(rs = stmt.executeQuery(strSQL); rs.next(); MemberList.add(curSendPersons))
                curSendPersons = rs.getString(2);

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
        return MemberList;
    }
    public String[] getMaxSellByNameGuige()
    {
        String[] tmp = new String[2];
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
            strSQL = "SELECT top 1 goodsname,guige,sum(exportUnitPrice*exportAmount) FROM TabGoodsExportGoods a inner join "+
            " tabGoodsinfo b on a.goodsid=b.goodsid where YEAR(a.CreateTime)=YEAR(GETDATE()) group by goodsname,guige Order by sum(exportUnitPrice*exportAmount) desc";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	tmp[0]=rs.getString(1);
            	tmp[1]=rs.getString(2);
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
        return tmp;
    }
}
