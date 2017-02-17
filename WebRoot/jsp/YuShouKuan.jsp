<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@page import="mediastore.web.form.*"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="java.util.List"%>
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
	form1.param.value="";
	form1.submit();
}
function savePrepay(prepay,kind)
{
	var factory=document.getElementById("factory").value;
	if(factory==0)
	{
		alert('请选择一个客户或供应商');
		factory.focus();
		return;
	}
	var newpay=document.getElementById("newpay").value;
	if(newpay=='')
	{
		alert('金额不能为空');
		newpay.focus();
		return;
	}
	if(!IsFloat(newpay))
	{
		alert('金额必须是浮点数');
		newpay.focus();
		return;
	}
	var msg;
	if(kind==1)
		msg="预收帐户";
	else
		msg="预付帐户";
	if(confirm('当前'+msg+'有：'+prepay+'元，是否确认存入'+newpay+'?'))
	{
		form1.param.value="yushou";
		form1.submit();
	}
}
function viewDetail(url)
{
	window.open(url,'view','top=0,left=0,location=no,directories=no,hotkeys=0,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=800,height=400');
}
function gotoPage(){
	
	//该值为要条转到的页面的起始记录号
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
	List FactList = (List)request.getAttribute("FactList");
	List truckList = (List)request.getAttribute("truckList");
	List fkList = (List)request.getAttribute("fk");
	int kind = oConvert.getInt((String)request.getAttribute("kind"),0);
	int factory = oConvert.getInt((String)request.getAttribute("factory"),0);
	double minHuoKuan = oConvert.getDouble((String)request.getAttribute("minHuoKuan"),0);
	String zhujima = oConvert.getString((String)request.getAttribute("zhujima"),"");
	ResultsetList ys = (ResultsetList)request.getAttribute("ys");
	List acc = (List)request.getAttribute("acc");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
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
    <td width="379" height="20" class="mainhead">预收预付货款</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    
    
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td align="center" valign="top">   
         
    	<form name="form1" method="post" action="yuShouKuan.do" >
    	<input type="hidden" name="param">
    	<input type="hidden" name="curpage" value="<%=ys.curpage%>">
    	<font color=#215385><b></b></font>
    	<br>
    	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	  	<tr>
            <td  align="right" > 
                <b>操作类型:</b>
            </td>
            <td >
            	<select name="kind" onChange="SubmitThis();">
            		<option value="1" <%=(kind==1?"selected":"")%>>预收货款</option>
            		<option value="-1" <%=(kind==-1?"selected":"")%>>预付货款</option>
            		<option value="0" <%=(kind==0?"selected":"")%>>预付运杂费</option>
            	</select>
            </td>
        	<%if(kind==1 || kind==-1){ %>
            <td  align="right" > 
                <b><%=(kind==1?"客户":"供应商")%>名称:</b>
            </td>
            <td colspan=3>
            	<SELECT name="factory" onChange="SubmitThis();">
            		<option value="0">请选择...</option>
            		<%
            		            	FactoryInfoForm tmpFact=null;
            		            	for(int i=0; i<FactList.size(); i++)
            		            	{
            		            		tmpFact = (FactoryInfoForm)FactList.get(i);
            		%>
            		<option value="<%=tmpFact.getId()%>" <%=(tmpFact.getId()==factory?"selected":"") %>><%=tmpFact.getName()%></option>
			<%
				}
       
			%>
            	</SELECT><input class=none type="text" name="zhujima" value="<%=zhujima %>" size=5 onChange="SubmitThis();">(助记码,如"易通"为"yt")
            
            </td>
            <%}else{ %>
            <td  align="right" > 
                <b>车辆名称:</b>
            </td>
            <td colspan=3>
            	<SELECT name="factory" onChange="SubmitThis();">
            		<option value="0">请选择...</option>
            		<%
            						TruckInfoForm tmpFact=null;
            		            	for(int i=0; i<truckList.size(); i++)
            		            	{
            		            		tmpFact = (TruckInfoForm)truckList.get(i);
            		%>
            		<option value="<%=tmpFact.getId()%>" <%=(tmpFact.getId()==factory?"selected":"") %>><%=tmpFact.getCarno()%>(<%=tmpFact.getDriver()%>)</option>
			<%
				}
       
			%>
            	</SELECT>
            
            </td>
            <%} %>
            </tr>
            <tr>
        	<td  align="right" > 
                <b><%=(kind==1?"预收":"预付")%>方式:</b>
            </td>
            <td >
            	<SELECT name="fkType">
            		<%
            		FuKuanForm fkf=null;       	
            		     for(int i=0; i<fkList.size(); i++)
            		     {
            		         fkf = (FuKuanForm)fkList.get(i);
            		         if(fkf.getId()==5)
            		        	 continue;
            		%>
            		<option value="<%=fkf.getId()%>" ><%=(fkf.getName().length()>12?fkf.getName().substring(0,10):fkf.getName())%></option>
			<%
            		     }
			%>
            	</SELECT>
            </td> 
            <td  align="right" > 
                <b><%=(kind==1?"存入":"支付")%>账户:</b>
            </td>
            <td >
            	
            	<SELECT name="acctype" >
            		<%
            		AccountForm tmp;
            		for(int i=0;i<acc.size();i++)
            		{
            			tmp=(AccountForm)acc.get(i);
            			out.print("<option value="+tmp.getId()+" >"+tmp.getAccName()+" "+tmp.getJine()+"</option>");
            		}
            		%>
            	</SELECT>
            </td>
            <td  align="right" > 
                <b><%=(kind==1?"预收":"预付")%>金额:</b>
            </td>
            <td >
            	<INPUT  name="newpay" class=none type="text" size="20" maxlength="15" value="" onKeyUp="InputFloat(this);">
            	
            	<INPUT   name="submit1" type="button" value="<%=(kind==1?" 收款 ":" 付款 ")%>" onClick="savePrepay(<%=ys.realmoney%>,<%=kind %>);">
            </td>
            
         </tr>
		</TABLE>	
		</form>
	 
    <%
    if(ys!=null && ys.rslist.size()>0)
    {
    %>
    <font color=#215385><%=(kind==1?"预收":"预付")%>款账户操作记录（共 <b><%=ys.allnum%></b> 笔，当前帐户：<b><%=nf.format(ys.realmoney)%></b> 元，待结货款：<b><%=nf.format(ys.allmoney)%></b> 元，实欠：<b><%=nf.format(ys.allmoney-ys.realmoney)%></b>）</font>
	<%
	if(ys.allmoney!=0 && minHuoKuan<ys.realmoney){
	%>
	[<a href="payHuoKuan.do?param=list&kind=<%=kind %>&factory=<%=factory %>&deptid=<%=ctx.getDeptid() %>">进入货款应收应付</a>]
	<%} %>
	<IMG src="images/line1.gif" width=900 border=0>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
	
		<tr bgcolor="#C2CEDC">
		<th></th>
		<th>单号</th>
		<th>录入员</th>
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
		 	<%if(tmpInfo.getAddChuzhi()==0 && kind==1){ %>
		 	<td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath()+ "/exportItemsView.do?billId=" + tmpInfo.getBillid()+"&deptid="+tmpInfo.getDeptid() %>');"><IMG src="images/news1.gif" border=0 alt='查看单据明细'></a></td></td>
		 	<%}else if(tmpInfo.getAddChuzhi()==0 && kind==-1){ %>
		 	<td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath()+ "/importItemsView.do?billId=" + tmpInfo.getBillid()+"&deptid="+tmpInfo.getDeptid() %>');"><IMG src="images/news1.gif" border=0 alt='查看单据明细'></a></td></td>
		 	<%}else{ %>
			<td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath()+ "/shouKuanItemView.do?billId=" + tmpInfo.getBillid()+"&deptid="+tmpInfo.getDeptid() %>');"><IMG src="images/news1.gif" border=0 alt='查看单据明细'></a></td></td>
			<%} %>
		 	
	        <td align='center' width=5%><%=tmpInfo.getBillid() %></td>
	        <td align='center'><%= tmpInfo.getOperPerson() %></td>
	        <td align='right'><%= nf.format(tmpInfo.getCurChuzhi())%></td>
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
	<%}%>
	
	
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

