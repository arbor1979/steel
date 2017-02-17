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

function verify(form)
{
	if(gs.form1.goodsId.value=='')
	{
		alert('请选择产品');
		gs.form1.goodsId.focus();
		return false;
	}
	if(gs.form1.storeid.value=='0')
	{
		alert('请选择所在仓库');
		gs.form1.storeid.focus();
		return false;
	}
	form.goodsId.value=gs.form1.goodsId.value;
	form.storeId.value=gs.form1.storeid.value;
	return true;
}

function updateExportNum(id, newexportamount,kind)
{
	//用于数据校验的临时对象
	document.form2.obj4numbercheck.value = newexportamount;
	if(document.form2.obj4numbercheck.value == null || document.form2.obj4numbercheck.value == '')
	{
		alert("数量不可为空");
		return '1';
	}
	if(IsFloat(document.form2.obj4numbercheck.value)==false)
	{
		alert("数量必须是浮点数");
		return '1';
	}
	
	if(Number(newexportamount)<=0)
	{
		alert("数量必须大于0");
		return '1';	
	}
	
	window.navigate("checkGoodsBill.do?param=updateNum&kind="+kind+"&id="+id+"&checkNum="+newexportamount);
}
function updateExportAmount(id, newexportamount,kind)
{
	//用于数据校验的临时对象
	document.form2.obj4numbercheck.value = newexportamount;
	if(document.form2.obj4numbercheck.value == null || document.form2.obj4numbercheck.value == '')
	{
		alert("重量不可为空");
		return '1';
	}
	if(IsFloat(document.form2.obj4numbercheck.value)==false)
	{
		alert("重量必须是浮点数");
		return '1';
	}
	
	if(Number(newexportamount)<=0)
	{
		alert("重量必须大于0");
		return '1';	
	}
	
	window.navigate("checkGoodsBill.do?param=updateAmount&kind="+kind+"&id="+id+"&checkAmount="+newexportamount);
}
function updateExportRealAmount(id, realamount,cur,kind)
{
	//用于数据校验的临时对象
	var newexportamount=0;
	if(kind==1)
	{
		newexportamount=Number(realamount-cur);
	}
	else
	{
		newexportamount=Number(cur-realamount);
	}
	document.form2.obj4numbercheck.value = newexportamount;
	if(document.form2.obj4numbercheck.value == null || document.form2.obj4numbercheck.value == '')
	{
		alert("重量不可为空");
		return '1';
	}
	if(IsFloat(document.form2.obj4numbercheck.value)==false)
	{
		alert("重量必须是浮点数");
		return '1';
	}
	
	if(Number(newexportamount)<=0)
	{
		alert("重量必须大于0");
		return '1';	
	}
	window.navigate("checkGoodsBill.do?param=updateAmount&kind="+kind+"&id="+id+"&checkAmount="+newexportamount);
}
function deleteATempImportGoods(id,kind)
{
	if(confirm("确定要删除该记录吗？")) 
	{
		window.navigate("checkGoodsBill.do?param=del&kind="+kind+"&id="+id);
	}
}

function deleteAllGoods(billid,kind)
{
	try{
		var goods_num = document.form2.goodsid.length;
		if(goods_num == null || document.form2.goodsid == null)
		{//
			//alert("空列表");
		}
	}catch(e)
	{
		alert("列表目前为空");
		return;
	}
	
	if(confirm("确定要清空该列表吗？")) 
	{
		window.navigate("checkGoodsBill.do?param=delAll&kind="+kind+"&billId="+billid);
	}
}

