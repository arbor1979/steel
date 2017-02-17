<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="mediastore.web.form.*"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
</head>
<%
	List detail = (List)request.getAttribute("detail");
%>
<body>
<table width="98%" align="center">
  <tr> 
    <td><%@include file='../html/head.htm'%></td>
  </tr>
  <tr> 
    <td><%@include file='Menu.jsp'%></td>
  </tr>
  <tr>
  	<td>
<table width="768" border="0" align="center" cellpadding="0" cellspacing="0">
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
    <td width="379" height="20" class="mainhead" align="centre">饰品挂单明晰</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td align="center" valign="top">   
          
	<IMG src="images/line1.gif" border=0>
	<form name="form2" method="post" action="" >
	<div id="p_content">
	<table id="tab1"  width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">
	  <tr>
	     <td colspan="7">
		 </td>
		
	  </tr>
	  <tr>
		 <td colspan="7">
		 </td> 
	  </tr>
   
<%
	int id=0;
	if(detail.size()>0)
	{
%>
		<tr bgcolor="#C2CEDC">
		<th>货物编号</th>
		<th>货物名称</th>
		<th>数量</th>
		<th>单价</th>
		<th>金额</th>
		<th>买方会员</th>
		</tr>	
<%
		GoodsExportGoodsInfo tmp;
	    for (int index=0; index<detail.size(); index++)
		{
	      		tmp = (GoodsExportGoodsInfo)detail.get(index);
	      		id=tmp.getBillId();
%>
	    <tr <%if((index%2)==1) out.print("bgcolor='#CAE4F4'");%> >
        <td align='center'><%= tmp.getGoodsId() %></td>
        <td align='center'><%= tmp.getGoodsName() %></td>
		<td align='right'><%= tmp.getExportAmount() %></td>
		<td align='right'><%= tmp.getExportUnitPrice() %></td>
		<td align='right'><%= tmp.getExportAmount()*tmp.getExportUnitPrice() %></td>
		<td align='center'><%= tmp.getmembername() %></td>
		</tr>
<%
		}
%>
	<tr><td colspan=6 align=center height=50>
	<input type="button" value=" 返回 " onclick="window.history.back(-1);">
<%
	if(detail.size()>0)
	{
%>
		&nbsp;&nbsp;<input type="button" value=" 处理 " onclick="window.navigate('HandlerHoldGoods.do?id=<%=id %>');">
		&nbsp;&nbsp;<input type="button" value=" 删除 " onclick="if(confirm('是否确认删除？')){window.navigate('DeleteHoldGoods.do?id=<%=id %>');}">
<%
	}
%>
	</td></tr>
<%
	}
%>
	 
		
	</TABLE>
	</div>
	</form>


	</td>
        </tr>
        <tr> 
          <td valign="middle" align="center">
</td>
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
<a name="cc" style="display:none"></a> 
</body>
</html>
