<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.util.chineseMoney"%>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.text.NumberFormat"%>

<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link rel="stylesheet" href="css/stylePrint.css" type="text/css">
</head>

<body >
<%
	AccountForm af = (AccountForm)request.getAttribute("billInfo");
	String ifprint = (String)request.getAttribute("ifprint");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
%>
<br>
<h2 align="center" style="font-family: ����_GB2312;"><%=af.getShortname() %>��<%=af.getTypename() %>����</h2>
<table width="100%" align="cneter">
<tr >
	<td >���ţ�<b><%=af.getBillid() %></b></td>
	<td align=right>¼��ʱ�䣺<%=af.getOpertime().substring(0,10)%></td>
	
</tr>

	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="0" cellspacing="0" style="border-collapse:collapse">
	<tr><td>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="5" cellspacing="0" style="border-collapse:collapse">


		</tr>
		<tr bgcolor="#ffffff">
			<td align="center" ><b>���<b></td>
			<td align="center" ><b>�ʻ�<b></td>
			<td align="center" ><b>�ڳ�(Ԫ)<b></td>
			<td align="center"><b>����(Ԫ)</b></td>
			<td align="center"><b>����(Ԫ)</b></td>
			<td align="center"><b>��ĩ(Ԫ)</b></td>
			
		</tr>
		
		<tr bgcolor="#ffffff">
			<td align='center'>1</td>
			<td align='center'><%= af.getAccName() %></td>
			<td align='right'><%= nf.format(af.getCurjine()) %></td>
			<%if(af.isInout()){%>
			<td align='right'><%= nf.format(af.getJine()) %></td>
			<td align='left'></td>
			<%}else{%>
			<td align='right'></td>
			<td align='right'><%= nf.format(-af.getJine()) %></td>
			<%}%>
			<td align='right'><%= nf.format(af.getCurjine()+af.getJine()) %></td>
		</tr>
		<tr bgcolor="#ffffff">
			<td align="center" colspan=2><b>��д�ϼ�</b></td>
			<td colspan="4" align="left" ><%chineseMoney cm=new chineseMoney();out.print(cm.getChineseMoney(af.getJine())); %></td>
		</tr>
	</table></td></tr>
	</table>
	<table width="100%" align="cneter">
	<tr>
	<td>�Ƶ�Ա��<%=af.getOperPerson()%></td>
	<td>&nbsp;</td>
	<td>�����ˣ�</td>
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
		out.print("window.navigate('inputOutputMoney.do');");
	else
		out.print("window.close();");
%>
</script>
<%}else{ %>
<p align="center"><input type="button" name="printtr" value=" ��ӡ " onclick="window.navigate('inputOutputMoneyItemView.do?billId=<%=af.getBillid() %>&deptid=<%=af.getDeptid() %>&ifprint=2');">
&nbsp;<input type="button" name="close" value=" �ر� " onclick="window.close();">
<%} %>
</body>

</html>

