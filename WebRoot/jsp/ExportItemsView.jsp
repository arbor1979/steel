<%@ page contentType="text/html; charset=gb2312" language="java" %>
<%@ page import="mediastore.util.chineseMoney"%>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="mediastore.web.global.Globals" %>

<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="./css/stylePrint.css" type="text/css">
</head>
<body>
<%
	UserInfoForm ctx = (UserInfoForm)session.getAttribute(Globals.SESSION_CONTEXT);
	ExportBillForm ibf = (ExportBillForm)request.getAttribute("billInfo");
	String ifprint = (String)request.getAttribute("ifprint");
	List bdl = (List)request.getAttribute("billDetail");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
	boolean bYiXiang=(ibf.getConfirmFlage().equals("2")?true:false);
	String skind="";
	if(ibf.getKind()==1)
		skind="����";
	else if(ibf.getKind()==-1)
		skind="�����˻�";
%><br>
<h2 align="center" style="font-family: ����_GB2312;"><%=ibf.getGongsi() %><%=(bYiXiang?"��������":"��"+skind+"����")%><%if(ibf.getConfirmFlage().equals("3")) out.print("(�ѳ���)"); %></h2>
<table width="100%" align="center">
<%if(bYiXiang){ %>
<tr >
	<td >���ţ�<b><%=ibf.getBillId() %></b></td>
	<td >�ͻ���<%=ibf.getFactoryName()%></td>
	<td >����ʱ�䣺<%=ibf.getExportTime()%></td>
	<td>�����ʽ:<%=ibf.getTihuo() %></td>
	<td align=right>���ƺţ�<%=(ibf.getCarNo().equals("")?"":ibf.getCarNo()) %></td>
</tr>
<%}else{ %>
<tr >
	<td >���ţ�<b><%=ibf.getBillId() %></b></td>
	<td >�ͻ���<%=ibf.getFactoryName()%></td>
	<%if(ibf.getKind()==1){ %>
	<td>�����ʽ��<%=ibf.getTihuo() %></td>
	<%}else{ %><td></td><%} %>
	<td align=right>����ʱ�䣺<%=ibf.getExportTime()%></td>
</tr>
<tr>
	<td >���ƺţ�<%=(ibf.getCarNo().equals("")?"":ibf.getCarNo()) %></td>
	<td >�˷ѣ�<%=(ibf.getYunFei()==0?"":ibf.getYunFei()) %>&nbsp;&nbsp;&nbsp;&nbsp;
	<%
	if(!ibf.getFpName().equals("�վ�"))
		out.print("��Ʊ���ͣ�"+ibf.getFpName()+"&nbsp;&nbsp;&nbsp;&nbsp;");
	%>
	</td>
	<td>���ʽ��<%=ibf.getFkName()%></td>
	<td align=right>�������ޣ�<%=ibf.getPayLimTime()%></td>
</tr>
<%} %>
</table>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="0" cellspacing="0" style="border-collapse:collapse">
	<tr><td>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="3" cellspacing="0" style="border-collapse:collapse">


		</tr>
		<tr bgcolor="#ffffff">
			<td align="center"><b>�ֿ�<b></td>
			<td align="center"><b>���</b></td>
			<td align="center"><b>����</b></td>
			<td align="center"><b>����</b></td>
			<td align="center"><b>���</b></td>
			<td align="center"><b>����</b></td>
			<%if(bYiXiang){ %>
			<td align="center"><b>Ӧ����</b></td>
			<td align="center"><b>����(��)</b></td>
			<td align="center"><b>ʵ����</b></td>
			<%}else{ %>
			<td align="center"><b>ʵ����</b></td>
			<td align="center"><b>����(��)</b></td>
			<%} %>
			<!-- <td align="center"><b>���ط���</b></td> -->
			<%if(!bYiXiang || ctx.isIfyixiangjine()){ %>
			<td align="center"><b>����</b></td>
			<td align="center"><b>���(Ԫ)</b></td>
			<%if(ibf.getTotaljiagong()>0){ %>
			<td align="center"><b>�ӹ���</b></td>
			<%}}%>
			<td align="center"><b>˵��</b></td>

		</tr>
		<%
		GoodsExportGoodsInfo tmpInfo=null;
		
	      	for (int index=0; index<bdl.size(); index++)
			{
	      		tmpInfo = (GoodsExportGoodsInfo)bdl.get(index);
	      	%>
		<tr bgcolor="#ffffff">
			<td align='left'><%= tmpInfo.getStoreName() %></td>
			<td align='left'><%= tmpInfo.getGoodsId() %></td>
			<td align='left'><%= tmpInfo.getGoodsName() %></td>
			<td align='left'><%= tmpInfo.getCaizhi() %></td>
			<td align='left'><%= tmpInfo.getGuige() %></td>
			<td align='left'><%= tmpInfo.getChandi()%></td>
			
			<%if(bYiXiang){ %>
			<td align='right'><%
			if(tmpInfo.getExportNum()!=0)
				out.print(tmpInfo.getExportNum()*ibf.getKind()+tmpInfo.getDanwei());
			else if(tmpInfo.getJianxishu()>0)
				out.print(oConvert.getRound(tmpInfo.getExportAmount()*ibf.getKind()*1000/tmpInfo.getJianxishu(),1)+"��");
			%></td>
			<td align='right'><%= tmpInfo.getExportAmount()*ibf.getKind() %></td>
			<td></td>
			<%}else{ %>
			<td align='right'><%
			if(tmpInfo.getExportNum()!=0)
				out.print(tmpInfo.getExportNum()*ibf.getKind()+tmpInfo.getDanwei());
			else if(tmpInfo.getJianxishu()>0)
				out.print(oConvert.getRound(tmpInfo.getExportAmount()*ibf.getKind()*1000/tmpInfo.getJianxishu(),1)+"��");
			%></td>
			<td align='right'><%= tmpInfo.getExportAmount()*ibf.getKind() %></td>
			<%} %>
			<!-- 
			<td align='center'><%
			if(tmpInfo.getXishu()>0 || tmpInfo.getJianxishu()>0) 
				out.print("����");
			else out.print("����"); 
			%></td> -->
			<%if(!bYiXiang || ctx.isIfyixiangjine()){ %>
			<td align='right'><%= tmpInfo.getExportUnitPrice()%></td>
			<td align='right'><%= nf.format(oConvert.getRound(tmpInfo.getExportAmount()*ibf.getKind()*tmpInfo.getExportUnitPrice(),2)) %></td>
			<%if(ibf.getTotaljiagong()>0){ %>
			<td align='right'><%=(tmpInfo.getJiagong()==0?"":nf.format(tmpInfo.getJiagong()))%></td>
			<% }} %>
			<td align='left'><%= tmpInfo.getMemo()%></td>
		</tr>
		<%}%>
		
		<tr bgcolor="#ffffff">
			<td align="center"><b>С��</b></td>
			<td></td><td></td><td></td><td></td><td></td>
			<%if(bYiXiang){ %>
			<td></td>
			<td align="right"><%=ibf.getTotalNum()*ibf.getKind()%></td>
			<td></td>
			<%}else{ %>
			<td></td>
			<td align="right"><%=ibf.getTotalNum()*ibf.getKind()%></td>
			<%} %>
			<%if(!bYiXiang || ctx.isIfyixiangjine()){ %>
			<td></td>
			<td align="right"><u><%=nf.format(ibf.getTotalPrice()*ibf.getKind())%></u></td>
			<%if(ibf.getTotaljiagong()>0){ %>
			<td align="right"><u><%=nf.format(ibf.getTotaljiagong())%></u></td>
			<%} }%>
			<td></td>
		</tr>
		<%if(!bYiXiang){ %>
		<tr bgcolor="#ffffff">
			<td align="center"><b>��ע</b></td><td colspan=2><%=ibf.getMemo() %></td>
			<td><b>��д</b></td><td colspan="4" align="left" ><%chineseMoney cm=new chineseMoney();out.print(cm.getChineseMoney(ibf.getTotalPrice()*ibf.getKind()+ibf.getTotaljiagong())); %></td>
			<td align="center"><b>�ϼ�</b></td><td colspan=2 align=center><b><%=nf.format(ibf.getTotalPrice()*ibf.getKind()+ibf.getTotaljiagong())%> Ԫ</b> </td>
		</tr>
		<%} %>
	</table></td></tr>
	</table>
	<table width="100%" align="center">
	<tr>
	<td>�Ƶ�Ա��<%=ibf.getSalesPerson()%></td>
	<td>ҵ��Ա��<%=ibf.getYewuname()%></td>
	<td>�����ˣ�&nbsp;&nbsp;&nbsp;&nbsp;</td>
