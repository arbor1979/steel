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
import mediastore.web.form.TruckInfoForm;

public class TruckManager
{

    private String strError;

    public TruckManager()
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
            strSQL = "SELECT count(*) FROM TabFactory";
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
    public TruckInfoForm getTruckByID(int id)
    {
    	TruckInfoForm ui = new TruckInfoForm();;
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
            strSQL = "SELECT * FROM TabTruck where id=" + id;
            rs = stmt.executeQuery(strSQL);
            if( rs.next())
            {
                
                ui.setId(rs.getInt("id"));
                ui.setCarno(rs.getString("carno"));
                ui.setDriver(rs.getString("driver"));
                ui.setAddress(rs.getString("address"));
                ui.setIdcard(rs.getString("idcard"));
                ui.setKind(rs.getString("kind"));
                ui.setMobile(rs.getString("mobile"));
                ui.setChejia(rs.getString("chejia"));
                ui.setFadongji(rs.getString("fadongji"));
                ui.setKind(rs.getString("kind"));
                ui.setWeight(rs.getFloat("weight"));
                ui.setDeptid(rs.getInt("deptid"));
                ui.setAccount(rs.getDouble("account"));
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
	public List getTruckList(String orderField,TruckInfoForm fi) throws Exception
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
            strSQL = "SELECT a.*,b.shortname FROM TabTruck a inner join TabDepartInfo b on a.deptid=b.id where 1=1 ";
            if(fi.getCarno().length()>0)
            {
            	strSQL=strSQL+" and carno like '%"+fi.getCarno()+"%'";
            }
            if(fi.getDriver().length()>0)
            {
            	strSQL=strSQL+" and driver like '%"+fi.getDriver()+"%'";
            }
            if(fi.getKind().length()>0)
            {
            	strSQL=strSQL+" and kind like '%"+fi.getKind()+"%'";
            }
            if(fi.getOper().length()>0)
            {
            	strSQL=strSQL+" and weight"+fi.getOper()+fi.getWeight();
            }
            if(fi.getDeptid()>0)
            {
            	strSQL=strSQL+" and deptid="+fi.getDeptid();
            }
            if(fi.getAccount()>0)
            {
            	strSQL=strSQL+" and a.account>="+fi.getAccount();
            }
            strSQL=strSQL+"	Order by " + orderField + ";";
            TruckInfoForm ui;
            for(rs = stmt.executeQuery(strSQL); rs.next(); userList.add(ui))
            {
            	ui = new TruckInfoForm();
                ui.setId(rs.getInt("id"));
                ui.setCarno(rs.getString("carno"));
                ui.setDriver(rs.getString("driver"));
                ui.setAddress(rs.getString("address"));
                ui.setIdcard(rs.getString("idcard"));
                ui.setKind(rs.getString("kind"));
                ui.setMobile(rs.getString("mobile"));
                ui.setChejia(rs.getString("chejia"));
                ui.setFadongji(rs.getString("fadongji"));
                ui.setKind(rs.getString("kind"));
                ui.setWeight(rs.getFloat("weight"));
                ui.setDeptid(rs.getInt("deptid"));
                ui.setAccount(rs.getDouble("account"));
                ui.setShortname(rs.getString("shortname"));
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
            String strSQL = "select * from TabTruck where id=" + userId;
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("无此车辆！");
            String carno=rs.getString("carno");
            double account=rs.getDouble("account");
            if(account!=0)
            	throw new Exception("预付运费账户不为0，不能删除！");
            rs.close();
            strSQL = "select * from TabGoodsImportInfo where carno='" + carno+"'";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("有此车辆的入库运输单，不能删除！");
            rs.close();
            strSQL = "select * from TabGoodsExportInfo where carno='" + carno+"'";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("有此车辆的出库运输单，不能删除！");
            strSQL = "DELETE FROM TabTruck WHERE ID = " + userId;
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

    public int insertUserRec(TruckInfoForm fi) throws Exception
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
            strSQL = "SELECT * FROM TabTruck where carno='"+fi.getCarno()+"'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("已存在此车牌号");
            rs.close();
            strSQL = "insert into TabTruck(carno,driver,idcard, address, mobile,kind,chejia,fadongji,weight,deptid) values "+
            "('" +fi.getCarno() + "', '" + fi.getDriver() + "','" +fi.getIdcard() + "','"+fi.getAddress()+"','"+fi.getMobile()+
            "','"+fi.getKind()+"','"+fi.getChejia()+"','"+fi.getFadongji()+"',"+fi.getWeight()+","+fi.getDeptid()+")";
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

    public int updateUserRec(TruckInfoForm fi) throws Exception
    {
        int nRet;
        nRet = 0;
        String carno="";
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            String strSQL = "select * from TabTruck where id=" + fi.getId();
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("无此车辆记录！");
            else
            	carno=rs.getString("carno");
            rs.close();
            strSQL = "select * from TabTruck where id<>" + fi.getId()+" and carno='"+fi.getCarno()+"'";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("已存在此车牌号！");
            if(!fi.getCarno().equals(carno))
            {
            	rs.close();
                strSQL = "select * from TabGoodsImportInfo where carno='"+carno+"'";
                rs = stmt.executeQuery(strSQL);
                if(rs.next())
                	throw new Exception("已存在此车的入库记录，不能更改车牌！");
                rs.close();
                strSQL = "select * from TabGoodsExportInfo where carno='"+carno+"'";
                rs = stmt.executeQuery(strSQL);
                if(rs.next())
                	throw new Exception("已存在此车的出库记录，不能更改车牌！");
            }
           	strSQL = "update TabTruck set carno='"+fi.getCarno()+"',driver='"+fi.getDriver()+"',idcard='"+fi.getIdcard()+
           	"',address='"+fi.getAddress()+"',mobile='"+fi.getMobile()+"',kind='"+fi.getKind()+"',chejia='"+fi.getChejia()+
           	"',fadongji='"+fi.getFadongji()+"',weight="+fi.getWeight()+",deptid="+fi.getDeptid()+" WHERE ID = " + fi.getId();         
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
}
