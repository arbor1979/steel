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

public class PayYunFeiAction extends AbstractAction{

	public PayYunFeiAction() {
		super();
	}

	public ActionForward execute(ActionContext actionContext) 
	{
		try
    	{
	    	String param = oConvert.getString(actionContext.getParameter("param"),"");
	    	PayHuoKuanForm phf=(PayHuoKuanForm)actionContext.getForm();
			if(phf==null)
				phf=new PayHuoKuanForm();
			UserInfoForm  ctx=(UserInfoForm) actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
			
	    	CaiWu cw=new CaiWu();
	        if(param.equals("pay"))
	        {
	        	if(phf.getDeptid()!=ctx.getDeptid())
	        		throw new Exception("您无权操作此单");
	        	
	        	int billid=oConvert.getInt(actionContext.getParameter("billid"),-1);
	        	int acctype=oConvert.getInt(actionContext.getParameter("acctype"),0);
	        	int fkType=oConvert.getInt(actionContext.getParameter("fkType"),0);
	        	double realmoney=oConvert.getFloat(actionContext.getParameter("realmoney"),0);
	        	if(billid==-1)
	        		throw new Exception("单号不正确");
	        	if(realmoney==0)
	        		throw new Exception("运费不能为0");
	        	if(acctype==0)
	        		throw new Exception("支付账户不能为空");
	        	int fybillid=cw.payYunFei(phf.getKind(), billid,phf.getDeptid(), realmoney,ctx.getUserName(),acctype,fkType);
	        	if(fybillid>0)
	        	{
	        		actionContext.getResponse().sendRedirect("feiYongItemView.do?billId="+fybillid+"&deptid="+phf.getDeptid()+"&kind=-1&ifprint=3");
	        		return null;
	        	}
	        }
	        else if(param.equals("list"))
	        {
		        if(!ctx.isIfview())
					phf.setDeptid(ctx.getDeptid());
		        if(phf.getOrderby().length()==0)
		        	phf.setOrderby("billid");
		        ResultsetList ys=cw.getYunFeiList(phf);
		        MoneyBank mb=new MoneyBank();
		        List acc=mb.getAccountList(ctx.getDeptid());
		        actionContext.getRequest().setAttribute("acc", acc);
		        actionContext.getRequest().setAttribute("ys", ys);
		        actionContext.getRequest().setAttribute("phf", phf);
		        return actionContext.getMapping().findForward("PayYunFei");
	        }
	        ResultsetList ysg = cw.getYunFeiGroupByFac(phf);
	        DepartManager dm=new DepartManager();
	        java.util.List departList=dm.getDepartList("id");
	        actionContext.getRequest().setAttribute("departList", departList);
	        actionContext.getRequest().setAttribute("ysg", ysg);
	        actionContext.getRequest().setAttribute("phf", phf);
        	return actionContext.getMapping().findForward("PayYunFeiGroup");
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
