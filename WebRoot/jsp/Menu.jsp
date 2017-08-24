<%@ page contentType="text/html; charset=gbk" language="java" %>
<%@ page import="mediastore.web.form.UserInfoForm" %>
<%@ page import="mediastore.web.global.Globals" %>
<LINK href="images/ttygw.ico" rel="Shortcut Icon">
<LINK href="images/ttygw.ico" rel=Bookmark>
<SCRIPT> 
<!-- 
window.defaultStatus="技术服务电话：0371-66871976";
//--> 
</SCRIPT> 
<style type="text/css">
.menu_iframe{position:absolute; visibility:inherit; top:0px; left:0px;  z-index:-1;filter:'progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)'}
</style>
<%
	UserInfoForm ctx = (UserInfoForm)session.getAttribute(Globals.SESSION_CONTEXT);
%>
<script>

var mmenus    = new Array();
var misShow   = new Boolean(); 
misShow=false;
var misdown   = new Boolean();
misdown=false;
var mnumberofsub=0;
var musestatus=false;
var mpopTimer = 0;
mmenucolor='#215385';
mfontcolor='#ffffff';
mmenuoutcolor='#B5BED6';
mmenuincolor='#B5BED6';
mmenuoutbordercolor='#215385';
mmenuinbordercolor='#000000';
mmidoutcolor='#8D8A85';
mmidincolor='#8D8A85';
mmenuovercolor='MenuText';
mitemedge='1';
msubedge='0';
mmenuunitwidth=85;
mmenuitemwidth=95;
mmenuheight=25;
mmenuwidth='980';
mmenuadjust=0;
mmenuadjustV=0;
mfonts='font-family: 宋体; font-size: 9pt; color: #ffffff; ';mcursor='default';


function stoperror(){
return true;
}

window.onerror=stoperror;

function mpopOut() {
   mpopTimer = setTimeout('mallhide()', 500);
}

function getReal(el, type, value) {
	temp = el;
	while ((temp != null) && (temp.tagName != "BODY")) {
		if (eval("temp." + type) == value) {
			el = temp;
			return el;
		}
		temp = temp.parentElement;
	}
	return el;
}


function mMenuRegister(menu) 
{
  mmenus[mmenus.length] = menu;
  return (mmenus.length - 1);
}

function mMenuItem(caption,command,target,isline,statustxt,level,img,sizex,sizey,pos){
	this.items = new Array();
	this.caption=caption;
	this.command=command;
	this.target=target;
	this.isline=isline;
	this.statustxt=statustxt;
	if(level!=null){mnumberofsub++;
	this.hasc=mnumberofsub;}
	this.level=level;
	this.img=img;
	this.sizex=sizex;
	this.sizey=sizey;
	this.pos=pos;
}

function mMenu(caption,command,target,img,sizex,sizey,pos){
	this.items = new Array();
	this.caption=caption;
	this.command=command;
	this.target=target;
	this.img=img;
	this.sizex=sizex;
	this.sizey=sizey;
	this.pos=pos;
	this.id=mMenuRegister(this);
}
function mMenuAddItem(item)
{
  this.items[this.items.length] = item;
  item.parent = this.id;
  this.children=true;
}

mMenu.prototype.addItem = mMenuAddItem;
mMenuItem.prototype.addsubItem = mMenuAddItem;