function submitAllGoods()
{
	//查看该单是否是空列表，空列表就不提交
	try{
		var goods_num = document.form2.goodsid.length;
		if(goods_num == null || document.form2.goodsid == null)
		{//
			//alert("空列表");
		}
	}catch(e)
	{
		alert("请添加货物后再提交");
		return;
	}

	//检查数量和单价是否都为数字
	var amount_num= document.form2.exportamount.length;

	//amount_num有库存货物的数量，amount_num>0
	if(amount_num == null)
	{//只有一项
		var isNumberFlage = 'yes';//默认为“是数字”
		
		if(isFloat(document.form2.exportamount)==false)
		{
			isNumberFlage = 'no';
		}
		
		if(isFloat(document.form2.realamount)==false)
		{
			isNumberFlage = 'no';
		}
		
		if(isNumberFlage=='yes')
		{//数字
			
			if(confirm('确认保存此单据？')) 
			{
				document.form2.submit();
			}
		}

	}
	else
	{//两项以上
	
		var isNumberFlage = 'yes';//默认为“是数字”
		for(iii=0; iii<amount_num; iii++)
		{
			if(isFloat(document.form2.exportamount[iii])==false)
			{
				isNumberFlage = 'no';
			}
			if(isFloat(document.form2.realamount[iii])==false)
			{
				isNumberFlage = 'no';
			}
		}

		if(isNumberFlage=='yes')
		{//数字
			if(confirm('确认保存此单据？')) 
			{
				document.form2.submit();
			}
		}
		
	}
}
function showModalWindow(form) 
{	
	window.showModalDialog('goodsSelect2.do',form,'dialogWidth:700px;dialogHeight:500px');
}
</script>
</head>
<body background='images/bgall.gif' onload="submitall.focus();" onkeyup="if(event.keyCode==13) submitall.focus();">
<table width="98%" align="center">
  <tr> 
    <td><%@include file='../html/head.htm'%></td>
  </tr>
  <tr> 
    <td><%@include file='Menu.jsp'%></td>
  </tr>
  <tr>
  	<td>
<%
	int maxBillId = oConvert.getInt((String)request.getAttribute("maxBillId"),0);
	int kind = oConvert.getInt((String)request.getAttribute("kind"),1);
	List goodsList = (List)request.getAttribute("goodsList");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
	int selectmode = oConvert.getInt((String)request.getAttribute("selectmode"),1);
	String skind="";
	if(kind==-1)
		skind="<font color=red>报损</font>";
	else
		skind="报溢";
