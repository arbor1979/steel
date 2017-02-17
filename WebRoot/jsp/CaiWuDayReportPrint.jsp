<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@page import="mediastore.util.oConvert"%>
      
<%
List ac = (List)request.getAttribute("ac");
ResultsetList ys = (ResultsetList)request.getAttribute("ys");
ResultsetList yf = (ResultsetList)request.getAttribute("yf");
List yushou = (List)request.getAttribute("yushou");
List yufu = (List)request.getAttribute("yufu");
List yufuyunfei = (List)request.getAttribute("yufuyunfei");
List otherYS = (List)request.getAttribute("otherYS");
List KucunList = (List)request.getAttribute("KucunList");
List shouruList = (List)request.getAttribute("shouruList");
List feiyongList = (List)request.getAttribute("feiyongList");
NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
	String ifprint = (String)request.getAttribute("ifprint");
if(ifprint.equals("2"))
	response.setHeader("Content-disposition","attachment; filename="+oConvert.FormDateShort(new java.util.Date())+".xls"); 
%>

<html>
<head>
<title>天天阳光钢材进销存系统</title>
<%if(ifprint.equals("1")){%>
<link rel="stylesheet" href="css/stylePrint.css" type="text/css">
<%}%>
</head>
<body>

<br>
<h2 align="center" style="font-family: 楷体_GB2312;">财务日报</h2>
<table width="100%" align="cneter">
<tr >
	<td align="right">当前时间：<%=oConvert.FormDate(new java.util.Date())%></td>
