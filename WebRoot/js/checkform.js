// JavaScript Document
function LTrim(str)
{
    var whitespace = new String(" \t\n\r");
    var s = new String(str);
    if (whitespace.indexOf(s.charAt(0)) != -1)
    {
        var j=0, i = s.length;
        while (j < i && whitespace.indexOf(s.charAt(j)) != -1)
        {
            j++;
        }
        s = s.substring(j, i);
    }
    return s;
}
//RTrim(string):??????????????
function RTrim(str)
{
    var whitespace = new String(" \t\n\r");
    var s = new String(str);
    if (whitespace.indexOf(s.charAt(s.length-1)) != -1)
    {
        var i = s.length - 1;
        while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1)
        {
            i--;
        }
        s = s.substring(0, i+1);
    }
    return s;
}
//????????
function Trim(str){
 if(str.charAt(0) == " "){
  str = str.slice(1);
  str = Trim(str); 
 }
 return str;
}
function IsInteger(string) 
{  
	var integer; 
	integer =parseInt(string); 
	if (isNaN(integer) || String(integer).length!=string.length) 
	{ 
		return false; 
	} 
	else 
		return true;  
} 
function IsFloat(string) 
{  
	var number; 
	number = new Number(string); 
	if (isNaN(number)) 
	{ 
		return false; 
	} 
	else 
		return true;  
} 
function InputFloat(str)
{
	var moneyObj;
	moneyObj = document.getElementById(str);
	while(!IsFloat(moneyObj.value) && moneyObj.value.length>0)
		moneyObj.value=moneyObj.value.substring(0,moneyObj.value.length-1);
}
function InputInt(str)
{
	var replyObj;
	replyObj = document.getElementById(str);
	if(!IsInteger(replyObj.value) && replyObj.value.length>0)
		replyObj.value=replyObj.value.substring(0,replyObj.value.length-1);
}

function InputNumber(str)
{
	var replyObj;
	var code;
	replyObj = document.getElementById(str);
	code=window.event.keyCode;
	if (code>=48 && code<=57)
	{
	}
	else if(code>=96 && code<=105)
	{
	}
	else
	{
		if(replyObj.value.length>0)
			replyObj.value=replyObj.value.substring(0,replyObj.value.length-1);
	}
}

//????????????
function isLeapYear(year) 
{ 
 if((year%4==0&&year%100!=0)||(year%400==0)) 
 { 
 return true; 
 }  
 return false; 
} 



//??????????????????
function isMoney(pObj,errMsg){
 var obj = eval(pObj);
 strRef = "1234567890.";
 if(!isEmpty(pObj,errMsg)) return false;
 for (i=0;i<obj.value.length;i++) {
  tempChar= obj.value.substring(i,i+1);
  if (strRef.indexOf(tempChar,0)==-1) {
   if (errMsg == null || errMsg =="")
    alert("??????????????,??????");
   else
    alert(errMsg);   
   if(obj.type=="text") 
    obj.focus(); 
   return false; 
  }else{
   tempLen=obj.value.indexOf(".");
   if(tempLen!=-1){
    strLen=obj.value.substring(tempLen+1,obj.value.length);
    if(strLen.length>2){
     if (errMsg == null || errMsg =="")
      alert("??????????????,??????");
     else
      alert(errMsg);   
     if(obj.type=="text") 
     obj.focus(); 
     return false; 
    }
   }
  }
 }
 return true;
}

//??????????????,??????????????
function isNegative(pObj,errMsg){
 var obj = eval(pObj);
 strRef = "1234567890-";
 if(!isEmpty(pObj,errMsg))return false;
 for (i=0;i<obj.value.length;i++) {
  tempChar= obj.value.substring(i,i+1);
  if (strRef.indexOf(tempChar,0)==-1) {
   if (errMsg == null || errMsg =="")
    alert("??????????????,??????");
   else
    alert(errMsg);
   if(obj.type=="text") 
    obj.focus(); 
   return false; 
  }else{
   if(i>0){
    if(obj.value.substring(i,i+1)=="-"){
     if (errMsg == null || errMsg =="")
      alert("??????????????,??????");
     else
      alert(errMsg);   
     if(obj.type=="text") 
     obj.focus(); 
     return false; 
    }
   }
  }
 }
 return true;
}

//??????????????
function isNumber(pObj,errMsg){
 var obj = eval(pObj);
 strRef = "1234567890";
 if(!isEmpty(pObj,errMsg))return false;
 for (i=0;i<obj.value.length;i++) {
  tempChar= obj.value.substring(i,i+1);
  if (strRef.indexOf(tempChar,0)==-1) {
   if (errMsg == null || errMsg =="")
    alert("??????????????,??????");
   else
    alert(errMsg);
   if(obj.type=="text") 
    obj.focus(); 
   return false; 
  }
 }
 return true;
}

//????????????
function isEmpty(pObj,errMsg){
 var obj = eval(pObj);
 if( obj == null || Trim(obj.value) == ""){
  if (errMsg == null || errMsg =="")
   alert("????????!");
  else
   alert(errMsg); 
  obj.focus(); 
  return false;
 }
 return true;
}

