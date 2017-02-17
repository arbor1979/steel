package mediastore.web.action;

import mediastore.dao.GoodsStoreCode;
import mediastore.web.form.GoodsPropertyManageFB;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class GoodsStoreManageAction extends AbstractAction{

	public GoodsStoreManageAction() {
		super();
	}

	@Override
	public ActionForward execute(ActionContext actionContext) 
	{
		GoodsStoreCode gsic = new GoodsStoreCode();
		GoodsPropertyManageFB gpmfb = new GoodsPropertyManageFB();
		java.util.List goodsShelfIdList = gsic.getGoodsShelfIdList();
		gpmfb.setgoodsShelfList(goodsShelfIdList);
        actionContext.getRequest().setAttribute(Globals.REQUEST_GOODSPROMANAGE, gpmfb);
        return actionContext.getMapping().findForward("GroupManager");
	}

}
