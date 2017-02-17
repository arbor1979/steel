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

public class YeWuDayReportAction extends AbstractAction
{

    public YeWuDayReportAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
		{
    		String ifprint = oConvert.getString(actionContext.getParameter("ifprint"),"0");
    		String orderby=oConvert.getString(actionContext.getParameter("orderby"),"");
    		int showall = oConvert.getInt((String)actionContext.getParameter("showall"),0);
    		String importTime1="";
			if(actionContext.getParameter("searchbutton")==null)
	            importTime1 = oConvert.FormDate(new java.util.Date(), "yyyy-MM-dd 00:00:00");
			else
				importTime1=oConvert.getString(actionContext.getParameter("importtime1"),"");
			String importTime2="";
			if(actionContext.getParameter("searchbutton")==null)
	            importTime2 = oConvert.FormDate(new java.util.Date(), "yyyy-MM-dd 23:59:59");
			else
				importTime2=oConvert.getString(actionContext.getParameter("importtime2"),"");
	        
			int deptid = oConvert.getInt((String)actionContext.getParameter("deptid"),0);
    		UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
    		if(!ctx.isIfview() || deptid==0)
    			deptid=ctx.getDeptid();
			ReportInfo cw=new ReportInfo();
			List flowList=null;
	    	
	    	flowList=cw.yeWuDayReport(importTime1, importTime2,deptid,showall,orderby);
	    	
	    	DepartManager dm=new DepartManager();
	        java.util.List departList=dm.getDepartList("id");
	        actionContext.getRequest().setAttribute("departList", departList);
	    	actionContext.getRequest().setAttribute("flowList", flowList);
	    	actionContext.getRequest().setAttribute("ifprint", ifprint);
    		actionContext.getRequest().setAttribute("importTime1", importTime1);
    		actionContext.getRequest().setAttribute("importTime2", importTime2);
    		actionContext.getRequest().setAttribute("orderby", orderby);
    		actionContext.getRequest().setAttribute("showall", String.valueOf(showall));
    		actionContext.getRequest().setAttribute("deptid", String.valueOf(deptid));
    		if(ifprint.equals("0"))
    			return actionContext.getMapping().findForward("YeWuDayReport");
    		else
    			return actionContext.getMapping().findForward("YeWuDayReportPrint");
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
