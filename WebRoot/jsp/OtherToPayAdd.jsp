<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.text.NumberFormat"%>
<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js"></script>
<script language="JavaScript">
function verify()
{
	
	if(form2.factory.value==0)
	{
		alert('������λ����Ϊ��');
		form2.factory.focus();
		return false;
	}
	if(form2.kind.value==0)
	{
		alert('��ѡ���������');
		form2.kind.focus();
		return false;
	}
	if(form2.acctype.value==0)
	{
		alert('��ѡ���ʻ�');
		form2.acctype.focus();
		return false;
	}
	
	if(!IsFloat(form2.jine.value))
	{
		alert('������Ϊ������');
		form2.jine.focus();
		return false;
	}
	if(Number(form2.jine.value)<=0)
	{
		alert('���������0');
		form2.jine.focus();
		return false;
	}
	var skind=form2.kind.options[form2.kind.selectedIndex].text;
	if(confirm('ȷ��Ҫ'+skind+' '+form2.jine.value+' ��'))
		return true;
	else
		return false;
}
function SubmitThis(factory,kind)
{
	form1.factory.value=factory;
	form1.submit();
}
function viewDetail(url)
{
	window.open(url,'view','top=0,left=0,location=no,directories=no,hotkeys=0,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=800,height=400');
}
function gotoPage(){
	
	//��ֵΪҪ��ת����ҳ�����ʼ��¼��
	//alert(document.all.gotopagenum.value);

	document.form1.curpage.value = document.all.gotopagenum.value;
	document.form1.submit();
	
}
function gotoPageByN(n)
{
	document.form1.curpage.value = n;
	document.form1.submit();
	
}
</script>
</head>
<%
	List acc = (List)request.getAttribute("acc");
	ResultsetList ys = (ResultsetList)request.getAttribute("ys");
	List FactList = (List)request.getAttribute("FactList");
	List kindList = (List)request.getAttribute("kindList");
	OtherToPayForm fi = (OtherToPayForm)request.getAttribute("fi");
	int kind=fi.getType();
	int factory=fi.getFactory();
	String zhujima = (String)request.getAttribute("zhujima");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
	AccountForm tmp=null;
	String[] str=null;
	
