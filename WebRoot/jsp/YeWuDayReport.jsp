<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@page import="mediastore.util.oConvert"%>
<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<script language="JavaScript">
function orderList(order,old)
{
	form1.orderby.value=order;
	form1.submit();
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
    <td width="379" height="20" class="mainhead">ҵ���ձ���</td>
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
	String showall = (String)request.getAttribute("showall");
	String orderby = (String)request.getAttribute("orderby");
	int deptid = oConvert.getInt((String)request.getAttribute("deptid"),0);
	NumberFormat nf = NumberFormat.getNumberInstance();
  	nf.setMaximumFractionDigits(2);
  	nf.setMinimumFractionDigits(2);
%>
     <form name="form1" method="post" action="yeWuDayReport.do">
    	<input type="hidden" name="searchbutton" value="��ѯ"> 	
    	<input type="hidden" name="ifprint" value="0">
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
		<td><input type="checkbox" name="showall" value="1" <%=(showall.equals("1")?"checked":"") %>>��ʾ������Ʒ</td>
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
            <input type="hidden" name="orderby"  value="<%=orderby %>">
            <td ><input name=searchbutton type=button value=" ��ѯ " onclick="form1.ifprint.value=0;form1.submit();"></TD>
        </tr>
         <tr>
		
		
	</TABLE>
   
	</form>
    	
	<font color=#215385>��ѯ���(�� <b><%=flowList.size() %></b> ����¼)
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
		<tr bgcolor="#C2CEDC">
		<th></th>
		<th><a class="tablehead" href="javascript:orderList('goodsid','<%=orderby%>');">��Ʒ���</a><%if(orderby.equals("goodsid")) out.print("��"); %></th>
		<th><a class="tablehead" href="javascript:orderList('goodsname','<%=orderby%>');">Ʒ��</a><%if(orderby.equals("goodsname")) out.print("��"); %></th>
		<th><a class="tablehead" href="javascript:orderList('guige','<%=orderby%>');">���</a><%if(orderby.equals("guige")) out.print("��"); %></th>
		<th><a class="tablehead" href="javascript:orderList('importamount','<%=orderby%>');">��������</a><%if(orderby.equals("importamount")) out.print("��"); %></th>
		<th><a class="tablehead" href="javascript:orderList('importmoney','<%=orderby%>');">�������</a><%if(orderby.equals("importmoney")) out.print("��"); %></th>
		<th>��������</th>
		<th><a class="tablehead" href="javascript:orderList('exportamount','<%=orderby%>');">��������</a><%if(orderby.equals("exportamount")) out.print("��"); %></th>
		<th><a class="tablehead" href="javascript:orderList('exportmoney','<%=orderby%>');">���۽��</a><%if(orderby.equals("exportmoney")) out.print("��"); %></th>
		<th>���۳ɱ�</th>
		<th><a class="tablehead" href="javascript:orderList('exportlirun','<%=orderby%>');">ë��</a><%if(orderby.equals("exportlirun")) out.print("��"); %></th>
		<th>���۾���</th>
		<th><a class="tablehead" href="javascript:orderList('kucunamount','<%=orderby%>');">�������</a><%if(orderby.equals("kucunamount")) out.print("��"); %></th>
		<th><a class="tablehead" href="javascript:orderList('kucunmoney','<%=orderby%>');">�����</a><%if(orderby.equals("kucunmoney")) out.print("��"); %></th>
		<th>������</th>
		</tr>
		
		<%
			String[] otpf = null;
			double totalIm=0;
			double totalImPrice=0;
			double totalEx=0;
			double totalExPrice=0;
			double totalMl=0;
			double totalKc=0;
			double totalKcPrice=0;
			
		    for (int index=0; index<flowList.size(); index++)
			{
		    	otpf = (String[])flowList.get(index);
		    	totalIm=totalIm+oConvert.getDouble(otpf[3],0);
		    	totalImPrice=totalImPrice+oConvert.getDouble(otpf[4],0);
		    	totalEx=totalEx+oConvert.getDouble(otpf[5],0);
		    	totalExPrice=totalExPrice+oConvert.getDouble(otpf[6],0);
		    	totalMl=totalMl+oConvert.getDouble(otpf[7],0);
		    	totalKc=totalKc+oConvert.getDouble(otpf[8],0);
		    	totalKcPrice=totalKcPrice+oConvert.getDouble(otpf[9],0);
		%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        <td align='center' width=3%><%=index+1 %></td>
	        <td align=center><%= otpf[0]%></td>
	        <td align=center><%= otpf[1]%></td>
	        <td align=center><%= otpf[2]%></td>
	        <td align=right><%= oConvert.getString(otpf[3],"0")%></td>
	        <td align=right><%= nf.format(oConvert.getDouble(otpf[4],0))%></td>
	        <td align=right><%= (oConvert.getDouble(otpf[3],0)!=0?oConvert.getRound(oConvert.getDouble(otpf[4],0)/oConvert.getDouble(otpf[3],0),2):"")%></td>
	        <td align=right><%= oConvert.getString(otpf[5],"0")%></td>
	        <td align=right><%= nf.format(oConvert.getDouble(otpf[6],0))%></td>
	        <td align=right><%= nf.format(oConvert.getRound(oConvert.getDouble(otpf[6],0)-oConvert.getDouble(otpf[7],0),2))%></td>
	        <td align=right><%= nf.format(oConvert.getDouble(otpf[7],0))%></td>
	        <td align=right><%= (oConvert.getDouble(otpf[5],0)!=0?oConvert.getRound(oConvert.getDouble(otpf[6],0)/oConvert.getDouble(otpf[5],0),2):"")%></td>
	        <td align=right><%= oConvert.getString(otpf[8],"0")%></td>
	        <td align=right><%= nf.format(oConvert.getDouble(otpf[9],0))%></td>
	        <td align=right><%= (oConvert.getDouble(otpf[8],0)!=0?oConvert.getRound(oConvert.getDouble(otpf[9],0)/oConvert.getDouble(otpf[8],0),2):"")%></td>
	     </tr>
	     <%} %>
	     <tr>
	     	<td></td>
	     	<td align="center"><u>�ϼ�</u></td>
	     	<td></td>
	     	<td></td>
	     	<td align=right><%=oConvert.getRound(totalIm,3)%></td>
	     	<td align=right><%=nf.format(totalImPrice)%></td>
	     	<td></td>
	     	<td align=right><%=oConvert.getRound(totalEx,3)%></td>
	     	<td align=right><%=nf.format(totalExPrice)%></td>
	     	<td align=right><%=nf.format(totalExPrice-totalMl)%></td>
	     	<td align=right><%=nf.format(totalMl)%></td>
	     	<td></td>
	     	<td align=right><%=oConvert.getRound(totalKc,3)%></td>
	     	<td align=right><%=nf.format(totalKcPrice)%></td>
			<td></td>
	     </tr>
	</TABLE>
 <p align="center"><input type="button" value=" ��ӡ " onclick="form1.ifprint.value=1;form1.submit();">&nbsp;&nbsp;
    <input type="button" value=" ���� " onclick="form1.ifprint.value=2;form1.submit();"></p> 
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

