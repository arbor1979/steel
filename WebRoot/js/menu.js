

function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_showHideLayers() { //v6.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v=='hide')?'hidden':v; }
    obj.visibility=v; }
}
//ȥ���ո�
function Trim(str){
 if(str.charAt(0) == " "){
  str = str.slice(1);
  str = Trim(str); 
 }
 return str;
}

//�ж��Ƿ��ǿ�
function isEmpty(pObj,errMsg){
 var obj = eval(pObj);
 if( obj == null || Trim(obj.value) == ""){
  if (errMsg == null || errMsg =="")
   alert("����Ϊ��");
  else
   alert(errMsg); 
  obj.focus(); 
  return false;
 }
 return true;
}

//�ж��Ƿ�������
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
//�ж��Ƿ�������
function isNumber(pObj,errMsg){
 var obj = eval(pObj);
 strRef = "1234567890-";
 if(!isEmpty(pObj,errMsg))return false;
 for (i=0;i<obj.value.length;i++) {
  tempChar= obj.value.substring(i,i+1);
  if (strRef.indexOf(tempChar,0)==-1) {
   if (errMsg == null || errMsg =="")
    alert("���ݲ�����Ҫ������");
   else
    alert(errMsg);
   if(obj.type=="text") 
    obj.focus(); 
   return false; 
  }
 }
 return true;
}
//�ж��Ƿ���С��
function isFloat(pObj,errMsg){
 var obj = eval(pObj);
 strRef = "1234567890.-E";
 if(!isEmpty(pObj,errMsg))return false;
 for (i=0;i<obj.value.length;i++) {
  tempChar= obj.value.substring(i,i+1);
  if (strRef.indexOf(tempChar,0)==-1) {
   if (errMsg == null || errMsg =="")
    alert("���ݲ�����Ҫ������");
   else
    alert(errMsg);
   if(obj.type=="text") 
    obj.focus(); 
   return false; 
  }
 }
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
function InputInt(str)
{
	var replyObj;
	replyObj = document.getElementById(str);
	if(!IsInteger(replyObj.value) && replyObj.value.length>0)
		replyObj.value=replyObj.value.substring(0,replyObj.value.length-1);
}
function InputFloat(moneyObj)
{
	if(moneyObj.value=='-')
		return;
	while(!IsFloat(moneyObj.value) && moneyObj.value.length>0)
		moneyObj.value=moneyObj.value.substring(0,moneyObj.value.length-1);
}

function Calendar_importtime1()
{
var newWindow;
var urlstring = './html/Calendar_importtime1.htm'
newWindow = window.open(urlstring,'','height=200,width=280,left=280,top=200,toolbar=no,minimize=no,status=no,memubar=no,location=no,scrollbars=no')

}
function Calendar_importtime2()
{
var newWindow;
var urlstring = './html/Calendar_importtime2.htm'
newWindow = window.open(urlstring,'','height=200,width=280,left=560,top=200,toolbar=no,minimize=no,status=no,memubar=no,location=no,scrollbars=no')

}
function Calendar_paymenttime1()
{
var newWindow;
var urlstring = './html/Calendar_paymenttime1.htm'
newWindow = window.open(urlstring,'','height=200,width=280,left=280,top=200,toolbar=no,minimize=no,status=no,memubar=no,location=no,scrollbars=no')

}
function Calendar_paymenttime2()
{
var newWindow;
var urlstring = './html/Calendar_paymenttime2.htm'
newWindow = window.open(urlstring,'','height=200,width=280,left=560,top=200,toolbar=no,minimize=no,status=no,memubar=no,location=no,scrollbars=no')

}
function Calendar_paylimtime()
{
var newWindow;
var urlstring = './html/Calendar_paylimtime.htm'
newWindow = window.open(urlstring,'','height=200,width=280,left=280,top=200,toolbar=no,minimize=no,status=no,memubar=no,location=no,scrollbars=no')

}

function isLeapYear(year) 
{ 
	if((Number(year)%4==0&&Number(year)%100!=0)||(Number(year)%400==0)) 
	{ 
		return true; 
	}  
	return false; 
}


//У�飬����������ֵ�Ƿ�Ϊ��Ч����yyyy-mm-dd hh:mm:ss
function isDateChen(checktext){

	var datetime;
	var year,month,day,hour,mini,senc;
	var gone,gtwo,gthree,gfour,gfive;
	if(Trim(checktext.value)!=""){
		datetime = Trim(checktext.value);
		if(datetime.length==19){
			year=datetime.substring(0,4);
			if(isNaN(year)==true){
				alert("ʱ���ʽ����ȷ\n���磺yyyy-mm-dd hh:mm:ss\n����2001-02-23 02:56:12");
				checktext.focus();
				return false;
			}
			gone=datetime.substring(4,5);
			month=datetime.substring(5,7);
			if(isNaN(month)==true){
				alert("ʱ���ʽ����ȷ\n���磺yyyy-mm-dd hh:mm:ss\n����2001-02-23 02:56:12");
				checktext.focus();
				return false;
			}
			gtwo=datetime.substring(7,8);
			day=datetime.substring(8,10);
			if(isNaN(day)==true){
				alert("ʱ���ʽ����ȷ\n���磺yyyy-mm-dd hh:mm:ss\n����2001-02-23 02:56:12");
				checktext.focus();
				return false;
			}
			gthree=datetime.substring(10,11);
			firstm = datetime.indexOf(":");//��һ��ð�ŵ�λ��
			//alert("firstm:"+firstm);
			hour=datetime.substring(11,firstm);
			if(isNaN(hour)==true){
				alert("ʱ���ʽ����ȷ\n���磺yyyy-mm-dd hh:mm:ss\n����2001-02-23 02:56:12");
				checktext.focus();
				return false;
			}
			gfour=datetime.substring(firstm,firstm+1);
			
			//alert(datetime.length);
			tempstr = datetime.substring(firstm+1,datetime.length);
			secondm = tempstr.indexOf(":");//�ڶ���ð�ŵ�λ��
			mini=tempstr.substring(0,secondm);
			if(isNaN(mini)==true){
				alert("ʱ���ʽ����ȷ\n���磺yyyy-mm-dd hh:mm:ss\n����2001-02-23 02:56:12");
				checktext.focus();
				return false;
			}
			gfive=tempstr.substring(secondm,secondm+1);
			senc=tempstr.substring(secondm+1,tempstr.length);
			if(isNaN(senc)==true){
				alert("ʱ���ʽ����ȷ\n���磺yyyy-mm-dd hh:mm:ss\n����2001-02-23 02:56:12");
				checktext.focus();
				return false;
			}
			
			//alert(year+gone+month+gtwo+day+gthree+hour+gfour+mini+gfive+senc)
			
			if((gone=="-")&&(gtwo=="-")){
				if( Number(month)<1 || Number(month)>12 ) { 
					alert("�·ݱ�����01��12֮��!"); 
					checktext.focus();
					return false; 
				} 
				if(Number(day)<1 || Number(day)>31){ 
					alert("���ڱ�����01��31֮��!");
					checktext.focus(); 
					return false; 
				}else{
					if(Number(month)==2){  
					
						if(isLeapYear(year)&& Number(day)>29){ 
							alert("2�·����ڱ�����01��29֮��"); 
							checktext.focus();
							return false; 
						}       
						if(!isLeapYear(year)&& Number(day)>28){ 
							alert("2�·����ڱ�����01��28֮��");
							checktext.focus(); 
							return false; 
						} 
					} 
					if((Number(month)==4||Number(month)==6||Number(month)==9||Number(month)==11)&&(Number(day)>30)){ 
						alert("��4��6��9��11�·�\n���ڱ�����01��30֮��");
						checktext.focus(); 
						return false; 
					} 
				}
			}else{
				alert("ʱ���ʽ����ȷ\n���磺yyyy-mm-dd hh:mm:ss\n����2001-02-23 02:56:12");
				checktext.focus();
				return false;
			}
		}else{
			alert("ʱ���ʽ����ȷ\n���磺yyyy-mm-dd hh:mm:ss\n����2001-02-23 02:56:12");
			checktext.focus();
			return false;
		}
	}else{
		return true;
	}
	return true;
}
function isDate(checktext){

	var datetime;
	var year,month,day;
	var gone,gtwo;
	var msg="ʱ���ʽ����ȷ\n���磺yyyy-mm-dd����2007-01-01";
	if(Trim(checktext.value)!=""){
		datetime = Trim(checktext.value);
		if(datetime.length==10){
			year=datetime.substring(0,4);
			if(isNaN(year)==true){
				alert(msg);
				checktext.focus();
				return false;
			}
			gone=datetime.substring(4,5);
			month=datetime.substring(5,7);
			if(isNaN(month)==true){
				alert(msg);
				checktext.focus();
				return false;
			}
			gtwo=datetime.substring(7,8);
			day=datetime.substring(8,10);
			if(isNaN(day)==true){
				alert(msg);
				checktext.focus();
				return false;
			}
			
			if((gone=="-")&&(gtwo=="-")){
				if( Number(month)<1 || Number(month)>12 ) { 
					alert("�·ݱ�����01��12֮��!"); 
					checktext.focus();
					return false; 
				} 
				if(Number(day)<1 || Number(day)>31){ 
					alert("���ڱ�����01��31֮��!");
					checktext.focus(); 
					return false; 
				}else{
					if(Number(month)==2){  
					
						if(isLeapYear(year)&& Number(day)>29){ 
							alert("2�·����ڱ�����01��29֮��"); 
							checktext.focus();
							return false; 
						}       
						if(!isLeapYear(year)&& Number(day)>28){ 
							alert("2�·����ڱ�����01��28֮��");
							checktext.focus(); 
							return false; 
						} 
					} 
					if((Number(month)==4||Number(month)==6||Number(month)==9||Number(month)==11)&&(Number(day)>30)){ 
						alert("��4��6��9��11�·�\n���ڱ�����01��30֮��");
						checktext.focus(); 
						return false; 
					} 
				}
			}else{
				alert(msg);
				checktext.focus();
				return false;
			}
		}else{
			alert(msg);
			checktext.focus();
			return false;
		}
	}else{
		return true;
	}
	return true;
}

function checkUserName(iv)
{ 
	var str=/^[A-Z0-9]+$/;
	if (!str.test(iv))
	{ 
		return false;
	} 
	else
		return true;
}


