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
	if(form1.kind.value==-1)
	{
		if(gs1.form1.goodsId.value=='')
		{
			alert('请选择源料商品');
			gs1.form1.goodsId.focus();
			return false;
		}
		if(gs1.form1.storeid.value=='0')
		{
			alert('请选择源料商品所在仓库');
			gs1.form1.storeid.focus();
			return false;
		}
	}
	else if(form1.kind.value==1)
	{
		if(gs1.form1.goodsId.value=='')
		{
			alert('请选择加工后商品');
			gs2.form1.goodsId.focus();
			return false;
		}
		if(gs1.form1.storeid.value=='0')
		{
			alert('请选择加工后商品所在仓库');
			gs2.form1.storeid.focus();
			return false;
		}
	}
	form.goodsId.value=gs1.form1.goodsId.value;
	form.storeId.value=gs1.form1.storeid.value;
	return true;
}

function updateFromNum(id, newexportamount)
{
	//用于数据校验的临时对象
	document.form2.obj4numbercheck.value = newexportamount;
	if(document.form2.obj4numbercheck.value == null || document.form2.obj4numbercheck.value == '')
	{
		alert("数量不可为空");
		return '1';
	}
	if(IsInteger(document.form2.obj4numbercheck.value)==false)
	{
		alert("数量必须是整数");
		return '1';
	}
	
	if(Number(newexportamount)<=0)
	{
		alert("数量必须大于0");
		return '1';	
	}	
	window.navigate("kaiPing.do?param=updateFromNum&id="+id+"&fromNum="+newexportamount);
}


function updateFromAmount(id, newexportamount)
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
	
	window.navigate("kaiPing.do?param=updateFromAmount&id="+id+"&fromAmount="+newexportamount);
}
function updateMoney(id, newimportprice)
{
	//用于数据校验的临时对象
	document.form2.obj4numbercheck.value = newimportprice;

	if(isFloat(document.form2.obj4numbercheck,'金额必须为浮点类型')==false)
	{
		return '1';
	}
	if(Number(document.form2.obj4numbercheck.value)<=0)
	{
		alert("金额必须大于零");
		return '1';
	}
	else
	{
		window.navigate("kaiPing.do?param=updateMoney&id="+id+"&jine="+newimportprice);
		
	}
}
function updatePrice(id, newimportprice,fromAmount)
{
	//用于数据校验的临时对象
	document.form2.obj4numbercheck.value = newimportprice;

	if(isFloat(document.form2.obj4numbercheck,'金额必须为浮点类型')==false)
	{
		return '1';
	}
	if(Number(document.form2.obj4numbercheck.value)<=0)
	{
		alert("金额必须大于零");
		return '1';
	}
	else
	{
		window.navigate("kaiPing.do?param=updatePrice&id="+id+"&fromPrice="+newimportprice+"&fromAmount="+fromAmount);
		
	}
}
function deleteATempImportGoods(id)
{
	if(confirm("确定要删除该记录吗？")) 
	{
		window.navigate("kaiPing.do?param=del&id="+id);
	}
}

