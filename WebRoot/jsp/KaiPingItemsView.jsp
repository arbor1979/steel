<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.util.chineseMoney"%>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>


<html>
<head>
<title>天天阳光钢材进销存系统</title>
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
<h2 align="center" style="font-family: 楷体_GB2312;"><%=ibf.getShortname() %>【商品二次加工单】</h2>
<table width="100%" align="cneter">
<tr >
	<td >单号：<b><%=ibf.getBillid() %></b></td>
	<td >备注：<%=ibf.getMemo() %></td>
	<td align=right>录单时间：<%=ibf.getCreatetime()%></td>
</tr>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="0" cellspacing="0" style="border-collapse:collapse">
	<tr><td>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="5" cellspacing="0" style="border-collapse:collapse">


		</tr>
		<tr bgcolor="#ffffff">
			<td align="center"><b>序号</b></td>
			<td align="center"><b>类型</b></td>
			<td align="center"><b>编号</b></td>
			<td align="center"><b>名称</b></td>
			<td align="center"><b>材质</b></td>
			<td align="center"><b>规格</b></td>
			<td align="center" width=60><b>数量</b></td>
			<td align="center" width=60><b>重量(吨)</b></td>
			<td align="center" width=60><b>成本价(元/吨)</b></td>
			<td align="center"><b>金额(元)</b></td>
		</tr>
		<%
		KaiPingForm tmpInfo=null;
	      	for (int index=0; index<bdl.size(); index++)
			{
	      		tmpInfo = (KaiPingForm)bdl.get(index);
	      	%>
		<tr bgcolor="#ffffff">
			<td align='center'><%= index+1 %></td>
			<td align='center' ><%= (tmpInfo.getKind()==-1?"<font color=green>原料</font>":"<font color=blue>成品</font>") %></td>
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
			<td align="center" colspan=2><b>合计</b></td>
			<td colspan=8>原料重量：<%=ibf.getTotalnum1() %>吨 成品重量：<%=ibf.getTotalnum2() %>吨 原料金额：<%=nf.format(ibf.getTotalprice())%>元 成品金额：<%=nf.format(ibf.getTotalprice())%>元 </td>
		</tr>

		<tr bgcolor="#ffffff">
			<td align="center" colspan=2><b>大写合计</b></td>
			<td colspan="8" align="left" ><%chineseMoney cm=new chineseMoney();out.print(cm.getChineseMoney(ibf.getTotalprice())); %></td>
		</tr>
	</table></td></tr>
	</table>
	<table width="100%" align="cneter">
	<tr>
	<td>制单员：<%=ibf.getCreateperson()%></td>
	<td>库管员：</td>
	<td>记帐人：</td>
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
<p align="center"><input type="button" name="printtr" value=" 打印 " onclick="window.location='kaiPingItemsView.do?billId=<%=ibf.getBillid() %>&deptid=<%=ibf.getDeptid() %>&ifprint=2';">
&nbsp;<input type="button" name="close" value=" 关闭 " onclick="window.close();">
<%} %>
</body>

</html>


