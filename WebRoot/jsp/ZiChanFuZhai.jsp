<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.util.*"%>
<%@page import="mediastore.util.oConvert"%>
<%@ page import="java.text.NumberFormat"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<script language="JavaScript">
function SubmitThis(deptid)
{
	window.location='ziChanFuZhai.do?deptid='+deptid;
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
    <td width="379" height="20" class="mainhead">资产负债表</td>
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
	int deptid = oConvert.getInt((String)request.getAttribute("deptid"),0);
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);

	AccountForm[] tmp1=(AccountForm[])flowList.get(0);
	AccountForm[] tmp2=(AccountForm[])flowList.get(1);

%>
     <form name="form1" method="post" action="ziChanFuZhai.do">
    	<input type="hidden" name="searchbutton" value="查询"> 	
    	<TABLE  class="mailtable"> 
	  <tr>
          <tr>
          
		<TD align="right"><b>报表期间: </b></TD>
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
    			<option value="<%=dif.getId() %>" <%=(deptid==dif.getId()?"selected":"") %>><%=dif.getShortname()%></option>
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
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE  class="mailtable">  
		<tr bgcolor="#C2CEDC">
			<th ></th>
			<th colspan=2>资产类(元)</th>
			<th colspan=2>负债及所有者权益类(元)</th>
		</tr>
		
	    <tr>
	        <%
	        for(int i=0;i<tmp1.length;i++)
	        {
	        	if((i%2)==1)
	        		out.println("<tr bgcolor=#CAE4F4>");
	        	else
	        		out.println("<tr>");
	        	if(i<tmp1.length-1)
		        	out.println("<td align=center width=20>"+(i+1)+"</td>");
	        	else
	        		out.println("<td></td>");
	        	if(tmp1[i].getAccount().length()>0)
	        	{
	        		out.println("<td align=center>"+tmp1[i].getAccount()+"</td>");
	        		out.println("<td align=right>"+nf.format(tmp1[i].getJine())+"</td>");
	        	}
	        	else
	        		out.println("<td></td><td></td>");
	        	if(tmp2[i].getAccount().length()>0)
	        	{
	        		out.println("<td align=center>"+tmp2[i].getAccount()+"</td>");
	        		out.println("<td align=right>"+nf.format(tmp2[i].getJine())+"</td>");
	        	}
	        	else
	        		out.println("<td></td><td></td>");
	        	
	        }
	        %>
	     </tr>	 
	    <tr>
	    <td></td>
	    <td colspan=6 align=center>平衡关系校验：<b>资产 = 负债 + 所有者权益  </b><%=(oConvert.getRound(tmp1[tmp1.length-1].getJine(),0)==oConvert.getRound(tmp2[tmp2.length-1].getJine(),0)?"<font color=green>√</font>":"<font color=red>×</font>") %></td>
		</tr>
	   
	</TABLE>
	</td>
        </tr>
        <tr> 
          <td valign="middle" align="center">&nbsp;</td>
        </tr>

      </table>
      <img src="chart/<%=filename %>">
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

