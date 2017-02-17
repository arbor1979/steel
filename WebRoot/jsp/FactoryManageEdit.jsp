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
		alert("请输入企业名称！"); 
		document.form1.name.focus(); 
		return false;
	}
	if (Trim(document.form1.tel.value) == "" || document.form1.tel.value == null) 
	{ 
		alert("请输入联系电话！"); 
		document.form1.tel.focus(); 
		return false;
	}
	
	if (Trim(SCT.form1.townid.value) == "0" ) 
	{ 
		alert("请选择省市县！"); 
		SCT.form1.shengid.focus(); 
		return false;
	}
	if (!form1.GongYing.checked && !form1.JingXiao.checked &&!form1.KeHu.checked &&!form1.QiTa.checked) 
	{ 
		alert("请至少选择一个企业类型！"); 
		document.form1.GongYing.focus(); 
		return false;
	}
	if (form1.deptid.value == "" ) 
	{ 
		alert("请选择分公司！"); 
		form1.deptid.focus(); 
		return false;
	}
	var kind;;
	if(form1.GongYing.checked)
		kind='1';
	else
		kind='0';
	if(form1.JingXiao.checked)
		kind=kind+'1';
	else
		kind=kind+'0';
	if(form1.KeHu.checked)
		kind=kind+'1';
	else
		kind=kind+'0';
	if(form1.QiTa.checked)
		kind=kind+'1';
	else
		kind=kind+'0';
	form1.kind.value=kind;	
	form1.townid.value=SCT.form1.townid.value;
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
    <td width="379" height="20" class="mainhead">往来单位编辑</td>
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
                   	FactoryInfoForm fi = (FactoryInfoForm)request.getAttribute("fi");
                   	java.util.List YeWuList = (java.util.List)request.getAttribute("YeWuList");
                 	java.util.List departList = (java.util.List)request.getAttribute("departList");
 %>

    	<form name="form1" method="post" action="factoryManage.do?param=save" onSubmit="return addUserRec();">
    	<font color=#215385><b>往来单位信息</b></font>
    	<IMG src="images/line1.gif" border=0 width=900>
    	<br>
    	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	  	<tr>
            <td  align="right" width=15%> 
                <b>*企业名称:</b>
            </td>
            <td width=35%>
            	<INPUT class=none  name="name" type="text" size="30" maxlength="25" value="<%=oConvert.getString(fi.getName(),"") %>">
            </td>
            <td  align="right" width=15%> 
                <b>详细地址:</b>
            </td>
            <td width=35%>
            	<INPUT class=none  name="address" type="text" size="30" maxlength="25" value="<%=oConvert.getString(fi.getAddress(),"") %>">
            	
            </td>
         </tr>
         <tr>
            <td  align="right" > 
                <b>*固定电话:</b>
            </td>
            <td >
            	<INPUT class=none  name="tel" type="text" size="30" maxlength="15" value="<%=oConvert.getString(fi.getTel(),"") %>">
            </td>
            <td  align="right" > 
                <b>传真号码:</b>
            </td>
            <td >
            	<INPUT class=none  name="fax" type="text" size="30" maxlength="15" value="<%=oConvert.getString(fi.getFax(),"") %>">
            </td>
         </tr>
         <tr>
            <td  align="right" > 
                <b>法人代表:</b>
            </td>
            <td >
            	<INPUT class=none  name="faren" type="text" size="30" maxlength="10" value="<%=oConvert.getString(fi.getFaren(),"") %>">
            </td>
            <td  align="right" > 
                <b>联系人:</b>
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
            <td  align="right" > 
                <b>隶属业务员:</b>
            </td>
            <td >
            	<SELECT name="yewuyuan">
            		<option value="0" <%if(oConvert.getString(fi.getYewuyuan(),"0").equals("0")) out.print("selected");%>>请选择...</option>
            		<%
            		            	YeWuInfoForm tmpYewu=null;
            		            	for(int i=0; i<YeWuList.size(); i++)
            		            	{
            		            		tmpYewu = (YeWuInfoForm)YeWuList.get(i);
            		            		
            		            		if (tmpYewu.getId()==oConvert.getInt(fi.getYewuyuan(),0))
            						out.print("<option value='"+tmpYewu.getId()+"' selected>"+tmpYewu.getName()+"</option>");
            					else
            						out.print("<option value='"+tmpYewu.getId()+"'>"+tmpYewu.getName()+"</option>");
            				}
            		%>
            	</SELECT>
            </td>
         </tr>
         
          <tr>
            <td  align="right" > 
                <b>开户银行:</b>
            </td>
            <td >
            	<INPUT class=none  name="bank" type="text" size="30" maxlength="50" value="<%=oConvert.getString(fi.getBank(),"") %>">
            </td>
            <td  align="right" > 
                <b>银行帐号:</b>
            </td>
            <td >
            	<INPUT class=none  name="account" type="text" size="30" maxlength="50" value="<%=oConvert.getString(fi.getAccount(),"") %>">
            </td>
          </tr>
          <tr>
            <td  align="right" > 
                <b>纳税号:</b>
            </td>
            <td >
            	<INPUT class=none  name="taxno" type="text" size="30" maxlength="50" value="<%=oConvert.getString(fi.getTaxno(),"") %>">
            </td>
            <td  align="right" > 
                <b>*企业类型:</b>
            </td>
            <td >
            	<input type="checkbox" name="GongYing" value="1" <%=((fi.getKind().substring(0,1).equals("1"))?"checked":"") %>>供应商&nbsp;
            	<input type="checkbox" name="JingXiao" value="1" <%=((fi.getKind().substring(1,2).equals("1"))?"checked":"") %>>经销商&nbsp;
            	<input type="checkbox" name="KeHu" value="1" <%=((fi.getKind().substring(2,3).equals("1"))?"checked":"") %>>客户&nbsp;
				<input type="checkbox" name="QiTa" value="1" <%=((fi.getKind().substring(3,4).equals("1"))?"checked":"") %>>其他
            </td>
            
          </tr>
          <tr>
            <td  align="right" > 
                <b>*所属区域:</b>
            </td>
            <td colspan=2>
            	<iframe name="SCT" id="SCT" src="SCT.do?townid=<%=fi.getTownid() %>" width="500" height="25" frameborder=0 scrolling=no ></iframe>	
            </td>
            <%
            if(ctx.getDeptid()==0) {%>
             <td  align="left" > 
                <b>*分公司:</b>
            	<select name="deptid" <%if(fi.getId()>0) out.print("disabled"); %> >
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
          <input type="hidden" name="kind">
          <input type="hidden" name="townid">
          <input type="hidden" name="id" value=<%=fi.getId() %>>
	</TABLE>
	<p align=center><input name=addrec type=submit value=" 保存 ">&nbsp;<input name=back type=button value=" 返回 " onclick="window.navigate('factoryManage.do');"></p>
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

