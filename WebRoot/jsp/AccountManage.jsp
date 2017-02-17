<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js"></script>
<script language="javascript" src="js/jquery.js"></script>

<script language="JavaScript">

function delYeWuYuan(id)
{
	if(confirm('是否删除此帐户？'))
	{
		window.navigate('accountManage.do?param=del&id='+id);
	}
}
function addAccount()
{
	if(Trim(form1.accName.value)=='')
	{
		alert('帐户名称不能为空');
		form1.accName.focus();
		return false;
	}
	return true;

}
function getAccountList(deptid)
{
	window.navigate('accountManage.do?param=list&deptid='+deptid);
}
function updateAccount(id,name,account)
{
	if(Trim(name)=='')
	{
		alert('帐户名称不能为空');
		return false;
	}
	//window.navigate('accountManage.do?param=update&id='+id+'&accName='+name+'&account='+account);

	$.ajax({ 
		  type:'POST', 
		  url:"accountManage.do?param=update&id="+id+"&account="+account, 
		  data:{accName:encodeURIComponent(name)},
		  dataType: 'text', 
		  cache:false,
		  async: false,
		  success:function(data) 
		  { 
			  if(data=='ok')
			  {
			  	
			  }
			  else
			  {
				alert(data);
				location.reload();
				return false;
			   }
			  	
		  },
		  error:function(XmlHttpRequest,textStatus, errorThrown)
	  	  {
			  alert('出错：'+errorThrown);
	  	  }
	});
	return true;
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
    <td width="379" height="20" class="mainhead">帐户管理</td>
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
				AccountForm af=(AccountForm)request.getAttribute("af");
            	List departList = (java.util.List)request.getAttribute("departList");
            	List accList = (java.util.List)request.getAttribute("accList");
            	NumberFormat nf = NumberFormat.getNumberInstance();
            	nf.setMaximumFractionDigits(2);		
            	nf.setMinimumFractionDigits(2);
             %>
	<form name="form1" method="post" action="accountManage.do" onsubmit="return addAccount();">
	<input type="hidden" name="param" value="update">
    	<br>
    	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	  <tr>
	  		
           
            <td  align="right" > 
                <b>名称:</b>
            </td>
            <td>
            	<INPUT class=none  name="accName" type="text" size="15" maxlength=5 value="">（不超过5字）
            </td>
			 <td  align="right"> 
                <b>帐号:</b>
            </td>
            <td >
            	<INPUT class=none  name="account" type="text" size="15" maxlength=25 value="">
            </td>
            
            <td >
            <input name=new type=submit value=" 新增 ">
            </td>
            <%if(ctx.isIfview()){ %>
	  		<td  align="right"> 
                <b>分公司:</b>
            </td>
            <td>
				<select name="deptid" onchange="getAccountList(this.value);">
				<option value="-1">-全部-</option>
				<%
				DepartInfoForm df=new DepartInfoForm();
				for(int i=0;i<departList.size();i++)
				{
					df=(DepartInfoForm)departList.get(i);
				%>
				<option value="<%=df.getId() %>" <%=(af.getDeptid()==df.getId()?"selected":"") %>><%=df.getShortname() %></option>
				<%
				}
				%>
				</select>&nbsp;&nbsp;
            </td>
             <%} %>
          </tr>
	</TABLE>
	<br>
	</form>	
    	
	<font color=#215385><b>帐户列表</b>(共 <b><%=accList.size()%></b> 个,合计金额 <b><span id="total" name="total"></span></b> 元)</font>
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
		<tr bgcolor="#C2CEDC">
		<th>编号</th>
		<%if(ctx.isIfview()){%><th>分公司</th><%} %>
		<th>名称</th>
		<th>帐号</th>
		<th>金额</th>
		<th>操作</th>
		</tr>
		
		<%
					    int index = 0;
						AccountForm tmp = null;
						double total=0;
					    for (index=0; index<accList.size(); index++)
						{
					    	tmp = (AccountForm)accList.get(index);
					    	total=total+tmp.getJine();
				%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        <td align=center><%= tmp.getId() %></td>
	        <%if(ctx.isIfview()){%>
	        <td align=center><%= tmp.getShortname() %></td><%} %>
	        <%if(ctx.getDeptid()==tmp.getDeptid()){ %>
	        <td  align=center><input type="text" name="accName<%= tmp.getId() %>" size=25 value="<%=tmp.getAccName()%>" 
	        onChange="if(!updateAccount('<%=tmp.getId()%>', this.value,account<%= tmp.getId() %>.value)){this.value='<%=tmp.getAccName() %>';}"></td>
	        <%}else{ %><td align=center><%= tmp.getAccName() %></td><%} %>
	        <%if(ctx.getDeptid()==tmp.getDeptid()){ %>
	        <td  align=center><input type="text" name="account<%= tmp.getId() %>" size=25 value="<%=tmp.getAccount()%>" 
	        onChange="if(!updateAccount('<%=tmp.getId()%>', accName<%= tmp.getId() %>.value,this.value)){this.value='<%=tmp.getAccount()%>';}"></td>
	        <%}else{ %><td align=center><%= tmp.getAccount() %></td><%} %>
	        <td align=right><%= nf.format(tmp.getJine()) %></td>
	        <td align=center  width=90>
	        <%if(ctx.getDeptid()==tmp.getDeptid()){ %>
	        <input type="button" value="删除" onclick="delYeWuYuan(<%= tmp.getId() %>);">
	        <%} %></td>
	        </tr>
	        <%
	     }
	        %>
	</TABLE>
	</td>
        </tr>
      </table>
<script language="javascript">
	document.getElementById('total').innerText='<%=nf.format(total)%>';
</script>
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

