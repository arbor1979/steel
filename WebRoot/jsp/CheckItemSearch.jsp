<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.global.*"%>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.rule.*"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<script language="JavaScript">

function mySubmit1(){

	document.form1.curpage.value = '1';
	document.form1.classid.value =cat.form1.classid.value;
	document.form1.typeid.value = cat.form1.typeid.value;
	document.form1.action="checkItemSearch.do";
	document.form1.submit();
}

function gotoPage(){
	
	//��ֵΪҪ��ת����ҳ�����ʼ��¼��
	//alert(document.all.gotopagenum.value);

	document.form1.curpage.value = document.all.gotopagenum.value;
	document.form1.action="checkItemSearch.do";
	document.form1.submit();
	
}


function gotoPageByN(n){
	
	document.form1.curpage.value = n;
	document.form1.action="checkItemSearch.do";
	document.form1.submit();
	
}
function viewDetail(url)
{
	window.open(url,'view','top=0,left=0,location=no,directories=no,hotkeys=0,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=800,height=400');
}
function orderList(order,old)
{
	if(order==old)
		order=order+' desc';
	form1.orderby.value=order;
	form1.submit();
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
    <td width="379" height="20" class="mainhead">������ϸ��ѯ</td>
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
	CheckItemSearchRule iisr = (CheckItemSearchRule)request.getAttribute("iisr");
	ResultsetList ibsri = (ResultsetList)request.getAttribute("ibsri");
	List billIdList = (List)request.getAttribute("billIdList");
	List StoreList = (List)request.getAttribute("StoreList");
	List PersonList = (List)request.getAttribute("PersonList");
	List cdList = (List)request.getAttribute("cdList");
 	List departList = (List)request.getAttribute("departList");
	int curStartRecNum = ibsri.curpage;		//��ǰ��ʼ��¼��
	int curPageRecNum = ibsri.pagesize;		//ҳ��¼��
	String orderby=iisr.getOrderStr();
	
	String tmpStr;
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
%>

    	<form name="form1" method="post" action="" onSubmit="return mySubmit1()">
    	<font color=#215385><b>��ѯ����</b></font>
    	<IMG src="images/line1.gif" border=0 width=900>
    	<br>
    	<input type="hidden" name="curpage"  value="1">
    	<input type="hidden" name="classid"  value="">
    	<input type="hidden" name="typeid"  value="">
    	<input type="hidden" name="orderby"  value="<%=orderby %>">
    	<input type="hidden" name="searchbutton" value="��ѯ">
    	<TABLE  class="mailtable"> 
	  <tr>
            <td  align="right"> 
                <b>����:</b>
            </td>
            <td>
            	<SELECT size=1 name="billId">
            		<option value="0">-ȫ��-</option>
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
                <b>�����ʶ:</b>
            </td>
            <td>
            	<input type=text name='goodsId' size="20" class="none" value=<%=iisr.getGif().getGoodsId()%>>
            </td>
            <td  align="right"> 
                <b>¼��Ա:</b>
            </td>
            <td>
            	<SELECT size=1 name="createPerson">
            		<option value="">-ȫ��-</option>
            		<%
            			for(int i=0; i<PersonList.size(); i++)
            			{
            				tmpStr = (String)PersonList.get(i);
            		%>
            		<option value="<%=tmpStr%>" <%=(iisr.getGif().getCreatePerson().equals(tmpStr)?"selected":"") %>><%=tmpStr%></option>
			<%
				}
			%>
            	</SELECT>
            </td>
            
          </tr>
          <tr>
            <td  align="right"> 
                <b>�ֿ�:</b>
            </td>
            <td>
            	<SELECT size=1 name="storeId">
            		<option value="">-ȫ��-</option>
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
                <b>��Ʒ����:</b>
            </td>
            <td>
            	<input type=text name='goodsName' size="20" class="none" value="<%=iisr.getGif().getGoodsName()%>">
            </td>
            <td  align="right"> 
			 <b>����:</b>
			 </td>
            <td> 
			<select name="chandi">
		   		<option value="" <%if(iisr.getGif().getChandi().equals("")) out.print("selected");%>>-ȫ��-</option>
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
			<TD align="right"><b>����ʱ��:</b></TD>
			<TD colspan=3>��ʼ 
				<input type=text name='importtime1' size="20" class="none" value="<%=iisr.getImportTime1() %>">
				<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="���������˵�"  onClick="Calendar_importtime1();">&nbsp; 
				��ֹ 
				<input type=text name='importtime2'  size="20" class="none" value="<%=iisr.getImportTime2() %>"> 
				<img src="./images/cal.gif" width="16" height="16" border="0" style="cursor:hand;" align="absmiddle" alt="���������˵�"  onClick="Calendar_importtime2();">
			</TD>
			<td align="right"><b>����:</b></td>
			<td>
			<select name="kind">
            	<option value="0" <%=(iisr.getKind()==0?"selected":"") %>>-ȫ��-</option>
            	<option value="1" <%=(iisr.getKind()==1?"selected":"") %>>����</option>
            	<option value="-1" <%=(iisr.getKind()==-1?"selected":"") %>>����</option>
         	</td> 
          </tr>
          <tr>
          	 <td align="right"> 
                <b>��Ʒ���:</b>
	         </TD>
          	<td colspan=3>
          	<iframe name="cat" id="cat" src="ClassAndType.do?classid=<%=iisr.getGoodsClass() %>&typeid=<%=iisr.getGoodsType() %>" width="300" height="25" frameborder=0 scrolling=no ></iframe>
          	</td>
          	<%if(ctx.isIfview()) {%>
             <td  align="right" > 
                <b>�ֹ�˾:</b></td>
                <td>
            	<select name="deptid" >
    			<option value="-1">-ȫ��-</option>
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
          &nbsp;&nbsp<input name=searchbutton type=submit value=" ��ѯ " ></td>
	</TABLE>
	</form>

	<br>
<%
	if(ibsri!=null)
	{
%>
	
	<font color=#215385><b>������ϸ�б�</b>���� <b><%=ibsri.allnum%></b> ����ϸ��Ϣ��</font>
	<br>
	<div align="left">
	&nbsp;&nbsp;<font color=#215385>������<b><%=ibsri.allsumnum %></b> ��&nbsp;  �����ܶ�:<b><%=nf.format(ibsri.allmoney)%></b> Ԫ&nbsp;&nbsp;
	</div>
	<IMG src="images/line1.gif" border=0 width=900>
	
	<TABLE  class="mailtable">  

		<tr bgcolor="#C2CEDC">
		<th><a class="tablehead" href="javascript:orderList('billid','<%=orderby%>');">����</a><%if(orderby.equals("billid")) out.print("��"); if(orderby.equals("billid desc")) out.print("��");%></th>
		<%if(ctx.isIfview()) {%>
		<th><a class="tablehead" href="javascript:orderList('shortname','<%=orderby%>');">�ֹ�˾</a><%if(orderby.equals("shortname")) out.print("��"); if(orderby.equals("shortname desc")) out.print("��");%></th><%} %>
		<th>����</th>
		<th><a class="tablehead" href="javascript:orderList('a.goodsid','<%=orderby%>');">�����ʶ</a><%if(orderby.equals("goodsid")) out.print("��"); if(orderby.equals("goodsid desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('goodsname','<%=orderby%>');">��������</a><%if(orderby.equals("goodsname")) out.print("��"); if(orderby.equals("goodsname desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('chandi','<%=orderby%>');">����</a><%if(orderby.equals("chandi")) out.print("��"); if(orderby.equals("chandi desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('b.name','<%=orderby%>');">�ֿ�</a><%if(orderby.equals("b.name")) out.print("��"); if(orderby.equals("b.name desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('checkAmount','<%=orderby%>');">����</a><%if(orderby.equals("checkAmount")) out.print("��"); if(orderby.equals("checkAmount desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('checkUnitPrice','<%=orderby%>');">����</a><%if(orderby.equals("checkUnitPrice")) out.print("��"); if(orderby.equals("checkUnitPrice desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('checkAmount*checkUnitPrice','<%=orderby%>');">���</a><%if(orderby.equals("checkAmount*checkUnitPrice")) out.print("��"); if(orderby.equals("checkAmount*checkUnitPrice desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('createperson','<%=orderby%>');">¼��Ա</a><%if(orderby.equals("createperson")) out.print("��"); if(orderby.equals("createperson desc")) out.print("��");%></th>
		<th><a class="tablehead" href="javascript:orderList('createTime','<%=orderby%>');">ʱ��</a><%if(orderby.equals("createTime")) out.print("��"); if(orderby.equals("createTime desc")) out.print("��");%></th>
		
		</tr>
	
		<%
		
		int index = 0;
		GoodsCheckGoodsInfo tmpInfo = null;
	      	
	    for (index=0; index<ibsri.rslist.size(); index++)
		{
	      	tmpInfo = (GoodsCheckGoodsInfo)ibsri.rslist.get(index);
	      	%>
	        <tr <%if((index%2)==1){%>bgcolor="#CAE4F4"<%}%> >
	        
	        <td align='center' width=50><%= tmpInfo.getBillId() %><a href="javascript:viewDetail('<%= request.getContextPath() + "/checkItemsView.do?billId=" + tmpInfo.getBillId()+"&deptid="+tmpInfo.getDeptid() %>');"><IMG src="images/news1.gif" border=0 alt='�鿴������ϸ'></a></td>
       		<%if(ctx.isIfview()) {%>
       		<td align='center'><%= tmpInfo.getShortname() %></td><%} %>
       		<td align='center'><%= (tmpInfo.getCheckAmount()>0?"<font color=green>��</font>":"<font color=red>��</font>") %></td>
	        <td align='center'><%= tmpInfo.getGoodsId() %></td>
	        <td align='center'><%= tmpInfo.getGoodsName() %></td>
	        <td align='center'><%= tmpInfo.getChandi() %></td>
	        <td align='center'><%= tmpInfo.getStoreName() %></td>
			<td align='right'><%= tmpInfo.getCheckAmount()%></td>
			<td align='right'><%= nf.format(tmpInfo.getCheckUnitPrice()) %></td>
			<td align='right'><%= nf.format(tmpInfo.getCheckAmount()*tmpInfo.getCheckUnitPrice()) %></td>
			<td align='center' width=60><%= tmpInfo.getCreatePerson() %></td>
			<td align='center' width=80><%= tmpInfo.getCreateTime() %></td>
		
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
		<a href="javascript:gotoPageByN(1)"><IMG src="images/startpage.gif" border=0 alt='��ҳ'></a>&nbsp;
		<a href="javascript:gotoPageByN(<%=curStartRecNum-1%>)"><IMG src="images/lastpage.gif" border=0 alt='��һҳ'></a>&nbsp;&nbsp;
		<%
			}
			else
			{
		%>
		<IMG src="images/startpage.gif" border=0 alt='��ҳ'>&nbsp;
		<IMG src="images/lastpage.gif" border=0 alt='��һҳ'>&nbsp;&nbsp;
		<%	
			}
	    	if(curStartRecNum<ibsri.pagecount)
	    	{
	    %>
	    <a href="javascript:gotoPageByN(<%=curStartRecNum+1%>)"><IMG src="images/nextpage.gif" border=0 alt='��һҳ'></a>&nbsp;
	    <a href="javascript:gotoPageByN(<%=ibsri.pagecount%>)"><IMG src="images/endpage.gif" border=0 alt='ĩҳ'></a>&nbsp;&nbsp;&nbsp;&nbsp;
	    <%
	    	}
			else
			{
		%>
		<IMG src="images/nextpage.gif" border=0 alt='��һҳ'>&nbsp;
		<IMG src="images/endpage.gif" border=0 alt='ĩҳ'>&nbsp;&nbsp;&nbsp;&nbsp;
		<%	
			}
	    %>
    	</div>
    	
    	<div align="center">
		�� <select name="gotopagenum" onChange="gotoPage()">
		<%for(int i=1; i<=ibsri.pagecount; i++)
		  {%>
			<option value="<%=i%>" <%if(i==curStartRecNum){%>selected<%}%>><%=i%></option>
		<%}%>
		</select> ҳ
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

