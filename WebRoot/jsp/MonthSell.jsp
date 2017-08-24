<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.util.*"%>
<%@page import="mediastore.util.oConvert"%>
<%@ page import="java.text.NumberFormat"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<meta http-equiv="Expires" CONTENT="0">     
<meta http-equiv="Cache-Control" CONTENT="no-cache">     
<meta http-equiv="Pragma" CONTENT="no-cache">    
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<script language="JavaScript">
function SubmitThis(deptid)
{
	window.location='monthSell.do?deptid='+deptid;
}
function mySubmit1(){

	document.form1.classid.value =cat.form1.classid.value;
	document.form1.typeid.value = cat.form1.typeid.value;
	return true;
}
</script>
</head>
<body background='images/bgall.gif'>
<table width="98%" align="center">
  <tr> 
    <td><%@include file='../html/head.htm'%></td>
  </tr>
  <tr> 
    <td><%@include file='Menu.jsp'%></td>
  </tr>
  <tr>
  	<td>
  	
<table width="980" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td width="5" rowspan="4" class="trtable"></td>
    <td width="6" bgcolor="#214984" height="1"></td>
    <td rowspan="4" bgcolor="#214984" width="1" height="1"></td>
    <td bgcolor="#214984" class="10p" colspan="2" height="1"></td>
    <td rowspan="4" width="1" bgcolor="#214984" height="1"></td>
    <td bgcolor="#214984" width="5" height="1"></td>
    <td width="5" rowspan="4" class="trtable"></td>
  </tr>
  <tr> 
    <td width="6"></td>
    <td width="379" height="20" class="mainhead">月进销情况表</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
  <tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td  align="center" valign="top">   
          
	<br>

<%
	List flowList = (List)request.getAttribute("flowList");
	List qjList = (List)request.getAttribute("qjList");
	List departList = (List)request.getAttribute("departList");
	String qijian = (String)request.getAttribute("qijian");
	String filename = (String)request.getAttribute("filename");
	int classid = oConvert.getInt((String)request.getAttribute("classid"),0);
	int typeid = oConvert.getInt((String)request.getAttribute("typeid"),0);
	GoodsImportGoodsInfo gif = (GoodsImportGoodsInfo)request.getAttribute("gif");
	List cdList = (List)request.getAttribute("cdList");
	List StoreList = (List)request.getAttribute("StoreList");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
	double total1=0;
	double total2=0;
	double tmp1[]=(double[])flowList.get(0);
	for(int i=0;i<tmp1.length;i++)
		total1=total1+tmp1[i];
	double tmp2[]=(double[])flowList.get(1);
	for(int i=0;i<tmp2.length;i++)
		total2=total2+tmp2[i];
%>
     <form name="form1" method="post" action="monthSell.do" onsubmit="return mySubmit1();">
     	<input type="hidden" name="classid"  value="">
    	<input type="hidden" name="typeid"  value="">
    	<input type="hidden" name="searchbutton" value="查询"> 	
    	<TABLE  class="mailtable"> 
	  <tr>
          <tr>
          
		<TD align="right"><b>年份: </b></TD>
		<TD >
		<select name="qijian">
		<%
		String tmp="";
		for(int i=0;i<qjList.size();i++)
		{
			tmp=(String)qjList.get(i);
			out.println("<option value='"+tmp+"'"+(qijian.equals(tmp)?"selected":"")+">"+tmp+"</option>");
		}
			
		%>
		</select>
		</td>
		<td  align="right"> 
                <b>仓库:</b>
            </td>
            <td>
            	<SELECT size=1 name="storeId">
            		<option value="">-全部-</option>
            		<%
	            		StoreInfoForm sif=new StoreInfoForm();
            			for(int i=0; i<StoreList.size(); i++)
            			{
            				sif = (StoreInfoForm)StoreList.get(i);
            		%>
            		<option value="<%=sif.getId()%>" <%=(gif.getStoreId()==sif.getId()?"selected":"") %>><%=sif.getName()%></option>
			<%
				}
			%>
            	</SELECT>
            </td>
             <td  align="right"> 
			 <b>产地:</b>
			 </td>
            <td> 
			<select name="chandi">
		   		<option value="" <%if(gif.getChandi().equals("")) out.print("selected");%>>-全部-</option>
		<%
				
				String s[];
				for(int i=0;i<cdList.size();i++)
				{
					
					s=(String[])cdList.get(i);
					if (s[0].equals(gif.getChandi()))
						out.print("<option value='"+s[0]+"' selected>"+s[0]+"</option>");
					else
						out.print("<option value='"+s[0]+"'>"+s[0]+"</option>");
				}
		%>
		      </select> 
            </td>
            <td align="right"> 
                <b>产品类别:</b>
	         </TD>
          	<td colspan=3>
          	<iframe name="cat" id="cat" src="ClassAndType.do?classid=<%=classid %>&typeid=<%=typeid %>" width="300" height="25" frameborder=0 scrolling=no ></iframe>
          	</td>
            <%
		if(ctx.isIfview()) {
		%>
             <td  align="right" > 
                <b>分公司:</b></td>
                <td>
            	<select name="deptid" onchange="SubmitThis(this.value);">
    			<%
    			    	DepartInfoForm dif=new DepartInfoForm();
    			    	for(int i=0;i<departList.size();i++)
    			    	{
    			    		dif=(DepartInfoForm)departList.get(i);
    			%>
    			<option value="<%=dif.getId() %>" <%=(gif.getDeptid()==dif.getId()?"selected":"") %>><%=dif.getShortname()%></option>
    			<%
    			}
    			%>
    			</select>
    			</td>
            <%
            }else{
            %>
            <td>&nbsp;</td><td><input type="hidden" name="deptid" value="<%=ctx.getDeptid() %>"></td>
            <%
            }
            %>
            <td ><input name=searchbutton type=submit value=" 查询 "></TD>
        </tr>
         <tr>
		
		
	</TABLE>
   
	</form>
    	
	<font color=#215385>合计入库：<b><%=oConvert.getRound(total1,4) %></b> 吨，合计销售：<b><%=oConvert.getRound(total2,4) %></b> 吨</font>
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE  class="mailtable">  
		<tr bgcolor="#C2CEDC">
		<th></th>
		<%
		for(int i=0;i<12;i++)
			out.print("<th>"+(i+1)+"月</th>");
		%>
		<th>合计</th>
		</tr>
		<tr>
			<td align=center>入库</td>
		<%
		    for (int i=0; i<tmp1.length; i++)
		    	out.print("<td align=right>"+oConvert.getRound(tmp1[i],4)+"</td>");
		%>
			<td align=right><%=oConvert.getRound(total1,4) %></td>
		</tr>
		<tr>
			<td align=center>出库</td>
		<%
		    for (int i=0; i<tmp2.length; i++)
		    	out.print("<td align=right>"+oConvert.getRound(tmp2[i],4)+"</td>");
		%>
			<td align=right><%=oConvert.getRound(total2,4) %></td>
		</tr>
	</TABLE>
	<img src="chart/<%=filename %>">
	
	</td>
        </tr>
        <tr> 
          <td valign="middle" align="center">&nbsp;</td>
        </tr>

      </table>
      
    </td>
    <td width="5"></td>
  </tr>
  <tr> 
    <td width="6"></td>
    <td colspan="2" class="mainhead">&nbsp;</td>
    <td width="5"></td>
  </tr>
  <tr> 
    <td colspan="8" bgcolor="#214984" height="2"></td>
  </tr>
</table>
</td>
	</tr>
</table>
</body>
</html>

