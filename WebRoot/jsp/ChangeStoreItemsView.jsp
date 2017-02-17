<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.util.chineseMoney"%>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.util.oConvert"%>
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
	ChangeStoreBillForm ibf = (ChangeStoreBillForm)request.getAttribute("billInfo");
	
	String ifprint = (String)request.getAttribute("ifprint");
	List bdl = (List)request.getAttribute("billDetail");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
%>
<br>
<h2 align="center" style="font-family: ����_GB2312;"><%=ibf.getShortname() %>��ת�ֵ���</h2>
<table width="100%" align="cneter">
<tr >
	<td >���ţ�<b><%=ibf.getBillId() %></b></td>
	<td >ת���ֿ⣺<b><%=ibf.getFromname() %></b></td>
	<td >ת��ֿ⣺<b><%=ibf.getToname() %></b></td>
	<td align=right>¼��ʱ�䣺<%=ibf.getCreateTime()%></td>
</tr>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="0" cellspacing="0" style="border-collapse:collapse">
	<tr><td>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="5" cellspacing="0" style="border-collapse:collapse">


		</tr>
		<tr bgcolor="#ffffff">
			<td align="center"><b>��Ʒ���</b></td>
			<td align="center"><b>��Ʒ����</b></td>
			<td align="center"><b>����</b></td>
			<td align="center"><b>���</b></td>
			<td align="center"><b>����</b></td>
			<td align="center" width=60><b>���(��)</b></td>
			<td align="center" width=60><b>����</b></td>
			<td align="center" width=60><b>����(��)</b></td>
			<td align="center" width=60><b>����(Ԫ/��)</b></td>
			<td align="center"><b>���(Ԫ)</b></td>

		</tr>
		<%
		ChangeStoreGoodsForm tmpInfo=null;
		
	      	for (int index=0; index<bdl.size(); index++)
			{
	      		tmpInfo = (ChangeStoreGoodsForm)bdl.get(index);
	      	%>
		<tr bgcolor="#ffffff">
			<td align='left'><%= tmpInfo.getGoodsId() %></td>
			<td align='left'><%= tmpInfo.getGoodsName() %></td>
			<td align='left'><%= tmpInfo.getCaizhi() %></td>
			<td align='left'><%= tmpInfo.getGuige() %></td>
			<td align='left'><%= tmpInfo.getChandi()%></td>
			<td align='right'><%= tmpInfo.getCurRepertory()%></td>
			<td align='right'><%=tmpInfo.getChangeNum()+tmpInfo.getDanwei()%></td>
			<td align='right'><%= tmpInfo.getChangeAmount()%></td>
			<td align='right'><%= tmpInfo.getChangeUnitPrice()%></td>
			<td align='right'><%= nf.format(oConvert.getRound(tmpInfo.getChangeUnitPrice()*tmpInfo.getChangeAmount(),2)) %></td>

		</tr>
		<%			}%>
		
		<tr bgcolor="#ffffff">
			<td align="center" ><b>�ϼ�</b></td>
			<td></td><td></td><td></td><td></td><td></td><td></td>
			<td align="right"><%=ibf.getTotalNum() %></td><td></td>
			<td align="right"><u><%=nf.format(ibf.getTotalPrice())%></u></td>
		</tr>

		<tr bgcolor="#ffffff">
			<td align="center"><b>��д�ϼ�</b></td>
			<td colspan="8" align="left" ><%chineseMoney cm=new chineseMoney();out.print(cm.getChineseMoney(ibf.getTotalPrice())); %></td>
		</tr>
	</table></td></tr>
	</table>
	<table width="100%" align="cneter">
	<tr>
	<td>�Ƶ�Ա��<%=ibf.getCreatePerson()%></td>
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
		out.print("window.navigate('changeStoreBill.do');");
	else
		out.print("window.close();");
%>
</script>
<%}else{ %>
<p align="center"><input type="button" name="printtr" value=" ��ӡ " onclick="window.navigate('changeStoreItemsView.do?billId=<%=ibf.getBillId() %>&deptid=<%=ibf.getDeptid() %>&ifprint=2');">
&nbsp;<input type="button" name="close" value=" �ر� " onclick="window.close();">
<%} %>
</body>

</html>


