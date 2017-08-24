<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.util.chineseMoney"%>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>


<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link rel="stylesheet" href="./css/stylePrint.css" type="text/css">
<script language="javascript" src="js/LodopFuncs.js"></script>
</head>
<body >
<%
	ImportBillForm ibf = (ImportBillForm)request.getAttribute("billInfo");
	String ifprint = (String)request.getAttribute("ifprint");
	List bdl = (List)request.getAttribute("billDetail");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
	String skind="";
	if(ibf.getKind()==1)
		skind="����";
	else if(ibf.getKind()==-1)
		skind="�����˻�";
	
%><br>
<div id="main">
<h2 align="center" style="font-family: ����_GB2312;"><%=ibf.getGongsi() %>��<%=(ibf.getIeva()==1?"����":"")%><%=skind %>����<%if(ibf.getConfirmFlage().equals("3")) out.print("(�ѳ���)"); %></h2>
<table width="100%" align="cneter">
<tr >
	<td >���ţ�<b><%=ibf.getBillId() %></b></td>
	<td >��Ӧ�̣�<%=ibf.getFactoryName()%></td>
	<td align=right><%=skind %>ʱ�䣺<%=ibf.getImportTime()%></td>
</tr>
<tr>
	<td>���ƺţ�<%=(ibf.getCarNo().equals("")?"��":ibf.getCarNo()) %></td>
	<td>�˷ѣ�<%=(ibf.getYunFei()==0?"��":ibf.getYunFei()) %>&nbsp;&nbsp;&nbsp;&nbsp;
	<%
	if(!ibf.getFpName().equals("�վ�"))
		out.print("��Ʊ���ͣ�"+ibf.getFpName());
	%>
	&nbsp;&nbsp;&nbsp;&nbsp;���ʽ��<%=ibf.getFkName()%></td>
	<td align=right>�������ޣ�<%=ibf.getPayLimTime()%></td>
</tr>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="0" cellspacing="0" style="border-collapse:collapse">
	<tr><td>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="5" cellspacing="0" style="border-collapse:collapse">


		</tr>
		<tr bgcolor="#ffffff">
			<td align="center" ><b>�ֿ�<b></td>
			<td align="center"><b>��Ʒ���</b></td>
			<td align="center"><b>��Ʒ����</b></td>
			<td align="center"><b>���</b></td>
			<td align="center"><b>����</b></td>
			<td align="center"><b>����</b></td>
			<td align="center"><b>����</b></td>
			<td align="center"><b>����(��)</b></td>
			<td align="center"><b>���ط���</b></td>
			<td align="center"><b>����(Ԫ/��)</b></td>
			<td align="center"><b>���(Ԫ)</b></td>
			<td align="center"><b>˵��</b></td>

		</tr>
		<%
		GoodsImportGoodsInfo tmpInfo=null;
		
	      	for (int index=0; index<bdl.size(); index++)
			{
	      		tmpInfo = (GoodsImportGoodsInfo)bdl.get(index);
	      	%>
		<tr bgcolor="#ffffff" >
			<td align='left'><%= tmpInfo.getStoreName() %></td>
			<td align='left'><%= tmpInfo.getGoodsId() %></td>
			<td align='left'><%= tmpInfo.getGoodsName() %></td>
			<td align='left'><%= tmpInfo.getGuige() %></td>
			<td align='left'><%= tmpInfo.getCaizhi() %></td>
			<td align='left'><%= tmpInfo.getChandi()%></td>
			<td align='right'>
			<%	
			if(tmpInfo.getImportNum()!=0)
				out.print(tmpInfo.getImportNum()*ibf.getKind()+tmpInfo.getDanwei());
			else if(tmpInfo.getJianxishu()>0) 
				out.print(oConvert.getRound(tmpInfo.getImportAmount()*ibf.getKind()*1000/tmpInfo.getJianxishu(),1)+"��"); 
			%></td>
			<td align='right'><%= tmpInfo.getImportAmount()*ibf.getKind() %></td>
			<td align='center'>
			<%
			if(tmpInfo.getXishu()>0 || tmpInfo.getJianxishu()>0)
				out.print("����");
			else out.print("����");
			%></td>
			<td align='right'><%= tmpInfo.getImportUnitPrice()%></td>
			<td align='right'><%= nf.format(oConvert.getRound(tmpInfo.getImportUnitPrice()*tmpInfo.getImportAmount()*ibf.getKind(),2)) %></td>
			<td><%= tmpInfo.getMemo()%></td>

		</tr>
		<%			}%>
		
		<tr bgcolor="#ffffff">
			<td align="center"><b>�ϼ�</b></td>
			<td></td><td></td><td></td><td></td><td></td><td></td>
			<td align="right"><%=ibf.getTotalNum()*ibf.getKind() %></td><td></td><td></td>
			<%if(ibf.isIyunfei()){ %>
			<td align="right"><u><%=nf.format(ibf.getTotalPrice()*ibf.getKind()+ibf.getYunFei())%></u></td>
			<%}else{ %>
			<td align="right"><u><%=nf.format(ibf.getTotalPrice()*ibf.getKind())%></u></td>
			<%} %>
			<td></td>
		</tr>

		<tr bgcolor="#ffffff">
			<%if(ibf.isIyunfei()){ %>
			<td align="center"><b>Ӧ������</b></td>
			<td colspan="6" align="left" ><%=nf.format(ibf.getTotalPrice()*ibf.getKind())%></td>
			<%}else{ %>
			<td align="center"><b>��д�ϼ�</b></td>
			<td colspan="6" align="left" ><%chineseMoney cm=new chineseMoney();out.print(cm.getChineseMoney(ibf.getTotalPrice()*ibf.getKind())); %></td>
			<%} %>
			<td align=center><b>��ע</b></td><td colspan="4"><%=ibf.getMemo() %></td>
		</tr>
	</table></td></tr>
	</table>
	<table width="100%" align="cneter">
	<tr>
	<td>�Ƶ�Ա��<%=ibf.getCreatePerson()%></td>
	<td>����ˣ�</td>
	<td>�����ˣ�</td>
	</tr>
	</table>
</div>
<script>
	var LODOP;
	function printBill()
	{
		LODOP=getLodop();  
		LODOP.PRINT_INITA(0,0,522,333,"������");	
		//LODOP.PRINT_INIT("��ӡ�ؼ�������ʾ_Lodop����_ȫҳ");
		LODOP.SET_PRINT_PAGESIZE(0,2410,1300,"241");
		LODOP.ADD_PRINT_HTM("5%","5%","90%","90%",document.getElementById('main').innerHTML);
		LODOP.PREVIEW();
	}
<%if(ifprint.equals("1")){%>
	window.onload=function(){
		printBill();	
	};
<%}%>
</script>

<p align="center"><input type="button" name="printtr" value=" ��ӡ " onclick="printBill();">
&nbsp;<input type="button" name="close" value=" ����" onclick="
<%if(ifprint.equals("1"))
		out.print("window.location='importGoodsBill.do?kind="+ibf.getKind()+"';");
	else
		out.print("window.close();");
%>">
</body>

</html>


