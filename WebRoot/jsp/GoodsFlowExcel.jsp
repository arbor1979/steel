
<%@ page contentType="application/msexcel;charset=UTF-8" language="java"%> 
<%@ page import="mediastore.util.oConvert"%>

<%@ page import="java.util.*"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
</head>
<body>
      
<%
	List flowList = (List)request.getAttribute("flowList");
	String importTime1 = (String)request.getAttribute("importTime1");
	String importTime2 = (String)request.getAttribute("importTime2");
	String groupby = (String)request.getAttribute("groupby");
	
	response.setHeader("Content-disposition","attachment; filename="+oConvert.FormDateShort(new java.util.Date())+".xls"); 
%>
<br>
<h2 align="center" style="font-family: 楷体_GB2312;">商品流动表</h2>
<table width="100%" align="cneter">
<tr >
	<td colspan="10">报表期间: <%=oConvert.getString(importTime1,"期初")%> - <%=oConvert.getString(importTime2,"现在")%>
	&nbsp; 汇总条件:
	<%
	if(groupby.equals("store")) out.print("仓库");
	if(groupby.equals("class")) out.print("类别");
	if(groupby.equals("chandi")) out.print("产地");
	if(groupby.equals("caizhi")) out.print("材质");
	if(groupby.equals("goodsid")) out.print("产品编号");
	%>
	</td>
	<td align="right" colspan="5">当前时间：<%=oConvert.FormDateShort(new java.util.Date())%></td>
</tr>
</table>
	<table width="100%" align="center" bordercolor="#000000" border="1" cellpadding="0" cellspacing="0" style="border-collapse:collapse">
	<tr><td>
	<table width="100%" align="center" bordercolor="#000000" border="1" cellpadding="3" cellspacing="0" style="border-collapse:collapse">

		<tr bgcolor="#ffffff">
		<th></th>
		<th>
		<%
		if(groupby.equals("store")) 
			out.print("仓库名称");
		else if(groupby.equals("class")) 
			out.print("类别名称");
		else if(groupby.equals("chandi")) 
			out.print("产地");
		else if(groupby.equals("chandi")) 
			out.print("产地");
		else if(groupby.equals("goodsid")) 
				out.print("产品编号");
		else if(groupby.equals("caizhi")) 
			out.print("材质");
		%></th>
		<th>期初(吨)</th>
		<th></th>
		<th>入库(吨)</th>
		<th></th>
		<th>损益(吨)</th>
		<th></th>
		<th>转仓(吨)</th>
		<th></th>
		<th>加工(吨)</th>
		<th></th>
		<th>出库(吨)</th>
		<th></th>
		<th>期末(吨)</th>

		</tr>
		<%
		String[] otpf = null;
		double [] dlot=new double[8];
	    for (int index=0; index<flowList.size(); index++)
		{
	    	otpf = (String[])flowList.get(index);
	    	for(int j=1;j<8;j++)
		    	dlot[j]=dlot[j]+oConvert.getDouble(otpf[j],0);
		%>
       <tr bgcolor="#ffffff">  
	        <td align='center' width='3%'><%=index+1 %></td>
	        <td align='center'><%= otpf[0]%></td>
	        <td align='right'><%= oConvert.getString(otpf[1],"0")%></td>
	        <td align='right' width='5'>+</td>
	        <td align='right'><%= oConvert.getString(otpf[2],"0")%></td>
	        <td align='right' width='5'>+</td>
	        <td align='right'><%= oConvert.getString(otpf[3],"0")%></td>
	        <td align='right' width='5'>+</td>
	        <td align='right'><%= oConvert.getString(otpf[4],"0")%></td>
	        <td align='right' width='5'>+</td>
	        <td align='right'><%= oConvert.getString(otpf[5],"0")%></td>
	        <td align='right' width='5'>-</td>
	        <td align='right'><%= oConvert.getString(otpf[6],"0")%></td>
	        <td align='right' width='5'>=</td>
	        <td align='right'><%= oConvert.getString(otpf[7],"0")%></td>
       </tr>
<%
	    }
%>
		
		<tr bgcolor="#ffffff">
			<td></td>
	     	<td align="center"><u>合计</u></td>
	     	<%
	     	for(int j=1;j<8;j++)
	     	{
		    	out.print("<td align=right>"+oConvert.getRound(dlot[j],4)+"</td>");
		    	if(j<7)
		    		out.print("<td></td>");
	     	}
	     	%>
		</tr>
	</table></td></tr>
	</table>
	
<script>
window.close();
</script>
    	
</body>
</html>
