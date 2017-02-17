package mediastore.web.action;

import java.util.List;

import mediastore.dao.CaiWu;
import mediastore.dao.MoneyBank;
import mediastore.user.DepartManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.PayHuoKuanForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class PayHuoKuanAction extends AbstractAction{

	public PayHuoKuanAction() {
		super();
	}

	public ActionForward execute(ActionContext actionContext) 
	{
		try
    	{
	    	String param = oConvert.getString(actionContext.getParameter("param"),"");
	    	String act = oConvert.getString(actionContext.getParameter("act"),"");
	    	int curpage = oConvert.getInt(actionContext.getParameter("curpage"),1);
	    	PayHuoKuanForm phf=(PayHuoKuanForm)actionContext.getForm();
			if(phf==null)
				phf=new PayHuoKuanForm();
	    	UserInfoForm  ctx=(UserInfoForm) actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
	    	CaiWu cw=new CaiWu();
	    	MoneyBank mb=new MoneyBank();
	        if(param.equals("pay"))
	        {
	        	if(phf.getDeptid()!=ctx.getDeptid())
	        		throw new Exception("您无权操作此单");
	        	int billid=oConvert.getInt(actionContext.getParameter("billid"),0);
	        	double realmoney=oConvert.getDouble(actionContext.getParameter("realmoney"),0);
	        	int fkType=oConvert.getInt(actionContext.getParameter("fkType"),0);
	        	int acctype=oConvert.getInt(actionContext.getParameter("acctype"),0);
	        	if(billid==-1)
	        		throw new Exception("单号不正确");
	        	if(realmoney==0)
	        		throw new Exception("实收金额不能为0");
	        	if(fkType==0)
	        		throw new Exception("请选择一种付款方式");
	        	if(fkType!=5 && acctype==0)
	        		throw new Exception("请选择一个账户");
	        	cw.payHuoKuan(phf.getKind(), billid,phf.getDeptid(), realmoney,fkType,acctype,ctx.getUserName());
	        	actionContext.getResponse().sendRedirect("payHuoKuan.do?param=list&factory="+phf.getFactory()+"&deptid="+phf.getDeptid()+"&kind="+phf.getKind());
	        	return null;
	        }
	        if(param.equals("list"))
	        {
	        	if(phf.getFactory()==0)
	        		throw new Exception("请选择一个单位");
	        	ResultsetList ys=null;
	        	if(phf.getKind()==1)
	        		ys= cw.getYingShouList(phf);
		        if(phf.getKind()==-1)
		        	ys=cw.getYingFuList(phf);
		        if(ys.rslist.size()==0)
		        {
		        	actionContext.getResponse().sendRedirect("payHuoKuan.do?kind="+phf.getKind());
		        	return null;
		        }
		        List fk=cw.getFuKuanTypeList(phf.getKind());
		        List acc=mb.getAccountList(ctx.getDeptid());
		        actionContext.getRequest().setAttribute("fk", fk);        
		        actionContext.getRequest().setAttribute("ys", ys);
		        actionContext.getRequest().setAttribute("acc", acc);
		        actionContext.getRequest().setAttribute("act", act);
		        actionContext.getRequest().setAttribute("kind", String.valueOf(phf.getKind()));
		        return actionContext.getMapping().findForward("PayHuoKuan");
	        }
	        DepartManager dm=new DepartManager();
	        java.util.List departList=dm.getDepartList("id");
	        actionContext.getRequest().setAttribute("departList", departList);
	        actionContext.getRequest().setAttribute("act", act);
	        actionContext.getRequest().setAttribute("phf", phf);
	    	ResultsetList ysg=null;
	    	if(phf.getViewtype()==0)
	    	{
		        if(phf.getKind()==1)
		        	ysg = cw.getYingShouGroupByFac(phf,curpage);
		        if(phf.getKind()==-1)
		        	ysg = cw.getYingFuGroupByFac(phf,curpage);
		        actionContext.getRequest().setAttribute("ysg", ysg);
	        	return actionContext.getMapping().findForward("PayHuoKuanGroup");
	    	}
	    	else
	    	{
	    		if(phf.getKind()==1)
	    			ysg= cw.getYingShouList(phf);
	    		if(phf.getKind()==-1)
	    			ysg=cw.getYingFuList(phf);
	    		List fk=cw.getFuKuanTypeList(phf.getKind());
		        List acc=mb.getAccountList(ctx.getDeptid());
	    		actionContext.getRequest().setAttribute("ys", ysg);
	    		actionContext.getRequest().setAttribute("fk", fk);        
		        actionContext.getRequest().setAttribute("acc", acc);
		        actionContext.getRequest().setAttribute("kind", String.valueOf(phf.getKind()));
	    		return actionContext.getMapping().findForward("PayHuoKuanDetail");
	    	}





	        
	        
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
