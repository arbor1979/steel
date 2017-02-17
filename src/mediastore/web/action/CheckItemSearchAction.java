// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportItemSearchConAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.GoodsCheckGoods;
import mediastore.dao.GoodsCheckInfo;
import mediastore.dao.GoodsInfo;
import mediastore.rule.CheckItemSearchRule;
import mediastore.user.DepartManager;
import mediastore.user.StoreManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.GoodsCheckGoodsInfo;
import mediastore.web.form.ResultsetList;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class CheckItemSearchAction extends AbstractAction
{

    public CheckItemSearchAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
        
    	try
    	{
    		
    		GoodsCheckGoodsInfo gif=(GoodsCheckGoodsInfo)actionContext.getForm();
			if(gif==null)
				gif=new GoodsCheckGoodsInfo();
			GoodsCheckInfo gii = new GoodsCheckInfo();
			GoodsCheckGoods giinfo = new GoodsCheckGoods();
			DepartManager dm=new DepartManager();
	        java.util.List departList=dm.getDepartList("id");
			int curpage = oConvert.getInt(actionContext.getParameter("curpage"),1);
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
			if(!ctx.isIfview())
				gif.setDeptid(ctx.getDeptid());
			String importTime1 ="";
	        if(actionContext.getParameter("searchbutton")==null)
				importTime1=oConvert.FormDate(new java.util.Date(), "yyyy-MM-01 00:00:00");
			else
				importTime1=oConvert.getString(actionContext.getParameter("importtime1"),"");
	        String importTime2 = oConvert.getString(actionContext.getParameter("importtime2"),"");
	        String classid = oConvert.getString(actionContext.getParameter("classid"),"0");
	        String typeid = oConvert.getString(actionContext.getParameter("typeid"),"0");
	        int kind = oConvert.getInt((String)actionContext.getParameter("kind"),0);
	    	String orderby = oConvert.getString(actionContext.getParameter("orderby"),"createTime desc");
	        CheckItemSearchRule iisr=new CheckItemSearchRule();
	        iisr.setKind(kind);
	        iisr.setGif(gif);
	        iisr.setCurpage(curpage);
	        iisr.setGoodsClass(classid);
	        iisr.setGoodsType(typeid);
	        iisr.setImportTime1(importTime1);
	        iisr.setImportTime2(importTime2);
	        iisr.setOrderStr(orderby);
	        ResultsetList ibsri = giinfo.getItemListByRule(iisr);
	        List billIdList = gii.getBillIdList(ctx.getDeptid());
	        List PersonList = gii.getPersonsList(ctx.getDeptid());
	        StoreManager sm=new StoreManager();
	        List StoreList = sm.getDepartList("id",ctx.getDeptid());
	        GoodsInfo gcc = new GoodsInfo();
	        List cdList=gcc.getChanDiList();
	        actionContext.getRequest().setAttribute("cdList", cdList);
	        actionContext.getRequest().setAttribute("departList", departList);
	        actionContext.getRequest().setAttribute("billIdList", billIdList);
	        actionContext.getRequest().setAttribute("StoreList", StoreList);
	        actionContext.getRequest().setAttribute("PersonList", PersonList);
	        actionContext.getRequest().setAttribute("ibsri", ibsri);
	        actionContext.getRequest().setAttribute("iisr", iisr);
	        return actionContext.getMapping().findForward("CheckItemSearch");
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
