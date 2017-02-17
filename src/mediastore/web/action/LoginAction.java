// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LoginAction.java

package mediastore.web.action;

import java.io.IOException;
import java.net.URLDecoder;

import mediastore.common.HttpSessionBinding;
import mediastore.user.UserManager;
import mediastore.util.CpuInfo;
import mediastore.util.StrUtility;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.ApplicationContext;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;


public class LoginAction extends AbstractAction
{

    public LoginAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
    		
    		actionContext.getResponse().setCharacterEncoding("gbk");
    		
    		UserInfoForm fi=(UserInfoForm)actionContext.getForm();
    		fi.setUserName(URLDecoder.decode(fi.getUserName(),"UTF-8"));
			if(fi==null)
				fi=new UserInfoForm();
	        UserManager um = new UserManager();
	        UserInfoForm  ctx=(UserInfoForm) actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
	        String action1=oConvert.getString(actionContext.getParameter("action"),"");
	        if(action1.equals("suc"))
	        {
	        	return actionContext.getMapping().findForward("LoginSuccess");
	        }
	        /*
	        String MacString=ApplicationContext.getMacAddress();
	        if(MacString==null || MacString.length()==0);
	        {
	        	String mac=StrUtility.qufan(StrUtility.getMacAddress());
	        	CpuInfo ci=new CpuInfo();
	        	int hz=(int)oConvert.getRound(ci.getCPUHz(),-1);
	        	//int hz=2490;
	        	mac=mac+Long.toHexString(hz).toUpperCase();
	        	java.io.File f=new java.io.File(actionContext.getRequest().getSession().getServletContext().getRealPath("\\jsp\\serialID.jsp"));
	        	if(!f.exists())
	        	{
	        		actionContext.getRequest().setAttribute("machinecode", mac);
        			actionContext.getRequest().setAttribute("errmsg","注册文件不存在");
        			return actionContext.getMapping().findForward("SoftReg");
	        	}
	        	else
	        	{
	        		java.io.FileReader fr=new java.io.FileReader(f);
	        		char[] cbuf=new char[256];
	        		fr.read(cbuf);
	  	  		    String regcode=String.valueOf(cbuf).trim();
	  	  		    int result=StrUtility.CheckRegcode(mac, hz, regcode);
	  	  		    if(result<0)
	  	  		    {
	  	  		    	String errmsg="";
	  	  		    	if(result==-1)
	  	  		    		errmsg="注册文件格式不正确";
	  	  		    	else if(result==-2)
	  	  		    		errmsg="注册码不正确";
	  	  		    	else if(result==-3)
	  	  		    		errmsg="您的软件已过期";
	  	  		    	actionContext.getRequest().setAttribute("machinecode", mac);
	  	  		    	actionContext.getRequest().setAttribute("errmsg",errmsg);
	  	  		    	return actionContext.getMapping().findForward("SoftReg");
	  	  		    }
	  	  		    else
	  	  		    {
	        			ApplicationContext.setMacAddress(mac);
	        			ApplicationContext.setDays(result);
	  	  		    }
	        			
	        	}
	        }
	        */
	        ApplicationContext.setDays(31);
	        ctx = um.login(fi.getUserName(), fi.getPassword(),actionContext.getRequest().getRemoteAddr());
	        if(ctx != null)
	        {
	        	actionContext.getSession().setAttribute(Globals.SESSION_CONTEXT, ctx);
	        	actionContext.getSession().setAttribute("BindingNotify",new HttpSessionBinding());
	        	actionContext.getSession().setAttribute("days", String.valueOf(ApplicationContext.getDays()));
	        	//return actionContext.getMapping().findForward("LoginSuccess");
	        	actionContext.getResponse().getWriter().print("ok");
	        	return null;
	        }
	        else
	        {
	        	actionContext.getResponse().getWriter().print("用户名或密码不正确！");
	        	return null;
	        }
    	}
    	catch(Exception e)
        {
    		try {
				actionContext.getResponse().getWriter().print(oConvert.filterSQL(e.getMessage()));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	return null;
         
        }
    }
}
