<%@ page contentType="text/html; charset=gbk" language="java" %>

<%
String tab = request.getParameter("tab");
%>
<html>
<head>
<title></title>
<link rel="stylesheet" href="../css/print.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" topmargin="0"  >
<br>
<br>
<div id="p_title">

</div>
<br>
<div id="p_content" align="center">
</div>
</body>
<script language="javascript">
<!--

function onprintclick()
{
	window.print();
}

function window.onbeforeprint()
{
	
			try
			{
				document.all("onPrint").style.display = "none";
			}
			catch(e){}
		
}

function window.onafterprint()
{
	
			try
			{
				document.all("onPrint").style.display = "block";
			}
			catch(e){}
		
}


function removeLink(ss) {

  //var re =  /<([img|IMG][^>]*)>/ig;   
  //var r = ss.replace(re,"");
  
  var re = /<([a|A][^>]*)>/ig;   
  var  r = ss.replace(re, "");
  re = /<\/A>/ig;
  r = r.replace(re,"");
  

  
  re =  /nowrap/ig;   
  r = r.replace(re,"");
  return r;
}

  var a = window.opener;
  var tt = removeLink(a.p_content.innerHTML);
  p_content.innerHTML = tt;
  p_title.innerHTML = a.p_title.innerHTML;
  document.title = a.document.title;
  if(parseInt(<%=tab%>.width) > parseInt(a.aaa.tabwidth.value)||<%=tab%>.width == "100%"){
  	<%=tab%>.style.width = a.aaa.tabwidth.value;
  }
//-->
</script>
<body>
<br>
<br>
<br>
<div ID="onPrint">
<table width="50%"  border="0" align="center" cellspacing="0" cellpadding="0" bgcolor="#ffffff" >
	<tr>
		<td align="center" bgcolor="#ffffff">
				<input type="button" value="´òÓ¡" onclick="onprintclick();" style="width:80px">&nbsp;&nbsp;&nbsp;&nbsp; <input type="button" value="¹Ø±Õ" onclick="javascript:window.close();" style="width:80px">
		</td>
	</tr>
</table>
</div>
</body>
</html>
