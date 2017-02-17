<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@page import="mediastore.util.oConvert"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<script language="JavaScript">

</script>
</head>
<body background='images/bgall.gif'>
<table width="98%" align="center">
  <tr> 
    <td><%@include file='../html/head.htm'%></td>
  </tr>
  <tr> 
    <td><%@include file='Menu.jsp'%></td>
  </tr>
  <tr>
  	<td>
  	
<table width="980" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td width="5" rowspan="4" class="trtable"></td>
    <td width="6" bgcolor="#214984" height="1"></td>
    <td rowspan="4" bgcolor="#214984" width="1" height="1"></td>
    <td bgcolor="#214984" class="10p" colspan="2" height="1"></td>
    <td rowspan="4" width="1" bgcolor="#214984" height="1"></td>
    <td bgcolor="#214984" width="5" height="1"></td>
    <td width="5" rowspan="4" class="trtable"></td>
  </tr>
  <tr> 
    <td width="6"></td>
    <td width="379" height="20" class="mainhead">财务日报表</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
  <tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td  align="center" valign="top">   
          
	<br>

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
	String importTime1 = (String)request.getAttribute("importTime1");
	String importTime2 = (String)request.getAttribute("importTime2");
	List departList = (List)request.getAttribute("departList");
	int deptid = oConvert.getInt((String)request.getAttribute("deptid"),0);
	NumberFormat nf = NumberFormat.getNumberInstance();
  	nf.setMaximumFractionDigits(2);
  	nf.setMinimumFractionDigits(2);
%>
     <form name="form1" method="post" action="caiWuDayReport.do">
    	<input type="hidden" name="searchbutton" value="查询"> 	
    	<input type="hidden" name="ifprint" value="0">
    	<input type="hidden" name="importtime1" value="<%=importTime1 %>">
    	<input type="hidden" name="importtime2" value="<%=importTime2 %>">
    	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	  <tr>
          <tr>
		<TD align="right" width=10%><b>报表日期: </b></TD>
		<td><input type="text" class=none size=20 value="<%=importTime1.substring(0,10) %>" disabled></td>
       <%
		if(ctx.isIfview()) {
		%>
             <td  align="right" > 
                <b>分公司:</b></td>
                <td>
            	<select name="deptid">
    			<%
    			    	DepartInfoForm dif=new DepartInfoForm();
    			    	for(int i=0;i<departList.size();i++)
    			    	{
    			    		dif=(DepartInfoForm)departList.get(i);
    			%>
    			<option value="<%=dif.getId() %>" <%=(deptid==dif.getId()?"selected":"") %>><%=dif.getShortname()%></option>
    			<%
    			}
    			%>
    			</select>
    			</td>
    			<td ><input name=searchbutton type=submit value=" 查询 "></TD>
            <%
            }else{
            %>
            <td>&nbsp;</td><td><input type="hidden" name="deptid" value="<%=ctx.getDeptid() %>"></td>
            <%
            }
            %>
            
        </tr>
         <tr>
		
		
	</TABLE>
   
	</form>
    	
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
		<tr bgcolor="#C2CEDC">
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
		<tr>
		<td>合计:</td><td></td><td ><b><%=nf.format(totalyushou) %></b></td>
		</tr>
		
		<tr>
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
		<tr>
		<td>合计:</td><td></td><td ><b><%=nf.format(totalshouru) %></b></td>
		</tr>
		
		<tr>
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
		
		<tr>
		<td width=120><b>净资产</b></td>
		<td></td>
		<td><b><%=nf.format(totalkucun+totalmoney+totalys+totalotherys+totalyufu+totalyufuyunfei-totalyf-totalyushou) %></td>
		</tr>
	</TABLE>
 <p align="center"><input type="button" value=" 打印 " onclick="form1.ifprint.value=1;form1.submit();">&nbsp;&nbsp;
    <input type="button" value=" 导出 " onclick="form1.ifprint.value=2;form1.submit();"></p> 
	</td>
        </tr>
        <tr> 
          <td valign="middle" align="center">&nbsp;</td>
        </tr>

      </table>
      
    </td>
    <td width="5"></td>
  </tr>
  <tr> 
    <td width="6"></td>
    <td colspan="2" class="mainhead">&nbsp;</td>
    <td width="5"></td>
  </tr>
  <tr> 
    <td colspan="8" bgcolor="#214984" height="2"></td>
  </tr>
</table>
</td>
	</tr>
</table>
</body>
</html>

