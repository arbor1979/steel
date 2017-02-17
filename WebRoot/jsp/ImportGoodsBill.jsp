<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js"></script>
<script language="JavaScript">

function verify(form){
	if(gs.form1.goodsId.value=='')
	{
		alert('请选择产品');
		gs.form1.goodsId.focus();
		return false;
	}
	if(gs.form1.storeid.length==0)
	{
		alert('请先创建一个仓库');
		return false;
	}
	if(gs.form1.storeid.value=='0')
	{
		alert('请选择仓库');
		gs.form1.storeid.focus();
		return false;
	}
	
	form.goodsId.value=gs.form1.goodsId.value;
	form.storeId.value=gs.form1.storeid.value;
	return true;
}

function updateImportJianNum(id, newexportamount,kind)
{
	//用于数据校验的临时对象
	document.form2.obj4numbercheck.value = newexportamount;
	if(document.form2.obj4numbercheck.value == null || document.form2.obj4numbercheck.value == '')
	{
		alert("件数不可为空");
		return '1';
	}
	if(IsFloat(document.form2.obj4numbercheck.value)==false)
	{
		alert("件数必须是浮点数");
		return '1';
	}
	if(newexportamount<=0)
	{
		alert("件数必须大于0");
		return '1';	
	}
	window.navigate("importGoodsBill.do?param=updateJianNum&kind="+kind+"&id="+id+"&importJianNum="+newexportamount);
}
function updateImportNum(id, newexportamount,kind)
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
	if(newexportamount<0)
	{
		alert("数量必须大于等于0");
		return '1';	
	}
	window.navigate("importGoodsBill.do?param=updateNum&kind="+kind+"&id="+id+"&importNum="+newexportamount);
}
function updateImportAmount(id, newexportamount,kind)
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
	window.navigate("importGoodsBill.do?param=updateAmount&kind="+kind+"&id="+id+"&importAmount="+newexportamount);
}
function updateImportPrice(id, newimportprice,kind)
{
	//用于数据校验的临时对象
	document.form2.obj4numbercheck.value = newimportprice;

	if(isFloat(document.form2.obj4numbercheck,'单价必须为金额类型')==false)
	{
		return '1';
	}
	if(Number(document.form2.obj4numbercheck.value)<=0)
	{
		alert("单价必须大于零");
		return '1';
	}
	else
	{
		window.navigate("importGoodsBill.do?param=updatePrice&kind="+kind+"&id="+id+"&importUnitPrice="+newimportprice);
		
	}
}
function updateImportMoney(id, newimportprice,kind)
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
		window.navigate("importGoodsBill.do?param=updateMoney&kind="+kind+"&id="+id+"&importMoney="+newimportprice);
		
	}
}
function updateMemo(id, newimportprice,kind)
{
	window.navigate("importGoodsBill.do?param=updateMemo&kind="+kind+"&id="+id+"&memo="+newimportprice);	
}
function deleteAllGoods(billId,kind)
{
	try{
		var goods_num = document.form2.goodsid.length;
		if(goods_num == null || document.form2.goodsid == null)
		{//
			//alert("空列表");
		}
	}catch(e)
	{
		alert("货物列表目前为空，无需清除！");
		return;
	}
	if(confirm("确定要清空该货物列表吗？")) 
	{
		window.navigate("importGoodsBill.do?param=delall&kind="+kind+"&billId="+billId);
	}
}

