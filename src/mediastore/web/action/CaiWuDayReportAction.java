// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportGoodsBillShowAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.CaiWu;
import mediastore.dao.FeiYongInput;
import mediastore.dao.GoodsInfo;
import mediastore.dao.MoneyBank;
import mediastore.user.DepartManager;
import mediastore.user.TruckManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.PayHuoKuanForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.form.TruckInfoForm;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class CaiWuDayReportAction extends AbstractAction
{

    public CaiWuDayReportAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
		{
    		String ifprint = oConvert.getString(actionContext.getParameter("ifprint"),"0");
    		
    		String importTime1="";
			if(actionContext.getParameter("searchbutton")==null)
	            importTime1 = oConvert.FormDate(new java.util.Date(), "yyyy-MM-dd 00:00:00");
			else
				importTime1=oConvert.getString(actionContext.getParameter("importtime1"),"");
			String importTime2="";
			if(actionContext.getParameter("searchbutton")==null)
	            importTime2 = oConvert.FormDate(new java.util.Date(), "yyyy-MM-dd 23:59:59");
			else
				importTime2=oConvert.getString(actionContext.getParameter("importtime2"),"");
			
			int deptid = oConvert.getInt((String)actionContext.getParameter("deptid"),0);
    		UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
    		if(!ctx.isIfview() || deptid==0)
    			deptid=ctx.getDeptid();
    		
			MoneyBank mb=new MoneyBank();
			List ac=mb.getAccountList(ctx.getDeptid());
	    	CaiWu cw=new CaiWu();
	    	GoodsInfo gi=new GoodsInfo();
	    	FeiYongInput fyi=new FeiYongInput();
	    	TruckManager tm=new TruckManager();
	    	PayHuoKuanForm phf=new PayHuoKuanForm();
	    	phf.setDeptid(deptid);
	    	phf.setOrderby("SUM(a.TotalPrice+a.totaljiagong) desc");
        	ResultsetList ys=cw.getYingShouGroupByFac(phf,10000);
        	phf.setOrderby("SUM(a.TotalPrice) desc");
	        ResultsetList yf=cw.getYingFuGroupByFac(phf,10000);
	    	
	        List yushou=cw.getYuShouKuan(deptid);
	        List yufu=cw.getYuFuKuan(deptid);
	        TruckInfoForm tif=new TruckInfoForm();
	        tif.setAccount(1);
	        List yufuyunfei=tm.getTruckList("a.account desc", tif);
	        List otherYS=cw.getQiTaYingShouKuan(deptid);
	        List KucunList=gi.getRepertoryGroupByType(deptid);
	        
	        List shouruList=fyi.getFeiYongGroupByName(deptid,1,importTime1,importTime2);
	        List feiyongList=fyi.getFeiYongGroupByName(deptid,-1,importTime1,importTime2);
	    	DepartManager dm=new DepartManager();
	        java.util.List departList=dm.getDepartList("id");
	        actionContext.getRequest().setAttribute("departList", departList);
	    	actionContext.getRequest().setAttribute("ac", ac);
	    	actionContext.getRequest().setAttribute("ys", ys);
	    	actionContext.getRequest().setAttribute("yf", yf);
	    	actionContext.getRequest().setAttribute("yushou", yushou);
	    	actionContext.getRequest().setAttribute("yufu", yufu);
	    	actionContext.getRequest().setAttribute("yufuyunfei", yufuyunfei);
	    	actionContext.getRequest().setAttribute("otherYS", otherYS);
	    	actionContext.getRequest().setAttribute("KucunList", KucunList);
	    	actionContext.getRequest().setAttribute("shouruList", shouruList);
	    	actionContext.getRequest().setAttribute("feiyongList", feiyongList);
	    	actionContext.getRequest().setAttribute("ifprint", ifprint);
    		actionContext.getRequest().setAttribute("importTime1", importTime1);
    		actionContext.getRequest().setAttribute("importTime2", importTime2);
    		actionContext.getRequest().setAttribute("deptid", String.valueOf(deptid));
    		if(ifprint.equals("0"))
    			return actionContext.getMapping().findForward("CaiWuDayReport");
    		else
    			return actionContext.getMapping().findForward("CaiWuDayReportPrint");
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
