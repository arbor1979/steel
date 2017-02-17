

<%@ page contentType="text/html; charset=gb2312" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<HTML>
<HEAD>
<TITLE>天天阳光钢材进销存系统</TITLE>

<META http-equiv=Content-Type content="text/html; charset=gb2312">

<link rel="stylesheet" href="css/style.css" type="text/css">
<LINK href="images/ttygw.ico" rel="Shortcut Icon">
<LINK href="images/ttygw.ico" rel=Bookmark>
<script language="javascript" src="js/jquery.js"></script>
<SCRIPT language=javascript>
function Login()
{
	
	$.ajax({ 
		  type:'POST', 
		  url:"login.do", 
		  data:{userName:encodeURIComponent(document.form1.userName.value),password:document.form1.password.value},
		  dataType: 'text', 
		  cache:false,
		  async: true,
		  success:function(data) 
		  { 
		  	
			  if(data=='ok')
			  {
			  	if(window.name=='main' || 1==1)
			  	{
			  		location.href='login.do?action=suc';
			  		
			  	}
				else
			  		openNewWindow('login.do?action=suc');
			  }
			  else if(data.indexOf('注册码不正确')>=0)
	  	  	  	document.write(data);
			  else
				alert(data);
				
			   
			  	
		  },
		  error:function(XmlHttpRequest,textStatus, errorThrown)
	  	  {
			 alert('出错：'+errorThrown);
	  	  }
	});

	return false;
}
</script>
</HEAD>

<BODY background='images/bgall.gif' onLoad="form1.userName.focus();" >
<table width=100% height=100% valign="center">
<tr><td>
<FORM name=form1 action="" method="post" onSubmit="return Login();">
<table align="center"  background="images/loginbg.gif" border="0" cellspacing="6">
  <tr> 
    <td align="center" colSpan=3 bgcolor="#F2F2F2"><img src="images/topnew.gif" width="482" border="0" usemap="#Map">
	  <BR>
	  <iframe name="title" width=100% height=30 marginwidth=0 marginheight=0 frameborder=0 src="html\DeptName.htm"></iframe>
		<br>
	    用 户 名：
	    <INPUT type="text" class=bgg size=20 name='userName'>
	      <BR>
	      <BR>
	    用户口令：
	    <INPUT class=bgg type=password size=20 name='password'>
	    <BR>
	    <BR>
		<br>
	    <INPUT name=login type=submit class="bgg" value=用户登录>
&nbsp;&nbsp;
	    <INPUT name=reset type=reset class="bgg" value=重新填写>
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
