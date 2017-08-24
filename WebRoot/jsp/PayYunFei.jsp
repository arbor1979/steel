<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>

<%@ page import="java.text.*"%>
<%@page import="java.util.List"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js"></script>
<script language="JavaScript">

function ConfirmPay(kind,billid,deptid,totalprice,realmoney,acctype,fktype)
{
	if(!IsFloat(realmoney))
	{
		alert('金额必须是浮点数');
		return;
	}
	if(realmoney<=0)
	{
		alert('金额必须大于零');
		return;
	}
	var msg;
	var odd=Math.round((totalprice-realmoney)*100)/100;
	msg="应付运费："+totalprice+" 实付："+realmoney+" 去零："+odd+"\r是否确认支付？";
	if(confirm(msg))
	{
		if(odd>5)
		{
			if(!confirm('去零金额 '+odd+' 超过 5 元！是否继续？'))
				return;
		}
		else if(odd<-5)
		{
			if(!confirm('去零金额 '+odd+' 小于 -5 元！是否继续？'))
				return;
		}
		window.location="payYunFei.do?param=pay&kind="+kind+"&billid="+billid+"&deptid="+deptid+"&realmoney="+realmoney+"&fkType="+fktype+"&acctype="+acctype;
	}
}
function viewDetail(url)
{
	window.open(url,'view','top=0,left=0,location=no,directories=no,hotkeys=0,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=800,height=400');
}
function orderList(order,old)
{
	if(order!=old)
	{
		form1.orderby.value=order;
		form1.submit();
	}
}
function setTotalPrice(kind,text,price,yuchu,acctype,fk)
{
	
	if(kind==5)
	{
		text.value=price;
		acctype.style.visibility="hidden";
		if(yuchu<price)
			alert('预付金额 '+yuchu+' 不足以支付运费 '+price+' ！');
		//text.readOnly=true;
		
	}
	else
	{
		
		text.value='';
		acctype.style.visibility="visible";
		//text.readOnly=false;
	}
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
    <td width="379" height="20" class="mainhead">支付运费</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td align="center" valign="top">   
          
<%
	ResultsetList ys = (ResultsetList)request.getAttribute("ys");
	List acc = (List)request.getAttribute("acc");
	PayHuoKuanForm phf = (PayHuoKuanForm)request.getAttribute("phf");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
	String orderby=phf.getOrderby();
	String zhujima=phf.getZhujima();
%>
	<form name="form1" method="post" action="payYunFei.do" >
	<br>
	<input type="hidden" name="orderby"  value="<%=orderby %>">
		<input type="hidden" name="param"  value="list">
	<TABLE  class="mailtable"> 
	  <tr>
            <td  align="right" width=150> 
                <b>车牌号(车主):</b>
            </td>
            <td>
            	<input class=none type="text" name="zhujima" value="<%=zhujima %>" readonly> 
            </td>
            <td><input type="button" value=" 返回 " onclick="window.location='payYunFei.do';"></td>
         </tr>
     </TABLE>
     </form>
	<font color=#215385>运费列表（共 <b><%=ys.rslist.size() %></b> 张单据，合计运费 <b><%=nf.format(ys.allmoney)%></b> 元，预付 <b><%=nf.format(ys.oddmoney) %></b> 元，应付 <b><%=nf.format(ys.allmoney-ys.oddmoney) %>）</font>

	<IMG src="images/line1.gif" border=0 width=900>

	<TABLE  class="mailtable">  
	
		<tr bgcolor="#C2CEDC">
		<th></th>
		<th><a class="tablehead" href="javascript:orderList('billid','<%=orderby%>');">单号<%if(orderby.equals("billid")) out.print("↓"); if(orderby.equals("billid desc")) out.print("↑");%></th>
		<%if(ctx.isIfview()) {%>
		<th><a class="tablehead" href="javascript:orderList('shortname','<%=orderby%>');">分公司<%if(orderby.equals("shortname")) out.print("↓"); if(orderby.equals("shortname desc")) out.print("↑");%></th>
		<%} %>
		<th><a class="tablehead" href="javascript:orderList('salesPerson','<%=orderby%>');">录入员<%if(orderby.equals("salesPerson")) out.print("↓"); if(orderby.equals("salesPerson desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('totalnum','<%=orderby%>');">重量(吨)<%if(orderby.equals("totalnum")) out.print("↓"); if(orderby.equals("totalnum desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('exportTime','<%=orderby%>');">录入时间<%if(orderby.equals("exportTime")) out.print("↓"); if(orderby.equals("exportTime desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('yunfei','<%=orderby%>');">运费<%if(orderby.equals("yunfei")) out.print("↓"); if(orderby.equals("yunfei desc")) out.print("↑");%></th>
		<th>支付方式</th>
		
		<th>支付账户</th>
		<th>支付金额</th>
		</tr>
		
		<%
		
		ExportBillForm tmpInfo = null;
	    for (int index=0; index<ys.rslist.size(); index++)
		{
	      	tmpInfo = (ExportBillForm)ys.rslist.get(index);
	    %>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        <%if(tmpInfo.getKind()==1){ %>
	        <td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath()+ "/exportItemsView.do?billId=" + tmpInfo.getBillId()+"&deptid="+tmpInfo.getDeptid() %>');"><IMG src="images/news1.gif" border=0 alt='查看货单明细'></a></td>
	        <td align='center' width=60><%= tmpInfo.getBillId() %>(售)</td>
	        <%}else{ %>
			<td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath()+ "/importItemsView.do?billId=" + tmpInfo.getBillId()+"&deptid="+tmpInfo.getDeptid() %>');"><IMG src="images/news1.gif" border=0 alt='查看货单明细'></a></td>
			<td align='center' width=60><%= tmpInfo.getBillId() %>(入)</td>
			<%} %>	        
			<%if(ctx.isIfview()) {%>
			<td align='center' width=60><%= tmpInfo.getShortname() %></td>
			<%} %>
	        <td align='center' width=60><%= tmpInfo.getSalesPerson() %></td>
	        <td align='right' ><%= tmpInfo.getTotalNum() %></td>
			<td align='center' ><%=tmpInfo.getExportTime()%></td>
			<td align='center' width=80><%=tmpInfo.getYunFei()%></td>
			<td align='center'>
				
				<select name="f<%=(tmpInfo.getKind()==1?"e":"i")+tmpInfo.getBillId()%>" onChange="setTotalPrice(this.value,<%=(tmpInfo.getKind()==1?"e":"i")+tmpInfo.getBillId()%>,<%=tmpInfo.getYunFei() %>,<%=ys.oddmoney%>,acc<%=(tmpInfo.getKind()==1?"e":"i")+tmpInfo.getBillId()%>,0)" <%=(ctx.getDeptid()!=tmpInfo.getDeptid()?"disabled":"")%>>
            		<option value=1>现金</option>
            		<option value=5>预付款</option>
            	</SELECT>
			</td>
			<td align='center'>
            	<SELECT name="acc<%=(tmpInfo.getKind()==1?"e":"i")+tmpInfo.getBillId()%>">
            		<%
            		AccountForm tmp;
            		for(int i=0;i<acc.size();i++)
            		{
            			tmp=(AccountForm)acc.get(i);
            			out.print("<option value="+tmp.getId()+" >"+tmp.getAccName()+" "+nf.format(tmp.getJine())+"</option>");
            		}
            		%>
            	</SELECT>
            </td>
			<td align='center'><input type="text" name="<%=(tmpInfo.getKind()==1?"e":"i")+tmpInfo.getBillId()%>" size=8 maxlength=10 onkeyup="InputFloat(this);" <%=(ctx.getDeptid()!=tmpInfo.getDeptid()?"readonly":"")%>>
			<input type="button" value="支付" onclick="ConfirmPay(<%=tmpInfo.getKind() %>,<%=tmpInfo.getBillId()%>,<%=tmpInfo.getDeptid()%>,<%=tmpInfo.getYunFei()%>,<%=(tmpInfo.getKind()==1?"e":"i")+tmpInfo.getBillId()%>.value,acc<%=(tmpInfo.getKind()==1?"e":"i")+tmpInfo.getBillId()%>.value,f<%=(tmpInfo.getKind()==1?"e":"i")+tmpInfo.getBillId()%>.value)" <%=(ctx.getDeptid()!=tmpInfo.getDeptid()?"disabled":"")%>>
		</td>		
        </tr>
      <%} %>
	</TABLE>
	</td>
        </tr>
       <tr> 
          <td valign="middle" align="center" height=30><input type="button" value=" 返回 " onclick="window.location='payYunFei.do';"></td>
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

