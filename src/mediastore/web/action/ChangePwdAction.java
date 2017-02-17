// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   UserManageAction.java

package mediastore.web.action;

import mediastore.user.UserManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class ChangePwdAction extends AbstractAction
{

    public ChangePwdAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
	    	String param = oConvert.getString(actionContext.getParameter("param"),"");
	    	UserInfoForm gif=(UserInfoForm)actionContext.getForm();
			if(gif==null)
				gif=new UserInfoForm();
			UserManager um = new UserManager();
			
			if(param.equals("change"))
			{
				if(gif.getNewpwd().length()==0)
					throw new Exception("新密码不能为空！");
				um.changePwd(gif);
				actionContext.getRequest().setAttribute("suc", "1");
		        return actionContext.getMapping().findForward("ChangePwd");
			}
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
			gif=um.getUserbyID(ctx.getId());
			actionContext.getRequest().setAttribute("gif", gif);
	        return actionContext.getMapping().findForward("ChangePwd");
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
