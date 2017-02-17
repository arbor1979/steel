<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@page import="mediastore.web.form.*"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="java.text.*"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>

<script language="JavaScript">
function SubmitThis()
{
	form1.submit();
}
function gotoPage(){
	
	//该值为要条转到的页面的起始记录号
	//alert(document.all.gotopagenum.value);

	document.form1.curpage.value = document.all.gotopagenum.value;
	document.form1.submit();
	
}


function gotoPageByN(n){
	
	document.form1.curpage.value = n;
	document.form1.submit();
	
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
	int kind = oConvert.getInt((String)request.getAttribute("kind"),0);
	int factory = oConvert.getInt((String)request.getAttribute("factory"),0);
	String operTime1 = oConvert.getString((String)request.getAttribute("opertime1"),"");
	String operTime2 = oConvert.getString((String)request.getAttribute("opertime2"),"");
	String fName=(String)request.getAttribute("fName");
	ResultsetList ys = (ResultsetList)request.getAttribute("ys");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
	String ch="";
	if(kind==1 || kind==-1)
		ch="货款";
	else
		ch="运费";
%>
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
    <td width="379" height="20" class="mainhead">预收预付明细</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <form name="form1" method="post" action="yuShouKuanSearch.do">
    <input type="hidden" name="param" value="detail">
    <input type="hidden" name="factory" value="<%=factory %>">
    <input type="hidden" name="kind" value="<%=kind %>">
    <input type="hidden" name="importtime1" value="<%=operTime1 %>">
    <input type="hidden" name="importtime2" value="<%=operTime2 %>">
    <input type="hidden" name="curpage" value="<%=ys.curpage %>">
    </form>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td height="300" align="center" valign="top">   
     <br>
     <font color=#215385><b><%=fName%></b>&nbsp;&nbsp;（<%=(operTime1.length()>0?operTime1:"建账初") %> - <%=(operTime2.length()>0?operTime2:"现在") %>）</font><br>
     
    <font color=#215385><%=(kind==1?"预收":"预付")+ch%>操作记录（共 <b><%=ys.allnum%></b> 笔，当前余额：<b><%=nf.format(ys.realmoney)%></b> 元，待结<%=ch %>：<b><%=nf.format(ys.allmoney)%></b> 元，实欠：<b><%=nf.format(ys.allmoney-ys.realmoney)%></b>）</font>
	<IMG src="images/line1.gif" width=900 border=0>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
	
		<tr bgcolor="#C2CEDC">
		<th></th>
		<th>单号</th>
		<th>操作员</th>
		<th>上次余额</th>
		<th><%=(kind==1?"预收":"预付")%>金额(元)</th>
		<th>冲抵欠款</th>
		<th>帐户余额(元)</th>
		<th><%=(kind==1?"预收":"预付")%>方式</th>
		<th>操作时间↓</th>
		</tr>
		
		<%
		
		ChongZhiForm tmpInfo = null;
        for(int index=0;index<ys.rslist.size();index++)
        { 
        	tmpInfo=(ChongZhiForm)ys.rslist.get(index);
%>
		 <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
		 	<%if(tmpInfo.getAddChuzhi()==0 && (kind==1 || (kind==0 && tmpInfo.getTypename().indexOf("出库")>-1)) ){ %>
		 	<td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath()+ "/exportItemsView.do?billId=" + tmpInfo.getBillid()+"&deptid="+tmpInfo.getDeptid() %>');"><IMG src="images/news1.gif" border=0 alt='查看单据明细'></a></td></td>
		 	<%}else if(tmpInfo.getAddChuzhi()==0 && (kind==-1 || (kind==0 && tmpInfo.getTypename().indexOf("入库")>-1)) ){ %>
		 	<td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath()+ "/importItemsView.do?billId=" + tmpInfo.getBillid()+"&deptid="+tmpInfo.getDeptid() %>');"><IMG src="images/news1.gif" border=0 alt='查看单据明细'></a></td></td>
		 	<%}else{ %>
			<td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath()+ "/shouKuanItemView.do?billId=" + tmpInfo.getBillid()+"&deptid="+tmpInfo.getDeptid() %>');"><IMG src="images/news1.gif" border=0 alt='查看单据明细'></a></td></td>
			<%} %>
		 	
	        <td align='center' width=5%><%=tmpInfo.getBillid() %></td>
	        <td align='center'><%= tmpInfo.getOperPerson() %></td>
	        <td align='right'><%= nf.format(tmpInfo.getCurChuzhi()) %></td>
	        <td align='right'><%= (tmpInfo.getAddChuzhi()!=0?nf.format(tmpInfo.getAddChuzhi()):"") %></td>
	        <td align='right'><%= (tmpInfo.getPayhuokuan()!=0?nf.format(tmpInfo.getPayhuokuan()):"") %></td>
	        <td align='right'><%= nf.format(tmpInfo.getCurChuzhi()+tmpInfo.getAddChuzhi()-tmpInfo.getPayhuokuan()) %></td>
			<td align='right'><%= tmpInfo.getFkTypeName() %></td>
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
		<a href="javascript:gotoPageByN(1)"><IMG src="images/startpage.gif" border=0 alt='首页'></a>&nbsp;
		<a href="javascript:gotoPageByN(<%=ys.curpage-1%>)"><IMG src="images/lastpage.gif" border=0 alt='上一页'></a>&nbsp;&nbsp;

		<%
			}
			else
			{
		%>
		<IMG src="images/startpage.gif" border=0 alt='首页'>&nbsp;
		<IMG src="images/lastpage.gif" border=0 alt='上一页'>&nbsp;&nbsp;
		<%	
			}

	    	if(ys.curpage<ys.pagecount)
	    	{
	    %>
	    	<a href="javascript:gotoPageByN(<%=ys.curpage+1%>)"><IMG src="images/nextpage.gif" border=0 alt='下一页'></a>&nbsp;
	        <a href="javascript:gotoPageByN(<%=ys.pagecount%>)"><IMG src="images/endpage.gif" border=0 alt='末页'></a>&nbsp;&nbsp;&nbsp;&nbsp;
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
		<%for(int i=1; i<=ys.pagecount; i++)
		  {%>
			<option value="<%=i%>" <%if(i==ys.curpage){%>selected<%}%>><%=i%></option>
		<%}%>
		</select> 页
	</div>  
	<p align="center"><input type="button" value=" 返回 " onclick="window.navigate('yuShouKuanSearch.do?kind=<%=kind %>');">
	</td></tr>
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

