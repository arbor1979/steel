<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>
<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js"></script>
<script language="JavaScript">


function updateEvaPrice(billid,id, newimportprice)
{
	//��������У�����ʱ����
	document.form2.obj4numbercheck.value = newimportprice;
	if(isFloat(document.form2.obj4numbercheck,'���۱���Ϊ�������')==false)
	{
		return '1';
	}
	if(Number(document.form2.obj4numbercheck.value)<=0)
	{
		alert("���۱��������");
		return '1';
	}
	else
	{
		window.location="evaPriceHandle.do?billId="+billid+"&param=updatePrice&id="+id+"&evaPrice="+newimportprice;
		
	}
}
function updateEvaMoney(billid,id, newimportprice)
{
	//��������У�����ʱ����
	document.form2.obj4numbercheck.value = newimportprice;

	if(isFloat(document.form2.obj4numbercheck,'������Ϊ��������')==false)
	{
		return '1';
	}
	if(Number(document.form2.obj4numbercheck.value)<=0)
	{
		alert("�����������");
		return '1';
	}
	else
	{
		window.location="evaPriceHandle.do?billId="+billid+"&param=updateMoney&id="+id+"&evaMoney="+newimportprice;
		
	}
}
function deleteAllGoods(billId)
{
	if(billId<=0)
		alert("û����ʾ��ⵥ");
	else
		window.location="evaPriceHandle.do?param=delall&billId="+billId;
}

function submitAllGoods()
{
	
	if(form2.billId.value==0)
	{
		alert("��ѡ��һ����ⵥ");
		document.form1.billId.focus();
		return false;
	}
	
	//��������͵����Ƿ�Ϊ����
	var errMsg;
	
	var amount_num = document.form2.evaPrice.length;
	var unitpr_num = document.form2.evaMoney.length;
	var isNumberFlage = 'yes';//Ĭ��Ϊ�������֡�
	if(amount_num == null) 
	{
		if(isFloat(document.form2.evaPrice)==false)
		{
			isNumberFlage = 'no';
		}
		if(isFloat(document.form2.evaMoney)==false)
		{
			isNumberFlage = 'no';
		}
		if(Number(document.form2.evaPrice.value)<=0)
		{
			alert("ʵ�ʵ��۱������0");
			document.form2.evaPrice.focus();
			return false;
		}
		if(Number(document.form2.evaMoney.value)<=0)
		{
			alert("ʵ�ʽ��������0");
			document.form2.evaMoney.focus();
			return false;
		}
	}
	else
	{
		
		for(iii=0; iii<amount_num; iii++)
		{
			//alert(document.form2.importamount[iii].value);
			if(isFloat(document.form2.evaPrice[iii])==false)
			{
				isNumberFlage = 'no';
			}
			
			//alert(document.form2.importunitprice[iii].value);
			if(isFloat(document.form2.evaMoney[iii])==false)
			{
				isNumberFlage = 'no';
			}
			if(Number(document.form2.evaPrice[iii].value)<=0)
			{
				alert("ʵ�ʵ��۱������0");
				document.form2.evaPrice[iii].focus();
				return false;
			}
			if(Number(document.form2.evaMoney[iii].value)<=0)
			{
				alert("ʵ�ʽ��������0");
				document.form2.evaMoney[iii].focus();
				return false;
			}
		}
	}
	if(isNumberFlage=='yes')
	{
		if(confirm('����ǰ���ٴκ˲鵥�ۼ����Ƿ�ȷ�ϱ��棿'))
		{
			form2.param.value='submit';
			form2.submit();	
		}
	}
	else
	{//������
		alert('�����򵥼۱���Ϊ������');
	}
}
</script>
</head>
<%
	ImportGoodsBillFB igbfb = (ImportGoodsBillFB)request.getAttribute(Globals.REQUEST_IMPORTGOODSBILL);
	ImportBillForm ibf = igbfb.getIbf();
	List goodsList = igbfb.getGoodsList();
	List billIdList=igbfb.getFactList();
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);

%>

