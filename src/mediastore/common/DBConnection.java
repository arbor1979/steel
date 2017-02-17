// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.

// Source File Name:   DBConnection.java

package mediastore.common;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;

// Referenced classes of package mediastore.common:
//            SystemConfig

public class DBConnection
{

    //private static String DBDriver = null;
    //private static String ConnStr = null;
	private static Context ctx=null;
	private static javax.sql.DataSource ds = null;
    private int nDriverLoaded;

    public DBConnection()
        throws Exception
    {
        nDriverLoaded = -1;
        /*
        DBDriver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
        ConnStr = SystemConfig.getDBConnection();
        try
        {
            Class.forName(DBDriver);
            nDriverLoaded = 0;
        }
        catch(ClassNotFoundException e)
        {
            throw e;
        }
        */
        try
		{
			ctx = new InitialContext();
			if(ctx==null)
				throw new Exception("没有匹配的环境");
			ds=(javax.sql.DataSource)ctx.lookup("java:comp/env/steeldb");
			if(ds==null)
				throw new Exception("没有匹配数据库");
			nDriverLoaded = 0;
		}
		catch(Exception e)
        {
			e.printStackTrace();
			throw e;
        }
    }

    public Connection getDBConnection()
        throws SQLException
    {
        Connection conn = null;
        if(nDriverLoaded == 0)
            try
            {
                //conn = DriverManager.getConnection(ConnStr);
            	conn=ds.getConnection();
            }
            catch(SQLException e)
            {
                throw e;
            }
        return conn;
    }

    public void closeDBConnection(Connection conn)
        throws SQLException
    {
        try
        {
            conn.close();
            conn=null;
        }
        catch(SQLException e)
        {
            throw e;
        }
    }

}
