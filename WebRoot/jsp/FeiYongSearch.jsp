<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.rule.FeiYongBillSearchRule"%>
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
	}
	else if(!isDateChen(document.form1.importtime2))
	{//检查输入的时间是否合法
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
    <td width="379" height="20" class="mainhead">财务审批</td>
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
             	List BillList = (List)request.getAttribute("BillList");
				List acc = (List)request.getAttribute("acc");
           		List UserList = (List)request.getAttribute("UserList");
           		List YeWuList = (List)request.getAttribute("YeWuList");
           		List departList = (List)request.getAttribute("departList");
             	ResultsetList ibsri = (ResultsetList)request.getAttribute("ibsri");
             	FeiYongBillSearchRule fbsr = (FeiYongBillSearchRule)request.getAttribute("fbsr");
             	NumberFormat nf = NumberFormat.getNumberInstance();
             	nf.setMaximumFractionDigits(2);
             	nf.setMinimumFractionDigits(2);
             	String orderby=fbsr.getOrderStr();
%>

    	<form name="form1" method="post" action="feiYongSearch.do" >
    	<br>
    	<input type="hidden" name="curpage"  value="1">
    	<input type="hidden" name="orderstr"  value="<%=orderby %>">
    	<input type="hidden" name="searchbutton" value="查询">
    	<TABLE  class="mailtable"> 
	    <tr>
           
		<%
		if(ctx.isIfview()) {
		%>
             <td  align="right" width=80> 
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
		
	</TABLE>
   
	</form>

	<font color=#215385>单据列表（共 <b><%=ibsri.allnum%></b> 张，合计：<b><%=ibsri.allmoney%></b> 元）</font>

	<IMG src="images/line1.gif" border=0 width=900>

	<TABLE  class="mailtable">  
		
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
		<th><a class="tablehead" href="javascript:orderList('acctype','<%=orderby%>');">操作帐户</a><%
		if(orderby.equals("acctype")) out.print("↓"); if(orderby.equals("acctype desc")) out.print("↑");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('totalprice','<%=orderby%>');">金额(元)</a><%
		if(orderby.equals("totalprice")) out.print("↓"); if(orderby.equals("totalprice desc")) out.print("↑");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('operPerson','<%=orderby%>');">录入员</a><%
		if(orderby.equals("operPerson")) out.print("↓"); if(orderby.equals("operPerson desc")) out.print("↑");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('a.billtime','<%=orderby%>');">单据日期</a><%
		if(orderby.equals("a.billtime")) out.print("↓"); if(orderby.equals("a.billtime desc")) out.print("↑");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('a.createtime','<%=orderby%>');">操作时间</a><%
		if(orderby.equals("a.createtime")) out.print("↓"); if(orderby.equals("a.createtime desc")) out.print("↑");
		%></th>
		
		</tr>
		
		
		<%
						FeiYongBillForm tmpInfo = null;
					    for (int index=0; index<ibsri.rslist.size(); index++)
						{
					      	tmpInfo = (FeiYongBillForm)ibsri.rslist.get(index);
				%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        <td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath() + "/feiYongItemView.do?billId=" + tmpInfo.getBillId()+"&deptid="+tmpInfo.getDeptid()+"&kind="+tmpInfo.getKind()%>');"><IMG src="images/news1.gif" border=0 alt='查看货单明细'></a></td>
	        <td align='center' width=60><%= tmpInfo.getBillId() %></td>
   	        <td align='center' ><%=(tmpInfo.getKind()==1?"<font color=green>收入</font>":"费用支出")%></td>
			 <%if(ctx.isIfview()){%><td align='center'><%= tmpInfo.getShortname() %></td><%} %>
	        <td align='center'><%=tmpInfo.getAccName()%></td>
	        <td align='right'><%= nf.format(tmpInfo.getTotalPrice()) %></td>
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

