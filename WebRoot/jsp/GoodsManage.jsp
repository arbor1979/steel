<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.GoodsInfoForm"%>
<%@ page import="mediastore.web.form.GoodsStoreForm"%>
<%@ page import="mediastore.web.form.ResultsetList"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="java.util.List"%>
<%@ page import="java.io.File"%>
<%@ page import="java.text.NumberFormat"%>


<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<script language="JavaScript">

function mySubmit()
{
	form1.goodsClass.value=cat.form1.classid.value;
	form1.goodsType.value=cat.form1.typeid.value;
	return true;
}
function delGoodsInfo(goodsid)
{
	if(confirm('�Ƿ�ɾ���˲�Ʒ��Ϣ��'))
	{
		window.navigate('goodsManage.do?param=del&goodsId='+goodsid);
	}
}
function updateGoodsPrice(memberId,newname)
{
	document.form2.action = "goodsManage.do?param=updateprice&goodsId="+memberId+"&purchaseUnitPrice="+newname+"&";
	document.form2.submit();
}
function IsFloat(string) 
{  
	var number; 
	number = new Number(string); 
	if (isNaN(number)) 
	{ 
		return false; 
	} 
	else 
		return true;  
} 
function InputFloat(str)
{
	var moneyObj;
	moneyObj = document.getElementById(str);
	while(!IsFloat(moneyObj.value) && moneyObj.value.length>0)
		moneyObj.value=moneyObj.value.substring(0,moneyObj.value.length-1);
}
function orderList(order,old)
{
	if(order==old)
		order=order+' desc';
	form2.orderstr.value=order;
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
    <td width="379" height="20" class="mainhead">��Ʒ��</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td height="300" align="center" valign="top">   
          
<%////
	//---��ҳ������л�ȡ����---
	GoodsInfoForm gif = (GoodsInfoForm)request.getAttribute("gif");
	ResultsetList result=(ResultsetList)request.getAttribute("result");
	String orderby = (String)request.getAttribute("orderby");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
%>

    	<form name="form1" method="post" action="goodsManage.do"  onSubmit="return mySubmit();">

    	<br>
    	<input type="hidden" name="goodsClass"  value="<%=gif.getGoodsClass() %>">
    	<input type="hidden" name="goodsType"  value="<%=gif.getGoodsType() %>">
		<input type="hidden" name="orderstr"  value="<%=orderby %>">

    	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	  
	  <tr>
            
            <td  align="right"> 
                <iframe name="cat" id="cat" src="ClassAndType.do?classid=<%=gif.getGoodsClass() %>&typeid=<%=gif.getGoodsType() %>" width="350" height="25" frameborder=0 scrolling=no ></iframe>
            </td>
            <td  align="right"> 
                <b>����:</b>
            	<input type=text name='chandi' size="15" class="none" value=<%=gif.getChandi() %>>
            </td>
            <td  align="right"> 
                <b>��Ʒ���:</b>
            	<input type=text name='goodsId' size="15" class="none" value=<%=gif.getGoodsId() %>>
            </td>
            <TD colspan=2>
            		<input name="searchbutton" type="submit" value=" ��ѯ ">
            		<input type="button" value="������Ʒ" onclick="window.navigate('goodsManage.do?param=add');">
			</TD>
          </tr>

		</TABLE>
		</form>
	<font color=#215385>��Ʒ�� (�� <b><%=result.allnum%></b> ��)</font>
	<br>
	<div align="left">
	&nbsp;&nbsp;<font color=#215385></font>
	</div>
	<IMG src="images/line1.gif" border=0 width=900>

	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
	<tr bgcolor="#C2CEDC">
		<th><a class="tablehead" href="javascript:orderList('a.goodsid','<%=orderby%>');">��Ʒ���</a><%
		if(orderby.equals("a.goodsid")) out.print("��"); if(orderby.equals("a.goodsid desc")) out.print("��");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('b.classid','<%=orderby%>');">��������</a><%
		if(orderby.equals("b.classid")) out.print("��"); if(orderby.equals("b.classid desc")) out.print("��");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('a.goodstype','<%=orderby%>');">��������</a><%
		if(orderby.equals("a.goodstype")) out.print("��"); if(orderby.equals("a.goodstype desc")) out.print("��");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('a.guige','<%=orderby%>');">���</a><%
		if(orderby.equals("a.guige")) out.print("��"); if(orderby.equals("a.guige desc")) out.print("��");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('a.caizhi','<%=orderby%>');">����</a><%
		if(orderby.equals("a.caizhi")) out.print("��"); if(orderby.equals("a.caizhi desc")) out.print("��");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('a.chandi','<%=orderby%>');">����</a><%
		if(orderby.equals("a.chandi")) out.print("��"); if(orderby.equals("a.chandi desc")) out.print("��");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('a.purchaseUnitPrice','<%=orderby%>');">�����ۼ�(Ԫ/��)</a><%
		if(orderby.equals("a.purchaseUnitPrice")) out.print("��"); if(orderby.equals("a.purchaseUnitPrice desc")) out.print("��");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('a.xishu','<%=orderby%>');">����ϵ��(����/��λ)</a><%
		if(orderby.equals("a.xishu")) out.print("��"); if(orderby.equals("a.xishu desc")) out.print("��");
		%></th>
		<th>����</th>
	</tr>		
<%
			   	int index = 0;
			   	GoodsInfoForm tmpInfo = null;	
			   	for (index=0; index<result.rslist.size(); index++)
				{
			      	tmpInfo = (GoodsInfoForm)result.rslist.get(index);
		%>
       <tr 
       <%if((index%2)==1)
       		out.print("bgcolor=\"#CAE4F4\"");
       	%>>  
	        <td width="80"><a href='goodsManage.do?param=edit&goodsId=<%=tmpInfo.getGoodsId()%>'><%=tmpInfo.getGoodsId() %></a></td>  
   			<td><%=tmpInfo.getGoodsClassName()%></td>
	        <td><%=tmpInfo.getGoodsTypeName()%></td>
			<td><%=tmpInfo.getGuige()%></td>
			<td><%=tmpInfo.getCaizhi()%></td>
			<td><%=tmpInfo.getChandi()%></td>
			<td align="right" width="80"><INPUT name="price<%=tmpInfo.getGoodsId()%>" align="right" type="text" size=15 maxsize=10 value="<%=tmpInfo.getPurchaseUnitPrice()%>" onChange="updateGoodsPrice('<%=tmpInfo.getGoodsId()%>', this.value)" onkeyup="InputFloat(this.name);"></td>
			<td align="right" width="73"><%=tmpInfo.getXishu() %></td>
			<td align="center"  width="100">
			<input type="button" value="�޸�" onclick="window.navigate('goodsManage.do?param=edit&goodsId=<%=tmpInfo.getGoodsId()%>');">
			<input type="button" value="ɾ��" onclick="delGoodsInfo('<%=tmpInfo.getGoodsId()%>');">
			</td>
       </tr>
<%
	    }
%>
	</TABLE>
	<div align="right">
		<%
			if(result.curpage>1)
			{
		%>
			<a href="javascript:form2.curpage.value=1;form2.submit();"><IMG src="images/startpage.gif" border=0 alt='��ҳ'></a>&nbsp;
			<a href="javascript:form2.curpage.value=<%=result.curpage-1 %>;form2.submit();"><IMG src="images/lastpage.gif" border=0 alt='��һҳ'></a>&nbsp;&nbsp;
		<%
			}
			else
			{
		%>
			<IMG src="images/startpage.gif" border=0 alt='��ҳ'>&nbsp;
			<IMG src="images/lastpage.gif" border=0 alt='��һҳ'>&nbsp;&nbsp;
		<%
			}
			
	    	if(result.curpage<result.pagecount)
	    	{
	    %>
	    	<a href="javascript:form2.curpage.value=<%=result.curpage+1 %>;form2.submit();"><IMG src="images/nextpage.gif" border=0 alt='��һҳ'></a>&nbsp;
	    	<a href="javascript:form2.curpage.value=<%=result.pagecount%>;form2.submit();"><IMG src="images/endpage.gif" border=0 alt='ĩҳ'></a>&nbsp;&nbsp;&nbsp;&nbsp;
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
		�� <select name="gotopagenum" onChange="form2.curpage.value=this.value;form2.submit();">
		<%for(int i=1; i<=result.pagecount; i++)
		  {%>
			<option value="<%=i%>" <%if(i==result.curpage){%>selected<%}%>><%=i%></option>
		<%}%>
		</select> ҳ
	</div>
	</td>
    </tr>
    <tr> 
          <td valign="middle" align="center">
          <form name="form2" method="post" action="goodsManage.do" >
          <input type="hidden" name="goodsType" value="<%=gif.getGoodsType() %>">
          <input type="hidden" name="goodsClass" value="<%=gif.getGoodsClass() %>">
          <input type="hidden" name="goodsId" value="<%=gif.getGoodsId() %>">
          <input type="hidden" name="chandi" value="<%=gif.getChandi() %>">
          <input type="hidden" name="curpage" value="<%=result.curpage %>">
          <input type="hidden" name="orderstr"  value="<%=orderby %>">
          <input name=searchbutton type="hidden" value=" ��ѯ ">
          </form>
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
