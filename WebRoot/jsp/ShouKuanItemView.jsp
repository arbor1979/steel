<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.util.chineseMoney"%>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.text.NumberFormat"%>

<html>
<head>
<title>天天阳光钢材进销存系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link rel="stylesheet" href="css/stylePrint.css" type="text/css">
</head>

<body >
<%
	ShouKuanBillForm ibf = (ShouKuanBillForm)request.getAttribute("billInfo");
	String ifprint = (String)request.getAttribute("ifprint");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
	
%>
<br>
<h2 align="center" style="font-family: 楷体_GB2312;"><%=ibf.getShortname() %>【<%=(ibf.getKind()==1?"收":"付") %>款单】</h2>
<table width="100%" align="cneter">
<tr >
	<td >单号：<b><%=ibf.getBillId() %></b></td>
	<td ><%=(ibf.getKind()==1?"付":"收") %>款单位：<%=ibf.getFactname()%></td>
	<td align=right>录入时间：<%=ibf.getCreateTime().substring(0,10)%></td>
	
</tr>

	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="0" cellspacing="0" style="border-collapse:collapse">
	<tr><td>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="5" cellspacing="0" style="border-collapse:collapse">


		</tr>
		<tr bgcolor="#ffffff">
			<td align="center" ><b>序号<b></td>
			<td align="center"><b>金额(元)</b></td>
			<td align="center"><b>支付方式</b></td>
			<td align="center"><b>备注</b></td>
			
		</tr>
		<tr bgcolor="#ffffff">
			<td align='center'>1</td>
			<td align='right'><%= nf.format(ibf.getTotalPrice()) %></td>
			<td align='center'><%= ibf.getFkname()%></td>
			<td align='left'></td>
			
		</tr>
		
		<tr bgcolor="#ffffff">
			<td align="center"><b>合计</b></td>
			<td align="right"><u><%=nf.format(ibf.getTotalPrice())%></u></td><td></td><td></td>
		</tr>

		<tr bgcolor="#ffffff">
			<td align="center"><b>大写合计</b></td>
			<td colspan="3" align="left" ><%chineseMoney cm=new chineseMoney();out.print(cm.getChineseMoney(ibf.getTotalPrice())); %></td>
		</tr>
	</table></td></tr>
	</table>
	<table width="100%" align="cneter">
	<tr>
	<td>制单员：<%=ibf.getOperPerson()%></td>
	<td>&nbsp;</td>
	<td>记帐人：</td>
	<td align=right></td>
	</tr>
	</table>
	<%
if(ifprint.equals("1") || ifprint.equals("2"))
{
%>
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

<%  
	if(ifprint.equals("1"))
		out.print("window.navigate('yuShouKuan.do?factory="+ibf.getFactory()+"&kind="+ibf.getKind()+"');");
	else
		out.print("window.close();");
%>
</script>
<%}else{ %>
<p align="center"><input type="button" name="printtr" value=" 打印 " onclick="window.navigate('shouKuanItemView.do?billId=<%=ibf.getBillId() %>&deptid=<%=ibf.getDeptid() %>&ifprint=2');">
&nbsp;<input type="button" name="close" value=" 关闭 " onclick="window.close();">
<%} %>
</body>

</html>


