// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsImportInfo.java

package mediastore.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mediastore.common.DBConnection;
import mediastore.util.StrUtility;
import mediastore.util.oConvert;
import mediastore.web.form.FactoryInfoForm;
import mediastore.web.form.ImportBillForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.global.Globals;

// Referenced classes of package mediastore.dao:
//            GoodsImportGoods, GoodsInfo

public class GoodsImportInfo
{

    public GoodsImportInfo()
    {
    }

    public int getTempBillIDNum(String saleman,int deptid,int kind) throws Exception
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
            stmt = conn.createStatement();
            strSQL = "SELECT max(BillID) FROM TabGoodsImportInfo WHERE ConfirmFlage='0' and deptid="+deptid+" and createperson='"+saleman+"' and kind="+kind;
            rs = stmt.executeQuery(strSQL);
            if(rs.next() && rs.getInt(1)!=0)
            {
                billid = rs.getInt(1);
            }
            else
            {
            	while(billid==0)
            	{
	            	billid=(int)(Math.random()*(-1000));
	            	strSQL = "SELECT * FROM TabGoodsImportInfo WHERE billid="+billid;
	            	rs = stmt.executeQuery(strSQL);
	            	if(rs.next())
	            		billid=0;
            	}
            	strSQL = "insert into TabGoodsImportInfo( BillID,createPerson,totalnum,totalprice, "+
            	"importtime, confirmFlage,deptid,kind) values (" + billid + ",'"+saleman+"',0,0,getDate(),'0',"+deptid+","+kind+")"; 
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
    
    public int insertNewBillRec(ImportBillForm ibf) throws Exception
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
        double totalNum=0;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt1 = conn.createStatement();
            //判断总金额是否大于零
            strSQL = "select sum(importamount) as totalnum,sum(importamount*importUnitprice) as totalPrice from"+
            " TabGoodsImportGoods WHERE  BillID=" + ibf.getBillId()+" and confirmFlage='0' and deptid="+ibf.getDeptid();
            rs=stmt.executeQuery(strSQL);
            rs.next();
            if(rs.getFloat(1)==0 || rs.getFloat(2)==0)
            	throw new Exception("总金额不能为零");
            else
            {
            	totalNum=oConvert.getRound(rs.getDouble(1),4);
            	totalPrice=oConvert.getRound(rs.getDouble(2),2);
            }
            rs.close();
            //获取入库单号
            int billid=0;
            strSQL = "select imbillid from TabDepartInfo where id="+ibf.getDeptid();
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	billid=rs.getInt("imbillid")+1;
            else
            	throw new Exception("分公司不存在");
            strSQL = "update TabDepartInfo set imbillid=imbillid+1 where id="+ibf.getDeptid();
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新入库单号失败");
            //更新为正式单 
            double yfunit=0;
            if(ibf.isIyunfei())
            	yfunit=oConvert.getRound(ibf.getYunFei()/totalNum,4);
            strSQL =" update TabGoodsImportInfo set billid="+billid+",totalnum="+totalNum+",totalprice="+totalPrice+",factory="+
            ibf.getFactory()+",fkType="+ibf.getFkType()+",fpType="+ibf.getFpType()+",payLimTime='"+ibf.getPayLimTime()+
            "',yunfei="+ibf.getYunFei()+",carno='"+ibf.getCarNo()+"',confirmFlage='1',ImportTime=getDate(),iyunfei="+(ibf.isIyunfei()?"1":"0")+
            " where billid="+ibf.getBillId()+
            " and deptid="+ibf.getDeptid()+" and confirmFlage='0'";
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
                throw new Exception("新增入库单失败");
            
            strSQL = "UPDATE TabGoodsImportGoods SET billid="+billid+",ConfirmFlage = '1',Createtime=getDate() WHERE BillID =" + ibf.getBillId()+" and confirmFlage='0' and deptid="+ibf.getDeptid();
            nRet=stmt.executeUpdate(strSQL);
            if(nRet < 1)
                throw new Exception("更新入库明细表错误");
            ibf.setBillId(billid);
            //更新库存
            GoodsInfo gi=new GoodsInfo();
            strSQL = "select * from TabGoodsImportGoods WHERE BillID = " + billid+" and deptid="+ibf.getDeptid();
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	double kucun=gi.getRepertoryAmount(rs.getString("goodsid"),rs.getInt("storeid"),conn);
                if(rs.getDouble("importamount")<0 && kucun+rs.getDouble("importamount")<0)
                	throw new Exception("编号为"+rs.getString("goodsid")+"的产品库存不足");
               
                //计算加权平均价
                double avgprice=gi.CountAvgPrice(rs.getString("goodsid"), rs.getInt("deptid"), rs.getDouble("importamount"), rs.getDouble("importUnitPrice")+yfunit,conn);
            	
            	//更新库存
            	strSQL="select * from TabGoodsRepertory where goodsid='"+rs.getString("goodsid")+"' and storeid="+rs.getString("storeid");
            	rs1=stmt1.executeQuery(strSQL);
            	if(rs1.next())
            		strSQL = "UPDATE TabGoodsRepertory SET RepertoryAmount=RepertoryAmount+(" + rs.getString("importamount") + 
            		"),RepertoryNum=RepertoryNum+(" + rs.getString("importNum") +")  WHERE GoodsID='" +rs.getString("goodsid")+ "' and storeid="+rs.getString("storeid");
            	else
            		strSQL="insert into TabGoodsRepertory (goodsid,RepertoryAmount,RepertoryNum,storeid,avgprice) values('"+rs.getString("goodsid")+
            		"',"+rs.getString("importamount")+","+rs.getString("importNum")+","+rs.getString("storeid")+","+avgprice+")";
            	nRet=stmt1.executeUpdate(strSQL);
            	if(nRet != 1)
                    throw new Exception("更新库存表错误");
            	
                //更新加权平均价
            	strSQL="update TabGoodsRepertory set avgprice="+avgprice+" where goodsid='"+rs.getString("goodsid")+
            	"' and storeid in (select id from TabStore where deptid="+rs.getInt("deptid")+")";
            	nRet=stmt1.executeUpdate(strSQL);
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
                if(proc != null)
                	proc.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException ex) { }
        }
        return nRet;
    }
  
    public int UpdateBillRec(ImportBillForm ibf) throws Exception
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
            strSQL="update TabGoodsImportInfo set factory="+(ibf.getFactory()>0?ibf.getFactory():"null")+
            ",fkType="+(ibf.getFkType()>0?ibf.getFkType():"null")+
            ",fpType="+(ibf.getFpType()>0?ibf.getFpType():"null")+
            ",payLimTime="+(ibf.getPayLimTime().length()>0?"'"+ibf.getPayLimTime()+"'":"null")+
            ",yunfei="+(ibf.getYunFei()>0?ibf.getYunFei():"null")+
            ",carno="+(ibf.getCarNo().length()>0?"'"+ibf.getCarNo()+"'":"null")+
            ",memo='"+ibf.getMemo()+"',ieva="+ibf.getIeva()+
            " where billid="+ibf.getBillId()+" and deptid="+ibf.getDeptid()+" and confirmFlage='0'";
            nRet = stmt.executeUpdate(strSQL);
        	if(nRet != 1)
        		throw new Exception("更新入库单失败");
            
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
            strSQL = "SELECT DISTINCT top 500 BillID FROM TabGoodsImportInfo where (confirmFlage='1')";
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
	@SuppressWarnings("unchecked")
	public List getBillIdListbyFapiao(int deptid,int kind)
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
            if(kind==0)
            	strSQL = "SELECT DISTINCT top 500 BillID FROM TabGoodsImportInfo where (confirmFlage='1') and fpType>1";
            else if(kind==1)
            	strSQL = "SELECT DISTINCT top 500 BillID FROM TabGoodsExportInfo where (confirmFlage='1') and fpType>1";
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
	@SuppressWarnings("unchecked")
	public List getBillIdList(int deptid,String person)
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
            strSQL = "SELECT DISTINCT top 500 BillID FROM TabGoodsImportInfo where (confirmFlage='1') and createPerson='"+person+"' and paymenttime is null";
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
    
	@SuppressWarnings("unchecked")
	public List getSendPersonsList()
    {
        List sendPersonsList;
        sendPersonsList = new ArrayList();
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
            strSQL = "SELECT DISTINCT SendPersons FROM TabGoodsImportInfo Order by SendPersons";
            String curSendPersons;
            for(rs = stmt.executeQuery(strSQL); rs.next(); sendPersonsList.add(curSendPersons))
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
        return sendPersonsList;
    }

    
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
            strSQL = "SELECT DISTINCT createperson FROM TabGoodsImportInfo";
            if(deptid>0)
            	strSQL=strSQL+" where deptid="+deptid;
            strSQL=strSQL+" Order by createperson";
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
	
	@SuppressWarnings("unchecked")
	public List getKaiPiaoRenList(int deptid,int kind)
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
            if(kind==0)
            	strSQL = "SELECT DISTINCT kaipiaoren FROM TabGoodsImportInfo where kaipiaoren is not null";
            else if(kind==1)
            	strSQL = "SELECT DISTINCT kaipiaoren FROM TabGoodsExportInfo where kaipiaoren is not null";
            if(deptid>0)
            	strSQL=strSQL+" and deptid="+deptid;
            strSQL=strSQL+" Order by kaipiaoren";
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
            strSQL = "SELECT DISTINCT factory,b.name FROM TabGoodsImportInfo a inner join TabFactory b on a.factory=b.id Order by b.name";
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
            strSQL = "SELECT DISTINCT factory,b.name FROM TabGoodsImportInfo a inner join TabFactory b on "+
            "a.factory=b.id ";
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
	@SuppressWarnings("unchecked")
	public List getFactListbyFaPiao(String zhujima,int kind)
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
            String tabname="";
            if(kind==0)
            	tabname="import";
            else
            	tabname="export";
            strSQL = "SELECT DISTINCT factory,b.name FROM TabGoods"+tabname+"Info a inner join TabFactory b on "+
            "a.factory=b.id where fptype>1";
            if(zhujima.length()>0)
            	strSQL=strSQL+" and b.zhujima like '%"+zhujima+"%'";
            strSQL=strSQL+" Order by b.name";
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
	public ResultsetList getBillListByRule(ImportBillForm ibf,String importTime1,String importTime2,String paymentTime1,String paymentTime2,int curPage,String ifpay,String orderby,String kind) throws Exception
    {
        ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
     
        strSQL = " SELECT a.*,b.name,c.shortname FROM TabGoodsImportInfo a inner join TabFactory b on a.factory=b.id inner join TabDepartInfo c on a.deptid=c.id where (confirmFlage='1' or confirmFlage='3')";
        strSQL1 = " SELECT sum(totalnum) as allnum,sum(totalprice),sum(realmoney),sum(totalprice-realmoney) as allprice FROM TabGoodsImportInfo a where (confirmFlage='1')";
        String whereStr = "";
        if(ibf.getBillId()!= 0)
            whereStr = whereStr + " and BillID=" + ibf.getBillId();
        if(!ibf.getCreatePerson().equals(""))
            whereStr = whereStr + " and createPerson='" + ibf.getCreatePerson()+"'";
        if(ibf.getFactory()!=0)
            whereStr = whereStr + " and factory=" + ibf.getFactory();
        if(!importTime1.equals(""))
        	whereStr = whereStr + " and ImportTime >= '" + importTime1 + "'";
        if(!importTime2.equals(""))
        	whereStr = whereStr + " and ImportTime <='" + importTime2 + "'";
        if(!paymentTime1.equals(""))
        	whereStr = whereStr + " and paymentTime >= '" + paymentTime1 + "'";
        if(!paymentTime2.equals(""))
        	whereStr = whereStr + " and paymentTime <='" + paymentTime2 + "'";
        if(ifpay.equals("1"))
        	whereStr = whereStr + " and paymenttime is not null";
        if(ifpay.equals("0"))
        	whereStr = whereStr + " and paymenttime is null";
        if(ibf.getFkType()!= 0)
            whereStr = whereStr + " and fkType=" + ibf.getFkType();
        if(ibf.getDeptid()>0)
            whereStr = whereStr + " and a.deptid=" + ibf.getDeptid();
        if(kind.equals("1"))
            whereStr = whereStr + " and a.kind=1";
        else if(kind.equals("-1"))
            whereStr = whereStr + " and a.kind=-1";
        else if(kind.equals("2"))
            whereStr = whereStr + " and a.confirmflage='3'";
        strSQL=strSQL+whereStr;
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
            		ImportBillForm gibi = new ImportBillForm();
                    gibi.setBillId(rs.getInt("billid"));
                    gibi.setCreatePerson(rs.getString("createPerson"));
                    gibi.setTotalNum(rs.getDouble("totalnum"));
                    gibi.setTotalPrice(rs.getDouble("totalprice"));
                    gibi.setImportTime(oConvert.FormDateTimeShort(rs.getTimestamp("importTime")));
                    if(rs.getString("paymentTime")!=null)
                    	gibi.setPaymentTime(oConvert.FormDateTimeShort(rs.getTimestamp("paymentTime")));
                    else
                    	gibi.setPaymentTime("");
                    gibi.setFactory(rs.getInt("factory"));
                    gibi.setFactoryName((rs.getString("name").length()>18?rs.getString("name").substring(0,17)+"..":rs.getString("name")));
                    gibi.setRealMoney(rs.getDouble("realmoney"));
                    gibi.setYunFei(rs.getFloat("yunfei"));
                    gibi.setDeptid(rs.getInt("deptid"));
                    gibi.setShortname(rs.getString("shortname"));
                    gibi.setConfirmFlage(rs.getString("confirmflage"));
                    gibi.setKind(rs.getInt("kind"));
                    gibi.setIeva(rs.getInt("ieva"));
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
	@SuppressWarnings("unchecked")
	public ResultsetList getBillListByFaPiao(ImportBillForm ibf,String importTime1,String importTime2,String paymentTime1,String paymentTime2,int curPage,String ifpay,String orderby,int kind) throws Exception
    {
        ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
        String oper="";
        if(kind==0)
        	oper="import";
        else if(kind==1)
        	oper="export";
        
        strSQL = " SELECT a.*,b.name,c.shortname,d.name as fpname FROM TabGoods"+oper+"Info a inner join TabFactory b on "+
        "a.factory=b.id inner join TabDepartInfo c on a.deptid=c.id inner join TabFaPiao d on a.fptype=d.id where (confirmFlage='1') and a.fptype>1";
        strSQL1 = " SELECT sum(totalnum) as allnum,sum(totalprice),sum(realmoney),sum(totalprice-realmoney) as allprice FROM TabGoods"+oper+"Info a where (confirmFlage='1') and a.fptype>1";
        String whereStr = "";
        if(kind==0)
        	whereStr = whereStr+" and ieva=0";
        if(ibf.getBillId()!= 0)
            whereStr = whereStr + " and BillID=" + ibf.getBillId();
        if(!ibf.getCreatePerson().equals(""))
            whereStr = whereStr + " and kaipiaoren='" + ibf.getCreatePerson()+"'";
        if(ibf.getFactory()!=0)
            whereStr = whereStr + " and factory=" + ibf.getFactory();
        if(!importTime1.equals(""))
        	whereStr = whereStr + " and "+oper+"Time >= '" + importTime1 + "'";
        if(!importTime2.equals(""))
        	whereStr = whereStr + " and "+oper+"Time <='" + importTime2 + "'";
        if(!paymentTime1.equals(""))
        	whereStr = whereStr + " and payfpTime >= '" + paymentTime1 + "'";
        if(!paymentTime2.equals(""))
        	whereStr = whereStr + " and payfpTime <='" + paymentTime2 + "'";
        if(ifpay.equals("1"))
        	whereStr = whereStr + " and payfpTime is not null";
        if(ifpay.equals("0"))
        	whereStr = whereStr + " and payfpTime is null";
        if(ibf.getFpType()!= 0)
            whereStr = whereStr + " and fpType=" + ibf.getFpType();
        if(ibf.getDeptid()>0)
            whereStr = whereStr + " and a.deptid=" + ibf.getDeptid();
        
        strSQL=strSQL+whereStr;
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
            		ImportBillForm gibi = new ImportBillForm();
                    gibi.setBillId(rs.getInt("billid"));
                    gibi.setCreatePerson(oConvert.getString(rs.getString("kaipiaoren"),""));
                    gibi.setTotalNum(rs.getDouble("totalnum"));
                    gibi.setTotalPrice(rs.getDouble("totalprice"));
                    gibi.setImportTime(oConvert.FormDateShort(rs.getTimestamp(oper+"Time")));
                    if(rs.getString("paymentTime")!=null)
                    	gibi.setPaymentTime(oConvert.FormDateShort(rs.getTimestamp("paymentTime")));
                    else
                    	gibi.setPaymentTime("");
                    if(rs.getString("payfpTime")!=null)
                    	gibi.setPayFpTime(oConvert.FormDateTimeShort(rs.getTimestamp("payfpTime")));
                    else
                    	gibi.setPayFpTime("");
                    gibi.setFactory(rs.getInt("factory"));
                    gibi.setFpType(rs.getInt("fptype"));
                    gibi.setFpName(rs.getString("fpname"));
                    gibi.setFactoryName(rs.getString("name"));
                    gibi.setRealMoney(rs.getDouble("realmoney"));
                    gibi.setDeptid(rs.getInt("deptid"));
                    gibi.setShortname(rs.getString("shortname"));
                    gibi.setConfirmFlage(rs.getString("confirmflage"));
                    gibi.setKind(rs.getInt("kind"));
                    ibsri.rslist.add(gibi);
                    if(!rs.next())
	            		break;
                }

            }
            rs.close();
            rs = stmt.executeQuery(strSQL1);
            if(rs.next())
            {
            	ibsri.allsumnum=oConvert.getRound(rs.getDouble(1),4);
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
    public ImportBillForm getImportBillById(int billId,int deptid) throws Exception
    {
    	ImportBillForm ibf=new ImportBillForm();
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
            strSQL = "SELECT a.*,b.name as fname,c.name as pname,d.name as kname,e.shortname,e.name as gongsi FROM TabGoodsImportInfo a left join TabFactory b "+
            "on a.factory=b.id left join TabFaPiao c on a.fptype=c.id left join TabfukuanType d"+
            " on a.fktype=d.id inner join TabDepartInfo e on a.deptid=e.id WHERE a.BillID=" + billId+" and a.deptid="+deptid;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	ibf=new ImportBillForm();
            	ibf.setBillId(billId);
            	ibf.setCreatePerson(rs.getString("createperson"));
            	ibf.setFactory(rs.getInt("factory"));
            	ibf.setFactoryName(rs.getString("fname"));
            	ibf.setImportTime(oConvert.FormDateShort(rs.getTimestamp("ImportTime")));
            	if(rs.getTimestamp("PaymentTime")!=null)
            		ibf.setPaymentTime(oConvert.FormDateShort(rs.getTimestamp("PaymentTime")));
            	ibf.setTotalNum(rs.getDouble("totalnum"));
            	ibf.setTotalPrice(rs.getDouble("totalprice"));
            	ibf.setFkType(rs.getInt("fkType"));
            	ibf.setFpType(rs.getInt("fpType"));
            	ibf.setFkName(rs.getString("kname"));
            	ibf.setFpName(rs.getString("pname"));
            	ibf.setYunFei(rs.getFloat("yunfei"));
            	ibf.setCarNo(oConvert.getString(rs.getString("carNo"),""));
            	if(rs.getTimestamp("payLimTime")!=null)
            		ibf.setPayLimTime(oConvert.FormDateShort(rs.getTimestamp("payLimTime")));
            	if(rs.getTimestamp("PayShipTime")!=null)
            		ibf.setPayShipTime(oConvert.FormDateShort(rs.getTimestamp("PayShipTime")));
            	if(rs.getTimestamp("PayFpTime")!=null)
            		ibf.setPayFpTime(oConvert.FormDateShort(rs.getTimestamp("PayFpTime")));
            	ibf.setRealMoney(rs.getDouble("realmoney"));
            	ibf.setConfirmFlage(rs.getString("confirmFlage"));
            	ibf.setDeptid(rs.getInt("deptid"));
            	ibf.setShortname(rs.getString("shortname"));
            	ibf.setGongsi(rs.getString("gongsi"));
            	ibf.setMemo(oConvert.getString(rs.getString("memo"),""));
            	ibf.setKind(rs.getInt("kind"));
            	ibf.setConfirmFlage(rs.getString("confirmflage"));
            	ibf.setIeva(rs.getInt("ieva"));
            	ibf.setIyunfei(rs.getBoolean("iyunfei"));
            	
            }
            rs.close();
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
            strSQL = "UPDATE TabGoodsImportInfo SET PaymentTime = getdate() WHERE BillID = " + billId;
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
    
    public int UpdateEvaPriceBill(int billid,int deptid,String operPerson) throws Exception
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        Statement stmt1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        String strSQL = null;
        double totalPrice=0;
        double oldtotalPrice=0;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt1 = conn.createStatement();
            //判断总金额是否大于零
            strSQL = "select sum(importamount*importUnitprice),sum(importamount*evaprice) as totalPrice from"+
            " TabGoodsImportGoods WHERE BillID=" + billid+" and deptid="+deptid;
            rs=stmt.executeQuery(strSQL);
            rs.next();
            if(rs.getFloat(1)==0 || rs.getFloat(2)==0)
            	throw new Exception("总金额不能为零");
            else
            {
            	oldtotalPrice=oConvert.getRound(rs.getDouble(1),2);
            	totalPrice=oConvert.getRound(rs.getDouble(2),2);
            }
            rs.close();
            //更新库存成本价
            strSQL = "select * from TabGoodsImportGoods WHERE BillID = " + billid+" and deptid="+deptid;
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	double oldprice=rs.getDouble("importUnitPrice");
            	double newprice=rs.getDouble("evaPrice");
            	double jine=(oldprice-newprice)*rs.getDouble("importamount");
            	strSQL="select sum(RepertoryAmount),sum(RepertoryAmount*avgprice) from TabGoodsRepertory where goodsid='"+rs.getString("goodsid")+
                "' and storeid in (select id from TabStore where deptid="+rs.getInt("deptid")+")";
            	rs1=stmt1.executeQuery(strSQL);
            	rs1.next();
            	if(jine==0)
            	{
            		
            	}
            	else if(rs1.getDouble(2)<jine)
            		throw new Exception("库存金额"+rs1.getDouble(2)+"小于估价差"+jine+"，无法更新库存成本价\\n请将估价差设为零，在其他收入中增加收入单");
            	else if(rs1.getDouble(1)<=0)
            		throw new Exception(rs.getString("goodsid")+"库存数量不足，无法更新库存成本价\\n请将估价差设为零，在其他收入中增加收入单");
            	else
            	{
            		//计算加权平均价
            		double avgprice=oConvert.getRound((rs1.getDouble(2)-jine)/rs1.getDouble(1),4);
            		//更新加权平均价
                	strSQL="update TabGoodsRepertory set avgprice="+avgprice+" where goodsid='"+rs.getString("goodsid")+
                	"' and storeid in (select id from TabStore where deptid="+rs.getInt("deptid")+")";
                	nRet=stmt1.executeUpdate(strSQL);
            	}
            	strSQL =" update TabGoodsImportGoods set importUnitPrice="+newprice+",evaPrice="+oldprice+" where id="+rs.getInt("id");
            	nRet = stmt1.executeUpdate(strSQL);
                if(nRet != 1)
                    throw new Exception("更新入库单明细失败");
                
            }
            rs.close();
            //更新实际金额
            boolean ifyunfei=false;
            double yunfei=0;
            strSQL =" select * from TabGoodsImportInfo where billid="+billid+" and deptid="+deptid;
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	ifyunfei=rs.getBoolean("iyunfei");
            	yunfei=rs.getDouble("yunfei");
            }
            if(ifyunfei)
            	totalPrice=totalPrice-yunfei;
            strSQL =" update TabGoodsImportInfo set totalprice="+totalPrice+",ieva=2 where billid="+billid+
            " and deptid="+deptid;
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
                throw new Exception("更新入库单失败");
            //插入估计记录
            strSQL ="insert into TabEvaPriceHandle (imbillid,deptid,oldTotalPrice,evaTotalPrice,operPerson) values("+billid+","+
            deptid+","+oldtotalPrice+","+totalPrice+",'"+operPerson+"')";
            nRet = stmt.executeUpdate(strSQL);
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
    //进货单撤销
    public int CancelImportBill(int oldbillid,int deptid) throws Exception
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
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt1 = conn.createStatement();
            strSQL="update TabGoodsImportInfo set confirmflage='3' where  billid="+oldbillid+" and ConfirmFlage='1' and deptid="+deptid;
            nRet=stmt.executeUpdate(strSQL);
            if(nRet != 1)
                throw new Exception("撤销进货单失败");
            strSQL=" update TabGoodsImportGoods set confirmflage='3' where billid="+oldbillid+" and ConfirmFlage='1' and deptid="+deptid;
            nRet=stmt.executeUpdate(strSQL);
            if(nRet<1)
                throw new Exception("撤销进货单失败");
            strSQL="select * from TabGoodsImportInfo where billid="+oldbillid+" and deptid="+deptid;
            rs=stmt.executeQuery(strSQL);
            rs.next();
            int iyunfei=rs.getInt("iyunfei");
            double yfunit=0;
            if(iyunfei==1)
            	yfunit=oConvert.getRound(rs.getDouble("yunfei")/rs.getDouble("totalnum"),4);
            //更新库存
            GoodsInfo gi=new GoodsInfo();
            strSQL = "select * from TabGoodsImportGoods WHERE BillID = " + oldbillid+" and deptid="+deptid;
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	double amount=-rs.getDouble("importAmount");
            	double kucun=gi.getRepertoryAmount(rs.getString("goodsid"),rs.getInt("storeid"));
                if(amount<0 && kucun+amount<0)
                	throw new Exception("编号为"+rs.getString("goodsid")+"的产品库存不足");
                //计算加权平均价
                double avgprice=gi.CountAvgPrice(rs.getString("goodsid"), rs.getInt("deptid"), amount, rs.getDouble("importUnitPrice")+yfunit,conn);
            	
            	//更新库存
            	strSQL="select * from TabGoodsRepertory where goodsid='"+rs.getString("goodsid")+"' and storeid="+rs.getString("storeid");
            	rs1=stmt1.executeQuery(strSQL);
            	if(rs1.next())
            		strSQL = "UPDATE TabGoodsRepertory SET RepertoryAmount=RepertoryAmount+(" +amount + 
            		") WHERE GoodsID='" +rs.getString("goodsid")+ "' and storeid="+rs.getString("storeid");
            	else
            		strSQL="insert into TabGoodsRepertory (goodsid,RepertoryAmount,storeid,avgprice) values('"+rs.getString("goodsid")+
            		"',"+amount+","+rs.getString("storeid")+","+avgprice+")";
            	nRet=stmt1.executeUpdate(strSQL);
            	if(nRet != 1)
                    throw new Exception("更新库存表错误");
            	
                //更新加权平均价
            	strSQL="update TabGoodsRepertory set avgprice="+avgprice+" where goodsid='"+rs.getString("goodsid")+
            	"' and storeid in (select id from TabStore where deptid="+rs.getInt("deptid")+")";
            	nRet=stmt1.executeUpdate(strSQL);
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
    
    @SuppressWarnings("unchecked")
	public List getEvaBillList(int deptid)
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
            strSQL = "SELECT DISTINCT BillID FROM TabGoodsImportInfo where deptid="+deptid+" and ieva=1 and confirmflage='1' Order by BillID desc";
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
}
