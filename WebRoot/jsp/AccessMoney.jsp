<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.text.NumberFormat"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js"></script>
<script language="JavaScript">
function verify1()
{
	if(form1.accfrom.value==0)
	{
		alert('请选择转出帐户');
		form1.accfrom.focus();
		return false;
	}
	if(form1.accto.value==0)
	{
		alert('请选择转入帐户');
		form1.accto.focus();
		return false;
	}
	if(form1.accfrom.value==form1.accto.value)
	{
		alert('转出转入帐户不能相同');
		form1.accfrom.focus();
		return false;
	}
	if(!IsFloat(form1.jine.value))
	{
		alert('金额必须为浮点数');
		form1.jine.focus();
		return false;
	}
	if(Number(form1.jine.value)<=0)
	{
		alert('金额必须大于0');
		form1.jine.focus();
		return false;
	}
	if(confirm('确认要转款 '+form1.jine.value+' 元吗？'))
		return true;
	else
		return false;
}

</script>
</head>
<%
	List acc = (List)request.getAttribute("acc");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
	AccountForm tmp=null;
	
%>

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
    <td width="379" height="20" class="mainhead">存取款</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
  <tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td height="100" align="center" valign="top">   
          
	<br>

	
	<form name="form1" method="post" action="accessMoney.do" onSubmit="return verify1();">
	
	<input type="hidden" name="kind" value="1">
	<input type="hidden" name="param" value="submit">
	<%
		for(int i=0;i<acc.size();i++)
		{
			tmp=(AccountForm)acc.get(i);
			out.print("<input type='hidden' name='acc"+tmp.getId()+"' value="+tmp.getJine()+">");
		}
	%>
	<TABLE  class="mailtable"> 
          <tr>
            <td  align="right" width=80> 
                <b>*转出帐户:</b>
            </td>
            <td >
            	
            	<SELECT name="accfrom">
            		<option value="0">请选择...</option>
            		<%
            		for(int i=0;i<acc.size();i++)
            		{
            			tmp=(AccountForm)acc.get(i);
            			out.print("<option value="+tmp.getId()+" >"+tmp.getAccName()+" "+tmp.getJine()+"</option>");
            		}
            		%>
            	</SELECT>
            </td>
            <td  align="right" width=80> 
                <b>*金额:</b>
            </td>
            <td >
            	<input type="text" class="none" name="jine" size=20 onKeyUp="InputFloat(this);">
            </td>
            <td  align="right" width=80> 
                <b>*转入帐户:</b>
            </td>
             <td >
            	
            	<SELECT name="accto">
            		<option value="0">请选择...</option>
            		<%
            		for(int i=0;i<acc.size();i++)
            		{
            			tmp=(AccountForm)acc.get(i);
            			out.print("<option value="+tmp.getId()+" >"+tmp.getAccName()+" "+tmp.getJine()+"</option>");
            		}
            		%>
            	</SELECT>
            </td>
            <td><input type="submit" name="submit" value=" 确定 "></td>
            
            </tr>
          	
	</TABLE>
	
	</form>
	
	</td>
        </tr>
        <tr> 
          <td valign="middle" align="center">&nbsp;</td>
        </tr>

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

