// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsExportInfo.java

package mediastore.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mediastore.common.DBConnection;
import mediastore.util.oConvert;
import mediastore.web.form.CheckBillForm;
import mediastore.web.form.GoodsCheckGoodsInfo;
import mediastore.web.form.ResultsetList;
import mediastore.web.global.Globals;


public class GoodsCheckInfo
{

    public GoodsCheckInfo()
    {
    }
    //取得售货员的最大订单ID
    public int getTempBillIDNum(String saleman,int kind,int deptid) throws Exception
    {
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;

        ResultSet rs = null;
        String strSQL = null;
        int billid = 0;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            
            strSQL = "SELECT max(BillID) FROM TabGoodsCheckInfo WHERE ConfirmFlage='0' and createperson='"+saleman+"' and kind="+kind;
            rs = stmt.executeQuery(strSQL);
            if(rs.next() && rs.getInt(1)!=0)
                billid = rs.getInt(1);
            else
            {
            	while(billid==0)
            	{
	            	billid=(int)(Math.random()*(-1000));
	            	strSQL = "SELECT * FROM TabGoodsCheckInfo WHERE billid="+billid;
	            	rs = stmt.executeQuery(strSQL);
	            	if(rs.next())
	            		billid=0;
            	}
            	strSQL = "insert into TabGoodsCheckInfo( BillID,createPerson,totalnum,totalprice, "+
            	"checktime, confirmFlage,deptid,kind) values (" + billid + ",'"+saleman+"',0,0,getDate(),'0',"+deptid+","+kind+")"; 
            	int nRet = stmt.executeUpdate(strSQL);
                if(nRet != 1)
                {
                    nRet = -1;
                    throw new Exception("插入新记录错误");
                }
            }
            rs.close();
            rs = null;
        }
        catch(Exception e) {throw e;}
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
        
