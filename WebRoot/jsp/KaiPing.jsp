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

function verify(form)
{
	if(form1.kind.value==-1)
	{
		if(gs1.form1.goodsId.value=='')
		{
			alert('��ѡ��Դ����Ʒ');
			gs1.form1.goodsId.focus();
			return false;
		}
		if(gs1.form1.storeid.value=='0')
		{
			alert('��ѡ��Դ����Ʒ���ڲֿ�');
			gs1.form1.storeid.focus();
			return false;
		}
	}
	else if(form1.kind.value==1)
	{
		if(gs1.form1.goodsId.value=='')
		{
			alert('��ѡ��ӹ�����Ʒ');
			gs2.form1.goodsId.focus();
			return false;
		}
		if(gs1.form1.storeid.value=='0')
		{
			alert('��ѡ��ӹ�����Ʒ���ڲֿ�');
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
	//��������У�����ʱ����
	document.form2.obj4numbercheck.value = newexportamount;
	if(document.form2.obj4numbercheck.value == null || document.form2.obj4numbercheck.value == '')
	{
		alert("��������Ϊ��");
		return '1';
	}
	if(IsInteger(document.form2.obj4numbercheck.value)==false)
	{
		alert("��������������");
		return '1';
	}
	
	if(Number(newexportamount)<=0)
	{
		alert("�����������0");
		return '1';	
	}	
	window.navigate("kaiPing.do?param=updateFromNum&id="+id+"&fromNum="+newexportamount);
}


function updateFromAmount(id, newexportamount)
{
	//��������У�����ʱ����
	document.form2.obj4numbercheck.value = newexportamount;
	if(document.form2.obj4numbercheck.value == null || document.form2.obj4numbercheck.value == '')
	{
		alert("��������Ϊ��");
		return '1';
	}
	if(IsFloat(document.form2.obj4numbercheck.value)==false)
	{
		alert("���������Ǹ�����");
		return '1';
	}
	
	if(Number(newexportamount)<=0)
	{
		alert("�����������0");
		return '1';	
	}
	
	window.navigate("kaiPing.do?param=updateFromAmount&id="+id+"&fromAmount="+newexportamount);
}
function updateMoney(id, newimportprice)
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
		window.navigate("kaiPing.do?param=updateMoney&id="+id+"&jine="+newimportprice);
		
	}
}
function updatePrice(id, newimportprice,fromAmount)
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
		window.navigate("kaiPing.do?param=updatePrice&id="+id+"&fromPrice="+newimportprice+"&fromAmount="+fromAmount);
		
	}
}
function deleteATempImportGoods(id)
{
	if(confirm("ȷ��Ҫɾ���ü�¼��")) 
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
			//alert("���б�");
		}
	}catch(e)
	{
		alert("�б�ĿǰΪ��");
		return;
	}
	
	if(confirm("ȷ��Ҫ��ո��б���")) 
	{
		window.navigate("kaiPing.do?param=delAll");
	}
}

