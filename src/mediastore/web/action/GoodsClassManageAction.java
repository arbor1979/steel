// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsPropertyManageAction.java

package mediastore.web.action;

import java.net.URLDecoder;

import mediastore.dao.GoodsClassCode;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.GoodsClassInfo;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class GoodsClassManageAction extends AbstractAction
{

    public GoodsClassManageAction()
    {
    }
    //产品大类维护
    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
	    	String param = oConvert.getString(actionContext.getParameter("param"),"list");
	    	GoodsClassInfo gci=new GoodsClassInfo();
	    	gci=(GoodsClassInfo)actionContext.getForm();
	    	GoodsClassCode gcc = new GoodsClassCode();
	    	
			if (param.equals("add"))
			{
	    		gcc.insertNewGoodsClass(gci.getName());
			}
			else if (param.equals("edit"))
			{
				gci.setName(URLDecoder.decode(gci.getName(),"UTF-8"));
		        gcc.updateGoodsClass(oConvert.getString(gci.getId()),gci.getName());
		        actionContext.getResponse().getWriter().print("ok");
	        	return null;
			}
			else if(param.equals("del"))
			{
	    		gcc.deleteGoodsClass(oConvert.getString(gci.getId()));
			}
			
	        java.util.List goodsClassList = gcc.getGoodsClassList();
	        actionContext.getRequest().setAttribute("classlist", goodsClassList);
	        return actionContext.getMapping().findForward("GoodsClassManage");
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
