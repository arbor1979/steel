<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>

<%@ page import="java.text.*"%>
<%@page import="java.util.List"%>
<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js"></script>
<script language="JavaScript">

function ConfirmPay(kind,billid,deptid,factory,totalprice)
{
	var realmoney=document.getElementById('ims'+billid).value;
	var fktype=document.getElementById('imf'+billid).value;
	var acctype=document.getElementById('acc'+billid).value;
	if(!IsFloat(realmoney))
	{
		alert('�������Ǹ�����');
		return;
	}
	if(totalprice<0 && realmoney>0)
	{
		if(kind==1)
			alert('�˻���ʵ�ս��ӦΪ����');
		else
			alert('�˿ⵥʵ�����ӦΪ����');
		return;
	}
	var msg;
	var odd=Math.round((totalprice-realmoney)*100)/100;
	if(kind==1)
	{
		msg="Ӧ�ս�"+totalprice+" ʵ�գ�"+realmoney+" ȥ�㣺"+odd+"\r�Ƿ�ȷ�ϻ�������ȡ��";
	}
	else
		msg="Ӧ����"+totalprice+" ʵ����"+realmoney+" ȥ�㣺"+odd+"\r�Ƿ�ȷ�ϻ�����֧����";
	if(confirm(msg))
	{
		if(odd>5)
		{
			if(!confirm('ȥ���� '+odd+' ���� 5 Ԫ���Ƿ�ȷ�ϸñʻ�����壿'))
				return;
		}
		else if(odd<-5)
		{
			if(!confirm('ȥ���� '+odd+' С�� -5 Ԫ���Ƿ�ȷ�ϸñʻ�����壿'))
				return;
		}
		window.location="payHuoKuan.do?param=pay&kind="+kind+"&billid="+billid+"&deptid="+deptid+"&realmoney="+realmoney+"&fkType="+fktype+"&acctype="+acctype+"&factory="+factory;
	}
}

