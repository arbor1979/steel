package mediastore.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import mediastore.common.DBConnection;
import mediastore.user.TruckManager;
import mediastore.util.oConvert;
import mediastore.web.form.AccountForm;
import mediastore.web.form.ChongZhiForm;
import mediastore.web.form.ExportBillForm;
import mediastore.web.form.FuKuanForm;
import mediastore.web.form.ImportBillForm;
import mediastore.web.form.OtherToPayForm;
import mediastore.web.form.PayHuoKuanForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.form.ShouKuanBillForm;
import mediastore.web.form.TruckInfoForm;
import mediastore.web.global.Globals;

public class CaiWu {
	public CaiWu()
	{
	
	}
	//取得付款类型
	@SuppressWarnings("unchecked")
	public List getFuKuanTypeList(int kind)
    {
        List HYList;
        HYList = new ArrayList();
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
            strSQL = "SELECT * from TabFuKuanType";
            FuKuanForm tmp;
            for(rs = stmt.executeQuery(strSQL); rs.next(); HYList.add(tmp))
            {
                tmp = new FuKuanForm();
                tmp.setId(rs.getInt("id"));
                if(rs.getString("name").indexOf("预付")>=0 && kind==1)
                	tmp.setName(rs.getString("name").replaceAll("预付", "预收"));
                else
                	tmp.setName(rs.getString("name"));
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
        return HYList;
    }
	//	取得发票类型
	@SuppressWarnings("unchecked")
	public List getFPTypeList()
    {
        List HYList;
        HYList = new ArrayList();
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
            strSQL = "SELECT * from TabFaPiao";
            FuKuanForm tmp;
            for(rs = stmt.executeQuery(strSQL); rs.next(); HYList.add(tmp))
            {
                tmp = new FuKuanForm();
                tmp.setId(rs.getInt("id"));
                tmp.setName(rs.getString("name"));
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
        return HYList;
    }
	@SuppressWarnings("unchecked")
	public List getFPTypeList(int noshouju)
    {
        List HYList;
        HYList = new ArrayList();
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
            strSQL = "SELECT * from TabFaPiao";
            if(noshouju==1)
            	strSQL=strSQL+" where id>1";
            FuKuanForm tmp;
            for(rs = stmt.executeQuery(strSQL); rs.next(); HYList.add(tmp))
            {
                tmp = new FuKuanForm();
                tmp.setId(rs.getInt("id"));
                tmp.setName(rs.getString("name"));
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
        return HYList;
    }
	//	取得企业欠款汇总
	@SuppressWarnings("unchecked")
	public ResultsetList getYingShouGroupByFac(PayHuoKuanForm phf,int curPage) throws Exception
    {
		ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        Statement stmt1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        String strSQL = null;
        String strSQL1 = null;
        String whereStr="";
        strSQL = "SELECT SUM(a.Totalnum) AS allnum, SUM(a.TotalPrice+a.totaljiagong) AS allprice,count(*) as allcount,a.factory,a.deptid,b.name,c.shortname,d.preShou FROM "+
        "TabGoodsExportInfo a INNER JOIN TabFactory b ON a.factory = b.id inner join TabDepartInfo c on a.deptid=c.id left join TabPrePay d on "+
        " (a.factory=d.factory and a.deptid=d.deptid) WHERE (a.PaymentTime IS NULL) AND (a.ConfirmFlage = '1') ";
        strSQL1 = "SELECT SUM(a.Totalnum) AS allnum, SUM(a.TotalPrice+a.totaljiagong) AS allprice FROM "+
        "TabGoodsExportInfo a INNER JOIN TabFactory b ON a.factory = b.id inner join TabDepartInfo c on a.deptid=c.id left join TabPrePay d on "+
        " (a.factory=d.factory and a.deptid=d.deptid) WHERE (a.PaymentTime IS NULL) AND (a.ConfirmFlage = '1') ";
        if(phf.getDeptid()>0)
        	whereStr=whereStr+" and a.deptid="+phf.getDeptid();
        if(phf.getZhujima().length()>0)
        	whereStr=whereStr+" and (b.name like '%"+phf.getZhujima()+"%' or b.zhujima like '%"+phf.getZhujima()+"%')";
        strSQL=strSQL+whereStr;
        strSQL1=strSQL1+whereStr;
        strSQL=strSQL+" GROUP BY a.factory,b.name,d.preShou,a.deptid,c.shortname";
        if(phf.getOrderby().length()>0)
        	strSQL=strSQL+" order by "+phf.getOrderby();
        
        
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt1 = conn.createStatement();
            rs = stmt.executeQuery(strSQL);
            ExportBillForm gibi;
            double totalPrepay=0;
            double totalown=0;
            while(rs.next())
            {
            	strSQL = " SELECT preShou FROM TabPrePay where factory="+rs.getInt("factory")+" and deptid="+rs.getInt("deptid");
            	rs1 = stmt1.executeQuery(strSQL);
            	if(rs1.next())
            	{
            		totalPrepay=totalPrepay+rs1.getDouble("preShou");
            		if(rs.getDouble("allprice")>0)
            		{
            			if(rs1.getDouble("preShou")<rs.getDouble("allprice"))
            				totalown=totalown+(rs.getDouble("allprice")-rs1.getDouble("preShou"));
            		}
            		else
            			totalown=totalown+rs.getDouble("allprice");
            	}
            	else
            		totalown=totalown+rs.getDouble("allprice");
            }
            if(rs.last())
            {
            	int RowCount=rs.getRow();
            	ibsri.allnum=RowCount;
            	ibsri.curpage=curPage;
            	ibsri.pagesize=10000;
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
            		gibi= new ExportBillForm();
                    gibi.setTotalNum(rs.getDouble("allnum"));
                    gibi.setTotalPrice(rs.getDouble("allprice"));
                    gibi.setFactory(rs.getInt("factory"));
                    gibi.setFactoryName(rs.getString("name"));
                    gibi.setPrepay(oConvert.getDouble(rs.getString("preShou"),0));
                    gibi.setAllcount(rs.getInt("allcount"));
                    gibi.setShortname(rs.getString("shortname"));
                    gibi.setDeptid(rs.getInt("deptid"));
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
            	
            }
            rs.close();
            ibsri.realmoney=oConvert.getRound(totalPrepay,2);
            ibsri.oddmoney=oConvert.getRound(totalown,2);
            
        }
        catch(Exception e) {throw e;}
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
        return ibsri;
    }
//	取得供应商欠款汇总
	@SuppressWarnings("unchecked")
	public ResultsetList getYingFuGroupByFac(PayHuoKuanForm phf,int curPage) throws Exception
    {
		ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
        String whereStr = "";
        strSQL = "SELECT SUM(a.Totalnum) AS allnum, SUM(a.TotalPrice) AS allprice,count(*) as allcount,a.factory, a.deptid,b.name,c.shortname,d.prePay FROM "+
        "TabGoodsImportInfo a INNER JOIN TabFactory b ON a.factory = b.id inner join TabDepartInfo c on a.deptid=c.id left join TabPrePay d on"+
        " (a.factory=d.factory and a.deptid=d.deptid) WHERE (a.PaymentTime IS NULL) AND (a.ConfirmFlage = '1') ";
        
        strSQL1 = "SELECT SUM(a.Totalnum) AS allnum, SUM(a.TotalPrice) AS allprice FROM "+
        "TabGoodsImportInfo a INNER JOIN TabFactory b ON a.factory = b.id inner join TabDepartInfo c on a.deptid=c.id left join TabPrePay d on "+
        " (a.factory=d.factory and a.deptid=d.deptid) WHERE (a.PaymentTime IS NULL) AND (a.ConfirmFlage = '1') ";
        if(phf.getDeptid()>0)
        	whereStr=whereStr+" and a.deptid="+phf.getDeptid();
        if(phf.getZhujima().length()>0)
        	whereStr=whereStr+" and (b.name like '%"+phf.getZhujima()+"%' or b.zhujima like '%"+phf.getZhujima()+"%')";
        strSQL=strSQL+whereStr;
        strSQL1=strSQL1+whereStr;
        strSQL=strSQL+" GROUP BY a.factory,b.name,d.prePay,a.deptid,c.shortname";
        if(phf.getOrderby().length()>0)
        	strSQL=strSQL+" order by "+phf.getOrderby();
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(strSQL);
            ImportBillForm gibi;
            if(rs.last())
            {
            	int RowCount=rs.getRow();
            	ibsri.allnum=RowCount;
            	ibsri.curpage=curPage;
            	ibsri.pagesize=10000;
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
            		gibi= new ImportBillForm();
                    gibi.setTotalNum(rs.getDouble("allnum"));
                    gibi.setTotalPrice(rs.getDouble("allprice"));
                    gibi.setFactory(rs.getInt("factory"));
                    gibi.setFactoryName(rs.getString("name"));
                    gibi.setPrepay1(oConvert.getDouble(rs.getString("prePay"),0));
                    gibi.setAllcount(rs.getInt("allcount"));
                    gibi.setShortname(rs.getString("shortname"));
                    gibi.setDeptid(rs.getInt("deptid"));
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
            }
            rs.close();
            
            double totalPrepay=0;
            double totalown=0;
            for(int i=0;i<ibsri.rslist.size();i++)
            {
            	gibi=(ImportBillForm)ibsri.rslist.get(i);
            	strSQL1 = " SELECT prePay FROM TabPrePay where factory="+gibi.getFactory()+" and deptid="+gibi.getDeptid();
            	rs = stmt.executeQuery(strSQL1);
            	if(rs.next())
            	{
            		totalPrepay=totalPrepay+rs.getDouble("prePay");
            		if(gibi.getTotalPrice()>0)
            		{
            			if(rs.getDouble("prePay")<gibi.getTotalPrice())
            				totalown=totalown+(gibi.getTotalPrice()-rs.getDouble("prePay"));
            		}
            		else
            			totalown=totalown+gibi.getTotalPrice();
            	}
            	else
            		totalown=totalown+gibi.getTotalPrice();
                
            }
            ibsri.realmoney=oConvert.getRound(totalPrepay,2);
            ibsri.oddmoney=oConvert.getRound(totalown,2);
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
	
	
	//	取得应收
	@SuppressWarnings("unchecked")
	public ResultsetList getYingShouList(PayHuoKuanForm phf) throws Exception
    {
		ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Statement stmt1 = null;
        ResultSet rs1 = null;
        String strSQL = null;
        String strSQL1 = null;
        double totalPrepay=0;
        double totalown=0;
        strSQL = " SELECT a.*,b.name,d.preShou,c.name as fkname FROM TabGoodsExportInfo a inner join TabFactory b on a.factory=b.id "+
        " inner join TabFuKuanType c on a.fkType=c.id left join TabPrePay d on (a.factory=d.factory and a.deptid=d.deptid) where paymenttime is null and (confirmFlage='1')";
        strSQL1 = " SELECT sum(totalnum) as allnum,sum(totalprice+totaljiagong) as allprice FROM "+
        "TabGoodsExportInfo a inner join TabFactory b on a.factory=b.id where paymenttime is null and (confirmFlage='1')";
        String whereStr = "";
        if(phf.getFactory()>0)
        	whereStr=whereStr+" and a.factory="+phf.getFactory();
        if(phf.getDeptid()>0)
        	whereStr =whereStr+ " and a.deptid="+phf.getDeptid();
        if(phf.getZhujima().length()>0)
        	whereStr=whereStr+" and (b.name like '%"+phf.getZhujima()+"%' or b.zhujima like '%"+phf.getZhujima()+"%')";
        strSQL = strSQL +whereStr+ " Order by exporttime desc";
        strSQL1=strSQL1+whereStr;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(strSQL);
            double prepay=0;
            while(rs.next())
            {
            	ExportBillForm gibi = new ExportBillForm();
                gibi.setBillId(rs.getInt("billid"));
                gibi.setSalesPerson(rs.getString("salesPerson"));
                gibi.setTotalNum(rs.getDouble("totalnum"));
                gibi.setTotalPrice(rs.getDouble("totalprice")+rs.getDouble("totaljiagong"));
                gibi.setExportTime(oConvert.FormDateTimeShort(rs.getTimestamp("exportTime")));
                gibi.setPayLimTime(oConvert.FormDateShort(rs.getTimestamp("paylimtime")));
                gibi.setFactory(rs.getInt("factory"));
                gibi.setFactoryName(rs.getString("name"));
                gibi.setFkType(rs.getInt("fkType"));
                gibi.setFkName(rs.getString("fkname"));
                gibi.setConfirmFlage(rs.getString("confirmflage"));
                prepay=oConvert.getDouble(rs.getString("preShou"),0);
                gibi.setPrepay(prepay);
                gibi.setDeptid(rs.getInt("deptid"));
                gibi.setKind(rs.getInt("kind"));
                ibsri.rslist.add(gibi);
            }
            rs.close();
            if(phf.getFactory()==0)
            {
                strSQL = "SELECT factory,a.deptid,SUM(a.TotalPrice+a.totaljiagong) as allprice FROM TabGoodsExportInfo a inner join TabFactory b on a.factory=b.id"+
                " WHERE (a.PaymentTime IS NULL) AND (a.ConfirmFlage = '1') ";
                if(phf.getDeptid()>0)
                	strSQL=strSQL+" and a.deptid="+phf.getDeptid();
                if(phf.getZhujima().length()>0)
                	strSQL=strSQL+" and (b.name like '%"+phf.getZhujima()+"%' or b.zhujima like '%"+phf.getZhujima()+"%')";
                strSQL=strSQL+" group by factory,a.deptid";
                rs=stmt.executeQuery(strSQL);
                stmt1 = conn.createStatement();

                while(rs.next())
                {
                	strSQL = " SELECT preShou FROM TabPrePay where factory="+rs.getInt("factory")+" and deptid="+rs.getInt("deptid");
                	rs1 = stmt1.executeQuery(strSQL);
                	if(rs1.next())
                	{
                		totalPrepay=totalPrepay+rs1.getDouble("preShou");
                		if(rs.getDouble("allprice")>0)
                		{
                			if(rs1.getDouble("preShou")<rs.getDouble("allprice"))
                				totalown=totalown+(rs.getDouble("allprice")-rs1.getDouble("preShou"));
                		}
                		else
                			totalown=totalown+rs.getDouble("allprice");
                	}
                	else
                		totalown=totalown+rs.getDouble("allprice");
                }
            }
            
            rs = stmt.executeQuery(strSQL1);
            if(rs.next())
            {
            	ibsri.allsumnum=oConvert.getRound(rs.getDouble(1),4);
            	ibsri.allmoney=oConvert.getRound(rs.getDouble(2),2);
            }
            if(phf.getFactory()>0)
            {
                totalPrepay=prepay;
                totalown=ibsri.allmoney-prepay;
            }
            ibsri.realmoney=oConvert.getRound(totalPrepay,2);
            ibsri.oddmoney=oConvert.getRound(totalown,2);
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
                if(rs1 != null)
                    rs1.close();
                if(stmt1 != null)
                    stmt1.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return ibsri;
    }
	//	取得充值记录
	@SuppressWarnings("unchecked")
	public ResultsetList getChongZhiList(int factory,int deptid,int kind,int curPage,String importTime1,String importTime2) throws Exception
    {
		ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
        ChongZhiForm gibi=null;
        
        String whereStr = " and a.factory="+factory+" and a.deptid="+deptid;
        
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //未付款额
            if(kind==1)
            	strSQL = " SELECT sum(totalprice) as allprice FROM "+
            	"TabGoodsExportInfo a where paymenttime is null and confirmFlage='1'"+whereStr;
            else
            	strSQL = " SELECT sum(totalprice) as allprice FROM "+
            	"TabGoodsImportInfo a where paymenttime is null and confirmFlage='1'"+whereStr;
            rs = stmt.executeQuery(strSQL);
            rs.next();
            ibsri.allmoney=oConvert.getRound(rs.getDouble(1),2);
            strSQL = " SELECT * FROM TabPrePay a where 1=1"+whereStr;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	if(kind==1)
            		ibsri.realmoney=oConvert.getRound(rs.getDouble("preShou"),2);
            	else
            		ibsri.realmoney=oConvert.getRound(rs.getDouble("prePay"),2);
            }
            else
            	ibsri.realmoney=0;
            //充值记录
            strSQL = "SELECT a.*,b.name,c.name as fkname FROM accessPrePay a inner join TabFactory b on a.factory=b.id "+
            " left join TabFuKuanType c on a.fkType=c.id where a.kind="+kind;
            
            //总充值额
            strSQL1 = " SELECT sum(jine) FROM accessPrePay a inner join TabFactory b on a.factory=b.id "+
            " left join TabFuKuanType c on a.fkType=c.id where (typename='预收货款' or typename='预付货款') and a.kind="+kind;
            
            if(importTime1.length()>0)
            	whereStr=whereStr+" and createtime>='"+importTime1+"'";
            if(importTime2.length()>0)
            	whereStr=whereStr+" and createtime<='"+importTime2+"'";
            strSQL = strSQL +whereStr+ " Order by a.createtime,a.billid";
            strSQL1=strSQL1+whereStr;
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
	        		gibi = new ChongZhiForm();
	                gibi.setId(rs.getInt("id"));
	                gibi.setBillid(rs.getInt("billid"));
	                gibi.setTypename(rs.getString("typename"));
	                gibi.setOperPerson(rs.getString("operPerson"));
	                gibi.setCurChuzhi(rs.getDouble("curChuZhi"));
	                if(gibi.getTypename().equals("预收货款") || gibi.getTypename().equals("预付货款"))
	                	gibi.setAddChuzhi(rs.getDouble("jine"));
	                else
	                	gibi.setPayhuokuan(rs.getDouble("jine"));
	                gibi.setCreateTime(oConvert.FormDateTimeShort(rs.getTimestamp("createTime")));
	                gibi.setFactory(rs.getInt("factory"));
	                gibi.setFactoryName(rs.getString("name"));
	                gibi.setFkType(oConvert.getInt(rs.getString("fkType"),0));
	                gibi.setFkTypeName(oConvert.getString(rs.getString("fkname"),""));
	                gibi.setDeptid(rs.getInt("deptid"));
	                gibi.setBillid(rs.getInt("billid"));
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
            	ibsri.allsumnum=oConvert.getRound(rs.getDouble(1),2);
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
	//取得运费充值记录
	@SuppressWarnings("unchecked")
	public ResultsetList getYunFeiYuFuList(int factory,int deptid,int kind,int curPage,String importTime1,String importTime2) throws Exception
    {
		ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
        ChongZhiForm gibi=null;
        TruckManager um = new TruckManager();
        TruckInfoForm fi=um.getTruckByID(factory);
        String whereStr = "and carno='"+fi.getCarno()+"' and a.deptid="+deptid;
        
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //未付款额
            strSQL = " SELECT sum(yunfei) as allprice FROM "+
            	"TabGoodsExportInfo a where payshiptime is null and confirmFlage='1'"+whereStr;
            rs = stmt.executeQuery(strSQL);
            rs.next();
            ibsri.allmoney=oConvert.getRound(rs.getDouble(1),2);
            strSQL = " SELECT sum(yunfei) as allprice FROM "+
            	"TabGoodsImportInfo a where payshiptime is null and confirmFlage='1'"+whereStr;
            rs = stmt.executeQuery(strSQL);
            rs.next();
            ibsri.allmoney=ibsri.allmoney+oConvert.getRound(rs.getDouble(1),2);
            ibsri.realmoney=fi.getAccount();
            //充值记录
            strSQL = "SELECT a.*,b.carno,c.name as fkname FROM accessPrePay a inner join TabTruck b on a.factory=b.id "+
            " left join TabFuKuanType c on a.fkType=c.id where a.kind="+kind;
            
            //总充值额
            strSQL1 = " SELECT sum(jine) FROM accessPrePay a inner join TabTruck b on a.factory=b.id "+
            " left join TabFuKuanType c on a.fkType=c.id where (typename='预付运费') and a.kind="+kind;
            
            if(importTime1.length()>0)
            	whereStr=whereStr+" and createtime>='"+importTime1+"'";
            if(importTime2.length()>0)
            	whereStr=whereStr+" and createtime<='"+importTime2+"'";
            strSQL = strSQL +whereStr+ " Order by a.createtime,a.billid";
            strSQL1=strSQL1+whereStr;
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
	        		gibi = new ChongZhiForm();
	                gibi.setId(rs.getInt("id"));
	                gibi.setBillid(rs.getInt("billid"));
	                gibi.setTypename(rs.getString("typename"));
	                gibi.setOperPerson(rs.getString("operPerson"));
	                gibi.setCurChuzhi(rs.getDouble("curChuZhi"));
	                if(gibi.getTypename().equals("预付运费"))
	                	gibi.setAddChuzhi(rs.getDouble("jine"));
	                else
	                	gibi.setPayhuokuan(rs.getDouble("jine"));
	                gibi.setCreateTime(oConvert.FormDateTimeShort(rs.getTimestamp("createTime")));
	                gibi.setFactory(rs.getInt("factory"));
	                gibi.setFactoryName(rs.getString("carno"));
	                gibi.setFkType(oConvert.getInt(rs.getString("fkType"),0));
	                gibi.setFkTypeName(oConvert.getString(rs.getString("fkname"),""));
	                gibi.setDeptid(rs.getInt("deptid"));
	                gibi.setBillid(rs.getInt("billid"));
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
            	ibsri.allsumnum=oConvert.getRound(rs.getDouble(1),2);
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
	//	取得充值汇总记录
	@SuppressWarnings("unchecked")
	public ResultsetList getChongZhiGroupList(int kind,int factory,int deptid,String importTime1,String importTime2) throws Exception
    {
		ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        double addall=0;
        double huokuanall=0;
        double chuzhiall=0;
        double beginall=0;
        ChongZhiForm gibi=null;
        List tmplist=new ArrayList();
        
        if(kind==1 || kind==-1)
        	strSQL = "SELECT b.id,b.name,sum(jine) as addchuzhi FROM AccessPrepay a inner join TabFactory b on a.factory=b.id "+
        	" where 1=1";
        else
        	strSQL = "SELECT b.id,b.carno+'('+b.driver+')' as name,sum(jine) as addchuzhi FROM AccessPrepay a inner join TabTruck b on a.factory=b.id "+
        	" where 1=1";
        if(kind==1)
        	strSQL=strSQL+" and typename='预收货款'";
        else if(kind==-1)
        	strSQL=strSQL+" and typename='预付货款'";
        else if(kind==0)
        	strSQL=strSQL+" and typename='预付运费'";
        if(factory!=0)
        	strSQL=strSQL+" and factory="+factory;
        if(deptid>-1)
        	strSQL=strSQL+" and a.deptid="+deptid;
        if(importTime1.length()>0)
        	strSQL=strSQL+" and createtime>='"+importTime1+"'";
        if(importTime2.length()>0)
        	strSQL=strSQL+" and createtime<='"+importTime2+"'";
        if(kind==1 || kind==-1)
        	strSQL=strSQL+" group by b.id,b.name order by sum(jine) desc";
        else
        	strSQL=strSQL+" group by b.id,b.carno+'('+b.driver+')' order by sum(jine) desc";
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	gibi = new ChongZhiForm();
            	gibi.setFactoryName(rs.getString("name"));
                gibi.setAddChuzhi(rs.getDouble("addchuzhi"));
                gibi.setFactory(rs.getInt("id"));
                tmplist.add(gibi);
                addall=addall+rs.getDouble("addchuzhi");
            }
            rs.close();
            for(int i=0;i<tmplist.size();i++)
            {
            	gibi=(ChongZhiForm)tmplist.get(i);
            	strSQL = "SELECT top 1 curchuzhi FROM accessPrepay where factory="+gibi.getFactory()+" and deptid="+deptid;
            	if(kind==1)
                	strSQL=strSQL+" and (typename='预收货款' or typename='货款收取')";
                else if(kind==-1)
                	strSQL=strSQL+" and (typename='预付货款' or typename='货款支付')";
                else if(kind==0)
                	strSQL=strSQL+" and (typename='预付运费' or typename='运费支付')";
	        	if(importTime1.length()>0)
	        		strSQL=strSQL+" and createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	             	strSQL=strSQL+" and createtime<='"+importTime2+"'";
	            strSQL=strSQL+" order by createtime,billid";
	            rs = stmt.executeQuery(strSQL);
	            if(rs.next())
	            {
	            	gibi.setBegChuzhi(rs.getDouble(1));
	            	beginall=beginall+rs.getDouble(1);
	            }
	            rs.close();
	            
            	strSQL = "SELECT sum(jine) as addchuzhi FROM accessPrepay where factory="+gibi.getFactory()+" and deptid="+deptid;
            	if(kind==1)
                	strSQL=strSQL+" and typename='货款收取'";
                else if(kind==-1)
                	strSQL=strSQL+" and typename='货款支付'";
                else if(kind==0)
                	strSQL=strSQL+" and typename='运费支付'";
	        	if(importTime1.length()>0)
	        		strSQL=strSQL+" and createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	             	strSQL=strSQL+" and createtime<='"+importTime2+"'";
            	rs = stmt.executeQuery(strSQL);
            	if(rs.next())
            	{
            		gibi.setPayhuokuan(rs.getDouble(1));
            		huokuanall=huokuanall+rs.getDouble(1);
            		chuzhiall=chuzhiall+(gibi.getBegChuzhi()+gibi.getAddChuzhi()-gibi.getPayhuokuan());
            	}
            	rs.close();
            	ibsri.rslist.add(gibi);
            	
            }
            
        	ibsri.allmoney=oConvert.getRound(addall,2);
        	ibsri.realmoney=oConvert.getRound(huokuanall,2);
        	ibsri.allsumnum=oConvert.getRound(chuzhiall,2);
        	ibsri.oddmoney=oConvert.getRound(beginall,2);
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
//	取得充值记录
	@SuppressWarnings("unchecked")
	public ResultsetList getChongZhiList(int factory,int kind,int fktype,String opertime1,String opertime2) throws Exception
    {
		ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
        String strSQL2 = null;
        ChongZhiForm gibi=null;
        strSQL = "SELECT a.*,b.name,c.name as fkname FROM chongzhi a inner join TabFactory b on a.factory=b.id "+
        " inner join TabFuKuanType c on a.fkType=c.id where a.kind="+kind;
        if(kind==0)
        	strSQL1 = " SELECT sum(totalprice) as allprice FROM "+
        	"TabGoodsExportInfo where paymenttime is null and confirmFlage='1'";
        else
        	strSQL1 = " SELECT sum(totalprice) as allprice FROM "+
        	"TabGoodsImportInfo where paymenttime is null and confirmFlage='1'";
        strSQL2 = " SELECT sum(addChuzhi) FROM chongzhi a inner join TabFactory b on a.factory=b.id "+
        " inner join TabFuKuanType c on a.fkType=c.id where a.kind="+kind;
        String whereStr = " and factory="+factory;
        strSQL = strSQL +whereStr;
        strSQL1=strSQL1+whereStr;
        strSQL2=strSQL2+whereStr;
        if(fktype!=0)
        	strSQL=strSQL+" and fktype="+fktype;
        if(opertime1.length()>0)
        	strSQL=strSQL+" and createtime>='"+opertime1+"'";
        if(opertime2.length()>0)
        	strSQL=strSQL+" and createtime<='"+opertime2+"'";
        strSQL=strSQL+" order by createtime desc";
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(strSQL);
            
            while(rs.next())
            {
            	gibi = new ChongZhiForm();
                gibi.setId(rs.getInt("id"));
                gibi.setOperPerson(rs.getString("operPerson"));
                gibi.setCurChuzhi(rs.getDouble("curChuZhi"));
                gibi.setAddChuzhi(rs.getDouble("addChuzhi"));
                gibi.setHuokuan(rs.getDouble("Huokuan"));
                gibi.setCreateTime(oConvert.FormDateTimeShort(rs.getTimestamp("createTime")));
                gibi.setFactory(rs.getInt("factory"));
                gibi.setFactoryName(rs.getString("name"));
                gibi.setFkType(rs.getInt("fkType"));
                gibi.setFkTypeName(rs.getString("fkname"));
                ibsri.rslist.add(gibi);
            }
            rs.close();
            rs = stmt.executeQuery(strSQL1);
            if(rs.next())
            {
            	ibsri.allmoney=oConvert.getRound(rs.getDouble(1),2);
            }
            rs.close();
            rs = stmt.executeQuery(strSQL2);
            if(rs.next())
            {
            	ibsri.allsumnum=oConvert.getRound(rs.getDouble(1),2);
            	
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
	public ResultsetList getAlreadyPayList(int factory,int kind,String opertime1,String opertime2) throws Exception
    {
		ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL1 = null;
        ChongZhiForm gibi=null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            
	        if(kind==0)
	        	strSQL1 = " SELECT billid,salesperson,realmoney,paymenttime  FROM "+
	        	"TabGoodsExportInfo where paymenttime is not null and fktype=5 and confirmFlage='1'";
	        else
	        	strSQL1 = " SELECT billid,createperson as salesperson,realmoney,paymenttime  FROM "+
	        	"TabGoodsImportInfo where paymenttime is not null and fktype=5 and confirmFlage='1'";
	        String whereStr = " and factory="+factory;
	        if(opertime1.length()>0)
        		strSQL1=strSQL1+" and paymenttime>='"+opertime1+"'";
	        if(opertime2.length()>0)
        		strSQL1=strSQL1+" and paymenttime<='"+opertime2+"'";	
	        strSQL1=strSQL1+whereStr+" order by paymenttime";
	        rs = stmt.executeQuery(strSQL1);
	        while(rs.next())
	        {
	        	
	     		gibi = new ChongZhiForm();
	     		gibi.setId(rs.getInt("billid"));
	     		gibi.setOperPerson(rs.getString("salesperson"));
	     		gibi.setPayhuokuan(rs.getDouble("realmoney"));
	     		gibi.setCreateTime(oConvert.FormDateTimeShort(rs.getTimestamp("paymenttime")));
	     		ibsri.rslist.add(gibi);
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
	//取得应付
	@SuppressWarnings("unchecked")
	public ResultsetList getYingFuList(PayHuoKuanForm phf) throws Exception
    {
        ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Statement stmt1 = null;
        ResultSet rs1 = null;
        String strSQL = null;
        String strSQL1 = null;
        double totalPrepay=0;
        double totalown=0;
        strSQL = " SELECT a.*,b.name,d.prepay,c.name as fkname FROM TabGoodsImportInfo a left join TabFactory b on a.factory=b.id "+
        "inner join TabFuKuanType c on a.fkType=c.id left join TabPrePay d on (a.factory=d.factory and a.deptid=d.deptid) where (confirmFlage='1') and paymenttime is null";
        strSQL1 = " SELECT sum(totalnum) as allnum,sum(totalprice) as allprice FROM "+
        "TabGoodsImportInfo a INNER JOIN TabFactory b ON a.factory = b.id where (confirmFlage='1') and paymenttime is null";
        String whereStr = "";
        if(phf.getFactory()>0)
        	whereStr =whereStr+ " and a.factory="+phf.getFactory();
        if(phf.getDeptid()>0)
        	whereStr =whereStr+ " and a.deptid="+phf.getDeptid();
        if(phf.getZhujima().length()>0)
        	whereStr=whereStr+" and (b.name like '%"+phf.getZhujima()+"%' or b.zhujima like '%"+phf.getZhujima()+"%')";
        strSQL = strSQL +whereStr+ " Order by importtime desc";
        strSQL1=strSQL1+whereStr;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(strSQL);
            double prepay=0;
            while(rs.next())
            {
            	ImportBillForm gibi = new ImportBillForm();
                gibi.setBillId(rs.getInt("billid"));
                gibi.setCreatePerson(rs.getString("createPerson"));
                gibi.setTotalNum(rs.getDouble("totalnum"));
                gibi.setTotalPrice(rs.getDouble("totalprice"));
                gibi.setImportTime(oConvert.FormDateTimeShort(rs.getTimestamp("importTime")));
                gibi.setPayLimTime(oConvert.FormDateShort(rs.getTimestamp("paylimtime")));
                gibi.setFactory(rs.getInt("factory"));
                gibi.setFactoryName(rs.getString("name"));
                gibi.setFkType(rs.getInt("fkType"));
                gibi.setFkName(rs.getString("fkname"));
                gibi.setConfirmFlage(rs.getString("confirmflage"));
                prepay=oConvert.getDouble(rs.getString("prepay"),0);
                gibi.setPrepay1(prepay);
                gibi.setDeptid(rs.getInt("deptid"));
                gibi.setKind(rs.getInt("kind"));
                gibi.setIeva(rs.getInt("ieva"));
                ibsri.rslist.add(gibi);

            }
            rs.close();
            if(phf.getFactory()==0)
            {
                strSQL = "SELECT factory,a.deptid,SUM(a.TotalPrice) as allprice FROM TabGoodsImportInfo a inner join TabFactory b on a.factory=b.id"+
                " WHERE (a.PaymentTime IS NULL) AND (a.ConfirmFlage = '1') ";
                if(phf.getDeptid()>0)
                	strSQL=strSQL+" and a.deptid="+phf.getDeptid();
                if(phf.getZhujima().length()>0)
                	strSQL=strSQL+" and (b.name like '%"+phf.getZhujima()+"%' or b.zhujima like '%"+phf.getZhujima()+"%')";
                strSQL=strSQL+" group by factory,a.deptid";
                rs=stmt.executeQuery(strSQL);
                stmt1 = conn.createStatement();

                while(rs.next())
                {
                	strSQL = " SELECT prepay FROM TabPrePay where factory="+rs.getInt("factory")+" and deptid="+rs.getInt("deptid");
                	rs1 = stmt1.executeQuery(strSQL);
                	if(rs1.next())
                	{
                		totalPrepay=totalPrepay+rs1.getDouble("prepay");
                		if(rs.getDouble("allprice")>0)
                		{
                			if(rs1.getDouble("prepay")<rs.getDouble("allprice"))
                				totalown=totalown+(rs.getDouble("allprice")-rs1.getDouble("prepay"));
                		}
                		else
                			totalown=totalown+rs.getDouble("allprice");
                	}
                	else
                		totalown=totalown+rs.getDouble("allprice");
                }

            }
            
            rs = stmt.executeQuery(strSQL1);
            if(rs.next())
            {
            	ibsri.allsumnum=oConvert.getRound(rs.getDouble(1),4);
            	ibsri.allmoney=oConvert.getRound(rs.getDouble(2),2);
            }
            if(phf.getFactory()>0)
            {
                totalPrepay=prepay;
                totalown=ibsri.allmoney-prepay;
            }
            ibsri.realmoney=oConvert.getRound(totalPrepay,2);
            ibsri.oddmoney=oConvert.getRound(totalown,2);
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
	//	货款支付
	@SuppressWarnings("unchecked")
	public int payHuoKuan(int kind,int billid,int deptid,double realmoney,int fkType,int acctype,String shoukuanren) throws Exception
    {
		int nRet;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        int factory=0;
        double prepay=0;
        double totalprice=0;
        double jiagong=0;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String fk="";
            if(kind==1)
            {
            	fk="预收";
            	strSQL="select * from TabGoodsExportInfo where paymenttime is null and billid="+billid+" and deptid="+deptid;
            }
            else
            {
            	strSQL="select * from TabGoodsImportInfo where paymenttime is null and billid="+billid+" and deptid="+deptid;
            	fk="预付";
            }
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	factory=rs.getInt("factory");
            	totalprice=rs.getDouble("totalprice");
            	if(kind==1)
            	{
            		totalprice=totalprice+rs.getDouble("totaljiagong");
            		jiagong=rs.getDouble("totaljiagong");
            	}
            	
            }
            else
            	throw new Exception("不存在此订单");
            rs.close();
            //扣减预收预付
            if(fkType==5)
            {
            	if(kind==1)
                	strSQL="select preShou from TabPrePay where factory="+factory+" and deptid="+deptid;
                else
                	strSQL="select prepay from TabPrePay where factory="+factory+" and deptid="+deptid;
                rs=stmt.executeQuery(strSQL);
                if(rs.next())
                	prepay=rs.getDouble(1);
                if(realmoney>prepay)
            		throw new Exception(fk+"金额 "+prepay+" 不足以支付货款 "+realmoney);
                
                strSQL="insert into accessPrepay (factory,curChuzhi,jine,operPerson,kind,deptid,billid,typename) values("+
            	factory+","+prepay+","+(realmoney)+",'"+shoukuanren+"',"+kind+","+deptid+","+billid+",'"+(kind==1?"货款收取":"货款支付")+"')";
                nRet=stmt.executeUpdate(strSQL);
            	if(kind==1)
            	{
                    strSQL = "update TabPrePay set preShou=round(preShou-("+realmoney+"),2) where factory="+factory+" and deptid="+deptid;
            	}
            	else
            		strSQL = "update TabPrePay set prepay=round(prepay-("+realmoney+"),2) where factory="+factory+" and deptid="+deptid;
            	nRet=stmt.executeUpdate(strSQL);
                if(nRet!=1)
                	throw new Exception("更新预储帐户失败");
                
                
            }
            else//更新账户余额
            {
            	strSQL="select * from TabAccount where id="+acctype+" and deptid="+deptid;
            	rs=stmt.executeQuery(strSQL);
                rs.next();
                prepay=rs.getDouble("jine");
                
                if(realmoney*kind+prepay<0)
            		throw new Exception(rs.getString("accname")+" 余额不足，仅剩 "+prepay+" 元");
        		strSQL = "update TabAccount set jine=round(jine+("+realmoney*kind+"),2) where id="+acctype+" and deptid="+deptid;
            	nRet=stmt.executeUpdate(strSQL);
                if(nRet!=1)
                	throw new Exception("更新帐户余额失败");
                //帐户操作记录
            	strSQL = "insert into AccessAccount (deptid,acctype,curjine,jine,operPerson,typename,billid) values("+
                deptid+","+acctype+","+prepay+","+realmoney*kind+",'"+shoukuanren+"','"+(kind==1?"货款收取":"货款支付")+"',"+billid+")";
                stmt.executeUpdate(strSQL);
            }
            
            //抹零收益或费用
            if(totalprice!=realmoney)
            {
            	int fykind=0;
            	String fyname="";
            	if(kind==1)
            	{
            		if(totalprice>realmoney)
            			fykind=-1;
            		else
            			fykind=1;
            	}
            	else
            	{
            		if(totalprice>realmoney)
            			fykind=1;
            		else
            			fykind=-1;
            	}
            	if(fykind==1)
            		fyname="其他业务收入";
            	else
            		fyname="其他业务支出";
            	//费用类别中加入
                int classid=0;
                strSQL = "select * from FeiYongClass where feiyongclass='"+fyname+"' and kind="+fykind; 
            	rs = stmt.executeQuery(strSQL);
            	if(!rs.next())
            	{
            		strSQL = "insert into FeiYongClass (feiyongclass,kind,lock) values('"+fyname+"',"+fykind+",1)";
            		stmt.executeUpdate(strSQL);
            		strSQL = "select @@IDENTITY";
            		rs=stmt.executeQuery(strSQL);
            		rs.next();
            		classid=rs.getInt(1);
            	}
            	else
            		classid=rs.getInt("id");
            	int typeid=0;
            	String typename="";
            	if(fykind==1)
            		typename="货单抹零收入";
            	else
            		typename="货单抹零支出";
            	strSQL = "select * from FeiYongType where feiyongtype='"+typename+"' and classid="+classid; 
            	rs = stmt.executeQuery(strSQL);
            	if(!rs.next())
            	{
            		strSQL = "insert into FeiYongType (feiyongtype,classid,lock) values('"+typename+"',"+classid+",1)";
            		stmt.executeUpdate(strSQL);
            		strSQL = "select @@IDENTITY";
            		rs=stmt.executeQuery(strSQL);
            		rs.next();
            		typeid=rs.getInt(1);
            	}
            	else
            		typeid=rs.getInt("id");
            	
            	//新增费用单
            	if(fykind==1)
            	{
            		strSQL = "update TabDepartInfo set srbillid=srbillid+1 where id="+deptid;
	        		stmt.executeUpdate(strSQL);
	        		strSQL = "select srbillid from TabDepartInfo where id="+deptid; 
            	}
            	else
	            {
	            	strSQL = "update TabDepartInfo set fybillid=fybillid+1 where id="+deptid;
	        		stmt.executeUpdate(strSQL);
	        		strSQL = "select fybillid from TabDepartInfo where id="+deptid; 
	            }
            	rs = stmt.executeQuery(strSQL);
            	rs.next();
            	int fybillid=rs.getInt(1);
            	java.util.Date dt=new java.util.Date();
            	
                strSQL = "insert into TabFeiYongInfo( BillID,deptid,operPerson,totalprice,confirmflage,billtime,kind,acctype)"+
                " values (" + fybillid + ","+deptid+",'"+shoukuanren+"',"+(realmoney-totalprice)*kind+",'1','"+oConvert.FormDateShort(dt)+"',"+fykind+","+acctype+")"; 
            	stmt.executeUpdate(strSQL);
            	strSQL = "insert into TabFeiYongGoods( BillID,typeid,typename,jine,memo,operPerson,confirmflage,deptid,kind)"+
                " values (" + fybillid + ","+typeid+",'"+typename+"',"+(realmoney-totalprice)*kind+",'"+(kind==1?"出":"入")+"库单号:"+billid+"','"+shoukuanren+"','1',"+deptid+","+fykind+")"; 
            	stmt.executeUpdate(strSQL);
            	
            }
            
            //加工费
            if(jiagong>0)
            {
            	int fykind=1;
            	String fyname="其他业务收入";
            	
            	//费用类别中加入
                int classid=0;
                strSQL = "select * from FeiYongClass where feiyongclass='"+fyname+"' and kind="+fykind; 
            	rs = stmt.executeQuery(strSQL);
            	if(!rs.next())
            	{
            		strSQL = "insert into FeiYongClass (feiyongclass,kind,lock) values('"+fyname+"',"+fykind+",1)";
            		stmt.executeUpdate(strSQL);
            		strSQL = "select @@IDENTITY";
            		rs=stmt.executeQuery(strSQL);
            		rs.next();
            		classid=rs.getInt(1);
            	}
            	else
            		classid=rs.getInt("id");
            	int typeid=0;
            	String typename="加工费收入";
            	strSQL = "select * from FeiYongType where feiyongtype='"+typename+"' and classid="+classid; 
            	rs = stmt.executeQuery(strSQL);
            	if(!rs.next())
            	{
            		strSQL = "insert into FeiYongType (feiyongtype,classid,lock) values('"+typename+"',"+classid+",1)";
            		stmt.executeUpdate(strSQL);
            		strSQL = "select @@IDENTITY";
            		rs=stmt.executeQuery(strSQL);
            		rs.next();
            		typeid=rs.getInt(1);
            	}
            	else
            		typeid=rs.getInt("id");
            	
            	//新增费用单
            	if(fykind==1)
            	{
            		strSQL = "update TabDepartInfo set srbillid=srbillid+1 where id="+deptid;
	        		stmt.executeUpdate(strSQL);
	        		strSQL = "select srbillid from TabDepartInfo where id="+deptid; 
            	}
            	else
	            {
	            	strSQL = "update TabDepartInfo set fybillid=fybillid+1 where id="+deptid;
	        		stmt.executeUpdate(strSQL);
	        		strSQL = "select fybillid from TabDepartInfo where id="+deptid; 
	            }
            	rs = stmt.executeQuery(strSQL);
            	rs.next();
            	int fybillid=rs.getInt(1);
            	java.util.Date dt=new java.util.Date();
            	
                strSQL = "insert into TabFeiYongInfo( BillID,deptid,operPerson,totalprice,confirmflage,billtime,kind,acctype)"+
                " values (" + fybillid + ","+deptid+",'"+shoukuanren+"',"+jiagong+",'1','"+oConvert.FormDateShort(dt)+"',"+fykind+","+acctype+")"; 
            	stmt.executeUpdate(strSQL);
            	strSQL = "insert into TabFeiYongGoods( BillID,typeid,typename,jine,memo,operPerson,confirmflage,deptid,kind)"+
                " values (" + fybillid + ","+typeid+",'"+typename+"',"+jiagong+",'"+(kind==1?"出":"入")+"库单号:"+billid+"','"+shoukuanren+"','1',"+deptid+","+fykind+")"; 
            	stmt.executeUpdate(strSQL);
            	
            }
            
            
            if(kind==1)
            {
            	strSQL = "update TabGoodsExportInfo set paymenttime=getDate(),realmoney="+realmoney+",fkType="+fkType+",shoukuanren='"+shoukuanren+"' where billid="+billid+" and deptid="+deptid;
            }
            else
            {
            	strSQL = "update TabGoodsImportInfo set paymenttime=getDate(),realmoney="+realmoney+",fkType="+fkType+",shoukuanren='"+shoukuanren+"' where billid="+billid+" and deptid="+deptid;
            }
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("更新付款记录失败");
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
        return nRet;
    }
	//	应收发票
	@SuppressWarnings("unchecked")
	public ResultsetList giveFaPiaoList(PayHuoKuanForm phf,int curPage) throws Exception
    {
		ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
        strSQL = " SELECT a.*,b.name,c.name as fpName FROM TabGoodsExportInfo a inner join TabFactory b on a.factory=b.id inner join "+
        " TabFaPiao c on a.fpType=c.id where payFptime is null and confirmFlage='1' and a.fpType>1 and a.factory="+phf.getFactory();
        strSQL1 = " SELECT sum(totalnum) as allnum,sum(totalprice) as allprice FROM TabGoodsExportInfo a inner join TabFactory b on a.factory=b.id inner join "+
        " TabFaPiao c on a.fpType=c.id where payFptime is null and confirmFlage='1' and fpType>1 and a.factory="+phf.getFactory();
        String whereStr = "";
        if(phf.getDeptid()>0)
        	whereStr=whereStr+" and a.deptid="+phf.getDeptid();
        strSQL=strSQL+whereStr;
        strSQL1=strSQL1+whereStr;
        if(phf.getOrderby().length()>0)
        	strSQL=strSQL+" order by "+phf.getOrderby();
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(strSQL);
            ExportBillForm gibi;
            while(rs.next())
        	{
        		gibi = new ExportBillForm();
                gibi.setBillId(rs.getInt("billid"));
                gibi.setSalesPerson(rs.getString("salesPerson"));
                gibi.setTotalNum(rs.getDouble("totalnum"));
                gibi.setTotalPrice(rs.getDouble("totalprice"));
                gibi.setExportTime(oConvert.FormDateTimeShort(rs.getTimestamp("exportTime")));
                gibi.setPayLimTime(oConvert.FormDateShort(rs.getTimestamp("paylimtime")));
                gibi.setFactory(rs.getInt("factory"));
                gibi.setFactoryName(rs.getString("name"));
                gibi.setFpType(rs.getInt("fpType"));
                gibi.setFpName(rs.getString("fpName"));
                gibi.setDeptid(rs.getInt("deptid"));
                ibsri.rslist.add(gibi);
                
        	}
            rs.close();
            rs = stmt.executeQuery(strSQL1);
            if(rs.next())
            {
            	ibsri.allsumnum=oConvert.getRound(rs.getDouble(1),4);
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
	//取得应开发票
	@SuppressWarnings("unchecked")
	public ResultsetList getFaPiaoList(PayHuoKuanForm phf,int curPage) throws Exception
    {
        ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
     
        strSQL = " SELECT a.*,b.name,c.name as fpName FROM TabGoodsImportInfo a inner join TabFactory b on a.factory=b.id inner join "+
        "TabFaPiao c on a.fpType=c.id where confirmFlage='1' and payFptime is null and a.fpType>1 and factory="+phf.getFactory();
        strSQL1 = " SELECT sum(totalnum) as allnum,sum(totalprice) as allprice FROM TabGoodsImportInfo a inner join TabFactory b "+
        " on a.factory=b.id inner join TabFaPiao c on a.fpType=c.id where a.confirmFlage='1' and a.payFptime is null and a.fpType>1 and factory="+phf.getFactory();
        String whereStr = "";
        if(phf.getDeptid()>0)
        	whereStr=whereStr+" and a.deptid="+phf.getDeptid();
        strSQL=strSQL+whereStr;
        strSQL1=strSQL1+whereStr;
        if(phf.getOrderby().length()>0)
        	strSQL=strSQL+" order by "+phf.getOrderby();
        strSQL1=strSQL1+whereStr;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(strSQL);
            while(rs.next())
            {
        		ImportBillForm gibi = new ImportBillForm();
                gibi.setBillId(rs.getInt("billid"));
                gibi.setCreatePerson(rs.getString("createPerson"));
                gibi.setTotalNum(rs.getDouble("totalnum"));
                gibi.setTotalPrice(rs.getDouble("totalprice"));
                gibi.setImportTime(oConvert.FormDateTimeShort(rs.getTimestamp("importTime")));
                gibi.setPayLimTime(oConvert.FormDateShort(rs.getTimestamp("paylimtime")));
                gibi.setFactory(rs.getInt("factory"));
                gibi.setFactoryName(rs.getString("name"));
                gibi.setFpType(rs.getInt("fpType"));
                gibi.setFpName(rs.getString("fpName"));
                gibi.setDeptid(rs.getInt("deptid"));
                gibi.setIeva(rs.getInt("ieva"));
                ibsri.rslist.add(gibi);
            }
            rs.close();
            rs = stmt.executeQuery(strSQL1);
            if(rs.next())
            {
            	ibsri.allsumnum=oConvert.getRound(rs.getDouble(1),4);
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
	
	public int payFaPiao(int kind,int billid,int deptid,String username) throws Exception
    {
		int nRet;
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
            if(kind==1)
            	strSQL = "select * from TabGoodsImportInfo where payFptime is null and billid="+billid+" and deptid="+deptid;
            else
            	strSQL = "select * from TabGoodsExportInfo where payFptime is null and billid="+billid+" and deptid="+deptid;
            rs=stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("不存在此订单");
            if(kind==1)
            	strSQL = "update TabGoodsImportInfo set payFptime=getDate(),kaipiaoren='"+username+"' where billid="+billid+" and deptid="+deptid;
            else
            	strSQL = "update TabGoodsExportInfo set payFptime=getDate(),kaipiaoren='"+username+"' where billid="+billid+" and deptid="+deptid;
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("更新记录失败");
            
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
        return nRet;
    }
	//	取得出库单运费
	@SuppressWarnings("unchecked")
	public ResultsetList getYunFeiList(PayHuoKuanForm phf) throws Exception
    {
		ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
     
        strSQL = " SELECT a.*,c.Driver,d.shortname FROM TabGoodsExportInfo a "+
        "left join TabTruck c on a.carno=c.carno inner join TabDepartInfo d on a.deptid=d.id where yunfei>0 and payshiptime is null and confirmFlage='1'";
        strSQL1 = " SELECT sum(totalnum) as allnum,sum(yunfei) as allprice FROM TabGoodsExportInfo a left join"+
        " TabTruck c on a.carno=c.carno where yunfei>0 and payshiptime is null and confirmFlage='1'";
        String whereStr = "";
        if(phf.getDeptid()>0)
        	whereStr=whereStr+" and a.deptid="+phf.getDeptid();
        whereStr=whereStr+" and a.carno='"+phf.getZhujima()+"'";
        strSQL=strSQL+whereStr;
        strSQL1=strSQL1+whereStr;
        
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	ExportBillForm gibi = new ExportBillForm();
            	gibi.setKind(1);
                gibi.setBillId(rs.getInt("billid"));
                gibi.setSalesPerson(rs.getString("salesPerson"));
                gibi.setTotalNum(rs.getDouble("totalnum"));
                gibi.setTotalPrice(rs.getDouble("totalprice"));
                gibi.setExportTime(oConvert.FormDateTimeShort(rs.getTimestamp("exportTime")));
                gibi.setCarNo(rs.getString("carno"));
                gibi.setDriver(rs.getString("driver"));
                gibi.setYunFei(rs.getFloat("yunfei"));
                gibi.setDeptid(rs.getInt("deptid"));
                gibi.setShortname(rs.getString("shortname"));
                ibsri.rslist.add(gibi);
            }
            rs.close();
            rs = stmt.executeQuery(strSQL1);
            if(rs.next())
            {
            	ibsri.allsumnum=oConvert.getRound(rs.getDouble(1),4);
            	ibsri.allmoney=oConvert.getRound(rs.getDouble(2),2);
            }
            strSQL = " SELECT a.*,c.Driver,d.shortname FROM TabGoodsImportInfo a "+
            "left join TabTruck c on a.carno=c.carno inner join TabDepartInfo d on a.deptid=d.id where confirmFlage='1' and yunfei>0 and payshiptime is null";
            strSQL1 = " SELECT sum(totalnum) as allnum,sum(yunfei) as allprice FROM TabGoodsImportInfo a left join "+
            "TabTruck c on a.carno=c.carno where confirmFlage='1' and yunfei>0 and payshiptime is null";
            whereStr = "";
            if(phf.getDeptid()>0)
            	whereStr=whereStr+" and a.deptid="+phf.getDeptid();
            if(phf.getZhujima().length()>0)
            	whereStr=whereStr+" and a.carno like '%"+phf.getZhujima()+"%' or c.Driver like '%"+phf.getZhujima()+"%'";
            strSQL=strSQL+whereStr;
            strSQL1=strSQL1+whereStr;
            rs = stmt.executeQuery(strSQL);
            while(rs.next())
        	{
        		ExportBillForm gibi = new ExportBillForm();
        		gibi.setKind(-1);
                gibi.setBillId(rs.getInt("billid"));
                gibi.setSalesPerson(rs.getString("createPerson"));
                gibi.setTotalNum(rs.getDouble("totalnum"));
                gibi.setTotalPrice(rs.getDouble("totalprice"));
                gibi.setExportTime(oConvert.FormDateTimeShort(rs.getTimestamp("importTime")));
                gibi.setCarNo(rs.getString("carno"));
                gibi.setDriver(rs.getString("driver"));
                gibi.setYunFei(rs.getFloat("yunfei"));
                gibi.setDeptid(rs.getInt("deptid"));
                gibi.setShortname(rs.getString("shortname"));
                ibsri.rslist.add(gibi);
        	}
            rs.close();
            rs = stmt.executeQuery(strSQL1);
            if(rs.next())
            {
            	ibsri.allsumnum=ibsri.allsumnum+oConvert.getRound(rs.getDouble(1),4);
            	ibsri.allmoney=ibsri.allmoney+oConvert.getRound(rs.getDouble(2),2);
            }
            strSQL="select account from TabTruck where carno='"+phf.getZhujima()+"'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	ibsri.oddmoney=rs.getDouble("account");
            class ContentComparator implements Comparator 
            {
            	public String orderby;
            	 public int compare(Object o1, Object o2) 
            	 {
            	 
            		 ExportBillForm c1 = (ExportBillForm) o1;
            		 ExportBillForm c2 = (ExportBillForm) o2;
            		 if(orderby.equals("billid"))
            		 {
            			 if (c1.getBillId() > c2.getBillId()) 
            				 return 1;
            			 else if (c1.getBillId() == c2.getBillId())
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 else if(orderby.equals("salesPerson"))
            		 {
            			 if (c1.getSalesPerson().compareTo(c2.getSalesPerson())>0 ) 
            				 return 1;
            			 else if (c1.getSalesPerson().equals(c2.getSalesPerson()))
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 else if(orderby.equals("totalnum"))
            		 {
            			 if (c1.getTotalNum()>c2.getTotalNum() ) 
            				 return 1;
            			 else if (c1.getTotalNum()==c2.getTotalNum() )
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 else if(orderby.equals("totalprice"))
            		 {
            			 if (c1.getTotalPrice()>c2.getTotalPrice() ) 
            				 return 1;
            			 else if (c1.getTotalPrice()==c2.getTotalPrice() )
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 else if(orderby.equals("exportTime"))
            		 {
            			 if (c1.getExportTime().compareTo(c2.getExportTime())>0 ) 
            				 return 1;
            			 else if (c1.getExportTime().equals(c2.getExportTime()))
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 else if(orderby.equals("carno"))
            		 {
            			 if (c1.getCarNo().compareTo(c2.getCarNo())>0 ) 
            				 return 1;
            			 else if (c1.getCarNo().equals(c2.getCarNo()))
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 else if(orderby.equals("driver"))
            		 {
            			 if (c1.getDriver().compareTo(c2.getDriver())>0 ) 
            				 return 1;
            			 else if (c1.getDriver().equals(c2.getDriver()))
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 else if(orderby.equals("yunfei"))
            		 {
            			 if (c1.getYunFei()>c2.getYunFei() ) 
            				 return 1;
            			 else if (c1.getYunFei()==c2.getYunFei() )
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 else if(orderby.equals("shortname"))
            		 {
            			 if (c1.getShortname().compareTo(c2.getShortname())>0 ) 
            				 return 1;
            			 else if (c1.getShortname().equals(c2.getShortname()))
            				 return 0;
            			 else 
            				 return -1;
            		 }
					return 0;
            	 }
            }
            ContentComparator comp = new ContentComparator();  
            comp.orderby=phf.getOrderby();
            Collections.sort(ibsri.rslist,comp);

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
	
	//	取得付款类型
	@SuppressWarnings("unchecked")
	public int payYunFei(int kind,int billid,int deptid,double realmoney,String operPerson,int acctype,int fktype) throws Exception
    {
		int nRet;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        double yunfei=0;
        int carid=0;
        String carno="";
        boolean iyunfei=false;
        double prepay=0;
        int fybillid=0;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            if(kind==-1)
            	strSQL = "select * from TabGoodsImportInfo where payshiptime is null and billid="+billid+" and deptid="+deptid;
            else
            	strSQL = "select * from TabGoodsExportInfo where payshiptime is null and billid="+billid+" and deptid="+deptid;
            rs=stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("不存在此订单");
            else
            {
            	carno=rs.getString("carno");
            	yunfei=rs.getDouble("yunfei");
            	if(kind==-1)
            		iyunfei=rs.getBoolean("iyunfei");
            }
            strSQL = "select * from TabTruck where carno='"+carno+"' and deptid="+deptid;
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	carid=rs.getInt("id");
            String typename="";
            if(kind==1)
        		typename="出库运费";
        	else
        		typename="入库运费";
            if(iyunfei)
            	typename="入库运杂费";
        	FeiYongClass fyc=new FeiYongClass();
        	int typeid=fyc.getTypeIdByName("运费", -1, typename);
        	if(!iyunfei)
        	{
	        	//新增费用单
	        	strSQL = "update TabDepartInfo set fybillid=fybillid+1 where id="+deptid;
	    		stmt.executeUpdate(strSQL);
	    		strSQL = "select fybillid from TabDepartInfo where id="+deptid; 
	        	rs = stmt.executeQuery(strSQL);
	        	rs.next();
	        	fybillid=rs.getInt(1);
	        	java.util.Date dt=new java.util.Date();
	            strSQL = "insert into TabFeiYongInfo( BillID,deptid,operPerson,totalprice,confirmflage,billtime,kind,acctype)"+
	            " values (" + fybillid + ","+deptid+",'"+operPerson+"',"+(-realmoney)+",'1','"+oConvert.FormDateShort(dt)+"',-1,"+acctype+")"; 
	        	stmt.executeUpdate(strSQL);
	        	strSQL = "insert into TabFeiYongGoods( BillID,typeid,typename,jine,memo,operPerson,confirmflage,deptid,kind)"+
	            " values (" + fybillid + ","+typeid+",'"+typename+"',"+(-realmoney)+",'"+typename.substring(0, 2)+"单:"+billid+"','"+operPerson+"','1',"+deptid+",-1)"; 
	        	stmt.executeUpdate(strSQL);
        	}
        	else
        	{
        		fybillid=billid;
        		double oddment=yunfei-realmoney;
        		if(oddment!=0)
        		{
        			String srtypename="运费抹零收入";
        			int srtypeid=fyc.getTypeIdByName("其他业务收入", 1, srtypename);
        			//新增收入单
    	        	strSQL = "update TabDepartInfo set srbillid=srbillid+1 where id="+deptid;
    	    		stmt.executeUpdate(strSQL);
    	    		strSQL = "select srbillid from TabDepartInfo where id="+deptid; 
    	        	rs = stmt.executeQuery(strSQL);
    	        	rs.next();
    	        	int srbillid=rs.getInt(1);
    	        	java.util.Date dt=new java.util.Date();
    	            strSQL = "insert into TabFeiYongInfo( BillID,deptid,operPerson,totalprice,confirmflage,billtime,kind,acctype)"+
    	            " values (" + srbillid + ","+deptid+",'"+operPerson+"',"+(oddment)+",'1','"+oConvert.FormDateShort(dt)+"',1,"+acctype+")"; 
    	        	stmt.executeUpdate(strSQL);
    	        	strSQL = "insert into TabFeiYongGoods( BillID,typeid,typename,jine,memo,operPerson,confirmflage,deptid,kind)"+
    	            " values (" + srbillid + ","+srtypeid+",'"+srtypename+"',"+(oddment)+",'"+"入库单:"+billid+"','"+operPerson+"','1',"+deptid+",1)"; 
    	        	stmt.executeUpdate(strSQL);
        		}
        		
        	}
            if(fktype==5)
            {
            	strSQL="select account from TabTruck where id='"+carid+"' and deptid="+deptid;
                rs=stmt.executeQuery(strSQL);
                if(rs.next())
                	prepay=rs.getDouble(1);
                if(realmoney>prepay)
            		throw new Exception("预付金额 "+prepay+" 不足以支付运费 "+realmoney);
                
                strSQL="insert into accessPrepay (factory,curChuzhi,jine,operPerson,kind,deptid,billid,typename) values("+
            	carid+","+prepay+","+(realmoney)+",'"+operPerson+"',0,"+deptid+","+billid+",'"+typename+"')";
                nRet=stmt.executeUpdate(strSQL);
            	strSQL = "update TabTruck set account=round(account-("+realmoney+"),2) where id="+carid+" and deptid="+deptid;
            	nRet=stmt.executeUpdate(strSQL);
                if(nRet!=1)
                	throw new Exception("更新预储帐户失败");
                    
            }
            else
            {
	            //更新现金帐户
	            strSQL="select jine,accname from TabAccount where id="+acctype+" and deptid="+deptid;
	            rs=stmt.executeQuery(strSQL);
	            rs.next();
	            double xj=rs.getDouble(1);
	            if(realmoney>xj)
	            	throw new Exception(rs.getString("accname")+" 余额不足，仅剩 "+xj+" 元");
	            strSQL="update TabAccount set jine=jine-"+realmoney+" where id="+acctype+" and deptid="+deptid;
	            nRet=stmt.executeUpdate(strSQL);
	            if(nRet!=1)
	            	throw new Exception("更新帐户余额失败");
	            strSQL = "insert into accessAccount (deptid,acctype,curjine,jine,operPerson,typename,billid) values("+deptid+
	            ","+acctype+","+xj+","+(-realmoney)+",'"+operPerson+"','"+typename+"',"+fybillid+")";
	            stmt.executeUpdate(strSQL);
            }
            
        	//更新单据状态
            if(kind==-1)
            	strSQL = "update TabGoodsImportInfo set payshiptime=getDate(),realyunfei="+realmoney+",yunfeiren='"+operPerson+"' where billid="+billid+" and deptid="+deptid;
            else
            	strSQL = "update TabGoodsExportInfo set payshiptime=getDate(),realyunfei="+realmoney+",yunfeiren='"+operPerson+"' where billid="+billid+" and deptid="+deptid;
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("更新运费失败");
            if(!iyunfei)
            	nRet=fybillid;
            else
            	nRet=0;
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
        return nRet;
    }
	//增加储值
	public int addPrepay(int kind,int factory,int deptid,double newpay,String oper,int fktype,int acctype) throws Exception
    {
		int nRet;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        int billid=0;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            double prepay=0;
            if(kind==1 || kind==-1)
            {
	            strSQL="select * from Tabfactory where id="+factory;
	        	rs=stmt.executeQuery(strSQL);
	        	if(!rs.next())
	        		throw new Exception("不存在此客户");
	        	rs.close();
	        	strSQL="select * from TabPrepay where factory="+factory+" and deptid="+deptid;
	        	rs=stmt.executeQuery(strSQL);
	        	if(rs.next())
	        	{
	        		if(kind==1)
	        			prepay=rs.getDouble("preshou");
	        		else
	        			prepay=rs.getDouble("prepay");
	        	}
	        	else
	        	{
	        		strSQL="insert into TabPrepay (factory,deptid) values("+factory+","+deptid+")";
	        		stmt.executeUpdate(strSQL);
	        	}
	        	rs.close();
            }
            else
            {
            	TruckManager um = new TruckManager();
            	TruckInfoForm fi=um.getTruckByID(factory);
            	prepay=fi.getAccount();
            }
            if(kind==1)
            {
            	strSQL = "update TabPrepay set preshou=preshou+("+newpay+") where factory="+factory+" and deptid="+deptid;
            	nRet=stmt.executeUpdate(strSQL);
                if(nRet!=1)
                	throw new Exception("更新预收款帐户失败");
            }
            else if(kind==-1)
            {
            	strSQL = "update TabPrepay set prepay=prepay+("+newpay+") where factory="+factory+" and deptid="+deptid;
            	nRet=stmt.executeUpdate(strSQL);
                if(nRet!=1)
                	throw new Exception("更新预付款帐户失败");
            }
            else if(kind==0)
            {
            	strSQL = "update TabTruck set account=account+("+newpay+") where id="+factory+" and deptid="+deptid;
            	nRet=stmt.executeUpdate(strSQL);
                if(nRet!=1)
                	throw new Exception("更新预付运费帐户失败");
            }
            double curjine=0;
            strSQL = "update TabDepartInfo set ysbillid=ysbillid+1 where id="+deptid;
            stmt.executeUpdate(strSQL);
            strSQL = " SELECT ysbillid FROM TabDepartInfo where id="+deptid;
         	rs=stmt.executeQuery(strSQL);
         	rs.next();
         	billid=rs.getInt("ysbillid");
         	rs.close();
         	strSQL = " SELECT jine,accname FROM TabAccount where id="+acctype+" and deptid="+deptid;
         	rs=stmt.executeQuery(strSQL);
         	rs.next();
         	curjine=rs.getDouble(1);
         	int fuhao=1;
         	if(kind==1 || kind==-1)
         	{
         		fuhao=kind;
         	}
         	else
         	{
         		fuhao=-1;
         	}
         	if(curjine+newpay*fuhao<0)
     			throw new Exception(rs.getString("accname")+" 余额不足");
     		strSQL = "update TabAccount set jine=jine+("+newpay*fuhao+") where id="+acctype+" and deptid="+deptid;
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("更新帐户余额失败");
        		
        	String typename="";
        	if(kind==1)
        		typename="预收货款";
        	else if(kind==-1)
        		typename="预付货款";
        	else if(kind==0)
        		typename="预付运费";
        	//储值操作记录
            strSQL="insert into AccessPrepay (factory,deptid,curChuzhi,jine,operPerson,kind,typename,billid,fktype) values("+
        	factory+","+deptid+","+prepay+","+newpay+",'"+oper+"',"+kind+",'"+typename+"',"+billid+","+fktype+")";
            stmt.executeUpdate(strSQL);
            
            //帐户操作记录
        	strSQL = "insert into AccessAccount (deptid,acctype,curjine,jine,operPerson,typename,billid) values("+
            deptid+","+acctype+","+curjine+","+newpay*fuhao+",'"+oper+"','"+typename+"',"+billid+")";
            stmt.executeUpdate(strSQL);
            conn.commit();
            nRet=billid;
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
        return nRet;
    }
	//取得最小未付款
	public double getMinHuoKuan(int kind,int factory) throws Exception
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
            if(kind==1)
            	strSQL = " SELECT min(totalprice) FROM TabGoodsExportInfo where paymenttime is null and "+
            	"confirmFlage='1' and factory="+factory;
            else
            	strSQL = " SELECT min(totalprice) FROM TabGoodsImportInfo where paymenttime is null and "+
            	"confirmFlage='1' and factory="+factory;
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	nRet=rs.getDouble(1);
            	
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
        return nRet;
    }
	//查询预收预付记录
	@SuppressWarnings("unchecked")
	public ResultsetList getChongzhiGroupByFac(int kind) throws Exception
    {
		ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
     
        strSQL = "SELECT SUM(a.addchuzhi) AS allnum, SUM(a.TotalPrice) AS allprice,count(*) as allcount,factory, b.name,b.prepay FROM "+
        "Chongzhi a INNER JOIN TabFactory b ON a.factory = b.id WHERE (a.PaymentTime IS NULL) "+
        "AND (a.ConfirmFlage = '1') GROUP BY a.factory,b.name,b.prepay ORDER BY SUM(a.TotalPrice) DESC";
        strSQL1 = " SELECT sum(totalnum) as allnum,sum(totalprice) as allprice FROM "+
        "TabGoodsExportInfo where paymenttime is null and confirmFlage='1'";
        
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(strSQL);
            ExportBillForm gibi;
            while(rs.next())
            {
            	gibi= new ExportBillForm();
                gibi.setTotalNum(rs.getDouble("allnum"));
                gibi.setTotalPrice(rs.getDouble("allprice"));
                gibi.setFactory(rs.getInt("factory"));
                gibi.setFactoryName(rs.getString("name"));
                gibi.setPrepay(rs.getDouble("prepay"));
                gibi.setAllcount(rs.getInt("allcount"));
                ibsri.rslist.add(gibi);
            }
            rs.close();
            rs = stmt.executeQuery(strSQL1);
            if(rs.next())
            {
            	ibsri.allsumnum=oConvert.getRound(rs.getDouble(1),4);
            	ibsri.allmoney=oConvert.getRound(rs.getDouble(2),2);
            }
            rs.close();
            double totalPrepay=0;
            for(int i=0;i<ibsri.rslist.size();i++)
            {
            	gibi=(ExportBillForm)ibsri.rslist.get(i);
            	strSQL1 = " SELECT prepay FROM TabFactory where id="+gibi.getFactory();
            	rs = stmt.executeQuery(strSQL1);
            	if(rs.next())
            	{
            		if(rs.getDouble("prepay")>=gibi.getTotalPrice())
            			totalPrepay=totalPrepay+gibi.getTotalPrice();
            		else
            			totalPrepay=totalPrepay+rs.getDouble("prepay");
            	}
                
            }
            ibsri.realmoney=oConvert.getRound(totalPrepay,2);
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
	public ShouKuanBillForm getShouKuanBillById(int billid,int deptid) throws Exception
    {
		ShouKuanBillForm cbf=null;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        int kind=0;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL="select * from accessprepay where (typename='预付货款' or typename='预收货款' or typename='预付运费') and billid="+billid+" and deptid="+deptid;
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("找不到此单");
            else
            	kind=rs.getInt("kind");
            if(kind==-1 || kind==1)
            	strSQL = "SELECT a.*,b.name,c.name as fkname,d.name as deptname from accessprepay a inner join TabFactory b on a.factory=b.id "+
            	"left join TabFuKuanType c on a.fktype=c.id inner join TabDepartInfo d on a.deptid=d.id WHERE (typename='预付货款' or typename='预收货款') and a.billid="+billid+" and a.deptid="+deptid;
            else
            	strSQL = "SELECT a.*,b.carno+'('+b.driver+')' as name,c.name as fkname,d.name as deptname from accessprepay a inner join TabTruck b on a.factory=b.id "+
            	"left join TabFuKuanType c on a.fktype=c.id inner join TabDepartInfo d on a.deptid=d.id WHERE typename='预付运费' and a.billid="+billid+" and a.deptid="+deptid;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	cbf=new ShouKuanBillForm();
            	cbf.setBillId(rs.getInt("billid"));
            	cbf.setKind(rs.getInt("kind"));
            	cbf.setDeptid(rs.getInt("deptid"));
            	cbf.setFactname(rs.getString("name"));
            	cbf.setFkname(rs.getString("fkname"));
            	cbf.setOperPerson(rs.getString("operPerson"));
            	cbf.setTotalPrice(rs.getDouble("jine"));
            	cbf.setShortname(rs.getString("deptname"));
            	cbf.setCreateTime(oConvert.FormDateTimeShort(rs.getTimestamp("createTime")));
            	
            }
            else
            	throw new Exception("找不到此单");
            	
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
        return cbf;
    }
	//帐户转款
	public List<AccountForm> getZhuanKuanById(int billid,int deptid) throws Exception
    {
		List<AccountForm> afList=new ArrayList<AccountForm>();
		AccountForm cbf=null;
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
            strSQL = "SELECT a.*,b.accname,c.name,d.inout from accessAccount a inner join Tabaccount b on a.acctype=b.id "+
            " inner join TabDepartInfo c on a.deptid=c.id inner join accessType d on a.typename=d.name WHERE a.billid="+billid+" and a.deptid="+deptid+" and (typename='资金转出' or typename='资金转入')";
            rs = stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	cbf=new AccountForm();
            	cbf.setBillid(rs.getInt("billid"));
            	cbf.setDeptid(rs.getInt("deptid"));
            	cbf.setShortname(rs.getString("name"));
            	cbf.setAccName(rs.getString("accname"));
            	cbf.setOperPerson(rs.getString("operPerson"));
            	cbf.setCurjine(rs.getDouble("curjine"));
            	cbf.setJine(rs.getDouble("jine"));
            	cbf.setTypename(rs.getString("typename"));
            	cbf.setOpertime(oConvert.FormDateTimeShort(rs.getTimestamp("operTime")));
            	cbf.setInout(rs.getBoolean("inout"));
            	afList.add(cbf);
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
        return afList;
    }
	//资本注入抽取
	public AccountForm getZhuRuChouQuById(int billid,int deptid) throws Exception
    {
		AccountForm cbf=null;
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
            strSQL = "SELECT a.*,b.accname,c.name,d.inout from accessAccount a inner join Tabaccount b on a.acctype=b.id "+
            " inner join TabDepartInfo c on a.deptid=c.id inner join accessType d on a.typename=d.name WHERE a.billid="+billid+" and a.deptid="+deptid+" and (typename='资本注入' or typename='资本抽取')";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	cbf=new AccountForm();
            	cbf.setBillid(rs.getInt("billid"));
            	cbf.setDeptid(rs.getInt("deptid"));
            	cbf.setShortname(rs.getString("name"));
            	cbf.setAccName(rs.getString("accname"));
            	cbf.setOperPerson(rs.getString("operPerson"));
            	cbf.setCurjine(rs.getDouble("curjine"));
            	cbf.setJine(rs.getDouble("jine"));
            	cbf.setTypename(rs.getString("typename"));
            	cbf.setOpertime(oConvert.FormDateTimeShort(rs.getTimestamp("operTime")));
            	cbf.setInout(rs.getBoolean("inout"));
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
        return cbf;
    }
//	取得企业发票汇总
	@SuppressWarnings("unchecked")
	public ResultsetList getKaiFaPiaoGroupByFac(PayHuoKuanForm phf,int curPage) throws Exception
    {
		ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        Statement stmt1 = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
        String whereStr="";
        strSQL = "SELECT SUM(a.Totalnum) AS allnum, SUM(a.TotalPrice) AS allprice,count(*) as allcount,a.factory,a.deptid,b.name,c.shortname FROM "+
        "TabGoodsExportInfo a INNER JOIN TabFactory b ON a.factory = b.id inner join TabDepartInfo c on a.deptid=c.id WHERE (a.PayFpTime IS NULL) and a.fpType>1 AND (a.ConfirmFlage = '1') ";
        strSQL1 = "SELECT SUM(a.Totalnum) AS allnum, SUM(a.TotalPrice) AS allprice FROM "+
        "TabGoodsExportInfo a INNER JOIN TabFactory b ON a.factory = b.id inner join TabDepartInfo c on a.deptid=c.id WHERE (a.PayFpTime IS NULL) and a.fpType>1 AND (a.ConfirmFlage = '1') ";
        if(phf.getDeptid()>0)
        	whereStr=whereStr+" and a.deptid="+phf.getDeptid();
        if(phf.getZhujima().length()>0)
        	whereStr=whereStr+" and (b.name like '%"+phf.getZhujima()+"%' or b.zhujima like '%"+phf.getZhujima()+"%')";
        strSQL=strSQL+whereStr;
        strSQL1=strSQL1+whereStr;
        strSQL=strSQL+" GROUP BY a.factory,b.name,a.deptid,c.shortname";
        if(phf.getOrderby().length()>0)
        	strSQL=strSQL+" order by "+phf.getOrderby();
        
        
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt1 = conn.createStatement();
            rs = stmt.executeQuery(strSQL);
            ExportBillForm gibi;
           
            if(rs.last())
            {
            	int RowCount=rs.getRow();
            	ibsri.allnum=RowCount;
            	ibsri.curpage=curPage;
            	ibsri.pagesize=10000;
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
            		gibi= new ExportBillForm();
                    gibi.setTotalNum(rs.getDouble("allnum"));
                    gibi.setTotalPrice(rs.getDouble("allprice"));
                    gibi.setFactory(rs.getInt("factory"));
                    gibi.setFactoryName(rs.getString("name"));
                    gibi.setAllcount(rs.getInt("allcount"));
                    gibi.setShortname(rs.getString("shortname"));
                    gibi.setDeptid(rs.getInt("deptid"));
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
                if(stmt1 != null)
                    stmt1.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return ibsri;
    }
//	取得供应商欠款汇总
	@SuppressWarnings("unchecked")
	public ResultsetList getShouFaPiaoGroupByFac(PayHuoKuanForm phf,int curPage) throws Exception
    {
		ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
        String whereStr = "";
        strSQL = "SELECT SUM(a.Totalnum) AS allnum, SUM(a.TotalPrice) AS allprice,count(*) as allcount,a.factory, a.deptid,b.name,c.shortname FROM "+
        "TabGoodsImportInfo a INNER JOIN TabFactory b ON a.factory = b.id inner join TabDepartInfo c on a.deptid=c.id WHERE (a.PayFpTime IS NULL) and a.fpType>1 AND  (a.ConfirmFlage = '1') ";
        
        strSQL1 = "SELECT SUM(a.Totalnum) AS allnum, SUM(a.TotalPrice) AS allprice FROM "+
        "TabGoodsImportInfo a INNER JOIN TabFactory b ON a.factory = b.id inner join TabDepartInfo c on a.deptid=c.id WHERE (a.PayFpTime IS NULL) and a.fpType>1 AND (a.ConfirmFlage = '1') ";
        if(phf.getDeptid()>0)
        	whereStr=whereStr+" and a.deptid="+phf.getDeptid();
        if(phf.getZhujima().length()>0)
        	whereStr=whereStr+" and (b.name like '%"+phf.getZhujima()+"%' or b.zhujima like '%"+phf.getZhujima()+"%')";
        strSQL=strSQL+whereStr;
        strSQL1=strSQL1+whereStr;
        strSQL=strSQL+" GROUP BY a.factory,b.name,a.deptid,c.shortname";
        if(phf.getOrderby().length()>0)
        	strSQL=strSQL+" order by "+phf.getOrderby();
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(strSQL);
            ImportBillForm gibi;
            if(rs.last())
            {
            	int RowCount=rs.getRow();
            	ibsri.allnum=RowCount;
            	ibsri.curpage=curPage;
            	ibsri.pagesize=10000;
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
            		gibi= new ImportBillForm();
                    gibi.setTotalNum(rs.getDouble("allnum"));
                    gibi.setTotalPrice(rs.getDouble("allprice"));
                    gibi.setFactory(rs.getInt("factory"));
                    gibi.setFactoryName(rs.getString("name"));
                    gibi.setAllcount(rs.getInt("allcount"));
                    gibi.setShortname(rs.getString("shortname"));
                    gibi.setDeptid(rs.getInt("deptid"));
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
        return ibsri;
    }
	//借贷归还记录
	@SuppressWarnings("unchecked")
	public ResultsetList getOtherToPayList(int factory,int deptid,int kind,int curPage) throws Exception
    {
		ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
        OtherToPayForm gibi=null;
        
        String whereStr = " and a.factory="+factory+" and a.deptid="+deptid;
        if(kind==0)
        	whereStr=whereStr+" and (a.kind=16 or a.kind=17)";
        else if(kind==1)
        	whereStr=whereStr+" and (a.kind=18 or a.kind=19)";
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            //操作记录
            strSQL = "SELECT a.*,b.name,c.name as kindname,d.accname FROM TabOtherToPay a inner join TabFactory b on a.factory=b.id "+
            " left join AccessType c on a.kind=c.id inner join TabAccount d on a.acctype=d.id where 1=1";
            
            //总充值额
            strSQL1 = " SELECT sum(jine) FROM TabOtherToPay a inner join TabFactory b on a.factory=b.id "+
            " left join AccessType c on a.kind=c.id where 1=1";
            
            strSQL = strSQL +whereStr+ " Order by a.createtime,a.billid";
            strSQL1=strSQL1+whereStr;
            
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
	        		gibi = new OtherToPayForm();
	                gibi.setId(rs.getInt("id"));
	                gibi.setBillid(rs.getInt("billid"));
	                gibi.setFactory(rs.getInt("factory"));
	                gibi.setFactname(rs.getString("name"));
	                gibi.setDeptid(rs.getInt("deptid"));
	                gibi.setAcctype(rs.getInt("acctype"));
	                gibi.setAccName(rs.getString("accname"));
	                gibi.setJine(rs.getDouble("jine"));
	                if(rs.getInt("kind")==16 || rs.getInt("kind")==17)
	                	gibi.setCurjine(-rs.getDouble("curjine"));
	                else
	                	gibi.setCurjine(rs.getDouble("curjine"));
	                gibi.setKind(rs.getInt("kind"));
	                gibi.setKindName(rs.getString("kindname"));
	                if(rs.getInt("kind")==16)
	                	gibi.setKindName(rs.getString("kindname")+"(其他应收款+)");
	                else if(rs.getInt("kind")==17)
	                	gibi.setKindName(rs.getString("kindname")+"(其他应收款-)");
	                else if(rs.getInt("kind")==18)
	                	gibi.setKindName(rs.getString("kindname")+"(其他应付款+)");
	                else if(rs.getInt("kind")==19)
	                	gibi.setKindName(rs.getString("kindname")+"(其他应付款-)");
	                gibi.setOperPerson(rs.getString("operPerson"));
	                gibi.setCreateTime(oConvert.FormDateTimeShort(rs.getTimestamp("createTime")));
	                gibi.setMemo(oConvert.getString(rs.getString("memo"),""));
	                ibsri.rslist.add(gibi);
	                if(!rs.next())
	                	break;
            	}
            }
            rs.close();
            rs = stmt.executeQuery(strSQL1);
            if(rs.next())
            {
            	ibsri.allmoney=oConvert.getRound(rs.getDouble(1),2);
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
	
	//取得其他应收应付汇总
	@SuppressWarnings("unchecked")
	public ResultsetList getOtherToPayGroupByFac(OtherToPayForm phf,String zhujima,String orderby,int curPage) throws Exception
    {
		ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        Statement stmt1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        String strSQL = null;
        String strSQL1 = null;
        String whereStr="";
        OtherToPayForm gibi=null;
        strSQL = "SELECT SUM(CASE a.kind WHEN 16 THEN -a.jine WHEN 17 THEN -a.jine ELSE 0 END) AS allprice1,"+
        "SUM(CASE a.kind WHEN 18 THEN a.jine WHEN 19 THEN a.jine ELSE 0 END) AS allprice2,count(*) as allcount,a.factory,a.deptid,b.name,c.shortname FROM "+
        "TabOtherToPay a INNER JOIN TabFactory b ON a.factory = b.id inner join TabDepartInfo c on a.deptid=c.id "+
        " WHERE 1=1 ";
        strSQL1 = "SELECT SUM(CASE a.kind WHEN 16 THEN -a.jine WHEN 17 THEN -a.jine ELSE 0 END) AS allprice1,"+
        "SUM(CASE a.kind WHEN 18 THEN a.jine WHEN 19 THEN a.jine ELSE 0 END)  AS allprice2 FROM "+
        "TabOtherToPay a INNER JOIN TabFactory b ON a.factory = b.id inner join TabDepartInfo c on a.deptid=c.id "+
        " WHERE 1=1 ";
        if(phf.getDeptid()>0)
        	whereStr=whereStr+" and a.deptid="+phf.getDeptid();
        if(zhujima.length()>0)
        	whereStr=whereStr+" and (b.name like '%"+zhujima+"%' or b.zhujima like '%"+zhujima+"%')";
        strSQL=strSQL+whereStr;
        strSQL1=strSQL1+whereStr;
        strSQL=strSQL+" GROUP BY a.factory,b.name,a.deptid,c.shortname";
        if(orderby.length()>0)
        	strSQL=strSQL+" order by "+orderby;
        
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt1 = conn.createStatement();
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
            		gibi= new OtherToPayForm();
            		gibi.setFactory(rs.getInt("factory"));
            		gibi.setFactname(rs.getString("name"));
            		gibi.setShortname(rs.getString("shortname"));
                    gibi.setDeptid(rs.getInt("deptid"));
                    gibi.setJine(rs.getDouble("allprice1"));
                    gibi.setJine1(rs.getDouble("allprice2"));
                    gibi.setId(rs.getInt("allcount"));
                    ibsri.rslist.add(gibi);
                    if(!rs.next())
	            		break;
            	}
            }
            rs.close();
            rs = stmt.executeQuery(strSQL1);
            if(rs.next())
            {
            	ibsri.allmoney=oConvert.getRound(rs.getDouble(1),2);
            	ibsri.allsumnum=oConvert.getRound(rs.getDouble(2),2);
            	
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
        return ibsri;
    }
	
//	取得应付运费汇总
	@SuppressWarnings("unchecked")
	public ResultsetList getYunFeiGroupByFac(PayHuoKuanForm phf) throws Exception
    {
		ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
        String whereStr = "";
        strSQL = "SELECT SUM(a.yunfei) AS allprice,count(*) as allcount,a.deptid,a.carno,b.driver,b.account,c.shortname FROM "+
        "TabGoodsImportInfo a left JOIN TabTruck b ON a.carno = b.carno inner join TabDepartInfo c on a.deptid=c.id WHERE (a.PayshipTime IS NULL) AND (a.ConfirmFlage = '1') ";
        
        strSQL1 = "SELECT SUM(a.yunfei) AS allprice FROM TabGoodsImportInfo a left JOIN TabTruck b ON a.carno = b.carno "+
        "inner join TabDepartInfo c on a.deptid=c.id WHERE (a.PayshipTime IS NULL) AND (a.ConfirmFlage = '1') ";
        whereStr=" and a.yunfei>0";
        if(phf.getDeptid()>0)
        	whereStr=whereStr+" and a.deptid="+phf.getDeptid();
        if(phf.getZhujima().length()>0)
        	whereStr=whereStr+" and (a.carno like '%"+phf.getZhujima()+"%' or b.driver like '%"+phf.getZhujima()+"%')";
        strSQL=strSQL+whereStr;
        strSQL1=strSQL1+whereStr;
        strSQL=strSQL+" GROUP BY a.carno,b.driver,b.account,a.deptid,c.shortname";
        if(phf.getOrderby().length()>0)
        	strSQL=strSQL+" order by "+phf.getOrderby();
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(strSQL);
            ImportBillForm gibi;
            while(rs.next())
        	{
        		gibi= new ImportBillForm();
                gibi.setTotalPrice(rs.getDouble("allprice"));
                gibi.setCarNo(rs.getString("carno"));
                gibi.setDriver(oConvert.getString(rs.getString("driver"),""));
                gibi.setAllcount(rs.getInt("allcount"));
                gibi.setShortname(rs.getString("shortname"));
                gibi.setDeptid(rs.getInt("deptid"));
                gibi.setPrepay1(rs.getDouble("account"));
                ibsri.rslist.add(gibi);
                
        	}
            rs.close();
            rs = stmt.executeQuery(strSQL1);
            if(rs.next())
            {
            	ibsri.allmoney=oConvert.getRound(rs.getDouble(1),2);
            }
            rs.close();
            
            strSQL = "SELECT SUM(a.yunfei) AS allprice,count(*) as allcount,a.deptid,a.carno,b.driver,b.account,c.shortname FROM "+
            "TabGoodsExportInfo a left JOIN TabTruck b ON a.carno = b.carno inner join TabDepartInfo c on a.deptid=c.id WHERE (a.PayshipTime IS NULL) AND (a.ConfirmFlage = '1') ";
            
            strSQL1 = "SELECT SUM(a.yunfei) AS allprice FROM TabGoodsExportInfo a left JOIN TabTruck b ON a.carno = b.carno "+
            "inner join TabDepartInfo c on a.deptid=c.id WHERE (a.PayshipTime IS NULL) AND (a.ConfirmFlage = '1') ";
            whereStr=" and a.yunfei>0";
            if(phf.getDeptid()>0)
            	whereStr=whereStr+" and a.deptid="+phf.getDeptid();
            if(phf.getZhujima().length()>0)
            	whereStr=whereStr+" and (a.carno like '%"+phf.getZhujima()+"%' or b.driver like '%"+phf.getZhujima()+"%')";
            
            strSQL=strSQL+whereStr;
            strSQL1=strSQL1+whereStr;
            strSQL=strSQL+" GROUP BY a.carno,b.driver,b.account,a.deptid,c.shortname";
            if(phf.getOrderby().length()>0)
            	strSQL=strSQL+" order by "+phf.getOrderby();
            rs = stmt.executeQuery(strSQL);
            while(rs.next())
        	{
            	boolean bflag=false;
        		for(int i=0;i<ibsri.rslist.size();i++)
        		{
        			gibi=(ImportBillForm)ibsri.rslist.get(i);
        			if(gibi.getCarNo().equals(rs.getString("carno")))
        			{
        				gibi.setAllcount(gibi.getAllcount()+1);
        				gibi.setTotalPrice(gibi.getTotalPrice()+rs.getDouble("allprice"));
        				bflag=true;
        				break;
        			}
        			
        		}
        		if(!bflag)
    			{
    				gibi= new ImportBillForm();
                    gibi.setTotalPrice(rs.getDouble("allprice"));
                    gibi.setCarNo(rs.getString("carno"));
                    gibi.setDriver(oConvert.getString(rs.getString("driver"),""));
                    gibi.setAllcount(rs.getInt("allcount"));
                    gibi.setShortname(rs.getString("shortname"));
                    gibi.setDeptid(rs.getInt("deptid"));
                    gibi.setPrepay1(rs.getDouble("account"));
                    ibsri.rslist.add(gibi);
    			}
                
        	}
            rs.close();
            rs = stmt.executeQuery(strSQL1);
            if(rs.next())
            {
            	ibsri.allmoney=ibsri.allmoney+oConvert.getRound(rs.getDouble(1),2);
            }
            rs.close();
            
            double totalPrepay=0;
            double totalown=0;
            for(int i=0;i<ibsri.rslist.size();i++)
            {
            	gibi=(ImportBillForm)ibsri.rslist.get(i);
            	strSQL1 = " SELECT account FROM Tabtruck where carno='"+gibi.getCarNo()+"' and deptid="+gibi.getDeptid();
            	rs = stmt.executeQuery(strSQL1);
            	if(rs.next())
            	{
            		totalPrepay=totalPrepay+rs.getDouble("account");
            		if(gibi.getTotalPrice()>0)
            		{
            			if(rs.getDouble("account")<gibi.getTotalPrice())
            				totalown=totalown+(gibi.getTotalPrice()-rs.getDouble("account"));
            		}
            		else
            			totalown=totalown+gibi.getTotalPrice();
            	}
            	else
            		totalown=totalown+gibi.getTotalPrice();
                
            }
            ibsri.realmoney=oConvert.getRound(totalPrepay,2);
            ibsri.oddmoney=oConvert.getRound(totalown,2);
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
	
//	取得当前预收款
	@SuppressWarnings("unchecked")
	public List<String[]> getYuShouKuan(int deptid) throws Exception
    {
		List <String[]> ibsri=new ArrayList();
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
            strSQL = "SELECT a.*,b.name from TabPrepay a inner join TabFactory b on a.factory=b.id where preshou>0 "+
            " and a.deptid="+deptid+" order by preshou desc";
            rs = stmt.executeQuery(strSQL);
            String []tmp=null;
            while(rs.next())
        	{
        		tmp= new String[2];
                tmp[0]=rs.getString("name");
                tmp[1]=rs.getString("preshou");
        		ibsri.add(tmp);
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
        return ibsri;
    }
//	取得当前预付款
	@SuppressWarnings("unchecked")
	public List<String[]> getYuFuKuan(int deptid) throws Exception
    {
		List <String[]> ibsri=new ArrayList();
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
            strSQL = "SELECT a.*,b.name from TabPrepay a inner join TabFactory b on a.factory=b.id where prepay>0"+
            " and a.deptid="+deptid+" order by preshou desc";
            rs = stmt.executeQuery(strSQL);
            String []tmp=null;
            while(rs.next())
        	{
        		tmp= new String[2];
                tmp[0]=rs.getString("name");
                tmp[1]=rs.getString("prepay");
        		ibsri.add(tmp);
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
        return ibsri;
    }
	
	//其他应收
	@SuppressWarnings("unchecked")
	public List<String[]> getQiTaYingShouKuan(int deptid) throws Exception
    {
		List <String[]> ibsri=new ArrayList();
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
            strSQL = "SELECT b.name,sum(-jine) as totalprice from TabOtherToPay a inner join TabFactory b on a.factory=b.id where "+
            " a.deptid="+deptid+" group by b.name order by sum(jine)";
            rs = stmt.executeQuery(strSQL);
            String []tmp=null;
            while(rs.next())
        	{
        		tmp= new String[2];
                tmp[0]=rs.getString("name");
                tmp[1]=rs.getString("totalprice");
        		ibsri.add(tmp);
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
        return ibsri;
    }
	public void insertInitNeedPay(List<HashMap> dataList,int deptid,String username,String importtype) throws Exception
    {
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        int retNum=0;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            for(HashMap hm : dataList)
            {
            	if(hm==null)continue;
            	String line=String.valueOf(hm.get("行数"));
            	String supplyName="";
            	String supplyTel="";
            	String supplyLink="";
            	String importtime="";
            	String supplytype="";
            	if(importtype.equals("import"))
            	{
            		supplyName=String.valueOf(hm.get("供应商"));
            		BigDecimal bd = new BigDecimal(String.valueOf(hm.get("供应商电话"))); 
            		supplyTel = bd.toPlainString();
            		supplyLink=String.valueOf(hm.get("供应商联系人"));
            		importtime=String.valueOf(hm.get("进货时间"));
            		supplytype="1000";
            	}
            	else if(importtype.equals("export"))
            	{
            		supplyName=String.valueOf(hm.get("客户"));
            		BigDecimal bd = new BigDecimal(String.valueOf(hm.get("客户电话"))); 
            		supplyTel = bd.toPlainString();
            		supplyLink=String.valueOf(hm.get("客户联系人"));
            		importtime=String.valueOf(hm.get("销售时间"));
            		supplytype="0010";
            	}
            	if(importtype.equals("import") || importtype.equals("export"))
            	{
	            	double num=Double.valueOf((String) hm.get("数量（吨）"));
	            	double jine=Double.valueOf((String) hm.get("金额（元）"));
	            	
	            	SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
	            	Date date=sdf1.parse(importtime);
	            	DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd"); 
	            	importtime=format1.format(date);
	            	String beizhu="";
	            	if(hm.get("备注")!=null)
	            		beizhu=String.valueOf(hm.get("备注"));
	            	if(supplyName!=null && supplyName.length()>0)
	            	{
	            		strSQL="select id from tabfactory where name='"+supplyName+"'";
	            		rs = stmt.executeQuery(strSQL);
	            		int supplyid=0;
	            		if(!rs.next())
	            		{
	            			String zhujima="";
	            			for(int i=0;i<supplyName.length();i++)
	                        {
	                        	String wd=supplyName.substring(i,i+1);
	                        	strSQL = "select * from hzpy where hz='" + wd+"'";
	                        	rs = stmt.executeQuery(strSQL);
	                        	if(rs.next())
	                        		zhujima=zhujima+rs.getString("py");
	                        }
	            			strSQL="insert into tabfactory (name,linkman,tel,kind,zhujima,deptid,townid,address,yewuyuan) values('"+supplyName+"','"+supplyLink+"','"+supplyTel+"','"+supplytype+"','"+zhujima+"','"+deptid+"','1114','','0')";
	            			stmt.executeUpdate(strSQL,Statement.RETURN_GENERATED_KEYS);
	            			rs = stmt.getGeneratedKeys();    
	                        if (rs.next()) {    
	                            supplyid = rs.getInt(1);    
	                        } 
	            		}
	            		else
	            			supplyid=rs.getInt("id");
	            		if(supplyid>0)
	                    {
	            			int billid=0;
	            			if(importtype.equals("import"))
	            			{
		            			strSQL = "select imbillid from TabDepartInfo where id="+deptid;
		                        rs=stmt.executeQuery(strSQL);
		                        if(rs.next())
		                        	billid=rs.getInt("imbillid")+1;
		                        else
		                        	throw new Exception("分公司不存在");
		                        strSQL = "update TabDepartInfo set imbillid=imbillid+1 where id="+deptid;
		                        int i=stmt.executeUpdate(strSQL);
		                        if(i!=1)
		                        	throw new Exception("更新入库单号失败");
		                    	strSQL="insert into tabgoodsimportinfo (billid,createperson,totalnum,totalprice,importtime,factory,confirmflage,deptid,kind,memo,fktype,payLimTime,fptype) values"+
		                        	"('"+billid+"','"+username+"','"+num+"','"+jine+"','"+importtime+"','"+supplyid+"','1','"+deptid+"','1','"+beizhu+"','1','"+importtime+"','1')";
		                    	int row=stmt.executeUpdate(strSQL);
		                    	if(row==0)
		                    		throw new Exception("第"+line+"行,插入进货单失败");
	            			}
	            			else
	            			{
	            				strSQL = "select exbillid from TabDepartInfo where id="+deptid;
		                        rs=stmt.executeQuery(strSQL);
		                        if(rs.next())
		                        	billid=rs.getInt("exbillid")+1;
		                        else
		                        	throw new Exception("分公司不存在");
		                        strSQL = "update TabDepartInfo set exbillid=exbillid+1 where id="+deptid;
		                        int i=stmt.executeUpdate(strSQL);
		                        if(i!=1)
		                        	throw new Exception("更新出库单号失败");
		                    	strSQL="insert into tabgoodsexportinfo (billid,salesperson,totalnum,totalprice,exporttime,factory,confirmflage,deptid,kind,memo,fktype,payLimTime,fptype,tihuo) values"+
		                        	"('"+billid+"','"+username+"','"+num+"','"+jine+"','"+importtime+"','"+supplyid+"','1','"+deptid+"','1','"+beizhu+"','1','"+importtime+"','1','自提')";
		                    	int row=stmt.executeUpdate(strSQL);
		                    	if(row==0)
		                    		throw new Exception("第"+line+"行，插入销售单失败");
	            			}
	                    }
	            		else
	            			throw new Exception("第"+line+"行,供应商 或客户名称 "+supplyName+" 创建失败");
	            			
	            	}
	            	else
	            		throw new Exception("第"+line+"行,供应商或客户名称不能为空");
            	}
            	else if(importtype.equals("store"))
            	{
            		String storeName=String.valueOf(hm.get("仓库名"));
            		String goodsId=String.valueOf(hm.get("产品编号"));
            		double num=Double.valueOf((String) hm.get("数量（吨）"));
	            	double price=Double.valueOf((String) hm.get("成本价（元）"));
	            	String beizhu="";
	            	if(hm.get("备注")!=null)
	            		beizhu=String.valueOf(hm.get("备注"));
	            	if(storeName!=null && storeName.length()>0)
	            	{
	            		strSQL="select id from tabstore where name='"+storeName+"'";
	            		rs = stmt.executeQuery(strSQL);
	            		int storeid=0;
	            		if(!rs.next())
	            		{
	            			strSQL="insert into tabstore (name,address,linkman,tel,mobile,deptid,ifdel) values('"+storeName+"','','','','','"+deptid+"',0)";
	            			stmt.executeUpdate(strSQL,Statement.RETURN_GENERATED_KEYS);
	            			rs = stmt.getGeneratedKeys();    
	                        if (rs.next()) {    
	                        	storeid = rs.getInt(1);    
	                        } 
	            		}
	            		else
	            			storeid=rs.getInt("id");
	            		if(storeid>0)
	                    {
	            			int shuliang=0;
	            			strSQL = "select xishu from TabGoodsInfo where goodsid='"+goodsId+"'";
	                        rs=stmt.executeQuery(strSQL);
	                        if(!rs.next())
	                        	throw new Exception("第"+line+"行，产品库中不存在编号为"+goodsId+"的产品");
	                        else
	                        {
	                        	if(rs.getDouble("xishu")>0)
	                        	shuliang=(int)(num/rs.getDouble("xishu"));
	                        }
	                        strSQL="select * from tabgoodsrepertory where goodsid='"+goodsId+"' and storeid='"+storeid+"'";
	                        rs = stmt.executeQuery(strSQL);
	                        if(rs.next())
	                        	throw new Exception("第"+line+"行，仓库中已存在编号为 "+goodsId+" 的产品");
	                    	strSQL="insert into tabgoodsrepertory (goodsid,repertoryAmount,storeid,avgprice,repertoryNum,memo) values"+
	                        	"('"+goodsId+"','"+num+"','"+storeid+"','"+price+"','"+shuliang+"','"+beizhu+"')";
	                    	int row=stmt.executeUpdate(strSQL);
	                    	if(row==0)
	                    		throw new Exception("第"+line+"行"+goodsId+",插入库存失败");

	            			
	                    }
	            		else
	            			throw new Exception("第"+line+"行,仓库名 "+supplyName+" 创建失败");
	            	}
            	}
            }
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
	
}
