
<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.web.global.*"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="mediastore.web.form.UserInfoForm"%>
<%@ page import="java.text.NumberFormat"%>
<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<link rel="stylesheet" href="css/stylePrint.css" type="text/css">
</head>
<body>
      
<%
	UserInfoForm ctx = (UserInfoForm)request.getSession().getAttribute(Globals.SESSION_CONTEXT);
	GoodsInfoForm gif = (GoodsInfoForm)request.getAttribute("gif");
	ResultsetList result=(ResultsetList)request.getAttribute("result");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
	

%>
<br>
<h2 align="center" style="font-family: ����_GB2312;">����嵥</h2>
<table width="100%" align="cneter">
<tr >
	<td >
	<%
	if(!gif.getGoodsClass().equals("0"))
	{
		out.print("�������ࣺ"+gif.getGoodsClassName()+" ");
	}
	if(!gif.getGoodsType().equals("0"))
	{
		out.print("�������ࣺ"+gif.getGoodsTypeName()+" ");
	}
	if(gif.getStoreid()!=0)
	{
		out.print("�ֿ⣺"+gif.getStoreName()+" ");
	}
	if(gif.getChandi().length()>0)
	{
		out.print("���أ�"+gif.getChandi()+" ");
	}
	if(gif.getDeptid()>-1)
	{
		out.print("�ֹ�˾��"+gif.getShortname()+" ");
	}
	if(gif.getGoodsId().length()>0)
	{
		out.print("��Ʒ��Ű�����"+gif.getGoodsId()+" ");
	}
	%>
	</td>
	<td align="right">��ǰʱ�䣺<%=oConvert.FormDateShort(new java.util.Date())%></td>
</tr>
</table>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="0" cellspacing="0" style="border-collapse:collapse">
	<tr><td>
	<table width="100%" align="center" bordercolor="#000000" border=1 cellpadding="3" cellspacing="0" style="border-collapse:collapse">

		<tr bgcolor="#ffffff">
		<th>��Ʒ���</th>
		<th>����</th>
		<th>���</th>
		<th>����</th>
		<th>����</th>
		<th>�ֿ�</th>
		<th>����</th>
		<th>����</th>
		<th>��ǰ���</th>
		<th width=80>�ɱ���</th>
		<th width=80>�����ۼ�</th>
		<th>��ע</th>

		</tr>
		<%
			   	int index = 0;
			   	GoodsInfoForm tmpInfo = null;	
			   	for (index=0; index<result.rslist.size(); index++)
				{
			      	tmpInfo = (GoodsInfoForm)result.rslist.get(index);
		%>
       <tr bgcolor="#ffffff">  
	        <td width="80"><%=tmpInfo.getGoodsId()%></td>  
	        <td><%=tmpInfo.getGoodsTypeName()%></td>
			<td align="center"><%=tmpInfo.getGuige()%></td>
			<td align="center"><%=tmpInfo.getCaizhi()%></td>
			<td align="center"><%=tmpInfo.getChandi()%></td>
			<td align="center"><%=tmpInfo.getStoreName()%></td>
			<td align="right"><%if(tmpInfo.getJianxishu()>0) out.print(oConvert.getRound(tmpInfo.getCurRepertory()*1000/tmpInfo.getJianxishu(),1));%></td>
			<td align="right"><%if(tmpInfo.getXishu()>0) out.print((int)oConvert.getRound(tmpInfo.getCurRepertory()*1000/tmpInfo.getXishu(),0));%></td>
			<td align="right"><%=tmpInfo.getCurRepertory()%></td>
			<%if(ctx.getUserName().equals("admin")){ %>
			<td align="right"><%=tmpInfo.getAvgprice()%></td>
			<%} %>
			<td align="right"><%=tmpInfo.getPurchaseUnitPrice()%></td>
			<td align="right"><%=tmpInfo.getMemo()%></td>
       </tr>
<%
	    }
%>
		
		<tr bgcolor="#ffffff">
			<td align="center"><b>�ϼ�</b></td>
			<td></td><td></td><td></td><td></td><td></td><td></td><td></td>
			<td align="right"><%=result.allsumnum%></td><td></td>
			<td align="right"><u></u></td>
		</tr>
	</table></td></tr>
	</table>
	<table width="100%" align="cneter">
	<tr>
	<td>�Ƶ�Ա��<%=ctx.getUserName()%></td>
	<td>���Ա��</td>
	</tr>
	</table>
	
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
window.history.back(-1);
</script>
    	
</body>
</html>
