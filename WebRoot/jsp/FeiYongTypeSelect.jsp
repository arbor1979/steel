<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="mediastore.web.form.*"%>
<link rel="stylesheet" href="css/style.css" type="text/css">
<style>
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<%
	int classid=oConvert.getInt((String)request.getAttribute("classid"),0);
	int typeid=oConvert.getInt((String)request.getAttribute("typeid"),0);
	List GoodsClassList=(List)request.getAttribute("GoodsClassList");
	List GoodsTypeList=(List)request.getAttribute("GoodsTypeList");
	int kind=oConvert.getInt((String)request.getAttribute("kind"),0);
%>
<body bgcolor="#f7f7ff">
<table width="100%"  valigh="top" cellpadding=0 cellspacing=0 >
<form action="feiYongTypeSelect.do" method="post" name="form1">
<input type="hidden" name="kind" value="<%=kind %>">
<tr><td >
		<b>大类:</b>
	<select name="classid"  onChange="form1.submit();">
   		<option value="0" <%if(classid==0) out.print("selected");%>>-全部-</option>
<%
		FeiYongTypeForm tmp;
		for(int i=0;i<GoodsClassList.size();i++)
		{
			tmp = (FeiYongTypeForm)GoodsClassList.get(i);
			if (tmp.getId()==classid)
				out.print("<option value='"+tmp.getId()+"' selected>"+tmp.getClassname()+"</option>");
			else
				out.print("<option value='"+tmp.getId()+"'>"+tmp.getClassname()+"</option>");
		}
%>
      </select>          
     &nbsp;<b>子类:</b>
     <select name="typeid">
         <option value="0" <%if(typeid==0) out.print("selected");%>>-全部-</option>
<%
		for(int i=0;i<GoodsTypeList.size();i++)
		{
			tmp = (FeiYongTypeForm)GoodsTypeList.get(i);
			if (tmp.getId()==typeid)
				out.print("<option value='"+tmp.getId()+"' selected>"+tmp.getTypename()+"</option>");
			else
				out.print("<option value='"+tmp.getId()+"'>"+tmp.getTypename()+"</option>");
		}
%>
          </select>
	
 </td></tr>
</form>
</table>
</body>
