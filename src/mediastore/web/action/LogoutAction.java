// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   LogoutAction.java

package mediastore.web.action;

import java.io.FileWriter;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import mediastore.user.DepartManager;
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

public class LogoutAction extends AbstractAction
{

    public LogoutAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
    		String param=oConvert.getString(actionContext.getParameter("param"),"");
    		if(param.equals("reg"))
    		{
    			String readcode=oConvert.getString(actionContext.getParameter("regcode"),"");
  	  		    String mac=StrUtility.qufan(StrUtility.getMacAddress());
  	  		    CpuInfo ci=new CpuInfo();
  	  		    int hz=(int)oConvert.getRound(ci.getCPUHz(),-1);
  	  		    
  	  		    int result=StrUtility.CheckRegcode(mac, hz, readcode);
	  		    if(result<0)
	  		    {
	  		    	if(result==-1)
	  		    		throw new Exception("注册码格式不正确");
	  		    	else if(result==-2)
	  		    		throw new Exception("注册码不正确");
	  		    	else if(result==-3)
	  		    		throw new Exception("注册码已过期");
	  		    }
        		else
        		{
        			 //写注册文件
        			 java.io.File f=new java.io.File(actionContext.getRequest().getSession().getServletContext().getRealPath("\\jsp\\serialID.jsp"));
	   			     if(f.exists())
	   			    	 f.delete();
	   			     f.createNewFile();
	   			     FileWriter fw=new FileWriter(f);
	   			     fw.write(readcode.toUpperCase());
	   			     fw.close();
	   			     //设置版本
	   			     String filename="topnew"+ApplicationContext.getVer()+".jpg";
	   			     java.io.File fi=new java.io.File(actionContext.getRequest().getSession().getServletContext().getRealPath("\\html\\"+filename));
	   			  	 java.io.File fo=new java.io.File(actionContext.getRequest().getSession().getServletContext().getRealPath("\\images\\topnew.jpg"));
	   			  	 if(fo.exists())
	   			  		 fo.delete();
	   			  	 StrUtility.CopyFile(fi,fo);
	   			  	 DepartManager dm=new DepartManager();
	   			  	 if(ApplicationContext.getVer()==3)
	   			  		 dm.updateToAdv(1);
	   			  	 else
	   			  		 dm.updateToAdv(0);
        		}
    		}
	        HttpSession session = actionContext.getSession();
	        UserInfoForm sessionUser = (UserInfoForm)session.getAttribute(Globals.SESSION_CONTEXT);
	        UserManager um = new UserManager();
	        if(sessionUser!=null)
	        	um.logout(sessionUser);
	        Vector activeSessions = (Vector) ApplicationContext.getContext();
	        if (activeSessions != null)
	        {
	        	activeSessions.remove(session);
	        	ApplicationContext.setContext(activeSessions);
	        }
	        java.util.Enumeration e = session.getAttributeNames();

	        while (e.hasMoreElements())
	        {
	         String s = (String)e.nextElement();
	         session.removeAttribute(s);
	        }
	        sessionUser=null;
	        actionContext.getResponse().sendRedirect("index.htm");
	        return null;
    	}
    	catch(Exception e)
	    {
	     	ErrorMsgFB ef=new ErrorMsgFB();
			ef.setSource(oConvert.filterSQL(e.getMessage()));
			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
		    return actionContext.getMapping().findForward("Errors");
	    }
        
    }
}
