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

public class EvaPriceHandleAction extends AbstractAction
{

    public EvaPriceHandleAction()
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
			
	    	
	    	if(param.equals("updatePrice"))
	    	{
	    		if(gif.getEvaPrice()<=0)
					throw new Exception("单价必须大于零");
		        gig.updateEvaPrice(gif);
	    	}
	    	else if(param.equals("updateMoney"))
	    	{
	    		if(gif.getEvaMoney()<=0)
					throw new Exception("金额必须大于零");
		        gig.updateEvaMoney(gif);
	    	}
	    	else if(param.equals("delall"))
	    	{
		        gig.deleteAllEvaPricce(gif);
		        gif.setBillId(0);
	    	}
	    	else if(param.equals("submit"))
	    	{
	    		giinfo.UpdateEvaPriceBill(gif.getBillId(),ctx.getDeptid(),ctx.getUserName());
	    		gif.setBillId(0);
	    	}
	    	ImportBillForm ibf=giinfo.getImportBillById(gif.getBillId(),ctx.getDeptid());
			if(ibf.getKind()<0)
				throw new Exception("退库单不能进行估计差处理");
			if(ibf.getPaymentTime().length()>0)
				throw new Exception("此入库单已付款，不能进行估价差处理");
			if(ibf.getIeva()==2)
				throw new Exception("此入库单已经进行过估价差处理");
			if(ibf.getConfirmFlage()!=null && ibf.getConfirmFlage().equals("3"))
				throw new Exception("此入库单已经作废");
	        List goodsList = gig.getGoodsList(gif.getBillId(),ctx.getDeptid());
	        List billIdList = giinfo.getEvaBillList(ctx.getDeptid());
	        ImportGoodsBillFB igbfb = new ImportGoodsBillFB();
	       	igbfb.setFactList(billIdList);
	        igbfb.setIbf(ibf);
	        igbfb.setGoodsList(goodsList);
	        actionContext.getRequest().setAttribute(Globals.REQUEST_IMPORTGOODSBILL, igbfb);
	        return actionContext.getMapping().findForward("EvaPriceHandle");
    	}
    	catch(Exception e)
        {
         	ErrorMsgFB ef=new ErrorMsgFB();
 			ef.setSource(oConvert.filterSQL(e.getMessage()));
 			ef.seturl("evaPriceHandle.do");
 			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
 	        return actionContext.getMapping().findForward("Errors");
        }
    }
}
