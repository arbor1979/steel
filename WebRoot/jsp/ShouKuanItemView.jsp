<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.util.chineseMoney"%>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.text.NumberFormat"%>

<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link rel="stylesheet" href="css/stylePrint.css" type="text/css">
<script language="javascript" src="js/LodopFuncs.js"></script>
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
<div id="main">
<h2 align="center" style="font-family: ����_GB2312;"><%=ibf.getShortname() %>��<%=(ibf.getKind()==1?"��":"��") %>���</h2>
<table width="100%" align="cneter">
<tr >
	<td >���ţ�<b><%=ibf.getBillId() %></b></td>
	<td ><%=(ibf.getKind()==1?"��":"��") %>�λ��<%=ibf.getFactname()%></td>
	<td align=right>¼��ʱ�䣺<%=ibf.getCreateTime().substring(0,10)%></td>
	
</tr>

	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="0" cellspacing="0" style="border-collapse:collapse">
	<tr><td>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="5" cellspacing="0" style="border-collapse:collapse">


		</tr>
		<tr bgcolor="#ffffff">
			<td align="center" ><b>���<b></td>
			<td align="center"><b>���(Ԫ)</b></td>
			<td align="center"><b>֧����ʽ</b></td>
			<td align="center"><b>��ע</b></td>
			
		</tr>
		<tr bgcolor="#ffffff">
			<td align='center'>1</td>
			<td align='right'><%= nf.format(ibf.getTotalPrice()) %></td>
			<td align='center'><%= ibf.getFkname()%></td>
			<td align='left'></td>
			
		</tr>
		
		<tr bgcolor="#ffffff">
			<td align="center"><b>�ϼ�</b></td>
			<td align="right"><u><%=nf.format(ibf.getTotalPrice())%></u></td><td></td><td></td>
		</tr>

		<tr bgcolor="#ffffff">
			<td align="center"><b>��д�ϼ�</b></td>
			<td colspan="3" align="left" ><%chineseMoney cm=new chineseMoney();out.print(cm.getChineseMoney(ibf.getTotalPrice())); %></td>
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
</div>

<script>
	var LODOP;
	function printBill()
	{
		LODOP=getLodop();  
		LODOP.PRINT_INITA(0,0,522,333,"�ո��");	
		//LODOP.PRINT_INIT("��ӡ�ؼ�������ʾ_Lodop����_ȫҳ");
		LODOP.SET_PRINT_PAGESIZE(0,1950,950,"195");
		LODOP.ADD_PRINT_HTM("5%","5%","90%","90%",document.getElementById('main').innerHTML);
		LODOP.PREVIEW();
	}
<%
	if(ifprint.equals("1")){
%>
	window.onload=function(){
		
		printBill();
	};
<%}%>	
</script>
<p align="center"><input type="button" name="printtr" value=" ��ӡ " onclick="printBill();">
&nbsp;<input type="button" name="close" value=" �ر� " onclick="
<%if(ifprint.equals("1"))
		out.print("window.location='yuShouKuan.do?factory="+ibf.getFactory()+"&kind="+ibf.getKind()+"';");
	else
		out.print("window.close();");
%>
">
</body>

</html>


