<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.util.chineseMoney"%>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>


<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link rel="stylesheet" href="./css/stylePrint.css" type="text/css">

</head>
<body >
<%
	String ifprint = (String)request.getAttribute("ifprint");
	KaiPingInfoForm ibf = (KaiPingInfoForm)request.getAttribute("ibf");
	List bdl = (List)request.getAttribute("billDetail");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
%>
<br>
<h2 align="center" style="font-family: ����_GB2312;"><%=ibf.getShortname() %>����Ʒ���μӹ�����</h2>
<table width="100%" align="cneter">
<tr >
	<td >���ţ�<b><%=ibf.getBillid() %></b></td>
	<td >��ע��<%=ibf.getMemo() %></td>
	<td align=right>¼��ʱ�䣺<%=ibf.getCreatetime()%></td>
</tr>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="0" cellspacing="0" style="border-collapse:collapse">
	<tr><td>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="5" cellspacing="0" style="border-collapse:collapse">


		</tr>
		<tr bgcolor="#ffffff">
			<td align="center"><b>���</b></td>
			<td align="center"><b>����</b></td>
			<td align="center"><b>���</b></td>
			<td align="center"><b>����</b></td>
			<td align="center"><b>����</b></td>
			<td align="center"><b>���</b></td>
			<td align="center" width=60><b>����</b></td>
			<td align="center" width=60><b>����(��)</b></td>
			<td align="center" width=60><b>�ɱ���(Ԫ/��)</b></td>
			<td align="center"><b>���(Ԫ)</b></td>
		</tr>
		<%
		KaiPingForm tmpInfo=null;
	      	for (int index=0; index<bdl.size(); index++)
			{
	      		tmpInfo = (KaiPingForm)bdl.get(index);
	      	%>
		<tr bgcolor="#ffffff">
			<td align='center'><%= index+1 %></td>
			<td align='center' ><%= (tmpInfo.getKind()==-1?"<font color=green>ԭ��</font>":"<font color=blue>��Ʒ</font>") %></td>
			<td align='center'><%= tmpInfo.getFromGoodsId() %></td>
			<td align='left'><%= tmpInfo.getFromGoodsName() %></td>
			<td align='left'><%= tmpInfo.getCaizhi() %></td>
			<td align='left'><%= tmpInfo.getFromGuige() %></td>
			<td align='right'><%= tmpInfo.getFromNum()%><%= tmpInfo.getDanwei()%></td>
			<td align='right'><%= tmpInfo.getFromAmount()%></td>
			<td align='right'><%= nf.format(tmpInfo.getFromPrice())%></td>
			<td align='right'><%= nf.format(tmpInfo.getJine())%></td>

		</tr>
		<%			}%>
		
		<tr bgcolor="#ffffff">
			<td align="center" colspan=2><b>�ϼ�</b></td>
			<td colspan=8>ԭ��������<%=ibf.getTotalnum1() %>�� ��Ʒ������<%=ibf.getTotalnum2() %>�� ԭ�Ͻ�<%=nf.format(ibf.getTotalprice())%>Ԫ ��Ʒ��<%=nf.format(ibf.getTotalprice())%>Ԫ </td>
		</tr>

		<tr bgcolor="#ffffff">
			<td align="center" colspan=2><b>��д�ϼ�</b></td>
			<td colspan="8" align="left" ><%chineseMoney cm=new chineseMoney();out.print(cm.getChineseMoney(ibf.getTotalprice())); %></td>
		</tr>
	</table></td></tr>
	</table>
	<table width="100%" align="cneter">
	<tr>
	<td>�Ƶ�Ա��<%=ibf.getCreateperson()%></td>
	<td>���Ա��</td>
	<td>�����ˣ�</td>
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
		out.print("window.location='kaiPing.do';");
	else
		out.print("window.close();");
%>
</script>
<%}else{ %>
<p align="center"><input type="button" name="printtr" value=" ��ӡ " onclick="window.location='kaiPingItemsView.do?billId=<%=ibf.getBillid() %>&deptid=<%=ibf.getDeptid() %>&ifprint=2';">
&nbsp;<input type="button" name="close" value=" �ر� " onclick="window.close();">
<%} %>
</body>

</html>


