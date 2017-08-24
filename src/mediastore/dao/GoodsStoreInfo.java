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
import mediastore.user.StoreManager;
import mediastore.util.oConvert;
import mediastore.web.form.ChangeStoreBillForm;
import mediastore.web.form.ChangeStoreGoodsForm;
import mediastore.web.form.GoodsInfoForm;
import mediastore.web.form.KaiPingForm;
import mediastore.web.form.KaiPingInfoForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.global.Globals;


public class GoodsStoreInfo
{

    public GoodsStoreInfo()
    {
    }
    //取得售货员的最大订单ID
    public int getTempBillIDNum(String saleman,int deptid) throws Exception
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
            
            strSQL = "SELECT max(BillID) FROM TabChangeStoreInfo WHERE ConfirmFlage='0' and createperson='"+saleman+"'";
            rs = stmt.executeQuery(strSQL);
            if(rs.next() && rs.getInt(1)!=0)
                billid = rs.getInt(1);
            else
            {
            	while(billid==0)
            	{
	            	billid=(int)(Math.random()*(-1000));
	            	strSQL = "SELECT * FROM TabChangeStoreInfo WHERE billid="+billid;
	            	rs = stmt.executeQuery(strSQL);
	            	if(rs.next())
	            		billid=0;
            	}
            	strSQL = "insert into TabChangeStoreInfo( BillID,createPerson,totalnum,totalprice,deptid,memo) "+
            	" values (" + billid + ",'"+saleman+"',0,0,"+deptid+",'')"; 
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
    public int insertNewBillRec(ChangeStoreGoodsForm gif) throws Exception
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
            strSQL="select * from TabChangeStoreInfo where billid="+gif.getBillId()+" and deptid="+gif.getDeptid()+" and confirmflage='0'";
            rs=stmt.executeQuery(strSQL);
            rs.next();
            int fromstore=rs.getInt("fromstore");
            int tostore=rs.getInt("tostore");
            if(fromstore==0)
            	throw new Exception("转入仓库不能为空");
            if(tostore==0)
            	throw new Exception("转出仓库不能为空");
            rs.close();
            
            strSQL = "select * from TabChangeStoreGoods WHERE BillID = " + gif.getBillId()+" and deptid="+gif.getDeptid();
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	avgprice=gi.getAvgInPrice(conn,rs.getString("goodsid"), rs.getInt("deptid"));
                strSQL = "UPDATE TabChangeStoreGoods SET changeUnitPrice="+avgprice+" WHERE id =" + rs.getInt("id");
                nRet=stmt1.executeUpdate(strSQL);
                if(nRet != 1)
                    throw new Exception("更新单据明细错误");
            }
            rs.close();
            strSQL = "select sum(changeamount) as totalnum,sum(changeamount*changeUnitprice) as totalPrice from"+
            " TabChangeStoreGoods WHERE BillID=" + gif.getBillId()+" and deptid="+gif.getDeptid();
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	totalNum=oConvert.getRound(rs.getDouble(1),4);
            	totalPrice=oConvert.getRound(rs.getDouble(2),2);
            }
            if(totalNum<=0 || totalPrice<=0)
            	throw new Exception("转仓重量或金额必须大于0");
            rs.close();
            //获取单号
            int newbillid=0;
            strSQL = "select stbillid from TabDepartInfo where id="+gif.getDeptid();
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	newbillid=rs.getInt(1)+1;
            else
            	throw new Exception("分公司不存在");
            strSQL = "update TabDepartInfo set stbillid=stbillid+1 where id="+gif.getDeptid();
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新单号失败");
            
            strSQL = "update TabChangeStoreInfo set billid="+newbillid+",totalnum="+totalNum+",totalprice="+totalPrice+",createTime=getDate(),confirmflage='1' where "+
            " billid="+gif.getBillId()+" and deptid="+gif.getDeptid()+" and confirmflage='0'"; 
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
            {
                nRet = -1;
                throw new Exception("更新单据失败");
            }
            strSQL = "UPDATE TabChangeStoreGoods SET billid="+newbillid+",ConfirmFlage = '1' WHERE BillID =" + gif.getBillId()+" and deptid="+gif.getDeptid();
            nRet=stmt.executeUpdate(strSQL);
            if(nRet < 1)
            {
                nRet = -1;
                throw new Exception("更新单据明细错误");
            }
            gif.setBillId(newbillid);
            strSQL = "select * from TabChangeStoreGoods WHERE BillID = " + newbillid+" and deptid="+gif.getDeptid();
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
        		double kucun=gi.getRepertoryAmount(rs.getString("goodsid"),fromstore);
                if(kucun-rs.getDouble("changeamount")<0)
                	throw new Exception("编号为"+rs.getString("goodsid")+"的产品库存不足，仅剩"+kucun+"吨");
            	
            	strSQL = "UPDATE TabGoodsRepertory SET RepertoryAmount=RepertoryAmount-(" + rs.getString("changeamount") + 
            	"),RepertoryNum=RepertoryNum-(" + rs.getString("changeNum") + 
            	") WHERE GoodsID='" +rs.getString("goodsid")+ "' and storeid="+fromstore;
            	nRet=dbc.executeUpdate(stmt1,strSQL);	
            	if(nRet != 1)
                {
                    nRet = -1;
                    throw new Exception("更新转出库存错误");
                }
            	strSQL="select * from TabGoodsRepertory where goodsid='"+rs.getString("goodsid")+"' and storeid="+tostore;
            	rs1=stmt1.executeQuery(strSQL);
            	if(rs1.next())
            		strSQL = "UPDATE TabGoodsRepertory SET RepertoryAmount=RepertoryAmount+(" + rs.getString("changeamount") + 
                	"),RepertoryNum=RepertoryNum+(" + rs.getString("changeNum") + 
                	") WHERE GoodsID='" +rs.getString("goodsid")+ "' and storeid="+tostore;
            	else
            	{
            		
            		strSQL="insert into TabGoodsRepertory (goodsid,RepertoryAmount,RepertoryNum,storeid,avgprice) values('"+rs.getString("goodsid")+
            		"',"+rs.getString("changeamount")+","+rs.getString("changeNum")+","+tostore+","+rs.getDouble("changeUnitPrice")+")";
            	}
            	nRet=dbc.executeUpdate(stmt1,strSQL);	
            	if(nRet != 1)
                {
                    nRet = -1;
                    throw new Exception("更新转入库存错误");
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
    public ChangeStoreBillForm getChangeStoreBillById(int billId,int deptid) throws Exception
    {
    	ChangeStoreBillForm ibf=null;
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
            strSQL = "SELECT a.*,b.name FROM TabChangeStoreInfo a inner join TabDepartInfo b on a.deptid=b.id WHERE BillID=" + billId+" and deptid="+deptid;
            rs = stmt.executeQuery(strSQL);
            StoreManager sm=new StoreManager();
            if(rs.next())
            {
            	ibf=new ChangeStoreBillForm();
            	ibf.setBillId(billId);
            	ibf.setCreatePerson(rs.getString("createperson"));
            	ibf.setCreateTime(oConvert.FormDateShort(rs.getTimestamp("createTime")));
            	ibf.setTotalNum(rs.getDouble("totalnum"));
            	ibf.setTotalPrice(rs.getDouble("totalprice"));
            	ibf.setDeptid(rs.getInt("deptid"));
            	ibf.setFromStore(rs.getInt("fromstore"));
            	ibf.setToStore(rs.getInt("tostore"));
            	if(ibf.getFromStore()>0)
            		ibf.setFromname(sm.getDepartByID(ibf.getFromStore()).getName());
            	if(ibf.getToStore()>0)
            		ibf.setToname(sm.getDepartByID(ibf.getToStore()).getName());
            	ibf.setShortname(rs.getString("name"));
            	ibf.setMemo(rs.getString("memo"));
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
            strSQL = "SELECT DISTINCT BillID FROM TabChangeStoreInfo where confirmflage='1'";
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
            strSQL = "SELECT DISTINCT createperson FROM TabChangeStoreInfo";
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
	
    //转仓单记录查询
	@SuppressWarnings("unchecked")
	public ResultsetList getBillListByRule(ChangeStoreBillForm ibf,String exportTime1,String exportTime2,int curPage,String orderby) throws Exception
    {
		ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
     
        strSQL = " SELECT a.*,b.shortname FROM TabChangeStoreInfo a inner join TabDepartInfo b on a.deptid=b.id where a.confirmflage='1'";
        strSQL1 = " SELECT sum(totalnum) as allnum,sum(totalprice) as allprice FROM TabChangeStoreInfo a where confirmflage='1'";
        String whereStr = "";
        if(ibf.getBillId()!= 0)
            whereStr = whereStr + " and BillID=" + ibf.getBillId();
        if(!ibf.getCreatePerson().equals(""))
            whereStr = whereStr + " and createPerson='" + ibf.getCreatePerson()+"'";
        if(!exportTime1.equals(""))
        	whereStr = whereStr + " and createTime >= '" + exportTime1 + "'";
        if(!exportTime2.equals(""))
        	whereStr = whereStr + " and createTime <='" + exportTime2 + "'";
        if(ibf.getDeptid()>0)
        	whereStr = whereStr +" and a.deptid="+ibf.getDeptid();
        if(ibf.getFromStore()>0)
        	whereStr = whereStr +" and a.fromstore="+ibf.getFromStore();
        if(ibf.getToStore()>0)
        	whereStr = whereStr +" and a.tostore="+ibf.getToStore();
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
            	StoreManager sm=new StoreManager();
            	for(int i=0;i<ibsri.pagesize;i++)
            	{
            		ChangeStoreBillForm gibi = new ChangeStoreBillForm();
                    gibi.setBillId(rs.getInt("billid"));
                    gibi.setCreatePerson(rs.getString("CreatePerson"));
                    gibi.setTotalNum(rs.getDouble("totalnum"));
                    gibi.setTotalPrice(rs.getDouble("totalprice"));
                    gibi.setCreateTime(oConvert.FormDateTimeShort(rs.getTimestamp("createTime")));
                    gibi.setDeptid(rs.getInt("deptid"));
                    gibi.setShortname(rs.getString("shortname"));
                    gibi.setFromStore(rs.getInt("fromstore"));
                    gibi.setToStore(rs.getInt("tostore"));
                    gibi.setFromname(sm.getDepartByID(gibi.getFromStore()).getName());
                    gibi.setToname(sm.getDepartByID(gibi.getToStore()).getName());
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

	public int UpdateBillRec(int billid,int deptid,int fromstore,int tostore,String memo) throws Exception
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
            strSQL="update TabChangeStoreInfo set fromstore="+(fromstore>0?fromstore:"null")+
            ",tostore="+(tostore>0?tostore:"null")+
            ",memo='"+memo+"'"+
            " where billid="+billid+" and deptid="+deptid+" and confirmFlage='0'";
            nRet = stmt.executeUpdate(strSQL);
        	if(nRet != 1)
        		throw new Exception("更新单据失败");
            
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
	//	插入新的开平记录
    public int insertNewKaiPingRec(KaiPingForm gif,String deptname) throws Exception
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
        double jine=0;
        double totalnum1=0;
        double totalnum2=0;
        double totalprice=0;
        int billid=0;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt1 = conn.createStatement();
            GoodsInfo gi=new GoodsInfo();
            strSQL = "select sum(jine) from TabKaiPingGoods WHERE billid="+gif.getBillid()+" and createPerson='" + gif.getCreatePerson()+"' and deptid="+gif.getDeptid()+" and confirmflage='0' and kind=-1";
            rs=stmt.executeQuery(strSQL);
            rs.next();
            jine=rs.getDouble(1);
            strSQL = "select sum(jine) from TabKaiPingGoods WHERE billid="+gif.getBillid()+" and createPerson='" + gif.getCreatePerson()+"' and deptid="+gif.getDeptid()+" and confirmflage='0' and kind=1";
            rs=stmt.executeQuery(strSQL);
            rs.next();
            if(oConvert.getRound(jine,2)!=oConvert.getRound(rs.getDouble(1),2))
            	throw new Exception("原料总金额不等于成品总金额,请调整成品金额");
            //从新获得平均价
            strSQL = "select * from TabKaiPingGoods WHERE billid="+gif.getBillid()+" and createPerson='" + gif.getCreatePerson()+"' and deptid="+gif.getDeptid()+" and confirmflage='0' and kind=-1";
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	billid=rs.getInt("billid");
            	avgprice=gi.getAvgInPrice(conn,rs.getString("goodsid"), rs.getInt("deptid"));
                if(avgprice!=rs.getDouble("price"))
                {
                	strSQL="update TabKaiPingGoods set price="+avgprice+",jine="+oConvert.getRound(avgprice*rs.getDouble("amount"), 2)+" where id="+rs.getInt("id");
                	stmt1.executeUpdate(strSQL);
                	throw new Exception(rs.getString("goodsid")+"的成本单价已改变，请重新调整成品金额");
                }
                
                double kucun=gi.getRepertoryAmount(rs.getString("goodsid"),rs.getInt("storeid"));
                if(kucun-rs.getDouble("amount")<0)
                	throw new Exception("编号为"+rs.getString("goodsid")+"的产品库存不足，仅剩"+kucun+"吨");
            	
            	strSQL = "UPDATE TabGoodsRepertory SET RepertoryAmount=RepertoryAmount-(" + rs.getString("amount") + 
            	"),RepertoryNum=RepertoryNum-(" + rs.getString("Num") + 
            	") WHERE GoodsID='" +rs.getString("goodsid")+ "' and storeid="+rs.getInt("storeid");
            	nRet=dbc.executeUpdate(stmt1,strSQL);	
            	if(nRet != 1)
                {
                    nRet = -1;
                    throw new Exception("更新产"+rs.getString("goodsid")+"库存时出错");
                }
            	totalnum1=totalnum1+rs.getDouble("amount");
            	totalprice=totalprice+oConvert.getRound(rs.getDouble("jine"),2);
            }
            strSQL = "select * from TabKaiPingGoods WHERE billid="+gif.getBillid()+" and createPerson='" + gif.getCreatePerson()+"' and deptid="+gif.getDeptid()+" and confirmflage='0' and kind=1";
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	//计算加权平均价
                avgprice=gi.CountAvgPrice(rs.getString("goodsid"), rs.getInt("deptid"), rs.getDouble("amount"), rs.getDouble("price"),conn);
            	strSQL="select * from TabGoodsRepertory where goodsid='"+rs.getString("goodsid")+"' and storeid="+rs.getInt("storeid");
            	rs1=stmt1.executeQuery(strSQL);
            	if(rs1.next())
            		strSQL = "UPDATE TabGoodsRepertory SET RepertoryAmount=RepertoryAmount+(" + rs.getString("amount") + 
                	"),RepertoryNum=RepertoryNum+(" + rs.getString("Num") + 
                	") WHERE GoodsID='" +rs.getString("goodsid")+ "' and storeid="+rs.getInt("storeid");
            	else
            	{
            		
            		strSQL="insert into TabGoodsRepertory (goodsid,RepertoryAmount,RepertoryNum,storeid,avgprice) values('"+rs.getString("goodsid")+
            		"',"+rs.getString("amount")+","+rs.getString("Num")+","+rs.getInt("storeid")+","+avgprice+")";
            	}
            	nRet=dbc.executeUpdate(stmt1,strSQL);	
            	if(nRet != 1)
                {
                    nRet = -1;
                    throw new Exception("更新产品"+rs.getString("goodsid")+"库存时出错");
                }	
            	//更新加权平均价
            	strSQL="update TabGoodsRepertory set avgprice="+avgprice+" where goodsid='"+rs.getString("goodsid")+
            	"' and storeid in (select id from TabStore where deptid="+rs.getInt("deptid")+")";
            	nRet=stmt1.executeUpdate(strSQL);
                totalnum2=totalnum2+rs.getDouble("amount");
            }
            rs.close();
            strSQL = "update TabGoodsRepertory set RepertoryAmount=0 where Round(RepertoryAmount,3)=0";
            nRet=stmt.executeUpdate(strSQL);
            strSQL = "update TabDepartInfo set kpbillid=kpbillid+1 where id="+gif.getDeptid();
            stmt.executeUpdate(strSQL);
            strSQL = "select kpbillid from TabDepartInfo where id="+gif.getDeptid();
            rs=stmt.executeQuery(strSQL);
            rs.next();
            billid=rs.getInt(1);
            strSQL = "update TabKaiPingGoods set billid="+billid+",confirmflage='1' WHERE billid="+gif.getBillid()+" and createPerson='" + gif.getCreatePerson()+"' and deptid="+gif.getDeptid()+" and confirmflage='0'";
            stmt.executeUpdate(strSQL);
            strSQL = "update TabKaiPingInfo set billid="+billid+",confirmflage='1',totalnum1="+totalnum1+",totalnum2="+totalnum2+",totalprice="+totalprice+" WHERE billid="+gif.getBillid()+" and createPerson='" + gif.getCreatePerson()+"' and deptid="+gif.getDeptid()+" and confirmflage='0'";
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
        return billid;
    }
    
    public int getKaiPingTempBill(String saleman,int deptid) throws Exception
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
            
            strSQL = "SELECT max(BillID) FROM TabKaiPingInfo WHERE ConfirmFlage='0' and createperson='"+saleman+"'";
            rs = stmt.executeQuery(strSQL);
            if(rs.next() && rs.getInt(1)!=0)
                billid = rs.getInt(1);
            else
            {
            	while(billid==0)
            	{
	            	billid=(int)(Math.random()*(-1000));
	            	strSQL = "SELECT * FROM TabKaiPingInfo WHERE billid="+billid;
	            	rs = stmt.executeQuery(strSQL);
	            	if(rs.next())
	            		billid=0;
            	}
            	strSQL = "insert into TabKaiPingInfo( BillID,createPerson,totalnum1,totalnum2,totalprice,deptid,memo) "+
            	" values (" + billid + ",'"+saleman+"',0,0,0,"+deptid+",'')"; 
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
    
    public KaiPingInfoForm getKaiPingBillById(int billId,int deptid) throws Exception
    {
    	KaiPingInfoForm ibf=null;
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
            strSQL = "SELECT a.*,b.name FROM TabKaiPingInfo a inner join TabDepartInfo b on a.deptid=b.id WHERE BillID=" + billId+" and deptid="+deptid;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	ibf=new KaiPingInfoForm();
            	ibf.setBillid(billId);
            	ibf.setCreateperson(rs.getString("createperson"));
            	ibf.setCreatetime(oConvert.FormDateShort(rs.getTimestamp("createTime")));
            	ibf.setTotalnum1(rs.getDouble("totalnum1"));
            	ibf.setTotalnum2(rs.getDouble("totalnum2"));
            	ibf.setTotalprice(rs.getDouble("totalprice"));
            	ibf.setDeptid(rs.getInt("deptid"));
            	ibf.setShortname(rs.getString("name"));
            	ibf.setMemo(rs.getString("memo"));
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
    public int UpdateKaiPingBillRec(int billid,int deptid,String memo) throws Exception
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
            strSQL="update TabKaiPingInfo set memo='"+memo+"'"+
            " where billid="+billid+" and deptid="+deptid+" and confirmFlage='0'";
            nRet = stmt.executeUpdate(strSQL);
        	if(nRet != 1)
        		throw new Exception("更新单据失败");
            
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
    
    //二次加工记录查询
	@SuppressWarnings("unchecked")
	public ResultsetList getKaiPingListByRule(KaiPingForm ibf,String exportTime1,String exportTime2,int curPage,String orderby) throws Exception
    {
		ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
        String strSQL2 = null;
        GoodsInfo gi=new GoodsInfo();
        GoodsInfoForm g=null;
        strSQL = " SELECT a.*,b.shortname FROM TabKaiPingGoods a inner join TabDepartInfo b on a.deptid=b.id where a.confirmflage='1'";
        strSQL1 = " SELECT sum(amount) as allnum,sum(jine) as allprice FROM TabKaiPingGoods"+
        " a where confirmflage='1' and kind=-1";
        strSQL2 = " SELECT sum(amount) as allnum FROM TabKaiPingGoods"+
        " a where confirmflage='1' and kind=1";
        String whereStr = "";
        if(ibf.getBillid()!= 0)
            whereStr = whereStr + " and BilliD=" + ibf.getBillid();
        if(!ibf.getCreatePerson().equals(""))
            whereStr = whereStr + " and createPerson='" + ibf.getCreatePerson()+"'";
        if(!exportTime1.equals(""))
        	whereStr = whereStr + " and createTime >= '" + exportTime1 + "'";
        if(!exportTime2.equals(""))
        	whereStr = whereStr + " and createTime <='" + exportTime2 + "'";
        if(ibf.getDeptid()>0)
        	whereStr = whereStr +" and a.deptid="+ibf.getDeptid();
        if(ibf.getFromGoodsId().length()>0)
        	whereStr = whereStr +" and a.goodsid='"+ibf.getFromGoodsId()+"'";
        if(ibf.getKind()!=0)
        	whereStr = whereStr +" and a.kind='"+ibf.getKind()+"'";
        strSQL = strSQL +whereStr;
        if(orderby.length()>0)
        	strSQL = strSQL + " Order by "+orderby;
        strSQL1=strSQL1+whereStr;
        strSQL2=strSQL2+whereStr;
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
            		KaiPingForm gibi = new KaiPingForm();
            		gibi.setId(rs.getInt("id"));
            		gibi.setBillid(rs.getInt("billid"));
            		gibi.setKind(rs.getInt("kind"));
                    gibi.setFromGoodsId(rs.getString("goodsid"));
                    g=gi.getGoodsInfoByGoodsId(gibi.getFromGoodsId());
                    gibi.setFromGuige(g.getGuige());
                    gibi.setFromGoodsName(g.getGoodsTypeName());
                    gibi.setFromNum(rs.getInt("Num"));
                    gibi.setCurRepertory(rs.getDouble("CurRepertory"));
                    gibi.setFromAmount(rs.getDouble("Amount"));
                    gibi.setFromPrice(rs.getDouble("Price"));
                    gibi.setJine(oConvert.getRound(rs.getDouble("jine"), 2));
                    gibi.setDanwei(g.getDanwei());
                    gibi.setCreatePerson(rs.getString("createPerson"));
                    gibi.setCreatetime(oConvert.FormDateTimeShort(rs.getTimestamp("createtime")));
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
            	ibsri.allsumnum=oConvert.getRound(rs.getDouble(1),3);
            	ibsri.allmoney=oConvert.getRound(rs.getDouble(2),2);
            }
            rs.close();
            rs = stmt.executeQuery(strSQL2);
            if(rs.next())
            {
            	ibsri.oddmoney=oConvert.getRound(rs.getDouble(1),3);
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
//	取得售货员所有单号列表
	@SuppressWarnings("unchecked")
	public List getKaiPingBillIdList(int deptid)
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
            strSQL = "SELECT DISTINCT BillID FROM TabKaiPingGoods where confirmflage='1'";
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
//	取得所有售货员列表
	@SuppressWarnings("unchecked")
	public List getKaiPingPersonsList(int deptid) throws Exception
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
            strSQL = "SELECT DISTINCT createperson FROM TabKaiPingGoods";
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
}
