// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ExportGoodsBillShowAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.GoodsCheckGoods;
import mediastore.dao.GoodsCheckInfo;
import mediastore.dao.GoodsInfo;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.GoodsCheckGoodsInfo;
import mediastore.web.form.GoodsInfoForm;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class CheckGoodsBillAction extends AbstractAction
{

    public CheckGoodsBillAction()
    {
    }
    //盘点单录入
    public ActionForward execute(ActionContext actionContext)
    {
    	
    	try
    	{
    		
    		String param = oConvert.getString(actionContext.getParameter("param"),"list");
    		GoodsCheckGoodsInfo gif=(GoodsCheckGoodsInfo)actionContext.getForm();
			if(gif==null)
				gif=new GoodsCheckGoodsInfo();
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
	        gif.setCreatePerson(ctx.getUserName());
	        gif.setDeptid(ctx.getDeptid());
			GoodsCheckInfo geinfo = new GoodsCheckInfo();
			GoodsCheckGoods geg = new GoodsCheckGoods();
			
	    	if(param.equals("add"))
	    	{
	    		GoodsInfo gi=new GoodsInfo();
				GoodsInfoForm g=gi.getGoodsInfoByGoodsId(gif.getGoodsId());
	    		//插入入库明细表
				if(g==null)
					throw new Exception("不存在此编号的产品");
				double kucun=gi.getRepertoryAmount(gif.getGoodsId(),gif.getStoreId());
				gif.setCurRepertoryAmount(kucun);
				gif.setGoodsName(g.getGoodsTypeName());
				gif.setCheckNum(0);
				gif.setCheckAmount(0);
		        geg.insertNewGoodsRec(gif);
	    	}
	    	else if(param.equals("del"))
	    	{
	    		geg.deleteGoodsRec(gif);
	    	}
	    	else if(param.equals("updateNum"))
	    	{
	    		
    			if(gif.getCheckNum()<=0)
    				throw new Exception("数量必须大于0");
		        geg.updateCheckNum(gif);
	    	}
	    	else if(param.equals("updateAmount"))
	    	{
	    		
    			if(gif.getCheckAmount()<=0)
    				throw new Exception("重量必须大于0");
		        geg.updateCheckAmount(gif); 
	    	}
	    	else if(param.equals("delAll"))
	    	{
	    		if(gif.getBillId()<=0)
					throw new Exception("单号不正确");
		        geg.deleteAllGoods(gif);
	    	}
	    	else if(param.equals("submit"))
	    	{
	    		GoodsCheckInfo gci=new GoodsCheckInfo();
	    		gci.insertNewBillRec(gif);
	    		actionContext.getResponse().sendRedirect("checkItemsView.do?billId="+gif.getBillId()+"&deptid="+gif.getDeptid()+"&ifprint=1");
	    		return null;
	    		
	    	}
	    	int maxBillId = geinfo.getTempBillIDNum(ctx.getUserName(),gif.getKind(),gif.getDeptid());
	    	List goodsList = geg.getGoodsList(maxBillId,gif.getDeptid());
	    	
	        actionContext.getRequest().setAttribute("goodsList", goodsList);
	        actionContext.getRequest().setAttribute("kind", String.valueOf(gif.getKind()));
	        actionContext.getRequest().setAttribute("maxBillId", String.valueOf(maxBillId));
	        return actionContext.getMapping().findForward("CheckGoodsBill");
    	}
    	catch(Exception e)
        {
         	ErrorMsgFB ef=new ErrorMsgFB();
 			ef.setSource(e.getMessage());
 			ef.seturl("checkGoodsBill.do?kind="+actionContext.getParameter("kind"));
 			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
 	        return actionContext.getMapping().findForward("Errors");
        }
    }
}
