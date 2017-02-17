// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   UserManager.java

package mediastore.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import mediastore.common.DBConnection;
import mediastore.util.StrUtility;
import mediastore.util.oConvert;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.ApplicationContext;
import mediastore.web.global.Globals;

public class UserManager
{

    private String strError;

    public UserManager()
    {
        strError = "";
    }

    public UserInfoForm login(String strUserName, String strPassword,String ip) throws Exception
    {
        UserInfoForm sc=null;
        DBConnection dbc = null;
        Connection conn = null;
        ResultSet rs = null;
        Statement stmt = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "SELECT a.*,b.name,b.shortName,b.ifview,b.ifjiagong,b.ifyunfei,b.pagesize,b.ifyixiangjine FROM TabUserInfo a inner join TabDepartInfo b on a.deptid=b.id"+
            " WHERE UserName = '" + StrUtility.replaceString(strUserName, "'", "''") + "' and Password = '" + StrUtility.replaceString(strPassword, "'", "''") + "';";
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            {
                throw new Exception("用户不存在");
            }
            else
            {
            	if(rs.getInt("status")==0)
            		throw new Exception("用户已被禁用");
            	sc = new UserInfoForm();
            	sc.setId(rs.getInt("id"));
            	sc.setRole(rs.getString("role"));
            	sc.setUserName(strUserName);
            	sc.setPassword(rs.getString("password"));
            	sc.setUserStatus(rs.getInt("status"));
            	sc.setDeptName(rs.getString("name"));
            	sc.setDeptid(rs.getInt("deptid"));
            	sc.setIfview(rs.getBoolean("ifview"));
            	sc.setIfjiagong(rs.getBoolean("ifjiagong"));
            	sc.setDeptShortName(rs.getString("shortname"));
            	sc.setPagesize(rs.getInt("pagesize"));
            	sc.setIfyunfei(rs.getBoolean("ifyunfei"));
            	sc.setIfyixiangjine(rs.getBoolean("ifyixiangjine"));
                sc.setCreateTime(oConvert.FormDateShort(rs.getTimestamp("createtime")));
                if(rs.getString("lastlogintime")!=null)
                	sc.setLastLoginTime(oConvert.FormDateTimeShort(rs.getTimestamp("lastlogintime")));
                else
                	sc.setLastLoginTime("");
            	List menu=getMenuList(sc.getRole(),true);
            	sc.setMenu(menu);
            	List menusub=getMenuSubList(sc.getRole(),true);
            	sc.setMenusub(menusub);
            	sc.setRemoteip(ip);
            	sc.setLogintime(oConvert.FormDateTimeShort(new java.util.Date()));
            }
            rs.close();
            strSQL="update TabUserInfo set online=1,lastlogintime=getDate() where id="+sc.getId();
            stmt.executeUpdate(strSQL);
            strSQL="insert into AccessIP (username,remoteip,oper) values('"+sc.getUserName()+"','"+ip+"','login')";
            stmt.executeUpdate(strSQL);
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
        return sc;
        
    }

