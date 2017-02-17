package mediastore.web.action;

import java.util.List;

import mediastore.dao.GoodsExportInfo;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.ExportBillForm;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class ExportBillHoldAction extends AbstractAction{

	public ExportBillHoldAction() {
		super();
	}

	public ActionForward execute(ActionContext actionContext) 
	{
		try
    	{
	    	String param = oConvert.getString(actionContext.getParameter("param"),"list");
	    	ExportBillForm gif=(ExportBillForm)actionContext.getForm();
			if(gif==null)
				gif=new ExportBillForm();
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
			gif.setSalesPerson(ctx.getUserName());
			gif.setDeptid(ctx.getDeptid());
			GoodsExportInfo giinfo = new GoodsExportInfo();
	        
	        if(param.equals("deel"))
	        {
	        	int factid=giinfo.deelExportHold(gif);
	        	actionContext.getResponse().sendRedirect("exportGoodsBill.do?factory="+factid);
	        	return null;
	        }
	        else if(param.equals("del"))
	        {
	        	giinfo.deleteExportBill(gif);
	        	actionContext.getResponse().sendRedirect("exportBillHold.do");
	        	return null;
	        }
	        else
	        {
		        List hbList = giinfo.getHoldBillList(gif.getSalesPerson());
		        actionContext.getRequest().setAttribute("hbList", hbList);
		        return actionContext.getMapping().findForward("ExportBillHold");
	        }
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
