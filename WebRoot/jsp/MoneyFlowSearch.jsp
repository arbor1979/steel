<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.util.*"%>
<%@page import="mediastore.util.oConvert"%>
<%@ page import="java.text.NumberFormat"%>
<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
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
    <td width="379" height="20" class="mainhead">�ʽ�������</td>
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
	List flowList = (List)request.getAttribute("flowList");
	List departList = (List)request.getAttribute("departList");
	String importTime1 = (String)request.getAttribute("importTime1");
	String importTime2 = (String)request.getAttribute("importTime2");
	int deptid = oConvert.getInt((String)request.getAttribute("deptid"),0);
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
%>
     <form name="form1" method="post" action="moneyFlowSearch.do">
    	<input type="hidden" name="searchbutton" value="��ѯ"> 	
    	<TABLE  class="mailtable"> 
	  <tr>
          <tr>
          
		<TD align="right" width=10%><b>�����ڼ�: </b></TD>
		<TD colspan=3>��ʼ 
		<input type=text name='importtime1' size="18" class="none" value="<%=importTime1 %>">
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="���������˵�"  onClick="Calendar_importtime1();">&nbsp;
		&nbsp;��ֹ 
		<input type=text name='importtime2'  size="18" class="none" value="<%=importTime2 %>"> 
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="���������˵�"  onClick="Calendar_importtime2();"></td>
            <%
		if(ctx.isIfview()) {
		%>
             <td  align="right" > 
                <b>�ֹ�˾:</b></td>
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
            <%
            }else{
            %>
            <td>&nbsp;</td><td><input type="hidden" name="deptid" value="<%=ctx.getDeptid() %>"></td>
            <%
            }
            %>
            <td ><input name=searchbutton type=submit value=" ��ѯ "></TD>
        </tr>
         <tr>
		
		
	</TABLE>
   
	</form>
    	
	<font color=#215385>��ѯ���(�� <b><%=flowList.size()-2 %></b> ���ʻ�)
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE  class="mailtable">  
		
		<%
		HeadForm [] otpf=(HeadForm [])flowList.get(0);
		String [] tmp=(String [])flowList.get(1);
		double [] dlot=new double[tmp.length+4];
		int row=1;
		int j=0;
		for(int i=0;i<tmp.length+4;i++)
		{
			if((i%2)==1)
				out.print("<tr bgcolor=#CAE4F4>");
			else
				out.print("<tr>");
			row=row-1;
			if(row==0)
			{
				HeadForm hf=otpf[j]; 
				out.println("<td align=center bgcolor=#C2CEDC rowspan="+hf.getColspan()+" colspan="+hf.getRowspan()+">"+hf.getValue()+"</td>");
				row=hf.getColspan();
				j=j+1;
			}
			if(i>2 && i<tmp.length+3)
				out.println("<td align=center bgcolor=#C2CEDC>"+tmp[i-3]+"</td>");
			for (int index=2; index<flowList.size(); index++)
			{
		    	String[] stmp = (String[])flowList.get(index);
		    	if(i<=1)
		    		out.println("<th align=center>"+stmp[i]+"</th>");
		    	else
		    		out.println("<td align=right>"+nf.format(oConvert.getDouble(stmp[i],0))+"</td>");
		    	dlot[i]=dlot[i]+oConvert.getDouble(stmp[i],0);
			}
			if(i==0)
	    		out.println("<td></td>");
	    	else if(i==1)
	    		out.println("<th><u>�ϼ�</u></th>");
	    	else
	    	{
	    		out.println("<td align=right>"+nf.format(dlot[i])+"</td>");
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

