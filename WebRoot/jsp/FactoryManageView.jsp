<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@page import="mediastore.web.form.FactoryInfoForm"%>
<%@ page import="mediastore.util.oConvert"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js"></script>
<script language="javascript">
function disableall()
{
	SCT.form1.shengid.disabled=true;
	SCT.form1.cityid.disabled=true;
	SCT.form1.townid.disabled=true;
}
</script>

</head>
<body background='images/bgall.gif' onload="disableall();">

          
<%
         FactoryInfoForm fi = (FactoryInfoForm)request.getAttribute("fi");
 %>

    	<form name="form1" method="post" action="factoryManage.do?param=save" onSubmit="return addUserRec();">
    	<center><font color=#215385><b>供应商或客户信息</b></font></center>
    	<IMG src="images/line1.gif" border=0 width=100%>
    	<br>
    	<TABLE  class="mailtable"> 
	  	<tr>
            <td  align="right" width=15%> 
                <b>企业名称:</b>
            </td>
            <td width=35%>
            	<%=oConvert.getString(fi.getName(),"") %>
            </td>
            <td  align="right" width=15%> 
                <b>详细地址:</b>
            </td>
            <td width=35%>
            	<%=oConvert.getString(fi.getAddress(),"") %>
            	
            </td>
         </tr>
         <tr>
            <td  align="right" > 
                <b>固定电话:</b>
            </td>
            <td >
            	<%=oConvert.getString(fi.getTel(),"") %>
            </td>
            <td  align="right" > 
                <b>传真号码:</b>
            </td>
            <td >
            	<%=oConvert.getString(fi.getFax(),"") %>
            </td>
         </tr>
         <tr>
            <td  align="right" > 
                <b>法人代表:</b>
            </td>
            <td >
            	<%=oConvert.getString(fi.getFaren(),"") %>
            </td>
            <td  align="right" > 
                <b>联系人:</b>
            </td>
            <td >
            	<%=oConvert.getString(fi.getLinkman(),"") %>
            </td>
         </tr>
         <tr>
            <td  align="right" > 
                <b>联系人手机:</b>
            </td>
            <td >
            	<%=oConvert.getString(fi.getMobile(),"") %>
            </td>
            <td  align="right" > 
                <b>隶属业务员:</b>
            </td>
            <td >
            	<%=oConvert.getString(fi.getYewuName(),"") %>
            </td>
         </tr>
         
          <tr>
            <td  align="right" > 
                <b>开户银行:</b>
            </td>
            <td >
            	<%=oConvert.getString(fi.getBank(),"") %>
            <td  align="right" > 
                <b>银行帐号:</b>
            </td>
            <td >
            	<%=oConvert.getString(fi.getAccount(),"") %>
            </td>
          </tr>
          <tr>
            <td  align="right" > 
                <b>纳税号:</b>
            </td>
            <td >
            	<%=oConvert.getString(fi.getTaxno(),"") %>
            </td>
            <td  align="right" > 
                <b>*企业类型:</b>
            </td>
            <td >
            	<input type="checkbox" name="GongYing" value="1" <%=((fi.getKind().substring(0,1).equals("1"))?"checked":"") %> disabled>供应商&nbsp;
            	<input type="checkbox" name="JingXiao" value="1" <%=((fi.getKind().substring(1,2).equals("1"))?"checked":"") %> disabled>经销商&nbsp;
            	<input type="checkbox" name="KeHu" value="1" <%=((fi.getKind().substring(2,3).equals("1"))?"checked":"") %> disabled>客户

            </td>
            
          </tr>
          <tr>
            <td  align="right" > 
                <b>*所属区域:</b>
            </td>
            <td colspan=3>
            	<iframe name="SCT" id="SCT" src="SCT.do?townid=<%=fi.getTownid() %>" width="500" height="25" frameborder=0 scrolling=no readonly></iframe>	
            </td>
          </tr>
          <input type="hidden" name="kind">
          <input type="hidden" name="townid">
          <input type="hidden" name="id" value=<%=fi.getId() %>>
	</TABLE>
	<p align=center><input name=back type=button value=" 关闭 " onclick="window.close();"></p>
	</form>
    

</body>
</html>

