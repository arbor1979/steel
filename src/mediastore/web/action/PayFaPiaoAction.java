package mediastore.web.action;

import mediastore.dao.CaiWu;
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

public class PayFaPiaoAction extends AbstractAction{

	public PayFaPiaoAction() {
		super();
	}

	public ActionForward execute(ActionContext actionContext) 
	{
		try
    	{
	    	String param = oConvert.getString(actionContext.getParameter("param"),"all");
	    	UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
	    	int curpage = oConvert.getInt(actionContext.getParameter("curpage"),1);
	    	PayHuoKuanForm phf=(PayHuoKuanForm)actionContext.getForm();
			if(phf==null)
				phf=new PayHuoKuanForm();
	    	CaiWu cw=new CaiWu();
	    	if(!ctx.isIfview())
	        	phf.setDeptid(ctx.getDeptid());
	    	ResultsetList ys=null;
	        if(param.equals("pay"))
	        {
	        	if(phf.getDeptid()!=ctx.getDeptid())
	        		throw new Exception("您无权操作此单");
	        	int billid=oConvert.getInt(actionContext.getParameter("billid"),-1);
	        	if(billid==-1)
	        		throw new Exception("单号不正确");
	        	cw.payFaPiao(phf.getKind(), billid,phf.getDeptid(),ctx.getUserName());
	        	actionContext.getResponse().sendRedirect("payFaPiao.do?param=list&factory="+phf.getFactory()+"&deptid="+phf.getDeptid()+"&kind="+phf.getKind());
	        	return null;
	        }
	        else if(param.equals("list"))
	        {
	        	if(phf.getKind()==0)
		        	ys= cw.giveFaPiaoList(phf,curpage);
		        if(phf.getKind()==1)
		        	ys = cw.getFaPiaoList(phf,curpage);
		        if(ys.rslist.size()==0)
		        {
		        	actionContext.getResponse().sendRedirect("payFaPiao.do?kind="+phf.getKind());
		        	return null;
		        }
		        actionContext.getRequest().setAttribute("ys", ys);
		        actionContext.getRequest().setAttribute("phf", phf);
		        return actionContext.getMapping().findForward("PayFaPiao");
	        }
	        
        	if(phf.getKind()==0)
	        	ys= cw.getKaiFaPiaoGroupByFac(phf,curpage);
	        if(phf.getKind()==1)
	        	ys = cw.getShouFaPiaoGroupByFac(phf,curpage);
	        DepartManager dm=new DepartManager();
	        java.util.List departList=dm.getDepartList("id");
	        actionContext.getRequest().setAttribute("departList", departList);
	        actionContext.getRequest().setAttribute("ys", ys);
	        actionContext.getRequest().setAttribute("phf", phf);
	        return actionContext.getMapping().findForward("PayFaPiaoGroup");
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
