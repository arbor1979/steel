<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.util.*"%>
<%@page import="mediastore.util.oConvert"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<script language="JavaScript">

function orderList(order,old)
{
	if(order!=old)
	{
		form1.orderby.value=order;
		form1.submit();
	}
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
    <td width="379" height="20" class="mainhead">库存预警表</td>
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
	List kcList = (List)request.getAttribute("kcList");
	List departList = (List)request.getAttribute("departList");
	String orderby = (String)request.getAttribute("orderby");
	int deptid = oConvert.getInt((String)request.getAttribute("deptid"),0);
	int days = oConvert.getInt((String)request.getAttribute("days"),7);
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
%>
     <form name="form1" method="post" action="repertoryWarn.do" onsubmit="verify();">
    	<input type="hidden" name="searchbutton" value="查询"> 	
    	<input type="hidden" name="orderby" value="<%=orderby %>"> 	
    	
        
		 <%
		if(ctx.isIfview()) {
		%>
		<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	  <tr>
          <tr>
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
    			</TABLE>
            <%
            }else{
            %>
            <input type="hidden" name="deptid" value="<%=ctx.getDeptid() %>">
            <%
            }
            %>
   
	</form>
    	
	<font color=#215385>查询结果(共 <b><%=kcList.size() %></b> 种商品，红色表示库存不足商品)

	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
	<tr bgcolor=#CAE4F4>
		<th><a class="tablehead" href="javascript:orderList('goodsname','<%=orderby%>');">商品名称
		<%if(orderby.equals("goodsname")) out.print("↓"); if(orderby.equals("goodsname desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('guige','<%=orderby%>');">规格
		<%if(orderby.equals("guige")) out.print("↓"); if(orderby.equals("guige desc")) out.print("↑");%></th>
		<%
		SimpleDateFormat df=new SimpleDateFormat("MM-dd"); 
		Date d=new Date();
		for(int i=0;i<days;i++)
		{
			out.println("<th>"+df.format(new Date(d.getTime() - (days-i) * 24 * 60 * 60 * 1000))+"(日)</th>");
		}
		%>
		<th><a class="tablehead" href="javascript:orderList('avg','<%=orderby%>');">平均日销售(吨)
		<%if(orderby.equals("avg")) out.print("↓"); if(orderby.equals("avg desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('rep','<%=orderby%>');">当前库存(吨)
		<%if(orderby.equals("rep")) out.print("↓"); if(orderby.equals("rep desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('eva','<%=orderby%>');">估计可售(天)
		<%if(orderby.equals("eva")) out.print("↓"); if(orderby.equals("eva desc")) out.print("↑");%></th>
	</tr>
	<%
			for(int index=0;index<kcList.size();index++)
			{
				if((index%2)==1)
		       		out.print("<tr bgcolor=\"#CAE4F4\">");
				else
					out.print("<tr>");
				String[] tmp = (String[])kcList.get(index);
				boolean flag=false;
				if(oConvert.getDouble(tmp[tmp.length-1],0)<=4)
					flag=true;
				for(int i=0;i<tmp.length;i++)
				{
					if(i<2)
						out.print("<td align=center>"+tmp[i]+"</td>");	
					else if(i>=tmp.length-2 && flag)
						out.print("<td align=right><font color=red>"+nf.format(oConvert.getDouble(tmp[i],0))+"<font></td>");						
					else
						out.print("<td align=right>"+nf.format(oConvert.getDouble(tmp[i],0))+"</td>");
				}
				out.print("</tr>");
			}
		
	%>
		
	</TABLE>

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
