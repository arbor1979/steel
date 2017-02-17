// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportGoodsBillShowAction.java

package mediastore.web.action;

import mediastore.dao.GoodsExportInfo;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.ExportBillForm;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class ExportBillSubmitAction extends AbstractAction
{

    public ExportBillSubmitAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
    		String param = oConvert.getString(actionContext.getParameter("param"),"");
    		ExportBillForm gif=new ExportBillForm();
			gif=(ExportBillForm)actionContext.getForm();
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
	        gif.setSalesPerson(ctx.getUserName());
	        gif.setDeptid(ctx.getDeptid());
	        GoodsExportInfo giinfo = new GoodsExportInfo();
	        if(param.equals("update"))
	        {
	        	giinfo.UpdateBillRec(gif);
	        	actionContext.getResponse().sendRedirect("exportGoodsBill.do?kind="+gif.getKind());
	        	return null;
	        }
	        else if(param.equals("submit"))
	        {
	    		if(gif.getFactory()==0)
	    			throw new Exception("请选择客户");
	    		if(gif.getConfirmFlage().equals("1"))
	    		{
		    		if(gif.getFkType()==0)
		    			throw new Exception("请选择付款类型");
		    		if(gif.getFpType()==0)
		    			throw new Exception("请选择发票类型");
	    		}	
	    		if(gif.getYunFei()>0 && gif.getCarNo().equals(""))
	    			throw new Exception("请选择运输车辆牌号");
	    		
	    		giinfo.insertNewBillRec(gif);
	    		gif=giinfo.getExportBillById(gif.getBillId(),gif.getDeptid());
	        	actionContext.getResponse().sendRedirect("exportItemsView.do?billId="+gif.getBillId()+"&deptid="+gif.getDeptid()+"&ifprint=1");
	        }
	        return null;
    	}
    	catch(Exception e)
        {
         	ErrorMsgFB ef=new ErrorMsgFB();
 			ef.setSource(oConvert.filterSQL(e.getMessage()));
 			ef.seturl("exportGoodsBill.do?kind="+actionContext.getParameter("kind"));
 			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
 	        return actionContext.getMapping().findForward("Errors");
        }
    }
}
