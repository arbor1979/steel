<%@ page contentType="text/html; charset=gbk" language="java" %>
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

%>

<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<%if(ifprint.equals("1")){%>
<link rel="stylesheet" href="css/stylePrint.css" type="text/css">
<%}%>
</head>
<body>

<br>
<h2 align="center" style="font-family: ����_GB2312;">�������ۻ���</h2>
<table width="100%" align="cneter">
<tr >
	<td align="right">���ͣ�<%=(kind.equals("month")?"�걨":"�±�") %>&nbsp;�ڼ䣺<%=(kind.equals("month")?qijian:qijian+"-"+qijian1) %></td>
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
			<th><%=(kind.equals("month")?"��":"��") %></th>
		<%
				for(int j=0;j<caizhi.length;j++)
				{
					out.print("<th>"+caizhi[j]+"</th>");

				}
		%>
			<th>�ϼ�</th>
			</tr>
	
		<%

			for(int i=0;i<tmp1.length;i++)
			{
		%>
			
			<tr>
			<td align=center><%=(i+1)%><%=(kind.equals("month")?"��":"��") %></td>
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
			<td><b>�ϼ�</b></td>
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
