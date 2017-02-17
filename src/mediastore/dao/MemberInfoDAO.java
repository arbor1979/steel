

package mediastore.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mediastore.common.DBConnection;
import mediastore.util.StrUtility;

public class MemberInfoDAO
{

    public MemberInfoDAO()
    {
    }

    public float getDiscount(int memberId)
    {
        float discount;
        discount = 1.0F;
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
            strSQL = "SELECT Discount FROM TabMemberInfo WHERE MemberId=" + memberId;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
                discount = rs.getFloat(1);
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
        return discount;
    }
    public String getMemberName(int memberId)
    {
        String memberName="";
        
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
            strSQL = "SELECT MemberName FROM TabMemberInfo WHERE MemberId=" + memberId;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            	memberName = rs.getString(1);
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
        return memberName;
    }
    public int getMemberCount(int memberId)
    {
        int memberCount;
        memberCount = 0;
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
            strSQL = "SELECT count(memberId) FROM TabMemberInfo WHERE MemberId=" + memberId;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            	memberCount = rs.getInt(1);
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
        return memberCount;
    }
    public int getMemberDaiJin(int memberId)
    {
        int memberCount;
        memberCount = 0;
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
            strSQL = "SELECT daijinquan FROM TabMemberInfo WHERE MemberId=" + memberId;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            	memberCount = rs.getInt(1);
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
        return memberCount;
    }
    public int insertNewMember(String memberId,String memberName,String tel,String addr) throws Exception
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
            strSQL="select * from TabMemberInfo where Memberid="+memberId;
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("已存在此会员卡号，不能添加！");
            rs.close();
            strSQL = "INSERT INTO TabMemberInfo(Memberid,MemberName, MemberTel,MemberAddr ) values ('"+memberId+"','"+memberName+"','"+tel+"','"+addr+"')";
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
            {
                nRet = -1;
                throw new Exception("插入新记录错误");
            }
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
            catch(SQLException ex) { }
        }
        return nRet;
    }

    public int deleteOne(int memberId) throws Exception
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
            String strSQL="select * from TabGoodsExportInfo where memberid="+memberId;
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("有购买记录，不能删除！");
            strSQL = "DELETE FROM TabMemberInfo WHERE MemberId=" + memberId + " ";
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
                nRet = -1;
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
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return nRet;
    }
    //清除会员的积分
    public int clearOne(int memberId) throws Exception
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            String strSQL = "update TabMemberInfo set membercount=0 WHERE MemberId=" + memberId;
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
            	throw new Exception("更新失败！");
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
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    dbc.closeDBConnection(conn);
            }
            catch(SQLException e) { }
        }
        return nRet;
    }

    public int updateDis(int memberId, String method, float point)
    {
        int nRet;
        nRet = 0;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        String strSQL = null;
        float oldDiscount = getDiscount(memberId);
        float newDiscount = oldDiscount;
        if("add".equals(method))
            newDiscount = oldDiscount + point;
        else
        if("sub".equals(method))
            newDiscount = oldDiscount - point;
        else
        if("none".equals(method))
            newDiscount = point;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "UPDATE TabMemberInfo SET Discount = " + newDiscount + " WHERE MemberId = " + memberId;
            stmt.executeUpdate(strSQL);
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
    public int updateMemberName(int memberId, String newname) throws Exception
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
            if(memberId==0)
            	throw new Exception("此客户名称不能更改！");
            strSQL = "UPDATE TabMemberInfo SET Membername = '" + StrUtility.replaceString(newname, "'", "''") + "' WHERE MemberId = " + memberId;
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新姓名失败！");
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
            	if(rs!=null)
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
    public int updateMemberTel(int memberId, String memberTel) throws Exception
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
            strSQL = "UPDATE TabMemberInfo SET MemberTel = '" + StrUtility.replaceString(memberTel, "'", "''") + "' WHERE MemberId = " + memberId;
            int i=stmt.executeUpdate(strSQL);
            if(i!=1)
            	throw new Exception("更新手机号失败！");
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
            	if(rs!=null)
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
    public int updateMemberAddr(int memberId, String memberAddr)
    {
        int nRet;
        nRet = 1;
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        String strSQL = null;
       
        
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "UPDATE TabMemberInfo SET MemberAddr = '" + StrUtility.replaceString(memberAddr, "'", "''") + "' WHERE MemberId = " + memberId;
            stmt.executeUpdate(strSQL);
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
}
