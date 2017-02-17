// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   UserManager.java

package mediastore.user;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mediastore.common.DBConnection;
import mediastore.util.oConvert;
import mediastore.web.form.DepartInfoForm;

public class DepartManager
{

    private String strError;

    public DepartManager()
    {
        strError = "";
    }

    public DepartInfoForm getFactByID(int id)
    {
    	DepartInfoForm ui=null;
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
            strSQL = "SELECT * FROM TabDepartInfo where id=" + id;
            rs = stmt.executeQuery(strSQL);
            if( rs.next())
            {
                ui = new DepartInfoForm();
                ui.setId(rs.getInt("id"));
                ui.setName(rs.getString("name"));
                ui.setShortname(rs.getString("shortname"));
                ui.setAddress(rs.getString("address"));
                ui.setTel(rs.getString("tel"));
                ui.setLinkman(rs.getString("linkman"));
                ui.setFax(rs.getString("fax"));
                ui.setMobile(rs.getString("mobile"));
                ui.setTownid(rs.getInt("townid"));
                ui.setBank(rs.getString("bank"));
                ui.setAccount(rs.getString("account"));
                ui.setTaxno(rs.getString("taxno"));
                ui.setPagesize(rs.getInt("pagesize"));
                ui.setJiagong(rs.getInt("ifjiagong"));
                ui.setIfyunfei(rs.getBoolean("ifyunfei"));
                ui.setTip(oConvert.getString(rs.getString("tip")));
                ui.setIfyixiangjine(rs.getBoolean("ifyixiangjine"));
                
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
        return ui;
    }
	@SuppressWarnings("unchecked")
	public List getDepartList(String orderField)
    {
        List userList;
        userList = new ArrayList();
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
            strSQL = "SELECT * FROM TabDepartInfo Order by " + orderField + ";";
            DepartInfoForm ui;
            for(rs = stmt.executeQuery(strSQL); rs.next(); userList.add(ui))
            {
                ui = new DepartInfoForm();
                ui.setId(rs.getInt("id"));
                ui.setName(rs.getString("name"));
                ui.setShortname(rs.getString("shortname"));
                ui.setAddress(rs.getString("address"));
                ui.setTel(rs.getString("tel"));
                ui.setLinkman(rs.getString("linkman"));
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
        return userList;
    }
	
	@SuppressWarnings("unchecked")
	public List getDepartList(String orderField,DepartInfoForm fi) throws Exception
    {
        List userList;
        userList = new ArrayList();
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
            strSQL = "SELECT * FROM TabDepartInfo where 1=1 ";
            if(fi.getName().length()>0)
            {
            	strSQL=strSQL+" and name like '%"+fi.getName()+"%'";
            }
            if(fi.getLinkman().length()>0)
            {
            	strSQL=strSQL+" and linkman like '%"+fi.getLinkman()+"%'";
            }
            if(fi.getZhujima().length()>0)
            	strSQL=strSQL+" and zhujima like '%"+fi.getZhujima()+"%'";
            strSQL=strSQL+"	Order by " + orderField + ";";
            DepartInfoForm ui;
            for(rs = stmt.executeQuery(strSQL); rs.next(); userList.add(ui))
            {
                ui = new DepartInfoForm();
                ui.setId(rs.getInt("id"));
                ui.setName(rs.getString("name"));
                ui.setShortname(rs.getString("shortname"));
                ui.setAddress(rs.getString("address"));
                ui.setTel(rs.getString("tel"));
                ui.setLinkman(rs.getString("linkman"));
                
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
        return userList;
    }
	
    public int deleteUserRec(int userId) throws Exception
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
            String strSQL = "select * from TabDepartInfo where id=" + userId;
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("无此分公司！");
            rs.close();
            strSQL = "select * from TabUserInfo where deptid=" + userId;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("请先删除下属操作员！");
            rs.close();
            strSQL = "select * from TabStore where deptid=" + userId;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("请先删除下属仓库！");
            strSQL = "DELETE FROM TabDepartInfo WHERE ID = " + userId;
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
    public String GeneralZhuJiMa(String name) throws Exception
    {
        String nRet="";
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String wd="";
        String strSQL="";
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            for(int i=0;i<name.length();i++)
            {
            	wd=name.substring(i,i+1);
            	strSQL = "select * from hzpy where hz='" + wd+"'";
            	rs = stmt.executeQuery(strSQL);
            	if(rs.next())
            		nRet=nRet+rs.getString("py");
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
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return nRet;
    }
    public int insertUserRec(DepartInfoForm fi) throws Exception
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
            strSQL = "SELECT * FROM TabDepartInfo where name='"+fi.getName()+"'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("已存在同名分公司，请更换名称");
            rs.close();
            fi.setZhujima(GeneralZhuJiMa(fi.getName()));
            strSQL = "insert into TabDepartInfo( Name,shortname,townid, address,tel,fax,linkman,mobile,bank,account,taxno,zhujima,pagesize,ifjiagong,ifyunfei,tip,ifyixiangjine) values "+
            "('" +fi.getName() + "', '" + fi.getShortname() + "'," +fi.getTownid() + ",'"+fi.getAddress()+"','"+fi.getTel()+
            "','"+fi.getFax()+"','"+fi.getLinkman()+"','"+fi.getMobile()+"','"+fi.getBank()+"','"+fi.getAccount()+"','"+fi.getTaxno()+"','"+fi.getZhujima()+
            "',"+fi.getPagesize()+","+fi.getJiagong()+","+(fi.isIfyunfei()?"1":"0")+",'"+fi.getTip()+"',"+(fi.isIfyunfei()?"1":"0")+")";
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
                throw new Exception("插入新记录错误");
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
            catch(SQLException ex) { }
        }
        return nRet;
    }

    public int updateUserRec(DepartInfoForm fi) throws Exception
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
            String strSQL = "select * from TabDepartInfo where id=" + fi.getId();
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("无此分公司！");
            rs.close();
            strSQL = "select * from TabDepartInfo where id<>" + fi.getId()+" and name='"+fi.getName()+"'";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("已存在此名称的分公司！");
            fi.setZhujima(GeneralZhuJiMa(fi.getName()));
           	strSQL = "update TabDepartInfo set name='"+fi.getName()+"',shortname='"+fi.getShortname()+"',townid="+fi.getTownid()+
           	",address='"+fi.getAddress()+"',tel='"+fi.getTel()+"',fax='"+fi.getFax()+"',linkman='"+fi.getLinkman()+
           	"',mobile='"+fi.getMobile()+"',bank='"+fi.getBank()+"',account='"+fi.getAccount()+"',zhujima='"+fi.getZhujima()+
           	"',taxno='"+fi.getTaxno()+"',pagesize="+fi.getPagesize()+",ifjiagong="+fi.getJiagong()+",ifyunfei="+
           	(fi.isIfyunfei()?"1":"0")+",tip='"+fi.getTip()+"',ifyixiangjine="+(fi.isIfyixiangjine()?"1":"0") +" WHERE ID = " + fi.getId();         
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
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
    public boolean dataBakup(String dir) throws Exception
    {
        boolean nRet=false;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            File  dirFile = new File(dir);
            if(!dirFile.exists())
            { 	
            	if(!dirFile.mkdir())
            		throw new Exception("创建文件夹"+dir+"失败！");
            }
            String dbname=conn.getCatalog();
            String filename=dbname+"_"+oConvert.FormDate(new Date(),"yyyyMMddHHmmss")+".bak";
            String strSQL="backup database "+dbname+" to disk='"+dir+"\\"+filename+"'";
            nRet=stmt.execute(strSQL);
            	
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
    public boolean dataRestore(String dir) throws Exception
    {
        boolean nRet=false;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            File  dirFile = new File(dir);
            if(!dirFile.exists())
            	throw new Exception("备份文件夹"+dir+"不存在！");
            File list[] = dirFile.listFiles();
            long max=0;
            int j=-1;
            for(int i = 0; i < list.length; i++)
            { 
	            if(list[i].isFile() && list[i].getName().indexOf("steeldb")>=0)
	            { 
	            	if(list[i].lastModified()>max)
	            	{
	            		max=list[i].lastModified();
	            		j=i;
	            	}
	            } 
            }
            if(j==-1)
            	throw new Exception("备份文件夹"+dir+"下不存在任何备份文件！");
            
            String filename=list[j].getName();
            String strSQL="restore database steeldb from disk='"+dir+"\\"+filename+"'";
            nRet=stmt.execute(strSQL);
            	
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
    public boolean dataClear() throws Exception
    {
        boolean nRet=false;
        DBConnection dbc = null;
        Connection conn = null;
        CallableStatement proc = null; 
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            proc = conn.prepareCall("{ call ClearData()}");
            nRet=proc.execute(); 
            	
        }
        catch(Exception e)
        {
            throw e;
        }
        finally
        {
            try
            {
                if(proc != null)
                	proc.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return nRet;
    }
    public boolean dataDelete(Date begintime,Date endtime,int deptid) throws Exception
    {
        boolean nRet=false;
        DBConnection dbc = null;
        Connection conn = null;
        CallableStatement proc = null; 
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            proc = conn.prepareCall("{ call DeleteData(?,?,?)}");
            java.sql.Timestamp sqlbegin=new java.sql.Timestamp(begintime.getTime());        
            java.sql.Timestamp sqlend=new java.sql.Timestamp(endtime.getTime());
            proc.setTimestamp(1, sqlbegin);
            proc.setTimestamp(2, sqlend);
            proc.setInt(3, deptid);
            nRet=proc.execute(); 
            	
        }
        catch(Exception e)
        {
            throw e;
        }
        finally
        {
            try
            {
                if(proc != null)
                	proc.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return nRet;
    }
    public String getLastError()
    {
        return strError;
    }
    public int updateToAdv(int flag) throws Exception
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
           	String strSQL = "update TabDepartInfo set ifview="+flag+" WHERE ID =1";         
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
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
}
