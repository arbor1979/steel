<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@page import="mediastore.web.form.UserInfoForm"%>
<%@ page import="mediastore.util.oConvert"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<%
	UserInfoForm fi = (UserInfoForm)request.getAttribute("gif");
	String suc = oConvert.getString((String)request.getAttribute("suc"),"");
%>
<script language="JavaScript">
function addUserRec()
{
	if (Trim(document.form1.newpwd.value) == "" || document.form1.newpwd.value == null) 
	{ 
		alert("请输入新密码！"); 
		document.form1.newpwd.focus(); 
		return false;
	}
	if (Trim(document.form1.newpwd.value).length<6) 
	{ 
		alert("为了帐户的安全建议密码长度不小于6位");
		document.form1.newpwd.focus();
		return false;
	}
	if (Trim(document.form1.newpwd.value) != Trim(document.form1.newpwd1.value)) 
	{ 
		alert("两次输入密码不一致！"); 
		document.form1.newpwd.focus(); 
		return false;
	}
	
  	return true;
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
    <td width="379" height="20" class="mainhead">更改密码</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <%if(suc.equals("1")){%>
    <br><br>
<h3 align=center>密码更改成功！</h3><p align=center><input type="button" value="返回" onclick="window.navigate('changePwd.do');"></p><br>	
	<%}else{ %>
    
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td height="300" align="center" valign="top">   
         

    	<form name="form1" method="post" action="changePwd.do?param=change" onSubmit="return addUserRec();">
    	<input type="hidden" name="id" value="<%=fi.getId() %>">    	
    	<br>
    	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	  	<tr>
            <td  align="right" width=40%> 
                <b>*登陆名:</b>
            </td>
            <td >
            	<INPUT   name="userName" type="text" size="20" maxlength="25" value="<%=oConvert.getString(fi.getUserName(),"") %>" readonly>
            </td>
        </tr>
        <tr>
            <td  align="right" > 
                <b>旧密码:</b>
            </td>
            <td >
            	<INPUT   name="password" type="password" size="20" maxlength="15" value="">
            	
            </td>
        </tr>
        <tr>
            <td  align="right" > 
                <b>*新密码:</b>
            </td>
            <td >
            	<INPUT  name="newpwd" type="password" size="20" maxlength="15" value="">
            	
            </td>
        </tr>
        <tr>
            <td  align="right" > 
                <b>确认新密码:</b>
            </td>
            <td >
            	<INPUT   name="newpwd1" type="password" size="20" maxlength="15" value="">
            </td>
            
         </tr>
	</TABLE>
	<p align=center><input name=addrec type=submit value=" 保存 "></p>
	</form>
    </table>  
    <%} %>
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

