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
	ImportBillForm ibf = (ImportBillForm)request.getAttribute("billInfo");
	String ifprint = (String)request.getAttribute("ifprint");
	List bdl = (List)request.getAttribute("billDetail");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
	String skind="";
	if(ibf.getKind()==1)
		skind="进货";
	else if(ibf.getKind()==-1)
		skind="进货退货";
	
%><br>
<div id="main">
<h2 align="center" style="font-family: 楷体_GB2312;"><%=ibf.getGongsi() %>【<%=(ibf.getIeva()==1?"估价":"")%><%=skind %>单】<%if(ibf.getConfirmFlage().equals("3")) out.print("(已撤单)"); %></h2>
<table width="100%" align="cneter">
<tr >
	<td >单号：<b><%=ibf.getBillId() %></b></td>
	<td >供应商：<%=ibf.getFactoryName()%></td>
	<td align=right><%=skind %>时间：<%=ibf.getImportTime()%></td>
</tr>
<tr>
	<td>车牌号：<%=(ibf.getCarNo().equals("")?"无":ibf.getCarNo()) %></td>
	<td>运费：<%=(ibf.getYunFei()==0?"无":ibf.getYunFei()) %>&nbsp;&nbsp;&nbsp;&nbsp;
	<%
	if(!ibf.getFpName().equals("收据"))
		out.print("发票类型："+ibf.getFpName());
	%>
	&nbsp;&nbsp;&nbsp;&nbsp;付款方式：<%=ibf.getFkName()%></td>
	<td align=right>付款期限：<%=ibf.getPayLimTime()%></td>
</tr>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="0" cellspacing="0" style="border-collapse:collapse">
	<tr><td>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="5" cellspacing="0" style="border-collapse:collapse">


		</tr>
		<tr bgcolor="#ffffff">
			<td align="center" ><b>仓库<b></td>
			<td align="center"><b>产品编号</b></td>
			<td align="center"><b>产品名称</b></td>
			<td align="center"><b>规格</b></td>
			<td align="center"><b>材质</b></td>
			<td align="center"><b>产地</b></td>
			<td align="center"><b>数量</b></td>
			<td align="center"><b>重量(吨)</b></td>
			<td align="center"><b>计重方法</b></td>
			<td align="center"><b>单价(元/吨)</b></td>
			<td align="center"><b>金额(元)</b></td>
			<td align="center"><b>说明</b></td>

		</tr>
		<%
		GoodsImportGoodsInfo tmpInfo=null;
		
	      	for (int index=0; index<bdl.size(); index++)
			{
	      		tmpInfo = (GoodsImportGoodsInfo)bdl.get(index);
	      	%>
		<tr bgcolor="#ffffff" >
			<td align='left'><%= tmpInfo.getStoreName() %></td>
			<td align='left'><%= tmpInfo.getGoodsId() %></td>
			<td align='left'><%= tmpInfo.getGoodsName() %></td>
			<td align='left'><%= tmpInfo.getGuige() %></td>
			<td align='left'><%= tmpInfo.getCaizhi() %></td>
			<td align='left'><%= tmpInfo.getChandi()%></td>
			<td align='right'>
			<%	
			if(tmpInfo.getImportNum()!=0)
				out.print(tmpInfo.getImportNum()*ibf.getKind()+tmpInfo.getDanwei());
			else if(tmpInfo.getJianxishu()>0) 
				out.print(oConvert.getRound(tmpInfo.getImportAmount()*ibf.getKind()*1000/tmpInfo.getJianxishu(),1)+"件"); 
			%></td>
			<td align='right'><%= tmpInfo.getImportAmount()*ibf.getKind() %></td>
			<td align='center'>
			<%
			if(tmpInfo.getXishu()>0 || tmpInfo.getJianxishu()>0)
				out.print("理算");
			else out.print("过磅");
			%></td>
			<td align='right'><%= tmpInfo.getImportUnitPrice()%></td>
			<td align='right'><%= nf.format(oConvert.getRound(tmpInfo.getImportUnitPrice()*tmpInfo.getImportAmount()*ibf.getKind(),2)) %></td>
			<td><%= tmpInfo.getMemo()%></td>

		</tr>
		<%			}%>
		
		<tr bgcolor="#ffffff">
			<td align="center"><b>合计</b></td>
			<td></td><td></td><td></td><td></td><td></td><td></td>
			<td align="right"><%=ibf.getTotalNum()*ibf.getKind() %></td><td></td><td></td>
			<%if(ibf.isIyunfei()){ %>
			<td align="right"><u><%=nf.format(ibf.getTotalPrice()*ibf.getKind()+ibf.getYunFei())%></u></td>
			<%}else{ %>
			<td align="right"><u><%=nf.format(ibf.getTotalPrice()*ibf.getKind())%></u></td>
			<%} %>
			<td></td>
		</tr>

		<tr bgcolor="#ffffff">
			<%if(ibf.isIyunfei()){ %>
			<td align="center"><b>应付货款</b></td>
			<td colspan="6" align="left" ><%=nf.format(ibf.getTotalPrice()*ibf.getKind())%></td>
			<%}else{ %>
			<td align="center"><b>大写合计</b></td>
			<td colspan="6" align="left" ><%chineseMoney cm=new chineseMoney();out.print(cm.getChineseMoney(ibf.getTotalPrice()*ibf.getKind())); %></td>
			<%} %>
			<td align=center><b>备注</b></td><td colspan="4"><%=ibf.getMemo() %></td>
		</tr>
	</table></td></tr>
	</table>
	<table width="100%" align="cneter">
	<tr>
	<td>制单员：<%=ibf.getCreatePerson()%></td>
	<td>验货人：</td>
	<td>记帐人：</td>
	</tr>
	</table>
</div>
<script>
	var LODOP;
	function printBill()
	{
		LODOP=getLodop();  
		LODOP.PRINT_INITA(0,0,522,333,"进货单");	
		//LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_全页");
		LODOP.SET_PRINT_PAGESIZE(0,2410,1300,"241");
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
		out.print("window.location='importGoodsBill.do?kind="+ibf.getKind()+"';");
	else
		out.print("window.close();");
%>">
</body>

</html>


