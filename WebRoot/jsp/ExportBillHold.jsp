<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="mediastore.web.form.ExportBillForm"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<script language="javascript">
function viewDetail(url)
{
	window.open(url,'view','top=0,left=0,location=no,directories=no,hotkeys=0,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=800,height=400');
}
</script>
</head>
<%
	List HoldList = (List)request.getAttribute("hbList");
%>
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
  <div id="p_title">
  <tr> 
    <td width="6"></td>
    <td width="379" height="20" class="mainhead" align="centre">意向单处理</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
  </div>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td height="300" align="center" valign="top">   
          
	<IMG src="images/line1.gif" border=0 width=900>
	<form name="form2" method="post" action="" >
	<div id="p_content">
	<table id="tab1"  width="100%" border="0" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">
   
<%
	if(HoldList.size()>0)
	{
%>
		<tr bgcolor="#C2CEDC">
		<th></th>
		<th>意向单号</th>
		<th>客户</th>
		<th>重量(吨)</th>
		<th>金额(元)</th>
		<th>录入员</th>
		<th>录入时间</th>
		<th>操作</th>
		</tr>	
<%
		ExportBillForm ebf;
	    for (int index=0; index<HoldList.size(); index++)
		{
	    	ebf = (ExportBillForm)HoldList.get(index);
%>
	    <tr <%if((index%2)==1) out.print("bgcolor='#CAE4F4'");%> >
	    <td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath()+ "/exportItemsView.do?billId=" + ebf.getBillId()+"&deptid="+ebf.getDeptid() %>');"><IMG src="images/news1.gif" border=0 alt='查看货单明细'></a></td>
        <td align='center'><%= ebf.getBillId()%></td>
        <td align='left'><%= ebf.getFactoryName()%></td>
        <td align='center'><%= ebf.getTotalNum() %></td>
		<td align='center'><%= ebf.getTotalPrice() %></td>
		<td align='center'><%= ebf.getSalesPerson()%></td>
		<td align='center'><%= ebf.getExportTime()%></td>
		<td align='center'>
		<%if(ctx.getUserName().equals(ebf.getSalesPerson())){ %>
		<input type="button" value="处理" onclick="window.navigate('exportBillHold.do?param=deel&billId=<%=ebf.getBillId()%>');">
		<%} %>
		<input type="button" value="删除" onclick="if(confirm('是否删除此意向单？')) window.navigate('exportBillHold.do?param=del&billId=<%=ebf.getBillId()%>');">
		
		</td>
		</tr>
<%
		}
	}
%>
	 	
	</TABLE>
	</div>
	</form>
<%
	if(HoldList.size()==0)
	out.println("<div align=center colspan=6 align=center height=50>没有意向单</div>");
%>
	</td>
        </tr>
        <tr> 
          <td valign="middle" align="center"></td>
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