function setTotalPrice(kind,billid,price,yuchu,fk)
{
	var text;
	if(fk==0)
	{
		msg="Ԥ���ʻ�";
		text=document.getElementById("exs"+billid);
	}
	else
	{
		msg="Ԥ���ʻ�";
		text=document.getElementById("ims"+billid);
	}
	var acctype=document.getElementById("acc"+billid);
	if(kind==5)
	{
		text.value=price;
		acctype.style.visibility="hidden";
		if(yuchu<price)
			alert(msg+'��� '+yuchu+' ������֧������ '+price+' ��');
		//text.readOnly=true;
		
	}
	else
	{
		
		text.value='';
		acctype.style.visibility="visible";
		//text.readOnly=false;
	}
}
function viewDetail(url)
{
	window.open(url,'view','top=0,left=0,location=no,directories=no,hotkeys=0,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=800,height=400');
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
<%
String kind = (String)request.getAttribute("kind");
String act = (String)request.getAttribute("act");
%>
<table width=980 border="0" align="center" cellpadding="0" cellspacing="0">
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
    <td width="379" height="20" class="mainhead">
    <%
    if(act.equals("view"))
    {
    	if(kind.equals("1"))
    	    out.print("Ӧ���ʿ��ѯ����ϸ��");
    	else
    		out.print("Ӧ���ʿ��ѯ����ϸ��");
    }
    else
    {
    	if(kind.equals("1"))
    	    out.print("������ȡ");
    	else
    		out.print("����֧��");
    }
    %></td>
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
          
<%
	ResultsetList ys = (ResultsetList)request.getAttribute("ys");
	List fkList = (List)request.getAttribute("fk");
	List acc = (List)request.getAttribute("acc");
	
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
	
	if(kind.equals("1"))
	{
%>
	<font color=#215385>Ӧ�ջ����б��� <b><%=ys.rslist.size()%></b> �����۵����ϼ� <b><%=ys.allsumnum%></b> �֣�
	���<b><%=nf.format(ys.allmoney)%></b> Ԫ��Ԥ�գ�<b><%=nf.format(ys.realmoney)%></b> Ԫ��
	ʵǷ��<b><%=nf.format(ys.oddmoney)%></b> Ԫ��</font>

	<IMG src="images/line1.gif" width=900 border=0>

	<TABLE  class="mailtable">  
	
		<tr bgcolor="#C2CEDC">
		<th>���۵���</th>
		<th>����</th>
		<th>�ͻ�</th>
		<th>¼��Ա</th>
		<th>���(Ԫ)</th>
		<th>¼��ʱ��</th>
		<th>�տ�����</th>
		<%if(!act.equals("view")){ %>
		<th>�տʽ</th>
		<th>�տ��ʻ�</th>
		<th>ʵ�ս��</th>
		<%} %>
		</tr>
		
		<%
		
		ExportBillForm tmpInfo = null;
		String color="";
	    for (int index=0; index<ys.rslist.size(); index++)
		{
	      	tmpInfo = (ExportBillForm)ys.rslist.get(index);
	      	if(tmpInfo.getKind()==1)
	      		color="<font color=black>";
	      	else
	      		color="<font color=red>";
	    %>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        <td align='center' width=60 ><a href="javascript:viewDetail('<%= request.getContextPath()+ "/exportItemsView.do?billId=" + tmpInfo.getBillId()+"&deptid="+tmpInfo.getDeptid() %>');"><IMG src="images/news1.gif" border=0 alt='�鿴������ϸ'></a><%=color%><%= tmpInfo.getBillId() %></font></td>
	        <td align='center' width=30><%=color%><%= (tmpInfo.getKind()==1?"��":"��") %></font></td>
	        <td align='left' width=180><a href="javascript:viewDetail('<%= request.getContextPath()+ "/factoryView.do?factory="+tmpInfo.getFactory() %>');"><%=color%><%=tmpInfo.getFactoryName()%></font></a></td>
	        <td align='center' width=60><%=color%><%= tmpInfo.getSalesPerson() %></font></td>
			<td align='right' width=80><%=color%><%=nf.format(tmpInfo.getTotalPrice()) %></font></td>
			<td align='center' width=80><%=color%><%=tmpInfo.getExportTime()%></font></td>
			<td align='center' width=80><%=color%><%=tmpInfo.getPayLimTime()%></font></td>
			
			<%if(!act.equals("view")){ %>
			<td align='center'>
			<SELECT name="exf<%= tmpInfo.getBillId()%>" id="exf<%= tmpInfo.getBillId()%>" onChange="setTotalPrice(this.value,<%= tmpInfo.getBillId()%>,<%=tmpInfo.getTotalPrice() %>,<%=ys.realmoney%>,0)" <%=(ctx.getDeptid()!=tmpInfo.getDeptid()?"disabled":"")%>>
            		<%
            			FuKuanForm fkf;  	
            		     for(int i=0; i<fkList.size(); i++)
            		     {
            		         fkf = (FuKuanForm)fkList.get(i);
            		%>
            		<option value="<%=fkf.getId()%>" <%=(fkf.getId()==tmpInfo.getFkType()?"selected":"") %>><%=(fkf.getName().length()>12?fkf.getName().substring(0,12):fkf.getName())%></option>
			<%
            		     }
			%>
            	</SELECT></td>
             <td >
            	
            	<SELECT name="acc<%= tmpInfo.getBillId()%>" id="acc<%= tmpInfo.getBillId()%>" <%=(tmpInfo.getFkType()==5?"style='visibility:hidden'":"style='visibility:visible'") %>>
            		<%
            		AccountForm tmp;
            		for(int i=0;i<acc.size();i++)
            		{
            			tmp=(AccountForm)acc.get(i);
            			out.print("<option value="+tmp.getId()+" >"+tmp.getAccName()+" "+nf.format(tmp.getJine())+"</option>");
            		}
            		%>
            	</SELECT>
            </td>
			<td align='center'>
			<input type="text" name="exs<%= tmpInfo.getBillId()%>" size=8 maxlength=15 onkeyup="InputFloat(this);"  <%=(ctx.getDeptid()!=tmpInfo.getDeptid()?"readonly":"")%>><input type="button" value="�տ�" onclick="ConfirmPay(1,<%=tmpInfo.getBillId()%>,<%=tmpInfo.getDeptid() %>,<%=tmpInfo.getFactory() %>,<%=tmpInfo.getTotalPrice()+tmpInfo.getTotaljiagong() %>)" <%=(ctx.getDeptid()!=tmpInfo.getDeptid()?"disabled":"")%>>
			</td>
			<%} %>
        </tr>
        <%
      	}
        %>
	</TABLE>
	
	<%
	}
	else if(kind.equals("-1"))
	{
	%>
	<font color=#215385>Ӧ�������б��� <b><%=ys.rslist.size()%></b> �Ž��������ϼ� <b><%=ys.allsumnum%></b> �֣�
	���<b><%=nf.format(ys.allmoney)%></b> Ԫ��Ԥ����<b><%=nf.format(ys.realmoney)%></b> Ԫ��
	ʵǷ��<b><%=nf.format(ys.oddmoney)%></font> Ԫ��</b>
	<IMG src="images/line1.gif" width=900 border=0>

	<TABLE  class="mailtable">  
	
		<tr bgcolor="#C2CEDC">
		<th>��������</th>
		<th>����</th>
		<th>��Ӧ��</th>
		<th>¼��Ա</th>
		<th>���(Ԫ)</th>
		<th>¼��ʱ��</th>
		<th>��������</th>
		<%if(!act.equals("view")){ %>
		<th>���ʽ</th>
		<th>�����ʻ�</th>
		<th>ʵ�����</th>
		<%} %>
		</tr>
		
		<%
						ImportBillForm tmpInfo1 = null;
						String color="";
					    for (int index=0; index<ys.rslist.size(); index++)
						{
					    	tmpInfo1 = (ImportBillForm)ys.rslist.get(index);
					    	if(tmpInfo1.getKind()==1)
					      		color="<font color=black>";
					      	else
					      		color="<font color=red>";
				%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        <td align='center' width=60><a href="javascript:viewDetail('<%= request.getContextPath()+ "/importItemsView.do?billId=" + tmpInfo1.getBillId()+"&deptid="+tmpInfo1.getDeptid() %>');"><IMG src="images/news1.gif" border=0 alt='�鿴������ϸ'></a><%=color%><%= tmpInfo1.getBillId() %></font></td>
	        <td align='center' width=30><%=color%><%= (tmpInfo1.getKind()==1?"��":"��") %>
	        <%
	        if(tmpInfo1.getIeva()==1)
            		out.print("(��)");   
	        %>
	        </font></td>
	        <td align='left' ><a href="javascript:viewDetail('<%= request.getContextPath()+ "/factoryView.do?factory="+tmpInfo1.getFactory() %>');"><%=color%><%=tmpInfo1.getFactoryName()%></font></a></td>
	        <td align='center' width=50><%=color%><%= tmpInfo1.getCreatePerson() %></font></td>
			<td align='right' width=80><%=color%><%=nf.format(tmpInfo1.getTotalPrice()) %></font></td>
			<td align='center' width=70><%=color%><%=tmpInfo1.getImportTime()%></font></td>
			<td align='center' width=70><%=color%><%=tmpInfo1.getPayLimTime()%></font></td>
			<%if(!act.equals("view")){ %>
			<td align='center'>
			<SELECT name="imf<%= tmpInfo1.getBillId()%>" id="imf<%= tmpInfo1.getBillId()%>" onChange="setTotalPrice(this.value,<%= tmpInfo1.getBillId()%>,<%=tmpInfo1.getTotalPrice() %>,<%=ys.realmoney%>,1)" <%=(ctx.getDeptid()!=tmpInfo1.getDeptid()?"disabled":"")%>>
            		<%
            			FuKuanForm fkf;  	
            		     for(int i=0; i<fkList.size(); i++)
            		     {
            		         fkf = (FuKuanForm)fkList.get(i);
            		%>
            		<option value="<%=fkf.getId()%>" <%=(fkf.getId()==tmpInfo1.getFkType()?"selected":"") %>><%=(fkf.getName().length()>12?fkf.getName().substring(0,12):fkf.getName())%></option>
			<%
            		     }
			%>
            	</SELECT></td>
            	<td >
            	
            	<SELECT name="acc<%= tmpInfo1.getBillId()%>" id="acc<%= tmpInfo1.getBillId()%>" <%=(tmpInfo1.getFkType()==5?"style='visibility:hidden'":"style='visibility:visible'") %>>
            		<%
            		AccountForm tmp;
            		for(int i=0;i<acc.size();i++)
            		{
            			tmp=(AccountForm)acc.get(i);
            			out.print("<option value="+tmp.getId()+" >"+tmp.getAccName()+" "+nf.format(tmp.getJine())+"</option>");
            		}
            		%>
            	</SELECT>
            </td>
			<td align='center'>
			 <%
	        if(tmpInfo1.getIeva()!=1)
	        {
	        %>
			<input type="text" name="ims<%= tmpInfo1.getBillId()%>"  id="ims<%= tmpInfo1.getBillId()%>" size=8 maxlength=15 onkeyup="InputFloat(this);" <%=(ctx.getDeptid()!=tmpInfo1.getDeptid()?"readonly":"")%>><input type="button" value="����" onclick="ConfirmPay(-1,<%=tmpInfo1.getBillId()%>,<%=tmpInfo1.getDeptid() %>,<%=tmpInfo1.getFactory() %>,<%=tmpInfo1.getTotalPrice() %>);" <%=(ctx.getDeptid()!=tmpInfo1.getDeptid()?"disabled":"")%>>
			<%} %>
		</td>
		<%} %>
        </tr>
        <%
      	}
        %>
	</TABLE>
	
<%
	}
%>
<p align="center"><input type="button" value=" ���� " onclick="window.location='payHuoKuan.do?kind=<%=kind %>&act=<%=act %>';">
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

