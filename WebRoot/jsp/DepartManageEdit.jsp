<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@page import="mediastore.web.form.DepartInfoForm"%>
<%@ page import="mediastore.util.oConvert"%>
<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
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
		alert("������ֹ�˾���ƣ�"); 
		document.form1.name.focus(); 
		return false;
	}
	if (Trim(document.form1.shortname.value) == "" || document.form1.shortname.value == null) 
	{ 
		alert("������ֹ�˾��ƣ�"); 
		document.form1.shortname.focus(); 
		return false;
	}
	if(Trim(document.form1.shortname.value).length>5)
	{ 
		alert("��˾��Ʋ��ܳ���5���֣�"); 
		document.form1.shortname.focus(); 
		return false;
	}
	if (Trim(document.form1.address.value) == "" || document.form1.address.value == null) 
	{ 
		alert("������ֹ�˾��ַ��"); 
		document.form1.address.focus(); 
		return false;
	}
	if (Trim(document.form1.tel.value) == "" || document.form1.tel.value == null) 
	{ 
		alert("������绰��"); 
		document.form1.tel.focus(); 
		return false;
	}
	if (Trim(document.form1.linkman.value) == "" || document.form1.linkman.value == null) 
	{ 
		alert("�����븺����������"); 
		document.form1.linkman.focus(); 
		return false;
	}
	if (Trim(document.form1.mobile.value) == "" || document.form1.mobile.value == null) 
	{ 
		alert("�����븺�����ֻ���"); 
		document.form1.mobile.focus(); 
		return false;
	}
	if (Trim(SCT.form1.townid.value) == "0" ) 
	{ 
		alert("��ѡ��ʡ���أ�"); 
		SCT.form1.shengid.focus(); 
		return false;
	}
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
    <td width="379" height="20" class="mainhead">��˾��Ϣ</td>
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
   	DepartInfoForm fi = (DepartInfoForm)request.getAttribute("fi");