function deleteAllGoods()
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
		window.navigate("kaiPing.do?param=delAll");
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
	var amount_num= document.form2.goodsid.length;

	//amount_num有库存货物的数量，amount_num>0
	if(amount_num == null)
	{//只有一项
		var isNumberFlage = 'yes';//默认为“是数字”
		
		if(isFloat(document.form2.fromamount)==false)
		{
			isNumberFlage = 'no';
		}
		if(Number(document.form2.fromamount.value)<=0)
		{
			alert('吨数必须大于零');
			document.form2.fromamount.focus();
			return false;
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
			if(isFloat(document.form2.fromamount[iii])==false)
			{
				isNumberFlage = 'no';
			}
			if(Number(document.form2.fromamount[iii].value)<=0)
			{
				alert('吨数必须大于零');
				document.form2.fromamount[iii].focus();
				return false;
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
function UpdateBill(id)
{
	form2.param.value='update';
	form2.submit();
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
	KaiPingInfoForm cbf=(KaiPingInfoForm)request.getAttribute("cbf");
	List goodsList = (List)request.getAttribute("goodsList");
	int selectmode = oConvert.getInt((String)request.getAttribute("selectmode"),1);
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);

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
		<td width="379" height="20" class="mainhead">商品二次加工</td>
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
	<form name="form2" method="post" action="kaiPing.do" >
	<input type="hidden" name="billid" value="<%=cbf.getBillid() %>">
	<input type="hidden" name="param" value="submit">
	<input type="hidden" name="obj4numbercheck" value="">	
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	 <tr>
          <td align="right" >  
          	<b>备注说明:</b>
          </td>
          <td>
		 	<INPUT class=none  name="memo" type="text" size="30" value="<%=cbf.getMemo() %>"  onChange="UpdateBill();">
		  </td>
         
       </tr>   	
     
	</TABLE>
	<font color=#215385><b>货物列表</b></font>
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
		
		<tr bgcolor="#C2CEDC">
		<th></th>
		<th >类型</th>
		<th>商品编号</th>
		<th>商品名称</th>
		<th>规格</th>
		<th>产地</th>
		<th>当前库存</th>
		<th>数量</th>
		<th>重量(吨)</th>
		<th >成本价(元)</th>
		<th >金额(元)</th>
		<th>删除</th>
		</tr>
		
		<%

		int index = 0;
		KaiPingForm tmpInfo = null;
	    double totalPrice1 = 0;
	    double totalPrice2 = 0;
	    double totalnum1 = 0;
	    double totalnum2 = 0;
	    for (index=0; index<goodsList.size(); index++)
		{
	      		tmpInfo = (KaiPingForm)goodsList.get(index);
	      		if(tmpInfo.getKind()==-1)
	      		{
		      		totalnum1=totalnum1+tmpInfo.getFromAmount();
		      		totalPrice1 = totalPrice1 + oConvert.getRound(tmpInfo.getJine(),2);
	      		}
	      		else if(tmpInfo.getKind()==1)
	      		{
		      		totalnum2=totalnum2+tmpInfo.getFromAmount();
		      		totalPrice2 = totalPrice2 + oConvert.getRound(tmpInfo.getJine(),2);
	      		}
	      		
	    %>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        <td align='center' width=10><%= index+1 %></td>
	        <td align='center' ><%=(tmpInfo.getKind()==-1?"<font color=green>原料</font>":"<font color=blue>成品</font>") %></td>
	        <td align='center'><%= tmpInfo.getFromGoodsId() %>
	        <input type="hidden" name="goodsid"  value="<%= tmpInfo.getFromGoodsId() %>">
	        </td>
	        <td align='center' width=90><%= tmpInfo.getFromGoodsName() %></td>
	        <td align='center'><%= tmpInfo.getGuige() %></td>
	        <td align='center'><%= tmpInfo.getChandi() %></td>
	        <td align='center'><%= tmpInfo.getCurRepertory() %></td>
	        <td align='center'>
	        	<input type="text" name="fromnum" id="fromnum"   value="<%= tmpInfo.getFromNum() %>" size=8 onfocus="this.select();"
	        	onChange="if(updateFromNum('<%=tmpInfo.getId()%>', this.value)=='1'){this.value='<%= tmpInfo.getFromNum() %>'}">
	        </td>
	        <td align='center'>
	        	<input type="text" name="fromamount" id="fromamount"   value="<%= tmpInfo.getFromAmount() %>" size=8 onfocus="this.select();"
	        	onChange="if(updateFromAmount('<%=tmpInfo.getId()%>', this.value)=='1'){this.value='<%= tmpInfo.getFromAmount() %>'}">
	        </td>
	        <%if(tmpInfo.getKind()==-1){ %>
	        <td align='right'><%=nf.format(tmpInfo.getFromPrice()) %></td>
	        <td align='right'><%=nf.format(tmpInfo.getJine()) %></td>
	        <%}else{ %>
	        <td align='right'>
	        <input type="text" name="fromprice" id="fromprice"   value="<%= tmpInfo.getFromPrice() %>" size=8 onfocus="this.select();"
	        	onChange="if(updatePrice('<%=tmpInfo.getId()%>', this.value,<%=tmpInfo.getFromAmount() %>)=='1'){this.value='<%= tmpInfo.getFromPrice()%>'}">
	        </td>
	        <td align='right'>
	        <input type="text" name="jine" id="jine"   value="<%= oConvert.getRound(tmpInfo.getJine(),2) %>" size=8 onfocus="this.select();"
	        	onChange="if(updateMoney('<%=tmpInfo.getId()%>', this.value)=='1'){this.value='<%= oConvert.getRound(tmpInfo.getJine(),2)%>'}">
	        </td>
	        <%} %>
	        <td align='center'>
	        	<a href="javascript:deleteATempImportGoods('<%=tmpInfo.getId()%>') "><IMG src="images/delto.gif" border=0></a>
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
                <b>源料重量:</b>
            </td>
            <td >
            	<INPUT class=none  name="totalNum" type="text" size="12" value="<%=oConvert.getRound(totalnum1,3)%>" disabled>(吨)
            </td>
            <td  align="right" > 
                <b>成品重量:</b>
            </td>
            <td >
            	<INPUT class=none  name="totalNum" type="text" size="12" value="<%=oConvert.getRound(totalnum2,3)%>" disabled>(吨)
            </td>
            <td  align="right" > 
                <b>原料金额:</b>
            </td>
            <td >
            	<INPUT class=none  name="totalPrice" type="text" size="12" value="<%=nf.format(oConvert.getRound(totalPrice1,2))%>" disabled>(元)
            </td>
            <td><%=(totalPrice1==totalPrice2?"<font color=green>=</font>":"<font color=red>≠</font>") %></td>
            <td  align="right" > 
                <b>成品金额:</b>
            </td>
            <td >
            	<INPUT class=none  name="totalPrice" type="text" size="12" value="<%=nf.format(oConvert.getRound(totalPrice2,2))%>" disabled>(元)
            </td>
            
          </tr>
          	
     
	</TABLE>
	<br>
    <form name="form1" method="post" action="kaiPing.do?param=add" onSubmit="javascript:return verify(this);">
    	
    	<TABLE align="center" width="98%" border="0" cellpadding="3" cellspacing="0">

			<tr>
			<td>
				<input type="radio" name="selectmode" value=0 onclick="bykucun.style.display='none';bychandi.style.display='';" <%=(selectmode==0?"checked":"")%>>按产地<br>
				<input type="radio" name="selectmode" value=1 onclick="bykucun.style.display='';bychandi.style.display='none';" <%=(selectmode==1?"checked":"")%>>按库存<br>
			</td>
			<td align="center" id="bychandi" style="display:<%=(selectmode==0?"":"none")%>">
				<iframe name="gs1" id="gs1" src="goodsSelect.do?goodsid=&typeid=0&storeid=0" width="680" height="25" frameborder=0 scrolling=no ></iframe>
				<input name="addgoods1" type="button" value=" 添加 " onclick="if(verify(form1)) form1.submit();">
				</td>
				<td align="left" id="bykucun" style="display:<%=(selectmode==1?"":"none")%>">
				 <input name="addgoods2" type="button" value=" 添加商品 " onclick="showModalWindow(form1)">
				</td>
				<td>
				<input type="radio" name="kind" value="-1" <%=(cbf.getKind()==-1?"checked":"") %>>添加原料<br>
				<input type="radio" name="kind" value="1" <%=(cbf.getKind()==1?"checked":"") %>>添加成品<br>
				</td>
			</tr>
			<input type="hidden" name="billid" value="<%=cbf.getBillid() %>">
			<input type="hidden" name="goodsId" value="">
			<input type="hidden" name="storeId" value="">
		</TABLE>

	</form>
		
    	
	
	
	<p><center>
	<input name=deleteall type="button" value="清空列表" onClick="deleteAllGoods()">
	&nbsp;&nbsp;<input name=submitall id=submitall type="button" value=" 保  存 " onClick="submitAllGoods();">
	
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


