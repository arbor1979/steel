// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsSearchAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.GoodsClassCode;
import mediastore.dao.GoodsInfo;
import mediastore.dao.GoodsStoreCode;
import mediastore.dao.GoodsTypeCode;
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

public class RepertoryListAction extends AbstractAction
{

    public RepertoryListAction()
    {
    }

	public ActionForward execute(ActionContext actionContext)
    {
    	try
        {
    		String param = oConvert.getString(actionContext.getParameter("param"),"");
    		String ifprint = oConvert.getString(actionContext.getParameter("ifprint"),"0");
	    	String orderby = oConvert.getString(actionContext.getParameter("orderby"),"");
	    	UserInfoForm  ctx=(UserInfoForm) actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
    		GoodsInfoForm gif=(GoodsInfoForm)actionContext.getForm();
			if(gif==null)
				gif=new GoodsInfoForm();
			GoodsInfo um = new GoodsInfo();
			if(param.equals("updatememo"))
			{
				um.UpdateRepertoryMemo(gif);
				gif=new GoodsInfoForm();
			}
			String btsearch = oConvert.getString(actionContext.getParameter("searchbutton"),"");
			if(btsearch.length()==0)
				gif.setDeptid(ctx.getDeptid());
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
	        if(ifprint.equals("1") || ifprint.equals("2"))
	        {
	        	if(!gif.getGoodsClass().equals("0"))
	        	{
	        		GoodsClassCode gcc=new GoodsClassCode();
	        		gif.setGoodsClassName(gcc.getGoodsClassName(gif.getGoodsClass()));
	        	}
	        	if(!gif.getGoodsType().equals("0"))
	        	{
	        		GoodsTypeCode gcc=new GoodsTypeCode();
	        		gif.setGoodsTypeName(gcc.getGoodsTypeName(gif.getGoodsType()));
	        	}
	        	if(gif.getStoreid()!=0)
	        	{
	        		GoodsStoreCode gcc=new GoodsStoreCode();
	        		gif.setStoreName(gcc.getGoodsStoreName(gif.getStoreid()));
	        	}
	        	if(gif.getDeptid()>0)
	        	{
	        		gif.setShortname(dm.getFactByID(gif.getDeptid()).getShortname());
	        	}
	        	if(ifprint.equals("1"))
	        		return actionContext.getMapping().findForward("RepertoryPrint");
	        	else
	        		return actionContext.getMapping().findForward("RepertoryExcel");
	        }
	        else
	        	return actionContext.getMapping().findForward("RepertoryList");
    		
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
