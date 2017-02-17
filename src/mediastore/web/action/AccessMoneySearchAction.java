// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportGoodsBillShowAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.MoneyBank;
import mediastore.user.DepartManager;
import mediastore.util.oConvert;
import mediastore.web.form.AccountForm;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.ResultsetList;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class AccessMoneySearchAction extends AbstractAction
{

    public AccessMoneySearchAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
		{
    		int curpage = oConvert.getInt(actionContext.getParameter("curpage"),1);
    		String orderby = oConvert.getString(actionContext.getParameter("orderby"),"a.opertime");
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
			String oper = oConvert.getString(actionContext.getParameter("oper"),"");
			AccountForm af=(AccountForm)actionContext.getForm();
			if(af==null)
				af=new AccountForm();
			if(!ctx.isIfview())
				af.setDeptid(ctx.getDeptid());
			String importTime1="";
			if(actionContext.getParameter("searchbutton")==null)
	            importTime1 = oConvert.FormDate(new java.util.Date(), "yyyy-MM-01 00:00:00");
			else
				importTime1=oConvert.getString(actionContext.getParameter("importtime1"),"");
	        String importTime2 = actionContext.getParameter("importtime2");
	        if(importTime2 == null)
	            importTime2 = "";
	        MoneyBank cw=new MoneyBank();
	    	ResultsetList tp=cw.getAccessMoneyList(af,oper,importTime1,importTime2,orderby,curpage);
	        List PersonList = cw.getAccessMoneyPersonsList(af.getDeptid());
	        DepartManager dm=new DepartManager();
	        java.util.List departList=dm.getDepartList("id");
	        List acc=cw.getAccountList(ctx.getDeptid());
	        actionContext.getRequest().setAttribute("acc", acc);
	    	actionContext.getRequest().setAttribute("tp", tp);
	    	actionContext.getRequest().setAttribute("af", af);
	    	actionContext.getRequest().setAttribute("oper",oper);
	        actionContext.getRequest().setAttribute("PersonList", PersonList);
	        actionContext.getRequest().setAttribute("departList", departList);
	        actionContext.getRequest().setAttribute("importTime1", importTime1);
	        actionContext.getRequest().setAttribute("importTime2", importTime2);
	        actionContext.getRequest().setAttribute("orderby", orderby);
	        return actionContext.getMapping().findForward("AccessMoneySearch");
		}
    	catch(Exception e)
		{
			ErrorMsgFB ef=new ErrorMsgFB();
			ef.setSource(e.getMessage());
			ef.seturl("accessMoneySearch.do");
			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
	        return actionContext.getMapping().findForward("Errors");
		}
    }
}
