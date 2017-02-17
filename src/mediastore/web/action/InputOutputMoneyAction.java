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

public class InputOutputMoneyAction extends AbstractAction
{

    public InputOutputMoneyAction()
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
	    		int billid=0;
	    		if(kind==2)
	    		{
	    			if(af.getAcctype()==0)
		    			throw new Exception("注入帐户不能为空");
		    		if(af.getJine()<=0)
		    			throw new Exception("金额必须大于0");
		    		double accold=oConvert.getDouble((String)actionContext.getParameter("acc"+af.getAcctype()),0);
		    		billid=cw.accessInMoney(af,accold);
	    		}
	    		else if(kind==3)
	    		{
	    			if(af.getAcctype()==0)
		    			throw new Exception("抽取帐户不能为空");
		    		if(af.getJine()<=0)
		    			throw new Exception("金额必须大于0");
		    		double accold=oConvert.getDouble((String)actionContext.getParameter("acc"+af.getAcctype()),0);
		    		billid=cw.accessOutMoney(af,accold);
	    		}
	    		actionContext.getResponse().sendRedirect("inputOutputMoneyItemView.do?billId="+billid+"&deptid="+af.getDeptid()+"&ifprint=1");
	    		return null;
	    		/*
	    		List acc=cw.getAccountList(ctx.getDeptid());
		        actionContext.getRequest().setAttribute("acc", acc);
	    		actionContext.getRequest().setAttribute("af", af);
	    		actionContext.getRequest().setAttribute("kind", String.valueOf(kind));
	    		return actionContext.getMapping().findForward("AccessMoneyResult");
	    		*/
	    	}
	    	List acc=cw.getAccountList(ctx.getDeptid());
	        actionContext.getRequest().setAttribute("acc", acc);
	        return actionContext.getMapping().findForward("InputOutputMoney");
		}
    	catch(Exception e)
		{
			ErrorMsgFB ef=new ErrorMsgFB();
			ef.setSource(e.getMessage());
			ef.seturl("inputOutputMoney.do");
			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
	        return actionContext.getMapping().findForward("Errors");
		}
    }
}