function submitAllGoods()
{
	
	//�鿴�õ��Ƿ��ǿ��б����б�Ͳ��ύ
	try{
		var goods_num = document.form2.goodsid.length;
		if(goods_num == null || document.form2.goodsid == null)
		{//
			//alert("���б�");
		}
	}catch(e)
	{
		alert("����ӻ�������ύ");
		return;
	}
	
	//��������͵����Ƿ�Ϊ����
	var amount_num= document.form2.goodsid.length;

	//amount_num�п������������amount_num>0
	if(amount_num == null)
	{//ֻ��һ��
		var isNumberFlage = 'yes';//Ĭ��Ϊ�������֡�
		
		if(isFloat(document.form2.fromamount)==false)
		{
			isNumberFlage = 'no';
		}
		if(Number(document.form2.fromamount.value)<=0)
		{
			alert('�������������');
			document.form2.fromamount.focus();
			return false;
		}
		
		if(isNumberFlage=='yes')
		{//����
			
			if(confirm('ȷ�ϱ���˵��ݣ�')) 
			{
				document.form2.submit();
			}
		}

	}
	else
	{//��������
	
		var isNumberFlage = 'yes';//Ĭ��Ϊ�������֡�
		for(iii=0; iii<amount_num; iii++)
		{
			if(isFloat(document.form2.fromamount[iii])==false)
			{
				isNumberFlage = 'no';
			}
			if(Number(document.form2.fromamount[iii].value)<=0)
			{
				alert('�������������');
				document.form2.fromamount[iii].focus();
				return false;
			}
				
		}

		if(isNumberFlage=='yes')
		{//����
			if(confirm('ȷ�ϱ���˵��ݣ�')) 
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
		<td width="379" height="20" class="mainhead">��Ʒ���μӹ�</td>
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
          	<b>��ע˵��:</b>
          </td>
          <td>
		 	<INPUT class=none  name="memo" type="text" size="30" value="<%=cbf.getMemo() %>"  onChange="UpdateBill();">
		  </td>
         
       </tr>   	
     
	</TABLE>
	<font color=#215385><b>�����б�</b></font>
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
		
		<tr bgcolor="#C2CEDC">
		<th></th>
		<th >����</th>
		<th>��Ʒ���</th>
		<th>��Ʒ����</th>
		<th>���</th>
		<th>����</th>
		<th>��ǰ���</th>
		<th>����</th>
		<th>����(��)</th>
		<th >�ɱ���(Ԫ)</th>
		<th >���(Ԫ)</th>
		<th>ɾ��</th>
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
	        <td align='center' ><%=(tmpInfo.getKind()==-1?"<font color=green>ԭ��</font>":"<font color=blue>��Ʒ</font>") %></td>
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
                <b>Դ������:</b>
            </td>
            <td >
            	<INPUT class=none  name="totalNum" type="text" size="12" value="<%=oConvert.getRound(totalnum1,3)%>" disabled>(��)
            </td>
            <td  align="right" > 
                <b>��Ʒ����:</b>
            </td>
            <td >
            	<INPUT class=none  name="totalNum" type="text" size="12" value="<%=oConvert.getRound(totalnum2,3)%>" disabled>(��)
            </td>
            <td  align="right" > 
                <b>ԭ�Ͻ��:</b>
            </td>
            <td >
            	<INPUT class=none  name="totalPrice" type="text" size="12" value="<%=nf.format(oConvert.getRound(totalPrice1,2))%>" disabled>(Ԫ)
            </td>
            <td><%=(totalPrice1==totalPrice2?"<font color=green>=</font>":"<font color=red>��</font>") %></td>
            <td  align="right" > 
                <b>��Ʒ���:</b>
            </td>
            <td >
            	<INPUT class=none  name="totalPrice" type="text" size="12" value="<%=nf.format(oConvert.getRound(totalPrice2,2))%>" disabled>(Ԫ)
            </td>
            
          </tr>
          	
     
	</TABLE>
	<br>
    <form name="form1" method="post" action="kaiPing.do?param=add" onSubmit="javascript:return verify(this);">
    	
    	<TABLE align="center" width="98%" border="0" cellpadding="3" cellspacing="0">

			<tr>
			<td>
				<input type="radio" name="selectmode" value=0 onclick="bykucun.style.display='none';bychandi.style.display='';" <%=(selectmode==0?"checked":"")%>>������<br>
				<input type="radio" name="selectmode" value=1 onclick="bykucun.style.display='';bychandi.style.display='none';" <%=(selectmode==1?"checked":"")%>>�����<br>
			</td>
			<td align="center" id="bychandi" style="display:<%=(selectmode==0?"":"none")%>">
				<iframe name="gs1" id="gs1" src="goodsSelect.do?goodsid=&typeid=0&storeid=0" width="680" height="25" frameborder=0 scrolling=no ></iframe>
				<input name="addgoods1" type="button" value=" ��� " onclick="if(verify(form1)) form1.submit();">
				</td>
				<td align="left" id="bykucun" style="display:<%=(selectmode==1?"":"none")%>">
				 <input name="addgoods2" type="button" value=" �����Ʒ " onclick="showModalWindow(form1)">
				</td>
				<td>
				<input type="radio" name="kind" value="-1" <%=(cbf.getKind()==-1?"checked":"") %>>���ԭ��<br>
				<input type="radio" name="kind" value="1" <%=(cbf.getKind()==1?"checked":"") %>>��ӳ�Ʒ<br>
				</td>
			</tr>
			<input type="hidden" name="billid" value="<%=cbf.getBillid() %>">
			<input type="hidden" name="goodsId" value="">
			<input type="hidden" name="storeId" value="">
		</TABLE>

	</form>
		
    	
	
	
	<p><center>
	<input name=deleteall type="button" value="����б�" onClick="deleteAllGoods()">
	&nbsp;&nbsp;<input name=submitall id=submitall type="button" value=" ��  �� " onClick="submitAllGoods();">
	
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


