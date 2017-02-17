// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportGoodsBillShowAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.ReportInfo;
import mediastore.user.DepartManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class RepertoryWarnAction extends AbstractAction
{

    public RepertoryWarnAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
		{
    		int deptid = oConvert.getInt((String)actionContext.getParameter("deptid"),0);
    		int days = oConvert.getInt((String)actionContext.getParameter("days"),7);
    		String orderby = oConvert.getString((String)actionContext.getParameter("orderby"),"eva");
    		UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
    		if(!ctx.isIfview() || deptid==0)
    			deptid=ctx.getDeptid();
			ReportInfo cw=new ReportInfo();
			List kcList=cw.getRepertoryWarnList(deptid,days,orderby);
	    	DepartManager dm=new DepartManager();
	        java.util.List departList=dm.getDepartList("id");
	    	actionContext.getRequest().setAttribute("kcList", kcList);
	    	actionContext.getRequest().setAttribute("departList", departList);
	    	actionContext.getRequest().setAttribute("orderby", orderby);
    		actionContext.getRequest().setAttribute("deptid", String.valueOf(deptid));
    		actionContext.getRequest().setAttribute("days", String.valueOf(days));
	        return actionContext.getMapping().findForward("RepertoryWarnEval");
		}
    	catch(Exception e)
		{
			ErrorMsgFB ef=new ErrorMsgFB();
			ef.setSource(e.getMessage());
			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
	        return actionContext.getMapping().findForward("Errors");
		}
    }
}
