// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportGoodsBillShowAction.java

package mediastore.web.action;

import mediastore.dao.CaiWu;
import mediastore.user.DepartManager;
import mediastore.user.FactoryManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.FactoryInfoForm;
import mediastore.web.form.OtherToPayForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class OtherPaySearchAction extends AbstractAction
{

    public OtherPaySearchAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
		{
    		int curpage = oConvert.getInt(actionContext.getParameter("curpage"),1);
    		String param = oConvert.getString(actionContext.getParameter("param"),"");
    		String orderby = oConvert.getString(actionContext.getParameter("orderby"),"SUM(a.jine)");
    		String zhujima = oConvert.getString(actionContext.getParameter("zhujima"),"");
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
			OtherToPayForm fi=(OtherToPayForm)actionContext.getForm();
			if(fi==null)
				fi=new OtherToPayForm();
			if(!ctx.isIfview())
				fi.setDeptid(ctx.getDeptid());
			
	        CaiWu cw=new CaiWu();
	        if(param.equals("list"))
	        {
	        	FactoryManager fm=new FactoryManager();
	        	fi.setFactname(fm.getFactByID(fi.getFactory()).getName());
	        	ResultsetList ys = cw.getOtherToPayList(fi.getFactory(),fi.getDeptid(),fi.getType(),curpage);
	        	actionContext.getRequest().setAttribute("ys", ys);
	        	actionContext.getRequest().setAttribute("fi", fi);
	        	return actionContext.getMapping().findForward("OtherPaySearch");
	        }
	        
        	ResultsetList ys=cw.getOtherToPayGroupByFac(fi,zhujima,orderby,curpage);
        	FactoryManager fum = new FactoryManager();
	        FactoryInfoForm fif=new FactoryInfoForm();
	        fif.setKind("0001");
	        fif.setZhujima(zhujima);
	        java.util.List FactList = fum.getFactList("a.name",fif);
	        DepartManager dm=new DepartManager();
	        java.util.List departList=dm.getDepartList("id");
		   
	    	actionContext.getRequest().setAttribute("fi", fi);
	        actionContext.getRequest().setAttribute("FactList", FactList);
	        actionContext.getRequest().setAttribute("departList", departList);
	        actionContext.getRequest().setAttribute("orderby", orderby);
	        actionContext.getRequest().setAttribute("zhujima", zhujima);
	        actionContext.getRequest().setAttribute("ys", ys);
	        return actionContext.getMapping().findForward("OtherPayGroup");
	        
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
