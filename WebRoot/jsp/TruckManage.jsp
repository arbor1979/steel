<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<script language="JavaScript">
function Trim(str){
 if(str.charAt(0) == " "){
  str = str.slice(1);
  str = Trim(str); 
 }
 return str;
}

function delYeWuYuan(id)
{
	if(confirm('确认删除此车辆资料？'))
	{
		window.location='truckManage.do?param=del&id='+id;
	}
}
function orderList(order,old)
{
	
	if(order==old)
		order=order+' desc';
	form2.orderby.value=order;
	form2.submit();
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
    <td width="379" height="20" class="mainhead">车辆资料</td>
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
    java.util.List userList = (java.util.List)request.getAttribute("userList");
	java.util.List departList = (java.util.List)request.getAttribute("departList");
	TruckInfoForm fi=(TruckInfoForm)request.getAttribute("fi");
	String orderby=(String)request.getAttribute("orderby");
%>
	<form name="form2" method="post" action="truckManage.do?" >
    	<br>
    	<TABLE  class="mailtable"> 
	  <tr>
            <td  align="right" > 
                <b>车牌号:</b>
            </td>
            <td >
            	<INPUT class=none  name="carno" type="text" size="10" value="<%=fi.getCarno() %>">
            </td>
			 <td  align="right" > 
                <b>车主姓名:</b>
            </td>
            <td >
            	<INPUT class=none  name="driver" type="text" size="10" value="<%=fi.getDriver() %>">
            </td>
            <td  align="right" > 
                <b>车型:</b>
            </td>
            <td >
            	<INPUT class=none  name="kind" type="text" size="10" value="<%=fi.getKind() %>">
            </td>
            <td  align="right" > 
                <b>载重:</b>
            </td>
            <td >
            	<select name="oper">
	         		<option value="" <%=(fi.getOper().equals("")?"selected":"") %>></option>
	         		<option value=">" <%=(fi.getOper().equals(">")?"selected":"") %>>></option>
	         		<option value="<" <%=(fi.getOper().equals("<")?"selected":"") %>><</option>
	         		<option value="=" <%=(fi.getOper().equals("=")?"selected":"") %>>=</option>
	         	</select>
	         	<input type=text name='weight' size="10" class="none" value="<%=(fi.getOper().equals("")?"":fi.getWeight()) %>">
            </td>
            <%
			if(ctx.isIfview()) {
			%>
            <td  align="right" > 
                <b>分公司:</b>
            </td>
            <td>
            <select name="deptid">
			<option value="-1">-全部-</option>
			<%
			DepartInfoForm dif=new DepartInfoForm();
			for(int i=0;i<departList.size();i++)
			{
				dif=(DepartInfoForm)departList.get(i);
			%>
			<option value="<%=dif.getId() %>" <%=(fi.getDeptid()==dif.getId()?"selected":"") %>><%=dif.getShortname() %></option>
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
            <td >
			<input type="hidden" name="orderby" value="<%=orderby%>">
            <input name=search type=submit value="查询">&nbsp;
            <input name=new type=button value="新增" onclick="window.location='truckManage.do?param=add';">
            </td>
          </tr>
	</TABLE>
	<br>
	</form>	
    	
	<font color=#215385><b>车辆列表</b>(共 <b><%=userList.size()%></b> 个)</font>
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE  class="mailtable">  
		<tr bgcolor="#C2CEDC">
		<th><a class="tablehead" href="javascript:orderList('a.carno','<%=orderby%>');">车牌号</a><%if(orderby.equals("a.carno")) out.print("↓"); if(orderby.equals("a.carno desc")) out.print("↑");%></th>
		<%
			if(ctx.isIfview()) {
			%>
		<th><a class="tablehead" href="javascript:orderList('b.shortname','<%=orderby%>');">分公司</a><%if(orderby.equals("a.shortname")) out.print("↓"); if(orderby.equals("a.shortname desc")) out.print("↑");%></th>
		<%} %>
		<th><a class="tablehead" href="javascript:orderList('a.driver','<%=orderby%>');">车主</a><%if(orderby.equals("a.driver")) out.print("↓"); if(orderby.equals("a.driver desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('a.idcard','<%=orderby%>');">身份证</a><%if(orderby.equals("a.idcard")) out.print("↓"); if(orderby.equals("a.idcard desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('a.mobile','<%=orderby%>');">电话</a><%if(orderby.equals("a.mobile")) out.print("↓"); if(orderby.equals("a.mobile desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('a.chejia','<%=orderby%>');">车型</a><%if(orderby.equals("a.chejia")) out.print("↓"); if(orderby.equals("a.chejia desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('a.weight','<%=orderby%>');">载重</a><%if(orderby.equals("a.weight")) out.print("↓"); if(orderby.equals("a.weight desc")) out.print("↑");%></th>
		<th>操作</th>
		</tr>
		
		<%
					    int index = 0;
						TruckInfoForm tmpUserInfo = null;
					      	
					    for (index=0; index<userList.size(); index++)
						{
					      		tmpUserInfo = (TruckInfoForm)userList.get(index);
				%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        <td align=center><%= tmpUserInfo.getCarno() %></td>
	        <%
			if(ctx.isIfview()) {
			%>
	        <td align=center><%= tmpUserInfo.getShortname() %></td>
	        <%} %>
	        <td align=center><%= tmpUserInfo.getDriver() %></td>
	        <td align=center><%= tmpUserInfo.getIdcard() %></td>
	        <td align=center><%= tmpUserInfo.getMobile() %></td>
	        <td align=center><%= tmpUserInfo.getKind() %></td>
	        <td align=right><%= tmpUserInfo.getWeight() %></td>
	        <td align=center>
	        <%if(ctx.getDeptid()==tmpUserInfo.getDeptid() || ctx.getDeptid()==0) {%>
	        <input type="button" value="修改" onclick="window.location='truckManage.do?param=edit&id=<%= tmpUserInfo.getId() %>';">
	        <input type="button" value="删除" onclick="delYeWuYuan(<%= tmpUserInfo.getId() %>);">
	        <%
	        }
	        %>
	        </td>
	        </tr>
	        <%
	     }
	        %>
	</TABLE>
	</td>
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

