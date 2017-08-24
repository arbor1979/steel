<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.util.chineseMoney"%>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>


<html>
<head>
<title>天天阳光钢材进销存系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link rel="stylesheet" href="./css/stylePrint.css" type="text/css">
<script language="javascript" src="js/LodopFuncs.js"></script>
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
<div id="main">
<h2 align="center" style="font-family: 楷体_GB2312;"><%=ibf.getShortname() %>【转仓单】</h2>
<table width="100%" align="cneter">
<tr >
	<td >单号：<b><%=ibf.getBillId() %></b></td>
	<td >转出仓库：<b><%=ibf.getFromname() %></b></td>
	<td >转入仓库：<b><%=ibf.getToname() %></b></td>
	<td align=right>录单时间：<%=ibf.getCreateTime()%></td>
</tr>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="0" cellspacing="0" style="border-collapse:collapse">
	<tr><td>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="5" cellspacing="0" style="border-collapse:collapse">


		</tr>
		<tr bgcolor="#ffffff">
			<td align="center"><b>产品编号</b></td>
			<td align="center"><b>产品名称</b></td>
			<td align="center"><b>材质</b></td>
			<td align="center"><b>规格</b></td>
			<td align="center"><b>产地</b></td>
			<td align="center" width=60><b>库存(吨)</b></td>
			<td align="center" width=60><b>数量</b></td>
			<td align="center" width=60><b>重量(吨)</b></td>
			<td align="center" width=60><b>入库价(元/吨)</b></td>
			<td align="center"><b>金额(元)</b></td>

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
			<td align="center" ><b>合计</b></td>
			<td></td><td></td><td></td><td></td><td></td><td></td>
			<td align="right"><%=ibf.getTotalNum() %></td><td></td>
			<td align="right"><u><%=nf.format(ibf.getTotalPrice())%></u></td>
		</tr>

		<tr bgcolor="#ffffff">
			<td align="center"><b>大写合计</b></td>
			<td colspan="8" align="left" ><%chineseMoney cm=new chineseMoney();out.print(cm.getChineseMoney(ibf.getTotalPrice())); %></td>
		</tr>
	</table></td></tr>
	</table>
	<table width="100%" align="cneter">
	<tr>
	<td>制单员：<%=ibf.getCreatePerson()%></td>
	<td>库管员：</td>
	<td>记帐人：</td>
	</tr>
	</table>
	</div>
<script>
	var LODOP;
	function printBill()
	{
		LODOP=getLodop();  
		LODOP.PRINT_INITA(0,0,522,333,"转仓单");	
		//LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_全页");
		LODOP.SET_PRINT_PAGESIZE(0,1950,950,"195");
		LODOP.ADD_PRINT_HTM("5%","5%","90%","90%",document.getElementById('main').innerHTML);
		LODOP.PREVIEW();
	}
<%if(ifprint.equals("1")){%>
	window.onload=function(){
		printBill();	
	};
<%}%>
</script>

<p align="center"><input type="button" name="printtr" value=" 打印 " onclick="printBill();">
&nbsp;<input type="button" name="close" value=" 返回" onclick="
<%if(ifprint.equals("1"))
		out.print("window.location='changeStoreBill.do';");
	else
		out.print("window.close();");
%>">
</body>

</html>


