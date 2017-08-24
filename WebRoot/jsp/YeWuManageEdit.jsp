<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@page import="mediastore.web.form.*"%>
<%@ page import="mediastore.util.oConvert"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<script language="JavaScript">

function addUserRec()
{
	if (Trim(document.form1.name.value) == "" || document.form1.name.value == null) 
	{ 
		alert("请输入导购员姓名！"); 
		document.form1.name.focus(); 
		return false;
	}
	if (Trim(document.form1.mobile.value) == "" || document.form1.mobile.value == null) 
	{ 
		alert("请输入导购员电话！"); 
		document.form1.mobile.focus(); 
		return false;
	}
	if (Trim(document.form1.address.value) == "" || document.form1.address.value == null) 
	{ 
		alert("请输入导购员地址！"); 
		document.form1.address.focus(); 
		return false;
	}
	if (Trim(document.form1.deptid.value) == "" || document.form1.deptid.value == null) 
	{ 
		alert("请选择所属分公司！"); 
		document.form1.deptid.focus(); 
		return false;
	}
	return true;
}

</script>
</head>
<body background='images/bgall.gif' onload="form1.name.focus();">
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
    <td width="379" height="20" class="mainhead">业务员资料修改</td>
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
		YeWuInfoForm fi = (YeWuInfoForm)request.getAttribute("fi");
    	java.util.List departList = (java.util.List)request.getAttribute("deptList");
 %>

    	<form name="form1" method="post" action="yeWuManage.do?param=save" onSubmit="return addUserRec();">
    	<font color=#215385><b>编辑导购员档案</b></font>
    	<IMG src="images/line1.gif" border=0 width=900>
    	<br>
    	<TABLE  class="mailtable"> 
	  	<tr>
            <td  align="right" width=15%> 
                <b>*姓名:</b>
            </td>
            <td width=35%>
            	<INPUT class=none  name="name" type="text" size="30" maxlength="25" value="<%=oConvert.getString(fi.getName(),"") %>">
            </td>
            <td  align="right" width=15%> 
                <b>*地址:</b>
            </td>
            <td width=35%>
            	<INPUT class=none  name="address" type="text" size="30" maxlength="25" value="<%=oConvert.getString(fi.getAddress(),"") %>">
            	
            </td>
         </tr>
         <tr>
            <td  align="right" > 
                <b>*电话:</b>
            </td>
            <td >
            	<INPUT class=none  name="mobile" type="text" size="30" maxlength="15" value="<%=oConvert.getString(fi.getMobile(),"") %>">
            </td>
            <td  align="right" > 
                <b>身份证:</b>
            </td>
            <td >
            	<INPUT class=none  name="idcard" type="text" size="30" maxlength="15" value="<%=oConvert.getString(fi.getIdcard(),"") %>">
            </td>
         </tr>
         <%
            if(ctx.getDeptid()==0) {%>
            <tr>
             <td  align="right" > 
                <b>*分公司:</b>
            </td>
            <td >
            	<select name="deptid">
    			<option value="">请选择...</option>
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
    		</tr>
            <%}else{%>
            <input type="hidden" name="deptid" value="<%=ctx.getDeptid() %>">
            <%} %>
          <input type="hidden" name="kind">
          <input type="hidden" name="townid">
          <input type="hidden" name="id" value=<%=fi.getId() %>>
	</TABLE>
	<p align=center><input name=addrec type=submit value=" 保存 ">&nbsp;<input name=back type=button value=" 返回 " onclick="window.location='yeWuManage.do';"></p>
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

