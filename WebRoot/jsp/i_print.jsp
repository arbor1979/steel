<%@ page contentType="text/html; charset=gbk" language="java" %>
<%
String vTab = (String)request.getParameter("vTab");
boolean bExt=(Boolean.parseBoolean((String)request.getParameter("bExt")));
String sExtFile = (String)request.getParameter("sExtFile");
%>
<script language="javascript">
<!--
var bDis = false;

var bexcel = true;
function objErr(){
  if(bexcel) bexcel = !bexcel ;
}
function disPrint(){
  if(!bDis){
    doTab.style.visibility="visible";
    bDis = !bDis;
  }else{
    doTab.style.visibility="hidden";
    bDis = !bDis;
  }
  return;
}

function printR(){
  var a = window.open("jsp/i_doprint.jsp?tab=<%=vTab%>");
}

var letters = new Array('A','B','C','D','E','F','G','H','I','J','K','L',
                        'M','N','O','P','Q','R','S','T','U','V','W','X',
                        'Y','Z');

function exportE(){ 
 var rowCount ;
 var oRow;
 var curr_row, curr_cell;
 if(!bexcel){
   alert("��û�а�װExcel���޷������ļ���");
   return;
 }
 rowCount = tab1.rows.length ;
 


 if(rowCount > 0){
 	//����һ��worksheet
	
     var Excel = new ActiveXObject("Excel.Application");  
    // Excel.Visible = true;
   
	 var myWorkbook = Excel.Workbooks.Add() ;
	  
	 var myWorksheet = myWorkbook.Worksheets(1) ;
	 for (curr_row = 0; curr_row < rowCount; curr_row++)
	 {
	   oRow = tab1.rows[curr_row];
	   for (curr_cell = 0; curr_cell < oRow.cells.length; curr_cell++)
	   {
	   	//д���ݵ�Excel��worksheet������ʵ�������ֻ֧���������Ϊ52��
	   	var a  = oRow.cells[curr_cell].innerText.toString() ;
	   	if(curr_cell > 25){
   			myWorksheet.Range('A' + letters[curr_cell - 26] + (curr_row + 1)).NumberFormatLocal = "@";	
	   		myWorksheet.Range('A' + letters[curr_cell - 26] + (curr_row + 1)).Value = a;
	   	}else{
   			myWorksheet.Range(letters[curr_cell] + (curr_row + 1)).NumberFormatLocal = "@" ;
	   		myWorksheet.Range(letters[curr_cell] + (curr_row + 1)).Value = a;
	   	}
	   	if(curr_row == 0)
	   		myWorksheet.Range(letters[curr_cell] + 1).Font.Bold = true;
	   	
	   }
	 }
 
	 //ȷ���ļ���Ψһ
	 var strFileName = aaa.extfile.value;
	 //�����ļ�
	 myWorkbook.SaveAs(strFileName);
	 //�ر�Excel
	 myWorkbook.Close;
	 alert("�����ļ���" + strFileName);
 }
}
//-->
</script>

<table border="0" align="center" bordercolor="#FFFFFF" bgcolor="#FFFFFF"  cellspacing="0" cellpadding="10" >
  <tr>
    <td align="center" bordercolor="#FFFFFF" bgcolor="#FFFFFF"> <A href="javascript:disPrint()"><img src="images/oprint.gif" width="50" height="50" border="0"></A> 
    </td>
  </tr>
<tr>
    <td bgcolor="#FFFFFF"> 
      <DIV id="doTab" style="width:500; z-index:1; visibility: hidden">
<form name="aaa">
  <table width="100%" border="0" cellspacing="0" cellpadding="3" class="tableGlcx" align="center">
    <tr> 
      <td colspan="2"><b>��ӡ����</b></td>
    </tr>
    <tr> 
      <td colspan="2" bgcolor="#FFFFFF">���ñ��������Ӧ��ӡֽ�� 
        <select name="tabwidth">
          <option value="800">A4 ����</option>
          <option value="1200">A4 ����</option>
          <option value="1000">A3 ����</option>
          <option value="1800">A3 ����</option>
        </select>
        <input type="button" name="Button" value="��ӡԤ��" onClick="printR()">
      </td>
    </tr>
<%if(bExt){%>
    <tr> 
      <td colspan="2" bgcolor="#FFFFFF">������ѯ�����Excel�ļ�
        <input type="text" name="extfile" size="20" maxlength="100" value="c:\<%=sExtFile%>">
        <input type="button" name="Button" value=" ���� " onClick="exportE()">
      </td>
    </tr>
<%}%>
  </table>
</form>
</Div>
</td>
</tr></table>
<br>
<%if(bExt)
{%>

<OBJECT ID="Excel" CLASSID="CLSID:00020812-0000-0000-C000-000000000046" codebase="javascript:objErr();">
</OBJECT>

<%}%>
