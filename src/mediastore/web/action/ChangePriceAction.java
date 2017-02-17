// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsSearchAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.GoodsInfo;
import mediastore.user.DepartManager;
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

public class ChangePriceAction extends AbstractAction
{

    public ChangePriceAction()
    {
    }

	public ActionForward execute(ActionContext actionContext)
    {
    	try
        {
    		String param = oConvert.getString(actionContext.getParameter("param"),"");
	    	String orderby = oConvert.getString(actionContext.getParameter("orderby"),"");
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
    		GoodsInfoForm gif=(GoodsInfoForm)actionContext.getForm();
			if(gif==null)
				gif=new GoodsInfoForm();
			GoodsInfo um = new GoodsInfo();
			if(!ctx.isIfview())
				gif.setDeptid(ctx.getDeptid());
	        if(param.equals("updatePrice"))
	        {
	        	String goodsid=oConvert.getString(actionContext.getParameter("egoodsid"),"");
	        	int deptid=oConvert.getInt(actionContext.getParameter("edeptid"),0);
	        	double sellprice=oConvert.getRound(oConvert.getDouble(actionContext.getParameter("esellprice"),0),4);
	        	if(sellprice<0)
	        		throw new Exception("单价必须大于等于0");
	        	if(deptid!=ctx.getDeptid())
	        		throw new Exception("您没有权限更改");
	        	um.changeSellPrice(goodsid, deptid, sellprice);
	        }
			ResultsetList gsri = um.getRepertoryListByRule(gif,orderby,0,0);
			StoreManager sm=new StoreManager();
			List StoreList = sm.getDepartList("id",gif.getDeptid());
	        DepartManager dm=new DepartManager();
	        java.util.List departList=dm.getDepartList("id");
	        List cdList=um.getChanDiList();
	        actionContext.getRequest().setAttribute("cdList", cdList);
	        actionContext.getRequest().setAttribute("StoreList", StoreList);
	        actionContext.getRequest().setAttribute("departList", departList);
	        actionContext.getRequest().setAttribute("result",gsri);
	        actionContext.getRequest().setAttribute("gif",gif);
	        actionContext.getRequest().setAttribute("orderby",orderby);
	        
	        return actionContext.getMapping().findForward("ChangePrice");
    		
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
