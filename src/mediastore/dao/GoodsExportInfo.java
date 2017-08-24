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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mediastore.common.DBConnection;
import mediastore.rule.ExportReportRule;
import mediastore.util.StrUtility;
import mediastore.util.oConvert;
import mediastore.web.form.ExportBillForm;
import mediastore.web.form.ExportReportResultInfo;
import mediastore.web.form.FactoryInfoForm;
import mediastore.web.form.GoodsExportReportInfo;
import mediastore.web.form.MemberInfo;
import mediastore.web.form.ResultsetList;
import mediastore.web.global.Globals;


public class GoodsExportInfo
{

    public GoodsExportInfo()
    {
    }
    //取得售货员的最大订单ID
    public int getTempBillIDNum(String saleman,int deptid,int kind)
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
            
            strSQL = "SELECT max(BillID) FROM TabGoodsExportInfo WHERE ConfirmFlage='0' and deptid="+deptid+" and kind="+kind+" and SalesPerson='"+saleman+"'";
            rs = stmt.executeQuery(strSQL);
            if(rs.next() && rs.getInt(1)!=0)
            	billid = rs.getInt(1);
            else
            {
            	while(billid==0)
            	{
	            	billid=(int)(Math.random()*(-1000));
	            	strSQL = "SELECT * FROM TabGoodsExportInfo WHERE billid="+billid;
	            	rs = stmt.executeQuery(strSQL);
	            	if(rs.next())
	            		billid=0;
            	}
            	strSQL = "insert into TabGoodsExportInfo( BillID,salesPerson,totalnum,totalprice, payLimTime,exporttime,confirmFlage,deptid,kind)"+
            	" values (" + billid + ",'"+saleman+"',0,0,'"+oConvert.FormDateShort(new java.util.Date())+"',getDate(),'0',"+deptid+","+kind+")"; 
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
        catch(Exception e) {System.out.print(e.getMessage());}
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
    public int getMaxBillIDNum()
    {
        DBConnection dbc = null;
        Connection conn = null;
        CallableStatement proc = null; 

        
        int billid = 0;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
           
        	proc = conn.prepareCall("{ call get_billid(?) }");
        	proc.registerOutParameter(1, 4);
        	proc.execute(); 
        	billid=proc.getInt(1);
            	
            
        }
        catch(Exception e) {System.out.print(e.getMessage());}
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
    //插入新的售货单记录
    public int insertNewBillRec(ExportBillForm ibf) throws Exception
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
        double totalPrice=0;
        double totaljiagong=0;
        double totalNum=0;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt1 = conn.createStatement();
            //判断销售额不能为0
            
            strSQL = "select sum(exportamount) as totalnum,sum(exportamount*exportUnitprice) as totalPrice,sum(jiagong) as totaljiagong from"+
            " TabGoodsExportGoods WHERE BillID=" + ibf.getBillId()+" and salesperson='"+ibf.getSalesPerson()+"'";
            rs=stmt.executeQuery(strSQL);
            rs.next();
            if(ibf.getConfirmFlage().equals("1") && (rs.getFloat(1)==0 || rs.getFloat(2)==0))
            	throw new Exception("销售单总金额不能为0");
            else
            {
            	totalNum=oConvert.getRound(rs.getDouble(1),4);
            	totalPrice=oConvert.getRound(rs.getDouble(2),2);
            	totaljiagong=oConvert.getRound(rs.getDouble(3),2);
            }
            rs.close();
	        if(ibf.getConfirmFlage().equals("1"))
	        {
	            strSQL = "select * from TabGoodsExportGoods WHERE BillID=" + ibf.getBillId()+" and salesperson='"+
	            ibf.getSalesPerson()+"' and exportamount=0";
	            rs=stmt.executeQuery(strSQL);
	            if(rs.next())
	            	throw new Exception(rs.getString("goodsid")+" 的重量不能为0");
            }
            //如果是退库，先获取当前成本价
            if(ibf.getKind()==-1)
            {
            	GoodsInfo gi=new GoodsInfo();
	            strSQL = "select * from TabGoodsExportGoods WHERE BillID = " + ibf.getBillId()+" and deptid="+ibf.getDeptid();
	            rs=stmt.executeQuery(strSQL);
	            while(rs.next())
	            {
	            	double avgprice=gi.getAvgInPrice(conn,rs.getString("goodsid"), rs.getInt("deptid"));
	                strSQL = "UPDATE TabGoodsExportGoods SET importAvgPrice="+avgprice+" WHERE id =" + rs.getInt("id");
	                nRet=stmt1.executeUpdate(strSQL);
	                if(nRet != 1)
	                    throw new Exception("更新单据明细错误");
	                //退回库存
	                strSQL="select * from TabGoodsRepertory where goodsid='"+rs.getString("goodsid")+"' and storeid="+rs.getInt("storeid");
		        	rs1=stmt1.executeQuery(strSQL);
		        	if(rs1.next())
		        	{
		        		strSQL = "UPDATE TabGoodsRepertory SET RepertoryAmount=Round(RepertoryAmount-(" + rs.getDouble("exportAmount") + 
		            	"),4),RepertoryNum=Round(RepertoryNum-(" + rs.getDouble("exportNum") + 
		            	"),0) WHERE GoodsID='" +rs.getString("goodsid")+ "' and storeid="+rs.getInt("storeid");
		        	}
		        	else
		        	{
		        		strSQL="insert into TabGoodsRepertory (goodsid,RepertoryAmount,RepertoryNum,storeid,avgprice) values('"+rs.getString("goodsid")+
		        		"',-("+rs.getDouble("exportAmount")+"),-("+rs.getDouble("exportNum")+"),"+rs.getInt("storeid")+","+avgprice+")";
		        	}
		        	nRet=stmt1.executeUpdate(strSQL);	
		        	if(nRet != 1)
		            {
		                nRet = -1;
		                throw new Exception("更新库存失败");
		            }
		            strSQL = "update TabGoodsRepertory set RepertoryAmount=0 where Round(RepertoryAmount,3)=0";
		            nRet=stmt1.executeUpdate(strSQL);
	            }
	            rs.close();
            }
            //更新单号和状态
            int billid=0;
            if(ibf.getBillId()<=0)
            {
                strSQL = "select exbillid from TabDepartInfo where id="+ibf.getDeptid();
                rs=stmt.executeQuery(strSQL);
                if(rs.next())
                	billid=rs.getInt("exbillid")+1;
                else
                	throw new Exception("分公司不存在");
                strSQL = "update TabDepartInfo set exbillid=exbillid+1 where id="+ibf.getDeptid();
                int i=stmt.executeUpdate(strSQL);
                if(i!=1)
                	throw new Exception("更新销售单号失败");
            }
            else
            	billid=ibf.getBillId();
        	strSQL =" update TabGoodsExportInfo set billid="+billid+",totalnum="+totalNum+",totalprice="+totalPrice+",factory="+
            ibf.getFactory()+",fkType="+ibf.getFkType()+",fpType="+ibf.getFpType()+",payLimTime='"+ibf.getPayLimTime()+
            "',yunfei="+ibf.getYunFei()+",carno='"+ibf.getCarNo()+"',confirmFlage='"+ibf.getConfirmFlage()+
            "',ExportTime=getDate(),totaljiagong="+totaljiagong+" where billid="+ibf.getBillId()+" and confirmFlage='0'";
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
            {
                nRet = -1;
                throw new Exception("更新单据失败");
            }
            strSQL = "UPDATE TabGoodsExportGoods SET billid="+billid+",ConfirmFlage = '"+ibf.getConfirmFlage()+
            "',lirun=round((exportUnitPrice-importavgprice)*exportAmount,2),createtime=getDate() WHERE BillID =" + ibf.getBillId();
            nRet=stmt.executeUpdate(strSQL);
            if(nRet < 1)
            {
                nRet = -1;
                throw new Exception("更新单据明细错误");
            }
            ibf.setBillId(billid);
            
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
                if(proc != null)
                	proc.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException ex) { }
        }
        return nRet;
    }
    
    public int UpdateBillRec(ExportBillForm ibf) throws Exception
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
            strSQL="update TabGoodsExportInfo set factory="+(ibf.getFactory()>0?ibf.getFactory():"null")+
            ",fkType="+(ibf.getFkType()>0?ibf.getFkType():"null")+
            ",fpType="+(ibf.getFpType()>0?ibf.getFpType():"null")+
            ",payLimTime="+(ibf.getPayLimTime().length()>0?"'"+ibf.getPayLimTime()+"'":"null")+
            ",yunfei="+(ibf.getYunFei()>0?ibf.getYunFei():"null")+
            ",carno="+(ibf.getCarNo().length()>0?"'"+ibf.getCarNo()+"'":"null")+
            ",memo='"+ibf.getMemo()+"'"+
            ",tihuo='"+ibf.getTihuo()+"'"+
            ",yewuid='"+ibf.getYewuid()+"'"+
            " where billid="+ibf.getBillId()+" and deptid="+ibf.getDeptid()+" and confirmFlage='0'";
            nRet = stmt.executeUpdate(strSQL);
        	if(nRet != 1)
        		throw new Exception("更新销售单失败");
            
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
    
    public ExportBillForm getExportBillById(int billId,int deptid) throws Exception
    {
    	ExportBillForm ibf=new ExportBillForm();
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
            strSQL = "SELECT a.*,b.name as fname,c.name as pname,d.name as kname,e.shortname,e.name as gongsi,e.tip,f.name as yewuname FROM TabGoodsExportInfo a left join TabFactory b "+
            "on a.factory=b.id left join TabFaPiao c on a.fptype=c.id left join TabfukuanType d"+
            " on a.fktype=d.id inner join TabDepartInfo e on a.deptid=e.id left join TabYeWuYuan f on a.yewuid=f.id WHERE a.BillID=" + billId+" and a.deptid="+deptid;
            
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	ibf=new ExportBillForm();
            	ibf.setBillId(billId);
            	ibf.setSalesPerson(rs.getString("salesperson"));
            	ibf.setFactory(rs.getInt("factory"));
            	ibf.setFactoryName(rs.getString("fname"));
            	ibf.setExportTime(oConvert.FormDateShort(rs.getTimestamp("ExportTime")));
            	if(rs.getTimestamp("PaymentTime")!=null)
            		ibf.setPaymentTime(oConvert.FormDateTimeShort(rs.getTimestamp("PaymentTime")));
            	
            	ibf.setTotalNum(rs.getDouble("totalnum"));
            	ibf.setTotalPrice(rs.getDouble("totalprice"));
            	ibf.setFkType(oConvert.getInt(rs.getString("fkType"), 0));
            	ibf.setFpType(oConvert.getInt(rs.getString("fpType"), 0));
            	ibf.setFkName(rs.getString("kname"));
            	ibf.setFpName(rs.getString("pname"));
            	ibf.setYunFei(rs.getFloat("yunfei"));
            	ibf.setRealmoney(rs.getDouble("realmoney"));
            	ibf.setCarNo(oConvert.getString(rs.getString("carNo"),""));
            	if(rs.getTimestamp("payLimTime")!=null)
            		ibf.setPayLimTime(oConvert.FormDateShort(rs.getTimestamp("payLimTime")));
            	if(rs.getTimestamp("PayShipTime")!=null)
            		ibf.setPayShipTime(oConvert.FormDateTimeShort(rs.getTimestamp("PayShipTime")));
            	if(rs.getTimestamp("PayFpTime")!=null)
            		ibf.setPayFpTime(oConvert.FormDateTimeShort(rs.getTimestamp("PayFpTime")));
            	ibf.setConfirmFlage(rs.getString("confirmFlage"));
            	ibf.setMemo(oConvert.getString(rs.getString("memo"),""));
            	ibf.setDeptid(rs.getInt("deptid"));
            	ibf.setShortname(rs.getString("shortname"));
            	ibf.setGongsi(rs.getString("gongsi"));
            	ibf.setKind(rs.getInt("kind"));
            	ibf.setTihuo(oConvert.getString(rs.getString("tihuo"),"自提"));
            	ibf.setTotaljiagong(rs.getDouble("totaljiagong"));
            	ibf.setTip(oConvert.getString(rs.getString("tip")));
            	ibf.setYewuid(rs.getInt("yewuid"));
            	if(ibf.getYewuid()==0)
            		ibf.setYewuname("公司");
            	else
            		ibf.setYewuname(rs.getString("yewuname"));
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
            strSQL = "SELECT DISTINCT top 500 BillID FROM TabGoodsexportInfo where confirmFlage='1' Order by BillID desc";
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
	public List getBillIdList(int deptid,String username)
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
            strSQL = "SELECT DISTINCT top 500 BillID FROM TabGoodsexportInfo where confirmFlage='1' "+
            " and paymenttime is null and deptid="+deptid+" and salesperson='"+username+"' Order by BillID desc";
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
   //取得所有有购买记录的会员列表
	@SuppressWarnings("unchecked")
	public List getFactList()
    {
		List factList= new ArrayList();
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
            strSQL = "SELECT DISTINCT factory,b.name FROM TabGoodsexportInfo a inner join TabFactory b on a.factory=b.id Order by b.name";
            FactoryInfoForm fif=null;
            for(rs = stmt.executeQuery(strSQL); rs.next(); factList.add(fif))
            {
            	fif=new FactoryInfoForm();
            	fif.setId(rs.getInt("factory"));
            	fif.setName(rs.getString("name"));
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
        return factList;
    }
	@SuppressWarnings("unchecked")
	public List getFactList(String zhujima)
    {
		List factList= new ArrayList();
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
            strSQL = "SELECT DISTINCT factory,b.name FROM TabGoodsexportInfo a inner join TabFactory b "+
            "on a.factory=b.id";
            if(zhujima.length()>0)
            	strSQL=strSQL+" where b.zhujima like '%"+zhujima+"%'";
            strSQL=strSQL+" Order by b.name";
            FactoryInfoForm fif=null;
            for(rs = stmt.executeQuery(strSQL); rs.next(); factList.add(fif))
            {
            	fif=new FactoryInfoForm();
            	fif.setId(rs.getInt("factory"));
            	fif.setName((rs.getString("name").length()>18?rs.getString("name").substring(0,17)+"..":rs.getString("name")));
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
        return factList;
    }
   //取得所有售货员列表
	@SuppressWarnings("unchecked")
	public List getPersonsList(int deptid)
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
            strSQL = "SELECT DISTINCT salesperson FROM TabGoodsexportInfo ";
            if(deptid>0)
            	strSQL = strSQL+" where deptid="+deptid;
            strSQL = strSQL+" Order by salesperson";
            String curSendPersons;
            for(rs = stmt.executeQuery(strSQL); rs.next(); inceptPersonsList.add(curSendPersons))
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
        return inceptPersonsList;
    }
	//	意向单列表
	@SuppressWarnings("unchecked")
	public List getHoldBillList(String salesPerson) throws Exception
    {
		List hbList=new ArrayList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;

        strSQL = " SELECT a.*,b.name FROM TabGoodsExportInfo a left join TabFactory b on a.factory=b.id "+
        "where confirmFlage='2'";
        if(!salesPerson.equals("admin"))
        {
        	strSQL=strSQL+" and salesperson='"+salesPerson+"'";
        }
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            ExportBillForm gibi=null;
            for(rs = stmt.executeQuery(strSQL); rs.next(); hbList.add(gibi))
            {
            	gibi = new ExportBillForm();
                gibi.setBillId(rs.getInt("billid"));
                gibi.setSalesPerson(rs.getString("salesPerson"));
                gibi.setTotalNum(rs.getDouble("totalnum"));
                gibi.setTotalPrice(rs.getDouble("totalprice"));
                gibi.setExportTime(oConvert.FormDateTimeShort(rs.getTimestamp("exportTime")));
                if(rs.getString("paymentTime")!=null)
                	gibi.setPaymentTime(oConvert.FormDateTimeShort(rs.getTimestamp("paymentTime")));
                else
                	gibi.setPaymentTime("");
                gibi.setFactory(rs.getInt("factory"));
                gibi.setFactoryName(rs.getString("name"));
                gibi.setDeptid(rs.getInt("deptid"));
                gibi.setKind(rs.getInt("kind"));
            }
            rs.close();
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
        return hbList;
    }
    //售货单记录查询
	@SuppressWarnings("unchecked")
	public ResultsetList getBillListByRule(ExportBillForm ibf,String exportTime1,String exportTime2,String paymentTime1,String paymentTime2,int curPage,String ifpay,String kind,String orderby) throws Exception
    {
		ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
     
        strSQL = " SELECT a.*,b.name,c.shortname,d.name as yewuname FROM TabGoodsExportInfo a left join TabFactory b on a.factory=b.id "+
        "inner join TabDepartInfo c on a.deptid=c.id left join Tabyewuyuan d on a.yewuid=d.id where (confirmFlage='1' or confirmFlage='3')";
        strSQL1 = " SELECT sum(totalnum) as allnum,sum(totalprice+totaljiagong),sum(realmoney),sum(totalprice+totaljiagong-realmoney) as allprice FROM TabGoodsExportInfo a where confirmFlage='1'";
        String whereStr = "";
        if(ibf.getBillId()!= 0)
            whereStr = whereStr + " and BillID=" + ibf.getBillId();
        if(!ibf.getSalesPerson().equals(""))
            whereStr = whereStr + " and salesPerson='" + ibf.getSalesPerson()+"'";
        if(ibf.getFactory()!=0)
            whereStr = whereStr + " and factory=" + ibf.getFactory();
        
        if(!exportTime1.equals(""))
        	whereStr = whereStr + " and exportTime >= '" + exportTime1 + "'";
        if(!exportTime2.equals(""))
        	whereStr = whereStr + " and exportTime <='" + exportTime2 + "'";
        if(!paymentTime1.equals(""))
        	whereStr = whereStr + " and paymentTime >= '" + paymentTime1 + "'";
        if(!paymentTime2.equals(""))
        	whereStr = whereStr + " and paymentTime <='" + paymentTime2 + "'";
        if(ifpay.equals("1"))
        	whereStr = whereStr + " and paymenttime is not null";
        if(ifpay.equals("0"))
        	whereStr = whereStr + " and paymenttime is null";
        if(ibf.getFkType()!= 0)
            whereStr = whereStr + " and fktype=" + ibf.getFkType();
        if(ibf.getDeptid()>0)
            whereStr = whereStr + " and a.deptid=" + ibf.getDeptid();
        if(kind.equals("1"))
            whereStr = whereStr + " and a.kind=1";
        else if(kind.equals("-1"))
            whereStr = whereStr + " and a.kind=-1";
        if(ibf.getYewuid()>-1)
            whereStr = whereStr + " and a.yewuid=" + ibf.getYewuid();
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
            		ExportBillForm gibi = new ExportBillForm();
                    gibi.setBillId(rs.getInt("billid"));
                    gibi.setSalesPerson(rs.getString("salesPerson"));
                    gibi.setTotalNum(rs.getDouble("totalnum"));
                    gibi.setTotalPrice(rs.getDouble("totalprice"));
                    gibi.setExportTime(oConvert.FormDateTimeShort(rs.getTimestamp("exportTime")));
                    if(rs.getString("paymentTime")!=null)
                    	gibi.setPaymentTime(oConvert.FormDateTimeShort(rs.getTimestamp("paymentTime")));
                    else
                    	gibi.setPaymentTime("");
                    gibi.setFactory(rs.getInt("factory"));
                    gibi.setFactoryName((rs.getString("name").length()>18?rs.getString("name").substring(0,17)+"..":rs.getString("name")));
                    gibi.setRealmoney(rs.getDouble("realmoney"));
                    gibi.setDeptid(rs.getInt("deptid"));
                    gibi.setShortname(rs.getString("shortname"));
                    gibi.setConfirmFlage(rs.getString("confirmflage"));
                    gibi.setKind(rs.getInt("kind"));
                    gibi.setTotaljiagong(rs.getDouble("totaljiagong"));
                    gibi.setYewuid(rs.getInt("yewuid"));
                    if(gibi.getYewuid()==0)
                    	gibi.setYewuname("公司");
                    else
                    	gibi.setYewuname(rs.getString("yewuname"));
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
            	ibsri.realmoney=oConvert.getRound(rs.getDouble(3),2);
            	ibsri.oddmoney=oConvert.getRound(rs.getDouble(4),2);
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
//	分售货员统计
    @SuppressWarnings("unchecked")
	public ExportReportResultInfo getReportListByRule(ExportReportRule ebsr) throws Exception
    {
       int totalRecNum;
        float totalPrice = 0;
        float totalPrice1 = 0;
        float totalPrice2 = 0;
        List exportReportList;
        ExportReportResultInfo ersri;
        totalRecNum = 0;
        exportReportList = new ArrayList();
        ersri = new ExportReportResultInfo();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQLForCount = null;
        String strSQLForPrice = null;
        String strSQLForPrice1 = null;
        String strSQLForPrice2 = null;
        String strSQLForssPrice=null;//实收
        String strSQLForqkPrice=null;//欠款
        String strSQLForfhPrice=null;//发货
        String strSQLForHKPrice=null;//还款
        String exportTime1 = ebsr.getExportTime1();
        String exportTime2 = ebsr.getExportTime2();
        String paymentTime1 = ebsr.getPaymentTime1();
        String paymentTime2 = ebsr.getPaymentTime2();
        //实收
        strSQLForssPrice="select count(a.BillID),sum(a.totaldisprice),sum(a.oddment),sum(a.realmoney),b.UserName,sum(daijin) from TabGoodsExportInfo a,TabUserInfo b ";
        strSQLForssPrice=strSQLForssPrice+ "where a.SalesPersons=b.UserName ";
        strSQLForssPrice=strSQLForssPrice+ "AND a.ExportTime >= '" + exportTime1 + "'  AND a.ExportTime <='" + exportTime2 + "' group by b.UserName";
        //欠款
        strSQLForqkPrice="select count(a.BillID),sum(a.totaldisprice-a.oddment-a.realmoney-a.daijin),sum(a.realmoney),b.UserName from TabGoodsExportInfo a,TabUserInfo b ";
        strSQLForqkPrice=strSQLForqkPrice+"where a.SalesPersons=b.UserName and a.PaymentTime is null and fahuo=0 ";
        strSQLForqkPrice=strSQLForqkPrice+"AND a.ExportTime >= '" + exportTime1 + "'  AND a.ExportTime <='" + exportTime2 + "' group by b.UserName";
       //发货
        strSQLForfhPrice="select count(a.BillID),sum(a.totaldisprice-a.oddment-a.realmoney-a.daijin),sum(a.realmoney),b.UserName from TabGoodsExportInfo a,TabUserInfo b ";
        strSQLForfhPrice=strSQLForfhPrice+"where a.SalesPersons=b.UserName and a.PaymentTime is null and fahuo=1 ";
        strSQLForfhPrice=strSQLForfhPrice+"AND a.ExportTime >= '" + exportTime1 + "'  AND a.ExportTime <='" + exportTime2 + "' group by b.UserName";
        //还款
        strSQLForHKPrice="select count(a.BillID),sum(a.realmoney-a.yajin),b.UserName from TabGoodsExportInfo a,TabUserInfo b "+
        	"where a.SalesPersons=b.UserName and a.ExportTime<'"+exportTime1+"' and fahuo=0 AND a.PaymentTime >= '" + exportTime1 + "'  AND a.PaymentTime <='" + exportTime2 + "' group by b.UserName";
        
        strSQL = " SELECT * FROM TabGoodsExportInfo ";
        strSQLForCount = " SELECT count(*) FROM TabGoodsExportInfo ";
        strSQLForPrice = " SELECT sum(realmoney) FROM TabGoodsExportInfo where PaymentTime is not null";
        strSQLForPrice1 = " SELECT sum(realmoney) FROM TabGoodsExportInfo where PaymentTime is null and fahuo=0";
        strSQLForPrice2 = " SELECT sum(realmoney) FROM TabGoodsExportInfo where PaymentTime is null and fahuo=1";
        String whereStr = "";
               
        whereStr = whereStr + " ExportTime >= '" + exportTime1 + "' and";
        whereStr = whereStr + " ExportTime <='" + exportTime2 + "'";
        if(!paymentTime1.equals(""))
            whereStr = whereStr + " PaymentTime>= '" + paymentTime1 + "'";
        if(!paymentTime2.equals(""))
            whereStr = whereStr + " PaymentTime<='" + paymentTime2 + "' ";
        
        if(!whereStr.equals(""))
        {
            strSQL = strSQL + " WHERE " + whereStr;
            strSQLForCount = strSQLForCount + " WHERE " + whereStr;
            strSQLForPrice = strSQLForPrice + " and " + whereStr;
            strSQLForPrice1 = strSQLForPrice1 + " and " + whereStr;
            strSQLForPrice2 = strSQLForPrice2 + " and " + whereStr;
        }
        
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(strSQLForCount);
            if(!rs.next())
            {
                throw new Exception("获得总记录数失败");
            }
            totalRecNum = rs.getInt(1);
            rs.close();
            rs = null;
            rs = stmt.executeQuery(strSQLForPrice);
            if(!rs.next())
            {
                throw new Exception("获得总金额失败");
            }
            totalPrice = rs.getFloat(1);
            rs.close();
            rs = null;
            rs = stmt.executeQuery(strSQLForPrice1);
            if(!rs.next())
            {
                throw new Exception("获得总金额1失败");
            }
            totalPrice1 = rs.getFloat(1);
            rs.close();
            rs = null;
            rs = stmt.executeQuery(strSQLForPrice2);
            if(!rs.next())
            {
                throw new Exception("获得总金额2失败");
            }
            totalPrice2 = rs.getFloat(1);
            rs.close();
            rs = null;
            
            rs = stmt.executeQuery(strSQLForssPrice);
            
            while(rs.next())
            {             
            	
                    
            	    GoodsExportReportInfo geri = new GoodsExportReportInfo();
                    geri.setTotalRecNum(rs.getInt(1));
                    geri.setSalesPersonsYs(rs.getFloat(2));
                    geri.setSalesPersonsQl(rs.getFloat(3));
                    geri.setSalesPersonsSs(rs.getFloat(4));
                    geri.setSalesPersonsNa(rs.getString(5));
                    geri.setDaijin(rs.getFloat(6));
                    exportReportList.add(geri);
                   
                    
                      
            }

            
            rs.close();
            rs = null;
            
           
            rs = stmt.executeQuery(strSQLForqkPrice);
            
           while(rs.next())
            {        
            	int index = 0;
          		GoodsExportReportInfo qk = null;
                    for(index=0;index<exportReportList.size();index++)
                    {
                     qk=(GoodsExportReportInfo)exportReportList.get(index);           	    
            	    if(qk.getSalesPersonsNa().equals(rs.getString(4)))
            	    {
            	    qk.setSalesPersonsQkNum(rs.getInt(1));
            	    qk.setSalesPersonsQkSs(rs.getFloat(3));
                    qk.setSalesPersonsQk(rs.getFloat(2));
                   
                     } 
            	   
                    }                        
            }
              
            
            rs.close();
            rs = null;
            
            rs = stmt.executeQuery(strSQLForfhPrice);
            
            while(rs.next())
            {             
            	int index = 0;
          		GoodsExportReportInfo fh= null;
                    for(index=0;index<exportReportList.size();index++)
                    {
                     fh=(GoodsExportReportInfo)exportReportList.get(index);           	    
            	    if(fh.getSalesPersonsNa().equals(rs.getString(4)))
            	    {
            	    fh.setSalesPersonsFhNum(rs.getInt(1));
            	    fh.setSalesPersonsQkSs(rs.getFloat(3));
                    fh.setSalesPersonsFh(rs.getFloat(2));
                   
                     } 
            	   
                    }
                                        
            }

            
            rs.close();
            rs = null;
            
            rs = stmt.executeQuery(strSQLForHKPrice);
            boolean bfind;
            while(rs.next())
            {             
            	int index = 0;
            	bfind=false;
          		GoodsExportReportInfo fh= null;
                for(index=0;index<exportReportList.size();index++)
                {
                	fh=(GoodsExportReportInfo)exportReportList.get(index);           	    
                	if(fh.getSalesPersonsNa().equals(rs.getString(3)))
                	{
                		fh.setSalesPersonsHKNum(rs.getInt(1));
                		fh.setSalesPersonsHK(rs.getFloat(2));
                		bfind=true;
                	} 
                	
                }
                if(!bfind)
            	{
            		GoodsExportReportInfo geri = new GoodsExportReportInfo();
                    geri.setSalesPersonsNa(rs.getString(3));
                    geri.setSalesPersonsHKNum(rs.getInt(1));
                    geri.setSalesPersonsHK(rs.getFloat(2));	
                    exportReportList.add(geri);
            	}
                                        
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
        ersri.setExportReportList(exportReportList);
        ersri.setTotalRecNum(totalRecNum);
        ersri.setTotalXjPrice(totalPrice);
        ersri.setTotalQkPrice(totalPrice1);
        ersri.setTotalFhPrice(totalPrice2);
        return ersri;
    }
//  欠款和发货统计
    @SuppressWarnings("unchecked")
	public ExportReportResultInfo getReportQFListByRule(ExportReportRule ebsr)
    {
       
        List exportReportList;
        ExportReportResultInfo ersri;
        
        exportReportList = new ArrayList();
        ersri = new ExportReportResultInfo();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strAll=null;
        String strSQLForqkPrice=null;
        String strSQLForfhPrice=null;
        String exportTime1 = ebsr.getExportTime1();
        String exportTime2 = ebsr.getExportTime2();
        float totalprice=0;
        String bbtj=ebsr.getBbtj();
      
        strSQL="select a.BillID,a.SalesPersons,(a.totaldisprice-a.oddment-a.daijin),(a.totaldisprice-a.oddment-a.realmoney-a.daijin),"+
        "b.MemberName,a.ExportTime,a.realmoney,c.c_name from TabGoodsExportInfo a inner join TabMemberInfo b on "+
        "a.MemberId=b.MemberId left join TabHuoYun c on a.huoyun=c.autoid ";
        strAll="select sum(a.totaldisprice-a.oddment-a.realmoney-a.daijin) from TabGoodsExportInfo a inner join TabMemberInfo b on a.MemberId=b.MemberId";
        String where1=" where a.ExportTime >= '" + exportTime1 + "'  AND a.ExportTime <='" + exportTime2 + "' ";
        strSQL=strSQL+where1;
        strAll=strAll+where1;
        String where2="";
       
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //欠款
            if(bbtj.equals("2"))
            {
            	where2=" and a.PaymentTime is null  and a.fahuo=0 ";
            	strAll=strAll+where2;
            	rs = stmt.executeQuery(strAll);
            	if(rs.next())
            		totalprice=rs.getFloat(1);
            	rs.close();
            	ersri.setTotalQkPrice(totalprice);
            	strSQLForqkPrice=strSQL+where2+"order by a.ExportTime asc";
            	rs = stmt.executeQuery(strSQLForqkPrice);
            }
            else//发货
            {
            	where2=" and a.PaymentTime is null  and a.fahuo=1 ";
            	strAll=strAll+where2;
            	rs = stmt.executeQuery(strAll);
            	if(rs.next())
            		totalprice=rs.getFloat(1);
            	rs.close();
            	ersri.setTotalQkPrice(totalprice);
            	strSQLForfhPrice=strSQL+where2+"order by a.ExportTime asc";
            	rs = stmt.executeQuery(strSQLForfhPrice);	
            }
            if(rs.last())
            	ersri.setTotalRecNum(rs.getRow());
            else
            	ersri.setTotalRecNum(0);
            float totalQK=0;
            rs.beforeFirst();
            while(rs.next())
            {  
            	GoodsExportReportInfo geri = new GoodsExportReportInfo();
                geri.setTotalRecNum(rs.getInt(1));//售货单号
                geri.setSalesPersonsNa(rs.getString(2));//收银人
                geri.setSalesPersonsYs(rs.getFloat(3));//应收货款
                geri.setSalesPersonsQk(rs.getFloat(4));//尚欠货款
                totalQK=totalQK+rs.getFloat(4);
                geri.setMemberNa(rs.getString("MemberName"));//会员名称
                geri.setExportTime(rs.getString("ExportTime"));//欠款或发货时间  
                geri.setSalesPersonsQkSs(rs.getFloat("realmoney"));//欠款已收
                geri.setHuoyunbu(oConvert.getString(rs.getString("c_name"),""));//货运部
                exportReportList.add(geri);
                   
            }
            ersri.setTotalQkPrice(totalQK);
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
        ersri.setExportReportList(exportReportList);
       
        return ersri;
    }
//  分组统计
    @SuppressWarnings("unchecked")
	public ExportReportResultInfo getReportZKListByRule(ExportReportRule ebsr)
    {
       
        List exportReportList;
        ExportReportResultInfo ersri;
        
        exportReportList = new ArrayList();
        ersri = new ExportReportResultInfo();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        
        String exportTime1 = ebsr.getExportTime1();
        String exportTime2 = ebsr.getExportTime2();
        strSQL = " SELECT b.salespersons,SUM(CASE WHEN (b.discount*b.pifadiscount)<0.4 THEN a.exportamount*a.exportunitprice*b.discount*b.pifadiscount ELSE 0 END),"+
        "SUM(CASE WHEN (b.discount*b.pifadiscount)<0.4 THEN a.exportamount ELSE 0 END),"+
        "SUM(CASE WHEN (b.discount*b.pifadiscount)>=0.4 and (b.discount*b.pifadiscount)<0.5 THEN a.exportamount*a.exportunitprice*b.discount*b.pifadiscount ELSE 0 END),"+
        "SUM(CASE WHEN (b.discount*b.pifadiscount)>=0.4 and (b.discount*b.pifadiscount)<0.5 THEN a.exportamount ELSE 0 END),"+
        "SUM(CASE WHEN (b.discount*b.pifadiscount)>=0.5 and (b.discount*b.pifadiscount)<0.6 THEN a.exportamount*a.exportunitprice*b.discount*b.pifadiscount ELSE 0 END),"+
        "SUM(CASE WHEN (b.discount*b.pifadiscount)>=0.5 and (b.discount*b.pifadiscount)<0.6 THEN a.exportamount ELSE 0 END),"+
        "SUM(CASE WHEN (b.discount*b.pifadiscount)>=0.6 and (b.discount*b.pifadiscount)<0.7 THEN a.exportamount*a.exportunitprice*b.discount*b.pifadiscount ELSE 0 END),"+
        "SUM(CASE WHEN (b.discount*b.pifadiscount)>=0.6 and (b.discount*b.pifadiscount)<0.7 THEN a.exportamount ELSE 0 END),"+
        "SUM(CASE WHEN (b.discount*b.pifadiscount)>=0.7 and (b.discount*b.pifadiscount)<0.8 THEN a.exportamount*a.exportunitprice*b.discount*b.pifadiscount ELSE 0 END),"+
        "SUM(CASE WHEN (b.discount*b.pifadiscount)>=0.7 and (b.discount*b.pifadiscount)<0.8 THEN a.exportamount ELSE 0 END),"+
        "SUM(CASE WHEN (b.discount*b.pifadiscount)>=0.8 THEN a.exportamount*a.exportunitprice*b.discount*b.pifadiscount ELSE 0 END),"+
        "SUM(CASE WHEN (b.discount*b.pifadiscount)>=0.8 THEN a.exportamount ELSE 0 END)"+
        " FROM TabGoodsExportGoods a inner join TabGoodsExportInfo b on a.billid=b.billid "+
        "where b.ExportTime >= '" + exportTime1 + "' AND b.ExportTime <='" + exportTime2 + "' group by b.salespersons order by b.salespersons";
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(strSQL);
            while(rs.next())
            {             
               
            	    GoodsExportReportInfo geri = new GoodsExportReportInfo();      
            	    geri.setSalesPersonsNa(rs.getString(1));//售货员 
                    geri.setSalesPersonsYs(rs.getFloat(2));//4折以下的金额
                    geri.setTotalRecNum(rs.getInt(3));//4折以下的数量
                    
                    geri.setSalesPersonsQl(rs.getFloat(4));//4-5折的金额
                    geri.setSalesPersonsQkNum(rs.getInt(5));//4-5折的数量
                    
                    geri.setSalesPersonsSs(rs.getFloat(6));//5-6折的金额
                    geri.setSalesPersonsFhNum(rs.getInt(7));//5-6折的数量
                    
                    geri.setSalesPersonsQk(rs.getFloat(8));//6-7折的金额
                    geri.setSalesPersonsHKNum(rs.getInt(9));//6-7折的数量
                    
                    geri.setSalesPersonsFh(rs.getFloat(10));//7-8折的金额
                    geri.setsellnum(rs.getInt(11));//7-8折的数量
                    
                    geri.setSalesPersonsHK(rs.getFloat(12));//8折以上的金额
                    geri.setbacknum(rs.getInt(13));//8折以上的数量
                    exportReportList.add(geri);          
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
        ersri.setExportReportList(exportReportList);
       
        return ersri;
    }
//  分种类统计
    @SuppressWarnings("unchecked")
	public ExportReportResultInfo getGoodsReportListByRule(ExportReportRule ebsr)
    {
        float totalPrice = 0;   
        List exportReportList;
        ExportReportResultInfo ersri;
        exportReportList = new ArrayList();
        ersri = new ExportReportResultInfo();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQLForPrice = null;
        
        String exportTime1 = ebsr.getExportTime1();
        String exportTime2 = ebsr.getExportTime2();
       
       
        String goodsReport="SELECT sum(ExportAmount),SUM(ExportAmount * ExportUnitPrice*b.discount*b.pifadiscount), d.GoodsTypeName "+
        	"FROM TabGoodsExportGoods a inner join TabGoodsExportInfo b on a.billid=b.billid "+
        	"inner join TabGoodsInfo c on a.goodsid=c.goodsid inner join TabGoodsTypeCode d on c.goodstype=d.goodstype "+
        	"WHERE a.CreateTime >= '"+exportTime1+"' AND a.CreateTime <= '"+exportTime2+"'";
        
        //销售
        String goodsReportXs=goodsReport+" and ExportAmount>0 group by d.GoodstypeName order by SUM(ExportAmount * ExportUnitPrice) desc";
        //退货
        String goodsReportTh=goodsReport+ " and ExportAmount<0 group by d.GoodstypeName order by SUM(ExportAmount * ExportUnitPrice) desc";
        
        strSQLForPrice = " SELECT sum(TotalDisPrice) FROM TabGoodsExportInfo "+
        	"where ExportTime >= '" + exportTime1 + "' and ExportTime <='" + exportTime2 + "'";
        
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
           
            rs = stmt.executeQuery(strSQLForPrice);
            if(!rs.next())
            {
                throw new Exception("获得总金额失败");
            }
            totalPrice = rs.getFloat(1);
            rs.close();
            rs = null;
            
            rs = stmt.executeQuery(goodsReportXs);
            while(rs.next())
            {             
            	GoodsExportReportInfo geri = new GoodsExportReportInfo();
                geri.setgoodname(rs.getString(3));//某货物的名称
                geri.setsellnum(rs.getInt(1));//某货物销售的实际数量
                geri.setsellprice(rs.getFloat(2));//某货物销售的实际应收款
                exportReportList.add(geri);
                   
            }

            
            rs.close();
            rs = null;
            
         
            rs = stmt.executeQuery(goodsReportTh);
            
            while(rs.next())
            {             
            	int index = 0;
          		GoodsExportReportInfo xs= null;
                for(index=0;index<exportReportList.size();index++)
                {
                    xs=(GoodsExportReportInfo)exportReportList.get(index);           	    
            	    if(xs.getgoodname().equals(rs.getString(3)))
            	    {
            	    	xs.setbacknum(rs.getInt(1)); //退货数量           	    
            	    	xs.setbackprice(rs.getFloat(2));//退货金额 
                     } 
                }
                                        
            }

            
            rs.close();
            rs = null;
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
        ersri.setExportReportList(exportReportList);
        ersri.setTotalXjPrice(totalPrice);
 
        return ersri;
    }
//  销售率
    @SuppressWarnings("unchecked")
	public ExportReportResultInfo getReportXiaoShouLv(ExportReportRule ebsr)
    {
        
        List exportReportList=new ArrayList();
        ExportReportResultInfo ersri;
        ersri = new ExportReportResultInfo();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
       
        String exportTime1 = ebsr.getExportTime1();
        String exportTime2 = ebsr.getExportTime2();
       
        //销售
        String goodsReportXs="SELECT b.GoodsTypeName, SUM(c.ImportAmount) AS importnum,"+ 
        	"SUM(c.ImportAmount * c.ImportUnitPrice) AS importprice FROM TabGoodsInfo a INNER JOIN"+
            " TabGoodsTypeCode b ON a.GoodsType = b.GoodsType INNER JOIN TabGoodsImportGoods c ON a.GoodsID = c.GoodsID";
        //进货
        String goodsReportTh="SELECT b.GoodsTypeName, SUM(c.ExportAmount) AS exportnum,"+ 
        	"SUM(c.ExportAmount * c.ExportUnitPrice*d.discount*d.pifadiscount) AS exportprice FROM TabGoodsInfo a INNER JOIN"+
            " TabGoodsTypeCode b ON a.GoodsType = b.GoodsType INNER JOIN TabGoodsExportGoods c ON a.GoodsID = c.GoodsID"+
        	" inner join TabGoodsExportInfo d on c.billid=d.billid ";
        String where=" WHERE c.CreateTime >= '"+exportTime1+"' AND c.CreateTime <= '"+exportTime2+"' GROUP BY b.GoodsTypeName ";
        goodsReportXs=goodsReportXs+where;
        goodsReportTh=goodsReportTh+where;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
        
            rs = stmt.executeQuery(goodsReportXs);
            while(rs.next())
            {             
            	GoodsExportReportInfo geri = new GoodsExportReportInfo();
                geri.setgoodname(rs.getString(1));//子类的名称
                geri.setSalesPersonsFhNum(rs.getInt(2));//子类销售量
                geri.setSalesPersonsFh(rs.getFloat(3));//子类销售额
                exportReportList.add(geri);
                   
            }

            
            rs.close();
            rs = null;
            
            rs = stmt.executeQuery(goodsReportTh);           
            while(rs.next())
            {             
            	int index = 0;
          		GoodsExportReportInfo xs= null;
                for(index=0;index<exportReportList.size();index++)
                {
                    xs=(GoodsExportReportInfo)exportReportList.get(index);           	    
            	    if(xs.getgoodname().equals(rs.getString(1)))
            	    {
            	    	xs.setSalesPersonsQkNum(rs.getInt(2)); //子类入库数量           	    
            	    	xs.setSalesPersonsQk(rs.getFloat(3));//子类入库金额
            	    	xs.setsellprice(rs.getInt(2)*100/xs.getSalesPersonsFhNum());//销售率
                  	    break;
                    } 
                }
                                        
            }          
            rs.close();
            rs = null;
            ContentComparator comp = new ContentComparator();  
            Collections.sort(exportReportList,comp);

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
        ersri.setExportReportList(exportReportList);
        return ersri;
    }
    public class ContentComparator implements Comparator 
    {

    	 public int compare(Object o1, Object o2) 
    	 {
    		 GoodsExportReportInfo c1 = (GoodsExportReportInfo)o1;
    		 GoodsExportReportInfo c2 = (GoodsExportReportInfo)o2;
    		 if (c1.getsellprice() > c2.getsellprice()) 
    		 {
    			 return -1;
    		 }
    		 else
    		 {
    			 if (c1.getsellprice() == c2.getsellprice()) 
    			 {
    				 return 0;
    			 }
    			 else
    			 {
    				 return 1;
    			 }
    		 }
    	 }
    }


    //删除售货单
    public int deleteExportBill(ExportBillForm ibf) throws Exception
    {
    	int nRet=0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        Statement stmt1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        String strSQL=null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            stmt1 = conn.createStatement();
            conn.setAutoCommit(false);
            strSQL="select * from TabGoodsExportInfo WHERE confirmFlage='2' and BillID=" + ibf.getBillId()+" and deptid="+ibf.getDeptid();
            rs=stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("此单不存在");
            else
            	ibf.setKind(rs.getInt("kind"));
            rs.close();
            strSQL = "select * from TabGoodsExportGoods WHERE BillID=" + ibf.getBillId()+" and deptid="+ibf.getDeptid();
            rs=stmt.executeQuery(strSQL);
            GoodsInfo gi=new GoodsInfo();
            while(rs.next() && ibf.getKind()==1)
            {
        		double avgprice=gi.CountAvgPrice(rs.getString("goodsid"), rs.getInt("deptid"), rs.getDouble("exportAmount"), rs.getDouble("importAvgPrice"),conn);
                strSQL = "select * from TabGoodsRepertory WHERE goodsid='" + rs.getString("goodsid")+"' and storeid="+
                rs.getInt("storeid");
                rs1=stmt1.executeQuery(strSQL);
                if(rs1.next())
                {
                	strSQL = "update TabGoodsRepertory set repertoryamount=repertoryamount+("+rs.getString("exportamount")+
                	"),repertoryNum=repertoryNum+("+rs.getString("exportNum")+") WHERE goodsid='" + rs.getString("goodsid")+"'"+
                	" and storeid="+rs.getString("storeid");
                }
                else
                {
                	strSQL = "insert into TabGoodsRepertory (goodsid,repertoryamount,repertoryNum,storeid,avgprice) values('"+rs.getString("goodsid")+"',"+
                	rs.getString("exportamount")+","+rs.getString("exportNum")+","+rs.getString("storeid")+","+avgprice+")";
                }
                nRet=dbc.executeUpdate(stmt1,strSQL);
        		if(nRet != 1)
                	throw new Exception("更新库存记录失败");
                
	            //更新加权平均价
	        	strSQL="update TabGoodsRepertory set avgprice="+avgprice+" where goodsid='"+rs.getString("goodsid")+
	        	"' and storeid in (select id from TabStore where deptid="+rs.getInt("deptid")+")";
	        	nRet=stmt1.executeUpdate(strSQL);
            }
            rs.close();
            strSQL = "update TabGoodsRepertory set RepertoryAmount=0 where Round(RepertoryAmount,3)=0";
            nRet=stmt.executeUpdate(strSQL);
            
            strSQL = "delete from TabGoodsExportInfo WHERE  BillID=" + ibf.getBillId()+" and deptid="+ibf.getDeptid();
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
            	throw new Exception("删除销售记录失败");
            strSQL = "delete from TabGoodsExportGoods WHERE BillID=" + ibf.getBillId()+" and deptid="+ibf.getDeptid();
            stmt.executeUpdate(strSQL);
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
            catch(SQLException e) { }
        }
        return nRet;
    }
    //  修改意向单
    public int deelExportHold(ExportBillForm ibf) throws Exception
    {
    	int nRet=0;
    	int factid=0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL=null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            strSQL="select * from TabGoodsExportInfo WHERE BillID=" + ibf.getBillId()+" and deptid="+ibf.getDeptid();
            rs=stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("此意向单不存在");
            rs.close();
            strSQL="select * from TabGoodsExportInfo WHERE billid>0 and salesPerson='"+ibf.getSalesPerson()+"' and deptid="+ibf.getDeptid()+" and ConfirmFlage='0'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("您有未完成的销售单，单号："+rs.getInt("billid")+"，请先在[销售单录入]中保存或清除");
            rs.close();
            strSQL = "update TabGoodsExportInfo set confirmFlage='0' WHERE BillID=" + ibf.getBillId()+" and deptid="+ibf.getDeptid();
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
            	throw new Exception("更新销售单失败");
            strSQL = "update TabGoodsExportGoods set ConfirmFlage='0' WHERE BillID=" + ibf.getBillId()+" and deptid="+ibf.getDeptid();
            stmt.executeUpdate(strSQL);
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
        return factid;
    }
    //取得累计折扣
    public float getDiscount(int billId)
    {
        float discount;
        discount = 1.0F;
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
            strSQL = "SELECT Discount,pifaDiscount FROM TabGoodsExportInfo WHERE BillID=" + billId;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
                discount = rs.getFloat(1)*rs.getFloat(2);
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
        return discount;
    }
    //更新付款时间
    public int updatePaymentTime(int billId)
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
            strSQL = "UPDATE TabGoodsExportInfo SET PaymentTime = getdate(),realmoney=totaldisprice-oddment-daijin WHERE BillID = " + billId;
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
    //总金额
    public int updateTotalPrice(int billId, float totalPrice)
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
            strSQL = "UPDATE TabGoodsExportInfo SET TotalPrice = " + totalPrice + " WHERE BillID = " + billId;
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
    //应收（折后）金额
    public int updateTotalDisPrice(int billId, float totalDisPrice)
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
            strSQL = "UPDATE TabGoodsExportInfo SET TotalDisPrice = " + totalDisPrice + " WHERE BillID = " + billId;
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
    
    //修改某订单的所属会员
    public int changeOrderMember(String billId,String oldmemid,String newmemid) throws Exception
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
            strSQL="select * from tabMemberInfo where memberid="+newmemid;
            rs=stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("没有这个编号的会员！");
            
            strSQL = "UPDATE TabGoodsExportInfo SET MemberId = " + newmemid + " WHERE BillID = " + billId;
            int i=stmt.executeUpdate(strSQL);
            strSQL = "insert into sqlLog (sqltext) values ('" + StrUtility.replaceString(strSQL, "'", "''")+ "')";
            stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新订单表失败");
            strSQL = "UPDATE TabGoodsExportGoods SET MemberId = " + newmemid + " WHERE BillID = " + billId;
            i=stmt.executeUpdate(strSQL);
            strSQL = "insert into sqlLog (sqltext) values ('" + StrUtility.replaceString(strSQL, "'", "''")+ "')";
            stmt.executeUpdate(strSQL);
            if(i<1)
            	throw new Exception("更新订单明细表失败");
            float realmoney=0,totalmoney=0;
        	strSQL="select totaldisprice,realmoney from tabGoodsExportInfo where billid="+billId;
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	realmoney=rs.getFloat(2);
            	totalmoney=rs.getFloat(1);
            }
            double daijin=Math.floor(totalmoney/500)*5;
            strSQL = "UPDATE TabMemberInfo SET MemberCount = MemberCount+" + realmoney + ",daijinquan=daijinquan+"+
            daijin+" WHERE memberid = " + newmemid;
            i=stmt.executeUpdate(strSQL);
            strSQL = "insert into sqlLog (sqltext) values ('" + StrUtility.replaceString(strSQL, "'", "''")+ "')";
            stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("增加积分表失败");
            strSQL = "UPDATE TabMemberInfo SET MemberCount = MemberCount-" + realmoney + ",daijinquan=daijinquan-"+
            daijin+" WHERE memberid = " + oldmemid;
            i=stmt.executeUpdate(strSQL);
            strSQL = "insert into sqlLog (sqltext) values ('" + StrUtility.replaceString(strSQL, "'", "''")+ "')";
            stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("减去积分表失败");	
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
            	if(rs!=null)
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
    //根据订单号获取会员信息
    public MemberInfo getMemberInfoByBillId(int billId)
    {
    	MemberInfo tmp=null;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        String strSQL = null;
        ResultSet rs = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "select a.* from TabMemberInfo a inner join TabGoodsExportInfo b on a.memberid=b.memberid WHERE b.BillID = " + billId;
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	tmp=new MemberInfo();
            	tmp.setMemberId(rs.getInt("memberid"));
            	tmp.setMemberName(rs.getString("MemberName"));
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            try
            {
            	if(rs!=null)
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
//  采购员收支
    @SuppressWarnings("unchecked")
	public ExportReportResultInfo getReportYeWuYuan(ExportReportRule ebsr)
    {
        
        List exportReportList=new ArrayList();
        ExportReportResultInfo ersri;
        ersri = new ExportReportResultInfo();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
       
        String exportTime1 = ebsr.getExportTime1();
        String exportTime2 = ebsr.getExportTime2();
        //当前帐户
        String sql="select * from TabYewuyuan where del=0";
        //汇款收款
        String sqlGive="SELECT a.id,SUM(CASE WHEN (b.jine)>0 THEN b.jine ELSE 0 END) as allGive,SUM(CASE WHEN (b.jine)<0 THEN b.jine ELSE 0 END) as allPay FROM TabYeWuYuan a left JOIN"+
            " TabGiveGet b ON a.id = b.yewuid and a.del=0 where b.opertime>='"+exportTime1+"' and b.opertime<='"+exportTime2+"' group by a.id";
        //进货
        String sqlImport="SELECT a.id,sum(b.totalprice) as allImport FROM TabYeWuYuan a left JOIN"+
        " TabGoodsImportInfo b ON a.id = b.sendPersons where b.ImportTime>='"+exportTime1+"' and b.ImportTime<='"+exportTime2+"' group by a.id";
        
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next())
            {             
            	GoodsExportReportInfo geri = new GoodsExportReportInfo();
            	geri.setTotalRecNum(rs.getInt("id"));
                geri.setgoodname(rs.getString("name"));
                geri.settotalprice(rs.getFloat("account"));//当前帐户
                exportReportList.add(geri);
            }
            rs.close();
            rs = null;
            int index = 0;
            GoodsExportReportInfo xs= null;
            rs = stmt.executeQuery(sqlGive);
            while(rs.next())
            {             
                for(index=0;index<exportReportList.size();index++)
                {
                    xs=(GoodsExportReportInfo)exportReportList.get(index);           	    
            	    if(xs.getTotalRecNum()==rs.getInt("id"))
            	    {
            	    	xs.setDaijin(rs.getFloat("allGive"));
            	    	xs.setbackprice(rs.getFloat("allPay")); 
                  	    break;
                    } 
                }
                                        
            }  
            rs.close();
            rs = null;
            
            rs = stmt.executeQuery(sqlImport);           
            while(rs.next())
            {             
            	index = 0;
                for(index=0;index<exportReportList.size();index++)
                {
                    xs=(GoodsExportReportInfo)exportReportList.get(index);           	    
            	    if(xs.getTotalRecNum()==rs.getInt("id"))
            	    {
            	    	xs.setsellprice(rs.getFloat("allImport"));     
                  	    break;
                    } 
                }
                                        
            }          
            rs.close();
            rs = null;
            ContentComparator comp = new ContentComparator();  
            Collections.sort(exportReportList,comp);

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
        ersri.setExportReportList(exportReportList);
        return ersri;
    }
//  分厂家统计入库
    @SuppressWarnings("unchecked")
	public ExportReportResultInfo getReportFactory(ExportReportRule ebsr)
    {
        
        List exportReportList=new ArrayList();
        ExportReportResultInfo ersri;
        ersri = new ExportReportResultInfo();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
       
        String exportTime1 = ebsr.getExportTime1();
        String exportTime2 = ebsr.getExportTime2();
        //当前帐户
        String sql="select * from Tabfactory where del=0";
        //入库退库
        String sqlGive="SELECT a.id,SUM(CASE WHEN (c.importAmount*c.ImportUnitPrice)>0 THEN"+
        "(c.importAmount*c.ImportUnitPrice) ELSE 0 END) as allruku,SUM(CASE WHEN (c.importAmount*c.ImportUnitPrice)<0"+
        "THEN (c.importAmount*c.ImportUnitPrice) ELSE 0 END) as alltuiku FROM TabFactory a inner JOIN"+
            " TabGoodsImportInfo b ON a.id = b.factory and a.del=0 inner join TabGoodsImportGoods c"+
            " on b.billid=c.billid where b.Importtime>='"+exportTime1+"' and b.Importtime<='"+exportTime2+"' group by a.id";
        //总入库额
        String sqlall="select sum(totalprice) as allprice from TabGoodsImportInfo where factory is not null";
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next())
            {             
            	GoodsExportReportInfo geri = new GoodsExportReportInfo();
            	geri.setTotalRecNum(rs.getInt("id"));
                geri.setgoodname(rs.getString("name"));
                exportReportList.add(geri);
            }
            rs.close();
            rs = null;
            int index = 0;
            GoodsExportReportInfo xs= null;
            rs = stmt.executeQuery(sqlGive);
            while(rs.next())
            {             
                for(index=0;index<exportReportList.size();index++)
                {
                    xs=(GoodsExportReportInfo)exportReportList.get(index);           	    
            	    if(xs.getTotalRecNum()==rs.getInt("id"))
            	    {
            	    	xs.setDaijin(rs.getFloat("allruku"));
            	    	xs.setbackprice(rs.getFloat("alltuiku")); 
                  	    break;
                    } 
                }
                                        
            }  
            rs.close();
            rs = null;
            ContentComparator comp = new ContentComparator();  
            Collections.sort(exportReportList,comp);
            rs = stmt.executeQuery(sqlall);
            if(rs.next())
            	ersri.setTotalXjPrice(rs.getFloat("allprice"));
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
        ersri.setExportReportList(exportReportList);
        return ersri;
    }
    
    //销售单撤销
    public int CancelExportBill(int oldbillid,int deptid) throws Exception
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
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            stmt1 = conn.createStatement();
            conn.setAutoCommit(false);
            strSQL="select * from TabGoodsExportInfo WHERE confirmFlage='1' and BillID=" + oldbillid+" and deptid="+deptid;
            rs=stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("此单不存在");
            rs.close();
            strSQL = "select * from TabGoodsExportGoods WHERE BillID=" +oldbillid+" and deptid="+deptid;
            rs=stmt.executeQuery(strSQL);
            GoodsInfo gi=new GoodsInfo();
            while(rs.next())
            {
            	
        		double avgprice=gi.CountAvgPrice(rs.getString("goodsid"), rs.getInt("deptid"), rs.getDouble("exportAmount"), rs.getDouble("importAvgPrice"),conn);
                strSQL = "select * from TabGoodsRepertory WHERE goodsid='" + rs.getString("goodsid")+"' and storeid="+
                rs.getInt("storeid");
                rs1=stmt1.executeQuery(strSQL);
                if(rs1.next())
                {
                	strSQL = "update TabGoodsRepertory set repertoryamount=repertoryamount+("+rs.getString("exportamount")+") WHERE goodsid='" + rs.getString("goodsid")+"'"+
                	" and storeid="+rs.getString("storeid");
                }
                else
                {
                	strSQL = "insert into TabGoodsRepertory (goodsid,repertoryamount,storeid,avgprice) values('"+rs.getString("goodsid")+"',"+
                	rs.getString("exportamount")+","+rs.getString("storeid")+","+avgprice+")";
                }
                nRet=dbc.executeUpdate(stmt1,strSQL);
        		if(nRet != 1)
                	throw new Exception("更新库存记录失败");
                
	            //更新加权平均价
	        	strSQL="update TabGoodsRepertory set avgprice="+avgprice+" where goodsid='"+rs.getString("goodsid")+
	        	"' and storeid in (select id from TabStore where deptid="+rs.getInt("deptid")+")";
	        	nRet=stmt1.executeUpdate(strSQL);
            }
            rs.close();
            strSQL = "update TabGoodsRepertory set RepertoryAmount=0 where Round(RepertoryAmount,3)=0";
            nRet=stmt.executeUpdate(strSQL);
            
            strSQL = "update TabGoodsExportInfo set confirmflage='3' WHERE confirmFlage='1' and BillID=" + oldbillid+" and deptid="+deptid;
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
            	throw new Exception("撤销单据失败");
            strSQL = "update TabGoodsExportGoods set confirmflage='3' WHERE BillID=" + oldbillid+" and deptid="+deptid;
            stmt.executeUpdate(strSQL);
            
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
}
