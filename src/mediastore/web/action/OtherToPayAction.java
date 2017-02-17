// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportGoodsBillShowAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.CaiWu;
import mediastore.dao.MoneyBank;
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

public class OtherToPayAction extends AbstractAction
{

    public OtherToPayAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
		{
    		String param = oConvert.getString(actionContext.getParameter("param"),"");
    		int curpage = oConvert.getInt(actionContext.getParameter("curpage"),1);
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
			OtherToPayForm fi=(OtherToPayForm)actionContext.getForm();
			if(fi==null)
				fi=new OtherToPayForm();
			
			fi.setDeptid(ctx.getDeptid());
			CaiWu cw=new CaiWu();
			MoneyBank mb=new MoneyBank();
	    	if(param.equals("submit"))
	    	{
	    		fi.setOperPerson(ctx.getUserName());
	    		if(fi.getAcctype()==0)
	    			throw new Exception("帐户不能为空");
	    		if(fi.getKind()==0)
	    			throw new Exception("操作类型不能为空");
	    		if(fi.getJine()<=0)
	    			throw new Exception("金额必须大于0");
	    		int billid=mb.addOtherToPay(fi);
	    		actionContext.getResponse().sendRedirect("otherPayItemView.do?billId="+billid+"&deptid="+fi.getDeptid()+"&ifprint=1");
		        return null;
	    	}
	    	
	    	ResultsetList ys=null;
	        ys = cw.getOtherToPayList(fi.getFactory(),ctx.getDeptid(),fi.getType(),curpage);
	    	List acc=mb.getAccountList(ctx.getDeptid());
	        FactoryInfoForm fif=new FactoryInfoForm();
	        FactoryManager fum = new FactoryManager();
	        fif.setKind("0001");
	        String zhujima = oConvert.getString(actionContext.getParameter("zhujima"),"");
	        fif.setZhujima(zhujima);
	        java.util.List FactList = fum.getFactList("a.name",fif);
	        
	        actionContext.getRequest().setAttribute("acc", acc);
	        actionContext.getRequest().setAttribute("ys", ys);
	        actionContext.getRequest().setAttribute("fi", fi);
	        actionContext.getRequest().setAttribute("zhujima", zhujima);
	        actionContext.getRequest().setAttribute("FactList", FactList);
	        return actionContext.getMapping().findForward("OtherToPayAdd");
		}
    	catch(Exception e)
		{
			ErrorMsgFB ef=new ErrorMsgFB();
			ef.setSource(e.getMessage());
			ef.seturl("otherToPay.do");
			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
	        return actionContext.getMapping().findForward("Errors");
		}
    }
}
