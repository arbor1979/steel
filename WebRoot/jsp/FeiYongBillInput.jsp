<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>
<%@page import="java.util.Date"%>
<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js"></script>
<script language="javascript" src="js/jquery.js"></script>
<script language="JavaScript">

function verify(form)
{
	if(cat.form1.classid.value==0)
	{
		alert('��ѡ�����');
		cat.form1.classid.focus();
		return false;
	}
	if(cat.form1.typeid.value==0)
	{
		alert('����ѡ������');
		cat.form1.typeid.focus();
		return false;
	}
	
	form.typeid.value=cat.form1.typeid.value;
	return true;
}

function updateMoney(id,kind, newexportamount)
{
	//��������У�����ʱ����
	document.form2.obj4numbercheck.value = newexportamount;
	if(document.form2.obj4numbercheck.value == null || document.form2.obj4numbercheck.value == '')
	{
		alert("����Ϊ��");
		return '1';
	}
	if(Number(document.form2.obj4numbercheck.value)<=0)
	{
		alert("�����������");
		return '1';
	}
	if(IsFloat(document.form2.obj4numbercheck.value)==false)
	{
		alert("�������Ǹ�����");
		return '1';
	}
	location.href="feiYongBillInput.do?param=updateMoney&id="+id+"&jine="+newexportamount+"&kind="+kind;
}

function updateMemo(id,kind, newexportamount)
{
	$.ajax({ 
		  type:'POST', 
		  url:"feiYongBillInput.do?param=updateMemo&id="+id+"&kind="+kind, 
		  data:{memo:encodeURIComponent(newexportamount)},
		  dataType: 'text', 
		  cache:false,
		  async: false,
		  success:function(data) 
		  { 
			  if(data=='ok')
			  {
			  	
			  }
			  else
			  {
				alert(data);
				location.reload();
			   }
			  	
		  },
		  error:function(XmlHttpRequest,textStatus, errorThrown)
	  	  {
			  alert('����'+errorThrown);
	  	  }
	});
	
}
function updateJingShou(id,kind, newexportamount)
{
	$.ajax({ 
		  type:'POST', 
		  url:"feiYongBillInput.do?param=updateJingShou&id="+id+"&kind="+kind, 
		  data:{jingshouren:encodeURIComponent(newexportamount)},
		  dataType: 'text', 
		  cache:false,
		  async: false,
		  success:function(data) 
		  { 
			  if(data=='ok')
			  {
			  	
			  }
			  else
			  {
				alert(data);
				location.reload();
			   }
			  	
		  },
		  error:function(XmlHttpRequest,textStatus, errorThrown)
	  	  {
			  alert('����'+errorThrown);
	  	  }
	});

}
function deleteAllGoods(billId,kind)
{
	try{
		var goods_num = document.form2.jine.length;
		if(goods_num == null || document.form2.jine == null)
		{//
			//alert("���б�");
		}
	}catch(e)
	{
		alert("�����б�ĿǰΪ�գ����������");
		return;
	}
	if(confirm("ȷ��Ҫ��ո��б���")) 
	{
		location.href="feiYongBillInput.do?param=delall&billid="+billId+"&kind="+kind;
	}
}

function deleteATempImportGoods(id,kind)
{
	if(confirm("ȷ��Ҫɾ���ü�¼��")) 
	{
		location.href="feiYongBillInput.do?param=del&id="+id+"&kind="+kind;
	}
}

