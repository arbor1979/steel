<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="java.util.List"%>
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
<form action="accessType.do" method="post" name="form1">
<tr><td >
		<b>大类:</b>
	<select name="classid"  onChange="form1.submit();">
   		<option value="0" <%if(classid.equals("0")) out.print("selected");%>>-全部-</option>
<%
		String[] tmp;
		for(int i=0;i<GoodsClassList.size();i++)
		{
			tmp = (String[])GoodsClassList.get(i);
			
			if (tmp[0].equals(classid))
				out.print("<option value='"+tmp[0]+"' selected>"+tmp[1]+"</option>");
			else
				out.print("<option value='"+tmp[0]+"'>"+tmp[1]+"</option>");
		}
%>
      </select>          
     &nbsp;<b>子类:</b>
     <select name="typeid">
         <option value="0" <%if(typeid.equals("0")) out.print("selected");%>>-全部-</option>
<%
		String[] tmp1;
		for(int i=0;i<GoodsTypeList.size();i++)
		{
			tmp1 = (String[])GoodsTypeList.get(i);
			if (tmp1[0].equals(typeid))
				out.print("<option value='"+tmp1[0]+"' selected>"+tmp1[1]+"</option>");
			else
				out.print("<option value='"+tmp1[0]+"'>"+tmp1[1]+"</option>");
		}
%>
          </select>
	
 </td></tr>
</form>
</table>
</body>