%>

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
    <td width="379" height="20" class="mainhead">������������</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
        
        <tr> 
          <td height="100" align="center" valign="top">   
    <br> 
    <form name="form1" method="post" action="otherToPay.do">
    	<input type="hidden" name="type" value="<%=fi.getType() %>">
    	<input type="hidden" name="factory" value="<%=fi.getFactory() %>">
    	<input type="hidden" name="curpage"  value="<%=ys.curpage %>">
    </form>
	<form name="form2" method="post" action="otherToPay.do" onsubmit="return verify();">
	<input type="hidden" name="param" value="submit">
	<p align=center></p>
	<TABLE  class="mailtable"> 
		<tr>
			<td colspan=2 align=center>
			<input type="radio" name="type" value="0" <%=(kind==0?"checked":"") %> onclick="window.location='otherToPay.do?type=0'"><b>����Ӧ�տ�</b>&nbsp;&nbsp;
	<input type="radio" name="type" value="1" <%=(kind==1?"checked":"") %>  onclick="window.location='otherToPay.do?type=1'"><b>����Ӧ����</b>
			</td>
			
		
            
         <td align="right">
				<b>*������λ:</b>
			</td>
			<td>
			<SELECT name="factory" onChange="SubmitThis(this.value,<%=kind %>);">
            		<option value="0">��ѡ��..</option>
            		<%
         		         FactoryInfoForm tmpFact=null;
         		         for(int i=0; i<FactList.size(); i++)
         		         {
         		            	tmpFact = (FactoryInfoForm)FactList.get(i);
            		%>
            		<option value="<%=tmpFact.getId()%>" <%=(factory==tmpFact.getId()?"selected":"") %>><%=tmpFact.getName()%></option>
			<%
			}
			%>
            	</SELECT><input class=none type="text" name="zhujima" value="<%=zhujima %>" size=5 onChange="window.location='otherToPay.do?zhujima='+this.value;">(������)
           </td>
           <td  align="right" > 
                <b>*��������:</b>
            </td>
           <td >
            	<SELECT name="kind" >
            		<option value="0">��ѡ��..</option>
            		<%if(kind==0){ %>
            		<option value="16">�ʽ���������Ӧ�տ�+��</option>
            		<option value="17">�ʽ��루����Ӧ�տ�-��</option>
            		<%}else{ %>
            		<option value="18">�ʽ���루����Ӧ����+��</option>
            		<option value="19">�ʽ𻹳�������Ӧ����-��</option>
            		<%} %>
            	</SELECT>
            </td>
          </tr>
          <tr>
           <td  align="right" > 
                <b>*�����ʻ�:</b>
            </td>
           <td >
            	
            	<SELECT name="acctype" >
            		<%
            		for(int i=0;i<acc.size();i++)
            		{
            			tmp=(AccountForm)acc.get(i);
            			out.print("<option value="+tmp.getId()+" >"+tmp.getAccName()+" "+tmp.getJine()+"</option>");
            		}
            		%>
            	</SELECT>
            </td>
          	 <td  align="right"> 
                <b>��ע˵��:</b>
            </td>
            <td >
            	<input type="text" class="none" name="memo" size=20>
            </td>
            <td  align="right"> 
                <b>*�������:</b>
            </td>
            <td >
            	<input type="text" class="none" name="jine" size=15 onKeyUp="InputFloat(this);">
            	<input type="submit" name="submit" value=" ȷ�� ">
            </td>
            </tr>
          	
	</TABLE>
	</form>
	 <%
    if(ys!=null && ys.rslist.size()>0)
    {
    %>
    <font color=#215385>������¼���� <b><%=ys.allnum%></b> �ʣ�
    <%=(kind==0?"����Ӧ��":"����Ӧ��")%>�<b><%
    if(kind==0)
    	out.print((ys.allmoney==0?0:-ys.allmoney));
    else
    	out.print(ys.allmoney);
	%></b> Ԫ��</font>
	<IMG src="images/line1.gif" width=900 border=0>
	<TABLE  class="mailtable">  
	
		<tr bgcolor="#C2CEDC">
		<th></th>
		<th>����</th>
		<th>�����ʻ�</th>
		<th>��������</th>
		<th>�跽(<%=(kind==0?"����":"����") %>)</th>		
		<th>����(<%=(kind==0?"����":"����") %>)</th>	
		<th>���</th>	
		<th>¼��Ա</th>
		<th>��ע</th>
		<th>����ʱ���</th>
		</tr>
		
		<%
		
		OtherToPayForm tmpInfo = null;
        for(int index=0;index<ys.rslist.size();index++)
        { 
        	tmpInfo=(OtherToPayForm)ys.rslist.get(index);
%>
		 <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
			<td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath()+ "/otherPayItemView.do?billId=" + tmpInfo.getBillid()+"&deptid="+tmpInfo.getDeptid() %>');"><IMG src="images/news1.gif" border=0 alt='�鿴������ϸ'></a></td></td>		 	
	        <td align='center' width=5%><%=tmpInfo.getBillid() %></td>
	        <td align='center'><%= tmpInfo.getAccName() %></td>
	        <td align='center'><%= tmpInfo.getKindName() %></td>
	        <%
	        if(tmpInfo.getJine()>0) 
	        {
	        	out.print("<td></td>");
	        	out.print("<td align='right'>"+nf.format(tmpInfo.getJine())+"</td>");
	        	
	        }
	        else
	        {
	        	
	        	out.print("<td align='right'>"+nf.format(-tmpInfo.getJine())+"</td>");
	        	out.print("<td></td>");
	        }
	        %>
	        <td align='right'><%=nf.format(tmpInfo.getCurjine()==0?0:tmpInfo.getCurjine()) %></td>
	        <td align='center'><%= tmpInfo.getOperPerson() %></td>
			<td align='center'><%= tmpInfo.getMemo() %></td>
			<td align='center'><%=tmpInfo.getCreateTime()%></td>
            </tr>
<%
  			      	
        }
        %>
	</TABLE>
	
	<div align="right">
		<%
			if(ys.curpage>1)
			{
		%>
		<a href="javascript:gotoPageByN(1)"><IMG src="images/startpage.gif" border=0 alt='��ҳ'></a>&nbsp;
		<a href="javascript:gotoPageByN(<%=ys.curpage-1%>)"><IMG src="images/lastpage.gif" border=0 alt='��һҳ'></a>&nbsp;&nbsp;

		<%
			}
			else
			{
		%>
		<IMG src="images/startpage.gif" border=0 alt='��ҳ'>&nbsp;
		<IMG src="images/lastpage.gif" border=0 alt='��һҳ'>&nbsp;&nbsp;
		<%	
			}

	    	if(ys.curpage<ys.pagecount)
	    	{
	    %>
	    	<a href="javascript:gotoPageByN(<%=ys.curpage+1%>)"><IMG src="images/nextpage.gif" border=0 alt='��һҳ'></a>&nbsp;
	        <a href="javascript:gotoPageByN(<%=ys.pagecount%>)"><IMG src="images/endpage.gif" border=0 alt='ĩҳ'></a>&nbsp;&nbsp;&nbsp;&nbsp;
	    <%
	    	}
			else
			{
		%>
		<IMG src="images/nextpage.gif" border=0 alt='��һҳ'>&nbsp;
		<IMG src="images/endpage.gif" border=0 alt='ĩҳ'>&nbsp;&nbsp;&nbsp;&nbsp;
		<%	
			}
		%>
    	</div>
    	<div align="center">
		�� <select name="gotopagenum" onChange="gotoPage()">
		<%for(int i=1; i<=ys.pagecount; i++)
		  {%>
			<option value="<%=i%>" <%if(i==ys.curpage){%>selected<%}%>><%=i%></option>
		<%}%>
		</select> ҳ
	</div> 
	<%}%>
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

