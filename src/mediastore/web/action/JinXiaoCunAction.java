// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportItemSearchConAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.GoodsImportGoods;
import mediastore.dao.GoodsInfo;
import mediastore.rule.ImportItemSearchRule;
import mediastore.user.StoreManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.GoodsImportGoodsInfo;
import mediastore.web.form.ResultsetList;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class JinXiaoCunAction extends AbstractAction
{

    public JinXiaoCunAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
        
    	try
    	{
    		
    		GoodsImportGoodsInfo gif=(GoodsImportGoodsInfo)actionContext.getForm();
			if(gif==null)
				gif=new GoodsImportGoodsInfo();
			GoodsImportGoods giinfo = new GoodsImportGoods();
			int curpage = oConvert.getInt(actionContext.getParameter("curpage"),1);
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
	        String importTime1 = oConvert.getString(actionContext.getParameter("importtime1"),"");
	        String importTime2 = oConvert.getString(actionContext.getParameter("importtime2"),"");
	        String oper = oConvert.getString(actionContext.getParameter("oper"),"");
	        String classid = oConvert.getString(actionContext.getParameter("classid"),"0");
	        String typeid = oConvert.getString(actionContext.getParameter("typeid"),"0");
	        ImportItemSearchRule iisr=new ImportItemSearchRule();
	        iisr.setGif(gif);
	        iisr.setCurpage(curpage);
	        iisr.setGoodsClass(classid);
	        iisr.setGoodsType(typeid);
	        iisr.setImportTime1(importTime1);
	        iisr.setImportTime2(importTime2);
	        iisr.setOper(oper);
	        iisr.setOrderStr("goodsid");
	        ResultsetList ibsri = giinfo.getItemListByRule(iisr);

	        StoreManager sm=new StoreManager();
	        List StoreList = sm.getDepartList("id",ctx.getDeptid());
	        GoodsInfo gcc = new GoodsInfo();
	        List cdList=gcc.getChanDiList();
	        actionContext.getRequest().setAttribute("cdList", cdList);
	        actionContext.getRequest().setAttribute("StoreList", StoreList);
	        actionContext.getRequest().setAttribute("ibsri", ibsri);
	        actionContext.getRequest().setAttribute("iisr", iisr);
	        return actionContext.getMapping().findForward("ImportItemSearch");
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