%>

    	<form name="form1" method="post" action="departManage.do?param=save" onSubmit="return addUserRec();">
    	<font color=#215385><b>�༭��˾��Ϣ</b></font>
    	<IMG src="images/line1.gif" border=0 width=900>
    	<br>
    	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	  	<tr>
            <td  align="right" width=15%> 
                <b>*��˾����:</b>
            </td>
            <td width=35%>
            	<INPUT class=none  name="name" type="text" size="30" maxlength="25" value="<%=oConvert.getString(fi.getName(),"") %>">
            </td>
            <td  align="right" > 
                <b>*��˾���:</b>
            </td>
            <td >
            	<INPUT class=none  name="shortname" type="text" size="30" maxlength="10" value="<%=oConvert.getString(fi.getShortname(),"") %>">(������5����)
            </td>
         </tr>
         <tr>
         	<td  align="right" width=15%> 
                <b>*��ϸ��ַ:</b>
            </td>
            <td width=35%>
            	<INPUT class=none  name="address" type="text" size="30" maxlength="25" value="<%=oConvert.getString(fi.getAddress(),"") %>">
            	
            </td>
            <td  align="right" > 
                <b>*�̶��绰:</b>
            </td>
            <td >
            	<INPUT class=none  name="tel" type="text" size="30" maxlength="15" value="<%=oConvert.getString(fi.getTel(),"") %>">
            </td>
            
         </tr>
         <tr>
            <td  align="right" > 
                <b>*������:</b>
            </td>
            <td >
            	<INPUT class=none  name="linkman" type="text" size="30" maxlength="10" value="<%=oConvert.getString(fi.getLinkman(),"") %>">
            </td>
            <td  align="right" > 
                <b>*�������ֻ�:</b>
            </td>
            <td >
            	<INPUT class=none  name="mobile" type="text" size="30" maxlength="15" value="<%=oConvert.getString(fi.getMobile(),"") %>">
            </td>
         </tr>
         
          <tr>
            <td  align="right" > 
                <b>��������:</b>
            </td>
            <td >
            	<INPUT class=none  name="bank" type="text" size="30" maxlength="50" value="<%=oConvert.getString(fi.getBank(),"") %>">
            </td>
            <td  align="right" > 
                <b>�����ʺ�:</b>
            </td>
            <td >
            	<INPUT class=none  name="account" type="text" size="30" maxlength="50" value="<%=oConvert.getString(fi.getAccount(),"") %>">
            </td>
          </tr>
          <tr>
            <td  align="right" > 
                <b>��˰��:</b>
            </td>
            <td>
            	<INPUT class=none  name="taxno" type="text" size="30" maxlength="50" value="<%=oConvert.getString(fi.getTaxno(),"") %>">
            </td>
            <td  align="right" > 
                <b>�������:</b>
            </td>
            <td >
            	<INPUT class=none  name="fax" type="text" size="30" maxlength="15" value="<%=oConvert.getString(fi.getFax(),"") %>">
            </td>
            
          </tr>
          <tr>
            <td  align="right" > 
                <b>*��������:</b>
            </td>
            <td colspan=3>
            	<iframe name="SCT" id="SCT" src="SCT.do?townid=<%=fi.getTownid() %>" width="500" height="25" frameborder=0 scrolling=no ></iframe>	
            </td>
          </tr>
          <tr>
            <td  align="right" > 
                <b>��ӡ����:</b>
            </td>
            <td colspan=3>
            	<input name="pagesize" type="radio" value="0" <%=(fi.getPagesize()==0?"checked":"") %>>���д�ӡֽ(241mm*130mm) 
            	<input name="pagesize" type="radio" value="1" <%=(fi.getPagesize()==1?"checked":"") %>>խ�д�ӡֽ(195mm*95mm)
            	&nbsp;&nbsp;
            	<input name="jiagong" type="checkbox" value="1" <%=(fi.getJiagong()==1?"checked":"") %>>�Ƿ��ӡ�ӹ���
            	&nbsp;&nbsp;
            	<input name="ifyixiangjine" type="checkbox" value="1" <%=(fi.isIfyixiangjine()?"checked":"") %>>�����Ƿ���ʾ���۽��
            </td>
          </tr>
          <tr>
            <td  align="right" > 
                <b></b>
            </td>
            <td colspan=3>
            	���۵�β����ʾ���֣�<input name="tip" type="text" class=none size=50 maxlength=25 value=<%=fi.getTip()%>> 
            	
            </td>
          </tr>
          <tr>
            <td  align="right" > 
                <b>�˷Ѻ�������:</b>
            </td>
            <td colspan=3>
            	<input name="ifyunfei" type="checkbox" value="true" <%=(fi.isIfyunfei()?"checked":"") %>>�������˷Ѽ���ɱ�
            </td>
          </tr>
          <tr>
            <td  align="right" > 
                <b>���ݱ��������:</b>
            </td>
            <td colspan=3>
            	<input name="beifen" type="button" value=" ���� " onclick="form2.param.value='bakup';form2.submit();"> &nbsp;
            	<input name="clearData" type="button" value="�������" onclick="if(confirm('�����������ǰ���ȱ��ݣ��Ƿ�ȷ�������')){form2.param.value='clear';form2.submit();}">
            	(������г���⡢��漰�������ݣ������������ϼ���Ʒ��)
            </td>
            </tr>
            <tr>
            <td></td>
            <td colspan=3>
            	��ʼ 
		<input type=text name='importtime1' size="18" class="none" value="">
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="���������˵�"  onClick="Calendar_importtime1();">&nbsp;
		&nbsp;��ֹ 
		<input type=text name='importtime2'  size="18" class="none" value=""> 
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="���������˵�"  onClick="Calendar_importtime2();">
            	&nbsp;<input name="clearRecord" type="button" value="��������" 
            	onclick="if(confirm('���������¼ǰ���ȱ��ݣ��Ƿ�ȷ�������')){form2.param.value='delete';form2.fromtime.value=importtime1.value;form2.endtime.value=importtime2.value;form2.submit();}">
            	(�������ʱ��εĳ�����¼)
            </td>
          </tr>
          <input type="hidden" name="kind">
          <input type="hidden" name="townid">
          <input type="hidden" name="id" value=<%=fi.getId() %>>
	</TABLE>
	<p align=center><input name=addrec type=submit value=" ���� ">&nbsp;
	<%if(ctx.isIfview()){%>
	<input name=back type=button value=" ���� " onclick="window.navigate('departManage.do');">
	<%}else{%>
	<input name=back type=button value=" ���� " onclick="window.navigate('login.do?userName=<%=ctx.getUserName() %>&password=<%=ctx.getPassword() %>');">
	<%} %>
	</p>
	</form>
	<form name=form2 action="departManage.do" target="hideframe" method=post>
	<input type="hidden" name="param" value="">
	<input type="hidden" name="fromtime" value="">
	<input type="hidden" name="endtime" value="">
	<input type="hidden" name="id" value="<%=fi.getId() %>">
	</form>
    </table>  
    <iframe name="hideframe" id="hideframe" src="" width="0" height="0" frameborder=0 scrolling=no></iframe> 
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

