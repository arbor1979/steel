<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.util.oConvert"%>
<html>
<head>
<title>天天阳光钢材进销存系统</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
<script  src="js/jquery-1.8.2.min.js">
<script  src="js/menu.js">
<script  src="js/jquery-form.js">
</script>
<script type="text/javascript">
function beginUpload()
{
    //首先验证文件格式
    var fileName = $('#file_input').val();
    if (fileName === '') {
        alert('请选择文件');
        return false;
    }
    var fileType = (fileName.substring(fileName
            .lastIndexOf(".") + 1, fileName.length))
            .toLowerCase();
    if (fileType !== 'xls' && fileType !== 'xlsx') {
        alert('文件格式不正确，excel文件！');
        return false;
    }

    $("#file_form").ajaxSubmit({
        dataType : "json",
        success : function(data) {
            if (data['result'] === 'OK') {
                $("#importresult").val(data.message);
            } else {
                $("#importresult").val(data.message);
            }
            return false;
        }
    });
    return false;
}

</script>
</head>
<body background='images/bgall.gif' onload="form1.name.focus();">
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
    <td width="379" height="20" class="mainhead">期初往来帐</td>
    <td class="mainhead" width="379" height="20">&nbsp;</td>
    <td width="5"></td>
  </tr>
<tr> 
    <td width="6"></td>
    <td valign="top" colspan="2"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr> 
          <td height="300" align="center" valign="top">         
<br>


    	 <form id="file_form" action="initBillImport.do?param=import" enctype="multipart/form-data"
        method="post" onsumbit="return beginUpload();">
        <input type=radio name='importtype' value='import' checked><label for="importtype">期初应付款导入：</label> <a href='html/importExcel.xlsx'>应付款模版下载</a></p>
        <input type=radio name='importtype' value='export'><label for="importtype">期初应收款导入：</label> <a href='html/exportExcel.xlsx'>应收款模版下载</a></p>
        <input type=radio name='importtype' value='store'><label for="importtype">期初库存导入：</label> <a href='html/storeExcel.xlsx'>库存模版下载</a></p>
        <br>
        <input type="file" name="file" id="file_input" /> 
        <input type="submit" value="文件上传" id='upFile-btn'>
    </form>
    <div id='importresult'></div>	 
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

