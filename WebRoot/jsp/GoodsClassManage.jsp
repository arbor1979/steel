<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.GoodsClassInfo"%>
<%@ page import="java.util.List"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js"></script>
<script language="javascript" src="js/jquery.js"></script>
<script language="JavaScript">

function addClass()
{
	if(document.form1.name.value == null || document.form1.name.value == "")
	{
		alert("类型名称不可为空");
		return false;
	}
	return true;
}

function updateGoodsClass(goodsclass, newgoodsclassname)
{
$.ajax({ 
		  type:'POST', 
		  url:"goodsClassManage.do?param=edit&id="+goodsclass, 
		  data:{name:encodeURIComponent(newgoodsclassname)},
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
	//document.form2.action = "goodsClassManage.do?param=edit&id="+goodsclass+"&name="+newgoodsclassname+"&";
	//document.form2.submit();
}

function deleteGoodsClass(goodsclass)
{
	if(confirm("确定要删除该大类及下属子类吗？"))
	{
		document.form2.action = "goodsClassManage.do?param=del&id="+goodsclass+"&";
		document.form2.submit();
	}
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
    <td width="379" height="20" class="mainhead">产品大类</td>
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
      	List goodsClassList = (List)request.getAttribute("classlist");
      	GoodsClassInfo gci=null;
   %>

<form name="form1" method="post" action="goodsClassManage.do?param=add"  onSubmit="return addClass();">
<br>
<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
<tr>
	<td align="center">
	<font color=#215385>大类名称</font>
	<input type=text name='name' size="18" maxlength="25" class="none">
	<input name=addclass type=submit value=" 添加 ">
	</td>
</tr>
</TABLE>
</form>
<form name="form2" method="post" action="" >
<font color=#215385>大类列表 (共 <b><%=goodsClassList.size()%></b> 个)</font>
<IMG src="images/line1.gif" border=0 width=900>
<br>
<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
<tr>
<td align="center">
	<table width="100%">
		<tr bgcolor="#C2CEDC">
		<td align=center width=80><b>大类编码</b></td>
		<td align=center ><b>大类名称</b></td>
		<td align=center ><b>子类数</b></td>
		<td align=center ><b>删除</b></td>
		<td align=center ><b>子类维护</b></td>
		</tr>
		<%
			for (int i=0; i<goodsClassList.size(); i++)
			{
			      	gci= new GoodsClassInfo();
			      	gci = (GoodsClassInfo)goodsClassList.get(i);
		%>

	        <tr <%if((i%2)==1){%>bgcolor="#CAE4F4"<%}%> >
			<td align=center><%=i+1%></td>
			<td align=center><INPUT name="goodsclassname" type="text" size=25 value="<%=gci.getName()%>" onChange="updateGoodsClass('<%=gci.getId()%>', this.value)"></td>
			<td align=center><%=gci.getSubNum()%></td>
			<td align=center>
			<a href="javascript:deleteGoodsClass('<%=gci.getId()%>') "><IMG src="images/delto.gif" border=0></a>
			</td>
			<td align=center><input type="button" value=" 进入 " onclick="window.navigate('goodsTypeManage.do?classid=<%=gci.getId()%>');"></td>
		</tr>
		<%
			}//for
		%>
	</table>
</td>
</tr>
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
