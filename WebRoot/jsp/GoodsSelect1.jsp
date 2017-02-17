<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="mediastore.util.oConvert;"%>
<link rel="stylesheet" href="css/style.css" type="text/css">
<style>
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<%
	GoodsInfoForm gif=(GoodsInfoForm)request.getAttribute("gif");
	String param=(String)request.getAttribute("param");
	int hideZero=oConvert.getInt((String)request.getAttribute("hideZero"),0);
	ResultsetList GoodsList=(ResultsetList)request.getAttribute("GoodsList");
	List StoreList=(List)request.getAttribute("StoreList");
	List cdList=(List)request.getAttribute("cdList");
%>
<body bgcolor="#f7f7ff">
<table width="100%"  valigh="top" cellpadding=0 cellspacing=0 >
<form action="goodsSelect1.do" method="post" name="form1">
<input type="hidden" name="param" value="<%=param %>">
<input type="hidden" name="hideZero" value="<%=hideZero %>">
<tr><td >
	<b>类别:</b>
	<select name="goodsType"  onChange="form1.submit();">
   		<option value="" <%if(gif.getGoodsType().equals("")) out.print("selected");%>>-全部-</option>
<%
		
		GoodsTypeInfo gti=null;
		for(int i=0;i<cdList.size();i++)
		{
			
			gti=(GoodsTypeInfo)cdList.get(i);

			if (gti.getId()==oConvert.getInt(gif.getGoodsType(),0))
				out.print("<option value='"+gti.getId()+"' selected>"+gti.getName()+"</option>");
			else
				out.print("<option value='"+gti.getId()+"'>"+gti.getName()+"</option>");
		}
%>
      </select>          
     &nbsp;
		<b>产品:</b>
	<select name="goodsId" <%=(param.length()==0?"onChange='form1.storeid.value=0;form1.submit();'":"") %> >
   		<option value="" <%if(gif.getGoodsId().equals("")) out.print("selected");%>>-全部-</option>
<%
		String str;
		GoodsInfoForm tmp;
		for(int i=0;i<GoodsList.rslist.size();i++)
		{
			tmp = (GoodsInfoForm)GoodsList.rslist.get(i);
			if(hideZero==1 && tmp.getCurRepertory()==0)
				continue;
			//str=tmp.getGoodsId();
			//str=oConvert.limitStr(str,12);
			str=tmp.getGuige();
			str=oConvert.limitStr(str,12);
			str=str+tmp.getGoodsTypeName();
			str=oConvert.limitStr(str,22);
			str=str+tmp.getChandi();
			str=oConvert.limitStr(str,30);
			str=str+tmp.getCurRepertory();
			str=oConvert.HtmlSpace(str);
			
			if (tmp.getGoodsId().equals(gif.getGoodsId()))
				out.print("<option value='"+tmp.getGoodsId()+"' selected>"+str+"</option>");
			else
				out.print("<option value='"+tmp.getGoodsId()+"'>"+str+"</option>");
		}
%>
      </select>  
<%if(param.length()==0){ %>        
     &nbsp;<b>仓库:</b>
     <select name="storeid">
         
<%
		if(gif.getGoodsId().equals(""))
			out.print("<option value='0' "+(gif.getStoreid()==0?"selected":"")+">-全部-</option>");
		StoreInfoForm tmp1;
		for(int i=0;i<StoreList.size();i++)
		{
			tmp1 = (StoreInfoForm)StoreList.get(i);
			str=tmp1.getName();
			str=oConvert.limitStr(str,12);
			str=str+String.valueOf(tmp1.getNum());
			str=oConvert.HtmlSpace(str);
			
			if (tmp1.getId()==gif.getStoreid())
				out.print("<option value='"+tmp1.getId()+"' selected>"+str+"</option>");
			else
				out.print("<option value='"+tmp1.getId()+"'>"+str+"</option>");
		}
%>
          </select>
<%} %>
 </td></tr>
</form>
</table>
</body>
