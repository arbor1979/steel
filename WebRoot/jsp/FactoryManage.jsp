<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@page import="mediastore.web.form.*"%>
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

function delYeWuYuan(id)
{
	if(confirm('�Ƿ�ɾ���˵�λ��'))
	{
		window.navigate('factoryManage.do?param=del&id='+id);
	}
}
function gotoPage(){
	
	//��ֵΪҪ��ת����ҳ�����ʼ��¼��
	//alert(document.all.gotopagenum.value);

	document.form2.curpage.value = document.all.gotopagenum.value;
	document.form2.submit();
	
}


function gotoPageByN(n){
	
	document.form2.curpage.value = n;
	document.form2.submit();
	
}
function orderList(order,old)
{
	
	if(order==old)
		order=order+' desc';
	form2.orderby.value=order;
	form2.submit();
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
    <td width="379" height="20" class="mainhead">������λ����</td>
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
	ResultsetList userList = (ResultsetList)request.getAttribute("userList");
	FactoryInfoForm fi=(FactoryInfoForm)request.getAttribute("fi");
	java.util.List departList = (java.util.List)request.getAttribute("departList");
	String orderby=(String)request.getAttribute("orderby");
%>
	<form name="form2" method="post" action="factoryManage.do?" >
    	<br>
    	<input type="hidden" name="curpage"  value="1">
    	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	  <tr>
            <td  align="right" > 
                <b>����:</b>
            </td>
            <td >
            	<INPUT class=none  name="name" type="text" size="12" value="<%=fi.getName() %>">
            </td>
			 <td  align="right" > 
                <b>������:</b>
            </td>
            <td >
            	<INPUT class=none  name="zhujima" type="text" size="12" value="<%=fi.getZhujima() %>">
            </td>
            <td  align="right" > 
                <b>��ϵ��:</b>
            </td>
            <td >
            	<INPUT class=none  name="linkman" type="text" size="12" value="<%=fi.getLinkman() %>">
            </td>
            <td  align="right" > 
                <b>����:</b>
            </td>
            <td >
            	<select name="kind">
            		<option value="" <%if(fi.getKind().length()==0) out.print("selected");%>>-ȫ��-</option>
            		<option value="1000" <%if(fi.getKind().equals("1000")) out.print("selected");%>>��Ӧ��</option>
            		<option value="0100" <%if(fi.getKind().equals("0100")) out.print("selected");%>>������</option>
            		<option value="0010" <%if(fi.getKind().equals("0010")) out.print("selected");%>>�ͻ�</option>
            		<option value="0001" <%if(fi.getKind().equals("0001")) out.print("selected");%>>����</option>
            	</select>
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
				DepartInfoForm df=new DepartInfoForm();
				for(int i=0;i<departList.size();i++)
				{
					df=(DepartInfoForm)departList.get(i);
				%>
				<option value="<%=df.getId() %>" <%=(fi.getDeptid()==df.getId()?"selected":"") %>><%=df.getShortname() %></option>
				<%
				}
				%>
				</select>&nbsp;&nbsp;
            </td>
             <%
            }else{
            %>
            <td>&nbsp;</td><td><input type="hidden" name="deptid" value="<%=ctx.getDeptid() %>"></td>
            <%
            }
            %>
            <td >
            <input type="hidden" name="orderby" value="<%=orderby%>">
            <input name=search type=submit value="��ѯ">&nbsp;
            <input name=new type=button value="����" onclick="window.navigate('factoryManage.do?param=add');">&nbsp;
            <input name=export type=button value="����" onclick="window.navigate('factoryManage.do?param=export');">
            </td>
          </tr>
	</TABLE>
	<br>
	</form>	
    	
	<font color=#215385><b>������λ�б�</b>(�� <b><%=userList.allnum %></b> ��)
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
		<tr bgcolor="#C2CEDC">
		<th><a class="tablehead" href="javascript:orderList('a.id','<%=orderby%>');">���</a><%if(orderby.equals("a.id")) out.print("��"); if(orderby.equals("a.id desc")) out.print("��");%></th>
		<%
			if(ctx.isIfview()) {
		%>
		<th><a class="tablehead" href="javascript:orderList('a.shortname','<%=orderby%>');">�ֹ�˾</a><%if(orderby.equals("a.shortname")) out.print("��"); if(orderby.equals("a.shortname desc")) out.print("��");%></th>
		<%} %>
		<th><a class="tablehead" href="javascript:orderList('a.name','<%=orderby%>');">��λ����</a><%if(orderby.equals("a.name")) out.print("��"); if(orderby.equals("a.name desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('a.address','<%=orderby%>');">��ַ</a><%if(orderby.equals("a.address")) out.print("��"); if(orderby.equals("a.address desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('a.tel','<%=orderby%>');">�绰</a><%if(orderby.equals("a.tel")) out.print("��"); if(orderby.equals("a.tel desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('a.linkman','<%=orderby%>');">��ϵ��</a><%if(orderby.equals("a.linkman")) out.print("��"); if(orderby.equals("a.linkman desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('a.kind','<%=orderby%>');">����</a><%if(orderby.equals("a.kind")) out.print("��"); if(orderby.equals("a.kind desc")) out.print("��");%></th>
		<th>����</th>
		</tr>
		
		<%
		    FactoryInfoForm tmpUserInfo = null;
		    for (int index=0; index<userList.rslist.size(); index++)
			{
		      		tmpUserInfo = (FactoryInfoForm)userList.rslist.get(index);
		%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        <td align=center><%= tmpUserInfo.getId() %></td>
	        <%
			if(ctx.isIfview()) {
			%>
	        <td align=center><%= tmpUserInfo.getShortname() %></td>
	        <%} %>
	        <td align=left><%= tmpUserInfo.getName() %></td>
	        <td align=left><%= tmpUserInfo.getAddress() %></td>
	        <td align=center><%= tmpUserInfo.getTel() %></td>
	        <td align=center><%= tmpUserInfo.getLinkman() %></td>
	        <%
	        	String kind="";
	        	if(tmpUserInfo.getKind().substring(0,1).equals("1"))
	        		kind="��";
	        	if(tmpUserInfo.getKind().substring(1,2).equals("1"))
	        		kind=kind+" ��";
	        	if(tmpUserInfo.getKind().substring(2,3).equals("1"))
	        		kind=kind+" ��";
	        	if(tmpUserInfo.getKind().substring(3,4).equals("1"))
	        		kind=kind+" ����";
	        %>
	        <td align=center><%= kind%></td>
	        <td align=center>
	        <%if(ctx.getDeptid()==tmpUserInfo.getDeptid() || ctx.getDeptid()==0){ %>
	        <input type="button" value="�޸�" onclick="window.navigate('factoryManage.do?param=edit&id=<%= tmpUserInfo.getId() %>');">
	        <input type="button" value="ɾ��" onclick="delYeWuYuan(<%= tmpUserInfo.getId() %>);">
	        <%} %></td>
	        </tr>
	        <%
	     }
	        %>
	</TABLE>
	<div align="right">
		<%
			if(userList.curpage>1)
			{
		%>
		<a href="javascript:gotoPageByN(1)"><IMG src="images/startpage.gif" border=0 alt='��ҳ'></a>&nbsp;
		<a href="javascript:gotoPageByN(<%=userList.curpage-1%>)"><IMG src="images/lastpage.gif" border=0 alt='��һҳ'></a>&nbsp;&nbsp;

		<%
			}
			else
			{
		%>
		<IMG src="images/startpage.gif" border=0 alt='��ҳ'>&nbsp;
		<IMG src="images/lastpage.gif" border=0 alt='��һҳ'>&nbsp;&nbsp;
		<%	
			}

	    	if(userList.curpage<userList.pagecount)
	    	{
	    %>
	    	<a href="javascript:gotoPageByN(<%=userList.curpage+1%>)"><IMG src="images/nextpage.gif" border=0 alt='��һҳ'></a>&nbsp;
	        <a href="javascript:gotoPageByN(<%=userList.pagecount%>)"><IMG src="images/endpage.gif" border=0 alt='ĩҳ'></a>&nbsp;&nbsp;&nbsp;&nbsp;
	    <%
	    	}
			else
			{
		%>
		<IMG src="images/nextpage.gif" border=0 alt='��һҳ'>&nbsp;
		<IMG src="images/endpage.gif" border=0 alt='ĩҳ'>&nbsp;&nbsp;&nbsp;&nbsp;
		<%	
			}
		%>
    	</div>
    	<div align="center">
		�� <select name="gotopagenum" onChange="gotoPage()">
		<%for(int i=1; i<=userList.pagecount; i++)
		  {%>
			<option value="<%=i%>" <%if(i==userList.curpage){%>selected<%}%>><%=i%></option>
		<%}%>
		</select> ҳ
	</div>
	</td>
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

