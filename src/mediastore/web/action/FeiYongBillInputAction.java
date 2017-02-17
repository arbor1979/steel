// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportGoodsBillShowAction.java

package mediastore.web.action;

import java.net.URLDecoder;
import java.util.List;

import mediastore.dao.FeiYongInput;
import mediastore.dao.MoneyBank;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.FeiYongBillForm;
import mediastore.web.form.FeiYongGoodsForm;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class FeiYongBillInputAction extends AbstractAction
{

    public FeiYongBillInputAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
		{
    		
    		String param = oConvert.getString(actionContext.getParameter("param"),"");
    		int kind = oConvert.getInt(actionContext.getParameter("kind"),0);
    		UserInfoForm  ctx=(UserInfoForm) actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
    		FeiYongGoodsForm fyf=(FeiYongGoodsForm)actionContext.getForm();
			if(fyf==null)
				fyf=new FeiYongGoodsForm();
			fyf.setDeptid(ctx.getDeptid());
			FeiYongInput gig = new FeiYongInput();
			
	    	if(param.equals("add"))
	    	{
	    		gig.insertNewGoodsRec(fyf.getBillid(),ctx.getDeptid(),kind, fyf.getTypeid(), ctx.getUserName());
	    	}
	    	else if(param.equals("del"))
	    	{
		        gig.deleteGoodsRec(fyf.getId());
	    	}
	    	else if(param.equals("update"))
	    	{
	    		int acctype = oConvert.getInt(actionContext.getParameter("acctype"),0);
	    		String dt = oConvert.getString(actionContext.getParameter("payLimTime"),"");
	    		if(oConvert.parseDateShort(dt)==null)
	    			throw new Exception("日期格式不正确");
		        gig.updateBill(fyf.getBillid(),ctx.getDeptid(),kind, acctype,dt);
	    	}
	    	else if(param.equals("updateMoney"))
	    	{
		        gig.updateMoney(fyf.getId(),fyf.getJine()*kind);
	    	}
	    	else if(param.equals("updateMemo"))
	    	{
		        gig.updateMemo(fyf.getId(),URLDecoder.decode(fyf.getMemo(),"UTF-8"));
		        actionContext.getResponse().getWriter().print("ok");
		        return null;
	    	}
	    	else if(param.equals("updateJingShou"))
	    	{
		        gig.updateJingShou(fyf.getId(),URLDecoder.decode(fyf.getJingshouren(),"UTF-8"));
		        actionContext.getResponse().getWriter().print("ok");
		        return null;
	    	}
	    	else if(param.equals("delall"))
	    	{
		        gig.deleteAllGoods(fyf.getBillid(),ctx.getDeptid(),kind);
	    	}
	    	else if(param.equals("submit"))
	    	{
	    		String dt = oConvert.getString(actionContext.getParameter("payLimTime"),"");
	    		if(oConvert.parseDateShort(dt)==null)
	    			throw new Exception("日期格式不正确");
		        int newbillid=gig.submitCaiGouBill(fyf.getBillid(),ctx.getDeptid(),kind,dt,ctx.getUserName());
		        actionContext.getResponse().sendRedirect("feiYongItemView.do?billId="+newbillid+"&deptid="+ctx.getDeptid()+"&kind="+kind+"&ifprint=1");
		        return null;
	    	}
	    	MoneyBank cw=new MoneyBank();
	    	List acc=cw.getAccountList(fyf.getDeptid());
			int maxBillId = gig.getTempBillIDNum(ctx.getDeptid(),ctx.getUserName(),kind);
			FeiYongBillForm ibf=gig.getFeiYongBillById(maxBillId,ctx.getDeptid(),kind);
	        List goodsList = gig.getGoodsList(maxBillId,ctx.getDeptid(),kind);
	       	
	        actionContext.getRequest().setAttribute("ibf", ibf);
	        actionContext.getRequest().setAttribute("acc", acc);
	        actionContext.getRequest().setAttribute("goodsList", goodsList);
	        return actionContext.getMapping().findForward("FeiYongBillInput");
		}
    	catch(Exception e)
		{
			ErrorMsgFB ef=new ErrorMsgFB();
			ef.setSource(e.getMessage());
			ef.seturl("feiYongBillInput.do?kind="+actionContext.getParameter("kind"));
			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
	        return actionContext.getMapping().findForward("Errors");
		}
    }
}
