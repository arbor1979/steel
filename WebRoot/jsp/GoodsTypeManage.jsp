<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="mediastore.web.form.GoodsClassInfo"%>
<%@ page import="mediastore.web.form.GoodsTypeInfo"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<script language="javascript" src="js/jquery.js"></script>
<script language="JavaScript">
function addType()
{
	if(document.form1.name.value == null || document.form1.name.value == "")
	{
		alert("子类名称不可为空");
		return false;
	}
	return true;
}

function updateGoodsType(goodstype, newgoodstypename,classid)
{
	//document.form2.action = "goodsTypeManage.do?param=edit&id="+goodstype+"&name="+newgoodstypename+"&classid="+classid;
	//document.form2.submit();
	$.ajax({ 
		  type:'POST', 
		  url:"goodsTypeManage.do?param=edit&id="+goodstype+"&classid="+classid, 
		  data:{name:encodeURIComponent(newgoodstypename)},
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


function deleteGoodsType(goodstype,classid)
{
	if(confirm("确定要删除该子类及下属所有商品信息吗？"))
	{
		document.form2.action = "goodsTypeManage.do?param=del&id="+goodstype+"&classid="+classid;
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
    <td width="379" height="20" class="mainhead">产品子类</td>
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
	List goodsTypeList = (List)request.getAttribute("goodsTypeList");
	List goodsClassList = (List)request.getAttribute("goodsClassList");
	String classid=(String)request.getAttribute("classid");

%>

<form name="form1" method="post" action="goodsTypeManage.do?param=add" onSubmit="return addType()">
<br>
<TABLE  class="mailtable">  
<tr>
	<td align="center">
	<font color=#215385>子类名称</font>
	<input type=text name='name' size="18" maxlength="25" class="none">
	<input type="hidden" name="classid"  value="<%=classid %>">
	<input name=addclass type=submit value=" 添加 ">&nbsp;&nbsp;
	<input type=button value=" 返回 " onClick="window.location='goodsClassManage.do';">
	</td>
</tr>
</TABLE>
</form>
<form name="form2" method="post" action="" >
<font color=#215385>子类列表 (共 <b><%=goodsTypeList.size()%></b> 个)</font>
<IMG src="images/line1.gif" border=0 width=900>
<br>
<input type="hidden" name="start_rec_num"  value="1">
<TABLE  class="mailtable">  
<tr>
<td align="center" >
	<table width="100%">
		<tr bgcolor="#C2CEDC">
		<td align=center width=80><b>子类编码</b></td>
		<td align=center ><b>子类名称</b></td>
		<td align=center ><b>拥有商品种类</b></td>
		<td align=center ><b>所属大类</b></td>
		<td align=center ><b>删除</b></td>
		</tr>
		<%
			GoodsTypeInfo gti=new GoodsTypeInfo();
			GoodsClassInfo gci=new GoodsClassInfo();
	      	for (int i=0; i<goodsTypeList.size(); i++)
			{
	      		gti = (GoodsTypeInfo)goodsTypeList.get(i);
	      	%>

	        <tr <%if((i%2)==1){%>bgcolor="#CAE4F4"<%}%> >
			<td align=center><%=gti.getId()%></td>
			<td align=center><INPUT name="goodsclassname" type="text" size=25 value="<%=gti.getName()%>" onChange="updateGoodsType('<%=gti.getId()%>', this.value,<%=classid %>);"></td>
			<td align=center><%=gti.getSubNum()%></td>
			<td align=center><select name="sel" onChange="updateGoodsType('<%=gti.getId()%>','<%=gti.getName()%>',this.value);">
<%
				for (int j=0; j<goodsClassList.size(); j++)
				{
					gci = (GoodsClassInfo)goodsClassList.get(j);
	      			if(gti.getClassid()==gci.getId())
						out.println("<option value=\""+gci.getId()+"\" selected>"+gci.getName()+"</option>");
	      			else
	      				out.println("<option value=\""+gci.getId()+"\">"+gci.getName()+"</option>");
				}
%>
				</select>
			</td>
			<td align=center>
				<a href="javascript:deleteGoodsType('<%=gti.getId()%>',<%=classid %>);"><IMG src="images/delto.gif" border=0></a>
			</td>
			
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
          <td valign="middle" align="center">	<input type=button value=" 返回 " onClick="window.location='goodsClassManage.do';"></td>
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

