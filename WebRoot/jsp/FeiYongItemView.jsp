<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.util.chineseMoney"%>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>


<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link rel="stylesheet" href="css/stylePrint.css" type="text/css">

</head>
<body >
<%
	FeiYongBillForm ibf = (FeiYongBillForm)request.getAttribute("billInfo");
	String ifprint = (String)request.getAttribute("ifprint");
	List bdl = (List)request.getAttribute("billDetail");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
	
%>
<br>
<br>
<h2 align="center" style="font-family: ����_GB2312;"><%=ibf.getShortname() %>��<%=(ibf.getKind()==1?"��������":"����֧��") %>����</h2>
<table width="100%" align="cneter">
<tr >
	<td >���ţ�<b><%=ibf.getBillId() %></b></td>
	<td >�ʻ���<%=ibf.getAccName()%></td>
	<td align=right>�������ڣ�<%=ibf.getBillTime()%></td>
	
</tr>

	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="0" cellspacing="0" style="border-collapse:collapse">
	<tr><td>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="5" cellspacing="0" style="border-collapse:collapse">


		</tr>
		<tr bgcolor="#ffffff">
			<td align="center" ><b>���<b></td>
			<td align="center"><b><%=(ibf.getKind()==1?"����":"����") %>���</b></td>
			<td align="center"><b><%=(ibf.getKind()==1?"����":"����") %>����</b></td>
			<td align="center"><b>���(Ԫ)</b></td>
			<td align="center"><b>����˵��</b></td>
			<td align="center"><b>������</b></td>
			<td align="center"><b>ǩ��</b></td>
			
		</tr>
		<%
		FeiYongGoodsForm tmpInfo=null;
		
	      	for (int index=0; index<bdl.size(); index++)
			{
	      		tmpInfo = (FeiYongGoodsForm)bdl.get(index);
	      	%>
		<tr bgcolor="#ffffff">
			<td align='center'><%= index+1 %></td>
			<td align='center'><%= tmpInfo.getClassname() %></td>
			<td align='center'><%= tmpInfo.getTypeName() %></td>
			<td align='right'><%= nf.format(tmpInfo.getJine()*ibf.getKind()) %></td>
			<td align='left'><%= tmpInfo.getMemo()%></td>
			<td align='center'><%= tmpInfo.getJingshouren()%></td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			
		</tr>
		<%			}%>
		
		<tr bgcolor="#ffffff">
			<td align="center"><b>�ϼ�</b></td>
			<td></td><td></td>
			<td align="right"><u><%=nf.format(ibf.getTotalPrice()*ibf.getKind())%></u></td><td></td><td></td><td></td>
		</tr>

		<tr bgcolor="#ffffff">
			<td align="center"><b>��д�ϼ�</b></td>
			<td colspan="8" align="left" ><%chineseMoney cm=new chineseMoney();out.print(cm.getChineseMoney(ibf.getTotalPrice()*ibf.getKind())); %></td>
		</tr>
	</table></td></tr>
	</table>
	<table width="100%" align="cneter">
	<tr>
	<td>�Ƶ�Ա��<%=ibf.getOperPerson()%></td>
	<td>&nbsp;&nbsp;&nbsp;</td>
	<td>�����ˣ�</td>
	<td>&nbsp;&nbsp;&nbsp;</td>
	<%if(ibf.getKind()==-1){ %>
	<td>�����ˣ�</td>
	<%} %>
	<td align=right>¼��ʱ�䣺<%=ibf.getCreateTime().substring(0,10)%></td>
	</tr>
	</table>
	<%
if(ifprint.equals("1") || ifprint.equals("2") || ifprint.equals("3"))
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
		out.print("window.navigate('feiYongBillInput.do?kind="+ibf.getKind()+"');");
	else if(ifprint.equals("2"))
		out.print("window.close();");
	else if(ifprint.equals("3"))
		out.print("window.navigate('payYunFei.do');");
%>
</script>
<%}else{ %>
<p align="center"><input type="button" name="printtr" value=" ��ӡ " onclick="window.navigate('feiYongItemView.do?billId=<%=ibf.getBillId() %>&deptid=<%=ibf.getDeptid() %>&kind=<%=ibf.getKind() %>&ifprint=2');">
&nbsp;<input type="button" name="close" value=" �ر� " onclick="window.close();">
<%} %>
</body>

</html>


