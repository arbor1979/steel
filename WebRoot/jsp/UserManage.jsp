<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="java.util.*"%>
<%@ page import="mediastore.web.form.*"%>
<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<script language="JavaScript">
function confirmDel(id)
{
	if(confirm('�Ƿ�ȷ��ɾ���˲���Ա��'))
		window.navigate('userManage.do?param=del&id='+id);
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
    <td width="379" height="20" class="mainhead">����Ա����</td>
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
	List userList = (List)request.getAttribute("userList");
	List departList = (List)request.getAttribute("departList");
	UserInfoForm fi = (UserInfoForm)request.getAttribute("gif");
%>
<p align="center">
<%if(ctx.isIfview()){ %>
�����ֹ�˾��
	<select name="deptid" onChange="window.navigate('userManage.do?deptid='+this.value+'');">
	<option value="-1">��ѡ��...</option>
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
<%} %>
<input type="button" value="��������Ա" onclick="window.navigate('userManage.do?param=add');"></p>
    	
	<font color=#215385><b>����Ա�б�</b></font><br>
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
		<tr bgcolor="#C2CEDC">
		<th>�ֹ�˾</th>
		<th>�û���</th>
		<th>�ʻ�״̬</th>
		<th>����״̬</th>
		<th>����ʱ��</th>
		<th>���һ�ε�¼ʱ��</th>
		<th>�༭</th>
		</tr>
		
		<%
		int index = 0;
	      	UserInfoForm tmpUserInfo = null;
	      	
	      	for (index=0; index<userList.size(); index++)
		{
	      		tmpUserInfo = (UserInfoForm)userList.get(index);
	      	%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        <td align=center><%= tmpUserInfo.getDeptShortName() %></td>
	        <td align=center><%= tmpUserInfo.getUserName() %></td>
	        <td align=center><%= (tmpUserInfo.getUserStatus()==1?"����":"����") %></td>
	        <td align=center><%= (tmpUserInfo.getOnline()==1?"<font color=green>����</font>":"<font color=red>����</font>") %></td>
	        <td align=center><%= tmpUserInfo.getCreateTime()%></td>
	        <td align=center><%= tmpUserInfo.getLastLoginTime()%></td>
	        <td align=center><input type="button" value="�༭" onclick="window.navigate('userManage.do?param=edit&id=<%=tmpUserInfo.getId()%>');">&nbsp;
	        <%if(ctx.getId()==tmpUserInfo.getId() || tmpUserInfo.getUserName().equals("admin")){ }else{%><input type="button" value="ɾ��" onclick="confirmDel(<%=tmpUserInfo.getId() %>);"><%} %></td>
			</tr>
		<%} %>
	</TABLE>
	
	<p><center>
	�û�������<font color="red"><%=userList.size()%></font>&nbsp;&nbsp;&nbsp;
    	</center><p>
    	



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

