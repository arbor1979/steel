// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   UserManageAction.java

package mediastore.web.action;

import mediastore.user.DepartManager;
import mediastore.user.StoreManager;
import mediastore.util.oConvert;
import mediastore.web.form.StoreInfoForm;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class StoreManageAction extends AbstractAction
{

    public StoreManageAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
	    	String param = oConvert.getString(actionContext.getParameter("param"),"list");
	    	StoreInfoForm dif=(StoreInfoForm)actionContext.getForm();
			if(dif==null)
				dif=new StoreInfoForm();
			StoreManager sm = new StoreManager();
	        DepartManager dm=new DepartManager();
	        java.util.List departList=dm.getDepartList("id");
			if (param.equals("add"))
			{
				actionContext.getRequest().setAttribute("dif",dif);
		        actionContext.getRequest().setAttribute("departList",departList);
		        return actionContext.getMapping().findForward("StoreManageEdit");
			}
			else if (param.equals("edit"))
			{
		        dif=sm.getDepartByID(dif.getId());
		        actionContext.getRequest().setAttribute("dif",dif);
		        actionContext.getRequest().setAttribute("departList",departList);
		        return actionContext.getMapping().findForward("StoreManageEdit");
			}
			else if (param.equals("save"))
			{
				
				if(oConvert.isEmpty(dif.getName()))
		        	throw new Exception("部门名称不能为空！");
				if(oConvert.isEmpty(dif.getAddress()))
		        	throw new Exception("部门地址不能为空！");
				if(oConvert.isEmpty(dif.getTel()))
		        	throw new Exception("部门电话不能为空！");
				if(oConvert.isEmpty(dif.getLinkman()))
		        	throw new Exception("联系人不能为空！");
				
		        if(dif.getId()==0)
		        	sm.insertDepartRec(dif);
		        else
		        	sm.updateDepartRec(dif);
		        dif=new StoreInfoForm();
		        java.util.List DepartList = sm.getDepartList("a.id",dif);
		        actionContext.getRequest().setAttribute("DepartList", DepartList);
		        actionContext.getRequest().setAttribute("departList",departList);
		        actionContext.getRequest().setAttribute("dif", dif);
		        return actionContext.getMapping().findForward("StoreManage");
			}
			else if(param.equals("del"))
			{
				sm.deleteDepartRec(dif.getId());
				dif=new StoreInfoForm();
				java.util.List DepartList = sm.getDepartList("a.id",dif);
		        actionContext.getRequest().setAttribute("DepartList", DepartList);
		        actionContext.getRequest().setAttribute("departList",departList);
				actionContext.getRequest().setAttribute("dif", dif);
		        return actionContext.getMapping().findForward("StoreManage");	
			}
			else
			{
		    	java.util.List DepartList = sm.getDepartList("a.id",dif);
		        actionContext.getRequest().setAttribute("DepartList", DepartList);
		        actionContext.getRequest().setAttribute("departList",departList);
		        actionContext.getRequest().setAttribute("dif", dif);
		        return actionContext.getMapping().findForward("StoreManage");
			}
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
