// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportItemsViewAction.java

package mediastore.web.action;

import mediastore.dao.CaiWu;
import mediastore.util.oConvert;
import mediastore.web.form.AccountForm;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class InputOutputMoneyItemViewAction extends AbstractAction
{

    public InputOutputMoneyItemViewAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
	    	CaiWu fyi=new CaiWu();
	    	int deptid = oConvert.getInt(actionContext.getParameter("deptid"),0);
	    	int billId = oConvert.getInt(actionContext.getParameter("billId"),0);
	    	String ifprint=oConvert.getString(actionContext.getParameter("ifprint"),"0");
	    	AccountForm ibf=fyi.getZhuRuChouQuById(billId,deptid);
			actionContext.getRequest().setAttribute("billInfo", ibf);
			actionContext.getRequest().setAttribute("ifprint",ifprint);
			return actionContext.getMapping().findForward("InputOutputMoneyItemView");
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
