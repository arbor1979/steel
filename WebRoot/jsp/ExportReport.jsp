<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.rule.*"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="mediastore.web.form.UserInfoForm" %>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<script language="JavaScript">

function Calendar_exporttime1()
{
var newWindow;
var urlstring = './html/Calendar_exporttime1.htm'
newWindow = window.open(urlstring,'','height=200,width=280,toolbar=no,minimize=no,status=no,memubar=no,location=no,scrollbars=no')

}
function Calendar_exporttime2()
{
var newWindow;
var urlstring = './html/Calendar_exporttime2.htm'
newWindow = window.open(urlstring,'','height=200,width=280,toolbar=no,minimize=no,status=no,memubar=no,location=no,scrollbars=no')

}
function Calendar_paymenttime1()
{
var newWindow;
var urlstring = './html/Calendar_paymenttime1.htm'
newWindow = window.open(urlstring,'','height=200,width=280,toolbar=no,minimize=no,status=no,memubar=no,location=no,scrollbars=no')

}
function Calendar_paymenttime2()
{
var newWindow;
var urlstring = './html/Calendar_paymenttime2.htm'
newWindow = window.open(urlstring,'','height=200,width=280,toolbar=no,minimize=no,status=no,memubar=no,location=no,scrollbars=no')

}

function isLeapYear(year) 
{ 
	if((Number(year)%4==0&&Number(year)%100!=0)||(Number(year)%400==0)) 
	{ 
		return true; 
	}  
	return false; 
}

function Trim(str){
 if(str.charAt(0) == " "){
  str = str.slice(1);
  str = Trim(str); 
 }
 return str;
}

//校验，看输入对象的值是否为有效日期yyyy-mm-dd hh:mm:ss
function isDateChen(checktext){

	var datetime;
	var year,month,day,hour,mini,senc;
	var gone,gtwo,gthree,gfour,gfive;
	if(Trim(checktext.value)!=""){
		datetime = Trim(checktext.value);
		if(datetime.length==19){
			year=datetime.substring(0,4);
			if(isNaN(year)==true){
				alert("时间格式不正确\n形如：yyyy-mm-dd hh:mm:ss\n例：2001-02-23 02:56:12");
				checktext.focus();
				return false;
			}
			gone=datetime.substring(4,5);
			month=datetime.substring(5,7);
			if(isNaN(month)==true){
				alert("时间格式不正确\n形如：yyyy-mm-dd hh:mm:ss\n例：2001-02-23 02:56:12");
				checktext.focus();
				return false;
			}
			gtwo=datetime.substring(7,8);
			day=datetime.substring(8,10);
			if(isNaN(day)==true){
				alert("时间格式不正确\n形如：yyyy-mm-dd hh:mm:ss\n例：2001-02-23 02:56:12");
				checktext.focus();
				return false;
			}
			gthree=datetime.substring(10,11);
			firstm = datetime.indexOf(":");//第一个冒号的位置
			//alert("firstm:"+firstm);
			hour=datetime.substring(11,firstm);
			if(isNaN(hour)==true){
				alert("时间格式不正确\n形如：yyyy-mm-dd hh:mm:ss\n例：2001-02-23 02:56:12");
				checktext.focus();
				return false;
			}
			gfour=datetime.substring(firstm,firstm+1);
			
			//alert(datetime.length);
			tempstr = datetime.substring(firstm+1,datetime.length);
			secondm = tempstr.indexOf(":");//第二个冒号的位置
			mini=tempstr.substring(0,secondm);
			if(isNaN(mini)==true){
				alert("时间格式不正确\n形如：yyyy-mm-dd hh:mm:ss\n例：2001-02-23 02:56:12");
				checktext.focus();
				return false;
			}
			gfive=tempstr.substring(secondm,secondm+1);
			senc=tempstr.substring(secondm+1,tempstr.length);
			if(isNaN(senc)==true){
				alert("时间格式不正确\n形如：yyyy-mm-dd hh:mm:ss\n例：2001-02-23 02:56:12");
				checktext.focus();
				return false;
			}
			
			//alert(year+gone+month+gtwo+day+gthree+hour+gfour+mini+gfive+senc)
			
			if((gone=="-")&&(gtwo=="-")){
				if( Number(month)<1 || Number(month)>12 ) { 
					alert("月份必须在01和12之间!"); 
					checktext.focus();
					return false; 
				} 
				if(Number(day)<1 || Number(day)>31){ 
					alert("日期必须在01和31之间!");
					checktext.focus(); 
					return false; 
				}else{
					if(Number(month)==2){  
					
						if(isLeapYear(year)&& Number(day)>29){ 
							alert("2月份日期必须在01到29之间"); 
							checktext.focus();
							return false; 
						}       
						if(!isLeapYear(year)&& Number(day)>28){ 
							alert("2月份日期必须在01到28之间");
							checktext.focus(); 
							return false; 
						} 
					} 
					if((Number(month)==4||Number(month)==6||Number(month)==9||Number(month)==11)&&(Number(day)>30)){ 
						alert("在4、6、9和11月份\n日期必须在01到30之间");
						checktext.focus(); 
						return false; 
					} 
				}
			}else{
				alert("时间格式不正确\n形如：yyyy-mm-dd hh:mm:ss\n例：2001-02-23 02:56:12");
				checktext.focus();
				return false;
			}
		}else{
			alert("时间格式不正确\n形如：yyyy-mm-dd hh:mm:ss\n例：2001-02-23 02:56:12");
			checktext.focus();
			return false;
		}
	}else{
		return true;
	}
	return true;
}

