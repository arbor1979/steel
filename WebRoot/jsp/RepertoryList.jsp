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

function mySubmit()
{
	form1.goodsClass.value=cat.form1.classid.value;
	form1.goodsType.value=cat.form1.typeid.value;
	return true;
}
function orderList(order,old)
{
	if(order==old)
		order=order+' desc';
	form1.ifprint.value=0;
	form1.orderby.value=order;
	form1.submit();
}
function updateMemo(goodsid,storeid,memo)
{
	window.navigate('repertoryList.do?param=updatememo&goodsId='+goodsid+'&storeid='+storeid+'&memo='+memo);
}

</script>
</head>
<body background='images/bgall.gif' onkeyup="if(event.keyCode==13) form1.searchbutton.focus();">
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
    <td width="379" height="20" class="mainhead">����嵥</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td height="300" align="center" valign="top">   
          
<%////
	//---��ҳ������л�ȡ����---
	GoodsInfoForm gif = (GoodsInfoForm)request.getAttribute("gif");
	List StoreList = (List)request.getAttribute("StoreList");
	List cdList = (List)request.getAttribute("cdList");
 	List departList = (List)request.getAttribute("departList");
	ResultsetList result=(ResultsetList)request.getAttribute("result");
	String orderby=(String)request.getAttribute("orderby");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
