package mediastore.web.action;

import java.util.List;

import mediastore.dao.FeiYongClass;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class FeiYongTypeSelectAction extends AbstractAction{

	public FeiYongTypeSelectAction()  {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ActionForward execute(ActionContext actionContext) 
	{
		try
		{
	        String classid =actionContext.getParameter("classid");
	        String typeid = actionContext.getParameter("typeid");
	        int kind =oConvert.getInt((String)actionContext.getParameter("kind"),0);
	        FeiYongClass gcc = new FeiYongClass();
	        List GoodsClassList = gcc.getGoodsClassList(kind);
	        List GoodsTypeList = gcc.getGoodsTypeList(oConvert.getInt(classid,0));

	        actionContext.getRequest().setAttribute("GoodsClassList", GoodsClassList);
	        actionContext.getRequest().setAttribute("GoodsTypeList", GoodsTypeList);
	        actionContext.getRequest().setAttribute("classid", classid);
	        actionContext.getRequest().setAttribute("typeid", typeid);
	        actionContext.getRequest().setAttribute("kind", String.valueOf(kind));
	        return actionContext.getMapping().findForward("FeiYongTypeSelect");
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
