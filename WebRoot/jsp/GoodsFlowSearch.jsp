<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.util.*"%>
<%@page import="mediastore.util.oConvert"%>
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
    <td width="379" height="20" class="mainhead">��Ʒ������</td>
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
	String groupby = (String)request.getAttribute("groupby");
%>
     <form name="form1" method="post" action="goodsFlowSearch.do">
    	<input type="hidden" name="searchbutton" value="��ѯ">
    	<input type="hidden" name="ifprint" value="">
    	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	  <tr>
          <tr>
          
		<TD align="right" width=10%><b>�����ڼ�: </b></TD>
		<TD colspan=3>��ʼ 
		<input type=text name='importtime1' size="18" class="none" value="<%=importTime1 %>">
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="���������˵�"  onClick="Calendar_importtime1();">&nbsp;
		&nbsp;��ֹ 
		<input type=text name='importtime2'  size="18" class="none" value="<%=importTime2 %>"> 
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="���������˵�"  onClick="Calendar_importtime2();"></td>
		 <td  align="right" width=80> 
                <b>��������:</b>
            </td>
            <td >
				<select name="groupby">
				<option value="store" <%=(groupby.equals("store")?"selected":"") %>>�ֿ�</option>
				<option value="class" <%=(groupby.equals("class")?"selected":"") %>>���</option>
				<option value="chandi" <%=(groupby.equals("chandi")?"selected":"") %>>����</option>
				<option value="caizhi" <%=(groupby.equals("caizhi")?"selected":"") %>>����</option>
				<option value="goodsid" <%=(groupby.equals("goodsid")?"selected":"") %>>��Ʒ���</option>
				</select>
            </td>
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
            <td ><input name=searchbutton type=submit value=" ��ѯ " onclick="form1.ifprint.value=0;">&nbsp;
            <input name="exportbutton" type="button" value=" ���� " onclick="form1.ifprint.value=2;form1.submit();">
            </TD>
        </tr>
         <tr>
		
		
	</TABLE>
   
	</form>
    	
	<font color=#215385>��ѯ���(�� <b><%=flowList.size() %></b> ����¼)
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
		<tr bgcolor="#C2CEDC">
		<th></th>
		<th>
		<%
		if(groupby.equals("store")) 
			out.print("�ֿ�����");
		else if(groupby.equals("class")) 
			out.print("�������");
		else if(groupby.equals("chandi")) 
			out.print("����");
		else if(groupby.equals("chandi")) 
			out.print("����");
		else if(groupby.equals("goodsid")) 
				out.print("��Ʒ���");
		else if(groupby.equals("caizhi")) 
			out.print("����");
		%></th>
		<th>�ڳ�(��)</th>
		<th></th>
		<th>���(��)</th>
		<th></th>
		<th>����(��)</th>
		<th></th>
		<th>ת��(��)</th>
		<th></th>
		<th>�ӹ�(��)</th>
		<th></th>
		<th>����(��)</th>
		<th></th>
		<th>��ĩ(��)</th>
		</tr>
		
		<%
			String[] otpf = null;
			double [] dlot=new double[8];
		    for (int index=0; index<flowList.size(); index++)
			{
		    	otpf = (String[])flowList.get(index);
		    	for(int j=1;j<8;j++)
			    	dlot[j]=dlot[j]+oConvert.getDouble(otpf[j],0);
		%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        <td align='center' width=3%><%=index+1 %></td>
	        <td align=center><%= otpf[0]%></td>
	        <td align=right><%= oConvert.getString(otpf[1],"0")%></td>
	        <td align='right' width=5>+</td>
	        <td align=right><%= oConvert.getString(otpf[2],"0")%></td>
	        <td align='right' width=5>+</td>
	        <td align=right><%= oConvert.getString(otpf[3],"0")%></td>
	        <td align='right' width=5>+</td>
	        <td align=right><%= oConvert.getString(otpf[4],"0")%></td>
	        <td align='right' width=5>+</td>
	        <td align=right><%= oConvert.getString(otpf[5],"0")%></td>
	        <td align='right' width=5>-</td>
	        <td align=right><%= oConvert.getString(otpf[6],"0")%></td>
	        <td align='right' width=5>=</td>
	        <td align=right><%= oConvert.getString(otpf[7],"0")%></td>
	     </tr>
	     <%} %>
	     <tr>
	     	<td></td>
	     	<td align="center"><u>�ϼ�</u></td>
	     	<%
	     	for(int j=1;j<8;j++)
	     	{
		    	out.print("<td align=right>"+oConvert.getRound(dlot[j],4)+"</td>");
		    	if(j<7)
		    		out.print("<td></td>");
	     	}
	     	%>
	     </tr>
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