<body background='images/bgall.gif' onload="form1.billId.focus();">
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
    <td width="379" height="20" class="mainhead">���۵�����</td>
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
	<form name="form1" method="post" action="evaPriceHandle.do" >
    	
    	
    	<TABLE align="center" width="100%" border="0" cellpadding="3" cellspacing="0"
					>

			<tr>
				<td width=85 align="right"><b>���۵���:</b></td>
				<td>
            	<SELECT name="billId" onChange="form1.submit();">
            		<option value="0">-ȫ��-</option>
            		<%
            		            	String tmpStr;
            		            	for(int i=0; i<billIdList.size(); i++)
            		            	{
            		            		tmpStr = (String)billIdList.get(i);
            		%>
            		<option value="<%=tmpStr%>" <%=(tmpStr.equals(String.valueOf(ibf.getBillId()))?"selected":"") %>><%=tmpStr%></option>
			<%
			}
			%>
            	</SELECT>
            </td>
			
			</tr>
		</TABLE>

	</form>
	
	<form name="form2" method="post" action="evaPriceHandle.do" >
	<input type="hidden" name="billId"  value="<%=ibf.getBillId()%>"> 	
	<input type="hidden" name="obj4numbercheck" value="">
	<input type="hidden" name="param" value="submit">	
	<TABLE width="100%" cellpadding="3" > 
          <tr>
          	<td  align="right" width=10%> 
                <b>��Ӧ��:</b>
            </td>
            <td colspan=3><input type="text" size=52 class="none" value="<%=ibf.getFactoryName() %>" disabled></td>
            <td  align="right" width=10%> 
                <b>��Ʊ����:</b>
            </td>
            <td><input type="text" size=15 class="none" value="<%=ibf.getFpName() %>" disabled></td>
            
            <td  align="right" width=10%> 
                <b>���ʽ:</b>
            </td>
            <td><input type="text" size=15 class="none" value="<%=ibf.getFkName() %>" disabled> </td>
          </tr>
          <tr>
          	<td  align="right" > 
                <b>��������:</b>
            </td>
            <td ><input type="text" size=15 class="none" value="<%=ibf.getPayLimTime() %>" disabled></td>
            <td align="right" width=10%>
            	<b>��ע˵��:</b>
            </td>
            <td ><input type="text" size=15 class="none" value="<%=ibf.getMemo() %>" disabled></td>
            <td  align="right" > 
                <b>�˷�:</b>
            </td>
            <td ><input type="text" size=15 class="none" value="<%=(ibf.getYunFei()>0?ibf.getYunFei():"") %>" disabled></td>
          	<td  align="right" > 
                <b>���ƺ�:</b>
            </td>
            <td ><input type="text" size=15 class="none" value="<%=ibf.getCarNo() %>" disabled> </td>
            <td  align="right" > 
                <b></b>
            </td>
            </td>

	</TABLE>
	<br>
	<font color=#215385><b>�����б�</b></font>
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE  class="mailtable">  
		
		<tr bgcolor="#C2CEDC">
		<th></th>
		<th>�ֿ�</th>
		<th>��Ʒ���</th>
		<th>��Ʒ����</th>
		<th>���</th>
		<th>����</th>
		<th>����</th>
		<th>����</th>
		<th>����(��)</th>
		<th>����(Ԫ/��)</th>
		<th>���۽��</th>
		<th>ʵ�ʵ���</th>
		<th>ʵ�ʽ��</th>
		<th>���</th>
		</tr>
		
		
		<%
		int index = 0;
	    GoodsImportGoodsInfo tmpInfo = null;
	    double totalPrice = 0;
	    double totalPrice1 = 0;
	      	
	    for (index=0; index<goodsList.size(); index++)
		{
	      		tmpInfo = (GoodsImportGoodsInfo)goodsList.get(index);
	      		totalPrice = totalPrice + tmpInfo.getImportUnitPrice()*tmpInfo.getImportAmount();
	      		totalPrice1 = totalPrice1 + tmpInfo.getEvaPrice()*tmpInfo.getImportAmount();
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
	        <td align='center'><%=(tmpInfo.getJianxishu()>0?tmpInfo.getImportJianNum():"") %></td>
	        <td align='center'><%=(tmpInfo.getXishu()>0?tmpInfo.getImportNum():"") %></td>
	        <td align='center'><%=tmpInfo.getImportAmount() %></td>
			<td align='center'><%=tmpInfo.getImportUnitPrice() %></td>
	        <td align='right'><%=oConvert.getRound(tmpInfo.getImportUnitPrice()*tmpInfo.getImportAmount(),2) %></td>
	        <td align='center'>
	        	<input type="text" name="evaPrice" id="evaPrice"   value="<%= oConvert.getRound(tmpInfo.getEvaPrice(),2) %>" size=8 onfocus="this.select();"
	        	onChange="if(updateEvaPrice(<%=ibf.getBillId() %>,'<%=tmpInfo.getId()%>', this.value)=='1'){this.value='<%= oConvert.getRound(tmpInfo.getEvaPrice(),2)%>'}">
			</td>
	        <td align='right'>
	        <input type="text" name="evaMoney" id="evaMoney"   value="<%= oConvert.getRound(tmpInfo.getEvaPrice()*tmpInfo.getImportAmount(),2) %>" size=8 onfocus="this.select();"
	        	onChange="if(updateEvaMoney(<%=ibf.getBillId() %>,'<%=tmpInfo.getId()%>', this.value)=='1'){this.value='<%= oConvert.getRound(tmpInfo.getEvaPrice()*tmpInfo.getImportAmount(),2)%>'}">
	        </td>
	        <td align='right'><%=oConvert.getRound((tmpInfo.getImportUnitPrice()-tmpInfo.getEvaPrice())*tmpInfo.getImportAmount(),2) %></td>
	        </tr>
	        <%
	
	      	}
	        %>
	</TABLE>
	</form>
	
	<TABLE  class="mailtable"> 
	  <tr>
            <td  align="right" > 
                <b>����:</b>
            </td>
            <td >
            	<INPUT class=none  name="billid" type="text" size="20" value="<%=ibf.getBillId()%>" disabled>
            </td>
            <td  align="right" > 
                <b>���۽��:</b>
            </td>
            <td >
            	<INPUT class=none  name="totalPrice" type="text" size="15" value="<%=nf.format(oConvert.getRound(totalPrice,2))%>" disabled>
            </td>
            <td  align="right" > 
                <b>ʵ�ʽ��:</b>
            </td>
            <td >
            	<INPUT class=none  name="totalPrice" type="text" size="15" value="<%=nf.format(oConvert.getRound(totalPrice1,2))%>" disabled>
            </td>
            <td  align="right" > 
                <b>�ܲ��:</b>
            </td>
            <td >
            	<INPUT class=none  name="totalPrice" type="text" size="15" value="<%=nf.format(oConvert.getRound(totalPrice-totalPrice1,2))%>" disabled>
            </td>
          </tr>
     </TABLE>
	
	

	<p><center><input name=giveup type="button" value="��������" onClick="deleteAllGoods(<%=ibf.getBillId() %>)">&nbsp;&nbsp;&nbsp;
	<input name=submitreal type="button" value="ȷ���ύ" onClick="submitAllGoods()">
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

