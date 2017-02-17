// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsPropertyManageAction.java

package mediastore.web.action;

import java.net.URLDecoder;

import mediastore.dao.FeiYongClass;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.FeiYongTypeForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class FeiYongTypeManageAction extends AbstractAction
{

    public FeiYongTypeManageAction()
    {
    }
    //货物属性维护
    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
    		String param = oConvert.getString(actionContext.getParameter("param"),"");
    		FeiYongTypeForm fyf=(FeiYongTypeForm)actionContext.getForm();
			if(fyf==null)
				fyf=new FeiYongTypeForm();
	    	FeiYongClass fyc = new FeiYongClass();
    		if(param.equals("add"))
    		{

        		if(fyf.getClassid()==0)
        			throw new Exception("大类不能为空");
        		if(fyf.getTypename().length()==0)
        			throw new Exception("名称不能为空");
        		fyc.insertNewGoodsType(fyf);
    		}
    		
    		else if(param.equals("edit"))
    		{
    			fyf.setTypename(URLDecoder.decode(fyf.getTypename(),"UTF-8"));
        		fyc.updateGoodsType(fyf);
        		actionContext.getResponse().getWriter().print("ok");
	        	return null;
    		}
    		else if(param.equals("del"))
    		{
        		fyc.deleteGoodsType(fyf.getId());
    		}
	        java.util.List classList = fyc.getGoodsClassList(fyf.getKind());
	        java.util.List typeList = fyc.getGoodsTypeList(fyf.getClassid());
	        actionContext.getRequest().setAttribute("classList", classList);
	        actionContext.getRequest().setAttribute("typeList", typeList);
	        actionContext.getRequest().setAttribute("classid", String.valueOf(fyf.getClassid()));
	        actionContext.getRequest().setAttribute("kind", String.valueOf(fyf.getKind()));
	        return actionContext.getMapping().findForward("FeiYongTypeManage");
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
