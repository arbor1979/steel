<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.*"%>

<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js"></script>
<script language="JavaScript">

function mySubmit1(){

	if(!isDateChen(document.form1.importtime1))
	{//��������ʱ���Ƿ�Ϸ�
	}
	else if(!isDateChen(document.form1.importtime2))
	{//��������ʱ���Ƿ�Ϸ�
	}
	else if(!isDateChen(document.form1.paymenttime1))
	{//��������ʱ���Ƿ�Ϸ�
	}
	else if(!isDateChen(document.form1.paymenttime2))
	{//��������ʱ���Ƿ�Ϸ�
	}
	else
	{
		document.form1.submit();
	}
}

function gotoPage(){
	
	//��ֵΪҪ��ת����ҳ�����ʼ��¼��
	//alert(document.all.gotopagenum.value);

	document.form1.curpage.value = document.all.gotopagenum.value;
	document.form1.submit();
	
}


function gotoPageByN(n){
	
	document.form1.curpage.value = n;
	document.form1.submit();
	
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
    <td width="379" height="20" class="mainhead">���絥��ѯ</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td height="300" align="center" valign="top">   
          
<%
	List billIdList = (List)request.getAttribute("billIdList");
	List PersonList = (List)request.getAttribute("PersonList");
 	List departList = (List)request.getAttribute("departList");
	ResultsetList ibsri = (ResultsetList)request.getAttribute("ibsri");
	CheckBillForm gif = (CheckBillForm)request.getAttribute("gif");
	String importTime1=(String)request.getAttribute("importTime1");
	String importTime2=(String)request.getAttribute("importTime2");
	String orderby=(String)request.getAttribute("orderby");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
%>

    	<form name="form1" method="post" action="checkBillSearch.do" >
    	<font color=#215385><b>��ѯ����</b></font>
    	<IMG src="images/line1.gif" border=0 width=900>
    	<br>
    	<input type="hidden" name="curpage"  value="1">
    	<input type="hidden" name="orderby"  value="<%=orderby %>">
    	<input type="hidden" name="searchbutton" value="��ѯ">
    	<TABLE  class="mailtable"> 
	  <tr>
            <td  align="right"> 
                <b>����:</b>
            </td>
            <td>
            	<SELECT size=1 name="billId">
            		<option value="0">-ȫ��-</option>
            		<%
            			String tmpStr;
            			for(int i=0; i<billIdList.size(); i++)
            			{
            				tmpStr = (String)billIdList.get(i);
            		%>
            		<option value="<%=tmpStr%>" <%=(tmpStr.equals(String.valueOf(gif.getBillId()))?"selected":"") %>><%=tmpStr%></option>
			<%
				}
			%>
            	</SELECT>
            </td>
            <td  align="right"> 
                <b>¼��Ա:</b>
            </td>
            <td>
            	<SELECT name="createPerson">
            		<option value="">-ȫ��-</option>
            		<%
            			for(int i=0; i<PersonList.size(); i++)
            			{
            				tmpStr = (String)PersonList.get(i);
            		%>
            		<option value="<%=tmpStr%>" <%=(tmpStr.equals(gif.getCreatePerson())?"selected":"") %>><%=tmpStr%></option>
			<%
				}
			%>
            	</SELECT>
            </td>
            <%if(ctx.isIfview()) {%>
             <td  align="left" > 
                <b>�ֹ�˾:</b></td>
                <td>
            	<select name="deptid" >
    			<option value="-1">-ȫ��-</option>
    			<%
    			DepartInfoForm dif=new DepartInfoForm();
    			for(int i=0;i<departList.size();i++)
    			{
    				dif=(DepartInfoForm)departList.get(i);
    			%>
    			<option value="<%=dif.getId() %>" <%=(gif.getDeptid()==dif.getId()?"selected":"") %>><%=dif.getShortname() %></option>
    			<%
    			}
    			%>
    			</select>
    			</td>
            <%}else{%>
            <td>&nbsp;</td><td><input type="hidden" name="deptid" value="<%=ctx.getDeptid() %>"></td>
            <%} %>
            <td align="right"><b>�������ͣ�</b></td>
			<td>
			<select name="kind">
            	<option value="0">-ȫ��-</option>
            	<option value="1">���絥</option>
            	<option value="-1">����</option>
         	</td>
          </tr>
          <tr>
		<TD align="right" width=10%><b>����ʱ��:</b></TD>
		<TD colspan=4>��ʼ 
		<input type=text name='importtime1' size="18" class="none" value="<%=importTime1 %>">
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="���������˵�"  onClick="Calendar_importtime1();"> 
		&nbsp;&nbsp;��ֹ 
		<input type=text name='importtime2'  size="18" class="none" value="<%=importTime2 %>"> 
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="���������˵�"  onClick="Calendar_importtime2();"></td>
		<td><input name=searchbutton type=submit value="��ѯ" onClick='mySubmit1()' ></TD>
        </tr>
	</TABLE>
   
	</form>

<%
	if(ibsri!=null) 
	{
%>
	<font color=#215385>�����б��� <b><%=ibsri.allnum%></b> �ŵ��ݣ��ϼ� <b><%=ibsri.allsumnum%></b> �� <b><%=nf.format(ibsri.allmoney)%></b> Ԫ��</font>

	<IMG src="images/line1.gif" border=0 width=900>

	<TABLE  class="mailtable">  
		<%
			if(ibsri.allnum>0)
			{
		%>
		<tr bgcolor="#C2CEDC">
		<th></th>
		<th><a class="tablehead" href="javascript:orderList('billid','<%=orderby%>');">����</a><%if(orderby.equals("billid")) out.print("��"); if(orderby.equals("billid desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('kind','<%=orderby%>');">����</a><%if(orderby.equals("kind")) out.print("��"); if(orderby.equals("kind desc")) out.print("��");%></th>
		<%if(ctx.isIfview()) {%>
		<th><a class="tablehead" href="javascript:orderList('shortname','<%=orderby%>');">�ֹ�˾</a><%if(orderby.equals("shortname")) out.print("��"); if(orderby.equals("shortname desc")) out.print("��");%></th><%}%>
		<th><a class="tablehead" href="javascript:orderList('CreatePerson','<%=orderby%>');">¼��Ա</a><%if(orderby.equals("CreatePerson")) out.print("��"); if(orderby.equals("CreatePerson desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('totalnum','<%=orderby%>');">��������(��)</a><%if(orderby.equals("totalnum")) out.print("��"); if(orderby.equals("totalnum desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('totalprice','<%=orderby%>');">���(Ԫ)</a><%if(orderby.equals("totalprice")) out.print("��"); if(orderby.equals("totalprice desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('checkTime','<%=orderby%>');">¼��ʱ��</a><%if(orderby.equals("checkTime")) out.print("��"); if(orderby.equals("checkTime desc")) out.print("��");%></th>
		</tr>
		<%
			}
		%>
		
		<%

		CheckBillForm tmpInfo = null;
	    for (int index=0; index<ibsri.rslist.size(); index++)
		{
	      	tmpInfo = (CheckBillForm)ibsri.rslist.get(index);
	    %>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        	        <td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath() + "/checkItemsView.do?billId=" + tmpInfo.getBillId()+"&deptid="+tmpInfo.getDeptid() %>');"><IMG src="images/news1.gif" border=0 alt='�鿴������ϸ'></a></td>
	        <td align='center' ><%= tmpInfo.getBillId() %></td>
	        <td align='center' ><%=(tmpInfo.getKind()==1?"<font color=green>��</font>":"<font color=red>��</font>")%></td>
	        <%if(ctx.isIfview()) {%>
	        <td align='center' ><%= tmpInfo.getShortname() %></td><%}%>
	        <td align='center' ><%= tmpInfo.getCreatePerson() %></td>
	        <td align='right' ><%= tmpInfo.getTotalNum()  %></td>
			<td align='right'><%= nf.format(tmpInfo.getTotalPrice()) %></td>
			<td align='center' ><%=tmpInfo.getCheckTime()%></td>
        </tr>
        <%
      	}
        %>
	</TABLE>
	</form>
	<div align="right">
		<%
			if(ibsri.curpage>1)
			{
		%>
		<a href="javascript:gotoPageByN(1)"><IMG src="images/startpage.gif" border=0 alt='��ҳ'></a>&nbsp;
		<a href="javascript:gotoPageByN(<%=ibsri.curpage-1%>)"><IMG src="images/lastpage.gif" border=0 alt='��һҳ'></a>&nbsp;&nbsp;

		<%
			}
			else
			{
		%>
		<IMG src="images/startpage.gif" border=0 alt='��ҳ'>&nbsp;
		<IMG src="images/lastpage.gif" border=0 alt='��һҳ'>&nbsp;&nbsp;
		<%	
			}

	    	if(ibsri.curpage<ibsri.pagecount)
	    	{
	    %>
	    	<a href="javascript:gotoPageByN(<%=ibsri.curpage+1%>)"><IMG src="images/nextpage.gif" border=0 alt='��һҳ'></a>&nbsp;
	        <a href="javascript:gotoPageByN(<%=ibsri.pagecount%>)"><IMG src="images/endpage.gif" border=0 alt='ĩҳ'></a>&nbsp;&nbsp;&nbsp;&nbsp;
	    <%
	    	}
			else
			{
		%>
		<IMG src="images/nextpage.gif" border=0 alt='��һҳ'>&nbsp;
		<IMG src="images/endpage.gif" border=0 alt='ĩҳ'>&nbsp;&nbsp;&nbsp;&nbsp;
		<%	
			}
		%>
    	</div>
    	<div align="center">
		�� <select name="gotopagenum" onChange="gotoPage()">
		<%for(int i=1; i<=ibsri.pagecount; i++)
		  {%>
			<option value="<%=i%>" <%if(i==ibsri.curpage){%>selected<%}%>><%=i%></option>
		<%}%>
		</select> ҳ
	</div>
<%
	}
%>
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

