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
	OtherToPayForm ibf = (OtherToPayForm)request.getAttribute("billInfo");
	String ifprint = (String)request.getAttribute("ifprint");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
	
%>
<h3 align="center"><%=ibf.getShortname() %>��<%=ibf.getKindName()%>����</h3>
<table width="100%" align="cneter">
<tr >
	<td >���ţ�<b><%=ibf.getBillid() %></b></td>
	<td >������λ��<%=ibf.getFactname()%></td>
	<td align=right>¼��ʱ�䣺<%=ibf.getCreateTime().substring(0,10)%></td>
	
</tr>

	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="0" cellspacing="0" style="border-collapse:collapse">
	<tr><td>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="5" cellspacing="0" style="border-collapse:collapse">


		</tr>
		<tr bgcolor="#ffffff">
			<td align="center" ><b>���<b></td>
			<td align="center"><b>�����ʻ�</b></td>
			<td align="center"><b>���(Ԫ)</b></td>
			<td align="center"><b>��ע</b></td>
			
		</tr>
		<tr bgcolor="#ffffff">
			<td align='center'>1</td>
			<td align='center'><%= ibf.getAccName()%></td>
			<td align='right'><%= nf.format((ibf.getJine()>0?ibf.getJine():-ibf.getJine())) %></td>
			<td align='center'><%= ibf.getMemo()%></td>
			
		</tr>
		
		<tr bgcolor="#ffffff">
			<td align="center"><b>�ϼ�</b></td><td></td>
			<td align="right"><u><%=nf.format((ibf.getJine()>0?ibf.getJine():-ibf.getJine()))%></u></td><td></td>
		</tr>

		<tr bgcolor="#ffffff">
			<td align="center"><b>��д�ϼ�</b></td>
			<td colspan="3" align="left" ><%chineseMoney cm=new chineseMoney();out.print(cm.getChineseMoney((ibf.getJine()>0?ibf.getJine():-ibf.getJine()))); %></td>
		</tr>
	</table></td></tr>
	</table>
	<table width="100%" align="cneter">
	<tr>
	<td>�Ƶ�Ա��<%=ibf.getOperPerson()%></td>
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
	int kind=0;
	if(ibf.getKind()==18 || ibf.getKind()==19)
		kind=1;
	if(ifprint.equals("1"))
		out.print("window.location='otherToPay.do?type="+kind+"&factory="+ibf.getFactory()+"';");
	else
		out.print("window.close();");
%>
</script>
<%}else{ %>
<p align="center"><input type="button" name="printtr" value=" ��ӡ " onclick="window.location='otherPayItemView.do?billId=<%=ibf.getBillid() %>&deptid=<%=ibf.getDeptid() %>&ifprint=2';">
&nbsp;<input type="button" name="close" value=" �ر� " onclick="window.close();">
<%} %>
</body>

</html>


