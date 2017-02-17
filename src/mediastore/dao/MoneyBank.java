package mediastore.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mediastore.common.DBConnection;
import mediastore.util.oConvert;
import mediastore.web.form.AccountForm;
import mediastore.web.form.OtherToPayForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.global.Globals;

public class MoneyBank 
{
	public MoneyBank()
	{
		
	}
	//	ȡ���ʻ�
	public List<AccountForm> getAccountList(int deptid) throws Exception
    {
        List<AccountForm> HYList;
        HYList = new ArrayList<AccountForm>();
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
            strSQL = "SELECT b.*,a.shortname from TabDepartInfo a inner join TabAccount b on a.id=b.deptid";
            if(deptid>0)
            	strSQL=strSQL+" where a.id="+deptid;
            rs=stmt.executeQuery(strSQL);
            AccountForm tmp;
            while(rs.next())
            {
                tmp = new AccountForm();
                tmp.setId(rs.getInt("id"));
                tmp.setAccName(rs.getString("accname"));
                tmp.setDeptid(rs.getInt("deptid"));
                tmp.setJine(rs.getDouble("jine"));
                tmp.setShortname(rs.getString("shortname"));
                tmp.setAccount(oConvert.getString(rs.getString("account"),""));
                HYList.add(tmp);
                
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
        return HYList;
    }
	
//	ȡ���ʻ�
	public AccountForm getAccountById(int id) throws Exception
    {
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        AccountForm tmp=null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "SELECT b.*,a.shortname from TabDepartInfo a inner join TabAccount b on b.id="+id;
            rs=stmt.executeQuery(strSQL);
            
            if(rs.next())
            {
                tmp = new AccountForm();
                tmp.setId(rs.getInt("id"));
                tmp.setAccName(rs.getString("accname"));
                tmp.setDeptid(rs.getInt("deptid"));
                tmp.setJine(rs.getDouble("jine"));
                tmp.setShortname(rs.getString("shortname"));
                tmp.setAccount(oConvert.getString(rs.getString("account"),""));
                
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
        return tmp;
    }
	//�½��ʻ�
	public int insertNewAccount(AccountForm af) throws Exception
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
            strSQL = "SELECT * FROM TabAccount where accname='"+af.getAccName()+"' and deptid="+af.getDeptid();
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("�Ѵ���ͬ���ʻ������������");
            rs.close();
            strSQL = "insert into TabAccount(accname,account,deptid,jine) values "+
            "('" +af.getAccName() + "', '" + af.getAccount()+"',"+af.getDeptid()+",0)";
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
                throw new Exception("�����¼�¼����");
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
	//�����ʻ�
    
    public int updateAccount(AccountForm af) throws Exception
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            String strSQL = "update TabAccount set accname='"+af.getAccName()+"',account='"+af.getAccount()+"' WHERE id = " + af.getId()+" and deptid="+af.getDeptid();         
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
                throw new Exception("���¼�¼ʧ��");
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
	//ɾ���ʻ�
    public int deleteAccount(AccountForm af) throws Exception
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            String strSQL = "select * from TabAccount where id=" + af.getId();
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("�޴��ʻ���");
            rs.close();
            strSQL="select * from accessAccount where acctype="+af.getId();
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("���ʻ��в�����¼������ɾ����");
            rs.close();
            strSQL = "delete  from TabAccount where id=" + af.getId()+" and deptid="+af.getDeptid();
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
                throw new Exception("ɾ���ʻ�ʧ��");
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
    
	//�ʻ���ת��
	public int accessSwitchMoney(AccountForm af,double accold) throws Exception
    {
		int nRet;
		int newbillid=0;
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
            strSQL="select jine from TabAccount where id="+af.getAccfrom()+" and deptid="+af.getDeptid();
            rs=stmt.executeQuery(strSQL);
            rs.next();
            double fromJine=rs.getDouble(1);
            if(fromJine!=accold)
            	throw new Exception("ת��ʧ�ܣ��ʻ�����ȷ");
            if(fromJine<af.getJine())
            	throw new Exception("ת���ʻ�����");
            strSQL="update TabAccount set jine=jine-"+af.getJine()+" where id="+af.getAccfrom()+" and deptid="+af.getDeptid();
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("�����ʻ�ʧ��");
            strSQL="update TabDepartInfo set zkbillid=zkbillid+1 where id="+af.getDeptid();
            stmt.executeUpdate(strSQL);
            strSQL="select zkbillid from TabDepartInfo where id="+af.getDeptid();
            rs=stmt.executeQuery(strSQL);
            rs.next();
            newbillid=rs.getInt(1);
            strSQL="insert into AccessAccount (deptid,acctype,curjine,jine,operPerson,typename,billid) values("+
            af.getDeptid()+","+af.getAccfrom()+","+fromJine+","+(-af.getJine())+",'"+af.getOperPerson()+"','�ʽ�ת��',"+newbillid+")";
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("�����¼ʧ��");
            
            strSQL="select jine from TabAccount where id="+af.getAccto()+" and deptid="+af.getDeptid();
            rs=stmt.executeQuery(strSQL);
            rs.next();
            double toJine=rs.getDouble(1);
            strSQL="update TabAccount set jine=jine+"+af.getJine()+" where id="+af.getAccto()+" and deptid="+af.getDeptid();
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("�����ʻ�ʧ��");
            strSQL="insert into AccessAccount (deptid,acctype,curjine,jine,operPerson,typename,billid) values("+
            af.getDeptid()+","+af.getAccto()+","+toJine+","+(af.getJine())+",'"+af.getOperPerson()+"','�ʽ�ת��',"+newbillid+")";
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("�����¼ʧ��");
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
	
	//	�ʽ�ע��
	public int accessInMoney(AccountForm af,double accold) throws Exception
    {
		int nRet;
		int newbillid;
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
            strSQL="select jine from TabAccount where id="+af.getAcctype()+" and deptid="+af.getDeptid();
            rs=stmt.executeQuery(strSQL);
            rs.next();
            double fromJine=rs.getDouble(1);
            if(fromJine!=accold)
            	throw new Exception("����ʧ�ܣ��ʻ�����ȷ");
            if(fromJine+af.getJine()<0)
            	throw new Exception("�ʻ�����");
            strSQL="update TabAccount set jine=jine+("+af.getJine()+") where id="+af.getAcctype()+" and deptid="+af.getDeptid();
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("�����ʻ�ʧ��");
            strSQL="update TabDepartInfo set zbbillid=zbbillid+1 where id="+af.getDeptid();
            stmt.executeUpdate(strSQL);
            strSQL="select zbbillid from TabDepartInfo where id="+af.getDeptid();
            rs=stmt.executeQuery(strSQL);
            rs.next();
            newbillid=rs.getInt(1);
            strSQL="insert into AccessAccount (deptid,acctype,curjine,jine,operPerson,typename,billid) values("+
            af.getDeptid()+","+af.getAcctype()+","+fromJine+","+(af.getJine())+",'"+af.getOperPerson()+"','�ʱ�ע��',"+newbillid+")";
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("�����¼ʧ��");
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
	//	�ʽ��ȡ
	public int accessOutMoney(AccountForm af,double accold) throws Exception
    {
		int nRet;
		int newbillid=0;
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
            strSQL="select jine from TabAccount where id="+af.getAcctype()+" and deptid="+af.getDeptid();
            rs=stmt.executeQuery(strSQL);
            rs.next();
            double fromJine=rs.getDouble(1);
            if(fromJine!=accold)
            	throw new Exception("����ʧ�ܣ��ʻ�����ȷ");
            if(fromJine-af.getJine()<0)
            	throw new Exception("�ʻ�����");
            strSQL="update TabAccount set jine=jine-("+af.getJine()+") where id="+af.getAcctype()+" and deptid="+af.getDeptid();
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("�����ʻ�ʧ��");
            strSQL="update TabDepartInfo set zbbillid=zbbillid+1 where id="+af.getDeptid();
            stmt.executeUpdate(strSQL);
            strSQL="select zbbillid from TabDepartInfo where id="+af.getDeptid();
            rs=stmt.executeQuery(strSQL);
            rs.next();
            newbillid=rs.getInt(1);
            strSQL="insert into AccessAccount (deptid,acctype,curjine,jine,operPerson,typename,billid) values("+
            af.getDeptid()+","+af.getAcctype()+","+fromJine+","+(-af.getJine())+",'"+af.getOperPerson()+"','�ʱ���ȡ',"+newbillid+")";
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("�����¼ʧ��");
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
	//	����Ӧ��Ӧ����
	public int addOtherToPay(OtherToPayForm fi) throws Exception
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
            conn.setAutoCommit(false);
            strSQL="select jine,accname from TabAccount where id="+fi.getAcctype()+" and deptid="+fi.getDeptid();
            rs=stmt.executeQuery(strSQL);
            rs.next();
            double jine=rs.getDouble(1);
            strSQL="select * from AccessType where id="+fi.getKind();
            rs=stmt.executeQuery(strSQL);
            rs.next();
            if(rs.getInt("inout")==0)
            	fi.setJine(0-fi.getJine());
            String typename=rs.getString("name");
            if(jine+fi.getJine()<0)
            	throw new Exception(rs.getString(2)+" �ʻ�����");
            strSQL="update TabAccount set jine=jine+"+fi.getJine()+" where id="+fi.getAcctype()+" and deptid="+fi.getDeptid();
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("�����ʻ�ʧ��");
            strSQL="update TabDepartInfo set jkbillid=jkbillid+1 where id="+fi.getDeptid();
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("���µ���ʧ��");
            strSQL="select jkbillid from TabDepartInfo where id="+fi.getDeptid();
            rs=stmt.executeQuery(strSQL);
            rs.next();
            int billid=rs.getInt(1);
            
            strSQL="insert into TabOtherToPay (billid,deptid,factory,acctype,jine,kind,operPerson,memo) values("+
            billid+","+fi.getDeptid()+","+fi.getFactory()+","+fi.getAcctype()+","+fi.getJine()+","+fi.getKind()+",'"+fi.getOperPerson()+"','"+
            fi.getMemo()+"')";
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("�����¼ʧ��");
            strSQL = " SELECT sum(jine) FROM TabOtherToPay a inner join TabFactory b on a.factory=b.id "+
            " left join AccessType c on a.kind=c.id where factory="+fi.getFactory();
            if(fi.getType()==0)
            	strSQL=strSQL+" and (a.kind=16 or a.kind=17)";
            else
            	strSQL=strSQL+" and (a.kind=18 or a.kind=19)";
            rs=stmt.executeQuery(strSQL);
            rs.next();
            double curjine=rs.getDouble(1);
            strSQL="update TabOtherToPay set curjine="+curjine+" where billid="+billid+" and deptid="+fi.getDeptid();
            stmt.executeUpdate(strSQL);
            strSQL="insert into AccessAccount (deptid,acctype,curjine,jine,operPerson,typename,billid) values("+
            fi.getDeptid()+","+fi.getAcctype()+","+jine+","+fi.getJine()+",'"+fi.getOperPerson()+
            "','"+typename+"',"+billid+")";
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("�����¼ʧ��");
            nRet=billid;
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
	
	public OtherToPayForm getOtherPayBillById(int billid,int deptid) throws Exception
    {
		OtherToPayForm cbf=null;
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
            strSQL = "SELECT a.*,b.name,c.accname,d.shortname,e.name as typename from TabOtherToPay a inner join TabFactory b on a.factory=b.id "+
            " inner join TabAccount c on a.acctype=c.id inner join TabDepartInfo d on a.deptid=d.id inner join accesstype e on a.kind=e.id  WHERE a.billid="+billid+" and a.deptid="+deptid;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	cbf=new OtherToPayForm();
            	cbf.setBillid(rs.getInt("billid"));
            	cbf.setFactory(rs.getInt("factory"));
            	cbf.setKind(rs.getInt("kind"));
            	cbf.setDeptid(rs.getInt("deptid"));
            	cbf.setFactname(rs.getString("name"));
            	cbf.setAcctype(rs.getInt("acctype"));
            	cbf.setAccName(rs.getString("accname"));
            	cbf.setOperPerson(rs.getString("operPerson"));
            	cbf.setJine(rs.getDouble("jine"));
            	cbf.setShortname(rs.getString("shortname"));
            	cbf.setCreateTime(oConvert.FormDateTimeShort(rs.getTimestamp("createTime")));
            	cbf.setKindName(rs.getString("typename"));
            	cbf.setMemo(oConvert.getString(rs.getString("memo"),""));
            	
            }
            else
            	throw new Exception("�Ҳ����˵�");
            	
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
	//��������Ӧ��Ӧ����
	public int PayOtherToPay(OtherToPayForm fi) throws Exception
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
            conn.setAutoCommit(false);
            strSQL="select jine,accname from TabAccount where id="+fi.getAcctype()+" and deptid="+fi.getDeptid();
            rs=stmt.executeQuery(strSQL);
            rs.next();
            double fromJine=rs.getDouble(1);
            if(fromJine+fi.getJine()*fi.getKind()<0)
            	throw new Exception(rs.getString(2)+" �ʻ�����");
            strSQL="update TabAccount set jine=jine+("+fi.getJine()*fi.getKind()+") where id="+fi.getAcctype()+" and deptid="+fi.getDeptid();
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("�����ʻ�ʧ��");
            
            strSQL="update TabOtherToPay set paymenttime=getDate(),shoukuanren='"+fi.getOperPerson()+"' where billid="+
            fi.getBillid()+" and deptid="+fi.getDeptid();
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("���¼�¼ʧ��");
            strSQL="insert into AccessAccount (deptid,acctype,curjine,jine,operPerson,typename,billid) values("+
            fi.getDeptid()+","+fi.getAcctype()+","+fromJine+",("+fi.getJine()*fi.getKind()+"),'"+fi.getOperPerson()+
            "','"+(fi.getKind()==1?"�ʽ���":"�ʽ𻹳�")+"',"+fi.getBillid()+")";
            nRet=stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("�����¼ʧ��");
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
	public List<String> getOtherPayBillIdList(int deptid)
    {
        List<String> billIdList;
        billIdList = new ArrayList<String>();
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
            strSQL = "SELECT DISTINCT top 500 BillID FROM TabOtherToPay where  deptid="+deptid;
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
	public List<String> getOtherpayPersonsList(int deptid) throws Exception
    {
		List<String> inceptPersonsList;
        inceptPersonsList = new ArrayList<String>();
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
            strSQL = "SELECT DISTINCT operperson FROM TabOtherToPay where deptid="+deptid;
            strSQL=strSQL+" Order by operperson";
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
	public List<String> getAccessMoneyPersonsList(int deptid) throws Exception
    {
		List<String> inceptPersonsList;
        inceptPersonsList = new ArrayList<String>();
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
            strSQL = "SELECT DISTINCT operperson FROM AccessAccount where 1=1";
            if(deptid>0)
            	strSQL=strSQL+" and deptid="+deptid;
            strSQL=strSQL+" Order by operperson";
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
	
	//��ȡ���ѯ
	@SuppressWarnings("unchecked")
	public ResultsetList getAccessMoneyList(AccountForm af,String oper,String importTime1,String importTime2,String orderby,int curPage) throws Exception
    {
        ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String strSQL1 = null;
        String strSQL2 = null;
        String strSQL3 = null;
        strSQL = " SELECT a.*,c.shortname,d.accname,e.inout FROM AccessAccount a inner join TabDepartInfo c on "+
        "a.deptid=c.id inner join TabAccount d on (a.acctype=d.id and a.deptid=d.deptid) inner join accessType e"+
        " on a.typename=e.name where 1=1";
        strSQL1="select sum(jine) from AccessAccount a inner join accessType e on a.typename=e.name where e.inout=1";
        strSQL2="select sum(jine) from AccessAccount a inner join accessType e on a.typename=e.name where e.inout=0";
        String strWhere="";
        if(af.getAcctype()>0)
        	strWhere=strWhere+" and acctype="+af.getAcctype();
        if(importTime1.length()>0)
        	strWhere=strWhere+" and a.opertime>='"+importTime1+"'";
        if(importTime2.length()>0)
        	strWhere=strWhere+" and a.opertime<='"+importTime2+"'";
        if(af.getDeptid()>0)
        	strWhere=strWhere+" and a.deptid="+af.getDeptid();
        if(oper.length()>0)
        	strWhere=strWhere+" and e.inout="+oper;
        if(af.getOperPerson().length()>0)
        	strWhere=strWhere+" and a.operPerson='"+af.getOperPerson()+"'";
        if(oConvert.getInt(af.getClassid(),0)>0)
        	strWhere=strWhere+" and e.kind="+af.getClassid();
        if(oConvert.getInt(af.getTypeid(),0)>0)
        	strWhere=strWhere+" and e.id="+af.getTypeid();
        
        strSQL=strSQL+strWhere+" order by "+orderby;
        strSQL1=strSQL1+strWhere;
        strSQL2=strSQL2+strWhere;
        AccountForm otpf;
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
	            	otpf = new AccountForm();
	            	otpf.setId(rs.getInt("id"));
	            	otpf.setBillid(rs.getInt("billid"));
	            	otpf.setDeptid(rs.getInt("deptid"));
	            	otpf.setShortname(rs.getString("shortname"));
	            	otpf.setAcctype(rs.getInt("acctype"));
	            	otpf.setAccName(rs.getString("accname"));
	            	otpf.setCurjine(rs.getDouble("curjine"));
	            	otpf.setJine(rs.getDouble("jine"));
	            	otpf.setTypename(rs.getString("typename"));
	            	otpf.setOpertime(oConvert.FormDateTimeShort(rs.getTimestamp("opertime")));
	            	otpf.setOperPerson(rs.getString("operPerson"));
	            	otpf.setInout(rs.getBoolean("inout"));
	                ibsri.rslist.add(otpf);
	                if(!rs.next())
	                	break;
	
	            }
            }
            rs.close();
            rs = stmt.executeQuery(strSQL1);
            if(rs.next())
            	ibsri.allmoney=rs.getDouble(1);
            rs = stmt.executeQuery(strSQL2);
            if(rs.next())
            	ibsri.realmoney=rs.getDouble(1);
            
            double qichu=0;
            if(af.getAcctype()==0)
            {
            	AccountForm tmp=null;
            	List acc=getAccountList(af.getDeptid());
            	for(int i=0;i<acc.size();i++)
            	{
            		tmp = new AccountForm();
            		tmp=(AccountForm)acc.get(i);
            		strSQL3="select top 1 curjine+jine from AccessAccount where acctype="+tmp.getId();
            		if(importTime1.length()>0)
            			strSQL3=strSQL3+" and opertime<'"+importTime1+"' order by opertime desc";
            		rs=stmt.executeQuery(strSQL3);
            		if(rs.next())
            			qichu=qichu+rs.getDouble(1);
            	}
            }
            else
            {
            	strSQL3="select top 1 curjine+jine from AccessAccount where acctype="+af.getAcctype();
        		if(importTime1.length()>0)
        			strSQL3=strSQL3+" and opertime<'"+importTime1+"' order by opertime desc";
        		rs=stmt.executeQuery(strSQL3);
        		if(rs.next())
        			qichu=rs.getDouble(1);
            }
            ibsri.allsumnum=qichu;
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
	
	
	//ȡ���ʻ��������ʹ���
	public List<String[]> getAccessClassList(int fw)
    {
        List<String[]> tmpList;
        tmpList = new ArrayList<String[]>();
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
            if(fw==0)
            	strSQL = "SELECT * FROM accessType where kind=0";
            else
            	strSQL = "SELECT top 2 * FROM accessType where kind=0 order by id desc";
            String[] tmp=null;
            for(rs = stmt.executeQuery(strSQL); rs.next(); tmpList.add(tmp))
            {
                tmp = new String[2];
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
        return tmpList;
    }
	//ȡ���ʻ������������
	public List<String[]> getAccessTypeList(String classid)
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
            strSQL = "SELECT * FROM accessType";
            if(classid.equals("0"))
            	strSQL=strSQL+" where 1=0";
            else
            	strSQL=strSQL+" where kind="+classid;
            String[] tmp;
            for(rs = stmt.executeQuery(strSQL); rs.next(); goodsTypeList.add(tmp))
            {
                tmp = new String[2];
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
        return goodsTypeList;
    }
}
