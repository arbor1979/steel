<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.global.*"%>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.rule.*"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<script language="JavaScript">

function mySubmit1(){

	document.form1.curpage.value = '1';
	document.form1.classid.value =cat.form1.classid.value;
	document.form1.typeid.value = cat.form1.typeid.value;
	document.form1.action="exportItemSearch.do";
	document.form1.submit();
}

function gotoPage(){
	
	//该值为要条转到的页面的起始记录号
	//alert(document.all.gotopagenum.value);

	document.form1.curpage.value = document.all.gotopagenum.value;
	document.form1.action="exportItemSearch.do";
	document.form1.submit();
	
}


function gotoPageByN(n){
	
	document.form1.curpage.value = n;
	document.form1.action="exportItemSearch.do";
	document.form1.submit();
	
}
function orderList(order,old)
{
	if(order==old)
		order=order+' desc';
	form1.orderby.value=order;
	form1.submit();
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
    <td width="379" height="20" class="mainhead">销售明细查询</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td height="300" align="center" valign="top">   
          
<%
	ExportItemSearchRule iisr = (ExportItemSearchRule)request.getAttribute("iisr");
	ResultsetList ibsri = (ResultsetList)request.getAttribute("ibsri");
	List billIdList = (List)request.getAttribute("billIdList");
	List StoreList = (List)request.getAttribute("StoreList");
	List PersonList = (List)request.getAttribute("PersonList");
	List departList = (List)request.getAttribute("departList");
	List cdList = (List)request.getAttribute("cdList");
	int curStartRecNum = ibsri.curpage;		//当前起始记录号
	int curPageRecNum = ibsri.pagesize;		//页记录数
	
	
	String tmpStr;
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
%>

    	<form name="form1" method="post" action="" onSubmit="return mySubmit1()">
    	<font color=#215385><b>查询条件</b></font>
    	<IMG src="images/line1.gif" border=0 width=900>
    	<br>
    	<input type="hidden" name="curpage"  value="1">
    	<input type="hidden" name="classid"  value="">
    	<input type="hidden" name="typeid"  value="">
    	<input type="hidden" name="orderby"  value="<%=iisr.getOrderStr() %>">
    	<input type="hidden" name="searchbutton" value="查询">
    	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	  <tr>
            <td  align="right"> 
                <b>单号:</b>
            </td>
            <td>
            	<SELECT size=1 name="billId">
            		<option value="0">请选择...</option>
            		<%
            			int tmpInt=0;
            			for(int i=0; i<billIdList.size(); i++)
            			{
            				tmpInt = oConvert.getInt((String)billIdList.get(i),0);
            		%>
            		<option value="<%=tmpInt%>" <%=((tmpInt==iisr.getGif().getBillId())?"selected":"") %>><%=tmpInt%></option>
			<%
				}
			%>
            	</SELECT>
            </td>
            <td  align="right"> 
                <b>货物标识:</b>
            </td>
            <td>
            	<input type=text name='goodsId' size="20" class="none" value=<%=iisr.getGif().getGoodsId()%>>
            </td>
            <td  align="right"> 
                <b>录入员:</b>
            </td>
            <td>
            	<SELECT size=1 name="salesPerson">
            		<option value="">请选择...</option>
            		<%
            			for(int i=0; i<PersonList.size(); i++)
            			{
            				tmpStr = (String)PersonList.get(i);
            		%>
            		<option value="<%=tmpStr%>" <%=(iisr.getGif().getSalesPerson().equals(tmpStr)?"selected":"") %>><%=tmpStr%></option>
			<%
				}
			%>
            	</SELECT>
            </td>
            
          </tr>
          <tr>
            <td  align="right"> 
                <b>仓库:</b>
            </td>
            <td>
            	<SELECT size=1 name="storeId">
            		<option value="">请选择...</option>
            		<%
	            		StoreInfoForm sif=new StoreInfoForm();
            			for(int i=0; i<StoreList.size(); i++)
            			{
            				sif = (StoreInfoForm)StoreList.get(i);
            		%>
            		<option value="<%=sif.getId()%>" <%=(iisr.getGif().getStoreId()==sif.getId()?"selected":"") %>><%=sif.getName()%></option>
			<%
				}
			%>
            	</SELECT>
            </td>
            <td  align="right"> 
                <b>产品名称:</b>
            </td>
            <td>
            	<input type=text name='goodsName' size="20" class="none" value="<%=iisr.getGif().getGoodsName()%>">
            </td>
             <td align="right"> 
                <b>销售单价:</b>
	         </TD>
	         <td>
	         	<select name="oper">
	         		<option value="" <%=(iisr.getOper().equals("")?"selected":"") %>>请选择...</option>
	         		<option value=">" <%=(iisr.getOper().equals(">")?"selected":"") %>>大于</option>
	         		<option value="<" <%=(iisr.getOper().equals("<")?"selected":"") %>>小于</option>
	         		<option value="=" <%=(iisr.getOper().equals("=")?"selected":"") %>>等于</option>
	         	</select>
	         	<input type=text name='exportUnitPrice' size="10" class="none" value="<%=(iisr.getOper().equals("")?"":iisr.getGif().getExportUnitPrice()) %>">
	         </td>
          </tr>
          <tr>
			<TD align="right"><b>录入时间:</b></TD>
			<TD colspan=3>起始 
				<input type=text name='importtime1' size="20" class="none" value="<%=iisr.getExportTime1() %>">
				<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="弹出日历菜单"  onClick="Calendar_importtime1();">&nbsp; 
				终止 
				<input type=text name='importtime2'  size="20" class="none" value="<%=iisr.getExportTime2() %>"> 
				<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="弹出日历菜单"  onClick="Calendar_importtime2();">
			</TD>
			 <td  align="right"> 
                <b>产地:</b>
			 </td>
            <td> 
			<select name="chandi">
		   		<option value="" <%if(iisr.getGif().getChandi().equals("")) out.print("selected");%>>-全部-</option>
		<%
				
				String s[];
				for(int i=0;i<cdList.size();i++)
				{
					
					s=(String[])cdList.get(i);
					if (s[0].equals(iisr.getGif().getChandi()))
						out.print("<option value='"+s[0]+"' selected>"+s[0]+"</option>");
					else
						out.print("<option value='"+s[0]+"'>"+s[0]+"</option>");
				}
		%>
		      </select> 
            </td>
          </tr>
          <tr>
          	 <td align="right"> 
                <b>产品类别:</b>
	         </TD>
          	<td colspan=3>
          	<iframe name="cat" id="cat" src="ClassAndType.do?classid=<%=iisr.getGoodsClass() %>&typeid=<%=iisr.getGoodsType() %>" width="300" height="25" frameborder=0 scrolling=no ></iframe>
          	</td>
          	<%if(ctx.isIfview()) {%>
             <td  align="right" > 
                <b>分公司:</b></td>
                <td>
            	<select name="deptid" >
    			<option value="-1">-全部-</option>
    			<%
    			DepartInfoForm dif=new DepartInfoForm();
    			for(int i=0;i<departList.size();i++)
    			{
    				dif=(DepartInfoForm)departList.get(i);
    			%>
    			<option value="<%=dif.getId() %>" <%=(iisr.getGif().getDeptid()==dif.getId()?"selected":"") %>><%=dif.getShortname() %></option>
    			<%
    			}
    			%>
    			</select>
    			
            <%}else{%>
            <td>&nbsp;</td><td><input type="hidden" name="deptid" value="<%=ctx.getDeptid() %>">
            <%} %>	
          &nbsp;<input name=searchbutton type=submit value=" 查询 " ></td>
	</TABLE>
	</form>
<%
	if(ibsri!=null)
	{
%>
	<form name="form2" method="post" action="" >
	<font color=#215385><b>售货明细列表</b>（共 <b><%=ibsri.allnum%></b> 条明细信息）</font>
	<br>
	<div align="left">
	&nbsp;&nbsp;<font color=#215385>售货总数：<b><%=ibsri.allsumnum%></b> 吨&nbsp;  售货总额:<b><%=nf.format(ibsri.allmoney) %></b> 元&nbsp;&nbsp;
	</div>
	<IMG src="images/line1.gif" border=0 width=900>
	
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  

		<tr bgcolor="#C2CEDC">
		<th><a class="tablehead" href="javascript:orderList('billid','<%=iisr.getOrderStr()%>');">单号</a><%if(iisr.getOrderStr().equals("billid")) out.print("↓"); if(iisr.getOrderStr().equals("billid desc")) out.print("↑");%></th>
		<th>类型</th>
		<%if(ctx.isIfview()) {%>
		<th><a class="tablehead" href="javascript:orderList('shortname','<%=iisr.getOrderStr()%>');">分公司</a><%if(iisr.getOrderStr().equals("shortname")) out.print("↓"); if(iisr.getOrderStr().equals("shortname desc")) out.print("↑");%></th>
		<%} %>
		<th><a class="tablehead" href="javascript:orderList('a.goodsid','<%=iisr.getOrderStr()%>');">货物标识</a><%if(iisr.getOrderStr().equals("a.goodsid")) out.print("↓"); if(iisr.getOrderStr().equals("a.goodsid desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('goodsname','<%=iisr.getOrderStr()%>');">货物名称</a><%if(iisr.getOrderStr().equals("goodsname")) out.print("↓"); if(iisr.getOrderStr().equals("goodsname desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('chandi','<%=iisr.getOrderStr()%>');">产地</a><%if(iisr.getOrderStr().equals("chandi")) out.print("↓"); if(iisr.getOrderStr().equals("chandi desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('guige','<%=iisr.getOrderStr()%>');">规格</a><%if(iisr.getOrderStr().equals("guige")) out.print("↓"); if(iisr.getOrderStr().equals("guige desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('b.name','<%=iisr.getOrderStr()%>');">仓库</a><%if(iisr.getOrderStr().equals("b.name")) out.print("↓"); if(iisr.getOrderStr().equals("b.name desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('ExportAmount','<%=iisr.getOrderStr()%>');">数量</a><%if(iisr.getOrderStr().equals("ExportAmount")) out.print("↓"); if(iisr.getOrderStr().equals("ExportAmount desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('ExportUnitPrice','<%=iisr.getOrderStr()%>');">单价</a><%if(iisr.getOrderStr().equals("ExportUnitPrice")) out.print("↓"); if(iisr.getOrderStr().equals("ExportUnitPrice desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('ExportAmount*ExportUnitPrice','<%=iisr.getOrderStr()%>');">金额</a><%if(iisr.getOrderStr().equals("ExportAmount*ExportUnitPrice")) out.print("↓"); if(iisr.getOrderStr().equals("ExportAmount*ExportUnitPrice desc")) out.print("↑");%></th>
		<%if(ctx.isIfjiagong()){ %>
		<th><a class="tablehead" href="javascript:orderList('jiagong','<%=iisr.getOrderStr()%>');">加工费</a><%if(iisr.getOrderStr().equals("jiagong")) out.print("↓"); if(iisr.getOrderStr().equals("jiagong desc")) out.print("↑");%></th>
		<%} %>
		<th><a class="tablehead" href="javascript:orderList('salesperson','<%=iisr.getOrderStr()%>');">录入员</a><%if(iisr.getOrderStr().equals("salesperson")) out.print("↓"); if(iisr.getOrderStr().equals("salesperson desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('createtime','<%=iisr.getOrderStr()%>');">时间</a><%if(iisr.getOrderStr().equals("createtime")) out.print("↓"); if(iisr.getOrderStr().equals("createtime desc")) out.print("↑");%></th>
		<th><a class="tablehead" href="javascript:orderList('memo','<%=iisr.getOrderStr()%>');">备注</a><%if(iisr.getOrderStr().equals("memo")) out.print("↓"); if(iisr.getOrderStr().equals("memo desc")) out.print("↑");%></th>
		</tr>
	
		<%
		
		int index = 0;
		GoodsExportGoodsInfo tmpInfo = null;
	    String color="";
	    for (index=0; index<ibsri.rslist.size(); index++)
		{
	      	tmpInfo = (GoodsExportGoodsInfo)ibsri.rslist.get(index);
	      	if(tmpInfo.getExportAmount()>0)
	      		color="<font color=black>";
	      	else
	      		color="<font color=red>";
	      	%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        
	        <td align='center' width=50><%=color%><a href="javascript:viewDetail('<%= request.getContextPath()+ "/exportItemsView.do?billId=" + tmpInfo.getBillId()+"&deptid="+tmpInfo.getDeptid() %>');"><IMG src="images/news1.gif" border=0 alt='查看货单明细'></a><%= tmpInfo.getBillId() %></font></td>
   	        <td align='center' width=30><%=color%><%=(tmpInfo.getExportAmount()>0?"售":"退") %></font></td>
	        <%if(ctx.isIfview()){%><td align='center'><%=color%><%= tmpInfo.getShortname() %></font></td><%} %>
	        <td align='center'><%=color%><%= tmpInfo.getGoodsId() %></font></td>
	        <td align='center'><%=color%><%= tmpInfo.getGoodsName() %></font></td>
	        <td align='center'><%=color%><%= tmpInfo.getChandi() %></font></td>
	        <td align='center'><%=color%><%= tmpInfo.getGuige() %></font></td>
	        <td align='center'><%=color%><%= tmpInfo.getStoreName() %></font></td>
			<td align='right'><%=color%><%= tmpInfo.getExportAmount() %></font></td>
			<td align='right'><%=color%><%= nf.format(tmpInfo.getExportUnitPrice()) %></font></td>
			<td align='right'><%=color%><%= nf.format(tmpInfo.getExportAmount()*tmpInfo.getExportUnitPrice()) %></font></td>
			<%if(ctx.isIfjiagong()){ %>
			<td align='right'><%=color%><%=(tmpInfo.getJiagong()==0?"":nf.format(tmpInfo.getJiagong())) %></font></td>
			<%} %>
			<td align='center' width=60><%=color%><%= tmpInfo.getSalesPerson() %></font></td>
			<td align='center' width=80><%=color%><%= tmpInfo.getCreateTime() %></font></td>
			<td align='center'><%=color%><%= tmpInfo.getMemo() %></font></td>
	        </tr>
	    <%
		}
	    %>
	</TABLE>
	</form>

	<div align="right">
		<%
			if(curStartRecNum>1)
			{
		%>
		<a href="javascript:gotoPageByN(1)"><IMG src="images/startpage.gif" border=0 alt='首页'></a>&nbsp;
		<a href="javascript:gotoPageByN(<%=curStartRecNum-1%>)"><IMG src="images/lastpage.gif" border=0 alt='上一页'></a>&nbsp;&nbsp;
		<%
			}
			else
			{
		%>
		<IMG src="images/startpage.gif" border=0 alt='首页'>&nbsp;
		<IMG src="images/lastpage.gif" border=0 alt='上一页'>&nbsp;&nbsp;
		<%	
			}
	    	if(curStartRecNum<ibsri.pagecount)
	    	{
	    %>
	    <a href="javascript:gotoPageByN(<%=curStartRecNum+1%>)"><IMG src="images/nextpage.gif" border=0 alt='下一页'></a>&nbsp;
	    <a href="javascript:gotoPageByN(<%=ibsri.pagecount%>)"><IMG src="images/endpage.gif" border=0 alt='末页'></a>&nbsp;&nbsp;&nbsp;&nbsp;
	    <%
	    	}
			else
			{
		%>
		<IMG src="images/nextpage.gif" border=0 alt='下一页'>&nbsp;
		<IMG src="images/endpage.gif" border=0 alt='末页'>&nbsp;&nbsp;&nbsp;&nbsp;
		<%	
			}
	    %>
    	</div>
    	
    	<div align="center">
		第 <select name="gotopagenum" onChange="gotoPage()">
		<%for(int i=1; i<=ibsri.pagecount; i++)
		  {%>
			<option value="<%=i%>" <%if(i==curStartRecNum){%>selected<%}%>><%=i%></option>
		<%}%>
		</select> 页
	</div>
<%
	}
%>
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

