Windows Registry Editor Version 5.00
<%@ page contentType="application/msexcel;charset=UTF-8" language="java"%>
<%response.setHeader("Content-disposition","attachment; filename=ttygw.reg"); 

String url=String.valueOf(request.getRequestURL());
String path="";
String path1="";
int i=url.indexOf("//");
String b=url.substring(i+2,i+5);
if(b.toLowerCase().equals("www"))
	path=url.substring(i+6,url.length()-1);
else
	path=url.substring(i+2,url.length()-1);
int j=path.indexOf("/");
int k=path.indexOf(":");
//if(j>k) j=k;
path1=path.substring(0,k);
path=path.substring(0,j);

if(b.toLowerCase().equals("www"))
{
%>
[HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Internet Settings\ZoneMap\Domains\<%=path %>]

[HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Internet Settings\ZoneMap\Domains\<%=path %>\www]
"http"=dword:00000002
<%}else{%>
[HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Internet Settings\ZoneMap\Ranges\Range1]
"http"=dword:00000002
":Range"="<%=path%>"
[HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Internet Settings\ZoneMap\Ranges\Range2]
"http"=dword:00000002
":Range"="<%=path1%>"
<%}%>

[HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Internet Settings\Zones\2]
@=""
"DisplayName"="可信站点"
"Description"="该区域包含有您确认不会损坏计算机或数据的网站。"
"Icon"="inetcpl.cpl#00004480"
"CurrentLevel"=dword:00000000
"MinLevel"=dword:00010000
"RecommendedLevel"=dword:00010000
"Flags"=dword:00000043
"1004"=dword:00000000
"1206"=dword:00000000
"1402"=dword:00000000
"1405"=dword:00000000
"1406"=dword:00000000
"1407"=dword:00000000
"1601"=dword:00000000
"1604"=dword:00000000
"1605"=dword:00000000
"1606"=dword:00000000
"1607"=dword:00000000
"1608"=dword:00000000
"1609"=dword:00000001
"1800"=dword:00000000
"1802"=dword:00000000
"1803"=dword:00000000
"1804"=dword:00000000
"1805"=dword:00000000
"1806"=dword:00000000
"1807"=dword:00000000
"1808"=dword:00000000
"1809"=dword:00000003
"1A00"=dword:00000000
"1A02"=dword:00000000
"1A03"=dword:00000000
"1A04"=dword:00000000
"1A05"=dword:00000000
"1A06"=dword:00000000
"1A10"=dword:00000000
"1C00"=hex:00,00,03,00
"1E05"=dword:00030000
"2100"=dword:00000000
"2101"=dword:00000001
"2102"=dword:00000000
"2200"=dword:00000000
"2201"=dword:00000000
"2300"=dword:00000001
"2000"=dword:00000000
"1207"=dword:00000000
"2001"=dword:00000000
"2004"=dword:00000000
"1201"=dword:00000000
"1001"=dword:00000000
"1200"=dword:00000000
"1400"=dword:00000000

[HKEY_LOCAL_MACHINE\SYSTEM\ControlSet001\Control\Print\Forms]
"195*95"=hex:b8,f9,02,00,18,73,01,00,00,00,00,00,00,00,00,00,b8,f9,02,00,18,73,\
  01,00,05,00,00,00,00,00,00,00
"241*130"=hex:80,a9,03,00,d0,fb,01,00,00,00,00,00,00,00,00,00,80,a9,03,00,d0,\
  fb,01,00,c8,f8,04,01,00,00,00,00
 
 [HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Print\Forms]
"241*130"=hex:68,ad,03,00,d0,fb,01,00,00,00,00,00,00,00,00,00,68,ad,03,00,d0,\
  fb,01,00,c9,f8,04,01,00,00,00,00
"195*95"=hex:b8,f9,02,00,18,73,01,00,00,00,00,00,00,00,00,00,b8,f9,02,00,18,73,\
  01,00,ca,f8,04,01,00,00,00,00