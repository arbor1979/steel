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
function orderList(order,old)
{
	if(order==old)
		order=order+' desc';
	form1.orderby.value=order;
	form1.submit();
}
function viewDetail(url)
{
	window.open(url,'view','top=0,left=0,location=no,directories=no,hotkeys=0,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=800,height=400');
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
    <td width="379" height="20" class="mainhead">��Ʒë������</td>
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
	String orderby = (String)request.getAttribute("orderby");
	String filename = (String)request.getAttribute("filename");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
%>
     <form name="form1" method="post" action="liRunSearch.do">
    	<input type="hidden" name="searchbutton" value="��ѯ"> 	
    	<input type="hidden" name="orderby" value="<%=orderby %>"> 	
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
		 <td  align="right" width=80> 
                <b>��������:</b>
            </td>
            <td >
				<select name="groupby">
				<option value="d.goodstypename" <%=(groupby.equals("d.goodstypename")?"selected":"") %>>��Ʒ���</option>
				<option value="a.goodsname,b.guige" <%=(groupby.equals("a.goodsname,b.guige")?"selected":"") %>>Ʒ�����</option>
				<option value="a.goodsid,a.goodsname" <%=(groupby.equals("a.goodsid,a.goodsname")?"selected":"") %>>��Ʒ���</option>
				<option value="b.chandi" <%=(groupby.equals("b.chandi")?"selected":"") %>>����</option>
				<option value="e.name,e.id" <%=(groupby.equals("e.name,e.id")?"selected":"") %>>�ͻ�</option>
				<option value="a.billid,e.name,e.id" <%=(groupby.equals("a.billid,e.name,e.id")?"selected":"") %>>���۵�</option>
				<option value="f.name" <%=(groupby.equals("f.name")?"selected":"") %>>ҵ��Ա</option>
				<option value="a.salesPerson" <%=(groupby.equals("a.salesPerson")?"selected":"") %>>¼��Ա</option>
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
            <td ><input name=searchbutton type=submit value=" ��ѯ "></TD>
        </tr>
         <tr>
		
		
	</TABLE>
   
	</form>
    	
	<font color=#215385>��ѯ���(�� <b><%=flowList.size() %></b> ����¼)
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE  class="mailtable">  
		<tr bgcolor="#C2CEDC">
		<th></th>
		<th><a class="tablehead" href="javascript:orderList('goodsid','<%=orderby%>');">
		<%
		if(groupby.equals("d.goodstypename")) 
			out.print("�������");
		else if(groupby.equals("a.goodsname,b.guige")) 
			out.print("��Ʒ����");
		else if(groupby.equals("a.goodsid,a.goodsname")) 
			out.print("��Ʒ���");
		else if(groupby.equals("e.name,e.id")) 
			out.print("�ͻ�����");
		else if(groupby.equals("b.chandi")) 
			out.print("����");
		else if(groupby.equals("a.salesPerson")) 
			out.print("¼��Ա");
		else if(groupby.equals("a.billid,e.name,e.id")) 
			out.print("����");
		else if(groupby.equals("f.name")) 
			out.print("ҵ��Ա");
		%><%if(orderby.equals("goodsid")) out.print("��"); if(orderby.equals("goodsid desc")) out.print("��");%></th>
		<%
		if(groupby.equals("a.goodsid,a.goodsname")) 
			out.print("<th>��Ʒ����</th>");
		else if(groupby.equals("a.billid,e.name,e.id")) 
			out.print("<th>�ͻ�</th>");
		else if(groupby.equals("a.goodsname,b.guige")) 
			out.print("<th>���</th>");
		%>
		<th><a class="tablehead" href="javascript:orderList('2','<%=orderby%>');">���۽��(Ԫ)
		<%if(orderby.equals("2")) out.print("��"); if(orderby.equals("2 desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('1','<%=orderby%>');">�����ɱ�(Ԫ)
		<%if(orderby.equals("1")) out.print("��"); if(orderby.equals("1 desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('3','<%=orderby%>');">ë��(Ԫ)
		<%if(orderby.equals("3")) out.print("��"); if(orderby.equals("3 desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('4','<%=orderby%>');">ë����(%)
		<%if(orderby.equals("4")) out.print("��"); if(orderby.equals("4 desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('5','<%=orderby%>');">ռ���������(%)
		<%if(orderby.equals("5")) out.print("��"); if(orderby.equals("5 desc")) out.print("��");%></th>
		</tr>
		
		<%
			String[] otpf = null;
			double [] dlot=new double[4];
		    for (int index=0; index<flowList.size(); index++)
			{
		    	otpf = (String[])flowList.get(index);
		    	for(int j=1;j<4;j++)
			    	dlot[j]=dlot[j]+oConvert.getDouble(otpf[j],0);
		%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        <td align='center' width=3%><%=index+1 %></td>
	        <%if (groupby.equals("a.billid,e.name,e.id")){%>
	        <td align='center'><a href="javascript:viewDetail('<%= request.getContextPath()+ "/exportItemsView.do?billId=" + otpf[0]+"&deptid="+deptid %>');"><IMG src="images/news1.gif" border=0 alt='�鿴������ϸ'></a>&nbsp;<%= otpf[0]%></td>
	        <td align='left'><a href="javascript:viewDetail('<%= request.getContextPath()+ "/factoryView.do?factory="+otpf[7] %>');"><%= otpf[6]%></a></td>
	        <%}else if (groupby.equals("e.name,e.id")){ %>
	        <td align='left'><a href="javascript:viewDetail('<%= request.getContextPath()+ "/factoryView.do?factory="+otpf[6] %>');"><%= otpf[0]%></a></td>
	        <%}else if (groupby.indexOf(",")>=0){ %>
	        <td align='center'><%= otpf[0]%></td>
	        <td align='center'><%= otpf[6]%></td>
	        <%}else{%>
	        <td align='center'><%= otpf[0]%></td>
	        <%} %>
	        <td align=right><%= nf.format(oConvert.getDouble(otpf[1],0))%></td>
	        <td align=right><%= nf.format(oConvert.getDouble(otpf[2],0))%></td>
	        <td align=right><%= nf.format(oConvert.getDouble(otpf[3],0))%></td>
	        <td align=right><%= nf.format(oConvert.getDouble(otpf[4],0))%></td>
	        <td align=right><%= nf.format(oConvert.getDouble(otpf[5],0))%></td>
	       </td>
	     </tr>
	     <%} %>
	     <tr>
	     	<td></td>
	     	<td align="center"><u>�ϼ�</u></td>
	     	<%
	     	if(otpf!=null && otpf[6]!=null)
	     		out.print("<td></td>");
	     	for(int j=1;j<4;j++)
	     	{
		    	out.print("<td align=right>"+nf.format(oConvert.getRound(dlot[j],4))+"</td>");
	     	}
    		out.print("<td></td>");
    		out.print("<td></td>");
	     	%>
	     </tr>
	</TABLE>
	<%if(flowList.size()<=20){ %>
	<img align=center src="chart/<%=filename %>">
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
