
<%@ page contentType="application/msexcel;charset=UTF-8" language="java"%> 
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.util.oConvert"%>
<html>
<head>
<title>单点科技饰品进销存</title>
</head>
<body>
      
<%
	ResultsetList userList = (ResultsetList)request.getAttribute("userList");
	response.setHeader("Content-disposition","attachment; filename="+oConvert.FormDateShort(new java.util.Date())+".xls"); 
%>
<br>
<h2 align="center" style="font-family: 楷体_GB2312;">往来单位</h2>
<table width="100%" align="cneter">
<tr >
	<td >
	
	</td>
	<td align="right">当前时间：<%=oConvert.FormDateShort(new java.util.Date())%></td>
</tr>
</table>
	<table width="100%" align="center" bordercolor="#000000" border="1" cellpadding="0" cellspacing="0" style="border-collapse:collapse">
	<tr><td>
	<table width="100%" align="center" bordercolor="#000000" border="1" cellpadding="3" cellspacing="0" style="border-collapse:collapse">

		<tr bgcolor="#ffffff">
		<th>编号</th>
		<th>单位名称</th>
		<th>地址</th>
		<th>固定电话</th>
		<th>联系人</th>
		<th>联系人手机</th>
		<th>类型</th>

		<th>隶属业务员</th>
		<th>开户行</th>
		<th>银行账号</th>
		<th>纳税号</th>

		</tr>
		
		<%
		    FactoryInfoForm tmpUserInfo = null;
		    for (int index=0; index<userList.rslist.size(); index++)
			{
		      		tmpUserInfo = (FactoryInfoForm)userList.rslist.get(index);
		%>
	        <tr bgcolor="#ffffff">
	        <td align="center"><%= tmpUserInfo.getId() %></td>
	        <td align="left"><%= tmpUserInfo.getName() %></td>
	        <td align="left"><%= tmpUserInfo.getAddress() %></td>
	        <td align="center"><%= tmpUserInfo.getTel() %></td>
	        <td align="center"><%= tmpUserInfo.getLinkman() %></td>
	        <td align="center"><%= tmpUserInfo.getMobile() %></td>
	        <%
	        	String kind="";
	        	if(tmpUserInfo.getKind().substring(0,1).equals("1"))
	        		kind="供";
	        	if(tmpUserInfo.getKind().substring(1,2).equals("1"))
	        		kind=kind+" 经";
	        	if(tmpUserInfo.getKind().substring(2,3).equals("1"))
	        		kind=kind+" 客";
	        	if(tmpUserInfo.getKind().substring(3,4).equals("1"))
	        		kind=kind+" 其";
	        %>
	        <td align="center"><%= kind%></td>
	        <td align="center"><%= tmpUserInfo.getYewuName()%></td>
	        <td align="center"><%= tmpUserInfo.getBank()%></td>
	        <td align="right"><%= tmpUserInfo.getAccount()%></td>
	        <td align="right"><%= tmpUserInfo.getTaxno()%></td>
	        </tr>
	        <%
	     }
	        %>
	       
		
	</table></td></tr>
	</table>
	
<script>
window.close();
</script>
    	
</body>
</html>
