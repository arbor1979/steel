<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@page import="mediastore.web.form.*"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="java.util.List"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<%
	UserInfoForm fi = (UserInfoForm)request.getAttribute("gif");
	List menu = (List)request.getAttribute("menu");
	List menusub = (List)request.getAttribute("menusub");
	List departList = (List)request.getAttribute("deptList");
%>
<script language="JavaScript">
var MENU_ID_ARRAY = new Array();
<%

	for(int i=0;i<menu.size();i++)
	{
		String m[]=(String [])menu.get(i);
		out.print("MENU_ID_ARRAY["+i+"]='"+m[0]+"';");
	}
%>
function addUserRec()
{
	if (Trim(document.form1.userName.value) == "" || document.form1.userName.value == null) 
	{ 
		alert("请输入登录名！"); 
		document.form1.userName.focus(); 
		return false;
	}
	
	if (Trim(document.form1.password.value).length<6) 
	{ 
		alert("为了帐户的安全建议密码长度不小于6位");
		document.form1.password.focus();
		return false;
	}
	
	if (Trim(document.form1.password.value) != Trim(document.form1.password1.value)) 
	{ 
		alert("两次输入密码不一致！"); 
		document.form1.password.focus(); 
		return false;
	}
	if (Trim(document.form1.deptid.value) == "" || document.form1.deptid.value == null) 
	{ 
		alert("请选择所属分公司！"); 
		document.form1.deptid.focus(); 
		return false;
	}
	var func_id_str="";
  
  	for(j=0;j<MENU_ID_ARRAY.length;j++)
  	{
    	MENU_ID=MENU_ID_ARRAY[j];

    	for(i=0;i<document.all(MENU_ID).length;i++)
    	{
        	el=document.all(MENU_ID).item(i);
        	if(el.checked)
        	{  
        		val=el.value;
           		func_id_str+=val+",";
        	}
    	}
    
    	if(i==0)
    	{
        	el=document.all(MENU_ID);
        	if(el.checked)
        	{  
        		val=el.value;
           		func_id_str+=val+",";
        	}
   		}
  	}
  	if(func_id_str=='')
  	{
  		alert('请至少选择一个操作权限');
  		return false;
  	}
  	form1.role.value = func_id_str;
  	
	return true;		
}
function check_all(menu_all,MENU_ID)
{

 for (i=0;i<document.all(MENU_ID).length;i++)
 {
   if(menu_all.checked)
      document.all(MENU_ID).item(i).checked=true;
   else
      document.all(MENU_ID).item(i).checked=false;
 }

 if(i==0)
 {
   if(menu_all.checked)
      document.all(MENU_ID).checked=true;
   else
      document.all(MENU_ID).checked=false;
 }
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
    <td width="379" height="20" class="mainhead">操作员修改</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td height="300" align="center" valign="top">   
         

    	<form name="form1" method="post" action="userManage.do?param=save" onSubmit="return addUserRec();">
    	<input type="hidden" name="role" value="">
    	<input type="hidden" name="id" value="<%=fi.getId() %>">
    	
    	<font color=#215385><b>操作员信息</b></font>
    	<IMG src="images/line1.gif" border=0 width=900>
    	<br>
    	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	  	<tr>
            <td  align="right" > 
                <b>*名称:</b>
            </td>
            <td >
            	<INPUT class=none  name="userName" type="text" size="15" maxlength="25" value="<%=oConvert.getString(fi.getUserName(),"") %>" <%if(fi.getId()>0) out.print("disabled"); %>>
            </td>
            <td  align="right" > 
                <b>*密码:</b>
            </td>
            <td >
            	<INPUT class=none  name="password" type="password" size="15" maxlength="15" value="<%=oConvert.getString(fi.getPassword(),"") %>">
            	
            </td>
         
            <td  align="right" > 
                <b>*确认密码:</b>
            </td>
            <td >
            	<INPUT class=none  name="password1" type="password" size="15" maxlength="15" value="<%=oConvert.getString(fi.getPassword(),"") %>">
            </td>
           
            <%
            if(ctx.isIfview()) {%>
             <td  align="right" > 
                <b>*分公司:</b>
            </td>
            <td >
            	<select name="deptid" <%if(fi.getId()>0) out.print("disabled"); %>>
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
            <td  align="right" > 
                <b>*状态:</b>
            </td>
            <td >
            	<select name="userStatus">
            	<option value=1 <%=(fi.getUserStatus()==1?"selected":"") %>>启用</option>
            	<option value=0 <%=(fi.getUserStatus()==0?"selected":"") %>>禁用</option>
            </td>
         </tr>
	</TABLE>
	<br>
	<center>操作权限</center>
	<table width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">
	<tr bgcolor="#C2CEDC">
	<%
	
	for(int i=0;i<menu.size();i++)
	{
		String m[]=(String [])menu.get(i);
		out.println("<th><input type='checkbox' name=m'"+m[0]+"' value='"+m[0]+"' onClick=check_all(this,'"+m[0]+"');>"+m[1]+"</th>");
	}
	int index=0;
	%>
	</tr>
	
	<%
	String rol[]=fi.getRole().split(",");
	while(menusub.size()>0)
	{
		out.print("<tr "+((index%2)==1?"bgcolor=#CAE4F4":"")+">");
		for(int i=0;i<menu.size();i++)
		{
			String m[]=(String [])menu.get(i);
			boolean flag=false;
			for(int j=0;j<menusub.size();j++)
			{
				String ms[]=(String[])menusub.get(j);
				if(m[0].equals(ms[3]))
				{
					String s="";
					for(int k=0;k<rol.length;k++)
					{
						if(ms[0].equals(rol[k]))
						{
							s="checked";
							break;
						}
					}
					out.print("<td align=left><input type='checkbox' name='"+m[0]+"' value='"+ms[0]+"' "+s+">"+ms[1]+"</td>");	
					menusub.remove(j);
					flag=true;
					break;
				}
			}
			if(!flag)
				out.println("<td align=center>&nbsp;</td>");
			
		}
		out.print("</tr>");
		index++;
	}
	%>
	</tr>
	</table>
	<p align=center><input name=addrec type=submit value=" 保存 ">&nbsp;
	<input name=back type=button value=" 返回 " onclick="window.history.back(-1);"></p>
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