function mySubmit1(){

	if(!isDateChen(document.form1.exporttime1))
	{//检查输入的时间是否合法
	}
	else if(!isDateChen(document.form1.exporttime2))
	{//检查输入的时间是否合法
	}
	else if(!isDateChen(document.form1.paymenttime1))
	{//检查输入的时间是否合法
	}
	else if(!isDateChen(document.form1.paymenttime2))
	{//检查输入的时间是否合法
	}
	else
	{
		
		if(form1.bbtj.value==6 && form1.exporttime2.value!='')
		{
			var dt=form1.exporttime2.value;
			var y=Number(dt.substring(0,4));
			var m=Number(dt.substring(5,7))-1;
			var d=Number(dt.substring(8,10));
			var h=Number(dt.substring(11,13));
			var mm=Number(dt.substring(14,16));
			var s=Number(dt.substring(17,19));
			var dt2=new Date(y,m,d,h,mm,s);
			var dt1=new Date();
			if(dt2<dt1)
			{
				alert('截至日期必须大于当前时间或置空');
				form1.exporttime2.value='';
				return false;
			}
		}
		document.form1.action="exportReport.do";
		document.form1.submit();
	}
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
<table width="768" border="0" align="center" cellpadding="0" cellspacing="0">
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
    <td width="379" height="20" class="mainhead" align="centre">饰品销售报表</td>
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
	//------
	ExportReportConFB ebscfb = (ExportReportConFB)request.getAttribute(Globals.REQUEST_EXPORTREPORT);
	
	ExportReportResultInfo ebsri = ebscfb.getErsri();	//检索结果可能为空
	ExportReportRule ebsr = ebscfb.getErsr();		//检索式可能为空
	//-------
	
	//组织与页、记录相关的信息
	List exportReportList = new ArrayList();
	int totalBillNum = 0;
	float totalPrice=0;//现金
	float totalPrice1=0;//欠款
	float totalPrice2=0;//发货

	if(ebsri != null)
	{
		totalBillNum = ebsri.getTotalRecNum();		//总记录数
		totalPrice=ebsri.getTotalXjPrice();
		totalPrice1=ebsri.getTotalQkPrice();
		totalPrice2=ebsri.getTotalFhPrice();
		exportReportList = ebsri.getExportReportList();
	}
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
	
	
	//获得上次检索式的值，由于检索式可能为空，所以设置一个缺省值
	
	String lastExportTime1 = null;
	String lastExportTime2 = null;
	String lastPaymentTime1 = null;
	String lastPaymentTime2 = null;
	String bbtj = "0";
	if(ebsr!=null)
	{
		
		lastExportTime1 = ebsr.getExportTime1();
		lastExportTime2 = ebsr.getExportTime2();
		lastPaymentTime1 = ebsr.getPaymentTime1();
		lastPaymentTime2 = ebsr.getPaymentTime2();
		bbtj=ebsr.getBbtj();
	}
	
%>
	<form name="form1" method="post" action="" >
    	<font color=#215385><b>报表生成条件</b></font>
    	<IMG src="images/line1.gif" border=0>
    	<br>
    	<input type="hidden" name="start_rec_num"  value="1">
    	
    	<TABLE  class="mailtable"> 
	  <tr>
		<TD align="right" width=13%><b>售货时间:</b></TD>
		<TD width=65%>&nbsp;&nbsp;
		起始 
		<input type=text name='exporttime1' size="20" class="none" value='<%=oConvert.getString(lastExportTime1,"")%>'>
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="弹出日历菜单"  onClick="Calendar_exporttime1()">&nbsp; 
		终止 
		<input type=text name='exporttime2'  size="20" class="none" value='<%=oConvert.getString(lastExportTime2,"")%>'> 
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="弹出日历菜单"  onClick="Calendar_exporttime2()">
		</TD>
		<TD>&nbsp;</TD>
          </tr>
		   <tr>
            <td  align="right"> 
                <b>报表条件:</b>
            </td>
            <td>
            	&nbsp;&nbsp;<SELECT size=1 name="bbtj">
            		<option value="0" <%if(bbtj.equals("0")) out.print("selected"); %>>分售货人员销售统计</option>
            		<option value="1" <%if(bbtj.equals("1")) out.print("selected"); %>>分商品种类销售统计</option>
            		<option value="2" <%if(bbtj.equals("2")) out.print("selected"); %>>欠款统计</option>
			        <option value="3" <%if(bbtj.equals("3")) out.print("selected"); %>>发货统计</option>
					<option value="4" <%if(bbtj.equals("4")) out.print("selected"); %>>分组别折扣统计</option>
					<option value="5" <%if(bbtj.equals("5")) out.print("selected"); %>>分商品利润统计</option>
					<option value="6" <%if(bbtj.equals("6")) out.print("selected"); %>>采购员收支统计</option>
					<option value="7" <%if(bbtj.equals("7")) out.print("selected"); %>>分厂家进货退货统计</option>
            	</SELECT>
            </td>
			<TD>&nbsp;</TD>
	  </tr>
	  <tr>
		<TD align="right" width=13% ><b></b></TD>
		<TD width=65%>&nbsp;&nbsp;
		
		<input  name='paymenttime1'  size="20" type="hidden" > 
		
		<input  name='paymenttime2'  size="20" type="hidden"> 
		
		</TD>
		<TD>&nbsp;&nbsp;&nbsp;
            		<input name=searchbutton type=button value="查询" onClick='mySubmit1()' >
		</TD>
          </tr>
	 
	</TABLE>
	</form>

	<IMG src="images/line1.gif" border=0>
	<form name="form2" method="post" action="" >
	<div id="p_content">
	<table id="tab1"   class="mailtable">
	  <div id="p_title">
       <tr> 
       <td colspan="10" height="20" class="mainhead" align="center"><font size=4><b>饰品销售报表</b></font></td>
       </tr>
     </div>
	  <tr>
	     <td colspan="10">
		<% SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");%>
		 制表时间:&nbsp;&nbsp;<%=sf.format(new Date())%>
		 &nbsp;&nbsp;报表生成区间:
		<%=lastExportTime1%>-------<%=lastExportTime2%>&nbsp;&nbsp;报表生成条件：&nbsp;
		<%if(bbtj.equals("0")){%>收银人员<%}else if(bbtj.equals("1")){%>商品种类<%}else if(bbtj.equals("2")){%>欠款<%}else if(bbtj.equals("3")){%>发货<%}else if(bbtj.equals("4")){%>组别折扣<%}%>
		
		</td>
	  </tr>
	
	<!--提交的时候设置-->
	<input type="hidden" name="start_rec_num"  value="1">
	<input type="hidden" name="role"  value="0">
	<input type="hidden" name="exporttime1"  value="<%=lastExportTime1%>">
	<input type="hidden" name="exporttime2"  value="<%=lastExportTime2%>">
	<input type="hidden" name="paymenttime1"  value="<%=lastPaymentTime1%>">
	<input type="hidden" name="paymenttime2"  value="<%=lastPaymentTime2%>">
    <%
	if(bbtj.equals("0"))
	{
		float totalys=0;
		float totalql=0;
		float totalqk=0;
		float totalfh=0;
		float totalhk=0;
		float totaldj=0;
		int   totalqn=0;
		int   totalfn=0;
		int   totalkn=0;
		
	%>
	  <tr>
		 <td colspan="10">
		<font color=#215385><b>合计：</b><font color="red"><%=totalBillNum%></font> 张售货单&nbsp;&nbsp;
	<b>现金：</b><font color="red"><%=nf.format(totalPrice+totalPrice1+totalPrice2)%></font> 元 &nbsp;其中：
	(<b>欠款已交：</b><font color="red"><%=totalPrice1%></font> 元&nbsp;&nbsp;
	<b>发货已交：</b><font color="red"><%=totalPrice2%></font> 元)
	</font>
		 </td>
		 
	  </tr>
	  <tr>
	   <td align='center' >售货员</td>
	   <td align='center'>单数</td>
	   <td align='center'>应收总价</td>
	   <td align='center'>-去零</td>
	   <td align='center' >-欠款</td>
	   <td align='center' >-发货</td>
	   <td align='center' >-代金券</td>
	   <td align='center'>实收现金</td>
	   <td align='center' >+还款</td>
	   <td align='center' >合计</td>
	  </tr>
	 
		<%
			if(exportReportList.size()>0)
			{
		%>
		<tr bgcolor="#C2CEDC">
		
		<%
			}	
	    int index = 0;
      	GoodsExportReportInfo tmpInfo = null;		
      	
	    for (index=0; index<exportReportList.size(); index++)
		{
	      		tmpInfo = (GoodsExportReportInfo)exportReportList.get(index);
	      	%>
	     <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
		
		<td align='center'>
	       
	        	<a href="<%= request.getContextPath()%>/exportBillSearch.do?exporttime1=<%=lastExportTime1%>& exporttime2=<%=lastExportTime2%>&salespersons=<%=tmpInfo.getSalesPersonsNa()%>&role=0&start_rec_num=1&billid=0&orderstr='BillID DESC'"><%= tmpInfo.getSalesPersonsNa()%></a>
	        
	    </td>
	    <td align='right'>
	       
	        	<%= tmpInfo.getTotalRecNum() %>
	        
	    </td>
		<td align='right'><%= nf.format(tmpInfo.getSalesPersonsYs()) %>
		</td><% totalys=totalys+tmpInfo.getSalesPersonsYs();%>
		<td align='right'>
	       
	        <%= nf.format(tmpInfo.getSalesPersonsQl()) %>
	        <% totalql=totalql+tmpInfo.getSalesPersonsQl();%>
	      </td>
	    <td align='center'>
	       
	        <%= nf.format(tmpInfo.getSalesPersonsQk())%>(共<%=tmpInfo.getSalesPersonsQkNum()%>笔)
	       <% 
				totalqk=totalqk+tmpInfo.getSalesPersonsQk();
                totalqn=totalqn+tmpInfo.getSalesPersonsQkNum();
			%>
	      </td>
	      
		   <td align='center'>
	       
	        	<%= nf.format(tmpInfo.getSalesPersonsFh())%>(共<%=tmpInfo.getSalesPersonsFhNum()%>笔)
	        <% 
				totalfh=totalfh+tmpInfo.getSalesPersonsFh();
                totalfn=totalfn+tmpInfo.getSalesPersonsFhNum();
			%>
	        </td>
	        <td align='right'>
	       
	        <%= nf.format(tmpInfo.getDaijin()) %>
	        <%totaldj=totaldj+tmpInfo.getDaijin(); %>
	      </td>
			<td align='right'>
	       
	        <%= nf.format(tmpInfo.getSalesPersonsSs()) %>
	        
	      </td>
	      <td align='center'>
	       
	        	<%= nf.format(tmpInfo.getSalesPersonsHK())%>(共<%=tmpInfo.getSalesPersonsHKNum()%>笔)
	        <% 
				totalhk=totalhk+tmpInfo.getSalesPersonsHK();
	        	totalkn=totalkn+tmpInfo.getSalesPersonsHKNum();
			%>
	        </td> 	
			<td align='right'>
	       
	        <%= nf.format(tmpInfo.getSalesPersonsSs()+tmpInfo.getSalesPersonsHK())%>
	        
	      </td>
	        </tr>
	        <%
	
	      	}//for
	        %>
			<tr>
	   <td align='center' >合计</td>
	   <td align='right'><%=totalBillNum%></td>
	   <td align='right'><%=nf.format(totalys)%></td>
	   <td align='right'><%=nf.format(totalql)%></td>
	   <td align='center' ><%=totalqk%>|共<%=totalqn%>笔</td>
	   <td align='center' ><%=totalfh%>|共<%=totalfn%>笔</td>
	   <td align='right'><%=nf.format(totaldj)%></td>
	   <td align='right'><%=nf.format(totalPrice+totalPrice1+totalPrice2)%></td>
	   <td align='center' ><%=nf.format(totalhk)%>|共<%=totalkn%>笔</td>
	   <td align='right' ><%=nf.format(totalPrice+totalPrice1+totalPrice2+totalhk)%></td>
	  </tr>
	  <%
	  }
	else if(bbtj.equals("1"))
	{
		int sellnum=0;
		int backnum=0;
		float sellprice=0;
		float backprice=0;
	%>
	  <tr>
		<td colspan="8" align="center">
		<font color=#215385><b>合计：</b><font color="red"><%=exportReportList.size()%></font>条记录
		 </td>
		 
	  </tr>
      <tr>
	   <td align='center' >货物名称</td>
	   <td align='center'>销售数</td>
	   <td align='center'>退货数</td>
  	   <td align='center'>冲抵后数量</td>
  	   <td align='center'>销售金额</td>
	   <td align='center'>退货金额</td>
		<td align='center'>冲抵后金额</td>
	   <td align='center'>与总销售额的比值</td>
	   
	  </tr>
     <%
	  if(exportReportList.size()>0)
		{
		%>
		<tr bgcolor="#C2CEDC">
		
		<%
		}
		
	    int index = 0;
      	GoodsExportReportInfo tmpInfo = null;
		
	    for (index=0; index<exportReportList.size(); index++)
		{
	      		tmpInfo = (GoodsExportReportInfo)exportReportList.get(index);
	      	%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
		
		<td align='center'>
	       
	        	<a href="<%= request.getContextPath()%>/exportItemSearch.do?createtime1=<%=lastExportTime1%>& createtime2=<%=lastExportTime2%>&goodsname=<%=tmpInfo.getgoodname()%>&start_rec_num=1
				&billid=0&region=0&zhekou='0'&orderstr=GoodsID DESC"><%= tmpInfo.getgoodname()%></a>
	        
	    </td>
	    <td align='right'>
	       
	        	<%=tmpInfo.getsellnum()%>
	        	<%sellnum=sellnum+tmpInfo.getsellnum(); %>
	        
	    </td>
	    <td align='right'><%= tmpInfo.getbacknum() %>
	   		 	<%backnum=backnum+tmpInfo.getbacknum(); %>
		
		</td>
		<td align='right'>
				<%= tmpInfo.getsellnum()+tmpInfo.getbacknum() %>
		
		</td>
	    <td align='right'>
	       
	       <%=nf.format(tmpInfo.getsellprice())%>
	       <%sellprice=sellprice+tmpInfo.getsellprice(); %>
	        
	    </td>
		
		<td align='right'>
	       
	       <%=nf.format(tmpInfo.getbackprice())%>
	       <%backprice=backprice+tmpInfo.getbackprice(); %>
	        
	    </td>
	    <td align='right'>
	       
	       <%=nf.format(tmpInfo.getsellprice()+tmpInfo.getbackprice())%>
	        
	    </td>
		<td align='center'>
		<%=nf.format((tmpInfo.getsellprice()+tmpInfo.getbackprice())*100/totalPrice)%>%
		</td>
	    
			 
			
	    </tr>
	  <%
	
	   }//for
	   %>
	<tr>
	   <td align='center' >合计</td>
	   <td align='right'><%=sellnum%></td>
	   <td align='right'><%=backnum%></td>
	   <td align='right'><%=sellnum+backnum%></td>
	   <td align='right'><%=nf.format(sellprice)%></td>
	   <td align='right'><%=nf.format(backprice)%></td>
	   <td align='right' ><%=nf.format(sellprice+backprice)%></td>
	   <td align='center' ><%=nf.format(-backprice*100/sellprice)%>%退货率</td>
	  </tr>
    <%}
	else if(bbtj.equals("2"))
    {
		float yingshou=0;
		float yishou=0;
		float qiankuan=0;
	%>
	  <tr>
		<td colspan="8" align="center">
		<font color=#215385><b>合计：</b><font color="red"><%=exportReportList.size()%></font>条记录
		 </td>
		 
	  </tr>
      <tr>
	   <td align='center' >单号</td>
	   <td align='center'>收银人</td>
  	   <td align='center'>会员名称</td>
	   <td align='center'>应收货款</td>
	   <td align='center'>货款押金</td>
	   <td align='center'>尚欠货款</td>
   	   <td align='center'>占欠款总额%</td>
	   <td align='center'>欠款时间</td>

	  </tr>
     <%
	  if(exportReportList.size()>0)
		{
		%>
		<tr bgcolor="#C2CEDC">
		
		<%
			}
		
	    int index = 0;
      	GoodsExportReportInfo tmpInfo = null;
		
	    for (index=0; index<exportReportList.size(); index++)
		{
	      		tmpInfo = (GoodsExportReportInfo)exportReportList.get(index);
	      	%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
		
		<td align='center'>
	       
	        	<a href="<%= request.getContextPath()%>/exportItemSearch.do?createtime1=<%=lastExportTime1%>& createtime2=<%=lastExportTime2%>&start_rec_num=1
				&billid=<%=tmpInfo.getTotalRecNum() %>&region=0&zhekou='0'&orderstr=GoodsID DESC"><%= tmpInfo.getTotalRecNum()%></a>
	        
	    </td>
	    <td align='center'><%=tmpInfo.getSalesPersonsNa()%>   
	    </td>
   	    <td align='center'><a href="<%= request.getContextPath()%>/memberManage.do?membername=<%=tmpInfo.getMemberNa() %>"><%=tmpInfo.getMemberNa()%></a></td>
		<td align='right'><%= nf.format(tmpInfo.getSalesPersonsYs()) %>
		<%yingshou=yingshou+tmpInfo.getSalesPersonsYs(); %>
		</td>
		<td align='right'><%= nf.format(tmpInfo.getSalesPersonsQkSs()) %>
		<%yishou=yishou+tmpInfo.getSalesPersonsQkSs(); %>
		</td>
		<td align='right'>
	       <%=nf.format(tmpInfo.getSalesPersonsQk())%>   
	       <%qiankuan=qiankuan+tmpInfo.getSalesPersonsQk(); %>     
	    </td>
		<td align='center'><%=nf.format(tmpInfo.getSalesPersonsQk()*100/totalPrice1)%>%</td>

		<td align='center'>
		<%
			String tmptime = tmpInfo.getExportTime();
			if(tmptime==null)tmptime="";
			if(tmptime.length()>=19)
			{
		%>
				<%= tmptime.substring(0,19) %>
		<%
			}
		%>
		</td> 
			
	    </tr>
	  <%
	
	   }//for
	   %>
		<tr>
	   <td align='center' >合计</td>
	   <td align='right'></td>
	   <td align='right'></td>
	   <td align='right'><%=nf.format(yingshou)%></td>
	   <td align='right'><%=nf.format(yishou)%></td>
	   <td align='right' ><%=nf.format(qiankuan)%></td>
	   <td align='center' ></td>
	   <td align='center' ></td>
	  </tr>
    <%}
	else if(bbtj.equals("3"))
	{
		float yingshou=0;
		float yishou=0;
		float qiankuan=0;
		%>

    <tr>
		<td colspan="8" align="center">
		<font color=#215385><b>合计：</b><font color="red"><%=exportReportList.size()%></font>条记录
		 </td>
		 
	  </tr>   

      <tr>
	   <td align='center' >单号</td>
	   <td align='center'>收银人</td>
   	   <td align='center'>会员名称</td>
	   <td align='center'>应收货款</td>
	   <td align='center'>货款押金</td>
	   <td align='center'>尚欠发货款</td>
		<td align='center'>占欠款总额%</td>
	   <td align='center'>发货时间</td>
	   <td align='center'>货运部</td>
	  </tr>
     <%
	  if(exportReportList.size()>0)
		{
		%>
		<tr bgcolor="#C2CEDC">
		
		<%
			}
		
	    int index = 0;
      	GoodsExportReportInfo tmpInfo = null;
		
	    for (index=0; index<exportReportList.size(); index++)
		{
	      		tmpInfo = (GoodsExportReportInfo)exportReportList.get(index);
	      	%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
		
		<td align='center'>
	       
	        	<a href="<%= request.getContextPath()%>/exportItemSearch.do?createtime1=<%=lastExportTime1%>& createtime2=<%=lastExportTime2%>&start_rec_num=1
				&billid=<%=tmpInfo.getTotalRecNum() %>&region=0&zhekou='0'&orderstr=GoodsID DESC"><%= tmpInfo.getTotalRecNum()%></a>
	        
	    </td>
	    <td align='center'><%=tmpInfo.getSalesPersonsNa()%>
	    </td>
	    <td align='center'><a href="<%= request.getContextPath()%>/memberManage.do?membername=<%=tmpInfo.getMemberNa() %>">
		<%=tmpInfo.getMemberNa()%></a>
		</td>
		<td align='right'><%= nf.format(tmpInfo.getSalesPersonsYs()) %>
		<%yingshou=yingshou+tmpInfo.getSalesPersonsYs(); %>
		</td>
		<td align='right'><%= nf.format(tmpInfo.getSalesPersonsQkSs()) %>
		<%yishou=yishou+tmpInfo.getSalesPersonsQkSs(); %>
		</td>
		<td align='right'>
	       
	       <%=nf.format(tmpInfo.getSalesPersonsQk())%>
	       <%qiankuan=qiankuan+tmpInfo.getSalesPersonsQk(); %>     
	    </td>
		<td align='center'><%=nf.format(tmpInfo.getSalesPersonsQk()*100/totalPrice1)%>%</td>
	    
		<td align='center'>
		<%
			String tmptime = tmpInfo.getExportTime();
			if(tmptime==null)tmptime="";
			if(tmptime.length()>=19)
			{
		%>
				<%= tmptime.substring(0,19) %>
		<%
			}
		%>
		
		</td> 
		<td align='left'><%=tmpInfo.getHuoyunbu()%></td>
	    </tr>
	  <%
	
	   }//for
	   %>
	   <tr>
	   <td align='center' >合计</td>
	   <td align='right'></td>
	   <td align='right'></td>
	   <td align='right'><%=nf.format(yingshou)%></td>
	   <td align='right'><%=nf.format(yishou)%></td>
	   <td align='right' ><%=nf.format(qiankuan)%></td>
	   <td align='center' ></td>
	   <td align='center' ></td>
	  </tr>
 <%}
	else if(bbtj.equals("4"))
		{%>
<tr>
		<td colspan="7" align="center">
		<font color=#215385><b>合计：</b><font color="red"><%=exportReportList.size()%></font>条记录
		 </td>
		 
	  </tr>  
      

      <tr>
	   <td align='center' >售货员</td>
	   <td align='center'><4数量</td>
	   <td align='center'>4-5数量</td>
	   <td align='center'>5-6数量</td>
	   <td align='center'>6-7数量</td>
	   <td align='center'>7-8数量</td>
	   <td align='center'>>8数量</td>
	   <td align='center'>合计数量</td>
	  </tr>
     <%
	  if(exportReportList.size()>0)
		{
		%>
		<tr bgcolor="#C2CEDC">
		
		<%
			}
		
	    int index = 0;
      	GoodsExportReportInfo tmpInfo = null;
		int n4=0,n45=0,n56=0,n67=0,n78=0,n8=0;
        float c4=0,c45=0,c56=0,c67=0,c78=0,c8=0;
	    for (index=0; index<exportReportList.size(); index++)
		{
	      		tmpInfo = (GoodsExportReportInfo)exportReportList.get(index);
	      	%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
		
		<td align='center'>
	       
	        	<a href="<%= request.getContextPath()%>/exportItemSearch.do?createtime1=<%=lastExportTime1%>& createtime2=<%=lastExportTime2%>&start_rec_num=1
				&billid=0&region=<%=tmpInfo.getSalesPersonsNa()%>&zhekou='0'&orderstr=GoodsID DESC">
				<%= tmpInfo.getSalesPersonsNa()%></a>
	        
	    </td>
	    <!-- ~4 -->
	    <td align='right'><%= tmpInfo.getTotalRecNum()%></td>
	       <% 
	       	  n4=n4+tmpInfo.getTotalRecNum();
	       %>
	    <!-- 4~5 -->
	    <td align='right'><%= tmpInfo.getSalesPersonsQkNum()%></td>
			<%
			  n45=n45+tmpInfo.getSalesPersonsQkNum();
			%>
		</td>
		<!-- 5~6 -->
	    <td align='right'><%= tmpInfo.getSalesPersonsFhNum()%></td>   
			<%
			  n56=n56+tmpInfo.getSalesPersonsFhNum();
			%>
		</td>
		<!-- 6~7 -->
	    <td align='right'><%= tmpInfo.getSalesPersonsHKNum()%></td>
			<%
			  n45=n45+tmpInfo.getSalesPersonsHKNum();
			%>
		</td>
		<!-- 7~8 -->
	    <td align='right'><%= tmpInfo.getsellnum()%></td>   
			<%
			  n45=n45+tmpInfo.getsellnum();
			%>
		</td>
		<!-- 8~ -->
	    <td align='right'><%= tmpInfo.getbacknum()%></td>   
			<%
			  n45=n45+tmpInfo.getbacknum();
			%>
		</td>
		<td align='right'><%= tmpInfo.getTotalRecNum()+tmpInfo.getSalesPersonsQkNum()+tmpInfo.getSalesPersonsFhNum()+
		tmpInfo.getSalesPersonsHKNum()+tmpInfo.getsellnum()+tmpInfo.getbacknum()%></td>  	
	    </tr>
	  <%
	
	   }//for
	   %>
	   <tr>
	   <td align='right'>合计</td>
	   <td align='right'><%=n4%></td>
	   <td align='right'><%=n45%></td>
	   <td align='right'><%=n56%></td>
	   <td align='right'><%=n67%></td>
	   <td align='right'><%=n78%></td>
	   <td align='right'><%=n8%></td>
	   <td align='right'><%=n4+n45+n56+n67+n78+n8%></td>
	   </tr>
<tr height="40"></tr>   

      <tr>
	   <td align='center' >售货员</td>
	   <td align='center'><4金额</td>
	   <td align='center'>4-5金额</td>
	   <td align='center'>5-6金额</td>
	   <td align='center'>6-7金额</td>
	   <td align='center'>7-8金额</td>
	   <td align='center'>>8金额</td>
	   <td align='center'>合计金额</td>
	   
	  </tr>
     <%
	  if(exportReportList.size()>0)
		{
		%>
		<tr bgcolor="#C2CEDC">
		
		<%
			}
	    for (index=0; index<exportReportList.size(); index++)
		{
	      		tmpInfo = (GoodsExportReportInfo)exportReportList.get(index);
	      	%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
		
		<td align='center'>
	       
	        	<a href="<%= request.getContextPath()%>/exportItemSearch.do?createtime1=<%=lastExportTime1%>& createtime2=<%=lastExportTime2%>&start_rec_num=1
				&billid=0&region=<%=tmpInfo.getSalesPersonsNa()%>&zhekou='0'&orderstr=GoodsID DESC">
				<%= tmpInfo.getSalesPersonsNa()%></a>
	        
	    </td>
	    <!-- ~4 -->
	    <td align='right'><%= nf.format(tmpInfo.getSalesPersonsYs())%></td>   
	       <% c4=c4+tmpInfo.getSalesPersonsYs();
	       %>
	    <!-- 4~5 -->
	    <td align='right'><%= nf.format(tmpInfo.getSalesPersonsQl())%></td>   
			<%c45=c45+tmpInfo.getSalesPersonsQl();
			%>
		</td>
		<!-- 5~6 -->
	    <td align='right'><%= nf.format(tmpInfo.getSalesPersonsSs())%></td>   
			<%c56=c56+tmpInfo.getSalesPersonsSs();
			%>
		</td>
		<!-- 6~7 -->  
	    <td align='right'><%= nf.format(tmpInfo.getSalesPersonsQk())%></td>   
			<%c45=c45+tmpInfo.getSalesPersonsQk();
			%>
		</td>
		<!-- 7~8 --> 
	    <td align='right'><%= nf.format(tmpInfo.getSalesPersonsFh())%></td>   
			<%c45=c45+tmpInfo.getSalesPersonsFh();
			%>
		</td>
		<!-- 8~ --> 
	    <td align='right'><%= nf.format(tmpInfo.getSalesPersonsHK())%></td>   
			<%c45=c45+tmpInfo.getSalesPersonsHK();
			%>
		</td> 
		<td align='right'><%= nf.format(tmpInfo.getSalesPersonsYs()+tmpInfo.getSalesPersonsQl()+tmpInfo.getSalesPersonsSs()+
				tmpInfo.getSalesPersonsQk()+tmpInfo.getSalesPersonsFh()+tmpInfo.getSalesPersonsHK())%></td>   
		</td>		
	    </tr>
	  <%
	
	   }//for
	   %>
	   <tr>
	   <td align='right'>合计</td>
	   <td align='right'><%=c4%></td>
	   <td align='right'><%=c45%></td>
	   <td align='right'><%=c56%></td>
	   <td align='right'><%=c67%></td>
	   <td align='right'><%=c78%></td>
	   <td align='right'><%=c8%></td>
	   <td align='right'><%=nf.format(c4+c45+c56+c67+c78+c8)%></td>
	  
	   </tr>
   <%}
   else if(bbtj.equals("5"))
	{
		int sellnum=0;
		int backnum=0;
		float sellprice=0;
		float backprice=0;
		float lirun=0;
	%>
	  <tr>
		<td colspan="9" align="center">
		<font color=#215385><b>合计：</b><font color="red"><%=exportReportList.size()%></font>条记录
		 </td>
		 
	  </tr>
      <tr>
	   <td align='center' >货物名称</td>
	   <td align='center'>入库数</td>
	   <td align='center'>入库金额</td>
  	   <td align='center'>销售数</td>
  	   <td align='center'>销售金额</td>
	   <td align='center'>销售率</td>
		<td align='center'>平均入库价</td>
	   <td align='center'>平均销售价</td>
	   <td align='center'>销售利润</td>
	   
	  </tr>
     <%
	  if(exportReportList.size()>0)
		{
		%>
		<tr bgcolor="#C2CEDC">
		
		<%
		}
		
	    int index = 0;
      	GoodsExportReportInfo tmpInfo = null;
		float persentFh=0;
		float persentQk=0;
	    for (index=0; index<exportReportList.size(); index++)
		{
	      		tmpInfo = (GoodsExportReportInfo)exportReportList.get(index);
	      	%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
		
		<td align='center'>
	       
	        	<a href="<%= request.getContextPath()%>/exportItemSearch.do?createtime1=<%=lastExportTime1%>& createtime2=<%=lastExportTime2%>&goodsname=<%=tmpInfo.getgoodname()%>&start_rec_num=1
				&billid=0&region=0&zhekou='0'&orderstr=GoodsID DESC"><%= tmpInfo.getgoodname()%></a>
	        
	    </td>
	    <td align='right'>
	       
	        	<%=tmpInfo.getSalesPersonsFhNum()%>
	        	<%backnum=sellnum+tmpInfo.getSalesPersonsFhNum(); %>
	        
	    </td>
	    <td align='right'><%= nf.format(tmpInfo.getSalesPersonsFh()) %>
	   		 	<%backprice=backprice+tmpInfo.getSalesPersonsFh(); %>
		
		</td>
		<td align='right'>
				<%= tmpInfo.getSalesPersonsQkNum() %>
				<%sellnum=sellnum+tmpInfo.getSalesPersonsQkNum(); %>
		</td>
	    <td align='right'>
	       
	       <%=nf.format(tmpInfo.getSalesPersonsQk()) %>
	       <%sellprice=sellprice+tmpInfo.getSalesPersonsQk(); %>
	        
	    </td>
		
		<td align='right'>
	       <%=nf.format(tmpInfo.getSalesPersonsQkNum()*100/tmpInfo.getSalesPersonsFhNum())%>%
	        
	    </td>
	    <td align='right'>
	       <%persentFh=tmpInfo.getSalesPersonsFh()/tmpInfo.getSalesPersonsFhNum();%>
	       <%=nf.format(persentFh)%>
	        
	    </td>
	    <td align='right'>
	       <%
	       if(tmpInfo.getSalesPersonsQkNum()==0)
	    	   persentQk=0;
	       else
		       persentQk=tmpInfo.getSalesPersonsQk()/tmpInfo.getSalesPersonsQkNum();%>
	       <%=nf.format(persentQk)%>
	        
	    </td>
		<td align='right'>
		   <%=nf.format((persentQk-persentFh)*tmpInfo.getSalesPersonsQkNum())%>
		   <%lirun=lirun+(persentQk-persentFh)*tmpInfo.getSalesPersonsQkNum(); %>
		</td>
	    
			 
			
	    </tr>
	  <%
	
	   }//for
	   %>
	<tr>
	   <td align='center' >合计</td>
	   <td align='right'><%=backnum%></td>
   	   <td align='right'><%=nf.format(backprice)%></td>
	   <td align='right'><%=sellnum%></td>
   	   <td align='right'><%=nf.format(sellprice)%></td>
	   <td align='right'></td>
	   <td align='right'></td>
	   <td align='right'></td>
	   <td align='right'><%=nf.format(lirun)%></td>
	  </tr>
    <%}
    else if(bbtj.equals("6"))
	{
		float qichu=0;
		float huikuan=0;
		float huafei=0;
		float ruku=0;
		float curr=0;
	%>
	  <tr>
		<td colspan="9" align="center">
		<font color=#215385><b>合计：</b><font color="red"><%=exportReportList.size()%></font>条记录
		 </td>
		 
	  </tr>
      <tr>
	   <td align='center'>采购员</td>
	   <td align='center'>期初余额</td>
	   <td align='center'>汇款金额</td>
	   <td align='center'>业务员花费</td>
	   <td align='center'>采购商品金额</td>
  	   <td align='center'>当前余额</td>	   
	  </tr>
     <%
	  if(exportReportList.size()>0)
		{
		%>
		<tr bgcolor="#C2CEDC">
		
		<%
		}
		
	    int index = 0;
      	GoodsExportReportInfo tmpInfo = null;
	    for (index=0; index<exportReportList.size(); index++)
		{
	      		tmpInfo = (GoodsExportReportInfo)exportReportList.get(index);
	      	%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
		
		<td align='center'>
	       
	        	<a href="<%= request.getContextPath()%>/importBillSearch.do?start_rec_num=1&orderstr=billid desc&billid=0&sendpersons=<%=tmpInfo.getTotalRecNum()%>&factory=0&inceptpersons=0&importtime1=<%=lastExportTime1%>&importtime2=<%=lastExportTime2%>"><%= tmpInfo.getgoodname()%></a>
	        
	    </td>
	    <td align='right'>
	       
	        	<%=nf.format(tmpInfo.gettotalprice()-(tmpInfo.getDaijin()+tmpInfo.getbackprice()-tmpInfo.getsellprice()))%>
	        	<%qichu=qichu+(tmpInfo.gettotalprice()-(tmpInfo.getDaijin()+tmpInfo.getbackprice()-tmpInfo.getsellprice()));%>
	        
	    </td>
	    <td align='right'>+<%= nf.format(tmpInfo.getDaijin()) %>
	   		 	<%huikuan=huikuan+tmpInfo.getDaijin(); %>
		
		</td>
		<td align='right'>
				<%= nf.format(tmpInfo.getbackprice()) %>
				<%huafei=huafei+tmpInfo.getbackprice(); %>
		</td>
		<td align='right'>
				<%= nf.format(-tmpInfo.getsellprice()) %>
				<%ruku=ruku+tmpInfo.getsellprice(); %>
		</td>
	    <td align='right'>
	       
	       <%=nf.format(tmpInfo.gettotalprice()) %>
	       <%curr=curr+tmpInfo.gettotalprice(); %>
	        
	    </td>	 
			
	    </tr>
	  <%
	
	   }//for
	   %>
	<tr>
	   <td align='center' >合计</td>
	   <td align='right'><%=nf.format(qichu)%></td>
   	   <td align='right'>+<%=nf.format(huikuan)%></td>
	   <td align='right'><%=nf.format(huafei)%></td>
   	   <td align='right'><%=nf.format(-ruku)%></td>
	   <td align='right'><%=nf.format(curr)%></td>
	  </tr>
    <%
    }
    else if(bbtj.equals("7"))
	{
		float ruku=0;
		float tuiku=0;
	%>
	  <tr>
		<td colspan="9" align="center">
		<font color=#215385><b>合计：</b><font color="red"><%=exportReportList.size()%></font>条记录
		 </td>
		 
	  </tr>
      <tr>
	   <td align='center'>厂家</td>
	   <td align='center'>入库金额</td>
	   <td align='center'>退库金额</td>
	   <td align='center'>实际金额</td>
	   <td align='center'>占总数%</td>
	  </tr>
     <%
	  if(exportReportList.size()>0)
		{
		%>
		<tr bgcolor="#C2CEDC">
		
		<%
		}
		
	    int index = 0;
      	GoodsExportReportInfo tmpInfo = null;
	    for (index=0; index<exportReportList.size(); index++)
		{
	      		tmpInfo = (GoodsExportReportInfo)exportReportList.get(index);
	      	%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
		
		<td align='left'>
	       
	        	<a href="<%= request.getContextPath()%>/importBillSearch.do?start_rec_num=1&orderstr=billid desc&billid=0&sendpersons=0&factory=<%=tmpInfo.getTotalRecNum()%>&inceptpersons=0&importtime1=<%=lastExportTime1%>&importtime2=<%=lastExportTime2%>"><%= tmpInfo.getgoodname()%></a>
	        
	    </td>
	    <td align='right'>+<%= nf.format(tmpInfo.getDaijin()) %>
	   		 	<%ruku=ruku+tmpInfo.getDaijin(); %>
		
		</td>
		<td align='right'>
				<%= nf.format(tmpInfo.getbackprice()) %>
				<%tuiku=tuiku+tmpInfo.getbackprice(); %>
		</td>
		<td align='right'>
				<%= nf.format(tmpInfo.getDaijin()+tmpInfo.getbackprice()) %>
				
		</td>
	    <td align='right'>
	       
	       <%=Math.round((tmpInfo.getDaijin()+tmpInfo.getbackprice())/totalPrice*100) %>%
	        
	    </td>	 
			
	    </tr>
	  <%
	
	   }//for
	   %>
	<tr>
	   <td align='center' >合计</td>
	   <td align='right'><%=nf.format(ruku)%></td>
   	   <td align='right'><%=nf.format(tuiku)%></td>
	   <td align='right'><%=nf.format(ruku+tuiku)%></td>
   	   <td align='right'></td>
	   <td align='right'></td>
	  </tr>
    <%
    }
    %>
	</TABLE>
	</div>
	</form>


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
<%
boolean bExt = true;  //true可以导出，false不可以导出
String sExtFile = ""; //导出的Excel文件名
String vTab ="tab1";
%>
<jsp:include page="i_print.jsp?vTab=<%=vTab %>&bExt=<%=bExt %>&sExtFile=<%=sExtFile %>" flush="true" />
</td>
	</tr>
</table>
</body>
</html>
