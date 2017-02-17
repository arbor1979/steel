// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportItemsViewAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.GoodsImportGoods;
import mediastore.dao.GoodsImportInfo;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.ImportBillForm;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class ImportItemsViewAction extends AbstractAction
{

    public ImportItemsViewAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
	    	GoodsImportInfo giinfo = new GoodsImportInfo();
	    	GoodsImportGoods gig = new GoodsImportGoods();
	    	int billId = oConvert.getInt(actionContext.getParameter("billId"),0);
	    	int deptid = oConvert.getInt(actionContext.getParameter("deptid"),-2);
	    	String ifprint=oConvert.getString(actionContext.getParameter("ifprint"),"0");
	    	ImportBillForm ibf=new ImportBillForm();
	    	ibf=giinfo.getImportBillById(billId,deptid);
			List goodsList = gig.getGoodsList(billId,deptid);
			actionContext.getRequest().setAttribute("billInfo", ibf);
			actionContext.getRequest().setAttribute("billDetail", goodsList);
			actionContext.getRequest().setAttribute("ifprint",ifprint);
			UserInfoForm  ctx=(UserInfoForm) actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
			if(ctx.getPagesize()==0)
				return actionContext.getMapping().findForward("ImportItemsView");
			else if(ctx.getPagesize()==1)
				return actionContext.getMapping().findForward("ImportItemsViewLittle");
			else
				return null;
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