function mtoout(src){

src.style.borderLeftColor=mmenuoutbordercolor;
src.style.borderRightColor=mmenuinbordercolor;
src.style.borderTopColor=mmenuoutbordercolor;
src.style.borderBottomColor=mmenuinbordercolor;
src.style.backgroundColor=mmenuoutcolor;
src.style.color=mmenuovercolor;
}
function mtoin(src){

src.style.borderLeftColor=mmenuinbordercolor;
src.style.borderRightColor=mmenuoutbordercolor;
src.style.borderTopColor=mmenuinbordercolor;
src.style.borderBottomColor=mmenuoutbordercolor;
src.style.backgroundColor=mmenuincolor;
src.style.color=mmenuovercolor;
}
function mnochange(src){
src.style.borderLeftColor=mmenucolor;
src.style.borderRightColor=mmenucolor;
src.style.borderTopColor=mmenucolor;
src.style.borderBottomColor=mmenucolor;
src.style.backgroundColor='';
src.style.color=mfontcolor;

}
function mallhide(){
	for(var nummenu=0;nummenu<mmenus.length;nummenu++){
		var themenu=document.all['mMenu'+nummenu];
		var themenudiv=document.all['mmenudiv'+nummenu];
                mnochange(themenu);
                mmenuhide(themenudiv);
                }
        for(nummenu=1;nummenu<=mnumberofsub;nummenu++){  
        	var thesub=document.all['msubmenudiv'+nummenu];      
        	msubmenuhide(thesub);
        	mnochange(document.all['mp'+nummenu]);
        	document.all["mitem"+nummenu].style.color=mfontcolor;
        	}
}
function mmenuhide(menuid){

menuid.style.visibility='hidden';
misShow=false;
}
function msubmenuhide(menuid){

menuid.style.visibility='hidden';
}
function mmenushow(menuid,pid)
{

menuid.style.left=parseInt(document.body.clientWidth*0.02)+mmenutable.offsetLeft+pid.offsetLeft+mmenuadjust;menuid.style.top=mposflag.offsetTop+mmenutable.offsetHeight+mmenuadjustV;
if(mmenuitemwidth+parseInt(menuid.style.left)>document.body.clientWidth+document.body.scrollLeft)
menuid.style.left=document.body.clientWidth+document.body.scrollLeft-mmenuitemwidth;
menuid.style.visibility='visible';
misShow=true;
}
function mshowsubmenu(menuid,pid,rid){

menuid.style.left=pid.offsetWidth+rid.offsetLeft;
menuid.style.top=pid.offsetTop+rid.offsetTop-3;
if(mmenuitemwidth+parseInt(menuid.style.left)>document.body.clientWidth+document.body.scrollLeft)
menuid.style.left=document.body.clientWidth+document.body.scrollLeft-mmenuitemwidth;
menuid.style.visibility='visible';
}
function mmenu_over(menuid,x){
toel = getReal(window.event.toElement, "className", "coolButton");
fromel = getReal(window.event.fromElement, "className", "coolButton");
if (toel == fromel) return;

  if(!misShow)
  {
    mtoout(eval("mMenu"+x));
  }else
  {
    mallhide();
    mtoin(eval("mMenu"+x));
    mmenushow(menuid,eval("mMenu"+x));
  }

  clearTimeout(mpopTimer);
}
function mmenu_out(x){
toel = getReal(window.event.toElement, "className", "coolButton");
fromel = getReal(window.event.fromElement, "className", "coolButton");
if (toel == fromel) return;
if (misShow){
mtoin(eval("mMenu"+x));
}else{
mnochange(eval("mMenu"+x));
}
mpopOut();
}
function mmenu_down(menuid,x){
  if(misShow){
  mmenuhide(menuid);
  mtoout(eval("mMenu"+x));
  }
  else{
  mtoin(eval("mMenu"+x));
  mmenushow(menuid,eval("mMenu"+x));
  misdown=true;
  }
}
function mmenu_up(){
  misdown=false;
}
function mmenuitem_over(menuid,item,x,j,i){

toel = getReal(window.event.toElement, "className", "coolButton");
fromel = getReal(window.event.fromElement, "className", "coolButton");
if (toel == fromel) return;
srcel = getReal(window.event.srcElement, "className", "coolButton");
        for(var nummenu=1;nummenu<=mnumberofsub;nummenu++)
        {  
        	var thesub=document.all['msubmenudiv'+nummenu]; 
        	if(!(menuid==thesub||menuid.style.tag>=thesub.style.tag)){
        	msubmenuhide(thesub);
        	mnochange(document.all['mp'+nummenu]);
        	document.all["mitem"+nummenu].style.color=mfontcolor;
        	}
        }
if(item)document.all["mitem"+item].style.color=mmenuovercolor;
if(misdown||item){
	mtoin(srcel);
}
else{
	mtoout(srcel);
}
if(x==-1)mthestatus=eval("msub"+j).items[i].statustxt;
if(j==-1)mthestatus=mmenus[x].items[i].statustxt;
if(mthestatus!=""){
	musestatus=true;
	window.status=mthestatus;
}
clearTimeout(mpopTimer);
}
function mmenuitem_out(hassub){
toel = getReal(window.event.toElement, "className", "coolButton");
fromel = getReal(window.event.fromElement, "className", "coolButton");
if (toel == fromel) return;
srcel = getReal(window.event.srcElement, "className", "coolButton");
if(!hassub)mnochange(srcel);
if(musestatus)window.status="";
mpopOut();
}
function mmenuitem_down(){

	srcel = getReal(window.event.srcElement, "className", "coolButton");
	mtoin(srcel);
	misdown=true;
}
function mmenuitem_up(){
	srcel = getReal(window.event.srcElement, "className", "coolButton");
	mtoout(srcel);
	misdown=false;
}
function mexec3(j,i)
{
	var cmd;
	if(eval("msub"+j).items[i].target=="blank"){
  		cmd = "window.open('"+eval("msub"+j).items[i].command+"')";
	}else{
  		cmd = eval("msub"+j).items[i].target+".location=\""+eval("msub"+j).items[i].command+"\"";
	}
	eval(cmd);
}
function mexec2(x)
{
	var cmd;
	if(mmenus[x].target=="blank"){
  		cmd = "window.open('"+mmenus[x].command+"')";
	}else{
  		cmd = mmenus[x].target+".location=\""+mmenus[x].command+"\"";
	}
	eval(cmd);
}
function mexec(x,i)
{
	var cmd;
	if(mmenus[x].items[i].target=="blank"){
  		cmd = "window.open('"+mmenus[x].items[i].command+"')";
	}else{
  		cmd = mmenus[x].items[i].target+".location=\""+mmenus[x].items[i].command+"\"";
	}
	eval(cmd);
}
function mbody_click()
{

	if (misShow)
	{
		srcel = getReal(window.event.srcElement, "className", "coolButton");
		for(var x=0;x<=mmenus.length;x++){
			if(srcel.id=="mMenu"+x)
			return;
		}
		for(x=1;x<=mnumberofsub;x++)
		{
			if(srcel.id=="mp"+x)
			return;
		}
		mallhide();
	}
}
document.onclick=mbody_click;

