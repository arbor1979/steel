<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>

<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<script language="JavaScript">
function updateSellPrice(goodsid,deptid, newimportprice)
{
	if(!IsFloat(newimportprice))
	{
		alert('单价必须为浮点型');
		return '1';
	}
	if(Number(newimportprice)<0)
	{
		alert("单价必须大于等于零");
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
    <td width="379" height="20" class="mainhead">销售定价</td>
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
	//---从页面对象中获取数据---
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
    	<font color=#215385><b>查询条件</b></font>
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
			 <b>产地:</b>
			<select name="chandi">
		   		<option value="" <%if(gif.getChandi().equals("")) out.print("selected");%>>-全部-</option>
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
                <b>仓库:</b>
            </td>
            <td>
            	<SELECT size=1 name="storeid">
            		<option value="">请选择...</option>
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
                <b>分公司:</b></td>
                <td>
            	<select name="deptid" >
    			<option value="-1">请选择...</option>
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
                <b>产品编号:</b>
            	<input type=text name='goodsId' size="10" class="none" value=<%=gif.getGoodsId() %>>
            </td>
            <TD>
            		<input name="searchbutton" type="submit" value=" 查询 " onclick="form1.ifprint.value=0;">
			</TD>
          </tr>

		</TABLE>
		</form>
	<font color=#215385><b>库存产品</b>（共 <b><%=result.rslist.size()%></b> 个）</font>
	<br>
	<div align="left">
	&nbsp;&nbsp;库存总数：<b><%=result.allsumnum %></b> 吨&nbsp; 成本金额:<b><%=nf.format(result.allmoney)%></b> 元&nbsp;&nbsp;
	</div>
	<IMG src="images/line1.gif" border=0 width=900>

	<TABLE  class="mailtable">  
	<tr bgcolor="#C2CEDC">
		<th><a class="tablehead" href="javascript:orderList('a.goodsid','<%=orderby%>');">产品编号</a><%if(orderby.equals("a.goodsid")) out.print("↓"); if(orderby.equals("a.goodsid desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('goodstypename','<%=orderby%>');">名称</a><%if(orderby.equals("goodstypename")) out.print("↓"); if(orderby.equals("goodstypename desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('guige','<%=orderby%>');">规格</a><%if(orderby.equals("guige")) out.print("↓"); if(orderby.equals("guige desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('caizhi','<%=orderby%>');">材质</a><%if(orderby.equals("caizhi")) out.print("↓"); if(orderby.equals("caizhi desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('chandi','<%=orderby%>');">产地</a><%if(orderby.equals("chandi")) out.print("↓"); if(orderby.equals("chandi desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('c.name','<%=orderby%>');">仓库</a><%if(orderby.equals("c.name")) out.print("↓"); if(orderby.equals("c.name desc")) out.print("↑");%></th>
		<%if(ctx.isIfview()) {%>
		<th><a class="tablehead" href="javascript:orderList('shortname','<%=orderby%>');">分公司</a><%if(orderby.equals("shortname")) out.print("↓"); if(orderby.equals("shortname desc")) out.print("↑");%></th><%}%>
		<th><a class="tablehead" href="javascript:orderList('repertoryAmount','<%=orderby%>');">当前库存</a><%if(orderby.equals("repertoryAmount")) out.print("↓"); if(orderby.equals("repertoryAmount desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('avgprice','<%=orderby%>');">成本价<br>(元/吨)</a><%if(orderby.equals("avgprice")) out.print("↓"); if(orderby.equals("avgprice desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('avgprice*repertoryAmount','<%=orderby%>');">成本金额</a><%if(orderby.equals("avgprice*repertoryAmount")) out.print("↓"); if(orderby.equals("avgprice*repertoryAmount desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('realprice','<%=orderby%>');">建议售价<br>(元/吨)</a><%if(orderby.equals("realprice")) out.print("↓"); if(orderby.equals("realprice desc")) out.print("↑");%></th>
		<th>估计销售额</th>
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
