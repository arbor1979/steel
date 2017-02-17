
package mediastore.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mediastore.common.DBConnection;
import mediastore.util.StrUtility;
import mediastore.web.form.GoodsClassInfo;

// Referenced classes of package mediastore.dao:
//            GoodsInfo

public class GoodsClassCode
{

    public GoodsClassCode()
    {
    }

    public String getGoodsClassName(String goodsClass)
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
            strSQL = "SELECT GoodsClassName FROM TabGoodsClassCode WHERE GoodsClass='" + StrUtility.replaceString(goodsClass, "'", "''") + "'";
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
	public List getGoodsClassList()
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
            strSQL = "SELECT GoodsClass, GoodsClassName FROM TabGoodsClassCode order by goodsclass";
            GoodsClassInfo tmp=null;
            for(rs = stmt.executeQuery(strSQL); rs.next(); tmpList.add(tmp))
            {
                tmp = new GoodsClassInfo();
                tmp.setId(rs.getInt(1));
                tmp.setName(rs.getString(2));
                GoodsInfo gi = new GoodsInfo();
                tmp.setSubNum(gi.getGoodsClassCount(rs.getString(1)));
                
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

    public int insertNewGoodsClass(String goodsClassName) throws Exception
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        String strSQL = null;
        ResultSet rs = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            goodsClassName=goodsClassName.trim();
            if(goodsClassName==null || goodsClassName.equals(""))
            	throw new Exception("添加失败！名称不能为空");
            strSQL = "select * from TabGoodsClassCode where GoodsClassName='"+goodsClassName+"'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("添加失败！已存在此名称的类别");
            strSQL = "insert into TabGoodsClassCode(GoodsClassName) values ('" + StrUtility.replaceString(goodsClassName, "'", "''") + "')";
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
            {
                nRet = -1;
                throw new Exception("插入新记录错误");
            }
            
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

    public int updateGoodsClass(String goodsClass, String newGoodsClassName) throws Exception
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        String strSQL = null;
        ResultSet rs = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            newGoodsClassName=newGoodsClassName.trim();
            if(newGoodsClassName==null || newGoodsClassName.equals(""))
            	throw new Exception("更新失败！名称不能为空");
            strSQL = "select * from TabGoodsClassCode where GoodsClassName='"+newGoodsClassName+"' and goodsclass<>"+goodsClass;
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("更新失败！已存在此名称的类别");
            strSQL = "UPDATE TabGoodsClassCode SET GoodsClassName = '" + StrUtility.replaceString(newGoodsClassName, "'", "''") + "' WHERE GoodsClass = '" + StrUtility.replaceString(goodsClass, "'", "''") + "'";
            nRet=stmt.executeUpdate(strSQL);
            if(nRet != 1)
            {
                nRet = -1;
                throw new Exception("更新记录错误");
            }
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

    public int deleteGoodsClass(String goodsClass) throws Exception
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Statement stmt1 = null;
        ResultSet rs1 = null;
        String strSQL;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            stmt1 = conn.createStatement();
            strSQL = "select * from TabGoodsTypeCode where Classid="+goodsClass;
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	strSQL="select * from TabgoodsInfo where goodstype="+rs.getInt("goodstype");
            	rs1=stmt1.executeQuery(strSQL);
            	if(rs1.next())
            		throw new Exception("子类 "+rs.getString("goodsTypeName")+" 存在商品信息，不能删除！");
            }          
            strSQL = "DELETE FROM TabGoodsTypeCode WHERE classid='" + StrUtility.replaceString(goodsClass, "'", "''") + "' ";
            nRet = stmt.executeUpdate(strSQL);
            strSQL = "DELETE FROM TabGoodsClassCode WHERE GoodsClass='" + StrUtility.replaceString(goodsClass, "'", "''") + "' ";
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
            {
                nRet = -1;
                throw new Exception("删除记录错误");
            }
            
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
                if(rs1 != null)
                    rs1.close();
                if(stmt1 != null)
                    stmt1.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return nRet;
    }
}
