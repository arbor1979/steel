

<%@ page contentType="text/html; charset=gb2312" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<HTML>
<HEAD>
<TITLE>天天阳光钢材进销存系统</TITLE>

<META http-equiv=Content-Type content="text/html; charset=gb2312">

<link rel="stylesheet" href="css/style.css" type="text/css">
<LINK href="images/ttygw.ico" rel="Shortcut Icon">
<LINK href="images/ttygw.ico" rel=Bookmark>

</HEAD>
<%
String machinecode=(String)request.getAttribute("machinecode"); 
String errmsg=(String)request.getAttribute("errmsg"); 
%>
<BODY background='images/bgall.gif' onLoad="form1.regcode.focus();" >
<table width=100% height=100% valign="center">
<tr><td>
<FORM name=form1 action="logout.do" method="post">
<table align="center"  background="images/loginbg.gif" border="0" cellspacing="6">
  <tr> 
    <td align="center" colSpan=3 bgcolor="#F2F2F2"><img src="images/softreg.gif" width="482" border="0" usemap="#Map">
	  <br>
	  <font color=red><%=errmsg %>，请将下面机器码发送给我们，您将会获得正确的注册码。</font><br>
		<br>
	    机器码：
	    <INPUT type="text" class=bgg size=20 name='machinecode' value="<%=machinecode %>" readonly>
	      <BR>
	      <BR>
	    注册码：
	    <INPUT class=bgg type=text size=20 name='regcode' onfocus="this.select();">
	    <BR>
	    <BR>
		<br>
	    <INPUT name=login type=submit class="bgg" value=" 注册 ">
&nbsp;&nbsp;
	    <INPUT name=reset type=button class="bgg" value=" 关闭 " onclick="window.close();">
	    <INPUT type=hidden name='param' value="reg">
	    <BR>
	    <BR>
	    <BR>
	  <div align="right">技术服务电话：0371-66871976&nbsp;&nbsp;</div><br>
  </td>
</TR>
</table>

 </FORM>
 </td></tr>
</table>

<map name="Map">
<area shape="rect" coords="4,5,478,80" href="http://www.ttygw.net/" target="_blank">
</map></BODY>
</HTML>
