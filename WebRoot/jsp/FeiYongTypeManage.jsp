<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="mediastore.web.form.*"%>
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
	if(document.form1.typename.value == null || document.form1.typename.value == "")
	{
		alert("分类名称不可为空");
		return false;
	}
	return true;
}

function updateGoodsType(id, newgoodstypename,classid,kind)
{
	//window.location="feiYongTypeManage.do?param=edit&id="+id+"&typename="+newgoodstypename+"&classid="+classid+"&kind="+kind;
	$.ajax({ 
		  type:'POST', 
		  url:"feiYongTypeManage.do?param=edit&id="+id+"&classid="+classid+"&kind="+kind, 
		  data:{typename:encodeURIComponent(newgoodstypename)},
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

function deleteGoodsType(goodstype,classid,kind)
{
	if(confirm("确定要删除该货物类型吗？"))
	{
		window.location="feiYongTypeManage.do?param=del&id="+goodstype+"&classid="+classid+"&kind="+kind;
	}
}
</script>
</head>
<body background='images/bgall.gif' >
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
    <td width="379" height="20" class="mainhead">费用子类管理</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td align="center" valign="top">   

<%
	List classList = (List)request.getAttribute("classList");
	List typeList = (List)request.getAttribute("typeList");
	String classid=oConvert.getString((String)request.getAttribute("classid"),"0");
	String kind=oConvert.getString((String)request.getAttribute("kind"),"0");
	
//----------------------------

FeiYongTypeForm tmp;
FeiYongTypeForm tmpclass;
%>

<form name="form1" method="post" action="feiYongTypeManage.do" onSubmit="return addType()">
<TABLE  class="mailtable">  
<tr>
	<td align="center">
	<font color=#215385>子类名称</font>
	<input type=text name='typename' size="18" class="none">
	<input type="hidden" name="classid"  value="<%=classid %>">
	<input type="hidden" name="kind"  value="<%=kind %>">
	<input type="hidden" name="param"  value="add">
	<input name=addclass type=submit value=" 添加 ">&nbsp;&nbsp;
	<input type=button value=" 返回 " onClick="window.location='feiYongClassManage.do';">
	</td>
</tr>
</TABLE>
</form>
<br>
<font color=#215385>子类列表 (共 <b><%=typeList.size()%></b> 个)</font>
<IMG src="images/line1.gif" border=0 width=900>
<br>
<TABLE  class="mailtable">  

		<tr bgcolor="#C2CEDC">
		<td align=center ><b>子类编码</b></td>
		<td align=center ><b>子类名称</b></td>
		<td align=center ><b>所属大类</b></td>
		<td align=center ><b>删除</b></td>
		</tr>
		<%
	      	for (int i=0; i<typeList.size(); i++)
			{
	      			tmp = (FeiYongTypeForm)typeList.get(i);
	      	%>

	        <tr <%if((i%2)==1){%>bgcolor="#CAE4F4"<%}%> >
			<td align=center><%=tmp.getId()%></td>
			<%if(tmp.isIflock()){%>
			<td align=center><%=tmp.getTypename() %></td>
			<td align=center><%=tmp.getClassname() %></td>
			<td align=center></td>
			<%}else{ %>
			<td align=center>
			<INPUT name="goodsclassname" type="text" size=20 value="<%=tmp.getTypename()%>" onChange="updateGoodsType('<%=tmp.getId()%>', this.value,<%=tmp.getClassid() %>,<%=kind %>);">
			</td>
			<td align=center><select name="sel" onChange="updateGoodsType('<%=tmp.getId()%>','<%=tmp.getTypename()%>',this.value,<%=kind %>);">
<%
				for (int j=0; j<classList.size(); j++)
				{
	      			tmpclass = (FeiYongTypeForm)classList.get(j);
	      			if(tmp.getClassid()==tmpclass.getId())
						out.println("<option value=\""+tmpclass.getId()+"\" selected>"+tmpclass.getClassname()+"</option>");
	      			else
	      				out.println("<option value=\""+tmpclass.getId()+"\">"+tmpclass.getClassname()+"</option>");
				}
%>
				</select>
			</td>
			<td align=center>
			
				<a href="javascript:deleteGoodsType('<%=tmp.getId()%>',<%=tmp.getClassid() %>,<%=kind %>);"><IMG src="images/delto.gif" border=0></a>
				
			</td>
			<%} %>
		</tr>
		<%
			}//for
		%>
</table>
	</td>
        </tr>
        <tr> 
          <td valign="middle" align="center"><br><br><input type=button value=" 返回 " onClick="window.location='feiYongClassManage.do?kind=<%=kind %>';"><br></td>
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

