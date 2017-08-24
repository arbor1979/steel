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

function gotoPage(){
	
	//该值为要条转到的页面的起始记录号
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
    <td width="379" height="20" class="mainhead">其他往来查询</td>
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
	OtherToPayForm fi = (OtherToPayForm)request.getAttribute("fi");
	List FactList = (List)request.getAttribute("FactList");
	List departList = (List)request.getAttribute("departList");	
	String orderby = (String)request.getAttribute("orderby");
	String zhujima = (String)request.getAttribute("zhujima");
	ResultsetList ys = (ResultsetList)request.getAttribute("ys");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
%>
	<form name="form1" method="post" action="otherPaySearch.do" >
	<br>
	<input type="hidden" name="curpage"  value="<%=ys.curpage %>">
	<input type="hidden" name="orderby"  value="<%=orderby %>">
	<TABLE  class="mailtable"> 
	  <tr>
        	<%if(ctx.isIfview()){ %>
             <td  align="right" width=80> 
                <b>分公司:</b></td>
                <td >
            	<select name="deptid" >
    			<option value="-1">-全部-</option>
    			<%
    			    	DepartInfoForm dif=new DepartInfoForm();
    			    	for(int i=0;i<departList.size();i++)
    			    	{
    			    		dif=(DepartInfoForm)departList.get(i);
    			%>
    			<option value="<%=dif.getId() %>" <%=(fi.getDeptid()==dif.getId()?"selected":"") %>><%=dif.getShortname()%></option>
    			<%
    			}
    			%>
    			</select>
    			</td>
            <%} %>
            
            <td  align="right" width=150> 
                <b>往来单位名称(助记码):</b>
            </td>
            <td>
            	<input class=none type="text" name="zhujima" value="<%=zhujima %>"> 
            &nbsp;&nbsp;<input name=searchbutton type=submit value="查询" ></TD>
         </tr>
     </TABLE>
     </form>

	<font color=#215385>汇总结果（共 <b><%=ys.allnum%></b> 个往来单位，其他应收 <b><%=nf.format(ys.allmoney)%></b> 元
	，其他应付 <b><%=nf.format(ys.allsumnum)%></b> 元）</font>

	<IMG src="images/line1.gif" width=900 border=0>

	<TABLE  class="mailtable">  
	
		<tr bgcolor="#C2CEDC">
		<th><a class="tablehead" href="javascript:orderList('b.name','<%=orderby%>');">往来单位名称<%if(orderby.equals("b.name")) out.print("↓"); if(orderby.equals("b.name desc")) out.print("↑");%></th>
		<%if(ctx.isIfview()){ %>
		<th><a class="tablehead" href="javascript:orderList('shortname','<%=orderby%>');">分公司<%if(orderby.equals("shortname")) out.print("↓"); if(orderby.equals("shortname desc")) out.print("↑");%></th>
		<%} %>
		<th>其他应收(元)</th>
		<th>明细</th>
		<th>其他应付(元)</th>
		<th>明细</th>
		</tr>
		
		<%
			OtherToPayForm tmpInfo = null;
		    for (int index=0; index<ys.rslist.size(); index++)
			{
		      	tmpInfo = (OtherToPayForm)ys.rslist.get(index);
		    %>
		        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
		        <td align='left' width=180><a href="javascript:viewDetail('<%= request.getContextPath()+ "/factoryView.do?factory="+tmpInfo.getFactory() %>');"><%=tmpInfo.getFactname()%></a></td>
		        <%if(ctx.isIfview()){ %>
		        <td align='center'><%=tmpInfo.getShortname() %></td>
		        <%} %>
		        <td align='right' ><%= nf.format(tmpInfo.getJine()) %></td>
		        <td align=center><input type="button" value="明细" onclick="window.location='otherPaySearch.do?param=list&factory=<%=tmpInfo.getFactory() %>&deptid=<%=tmpInfo.getDeptid() %>&type=0';"></td>
		        <td align='right' ><%= nf.format(tmpInfo.getJine1()) %></td>
		        <td align=center><input type="button" value="明细" onclick="window.location='otherPaySearch.do?param=list&factory=<%=tmpInfo.getFactory() %>&deptid=<%=tmpInfo.getDeptid() %>&type=1';"></td>

				</td>			
	        </tr>
	      <%} %>
	</TABLE>
	<br>
	
	<div align="right">
		<%
			if(ys.curpage>1)
			{
		%>
		<a href="javascript:gotoPageByN(1)"><IMG src="images/startpage.gif" border=0 alt='首页'></a>&nbsp;
		<a href="javascript:gotoPageByN(<%=ys.curpage-1%>)"><IMG src="images/lastpage.gif" border=0 alt='上一页'></a>&nbsp;&nbsp;

		<%
			}
			else
			{
		%>
		<IMG src="images/startpage.gif" border=0 alt='首页'>&nbsp;
		<IMG src="images/lastpage.gif" border=0 alt='上一页'>&nbsp;&nbsp;
		<%	
			}

	    	if(ys.curpage<ys.pagecount)
	    	{
	    %>
	    	<a href="javascript:gotoPageByN(<%=ys.curpage+1%>)"><IMG src="images/nextpage.gif" border=0 alt='下一页'></a>&nbsp;
	        <a href="javascript:gotoPageByN(<%=ys.pagecount%>)"><IMG src="images/endpage.gif" border=0 alt='末页'></a>&nbsp;&nbsp;&nbsp;&nbsp;
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
		<%for(int i=1; i<=ys.pagecount; i++)
		  {%>
			<option value="<%=i%>" <%if(i==ys.curpage){%>selected<%}%>><%=i%></option>
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

