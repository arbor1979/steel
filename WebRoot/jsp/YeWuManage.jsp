<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.text.*"%>
<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<script language="JavaScript">

function delYeWuYuan(id)
{
	if(confirm('�Ƿ�ɾ���˵���Ա��'))
	{
		window.navigate('yeWuManage.do?param=del&id='+id);
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
    <td width="379" height="20" class="mainhead">ҵ��Ա����</td>
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
	java.util.List userList = (java.util.List)request.getAttribute("userList");
	java.util.List departList = (java.util.List)request.getAttribute("departList");
	String orderStr = (String)request.getAttribute("orderStr");
	YeWuInfoForm wif = (YeWuInfoForm)request.getAttribute("gif");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);
%>

    	<form name="form1" method="post" action="yeWuManage.do">
    	<input type="hidden" name="param" value="">
    	<br>
    	<input type="hidden" name="orderStr"  value="<%=orderStr%>">
    	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
		<!--����<input name=submit type=submit onClick="addUserRec();">-->
	  <tr>
            <td  align="right"> 
                <b>����:</b>
            </td>
            <td >
            	<INPUT class=none  name="name" type="text" size="15" value="<%=wif.getName() %>">
            </td>
            <td  align="right" > 
                <b>��ַ:</b>
            </td>
            <td >
            	<INPUT class=none  name="address" type="text" size="15" value="<%=wif.getAddress() %>">
            </td>
            <td  align="right" > 
                <b>�绰:</b>
            </td>
            <td >
            	<INPUT class=none  name="mobile" type="text" size="15" value="<%=wif.getMobile() %>">
            </td>
             <%
			if(ctx.isIfview()) {
			%>
            <td  align="right"> 
                <b>�ֹ�˾:</b>
            </td>
            <td>
				<select name="deptid">
				<option value="-1">-ȫ��-</option>
				<%
				DepartInfoForm dif=new DepartInfoForm();
				for(int i=0;i<departList.size();i++)
				{
					dif=(DepartInfoForm)departList.get(i);
				%>
				<option value="<%=dif.getId() %>" <%=(wif.getDeptid()==dif.getId()?"selected":"") %>><%=dif.getShortname() %></option>
				<%
				}
				%>
				</select>
			</td>
			 <%
            }else{
            %>
            <td>&nbsp;</td><td><input type="hidden" name="deptid" value="<%=ctx.getDeptid() %>"></td>
            <%
            }
            %>
            <td>
				<input name=addrec type=submit value="����" onclick="form1.param.value='search';">
            	<input name=sear type=button value="���" onclick="window.navigate('yeWuManage.do?param=add');">
            </td>
          </tr>

	</TABLE>
	</form>
	<font color=#215385><b>����Ա�б�</b>(�� <b><%=userList.size()%></b> ��)</font>
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
		<tr bgcolor="#C2CEDC">
		<th>���</th>
		<%
			if(ctx.isIfview()) {
			%>
		<th>�ֹ�˾</th>
		<%} %>
		<th width=60>����</th>
		<th>סַ</th>
		<th>���֤</th>
		<th>�绰</th>
		<th>����</th>
		</tr>
		
		<%
					    int index = 0;
					    YeWuInfoForm tmpUserInfo = null;
					      	
					    for (index=0; index<userList.size(); index++)
						{
					      		tmpUserInfo = (YeWuInfoForm)userList.get(index);
				%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        <td align=center><%= tmpUserInfo.getId() %></td>
	        <%
			if(ctx.isIfview()) {
			%>
	        <td align=center><%=tmpUserInfo.getShortname() %></td>
	        <%} %>
	        <td align=center><%=tmpUserInfo.getName()%></td>
	        <td align=center><%=tmpUserInfo.getAddress()%></td>
	        <td align=center><%=tmpUserInfo.getIdcard()%></td>
	        <td align=center><%=tmpUserInfo.getMobile()%></td>
	        <td align=center>
	        <%if(ctx.getDeptid()==tmpUserInfo.getDeptid() || ctx.getDeptid()==0){ %>
	        <input type="button" value="�༭" onclick="window.navigate('yeWuManage.do?param=edit&id=<%= tmpUserInfo.getId() %>');">
	        <input type="button" value="ɾ��" onclick="delYeWuYuan('<%=tmpUserInfo.getId() %>');">
	        <%} %></td>
	        </tr>
	        <%
	     }
	        %>
	</TABLE>
	</td>
        </tr>
<form action="" name="form2" method="post">
	<input type="hidden" name="userid" value="">
	<input type="hidden" name="account" value="">
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

