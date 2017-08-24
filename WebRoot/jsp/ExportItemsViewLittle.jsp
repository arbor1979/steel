<%@ page contentType="text/html; charset=gb2312" language="java" %>
<%@ page import="mediastore.util.chineseMoney"%>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>


<html>
<head>
<title>天天阳光钢材进销存系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="./css/stylePrint.css" type="text/css">
<script language="javascript" src="js/LodopFuncs.js"></script>
</head>
<body>
<%
	ExportBillForm ibf = (ExportBillForm)request.getAttribute("billInfo");
	String ifprint = (String)request.getAttribute("ifprint");
	List bdl = (List)request.getAttribute("billDetail");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
	boolean bYiXiang=(ibf.getConfirmFlage().equals("2")?true:false);
	String skind="";
	if(ibf.getKind()==1)
		skind="销售";
	else if(ibf.getKind()==-1)
		skind="销售退货";
%><br>
<div id="main">
<h2 align="center" style="font-family: 楷体_GB2312;"><%=ibf.getGongsi() %><%=(bYiXiang?"销售意向单":"【"+skind+"单】")%><%if(ibf.getConfirmFlage().equals("3")) out.print("(已撤销)"); %></h2>
<table width="100%" align="center">
<%if(bYiXiang){ %>
<tr >
	<td >单号：<b><%=ibf.getBillId() %></b></td>
	<td >客户：<%=ibf.getFactoryName()%></td>
	<td >开单时间：<%=ibf.getExportTime()%></td>
	<td>提货方式:<%=ibf.getTihuo() %></td>
	<td align=right>车牌号：<%=(ibf.getCarNo().equals("")?"":ibf.getCarNo()) %></td>
</tr>
<%}else{ %>
<tr >
	<td >单号：<b><%=ibf.getBillId() %></b></td>
	<td >客户：<%=ibf.getFactoryName()%></td>
	<%if(ibf.getKind()==1){ %>
	<td>提货方式：<%=ibf.getTihuo() %></td>
	<%}else{ %><td></td><%} %>
	<td align=right>开单时间：<%=ibf.getExportTime()%></td>
</tr>
<tr>
	<td >车牌号：<%=(ibf.getCarNo().equals("")?"":ibf.getCarNo()) %></td>
	<td >运费：<%=(ibf.getYunFei()==0?"":ibf.getYunFei()) %>&nbsp;&nbsp;&nbsp;&nbsp;
	<%
	if(!ibf.getFpName().equals("收据"))
		out.print("发票类型："+ibf.getFpName()+"&nbsp;&nbsp;&nbsp;&nbsp;");
	%>
	</td>
	<td>付款方式：<%=ibf.getFkName()%></td>
	<td align=right>付款期限：<%=ibf.getPayLimTime()%></td>
</tr>
<%} %>
</table>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="0" cellspacing="0" style="border-collapse:collapse">
	<tr><td>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="3" cellspacing="0" style="border-collapse:collapse">


		</tr>
		<tr bgcolor="#ffffff">
			<td align="center"><b>编号</b></td>
			<td align="center"><b>名称</b></td>
			<td align="center"><b>材质</b></td>
			<td align="center"><b>规格</b></td>
			<td align="center"><b>产地</b></td>
			<%if(bYiXiang){ %>
			<td align="center"><b>应发数</b></td>
			<td align="center"><b>应发(吨)</b></td>
			<td align="center"><b>实发(吨)</b></td>
			<%}else{ %>
			<td align="center"><b>实发数</b></td>
			<td align="center"><b>实发(吨)</b></td>
			<%} %>
			<%if(!bYiXiang){ %>
			<td align="center"><b>单价</b></td>
			<td align="center"><b>金额(元)</b></td>
			<%if(ibf.getTotaljiagong()>0){ %>
			<td align="center"><b>加工费</b></td>
			<%}}%>
			<td align="center"><b>说明</b></td>

		</tr>
		<%
		GoodsExportGoodsInfo tmpInfo=null;
		
	      	for (int index=0; index<bdl.size(); index++)
			{
	      		tmpInfo = (GoodsExportGoodsInfo)bdl.get(index);
	      	%>
		<tr bgcolor="#ffffff">
			<td align='left'><%= tmpInfo.getGoodsId() %></td>
			<td align='left'><%= tmpInfo.getGoodsName() %></td>
			<td align='left'><%= tmpInfo.getCaizhi()%></td>
			<td align='left'><%= tmpInfo.getGuige() %></td>
			<td align='left'><%= tmpInfo.getChandi()%></td>
			
			<%if(bYiXiang){ %>
			<td align='right'><%
			if(tmpInfo.getExportNum()!=0)
				out.print(tmpInfo.getExportNum()*ibf.getKind()+tmpInfo.getDanwei());
			else if(tmpInfo.getJianxishu()>0)
				out.print(oConvert.getRound(tmpInfo.getExportAmount()*ibf.getKind()*1000/tmpInfo.getJianxishu(),1)+"件");
			%></td>
			<td align='right'><%= tmpInfo.getExportAmount()*ibf.getKind() %></td>
			<td></td>
			<%}else{ %>
			<td align='right'><%
			if(tmpInfo.getExportNum()!=0)
				out.print(tmpInfo.getExportNum()*ibf.getKind()+tmpInfo.getDanwei());
			else if(tmpInfo.getJianxishu()>0)
				out.print(oConvert.getRound(tmpInfo.getExportAmount()*ibf.getKind()*1000/tmpInfo.getJianxishu(),1)+"件");
			%></td>
			<td align='right'><%= tmpInfo.getExportAmount()*ibf.getKind() %></td>
			<%} %>
			<%if(!bYiXiang){ %>
			<td align='right'><%= tmpInfo.getExportUnitPrice()%></td>
			<td align='right'><%= nf.format(oConvert.getRound(tmpInfo.getExportAmount()*ibf.getKind()*tmpInfo.getExportUnitPrice(),2)) %></td>
			<%if(ibf.getTotaljiagong()>0){ %>
			<td align='right'><%=(tmpInfo.getJiagong()==0?"":nf.format(tmpInfo.getJiagong()))%></td>
			<% }} %>
			<td align='left' width=70><%= tmpInfo.getMemo()%></td>
		</tr>
		<%}%>
		
		<tr bgcolor="#ffffff">
			<td align="center"><b>小计</b></td>
			<td></td><td></td><td></td><td></td>
			<%if(bYiXiang){ %>
			<td></td>
			<td align="right"><%=ibf.getTotalNum()*ibf.getKind()%></td>
			<td></td>
			<%}else{ %>
			<td></td>
			<td align="right"><%=ibf.getTotalNum()*ibf.getKind()%></td><td></td>
			<%} %>
			<%if(!bYiXiang){ %>
			
			<td align="right"><u><%=nf.format(ibf.getTotalPrice()*ibf.getKind())%></u></td>
			<%if(ibf.getTotaljiagong()>0){ %>
			<td align="right"><u><%=nf.format(ibf.getTotaljiagong())%></u></td>
			<%} }%>
			<td></td>
		</tr>
		<%if(!bYiXiang){ %>
		<tr bgcolor="#ffffff">
			<td align="center"><b>备注</b></td><td colspan=2><%=ibf.getMemo() %></td>
			<td><b>大写</b></td><td colspan="3" align="left" ><%chineseMoney cm=new chineseMoney();out.print(cm.getChineseMoney(ibf.getTotalPrice()*ibf.getKind()+ibf.getTotaljiagong())); %></td>
			<td align="center"><b>合计</b></td><td colspan=2 align=center><b><%=nf.format(ibf.getTotalPrice()*ibf.getKind()+ibf.getTotaljiagong())%> 元</b> </td>
		</tr>
		<%} %>
	</table></td></tr>
	</table>
	<table width="100%" align="center">
	<tr>
	<td>制单员：<%=ibf.getSalesPerson()%></td>
	<td>业务员：<%=ibf.getYewuname()%></td>
	<td>发货人：&nbsp;&nbsp;&nbsp;&nbsp;</td>
<%if(!bYiXiang){ %>
	<td>提货人：&nbsp;&nbsp;&nbsp;&nbsp;</td>
	<td>记帐人：&nbsp;&nbsp;&nbsp;&nbsp;</td>
<%} %>
	</tr>
	<%if(!bYiXiang){ %>
	<tr><td colspan=2>提示：货物请当面点清，过后概不负责。</td><td height=30 align=right colspan=2><u><i>天天阳光软件</i></u></td></tr>
	<%} %>
	</table>
	</div>
<script>
	var LODOP;
	function printBill()
	{
		LODOP=getLodop();  
		LODOP.PRINT_INITA(0,0,522,333,"销售单");	
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
<p align="center"><input type="button" name="printtr" value=" 打印 " onClick="printBill();">
&nbsp;<input type="button" name="close" value=" 返回 " onClick="<%  
	if(ifprint.equals("1"))
		out.print("window.location='exportGoodsBill.do';");
	else
		out.print("window.close();");
%>">
</body>

</html>


