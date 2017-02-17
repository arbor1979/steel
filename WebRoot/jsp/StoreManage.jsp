<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.util.List"%>
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
	if(confirm('是否删除此仓库？'))
	{
		window.navigate('storeManage.do?param=del&id='+id);
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
    <td width="379" height="20" class="mainhead">仓库管理</td>
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
             	StoreInfoForm dif=(StoreInfoForm)request.getAttribute("dif");
             	List deptList=(List)request.getAttribute("DepartList");
            	java.util.List departList = (java.util.List)request.getAttribute("departList");
             %>
	<form name="form2" method="post" action="storeManage.do" >
    	<br>
    	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	  <tr>
            <td  align="right" > 
                <b>名称:</b>
            </td>
            <td>
            	<INPUT class=none  name="name" type="text" size="15" value="<%=dif.getName()%>">
            </td>
			 <td  align="right"> 
                <b>地址:</b>
            </td>
            <td >
            	<INPUT class=none  name="address" type="text" size="15" value="<%=dif.getAddress() %>">
            </td>
            <td  align="right" > 
                <b>联系人:</b>
            </td>
            <td >
            	<INPUT class=none  name="linkman" type="text" size="15" value="<%=dif.getLinkman() %>">
            </td>
             <%
			if(ctx.isIfview()) {
			%>
            <td  align="right"> 
                <b>分公司:</b>
            </td>
            <td>
				<select name="deptid">
				<option value="-1">-全部-</option>
				<%
				DepartInfoForm df=new DepartInfoForm();
				for(int i=0;i<departList.size();i++)
				{
					df=(DepartInfoForm)departList.get(i);
				%>
				<option value="<%=df.getId() %>" <%=(dif.getDeptid()==df.getId()?"selected":"") %>><%=df.getShortname() %></option>
				<%
				}
				%>
				</select>&nbsp;&nbsp;
            </td>
             <%
            }else{
            %>
            <td>&nbsp;</td><td><input type="hidden" name="deptid" value="<%=ctx.getDeptid() %>"></td>
            <%
            }
            %>
            <td >
            <input name=search type=submit value="查询">&nbsp;
            <input name=new type=button value="新增" onclick="window.navigate('storeManage.do?param=add');">
            </td>
          </tr>
	</TABLE>
	<br>
	</form>	
    	
	<font color=#215385><b>仓库列表</b>(共 <b><%=deptList.size()%></b> 个)</font>
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
		<tr bgcolor="#C2CEDC">
		<th>编号</th>
		<%
			if(ctx.isIfview()) {
			%>
		<th>分公司</th>
		<%} %>
		<th>仓库名称</th>
		<th>地址</th>
		<th>电话</th>
		<th>联系人</th>
		<th>操作</th>
		</tr>
		
		<%
					    int index = 0;
					    StoreInfoForm tmpUserInfo = null;
					      	
					    for (index=0; index<deptList.size(); index++)
						{
					      		tmpUserInfo = (StoreInfoForm)deptList.get(index);
				%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        <td align=center><%= tmpUserInfo.getId() %></td>
	        <%
			if(ctx.isIfview()) {
			%>
	        <td align=center><%= tmpUserInfo.getShortname() %></td>
	        <%} %>
	        <td align=left><%= tmpUserInfo.getName() %></td>
	        <td align=left><%= tmpUserInfo.getAddress() %></td>
	        <td align=center><%= tmpUserInfo.getTel() %></td>
	        <td align=center><%= tmpUserInfo.getLinkman() %></td>
	        <td align=center  width=90>
	        <%if(ctx.getDeptid()==tmpUserInfo.getDeptid() || ctx.getDeptid()==0){ %>
	        <input type="button" value="修改" onclick="window.navigate('storeManage.do?param=edit&id=<%= tmpUserInfo.getId() %>');">
	        <input type="button" value="删除" onclick="delYeWuYuan(<%= tmpUserInfo.getId() %>);">
	        <%} %></td>
	        </tr>
	        <%
	     }
	        %>
	</TABLE>
	</td>
        </tr>
<form action="" name="form2" method="post">
	<input type="hidden" name="userid" value="">
	<input type="hidden" name="account" value="">
</form>

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

