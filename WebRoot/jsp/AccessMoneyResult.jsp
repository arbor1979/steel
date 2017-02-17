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

</script>
</head>
<%
	int kind = oConvert.getInt((String)request.getAttribute("kind"),0);
	String oper="";
	if(kind==1)
		oper="帐户间转款";
	if(kind==2)
		oper="资本注入";
	if(kind==3)
		oper="资本抽取";
	List acc = (List)request.getAttribute("acc");
	AccountForm af=(AccountForm)request.getAttribute("af");
	NumberFormat nf = NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);		
	nf.setMinimumFractionDigits(2);
	AccountForm tmp=null;
	double curjine=0;
	String back="accessMoney.do";
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
    <td width="379" height="20" class="mainhead"><%=oper %></td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td height="100" align="center" valign="top">   
          
	<br>
	<h3 align=center>操作成功</h3>
	<%if(kind==1){ %>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
          <tr>
            <td  align="right" width="50%"> 
                <b>转出账户:</b>
            </td>
            <td >
            <%
            for(int i=0;i<acc.size();i++)
			{
				tmp=(AccountForm)acc.get(i);
				if(af.getAccfrom()==tmp.getId())
					out.print(nf.format(tmp.getJine()));
			}
            %> 元
			</td>
			</tr>
			<tr>
            <td  align="right"> 
                <b>转入账户:</b>
            </td>
            <td >
            <%
            for(int i=0;i<acc.size();i++)
			{
				tmp=(AccountForm)acc.get(i);
				if(af.getAccto()==tmp.getId())
					out.print(nf.format(tmp.getJine()));
			}
            %> 元
            </td>
         </tr>
         <tr>
         	<td align="right"><b>本次转款:</b></td>
         	<td><%=nf.format(af.getJine()) %> 元</td>
         </tr>
   </table>
   
	<%}else if(kind==2){ %>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
            <tr>
            <%
            for(int i=0;i<acc.size();i++)
			{
				tmp=(AccountForm)acc.get(i);
				if(af.getAcctype()==tmp.getId())
				{
					af.setAccName(tmp.getAccName());
					curjine=tmp.getJine();
				}
			}
            %>
            <td  align="right"> 
                <b><%=af.getAccName() %>:</b>
            </td>
            <td ><%=curjine %> 元</td>
         </tr>
          <tr>
          	<td  align="right" width="50%"> 
                <b>注入金额:</b>
            </td>
            <td ><%=nf.format(af.getJine())%> 元</td>
            </tr>
   </table>
	<%
		back="inputOutputMoney.do";
	}
	else if(kind==3)
	{ 
	%>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
          <tr>
            <%
            for(int i=0;i<acc.size();i++)
			{
				tmp=(AccountForm)acc.get(i);
				if(af.getAcctype()==tmp.getId())
				{
					af.setAccName(tmp.getAccName());
					curjine=tmp.getJine();
				}
			}
            %>
            <td  align="right"> 
                <b><%=af.getAccName() %>:</b>
            </td>
            <td ><%=curjine %> 元</td>
            
         </tr>
          <tr>
          	<td  align="right" width="50%"> 
                <b>抽取金额:</b>
            </td>
            <td ><%=nf.format(af.getJine())%> 元</td>
            </tr>
           
   </table>
<%
	back="inputOutputMoney.do";
}
%>
	<p align=center><input type="button" name="back" value=" 返回 " onclick="window.navigate('<%=back %>');"></p>
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