//??????????(a-z??A-Z)??????(0-9)
function isSsnString (ssn)
{
	var re=/^[0-9a-zA-Z]*$/i;
	if(re.test(ssn))
		return true;
	else
		return false;
}
//????Email????????
function checkemail(name, data, allowednull)
{
  var datastr = data;
  var lefttrim = datastr.search(/\S/gi);
  
  if (lefttrim == -1) {
    if (allowednull) {
      return 1;
    } else {
      alert("????????????????E-mail??????");
      return -1;
    }
  }
  var myRegExp = /[a-z0-9](([a-z0-9]|[_\-\.][a-z0-9])*)@([a-z0-9]([a-z0-9]|[_\-][a-z0-9])*)((\.[a-z0-9]([a-z0-9]|[_\-][a-z0-9])*)*)/gi;
  var answerind = datastr.search(myRegExp);
  var answerarr = datastr.match(myRegExp);
  
  if (answerind == 0 && answerarr[0].length == datastr.length)
  {
    return 0;
  }
  
  alert("????????????????E-mail??????");
  return -1;
}
//????????????????
function checkstring(name,data,allowednull)
{
var datastr = data;
var lefttrim = datastr.search(/\S/gi);

if (lefttrim == -1) {
    if (allowednull) {
      return 1;
    } else {
      alert("??????" + name + "??");
      return -2;
    }
  }
  
  if (datastr.search(/[<>]/gi) != -1) {
    alert("" + name + "??????????????<>");
    return -1;
  }
  return 0;
}
//?????
function isLeapYear(year) 
{ 
	if((Number(year)%4==0&&Number(year)%100!=0)||(Number(year)%400==0)) 
	{ 
		return true; 
	}  
	return false; 
}
function ChkUtil() { }
//校验是否为空(先删除二边空格再验证)
ChkUtil.isNull = function (str) {
if (null == str ||  ""== str.trim()) {
  return true;
} else {
  return false;
}
};
//校验是否全是数字
ChkUtil.isDigit  = function (str) {
var patrn=/^\d+$/;
return patrn.test(str);
};
//校验是否是整数
ChkUtil.isInteger = function (str) {
var patrn=/^([+-]?)(\d+)$/;
return patrn.test(str);
};
//校验是否为正整数
ChkUtil.isPlusInteger = function (str) {
var patrn=/^([+]?)(\d+)$/;
return patrn.test(str);
};
//校验是否为负整数
ChkUtil.isMinusInteger = function (str) {
var patrn=/^-(\d+)$/;
return patrn.test(str);
};
//校验是否为浮点数
ChkUtil.isFloat=function(str){
var patrn=/^([+-]?)\d*\.\d+$/;
return patrn.test(str);
};
//校验是否为正浮点数
ChkUtil.isPlusFloat=function(str){
  var patrn=/^([+]?)\d*\.\d+$/;
  return patrn.test(str);
};
//校验是否为负浮点数
ChkUtil.isMinusFloat=function(str){
  var patrn=/^-\d*\.\d+$/;
  return patrn.test(str);
};
//校验是否仅中文
ChkUtil.isChinese=function(str){
var patrn=/[\u4E00-\u9FA5\uF900-\uFA2D]+$/;
return patrn.test(str);
};
//校验是否仅ACSII字符
ChkUtil.isAcsii=function(str){
var patrn=/^[\x00-\xFF]+$/;
return patrn.test(str);
};
//校验手机号码
ChkUtil.isMobile = function (str) {
var patrn = /^0?1((3[0-9]{1})|(59)){1}[0-9]{8}$/;
return patrn.test(str);
};
//校验电话号码
ChkUtil.isPhone = function (str) {
var patrn = /^(0[\d]{2,3}-)?\d{6,8}(-\d{3,4})?$/;
return patrn.test(str);
};
//校验URL地址
ChkUtil.isUrl=function(str){
var patrn= /^http[s]?:\/\/[\w-]+(\.[\w-]+)+([\w-\.\/?%&=]*)?$/;
return patrn.test(str);
};
//校验电邮地址
ChkUtil.isEmail = function (str) {
var patrn = /^[\w-]+@[\w-]+(\.[\w-]+)+$/;
return patrn.test(str);
};
//校验邮编
ChkUtil.isZipCode = function (str) {
var patrn = /^\d{6}$/;
return patrn.test(str);
};
//校验合法时间
ChkUtil.isDate = function (str) {
  if(!/\d{4}(\.|\/|\-)\d{1,2}(\.|\/|\-)\d{1,2}/.test(str)){
    return false;
  }
  var r = str.match(/\d{1,4}/g);
  if(r==null){return false;};
  var d= new Date(r[0], r[1]-1, r[2]);
  return (d.getFullYear()==r[0]&&(d.getMonth()+1)==r[1]&&d.getDate()==r[2]);
};
//校验字符串：只能输入6-20个字母、数字、下划线(常用手校验用户名和密码)
ChkUtil.isString6_20=function(str){
var patrn=/^(\w){6,20}$/;
return patrn.test(str);
};

