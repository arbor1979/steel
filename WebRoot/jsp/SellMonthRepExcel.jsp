<%@ page contentType="application/msexcel;charset=UTF-8" language="java"%> 
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.text.NumberFormat"%>
    
<%
String caizhi[] = (String[])request.getAttribute("caizhi");
double tmp1[][] = (double[][])request.getAttribute("tmp1");
PriceChangeForm pcf=(PriceChangeForm)request.getAttribute("pcf");
String qijian = pcf.getQijian();
String qijian1 = pcf.getQijian1();
String kind = pcf.getKind();
NumberFormat nf = NumberFormat.getNumberInstance();
nf.setMaximumFractionDigits(2);		
nf.setMinimumFractionDigits(2);

String ifprint = (String)request.getAttribute("ifprint");
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
<h2 align="center" style="font-family: 楷体_GB2312;">材质销售汇总</h2>
<table width="100%" align="cneter">
<tr >
	<td align="right">类型：<%=(kind.equals("month")?"年报":"月报") %>&nbsp;期间：<%=(kind.equals("month")?qijian:qijian+"-"+qijian1) %></td>
</tr>
</table>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="0" cellspacing="0" style="border-collapse:collapse">
	<tr><td>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="3" cellspacing="0" style="border-collapse:collapse">
 <%
    if(tmp1!=null)
    {
		double totalH=0;
		double totalV[]=new double[caizhi.length];
    %>
	
		<tr bgcolor="#C2CEDC">
			<th><%=(kind.equals("month")?"月":"日") %></th>
		<%
				for(int j=0;j<caizhi.length;j++)
				{
					out.print("<th>"+caizhi[j]+"</th>");

				}
		%>
			<th>合计</th>
			</tr>
	
		<%

			for(int i=0;i<tmp1.length;i++)
			{
		%>
			
			<tr>
			<td align=center><%=(i+1)%><%=(kind.equals("month")?"月":"日") %></td>
		<%
				totalH=0;
				for(int j=0;j<caizhi.length;j++)
				{
			    	out.print("<td align=right>"+(tmp1[i][j]!=0?tmp1[i][j]:"")+"</td>");
					totalH=totalH+tmp1[i][j];
					totalV[j]=totalV[j]+tmp1[i][j];
				}
				out.print("<td align=right>"+oConvert.getRound(totalH,3)+"</td>");
		%>
			</tr>
		<%
			}
		%>
			<tr>
			<td><b>合计</b></td>
			<%
			for(int j=0;j<caizhi.length;j++)
				out.print("<td align=right>"+oConvert.getRound(totalV[j],3)+"</td>");

			%>		
			<td></td>
			</tr>

    <%} %>   
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
