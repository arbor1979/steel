package mediastore.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mediastore.common.DBConnection;
import mediastore.util.oConvert;

public class SCT {

	public SCT() 
	{
	}
	@SuppressWarnings("unchecked")
	public List GetSheng()
    {
		List tmpList;
        tmpList = new ArrayList();
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
            strSQL = "select * from sheng";
            String tmp[];
            for(rs = stmt.executeQuery(strSQL); rs.next(); tmpList.add(tmp))
            {
                tmp = new String[2];
                tmp[0] = rs.getString(1);
                tmp[1] = rs.getString(2);
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
	@SuppressWarnings("unchecked")
	public List GetCity(String sheng)
    {
		List tmpList;
        tmpList = new ArrayList();
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
            strSQL = "select * from city";
            if(oConvert.getInt(sheng,0)!=0)
            	strSQL=strSQL+" where shengid="+sheng;
            else
            	strSQL=strSQL+" where 1=0";
            String tmp[];
            for(rs = stmt.executeQuery(strSQL); rs.next(); tmpList.add(tmp))
            {
                tmp = new String[2];
                tmp[0] = rs.getString(1);
                tmp[1] = rs.getString(2);
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
	@SuppressWarnings("unchecked")
	public List GetTown(String city)
    {
		List tmpList;
        tmpList = new ArrayList();
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
            strSQL = "select * from town";
            if(oConvert.getInt(city,0)!=0)
            	strSQL=strSQL+" where cityid="+city;
            else
            	strSQL=strSQL+" where 1=0";
            String tmp[];
            for(rs = stmt.executeQuery(strSQL); rs.next(); tmpList.add(tmp))
            {
                tmp = new String[2];
                tmp[0] = rs.getString(1);
                tmp[1] = rs.getString(2);
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
//	由县ID得到城市ID
	public String GetCityidByTown(String town)
    {
		String cityid="0";
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
            strSQL = "select * from town where autoid="+town;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	cityid=rs.getString("cityid");
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
        return cityid;
    }
	//由城市ID得到省ID
	public String GetShengidByCity(String city)
    {
		String shengid="0";
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
            strSQL = "select * from city where autoid="+city;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	shengid=rs.getString("shengid");
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
        return shengid;
    }
}
