// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportBillSearchAction.java

package mediastore.web.action;

import mediastore.dao.FeiYongInput;
import mediastore.rule.FeiYongBillSearchRule;
import mediastore.user.DepartManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.FeiYongBillForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;
import org.apache.struts.action.ActionForward;

public class FeiYongSearchAction extends AbstractAction
{

    public FeiYongSearchAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
    		FeiYongBillForm fyf=(FeiYongBillForm)actionContext.getForm();
			if(fyf==null)
				fyf=new FeiYongBillForm();
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
			if(!ctx.isIfview())
				fyf.setDeptid(ctx.getDeptid());
	        int curpage = oConvert.getInt((String)actionContext.getParameter("curpage"),1);
	        String orderStr = oConvert.getString(actionContext.getParameter("orderstr"),"a.createtime desc");
	        
	        FeiYongBillSearchRule fbsr= new FeiYongBillSearchRule();
	        fbsr.setFyf(fyf);
	        fbsr.setCurpage(curpage);
	        fbsr.setOrderStr(orderStr);
	        
	        DepartManager dm=new DepartManager();
	        java.util.List departList=dm.getDepartList("id");
	        FeiYongInput fyi=new FeiYongInput();
	        ResultsetList ibsri =  fyi.getBillListByRule(fbsr);
	        actionContext.getRequest().setAttribute("fbsr", fbsr);
	        actionContext.getRequest().setAttribute("departList", departList);
	        actionContext.getRequest().setAttribute("ibsri", ibsri);
	        return actionContext.getMapping().findForward("FeiYongSearch");
	
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
