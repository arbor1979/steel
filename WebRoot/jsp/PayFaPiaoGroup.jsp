<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.*"%>
<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js"></script>
<script language="JavaScript">

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

function orderList(order,old)
{
	if(order==old)
		order=order+' desc';
	form1.orderby.value=order;
	form1.submit();
}
function viewDetail(url)
{
	window.open(url,'view','top=0,left=0,location=no,directories=no,hotkeys=0,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=700,height=300');
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
  	String zhujima=phf.getZhujima();
  	int deptid=phf.getDeptid();
%>
<table width=980 border="0" align="center" cellpadding="0" cellspacing="0">
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
    <td width="379" height="20" class="mainhead"><%=(kind==0?"Ӧ��":"Ӧ��") %>��Ʊ����</td>
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
	
	ResultsetList ys = (ResultsetList)request.getAttribute("ys");
  	List departList = (List)request.getAttribute("departList");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
%>
	<form name="form1" method="post" action="payFaPiao.do" >
	<br>
	<input type="hidden" name="kind"  value="<%=kind %>">
	<input type="hidden" name="curpage"  value="<%=ys.curpage %>">
	<input type="hidden" name="orderby"  value="<%=orderby %>">
	<TABLE  class="mailtable"> 
	  <tr>
	  	<td align="center">
		<input type="radio" name="kind" value="0" <%=(kind==0?"checked":"") %> onclick="window.location='payFaPiao.do?kind=0'"><b>��ƱӦ��</b>&nbsp;&nbsp;
		<input type="radio" name="kind" value="1" <%=(kind==1?"checked":"") %>  onclick="window.location='payFaPiao.do?kind=1'"><b>��ƱӦ��</b>
		</td>
        	<%if(ctx.isIfview()){ %>
             <td  align="right" width=80> 
                <b>�ֹ�˾:</b></td>
                <td >
            	<select name="deptid" >
    			<option value="-1">-ȫ��-</option>
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
            <%} %>
            <td  align="right" width=150> 
                <b><%=(kind==1?"�ͻ�":"��Ӧ��") %>����(������):</b>
            </td>
            <td>
            	<input class=none type="text" name="zhujima" value="<%=zhujima %>"> 
            &nbsp;&nbsp;<input name=searchbutton type=submit value="��ѯ" ></TD>
         </tr>
     </TABLE>
     </form>

	<font color=#215385><%=(kind==0?"Ӧ��":"Ӧ��") %>��Ʊ�б��� <b><%=ys.allnum%></b> ��<%=(kind==1?"�ͻ�":"��Ӧ��") %>���ϼ� <b><%=ys.allsumnum%></b> �֣�
	���<b><%=nf.format(ys.allmoney)%></b> Ԫ��</font>

	<IMG src="images/line1.gif" width=900 border=0>

	<TABLE  class="mailtable">  
	
		<tr bgcolor="#C2CEDC">
		<th><a class="tablehead" href="javascript:orderList('b.name','<%=orderby%>');"><%=(kind==1?"�ͻ�":"��Ӧ��") %>����<%if(orderby.equals("b.name")) out.print("��"); if(orderby.equals("b.name desc")) out.print("��");%></th>
		<%if(ctx.isIfview()){ %>
		<th><a class="tablehead" href="javascript:orderList('shortname','<%=orderby%>');">�ֹ�˾<%if(orderby.equals("shortname")) out.print("��"); if(orderby.equals("shortname desc")) out.print("��");%></th>
		<%} %>
		<th><a class="tablehead" href="javascript:orderList('SUM(a.Totalnum)','<%=orderby%>');">����(��)<%if(orderby.equals("SUM(a.Totalnum)")) out.print("��"); if(orderby.equals("SUM(a.Totalnum) desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('SUM(a.TotalPrice)','<%=orderby%>');">������(Ԫ)<%if(orderby.equals("SUM(a.TotalPrice)")) out.print("��"); if(orderby.equals("SUM(a.TotalPrice) desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('count(*)','<%=orderby%>');"><%=(kind==0?"Ӧ��":"Ӧ��") %>��Ʊ����(��)<%if(orderby.equals("count(*)")) out.print("��"); if(orderby.equals("count(*) desc")) out.print("��");%></th>
		<th>�鿴��ϸ</th>
		</tr>
		
		<%
		if(kind==0)
		{
			ExportBillForm tmpInfo = null;
		    for (int index=0; index<ys.rslist.size(); index++)
			{
		      	tmpInfo = (ExportBillForm)ys.rslist.get(index);
		    %>
		        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
		        <td align='left' width=180><a href="javascript:viewDetail('<%= request.getContextPath()+ "/factoryView.do?factory="+tmpInfo.getFactory() %>');"><%=tmpInfo.getFactoryName()%></a></td>
		        <%if(ctx.isIfview()){ %>
		        <td align='center'><%=tmpInfo.getShortname() %></td>
		        <%} %>
		        <td align='right' ><%= tmpInfo.getTotalNum() %></td>
				<td align='right'><%= nf.format(tmpInfo.getTotalPrice()) %></td>
				<td align='center' width=80><%=tmpInfo.getAllcount()%></td>
				<td align='center'><input type="button" value="������ϸ" onclick="window.location='payFaPiao.do?param=list&kind=<%=kind %>&factory=<%=tmpInfo.getFactory() %>&deptid=<%=tmpInfo.getDeptid() %>';">
			
			</td>
			
	        </tr>
	        <%
	      	}
		}
		else if(kind==1)
		{
			ImportBillForm tmpInfo = null;
		    for (int index=0; index<ys.rslist.size(); index++)
			{
		      	tmpInfo = (ImportBillForm)ys.rslist.get(index);
		    %>
		        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
		        <td align='left' width=180><a href="javascript:viewDetail('<%= request.getContextPath()+ "/factoryView.do?factory="+tmpInfo.getFactory() %>');"><%=tmpInfo.getFactoryName()%></a></td>
		        <%if(ctx.isIfview()){ %>
		        <td align='center'><%=tmpInfo.getShortname() %></td>
		        <%} %>
		        <td align='right' ><%= tmpInfo.getTotalNum() %></td>
				<td align='right'><%= nf.format(tmpInfo.getTotalPrice()) %></td>
				<td align='center' width=80><%=tmpInfo.getAllcount()%></td>
				<td align='center'><input type="button" value="������ϸ" onclick="window.location='payFaPiao.do?param=list&kind=<%=kind %>&factory=<%=tmpInfo.getFactory() %>&deptid=<%=tmpInfo.getDeptid() %>';">
			
			</td>
			
	        </tr>
	        <%
	      	}
		}
        %>
	</TABLE>
	<br>
	
		
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

