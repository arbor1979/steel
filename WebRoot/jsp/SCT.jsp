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
	String shengid=oConvert.getString((String)request.getAttribute("shengid"),"0");
	String cityid=oConvert.getString((String)request.getAttribute("cityid"),"0");
	String townid=oConvert.getString((String)request.getAttribute("townid"),"0");
	List sheng=(List)request.getAttribute("sheng");
	List city=(List)request.getAttribute("city");
	List town=(List)request.getAttribute("town");
%>
<body bgcolor="#f7f7ff">
<table width="100%"  valigh="top" cellpadding=0 cellspacing=0 >
<form action="SCT.do" method="post" name="form1">
<tr><td >
		<b>省:</b>
	<select name="shengid"  onChange="form1.townid.value=0;form1.cityid.value=0;form1.submit();">
   		<option value="0" <%if(shengid.equals("0")) out.print("selected");%>>-全部-</option>
<%
		String [] tmpStrBuf;
		for(int i=0;i<sheng.size();i++)
		{
			tmpStrBuf = (String [])sheng.get(i);
			if (tmpStrBuf[0].equals(shengid))
				out.print("<option value='"+tmpStrBuf[0]+"' selected>"+tmpStrBuf[1]+"</option>");
			else
				out.print("<option value='"+tmpStrBuf[0]+"'>"+tmpStrBuf[1]+"</option>");
		}
%>
      </select>          
     &nbsp;<b>市:</b>
     <select name="cityid" onChange="form1.townid.value=0;form1.submit();">
         <option value="0" <%if(cityid.equals("0")) out.print("selected");%>>-全部-</option>
<%
		for(int i=0;i<city.size();i++)
		{
			tmpStrBuf = (String [])city.get(i);
			if (tmpStrBuf[0].equals(cityid))
				out.print("<option value='"+tmpStrBuf[0]+"' selected>"+tmpStrBuf[1]+"</option>");
			else
				out.print("<option value='"+tmpStrBuf[0]+"'>"+tmpStrBuf[1]+"</option>");
		}
%>
          </select>
	&nbsp;<b>县:</b>
     <select name="townid">
         <option value="0" <%if(townid.equals("0")) out.print("selected");%>>-全部-</option>
<%
		for(int i=0;i<town.size();i++)
		{
			tmpStrBuf = (String [])town.get(i);
			if (tmpStrBuf[0].equals(townid))
				out.print("<option value='"+tmpStrBuf[0]+"' selected>"+tmpStrBuf[1]+"</option>");
			else
				out.print("<option value='"+tmpStrBuf[0]+"'>"+tmpStrBuf[1]+"</option>");
		}
%>
          </select>
 </td></tr>
</form>
</table>
</body>
