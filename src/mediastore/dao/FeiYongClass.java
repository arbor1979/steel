
package mediastore.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mediastore.common.DBConnection;
import mediastore.util.StrUtility;
import mediastore.web.form.FeiYongTypeForm;

// Referenced classes of package mediastore.dao:
//            GoodsInfo

public class FeiYongClass
{

    public FeiYongClass()
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

    public int getGoodsTypeCount(int classid)
    {
        int tmp=0;
        
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
            strSQL = "SELECT count(*) FROM FeiYongType WHERE classid=" +classid; 
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
                tmp = rs.getInt(1);
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
        return tmp;
    }
	
	public List<FeiYongTypeForm> getGoodsClassList(int kind)
    {
        List<FeiYongTypeForm> tmpList;
        tmpList = new ArrayList<FeiYongTypeForm>();
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
            strSQL = "SELECT * FROM FeiYongClass ";
            if(kind!=0)
            	strSQL=strSQL+"where kind="+kind;
            FeiYongTypeForm tmp=null;
            for(rs = stmt.executeQuery(strSQL); rs.next(); tmpList.add(tmp))
            {
            	tmp = new FeiYongTypeForm();
            	tmp.setId(rs.getInt("id"));
            	tmp.setClassname(rs.getString("feiyongclass"));
            	
            	tmp.setKind(rs.getInt("kind"));
            }
            rs.close();
            for(int i=0;i<tmpList.size();i++)
            {
            	tmp=tmpList.get(i);
            	strSQL = "SELECT count(*) FROM FeiYongType where classid="+tmp.getId();
            	rs = stmt.executeQuery(strSQL); 
            	rs.next(); 
            	tmp.setSubcount(rs.getInt(1));
            	
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
	public List<FeiYongTypeForm> getGoodsTypeList(int classid)
    {
        List tmpList;
        tmpList = new ArrayList<FeiYongTypeForm>();
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
            strSQL = "SELECT a.*,b.feiyongclass FROM FeiYongType a inner join FeiYongClass b on a.classid=b.id where a.classid="+classid;
            FeiYongTypeForm tmp;
            for(rs = stmt.executeQuery(strSQL); rs.next(); tmpList.add(tmp))
            {
                tmp = new FeiYongTypeForm();
                tmp.setId(rs.getInt("id"));
                tmp.setTypename(rs.getString("feiyongtype"));
                tmp.setClassid(classid);
                tmp.setIflock(rs.getBoolean("lock"));
                tmp.setClassname(rs.getString("feiyongclass"));
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
    public int insertNewGoodsClass(FeiYongTypeForm fyf) throws Exception
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
            strSQL = "select * from FeiYongClass where feiyongclass='"+fyf.getClassname()+"'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("添加失败！已存在此名称的类别");
            strSQL = "insert into FeiYongClass(feiyongclass,kind) values ('"+fyf.getClassname()+"',"+fyf.getKind()+")";
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
            {
                nRet = -1;
                throw new Exception("插入新记录错误");
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
            catch(SQLException ex) { }
        }
        return nRet;
    }
    public int insertNewGoodsType(FeiYongTypeForm fyf) throws Exception
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
            strSQL = "select * from FeiYongType where FeiYongType='"+fyf.getTypename()+"'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("添加失败！已存在此名称的类别");
            strSQL = "insert into FeiYongType(classid,FeiYongType) values ("+fyf.getClassid()+",'"+fyf.getTypename()+"')";
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
            {
                nRet = -1;
                throw new Exception("插入新记录错误");
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
            catch(SQLException ex) { }
        }
        return nRet;
    }
    public int updateGoodsClass(FeiYongTypeForm fyf) throws Exception
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
            strSQL = "select * from FeiYongClass where feiyongclass='"+fyf.getClassname()+"' and id<>"+fyf.getId();
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("更新失败！已存在此名称的类别");
            strSQL = "UPDATE FeiYongClass SET feiyongclass = '" + fyf.getClassname() + "' WHERE id ="+fyf.getId();
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
    public int updateGoodsType(FeiYongTypeForm fyf) throws Exception
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
            strSQL = "select * from FeiYongType where FeiYongType='"+fyf.getTypename()+"' and id<>"+fyf.getId();
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("更新失败！已存在此名称的类别");
            strSQL = "UPDATE FeiYongType SET FeiYongType = '" + fyf.getTypename() + "',classid="+fyf.getClassid()+" WHERE id ="+fyf.getId();
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
    public int deleteGoodsClass(int id) throws Exception
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
            String strSQL = "select * FROM FeiYongType WHERE classid="+id;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
                throw new Exception("存在子类，请先删除子类");
            strSQL = "DELETE FROM FeiYongClass WHERE id="+id;
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
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return nRet;
    }
    public int deleteGoodsType(int id) throws Exception
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
            String strSQL = "select * FROM TabFeiYongGoods WHERE typeid="+id;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
                throw new Exception("存在费用明细，不能删除");
            strSQL = "DELETE FROM FeiYongType WHERE id="+id;
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
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return nRet;
    }
    public int getTypeIdByName(String classname,int kind,String typename) throws Exception
    {
//    	费用类别中加入运费
        int classid=0;
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL=null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
    	
	        strSQL = "select * from FeiYongClass where feiyongclass='"+classname+"' and kind="+kind; 
	    	rs = stmt.executeQuery(strSQL);
	    	if(!rs.next())
	    	{
	    		strSQL = "insert into FeiYongClass (feiyongclass,kind) values('"+classname+"',"+kind+")";
	    		stmt.executeUpdate(strSQL);
	    		strSQL = "select @@IDENTITY";
	    		rs=stmt.executeQuery(strSQL);
	    		rs.next();
	    		classid=rs.getInt(1);
	    	}
	    	else
	    		classid=rs.getInt("id");
	    	
	    	strSQL = "select * from FeiYongType where feiyongtype='"+typename+"' and classid="+classid; 
	    	rs = stmt.executeQuery(strSQL);
	    	if(!rs.next())
	    	{
	    		strSQL = "insert into FeiYongType (feiyongtype,classid,lock) values('"+typename+"',"+classid+",1)";
	    		stmt.executeUpdate(strSQL);
	    		strSQL = "select @@IDENTITY";
	    		rs=stmt.executeQuery(strSQL);
	    		rs.next();
	    		nRet=rs.getInt(1);
	    	}
	    	else
	    		nRet=rs.getInt("id");
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
