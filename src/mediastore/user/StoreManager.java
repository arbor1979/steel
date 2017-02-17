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
import mediastore.web.form.StoreInfoForm;

public class StoreManager
{

    private String strError;

    public StoreManager()
    {
        strError = "";
    }

    public int getTotalDepartNum()
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
            strSQL = "SELECT count(*) FROM TabStore where ifdel=0";
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            {
                nRet = -1;
                throw new Exception("获得仓库总数失败");
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
    public StoreInfoForm getDepartByID(int id)
    {
    	StoreInfoForm ui=null;
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
            strSQL = "SELECT * FROM TabStore where id=" + id;
            rs = stmt.executeQuery(strSQL);
            if( rs.next())
            {
                ui = new StoreInfoForm();
                ui.setId(rs.getInt("id"));
                ui.setName(rs.getString("name"));
                ui.setAddress(rs.getString("address"));
                ui.setTel(rs.getString("tel"));
                ui.setLinkman(rs.getString("linkman"));
                ui.setMobile(rs.getString("mobile"));
                ui.setDeptid(rs.getInt("deptid"));
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
	public List getDepartList(String orderField,int deptid)
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
            strSQL = "SELECT * FROM TabStore where ifdel=0";
            if(deptid>-1)
            	strSQL=strSQL+" and deptid="+deptid;
            strSQL=strSQL+" Order by " + orderField + ";";
            StoreInfoForm ui;
            for(rs = stmt.executeQuery(strSQL); rs.next(); userList.add(ui))
            {
            	ui = new StoreInfoForm();
                ui.setId(rs.getInt("id"));
                ui.setName(rs.getString("name"));
                ui.setAddress(rs.getString("address"));
                ui.setTel(rs.getString("tel"));
                ui.setLinkman(rs.getString("linkman"));
                ui.setMobile(rs.getString("mobile"));
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
        return userList;
    }
	
	@SuppressWarnings("unchecked")
	public List getDepartList(String orderField,StoreInfoForm dif)
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
            strSQL = "SELECT a.*,b.shortname FROM TabStore a inner join TabDepartInfo b on a.deptid=b.id where ifdel=0 ";
            if(dif.getName().length()>0)
            {
            	strSQL=strSQL+" and a.name like '%"+dif.getName()+"%'";
            }
            if(dif.getAddress().length()>0)
            {
            	strSQL=strSQL+" and a.address like '%"+dif.getAddress()+"%'";
            }
            if(dif.getLinkman().length()>0)
            {
            	strSQL=strSQL+" and a.linkman like '%"+dif.getLinkman()+"%'";
            }
            if(dif.getDeptid()>0)
            {
            	strSQL=strSQL+" and a.deptid="+dif.getDeptid();
            }
            strSQL=strSQL+"	Order by " + orderField + ";";
            StoreInfoForm ui;
            for(rs = stmt.executeQuery(strSQL); rs.next(); userList.add(ui))
            {
            	ui = new StoreInfoForm();
                ui.setId(rs.getInt("id"));
                ui.setName(rs.getString("name"));
                ui.setAddress(rs.getString("address"));
                ui.setTel(rs.getString("tel"));
                ui.setLinkman(rs.getString("linkman"));
                ui.setMobile(rs.getString("mobile"));
                ui.setDeptid(rs.getInt("deptid"));
                ui.setShortname(rs.getString("shortname"));
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
        return userList;
    }
	
    public int deleteDepartRec(int departId) throws Exception
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
            if(departId==1)
            	throw new Exception("主仓库无法删除！");
            String strSQL = "select * from TabStore where id=" + departId;
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("无此仓库！");
            rs.close();
            strSQL = "select sum(repertoryAmount) from TabGoodsRepertory where storeid=" + departId;
            rs = stmt.executeQuery(strSQL);
            rs.next();
            if(rs.getDouble(1)!=0)
            	throw new Exception("此仓库中库存不为零，不能删除！");
            rs.close();
            strSQL = "select * from TabGoodsImportGoods where storeid=" + departId;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	strSQL = "update TabStore set ifdel=1 WHERE id = " + departId;
            }
            else
            {
            	strSQL = "DELETE FROM TabStore WHERE id = " + departId;
            }
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

    public int insertDepartRec(StoreInfoForm dif) throws Exception
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
            strSQL = "SELECT * FROM TabStore where name='"+dif.getName()+"'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	if(rs.getInt("ifdel")==1)
            	{
            		strSQL="update TabStore set ifdel=0 where id="+rs.getInt("id");
            		nRet = stmt.executeUpdate(strSQL);
            	}
            	else
            		throw new Exception("已存在同名仓库，请更换名称");
            }
            else
            {
	            strSQL = "insert into TabStore(Name,address, tel,linkman,mobile,deptid) values "+
	            "('" +dif.getName() + "', '" + dif.getAddress()+"','"+dif.getTel()+
	            "','"+dif.getLinkman()+"','"+dif.getMobile()+"',"+dif.getDeptid()+")";
	            nRet = stmt.executeUpdate(strSQL);
	            if(nRet != 1)
	                throw new Exception("插入新记录错误");
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
            catch(SQLException ex) { }
        }
        return nRet;
    }

    public int updateDepartRec(StoreInfoForm dif) throws Exception
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
            String strSQL = "select * from TabStore where id=" + dif.getId();
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("无此仓库！");
            rs.close();
            strSQL = "select * from TabStore where id<>" + dif.getId()+" and name='"+dif.getName()+"'";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("已存在此名称的仓库！");
           	strSQL = "update TabStore set name='"+dif.getName()+"',address='"+dif.getAddress()+"',tel='"+
           	dif.getTel()+"',linkman='"+dif.getLinkman()+"',mobile='"+dif.getMobile()+"' WHERE id = " + dif.getId();         
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
    
    public String getLastError()
    {
        return strError;
    }
    
    @SuppressWarnings("unchecked")
	public List getStoreList(String orderField,String goodsid,int deptid,int storeid)
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
            strSQL = "SELECT * FROM TabStore where ifdel=0 and deptid="+deptid;
            if(storeid>0)
            	strSQL=strSQL+" and id="+storeid;
            strSQL=strSQL+"	Order by " + orderField + ";";
            StoreInfoForm ui;
            for(rs = stmt.executeQuery(strSQL); rs.next(); userList.add(ui))
            {
            	ui = new StoreInfoForm();
                ui.setId(rs.getInt("id"));
                ui.setName(rs.getString("name"));
                ui.setAddress(rs.getString("address"));
                ui.setTel(rs.getString("tel"));
                ui.setLinkman(rs.getString("linkman"));
                ui.setMobile(rs.getString("mobile"));
                
            }
            rs.close();
            for(int i=0;i<userList.size();i++)
            {
            	ui=(StoreInfoForm)userList.get(i);
            	strSQL="select RepertoryAmount from TabGoodsRepertory where Storeid="+ui.getId()+" and goodsid='"+
            	goodsid+"'";
            	rs = stmt.executeQuery(strSQL);
            	if(rs.next())
            		ui.setNum(rs.getFloat(1));
            	rs.close();
            }
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
        return userList;
    }
}
