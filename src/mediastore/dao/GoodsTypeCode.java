// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsTypeCode.java

package mediastore.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mediastore.common.DBConnection;
import mediastore.util.StrUtility;
import mediastore.util.oConvert;
import mediastore.web.form.GoodsTypeInfo;

// Referenced classes of package mediastore.dao:
//            GoodsInfo

public class GoodsTypeCode
{

    public GoodsTypeCode()
    {
    }

    public String getGoodsTypeName(String goodsType)
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
            strSQL = "SELECT GoodsTypeName FROM TabGoodsTypeCode WHERE GoodsType='" + StrUtility.replaceString(goodsType, "'", "''") + "'";
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

   //���ݴ���ID��ȡ�����б�
	@SuppressWarnings("unchecked")
	public List getGoodsTypeList(String classid)
    {
        List goodsTypeList;
        goodsTypeList = new ArrayList();
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
            strSQL = "SELECT GoodsType, GoodsTypeName,goodsclass,goodsclassname FROM TabGoodsTypeCode a inner join "
            	+" TabGoodsClassCode b on a.classid=b.goodsclass";
            if(oConvert.getInt(classid,0)>0)
            	strSQL=strSQL+" where classid="+classid;
            GoodsTypeInfo tmp;
            for(rs = stmt.executeQuery(strSQL); rs.next(); goodsTypeList.add(tmp))
            {
                tmp = new GoodsTypeInfo();
                tmp.setId(rs.getInt("goodsType"));
                tmp.setName(rs.getString("goodsTypeName"));
                tmp.setClassid(rs.getInt("goodsclass"));
                GoodsInfo gi = new GoodsInfo();
                int i=gi.getGoodsTypeCount(rs.getString("goodstype"));
                if(i>0)
                    tmp.setSubNum(i);
                else
                	tmp.setSubNum(0);
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
        return goodsTypeList;
    }

    public int insertNewGoodsType(String goodsTypeName,String classid) throws Exception
    {
        int nRet;
        nRet = 0;
        int maxid;
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
            goodsTypeName=goodsTypeName.trim();
            if(goodsTypeName==null || goodsTypeName.equals(""))
            	throw new Exception("���ʧ�ܣ����Ʋ���Ϊ��");
            strSQL = "select * from TabGoodsTypeCode where GoodsTypeName='"+goodsTypeName+"'";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("���ʧ�ܣ��Ѵ��ڴ��������ƣ����������");
            rs.close();
            strSQL = "select max(goodstype) as maxid from TabGoodsTypeCode";
            rs = stmt.executeQuery(strSQL);
            if(rs.next())
            {
            	if(rs.getString(1)==null)
            		maxid=1;
            	else
            		maxid=rs.getInt(1)+1;
            }
            else
            	maxid=1;
            strSQL = "insert into TabGoodsTypeCode( GoodsType, GoodsTypeName,classid) values (" + maxid + ", '" + StrUtility.replaceString(goodsTypeName, "'", "''") + "',"+classid+")";
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
            {
                nRet = -1;
                throw new Exception("�����¼�¼����");
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

    public int updateGoodsType(String goodsType, String newGoodsTypeName,String classid) throws Exception
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
            newGoodsTypeName=newGoodsTypeName.trim();
            if(newGoodsTypeName==null || newGoodsTypeName.equals(""))
            	throw new Exception("����ʧ�ܣ��������Ʋ���Ϊ��");
            strSQL = "select * from TabGoodsTypeCode where GoodsTypeName='"+newGoodsTypeName+"' and GoodsType<>"+goodsType;
            rs=stmt.executeQuery(strSQL);
            if(rs.next())
            	throw new Exception("����ʧ�ܣ��Ѵ��ڴ����Ƶ�����");
            strSQL = "UPDATE TabGoodsTypeCode SET GoodsTypeName = '" + StrUtility.replaceString(newGoodsTypeName, "'", "''") + "',classid="+classid+" WHERE GoodsType = '" + StrUtility.replaceString(goodsType, "'", "''") + "'";
            nRet=stmt.executeUpdate(strSQL);
            if(nRet != 1)
            {
                nRet = -1;
                throw new Exception("���¼�¼����");
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

    public int deleteGoodsType(String goodsType) throws Exception
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
            strSQL="select * from TabGoodsInfo where goodstype="+goodsType;
            rs=stmt.executeQuery(strSQL);
            while(rs.next())
            {
            	strSQL="select * from TabGoodsImportGoods where goodsid='"+rs.getString("goodsid")+"'";
            	rs1=stmt1.executeQuery(strSQL);
            	if(rs1.next())
            		throw new Exception("����ɾ������Ʒ "+rs.getString("goodsid")+" ��������¼��");
            }
            strSQL = "DELETE FROM TabGoodsInfo WHERE GoodsType='" + StrUtility.replaceString(goodsType, "'", "''") + "' ";
            nRet = stmt.executeUpdate(strSQL);
            strSQL = "DELETE FROM TabGoodsTypeCode WHERE GoodsType='" + StrUtility.replaceString(goodsType, "'", "''") + "' ";
            nRet = stmt.executeUpdate(strSQL);
            if(nRet != 1)
            {
                nRet = -1;
                throw new Exception("ɾ���������");
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
}
