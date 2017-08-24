<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js"></script>
<script language="JavaScript">

function submitform(){
	if(form1.billId.value=='0')
	{
		alert('请选择需要撤销的销售单号');
		form1.billId.focus();
		return false;
	}
	if(confirm('是否确认撤销此单？'))
		form1.submit();
	else
		return false;
}
function changeBill(billid)
{
	window.location='exportCancel.do?billId='+billid;
}
</script>
</head>
<%
	ExportGoodsBillFB igbfb = (ExportGoodsBillFB)request.getAttribute(Globals.REQUEST_EXPORTGOODSBILL);
	ExportBillForm ibf = igbfb.getEbf();
	List goodsList = igbfb.getGoodsList();
	List billList=igbfb.getFactList();
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
    <td width="379" height="20" class="mainhead">销售单撤销</td>
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
	<form name="form1" method="post" action="exportCancel.do" onSubmit="javascript:return verify(this);">
	<input type="hidden" name="param" value="submit">
    	<TABLE  width="100%" border="0" cellpadding="3" cellspacing="0">

			<tr>
				 <td  align="right" width=10%> 
                <b>*销售单号:</b>
            	</td>
            	<td>
            	<SELECT name="billId" onChange="changeBill(this.value);">
            		<option value="0">-全部-</option>
            		<%
            		            	String tmpStr;
            		            	for(int i=0; i<billList.size(); i++)
            		            	{
            		            		tmpStr = (String)billList.get(i);
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
	
	<form name="form2" method="post" action="exportCancel.do" >
	<input type="hidden" name="billId"  value="<%=ibf.getBillId()%>"> 	
	<input type="hidden" name="obj4numbercheck" value="">
	<input type="hidden" name="param" value="submit">	
	<TABLE width="100%" cellpadding="3" > 
          <tr>
          	<td  align="right" width=10%> 
                <b>客户:</b>
            </td>
            <td colspan=3><input type="text" size=52 class="none" value="<%=ibf.getFactoryName() %>" disabled></td>
            <td  align="right" width=10%> 
                <b>发票类型:</b>
            </td>
            <td><input type="text" size=15 class="none" value="<%=ibf.getFpName() %>" disabled></td>
            
            <td  align="right" width=10%> 
                <b>收款方式:</b>
            </td>
            <td><input type="text" size=15 class="none" value="<%=ibf.getFkName() %>" disabled> </td>
          </tr>
          <tr>
          	<td  align="right" > 
                <b>收款期限:</b>
            </td>
            <td ><input type="text" size=15 class="none" value="<%=ibf.getPayLimTime() %>" disabled></td>
            <td align="right" width=10%>
            	<b>备注说明:</b>
            </td>
            <td ><input type="text" size=15 class="none" value="<%=ibf.getMemo() %>" disabled></td>
            <td  align="right" > 
                <b>运费:</b>
            </td>
            <td ><input type="text" size=15 class="none" value="<%=(ibf.getYunFei()>0?ibf.getYunFei():"") %>" disabled></td>
          	<td  align="right" > 
                <b>车牌号:</b>
            </td>
            <td ><input type="text" size=15 class="none" value="<%=ibf.getCarNo() %>" disabled> </td>
            <td  align="right" > 
                <b></b>
            </td>
            </td>

	</TABLE>
	<br>
	<font color=#215385><b>货物列表</b></font>
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE  class="mailtable">  
		
		<tr bgcolor="#C2CEDC">
		<th></th>
		<th>仓库</th>
		<th>产品编号</th>
		<th>产品名称</th>
		<th>规格</th>
		<th>产地</th>
		<th>件数</th>
		<th>个数</th>
		<th>重量(吨)</th>
		<th>价格(元/吨)</th>
		<th>金额</th>
		</tr>
		
		
		<%
		int index = 0;
	    GoodsExportGoodsInfo tmpInfo = null;
	      	
	    for (index=0; index<goodsList.size(); index++)
		{
	      		tmpInfo = (GoodsExportGoodsInfo)goodsList.get(index);
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
	        <td align='center'><%=(tmpInfo.getJianxishu()>0?tmpInfo.getExportJianNum():"") %></td>
	        <td align='center'><%=(tmpInfo.getXishu()>0?tmpInfo.getExportNum():"") %></td>
	        <td align='center'><%=tmpInfo.getExportAmount() %></td>
			<td align='center'><%=tmpInfo.getExportUnitPrice() %></td>
	        <td align='right'><%=oConvert.getRound(tmpInfo.getExportUnitPrice()*tmpInfo.getExportAmount(),2) %></td>
	       </tr>
	        <%
	
	      	}
	        %>
	</TABLE>
	</form>
	
	<TABLE  class="mailtable"> 
	  <tr>
            <td  align="right" > 
                <b>单号:</b>
            </td>
            <td >
            	<INPUT class=none  name="billid" type="text" size="20" value="<%=ibf.getBillId()%>" disabled>
            </td>
            <td  align="right" > 
                <b>重量:</b>
            </td>
            <td >
            	<INPUT class=none  name="totalPrice" type="text" size="15" value="<%=oConvert.getRound(ibf.getTotalNum(),4)%>" disabled>
            </td>
            <td  align="right" > 
                <b>金额:</b>
            </td>
            <td >
            	<INPUT class=none  name="totalPrice" type="text" size="15" value="<%=nf.format(oConvert.getRound(ibf.getTotalPrice(),2))%>" disabled>
            </td>
          </tr>
     </TABLE>
	
	<p><center><input name=submitreal type="button" value="确认撤销" onClick="submitform();">
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

