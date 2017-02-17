<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@page import="mediastore.web.form.*"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.List"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>

<script language="JavaScript">
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
	List FactList = (List)request.getAttribute("FactList");
	List truckList = (List)request.getAttribute("truckList");
	List departList = (List)request.getAttribute("departList");
	int factory = oConvert.getInt((String)request.getAttribute("factory"),0);
	int deptid = oConvert.getInt((String)request.getAttribute("deptid"),1);
	String zhujima = oConvert.getString((String)request.getAttribute("zhujima"),"");
	String importTime1 = oConvert.getString((String)request.getAttribute("importTime1"),"");
	String importTime2 = oConvert.getString((String)request.getAttribute("importTime2"),"");
	ResultsetList ys = (ResultsetList)request.getAttribute("ys");
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
    <td width="379" height="20" class="mainhead">预收预付查询</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    
    
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td height="300" align="center" valign="top">   
         
    	<form name="form1" method="post" action="yuShouKuanSearch.do" >
    	<font color=#215385><b></b></font>
    	<br>
    	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	  	<tr >
            <td  align="right" width=100> 
                <b>操作类型:</b>
            </td>
            <td >
            	<select name="kind" onchange="form1.submit();">
            		<option value="1" <%=(kind==1?"selected":"")%>>预收货款</option>
            		<option value="-1" <%=(kind==-1?"selected":"")%>>预付货款</option>
            		<option value="0" <%=(kind==0?"selected":"")%>>预付运费</option>
            	</select>
            </td>
            <%
		if(ctx.isIfview()) {
		%>
             <td  align="right" width=80> 
                <b>分公司:</b></td>
                <td >
            	<select name="deptid" >
    			<%
    			    	DepartInfoForm dif=new DepartInfoForm();
    			    	for(int i=0;i<departList.size();i++)
    			    	{
    			    		dif=(DepartInfoForm)departList.get(i);
    			%>
    			<option value="<%=dif.getId() %>" <%=(deptid==dif.getId()?"selected":"") %>><%=dif.getShortname()%></option>
    			<%
    			}
    			%>
    			</select>
    			</td>
            <%
            }else{
            %>
            <input type="hidden" name="deptid" value="<%=ctx.getDeptid() %>">
            <%
            }
            %>
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
            <TD align="right" width=10%><b>操作时间:</b></TD>
		<TD colspan=4>起始 
		<input type=text name='importtime1' size="18" class="none" value="<%=importTime1 %>">
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="弹出日历菜单"  onClick="Calendar_importtime1();">&nbsp;
		&nbsp;终止 
		<input type=text name='importtime2'  size="18" class="none" value="<%=importTime2 %>"> 
		<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="弹出日历菜单"  onClick="Calendar_importtime2();">
		&nbsp;&nbsp;<input type="submit" name="searchbutton" value=" 查询 "></td>
		
         </tr>
		</TABLE>	
		</form>
	 
  
    <font color=#215385><%=(kind==1?"预收":"预付")%>款汇总（共 <b><%=ys.rslist.size()%></b> 个<%=(kind==1?"客户":"供应商")%>，
    期初合计：<b><%=nf.format(ys.oddmoney)%></b> 元，累计<%=(kind==1?"预收":"预付")%>：<b><%=nf.format(ys.allmoney)%></b> 元，
    冲抵合计：<b><%=nf.format(ys.realmoney)%></b> 元，期末合计：<b><%=nf.format(ys.allsumnum)%></b> 元）</font>
	
	<IMG src="images/line1.gif" width=900 border=0>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable">  
	
		<tr bgcolor="#C2CEDC">
		<th><%if(kind==1){out.print("客户");}else if(kind==-1){out.print("供应商");}else{out.print("车辆");}%>名称</th>
		<th>期初余额(元)</th>
		<th><%=(kind==1?"预收":"预付")%>金额(元)↑</th>
		<th>冲抵货款(元)</th>
		<th>期末余额(元)</th>
		<th>查看明细</th>
		</tr>
		
		<%
		
		ChongZhiForm tmpInfo = null;
        for(int index=0;index<ys.rslist.size();index++)
        { 
        	tmpInfo=(ChongZhiForm)ys.rslist.get(index);
%>
		 <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
		 	<%if(kind==1 || kind==-1){ %>
	        <td align='left'><a href="javascript:viewDetail('<%= request.getContextPath()+ "/factoryView.do?factory="+tmpInfo.getFactory() %>');"><%= tmpInfo.getFactoryName() %></a></td>
	        <%}else{ %>
	        <td align='left'><%= tmpInfo.getFactoryName() %></td>
	        <%} %>
	        <td align='right'><%= nf.format(tmpInfo.getBegChuzhi()) %></td>
	        <td align='right'><%= nf.format(tmpInfo.getAddChuzhi()) %></td>
	        <td align='right'><%= nf.format(tmpInfo.getPayhuokuan()) %></td>
	        <td align='right'><%= nf.format(tmpInfo.getBegChuzhi()+tmpInfo.getAddChuzhi()-tmpInfo.getPayhuokuan()) %></td>

			<td align='center'><input type="button" value="单据明细" 	onclick="window.navigate('yuShouKuanSearch.do?param=detail&kind=<%=kind %>&factory=<%=tmpInfo.getFactory() %>&deptid=<%=deptid %>&importtime1=<%=importTime1 %>&importtime2=<%=importTime2 %>');"></td>
            </tr>
<%
  			      	
        }
        %>
	</TABLE>

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