function mwritetodocument()
{
	 var mwb=1;
	 var stringx='<div id="mposflag" style="position:absolute;"></div><table align=center id=mmenutable border=0 cellpadding=3 cellspacing=2 width='+mmenuwidth+' height='+mmenuheight+' bgcolor='+mmenucolor+
	 ' onselectstart="event.returnValue=false"'+
	 ' style="cursor:'+mcursor+';'+mfonts+
	 ' border-left: '+mwb+'px solid '+mmenuoutbordercolor+';'+
	 ' border-right: '+mwb+'px solid '+mmenuinbordercolor+'; '+
	 'border-top: '+mwb+'px solid '+mmenuoutbordercolor+'; '+
	 'border-bottom: '+mwb+'px solid '+mmenuinbordercolor+'; padding:0px"><tr>';
	 for(var x=0;x<mmenus.length;x++)
	 {
		var thismenu=mmenus[x];
		var imgsize="";
		if(thismenu.sizex!="0"||thismenu.sizey!="0")imgsize=" width="+thismenu.sizex+" height="+thismenu.sizey;
		var ifspace="";
		if(thismenu.caption!="")ifspace=" ";
		stringx += "<td nowrap class=coolButton id=mMenu"+x+" style='border: "+mitemedge+"px solid "+mmenucolor+
		"' width="+mmenuunitwidth+"px onmouseover=mmenu_over(mmenudiv"+x+
		//"' onmouseover=mmenu_over(mmenudiv"+x+
		","+x+") onmouseout=mmenu_out("+x+
		") onmousedown=mmenu_down(mmenudiv"+x+","+x+")";
		if(thismenu.command!=""){
		  stringx += " onmouseup=mmenu_up();mexec2("+x+");";
		}else{
		  stringx += " onmouseup=mmenu_up()";
		}
		if(thismenu.pos=="0"){
		  stringx += " align=center><img align=absmiddle src='"+thismenu.img+"'"+imgsize+">"+ifspace+thismenu.caption+"</td>";	
		}else if(thismenu.pos=="1"){
		  stringx += " align=center>"+thismenu.caption+ifspace+"<img align=absmiddle src='"+thismenu.img+"'"+imgsize+"></td>";	
		}else if(thismenu.pos=="2"){
		  stringx += " align=center background='"+thismenu.img+"'> "+thismenu.caption+" </td>";	
		}else{
		  stringx += " align=center> "+thismenu.caption+"</td>";
		}
		stringx += "";
	}
	stringx+="<td width=* align=right>操作员：<%=ctx.getUserName()%>&nbsp; </td></tr></table>";
                     
                     
	for(var x=0;x<mmenus.length;x++)
	{
		thismenu=mmenus[x];

		stringx+='<div id=mmenudiv'+x+
		' style="cursor:'+mcursor+';position:absolute;'+
		'width:'+mmenuitemwidth+'px; z-index:'+(x+100);
		if(mmenuinbordercolor!=mmenuoutbordercolor&&msubedge=="0")
		{
			stringx+=';border-left: 1px solid '+mmidoutcolor+
			';border-top: 1px solid '+mmidoutcolor;
		}
		stringx+=';border-right: 1px solid '+mmenuinbordercolor+
		';border-bottom: 1px solid '+mmenuinbordercolor+';visibility:hidden" onselectstart="event.returnValue=false">\n'+
		'<table  width="100%" border="0" height="100%" align="center" cellpadding="0" cellspacing="2" '+
		'style="'+mfonts+' border-left: 1px solid '+mmenuoutbordercolor;
		if(mmenuinbordercolor!=mmenuoutbordercolor&&msubedge=="0")
		{
			stringx+=';border-right: 1px solid '+mmidincolor+
			';border-bottom: 1px solid '+mmidincolor;
		}
		stringx+=';border-top: 1px solid '+mmenuoutbordercolor+
		';padding: 4px" bgcolor='+mmenucolor+'>\n';
		for(var i=0;i<thismenu.items.length;i++)
		{
			var thismenuitem=thismenu.items[i];
			var imgsize="";
			if(thismenuitem.sizex!="0"||thismenuitem.sizey!="0")imgsize=" width="+thismenuitem.sizex+" height=25"+thismenuitem.sizey;
			var ifspace="";
			if(thismenu.caption!="")ifspace=" ";
			if(thismenuitem.hasc!=null)
			{
				stringx += "<tr><td id=mp"+thismenuitem.hasc+" class=coolButton style='border: "+mitemedge+"px solid "+mmenucolor+
				"' width=100% onmouseout=mmenuitem_out(true) onmouseover=\"mmenuitem_over(mmenudiv"+x+
				",'"+thismenuitem.hasc+"',"+x+",-1,"+i+");mshowsubmenu(msubmenudiv"+thismenuitem.hasc+",mp"+thismenuitem.hasc+",mmenudiv"+x+");\""+
				"><table id=mitem"+thismenuitem.hasc+" cellspacing='0' cellpadding='0' border='0' width='100%' style='"+mfonts+"'><tr><td ";
				if(thismenuitem.pos=="0")
				{
					stringx += "><img align=absmiddle src='"+thismenuitem.img+"'"+imgsize+">"+ifspace+thismenuitem.caption+"</td><td";	
				}else if(thismenuitem.pos=="1"){
					stringx += ">"+thismenuitem.caption+ifspace+"<img align=absmiddle src='"+thismenuitem.img+"'"+imgsize+"></td><td";
				}else if(thismenuitem.pos=="2"){
					stringx += "background='"+thismenuitem.img+"'>"+thismenuitem.caption+"</td><td background='"+thismenuitem.img+"'";	
				}else{
					stringx += ">"+thismenuitem.caption+"</td><td";
				}
				stringx += " align=right width='1'><font face='Webdings' style='font-size: 6pt'>4</font></td></tr></table></td></tr>\n";                     		
			}
			else if(!thismenuitem.isline)
			{
				stringx += "<tr><td class=coolButton style='border: "+mitemedge+"px solid "+mmenucolor+
				"' width=100% height=25px onmouseover=\"mmenuitem_over(mmenudiv"+x+
				",false,"+x+",-1,"+i+");\" onmouseout=mmenuitem_out() onmousedown=mmenuitem_down() onmouseup=";
				stringx += "mmenuitem_up();mexec("+x+","+i+"); ";
				if(thismenuitem.pos=="0")
				{
					stringx += "><img align=absmiddle src='"+thismenuitem.img+"'"+imgsize+">"+ifspace+thismenuitem.caption+"</td></tr>";	
				}else if(thismenuitem.pos=="1"){
					stringx += ">"+thismenuitem.caption+ifspace+"<img align=absmiddle src='"+thismenuitem.img+"'"+imgsize+"></td></tr>";	
				}else if(thismenuitem.pos=="2"){
					stringx += "background='"+thismenuitem.img+"'>"+thismenuitem.caption+"</td></tr>";	
				}else{
					stringx += ">"+thismenuitem.caption+"</td></tr>";
				}
			}else{
				stringx+='<tr><td height="1" background="hr.gif" onmousemove="clearTimeout(mpopTimer);"><img height="1" width="1" src="none.gif" border="0"></td></tr>\n';
			}
		}
		stringx+='</table>\<iframe class="menu_iframe" width='+mmenuitemwidth+' height='+(2+thismenu.items.length*(mmenuheight+2))+'></iframe></div>';				
	}
                
	for(var j=1;j<=mnumberofsub;j++)
	{     
		thisitem=eval("msub"+j);
		stringx+='<div id=msubmenudiv'+j+' style="tag:'+thisitem.level+';cursor:'+mcursor+';position:absolute;'+'width:'+mmenuitemwidth+'px; z-index:'+(j+200);
		if(mmenuinbordercolor!=mmenuoutbordercolor&&msubedge=="0")
		{
			stringx+=';border-left: 1px solid '+mmidoutcolor+
			';border-top: 1px solid '+mmidoutcolor;
		}
		stringx+=';border-right: 1px solid '+mmenuinbordercolor+
		';border-bottom: 1px solid '+mmenuinbordercolor+';visibility:hidden" onselectstart="event.returnValue=false">\n'+
		'<table  width="100%" border="0" height="100%" align="center" cellpadding="0" cellspacing="2" '+
		'style="'+mfonts+' border-left: 1px solid '+mmenuoutbordercolor;
		if(mmenuinbordercolor!=mmenuoutbordercolor&&msubedge=="0")
		{
			stringx+=';border-right: 1px solid '+mmidincolor+
			';border-bottom: 1px solid '+mmidincolor;
		}
		stringx+=';border-top: 1px solid '+mmenuoutbordercolor+';padding: 4px" bgcolor='+mmenucolor+'>\n';
		for(var i=0;i<thisitem.items.length;i++)
		{
			var thismenuitem=thisitem.items[i];
			var imgsize="";
			if(thismenuitem.sizex!="0"||thismenuitem.sizey!="0")imgsize=" width="+thismenuitem.sizex+" height="+thismenuitem.sizey;
			var ifspace="";
			if(thismenu.caption!="")ifspace=" ";
			if(thismenuitem.hasc!=null)
			{
				stringx += "<tr><td id=mp"+thismenuitem.hasc+" class=coolButton style='border: "+mitemedge+"px solid "+mmenucolor+
				"' width=100% onmouseout=mmenuitem_out(true) onmouseover=\"mmenuitem_over(msubmenudiv"+j+
				",'"+thismenuitem.hasc+"',-1,"+j+","+i+");mshowsubmenu(msubmenudiv"+thismenuitem.hasc+",mp"+thismenuitem.hasc+",msubmenudiv"+j+");\""+
				"><table id=mitem"+thismenuitem.hasc+" cellspacing='0' cellpadding='0' border='0' width='100%' style='"+mfonts+"'><tr><td ";
				if(thismenuitem.pos=="0"){
					stringx += "><img align=absmiddle src='"+thismenuitem.img+"'"+imgsize+">"+ifspace+thismenuitem.caption+"</td><td";	
				}else if(thismenuitem.pos=="1"){
					stringx += ">"+thismenuitem.caption+ifspace+"<img align=absmiddle src='"+thismenuitem.img+"'"+imgsize+"></td><td";
				}else if(thismenuitem.pos=="2"){
					stringx += "background='"+thismenuitem.img+"'>"+thismenuitem.caption+"</td><td background='"+thismenuitem.img+"'";	
				}else{
					stringx += ">"+thismenuitem.caption+"</td><td";
				}
				stringx += " align=right width='1'><font face='Webdings' style='font-size: 6pt'>4</font></td></tr></table></td></tr>\n";                     		
			}
			else if(!thismenuitem.isline)
			{
				stringx += "<tr><td class=coolButton style='border: "+mitemedge+"px solid "+mmenucolor+
				"' width=100% height=15px onmouseover=\"mmenuitem_over(msubmenudiv"+j+
				",false,-1,"+j+","+i+");\" onmouseout=mmenuitem_out() onmousedown=mmenuitem_down() onmouseup=";
				stringx += "mmenuitem_up();mexec3("+j+","+i+"); ";
				if(thismenuitem.pos=="0")
				{
					stringx += "><img align=absmiddle src='"+thismenuitem.img+"'"+imgsize+">"+ifspace+thismenuitem.caption+"</td></tr>";	
				}else if(thismenuitem.pos=="1"){
					stringx += ">"+thismenuitem.caption+ifspace+"<img align=absmiddle src='"+thismenuitem.img+"'"+imgsize+"></td></tr>";	
				}else if(thismenuitem.pos=="2"){
					stringx += "background='"+thismenuitem.img+"'>"+thismenuitem.caption+"</td></tr>";	
				}else{
					stringx += ">"+thismenuitem.caption+"</td></tr>";
				}
			}else{
				stringx+='<tr><td height="1" background="hr.gif" onmousemove="clearTimeout(mpopTimer);"><img height="1" width="1" src="none.gif" border="0"></td></tr>\n';
			}
		}	
		stringx+='</table>\n</div>';
	}
	document.write("<div align='left'>"+stringx+"</div>");
}

<%

	for(int i=1;i<=ctx.getMenu().size();i++)
	{
		String m[]=(String[])ctx.getMenu().get(i-1);
		out.println("mpmenu"+m[0]+"=new mMenu(' "+m[1]+" ','','self','','','','');");
		for(int j=1;j<=ctx.getMenusub().size();j++)
		{
			String ms[]=(String[])ctx.getMenusub().get(j-1);
			if(ms[3].equals(m[0]))
			{
				out.println("mpmenu"+m[0]+".addItem(new mMenuItem('"+ms[1]+"','"+ms[2]+"','self',false,'',null,'','','',''));");					
			}
	
		}
	}
%>	
mwritetodocument();
</script>


