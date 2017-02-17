// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   AbstractAction.java

package mediastore.web.struts;

import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.ApplicationContext;
import mediastore.web.global.Globals;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

// Referenced classes of package mediastore.web.struts:
//            ActionContext

public abstract class AbstractAction extends Action
{

    public static final String LAST_FORWARD_KEY = "mediastore.web.struts.AbstractAction.LAST_FORWARD_KEY";

    public AbstractAction()
    {
    }

    public abstract ActionForward execute(ActionContext actioncontext);  

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        ActionForward forward = null;  
        ActionContext actionContext = new ActionContext(this, mapping, form, request, response, getLocale(request));
        HttpSession tmpsession = actionContext.getRequest().getSession(false);
        ErrorMsgFB ef=new ErrorMsgFB();
        if(tmpsession == null || tmpsession.getAttribute(Globals.SESSION_CONTEXT) == null)
        {
        	if(!mapping.getPath().equals("/login") && !mapping.getPath().equals("/logout"))
        	{
        		tmpsession.invalidate();
     			ef.seturl("index.htm");
     			ef.setSource("会话已过期，请重新登录");
     			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
     	        return actionContext.getMapping().findForward("Errors");
        	}           
        }
        else
        {
        	if(!mapping.getPath().equals("/login") && !mapping.getPath().equals("/logout"))
        	{
        		UserInfoForm fi=(UserInfoForm)tmpsession.getAttribute(Globals.SESSION_CONTEXT);
                Vector activeSessions = (Vector) ApplicationContext.getContext();
                HttpSession hs=null;
                UserInfoForm uif=null;
                for(int i=0;activeSessions!=null && i<activeSessions.size();i++)
                {
                	hs=(HttpSession)activeSessions.elementAt(i);
                	uif=(UserInfoForm)hs.getAttribute(Globals.SESSION_CONTEXT);
                	if(uif.getUserName().equals(fi.getUserName()))
                	{
                		Date dt=oConvert.parseDate(fi.getLogintime());
                		Date dt1=oConvert.parseDate(uif.getLogintime());
                		if(dt1.compareTo(dt)==0)
                			continue;
                		if(dt1.after(dt))
                		{
                			ef.seturl("logout.do");
                 			ef.setSource("帐号被其他人在IP为 " +uif.getRemoteip()+" 的电脑上登陆，您被迫下线");
                 			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
                 	        return actionContext.getMapping().findForward("Errors");
                		}
                		break;	
                	}
                }
            
        	}
        }
        
        	
        forward = execute(actionContext);  
		return forward;
        
    }
}
