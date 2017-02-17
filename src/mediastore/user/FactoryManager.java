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
import mediastore.util.oConvert;
import mediastore.web.form.FactoryInfoForm;
import mediastore.web.form.ResultsetList;

public class FactoryManager
{

    private String strError;

    public FactoryManager()
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
    public FactoryInfoForm getFactByID(int id) throws Exception
    {
    	FactoryInfoForm ui=null;
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
            strSQL = "SELECT a.*,b.name as yewuname FROM TabFactory a left join TabYeWuYuan b on a.yewuyuan=b.id where a.id=" + id;
            rs = stmt.executeQuery(strSQL);
            if( rs.next())
            {
                ui = new FactoryInfoForm();
                ui.setId(rs.getInt("id"));
                ui.setName(rs.getString("name"));
                ui.setAddress(rs.getString("address"));
                ui.setTel(rs.getString("tel"));
                ui.setLinkman(rs.getString("linkman"));
                ui.setFaren(rs.getString("faren"));
                ui.setFax(rs.getString("fax"));
                ui.setMobile(rs.getString("mobile"));
                ui.setTownid(rs.getString("townid"));
                ui.setYewuyuan(rs.getString("yewuyuan"));
                ui.setKind(rs.getString("kind"));
                ui.setBank(rs.getString("bank"));
                ui.setAccount(rs.getString("account"));
                ui.setTaxno(rs.getString("taxno"));
                ui.setDeptid(rs.getInt("deptid"));
                ui.setYewuName(rs.getString("yewuname"));
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
        return ui;
    }
	@SuppressWarnings("unchecked")
	public ResultsetList getFactListByPage(String orderField,FactoryInfoForm fi,int curPage,int pagesize)
    {
		ResultsetList ibsri=new ResultsetList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            strSQL = "SELECT a.*,b.shortname,c.name as yewuname FROM TabFactory a inner join TabDepartInfo b on a.deptid=b.id "+
            " left join TabYeWuYuan c on a.yewuyuan=c.id where del=0";
            if(fi.getName().length()>0)
            {
            	strSQL=strSQL+" and a.name like '%"+fi.getName()+"%'";
            }
            if(fi.getLinkman().length()>0)
            {
            	strSQL=strSQL+" and a.linkman like '%"+fi.getLinkman()+"%'";
            }
            if(fi.getKind().length()>0)
            {
            	String s="";
            	for(int i=0;i<fi.getKind().length();i++)
            	{
            		
            		if(fi.getKind().substring(i, i+1).equals("1"))
            		{
            			if(s.length()>0) s=s+" or ";
            			s=s+"substring(a.kind,"+(i+1)+",1)='1'";
            		}
            			
            		
            	}
            	if(s.length()>0)
            		strSQL=strSQL+" and ("+s+")";
            }
            if(fi.getZhujima().length()>0)
            	strSQL=strSQL+" and a.zhujima like '%"+fi.getZhujima()+"%'";
            if(fi.getDeptid()>0)
            	strSQL=strSQL+" and a.deptid="+fi.getDeptid();
            strSQL=strSQL+"	Order by " + orderField + ";";
            rs = stmt.executeQuery(strSQL);
            if(rs.last())
            {
            	int RowCount=rs.getRow();
            	ibsri.allnum=RowCount;
            	ibsri.curpage=curPage;
            	ibsri.pagesize=pagesize;
            	int PageCount = ((RowCount % ibsri.pagesize) == 0 ?(RowCount/ibsri.pagesize) : (RowCount/ibsri.pagesize)+1);
            	ibsri.pagecount=PageCount;
            	if(ibsri.curpage > PageCount)  
            	{
            		ibsri.curpage = PageCount; 
            	}
            	else if(ibsri.curpage <= 0)
            	{
            		ibsri.curpage = 1;  
            	}
            	rs.absolute((ibsri.curpage - 1) * ibsri.pagesize + 1);  
            	FactoryInfoForm ui;
            	for(int i=0;i<ibsri.pagesize;i++)
            	{
            		ui = new FactoryInfoForm();
                    ui.setId(rs.getInt("id"));
                    ui.setName(rs.getString("name"));
                    ui.setFaren(rs.getString("faren"));
                    ui.setAddress(rs.getString("address"));
                    ui.setTel(rs.getString("tel"));
                    ui.setFax(rs.getString("fax"));
                    ui.setLinkman(rs.getString("linkman"));
                    ui.setMobile(rs.getString("mobile"));
                    ui.setKind(rs.getString("kind"));
                    ui.setYewuName(oConvert.getString(rs.getString("yewuname"),""));
                    ui.setBank(rs.getString("bank"));
                    ui.setAccount(rs.getString("account"));
                    ui.setTaxno(rs.getString("taxno"));
                    ui.setDeptid(rs.getInt("deptid"));
                    ui.setShortname(rs.getString("shortname"));
                    ibsri.rslist.add(ui);
                    if(!rs.next())
	            		break;
                }

            }
            rs.close();
            
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
        return ibsri;
    }
	
	@SuppressWarnings("unchecked")
	public List getFactList(String orderField,FactoryInfoForm fi) throws Exception
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
            strSQL = "SELECT a.*,b.shortname,c.preshou,c.prepay FROM TabFactory a inner join TabDepartInfo b on a.deptid=b.id "+
            " left join TabPrePay c on a.id=c.factory and a.deptid=c.deptid where 1=1 ";
            if(fi.getName().length()>0)
            {
            	strSQL=strSQL+" and a.name like '%"+fi.getName()+"%'";
            }
            if(fi.getLinkman().length()>0)
            {
            	strSQL=strSQL+" and a.linkman like '%"+fi.getLinkman()+"%'";
            }
            if(fi.getKind().length()>0)
            {
            	String s="";
            	for(int i=0;i<fi.getKind().length();i++)
            	{
            		
            		if(fi.getKind().substring(i, i+1).equals("1"))
            		{
            			if(s.length()>0) s=s+" or ";
            			s=s+"substring(a.kind,"+(i+1)+",1)='1'";
            		}
            			
            		
            	}
            	if(s.length()>0)
            		strSQL=strSQL+" and ("+s+")";
            }
            if(fi.getZhujima().length()>0)
            	strSQL=strSQL+" and a.zhujima like '%"+fi.getZhujima()+"%'";
            if(fi.getDeptid()>0)
            	strSQL=strSQL+" and a.deptid="+fi.getDeptid();
            strSQL=strSQL+"	Order by " + orderField + ";";
            FactoryInfoForm ui;
            for(rs = stmt.executeQuery(strSQL); rs.next(); userList.add(ui))
            {
                ui = new FactoryInfoForm();
                ui.setId(rs.getInt("id"));
                ui.setName((rs.getString("name").length()>18?rs.getString("name").substring(0,17)+"..":rs.getString("name")));
                ui.setAddress(rs.getString("address"));
                ui.setTel(rs.getString("tel"));
                ui.setLinkman(rs.getString("linkman"));
                ui.setKind(rs.getString("kind"));
                if(oConvert.isEmpty(rs.getString("preshou")))
                	ui.setPrepay(0);
                else
                	ui.setPrepay(rs.getDouble("preshou"));
                if(oConvert.isEmpty(rs.getString("prepay")))
                	ui.setPrepay1(0);
                else
                	ui.setPrepay1(rs.getDouble("prepay"));
                ui.setDeptid(rs.getInt("deptid"));
                ui.setShortname(rs.getString("shortname"));
            }

            rs.close();
            rs = null;
        }
        catch(Exception e) {throw e;}
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
            String strSQL = "select * from TabFactory where id=" + userId;
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("无此供应商！");
            rs.close();
            strSQL = "select * from TabGoodsImportInfo where factory=" + userId;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("有此供应商的进货记录，不能删除！");
            rs.close();
            strSQL = "select * from TabGoodsExportInfo where factory=" + userId;
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("有此客户的销售记录，不能删除！");
            strSQL = "DELETE FROM TabFactory WHERE ID = " + userId;
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
    public String GeneralZhuJiMa(String name) throws Exception
    {
        String nRet="";
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String wd="";
        String strSQL="";
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            for(int i=0;i<name.length();i++)
            {
            	wd=name.substring(i,i+1);
            	strSQL = "select * from hzpy where hz='" + wd+"'";
            	rs = stmt.executeQuery(strSQL);
            	if(rs.next())
            		nRet=nRet+rs.getString("py");
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
            catch(SQLException e) { }
        }
        return nRet;
    }
    public int insertUserRec(FactoryInfoForm fi) throws Exception
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
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            strSQL = "SELECT * FROM TabFactory where name='"+fi.getName()+"'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("已存在同名厂家，请更换名称");
            rs.close();
            fi.setZhujima(GeneralZhuJiMa(fi.getName()));
            strSQL = "insert into TabFactory( Name,faren,townid, address, tel,fax,linkman,mobile,yewuyuan,kind,bank,account,taxno,zhujima,deptid) values "+
            "('" +fi.getName() + "', '" + fi.getFaren() + "'," +fi.getTownid() + ",'"+fi.getAddress()+"','"+fi.getTel()+
            "','"+fi.getFax()+"','"+fi.getLinkman()+"','"+fi.getMobile()+"','"+fi.getYewuyuan()+"','"+fi.getKind()+
            "','"+fi.getBank()+"','"+fi.getAccount()+"','"+fi.getTaxno()+"','"+fi.getZhujima()+"',"+fi.getDeptid()+")";
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
                throw new Exception("插入新记录错误");
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
            catch(SQLException ex) { }
        }
        return nRet;
    }

    public int updateUserRec(FactoryInfoForm fi) throws Exception
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
            String strSQL = "select * from TabFactory where id=" + fi.getId();
            rs = stmt.executeQuery(strSQL);
            if(!rs.next())
            	throw new Exception("无此供应商！");
            rs.close();
            strSQL = "select * from TabFactory where id<>" + fi.getId()+" and name='"+fi.getName()+"'";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("已存在此名称的供应商！");
            fi.setZhujima(GeneralZhuJiMa(fi.getName()));
           	strSQL = "update TabFactory set name='"+fi.getName()+"',faren='"+fi.getFaren()+"',townid='"+fi.getTownid()+
           	"',address='"+fi.getAddress()+"',tel='"+fi.getTel()+"',fax='"+fi.getFax()+"',linkman='"+fi.getLinkman()+
           	"',mobile='"+fi.getMobile()+"',yewuyuan="+fi.getYewuyuan()+",kind='"+fi.getKind()+"',bank='"+fi.getBank()+
           	"',account='"+fi.getAccount()+"',taxno='"+fi.getTaxno()+"',zhujima='"+fi.getZhujima()+"' WHERE ID = " + fi.getId();         
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
