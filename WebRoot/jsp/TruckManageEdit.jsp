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
	if (Trim(document.form1.carno.value) == "" || document.form1.carno.value == null) 
	{ 
		alert("请输入车牌号！"); 
		document.form1.carno.focus(); 
		return false;
	}
	if (Trim(document.form1.driver.value) == "" || document.form1.driver.value == null) 
	{ 
		alert("请输入车主姓名！"); 
		document.form1.driver.focus(); 
		return false;
	}
	if (Trim(document.form1.deptid.value) == "" || document.form1.mobile.value == null) 
	{ 
		alert("请选择分公司！"); 
		document.form1.deptid.focus(); 
		return false;
	}
	return true;
}

</script>
</head>
<body background='images/bgall.gif' onload="form1.carno.focus();">
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
    <td width="379" height="20" class="mainhead">运输车辆</td>
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
		TruckInfoForm fi = (TruckInfoForm)request.getAttribute("fi");
		java.util.List departList = (java.util.List)request.getAttribute("departList");
%>

    	<form name="form1" method="post" action="truckManage.do?param=save" onSubmit="return addUserRec();">
    	<font color=#215385><b>编辑车辆资料</b></font>
    	<IMG src="images/line1.gif" border=0 width=900>
    	<br>
    	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	  	<tr>
            <td  align="right" width=15%> 
                <b>*车牌号:</b>
            </td>
            <td width=35%>
            	<INPUT class=none  name="carno" type="text" size="30" maxlength="25" value="<%=oConvert.getString(fi.getCarno(),"") %>">
            </td>
            <td  align="right" width=15%> 
                <b>*车主:</b>
            </td>
            <td width=35%>
            	<INPUT class=none  name="driver" type="text" size="30" maxlength="25" value="<%=oConvert.getString(fi.getDriver(),"") %>">
            	
            </td>
         </tr>
         <tr>
            <td  align="right" > 
                <b>身份证:</b>
            </td>
            <td >
            	<INPUT class=none  name="idcard" type="text" size="30" maxlength="25" value="<%=oConvert.getString(fi.getIdcard(),"") %>">
            </td>
            <td  align="right" > 
                <b>地址:</b>
            </td>
            <td >
            	<INPUT class=none  name="address" type="text" size="30" maxlength="25" value="<%=oConvert.getString(fi.getAddress(),"") %>">
            </td>
         </tr>
         <tr>
            <td  align="right" > 
                <b>电话:</b>
            </td>
            <td >
            	<INPUT class=none  name="mobile" type="text" size="30" maxlength="25" value="<%=oConvert.getString(fi.getMobile(),"") %>">
            </td>
            <td  align="right" > 
                <b>车型:</b>
            </td>
            <td >
            	<INPUT class=none  name="kind" type="text" size="30" maxlength="25" value="<%=oConvert.getString(fi.getKind(),"") %>">
            </td>
         </tr>
         <tr>
            <td  align="right" > 
                <b>车架号:</b>
            </td>
            <td >
            	<INPUT class=none  name="chejia" type="text" size="30" maxlength="25" value="<%=oConvert.getString(fi.getChejia(),"") %>">
            </td>
            <td  align="right" > 
                <b>发动机号:</b>
            </td>
            <td >
            	<INPUT class=none  name="fadongji" type="text" size="30" maxlength="25" value="<%=oConvert.getString(fi.getFadongji(),"") %>">
            </td>
         </tr>
         
          <tr>
            <td  align="right" > 
                <b>核定载重:</b>
            </td>
            <td >
            	<INPUT class=none  name="weight" type="text" size="30" maxlength="10" value="<%=fi.getWeight() %>">
            </td>
            <%
            if(ctx.getDeptid()==0) {%>
           
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
    		
            <%}else{%>
            <input type="hidden" name="deptid" value="<%=ctx.getDeptid() %>">
            <%} %>
          </tr>
          <input type="hidden" name="id" value=<%=fi.getId() %>>
	</TABLE>
	<p align=center><input name=addrec type=submit value=" 保存 ">&nbsp;<input name=back type=button value=" 返回 " onclick="window.navigate('truckManage.do');"></p>
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

