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
	CheckBillForm ibf = (CheckBillForm)request.getAttribute("billInfo");
	String skind="";
	if(ibf.getKind()==-1)
		skind="����";
	else
		skind="����";
	String ifprint = (String)request.getAttribute("ifprint");
	List bdl = (List)request.getAttribute("billDetail");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
%>
<br>
<h2 align="center" style="font-family: ����_GB2312;"><%=ibf.getShortname() %>��<%=skind %>����</h2>
<table width="100%" align="cneter">
<tr >
	<td >���ţ�<b><%=ibf.getBillId() %></b></td>
	<td align=right>¼��ʱ�䣺<%=ibf.getCheckTime()%></td>
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
			<td align="center" width=60><b>������(��)</b></td>
			<td align="center" width=60><b>ʵ����(��)</b></td>
			<td align="center" width=60><b><%=skind %>��(��)</b></td>
			<td align="center"><b><%=skind %>��</b></td>
			<td align="center" width=60><b>����(Ԫ/��)</b></td>
			<td align="center"><b>���(Ԫ)</b></td>

		</tr>
		<%
		GoodsCheckGoodsInfo tmpInfo=null;
		
	      	for (int index=0; index<bdl.size(); index++)
			{
	      		tmpInfo = (GoodsCheckGoodsInfo)bdl.get(index);
	      	%>
		<tr bgcolor="#ffffff">

			<td align='left'><%= tmpInfo.getGoodsId() %></td>
			<td align='left'><%= tmpInfo.getGoodsName() %></td>
			<td align='left'><%= tmpInfo.getCaizhi() %></td>
			<td align='left'><%= tmpInfo.getGuige() %></td>
			<td align='left'><%= tmpInfo.getChandi()%></td>
			<td align='right'><%= tmpInfo.getCurRepertoryAmount()%></td>
			<td align='right'><%= oConvert.getRound(tmpInfo.getCurRepertoryAmount()+tmpInfo.getCheckAmount(),4)%></td>
			<td align='right'><%= tmpInfo.getCheckAmount()*ibf.getKind()%></td>
			<td align='right'><%=tmpInfo.getCheckNum()*ibf.getKind()+tmpInfo.getDanwei()%></td>
			<td align='right'><%= tmpInfo.getCheckUnitPrice()%></td>
			<td align='right'><%= nf.format(oConvert.getRound(tmpInfo.getCheckUnitPrice()*tmpInfo.getCheckAmount()*ibf.getKind(),2)) %></td>

		</tr>
		<%			}%>
		
		<tr bgcolor="#ffffff">
			<td align="center" colspan="2"><b>�ϼ�</b></td>
			<td></td><td></td><td></td><td></td><td></td>
			<td align="right"><%=ibf.getTotalNum()*ibf.getKind() %></td><td></td><td></td>
			<td align="right"><u><%=nf.format(ibf.getTotalPrice()*ibf.getKind())%></u></td>
		</tr>

		<tr bgcolor="#ffffff">
			<td align="center" colspan="2"><b>��д�ϼ�</b></td>
			<td colspan="9" align="left" ><%chineseMoney cm=new chineseMoney();out.print(cm.getChineseMoney(ibf.getTotalPrice())); %></td>
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
		out.print("window.navigate('checkGoodsBill.do?kind="+ibf.getKind()+"');");
	else
		out.print("window.close();");
%>
</script>
<%}else{ %>
<p align="center"><input type="button" name="printtr" value=" ��ӡ " onclick="window.navigate('checkItemsView.do?billId=<%=ibf.getBillId() %>&deptid=<%=ibf.getDeptid() %>&ifprint=2');">
&nbsp;<input type="button" name="close" value=" �ر� " onclick="window.close();">
<%} %>
</body>

</html>