function deleteATempImportGoods(id,kind)
{
	if(confirm("确定要删除该货物吗？")) 
	{
		window.navigate("importGoodsBill.do?param=del&kind="+kind+"&id="+id);
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
		alert("请添加货物后再提交进货单");
		return;
	}
	if(form2.factory.value==0)
	{
		alert("请选择一个供应商");
		document.form2.factory.focus();
		return false;
	}
	if(form2.fpType.value==0)
	{
		alert("请选择发票类型");
		document.form2.fpType.focus();
		return false;
	}
	if(form2.fkType.value==0)
	{
		alert("请选择付款方式");
		document.form2.fkType.focus();
		return false;
	}
	
	if(Trim(form2.payLimTime.value)!='')
	{
		if(!isDate(document.form2.payLimTime))
		{//检查输入的时间是否合法
			return false;
		}
	}
	if(form2.yunFei.value>0 && form2.carNo.value=='')
	{
		alert("请输入一个车牌号");
		document.form2.carNo.focus();
		return false;
	}
	//检查数量和单价是否都为数字
	var errMsg;
	
	var amount_num = document.form2.importamount.length;
	var unitpr_num = document.form2.importunitprice.length;
	
	var isNumberFlage = 'yes';//默认为“是数字”
	if(amount_num == null) 
	{
		if(isFloat(document.form2.importamount)==false)
		{
			isNumberFlage = 'no';
		}
		if(isFloat(document.form2.importunitprice)==false)
		{
			isNumberFlage = 'no';
		}
		if(Number(document.form2.importamount.value)<=0)
		{
			alert('重量必须大于0');
			document.form2.importamount.focus();
			return false;
		}
		if(Number(document.form2.importunitprice.value)<=0)
		{
			alert('单价必须大于0');
			document.form2.importunitprice.focus();
			return false;
		}
	}
	else
	{
		
		for(iii=0; iii<amount_num; iii++)
		{
			//alert(document.form2.importamount[iii].value);
			if(isFloat(document.form2.importamount[iii])==false)
			{
				isNumberFlage = 'no';
			}
			
			//alert(document.form2.importunitprice[iii].value);
			if(isFloat(document.form2.importunitprice[iii])==false)
			{
				isNumberFlage = 'no';
			}
			if(Number(document.form2.importamount[iii].value)<=0)
			{
				alert('重量必须大于0');
				document.form2.importamount[iii].focus();
				return false;
			}
			if(Number(document.form2.importunitprice[iii].value)<=0)
			{
				alert('单价必须大于0');
				document.form2.importunitprice[iii].focus();
				return false;
			}
		}
	}
	if(isNumberFlage=='yes')
	{
		var msg='';
		
		if(form2.ieva.value==1)
			msg='此单为估价单，是否确认保存？'
		else
			msg='是否确认保存？';
		if(confirm(msg))
		{
			form2.target="_self";
			form2.param.value='submit';
			form2.submit();	
		}
	}
	else
	{//非数字
		alert('数量或单价必须为浮点数');
	}
}
function UpdateBill()
{
	form2.param.value='update';
	form2.submit();
}
function showModalWindow(factid,form,kind) 
{	
	window.showModalDialog('goodsSelect2.do?factid='+factid+'&kind='+kind,form,'dialogWidth:700px;dialogHeight:500px');
}
</script>
</head>
<%
	ImportGoodsBillFB igbfb = (ImportGoodsBillFB)request.getAttribute(Globals.REQUEST_IMPORTGOODSBILL);
	ImportBillForm ibf = igbfb.getIbf();
	List goodsList = igbfb.getGoodsList();
	List FactList = igbfb.getFactList();
	List fkList	= igbfb.getFkList();
	List fpList =igbfb.getFpctList();
	int kind=igbfb.getKind();
	int selectmode=igbfb.getSelectmode();
	String skind="";
	if(kind==1)
		skind="进货单";
	else
		skind="<font color=red>进货退货单</font>";
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);

%>

