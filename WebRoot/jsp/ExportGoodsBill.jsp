<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.text.DateFormat"%>
<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<script language="JavaScript">

function verify(form)
{
	if(gs.form1.goodsId.value=='')
	{
		alert('��ѡ��Ҫ���۵���Ʒ');
		gs.form1.goodsId.focus();
		return false;
	}
	if(gs.form1.storeid.length==0)
	{
		alert('���ȴ���һ���ֿ�');
		return false;
	}
	if(gs.form1.storeid.value=='0')
	{
		alert('��ѡ�����ڲֿ�');
		gs.form1.storeid.focus();
		return false;
	}
	form.goodsId.value=gs.form1.goodsId.value;
	form.storeId.value=gs.form1.storeid.value;
	return true;
}

function updateExportJianNum(id, newexportamount,kind)
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
	if(newexportamount<0)
	{
		alert("����������ڵ���0");
		return '1';	
	}
	location.href=("exportGoodsBill.do?param=updateJianNum&kind="+kind+"&id="+id+"&exportJianNum="+newexportamount);
}
function updateExportNum(id, newexportamount,kind)
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
	if(newexportamount<0)
	{
		alert("����������ڵ���0");
		return '1';	
	}
	location.href=("exportGoodsBill.do?param=updateNum&kind="+kind+"&id="+id+"&exportNum="+newexportamount);
}
function updateExportAmount(id, newexportamount,kind)
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
	if(Number(newexportamount)<0)
	{
		alert("����������ڵ���0");
		return '1';	
	}
	location.href=("exportGoodsBill.do?param=updateAmount&kind="+kind+"&id="+id+"&exportAmount="+newexportamount);
}
function updateExportPrice(id, newimportprice,avgprice,kind)
{
	//��������У�����ʱ����
	document.form2.obj4numbercheck.value = newimportprice;

	if(isFloat(document.form2.obj4numbercheck,'���۱���Ϊ�������')==false)
	{
		return '1';
	}
	if(Number(document.form2.obj4numbercheck.value)<=0)
	{
		alert("���۱������0");
		return '1';
	}
	else
	{
		if(Number(document.form2.obj4numbercheck.value)<avgprice)
		{
			if(!confirm("���ۼ۵��ڳɱ��ۣ��Ƿ������"))
				return '1';
		}
		location.href=("exportGoodsBill.do?param=updatePrice&kind="+kind+"&id="+id+"&exportUnitPrice="+newimportprice);
		
	}
}
function updateJiaGong(id, newimportprice,kind)
{
	//��������У�����ʱ����
	document.form2.obj4numbercheck.value = newimportprice;
	if(isFloat(document.form2.obj4numbercheck,'����Ϊ�������')==false)
	{
		return '1';
	}
	if(Number(document.form2.obj4numbercheck.value)<0)
	{
		alert("�������0");
		return '1';
	}
	else
	{
		location.href=("exportGoodsBill.do?param=updateJiaGong&kind="+kind+"&id="+id+"&jiagong="+newimportprice);	
	}
}
function updateMemo(id, newimportprice,kind)
{
	location.href=("exportGoodsBill.do?param=updateMemo&kind="+kind+"&id="+id+"&memo="+newimportprice);	
}
function deleteATempImportGoods(id,kind)
{
	if(confirm("ȷ��Ҫɾ���û�����")) 
	{
		location.href=("exportGoodsBill.do?param=del&kind="+kind+"&id="+id);
	}
}

function deleteAllGoods(billid,kind)
{
	try{
		var goods_num = document.form2.goodsid.length;
		if(goods_num == null || document.form2.goodsid == null)
		{//
			//alert("���б�");
		}
	}catch(e)
	{
		alert("�����б�ĿǰΪ�գ����������");
		return;
	}
	if(confirm("ȷ��Ҫ����˵���")) 
	{
		location.href=("exportGoodsBill.do?param=delAll&kind="+kind+"&billId="+billid);
	}
}

