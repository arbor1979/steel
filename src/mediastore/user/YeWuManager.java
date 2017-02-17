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
import java.util.List;

import mediastore.common.DBConnection;
import mediastore.util.StrUtility;
import mediastore.util.oConvert;
import mediastore.web.form.YeWuInfoForm;

public class YeWuManager
{

    private String strError;

    public YeWuManager()
    {
        strError = "";
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
            strSQL = "SELECT count(*) FROM TabYeWuYuan";
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
    public YeWuInfoForm getYeWuById(int id)
    {
    	YeWuInfoForm ui=new YeWuInfoForm();
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
            strSQL = "SELECT a.*,b.shortname FROM TabYeWuYuan a inner join TabDepartInfo b on a.deptid=b.id where a.id="+id;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	ui.setId(rs.getInt("id"));
                ui.setName(rs.getString("name"));
                ui.setAddress(oConvert.getString(rs.getString("address"),""));
                ui.setIdcard(oConvert.getString(rs.getString("idcard"),""));
                ui.setMobile(oConvert.getString(rs.getString("mobile"),""));
                ui.setCreatetime(oConvert.FormDateTimeShort(rs.getTimestamp("createtime")));
                ui.setDeptid(rs.getInt("deptid"));
                ui.setShortname(rs.getString("shortname"));
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
	@SuppressWarnings("unchecked")
	public List getYeWuList(String orderField,YeWuInfoForm yif)
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
            strSQL = "SELECT a.*,b.shortname FROM TabYeWuYuan a inner join TabDepartInfo b on a.deptid=b.id where 1=1";
            if(yif.getName().length()>0)
            	strSQL=strSQL+" and a.name like '%"+yif.getName()+"%'";
            if(yif.getAddress().length()>0)
            	strSQL=strSQL+" and a.address like '%"+yif.getAddress()+"%'";
            if(yif.getIdcard().length()>0)
            	strSQL=strSQL+" and a.idcard like '%"+yif.getIdcard()+"%'";
            if(yif.getMobile().length()>0)
            	strSQL=strSQL+" and a.mobile like '%"+yif.getMobile()+"%'";
            if(yif.getDeptid()>0)
            	strSQL=strSQL+" and a.deptid="+yif.getDeptid();
            strSQL=strSQL+" Order by " + orderField;
            YeWuInfoForm ui;
            for(rs = stmt.executeQuery(strSQL); rs.next(); userList.add(ui))
            {
                ui = new YeWuInfoForm();
                ui.setId(rs.getInt("id"));
                ui.setName(rs.getString("name"));
                ui.setAddress(oConvert.getString(rs.getString("address"),""));
                ui.setIdcard(oConvert.getString(rs.getString("idcard"),""));
                ui.setMobile(oConvert.getString(rs.getString("mobile"),""));
                ui.setCreatetime(oConvert.FormDateTimeShort(rs.getTimestamp("createtime")));
                ui.setDeptid(rs.getInt("deptid"));
                ui.setShortname(rs.getString("shortname"));
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
            String strSQL = "select * from TabFactory where yewuyuan=" + userId;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	throw new Exception("此员工已与客户 "+rs.getString("name")+" 绑定，不能删除！");
            }
            rs.close();
            strSQL = "DELETE FROM TabYeWuYuan WHERE ID = " + userId;
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

    public int insertUserRec(YeWuInfoForm wif) throws Exception
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
            strSQL = "SELECT * FROM TabYeWuYuan where name='"+wif.getName()+"'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("已存在此人名，姓名不能重复");
            rs.close();
            strSQL = "insert into TabYeWuYuan( Name, address,idcard,mobile,deptid) values ('" +wif.getName() +
            "', '" + wif.getAddress() + "','" +wif.getIdcard()+"','"+wif.getMobile()+"',"+wif.getDeptid()+")";
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

    public int GiveMoney(String userId,float giveMoney) throws Exception
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
            conn.setAutoCommit(false);
            String strSQL = "select * from TabYeWuYuan where id=" + userId;
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("无此业务员！");
            rs.close();
           	strSQL = "update TabYeWuYuan set account=account+"+giveMoney+" WHERE ID = " + userId;         
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
                throw new Exception("更新记录失败");
            strSQL = "insert into sqlLog (sqltext) values ('" + StrUtility.replaceString(strSQL, "'", "''")+ "')";
            stmt.executeUpdate(strSQL);
            strSQL = "insert into TabGiveGet (jine,yewuid) values("+giveMoney+","+userId+")";         
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
                throw new Exception("插入记录失败");
            strSQL = "insert into sqlLog (sqltext) values ('" + StrUtility.replaceString(strSQL, "'", "''")+ "')";
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
        return nRet;
    }
    
    public int updateUserRec(YeWuInfoForm wif) throws Exception
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
            String strSQL = "select * from TabYeWuYuan where id=" + wif.getId();
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("无此员工！");
            rs.close();
            strSQL = "select * from TabYeWuYuan WHERE name='"+wif.getName()+"' and ID <>" + wif.getId();
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("存在此人名，姓名不能重复！");
            strSQL = "update TabYeWuYuan set name='"+wif.getName()+"',address='"+wif.getAddress()+"',mobile='"+
            wif.getMobile()+"',idcard='"+wif.getIdcard()+"',deptid="+wif.getDeptid()+" WHERE ID = " + wif.getId();
           	nRet = stmt.executeUpdate(strSQL);
            if(nRet!=1)
            	throw new Exception("更新员工档案失败！");
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
    public int updateMemberCard(int memberId,String card) throws Exception
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
            String strSQL = "select * from TabYeWuYuan where id=" + memberId;
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("无此业务员！");
            rs.close();
           	strSQL = "update TabYeWuYuan set bankcard='"+card+"' WHERE ID = " + memberId;         
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
                throw new Exception("更新记录失败");
            strSQL = "insert into sqlLog (sqltext) values ('" + StrUtility.replaceString(strSQL, "'", "''")+ "')";
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
        return nRet;
    }
    public String getLastError()
    {
        return strError;
    }
}
