<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>

<%@ page import="java.text.*"%>

<%@page import="java.util.Date"%>
<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js"></script>
<script language="JavaScript">

function ConfirmPay(kind,billid,deptid,factory)
{
	if(kind==1)
		msg="�Ƿ�ȷ�Ϸ�Ʊ����ȡ��";
	else
		msg="�Ƿ�ȷ�Ϸ�Ʊ�ѿ��ߣ�";
	if(confirm(msg))
	{
		window.navigate("payFaPiao.do?param=pay&factory="+factory+"&kind="+kind+"&billid="+billid+"&deptid="+deptid);
	}
}
function viewDetail(url)
{
	window.open(url,'view','top=0,left=0,location=no,directories=no,hotkeys=0,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=800,height=400');
}
function orderList(order,old)
{
	if(order==old)
		order=order+' desc';
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
<%
	PayHuoKuanForm phf = (PayHuoKuanForm)request.getAttribute("phf");
	int kind=phf.getKind();
	String orderby=phf.getOrderby();

%>
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
    <td width="379" height="20" class="mainhead">��Ʊ<%=(kind==0?"Ӧ��":"Ӧ��")%></td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td  align="center" valign="top"><br>
          <form name="form1" method="post" action="payFaPiao.do">
          <input type="hidden" name="param" value="list">
          <input type="hidden" name="factory" value="<%=phf.getFactory() %>">
          <input type="hidden" name="deptid" value="<%=phf.getDeptid() %>">
          <input type="hidden" name="kind" value="<%=phf.getKind() %>">
          <input type="hidden" name="orderby" value="">
          </form>
<%
	ResultsetList ys = (ResultsetList)request.getAttribute("ys");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
	DateFormat dt1=DateFormat.getDateTimeInstance();
	java.util.Date dt2=new Date();
	
	if(kind==0)
	{
%>
	<font color=#215385>Ӧ���߷�Ʊ�б��� <b><%=ys.rslist.size()%></b> �����۵����ϼ� <b><%=ys.allsumnum%></b> �� <b><%=nf.format(ys.allmoney)%></b> Ԫ��</font>

	<IMG src="images/line1.gif" border=0 width=900>

	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
	
		<tr bgcolor="#C2CEDC">
		<th></th>
		<th><a class="tablehead" href="javascript:orderList('billid','<%=orderby%>');">���۵���<%if(orderby.equals("billid")) out.print("��"); if(orderby.equals("billid desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('b.name','<%=orderby%>');">�ͻ�<%if(orderby.equals("b.name")) out.print("��"); if(orderby.equals("b.name desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('salesPerson','<%=orderby%>');">¼��Ա<%if(orderby.equals("salesPerson")) out.print("��"); if(orderby.equals("salesPerson desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('totalnum','<%=orderby%>');">����(��)<%if(orderby.equals("totalnum")) out.print("��"); if(orderby.equals("totalnum desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('totalprice','<%=orderby%>');">���(Ԫ)<%if(orderby.equals("totalprice")) out.print("��"); if(orderby.equals("totalprice desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('exportTime','<%=orderby%>');">¼��ʱ��<%if(orderby.equals("exportTime")) out.print("��"); if(orderby.equals("exportTime desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('paylimtime','<%=orderby%>');">��������<%if(orderby.equals("paylimtime")) out.print("��"); if(orderby.equals("paylimtime desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('fpName','<%=orderby%>');">��Ʊ����<%if(orderby.equals("fpName")) out.print("��"); if(orderby.equals("fpName desc")) out.print("��");%></th>
		<th>����</th>
		</tr>
		
		<%
		
		ExportBillForm tmpInfo = null;
	    for (int index=0; index<ys.rslist.size(); index++)
		{
	      	tmpInfo = (ExportBillForm)ys.rslist.get(index);
	    %>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        <td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath()+ "/exportItemsView.do?billId=" + tmpInfo.getBillId()+"&deptid="+tmpInfo.getDeptid() %>');"><IMG src="images/news1.gif" border=0 alt='�鿴������ϸ'></a></td>
	        <td align='center' width=65><%= tmpInfo.getBillId() %></td>
	        <td align='left' width=180><a href="javascript:viewDetail('<%= request.getContextPath()+ "/factoryView.do?factory="+tmpInfo.getFactory() %>');"><%=tmpInfo.getFactoryName()%></a></td>
	        <td align='center' width=60><%= tmpInfo.getSalesPerson() %></td>
	        <td align='right' ><%= tmpInfo.getTotalNum() %></td>
			<td align='right'><%= nf.format(tmpInfo.getTotalPrice()) %></td>
			<td align='center' width=80><%=tmpInfo.getExportTime()%></td>
			<%
			
			if(dt2.after(dt1.parse(tmpInfo.getPayLimTime()+" 0:00:00")))
				out.print("<td align='center' width=80><font color=blue>"+tmpInfo.getPayLimTime()+"</font></td>");
			else
				out.print("<td align='center' width=80>"+tmpInfo.getPayLimTime()+"</td>");
			%>
			<td align='center'><%=tmpInfo.getFpName() %></td>
			<td align='center'><input type="button" value="����" onclick="ConfirmPay(0,<%=tmpInfo.getBillId()%>,<%=tmpInfo.getDeptid() %>,<%=tmpInfo.getFactory() %>)" <%=(ctx.getDeptid()!=tmpInfo.getDeptid()?"disabled":"")%>>
		
		</td>
		
        </tr>
        <%
      	}
        %>
	</TABLE>
	<br>
<%
	}
	else if(kind==1)
	{
%>
	<font color=#215385>Ӧ��ȡ��Ʊ�б��� <b><%=ys.rslist.size()%></b> �Ž��������ϼ� <b><%=ys.allsumnum%></b> �� <b><%=nf.format(ys.allmoney)%></b> Ԫ��</font>

	<IMG src="images/line1.gif" border=0 width=900>

	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
	
		<tr bgcolor="#C2CEDC">
		<th></th>
		<th><a class="tablehead" href="javascript:orderList('billid','<%=orderby%>');">��������<%if(orderby.equals("billid")) out.print("��"); if(orderby.equals("billid desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('b.name','<%=orderby%>');">��Ӧ��<%if(orderby.equals("b.name")) out.print("��"); if(orderby.equals("b.name desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('createPerson','<%=orderby%>');">¼��Ա<%if(orderby.equals("createPerson")) out.print("��"); if(orderby.equals("createPerson desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('totalnum','<%=orderby%>');">����(��)<%if(orderby.equals("totalnum")) out.print("��"); if(orderby.equals("totalnum desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('totalprice','<%=orderby%>');">���(Ԫ)<%if(orderby.equals("totalprice")) out.print("��"); if(orderby.equals("totalprice desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('importTime','<%=orderby%>');">¼��ʱ��<%if(orderby.equals("importTime")) out.print("��"); if(orderby.equals("importTime desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('paylimtime','<%=orderby%>');">��������<%if(orderby.equals("paylimtime")) out.print("��"); if(orderby.equals("paylimtime desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('fpName','<%=orderby%>');">��Ʊ����<%if(orderby.equals("fpName")) out.print("��"); if(orderby.equals("fpName desc")) out.print("��");%></th>
		<th>����</th>
		</tr>
		
		<%
						ImportBillForm tmpInfo1 = null;
					    for (int index=0; index<ys.rslist.size(); index++)
						{
					    	tmpInfo1 = (ImportBillForm)ys.rslist.get(index);
				%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        <td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath()+ "/importItemsView.do?billId=" + tmpInfo1.getBillId()+"&deptid="+tmpInfo1.getDeptid() %>');"><IMG src="images/news1.gif" border=0 alt='�鿴������ϸ'></a></td>
	        <td align='center' width=65><%= tmpInfo1.getBillId() %><%=(tmpInfo1.getIeva()==1?"(��)":"")%></td>
	        <td align='left' width=180><a href="javascript:viewDetail('<%= request.getContextPath()+"/factoryView.do?factory="+tmpInfo1.getFactory()%>');")><%=tmpInfo1.getFactoryName()%></a></td>
	        <td align='center' width=60><%= tmpInfo1.getCreatePerson() %></td>
	        <td align='right' ><%= tmpInfo1.getTotalNum() %></td>
			<td align='right'><%= nf.format(tmpInfo1.getTotalPrice()) %></td>
			<td align='center' width=80><%=tmpInfo1.getImportTime()%></td>
			<%
			
			if(dt2.after(dt1.parse(tmpInfo1.getPayLimTime()+" 0:00:00")))
				out.print("<td align='center' width=80><font color=blue>"+tmpInfo1.getPayLimTime()+"</font></td>");
			else
				out.print("<td align='center' width=80>"+tmpInfo1.getPayLimTime()+"</td>");
			%>
			<td align='center'><%=tmpInfo1.getFpName() %></td>
			<td align='center'>
			<%if(tmpInfo1.getIeva()!=1){%>
			<input type="button" value="��ȡ" onclick="ConfirmPay(1,<%=tmpInfo1.getBillId()%>,<%=tmpInfo1.getDeptid() %>,<%=tmpInfo1.getFactory() %>)" <%=(ctx.getDeptid()!=tmpInfo1.getDeptid()?"disabled":"")%>>
			<%} %>
		</td>
		
        </tr>
        <%
      	}
        %>
	</TABLE>
<%
	}
%>
	</td>
        </tr>
        
      </table>
	<p align="center"><input type="button" value=" ���� " onclick="window.navigate('payFaPiao.do?kind=<%=kind %>');">
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

