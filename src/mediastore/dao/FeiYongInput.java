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
import mediastore.rule.FeiYongBillSearchRule;
import mediastore.rule.FeiYongItemSearchRule;
import mediastore.util.StrUtility;
import mediastore.util.oConvert;
import mediastore.web.form.FeiYongBillForm;
import mediastore.web.form.FeiYongGoodsForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.global.Globals;

// Referenced classes of package mediastore.dao:
//            GoodsImportInfo, GoodsInfo

public class FeiYongInput
{

    public FeiYongInput()
    {
    }

    public int insertNewGoodsRec(int billId,int deptid,int kind, int goodsType,String operPerson) throws Exception
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
            String goodsName="";
            strSQL="select * from FeiYongType where id="+goodsType;
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("不存在此费用类别");
            else
            	goodsName=rs.getString("feiyongType");
            rs.close();
            strSQL="select * from TabFeiYongGoods where billid="+billId+" and deptid="+deptid+" and typeid="+goodsType;
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("此单中已存在此费用类别");
        	strSQL = "insert into TabFeiYongGoods( BillID,deptid,kind,Typeid,typename,jine,memo,"+
        	"operPerson,confirmflage) values (" + billId + ", "+deptid+","+kind+"," +goodsType+",'"+goodsName+"',0,'','"+operPerson+"','0')"; 
        	nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
                throw new Exception("插入新记录错误");
            
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
	public List getGoodsList(int billId,int deptid,int kind) throws Exception
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
            strSQL = "SELECT a.*,c.feiyongclass FROM TabFeiYongGoods a inner join FeiyongType b on a.typeid=b.id inner join"+
            " FeiYongClass c on b.classid=c.id WHERE a.BillID=" + billId+" and a.deptid="+deptid+" and a.kind="+kind+" order by a.id";
            FeiYongGoodsForm cgf;
            for(rs = stmt.executeQuery(strSQL); rs.next(); goodsList.add(cgf))
            {
            	cgf = new FeiYongGoodsForm();
            	cgf.setId(rs.getInt("id"));
            	cgf.setBillid(rs.getInt("billid"));
            	cgf.setTypeid(rs.getInt("typeid"));
            	cgf.setTypeName(rs.getString("typename"));
            	cgf.setClassname(rs.getString("feiyongclass"));
            	cgf.setJine(rs.getDouble("jine"));
            	cgf.setOperPerson(rs.getString("operPerson"));
            	cgf.setCreateTime(oConvert.FormDateTimeShort(rs.getTimestamp("createTime")));
            	cgf.setMemo(rs.getString("memo"));
            	cgf.setJingshouren(oConvert.getString(rs.getString("jingshouren"),""));
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
	@SuppressWarnings("unchecked")
	public List getGiveGetList(int yewuid,String opertime1,String opertime2) throws Exception
    {
        List giveList;
        giveList = new ArrayList();
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
            strSQL = "SELECT * FROM TabGiveGet WHERE yewuid=" + yewuid;
            if(opertime1.length()>0)
            	strSQL=strSQL+" and opertime>='"+opertime1+"'";
            if(opertime2.length()>0)
            	strSQL=strSQL+" and opertime<='"+opertime2+"'";
            strSQL=strSQL+" order by opertime desc";
            String[] cgf;
            for(rs = stmt.executeQuery(strSQL); rs.next(); giveList.add(cgf))
            {
            	cgf = new String[4];
            	cgf[0]=rs.getString("autoid");
            	cgf[1]=rs.getString("jine");
            	cgf[2]=rs.getString("operPerson");
            	cgf[3]=oConvert.FormDateTimeShort(rs.getTimestamp("opertime"));
            	
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
        return giveList;
    }
    public void deleteGoodsRec(int id) throws Exception
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
            String strSQL = "DELETE FROM TabfeiyongGoods WHERE id=" + id;
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

    public void updateNum(int id, int caigouAmount) throws Exception
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
            strSQL = "UPDATE TabCaiGouGoods SET caigouAmount = " + caigouAmount + " WHERE ID = " + id;
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新记录失败");
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
    public void updatePrice(int id,double caigouPrice) throws Exception
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
            strSQL = "UPDATE TabCaigouGoods SET caigouPrice = " + caigouPrice + " WHERE ID = " + id;
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
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
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
    }
    public void updateMoney(int id,double caigouMoney) throws Exception
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
            
            strSQL = "UPDATE TabFeiYongGoods SET jine = " + oConvert.getRound(caigouMoney,2) + " WHERE ID = " + id;
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新金额失败");
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
    public void updateMemo(int id,String memo) throws Exception
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
            
            strSQL = "UPDATE TabFeiYongGoods SET memo ='" +memo + "' WHERE ID = " + id;
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新说明失败");
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
    public void updateJingShou(int id,String jingshou) throws Exception
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
            
            strSQL = "UPDATE TabFeiYongGoods SET jingshouren ='" +jingshou + "' WHERE ID = " + id;
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新经手人失败");
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
    public int deleteAllGoods(int billId,int deptid,int kind) throws Exception
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
            stmt = conn.createStatement();
            String strSQL = "DELETE FROM TabFeiYongGoods WHERE BillID=" + billId + " and deptid="+deptid+" and kind="+kind;
            nRet = stmt.executeUpdate(strSQL);
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
        return nRet;
    }
    public void updateBill(int billid,int deptid,int kind,int acctype,String dt) throws Exception
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
            strSQL = "UPDATE TabFeiYongInfo SET acctype="+acctype+",billtime='"+dt+"' WHERE billID = " +
            billid+" and deptid="+deptid+" and kind="+kind;
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新记录失败");
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
            strSQL = "UPDATE TabGoodsImportGoods SET ConfirmFlage = '1' WHERE BillID = " + billId;
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
    public int getTempBillIDNum(int deptid,String operPerson,int kind) throws Exception
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
            strSQL = "SELECT max(BillID) FROM TabFeiYongInfo WHERE ConfirmFlage='0' and kind="+kind+" and deptid="+deptid+" and operperson='"+operPerson+"'";
            rs = stmt.executeQuery(strSQL);
            if(rs.next() && rs.getInt(1)!=0)
                billid = rs.getInt(1);
            else
            {
            	while(billid==0)
            	{
	            	billid=(int)(Math.random()*(-1000));
	            	strSQL = "SELECT * FROM TabFeiYongInfo WHERE billid="+billid;
	            	rs = stmt.executeQuery(strSQL);
	            	if(rs.next())
	            		billid=0;
            	}
            	strSQL = "insert into TabFeiYongInfo( BillID,deptid,operPerson,kind) values (" + billid + ","+deptid+",'"+operPerson+"',"+kind+")"; 
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
    public FeiYongBillForm getFeiYongBillById(int billid,int deptid,int kind) throws Exception
    {
    	FeiYongBillForm cbf=null;
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
            strSQL = "SELECT a.*,b.name,c.accname from TabFeiYongInfo a inner join TabDepartInfo b on "+
            "a.deptid=b.id left join TabAccount c on a.acctype=c.id  WHERE a.billid="+billid+" and a.deptid="+deptid+" and a.kind="+kind;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	cbf=new FeiYongBillForm();
            	cbf.setBillId(billid);
            	cbf.setDeptid(rs.getInt("deptid"));
            	cbf.setShortname(rs.getString("name"));
            	cbf.setOperPerson(rs.getString("operPerson"));
            	cbf.setTotalPrice(rs.getDouble("totalPrice"));
            	cbf.setCreateTime(oConvert.FormDateTimeShort(rs.getTimestamp("createTime")));
            	cbf.setAcctype(rs.getInt("acctype"));
            	cbf.setAccName(rs.getString("accname"));
            	cbf.setKind(rs.getInt("kind"));
            	if(oConvert.isEmpty(rs.getString("billtime")))
            		cbf.setBillTime("");
            	else
            		cbf.setBillTime(oConvert.FormDateShort(rs.getTimestamp("billtime")));
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
    public int getMaxBillIDNum()
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
            strSQL = "SELECT max(BillID) FROM TabGoodsImportGoods;";
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            {
                nRet = -1;
                throw new Exception("获得最大进货单号失败");
            }
            nRet = rs.getInt(1);
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

    
	@SuppressWarnings("unchecked")
	public List getBillIdList(int kind)
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
            strSQL = "SELECT BillID FROM TabFeiYongInfo where confirmflage='1' and kind="+kind+" Order by BillID desc";
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
	public List getCreatePersonList(int deptid)
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
            strSQL = "SELECT DISTINCT operPerson FROM TabFeiYongInfo where deptid="+deptid+" Order by operPerson";
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
	public ResultsetList getBillListByRule(FeiYongBillSearchRule fbsr) throws Exception
    {
        ResultsetList iisri;
        iisri = new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
        
        strSQL = " SELECT a.*,b.shortname,c.accname FROM TabFeiYongInfo a ";
        strSQL1 = " SELECT sum(a.totalprice) FROM TabFeiYongInfo a ";

        String joinstr="inner join Tabdepartinfo b on a.deptid=b.id inner join TabAccount c on (a.acctype=c.id and a.deptid=c.deptid) where confirmflage='1'";
        strSQL=strSQL+joinstr;
        strSQL1=strSQL1+joinstr;
        String whereStr = " and kind=-1";
        if(fbsr.getFyf().getBillId() != 0)
            whereStr = whereStr + " and a.BillID=" + fbsr.getFyf().getBillId();
        if(fbsr.getFyf().getKind()!=0)
            whereStr = whereStr + " and a.kind=" + fbsr.getFyf().getKind();
        if(fbsr.getFyf().getDeptid()>0)
            whereStr = whereStr + " and a.deptID=" + fbsr.getFyf().getDeptid();
        if(fbsr.getFyf().getAcctype()>0)
            whereStr = whereStr + " and a.acctype=" + fbsr.getFyf().getAcctype();
        if(fbsr.getFyf().getOperPerson().length()>0)
            whereStr = whereStr + " and a.operPerson='" + fbsr.getFyf().getOperPerson() + "'";
        if(!fbsr.getCreatetime1().equals(""))
            whereStr = whereStr + " and a.createtime >='" + fbsr.getCreatetime1() + "'";
        if(!fbsr.getCreatetime2().equals(""))
            whereStr = whereStr + " and a.createtime <='" + fbsr.getCreatetime2() + "'";
        if(!fbsr.getPaymenttime1().equals(""))
            whereStr = whereStr + " and a.billtime >='" + fbsr.getPaymenttime1() + "'";
        if(!fbsr.getPaymenttime2().equals(""))
            whereStr = whereStr + " and a.billtime <='" + fbsr.getPaymenttime2() + "'";
        strSQL = strSQL + whereStr;
        strSQL1 = strSQL1 + whereStr;
        if(fbsr.getOrderStr().length()>0)
        	strSQL = strSQL + " Order by " + fbsr.getOrderStr();
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
            	iisri.curpage=fbsr.getCurpage();
            	iisri.pagesize=fbsr.getPagesize();
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
                    FeiYongBillForm giii = new FeiYongBillForm();
                    giii.setBillId(rs.getInt("billid"));
                    giii.setDeptid(rs.getInt("deptid"));
                    giii.setShortname(rs.getString("shortname"));
                    giii.setOperPerson(rs.getString("operPerson"));
                    giii.setTotalPrice(rs.getDouble("totalprice"));
                    giii.setCreateTime(oConvert.FormDateTimeShort(rs.getTimestamp("createtime")));
                    giii.setBillTime(oConvert.FormDateShort(rs.getTimestamp("billtime")));
                    giii.setKind(rs.getInt("kind"));
                    giii.setAcctype(rs.getInt("acctype"));
                    giii.setAccName(rs.getString("accname"));
                    iisri.rslist.add(giii);
                    if(!rs.next())
	            		break;
                }

            }
        	rs.close();
        	rs = stmt.executeQuery(strSQL1);
        	if(rs.next())
        		iisri.allmoney=rs.getDouble(1);
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
        return iisri;
    }
	@SuppressWarnings("unchecked")
	public ResultsetList getItemListByRule(FeiYongItemSearchRule fbsr) throws Exception
    {
        ResultsetList iisri;
        iisri = new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
        
        strSQL = " SELECT a.*,b.deptid,b.billtime,c.shortname FROM TabFeiYongGoods a ";
        strSQL1 = " SELECT sum(jine) FROM TabFeiYongGoods a ";

        String joinstr="inner join TabFeiYongInfo b on (a.billid=b.billid and a.deptid=b.deptid and a.kind=b.kind) inner join TabdepartInfo c "+
        "on b.deptid=c.id inner join FeiYongtype d on a.typeid=d.id where a.confirmflage='1' and a.kind="+fbsr.getFyf().getKind();
        strSQL=strSQL+joinstr;
        strSQL1=strSQL1+joinstr;
        String whereStr = "";
        if(fbsr.getFyf().getBillid()!= 0)
            whereStr = whereStr + " and a.BillID=" + fbsr.getFyf().getBillid();
        if(fbsr.getFyf().getDeptid()>0)
            whereStr = whereStr + " and b.deptid=" + fbsr.getFyf().getDeptid();
        if(!fbsr.getFyf().getOperPerson().equals(""))
            whereStr = whereStr + " and a.operPerson='" + fbsr.getFyf().getOperPerson() + "'";
        if(!fbsr.getCreatetime1().equals(""))
            whereStr = whereStr + " and a.createtime >='" + fbsr.getCreatetime1() + "'";
        if(!fbsr.getCreatetime2().equals(""))
            whereStr = whereStr + " and a.createtime <='" + fbsr.getCreatetime2() + "'";
        if(!fbsr.getPaymenttime1().equals(""))
            whereStr = whereStr + " and b.billtime >='" + fbsr.getPaymenttime1() + "'";
        if(!fbsr.getPaymenttime2().equals(""))
            whereStr = whereStr + " and b.billtime <='" + fbsr.getPaymenttime2() + "'";
        if(!(fbsr.getFyf().getTypeid()==0))
        	whereStr = whereStr + " and a.typeid =" + fbsr.getFyf().getTypeid();
        if(fbsr.getFyf().getTypeName().length()>0)
            whereStr = whereStr + " and a.typename like '%" + fbsr.getFyf().getTypeName() + "%'";
        if(fbsr.getFyf().getMemo().length()>0)
            whereStr = whereStr + " and a.memo like '%" + fbsr.getFyf().getMemo() + "%'";
        if(fbsr.getOper().length()>0 && fbsr.getFyf().getJine()!=0)
            whereStr = whereStr + " and a.jine*a.kind" + fbsr.getOper() + fbsr.getFyf().getJine();
        if(fbsr.getFyf().getClassid()>0)
        	whereStr = whereStr + " and d.classid =" + fbsr.getFyf().getClassid();
        strSQL = strSQL + whereStr;
        strSQL1 = strSQL1 + whereStr;
        if(fbsr.getOrderStr().length()>0)
        	strSQL = strSQL + " Order by " + fbsr.getOrderStr();
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
            	iisri.curpage=fbsr.getCurpage();
            	iisri.pagesize=Globals.REC_NUM_OF_PAGE;
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
                    FeiYongGoodsForm giii = new FeiYongGoodsForm();
                    giii.setId(rs.getInt("id"));
                    giii.setBillid(rs.getInt("billid"));
                    giii.setDeptid(rs.getInt("deptid"));
                    giii.setShortname(rs.getString("shortname"));
                    giii.setTypeid(rs.getInt("typeid"));
                    giii.setTypeName(rs.getString("typename"));
                    giii.setOperPerson(rs.getString("operPerson"));
                    giii.setJine(rs.getDouble("jine"));
                    giii.setMemo(rs.getString("memo"));
                    giii.setJingshouren(oConvert.getString(rs.getString("jingshouren")));
                    giii.setCreateTime(oConvert.FormDateTimeShort(rs.getTimestamp("createtime")));
                    giii.setBillTime(oConvert.FormDateShort(rs.getTimestamp("billtime")));
                    giii.setKind(rs.getInt("kind"));
                    iisri.rslist.add(giii);
                    if(!rs.next())
	            		break;
                }

            }
        	rs.close();
        	rs = stmt.executeQuery(strSQL1);
        	if(rs.next())
        		iisri.allmoney=rs.getDouble(1);
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
            strSQL = "UPDATE TabGoodsImportGoods SET GoodsName = '" + StrUtility.replaceString(newGoodsName, "'", "''") + "' WHERE GoodsID = '" + StrUtility.replaceString(goodsId, "'", "''") + "'";
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

    public int getRecCount1(String goodsId)
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
            strSQL = "SELECT count(*) FROM TabGoodsImportGoods WHERE GoodsID='" + StrUtility.replaceString(goodsId, "'", "''") + "' and ConfirmFlage='0'";
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
    
    public int submitCaiGouBill(int billId,int deptid,int kind,String dt,String operPerson) throws Exception
    {
    	int newbillid=0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            String strSQL = "select Round(sum(jine),2) from TabFeiYongGoods where billid="+billId+" and deptid="+
            deptid+" and kind="+kind;
            rs = stmt.executeQuery(strSQL);
            rs.next();
            double totalprice=rs.getDouble(1);
            if(totalprice==0)
            	throw new Exception("总金额不能为零");
            int acctype=0;
            strSQL = "select* from TabFeiYongInfo where billid="+billId+" and deptid="+deptid+" and kind="+kind;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	acctype=rs.getInt("acctype");
            }
            if(acctype==0)
            	throw new Exception("请选择一个操作帐户");
            if(kind==1)
            {
            	strSQL = "update TabDepartInfo set srbillid=srbillid+1 where id="+deptid;
            	stmt.executeUpdate(strSQL);
            	strSQL = "select srbillid from TabDepartInfo where id="+deptid;
            	rs = stmt.executeQuery(strSQL);
            	if(rs.next())
            		newbillid=rs.getInt("srbillid");
            }
            else if(kind==-1)
            {
            	strSQL = "update TabDepartInfo set fybillid=fybillid+1 where id="+deptid;
            	stmt.executeUpdate(strSQL);
            	strSQL = "select fybillid from TabDepartInfo where id="+deptid;
            	rs = stmt.executeQuery(strSQL);
            	if(rs.next())
            		newbillid=rs.getInt("fybillid");
            }
            if(newbillid==0)
            	throw new Exception("单号不正确");
            strSQL = "update TabfeiyongGoods set billid="+newbillid+",confirmflage='1' where billid="+billId+
            " and deptid="+deptid+" and kind="+kind;
            stmt.executeUpdate(strSQL);
            strSQL = "update TabAccount set jine=jine+("+totalprice+") where id="+acctype+" and deptid="+deptid;
        	stmt.executeUpdate(strSQL);
        	strSQL = "select jine from TabAccount where id="+acctype+" and deptid="+deptid;
            rs = stmt.executeQuery(strSQL);
            rs.next();
            double curjine=rs.getDouble(1);
            if(curjine<0)
            	throw new Exception("帐户余额不足！");
            strSQL = "update TabfeiyongInfo set billid="+newbillid+",confirmflage='1',totalprice="+totalprice+
            ",createtime=getDate(),billtime='"+dt+"' where billid="+billId+" and deptid="+deptid+" and kind="+kind;
            stmt.executeUpdate(strSQL);
            String typename="";
            if(totalprice>0)
            	typename="其他收入";
            else
            	typename="费用开支";
            strSQL = "insert into accessAccount (deptid,acctype,curjine,jine,operPerson,typename,billid) values("+deptid+
            ","+acctype+","+(curjine-totalprice)+","+totalprice+",'"+operPerson+"','"+typename+"',"+newbillid+")";
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
        return newbillid;
    }
    public void getFeiYongByDay(int yewuid,List<String[]> reportList,String dt1,String dt2)
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
            strSQL = "SELECT billtime,sum(totalprice) FROM TabFeiYongInfo WHERE billtime>='" + dt1+"' and billtime<='"+dt2+"' and confirmflage='1' and yewuid="+yewuid+" group by billtime order by billtime";
            rs = stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	String[] tmp;
            	for(int i=0;i<reportList.size();i++)
            	{
            		tmp=reportList.get(i);
            		if(oConvert.parseDate(tmp[0]).compareTo(rs.getDate(1))==0)
            		{
            			tmp[3]=rs.getString(2);
            			break;
            		}
            	}
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
    }
    
    @SuppressWarnings("unchecked")
	public List getFeiYongGroupByName(int deptid,int kind,String importtime1,String importtime2) throws Exception
    {
        List iisri=new ArrayList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        
        strSQL = " SELECT sum(jine*kind),typename FROM TabFeiYongGoods  where deptid="+deptid+" and kind="+
        kind+" and createtime>='"+importtime1+"' and createtime<='"+importtime2+"' group by typename";
       
        try
        {
        	dbc = new DBConnection();
        	conn = dbc.getDBConnection();
        	stmt = conn.createStatement();
        	rs = stmt.executeQuery(strSQL);
        	while(rs.next())
            {
                FeiYongGoodsForm giii = new FeiYongGoodsForm();
                giii.setTypeName(rs.getString("typename"));
                giii.setJine(rs.getDouble(1));
                iisri.add(giii);
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
        return iisri;
    }
}
