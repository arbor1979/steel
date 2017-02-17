<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.util.*"%>
<%@page import="mediastore.util.oConvert"%>
<%@ page import="java.text.NumberFormat"%>

<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<script language="JavaScript">

function verify(form){
	if(form1.goodsid.value=='')
	{
		alert('请选择产品名规格');
		form1.goodsid.focus();
		return false;
	}
	
	return true;
}
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
    <td width="379" height="20" class="mainhead">价格走势图</td>
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
	List ngList = (List)request.getAttribute("ngList");
	List flowList = (List)request.getAttribute("flowList");
	List qjList = (List)request.getAttribute("qjList");
	List departList = (List)request.getAttribute("departList");
	PriceChangeForm pcf=(PriceChangeForm)request.getAttribute("pcf");
	String qijian = pcf.getQijian();
	String qijian1 = pcf.getQijian1();
	String filename = (String)request.getAttribute("filename");
	String goodsid = pcf.getGoodsid();
	String kind = pcf.getKind();
	int deptid=oConvert.getInt((String)request.getAttribute("deptid"),0);
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
	
%>
     <form name="form1" method="post" action="priceChange.do" onSubmit="javascript:return verify(this);">
    	<input type="hidden" name="searchbutton" value="查询"> 		
    	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	  <tr>
          <tr>
		<TD align="center">
		<input type="radio" name="kind" value="month" <%=(kind.equals("month")?"checked":"") %> onClick="form1.submit();">年走势图
		<input type="radio" name="kind" value="day" <%=(kind.equals("day")?"checked":"") %> onClick="form1.submit();">月走势图
		</select>
		</td>  
		<TD align="right"><b>报表期间: </b></TD>
		<TD ><select name="qijian">
		<%
		String tmp="";
		for(int i=0;i<qjList.size();i++)
		{
			tmp=(String)qjList.get(i);
			out.println("<option value='"+tmp+"'"+(qijian.equals(tmp)?"selected":"")+">"+tmp+"</option>");
		}
         %>
        </select>
        <%
		if(kind.equals("day"))
		{ 
			out.print("<select name='qijian1'>");
        	for(int i=1;i<=12;i++)
        		out.print("<option value="+i+" "+(oConvert.getInt(qijian1,1)==i?"selected":"")+">"+i+"月</option>");
		}
        %>
		</td>
		<TD align="right"><b>品名规格: </b></TD>
		<TD >
		<select name="goodsid">
		<%
		String[] ng=null;
		for(int i=0;i<ngList.size();i++)
		{
			ng=(String[])ngList.get(i);
			tmp=oConvert.HtmlSpace(oConvert.limitStr(ng[0],10))+ng[1];
			out.println("<option value='"+ng[0]+","+ng[1]+"'"+(goodsid.equals(ng[0]+","+ng[1])?"selected":"")+">"+tmp+"</option>");
		}
			
		%>
		</select>	
		</TD>
            <%
		if(ctx.isIfview()) {
		%>
             <td  align="right" > 
                <b>分公司:</b></td>
                <td>
            	<select name="deptid" onchange="SubmitThis(this.value);">
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
            <%
            }else{
            %>
            <td>&nbsp;</td><td><input type="hidden" name="deptid" value="<%=ctx.getDeptid() %>"></td>
            <%
            }
            %>
            <td ><input name=searchbutton type=submit value=" 查询 "></TD>
        </tr>
         <tr>
		
		
	</TABLE>
   
	</form>
    <%
    if(flowList!=null)
    {
    %>
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
		
	<%
		if(kind.equals("month"))
		{
		%>
			<tr bgcolor="#C2CEDC">
			<th>月</th>
		<%
			for(int i=0;i<12;i++)
				out.print("<th>"+(i+1)+"月</th>");
		%>
			<tr>
			<td align=center>入库单价</td>
		<%
			double tmp1[]=(double[])flowList.get(0);
		    for (int i=0; i<tmp1.length; i++)
		    	out.print("<td align=right>"+(tmp1[i]!=0?nf.format(tmp1[i]):"")+"</td>");
		%>
			</tr>
			<tr>
			<td align=center>出库单价</td>
		<%
			double tmp2[]=(double[])flowList.get(1);
		    for (int i=0; i<tmp2.length; i++)
		    	out.print("<td align=right>"+(tmp2[i]!=0?nf.format(tmp2[i]):"")+"</td>");
		%>
		</tr>
		<%}else{%>
			<tr bgcolor="#C2CEDC">
			<th>1-15</th>
		<%
			for(int i=0;i<15;i++)
				out.print("<th>"+(i+1)+"日</th>");
		%>
			<tr>
			<td align=center>入库单价</td>
		<%
			double tmp1[]=(double[])flowList.get(0);
		    for (int i=0; i<15; i++)
		    	out.print("<td align=right>"+(tmp1[i]!=0?nf.format(tmp1[i]):"")+"</td>");
		%>
			</tr>
			<tr>
			<td align=center>出库单价</td>
		<%
			double tmp2[]=(double[])flowList.get(1);
		    for (int i=0; i<15; i++)
		    	out.print("<td align=right>"+(tmp2[i]!=0?nf.format(tmp2[i]):"")+"</td>");
		%>
		</tr>
		<tr bgcolor="#C2CEDC">
			<th>16-<%=tmp1.length %></th>
		<%
			for(int i=15;i<tmp1.length;i++)
				out.print("<th>"+(i+1)+"日</th>");
		%>
			<tr>
			<td align=center>入库单价</td>
		<%
			tmp1=(double[])flowList.get(0);
		    for (int i=15; i<tmp1.length; i++)
		    	out.print("<td align=right>"+(tmp1[i]!=0?nf.format(tmp1[i]):"")+"</td>");
		%>
			</tr>
			<tr>
			<td align=center>出库单价</td>
		<%
			tmp2=(double[])flowList.get(1);
		    for (int i=15; i<tmp2.length; i++)
		    	out.print("<td align=right>"+(tmp2[i]!=0?nf.format(tmp2[i]):"")+"</td>");
		%>
		</tr>
		<%} %>
	</TABLE>
	<img src="chart/<%=filename %>">
	<%} %>
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