%>

    	<form name="form1" method="post" action="repertoryList.do" onSubmit="return mySubmit();">
    	<font color=#215385><b>��ѯ����</b></font>
    	<IMG src="images/line1.gif" border=0 width=900>
    	<br>
    	  <input type="hidden" name="goodsType" value="<%=gif.getGoodsType() %>">
          <input type="hidden" name="goodsClass" value="<%=gif.getGoodsClass() %>">
          <input type="hidden" name="ifprint" value="0">
          <input type="hidden" name="orderby"  value="<%=orderby %>">
    	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	  
	  <tr>
            
            <td  align="right"> 
                <iframe name="cat" id="cat" src="ClassAndType.do?classid=<%=gif.getGoodsClass() %>&typeid=<%=gif.getGoodsType() %>" width="300" height="25" frameborder=0 scrolling=no ></iframe>
            </td>
            <td  align="right"> 
			 <b>����:</b>
			<select name="chandi">
		   		<option value="" <%if(gif.getChandi().equals("")) out.print("selected");%>>-ȫ��-</option>
		<%
				
				String s[];
				for(int i=0;i<cdList.size();i++)
				{
					
					s=(String[])cdList.get(i);
					if (s[0].equals(gif.getChandi()))
						out.print("<option value='"+s[0]+"' selected>"+s[0]+"</option>");
					else
						out.print("<option value='"+s[0]+"'>"+s[0]+"</option>");
				}
		%>
		      </select> 
            </td>
            <td  align="right"> 
                <b>�ֿ�:</b>
            </td>
            <td>
            	<SELECT size=1 name="storeid">
            		<option value="">��ѡ��...</option>
            		<%
	            		StoreInfoForm sif=new StoreInfoForm();
            			for(int i=0; i<StoreList.size(); i++)
            			{
            				sif = (StoreInfoForm)StoreList.get(i);
            		%>
            		<option  value="<%=sif.getId()%>" <%=(gif.getStoreid()==sif.getId()?"selected":"") %>><%=sif.getName()%></option>
			<%
				}
			%>
            	</SELECT>
            </td>
            <%
			if(ctx.isIfview()) {
			%>
             <td  align="left" > 
                <b>�ֹ�˾:</b></td>
                <td>
            	<select name="deptid" >
    			<option value="-1">��ѡ��...</option>
    			<%
    			DepartInfoForm dif=new DepartInfoForm();
    			for(int i=0;i<departList.size();i++)
    			{
    				dif=(DepartInfoForm)departList.get(i);
    			%>
    			<option value="<%=dif.getId() %>" <%=(gif.getDeptid()==dif.getId()?"selected":"") %>><%=dif.getShortname() %></option>
    			<%
    			}
    			%>
    			</select>
    			</td>
           
            <%
            }else{
            %>
            <td>&nbsp;</td><td><input type="hidden" name="deptid" value="<%=ctx.getDeptid() %>"></td>
            <%
            }
            %>
            <td  align="right"> 
                <b>��Ʒ���:</b>
            	<input type=text name='goodsId' size="15" class="none" value=<%=gif.getGoodsId() %>>
            </td>
            <TD>
            		<input name="searchbutton" type="submit" value=" ��ѯ " onclick="form1.ifprint.value=0;">
			</TD>
          </tr>

		</TABLE>
		</form>
	<font color=#215385><b>����Ʒ</b>���� <b><%=result.rslist.size()%></b> ����</font>
	<br>
	<div align="left">
	&nbsp;&nbsp;���������<b><%=result.allsumnum %></b> ��&nbsp;  �ܳɱ����:<b><%=nf.format(result.allmoney)%></b> Ԫ&nbsp;&nbsp;
	</div>
	<IMG src="images/line1.gif" border=0 width=900>

	<TABLE width="100%" border="1" cellpadding="5" cellspacing="1" bordercolor="#FFFFFF" class="mailtable">  
	<tr bgcolor="#C2CEDC">
		<th><a class="tablehead" href="javascript:orderList('a.goodsid','<%=orderby%>');">��Ʒ���</a><%if(orderby.equals("a.goodsid")) out.print("��"); if(orderby.equals("a.goodsid desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('goodstypename','<%=orderby%>');">����</a><%if(orderby.equals("goodstypename")) out.print("��"); if(orderby.equals("goodstypename desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('guige','<%=orderby%>');">���</a><%if(orderby.equals("guige")) out.print("��"); if(orderby.equals("guige desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('caizhi','<%=orderby%>');">����</a><%if(orderby.equals("caizhi")) out.print("��"); if(orderby.equals("caizhi desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('chandi','<%=orderby%>');">����</a><%if(orderby.equals("chandi")) out.print("��"); if(orderby.equals("chandi desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('c.name','<%=orderby%>');">�ֿ�</a><%if(orderby.equals("c.name")) out.print("��"); if(orderby.equals("c.name desc")) out.print("��");%></th>
		<%
			if(ctx.isIfview()) {
		%>
		<th><a class="tablehead" href="javascript:orderList('shortname','<%=orderby%>');">�ֹ�˾</a><%if(orderby.equals("shortname")) out.print("��"); if(orderby.equals("shortname desc")) out.print("��");%></th>
		<%} %>
		<th>����</th>
		<th><a class="tablehead" href="javascript:orderList('repertoryNum','<%=orderby%>');">����</a><%if(orderby.equals("repertoryNum")) out.print("��"); if(orderby.equals("repertoryNum desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('repertoryAmount','<%=orderby%>');">��ǰ���</a><%if(orderby.equals("repertoryAmount")) out.print("��"); if(orderby.equals("repertoryAmount desc")) out.print("��");%></th>
		<%if(ctx.getUserName().equals("admin")){ %>
		<th><a class="tablehead" href="javascript:orderList('avgprice','<%=orderby%>');">�ɱ���<br></a><%if(orderby.equals("avgprice")) out.print("��"); if(orderby.equals("avgprice desc")) out.print("��");%></th>
		<%} %>
		<th><a class="tablehead" href="javascript:orderList('realprice','<%=orderby%>');">�����ۼ�<br></a><%if(orderby.equals("realprice")) out.print("��"); if(orderby.equals("realprice desc")) out.print("��");%></th>
		<!-- <th><a class="tablehead" href="javascript:orderList('avgprice*repertoryAmount','<%=orderby%>');">�ɱ����</a><%if(orderby.equals("avgprice*repertoryAmount")) out.print("��"); if(orderby.equals("avgprice*repertoryAmount desc")) out.print("��");%></th> -->
		<th><a class="tablehead" href="javascript:orderList('memo','<%=orderby%>');">��ע</a><%if(orderby.equals("memo")) out.print("��"); if(orderby.equals("memo desc")) out.print("��");%></th>
	</tr>		
<%
			   	int index = 0;
			   	GoodsInfoForm tmpInfo = null;	
			   	for (index=0; index<result.rslist.size(); index++)
				{
			      	tmpInfo = (GoodsInfoForm)result.rslist.get(index);
		%>
       <tr 
       <%if((index%2)==1)
       		out.print("bgcolor=\"#CAE4F4\"");
       	%>>  
	        <td width="80"><%=tmpInfo.getGoodsId()%></td>  
	        <td><%=tmpInfo.getGoodsTypeName()%></td>
			<td align="center"><%=tmpInfo.getGuige()%></td>
			<td align="center"><%=tmpInfo.getCaizhi()%></td>
			<td align="center"><%=tmpInfo.getChandi()%></td>
			<td align="center"><%=tmpInfo.getStoreName()%></td>
			<%
			if(ctx.isIfview()) {
			%>
			<td align='center'><%= tmpInfo.getShortname() %></td>
			<%} %>
			<td align="right"><%
			if(tmpInfo.getJianxishu()>0) 
				out.print(oConvert.getRound(tmpInfo.getCurRepertory()*1000/tmpInfo.getJianxishu(),1));
			%></td>
			<td align="right"><%
			if(tmpInfo.getXishu()>0) 
				out.print((int)oConvert.getRound(tmpInfo.getCurRepertory()*1000/tmpInfo.getXishu(),0)+tmpInfo.getDanwei());
			else
				out.print((tmpInfo.getCurRepertoryNum()>0?tmpInfo.getCurRepertoryNum()+tmpInfo.getDanwei():""));
			%></td>
			<td align="right"><%=tmpInfo.getCurRepertory()%></td>
			<%if(ctx.getUserName().equals("admin")){ %>
			<td align="right"><%=tmpInfo.getAvgprice()%></td>
			<%} %>
			<td align="right"><%=tmpInfo.getPurchaseUnitPrice()%></td>
			<!-- <td align="right"><%=nf.format(oConvert.getRound(tmpInfo.getCurRepertory()*tmpInfo.getAvgprice(),2))%></td> -->
			<td align="center"><input type="text" name="memo" id="memo"   value="<%= tmpInfo.getMemo() %>" size=12 maxlength=25 onfocus="this.select();"
	        	onChange="updateMemo('<%=tmpInfo.getGoodsId()%>',<%=tmpInfo.getStoreid()%>,this.value);">
	       </td>
       </tr>
<%
	    }
%>
	</TABLE>
	</td>
    </tr>
   
    </table>
    <p align="center"><input type="button" value=" ��ӡ " onclick="form1.ifprint.value=1;form1.submit();">&nbsp;&nbsp;
    <input type="button" value=" ���� " onclick="form1.ifprint.value=2;form1.submit();"></p> 
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
<script language="JavaScript">
window.clearTimeout(tt);
var tt = setTimeout("form1.ifprint.value=0;form1.param.value='';form1.submit()",30000);
</script>
</html>