function submitAllGoods(test)
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
		alert("����ӻ�������ύ����");
		return;
	}

	if(form2.factory.value==0)
	{
		alert("��ѡ��һ���ͻ�");
		document.form2.factory.focus();
		return false;
	}
	if(test=='1')
	{
		if(form2.fpType.value==0)
		{
			alert("��ѡ��Ʊ����");
			document.form2.fpType.focus();
			return false;
		}
		if(form2.fkType.value==0)
		{
			alert("��ѡ�񸶿�����");
			document.form2.fkType.focus();
			return false;
		}
		
		if(Trim(form2.payLimTime.value)=='')
		{
			alert("�����븶������");
			document.form2.payLimTime.focus();
			return false;
		}
		if(!isDate(document.form2.payLimTime))
		{//��������ʱ���Ƿ�Ϸ�
			return false;
		}
		
	}
	if(form2.yunFei.value>0 && form2.carNo.value=='')
	{
		alert("������һ�����ƺ�");
		document.form2.carNo.focus();
		return false;
	}
	//��������͵����Ƿ�Ϊ����
	var amount_num= document.form2.exportamount.length;

	//amount_num�п������������amount_num>0
	if(amount_num == null)
	{//ֻ��һ��
		var isNumberFlage = 'yes';//Ĭ��Ϊ�������֡�
		
		if(isFloat(document.form2.exportamount)==false)
		{
			isNumberFlage = 'no';
		}
		if(isFloat(document.form2.exportunitprice)==false)
		{
			isNumberFlage = 'no';
		}
		if(Number(document.form2.exportamount.value)<0)
		{
			alert('����������ڵ���0');
			document.form2.exportamount.focus();
			return false;
		}
		if(Number(document.form2.exportunitprice.value)<=0)
		{
			alert('���۱������0');
			document.form2.exportunitprice.focus();
			return false;
		}
		if(isNumberFlage=='yes')
		{//����
			
			var msg;
			if(test==2)
				msg='ȷ�ϱ���Ϊ ���򵥣�';
			else
				msg='ȷ�ϱ���Ϊ ��ʽ����';
			if(confirm(msg)) 
			{
				document.form2.confirmFlage.value=test;
				form2.target="_self";
				form2.param.value='submit';
				document.form2.submit();
			}
		}

	}
	else
	{//��������
	
		var isNumberFlage = 'yes';//Ĭ��Ϊ�������֡�
		for(iii=0; iii<amount_num; iii++)
		{
			if(isFloat(document.form2.exportamount[iii])==false)
			{
				isNumberFlage = 'no';
			}
			if(isFloat(document.form2.exportunitprice[iii])==false)
			{
				isNumberFlage = 'no';
			}
			if(Number(document.form2.exportamount[iii].value)<0)
			{
				alert('����������ڵ���0');
				document.form2.exportamount[iii].focus();
				return false;
			}
			if(Number(document.form2.exportunitprice[iii].value)<=0)
			{
				alert('���۱������0');
				document.form2.exportunitprice[iii].focus();
				return false;
			}
		}

		if(isNumberFlage=='yes')
		{//����
			var msg;
			if(test==2)
				msg='ȷ�ϱ���Ϊ ���򵥣�';
			else
				msg='ȷ�ϱ���Ϊ ��ʽ����';
			if(confirm(msg)) 
			{
				document.form2.confirmFlage.value=test;
				form2.target="_self";
				form2.param.value='submit';
				document.form2.submit();
			}
		}
		
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
  
<%
	ExportGoodsBillFB egbfb = (ExportGoodsBillFB)request.getAttribute(Globals.REQUEST_EXPORTGOODSBILL);
	ExportBillForm ibf = egbfb.getEbf();
	List goodsList = egbfb.getGoodsList();
	List FactList = egbfb.getFactList();
	List fkList	= egbfb.getFkList();
	List fpList =egbfb.getFpList();
	List ywList=egbfb.getYwList();
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
	int kind=egbfb.getKind();
	int selectmode=egbfb.getSelectmode();
	String skind="";
	if(kind==1)
		skind="���۵�";
	else
		skind="<font color=red>�����˻���</font>";

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
		<td width="379" height="20" class="mainhead"><%=skind %>¼��</td>
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

	
	<form name="form2" method="post" action="exportBillSubmit.do" target="hideframe">
	
	<input type="hidden" name="billId"  value="<%=ibf.getBillId()%>">
	<input type="hidden" name="confirmFlage"  value="">
	<input type="hidden" name="param" value="submit">
	<input type="hidden" name="obj4numbercheck" value="">
	<input type="hidden" name="kind"  value="<%=kind%>">    	
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	 <tr>
          	<td  align="right" > 
                <b>*�ͻ�����:</b>
            </td>
            <td >
            	<SELECT name="factory" onChange="UpdateBill();">
            		<option value="0">��ѡ��...</option>
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
            	</SELECT><input class=none type="text" name="zhujima" value="<%=egbfb.getZhujima() %>" size=5 onChange="location.href=('exportGoodsBill.do?kind=<%=kind %>&zhujima='+this.value);">(������)
            </td>
      		<td  align="right" > 
                <b>ҵ��Ա:</b>
            </td>
            <td >
                  <SELECT name="yewuid" onChange="UpdateBill();">
            		<option value="0">��˾</option>
            		<%
            						YeWuInfoForm tmpYw=null;
            		            	for(int i=0; i<ywList.size(); i++)
            		            	{
            		            		tmpYw = (YeWuInfoForm)ywList.get(i);
            		%>
            		<option value="<%=tmpYw.getId()%>" <%=(tmpYw.getId()==ibf.getYewuid()?"selected":"") %>><%=tmpYw.getName()%></option>
			<%
				}

			%>
            	</SELECT>
            </td>
             <td  align="right" > 
                <b>*��Ʊ����:</b>
            </td>
            <td >
            	<SELECT name="fpType" onChange="UpdateBill();">
            		<%
            		FuKuanForm fkf=null;
           		    for(int i=0; i<fpList.size(); i++)
           		    {
           		        fkf = (FuKuanForm)fpList.get(i);
            		%>
            		<option value="<%=fkf.getId()%>" <%=(fkf.getId()==ibf.getFpType()?"selected":"") %>><%=(fkf.getName().length()>12?fkf.getName().substring(0,10):fkf.getName())%></option>
			<%
				}
			%>
            	</SELECT>
            </td>
            
            <td  align="right"> 
                <b>*�տʽ:</b>
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
                <b>*�տ�����:</b>
            </td>
            <td >
         <%
         	DateFormat dt1=DateFormat.getDateTimeInstance();
         	java.util.Date dt2=new Date();
         	if(ibf.getPayLimTime().equals("1900-01-01"))
         		ibf.setPayLimTime("");
         	//if(ibf.getPayLimTime().length()>0 && dt2.after(dt1.parse(ibf.getPayLimTime()+" 23:59:59")))
         	//ibf.setPayLimTime(oConvert.FormDateShort(dt2));
         %>
		<input type=text name='payLimTime' size="15" class="none" value="<%=ibf.getPayLimTime() %>" onChange="UpdateBill();">
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="���������˵�"  onClick="Calendar_paylimtime();">&nbsp; 
		&nbsp;&nbsp;<b>��ע:</b>
		 	<INPUT class=none  name="memo" type="text" size="15" value="<%=ibf.getMemo() %>"  onChange="UpdateBill();">
		 	</td>
		 	<%if(kind==1){ %>
		 	<td align="right"><b>�����ʽ:</b></td>
		 	<td>
		 	<select name='tihuo' onChange="UpdateBill();">
		 	<option value='����' <%=(ibf.getTihuo().equals("����")?"selected":"") %>>����</option>
		 	<option value='ί��' <%=(ibf.getTihuo().equals("ί��")?"selected":"") %>>ί��</option>
		 	</select></td>
		 	<%}else{ %>
		 	<td colspan=2></td>
		 	<%} %>
		</td>
          <td  align="right" > 
                <b>�˷�:</b>
            </td>
            <td >
            	<INPUT class=none  name="yunFei" type="text" size="12" value="<%=ibf.getYunFei() %>" onKeyUp="InputFloat(this);" onChange="UpdateBill();">
            </td>
          	<td  align="right" > 
                <b>���ƺ�:</b>
            </td>
            <td >
   	            	<input class=none  name="carNo" value="<%=ibf.getCarNo() %>" type="text" size="12" onChange="UpdateBill();">
            </td>
            <td  align="right" > 
                <b></b>
            </td>
       </tr>   	
     
	</TABLE>
	
	<font color=#215385><b>�����б�</b></font>
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
	
		<tr bgcolor="#C2CEDC">
		<th></th>
		<th>�ֿ�</th>
		<th>��Ʒ���</th>
		<th>��Ʒ����</th>
		<th>���</th>
		<th>����</th>
		<th>ʣ����</th>
		<th>����</th>
		<th>����</th>
		<th>����(��)</th>
		<th>����(Ԫ/��)</th>
		<th>������</th>
		<%if(ctx.isIfjiagong() && kind==1){ %>
		<th>�ӹ���</th>
		<%} %>
		<th>˵��</th>
		<th>ɾ��</th>
		</tr>

		
		<%

		int index = 0;
		GoodsExportGoodsInfo tmpInfo = null;
	    double totalPrice = 0;
	    double totalJiaGong = 0;
	    double totalnum = 0;
	      	
	    for (index=0; index<goodsList.size(); index++)
		{
	      		tmpInfo = (GoodsExportGoodsInfo)goodsList.get(index);
	      		totalnum=totalnum+tmpInfo.getExportAmount()*kind;
	      		totalPrice = totalPrice + tmpInfo.getExportUnitPrice()*tmpInfo.getExportAmount()*kind;
	      		totalJiaGong = totalJiaGong + tmpInfo.getJiagong();
	    %>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        <td align='center' width=10><%=index+1 %></td>
	        <td align='center' width=60><%= tmpInfo.getStoreName() %></td>
	        <td align='center'><%= tmpInfo.getGoodsId() %>
	        <input type="hidden" name="goodsid"  value="<%= tmpInfo.getGoodsId() %>">
	        </td>
	        <td align='center' width=90><%= tmpInfo.getGoodsName() %></td>
	        <td align='center'><%= tmpInfo.getGuige() %></td>
	        <td align='left'><%= tmpInfo.getChandi() %></td>
	        <td align='center'><%= tmpInfo.getCurRepertoryAmount() %></td>
	        <td align='center'>
	        	<%if(tmpInfo.getJianxishu()>0){ %>
	        	<input type="text" name="exportjiannum" id="exportjiannum"   value="<%= Math.abs(tmpInfo.getExportJianNum()*kind) %>" size=5 onfocus="this.select();"
	        	onChange="if(updateExportJianNum('<%=tmpInfo.getId()%>', this.value,<%=kind %>)=='1'){this.value='<%= Math.abs(tmpInfo.getExportJianNum()*kind) %>'}">
	        	<%} %>
	        </td>
	        <td align='center'>
	        	<input type="text" name="exportnum" id="exportnum"   value="<%= tmpInfo.getExportNum()*kind %>" size=5 onfocus="this.select();"
	        	onChange="if(updateExportNum('<%=tmpInfo.getId()%>', this.value,<%=kind %>)=='1'){this.value='<%= tmpInfo.getExportNum()*kind %>'}">
	        </td>
	        <td align='center'>
	        	<input type="text" name="exportamount" id="exportamount"   value="<%= (tmpInfo.getExportAmount()==0?0:tmpInfo.getExportAmount()*kind) %>" size=8 onfocus="this.select();"
	        	onChange="if(updateExportAmount('<%=tmpInfo.getId()%>', this.value,<%=kind %>)=='1'){this.value='<%= (tmpInfo.getExportAmount()==0?0:tmpInfo.getExportAmount()*kind) %>'}">
	        </td>
			<td align='center'>
	        	<input type="text" name="exportunitprice" id="exportunitprice"   value="<%= tmpInfo.getExportUnitPrice() %>" size=8 onfocus="this.select();"
	        	onChange="if(updateExportPrice('<%=tmpInfo.getId()%>', this.value,<%=tmpInfo.getAvgprice() %>,<%=kind %>)=='1'){this.value='<%= tmpInfo.getExportUnitPrice() %>'}">
			</td>
	        <td align='right'><%= nf.format(oConvert.getRound(tmpInfo.getExportAmount()*tmpInfo.getExportUnitPrice()*kind,2)) %></td>
			<%if(ctx.isIfjiagong() && kind==1){ %>
			<td align='center'>
	        	<input type="text" name="jiagong" id="jiagong"   value="<%= tmpInfo.getJiagong() %>" size=5 onfocus="this.select();"
	        	onChange="if(updateJiaGong('<%=tmpInfo.getId()%>', this.value,<%=kind %>)=='1'){this.value='<%= tmpInfo.getJiagong() %>'}">
			</td>
			<%} %>
			<td align='center'>
	        	<input type="text" name="memo" id="memo"   value="<%= tmpInfo.getMemo() %>" size=5 maxlength=25 onfocus="this.select();"
	        	onChange="if(updateMemo('<%=tmpInfo.getId()%>', this.value,<%=kind %>)=='1'){this.value='<%= tmpInfo.getMemo() %>'}">
			</td>	       
	        <td align='center'>
	        	<a href="javascript:deleteATempImportGoods('<%=tmpInfo.getId()%>',<%=kind%>) "><IMG src="images/delto.gif" border=0></a>
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
                <b>����:</b>
            </td>
            <td >
            	<INPUT class=none  name="billid" type="text" size="20" value="<%=(ibf.getBillId()<0?"****":ibf.getBillId())%>" disabled>
            </td>
            <td  align="right" > 
                <b>������:</b>
            </td>
            <td >
            	<INPUT class=none  name="totalNum" type="text" size="12" value="<%=oConvert.getRound(totalnum,4)%>" disabled>(��)
            </td>
            <td  align="right" > 
                <b>������:</b>
            </td>
            <td >
            	<INPUT class=none  name="totalPrice" type="text" size="12" value="<%=nf.format(oConvert.getRound(totalPrice,2))%>" disabled>(Ԫ)
            </td>
            <%if(ctx.isIfjiagong() && kind==1){ %>
            <td  align="right" > 
                <b>�ӹ���:</b>
            </td>
            <td >
            	<INPUT class=none  name="totalPrice" type="text" size="12" value="<%=nf.format(oConvert.getRound(totalJiaGong,2))%>" disabled>(Ԫ)
            </td>   
            <%} %>         
          </tr>
         </TABLE>

    <form name="form1" method="post" action="exportGoodsBill.do?param=add">
    	
    	<input type="hidden" name="billId"  value="<%=ibf.getBillId()%>">
    	<input type="hidden" name="kind"  value="<%=kind%>">    	
    	<TABLE align="center" width="98%" border="0" cellpadding="3" cellspacing="0">
			<tr>
			 
			<td>
				<input type="radio" name="selectmode" value=0 onclick="bykucun.style.display='none';bychandi.style.display='';" <%=(selectmode==0?"checked":"")%>>������<br>
				<input type="radio" name="selectmode" value=1 onclick="bykucun.style.display='';bychandi.style.display='none';" <%=(selectmode==1?"checked":"")%>>�����<br>
			</td>
			<td align="center" id="bychandi" style="display:<%=(selectmode==0?"":"none")%>">
				<iframe name="gs" id="gs" src="goodsSelect.do?hideZero=1" width="700" height="25" frameborder=0 scrolling=no ></iframe>
				<input name="addgoods1" type="button" value=" ��� " onclick="if(verify(form1)) form1.submit();">
			</td>
			<td align="left" id="bykucun" style="display:<%=(selectmode==1?"":"none")%>">
				 <input name="addgoods2" type="button" value=" �����Ʒ " onclick="showModalWindow(form2.factory.value,form1,1)">
			</td>
			</tr>
			<input type="hidden" name="goodsId" value="">
			<input type="hidden" name="storeId" value="">
		</TABLE>

	</form>	
    	
	<p><center>
	<input name=deleteall type="button" value="����˵�" onClick="deleteAllGoods(<%=ibf.getBillId() %>,<%=kind %>)">
	<%if(kind==1){ %>
	&nbsp;&nbsp;<input name=submitall type="button" value="����Ϊ����" onClick="submitAllGoods('2')">
	<%} %>
	&nbsp;&nbsp;<input name=submitreal type="button" value="����Ϊ��ʽ��" onClick="submitAllGoods('1')">
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
<a name="cc" style="display:none"></a> 
</body>
</html>