<%if(!bYiXiang){ %>
	<td>����ˣ�&nbsp;&nbsp;&nbsp;&nbsp;</td>
	<td>�����ˣ�&nbsp;&nbsp;&nbsp;&nbsp;</td>
<%} %>
	</tr>
	<%if(!bYiXiang && ibf.getTip().length()>0){ %>
	<tr><td colspan=2><%=ibf.getTip() %></td><td height=30 align=right colspan=2><u><i>�����������</i></u></td></tr>
	<%} %>
	</table>
	<%
if(ifprint.equals("1") || ifprint.equals("2"))
{
%>
<script>
function printbill()
{
	//printtr.style.display = "none";
	document.write("<object ID='WebBrowser' WIDTH=0 HEIGHT=0 CLASSID='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2'></object>"); 
	WebBrowser.ExecWB(7,1); 
	//window.history.back(-1);
	//printtr.style.display = "";
	//window.opener=null; 
	//window.close(); 
	
}
printbill();
<%  
	if(ifprint.equals("1"))
		out.print("window.navigate('exportGoodsBill.do');");
	else
		out.print("window.close();");
%>
</script>
<%}else{ %>
<p align="center"><input type="button" name="printtr" value=" ��ӡ " onClick="window.navigate('exportItemsView.do?billId=<%=ibf.getBillId() %>&deptid=<%=ibf.getDeptid() %>&ifprint=2');">
&nbsp;<input type="button" name="close" value=" �ر� " onClick="window.close();">
<%} %>
</body>

</html>