<body background='images/bgall.gif' onload="submitreal.focus();" onkeyup="if(event.keyCode==13) submitreal.focus();">
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
    <td width="379" height="20" class="mainhead"><%=skind %>录入  
    <%if(kind==1){ %>
    &nbsp;&nbsp;<font size=2><input type="radio" name="gujia" <%=(ibf.getIeva()==0?"checked":"") %> onClick="javascript:if(this.checked){form2.ieva.value=0;UpdateBill();};">正式单 
    <input type="radio" name="gujia" <%=(ibf.getIeva()==1?"checked":"") %> onclick="javascript:if(this.checked){form2.ieva.value=1;UpdateBill();};">估价单</font><%} %></td>
    <td class="mainhead" width="379" height="20"></td>
    <td width="5"></td>
  </tr>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td height="300" align="center" valign="top">   
          
	<br>
	<form name="form2" method="post" action="importBillSubmit.do" target="hideframe">
	
	<input type="hidden" name="billId"  value="<%=ibf.getBillId()%>"> 	
	<input type="hidden" name="obj4numbercheck" value="">
	<input type="hidden" name="param" value="submit">
	<input type="hidden" name="kind" value="<%=kind %>">
	<input type="hidden" name="ieva" value="<%=ibf.getIeva() %>">
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
          <tr>
          	<td  align="right" > 
                <b>*供应商:</b>
            </td>
            <td >
            	
            	<SELECT name="factory" onChange="UpdateBill();">
            		<option value="0" selected>请选择...</option>
            		<%
            		            	FactoryInfoForm tmpFact=null;
            		            	for(int i=0; i<FactList.size(); i++)
            		            	{
            		            		tmpFact = (FactoryInfoForm)FactList.get(i);
            		%>
            		<option value="<%=tmpFact.getId()%>" <%=(tmpFact.getId()==ibf.getFactory()?"selected":"") %>><%=(tmpFact.getName().length()>12?tmpFact.getName().substring(0,12):tmpFact.getName())%></option>
			<%
				}
			%>
            	</SELECT><input class=none type="text" name="zhujima" value="<%=igbfb.getZhujima() %>" size=5 onChange="window.navigate('importGoodsBill.do?kind=<%=kind %>&zhujima='+this.value);">(助记码,如"易通"为"yt")
            </td>
            <td  align="right" > 
                <b>*发票类型:</b>
            </td>
            <td >
            	<SELECT name="fpType" onChange="UpdateBill();">
            		<%
            		FuKuanForm fkf=null;
           		    for(int i=0; i<fpList.size(); i++)
           		    {
           		        fkf = (FuKuanForm)fpList.get(i);
            		%>
            		<option value="<%=fkf.getId()%>" <%=(fkf.getId()==ibf.getFpType()?"selected":"") %>><%=(fkf.getName().length()>12?fkf.getName().substring(0,12):fkf.getName())%></option>
			<%
				}
			%>
            	</SELECT>
            </td>
            
            <td  align="right"> 
                <b>*付款方式:</b>
            </td>
            <td >
            	<SELECT name="fkType" onChange="UpdateBill();">
            		<%
            		            	
            		            	for(int i=0; i<fkList.size(); i++)
            		            	{
            		            		fkf = (FuKuanForm)fkList.get(i);
            		%>
            		<option value="<%=fkf.getId()%>" <%=(fkf.getId()==ibf.getFkType()?"selected":"") %>><%=(fkf.getName().length()>12?fkf.getName().substring(0,10):fkf.getName())%></option>
			<%
				}
			%>
            	</SELECT>
            </td>
            
            
            
          </tr>
          <tr>
          	<td  align="right" > 
                <b>付款期限:</b>
            </td>
            <td >
		<input type=text name='payLimTime' size="15" class="none" value="<%=ibf.getPayLimTime() %>" onChange="UpdateBill();">
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="弹出日历菜单"  onClick="Calendar_paylimtime();">&nbsp; 
		&nbsp;&nbsp;<b>备注:</b>
		 	<INPUT class=none  name="memo" type="text" size="30" value="<%=ibf.getMemo() %>"  onChange="UpdateBill();">
		</td>
            <td  align="right" > 
                <b>运费:</b>
            </td>
            <td >
            	<INPUT class=none  name="yunFei" type="text" size="12" value="<%=ibf.getYunFei() %>" onKeyUp="InputFloat(this);" onChange="UpdateBill();">
            </td>
          	<td  align="right" > 
                <b>车牌号:</b>
            </td>
            <td >
            	<input class=none  name="carNo" value="<%=ibf.getCarNo() %>" type="text" size="12" onChange="UpdateBill();">
          
            </td>
            <td  align="right" > 
                <b></b>
            </td>

            </td>
          	
           

	</TABLE>
	<font color=#215385><b>货物列表</b></font>
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
		
		<tr bgcolor="#C2CEDC">
		<th></th>
		<th>仓库</th>
		<th>产品编号</th>
		<th>产品名称</th>
		<th>规格</th>
		<th>产地</th>
		<th>目前库存</th>
		<th>件数</th>
		<th>个数</th>
		<th>重量(吨)</th>
		<th>单价(元/吨)</th>
		<th>合计金额</th>
		<th>说明</th>
		<th>删除</th>
		</tr>
		
		
		<%
		int index = 0;
	    GoodsImportGoodsInfo tmpInfo = null;
	    double totalPrice = 0;
	    double totalnum = 0;
	      	
	    for (index=0; index<goodsList.size(); index++)
		{
	      		tmpInfo = (GoodsImportGoodsInfo)goodsList.get(index);
	      		totalnum=totalnum+tmpInfo.getImportAmount()*kind;
	      		totalPrice = totalPrice + tmpInfo.getImportUnitPrice()*tmpInfo.getImportAmount()*kind;
	    %>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        <td align='center' width=10><%= index+1 %></td>
	        <td align='left' width=60><%= tmpInfo.getStoreName() %></td>
	        <td align='center'><%= tmpInfo.getGoodsId() %>
	        <input type="hidden" name="goodsid"  value="<%= tmpInfo.getGoodsId() %>">
	        </td>
	        <td align='center' width=90><%= tmpInfo.getGoodsName() %></td>
	        <td align='center'><%= tmpInfo.getGuige() %></td>
	        <td align='left'><%= tmpInfo.getChandi() %></td>
	        <td align='center'><%= tmpInfo.getCurRepertoryAmount() %></td>
	        <td align='center'>
	        <%if(tmpInfo.getJianxishu()>0) {%>
	        	<input type="text" name="importjiannum" id="importjiannum"   value="<%= Math.abs(tmpInfo.getImportJianNum()*kind) %>" size=5 onfocus="this.select();"
	        	onChange="if(updateImportJianNum('<%=tmpInfo.getId()%>', this.value,<%=kind %>)=='1'){this.value='<%= Math.abs(tmpInfo.getImportJianNum()*kind) %>'}">
	        <%} %>
	        </td>
	        <td align='center'>
	        
	        	<input type="text" name="importnum" id="importnum"   value="<%= tmpInfo.getImportNum()*kind %>" size=5 onfocus="this.select();"
	        	onChange="if(updateImportNum('<%=tmpInfo.getId()%>', this.value,<%=kind %>)=='1'){this.value='<%= tmpInfo.getImportNum()*kind %>'}">
	       
	        </td>
	        <td align='center'>
	        	<input type="text" name="importamount" id="importamount"   value="<%=(tmpInfo.getImportAmount()==0?0:tmpInfo.getImportAmount()*kind) %>" size=8 onfocus="this.select();"
	        	onChange="if(updateImportAmount('<%=tmpInfo.getId()%>', this.value,<%=kind %>)=='1'){this.value='<%= (tmpInfo.getImportAmount()==0?0:tmpInfo.getImportAmount()*kind) %>'}">
	        </td>
		<td align='center'>
	        	<input type="text" name="importunitprice" id="importunitprice"   value="<%= tmpInfo.getImportUnitPrice() %>" size=8 onfocus="this.select();"
	        	onChange="if(updateImportPrice('<%=tmpInfo.getId()%>', this.value,<%=kind %>)=='1'){this.value='<%= tmpInfo.getImportUnitPrice() %>'}">
		</td>
	        <td align='right'>
	        <input type="text" name="importMoney" id="importMoney"   value="<%= oConvert.getRound(tmpInfo.getImportUnitPrice()*tmpInfo.getImportAmount()*kind,2) %>" size=8 onfocus="this.select();"
	        	onChange="if(updateImportMoney('<%=tmpInfo.getId()%>', this.value,<%=kind %>)=='1'){this.value='<%= oConvert.getRound(tmpInfo.getImportUnitPrice()*tmpInfo.getImportAmount()*kind,2)%>'}">
	        </td>
	        <td align='center'>
	        	<input type="text" name="memo" id="memo"   value="<%= tmpInfo.getMemo() %>" size=8 maxlength=25 onfocus="this.select();"
	        	onChange="if(updateMemo('<%=tmpInfo.getId()%>', this.value,<%=kind %>)=='1'){this.value='<%= tmpInfo.getMemo() %>'}">
			</td>	
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
            	<INPUT class=none  name="billid" type="text" size="20" value="<%=(ibf.getBillId()<0?"****":ibf.getBillId())%>" disabled>
            </td>
            <td  align="right" > 
                <b>总数量:</b>
            </td>
            <td >
            	<INPUT class=none  name="totalNum" type="text" size="15" value="<%=oConvert.getRound(totalnum,4)%>" disabled>
            </td>
            <td  align="right" > 
                <b>总金额:</b>
            </td>
            <td >
            	<INPUT class=none  name="totalPrice" type="text" size="15" value="<%=nf.format(oConvert.getRound(totalPrice,2))%>" disabled>
            </td>
            
            
          </tr>
     </TABLE>
	
	<form name="form1" method="post" action="importGoodsBill.do?param=add">
    	
    	<input type="hidden" name="billId"  value="<%=ibf.getBillId()%>">	
    	<TABLE align="center" width="98%" border="0" cellpadding="3" cellspacing="0">
			<tr>
			<td>
			<input type="radio" name="selectmode" value=0 onclick="bykucun.style.display='none';bychandi.style.display='';" <%=(selectmode==0?"checked":"")%>>按产地<br>
			<input type="radio" name="selectmode" value=1 onclick="bykucun.style.display='';bychandi.style.display='none';" <%=(selectmode==1?"checked":"")%>>按库存<br>
			</td>
			<td align="center" id="bychandi" style="display:<%=(selectmode==0?"":"none")%>">
			<iframe name="gs" id="gs" src="goodsSelect.do?goodsId=&storeid=0" width="700" height="25" frameborder=0 scrolling=no ></iframe>
				<input name="addgoods1" type="button" value=" 添加 " onclick="if(verify(form1)) form1.submit();">
			</td>
			<td align="left" id="bykucun" style="display:<%=(selectmode==1?"":"none")%>">
				 <input name="addgoods2" type="button" value=" 添加商品 " onclick="showModalWindow(form2.factory.value,form1,-1)">
			</td>
			</tr>
			<input type="hidden" name="goodsId" value="">
			<input type="hidden" name="storeId" value="">
			<input type="hidden" name="kind" value="<%=kind %>">
		</TABLE>

	</form>

	<p><center>
	<input name=deleteall type="button" value="清除此单" onClick="deleteAllGoods(<%=ibf.getBillId() %>,<%=kind %>)">
	&nbsp;&nbsp;&nbsp;&nbsp;
	<input name=submitreal type="button" value="确认提交" onClick="submitAllGoods()">
    	</center><p>
	<br>
  	<iframe name="hideframe" id="hideframe" src="" width="0" height="0" frameborder=0 scrolling=no></iframe> 
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

