<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.NumberFormat"%>
      
<%
List flowList = (List)request.getAttribute("flowList");
String ifprint = (String)request.getAttribute("ifprint");
NumberFormat nf = NumberFormat.getNumberInstance();
nf.setMaximumFractionDigits(2);
nf.setMinimumFractionDigits(2);
if(ifprint.equals("2"))
	response.setHeader("Content-disposition","attachment; filename="+oConvert.FormDateShort(new java.util.Date())+".xls"); 
%>

<html>
<head>
<title>天天阳光钢材进销存系统</title>
<%if(ifprint.equals("1")){%>
<link rel="stylesheet" href="css/stylePrint.css" type="text/css">
<%}%>
</head>
<body>

<br>
<h2 align="center" style="font-family: 楷体_GB2312;">业务日报</h2>
<table width="100%" align="cneter">
<tr >
	<td align="right">当前时间：<%=oConvert.FormDate(new java.util.Date())%></td>
</tr>
</table>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="0" cellspacing="0" style="border-collapse:collapse">
	<tr><td>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="3" cellspacing="0" style="border-collapse:collapse">

		<tr bgcolor="#ffffff">
		<th></th>
		<th>产品编号</th>
		<th>品名</th>
		<th>规格</th>
		<th>进货重量</th>
		<th>进货金额</th>
		<th>进货均价</th>
		<th>销售重量</th>
		<th>销售金额</th>
		<th>销售成本</th>
		<th>毛利</th>
		<th>销售均价</th>
		<th>库存重量</th>
		<th>库存金额</th>
		<th>库存均价</th>
		</tr>
		
		<%
			String[] otpf = null;
			double totalIm=0;
			double totalImPrice=0;
			double totalEx=0;
			double totalExPrice=0;
			double totalMl=0;
			double totalKc=0;
			double totalKcPrice=0;
			
		    for (int index=0; index<flowList.size(); index++)
			{
		    	otpf = (String[])flowList.get(index);
		    	totalIm=totalIm+oConvert.getDouble(otpf[3],0);
		    	totalImPrice=totalImPrice+oConvert.getDouble(otpf[4],0);
		    	totalEx=totalEx+oConvert.getDouble(otpf[5],0);
		    	totalExPrice=totalExPrice+oConvert.getDouble(otpf[6],0);
		    	totalMl=totalMl+oConvert.getDouble(otpf[7],0);
		    	totalKc=totalKc+oConvert.getDouble(otpf[8],0);
		    	totalKcPrice=totalKcPrice+oConvert.getDouble(otpf[9],0);
		%>
	        <tr bgcolor="#ffffff">
	        <td align='center' width=3%><%=index+1 %></td>
	        <td align=center><%= otpf[0]%></td>
	        <td align=center><%= otpf[1]%></td>
	        <td align=center><%= otpf[2]%></td>
	        <td align=right><%= oConvert.getString(otpf[3],"0")%></td>
	        <td align=right><%= oConvert.getString(otpf[4],"0")%></td>
	        <td align=right><%= (oConvert.getDouble(otpf[3],0)!=0?oConvert.getRound(oConvert.getDouble(otpf[4],0)/oConvert.getDouble(otpf[3],0),2):"")%></td>
	        <td align=right><%= oConvert.getString(otpf[5],"0")%></td>
	        <td align=right><%= oConvert.getString(otpf[6],"0")%></td>
	        <td align=right><%= oConvert.getRound(oConvert.getDouble(otpf[6],0)-oConvert.getDouble(otpf[7],0),2)%></td>
	        <td align=right><%= oConvert.getString(otpf[7],"0")%></td>
	        <td align=right><%= (oConvert.getDouble(otpf[5],0)!=0?oConvert.getRound(oConvert.getDouble(otpf[6],0)/oConvert.getDouble(otpf[5],0),2):"")%></td>
	        <td align=right><%= oConvert.getString(otpf[8],"0")%></td>
	        <td align=right><%= oConvert.getString(otpf[9],"0")%></td>
	        <td align=right><%= (oConvert.getDouble(otpf[8],0)!=0?oConvert.getRound(oConvert.getDouble(otpf[9],0)/oConvert.getDouble(otpf[8],0),2):"")%></td>
	     </tr>
	     <%} %>
	     <tr bgcolor="#ffffff">
	     	<td></td>
	     	<td align="center"><u>合计</u></td>
	     	<td></td>
	     	<td></td>
	     	<td align=right><%=oConvert.getRound(totalIm,3)%></td>
	     	<td align=right><%=nf.format(totalImPrice)%></td>
	     	<td></td>
	     	<td align=right><%=oConvert.getRound(totalEx,3)%></td>
	     	<td align=right><%=nf.format(totalExPrice)%></td>
	     	<td align=right><%=nf.format(totalExPrice-totalMl)%></td>
	     	<td align=right><%=nf.format(totalMl)%></td>
	     	<td></td>
	     	<td align=right><%=oConvert.getRound(totalKc,3)%></td>
	     	<td align=right><%=nf.format(totalKcPrice)%></td>
			<td></td>
	     </tr>
       
	</table></td></tr>
	</table>
	
<script>
function printbill()
{
	//printtr.style.display = "none";
	document.write("<object ID='WebBrowser' WIDTH=0 HEIGHT=0 CLASSID='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2'></object>"); 
	WebBrowser.ExecWB(7,1); 
	//window.history.back(-1);
	//printtr.style.display = "";
	//window.opener=null; 
	//window.close(); 
	
}
printbill();
window.history.back(-1);
</script>
    	
</body>
</html>
