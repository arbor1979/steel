<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="mediastore.web.form.GoodsClassInfo"%>
<%@ page import="mediastore.web.form.GoodsTypeInfo"%>
<%@ page import="mediastore.util.oConvert"%>
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
	String classid=oConvert.getString((String)request.getAttribute("classid"),"0");
	String typeid=oConvert.getString((String)request.getAttribute("typeid"),"0");
	List GoodsClassList=(List)request.getAttribute("GoodsClassList");
	List GoodsTypeList=(List)request.getAttribute("GoodsTypeList");
%>
<body bgcolor="#f7f7ff">
<table width="100%"  valigh="top" cellpadding=0 cellspacing=0 >
<form action="ClassAndType.do" method="post" name="form1">
<tr><td >
		<b>大类:</b>
	<select name="classid"  onChange="form1.submit();">
   		<option value="0" <%if(classid.equals("0")) out.print("selected");%>>-全部-</option>
<%
		GoodsClassInfo tmp;
		String str;
		for(int i=0;i<GoodsClassList.size();i++)
		{
			tmp = (GoodsClassInfo)GoodsClassList.get(i);
			if(tmp.getName().length()>5)
				str=tmp.getName().substring(0,5);
			else
				str=tmp.getName();
			if (tmp.getId()==oConvert.getInt(classid,0))
				out.print("<option value='"+tmp.getId()+"' selected>"+str+"</option>");
			else
				out.print("<option value='"+tmp.getId()+"'>"+str+"</option>");
		}
%>
      </select>          
     &nbsp;<b>子类:</b>
     <select name="typeid">
         <option value="0" <%if(typeid.equals("0")) out.print("selected");%>>-全部-</option>
<%
		GoodsTypeInfo tmp1;
		for(int i=0;i<GoodsTypeList.size();i++)
		{
			tmp1 = (GoodsTypeInfo)GoodsTypeList.get(i);
			if(tmp1.getName().length()>7)
				str=tmp1.getName().substring(0,7);
			else
				str=tmp1.getName();
			if (tmp1.getId()==oConvert.getInt(typeid,0))
				out.print("<option value='"+tmp1.getId()+"' selected>"+str+"</option>");
			else
				out.print("<option value='"+tmp1.getId()+"'>"+str+"</option>");
		}
%>
          </select>
	
 </td></tr>
</form>
</table>
</body>
