// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportBillSearchAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.CaiWu;
import mediastore.dao.GoodsImportInfo;
import mediastore.user.DepartManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.ImportBillForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class ImportBillSearchAction extends AbstractAction
{

    public ImportBillSearchAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
	    	ImportBillForm gif=(ImportBillForm)actionContext.getForm();
			if(gif==null)
				gif=new ImportBillForm();
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
			if(!ctx.isIfview())
				gif.setDeptid(ctx.getDeptid());
			GoodsImportInfo giinfo = new GoodsImportInfo();
			int curpage = oConvert.getInt(actionContext.getParameter("curpage"),1);
			String importTime1="";
			if(actionContext.getParameter("searchbutton")==null)
	            importTime1 = oConvert.FormDate(new java.util.Date(), "yyyy-MM-01 00:00:00");
			else
				importTime1=oConvert.getString(actionContext.getParameter("importtime1"),"");
	        String importTime2 = actionContext.getParameter("importtime2");
	        if(importTime2 == null)
	            importTime2 = "";
	        String paymentTime1 = actionContext.getParameter("paymenttime1");
	        if(paymentTime1 == null)
	        	paymentTime1 = "";
	        String paymentTime2 = actionContext.getParameter("paymenttime2");
	        if(paymentTime2 == null)
	        	paymentTime2 = "";
	        String ifpay = oConvert.getString(actionContext.getParameter("ifpay"),"");
	        String kind = oConvert.getString(actionContext.getParameter("kind"),"0");
	    	String orderby = oConvert.getString(actionContext.getParameter("orderby"),"importTime desc");
	        
	        ResultsetList ibsri = giinfo.getBillListByRule(gif,importTime1,importTime2,paymentTime1,paymentTime2,curpage,ifpay,orderby,kind);
	        List billIdList = giinfo.getBillIdList(ctx.getDeptid());
	        java.util.List FactList = giinfo.getFactList(gif.getZhujima());
	        List PersonList = giinfo.getPersonsList(ctx.getDeptid());
	        DepartManager dm=new DepartManager();
	        java.util.List departList=dm.getDepartList("id");
	        CaiWu cw=new CaiWu();
	        List fkList=cw.getFuKuanTypeList(1);
	        actionContext.getRequest().setAttribute("billIdList", billIdList);
	        actionContext.getRequest().setAttribute("FactList", FactList);
	        actionContext.getRequest().setAttribute("PersonList", PersonList);
	        actionContext.getRequest().setAttribute("departList", departList);
	        actionContext.getRequest().setAttribute("fkList", fkList);
	        actionContext.getRequest().setAttribute("ibsri", ibsri);
	        actionContext.getRequest().setAttribute("gif", gif);
	        actionContext.getRequest().setAttribute("importTime1", importTime1);
	        actionContext.getRequest().setAttribute("importTime2", importTime2);
	        actionContext.getRequest().setAttribute("paymentTime1", paymentTime1);
	        actionContext.getRequest().setAttribute("paymentTime2", paymentTime2);
	        actionContext.getRequest().setAttribute("ifpay", ifpay);
	        actionContext.getRequest().setAttribute("orderby", orderby);
	        actionContext.getRequest().setAttribute("kind", kind);
	        return actionContext.getMapping().findForward("ImportBillSearch");
    	}
    	catch(Exception e)
        {
         	ErrorMsgFB ef=new ErrorMsgFB();
 			ef.setSource(oConvert.filterSQL(e.getMessage()));
 			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
 	        return actionContext.getMapping().findForward("Errors");
        }
    }
}
