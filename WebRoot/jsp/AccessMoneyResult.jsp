<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="mediastore.util.oConvert"%>
<%@ page import="mediastore.web.form.*"%>
<%@ page import="java.text.NumberFormat"%>
<html>
<head>
<title>��������ֲĽ�����ϵͳ</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script language="javascript" src="js/menu.js"></script>
<script language="JavaScript">

</script>
</head>
<%
	int kind = oConvert.getInt((String)request.getAttribute("kind"),0);
	String oper="";
	if(kind==1)
		oper="�ʻ���ת��";
	if(kind==2)
		oper="�ʱ�ע��";
	if(kind==3)
		oper="�ʱ���ȡ";
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
	<h3 align=center>�����ɹ�</h3>
	<%if(kind==1){ %>
	<TABLE width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#FFFFFF" class="mailtable"> 
          <tr>
            <td  align="right" width="50%"> 
                <b>ת���˻�:</b>
            </td>
            <td >
            <%
            for(int i=0;i<acc.size();i++)
			{
				tmp=(AccountForm)acc.get(i);
				if(af.getAccfrom()==tmp.getId())
					out.print(nf.format(tmp.getJine()));
			}
            %> Ԫ
			</td>
			</tr>
			<tr>
            <td  align="right"> 
                <b>ת���˻�:</b>
            </td>
            <td >
            <%
            for(int i=0;i<acc.size();i++)
			{
				tmp=(AccountForm)acc.get(i);
				if(af.getAccto()==tmp.getId())
					out.print(nf.format(tmp.getJine()));
			}
            %> Ԫ
            </td>
         </tr>
         <tr>
         	<td align="right"><b>����ת��:</b></td>
         	<td><%=nf.format(af.getJine()) %> Ԫ</td>
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
            <td ><%=curjine %> Ԫ</td>
         </tr>
          <tr>
          	<td  align="right" width="50%"> 
                <b>ע����:</b>
            </td>
            <td ><%=nf.format(af.getJine())%> Ԫ</td>
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
            <td ><%=curjine %> Ԫ</td>
            
         </tr>
          <tr>
          	<td  align="right" width="50%"> 
                <b>��ȡ���:</b>
            </td>
            <td ><%=nf.format(af.getJine())%> Ԫ</td>
            </tr>
           
   </table>
<%
	back="inputOutputMoney.do";
}
%>
	<p align=center><input type="button" name="back" value=" ���� " onclick="window.navigate('<%=back %>');"></p>
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

