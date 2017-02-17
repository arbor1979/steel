// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   UserManageAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.CaiWu;
import mediastore.dao.MoneyBank;
import mediastore.user.FactoryManager;
import mediastore.user.TruckManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.FactoryInfoForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.form.TruckInfoForm;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class YuShouKuanAction extends AbstractAction
{

    public YuShouKuanAction()
    {
    }

    @SuppressWarnings("unchecked")
	public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
	    	String param = oConvert.getString(actionContext.getParameter("param"),"");
	    	int kind= oConvert.getInt((String)actionContext.getParameter("kind"),1);
	    	int fktype= oConvert.getInt((String)actionContext.getParameter("fkType"),1);
			int factory=oConvert.getInt((String)actionContext.getParameter("factory"),0);
			int curpage= oConvert.getInt((String)actionContext.getParameter("curpage"),1);
			String zhujima = oConvert.getString(actionContext.getParameter("zhujima"),"");
			UserInfoForm  ctx=(UserInfoForm) actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
			CaiWu cw=new CaiWu();
			if(param.equals("yushou"))
			{
	        	double newpay=oConvert.getDouble((String)actionContext.getParameter("newpay"),0);
	        	int acctype= oConvert.getInt((String)actionContext.getParameter("acctype"),0);
	        	if(newpay==0)
	        		throw new Exception("预收预付金额不能为0");
	        	if(acctype==0)
	        		throw new Exception("请选择一个账户");
	        	int billid=cw.addPrepay(kind, factory,ctx.getDeptid(), newpay,ctx.getUserName(),fktype,acctype);
	        	actionContext.getResponse().sendRedirect("shouKuanItemView.do?billId="+billid+"&deptid="+ctx.getDeptid()+"&ifprint=1");
		        return null;
			}
			if(kind==1 || kind==-1)
			{
				FactoryManager fum = new FactoryManager();
				FactoryInfoForm fi=new FactoryInfoForm();
				if(kind==1)
					fi.setKind("011");
				else if(kind==-1)
					fi.setKind("100");
				fi.setZhujima(zhujima);
		        java.util.List FactList = fum.getFactList("a.name",fi);
		        actionContext.getRequest().setAttribute("FactList", FactList);
			}
			else
			{
				TruckManager um = new TruckManager();
				TruckInfoForm fi=new TruckInfoForm();
				java.util.List truckList = um.getTruckList("a.carno", fi);
				actionContext.getRequest().setAttribute("truckList", truckList);
			}
	        List fk=cw.getFuKuanTypeList(kind);
	        ResultsetList ys=null;
	        if(kind==-1 || kind==1)
	        	ys = cw.getChongZhiList(factory,ctx.getDeptid(),kind,curpage,"","");
	        else
	        	ys = cw.getYunFeiYuFuList(factory,ctx.getDeptid(),kind,curpage,"","");
	        MoneyBank mb=new MoneyBank();
	        List acc=mb.getAccountList(ctx.getDeptid());
	        double minHuoKuan=cw.getMinHuoKuan(kind, factory);
	        actionContext.getRequest().setAttribute("ys", ys);
	        actionContext.getRequest().setAttribute("acc", acc);
			
			actionContext.getRequest().setAttribute("fk", fk);
			actionContext.getRequest().setAttribute("kind", String.valueOf(kind));
			actionContext.getRequest().setAttribute("factory", String.valueOf(factory));
			actionContext.getRequest().setAttribute("zhujima", zhujima);
			actionContext.getRequest().setAttribute("minHuoKuan", String.valueOf(minHuoKuan));
	        return actionContext.getMapping().findForward("YuShouKuan");
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