%>
<table width="980" border="0" align="center" cellpadding="0"
	cellspacing="0">
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
		<td width="379" height="20" class="mainhead"><%=skind %>单录入</td>
		<td class="mainhead" width="379" height="20">&nbsp;</td>
		<td width="5"></td>
	</tr>
	<tr>
		<td width="6"></td>
		<td valign="top" colspan="2">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">

			<tr>
				<td height="300" align="center" valign="top">


	<br>
	<form name="form2" method="post" action="checkGoodsBill.do" >
	<font color=#215385><b>货物列表</b></font>
	<IMG src="images/line1.gif" border=0 width=900>
	<input type="hidden" name="billId"  value="<%=maxBillId%>">
	<input type="hidden" name="kind" value="<%=kind %>">
	<input type="hidden" name="param"  value="submit">
	<input type="hidden" name="obj4numbercheck" value="">
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
		
		<tr bgcolor="#C2CEDC">
		<th></th>
		<th>仓库</th>
		<th>产品编号</th>
		<th>产品名称</th>
		<th>规格</th>
		<th>产地</th>
		<th >账面库存(吨)</th>
		<th >实盘重量(吨)</th>
		<th ><%=skind %>重量(吨)</th>
		<th ><%=skind %>个数</th>
		<th >平均入库价</th>
		<th>金额</th>
		<th>删除</th>
		</tr>
		
		<%

		int index = 0;
		GoodsCheckGoodsInfo tmpInfo = null;
	    double totalPrice = 0;
	    double totalnum = 0;
	    double realnum=0;  	
	    for (index=0; index<goodsList.size(); index++)
		{
	      		tmpInfo = (GoodsCheckGoodsInfo)goodsList.get(index);
	      		totalnum=totalnum+tmpInfo.getCheckAmount()*kind;
	      		totalPrice = totalPrice + tmpInfo.getCheckAmount()*kind*tmpInfo.getCheckUnitPrice();
	      		realnum=oConvert.getRound(tmpInfo.getCurRepertoryAmount()+tmpInfo.getCheckAmount(),4);
	    %>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        <td align='center' width=10><%= index+1 %></td>
	        <td align='left'><%= tmpInfo.getStoreName() %></td>
	        <td align='center'><%= tmpInfo.getGoodsId() %>
	        <input type="hidden" name="goodsid"  value="<%= tmpInfo.getGoodsId() %>">
	        </td>
	        <td align='center' width=90><%= tmpInfo.getGoodsName() %></td>
	        <td align='center'><%= tmpInfo.getGuige() %></td>
	        <td align='left'><%= tmpInfo.getChandi() %></td>
	        <td align='center'><%= tmpInfo.getCurRepertoryAmount() %></td>
	        <td align='center'>
	        	<input type="text" name="realamount" id="realamount"   value="<%=realnum  %>" size=8 onfocus="this.select();"
	        	onChange="if(updateExportRealAmount('<%=tmpInfo.getId()%>', this.value,<%=tmpInfo.getCurRepertoryAmount() %>,<%=kind %>)=='1'){this.value='<%=realnum %>'}">
	        </td>
	        <td align='center'>
	        	<input type="text" name="exportamount" id="exportamount"   value="<%=(tmpInfo.getCheckAmount()==0?0:tmpInfo.getCheckAmount()*kind) %>" size=8 onfocus="this.select();"
	        	onChange="if(updateExportAmount('<%=tmpInfo.getId()%>', this.value,<%=kind %>)=='1'){this.value='<%= (tmpInfo.getCheckAmount()==0?0:tmpInfo.getCheckAmount()*kind) %>'}">
	        </td>
	        <td align='center'>
	       
	        	<input type="text" name="exportnum" id="exportnum"   value="<%= tmpInfo.getCheckNum()*kind %>" size=8 onfocus="this.select();"
	        	onChange="if(updateExportNum('<%=tmpInfo.getId()%>', this.value,<%=kind %>)=='1'){this.value='<%= tmpInfo.getCheckNum()*kind %>'}">
	       
	        </td>
	        
	        <td align='center'><%=tmpInfo.getCheckUnitPrice()%></td>
	        <td align='center'><%= oConvert.getRound(tmpInfo.getCheckUnitPrice()*tmpInfo.getCheckAmount()*kind,2) %></td>
	        <td align='center'>
	        	<a href="javascript:deleteATempImportGoods('<%=tmpInfo.getId()%>',<%=kind %>) "><IMG src="images/delto.gif" border=0></a>
	        </td>
	        </tr>
	        <%

	      	}
	        %>
	</TABLE>
	</form>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 

	  <tr>
            <td  align="right" > 
                <b>单号:</b>
            </td>
            <td >
            	<INPUT class=none  name="billid" type="text" size="20" value="<%=(maxBillId<0?"****":maxBillId)%>" disabled>
            </td>
            <td  align="right" > 
                <b>总数量:</b>
            </td>
            <td >
            	<INPUT class=none  name="totalNum" type="text" size="12" value="<%=oConvert.getRound(totalnum,4)%>" disabled>(吨)
            </td>
            <td  align="right" > 
                <b>总金额:</b>
            </td>
            <td >
            	<INPUT class=none  name="totalPrice" type="text" size="12" value="<%=nf.format(oConvert.getRound(totalPrice,2))%>" disabled>(元)
            </td>
            
            
          </tr>
          	
     
	</TABLE>
	<br>
    <form name="form1" method="post" action="checkGoodsBill.do?param=add">
    	
    	<input type="hidden" name="billId"  value="<%=maxBillId%>">
    	<input type="hidden" name="kind" value="<%=kind %>">
    	<TABLE align="center" width="98%" border="0" cellpadding="3" cellspacing="0">

			<tr>
			<td>
				<input type="radio" name="selectmode" value=0 onclick="bykucun.style.display='none';bychandi.style.display='';" <%=(selectmode==0?"checked":"")%>>按产地<br>
				<input type="radio" name="selectmode" value=1 onclick="bykucun.style.display='';bychandi.style.display='none';" <%=(selectmode==1?"checked":"")%>>按库存<br>
			</td>
			<td align="center" id="bychandi" style="display:<%=(selectmode==0?"":"none")%>">
				<iframe name="gs" id="gs" src="goodsSelect.do?goodsid=&typeid=0" width="700" height="25" frameborder=0 scrolling=no ></iframe>
				<input name="addgoods1" type="button" value=" 添加 " onclick="if(verify(form1)) form1.submit();">
			</td>
			<td align="left" id="bykucun" style="display:<%=(selectmode==1?"":"none")%>">
				 <input name="addgoods2" type="button" value=" 添加商品 " onclick="showModalWindow(form1)">
			</td>
			</tr>
			<input type="hidden" name="goodsId" value="">
			<input type="hidden" name="storeId" value="">
		</TABLE>

	</form>
		
    	
	
	
	<p><center>
	<input name=deleteall type="button" value="清空列表" onClick="deleteAllGoods(<%=maxBillId %>,<%=kind %>)">
	&nbsp;&nbsp;<input name=submitall id=submitall type="button" value="确认提交" onClick="submitAllGoods()">
    	</center><p>
	<br>
  	
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


