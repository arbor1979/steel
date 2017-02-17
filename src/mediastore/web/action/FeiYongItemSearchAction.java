// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportBillSearchAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.FeiYongInput;
import mediastore.dao.MoneyBank;
import mediastore.rule.FeiYongItemSearchRule;
import mediastore.user.DepartManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.FeiYongGoodsForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class FeiYongItemSearchAction extends AbstractAction
{

    public FeiYongItemSearchAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
    		FeiYongGoodsForm fyf=(FeiYongGoodsForm)actionContext.getForm();
			if(fyf==null)
				fyf=new FeiYongGoodsForm();
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
			if(!ctx.isIfview())
				fyf.setDeptid(ctx.getDeptid());
	        int curpage = oConvert.getInt((String)actionContext.getParameter("curpage"),1);
	        String orderStr = oConvert.getString(actionContext.getParameter("orderstr"),"a.createtime desc");
	        
	        String createtime1 ="";
	        if(actionContext.getParameter("searchbutton")==null)
	        	createtime1=oConvert.FormDate(new java.util.Date(), "yyyy-MM-01 00:00:00");
			else
				createtime1=oConvert.getString(actionContext.getParameter("importtime1"),"");
	        String createtime2 = oConvert.getString(actionContext.getParameter("importtime2"),"");
	        String paymenttime1 = oConvert.getString(actionContext.getParameter("paymenttime1"),"");
	        String paymenttime2 = oConvert.getString(actionContext.getParameter("paymenttime2"),"");
	        String oper = oConvert.getString(actionContext.getParameter("oper"),"");
	        

	        FeiYongItemSearchRule fbsr= new FeiYongItemSearchRule();
	        fbsr.setFyf(fyf);
	        fbsr.setCurpage(curpage);
	        fbsr.setOrderStr(orderStr);
	        fbsr.setCreatetime1(createtime1);
	        fbsr.setCreatetime2(createtime2);
	        fbsr.setPaymenttime1(paymenttime1);
	        fbsr.setPaymenttime2(paymenttime2);
	        fbsr.setOper(oper);
	        DepartManager dm=new DepartManager();
	        java.util.List departList=dm.getDepartList("id");
	        MoneyBank cw=new MoneyBank();
	    	List acc=cw.getAccountList(ctx.getDeptid());
	        FeiYongInput fyi=new FeiYongInput();
	        java.util.List BillList=fyi.getBillIdList(fyf.getKind());
	        java.util.List UserList=fyi.getCreatePersonList(ctx.getDeptid());
	        ResultsetList ibsri =  fyi.getItemListByRule(fbsr);
	        actionContext.getRequest().setAttribute("fbsr", fbsr);
	        actionContext.getRequest().setAttribute("departList", departList);
	        actionContext.getRequest().setAttribute("UserList", UserList);
	        actionContext.getRequest().setAttribute("BillList", BillList);
	        actionContext.getRequest().setAttribute("ibsri", ibsri);
	        actionContext.getRequest().setAttribute("acc", acc);
	        return actionContext.getMapping().findForward("FeiYongItemSearch");
	
    	}
    	catch(Exception e)
        {
         	ErrorMsgFB ef=new ErrorMsgFB();
 			ef.setSource(e.getMessage());
 			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
 	        return actionContext.getMapping().findForward("Errors");
        }
    }
}
