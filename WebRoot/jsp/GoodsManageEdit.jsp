<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.GoodsInfoForm"%>
<%@ page import="mediastore.util.oConvert"%>
<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js">
</script>
<script language="JavaScript">
function JianShu()
{
	if(form1.xishu.value>0)
		form1.baozhuang.value=Math.round(form1.jianxishu.value*100/form1.xishu.value)/100
}
function mySubmit()
{
	
	if(cat.form1.classid.value=='0')
	{
		alert('��ѡ��һ������');
		cat.form1.classid.focus();
		return false;
	}
	if(cat.form1.typeid.value=='0')
	{
		alert('��ѡ��һ������');
		cat.form1.typeid.focus();
		return false;
	}
	if(Trim(form1.goodsId.value)=='')
	{
		alert('��Ʒ��Ų���Ϊ��');
		form1.goodsId.focus();
		return false;
	}
	if(!checkUserName(form1.goodsId.value))
	{
		alert('��Ʒ���ֻ��ʹ�ô�д��ĸ���������');
		form1.goodsId.focus();
		return false;
	}
	if(Trim(form1.guige.value)=='')
	{
		alert('��Ʒ�����Ϊ��');
		form1.guige.focus();
		return false;
	}
	if(Trim(form1.caizhi.value)=='')
	{
		alert('��Ʒ���ʲ���Ϊ��');
		form1.caizhi.focus();
		return false;
	}
	if(!IsFloat(form1.purchaseUnitPrice.value))
	{
		alert('���۵��۱����ǽ��������');
		form1.purchaseUnitPrice.focus();
		return false;
	}
	if(!IsFloat(form1.xishu.value))
	{
		alert('����ϵ������Ϊ��������');
		form1.xishu.focus();
		return false;
	}
	if(Trim(form1.chandi.value)=='')
	{
		alert('��Ʒ���ز���Ϊ��');
		form1.chandi.focus();
		return false;
	}
	form1.goodsType.value=cat.form1.typeid.value;
	return true;
}
function CheckInput()
{
	var filepath=document.form9.text1.value;
	var exename=filepath.substring(filepath.length-3,filepath.length);
	if(filepath.length==0)
	{
		alert("���������ѡ��һ��ͼƬ");
		return false;
	}
	else if(exename.toLowerCase()!="jpg" && exename.toLowerCase()!="gif")
	{
		alert("ѡ��ͼƬͼƬ�ĸ�ʽ������jpg��gif�ļ�");
		return false;
	}
	return true;
}
</script>
</head>
<body background='images/bgall.gif' onload="document.form1.goodsId.focus();">
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
    <td width="379" height="20" class="mainhead">��Ʒ��</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
        <td height="300" align="center" valign="top" >   
          
<%
	GoodsInfoForm gif = (GoodsInfoForm)request.getAttribute("gif");
 %>

    	<form name="form1" method="post" action="goodsManage.do" target="hideframe" onSubmit="return mySubmit();">
    	<font color=#215385><b>�༭��Ʒ��Ϣ</b></font>
    	<IMG src="images/line1.gif" border=0 width=900>
    	<br>
    	<input type="hidden" name="param"  value="save">
    	<input type="hidden" name="oldGoodsId"  value='<%=gif.getGoodsId() %>'>
    	<input type="hidden" name="goodsType"  value='<%=gif.getGoodsType()%>'>
    	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
	  
	    <tr>
           <td  align="right"><b>��Ʒ���:</b></td> 
            <td  align="left"> 
                <iframe name="cat" id="cat" src="ClassAndType.do?classid=<%=gif.getGoodsClass()%>&typeid=<%=gif.getGoodsType() %>" width="300" height="25" frameborder=0 scrolling=no ></iframe>
            </td>
            <td  align="right"><b>��Ʒ���:</b></td> 
         	<td  align="left" > 
            	<input type=text name='goodsId' size="20" class="none" maxlength="15" value='<%=gif.getGoodsId() %>'>(Ψһ)
            </td>
            
         </tr>
         	
        <tr>
        	
        	<td  align="right"><b>��Ʒ���:</b></td> 
         	<td  align="left" > 
            	<input type=text name='guige' size="20" maxlength="50" class="none" value='<%=gif.getGuige() %>'>
            </td>
            <td  align="right"><b>��Ʒ����:</b></td> 
            <td  align="left"> 
            	<input type=text name='caizhi' size="20" maxlength="50" class="none" value='<%=gif.getCaizhi() %>' >
            </td>
         </tr>
         <tr>
         	
         	<td  align="right"><b>�����ۼ�:</b></td> 
            <td  align="left"> 
            	<input type=text name=purchaseUnitPrice size="15" maxlength="10" class="none" value="<%=gif.getPurchaseUnitPrice() %>" onkeyup="InputFloat(this);">(Ԫ/��)
            </td>
         	<td  align="right"><b>������λ:</b></td> 
         	<td  align="left" > 
            	<input type=text name='danwei' size="20" maxlength="10" class="none" value='<%=gif.getDanwei() %>'>(����������)
            </td>
         </tr>	
         <tr>	
         	<td align="right"><b>����ϵ��:</b></td> 
            <td align="left"> 
            	<input type=text name='xishu' size="20" maxlength="10" class="none" value="<%=gif.getXishu() %>" onkeyup="InputFloat(this);" onChange="JianShu();">(����/��λ,��ȷ��λС��)
            </td>
            <td align="right"><b>����:</b></td> 
            <td align="left"> 
				<input type=text name='chandi' size="20" maxlength="10" class="none" value='<%=gif.getChandi() %>'>
            </td>
         </tr>		
         <tr>	
         	<td align="right"><b>������:</b></td> 
            <td align="left"> 
            	<input type=text name='jianxishu' size="20" maxlength="10" class="none" value="<%=gif.getJianxishu() %>" onkeyup="InputFloat(this);" onChange="JianShu();">(����/��)
            </td>
            <td align="right"><b>����װ:</b></td> 
            <td align="left"> 
				<input type=text name='baozhuang' size="20" maxlength="10" class="none" value='<%if(gif.getXishu()>0) out.print(oConvert.getRound(gif.getJianxishu()/gif.getXishu(),2)); %>' disabled>(��λ/��)
            </td>
         </tr>	             
		</TABLE>
		
		 <p align="center"><input name=searchbutton type="submit" value=" ���� ">&nbsp;
		 <input name=searchbutton type="button" onclick="window.navigate('goodsManage.do');" value=" ���� "></p>
		 </form>
        </td>
    </tr>
    </table>
    <iframe name="hideframe" id="hideframe" src="" width="0" height="0" frameborder=0 scrolling=no></iframe> 
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