       return billid;
        
        
    }
    //插入新的售货单记录
    public int insertNewBillRec(GoodsCheckGoodsInfo gif) throws Exception
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        Statement stmt1 = null;
        CallableStatement proc = null; 
        ResultSet rs = null;
        ResultSet rs1 = null;
        String strSQL = null;
        double avgprice=0;
        double totalPrice=0;
        double totalNum=0;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt1 = conn.createStatement();
            GoodsInfo gi=new GoodsInfo();
            strSQL = "select * from TabGoodsCheckGoods WHERE BillID = " + gif.getBillId()+" and deptid="+gif.getDeptid();
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	avgprice=gi.getAvgInPrice(conn,rs.getString("goodsid"), rs.getInt("deptid"));
                strSQL = "UPDATE TabGoodsCheckGoods SET checkUnitPrice="+avgprice+" WHERE id =" + rs.getInt("id");
                nRet=stmt1.executeUpdate(strSQL);
                if(nRet != 1)
                    throw new Exception("更新单据明细错误");
            }
            rs.close();
            strSQL = "select sum(checkamount) as totalnum,sum(checkamount*checkUnitprice) as totalPrice from"+
            " TabGoodsCheckGoods WHERE BillID=" + gif.getBillId()+" and deptid="+gif.getDeptid();
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	totalNum=oConvert.getRound(rs.getDouble(1),4);
            	totalPrice=oConvert.getRound(rs.getDouble(2),2);
            }
            rs.close();
            //获取单号
            strSQL = "update TabDepartInfo set ckbillid=ckbillid+1 where id="+gif.getDeptid();
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新损益单号失败");
            strSQL = "select ckbillid from TabDepartInfo where id="+gif.getDeptid();
            rs=stmt.executeQuery(strSQL);
            rs.next();
            int newbillid=rs.getInt("ckbillid");
            strSQL = "update TabGoodsCheckInfo set billid="+newbillid+",totalnum="+totalNum+",totalprice="+totalPrice+",checkTime=getDate(),confirmflage='1' where "+
            " billid="+gif.getBillId()+" and deptid="+gif.getDeptid()+" and confirmflage='0'"; 
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
            {
                nRet = -1;
                throw new Exception("更新单据失败");
            }
            strSQL = "UPDATE TabGoodsCheckGoods SET billid="+newbillid+",ConfirmFlage = '1' WHERE BillID =" + gif.getBillId()+" and deptid="+gif.getDeptid();
            nRet=stmt.executeUpdate(strSQL);
            if(nRet < 1)
            {
                nRet = -1;
                throw new Exception("更新单据明细错误");
            }
            gif.setBillId(newbillid);
            strSQL = "select * from TabGoodsCheckGoods WHERE BillID = " + newbillid+" and deptid="+gif.getDeptid();
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	if(rs.getDouble("checkamount")<0)
            	{
            		double kucun=gi.getRepertoryAmount(rs.getString("goodsid"),rs.getInt("storeid"));
                    if(kucun<-rs.getDouble("checkamount"))
                    	throw new Exception("编号为"+rs.getString("goodsid")+"的产品库存不足，仅剩"+kucun+"吨");
            	}
            	strSQL="select * from TabGoodsRepertory where goodsid='"+rs.getString("goodsid")+"' and storeid="+rs.getString("storeid");
            	rs1=stmt1.executeQuery(strSQL);
            	if(rs1.next())
            		strSQL = "UPDATE TabGoodsRepertory SET RepertoryAmount=RepertoryAmount+(" + rs.getString("checkamount") + 
            		"),RepertoryNum=RepertoryNum+(" + rs.getString("checkNum") + 
            		") WHERE GoodsID='" +rs.getString("goodsid")+ "' and storeid="+rs.getString("storeid");
            	else
            		strSQL="insert into TabGoodsRepertory (goodsid,RepertoryAmount,RepertoryNum,storeid,avgprice) values('"+rs.getString("goodsid")+
            		"',"+rs.getString("checkamount")+","+rs.getString("checkNum")+","+rs.getString("storeid")+","+rs.getDouble("checkUnitPrice")+")";
            	nRet=stmt1.executeUpdate(strSQL);	
            	if(nRet != 1)
                {
                    nRet = -1;
                    throw new Exception("更新库存表错误");
                }
            }
            rs.close();
            strSQL = "update TabGoodsRepertory set RepertoryAmount=0 where Round(RepertoryAmount,3)=0";
            nRet=stmt.executeUpdate(strSQL);
            
            proc = conn.prepareCall("{ call Bakup_Repertory()}");
            proc.execute(); 
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
            	if(rs1 != null)
                    rs1.close();
                if(stmt != null)
                    stmt.close();
                if(stmt1 != null)
                    stmt1.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException ex) { }
        }
        return nRet;
    }
    public int getMaxBillIDNum(int deptid) throws Exception
    {
        int billid=0;
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
            strSQL = "select ckbillid from TabDepartInfo where id="+deptid;
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	billid=rs.getInt("ckbillid")+1;
            else
            	throw new Exception("分公司不存在");
            strSQL = "update TabDepartInfo set ckbillid=ckbillid+1 where id="+deptid;
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新损益单号失败");
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
                
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return billid;
    }
    public CheckBillForm getCheckBillById(int billId,int deptid) throws Exception
    {
    	CheckBillForm ibf=null;
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
            strSQL = "SELECT a.*,b.name FROM TabGoodsCheckInfo a inner join TabDepartInfo b on a.deptid=b.id WHERE BillID=" + billId+" and deptid="+deptid;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	ibf=new CheckBillForm();
            	ibf.setBillId(billId);
            	ibf.setCreatePerson(rs.getString("createperson"));
            	ibf.setCheckTime(oConvert.FormDateShort(rs.getTimestamp("CheckTime")));
            	ibf.setTotalNum(rs.getDouble("totalnum"));
            	ibf.setTotalPrice(rs.getDouble("totalprice"));
            	ibf.setKind(rs.getInt("kind"));
            	ibf.setDeptid(rs.getInt("deptid"));
            	ibf.setShortname(rs.getString("name"));
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
            catch(SQLException e) {}
        }
        return ibf;
    }
   //取得售货员所有单号列表
	@SuppressWarnings("unchecked")
	public List getBillIdList(int deptid)
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
            strSQL = "SELECT DISTINCT BillID FROM TabGoodscheckInfo where confirmflage='1'";
            if(deptid>0)
            	strSQL=strSQL+" and deptid="+deptid;
            strSQL=strSQL+" Order by BillID desc";
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


   //取得所有售货员列表
	@SuppressWarnings("unchecked")
	public List getPersonsList(int deptid) throws Exception
    {
		List inceptPersonsList;
        inceptPersonsList = new ArrayList();
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
            strSQL = "SELECT DISTINCT createperson FROM TabGoodscheckInfo";
            if(deptid>0)
            	strSQL=strSQL+" where deptid="+deptid;
            strSQL=strSQL+" Order by createperson";
            String curSendPersons;
            for(rs = stmt.executeQuery(strSQL); rs.next(); inceptPersonsList.add(curSendPersons))
                curSendPersons = rs.getString(1);

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
        return inceptPersonsList;
    }
	
    //售货单记录查询
	@SuppressWarnings("unchecked")
	public ResultsetList getBillListByRule(CheckBillForm ibf,String exportTime1,String exportTime2,int curPage,String orderby) throws Exception
    {
		ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
     
        strSQL = " SELECT a.*,b.shortname FROM TabGoodsCheckInfo a inner join TabDepartInfo b on a.deptid=b.id where a.confirmflage='1'";
        strSQL1 = " SELECT sum(totalnum) as allnum,sum(totalprice) as allprice FROM TabGoodsCheckInfo a where confirmflage='1'";
        String whereStr = "";
        if(ibf.getBillId()!= 0)
            whereStr = whereStr + " and BillID=" + ibf.getBillId();
        if(!ibf.getCreatePerson().equals(""))
            whereStr = whereStr + " and createPerson='" + ibf.getCreatePerson()+"'";
        
        if(!exportTime1.equals(""))
        	whereStr = whereStr + " and checkTime >= '" + exportTime1 + "'";
        if(!exportTime2.equals(""))
        	whereStr = whereStr + " and checkTime <='" + exportTime2 + "'";
        if(ibf.getKind()!=0)
        	whereStr = whereStr +" and a.kind="+ibf.getKind();
        if(ibf.getDeptid()>0)
        	whereStr = whereStr +" and a.deptid="+ibf.getDeptid();
        strSQL = strSQL +whereStr;
        if(orderby.length()>0)
        	strSQL = strSQL + " Order by "+orderby;
        strSQL1=strSQL1+whereStr;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(strSQL);
            if(rs.last())
            {
            	int RowCount=rs.getRow();
            	ibsri.allnum=RowCount;
            	ibsri.curpage=curPage;
            	ibsri.pagesize=Globals.REC_NUM_OF_PAGE;
            	int PageCount = ((RowCount % ibsri.pagesize) == 0 ?(RowCount/ibsri.pagesize) : (RowCount/ibsri.pagesize)+1);
            	ibsri.pagecount=PageCount;
            	if(ibsri.curpage > PageCount)  
            	{
            		ibsri.curpage = PageCount; 
            	}
            	else if(ibsri.curpage <= 0)
            	{
            		ibsri.curpage = 1;  
            	}
            	rs.absolute((ibsri.curpage - 1) * ibsri.pagesize + 1);  
            	for(int i=0;i<ibsri.pagesize;i++)
            	{
            		CheckBillForm gibi = new CheckBillForm();
                    gibi.setBillId(rs.getInt("billid"));
                    gibi.setCreatePerson(rs.getString("CreatePerson"));
                    gibi.setTotalNum(rs.getDouble("totalnum"));
                    gibi.setTotalPrice(rs.getDouble("totalprice"));
                    gibi.setCheckTime(oConvert.FormDateTimeShort(rs.getTimestamp("checkTime")));
                    if(rs.getString("paymentTime")!=null)
                    	gibi.setPaymentTime(oConvert.FormDateTimeShort(rs.getTimestamp("paymentTime")));
                    else
                    	gibi.setPaymentTime("");
                    gibi.setKind(rs.getInt("kind"));
                    gibi.setDeptid(rs.getInt("deptid"));
                    gibi.setShortname(rs.getString("shortname"));
                    ibsri.rslist.add(gibi);
                    if(!rs.next())
	            		break;
                }

            }
            rs.close();
            rs = stmt.executeQuery(strSQL1);
            if(rs.next())
            {
            	ibsri.allsumnum=oConvert.getRound(rs.getDouble(1),3);
            	ibsri.allmoney=oConvert.getRound(rs.getDouble(2),2);
            }
        }
        catch(Exception e) {throw e;}
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
        return ibsri;
    }

}
