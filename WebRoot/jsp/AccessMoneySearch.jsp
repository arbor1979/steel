<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.text.NumberFormat"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js"></script>
<script language="JavaScript">

function orderList(order,old)
{
	if(order==old)
		order=order+' desc';
	form1.orderby.value=order;
	form1.submit();
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

function viewDetail(url)
{
	window.open(url,'view','top=0,left=0,location=no,directories=no,hotkeys=0,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=800,height=400');
}

function verify()
{
	form1.classid.value=cat.form1.classid.value;
	form1.typeid.value=cat.form1.typeid.value;
	return true;
}
</script>
</head>
<%
	AccountForm af = (AccountForm)request.getAttribute("af");
	List PersonList = (List)request.getAttribute("PersonList");
	List departList = (List)request.getAttribute("departList");
	List acc = (List)request.getAttribute("acc");
 	String importTime1=(String)request.getAttribute("importTime1");
 	String importTime2=(String)request.getAttribute("importTime2");
 	String orderby=(String)request.getAttribute("orderby");
 	String oper=(String)request.getAttribute("oper");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
	ResultsetList tp = (ResultsetList)request.getAttribute("tp");
	AccountForm tmp=null;
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
    <td width="379" height="20" class="mainhead">资金账户查询</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td height="100" align="center" valign="top">   
          
     <form name="form1" method="post" action="accessMoneySearch.do" onsubmit="return verify();">
    	<font color=#215385><b>查询条件</b></font>
    	<IMG src="images/line1.gif" border=0 width=900>
    	<br>
    	<input type="hidden" name="curpage"  value="1">
    	<input type="hidden" name="orderby"  value="<%=orderby %>">
    	<input type="hidden" name="searchbutton" value="查询">
    	<input type="hidden" name="classid" value="<%=af.getClassid() %>">
    	<input type="hidden" name="typeid" value="<%=af.getTypeid() %>">    	
    	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	  <tr>
            
            <td  align="right"><b>查询帐户:</b></td>
            <td >
            	
            	<SELECT name="acctype">
            		<option value="0">请选择...</option>
            		<%
            		double allmoney=0;
            		for(int i=0;i<acc.size();i++)
            		{
            			tmp=(AccountForm)acc.get(i);
            			if(af.getAcctype()==0)
	            			allmoney=allmoney+tmp.getJine();
            			else
            			{
            				if(af.getAcctype()==tmp.getId())
                				allmoney=tmp.getJine();	
            			}
            			out.print("<option value="+tmp.getId()+" "+(af.getAcctype()==tmp.getId()?"selected":"")+">"+tmp.getAccName()+" "+tmp.getJine()+"</option>");
            		}
            		%>
            	</SELECT>
            </td>
            <td  align="right"> 
                <b>录入员:</b>
            </td>
            <td>
            	<SELECT name="operPerson">
            		<option value="">-全部-</option>
            		<%
            						String tmpStr;
            		            	for(int i=0; i<PersonList.size(); i++)
            		            	{
            		            		tmpStr = (String)PersonList.get(i);
            		%>
            		<option value="<%=tmpStr%>" <%=(tmpStr.equals(af.getOperPerson())?"selected":"") %>><%=tmpStr%></option>
			<%
			}
			%>
            	</SELECT>
            </td>
            <td  align="right" width=80> 
                <b>科目类型:</b>
            </td>
             <td colspan=4>
            	<iframe name="cat" id="cat" src="accessType.do?classid=<%=af.getClassid() %>&typeid=<%=af.getTypeid() %>" width="300" height="25" frameborder=0 scrolling=no ></iframe>
            </td>
            
          	
          </tr>
          <tr>
          
		<TD align="right" width=10%><b>录入时间: </b></TD>
		<TD colspan=3>起始 
		<input type=text name='importtime1' size="18" class="none" value="<%=importTime1 %>">
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="弹出日历菜单"  onClick="Calendar_importtime1();">&nbsp;
		&nbsp;终止 
		<input type=text name='importtime2'  size="18" class="none" value="<%=importTime2 %>"> 
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="弹出日历菜单"  onClick="Calendar_importtime2();"></td>
		 <td  align="right" width=80> 
                <b>收支类型:</b>
            </td>
            <td >
				<select name="oper">
				<option value="">-全部-</option>
				<option value="1" <%=(oper.equals("1")?"selected":"") %>>收入</option>
				<option value="0" <%=(oper.equals("0")?"selected":"") %>>开支</option>
				</select>
            </td>
            <%
		if(ctx.isIfview()) {
		%>
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
    			<option value="<%=dif.getId() %>" <%=(af.getDeptid()==dif.getId()?"selected":"") %>><%=dif.getShortname()%></option>
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
            <td ><input name=searchbutton type=submit value=" 查询 "></TD>
        </tr>
         <tr>
		
		
	</TABLE>
   
	</form>
    	
	<font color=#215385>操作记录 (共 <b><%=tp.allnum %></b> 笔，期初余额：<b><%=nf.format(tp.allsumnum) %></b>，合计收入：<b><%=nf.format(tp.allmoney) %></b> 元
	，合计支出：<b><%=nf.format(-tp.realmoney) %></b> 元,帐户余额：<b><%=nf.format(allmoney) %></b> 元)
	<IMG src="images/line1.gif" border=0 width=900>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
		<tr bgcolor="#C2CEDC">
		<th></th>
		<th><a class="tablehead" href="javascript:orderList('a.billid','<%=orderby%>');">原始单号<%
		if(orderby.equals("a.billid")) out.print("↓"); if(orderby.equals("a.billid desc")) out.print("↑");
		%></th>
		<%if(ctx.isIfview()){ %>
		<th><a class="tablehead" href="javascript:orderList('a.deptid','<%=orderby%>');">分公司<%
		if(orderby.equals("a.deptid")) out.print("↓"); if(orderby.equals("a.deptid desc")) out.print("↑");
		%></th><%} %>
		<th><a class="tablehead" href="javascript:orderList('a.acctype','<%=orderby%>');">帐户<%
		if(orderby.equals("a.acctype")) out.print("↓"); if(orderby.equals("a.acctype desc")) out.print("↑");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('a.typename','<%=orderby%>');">操作类型<%
		if(orderby.equals("a.typename")) out.print("↓"); if(orderby.equals("a.typename desc")) out.print("↑");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('a.jine','<%=orderby%>');">收入<%
		if(orderby.equals("a.jine")) out.print("↓"); if(orderby.equals("a.jine desc")) out.print("↑");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('a.jine','<%=orderby%>');">支出<%
		if(orderby.equals("a.jine")) out.print("↓"); if(orderby.equals("a.jine desc")) out.print("↑");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('a.curjine+a.jine','<%=orderby%>');">余额<%
		if(orderby.equals("a.curjine+a.jine")) out.print("↓"); if(orderby.equals("a.curjine+a.jine desc")) out.print("↑");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('a.operPerson','<%=orderby%>');">操作员<%
		if(orderby.equals("a.operPerson")) out.print("↓"); if(orderby.equals("a.operPerson desc")) out.print("↑");
		%></th>
		<th><a class="tablehead" href="javascript:orderList('a.opertime','<%=orderby%>');">操作时间<%
		if(orderby.equals("a.opertime")) out.print("↓"); if(orderby.equals("a.opertime desc")) out.print("↑");
		%></th>
		</tr>
		
		<%
			AccountForm otpf = null;
		    for (int index=0; index<tp.rslist.size(); index++)
			{
		    	otpf = (AccountForm)tp.rslist.get(index);
		%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        <%if(otpf.getTypename().equals("货款支付") || otpf.getTypename().equals("入库运杂费")){ %>
	   	        <td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath()+ "/importItemsView.do?billId=" + otpf.getBillid()+"&deptid="+otpf.getDeptid() %>');"><IMG src="images/news1.gif" border=0 alt='查看原单明细'></a></td>
	   	    <%}else if(otpf.getTypename().equals("货款收取")){ %>
				<td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath()+ "/exportItemsView.do?billId=" + otpf.getBillid()+"&deptid="+otpf.getDeptid() %>');"><IMG src="images/news1.gif" border=0 alt='查看原单明细'></a></td>	   	    
			<%}else if(otpf.getTypename().equals("预收货款") || otpf.getTypename().equals("预付货款") || otpf.getTypename().equals("预付运费")){ %>
				<td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath()+ "/shouKuanItemView.do?billId=" + otpf.getBillid()+"&deptid="+otpf.getDeptid() %>');"><IMG src="images/news1.gif" border=0 alt='查看原单明细'></a></td>	   	    				
			<%}else if(otpf.getTypename().equals("费用开支") || otpf.getTypename().equals("其他收入") || otpf.getTypename().equals("入库运费") || otpf.getTypename().equals("出库运费")){ %>
				<td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath()+ "/feiYongItemView.do?billId=" + otpf.getBillid()+"&deptid="+otpf.getDeptid()+"&kind="+(otpf.isInout()?"1":"-1") %>');"><IMG src="images/news1.gif" border=0 alt='查看原单明细'></a></td>	   	    				
			<%}else if(otpf.getTypename().equals("资金借出") || otpf.getTypename().equals("资金借入") || otpf.getTypename().equals("资金还入") || otpf.getTypename().equals("资金还出")){ %>
				<td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath()+ "/otherPayItemView.do?billId=" + otpf.getBillid()+"&deptid="+otpf.getDeptid() %>');"><IMG src="images/news1.gif" border=0 alt='查看原单明细'></a></td>	   	    				
			<%}else if(otpf.getTypename().equals("资金转出") || otpf.getTypename().equals("资金转入")){ %>
				<td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath()+ "/accessMoneyItemView.do?billId=" + otpf.getBillid()+"&deptid="+otpf.getDeptid()%>');"><IMG src="images/news1.gif" border=0 alt='查看原单明细'></a></td>	   	    				
			<%}else if(otpf.getTypename().equals("资本注入") || otpf.getTypename().equals("资本抽取")){ %>
				<td align='center' width=3%><a href="javascript:viewDetail('<%= request.getContextPath()+ "/inputOutputMoneyItemView.do?billId=" + otpf.getBillid()+"&deptid="+otpf.getDeptid()%>');"><IMG src="images/news1.gif" border=0 alt='查看原单明细'></a></td>	   	    				
			<%}else{%>
				<td></td>
			<%} %>
	        <td align=center><%= otpf.getBillid() %></td>
	        <%if(ctx.isIfview()){ %>
	        <td align=center><%= otpf.getShortname()%></td><%} %>
	        <td align=center><%= otpf.getAccName() %></td>
	        <td align=center><%= otpf.getTypename() %></td>
	        <%if(otpf.isInout()){ %>
		        <td align=right><%= nf.format(otpf.getJine()) %></td>
		        <td></td>
		    <%}else{ %>
		    	<td></td>
		        <td align=right><%= nf.format(-otpf.getJine()) %></td>
		    <%} %>
		    <td align=right><%= nf.format(otpf.getCurjine()+otpf.getJine()) %></td>
	        <td align=center><%= otpf.getOperPerson() %></td>
	        <td align=center><%= otpf.getOpertime()%></td>
	       </td>
	        </tr>
	        <%
	     }
	        %>
	</TABLE>
	<div align="right">
		<%
			if(tp.curpage>1)
			{
		%>
		<a href="javascript:gotoPageByN(1)"><IMG src="images/startpage.gif" border=0 alt='首页'></a>&nbsp;
		<a href="javascript:gotoPageByN(<%=tp.curpage-1%>)"><IMG src="images/lastpage.gif" border=0 alt='上一页'></a>&nbsp;&nbsp;

		<%
			}
			else
			{
		%>
		<IMG src="images/startpage.gif" border=0 alt='首页'>&nbsp;
		<IMG src="images/lastpage.gif" border=0 alt='上一页'>&nbsp;&nbsp;
		<%	
			}

	    	if(tp.curpage<tp.pagecount)
	    	{
	    %>
	    	<a href="javascript:gotoPageByN(<%=tp.curpage+1%>)"><IMG src="images/nextpage.gif" border=0 alt='下一页'></a>&nbsp;
	        <a href="javascript:gotoPageByN(<%=tp.pagecount%>)"><IMG src="images/endpage.gif" border=0 alt='末页'></a>&nbsp;&nbsp;&nbsp;&nbsp;
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
		<%for(int i=1; i<=tp.pagecount; i++)
		  {%>
			<option value="<%=i%>" <%if(i==tp.curpage){%>selected<%}%>><%=i%></option>
		<%}%>
		</select> 页
	</div>     
          
          
          
	
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

