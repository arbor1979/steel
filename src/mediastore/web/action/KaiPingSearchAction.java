// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportBillSearchAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.GoodsStoreInfo;
import mediastore.user.DepartManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.KaiPingForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class KaiPingSearchAction extends AbstractAction
{

    public KaiPingSearchAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
	    	String orderby = oConvert.getString(actionContext.getParameter("orderby"),"createtime desc");
	    	KaiPingForm gif=(KaiPingForm)actionContext.getForm();
			if(gif==null)
				gif=new KaiPingForm();
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
			if(!ctx.isIfview())
				gif.setDeptid(ctx.getDeptid());
			GoodsStoreInfo geinfo = new GoodsStoreInfo();
			DepartManager dm=new DepartManager();
	        java.util.List departList=dm.getDepartList("id");
			int curpage = oConvert.getInt(actionContext.getParameter("curpage"),1);
			
			String importTime1 ="";
	        if(actionContext.getParameter("searchbutton")==null)
				importTime1=oConvert.FormDate(new java.util.Date(), "yyyy-MM-01 00:00:00");
			else
				importTime1=oConvert.getString(actionContext.getParameter("importtime1"),"");
	        String importTime2 = actionContext.getParameter("importtime2");
	        if(importTime2 == null)
	            importTime2 = "";
	        
	        ResultsetList ibsri = geinfo.getKaiPingListByRule(gif,importTime1,importTime2,curpage,orderby);
	        List billIdList = geinfo.getKaiPingBillIdList(ctx.getDeptid());
	        List PersonList = geinfo.getKaiPingPersonsList(ctx.getDeptid());
	        
	        actionContext.getRequest().setAttribute("billIdList", billIdList);
	        actionContext.getRequest().setAttribute("PersonList", PersonList);
	        actionContext.getRequest().setAttribute("departList", departList);
	        actionContext.getRequest().setAttribute("ibsri", ibsri);
	        actionContext.getRequest().setAttribute("gif", gif);
	        actionContext.getRequest().setAttribute("importTime1", importTime1);
	        actionContext.getRequest().setAttribute("importTime2", importTime2);
	        actionContext.getRequest().setAttribute("orderby", orderby);
	        return actionContext.getMapping().findForward("KaiPingSearch");
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
