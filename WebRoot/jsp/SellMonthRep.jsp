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
    <td width="379" height="20" class="mainhead">材质销售汇总</td>
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
	String caizhi[] = (String[])request.getAttribute("caizhi");
	double tmp1[][] = (double[][])request.getAttribute("tmp1");
	List qjList = (List)request.getAttribute("qjList");
	List departList = (List)request.getAttribute("departList");
	PriceChangeForm pcf=(PriceChangeForm)request.getAttribute("pcf");
	String qijian = pcf.getQijian();
	String qijian1 = pcf.getQijian1();
	String filename = (String)request.getAttribute("filename");
	String kind = pcf.getKind();
	int deptid=oConvert.getInt((String)request.getAttribute("deptid"),0);
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
	
%>
     <form name="form1" method="post" action="sellMonthRep.do" onSubmit="javascript:return verify(this);">
    	<input type="hidden" name="searchbutton" value="查询"> 	
    	<input type="hidden" name="ifprint" value="0">	
    	<TABLE  class="mailtable"> 
	  <tr>
          <tr>
		<TD align="center">
		<input type="radio" name="kind" value="month" <%=(kind.equals("month")?"checked":"") %> onClick="form1.submit();">年报
		<input type="radio" name="kind" value="day" <%=(kind.equals("day")?"checked":"") %> onClick="form1.submit();">月报
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
    if(tmp1!=null)
    {
		double totalH=0;
		double totalV[]=new double[caizhi.length+1];
    %>
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE  class="mailtable">  
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
				totalV[caizhi.length]=totalV[caizhi.length]+totalH;
		%>
			</tr>
		<%
			}
		%>
			<tr>
			<td><b>合计</b></td>
			<%
			for(int j=0;j<caizhi.length+1;j++)
				out.print("<td align=right>"+oConvert.getRound(totalV[j],3)+"</td>");

			%>		
			<td></td>
			</tr>
	</TABLE>
	<img src="chart/<%=filename %>">
	<%} %>
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

