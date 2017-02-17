// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsPropertyManageAction.java

package mediastore.web.action;

import java.net.URLDecoder;

import mediastore.dao.GoodsClassCode;
import mediastore.dao.GoodsTypeCode;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.GoodsTypeInfo;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class GoodsTypeManageAction extends AbstractAction
{

    public GoodsTypeManageAction()
    {
    }
    //产品子类维护
    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
	    	String param = oConvert.getString(actionContext.getParameter("param"),"list");
	    	String classid = actionContext.getParameter("classid");
	    	GoodsTypeCode gtc = new GoodsTypeCode();
	    	GoodsClassCode gcc = new GoodsClassCode();
	    	GoodsTypeInfo gti=new GoodsTypeInfo();
	    	gti=(GoodsTypeInfo)actionContext.getForm();
			if (param.equals("add"))
			{
				gtc.insertNewGoodsType(gti.getName(),classid);
			}
			else if (param.equals("edit"))
			{
				gti.setName(URLDecoder.decode(gti.getName(),"UTF-8"));
				gtc.updateGoodsType(oConvert.getString(gti.getId()), gti.getName(),classid);
				actionContext.getResponse().getWriter().print("ok");
	        	return null;
			}
			else if(param.equals("del"))
			{
	    		gtc.deleteGoodsType(oConvert.getString(gti.getId()));
			}
			
	        java.util.List goodsTypeList = gtc.getGoodsTypeList(classid);
	        java.util.List goodsClassList = gcc.getGoodsClassList();
	        actionContext.getRequest().setAttribute("goodsClassList", goodsClassList);
	        actionContext.getRequest().setAttribute("goodsTypeList", goodsTypeList);
	        actionContext.getRequest().setAttribute("classid", classid);
	        return actionContext.getMapping().findForward("GoodsTypeManage");
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
