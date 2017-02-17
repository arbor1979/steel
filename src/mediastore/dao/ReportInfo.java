package mediastore.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import mediastore.common.DBConnection;
import mediastore.util.oConvert;
import mediastore.web.form.AccountForm;
import mediastore.web.form.HeadForm;

public class ReportInfo 
{
	public ReportInfo()
	{
		
	}
	
	//商品进销损益转仓汇总
	public List<String[]> getGoodsJXList(String importTime1,String importTime2,int deptid,String group) throws Exception
    {
        List<String[]> ibsri=new ArrayList<String[]>();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String [] otpf;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            
            //入库汇总
            if(group.equals("store"))
            {
	        	strSQL = " SELECT sum(importAmount),b.name FROM TabGoodsImportGoods a inner join TabStore b on a.storeid=b.id "+
	        	"  where a.confirmflage='1' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by a.storeid,b.name";
            }
            else if(group.equals("class"))
            {
            	strSQL = " SELECT sum(importAmount),c.goodsTypename FROM TabGoodsImportGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" inner join TabGoodsTypeCode c on b.goodstype=c.goodstype where a.confirmflage='1' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by b.goodstype,c.goodsTypename";
            }
            else if(group.equals("chandi"))
            {
            	strSQL = " SELECT sum(importAmount),b.chandi FROM TabGoodsImportGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" where a.confirmflage='1' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by b.chandi";
            }
            else if(group.equals("caizhi"))
            {
            	strSQL = " SELECT sum(importAmount),b.caizhi FROM TabGoodsImportGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" where a.confirmflage='1' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by b.caizhi";
            }
            else if(group.equals("goodsid"))
            {
            	strSQL = " SELECT sum(importAmount),b.goodsid FROM TabGoodsImportGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" where a.confirmflage='1' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by b.goodsid";
            }
            rs = stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	otpf = new String[8];
            	otpf[0]=rs.getString(2);
            	otpf[2]=String.valueOf(oConvert.getRound(rs.getDouble(1),4));
            	ibsri.add(otpf);
            }
            rs.close();
            //损益汇总
            if(group.equals("store"))
            {
	            strSQL = " SELECT sum(checkAmount),b.name FROM TabGoodsCheckGoods a inner join TabStore b on a.storeid=b.id "+
	        	"  where a.confirmflage='1' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by a.storeid,b.name";
            }
            else if(group.equals("class"))
            {
            	strSQL = " SELECT sum(checkAmount),c.goodsTypename FROM TabGoodsCheckGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" inner join TabGoodsTypeCode c on b.goodstype=c.goodstype where a.confirmflage='1' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by b.goodstype,c.goodsTypename";
            }
            else if(group.equals("chandi"))
            {
            	strSQL = " SELECT sum(checkAmount),b.chandi FROM TabGoodsCheckGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" where a.confirmflage='1' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by b.chandi";
            }
            else if(group.equals("caizhi"))
            {
            	strSQL = " SELECT sum(checkAmount),b.caizhi FROM TabGoodsCheckGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" where a.confirmflage='1' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by b.caizhi";
            }
            else if(group.equals("goodsid"))
            {
            	strSQL = " SELECT sum(checkAmount),b.goodsid FROM TabGoodsCheckGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" where a.confirmflage='1' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by b.goodsid";
            }
            rs = stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	boolean flag=false;
            	for(int i=0;i<ibsri.size();i++)
            	{
            		otpf=ibsri.get(i);
            		if(otpf[0].equals(rs.getString(2)))
            		{
            			otpf[3]=String.valueOf(oConvert.getRound(rs.getDouble(1),4));
            			flag=true;
            			break;
            		}
            	}
            	if(!flag)
            	{
            		otpf = new String[8];
            		otpf[0]=rs.getString(2);
            		otpf[3]=String.valueOf(oConvert.getRound(rs.getDouble(1),4));
            		ibsri.add(otpf);
            	}
            }
            rs.close();
            //转仓汇总
            if(group.equals("store"))
            {
	            strSQL = " SELECT sum(totalnum),b.name FROM TabChangeStoreInfo a inner join TabStore b on a.fromstore=b.id "+
	        	"  where a.confirmflage='1' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by a.fromstore,b.name";
	            
            }
            else if(group.equals("class") || group.equals("chandi") || group.equals("goodsid") || group.equals("caizhi"))
            {
            	strSQL="select * from TabChangeStoreInfo where 1=0";
            }
            rs = stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	boolean flag=false;
            	for(int i=0;i<ibsri.size();i++)
            	{
            		otpf=ibsri.get(i);
            		if(otpf[0].equals(rs.getString(2)))
            		{
            			otpf[4]=String.valueOf(oConvert.getRound(-rs.getDouble(1),4));
            			flag=true;
            			break;
            		}
            	}
            	if(!flag)
            	{
            		otpf = new String[8];
            		otpf[0]=rs.getString(2);
            		otpf[4]=String.valueOf(-rs.getDouble(1));
            		ibsri.add(otpf);
            	}
            }
            rs.close();
            if(group.equals("store"))
            {
	            strSQL = " SELECT sum(totalnum),b.name FROM TabChangeStoreInfo a inner join TabStore b on a.tostore=b.id "+
	        	"  where a.confirmflage='1' and  a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by a.tostore,b.name";
	            
            }
            else if(group.equals("class") || group.equals("chandi") || group.equals("goodsid") || group.equals("caizhi"))
            {
            	strSQL="select * from TabChangeStoreInfo where 1=0";
            }
            rs = stmt.executeQuery(strSQL); 
            while(rs.next())
            {
            	boolean flag=false;
            	for(int i=0;i<ibsri.size();i++)
            	{
            		otpf=ibsri.get(i);
            		if(otpf[0].equals(rs.getString(2)))
            		{
            			otpf[4]=String.valueOf(oConvert.getRound(oConvert.getDouble(otpf[4], 0)+rs.getDouble(1),4));
            			flag=true;
            			break;
            		}
            	}
            	if(!flag)
            	{
            		otpf = new String[8];
            		otpf[0]=rs.getString(2);
            		otpf[4]=String.valueOf(oConvert.getDouble(otpf[3], 0)+rs.getDouble(1));
            		ibsri.add(otpf);
            	}
            }
            rs.close();
            //出库汇总
            if(group.equals("store"))
            {
	        	strSQL = " SELECT sum(exportAmount),b.name FROM TabGoodsExportGoods a inner join TabStore b on a.storeid=b.id "+
	        	"  where a.confirmflage<>'3' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by a.storeid,b.name";
            }
            else if(group.equals("class"))
            {
            	strSQL = " SELECT sum(exportAmount),c.goodsTypename FROM TabGoodsExportGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" inner join TabGoodsTypeCode c on b.goodstype=c.goodstype where a.confirmflage<>'3' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by b.goodstype,c.goodsTypename";
            }
            else if(group.equals("chandi"))
            {
            	strSQL = " SELECT sum(exportAmount),b.chandi FROM TabGoodsExportGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" where a.confirmflage<>'3' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by b.chandi";
            }
            else if(group.equals("caizhi"))
            {
            	strSQL = " SELECT sum(exportAmount),b.caizhi FROM TabGoodsExportGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" where a.confirmflage<>'3' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by b.caizhi";
            }
            else if(group.equals("goodsid"))
            {
            	strSQL = " SELECT sum(exportAmount),b.goodsid FROM TabGoodsExportGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" where a.confirmflage<>'3' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by b.goodsid";
            }
            rs = stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	boolean flag=false;
            	for(int i=0;i<ibsri.size();i++)
            	{
            		otpf=ibsri.get(i);
            		if(otpf[0].equals(rs.getString(2)))
            		{
            			otpf[6]=String.valueOf(oConvert.getRound(rs.getDouble(1),3));
            			flag=true;
            			break;
            		}
            	}
            	if(!flag)
            	{
            		otpf = new String[8];
            		otpf[0]=rs.getString(2);
            		otpf[6]=String.valueOf(oConvert.getRound(rs.getDouble(1),3));
            		ibsri.add(otpf);
            	}
            }
            rs.close();
            //加工汇总
            if(group.equals("store"))
            {
	        	strSQL = " SELECT sum(Amount),b.name FROM TabKaiPingGoods a inner join TabStore b on a.storeid=b.id "+
	        	"  where a.kind=-1 and a.confirmflage='1' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by a.storeid,b.name";
            }
            else if(group.equals("class"))
            {
            	strSQL = " SELECT sum(Amount),c.goodsTypename FROM TabKaiPingGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" inner join TabGoodsTypeCode c on b.goodstype=c.goodstype where a.kind=-1 and a.confirmflage='1' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by b.goodstype,c.goodsTypename";
            }
            else if(group.equals("chandi"))
            {
            	strSQL = " SELECT sum(Amount),b.chandi FROM TabKaiPingGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" where a.kind=-1 and a.confirmflage='1' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by b.chandi";
            }
            else if(group.equals("caizhi"))
            {
            	strSQL = " SELECT sum(Amount),b.caizhi FROM TabKaiPingGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" where a.kind=-1 and a.confirmflage='1' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by b.caizhi";
            }
            else if(group.equals("goodsid"))
            {
            	strSQL = " SELECT sum(Amount),a.goodsid FROM TabKaiPingGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" where a.kind=-1 and a.confirmflage='1' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by a.goodsid";
            }
            rs = stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	boolean flag=false;
            	for(int i=0;i<ibsri.size();i++)
            	{
            		otpf=ibsri.get(i);
            		if(otpf[0].equals(rs.getString(2)))
            		{
            			otpf[5]=String.valueOf(oConvert.getRound(-rs.getDouble(1),3));
            			flag=true;
            			break;
            		}
            	}
            	if(!flag)
            	{
            		otpf = new String[8];
            		otpf[0]=rs.getString(2);
            		otpf[5]=String.valueOf(oConvert.getRound(-rs.getDouble(1),3));
            		ibsri.add(otpf);
            	}
            }
            rs.close();
            if(group.equals("store"))
            {
	        	strSQL = " SELECT sum(Amount),b.name FROM TabKaiPingGoods a inner join TabStore b on a.storeid=b.id "+
	        	"  where a.kind=1 and a.confirmflage='1' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by a.storeid,b.name";
            }
            else if(group.equals("class"))
            {
            	strSQL = " SELECT sum(Amount),c.goodsTypename FROM TabKaiPingGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" inner join TabGoodsTypeCode c on b.goodstype=c.goodstype where a.kind=1 and a.confirmflage='1' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by b.goodstype,c.goodsTypename";
            }
            else if(group.equals("chandi"))
            {
            	strSQL = " SELECT sum(Amount),b.chandi FROM TabKaiPingGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" where a.kind=1 and a.confirmflage='1' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by b.chandi";
            }
            else if(group.equals("caizhi"))
            {
            	strSQL = " SELECT sum(Amount),b.caizhi FROM TabKaiPingGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" where a.kind=1 and a.confirmflage='1' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by b.caizhi";
            }
            else if(group.equals("goodsid"))
            {
            	strSQL = " SELECT sum(Amount),a.goodsid FROM TabKaiPingGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" where a.kind=1 and a.confirmflage='1' and a.deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
	            strSQL=strSQL+" group by a.goodsid";
            }
            rs = stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	boolean flag=false;
            	for(int i=0;i<ibsri.size();i++)
            	{
            		otpf=ibsri.get(i);
            		if(otpf[0].equals(rs.getString(2)))
            		{
            			otpf[5]=String.valueOf(oConvert.getRound(rs.getDouble(1)+oConvert.getDouble(otpf[5], 0),3));
            			flag=true;
            			break;
            		}
            	}
            	if(!flag)
            	{
            		otpf = new String[8];
            		otpf[0]=rs.getString(2);
            		otpf[5]=String.valueOf(oConvert.getRound(rs.getDouble(1)+oConvert.getDouble(otpf[5], 0),3));
            		ibsri.add(otpf);
            	}
            }
            rs.close();
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
        return ibsri;
    }
	//	商品流动表
	public List<String[]> getGoodsFlowList(String importTime1,String importTime2,int deptid,String group) throws Exception
    {
        List<String[]> ibsri=new ArrayList<String[]>();
        List<String[]> tmpList=new ArrayList<String[]>();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String [] otpf;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            
            //当前盘存汇总
            if(group.equals("store"))
            {
	        	strSQL = " SELECT round(sum(repertoryAmount),4),b.name FROM TabGoodsRepertory a inner join TabStore b on a.storeid=b.id "+
	        	"  where b.deptid="+deptid;
	            strSQL=strSQL+" group by a.storeid,b.name";
            }
            else if(group.equals("class"))
            {
            	strSQL = " SELECT round(sum(repertoryAmount),4),c.goodsTypename FROM TabGoodsRepertory a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" inner join TabGoodsTypeCode c on b.goodstype=c.goodstype inner join TabStore d on a.storeid=d.id where d.deptid="+deptid;
	            strSQL=strSQL+" group by b.goodstype,c.goodsTypename";
            }
            else if(group.equals("chandi"))
            {
            	strSQL = " SELECT Round(sum(repertoryAmount),4),b.chandi FROM TabGoodsRepertory a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" inner join TabStore d on a.storeid=d.id where d.deptid="+deptid;
	            strSQL=strSQL+" group by b.chandi";
            }
            else if(group.equals("caizhi"))
            {
            	strSQL = " SELECT Round(sum(repertoryAmount),4),b.caizhi FROM TabGoodsRepertory a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" inner join TabStore d on a.storeid=d.id where d.deptid="+deptid;
	            strSQL=strSQL+" group by b.caizhi";
            }
            else if(group.equals("goodsid"))
            {
            	strSQL = " SELECT Round(sum(repertoryAmount),4),b.goodsid FROM TabGoodsRepertory a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
	        	" inner join TabStore d on a.storeid=d.id where d.deptid="+deptid;
	            strSQL=strSQL+" group by b.goodsid";
            }
            rs = stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	otpf = new String[8];
            	otpf[0]=rs.getString(2);
            	otpf[7]=String.valueOf(oConvert.getRound(rs.getDouble(1),3));
            	ibsri.add(otpf);
            }
            rs.close();
            if(importTime2.length()>0)
            {
            	Date dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(importTime2);
            	Date nw=new Date();
            	if(dt.before(nw))
            	{
            		dt=new Date(dt.getTime()+1000);
            		tmpList=getGoodsJXList(oConvert.FormDateTimeShort(dt),"",deptid,group);
            		for(int j=0;j<tmpList.size();j++)
            		{
            			String[] tmp=(String[])tmpList.get(j);
            			boolean flag=false;
            			for(int i=0;i<ibsri.size();i++)
            			{
            				otpf=ibsri.get(i);
            				if(otpf[0].equals(tmp[0]))
            				{
            					otpf[7]=String.valueOf(oConvert.getRound(oConvert.getDouble(otpf[7],0)+oConvert.getDouble(tmp[6],0)-oConvert.getDouble(tmp[5],0)-oConvert.getDouble(tmp[4],0)-oConvert.getDouble(tmp[3],0)-oConvert.getDouble(tmp[2],0),4));
            					flag=true;
            					break;
            				}
            			}
            			if(!flag)
        				{
        					otpf=new String[7];
        					otpf[0]=tmp[0];
        					otpf[7]=String.valueOf(oConvert.getRound(oConvert.getDouble(otpf[7],0)+oConvert.getDouble(tmp[6],0)-oConvert.getDouble(tmp[5],0)-oConvert.getDouble(tmp[4],0)-oConvert.getDouble(tmp[3],0)-oConvert.getDouble(tmp[2],0),3));
        					ibsri.add(otpf);
        				}
            		}
            	}
            	
            }
            tmpList=getGoodsJXList(importTime1,importTime2,deptid,group);
            for(int i=0;i<ibsri.size();i++)
    		{
            	otpf=ibsri.get(i);
    			boolean flag=false;
    			for(int j=0;j<tmpList.size();j++)
    			{
    				String[] tmp=(String[])tmpList.get(j);
    				if(otpf[0].equals(tmp[0]))
    				{
    					tmp[7]=otpf[7];
    					tmp[1]=String.valueOf(oConvert.getRound(oConvert.getDouble(otpf[7],0)+oConvert.getDouble(tmp[6],0)-oConvert.getDouble(tmp[5],0)-oConvert.getDouble(tmp[4],0)-oConvert.getDouble(tmp[3],0)-oConvert.getDouble(tmp[2],0),3));
    					flag=true;
    					break;
    				}
    			}
    			if(!flag)
				{
					String tmp[]=new String[8];
					tmp[0]=otpf[0];
					tmp[7]=otpf[7];
					tmp[1]=otpf[7];
					tmpList.add(tmp);
				}
    		}
            
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
        return tmpList;
    }
	
	//现金按帐户汇总
	@SuppressWarnings("unchecked")
	public List getMoneyFlowList(String importTime1,String importTime2,int deptid) throws Exception
    {
        List ibsri=new ArrayList();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        Statement stmt1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        String strSQL = null;
        HeadForm [] otpf;
        HeadForm hf;
        String []tmp;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            stmt1 = conn.createStatement();
            //大类科目
            strSQL="select count(*) from accessType where kind=0";
            rs=stmt.executeQuery(strSQL);
            rs.next();
            int iCount=rs.getInt(1)+4;
            otpf=new HeadForm[iCount];
            hf=new HeadForm();
            hf.setValue("帐户编号");
            hf.setRowspan(2);
            otpf[0]=hf;
            hf=new HeadForm();
            hf.setValue("帐户名称");
            hf.setRowspan(2);
            otpf[1]=hf;
            hf=new HeadForm();
            hf.setValue("期初余额");
            hf.setRowspan(2);
            otpf[2]=hf;
            int i=3;
            strSQL="select * from accessType where kind=0 order by id";
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	hf=new HeadForm();
            	hf.setId(rs.getInt("id"));
            	hf.setValue(rs.getString("name"));
            	strSQL="select count(*) from accessType where kind="+rs.getInt("id");
            	rs1=stmt1.executeQuery(strSQL);
            	rs1.next();
            	hf.setColspan(rs1.getInt(1));
            	otpf[i]=hf;
            	i=i+1;
            }
            hf=new HeadForm();
            hf.setValue("期末余额");
            hf.setRowspan(2);
            otpf[i]=hf;
            ibsri.add(otpf);
            //子类科目
            strSQL="select count(*) from accessType where kind<>0";
            rs=stmt.executeQuery(strSQL);
            rs.next();
            iCount=rs.getInt(1);
            int j=0;
            tmp=new String[iCount];
            for(int k=3;k<otpf.length-1;k++)
            {
            	hf=otpf[k];
            	strSQL="select * from accessType where kind="+hf.getId();
                rs=stmt.executeQuery(strSQL);
                while(rs.next())
                {
                	tmp[j]=rs.getString("name");
                	j=j+1;
                }
            }
            ibsri.add(tmp);
            //获得帐户列表
            iCount=iCount+4;
            strSQL="select * from TabAccount where deptid="+deptid;
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	tmp=new String[iCount];
            	tmp[0]=rs.getString("id");
            	tmp[1]=rs.getString("accname");
            	ibsri.add(tmp);
            }
            //期初、期末
            for(int k=2;k<ibsri.size();k++)
            {
            	tmp=(String[])ibsri.get(k);
            	if(importTime1.length()>0)
            	{
            		strSQL = "SELECT top 1 (curjine+jine) FROM AccessAccount where acctype="+tmp[0]+" and deptid="+deptid+
            		" and opertime<'"+importTime1+"' order by opertime desc";
            		rs=stmt.executeQuery(strSQL);
            		if(rs.next())
            			tmp[2]=rs.getString(1);
            		else
            			tmp[2]="0";
            	}
            	else
            		tmp[2]="0";
            	
            	if(importTime2.length()>0)
            	{
            		strSQL = "SELECT top 1 (curjine+jine) FROM AccessAccount where acctype="+tmp[0]+" and deptid="+deptid+
            		" and opertime<='"+importTime2+"' order by opertime desc";
            		rs=stmt.executeQuery(strSQL);
            		if(rs.next())
            			tmp[iCount-1]=rs.getString(1);
            		else
            			tmp[iCount-1]="0";
            	}
            	else
            	{
            		strSQL="select jine from TabAccount where id="+tmp[0];
            		rs=stmt.executeQuery(strSQL);
            		if(rs.next())
            			tmp[iCount-1]=rs.getString(1);
            	}
            }
            rs.close();
            //帐户操作
            for(int k=2;k<ibsri.size();k++)
            {
            	tmp=(String [])ibsri.get(k);
            	String[] typename=(String [])ibsri.get(1);
            	String s="";
            	for(int m=0;m<typename.length;m++)
            	{
            		if(s.length()>0) s=s+",";
            		s=s+"sum(case when typename='"+typename[m]+"' then jine else 0 end)";
            	}
	        	strSQL = " SELECT "+s+" FROM AccessAccount where acctype="+tmp[0]+" and deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and opertime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and opertime<='"+importTime2+"'";
	            rs=stmt.executeQuery(strSQL);
	            rs.next();
	            for(int m=0;m<typename.length;m++)
	            {
	            	tmp[m+3]=rs.getString(m+1);
	            }
            }
            rs.close();
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
        return ibsri;
    }
	//库存预警
	@SuppressWarnings("unchecked")
	public List<String []> getRepertoryWarnList(int deptid,int days,String orderby) throws Exception
    {
        List<String []> ibsri=new ArrayList<String []>();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String []tmp=null;
        int a=5;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd"); 
            Date d=new Date();
            String dt=df.format(new Date(d.getTime() - (days) * 24 * 60 * 60 * 1000));
            //入库商品列表
            strSQL="select goodsname,guige from TabGoodsExportGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid"+
            " where deptid="+deptid+" and createtime>='"+dt+"' group by goodsname,guige";
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	tmp=new String[days+a];
            	tmp[0]=rs.getString(1);
            	tmp[1]=rs.getString(2);
            	ibsri.add(tmp);
            }
            strSQL="select goodstypename,guige from TabGoodsRepertory a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
            "inner join TabGoodsTypeCode c on b.goodstype=c.goodstype"+
            " where storeid in (select storeid from TabStore where deptid="+deptid+") group by goodstypename,guige";
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	boolean flag=false;
            	for(int i=0;i<ibsri.size();i++)
            	{
            		tmp=(String [])ibsri.get(i);
            		if(tmp[0].equals(rs.getString(1)) && tmp[1].equals(rs.getString(2)))
            		{
            			flag=true;
            			break;
            		}
            	}
            	if(!flag)
            	{
            		tmp=new String[days+a];
                    tmp[0]=rs.getString(1);
                    tmp[1]=rs.getString(2);
                    ibsri.add(tmp);
            	}
            	
            }
            df=new SimpleDateFormat("MM-dd");   
            String[] day=new String[days];
            
            for(int i=0;i<days;i++)
            {
            	day[i]=df.format(new Date(d.getTime() - (days-i) * 24 * 60 * 60 * 1000));
            }
            for(int i=0;i<ibsri.size();i++)
            {
            	tmp=ibsri.get(i);
            	double total=0;
            	double avg=0;
            	//最近一周销售
            	for(int j=0;j<days;j++)
            	{
            		strSQL="select sum(exportAmount) from TabGoodsExportGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid"+
            		" where goodsname='"+tmp[0]+"' and guige='"+tmp[1]+"' and deptid="+deptid+
            		" and month(createtime)="+day[j].substring(0,2)+" and day(createtime)="+day[j].substring(3,5);
            		rs=stmt.executeQuery(strSQL);
            		rs.next();
            		tmp[2+j]=String.valueOf(oConvert.getRound(rs.getDouble(1),4));
            		total=total+rs.getDouble(1);
            	}
            	//库存及估计销售天数
            	double rep=0;
            	avg=oConvert.getRound(total/days,4);
            	tmp[days+a-3]=String.valueOf(avg);
            	strSQL="select repertoryAmount from TabGoodsRepertory a inner join TabGoodsInfo b on a.goodsid=b.goodsid"+
            	" inner join TabGoodsTypeCode c on b.goodstype=c.goodstype where goodstypename='"+tmp[0]+"' and guige='"+
            	tmp[1]+"' and storeid in (select storeid from TabStore where deptid="+deptid+")";
            	rs=stmt.executeQuery(strSQL);
            	if(rs.next())
            		rep=rs.getDouble(1);
            	tmp[days+a-2]=String.valueOf(rep);
            	if(avg>0)
            		tmp[days+a-1]=String.valueOf(oConvert.getRound(rep/avg,1));
            	else
            	{
            		if(rep>0)
            			tmp[days+a-1]="999";
            		if(rep<0)
            			tmp[days+a-1]="0";
            	}
            	
            }
            rs.close();
            class ContentComparator implements Comparator 
            {
            	public String orderby;
            	public int days;
            	public int a;
            	 public int compare(Object o1, Object o2) 
            	 {
            	 
            		 String[] c1 = (String[]) o1;
            		 String[] c2 = (String[]) o2;
            		 if(orderby.equals("goodsname"))
            		 {
            			 if (c1[0].compareToIgnoreCase(c2[0])>0) 
            				 return 1;
            			 else if (c1[0].compareToIgnoreCase(c2[0])==0)
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 if(orderby.equals("guige"))
            		 {
            			 if (c1[1].compareToIgnoreCase(c2[1])>0) 
            				 return 1;
            			 else if (c1[1].compareToIgnoreCase(c2[1])==0)
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 if(orderby.equals("avg"))
            		 {
            			 if (oConvert.getDouble(c1[days+a-3],0)>oConvert.getDouble(c2[days+a-3],0) ) 
            				 return 1;
            			 else if (oConvert.getDouble(c1[days+a-3],0)==oConvert.getDouble(c2[days+a-3],0))
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 else if(orderby.equals("rep"))
            		 {
            			 if (oConvert.getDouble(c1[days+a-2],0)>oConvert.getDouble(c2[days+a-2],0) ) 
            				 return 1;
            			 else if (oConvert.getDouble(c1[days+a-2],0)==oConvert.getDouble(c2[days+a-2],0))
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 else if(orderby.equals("eva"))
            		 {
            			 if (oConvert.getDouble(c1[days+a-1],0)>oConvert.getDouble(c2[days+a-1],0) ) 
            				 return 1;
            			 else if (oConvert.getDouble(c1[days+a-1],0)==oConvert.getDouble(c2[days+a-1],0))
            				 return 0;
            			 else 
            				 return -1;
            		 }
					 return 0;
            	 }
            }
        	ContentComparator comp = new ContentComparator();  
            comp.orderby=orderby;
            comp.days=days;      
            comp.a=a;  
            Collections.sort(ibsri,comp);
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
        return ibsri;
    }
	//商品毛利统计
	@SuppressWarnings("unchecked")
	public List<String[]> getLiRunList(String importTime1,String importTime2,int deptid,String group,String orderby) throws Exception
    {
        List<String[]> ibsri=new ArrayList<String[]>();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String [] tmp;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL=", sum(ExportUnitPrice*exportAmount) as sellprice,sum(importAvgPrice*exportAmount) as buyprice,sum(lirun) as lirunprice from TabGoodsExportGoods a"+
            " inner join TabGoodsInfo b on a.goodsid=b.goodsid inner join TabGoodsExportInfo c on (a.billid=c.billid and a.deptid=c.deptid) "+
            " inner join TabGoodsTypeCode d on b.goodstype=d.goodstype inner join TabFactory e on c.factory=e.id"+
            " left join TabYeWuYuan f on c.yewuid=f.id where (a.confirmflage='1')  and a.deptid="+deptid;
            if(importTime1.length()>0)
            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
            if(importTime2.length()>0)
            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
            strSQL="select "+group+strSQL+" group by "+group;
            rs=stmt.executeQuery(strSQL);
            double total=0;
            while(rs.next())
            {
            	if(rs.getDouble("sellprice")==0 && rs.getDouble("buyprice")==0 && rs.getDouble("lirunprice")==0)
            		continue;
            	tmp=new String[8];
            	if(group.equals("f.name") && rs.getString(1)==null)
            		tmp[0]="公司";
            	else
            		tmp[0]=rs.getString(1);
            	tmp[1]=String.valueOf(oConvert.getRound(rs.getDouble("sellprice"),2));
            	tmp[2]=String.valueOf(oConvert.getRound(rs.getDouble("buyprice"),2));
            	tmp[3]=String.valueOf(oConvert.getRound(rs.getDouble("lirunprice"),2));
            	if(rs.getDouble("buyprice")!=0)
            		tmp[4]=String.valueOf(oConvert.getRound(rs.getDouble("lirunprice")/rs.getDouble("buyprice")*100,2));
            	total=total+rs.getDouble("lirunprice");
            	if(group.indexOf(",")>=0)
            	{
            		tmp[6]=rs.getString(2);
            		tmp[7]=rs.getString(3);
            	}
            	ibsri.add(tmp);
            }
            for(int i=0;i<ibsri.size();i++)
            {
            	tmp=ibsri.get(i);
            	if(total!=0)
            		tmp[5]=String.valueOf(oConvert.getRound(oConvert.getDouble(tmp[3],0)/total*100,2));
            }
            rs.close();
            class ContentComparator implements Comparator 
            {
            	public String orderby;
            	public String s;
            	 public int compare(Object o1, Object o2) 
            	 {
            	 
            		 String[] c1 = (String[]) o1;
            		 String[] c2 = (String[]) o2;
            		 if(orderby.equals("goodsid"))
            		 {
            			 if (c1[0].compareToIgnoreCase(c2[0])>0) 
            				 return 1;
            			 else if (c1[0].compareToIgnoreCase(c2[0])==0)
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 else if(orderby.equals("goodsid desc"))
            		 {
            			 if (c1[0].compareToIgnoreCase(c2[0])<0) 
            				 return 1;
            			 else if (c1[0].compareToIgnoreCase(c2[0])==0)
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 else if(orderby.equals(s))
            		 {
            			 if (oConvert.getDouble(c1[oConvert.getInt(s,1)],0)>oConvert.getDouble(c2[oConvert.getInt(s,1)],0) ) 
            				 return 1;
            			 else if (oConvert.getDouble(c1[oConvert.getInt(s,1)],0)==oConvert.getDouble(c2[oConvert.getInt(s,1)],0))
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 else if(orderby.equals(s+" desc"))
            		 {
            			 if (oConvert.getDouble(c1[oConvert.getInt(s,1)],0)<oConvert.getDouble(c2[oConvert.getInt(s,1)],0) ) 
            				 return 1;
            			 else if (oConvert.getDouble(c1[oConvert.getInt(s,1)],0)==oConvert.getDouble(c2[oConvert.getInt(s,1)],0))
            				 return 0;
            			 else 
            				 return -1;
            		 }
					 return 0;
            	 }
            }
        	ContentComparator comp = new ContentComparator();  
            comp.orderby=orderby;
            comp.s=orderby.substring(0,1);
            Collections.sort(ibsri,comp);
            
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
        return ibsri;
    }
	//净利润计算
	public double[] getJingLiRun(String importTime1,String importTime2,int deptid) throws Exception
    {
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        double[] tmp=new double[6];
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            //毛利
            strSQL="select sum(lirun) from TabGoodsExportGoods where confirmflage='1' and deptid="+deptid;
            if(importTime1.length()>0)
            	strSQL=strSQL+" and createtime>='"+importTime1+"'";
            if(importTime2.length()>0)
            	strSQL=strSQL+" and createtime<='"+importTime2+"'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	tmp[0]=rs.getDouble(1);
            //报溢
            strSQL="select sum(totalprice) from TabGoodsCheckInfo where confirmflage='1' and kind=1 and deptid="+deptid;
            if(importTime1.length()>0)
            	strSQL=strSQL+" and checktime>='"+importTime1+"'";
            if(importTime2.length()>0)
            	strSQL=strSQL+" and checktime<='"+importTime2+"'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	tmp[1]=rs.getDouble(1);
            //报损
            strSQL="select sum(totalprice) from TabGoodsCheckInfo where confirmflage='1' and kind=-1 and deptid="+deptid;
            if(importTime1.length()>0)
            	strSQL=strSQL+" and checktime>='"+importTime1+"'";
            if(importTime2.length()>0)
            	strSQL=strSQL+" and checktime<='"+importTime2+"'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	tmp[2]=rs.getDouble(1);
            //其他收入
            strSQL="select sum(totalprice) from TabFeiYongInfo where confirmflage='1' and kind=1 and deptid="+deptid;
            if(importTime1.length()>0)
            	strSQL=strSQL+" and createtime>='"+importTime1+"'";
            if(importTime2.length()>0)
            	strSQL=strSQL+" and createtime<='"+importTime2+"'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	tmp[3]=rs.getDouble(1);
            //费用开支
            strSQL="select sum(totalprice) from TabFeiYongInfo where confirmflage='1' and kind=-1 and deptid="+deptid;
            if(importTime1.length()>0)
            	strSQL=strSQL+" and createtime>='"+importTime1+"'";
            if(importTime2.length()>0)
            	strSQL=strSQL+" and createtime<='"+importTime2+"'";
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	tmp[4]=rs.getDouble(1);
            tmp[5]=tmp[0]+tmp[1]+tmp[2]+tmp[3]+tmp[4];
            
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
        return tmp;
    }
	//资产负债表
	public List<AccountForm[]> getZiChanFuZhai(String qijian,int deptid) throws Exception
    {
		List<AccountForm[]> ibsri=new ArrayList<AccountForm[]>();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String importTime1="";
        String importTime2="";
        int maxlen=10;
        AccountForm[] tmp1=new AccountForm[maxlen];
        AccountForm[] tmp2=new AccountForm[maxlen];
        
        for(int i=0;i<maxlen;i++)
        {
        	tmp1[i]=new AccountForm();
        	tmp2[i]=new AccountForm();
        
        }
        tmp1[0].setAccount("库存商品总值");
        tmp1[1].setAccount("货币资金");
        tmp1[2].setAccount("应收货款");
        tmp1[3].setAccount("其他应收");
        tmp1[4].setAccount("预付货款");
        tmp1[5].setAccount("预付运费");
        tmp1[9].setAccount("<b>资产总计</b>");
        
        tmp2[0].setAccount("应付货款");
        tmp2[1].setAccount("其他应付");
        tmp2[2].setAccount("预收货款");
        tmp2[3].setAccount("<b>负债合计</b>");
        tmp2[4].setAccount("期初资本");
        tmp2[5].setAccount("资本注入");
        tmp2[6].setAccount("资本抽取");
        tmp2[7].setAccount("净利润");
        tmp2[8].setAccount("<b>所有者权益合计</b>");
        tmp2[9].setAccount("<b>负债及所有者权益总计</b>");
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            if(qijian.equals(oConvert.FormDate(new Date(), "yyyyMM")))
            {
            	importTime1=oConvert.getFirstDay();
            	importTime2=oConvert.getLastDay();
            
	            //库存商品
	            strSQL="select sum(repertoryAmount*avgprice) from TabGoodsRepertory where storeid in (select id from TabStore where deptid="+deptid+")";
	            rs=stmt.executeQuery(strSQL);
	            if(rs.next())
	            	tmp1[0].setJine(rs.getDouble(1));
	            
	            //现金银行
	            strSQL="select sum(jine) from TabAccount where deptid="+deptid;
	            rs=stmt.executeQuery(strSQL);
	            if(rs.next())
	            	tmp1[1].setJine(rs.getDouble(1));
	            
	            //应收货款
	            strSQL="select sum(totalprice) from TabGoodsExportInfo where (confirmflage='1' or confirmflage='2') and  paymenttime is null and deptid="+deptid;
	            rs=stmt.executeQuery(strSQL);
	            if(rs.next())
	            	tmp1[2].setJine(rs.getDouble(1));
	            
	            //其他应收
	            strSQL="select sum(jine) from TabOtherToPay where (kind=16 or kind=17) and deptid="+deptid;
	            rs=stmt.executeQuery(strSQL);
	            if(rs.next())
	            	tmp1[3].setJine(-rs.getDouble(1));
	            
	            //预付货款
	            strSQL="select sum(prepay) from TabPrepay where deptid="+deptid;
	            rs=stmt.executeQuery(strSQL);
	            if(rs.next())
	            	tmp1[4].setJine(rs.getDouble(1));
	            
	            //预付运费
	            strSQL="select sum(account) from TabTruck where deptid="+deptid;
	            rs=stmt.executeQuery(strSQL);
	            if(rs.next())
	            	tmp1[5].setJine(rs.getDouble(1));
	            
	            tmp1[tmp1.length-1].setJine(tmp1[0].getJine()+tmp1[1].getJine()+tmp1[2].getJine()+tmp1[3].getJine()+tmp1[4].getJine()+tmp1[5].getJine());
	            
	            //应付货款
	            strSQL="select sum(totalprice) from TabGoodsImportInfo where confirmflage='1' and paymenttime is null and deptid="+deptid;
	            rs=stmt.executeQuery(strSQL);
	            if(rs.next())
	            	tmp2[0].setJine(rs.getDouble(1));
	            
	            //其他应付
	            strSQL="select sum(jine) from TabOtherToPay where (kind=18 or kind=19) and deptid="+deptid;
	            rs=stmt.executeQuery(strSQL);
	            if(rs.next())
	            	tmp2[1].setJine(rs.getDouble(1));
	            
	            //预收货款
	            strSQL="select sum(preshou) from TabPrepay where deptid="+deptid;
	            rs=stmt.executeQuery(strSQL);
	            if(rs.next())
	            	tmp2[2].setJine(rs.getDouble(1));
	            
	            tmp2[3].setJine(tmp2[0].getJine()+tmp2[1].getJine()+tmp2[2].getJine());
	            
	            //期初资本
	            strSQL="select * from ZiChanFuZhai where deptid="+deptid+" and qijian='"+oConvert.FormDate(oConvert.DateAdd(new Date(),2,-1),"yyyyMM")+"'";
            	rs=stmt.executeQuery(strSQL);
            	if(rs.next())
            		tmp2[4].setJine(rs.getDouble("qichu")+rs.getDouble("zhuru")+rs.getDouble("chouqu")+rs.getDouble("lirun")+rs.getDouble("shouru")+rs.getDouble("feiyong"));
	            
	            //资本注入
	            strSQL="select sum(jine) from accessAccount where typename='资本注入' and deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and opertime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and opertime<='"+importTime2+"'";
	            rs=stmt.executeQuery(strSQL);
	            if(rs.next())
	            	tmp2[5].setJine(rs.getDouble(1));
	            
	            //资本抽取
	            strSQL="select sum(jine) from accessAccount where typename='资本抽取' and deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and opertime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and opertime<='"+importTime2+"'";
	            rs=stmt.executeQuery(strSQL);
	            if(rs.next())
	            	tmp2[6].setJine(rs.getDouble(1));
	            
	            //毛利
	            strSQL="select sum(lirun) from TabGoodsExportGoods where (confirmflage='1' or confirmflage='2') and deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and createtime<='"+importTime2+"'";
	            rs=stmt.executeQuery(strSQL);
	            if(rs.next())
	            	tmp2[7].setJine(rs.getDouble(1));
	            //损益
	            strSQL="select sum(totalprice) from TabGoodsCheckInfo where confirmflage='1' and deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and checktime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and checktime<='"+importTime2+"'";
	            rs=stmt.executeQuery(strSQL);
	            if(rs.next())
	            	tmp2[7].setJine(tmp2[7].getJine()+rs.getDouble(1));
	            //其他收入
	            strSQL="select sum(totalprice) from TabFeiYongInfo where confirmflage='1' and kind=1 and deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and createtime<='"+importTime2+"'";
	            rs=stmt.executeQuery(strSQL);
	            if(rs.next())
	            	tmp2[7].setJine(tmp2[7].getJine()+rs.getDouble(1));
	            
	            //费用支出
	            strSQL="select sum(totalprice) from TabFeiYongInfo where confirmflage='1' and kind=-1 and deptid="+deptid;
	            if(importTime1.length()>0)
	            	strSQL=strSQL+" and createtime>='"+importTime1+"'";
	            if(importTime2.length()>0)
	            	strSQL=strSQL+" and createtime<='"+importTime2+"'";
	            rs=stmt.executeQuery(strSQL);
	            if(rs.next())
	            	tmp2[7].setJine(tmp2[7].getJine()+rs.getDouble(1));
	            tmp2[8].setJine(tmp2[4].getJine()+tmp2[5].getJine()+tmp2[6].getJine()+tmp2[7].getJine());
	            tmp2[tmp2.length-1].setJine(tmp2[3].getJine()+tmp2[4].getJine()+tmp2[5].getJine()+tmp2[6].getJine()+tmp2[7].getJine());
            }
            else
            {
            	strSQL="select * from ZiChanFuZhai where deptid="+deptid+" and qijian='"+qijian+"'";
            	rs=stmt.executeQuery(strSQL);
            	if(rs.next())
            	{
            		tmp1[0].setJine(rs.getDouble("kucun"));
            		tmp1[1].setJine(rs.getDouble("xianjin"));
            		tmp1[2].setJine(rs.getDouble("yingshou"));
            		tmp1[3].setJine(rs.getDouble("qitayingshou"));
            		tmp1[4].setJine(rs.getDouble("yufu"));
            		tmp1[9].setJine(rs.getDouble("zichan"));
            		
            		tmp2[0].setJine(rs.getDouble("yingfu"));
            		tmp2[1].setJine(rs.getDouble("qitayingfu"));
            		tmp2[2].setJine(rs.getDouble("yushou"));
            		tmp2[3].setJine(rs.getDouble("fuzhai"));
            		tmp2[4].setJine(rs.getDouble("qichu"));
            		tmp2[5].setJine(rs.getDouble("zhuru"));
            		tmp2[6].setJine(rs.getDouble("chouqu"));
            		tmp2[7].setJine(rs.getDouble("lirun")+rs.getDouble("shouru")+rs.getDouble("feiyong"));
            		tmp2[8].setJine(rs.getDouble("quanyi"));
            		tmp2[9].setJine(rs.getDouble("fuzhai")+rs.getDouble("quanyi"));
            	}
            }
            ibsri.add(tmp1);
            ibsri.add(tmp2);
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
        return ibsri;
    }
	//获得可查询的资产负债期间
	public List<String> getQiJianList(int deptid) throws Exception
    {
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        List<String> tmp=new ArrayList<String>();
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL="select qijian from zichanfuzhai where deptid="+deptid+" order by qijian";
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	String s=new String(rs.getString(1));
            	tmp.add(s);
            }
            tmp.add(oConvert.FormDate(new Date(),"yyyyMM"));
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
        return tmp;
    }
	
	//月销量变化
	public List<double[]> getMonthSell(String qijian,int deptid,int storeid,int classid,int typeid,String chandi) throws Exception
    {
		List<double[]> ibsri=new ArrayList<double[]>();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        double[] tmp1=new double[12];
        double[] tmp2=new double[12];
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = " SELECT month(a.createtime),sum(a.importamount) as allnum FROM TabGoodsImportGoods a "+
            "inner join TabStore b on a.storeid=b.id inner join TabGoodsInfo c on"+
            " a.goodsid=c.goodsid inner join TabGoodsTypeCode d on c.goodstype=d.goodstype inner join TabDepartInfo e "+
            " on a.deptid=e.id where a.confirmflage='1' and year(a.createtime)="+qijian+" and a.deptid="+deptid;
            String whereStr = "";
            if(storeid!= 0)
                whereStr = whereStr + " and a.storeid=" + storeid;
            if(classid!=0)
                whereStr = whereStr + " and d.classid=" +classid;
            if(typeid!=0)
                whereStr = whereStr + " and c.goodstype=" + typeid;
            if(chandi.length()>0)
                whereStr = whereStr + " and c.chandi like '%" + chandi+"%'";
            strSQL=strSQL+whereStr+" group by month(a.createtime) order by month(a.createtime)";
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	for(int i=0;i<tmp1.length;i++)
            	{
            		if(i+1==rs.getInt(1))
            		{
            			tmp1[i]=oConvert.getRound(rs.getDouble(2),4);
            			break;
            		}
            	}
            }
            strSQL = "SELECT month(a.createtime),sum(a.Exportamount) as allnum FROM TabGoodsExportGoods a "+
            "inner join TabStore b on a.storeid=b.id inner join TabGoodsInfo c on"+
            " a.goodsid=c.goodsid inner join TabGoodsTypeCode d on c.goodstype=d.goodstype inner join TabDepartInfo e"+
            " on a.deptid=e.id where a.confirmflage='1' and year(a.createtime)="+qijian+" and a.deptid="+deptid;
            whereStr = ""; 
            if(storeid!= 0)
                whereStr = whereStr + " and a.storeid=" + storeid;
            if(classid!=0)
                whereStr = whereStr + " and d.classid=" +classid;
            if(typeid!=0)
                whereStr = whereStr + " and c.goodstype=" + typeid;
            if(chandi.length()>0)
                whereStr = whereStr + " and c.chandi like '%" + chandi+"%'";
            strSQL=strSQL+whereStr+" group by month(a.createtime) order by month(a.createtime)";
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	for(int i=0;i<tmp2.length;i++)
            	{
            		if(i+1==rs.getInt(1))
            		{
            			tmp2[i]=oConvert.getRound(rs.getDouble(2),4);
            			break;
            		}
            	}
            }
            ibsri.add(tmp1);
            ibsri.add(tmp2);
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
        return ibsri;
    }
	
	//	获得可查询的年份
	public List<String> getYearList(int deptid) throws Exception
    {
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        Date dt=new Date();
        int minYear=oConvert.getYear(dt);
        int maxYear=oConvert.getYear(dt);
        List<String> tmp=new ArrayList<String>();
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL="select min(year(importtime)) from TabGoodsImportInfo where deptid="+deptid;
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	minYear=rs.getInt(1);
            strSQL="select max(year(Exporttime)) from TabGoodsExportInfo where deptid="+deptid;
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	maxYear=rs.getInt(1);
            for(int i=minYear;i<maxYear+1;i++)
            {
            	String s=new String(String.valueOf(i));
            	tmp.add(s);
            }
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
        return tmp;
    }
	//月价格变化
	public List<double[]> getPriceChange(String qijian,int deptid,String goodsname,String guige) throws Exception
    {
		List<double[]> ibsri=new ArrayList<double[]>();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        double[] tmp1=new double[12];
        double[] tmp2=new double[12];
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = " SELECT month(a.createtime),avg(a.importUnitPrice) FROM TabGoodsImportGoods a "+
            " inner join TabGoodsInfo b on a.goodsid=b.goodsid where a.confirmflage='1' and year(a.createtime)="+
            qijian+" and a.deptid="+deptid+" and a.goodsname='"+goodsname+"' and b.guige='"+guige+
            "' group by month(a.createtime) order by month(a.createtime)";
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	for(int i=0;i<tmp1.length;i++)
            	{
            		if(i+1==rs.getInt(1))
            		{
            			tmp1[i]=oConvert.getRound(rs.getDouble(2),4);
            			break;
            		}
            	}
            }
            strSQL = "SELECT month(a.createtime),avg(a.ExportUnitPrice) FROM TabGoodsExportGoods a "+
            " inner join TabGoodsInfo b on a.goodsid=b.goodsid where a.confirmflage='1' and year(a.createtime)="+
            qijian+" and a.deptid="+deptid+" and a.goodsname='"+goodsname+"' and b.guige='"+guige+
            "' group by month(a.createtime) order by month(a.createtime)";
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	for(int i=0;i<tmp2.length;i++)
            	{
            		if(i+1==rs.getInt(1))
            		{
            			tmp2[i]=oConvert.getRound(rs.getDouble(2),4);
            			break;
            		}
            	}
            }
            ibsri.add(tmp1);
            ibsri.add(tmp2);
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
        return ibsri;
    }
//	天价格变化
	public List<double[]> getPriceChangeDay(String qijian,String qijian1,int deptid,String goodsname,String guige) throws Exception
    {
		List<double[]> ibsri=new ArrayList<double[]>();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        Calendar cal=new GregorianCalendar();
        cal.set(oConvert.getInt(qijian,0),oConvert.getInt(qijian1,0)-1,1);   
        int max=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        double[] tmp1=new double[max];
        double[] tmp2=new double[max];
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = " SELECT day(a.createtime),avg(a.importUnitPrice) FROM TabGoodsImportGoods a "+
            " inner join TabGoodsInfo b on a.goodsid=b.goodsid where a.confirmflage='1' and year(a.createtime)="+
            qijian+" and month(a.createtime)="+qijian1+" and a.deptid="+deptid+" and a.goodsname='"+goodsname+"' and b.guige='"+guige+
            "' group by day(a.createtime) order by day(a.createtime)";
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	for(int i=0;i<tmp1.length;i++)
            	{
            		if(i+1==rs.getInt(1))
            		{
            			tmp1[i]=oConvert.getRound(rs.getDouble(2),4);
            			break;
            		}
            	}
            }
            strSQL = "SELECT day(a.createtime),avg(a.ExportUnitPrice) FROM TabGoodsExportGoods a "+
            " inner join TabGoodsInfo b on a.goodsid=b.goodsid where a.confirmflage='1' and year(a.createtime)="+
            qijian+" and month(a.createtime)="+qijian1+" and a.deptid="+deptid+" and a.goodsname='"+goodsname+"' and b.guige='"+guige+
            "' group by day(a.createtime) order by day(a.createtime)";
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	for(int i=0;i<tmp2.length;i++)
            	{
            		if(i+1==rs.getInt(1))
            		{
            			tmp2[i]=oConvert.getRound(rs.getDouble(2),4);
            			break;
            		}
            	}
            }
            ibsri.add(tmp1);
            ibsri.add(tmp2);
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
        return ibsri;
    }
	//月利润变化
	public List<double[]> getMonthLiRun(String qijian,int deptid,int storeid,int classid,int typeid,String chandi) throws Exception
    {
		List<double[]> ibsri=new ArrayList<double[]>();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        double[] tmp1=new double[12];
        double[] tmp2=new double[12];
        double[] tmp3=new double[12];
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL = " SELECT month(a.createtime),sum(a.Exportamount*a.ExportUnitPrice) as allnum FROM TabGoodsExportGoods a "+
            "inner join TabStore b on a.storeid=b.id inner join TabGoodsInfo c on"+
            " a.goodsid=c.goodsid inner join TabGoodsTypeCode d on c.goodstype=d.goodstype inner join TabDepartInfo e "+
            " on a.deptid=e.id where a.confirmflage='1' and year(a.createtime)="+qijian+" and a.deptid="+deptid;
            String whereStr = "";
            if(storeid!= 0)
                whereStr = whereStr + " and a.storeid=" + storeid;
            if(classid!=0)
                whereStr = whereStr + " and d.classid=" +classid;
            if(typeid!=0)
                whereStr = whereStr + " and c.goodstype=" + typeid;
            if(chandi.length()>0)
                whereStr = whereStr + " and c.chandi like '%" + chandi+"%'";
            strSQL=strSQL+whereStr+" group by month(a.createtime) order by month(a.createtime)";
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	for(int i=0;i<tmp1.length;i++)
            	{
            		if(i+1==rs.getInt(1))
            		{
            			tmp1[i]=oConvert.getRound(rs.getDouble(2),4);
            			break;
            		}
            	}
            }
            strSQL = "SELECT month(a.createtime),sum(a.lirun) as allnum FROM TabGoodsExportGoods a "+
            "inner join TabStore b on a.storeid=b.id inner join TabGoodsInfo c on"+
            " a.goodsid=c.goodsid inner join TabGoodsTypeCode d on c.goodstype=d.goodstype inner join TabDepartInfo e"+
            " on a.deptid=e.id where a.confirmflage='1' and year(a.createtime)="+qijian+" and a.deptid="+deptid;
            whereStr = ""; 
            if(storeid!= 0)
                whereStr = whereStr + " and a.storeid=" + storeid;
            if(classid!=0)
                whereStr = whereStr + " and d.classid=" +classid;
            if(typeid!=0)
                whereStr = whereStr + " and c.goodstype=" + typeid;
            if(chandi.length()>0)
                whereStr = whereStr + " and c.chandi like '%" + chandi+"%'";
            strSQL=strSQL+whereStr+" group by month(a.createtime) order by month(a.createtime)";
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	for(int i=0;i<tmp2.length;i++)
            	{
            		if(i+1==rs.getInt(1))
            		{
            			tmp2[i]=oConvert.getRound(rs.getDouble(2),4);
            			break;
            		}
            	}
            }
            //报损报溢
            strSQL="select month(a.createtime),sum(checkAmount*checkUnitPrice) from TabGoodsCheckGoods a "+
            "inner join TabStore b on a.storeid=b.id inner join TabGoodsInfo c on"+
            " a.goodsid=c.goodsid inner join TabGoodsTypeCode d on c.goodstype=d.goodstype inner join TabDepartInfo e"+
            " on a.deptid=e.id where a.confirmflage='1' and year(a.createtime)="+qijian+" and a.deptid="+deptid;
            whereStr = ""; 
            if(storeid!= 0)
                whereStr = whereStr + " and a.storeid=" + storeid;
            if(classid!=0)
                whereStr = whereStr + " and d.classid=" +classid;
            if(typeid!=0)
                whereStr = whereStr + " and c.goodstype=" + typeid;
            if(chandi.length()>0)
                whereStr = whereStr + " and c.chandi like '%" + chandi+"%'";
            strSQL=strSQL+whereStr+" group by month(a.createtime) order by month(a.createtime)";
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	for(int i=0;i<tmp3.length;i++)
            	{
            		if(i+1==rs.getInt(1))
            		{
            			tmp3[i]=oConvert.getRound(rs.getDouble(2),4);
            			break;
            		}
            	}
            }
            
            //其他收入、费用开支
            if(storeid== 0 && classid==0 && typeid==0 && chandi.length()==0)
            {
            	strSQL="select month(a.createtime),sum(totalprice) from TabFeiYongInfo a "+
            	" where a.confirmflage='1' and year(a.createtime)="+qijian+" and a.deptid="+deptid+
            	" group by month(a.createtime) order by month(a.createtime)";
            	rs=stmt.executeQuery(strSQL);
                while(rs.next())
                {
                	for(int i=0;i<tmp3.length;i++)
                	{
                		if(i+1==rs.getInt(1))
                		{
                			tmp3[i]=tmp3[i]+oConvert.getRound(rs.getDouble(2),4);
                			break;
                		}
                	}
                }
            }
            ibsri.add(tmp1);
            ibsri.add(tmp2);
            ibsri.add(tmp3);
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
        return ibsri;
    }
	//	月权益变化
	public List<double[]> getMonthQuanYi(String qijian,int deptid) throws Exception
    {
		List<double[]> ibsri=new ArrayList<double[]>();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        double[] tmp1=new double[12];
        double[] tmp2=new double[12];
        double[] tmp3=new double[12];
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            strSQL="select right(qijian,2),zichan,fuzhai,quanyi from ZiChanFuZhai where deptid="+deptid+" and left(qijian,4)='"+qijian.substring(0,4)+"'";
        	rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	for(int i=0;i<tmp1.length;i++)
            	{
            		if(i+1==rs.getInt(1))
            		{
            			tmp1[i]=oConvert.getRound(rs.getDouble(2),4);
            			tmp2[i]=oConvert.getRound(rs.getDouble(3),4);
            			tmp3[i]=oConvert.getRound(rs.getDouble(4),4);
            			break;
            		}
            	}
            }
            
            ibsri.add(tmp1);
            ibsri.add(tmp2);
            ibsri.add(tmp3);
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
        return ibsri;
    }
	//业务日报
	@SuppressWarnings("unchecked")
	public List<String[]> yeWuDayReport(String importTime1,String importTime2,int deptid,int showall,String orderby) throws Exception
    {
        List<String[]> ibsri=new ArrayList<String[]>();
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        String [] otpf;
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            //入库
            strSQL = " SELECT b.goodsid,a.goodsname,b.guige,sum(importAmount),sum(importAmount*importUnitPrice) FROM TabGoodsImportGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
        	" where a.confirmflage='1' and a.deptid="+deptid;
            if(importTime1.length()>0)
            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
            if(importTime2.length()>0)
            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
            strSQL=strSQL+" group by b.goodsid,a.goodsname,b.guige";
            rs = stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	otpf = new String[10];
            	otpf[0]=rs.getString(1);
            	otpf[1]=rs.getString(2);
            	otpf[2]=rs.getString(3);
            	otpf[3]=String.valueOf(oConvert.getRound(rs.getDouble(4),3));
            	otpf[4]=String.valueOf(oConvert.getRound(rs.getDouble(5),2));
            	ibsri.add(otpf);
            }
            rs.close();
            //出库
            strSQL = " SELECT b.goodsid,a.goodsname,b.guige,sum(exportAmount),sum(lirun),sum(ExportAmount*ExportUnitPrice) FROM TabGoodsExportGoods a inner join TabGoodsInfo b on a.goodsid=b.goodsid "+
        	" where a.confirmflage='1' and a.deptid="+deptid;
            if(importTime1.length()>0)
            	strSQL=strSQL+" and a.createtime>='"+importTime1+"'";
            if(importTime2.length()>0)
            	strSQL=strSQL+" and a.createtime<='"+importTime2+"'";
            strSQL=strSQL+" group by b.goodsid,a.goodsname,b.guige";
            rs = stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	boolean flag=false;
            	for(int i=0;i<ibsri.size();i++)
            	{
            		otpf=ibsri.get(i);
            		if(otpf[0].equals(rs.getString(1)))
            		{
            			otpf[5]=String.valueOf(oConvert.getRound(rs.getDouble(4),3));
            			otpf[6]=String.valueOf(oConvert.getRound(rs.getDouble(6),2));
            			otpf[7]=String.valueOf(oConvert.getRound(rs.getDouble(5),3));
            			flag=true;
            			break;
            		}
            	}
            	if(!flag)
            	{
            		otpf = new String[10];
            		otpf[0]=rs.getString(1);
                	otpf[1]=rs.getString(2);
                	otpf[2]=rs.getString(3);
                	otpf[5]=String.valueOf(oConvert.getRound(rs.getDouble(4),3));
        			otpf[6]=String.valueOf(oConvert.getRound(rs.getDouble(6),2));
        			otpf[7]=String.valueOf(oConvert.getRound(rs.getDouble(5),3));
            		ibsri.add(otpf);
            	}
            }
            rs.close();
            //库存
            if(showall==0)
            {
	            for(int i=0;i<ibsri.size();i++)
	    		{
	            	otpf=(String [])ibsri.get(i);
	            	if(oConvert.FormDate(new java.util.Date(), "yyyy-MM-dd 00:00:00").substring(0,10).equals(importTime2.substring(0,10)))
	            		strSQL = "SELECT sum(RepertoryAmount),sum(RepertoryAmount*avgprice) FROM TabGoodsRepertory WHERE GoodsID='" +otpf[0]+
	            		"' and storeid in (select id from TabStore where deptid="+deptid+")";
	            	else
	            		strSQL = "SELECT sum(RepertoryAmount),sum(RepertoryAmount*avgprice) FROM TabHistoryRepertory WHERE GoodsID='" +otpf[0]+
	            		"' and storeid in (select id from TabStore where deptid="+deptid+") and dt>='"+importTime1+"' and dt<='"+importTime2+"'";
	                rs = stmt.executeQuery(strSQL);
	                if(rs.next())
	                {
	                	otpf[8]=String.valueOf(oConvert.getRound(rs.getDouble(1),3));
	                	otpf[9]=String.valueOf(oConvert.getRound(rs.getDouble(2),2));
	                }
	    		}
            }
            else
            {
            	if(oConvert.FormDate(new java.util.Date(), "yyyy-MM-dd 00:00:00").substring(0,10).equals(importTime2.substring(0,10)))
            		strSQL = "SELECT b.goodsid,c.goodstypename,b.guige,sum(RepertoryAmount),sum(RepertoryAmount*avgprice) FROM TabGoodsRepertory a inner join TabGoodsInfo b on a.goodsid=b.goodsid inner join TabGoodsTypeCode c on b.goodstype=c.goodstype WHERE "+
            		" storeid in (select id from TabStore where deptid="+deptid+") group by b.goodsid,c.goodstypename,b.guige";
            	else
            		strSQL = "SELECT b.goodsid,c.goodstypename,b.guige,sum(RepertoryAmount),sum(RepertoryAmount*avgprice) FROM TabHistoryRepertory a inner join TabGoodsInfo b on a.goodsid=b.goodsid inner join TabGoodsTypeCode c on b.goodstype=c.goodstype WHERE "+
            		" storeid in (select id from TabStore where deptid="+deptid+") and dt>='"+importTime1+"' and dt<='"+importTime2+"' group by b.goodsid,c.goodstypename,b.guige";
                rs = stmt.executeQuery(strSQL);
                while(rs.next())
                {
                	boolean flag=false;
                	for(int i=0;i<ibsri.size();i++)
                	{
                		otpf=ibsri.get(i);
                		if(otpf[0].equals(rs.getString(1)))
                		{
                			otpf[8]=String.valueOf(oConvert.getRound(rs.getDouble(4),3));
                			otpf[9]=String.valueOf(oConvert.getRound(rs.getDouble(5),2));
                			flag=true;
                			break;
                		}
                	}
                	if(!flag)
                	{
                		otpf = new String[10];
                		otpf[0]=rs.getString(1);
                    	otpf[1]=rs.getString(2);
                    	otpf[2]=rs.getString(3);
                    	otpf[8]=String.valueOf(oConvert.getRound(rs.getDouble(4),3));
            			otpf[9]=String.valueOf(oConvert.getRound(rs.getDouble(5),2));
                		ibsri.add(otpf);
                	}
                }
            }
            class ContentComparator implements Comparator 
            {
            	public String orderby;
            	 public int compare(Object o1, Object o2) 
            	 {
            	 
            		 String[] c1 = (String[]) o1;
            		 String[] c2 = (String[]) o2;
            		 if(orderby.equals("goodsid"))
            		 {
            			 if (c1[0].compareTo(c2[0])>0) 
            				 return 1;
            			 else if (c1[0].equals(c2[0]))
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 else if(orderby.equals("goodsname"))
            		 {
            			 if (c1[1].compareTo(c2[1])>0) 
            				 return 1;
            			 else if (c1[1].equals(c2[1]))
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 else if(orderby.equals("guige"))
            		 {
            			 if (c1[2].compareTo(c2[2])>0) 
            				 return 1;
            			 else if (c1[2].equals(c2[2]))
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 else if(orderby.equals("importamount"))
            		 {
            			 if (oConvert.getInt(c1[3],0)>oConvert.getInt(c2[3],0)) 
            				 return 1;
            			 else if (oConvert.getInt(c1[3],0)==oConvert.getInt(c2[3],0))
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 else if(orderby.equals("importmoney"))
            		 {
            			 if (oConvert.getInt(c1[4],0)>oConvert.getInt(c2[4],0)) 
            				 return 1;
            			 else if (oConvert.getInt(c1[4],0)==oConvert.getInt(c2[4],0))
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 else if(orderby.equals("exportamount"))
            		 {
            			 if (oConvert.getInt(c1[5],0)>oConvert.getInt(c2[5],0)) 
            				 return 1;
            			 else if (oConvert.getInt(c1[5],0)==oConvert.getInt(c2[5],0))
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 else if(orderby.equals("exportmoney"))
            		 {
            			 if (oConvert.getInt(c1[6],0)>oConvert.getInt(c2[6],0)) 
            				 return 1;
            			 else if (oConvert.getInt(c1[6],0)==oConvert.getInt(c2[6],0))
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 else if(orderby.equals("exportlirun"))
            		 {
            			 if (oConvert.getInt(c1[7],0)>oConvert.getInt(c2[7],0)) 
            				 return 1;
            			 else if (oConvert.getInt(c1[7],0)==oConvert.getInt(c2[7],0))
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 else if(orderby.equals("kucunamount"))
            		 {
            			 if (oConvert.getInt(c1[8],0)>oConvert.getInt(c2[8],0)) 
            				 return 1;
            			 else if (oConvert.getInt(c1[8],0)==oConvert.getInt(c2[8],0))
            				 return 0;
            			 else 
            				 return -1;
            		 }
            		 else if(orderby.equals("kucunmoney"))
            		 {
            			 if (oConvert.getInt(c1[9],0)>oConvert.getInt(c2[9],0)) 
            				 return 1;
            			 else if (oConvert.getInt(c1[9],0)==oConvert.getInt(c2[9],0))
            				 return 0;
            			 else 
            				 return -1;
            		 }
					return 0;
            	 }
            }
            ContentComparator comp = new ContentComparator();  
            comp.orderby=orderby;
            Collections.sort(ibsri,comp);
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
        return ibsri;
    }
	//不同材质月销售变化
	public double[][] getMonthChangeByCaizhi(String qijian,String caizhi[],int deptid) throws Exception
    {
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        double[][] tmp=new double[12][caizhi.length];
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            for(int j=0;j<caizhi.length;j++)
            {
            	strSQL = " SELECT month(a.createtime),sum(a.exportAmount) FROM TabGoodsExportGoods a "+
            	" inner join TabGoodsInfo b on a.goodsid=b.goodsid where a.confirmflage='1' and year(a.createtime)="+
            	qijian+" and a.deptid="+deptid;
            	if(caizhi[j].length()>0)
            		strSQL=strSQL+" and caizhi='"+caizhi[j]+"'";
            	strSQL=strSQL+" group by month(a.createtime) order by month(a.createtime)";
            	rs=stmt.executeQuery(strSQL);
            	while(rs.next())
            	{
            		for(int i=0;i<12;i++)
            		{
            			if(i+1==rs.getInt(1))
            			{
            				tmp[i][j]=oConvert.getRound(rs.getDouble(2),3);
            				break;
            			}
            		}
            	}
            }
            for(int i=0;i<12;i++)
            	tmp[i][5]=oConvert.getRound(tmp[i][5]-tmp[i][4]-tmp[i][3]-tmp[i][2]-tmp[i][1]-tmp[i][0],3);
            
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
        return tmp;
    }
//	不同材质天销售变化
	public double[][] getDayChangeByCaizhi(String qijian,String qijian1,String []caizhi,int deptid) throws Exception
    {
        DBConnection dbc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        Calendar cal=new GregorianCalendar();
        cal.set(oConvert.getInt(qijian,0),oConvert.getInt(qijian1,0)-1,1);
        cal.setTime(oConvert.parseDateShort(qijian+"-"+qijian1+"-01"));
        int max=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        double[][] tmp=new double[max][caizhi.length];
        
        try
        {
            dbc = new DBConnection();
            conn = dbc.getDBConnection();
            stmt = conn.createStatement();
            for(int j=0;j<caizhi.length;j++)
            {
            	strSQL = " SELECT day(a.createtime),sum(a.exportAmount) FROM TabGoodsExportGoods a "+
            	" inner join TabGoodsInfo b on a.goodsid=b.goodsid where a.confirmflage='1' and year(a.createtime)="+
            	qijian+" and month(a.createtime)="+qijian1+" and a.deptid="+deptid;
            	
            	if(caizhi[j].length()>0)
            		strSQL=strSQL+" and caizhi='"+caizhi[j]+"'";
            	strSQL=strSQL+" group by day(a.createtime) order by day(a.createtime)";
            	
            	rs=stmt.executeQuery(strSQL);
            	while(rs.next())
            	{
            		for(int i=0;i<tmp.length;i++)
            		{
            			if(i+1==rs.getInt(1))
            			{
            				tmp[i][j]=oConvert.getRound(rs.getDouble(2),3);
            				break;
            			}
            		}
            	}
            }
            for(int i=0;i<tmp.length;i++)
            {
            	for(int k=0;k<caizhi.length-1;k++)
            		tmp[i][caizhi.length-1]=oConvert.getRound(tmp[i][caizhi.length-1]-tmp[i][k],3);
            }
            	
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
        return tmp;
    }

}
