package mediastore.web.action;

import java.util.List;

import mediastore.dao.MoneyBank;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class AccessTypeAction extends AbstractAction{

	public AccessTypeAction()  {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ActionForward execute(ActionContext actionContext) 
	{
		try
		{
	        String classid = String.valueOf(oConvert.getInt(actionContext.getParameter("classid"),0));
	        String typeid = String.valueOf(oConvert.getInt(actionContext.getParameter("typeid"),0));
	        MoneyBank gcc = new MoneyBank();
	        List GoodsClassList = gcc.getAccessClassList(1);
	        List GoodsTypeList = gcc.getAccessTypeList(classid);

	        actionContext.getRequest().setAttribute("GoodsClassList", GoodsClassList);
	        actionContext.getRequest().setAttribute("GoodsTypeList", GoodsTypeList);
	        actionContext.getRequest().setAttribute("classid", classid);
	        actionContext.getRequest().setAttribute("typeid", typeid);
	        return actionContext.getMapping().findForward("AccessType");
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
