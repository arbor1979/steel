// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportItemsViewAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.FeiYongInput;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.FeiYongBillForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class FeiYongItemViewAction extends AbstractAction
{

    public FeiYongItemViewAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
	    	FeiYongInput fyi=new FeiYongInput();
	    	int billId = oConvert.getInt(actionContext.getParameter("billId"),0);
	    	int deptid = oConvert.getInt(actionContext.getParameter("deptid"),0);
	    	int kind = oConvert.getInt(actionContext.getParameter("kind"),0);
	    	String ifprint=oConvert.getString(actionContext.getParameter("ifprint"),"0");
	    	FeiYongBillForm ibf=new FeiYongBillForm();
	    	ibf=fyi.getFeiYongBillById(billId,deptid,kind);
			List goodsList = fyi.getGoodsList(billId,deptid,kind);
			actionContext.getRequest().setAttribute("billInfo", ibf);
			actionContext.getRequest().setAttribute("billDetail", goodsList);
			actionContext.getRequest().setAttribute("ifprint",ifprint);
			return actionContext.getMapping().findForward("FeiYongItemView");
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
