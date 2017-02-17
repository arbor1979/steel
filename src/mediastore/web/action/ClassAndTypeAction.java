package mediastore.web.action;

import java.util.List;

import mediastore.dao.GoodsClassCode;
import mediastore.dao.GoodsTypeCode;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class ClassAndTypeAction extends AbstractAction{

	public ClassAndTypeAction()  {
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
	        GoodsClassCode gcc = new GoodsClassCode();
	        GoodsTypeCode gtc = new GoodsTypeCode();
	        List GoodsClassList = gcc.getGoodsClassList();
	        List GoodsTypeList = gtc.getGoodsTypeList(classid);

	        actionContext.getRequest().setAttribute("GoodsClassList", GoodsClassList);
	        actionContext.getRequest().setAttribute("GoodsTypeList", GoodsTypeList);
	        actionContext.getRequest().setAttribute("classid", classid);
	        actionContext.getRequest().setAttribute("typeid", typeid);
	        return actionContext.getMapping().findForward("ClassAndType");
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
