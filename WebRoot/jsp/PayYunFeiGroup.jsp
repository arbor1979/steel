<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.*"%>
<%@ page import="java.net.URLEncoder"%>
<%@page import="java.net.URL"%>

 
<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js"></script>
<script language="JavaScript">

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
	String orderby=phf.getOrderby();
  	String zhujima=phf.getZhujima();
  	int deptid=phf.getDeptid();

	ResultsetList ys = (ResultsetList)request.getAttribute("ysg");
	List departList = (List)request.getAttribute("departList");
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
    <td width="379" height="20" class="mainhead">Ӧ���˷ѣ����ܣ�</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td height="300" align="center" valign="top">   
         
	<form name="form1" method="post" action="payYunFei.do" >
	<br>
	<input type="hidden" name="orderby"  value="<%=orderby %>">
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
                <b>���ƺţ�������:</b>
            </td>
            <td>
            	<input class=none type="text" name="zhujima" value="<%=zhujima %>"> 
            &nbsp;&nbsp;<input name=searchbutton type=submit value="��ѯ" ></TD>
         </tr>
     </TABLE>
     </form>

	<font color=#215385>Ӧ���˷��б��� <b><%=ys.allnum%></b> ���������ϼ��˷ѣ�<b><%=nf.format(ys.allmoney)%></b> Ԫ��Ԥ����<b><%=nf.format(ys.realmoney)%></b> Ԫ��
	Ӧ����<b><%=nf.format(ys.oddmoney)%></b> Ԫ��</font>

	<IMG src="images/line1.gif" width=900 border=0>

	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
	
		<tr bgcolor="#C2CEDC">
		<%if(ctx.isIfview()){ %>
		<th><a class="tablehead" href="javascript:orderList('shortname','<%=orderby%>');">�ֹ�˾<%if(orderby.equals("shortname")) out.print("��"); if(orderby.equals("shortname desc")) out.print("��");%></th>
		<%} %>
		<th><a class="tablehead" href="javascript:orderList('b.carno','<%=orderby%>');">����<%if(orderby.equals("b.carno")) out.print("��"); if(orderby.equals("b.carno desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('b.driver','<%=orderby%>');">����<%if(orderby.equals("b.driver")) out.print("��"); if(orderby.equals("b.driver desc")) out.print("��");%></th>
		
		<th><a class="tablehead" href="javascript:orderList('SUM(a.TotalPrice)','<%=orderby%>');">�˷ѽ��(Ԫ)<%if(orderby.equals("SUM(a.TotalPrice)")) out.print("��"); if(orderby.equals("SUM(a.TotalPrice) desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('b.account','<%=orderby%>');">Ԥ���˷�(Ԫ)<%if(orderby.equals("b.account")) out.print("��"); if(orderby.equals("b.account desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('SUM(a.TotalPrice-b.account)','<%=orderby%>');">Ӧ�����(Ԫ)<%if(orderby.equals("SUM(a.TotalPrice-b.account)")) out.print("��"); if(orderby.equals("SUM(a.TotalPrice-b.account)")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('count(*)','<%=orderby%>');">�˵���(��)<%if(orderby.equals("count(*)")) out.print("��"); if(orderby.equals("count(*) desc")) out.print("��");%></th>
		<th>�鿴��ϸ</th>
		</tr>
		
		<%
			ImportBillForm tmpInfo = null;
		    for (int index=0; index<ys.rslist.size(); index++)
			{
		      	tmpInfo = (ImportBillForm)ys.rslist.get(index);
		    %>
		        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >

		        <%if(ctx.isIfview()){ %>
		        <td align='center'><%=tmpInfo.getShortname() %></td>
		        <%} %>
		        <td align='center'><%=tmpInfo.getCarNo()%></td>
		        <td align='center'><%=tmpInfo.getDriver()%></td>
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
				<td align='center'><input type="button" value="������ϸ" onclick="form2.param.value='list';form2.zhujima.value='<%=tmpInfo.getCarNo() %>';form2.deptid.value=<%=tmpInfo.getDeptid() %>;form2.submit();">
			
			</td>
			
	        </tr>
	        <%
	      	}
        %>
	</TABLE>
	<form name="form2" action="payYunFei.do" method="post">
	<input type="hidden" name="param" value="">
	<input type="hidden" name="zhujima" value="">
	<input type="hidden" name="deptid" value="">
	</form>
	
	
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

