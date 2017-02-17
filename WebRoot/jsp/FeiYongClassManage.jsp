<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="mediastore.web.form.*"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<script language="javascript" src="js/jquery.js"></script>
<script language="JavaScript">

function addClass()
{
	if(document.form1.classname.value == null || document.form1.classname.value == "")
	{
		alert("类型名称不可为空");
		return false;
	}
	return true;
}

function updateGoodsClass(goodsclass, newgoodsclassname,kind)
{
	//document.form2.action = "feiYongClassManage.do?param=edit&id="+goodsclass+"&classname="+newgoodsclassname+"&kind="+kind;
	//document.form2.submit();
	$.ajax({ 
		  type:'POST', 
		  url:"feiYongClassManage.do?param=edit&id="+goodsclass+"&kind="+kind, 
		  data:{classname:encodeURIComponent(newgoodsclassname)},
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

function deleteGoodsClass(goodsclass,kind)
{
	if(confirm("确定要删除该大类吗？"))
	{
		document.form2.action = "feiYongClassManage.do?param=del&id="+goodsclass+"&kind="+kind;
		document.form2.submit();
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
    <td width="379" height="20" class="mainhead">收入开支类型</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
  <tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td height="200" align="center" valign="top">   

<%
	List classList = (List)request.getAttribute("classList");
	String kind=(String)request.getAttribute("kind");
	FeiYongTypeForm tmp;
%>

<form name="form1" method="post" action="feiYongClassManage.do"  onSubmit="return addClass();">
<br>
<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
<tr>
	<td align="center">
	<input type="radio" name="kind" value="1" <%=(kind.equals("1")?"checked":"") %> onclick="window.navigate('feiYongClassManage.do?kind=1')"><b>收入</b>&nbsp;&nbsp;
	<input type="radio" name="kind" value="-1" <%=(kind.equals("-1")?"checked":"") %>  onclick="window.navigate('feiYongClassManage.do?kind=-1')"><b>开支</b>
	<td align="left">
	<font color=#215385>大类名称</font>
	<input type=text name='classname' size="18" class="none">
	<input type=hidden name='param' value='add'>
	<input name=addclass type=submit value=" 添加 ">
	</td>
</tr>
</TABLE>
</form>
<br>
<form name="form2" method="post" action="" >
<font color=#215385><b>大类列表 (总数<%=classList.size()%>个)</b></font>
<IMG src="images/line1.gif" border=0 width=900>
<br>
<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  

		<tr bgcolor="#C2CEDC">
		<td align=center ><b>大类编码</b></td>
		<td align=center ><b>大类名称</b></td>
		<td align=center ><b>子类数</b></td>
		<td align=center ><b>删除</b></td>
		<td align=center ><b>子类维护</b></td>
		</tr>
		<%
	      		for (int i=0; i<classList.size(); i++)
			{
	      			tmp= (FeiYongTypeForm)classList.get(i);
	      	%>

	        <tr <%if((i%2)==1){%>bgcolor="#CAE4F4"<%}%>>
			<td align=center><%=i+1%></td>
			<td align=center><INPUT name="goodsclassname" type="text" size=20 value="<%=tmp.getClassname()%>" onChange="updateGoodsClass('<%=tmp.getId()%>', this.value,<%=kind %>)"></td>
			<td align=center><%=tmp.getSubcount()%></td>
			<td align=center>
				<%
					if(tmp.getSubcount()==0)
					{
				%>
				<a href="javascript:deleteGoodsClass('<%=tmp.getId()%>',<%=kind %>) "><IMG src="images/delto.gif" border=0></a>
				<%
					}
				%>
			</td>
			<td align=center><input type="button" value=" 进入 " onclick="window.navigate('feiYongTypeManage.do?classid=<%=tmp.getId() %>&kind=<%=kind %>');"></td>
		</tr>
		<%
			}//for
		%>
	
</table>

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
