<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.rule.FeiYongItemSearchRule"%>
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
	else
	{
		form1.classid.value=cat.form1.classid.value;
		form1.typeid.value=cat.form1.typeid.value;
		document.form1.submit();
	}
}

function gotoPage(){
	
	//��ֵΪҪ��ת����ҳ�����ʼ��¼��
	//alert(document.all.gotopagenum.value);

	document.form1.curpage.value = document.all.gotopagenum.value;
	document.form1.submit();
	
}


function gotoPageByN(n)
{
	document.form1.curpage.value = n;
	document.form1.submit();
	
}
function orderList(order,old)
{
	if(order==old)
		order=order+' desc';
	form1.orderstr.value=order;
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
<%
          	List BillList = (List)request.getAttribute("BillList");
        	List UserList = (List)request.getAttribute("UserList");
        	List departList = (List)request.getAttribute("departList");
        	List acc = (List)request.getAttribute("acc");
          	ResultsetList ibsri = (ResultsetList)request.getAttribute("ibsri");
          	FeiYongItemSearchRule fbsr = (FeiYongItemSearchRule)request.getAttribute("fbsr");
          	NumberFormat nf = NumberFormat.getNumberInstance();
          	nf.setMaximumFractionDigits(2);
          	nf.setMinimumFractionDigits(2);
          	String orderby=fbsr.getOrderStr();
          	int kind=fbsr.getFyf().getKind();
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
    <td width="379" height="20" class="mainhead"><%=(kind==1?"���������ѯ":"����֧����ѯ") %></td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td height="300" align="center" valign="top">   
          


    	<form name="form1" method="post" action="feiYongItemSearch.do" >
    	<font color=#215385><b>��ѯ����</b></font>
    	<IMG src="images/line1.gif" border=0 width=900>
    	<br>
    	<input type="hidden" name="curpage"  value="1">
    	<input type="hidden" name="kind"  value="<%=fbsr.getFyf().getKind() %>">
    	<input type="hidden" name="orderstr"  value="<%=orderby %>">
    	<input type="hidden" name="classid"  value="<%=fbsr.getFyf().getClassid() %>">
    	<input type="hidden" name="typeid"  value="<%=fbsr.getFyf().getTypeid() %>">
    	<input type="hidden" name="searchbutton" value="��ѯ">
    	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	  <tr>
            <td  align="right"> 
                <b>����:</b>
            </td>
            <td>
            	<SELECT name="billid">
            		<option value="0">-ȫ��-</option>
            		<%
            		            	String tmpStr;
            		            	for(int i=0; i<BillList.size(); i++)
            		            	{
            		            		tmpStr = (String)BillList.get(i);
            		%>
            		<option value="<%=tmpStr%>" <%=(tmpStr.equals(String.valueOf(fbsr.getFyf().getBillid()))?"selected":"") %>><%=tmpStr%></option>
			<%
			}
			%>
            	</SELECT>
            </td>
            <td  align="right"> 
                <b>¼��Ա:</b>
            </td>
            <td>
            	<SELECT name="operPerson">
            		<option value="">-ȫ��-</option>
            		<%
            		            	for(int i=0; i<UserList.size(); i++)
            		            	{
            		            		tmpStr = (String)UserList.get(i);
            		%>
            		<option value="<%=tmpStr%>" <%=(tmpStr.equals(fbsr.getFyf().getOperPerson())?"selected":"") %>><%=tmpStr%></option>
			<%
			}
			%>
            	</SELECT>
            </td>
            <td align="right" ><b><%=(kind==1?"����":"����") %>���:</b></td>
			<td><input type=text class="none" size=15 name="typeName" value="<%=fbsr.getFyf().getTypeName() %>"></td>
			
			<td align="right" ><b><%=(kind==1?"����":"����") %>���:</b></td>
			<td>
			<select name="oper">
				<option value=""></option>
				<option value=">" <%=(fbsr.getOper().equals(">")?"selected":"") %>>></option>
				<option value="=" <%=(fbsr.getOper().equals("=")?"selected":"") %>>=</option>
				<option value="<" <%=(fbsr.getOper().equals("<")?"selected":"") %>><</option>
			</select><input type=text class="none" size=10 name="jine" value="<%=fbsr.getFyf().getJine() %>" onKeyUp="InputFloat(this);"></td>
          </tr>
          <tr>
			<td colspan=4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<iframe name="cat" id="cat" src="feiYongTypeSelect.do?classid=<%=fbsr.getFyf().getClassid() %>&typeid=<%=fbsr.getFyf().getTypeid() %>&kind=<%=kind %>" width="300" height="22" frameborder=0 scrolling=no ></iframe></td>
			<td align="right" ><b>����˵��:</b></td>
			<td><input type=text class="none" size=15 name="memo" value="<%=fbsr.getFyf().getMemo() %>"></td>
			<%
		if(ctx.isIfview()) {
		%>
             <td  align="right" > 
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
    			<option value="<%=dif.getId() %>" <%=(fbsr.getFyf().getDeptid()==dif.getId()?"selected":"") %>><%=dif.getShortname()%></option>
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
			
			
			</tr>
          
 
		<tr>
		<TD align="right" width=10%><b>¼��ʱ��:</b></TD>
		<TD colspan=5>��ʼ 
		<input type=text name='importtime1' size="18" class="none" value="<%=fbsr.getCreatetime1() %>">
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="���������˵�"  onClick="Calendar_importtime1();">&nbsp;
		&nbsp;��ֹ 
		<input type=text name='importtime2'  size="18" class="none" value="<%=fbsr.getCreatetime2() %>"> 
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="���������˵�"  onClick="Calendar_importtime2();"></td>	
		<td></td><td></td>
		</tr>
		<tr>
        <TD align="right" ><b>��������:</b></TD>
		<TD colspan=5>��ʼ 
		<input type=text name='paymenttime1' size="18" class="none" value="<%=fbsr.getPaymenttime1() %>">
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="���������˵�"  onClick="Calendar_paymenttime1();">&nbsp;
		&nbsp;��ֹ 
		<input type=text name='paymenttime2'  size="18" class="none" value="<%=fbsr.getPaymenttime2() %>"> 
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="���������˵�"  onClick="Calendar_paymenttime2();"></td>
		<td colspan=2>
            <input name=searchbutton type=submit value=" ��ѯ " onClick='mySubmit1()' >
        </td>
		</tr>
	</TABLE>
   
	</form>

	<font color=#215385>��ϸ�б��� <b><%=ibsri.allnum%></b> �����ϼƣ�<b><%=ibsri.allmoney*kind%></b> Ԫ��</font>

	<IMG src="images/line1.gif" border=0 width=900>

	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
		
		<tr bgcolor="#C2CEDC">
		<th></th>
		<th><a class="tablehead" href="javascript:orderList('a.billid','<%=orderby%>');">����</a><%
		if(orderby.equals("a.billid")) out.print("��"); if(orderby.equals("a.billid desc")) out.print("��");
		%></th>
		<%
		if(ctx.isIfview()) {
		%>
		<th><a class="tablehead" href="javascript:orderList('shortname','<%=orderby%>');">�ֹ�˾</a><%
		if(orderby.equals("shortname")) out.print("��"); if(orderby.equals("shortname desc")) out.print("��");
		%></th><%
		}
		%>
		<th><a class="tablehead" href="javascript:orderList('typename','<%=orderby%>');"><%=(kind==1?"����":"����") %>���</a><%
		if(orderby.equals("typename")) out.print("��"); if(orderby.equals("typename desc")) out.print("��");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('jine','<%=orderby%>');">���(Ԫ)</a><%
		if(orderby.equals("jine")) out.print("��"); if(orderby.equals("jine desc")) out.print("��");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('memo','<%=orderby%>');">����˵��</a><%
		if(orderby.equals("memo")) out.print("��"); if(orderby.equals("memo desc")) out.print("��");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('a.jingshouren','<%=orderby%>');">������</a><%
		if(orderby.equals("a.jingshouren")) out.print("��"); if(orderby.equals("a.jingshouren desc")) out.print("��");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('a.operPerson','<%=orderby%>');">¼��Ա</a><%
		if(orderby.equals("a.operPerson")) out.print("��"); if(orderby.equals("a.operPerson desc")) out.print("��");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('b.billtime','<%=orderby%>');">��������</a><%
		if(orderby.equals("b.billtime")) out.print("��"); if(orderby.equals("b.billtime desc")) out.print("��");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('a.createtime','<%=orderby%>');">����ʱ��</a><%
		if(orderby.equals("a.createtime")) out.print("��"); if(orderby.equals("a.createtime desc")) out.print("��");
		%></th>
		
		</tr>
		
		
		<%
						FeiYongGoodsForm tmpInfo = null;
					    for (int index=0; index<ibsri.rslist.size(); index++)
						{
					      	tmpInfo = (FeiYongGoodsForm)ibsri.rslist.get(index);
				%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
   	        <td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath() + "/feiYongItemView.do?billId=" + tmpInfo.getBillid()+"&deptid="+tmpInfo.getDeptid()+"&kind="+tmpInfo.getKind()%>');"><IMG src="images/news1.gif" border=0 alt='�鿴������ϸ'></a></td>
	        <td align='center' width=60><%= tmpInfo.getBillid()%></td>
	        <%if(ctx.isIfview()){%><td align='center'><%= tmpInfo.getShortname() %></td><%} %>
	        <td align='center'><%=tmpInfo.getTypeName()%></td>
	        <td align='right'><%= nf.format(tmpInfo.getJine()*tmpInfo.getKind()) %></td>
	        <td align='left'><%=tmpInfo.getMemo()%></td>
	        <td align='center' width=60><%= tmpInfo.getJingshouren() %></td>
	        <td align='center' width=60><%= tmpInfo.getOperPerson() %></td>
	        <td align='center'><%=tmpInfo.getBillTime()%></td>
			<td align='center'><%=tmpInfo.getCreateTime()%></td>
		
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

