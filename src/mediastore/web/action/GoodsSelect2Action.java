package mediastore.web.action;

import mediastore.dao.GoodsInfo;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.GoodsInfoForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class GoodsSelect2Action extends AbstractAction{

	public GoodsSelect2Action()  {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ActionForward execute(ActionContext actionContext) 
	{
		try
		{
			int factid=oConvert.getInt((String)actionContext.getParameter("factid"),0);
			String orderby = oConvert.getString(actionContext.getParameter("orderby"),"b.caizhi,a.goodsid");
			int kind=oConvert.getInt((String)actionContext.getParameter("kind"),1);
			GoodsInfoForm gif=(GoodsInfoForm)actionContext.getForm();
			if(gif==null)
				gif=new GoodsInfoForm();
			GoodsInfo um = new GoodsInfo();
			ResultsetList gsri = um.getRepertoryListByRule(gif,orderby,factid,kind);
	       
	        actionContext.getRequest().setAttribute("factid", String.valueOf(factid));
	        actionContext.getRequest().setAttribute("kind", String.valueOf(kind));
	        actionContext.getRequest().setAttribute("gsri", gsri);
	        actionContext.getRequest().setAttribute("orderby", orderby);
	        actionContext.getRequest().setAttribute("gif", gif);
	        return actionContext.getMapping().findForward("GoodsSelect2");
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
