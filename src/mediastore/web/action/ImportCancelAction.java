// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportGoodsBillShowAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.GoodsImportGoods;
import mediastore.dao.GoodsImportInfo;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.GoodsImportGoodsInfo;
import mediastore.web.form.ImportBillForm;
import mediastore.web.form.ImportGoodsBillFB;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class ImportCancelAction extends AbstractAction
{

    public ImportCancelAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	
    	try
    	{
	    	String param = oConvert.getString(actionContext.getParameter("param"),"list");
	    	GoodsImportGoodsInfo gif=(GoodsImportGoodsInfo)actionContext.getForm();
			if(gif==null)
				gif=new GoodsImportGoodsInfo();
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
	        gif.setCreatePerson(ctx.getUserName());
	        gif.setDeptid(ctx.getDeptid());
	        
			GoodsImportInfo giinfo = new GoodsImportInfo();
			GoodsImportGoods gig = new GoodsImportGoods();
			
			if(param.equals("submit"))
	    	{
	    		giinfo.CancelImportBill(gif.getBillId(),ctx.getDeptid());
	    		gif.setBillId(0);
	    	}
	    	ImportBillForm ibf=giinfo.getImportBillById(gif.getBillId(),ctx.getDeptid());
			List billList=giinfo.getBillIdList(ctx.getDeptid(), ctx.getUserName());
			if(ibf.getPaymentTime().length()>0)
				throw new Exception("此进货单已付款，不能撤销，请进行退货处理");
			if(ibf.getYunFei()>0 && ibf.getPayShipTime()!=null)
				throw new Exception("此进货单运费运费已支付，不能撤销，请进行退货处理");
			if(ibf.getPayFpTime().length()>0)
				throw new Exception("此进货单发票已开具，不能撤销，请进行退货处理");
	        List goodsList = gig.getGoodsList(gif.getBillId(),ctx.getDeptid());
	        ImportGoodsBillFB igbfb = new ImportGoodsBillFB();
	        igbfb.setIbf(ibf);
	        igbfb.setGoodsList(goodsList);
	        igbfb.setFactList(billList);
	        actionContext.getRequest().setAttribute(Globals.REQUEST_IMPORTGOODSBILL, igbfb);
	        return actionContext.getMapping().findForward("ImportCancel");
    	}
    	catch(Exception e)
        {
         	ErrorMsgFB ef=new ErrorMsgFB();
 			ef.setSource(oConvert.filterSQL(e.getMessage()));
 			ef.seturl("importCancel.do");
 			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
 	        return actionContext.getMapping().findForward("Errors");
        }
    }
}