</tr>
</table>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="0" cellspacing="0" style="border-collapse:collapse">
	<tr><td>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="3" cellspacing="0" style="border-collapse:collapse">

		<tr>
		<th >项目</th>
		<th >明细科目</th>
		<th >金额（单位：元）</th>
		</tr>
		<tr>
		<td width=120><b>资金账户</b></td>
		<td></td>
		<td></td>
		</tr>
		<%
		double totalmoney=0;
		AccountForm tmpacc;
		for(int i=0;i<ac.size();i++)
		{
			tmpacc=(AccountForm)ac.get(i);
			totalmoney=totalmoney+tmpacc.getJine();
		%>
		<tr>
		<td></td>
		<td><%=tmpacc.getAccName() %></td>
		<td><%=nf.format(tmpacc.getJine()) %>
		</tr>
		<%} %>
		<tr>
			<td>合计</td>
			<td></td>
			<td><b><%=nf.format(totalmoney) %></b></td>
		</tr>
		
		<tr>
		<td width=120><b>应收款</b></td>
		<td></td>
		<td></td>
		</tr>

		<%
		String tmp[]=null;
		ExportBillForm tmpInfo = null;
		double totalys=0;
		for(int i=0;i<ys.rslist.size();i++)
		{
			tmpInfo=(ExportBillForm)ys.rslist.get(i);
			totalys=totalys+tmpInfo.getTotalPrice();
			out.print("<tr><td></td>");
			out.print("<td >"+tmpInfo.getFactoryName()+"</td><td >"+nf.format(tmpInfo.getTotalPrice())+"</td>");
			out.print("</tr>");
		}
		%>
		<tr>
		<td>合计:</td><td></td><td><b><%=nf.format(totalys) %></b></td>
		</tr>
		
		<tr>
		<td width=120><b>应付款</b></td>
		<td></td>
		<td></td>
		</tr>
		<%
		ImportBillForm tmpInfo1 = null;
		double totalyf=0;
		for(int i=0;i<yf.rslist.size();i++)
		{
			tmpInfo1=(ImportBillForm)yf.rslist.get(i);
			totalyf=totalyf+tmpInfo1.getTotalPrice();
			out.print("<tr><td></td>");
			out.print("<td >"+tmpInfo1.getFactoryName()+"</td><td >"+nf.format(tmpInfo1.getTotalPrice())+"</td>");
			out.print("</tr>");
		}
		%>
		<tr>
		<td>合计:</td><td></td><td><B><%=nf.format(totalyf) %></B></td>
		</tr>
		
		<tr>
		<td width=120><b>预付款</b></td>
		<td></td>
		<td></td>
		</tr>
		
		<%
		double totalyufu=0;
		for(int i=0;i<yufu.size();i++)
		{
			tmp=(String [])yufu.get(i);
			totalyufu=totalyufu+oConvert.getDouble(tmp[1],0);
			out.print("<tr><td></td>");
			out.print("<td >"+tmp[0]+"</td><td >"+nf.format(oConvert.getDouble(tmp[1],0))+"</td>");
			out.print("</tr>");
		}
		%>
		<tr>
		<td>合计:</td><td></td><td><b><%=nf.format(totalyufu) %></b></td>
		</tr>
		
		<tr>
		<td width=120><b>预付运费</b></td>
		<td></td>
		<td></td>
		</tr>
		
		<%
		double totalyufuyunfei=0;
		TruckInfoForm tif=null;
		for(int i=0;i<yufuyunfei.size();i++)
		{
			tif=(TruckInfoForm)yufuyunfei.get(i);
			totalyufuyunfei=totalyufuyunfei+tif.getAccount();
			out.print("<tr><td></td>");
			out.print("<td >"+tif.getCarno()+"("+tif.getDriver()+")"+"</td><td >"+nf.format(tif.getAccount())+"</td>");
			out.print("</tr>");
		}
		%>
		<tr>
		<td>合计:</td><td></td><td><b><%=nf.format(totalyufuyunfei) %></b></td>
		</tr>
		
		<tr>
		<td width=120><b>预收款</b></td>
		<td></td>
		<td></td>
		</tr>
		
		<%
		double totalyushou=0;
		for(int i=0;i<yushou.size();i++)
		{
			tmp=(String [])yushou.get(i);
			totalyushou=totalyushou+oConvert.getDouble(tmp[1],0);
			out.print("<tr><td></td>");
			out.print("<td >"+tmp[0]+"</td><td >"+nf.format(oConvert.getDouble(tmp[1],0))+"</td>");
			out.print("</tr>");
		}
		%>
		<tr >
		<td>合计:</td><td></td><td ><b><%=nf.format(totalyushou) %></b></td>
		</tr>
		
		<tr >
		<td width=120><b>其他应收款</b></td>
		<td></td>
		<td></td>
		</tr>
		<%
		double totalotherys=0;
		for(int i=0;i<otherYS.size();i++)
		{
			tmp=(String [])otherYS.get(i);
			totalotherys=totalotherys+oConvert.getDouble(tmp[1],0);
			out.print("<tr><td></td>");
			out.print("<td >"+tmp[0]+"</td><td >"+nf.format(oConvert.getDouble(tmp[1],0))+"</td>");
			out.print("</tr>");
		}
		%>
		<tr>
		<td>合计:</td><td></td><td ><b><%=nf.format(totalotherys) %></b></td>
		</tr>
		
		<tr>
		<td width=120><b>库存商品</b></td>
		<td></td>
		<td></td>
		</tr>
		<%
		double totalkucun=0;
		GoodsInfoForm gif=null;
		for(int i=0;i<KucunList.size();i++)
		{
			gif=(GoodsInfoForm)KucunList.get(i);
			totalkucun=totalkucun+gif.getPurchaseUnitPrice();
			out.print("<tr><td></td>");
			out.print("<td >"+gif.getGoodsTypeName()+"</td><td >"+nf.format(gif.getPurchaseUnitPrice())+"</td>");
			out.print("</tr>");
		}
		%>
		<tr>
		<td>合计:</td><td></td><td ><b><%=nf.format(totalkucun) %></b></td>
		</tr>
		
		<tr>
		<td width=120><b>其它收入</b></td>
		<td></td>
		<td></td>
		</tr>
		<%
		double totalshouru=0;
		FeiYongGoodsForm fyg=null;
		for(int i=0;i<shouruList.size();i++)
		{
			fyg=(FeiYongGoodsForm)shouruList.get(i);
			totalshouru=totalshouru+fyg.getJine();
			out.print("<tr><td></td>");
			out.print("<td >"+fyg.getTypeName()+"</td><td >"+nf.format(fyg.getJine())+"</td>");
			out.print("</tr>");
		}
		%>
		<tr >
		<td>合计:</td><td></td><td ><b><%=nf.format(totalshouru) %></b></td>
		</tr>
		
		<tr >
		<td width=120><b>其它费用</b></td>
		<td></td>
		<td></td>
		</tr>
		<%
		double totalfeiyong=0;
		for(int i=0;i<feiyongList.size();i++)
		{
			fyg=(FeiYongGoodsForm)feiyongList.get(i);
			totalfeiyong=totalfeiyong+fyg.getJine();
			out.print("<tr><td></td>");
			out.print("<td >"+fyg.getTypeName()+"</td><td >"+nf.format(fyg.getJine())+"</td>");
			out.print("</tr>");
		}
		%>
		<tr>
		<td>合计:</td><td></td><td ><b><%=nf.format(totalfeiyong) %></b></td>
		</tr>
		
		<tr>
		<td width=120><b>应收票据</b></td>
		<td></td>
		<td></td>
		</tr>
		
		<tr>
		<td width=120><b>应付票据</b></td>
		<td></td>
		<td></td>
		</tr>
		
		<tr>
		<td width=120><b>短期借款</b></td>
		<td></td>
		<td></td>
		</tr>
		
		<tr >
		<td width=120><b>净资产</b></td>
		<td></td>
		<td><b><%=nf.format(totalkucun+totalmoney+totalys+totalotherys+totalyufu+totalyufuyunfei-totalyf-totalyushou) %></td>
		</tr>
	</table></td></tr>
	</table>
	
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
window.history.back(-1);
</script>
    	
</body>
</html>
