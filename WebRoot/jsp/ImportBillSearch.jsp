<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.*"%>

<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js"></script>
<script language="JavaScript">

function mySubmit1(){

	if(!isDateChen(document.form1.importtime1))
	{//检查输入的时间是否合法
		return 1;
	}
	else if(!isDateChen(document.form1.importtime2))
	{//检查输入的时间是否合法
		return 1;
	}
	else if(!isDateChen(document.form1.paymenttime1))
	{//检查输入的时间是否合法
		return 1;
	}
	else if(!isDateChen(document.form1.paymenttime2))
	{//检查输入的时间是否合法
		return 1;
	}
	else
	{
		document.form1.submit();
	}
}

function gotoPage(){
	
	//该值为要条转到的页面的起始记录号
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
    <td width="379" height="20" class="mainhead">进货单查询</td>
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
             	List FactList = (List)request.getAttribute("FactList");
             	List PersonList = (List)request.getAttribute("PersonList");
             	List fkList = (List)request.getAttribute("fkList");
              	List departList = (List)request.getAttribute("departList");
             	ResultsetList ibsri = (ResultsetList)request.getAttribute("ibsri");
             	ImportBillForm gif = (ImportBillForm)request.getAttribute("gif");
             	String importTime1=(String)request.getAttribute("importTime1");
             	String importTime2=(String)request.getAttribute("importTime2");
             	String paymentTime1=(String)request.getAttribute("paymentTime1");
             	String paymentTime2=(String)request.getAttribute("paymentTime2");
             	String orderby=(String)request.getAttribute("orderby");
             	String ifpay=(String)request.getAttribute("ifpay");
             	String kind=(String)request.getAttribute("kind");
             	NumberFormat nf = NumberFormat.getNumberInstance();
             	nf.setMaximumFractionDigits(2);
             	nf.setMinimumFractionDigits(2);
             %>

    	<form name="form1" method="post" action="importBillSearch.do" >
    	<font color=#215385><b>查询条件</b></font>
    	<IMG src="images/line1.gif" border=0 width=900>
    	<br>
    	<input type="hidden" name="curpage"  value="1">
    	<input type="hidden" name="orderby"  value="<%=orderby %>">
    	<input type="hidden" name="searchbutton" value="查询">
    	<TABLE  class="mailtable"> 
	  <tr>
            <td  align="right"> 
                <b>单号:</b>
            </td>
            <td>
            	<% String tmpStr="";
            	out.print("<input type=\"text\" class=none size=10 value='"+(gif.getBillId()==0?"":gif.getBillId())+"' name='billId'>");
            	 %>
            </td>
            <td  align="right"> 
                <b>类型:</b>
            </td>
            <td>
            	<SELECT size=1 name="kind">
 					<option value="0" <%=(kind.equals("0")?"selected":"") %>>-全部-</option>
					<option value="1" <%=(kind.equals("1")?"selected":"") %>>进货</option>
					<option value="-1" <%=(kind.equals("-1")?"selected":"") %>>退货</option>
	         	</SELECT>
            </td>
            <td  align="right"> 
                <b>录入员:</b>
            </td>
            <td>
            	<SELECT name="createPerson">
            		<option value="">-全部-</option>
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
            <td align="right" ><b>供应商:</b></td>
			<td colspan=3>
			<SELECT name="factory">
            		<option value="0">-全部-</option>
            		<%
            		            		            	FactoryInfoForm tmpFact=null;
            		            		            	for(int i=0; i<FactList.size(); i++)
            		            		            	{
            		            		            		tmpFact = (FactoryInfoForm)FactList.get(i);
            		%>
            		<option value="<%=tmpFact.getId()%>" <%=(tmpFact.getId()==gif.getFactory()?"selected":"") %>><%=tmpFact.getName()%></option>
			<%
			}
			%>
            	</SELECT><input class=none type="text" name="zhujima" value="<%=gif.getZhujima() %>" size=5 onChange="window.location='importBillSearch.do?zhujima='+this.value;">(助记码)
            		
         	</td>
           
          </tr>
          <tr>
		<TD align="right" width=10%><b>录入时间:</b></TD>
		<TD colspan=5>起始 
		<input type=text name='importtime1' size="18" class="none" value="<%=importTime1 %>">
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="弹出日历菜单"  onClick="Calendar_importtime1();">&nbsp;
		&nbsp;终止 
		<input type=text name='importtime2'  size="18" class="none" value="<%=importTime2 %>"> 
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="弹出日历菜单"  onClick="Calendar_importtime2();"></td>
		<td align="right"><b>是否付款:</b></td><td>
		<select name="ifpay">
			<option value="" <%=(ifpay.equals("")?"selected":"") %>>-全部-</option>
			<option value="1" <%=(ifpay.equals("1")?"selected":"") %>>已付</option>
			<option value="0" <%=(ifpay.equals("0")?"selected":"") %>>未付</option>
		</select></td>
		<td  align="right"> 
                <b>付款方式:</b>
            </td>
            <td >
            	<SELECT name="fkType" >
            		<option value="0" selected>-全部-</option>
            		<%
            		            		     FuKuanForm  fkf=null;   	
            		            		     for(int i=0; i<fkList.size(); i++)
            		            		     {
            		            		         fkf = (FuKuanForm)fkList.get(i);
            		%>
            		<option value="<%=fkf.getId()%>" <%=(fkf.getId()==gif.getFkType()?"selected":"") %>><%=(fkf.getName().length()>12?fkf.getName().substring(0,10):fkf.getName())%></option>
			<%
			}
			%>
            	</SELECT>
            </td> 
		
        </tr>
         <tr>
		<TD align="right" width=10%><b>付款时间:</b></TD>
		<TD colspan=5>起始 
		<input type=text name='paymenttime1' size="18" class="none" value="<%=paymentTime1 %>">
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="弹出日历菜单"  onClick="Calendar_paymenttime1();">&nbsp;
		&nbsp;终止 
		<input type=text name='paymenttime2'  size="18" class="none" value="<%=paymentTime2 %>"> 
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="弹出日历菜单"  onClick="Calendar_paymenttime2();"></td>
		<%
		if(ctx.isIfview()) {
		%>
             <td  align="right" > 
                <b>分公司:</b></td>
                <td>
            	<select name="deptid" >
    			<option value="-1">-全部-</option>
    			<%
    			    	DepartInfoForm dif=new DepartInfoForm();
    			    	for(int i=0;i<departList.size();i++)
    			    	{
    			    		dif=(DepartInfoForm)departList.get(i);
    			%>
    			<option value="<%=dif.getId() %>" <%=(gif.getDeptid()==dif.getId()?"selected":"") %>><%=dif.getShortname()%></option>
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
		<td colspan=2><input name=searchbutton type=submit value="查询" onClick='mySubmit1()' ></TD>
	</TABLE>
   
	</form>

<%
	if(ibsri!=null) 
	{
%>
	<font color=#215385>进货单列表（共 <b><%=ibsri.allnum%></b> 张单据，合计：<b><%=ibsri.allsumnum%></b> 吨，
	应付：<b><%=nf.format(ibsri.allmoney)%></b> 元，实付：<b><%=nf.format(ibsri.realmoney)%></b> 元，去零：<b><%=nf.format(ibsri.oddmoney)%></b> 元，未付：<b><%=nf.format(ibsri.allmoney-ibsri.realmoney-ibsri.oddmoney)%></b> 元）</font>

	<IMG src="images/line1.gif" border=0 width=900>

	<TABLE  class="mailtable">  
		<%
  			if(ibsri.allnum>0)
  			{
  		%>
		<tr bgcolor="#C2CEDC">
		<th></th>
		<th><a class="tablehead" href="javascript:orderList('billid','<%=orderby%>');">单号</a><%
		if(orderby.equals("billid")) out.print("↓"); if(orderby.equals("billid desc")) out.print("↑");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('a.kind','<%=orderby%>');">类型</a><%
		if(orderby.equals("a.kind")) out.print("↓"); if(orderby.equals("a.kind desc")) out.print("↑");
		%></th>
		<%
		if(ctx.isIfview()) {
		%>
		<th><a class="tablehead" href="javascript:orderList('shortname','<%=orderby%>');">分公司</a><%
		if(orderby.equals("shortname")) out.print("↓"); if(orderby.equals("shortname desc")) out.print("↑");
		%></th><%
		}
		%>
		<th><a class="tablehead" href="javascript:orderList('b.name','<%=orderby%>');">供应商</a><%
		if(orderby.equals("b.name")) out.print("↓"); if(orderby.equals("b.name desc")) out.print("↑");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('createPerson','<%=orderby%>');">录入员</a><%
		if(orderby.equals("createPerson")) out.print("↓"); if(orderby.equals("createPerson desc")) out.print("↑");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('totalnum','<%=orderby%>');">数量(吨)</a><%
		if(orderby.equals("totalnum")) out.print("↓"); if(orderby.equals("totalnum desc")) out.print("↑");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('totalprice','<%=orderby%>');">应付货款(元)</a><%
		if(orderby.equals("totalprice")) out.print("↓"); if(orderby.equals("totalprice desc")) out.print("↑");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('realmoney','<%=orderby%>');">实付货款(元)</a><%
		if(orderby.equals("realmoney")) out.print("↓"); if(orderby.equals("realmoney desc")) out.print("↑");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('importTime','<%=orderby%>');">进货时间</a><%
		if(orderby.equals("importTime")) out.print("↓"); if(orderby.equals("importTime desc")) out.print("↑");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('paymentTime','<%=orderby%>');">付款时间</a><%
		if(orderby.equals("paymentTime")) out.print("↓"); if(orderby.equals("paymentTime desc")) out.print("↑");
		%></th>
		</tr>
		<%
		}
						String color="";
						ImportBillForm tmpInfo = null;
					    for (int index=0; index<ibsri.rslist.size(); index++)
						{
					      	tmpInfo = (ImportBillForm)ibsri.rslist.get(index);
					      	if(tmpInfo.getKind()==1)
				        		color="<font color=black>";
				        	else
				        		color="<font color=red>";
				        	if(tmpInfo.getConfirmFlage().equals("3"))
				            	color="<font color=#0000aa>";
				%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        <td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath()+ "/importItemsView.do?billId=" + tmpInfo.getBillId()+"&deptid="+tmpInfo.getDeptid() %>');"><IMG src="images/news1.gif" border=0 alt='查看货单明细'></a></td>
	        <td align='center' width=60><%=color%><%
	        if(tmpInfo.getConfirmFlage().equals("3"))
	        	out.print("<s>"+tmpInfo.getBillId()+"</s>");
	        else
	        	out.print(tmpInfo.getBillId());
	        %></font></td>
	        <td align='center' ><%=color%><%
	        if(tmpInfo.getKind()==1)
	        {
        		out.print("进");
        		if(tmpInfo.getIeva()==1)
            		out.print("(估)");        			
	        }
        	else
        		out.print("退");
	        %></td>
	        <%if(ctx.isIfview()){%><td align='center'><%=color%><%= tmpInfo.getShortname() %></font></td><%} %>
	        <td align='left'><%=color%><%=tmpInfo.getFactoryName()%></font></td>
	        <td align='center' width=60><%=color%><%= tmpInfo.getCreatePerson() %></font></td>
	        <td align='right' ><%=color%><%= tmpInfo.getTotalNum()  %></font></td>
			<td align='right'><%=color%><%= nf.format(tmpInfo.getTotalPrice()) %></font></td>
			<td align='right'><%=color%><%= nf.format(tmpInfo.getRealMoney()) %></font></td>
			<td align='center' ><%=color%><%=tmpInfo.getImportTime()%></font></td>
			<td align='center' ><%=color%><%
			if(tmpInfo.getConfirmFlage().equals("3"))
		        out.print("此单已作废");
			else
				out.print(tmpInfo.getPaymentTime()); 
			%></font></td>
		
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
		<a href="javascript:gotoPageByN(1)"><IMG src="images/startpage.gif" border=0 alt='首页'></a>&nbsp;
		<a href="javascript:gotoPageByN(<%=ibsri.curpage-1%>)"><IMG src="images/lastpage.gif" border=0 alt='上一页'></a>&nbsp;&nbsp;

		<%
			}
			else
			{
		%>
		<IMG src="images/startpage.gif" border=0 alt='首页'>&nbsp;
		<IMG src="images/lastpage.gif" border=0 alt='上一页'>&nbsp;&nbsp;
		<%	
			}

	    	if(ibsri.curpage<ibsri.pagecount)
	    	{
	    %>
	    	<a href="javascript:gotoPageByN(<%=ibsri.curpage+1%>)"><IMG src="images/nextpage.gif" border=0 alt='下一页'></a>&nbsp;
	        <a href="javascript:gotoPageByN(<%=ibsri.pagecount%>)"><IMG src="images/endpage.gif" border=0 alt='末页'></a>&nbsp;&nbsp;&nbsp;&nbsp;
	    <%
	    	}
			else
			{
		%>
		<IMG src="images/nextpage.gif" border=0 alt='下一页'>&nbsp;
		<IMG src="images/endpage.gif" border=0 alt='末页'>&nbsp;&nbsp;&nbsp;&nbsp;
		<%	
			}
		%>
    	</div>
    	<div align="center">
		第 <select name="gotopagenum" onChange="gotoPage()">
		<%for(int i=1; i<=ibsri.pagecount; i++)
		  {%>
			<option value="<%=i%>" <%if(i==ibsri.curpage){%>selected<%}%>><%=i%></option>
		<%}%>
		</select> 页
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

