
<%@ page contentType="application/msexcel;charset=UTF-8" language="java"%> 
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.web.global.*"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="mediastore.web.form.UserInfoForm"%>
<%@ page import="java.text.NumberFormat"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
</head>
<body>
      
<%
	UserInfoForm ctx = (UserInfoForm)request.getSession().getAttribute(Globals.SESSION_CONTEXT);
	GoodsInfoForm gif = (GoodsInfoForm)request.getAttribute("gif");
	ResultsetList result=(ResultsetList)request.getAttribute("result");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
	response.setHeader("Content-disposition","attachment; filename="+oConvert.FormDateShort(new java.util.Date())+".xls"); 
%>
<br>
<h2 align="center" style="font-family: 楷体_GB2312;">库存清单</h2>
<table width="100%" align="cneter">
<tr >
	<td >
	<%
	if(!gif.getGoodsClass().equals("0"))
	{
		out.print("所属大类："+gif.getGoodsClassName()+" ");
	}
	if(!gif.getGoodsType().equals("0"))
	{
		out.print("所属子类："+gif.getGoodsTypeName()+" ");
	}
	if(gif.getStoreid()!=0)
	{
		out.print("仓库："+gif.getStoreName()+" ");
	}
	if(gif.getChandi().length()>0)
	{
		out.print("产地："+gif.getChandi()+" ");
	}
	if(gif.getDeptid()>-1)
	{
		out.print("分公司："+gif.getShortname()+" ");
	}
	if(gif.getGoodsId().length()>0)
	{
		out.print("产品编号包含："+gif.getGoodsId()+" ");
	}
	%>
	</td>
	<td align="right">当前时间：<%=oConvert.FormDateShort(new java.util.Date())%></td>
</tr>
</table>
	<table width="100%" align="center" bordercolor="#000000" border="1" cellpadding="0" cellspacing="0" style="border-collapse:collapse">
	<tr><td>
	<table width="100%" align="center" bordercolor="#000000" border="1" cellpadding="3" cellspacing="0" style="border-collapse:collapse">

		<tr bgcolor="#ffffff">
		<th>产品编号</th>
		<th>名称</th>
		<th>规格</th>
		<th>材质</th>
		<th>产地</th>
		<th>仓库</th>
		<th>件数</th>
		<th>个数</th>
		<th>当前库存</th>
		<th>成本价</th>
		<th>建议售价</th>
		<th>备注</th>

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
			<td align="center"><b>合计</b></td>
			<td></td><td></td><td></td><td></td><td></td><td></td><td></td>
			<td align="right"><%=result.allsumnum%></td><td></td>
			<td align="right"><u></u></td>
		</tr>
	</table></td></tr>
	</table>
	<table width="100%" align="cneter">
	<tr>
	<td>制单员：<%=ctx.getUserName()%></td>
	<td>库管员：</td>
	</tr>
	</table>
	
<script>
window.close();
</script>
    	
</body>
</html>
