// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportGoodsBillShowAction.java

package mediastore.web.action;

import mediastore.dao.GoodsImportInfo;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.ImportBillForm;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class ImportBillSubmitAction extends AbstractAction
{

    public ImportBillSubmitAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
    		String param = oConvert.getString(actionContext.getParameter("param"),"");
    		ImportBillForm gif=new ImportBillForm();
			gif=(ImportBillForm)actionContext.getForm();
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
	        gif.setCreatePerson(ctx.getUserName());
	        gif.setDeptid(ctx.getDeptid());
	        GoodsImportInfo giinfo = new GoodsImportInfo();
    		
	        if(param.equals("update"))
	        {
	        	giinfo.UpdateBillRec(gif);
	        	actionContext.getResponse().sendRedirect("importGoodsBill.do?kind="+gif.getKind());
	        }
	        else if(param.equals("submit"))
	        {
	        	if(gif.getPayLimTime().equals(""))
		        	gif.setPayLimTime(oConvert.FormDate(new java.util.Date(),"yyyy-MM-dd"));
	    		if(gif.getFactory()==0)
	    			throw new Exception("请选择供应商");
	    		if(gif.getFkType()==0)
	    			throw new Exception("请选择付款类型");
	    		if(gif.getFpType()==0)
	    			throw new Exception("请选择发票类型");
	    		gif.setIyunfei(ctx.isIfyunfei());
	    		giinfo.insertNewBillRec(gif);
	    		gif=giinfo.getImportBillById(gif.getBillId(),ctx.getDeptid());
	        	actionContext.getResponse().sendRedirect("importItemsView.do?billId="+gif.getBillId()+"&deptid="+gif.getDeptid()+"&ifprint=1");
        	
	    		
	        }
	        return null;
    	}
    	catch(Exception e)
        {
         	ErrorMsgFB ef=new ErrorMsgFB();
 			ef.setSource(oConvert.filterSQL(e.getMessage()));
 			ef.seturl("importGoodsBill.do?kind="+actionContext.getParameter("kind"));
 			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
 	        return actionContext.getMapping().findForward("Errors");
        }
    }
}
