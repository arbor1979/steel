package mediastore.web.action;

import java.util.List;

import mediastore.dao.GoodsInfo;
import mediastore.user.StoreManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.GoodsInfoForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class GoodsSelectAction extends AbstractAction{

	public GoodsSelectAction()  {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ActionForward execute(ActionContext actionContext) 
	{
		try
		{
			String param=oConvert.getString((String)actionContext.getParameter("param"),"");
			GoodsInfoForm gif=(GoodsInfoForm)actionContext.getForm();
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
			if(gif==null)
				gif=new GoodsInfoForm();
	        GoodsInfo gcc = new GoodsInfo();
	        StoreManager sm=new StoreManager();
	        List cdList=gcc.getChanDiList();
	        String goodsid=gif.getGoodsId();
	        gif.setGoodsId("");
	        ResultsetList GoodsList = gcc.getGoodsListByRule(gif,1,10000,"a.goodsid");
	        gif.setGoodsId(goodsid);
	        List StoreList = sm.getStoreList("id",gif.getGoodsId(),ctx.getDeptid(),gif.getStoreid());
	        actionContext.getRequest().setAttribute("cdList", cdList);
	        actionContext.getRequest().setAttribute("GoodsList", GoodsList);
	        actionContext.getRequest().setAttribute("StoreList", StoreList);
	        actionContext.getRequest().setAttribute("gif", gif);
	        actionContext.getRequest().setAttribute("param", param);
	        return actionContext.getMapping().findForward("GoodsSelect");
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
