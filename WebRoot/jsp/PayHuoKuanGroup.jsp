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

	ResultsetList ys = (ResultsetList)request.getAttribute("ysg");
  	List departList = (List)request.getAttribute("departList");
  	String act = (String)request.getAttribute("act");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
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
    <td width="379" height="20" class="mainhead"><%=(kind==1?"Ӧ��":"Ӧ��") %><%=(act.equals("view")?"�ʿ��ѯ":"����") %>�����ܣ�</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td height="300" align="center" valign="top">   
         
	<form name="form1" method="post" action="payHuoKuan.do" >
	<br>
	<input type="hidden" name="kind"  value="<%=kind %>">
	<input type="hidden" name="curpage"  value="<%=ys.curpage %>">
	<input type="hidden" name="orderby"  value="<%=orderby %>">
	<input type="hidden" name="act"  value="<%=act %>">
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	  <tr>
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
            &nbsp;&nbsp;<input name=searchbutton type=submit value="��ѯ" >
            &nbsp;&nbsp;<b>�鿴��ʽ��</b><input type="radio" name="viewtype" value=0 <%=(phf.getViewtype()==0?"checked":"") %> onclick="form1.submit();">����
            &nbsp;<input type="radio" name="viewtype" value=1 <%=(phf.getViewtype()==1?"checked":"") %> onclick="form1.submit();">��ϸ</TD>
         </tr>
     </TABLE>
     </form>

	<font color=#215385><%=(kind==1?"Ӧ��":"Ӧ��") %>���б��� <b><%=ys.allnum%></b> ��<%=(kind==1?"�ͻ�":"��Ӧ��") %>���ϼ� <b><%=ys.allsumnum%></b> �֣�
	���<b><%=nf.format(ys.allmoney)%></b> Ԫ��<%=(kind==1?"Ԥ��":"Ԥ��") %>��<b><%=nf.format(ys.realmoney)%></b> Ԫ��
	<%=(kind==1?"ʵǷ":"Ӧ��") %>��<b><%=nf.format(ys.oddmoney)%></b> Ԫ��</font>

	<IMG src="images/line1.gif" width=900 border=0>

	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
	
		<tr bgcolor="#C2CEDC">
		<th><a class="tablehead" href="javascript:orderList('b.name','<%=orderby%>');"><%=(kind==1?"�ͻ�":"��Ӧ��") %>����<%if(orderby.equals("b.name")) out.print("��"); if(orderby.equals("b.name desc")) out.print("��");%></th>
		<%if(ctx.isIfview()){ %>
		<th><a class="tablehead" href="javascript:orderList('shortname','<%=orderby%>');">�ֹ�˾<%if(orderby.equals("shortname")) out.print("��"); if(orderby.equals("shortname desc")) out.print("��");%></th>
		<%} %>
		<th><a class="tablehead" href="javascript:orderList('SUM(a.Totalnum)','<%=orderby%>');">����(��)<%if(orderby.equals("SUM(a.Totalnum)")) out.print("��"); if(orderby.equals("SUM(a.Totalnum) desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('SUM(a.TotalPrice)','<%=orderby%>');">������(Ԫ)<%if(orderby.equals("SUM(a.TotalPrice)")) out.print("��"); if(orderby.equals("SUM(a.TotalPrice) desc")) out.print("��");%></th>
		<%if(kind==1){%>
		<th><a class="tablehead" href="javascript:orderList('preshou','<%=orderby%>');">Ԥ���ʻ�(Ԫ)<%if(orderby.equals("preshou")) out.print("��"); if(orderby.equals("preShou desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('(SUM(a.TotalPrice)-CASE WHEN preshou IS NULL THEN 0 ELSE preshou END)','<%=orderby%>');">ʵǷ���(Ԫ)<%if(orderby.equals("(SUM(a.TotalPrice)-CASE WHEN preshou IS NULL THEN 0 ELSE preshou END)")) out.print("��"); if(orderby.equals("(SUM(a.TotalPrice)-CASE WHEN preshou IS NULL THEN 0 ELSE preshou END) desc")) out.print("��");%></th>
		<%}else{ %>
		<th><a class="tablehead" href="javascript:orderList('prepay','<%=orderby%>');">Ԥ���ʻ�(Ԫ)<%if(orderby.equals("prepay")) out.print("��"); if(orderby.equals("prepay desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('(SUM(a.TotalPrice)-CASE WHEN prepay IS NULL THEN 0 ELSE prepay END)','<%=orderby%>');">Ӧ�����(Ԫ)<%if(orderby.equals("(SUM(a.TotalPrice)-CASE WHEN prepay IS NULL THEN 0 ELSE prepay END)")) out.print("��"); if(orderby.equals("(SUM(a.TotalPrice)-CASE WHEN prepay IS NULL THEN 0 ELSE prepay END) desc")) out.print("��");%></th>
		<%} %>	
		<th><a class="tablehead" href="javascript:orderList('count(*)','<%=orderby%>');">�˵���(��)<%if(orderby.equals("count(*)")) out.print("��"); if(orderby.equals("count(*) desc")) out.print("��");%></th>
		<th>�鿴��ϸ</th>
		</tr>
		
		<%
		if(kind==1)
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
				<td align='right' width=100><%=nf.format(tmpInfo.getPrepay())%></td>
				<td align='right' ><%=(tmpInfo.getTotalPrice()-tmpInfo.getPrepay()>0?nf.format(tmpInfo.getTotalPrice()-tmpInfo.getPrepay()):"")%></td>
				<td align='center' width=80><%=tmpInfo.getAllcount()%></td>
				<td align='center'><input type="button" value="������ϸ" onclick="window.navigate('payHuoKuan.do?param=list&act=<%=act %>&kind=<%=kind %>&factory=<%=tmpInfo.getFactory() %>&deptid=<%=tmpInfo.getDeptid() %>');">
			
			</td>
			
	        </tr>
	        <%
	      	}
		}
		else if(kind==-1)
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
				<td align='right' width=100><%=nf.format(tmpInfo.getPrepay1())%></td>
				<td align='right' >
				<%if(tmpInfo.getTotalPrice()>0)
				{
					if(tmpInfo.getTotalPrice()-tmpInfo.getPrepay1()>0)
						out.print(nf.format(tmpInfo.getTotalPrice()-tmpInfo.getPrepay1()));
					else
						out.print("0.00");
				}
				else
					out.print(nf.format(tmpInfo.getTotalPrice()));
				%>
				</td>
				<td align='center' width=80><%=tmpInfo.getAllcount()%></td>
				<td align='center'><input type="button" value="������ϸ" onclick="window.navigate('payHuoKuan.do?param=list&act=<%=act %>&kind=<%=kind %>&factory=<%=tmpInfo.getFactory() %>&deptid=<%=tmpInfo.getDeptid() %>');">
			
			</td>
			
	        </tr>
	        <%
	      	}
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