function submitAllGoods()
{
	//�鿴�õ��Ƿ��ǿ��б����б�Ͳ��ύ
	try{
		var goods_num = document.form2.jine.length;
		if(goods_num == null || document.form2.jine == null)
		{//
			//alert("���б�");
		}
	}catch(e)
	{
		alert("�������ϸ�����ύ");
		return;
	}
	
	if(form2.acctype.value==0)
	{
		alert("��ѡ��һ���ʻ�");
		document.form2.yewuid.focus();
		return false;
	}
	//��������͵����Ƿ�Ϊ����
	var errMsg;
	
	var amount_num = document.form2.jine.length;
	
	var isNumberFlage = 'yes';//Ĭ��Ϊ�������֡�
	if(amount_num == null) 
	{
	
		if(IsFloat(document.form2.jine.value)==false)
		{
			isNumberFlage = 'no';
		}
	}
	else
	{
		
		for(iii=0; iii<amount_num; iii++)
		{
			if(IsFloat(document.form2.jine[iii].value)==false)
			{
				isNumberFlage = 'no';
			}
		}
	}
	if(isNumberFlage=='yes')
	{
		form2.param.value='submit';
		form2.submit();	
	}
	else
	{//������
		alert('��������Ϊ���������۱���Ϊ������');
	}
}
function UpdateBill()
{
	form2.param.value='update';
	form2.submit();
}
</script>
</head>
<%
	FeiYongBillForm ibf = (FeiYongBillForm)request.getAttribute("ibf");
	List goodsList = (List)request.getAttribute("goodsList");
	List acc = (List)request.getAttribute("acc");
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
    <td width="379" height="20" class="mainhead"><%=(ibf.getKind()==1?"�������뵥":"<font color=red>����֧����</font>") %>¼��</td>
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
	<form name="form2" method="post" action="feiYongBillInput.do" >
	
	<input type="hidden" name="billid"  value="<%=ibf.getBillId()%>">  	
	<input type="hidden" name="obj4numbercheck" value="">
	<input type="hidden" name="param" value="submit">
	<input type="hidden" name="kind" value="<%=ibf.getKind() %>">
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
          <tr>
            <td  align="right" width=80> 
                <b>*<%=(ibf.getKind()==1?"�տ��ʻ�":"�����ʻ�") %>:</b>
            </td>
            <td >
            	
            	<SELECT name="acctype" onChange="UpdateBill();">
            		<option value="0">��ѡ��...</option>
            		<%
            		AccountForm tmp;
            		for(int i=0;i<acc.size();i++)
            		{
            			tmp=(AccountForm)acc.get(i);
            			out.print("<option value="+tmp.getId()+" "+(ibf.getAcctype()==tmp.getId()?"selected":"")+">"+tmp.getAccName()+" "+tmp.getJine()+"</option>");
            		}
            		%>
            	</SELECT>
            </td>
            <td  align="right" width=80> 
                <b>*��������:</b>
            </td>
            <td>
            <%
            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
			Date dt=new Date();
            %>
            <input type=text name='payLimTime' size="15" class="none" value="<%=(ibf.getBillTime().length()>0?ibf.getBillTime():df.format(dt)) %>" onChange="UpdateBill();">
			<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="���������˵�"  onClick="Calendar_paylimtime();">
			</td>
            </tr>
          	
	</TABLE>
	<font color=#215385><b>��ϸ�б�</b></font>
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
		
		<tr bgcolor="#C2CEDC">
		<th></th>
		<th>�����</th>
		<th>����</th>
		<th>���</th>
		<th>������</th>
		<th>����˵��</th>
		<th>ɾ��</th>
		</tr>
		
		
		<%
		int index = 0;
		FeiYongGoodsForm tmpInfo = null;
	    double totalPrice = 0;
	      	
	    for (index=0; index<goodsList.size(); index++)
		{
	      		tmpInfo = (FeiYongGoodsForm)goodsList.get(index);
	      		totalPrice=totalPrice+tmpInfo.getJine();
	    %>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        <td align='center' width=10><%= index+1 %></td>
	        <td align='center'><%= tmpInfo.getTypeid() %></td>
	        <td align='center' width=90><%= tmpInfo.getTypeName() %></td>
        	<td align='center'>
	        	<input type="text" name="jine" id="jine"   value="<%= oConvert.getRound(tmpInfo.getJine()*ibf.getKind(),2) %>" size=10 onfocus="this.select();"
	        	onChange="if(updateMoney('<%=tmpInfo.getId()%>',<%=ibf.getKind() %>, this.value)=='1'){this.value='<%= oConvert.getRound(tmpInfo.getJine(),2) %>'}">
	        </td>
	        <td align='center'><input type="text" name="jingshouren" id="jingshouren"   value="<%= tmpInfo.getJingshouren() %>" size=12 onfocus="this.select();"
				onChange="updateJingShou('<%=tmpInfo.getId()%>',<%=ibf.getKind() %>, this.value);">
			</td>
			<td align='center'><input type="text" name="memo" id="memo"   value="<%= tmpInfo.getMemo() %>" size=25 onfocus="this.select();"
				onChange="updateMemo('<%=tmpInfo.getId()%>',<%=ibf.getKind() %>, this.value);">
			</td>
	        <td align='center'>
	        	<a href="javascript:deleteATempImportGoods('<%=tmpInfo.getId()%>',<%=ibf.getKind() %>) "><IMG src="images/delto.gif" border=0></a>
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
                <b>�ܽ��:</b>
            </td>
            <td >
            	<INPUT class=none  name="totalPrice" type="text" size="15" value="<%=nf.format(oConvert.getRound(totalPrice*ibf.getKind(),2))%>" disabled>
            </td>
            
            
          </tr>
     </TABLE>
	
	<form name="form1" method="post" action="feiYongBillInput.do?param=add" onSubmit="javascript:return verify(this);">
    	
    	<input type="hidden" name="billid"  value="<%=ibf.getBillId()%>">
    	<input type="hidden" name="kind"  value="<%=ibf.getKind()%>">
    	<input type="hidden" name="typeid" value="">
    	<TABLE align="center" width="90%" border="0" cellpadding="3" cellspacing="0">
			<tr>
				<td colspan=3>
                <iframe name="cat" id="cat" src="feiYongTypeSelect.do?classid=0&typeid=0&kind=<%=ibf.getKind() %>" width="300" height="22" frameborder=0 scrolling=no ></iframe>
				<input name=submit type=submit value=" ��� " ></td>
			</tr>
			
		</TABLE>

	</form>

	<p><center>
	<input name=deleteall type="button" value="����˵�" onClick="deleteAllGoods(<%=ibf.getBillId() %>,<%=ibf.getKind() %>)">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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

