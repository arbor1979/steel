<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>

<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<script language="JavaScript">
function updateSellPrice(goodsid,deptid, newimportprice)
{
	if(!IsFloat(newimportprice))
	{
		alert('���۱���Ϊ������');
		return '1';
	}
	if(Number(newimportprice)<0)
	{
		alert("���۱�����ڵ�����");
		return '1';
	}
	else
	{
		form1.param.value="updatePrice";
		form1.egoodsid.value=goodsid;
		form1.edeptid.value=deptid;
		form1.esellprice.value=newimportprice;
		form1.submit();
	}
}

function mySubmit()
{
	form1.goodsClass.value=cat.form1.classid.value;
	form1.goodsType.value=cat.form1.typeid.value;
	return true;
}
function orderList(order,old)
{
	if(order==old)
		order=order+' desc';
	form1.ifprint.value=0;
	form1.orderby.value=order;
	form1.submit();
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
    <td width="379" height="20" class="mainhead">���۶���</td>
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
	List StoreList = (List)request.getAttribute("StoreList");
	List cdList = (List)request.getAttribute("cdList");
 	List departList = (List)request.getAttribute("departList");
	ResultsetList result=(ResultsetList)request.getAttribute("result");
	String orderby=(String)request.getAttribute("orderby");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
%>

    	<form name="form1" method="post" action="changePrice.do" onSubmit="return mySubmit();">
    	<font color=#215385><b>��ѯ����</b></font>
    	<IMG src="images/line1.gif" border=0 width=900>
    	<br>
    	  <input type="hidden" name="goodsType" value="<%=gif.getGoodsType() %>">
          <input type="hidden" name="goodsClass" value="<%=gif.getGoodsClass() %>">
          <input type="hidden" name="ifprint" value="0">
          <input type="hidden" name="orderby"  value="<%=orderby %>">
          <input type="hidden" name="egoodsid"  value="">
          <input type="hidden" name="edeptid"  value="">
          <input type="hidden" name="esellprice"  value="">
          <input type="hidden" name="param"  value="">
    	<TABLE  class="mailtable"> 
	  
	  <tr>
            
            <td  align="right"> 
                <iframe name="cat" id="cat" src="ClassAndType.do?classid=<%=gif.getGoodsClass() %>&typeid=<%=gif.getGoodsType() %>" width="300" height="25" frameborder=0 scrolling=no ></iframe>
            </td>
            <td  align="right"> 
			 <b>����:</b>
			<select name="chandi">
		   		<option value="" <%if(gif.getChandi().equals("")) out.print("selected");%>>-ȫ��-</option>
		<%
				
				String s[];
				for(int i=0;i<cdList.size();i++)
				{
					
					s=(String[])cdList.get(i);
					if (s[0].equals(gif.getChandi()))
						out.print("<option value='"+s[0]+"' selected>"+s[0]+"</option>");
					else
						out.print("<option value='"+s[0]+"'>"+s[0]+"</option>");
				}
		%>
		      </select> 
            </td>
            <td  align="right"> 
                <b>�ֿ�:</b>
            </td>
            <td>
            	<SELECT size=1 name="storeid">
            		<option value="">��ѡ��...</option>
            		<%
	            		StoreInfoForm sif=new StoreInfoForm();
            			for(int i=0; i<StoreList.size(); i++)
            			{
            				sif = (StoreInfoForm)StoreList.get(i);
            		%>
            		<option  value="<%=sif.getId()%>" <%=(gif.getStoreid()==sif.getId()?"selected":"") %>><%=sif.getName()%></option>
			<%
				}
			%>
            	</SELECT>
            </td>
            <%if(ctx.isIfview()) {%>
             <td  align="left" > 
                <b>�ֹ�˾:</b></td>
                <td>
            	<select name="deptid" >
    			<option value="-1">��ѡ��...</option>
    			<%
    			DepartInfoForm dif=new DepartInfoForm();
    			for(int i=0;i<departList.size();i++)
    			{
    				dif=(DepartInfoForm)departList.get(i);
    			%>
    			<option value="<%=dif.getId() %>" <%=(gif.getDeptid()==dif.getId()?"selected":"") %>><%=dif.getShortname() %></option>
    			<%
    			}
    			%>
    			</select>
    			</td>
            <%}else{%>
            <input type="hidden" name="deptid" value="<%=ctx.getDeptid() %>">
            <%} %>
            <td  align="right"> 
                <b>��Ʒ���:</b>
            	<input type=text name='goodsId' size="10" class="none" value=<%=gif.getGoodsId() %>>
            </td>
            <TD>
            		<input name="searchbutton" type="submit" value=" ��ѯ " onclick="form1.ifprint.value=0;">
			</TD>
          </tr>

		</TABLE>
		</form>
	<font color=#215385><b>����Ʒ</b>���� <b><%=result.rslist.size()%></b> ����</font>
	<br>
	<div align="left">
	&nbsp;&nbsp;���������<b><%=result.allsumnum %></b> ��&nbsp; �ɱ����:<b><%=nf.format(result.allmoney)%></b> Ԫ&nbsp;&nbsp;
	</div>
	<IMG src="images/line1.gif" border=0 width=900>

	<TABLE  class="mailtable">  
	<tr bgcolor="#C2CEDC">
		<th><a class="tablehead" href="javascript:orderList('a.goodsid','<%=orderby%>');">��Ʒ���</a><%if(orderby.equals("a.goodsid")) out.print("��"); if(orderby.equals("a.goodsid desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('goodstypename','<%=orderby%>');">����</a><%if(orderby.equals("goodstypename")) out.print("��"); if(orderby.equals("goodstypename desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('guige','<%=orderby%>');">���</a><%if(orderby.equals("guige")) out.print("��"); if(orderby.equals("guige desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('caizhi','<%=orderby%>');">����</a><%if(orderby.equals("caizhi")) out.print("��"); if(orderby.equals("caizhi desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('chandi','<%=orderby%>');">����</a><%if(orderby.equals("chandi")) out.print("��"); if(orderby.equals("chandi desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('c.name','<%=orderby%>');">�ֿ�</a><%if(orderby.equals("c.name")) out.print("��"); if(orderby.equals("c.name desc")) out.print("��");%></th>
		<%if(ctx.isIfview()) {%>
		<th><a class="tablehead" href="javascript:orderList('shortname','<%=orderby%>');">�ֹ�˾</a><%if(orderby.equals("shortname")) out.print("��"); if(orderby.equals("shortname desc")) out.print("��");%></th><%}%>
		<th><a class="tablehead" href="javascript:orderList('repertoryAmount','<%=orderby%>');">��ǰ���</a><%if(orderby.equals("repertoryAmount")) out.print("��"); if(orderby.equals("repertoryAmount desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('avgprice','<%=orderby%>');">�ɱ���<br>(Ԫ/��)</a><%if(orderby.equals("avgprice")) out.print("��"); if(orderby.equals("avgprice desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('avgprice*repertoryAmount','<%=orderby%>');">�ɱ����</a><%if(orderby.equals("avgprice*repertoryAmount")) out.print("��"); if(orderby.equals("avgprice*repertoryAmount desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('realprice','<%=orderby%>');">�����ۼ�<br>(Ԫ/��)</a><%if(orderby.equals("realprice")) out.print("��"); if(orderby.equals("realprice desc")) out.print("��");%></th>
		<th>�������۶�</th>
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
       {
       		out.print("bgcolor=\"#CAE4F4\"");
       	}
       	%>>  
	        <td width="80"><%=tmpInfo.getGoodsId()%></td>  
	        <td><%=tmpInfo.getGoodsTypeName()%></td>
			<td align="center"><%=tmpInfo.getGuige()%></td>
			<td align="center"><%=tmpInfo.getCaizhi()%></td>
			<td align="center"><%=tmpInfo.getChandi()%></td>
			<td align="center"><%=tmpInfo.getStoreName()%></td>
			
			<%if(ctx.isIfview())
			{%>
				<td align='center'><%= tmpInfo.getShortname() %></td>
			<%} %>
			<td align="right"><%=tmpInfo.getCurRepertory()%></td>
			<td align="right"><%=nf.format(oConvert.getRound(tmpInfo.getAvgprice(),2))%></td>
			<td align="right"><%=nf.format(oConvert.getRound(tmpInfo.getCurRepertory()*tmpInfo.getAvgprice(),2))%></td>
			<%if(ctx.getDeptid()==tmpInfo.getDeptid()){ %>
			<td align="right"><input type="text" name="sellprice" size=10 value="<%=tmpInfo.getPurchaseUnitPrice()%>" onkeyup="InputFloat(this);" onfocus="this.select();"
			onChange="if(updateSellPrice('<%=tmpInfo.getGoodsId()%>',<%=tmpInfo.getDeptid()%>,this.value)=='1'){this.value='<%= tmpInfo.getPurchaseUnitPrice()%>'}"></td>
			<td align="right"><input type="text" name="sellmoney" size=10 value="<%=oConvert.getRound(tmpInfo.getCurRepertory()*tmpInfo.getPurchaseUnitPrice(),2)%>" onkeyup="InputFloat(this);" onfocus="this.select();"
			onChange="if(updateSellPrice('<%=tmpInfo.getGoodsId()%>',<%=tmpInfo.getDeptid()%>,Number(this.value)/Number(<%=tmpInfo.getCurRepertory()%>))=='1'){this.value='<%= oConvert.getRound(tmpInfo.getCurRepertory()*tmpInfo.getPurchaseUnitPrice(),2)%>'}"></td>
			
			<%}else{ %>
			<td align="right"><%=tmpInfo.getPurchaseUnitPrice()%></td>
			<td align="right"><%=nf.format(oConvert.getRound(tmpInfo.getCurRepertory()*tmpInfo.getPurchaseUnitPrice(),2))%></td>
			<%} %>
       </tr>
<%
	    }
%>
	</TABLE>
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
