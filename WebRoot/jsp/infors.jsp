<%@ page contentType="text/html; charset=gb2312" language="java" %>
<%@ page import="mediastore.web.global.*"%>
<%@ page import="mediastore.web.form.ErrorMsgFB"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<META http-equiv=Content-Type content="text/html; charset=gb2312">
<title>提示</title>
</head>
<body background='images/bgall.gif'>
<%
	ErrorMsgFB errfb = (ErrorMsgFB)request.getAttribute(Globals.REQUEST_ERRORS);
	if(errfb.geturl()==null)
		out.println("<script>alert('"+errfb.getSource()+"');</script>");
	else
		out.println("<script>alert('"+errfb.getSource()+"');if(window.parent!=null)window.parent.navigate('"+errfb.geturl()+"');else window.location='"+errfb.geturl()+"';</script>");
%>
</body>
</html>
</script>