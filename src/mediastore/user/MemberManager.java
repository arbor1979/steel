// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MemberManager.java

package mediastore.user;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mediastore.common.DBConnection;
import mediastore.dao.GoodsExportGoods;
import mediastore.util.oConvert;
import mediastore.web.form.MemberInfo;
import mediastore.web.form.ResultsetList;

public class MemberManager
{

    

    public MemberManager()
    {
        
    }

    public int getTotalMemberNum()
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
            strSQL = "SELECT count(*) FROM TabMemberInfo;";
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            {
                nRet = -1;
                throw new Exception("获得系统会员总数失败");
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
	public List getMemberInfoByMemberId(int memberId)
    {
        List oneMember;
       
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        oneMember = new ArrayList();
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "SELECT * FROM TabMemberInfo WHERE MemberId=" + memberId;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
                MemberInfo mi = new MemberInfo();
                mi.setMemberId(memberId);
                mi.setMemberName(rs.getString("MemberName"));
                mi.setDiscount(rs.getFloat("Discount"));
                mi.setCreateTime(rs.getString("CreateTime"));
                mi.setMemberTel(rs.getString("MemberTel"));
                mi.setMemberAddr(rs.getString("MemberAddr"));
                mi.setTotalPayment(rs.getFloat("MemberCount"));
                GoodsExportGoods geg = new GoodsExportGoods();
                String tmpTime = geg.getRecentBuyTimeOfMember(memberId);
                if(tmpTime == null)
                    tmpTime = "";
                mi.setRecentBuyTime(tmpTime);
                oneMember.add(mi);
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
        return oneMember;
    }
    @SuppressWarnings("unchecked")
	public List getMemberInfoByMemberName(String memberName)
    {
        List oneMember;
       
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        oneMember = new ArrayList();
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = "SELECT * FROM TabMemberInfo WHERE MemberName like '%" + memberName+"%'";
            
            rs = stmt.executeQuery(strSQL);
            while(rs.next())
            {
                MemberInfo mi = new MemberInfo();
                mi.setMemberId(Integer.parseInt(rs.getString("memberId")));
                mi.setMemberName(rs.getString("MemberName"));
                mi.setDiscount(rs.getFloat("Discount"));
                mi.setCreateTime(rs.getString("CreateTime"));
                mi.setMemberTel(rs.getString("MemberTel"));
                mi.setMemberAddr(rs.getString("MemberAddr"));
                mi.setTotalPayment(rs.getFloat("MemberCount"));
                GoodsExportGoods geg = new GoodsExportGoods();
                String tmpTime = geg.getRecentBuyTimeOfMember(Integer.parseInt(rs.getString("memberId")));
                if(tmpTime == null)
                    tmpTime = "";
                mi.setRecentBuyTime(tmpTime);
                oneMember.add(mi);
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
        return oneMember;
    }
    //
    @SuppressWarnings("unchecked")
	public ResultsetList getMemberList(String orderField,String id,String name,String tel,String orderStr,int curpage,int pagesize)
    {
    	ResultsetList memlist;
    	memlist = new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        java.sql.Date tmpDate=null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            strSQL="select sum(membercount) from TabMemberInfo";
            rs = stmt.executeQuery(strSQL);
            float totalprice=0;
            if(rs.next())
            	totalprice=rs.getFloat(1);
            rs.close();
            strSQL = "SELECT a.MemberId, a.CreateTime, a.MemberName, a.MemberTel, a.MemberAddr,"+ 
            	"a.membercount FROM TabMemberInfo a where 1=1";
            if(!oConvert.isEmpty(id))
            	strSQL=strSQL+" and a.memberid="+id;
            if(!oConvert.isEmpty(name))
            	strSQL=strSQL+" and a.membername like '%"+name+"%'";
            if(!oConvert.isEmpty(tel))
            	strSQL=strSQL+" and a.membertel like '%"+tel+"%'";
            strSQL=strSQL+" order by "+orderStr;
            rs = stmt.executeQuery(strSQL);
            int RowCount=0;
            if(rs.last())
            	RowCount=rs.getRow();
            memlist.allnum=RowCount;
            memlist.curpage=curpage;
            memlist.pagesize=pagesize;
        	int PageCount = ((RowCount % memlist.pagesize) == 0 ?(RowCount/memlist.pagesize) : (RowCount/memlist.pagesize)+1);
        	memlist.pagecount=PageCount;
        	if(memlist.curpage > PageCount)  
        	{
        		memlist.curpage = PageCount; 
        	}
        	else if(memlist.curpage <= 0)
        	{
        		memlist.curpage = 1;  
        	}
        	rs.absolute((memlist.curpage - 1) * memlist.pagesize + 1);  
        	for(int i=0;i<memlist.pagesize;i++)
        	{
                MemberInfo mi = new MemberInfo();
                mi.setMemberId(rs.getInt("MemberId"));
                mi.setMemberName(rs.getString("MemberName"));
                mi.setCreateTime(rs.getDate("CreateTime").toString());
                mi.setMemberTel(rs.getString("MemberTel"));
                mi.setMemberAddr(rs.getString("MemberAddr"));
                mi.setTotalPayment(rs.getFloat("membercount"));
                mi.setpersent(rs.getFloat("membercount")*100/totalprice);
                tmpDate=getLastBuyByMemid(rs.getString("MemberId"));
                mi.setRecentBuyTime((tmpDate==null?"":tmpDate.toString()));
                memlist.rslist.add(mi);
            	if(!rs.next())
            		break;
            }
            rs.close();
        }
        catch(Exception e) {System.out.println(e.getMessage());}
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
        return memlist;
    }
    //某个会员的消费总额
    public float getTotalPayByMemid(String memberId)
    {
        float nRet=0;
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
            strSQL = "SELECT sum(totaldisprice) FROM TabGoodsExportInfo where memberid="+memberId;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
                nRet = rs.getFloat(1);
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
        return nRet;
    }
    //  某个会员的消费总额
    public Date getLastBuyByMemid(String memberId)
    {
    	Date nRet=null;
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
            strSQL = "SELECT max(exporttime) FROM TabGoodsExportInfo where memberid="+memberId;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
                nRet = rs.getDate(1);
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
        return nRet;
    }
}