    public void logout(UserInfoForm uif)
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
            strSQL = "UPDATE TabUserInfo SET online = 0 WHERE username ='" + uif.getUserName() + "'";
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	System.out.println("更新操作员离线状态失败");
            strSQL="insert into AccessIP (username,remoteip,oper) values('"+uif.getUserName()+"','"+uif.getRemoteip()+"','logout')";
            stmt.executeUpdate(strSQL);
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
        return;
    }
    public void setonline(Vector activeSessions) throws SQLException
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
            strSQL = "update TabUserInfo set online=0";
            stmt.executeUpdate(strSQL);
            Iterator it = activeSessions.iterator();
            while (it.hasNext())
            {
            	HttpSession sess = (HttpSession)it.next();
            	UserInfoForm User = (UserInfoForm)sess.getAttribute(Globals.SESSION_CONTEXT);
            	strSQL = "UPDATE TabUserInfo SET online = 1 WHERE username ='" + User.getUserName() + "'";
            	stmt.executeUpdate(strSQL);
            }
        }
        catch(Exception e) 
        {
        	
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
        return;
    }
    public int getTotalUserNum()
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
            strSQL = "SELECT count(*) FROM TabUserInfo;";
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            {
                nRet = -1;
                throw new Exception("获得系统用户总数失败");
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

    public int getOnlineUserNum()
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
            strSQL = "SELECT count(*) FROM TabUserInfo WHERE Status = 1;";
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            {
                nRet = -1;
                throw new Exception("获得系统用户总数失败");
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

    public int getOfflineUserNum()
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
            strSQL = "SELECT count(*) FROM TabUserInfo WHERE Status = 0;";
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            {
                nRet = -1;
                throw new Exception("获得系统用户总数失败");
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
	public List getUserList(String orderField,int deptid)
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
            strSQL = "SELECT a.*,b.shortname FROM TabUserInfo a inner join TabDepartInfo b on a.deptid=b.id";
            if(deptid>-1)
            	strSQL=strSQL+" where deptid="+deptid;
            strSQL=strSQL+" Order by " + orderField + ";";
            UserInfoForm ui;
            Vector activeSessions = (Vector) ApplicationContext.getContext();
            for(rs = stmt.executeQuery(strSQL); rs.next(); userList.add(ui))
            {
                ui = new UserInfoForm();
                ui.setId(rs.getInt("id"));
                ui.setUserName(rs.getString("username"));
                ui.setDeptShortName(rs.getString("shortname"));
                ui.setPassword(rs.getString("password"));
                ui.setUserStatus(rs.getInt("status"));
                Iterator it = activeSessions.iterator();
                while (it.hasNext())
                {
                	HttpSession sess = (HttpSession)it.next();
                	UserInfoForm User = (UserInfoForm)sess.getAttribute(Globals.SESSION_CONTEXT);
                	if(User.getId()==rs.getInt("id"))
                	{
                		ui.setOnline(1);
                		break;
                	}
                }
                ui.setCreateTime(oConvert.FormDateShort(rs.getTimestamp("createtime")));
                if(rs.getString("lastlogintime")!=null)
                	ui.setLastLoginTime(oConvert.FormDateTimeShort(rs.getTimestamp("lastlogintime")));
                else
                	ui.setLastLoginTime("");
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
	public List getMenuList(boolean flag)
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
            strSQL = "SELECT * FROM menu where kind=0";
            if(!flag)
            	strSQL=strSQL+" and id<>36";
            strSQL=strSQL+" order by orderid";
            String ui[];
            for(rs = stmt.executeQuery(strSQL); rs.next(); userList.add(ui))
            {
                ui = new String[2];
                ui[0]=rs.getString("id");
                ui[1]=rs.getString("name");
                
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
	public List getMenuList(String role,boolean flag)
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
            strSQL = "SELECT * FROM menu where kind=0 and id in (select kind from menu where id in ("+role+"))";
            if(!flag)
            	strSQL=strSQL+" and id<>36";
            strSQL=strSQL+" order by orderid";
            String ui[];
            for(rs = stmt.executeQuery(strSQL); rs.next(); userList.add(ui))
            {
                ui = new String[2];
                ui[0]=rs.getString("id");
                ui[1]=rs.getString("name");
                
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
	public List getMenuSubList(boolean flag)
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
            strSQL = "SELECT * FROM menu where kind>0";
            if(!flag)
            	strSQL=strSQL+" and kind<>36";
            strSQL=strSQL+" order by kind,orderid";
            String ui[];
            for(rs = stmt.executeQuery(strSQL); rs.next(); userList.add(ui))
            {
                ui = new String[4];
                ui[0]=rs.getString("id");
                ui[1]=rs.getString("name");
                ui[2]=rs.getString("url");
                ui[3]=rs.getString("kind");
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
	public List getMenuSubList(String role,boolean flag)
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
            strSQL = "SELECT * FROM menu where id in ("+role+") ";
            if(!flag)
            	strSQL=strSQL+" and kind<>36";
            strSQL=strSQL+" order by kind,orderid";
            rs = stmt.executeQuery(strSQL);
            String ui[];
        	while(rs.next())
        	{
        		ui = new String[4];
                ui[0]=rs.getString("id");
                ui[1]=rs.getString("name");
                ui[2]=rs.getString("url");
                ui[3]=rs.getString("kind");
                userList.add(ui);
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
	public UserInfoForm getUserbyID(int id)
    {
		UserInfoForm ui=null;
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
            strSQL = "SELECT a.*,b.ifview FROM TabUserInfo a inner join TabDepartInfo b on a.deptid=b.id where a.id=" + id;
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	ui=new UserInfoForm();
            	ui = new UserInfoForm();
            	ui.setId(rs.getInt("id"));
            	ui.setRole(rs.getString("role"));
                ui.setUserName(rs.getString("username"));
                ui.setPassword(rs.getString("password"));
                ui.setUserStatus(rs.getInt("status"));
                ui.setDeptid(rs.getInt("deptid"));
                ui.setIfview(rs.getBoolean("ifview"));
                ui.setCreateTime(oConvert.FormDateShort(rs.getTimestamp("createtime")));
                ui.setLastLoginTime(oConvert.FormDateTimeShort(rs.getTimestamp("lastlogintime")));
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
        return ui;
    }
    public int deleteUserRec(UserInfoForm uif) throws Exception
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
            String strSQL ="select top 1 * from TabGoodsExportInfo where salesPerson='"+uif.getUserName()+"' or shoukuanren='"+uif.getUserName()+"' or kaipiaoren='"+uif.getUserName()+"'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("已存在此操作员的出库操作记录，不能删除");
            rs.close();
            strSQL ="select top 1 * from TabGoodsImportInfo where createPerson='"+uif.getUserName()+"' or shoukuanren='"+uif.getUserName()+"' or kaipiaoren='"+uif.getUserName()+"'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("已存在此操作员的入库操作记录，不能删除");
            rs.close();
            strSQL = "DELETE FROM TabUserInfo WHERE id=" + uif.getId();
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
        return nRet;
    }
    public int changePwd(UserInfoForm uif) throws Exception
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
            String strSQL ="select * from TabUserInfo where username='"+oConvert.filterSQL(uif.getUserName())+"' and password='"+oConvert.filterSQL(uif.getPassword())+"'";
            rs=stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("旧密码不正确，不能更改密码");
            rs.close();
            strSQL = "update TabUserInfo set password='"+oConvert.filterSQL(uif.getNewpwd())+"' WHERE username='" + uif.getUserName()+"'";
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
                throw new Exception("更改密码失败");
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
    public int insertUserRec(UserInfoForm uif) throws Exception
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
            if(uif.getId()==0 && ApplicationContext.getVer()==1)
            {
            	strSQL = "SELECT count(*) FROM TabUserInfo";
            	rs=stmt.executeQuery(strSQL);
            	rs.next();
            	if(rs.getInt(1)>=2)
            	{
            		throw new Exception("初级版最多只能创建2个用户");
            	}
            }
            if(uif.getId()!=0)
            	strSQL = "SELECT * FROM TabUserInfo where username='"+uif.getUserName()+"' and id<>"+uif.getId();
            else
            	strSQL = "SELECT * FROM TabUserInfo where username='"+uif.getUserName()+"'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("已存在同名用户，请更换用户名");
            rs.close();
            if(uif.getId()!=0)
            	strSQL=" update TabUserInfo set password='"+uif.getPassword()+"',role='"+
            	uif.getRole()+"',status="+uif.getUserStatus()+" where id="+uif.getId();
            else
            	strSQL = "insert into TabUserInfo( UserName, Password, Role, Status, CreateTime,deptid) values ('"+
            	uif.getUserName() + "','" + uif.getPassword() +	"','" + uif.getRole() + "',"+uif.getUserStatus()+", getdate(),+"+uif.getDeptid()+")";
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

    public String getLastError()
    {
        return strError;
    }
}
