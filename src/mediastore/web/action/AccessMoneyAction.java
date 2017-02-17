// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportGoodsBillShowAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.MoneyBank;
import mediastore.util.oConvert;
import mediastore.web.form.AccountForm;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class AccessMoneyAction extends AbstractAction
{

    public AccessMoneyAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
		{
    		String param = oConvert.getString(actionContext.getParameter("param"),"");
    		int kind = oConvert.getInt((String)actionContext.getParameter("kind"),0);
    		AccountForm af=(AccountForm)actionContext.getForm();
			if(af==null)
				af=new AccountForm();
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
			af.setDeptid(ctx.getDeptid());
			af.setOperPerson(ctx.getUserName());
			MoneyBank cw=new MoneyBank();
			
	    	if(param.equals("submit"))
	    	{
	    		if(kind==1)
	    		{
		    		if(af.getAccfrom()==0)
		    			throw new Exception("转出帐户不能为空");
		    		if(af.getAccto()==0)
		    			throw new Exception("转入帐户不能为空");
		    		if(af.getJine()<=0)
		    			throw new Exception("金额必须大于0");
		    		double accold=oConvert.getDouble((String)actionContext.getParameter("acc"+af.getAccfrom()),0);
		    		int billid=cw.accessSwitchMoney(af,accold);
		    		actionContext.getResponse().sendRedirect("accessMoneyItemView.do?billId="+billid+"&deptid="+af.getDeptid()+"&ifprint=1");
		    		return null;
	    		}
	    		List acc=cw.getAccountList(ctx.getDeptid());
		        actionContext.getRequest().setAttribute("acc", acc);
	    		actionContext.getRequest().setAttribute("af", af);
	    		actionContext.getRequest().setAttribute("kind", String.valueOf(kind));
	    		return actionContext.getMapping().findForward("AccessMoneyResult");
	    	}
	    	List acc=cw.getAccountList(ctx.getDeptid());
	        actionContext.getRequest().setAttribute("acc", acc);
	        return actionContext.getMapping().findForward("AccessMoney");
		}
    	catch(Exception e)
		{
			ErrorMsgFB ef=new ErrorMsgFB();
			ef.setSource(e.getMessage());
			ef.seturl("accessMoney.do");
			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
	        return actionContext.getMapping().findForward("Errors");
		}
    }
}
