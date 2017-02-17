package mediastore.web.action;

import java.util.List;

import mediastore.dao.CaiWu;
import mediastore.dao.GoodsExportInfo;
import mediastore.user.DepartManager;
import mediastore.user.YeWuManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.ExportBillForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.form.UserInfoForm;
import mediastore.web.form.YeWuInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class ExportBillSearchAction extends AbstractAction{

	public ExportBillSearchAction() {
		super();
	}

	public ActionForward execute(ActionContext actionContext) 
	{
		try
    	{
	    	String param = oConvert.getString(actionContext.getParameter("param"),"list");
	    	ExportBillForm gif=(ExportBillForm)actionContext.getForm();
			if(gif==null)
				gif=new ExportBillForm();
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
			if(!ctx.isIfview())
				gif.setDeptid(ctx.getDeptid());
			GoodsExportInfo giinfo = new GoodsExportInfo();
			int curpage = oConvert.getInt(actionContext.getParameter("curpage"),1);
			String exportTime1="";
			if(actionContext.getParameter("searchbutton")==null)
				exportTime1=oConvert.FormDate(new java.util.Date(), "yyyy-MM-01 00:00:00");
			else
				exportTime1=oConvert.getString(actionContext.getParameter("importtime1"),"");
	        
	        String exportTime2 =oConvert.getString(actionContext.getParameter("importtime2"),"");
	        
	        String paymentTime1 =oConvert.getString(actionContext.getParameter("paymenttime1"),"");
	        
	        String paymentTime2 =oConvert.getString(actionContext.getParameter("paymenttime2"),"");
	        
	        String ifpay =oConvert.getString(actionContext.getParameter("ifpay"),"");
	        String kind = oConvert.getString(actionContext.getParameter("kind"),"0");
	    	String orderby = oConvert.getString(actionContext.getParameter("orderby"),"exportTime desc");
	        if(param.equals("cancel"))
	        {
	        	gif.setSalesPerson(ctx.getUserName());
	        	giinfo.deleteExportBill(gif);
	        	gif.setBillId(0);
	        	gif.setSalesPerson("");
	        }
	        ResultsetList ibsri = giinfo.getBillListByRule(gif,exportTime1,exportTime2,paymentTime1,paymentTime2,curpage,ifpay,kind,orderby);
	        List billIdList = giinfo.getBillIdList();

	        java.util.List FactList = giinfo.getFactList(gif.getZhujima());
	        List PersonList = giinfo.getPersonsList(ctx.getDeptid());
	        CaiWu cw=new CaiWu();
	        List fkList=cw.getFuKuanTypeList(0);
	        DepartManager dm=new DepartManager();
	        List departList=dm.getDepartList("id");
	        YeWuManager um = new YeWuManager();
			YeWuInfoForm ywf=new YeWuInfoForm();
	        java.util.List ywList = um.getYeWuList("a.name",ywf);
	        actionContext.getRequest().setAttribute("billIdList", billIdList);
	        actionContext.getRequest().setAttribute("FactList", FactList);
	        actionContext.getRequest().setAttribute("PersonList", PersonList);
	        actionContext.getRequest().setAttribute("departList", departList);
	        actionContext.getRequest().setAttribute("ywList", ywList);
	        actionContext.getRequest().setAttribute("fkList", fkList);
	        actionContext.getRequest().setAttribute("ibsri", ibsri);
	        actionContext.getRequest().setAttribute("gif", gif);
	        actionContext.getRequest().setAttribute("exportTime1", exportTime1);
	        actionContext.getRequest().setAttribute("exportTime2", exportTime2);
	        actionContext.getRequest().setAttribute("paymentTime1", paymentTime1);
	        actionContext.getRequest().setAttribute("paymentTime2", paymentTime2);
	        actionContext.getRequest().setAttribute("ifpay", ifpay);
	        actionContext.getRequest().setAttribute("orderby", orderby);
	        actionContext.getRequest().setAttribute("kind", kind);
	        return actionContext.getMapping().findForward("ExportBillSearch");
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
