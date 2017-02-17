// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ExportGoodsBillShowAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.GoodsInfo;
import mediastore.dao.GoodsStoreGoods;
import mediastore.dao.GoodsStoreInfo;
import mediastore.user.StoreManager;
import mediastore.util.oConvert;
import mediastore.web.form.ChangeStoreBillForm;
import mediastore.web.form.ChangeStoreGoodsForm;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.GoodsInfoForm;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class ChangeStoreBillAction extends AbstractAction
{

    public ChangeStoreBillAction()
    {
    }
    //转仓单录入
    public ActionForward execute(ActionContext actionContext)
    {
    	
    	try
    	{
    		
    		String param = oConvert.getString(actionContext.getParameter("param"),"list");
    		int selectmode = oConvert.getInt((String)actionContext.getParameter("selectmode"),1);
    		ChangeStoreGoodsForm gif=(ChangeStoreGoodsForm)actionContext.getForm();
			if(gif==null)
				gif=new ChangeStoreGoodsForm();
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
	        gif.setCreatePerson(ctx.getUserName());
	        gif.setDeptid(ctx.getDeptid());
			GoodsStoreInfo geinfo = new GoodsStoreInfo();
			GoodsStoreGoods geg = new GoodsStoreGoods();
			
	    	if(param.equals("add"))
	    	{
	    		GoodsInfo gi=new GoodsInfo();
				GoodsInfoForm g=gi.getGoodsInfoByGoodsId(gif.getGoodsId());
	    		//插入入库明细表
				if(g==null)
					throw new Exception("不存在此编号的产品");
				if(gif.getFromstore()==0)
					gif.setFromstore(gif.getStoreId());
				double kucun=gi.getRepertoryAmount(gif.getGoodsId(),gif.getFromstore());
				gif.setCurRepertory(kucun);
				gif.setGoodsName(g.getGoodsTypeName());
				gif.setChangeNum(0);
				gif.setChangeAmount(0);
		        geg.insertNewGoodsRec(gif);
	    	}
	    	else if(param.equals("del"))
	    	{
	    		geg.deleteGoodsRec(gif);
	    	}
	    	else if(param.equals("updateNum"))
	    	{
	    		
    			if(gif.getChangeNum()<=0)
    				throw new Exception("数量必须大于0");
		        geg.updateChangeNum(gif);
	    	}
	    	else if(param.equals("updateAmount"))
	    	{
	    		
    			if(gif.getChangeAmount()<=0)
    				throw new Exception("重量必须大于0");
		        geg.updateChangeAmount(gif); 
	    	}
	    	else if(param.equals("delAll"))
	    	{
		        geg.deleteAllGoods(gif);
	    	}
	    	else if(param.equals("submit"))
	    	{
	    		geinfo.insertNewBillRec(gif);
	    		actionContext.getResponse().sendRedirect("changeStoreItemsView.do?billId="+gif.getBillId()+"&deptid="+gif.getDeptid()+"&ifprint=1");
	    		return null;
	    		
	    	}
	    	else if(param.equals("update"))
	    	{
	    		int fromstore=oConvert.getInt((String)actionContext.getParameter("fromstore"), 0);
	    		int tostore=oConvert.getInt((String)actionContext.getParameter("tostore"), 0);
	    		if(fromstore>0 && tostore>0 && fromstore==tostore)
	    			throw new Exception("转出、转入仓库不能相同");
	    		geinfo.UpdateBillRec(gif.getBillId(), gif.getDeptid(), fromstore, tostore, gif.getMemo());
	    	}
	    	int maxBillId = geinfo.getTempBillIDNum(ctx.getUserName(),gif.getDeptid());
	    	List goodsList = geg.getGoodsList(maxBillId,gif.getDeptid());
	    	StoreManager sm=new StoreManager();
	        List StoreList = sm.getDepartList("id",ctx.getDeptid());
	        ChangeStoreBillForm cbf=geinfo.getChangeStoreBillById(maxBillId,ctx.getDeptid());
	        actionContext.getRequest().setAttribute("cbf", cbf);
	        actionContext.getRequest().setAttribute("StoreList", StoreList);
	        actionContext.getRequest().setAttribute("goodsList", goodsList);
	        actionContext.getRequest().setAttribute("selectmode", String.valueOf(selectmode));
	        return actionContext.getMapping().findForward("ChangeStoreBill");
    	}
    	catch(Exception e)
        {
         	ErrorMsgFB ef=new ErrorMsgFB();
 			ef.setSource(e.getMessage());
 			ef.seturl("changeStoreBill.do");
 			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
 	        return actionContext.getMapping().findForward("Errors");
        }
    }
}
