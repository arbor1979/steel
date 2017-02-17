// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsShelfIDCode.java

package mediastore.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mediastore.common.DBConnection;
import mediastore.util.StrUtility;
import mediastore.web.form.GoodsStoreForm;

// Referenced classes of package mediastore.dao:
//            GoodsInfo

public class GoodsStoreCode
{

    public GoodsStoreCode()
    {
    }

    public String getGoodsStoreName(int goodsShelfId)
    {
        String tmpStr;
        tmpStr = "";
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
            strSQL = "SELECT name FROM TabStore WHERE id='" + goodsShelfId + "'";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
                tmpStr = rs.getString(1);
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
        return tmpStr;
    }

   
	@SuppressWarnings("unchecked")
	public List getGoodsShelfIdList()
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
            strSQL = "SELECT GoodsShelfID, GoodsShelfIDName FROM TabGoodsShelfIDCode order by goodsshelfid";
            GoodsStoreForm tmp;
            GoodsInfo gi = new GoodsInfo();
            for(rs = stmt.executeQuery(strSQL); rs.next(); tmpList.add(tmp))
            {
                tmp = new GoodsStoreForm();
                tmp.setId(rs.getInt(1));
                tmp.setName(rs.getString(2));
                int i=gi.getGoodsShelfIdCount(rs.getString(1));
                if(i> 0)
                    tmp.setSubnum(i);
                else
                	tmp.setSubnum(0);
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

    public int insertNewGoodsShelfId(int max, String goodsShelfIdName) throws Exception
    {
        int nRet;
        nRet = 0;
        String goodsShelfId = "";
        goodsShelfId = String.valueOf(max);
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
            strSQL = "select * from TabGoodsShelfIDCode where GoodsShelfID='"+String.valueOf(max)+"'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("此小组编号已存在，请换一个编号");
            strSQL = "insert into TabGoodsShelfIDCode( GoodsShelfID, GoodsShelfIDName) values ('" + goodsShelfId + "', '" + StrUtility.replaceString(goodsShelfIdName, "'", "''") + "')";
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
            {
                nRet = -1;
                throw new Exception("插入新记录错误");
            }
            strSQL = "insert into sqlLog (sqltext) values ('" + StrUtility.replaceString(strSQL, "'", "''")+ "')";
            stmt.executeUpdate(strSQL);
            conn.commit();
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
            	if(rs!= null)
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

    public int updateGoodsShelf(String goodsShelfId, String newGoodsShelfIdName)
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
            strSQL = "UPDATE TabGoodsShelfIDCode SET GoodsShelfIDName = '" + StrUtility.replaceString(newGoodsShelfIdName, "'", "''") + "' WHERE GoodsShelfID = '" + goodsShelfId + "'";
            nRet=stmt.executeUpdate(strSQL);
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

    public int deleteGoodsShelfId(String goodsShelfId) throws Exception
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL =null;
        int count=0;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "SELECT count(*) FROM TabGoodsInfo WHERE GoodsShelfID='" + goodsShelfId + "'";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
                count = rs.getInt(1);
            if(count>0)
            	throw new Exception("库存中存在此组的商品，不能删除此组！");
            strSQL = "DELETE FROM TabGoodsShelfIDCode WHERE GoodsShelfID='" + goodsShelfId + "' ";
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
            	throw new Exception("删除组失败！");
            strSQL = "insert into sqlLog (sqltext) values ('" + StrUtility.replaceString(strSQL, "'", "''")+ "')";
            stmt.executeUpdate(strSQL);
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
            catch(SQLException e) { }
        }
        return nRet;
    }
}
