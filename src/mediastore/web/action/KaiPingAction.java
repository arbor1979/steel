// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ExportGoodsBillShowAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.GoodsInfo;
import mediastore.dao.GoodsStoreGoods;
import mediastore.dao.GoodsStoreInfo;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.GoodsInfoForm;
import mediastore.web.form.KaiPingForm;
import mediastore.web.form.KaiPingInfoForm;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class KaiPingAction extends AbstractAction
{

    public KaiPingAction()
    {
    }
    //卷板开平
    public ActionForward execute(ActionContext actionContext)
    {
    	
    	try
    	{
    		
    		String param = oConvert.getString(actionContext.getParameter("param"),"list");
    		KaiPingForm gif=(KaiPingForm)actionContext.getForm();
			if(gif==null)
				gif=new KaiPingForm();
			if(gif.getKind()==0)
				gif.setKind(-1);
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
	        gif.setCreatePerson(ctx.getUserName());
	        gif.setDeptid(ctx.getDeptid());
	        GoodsStoreInfo gsi = new GoodsStoreInfo();
	        GoodsStoreInfo geinfo=new GoodsStoreInfo();
			GoodsStoreGoods geg = new GoodsStoreGoods();
			
	    	if(param.equals("add"))
	    	{
	    		if(gif.getFromGoodsId().length()==0)
	    			gif.setFromGoodsId(gif.getGoodsId());
	    		if(gif.getFromStore()==0)
	    			gif.setFromStore(gif.getStoreId());
	    		GoodsInfo gi=new GoodsInfo();
				GoodsInfoForm g=gi.getGoodsInfoByGoodsId(gif.getFromGoodsId());
				if(g==null)
					throw new Exception("不存在此编号"+gif.getFromGoodsId()+"的产品");
				double kucun=gi.getRepertoryAmount(gif.getFromGoodsId(),gif.getFromStore());
				gif.setCurRepertory(kucun);
				gif.setFromGoodsName(g.getGoodsTypeName());
		        geg.insertNewKaiPingGoods(gif);
	    	}
	    	else if(param.equals("del"))
	    	{
	    		geg.deleteKaiPingGoods(gif);
	    	}
	    	else if(param.equals("updateFromNum"))
	    	{
	    		
    			if(gif.getFromNum()<=0)
    				throw new Exception("数量必须大于0");
		        geg.updateFromNum(gif);
	    	}
	    	else if(param.equals("updateFromAmount"))
	    	{
	    		
    			if(gif.getFromAmount()<=0)
    				throw new Exception("重量必须大于0");
		        geg.updateFromAmount(gif); 
	    	}
	    	else if(param.equals("updateMoney"))
	    	{
    			if(gif.getJine()<0)
    				throw new Exception("金额必须大于等于0");
		        geg.updateJine(gif); 
	    	}
	    	else if(param.equals("updatePrice"))
	    	{
    			if(gif.getFromPrice()<0)
    				throw new Exception("单价必须大于等于0");
    			gif.setJine(oConvert.getRound(gif.getFromPrice()*gif.getFromAmount(),2));
		        geg.updateJine(gif); 
	    	}
	    	else if(param.equals("delAll"))
	    	{
		        geg.deleteAllKaiPingGoods(gif);
	    	}
	    	else if(param.equals("update"))
	    	{
	    		geinfo.UpdateKaiPingBillRec(gif.getBillid(),gif.getDeptid(),gif.getMemo());
	    	}
	    	else if(param.equals("submit"))
	    	{
	    		
	    		int billid=geinfo.insertNewKaiPingRec(gif,ctx.getDeptName());
	    		actionContext.getResponse().sendRedirect("kaiPingItemsView.do?billId="+billid+"&deptid="+gif.getDeptid()+"&ifprint=1");
	    		return null;
	    	}
	    	int maxBillId = gsi.getKaiPingTempBill(ctx.getUserName(),ctx.getDeptid());
	    	KaiPingInfoForm cbf=gsi.getKaiPingBillById(maxBillId, ctx.getDeptid());
	    	cbf.setKind(gif.getKind());
	    	gif.setBillid(maxBillId);
	    	List goodsList = geg.getKaiPingGoodsList(gif);
	    	actionContext.getRequest().setAttribute("cbf", cbf);
	        actionContext.getRequest().setAttribute("goodsList", goodsList);
	        return actionContext.getMapping().findForward("KaiPing");
    	}
    	catch(Exception e)
        {
         	ErrorMsgFB ef=new ErrorMsgFB();
 			ef.setSource(e.getMessage());
 			ef.seturl("kaiPing.do");
 			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
 	        return actionContext.getMapping().findForward("Errors");
        }
    }
}
