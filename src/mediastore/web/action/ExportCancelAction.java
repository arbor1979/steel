// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportGoodsBillShowAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.GoodsExportGoods;
import mediastore.dao.GoodsExportInfo;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.ExportBillForm;
import mediastore.web.form.ExportGoodsBillFB;
import mediastore.web.form.GoodsExportGoodsInfo;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class ExportCancelAction extends AbstractAction
{

    public ExportCancelAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	
    	try
    	{
	    	String param = oConvert.getString(actionContext.getParameter("param"),"list");
	    	GoodsExportGoodsInfo gif=(GoodsExportGoodsInfo)actionContext.getForm();
			if(gif==null)
				gif=new GoodsExportGoodsInfo();
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
	        gif.setSalesPerson(ctx.getUserName());
	        gif.setDeptid(ctx.getDeptid());
	        
			GoodsExportInfo giinfo = new GoodsExportInfo();
			GoodsExportGoods gig = new GoodsExportGoods();
			
			if(param.equals("submit"))
	    	{
	    		giinfo.CancelExportBill(gif.getBillId(),ctx.getDeptid());
	    		gif.setBillId(0);
	    	}
	    	ExportBillForm ibf=giinfo.getExportBillById(gif.getBillId(),ctx.getDeptid());
			List billList=giinfo.getBillIdList(ctx.getDeptid(), ctx.getUserName());
			if(ibf.getPaymentTime().length()>0)
				throw new Exception("此销售单已付款，不能撤销，请进行退货处理");
			if(ibf.getYunFei()>0 && ibf.getPayShipTime()!=null)
				throw new Exception("此进货单运费运费已支付，不能撤销，请进行退货处理");
			if(ibf.getPayFpTime()!=null)
				throw new Exception("此进货单发票已开具，不能撤销，请进行退货处理");
	        List goodsList = gig.getGoodsList(gif.getBillId(),ctx.getDeptid());
	        ExportGoodsBillFB igbfb = new ExportGoodsBillFB();
	        igbfb.setEbf(ibf);
	        igbfb.setGoodsList(goodsList);
	        igbfb.setFactList(billList);
	        actionContext.getRequest().setAttribute(Globals.REQUEST_EXPORTGOODSBILL, igbfb);
	        return actionContext.getMapping().findForward("ExportCancel");
    	}
    	catch(Exception e)
        {
         	ErrorMsgFB ef=new ErrorMsgFB();
 			ef.setSource(oConvert.filterSQL(e.getMessage()));
 			ef.seturl("exportCancel.do");
 			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
 	        return actionContext.getMapping().findForward("Errors");
        }
    }
}
