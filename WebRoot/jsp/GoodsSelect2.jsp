<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.util.oConvert;"%>
</html>
<head>
<base target="_self">
<title>产品选择</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script src="js/jquery-1.8.2.min.js"></script>
<style>
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<script language="JavaScript">
function orderList(order,old)
{
	if(order==old)
		order=order+' desc';
	form1.orderby.value=order;
	form1.submit();
}
function cc(content)  
{    
	//$("#goodsId").val("").focus().val(content); 
	$("#goodsId").css("display","inline").val(content);//赋值
moveEnd($("#goodsId").get(0));//移动光标至末尾，且切换selection的位置
function moveEnd(obj){
            obj.focus(); 
            var len = obj.value.length; 
            if (document.selection) { 
                var sel = obj.createTextRange(); 
                sel.moveStart('character',len); //设置开头的位置
                sel.collapse(); 
                sel.select(); 
            } else if (typeof obj.selectionStart == 'number' && typeof obj.selectionEnd == 'number') { 
                obj.selectionStart = obj.selectionEnd = len; 
            } 
        } 
}
function selectgoods(goodsid,storeid)
{
	var	pform=window.dialogArguments;
	if(typeof(pform)=='undefined')
		pform=window.opener.form1;
	pform.goodsId.value=goodsid;
    pform.storeId.value=storeid;
    pform.submit();
    window.close();
    
}
var last;
function postform(event,oldvalue){//.input为你的输入框
       last = event.timeStamp;
       //利用event的timeStamp来标记时间，这样每次的keyup事件都会修改last的值，注意last必需为全局变量
       setTimeout(function(){    //设时延迟0.5s执行
            if(last-event.timeStamp==0)
            {
            	if($("#goodsId").val()!=oldvalue)
            	 	form1.submit();     
            }
        },500);
}
window.name = "curWindow";
</script>
</head>

<%
	
	ResultsetList gsri=(ResultsetList)request.getAttribute("gsri");
	int factid=oConvert.getInt((String)request.getAttribute("factid"),0);
	int kind=oConvert.getInt((String)request.getAttribute("kind"),0);
	String orderby=(String)request.getAttribute("orderby");
	GoodsInfoForm gif=(GoodsInfoForm)request.getAttribute("gif");
%>
<body onload="form1.goodsId.focus();">

<form action="goodsSelect2.do" method="post" name="form1" target="curWindow">
<input type="hidden" name="factid" value="<%=factid %>">
<input type="hidden" name="kind" value="<%=kind %>">
<input type="hidden" name="orderby" value="<%=orderby %>">
<input type="hidden" name="storeid" value="<%=gif.getStoreid() %>">
&nbsp;&nbsp;商品编号：<input type="text" id="goodsId" name="goodsId" onkeyup="postform(event,'<%=gif.getGoodsId() %>');" size=20 value="<%=gif.getGoodsId() %>" onfocus="cc('<%=gif.getGoodsId() %>');">

<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="3" cellspacing="0" style="border-collapse:collapse">
	<tr bgcolor="#C2CEDC">
		<th><a class="tablehead" href="#" onclick="orderList('a.goodsid','<%=orderby%>');">商品编号</a><%if(orderby.equals("a.goodsid")) out.print("↓"); if(orderby.equals("a.goodsid desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="#" onclick="orderList('goodstypename','<%=orderby%>');">名称</a><%if(orderby.equals("goodstypename")) out.print("↓"); if(orderby.equals("goodstypename desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="#" onclick="orderList('caizhi','<%=orderby%>');">材质</a><%if(orderby.equals("caizhi")) out.print("↓"); if(orderby.equals("caizhi desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="#" onclick="orderList('guige','<%=orderby%>');">规格</a><%if(orderby.equals("guige")) out.print("↓"); if(orderby.equals("guige desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="#" onclick="orderList('chandi','<%=orderby%>');">产地</a><%if(orderby.equals("chandi")) out.print("↓"); if(orderby.equals("chandi desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="#" onclick="orderList('c.name','<%=orderby%>');">仓库</a><%if(orderby.equals("c.name")) out.print("↓"); if(orderby.equals("c.name desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="#" onclick="orderList('repertoryAmount','<%=orderby%>');">当前库存</a><%if(orderby.equals("repertoryAmount")) out.print("↓"); if(orderby.equals("repertoryAmount desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="#" onclick="orderList('realprice','<%=orderby%>');">建议售价<br></a><%if(orderby.equals("realprice")) out.print("↓"); if(orderby.equals("realprice desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="#" onclick="orderList('memo','<%=orderby%>');">备注</a><%if(orderby.equals("memo")) out.print("↓"); if(orderby.equals("memo desc")) out.print("↑");%></th>
		<%if(factid!=0){%>
		<th ><a class="tablehead" href="#">上次时间</a></th>
		<th ><a class="tablehead" href="#">上次数量</a></th>
		<th ><a class="tablehead" href="#">上次价格</a></th>
		<%} %>
	</tr>		
<%
			   	int index = 0;
			   	GoodsInfoForm tmpInfo = null;	
			   	for (index=0; index<gsri.rslist.size(); index++)
				{
			      	tmpInfo = (GoodsInfoForm)gsri.rslist.get(index);
		%>
       <tr onMouseOver="style.backgroundColor='#CAE4F4'" onMouseOut="style.backgroundColor='#FFFFFF'" onDblClick="selectgoods('<%=tmpInfo.getGoodsId()%>',<%=tmpInfo.getStoreid()%>);" title="双击鼠标选择此商品">  
	        <td width="80"><%=tmpInfo.getGoodsId()%></td>  
	        <td><%=tmpInfo.getGoodsTypeName()%></td>
   			<td align="center"><%=tmpInfo.getCaizhi()%></td>
			<td align="center"><%=tmpInfo.getGuige()%></td>
			<td align="center"><%=tmpInfo.getChandi()%></td>
			<td align="center"><%=tmpInfo.getStoreName()%></td>
			<td align="right"><%=tmpInfo.getCurRepertory()%></td>
			<td align="right"><%=tmpInfo.getPurchaseUnitPrice()%></td>
			<td align="center"><%= tmpInfo.getMemo() %></td>
			<%if(factid!=0){%>
			<td align="center"><%=oConvert.getString(tmpInfo.getLasttime(),"") %></td>
			<td align="right"><%=tmpInfo.getLastamount()%></td>
			<td align="right"><%=tmpInfo.getLastprice()%></td>
			<%} %>
       </tr>
<%
	    }
%>
	</table>
	</form>
	<p align="center"><input type="button" value=" 关闭 " onclick="window.close();"></p>
</body>
</html>
