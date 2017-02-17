// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   UserManageAction.java

package mediastore.web.action;

import java.net.URLDecoder;
import java.util.List;

import mediastore.dao.MoneyBank;
import mediastore.user.DepartManager;
import mediastore.util.oConvert;
import mediastore.web.form.AccountForm;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class AccountManageAction extends AbstractAction
{

    public AccountManageAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
	    	String param = oConvert.getString(actionContext.getParameter("param"),"list");
	    	AccountForm af=(AccountForm)actionContext.getForm();
			if(af==null)
				af=new AccountForm();
			MoneyBank sm = new MoneyBank();
	        DepartManager dm=new DepartManager();
	        java.util.List departList=dm.getDepartList("id");
	        UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
	        if(!ctx.isIfview())
	        	af.setDeptid(ctx.getDeptid());
			
			if (param.equals("update"))
			{
				af.setDeptid(ctx.getDeptid());
				af.setAccName(URLDecoder.decode(af.getAccName(),"UTF-8"));
				
				if(oConvert.isEmpty(af.getAccName()))
		        	throw new Exception("帐户名称不能为空！");
		        if(af.getId()==0)
		        {
		        	sm.insertNewAccount(af);
		        	af=new AccountForm();
		        }
		        else
		        {
		        	sm.updateAccount(af);
		        	actionContext.getResponse().getWriter().print("ok");
		        	return null;
		        }
			}
			else if(param.equals("del"))
			{
				af.setDeptid(ctx.getDeptid());
				sm.deleteAccount(af);
				af=new AccountForm();
			}
	    	List accList = sm.getAccountList(af.getDeptid());
	        actionContext.getRequest().setAttribute("accList", accList);
	        actionContext.getRequest().setAttribute("departList",departList);
	        actionContext.getRequest().setAttribute("af", af);
	        return actionContext.getMapping().findForward("AccountManage");
			
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
