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
function Trim(str){
 if(str.charAt(0) == " "){
  str = str.slice(1);
  str = Trim(str); 
 }
 return str;
}

function addUserRec()
{
	if (Trim(document.form1.name.value) == "" || document.form1.name.value == null) 
	{ 
		alert("请输入仓库名称！"); 
		document.form1.name.focus(); 
		return false;
	}
	if(document.form1.name.value.length>5)
	{
		alert("仓库名称不能超过5个字！"); 
		document.form1.name.focus(); 
		return false;
	}
	if (Trim(document.form1.address.value) == "" || document.form1.address.value == null) 
	{ 
		alert("请输入仓库地址！"); 
		document.form1.address.focus(); 
		return false;
	}
	if (Trim(document.form1.tel.value) == "" || document.form1.tel.value == null) 
	{ 
		alert("请输入电话！"); 
		document.form1.tel.focus(); 
		return false;
	}
	if (Trim(document.form1.linkman.value) == "" || document.form1.linkman.value == null) 
	{ 
		alert("请输入联系人姓名！"); 
		document.form1.linkman.focus(); 
		return false;
	}
	if (Trim(document.form1.deptid.value) == "" || document.form1.deptid.value == null) 
	{ 
		alert("请选择分公司！"); 
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
    StoreInfoForm fi = (StoreInfoForm)request.getAttribute("dif");
	java.util.List departList = (java.util.List)request.getAttribute("departList");
             %>

    	<form name="form1" method="post" action="storeManage.do?param=save" onSubmit="return addUserRec();">
    	<font color=#215385><b>编辑仓库信息</b></font>
    	<IMG src="images/line1.gif" border=0 width=900>
    	<br>
    	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	  	<tr>
            <td  align="right" width=15%> 
                <b>*名称:</b>
            </td>
            <td width=35%>
            	<INPUT class=none  name="name" type="text" size="30" maxlength="10" value="<%=oConvert.getString(fi.getName(),"") %>">(不超过5个字)
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
            	<INPUT class=none  name="tel" type="text" size="30" maxlength="15" value="<%=oConvert.getString(fi.getTel(),"") %>">
            </td>
            <td  align="right" > 
                <b>*联系人:</b>
            </td>
            <td >
            	<INPUT class=none  name="linkman" type="text" size="30" maxlength="10" value="<%=oConvert.getString(fi.getLinkman(),"") %>">
            </td>
         </tr>
         <tr>
            <td  align="right" > 
                <b>联系人手机:</b>
            </td>
            <td >
            	<INPUT class=none  name="mobile" type="text" size="30" maxlength="15" value="<%=oConvert.getString(fi.getMobile(),"") %>">
            </td>
            <%
            if(ctx.getDeptid()==0) {%>
             <td  align="right" > 
                <b>*分公司:</b>
            </td>
            <td >
            	<select name="deptid" <%if(fi.getId()>0) out.print("disabled"); %> <%=(fi.getId()>0?"disabled":"") %>>
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
            <%}else{%>
            <input type="hidden" name="deptid" value="<%=ctx.getDeptid() %>">
            <%} %>
         </tr>
          <input type="hidden" name="id" value=<%=fi.getId() %>>
	</TABLE>
	<p align=center><input name=addrec type=submit value=" 保存 ">&nbsp;<input name=back type=button value=" 返回 " onclick="window.history.back(-1);"></p>
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

