// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   UserManageAction.java

package mediastore.web.action;

import mediastore.user.DepartManager;
import mediastore.user.YeWuManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.YeWuInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class YeWuManageAction extends AbstractAction
{

    public YeWuManageAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
	    	String param = oConvert.getString(actionContext.getParameter("param"),"search");
	    	YeWuInfoForm gif=(YeWuInfoForm)actionContext.getForm();
			if(gif==null)
				gif=new YeWuInfoForm();
	        YeWuManager um = new YeWuManager();
	        DepartManager dm=new DepartManager();
	        java.util.List departList=dm.getDepartList("id");
	        if(param.equals("add"))
	        {
	        	actionContext.getRequest().setAttribute("fi",gif);
		        actionContext.getRequest().setAttribute("deptList",departList);
		        return actionContext.getMapping().findForward("YeWuManageEdit");
	        }
	        else if (param.equals("edit"))
			{
		        gif=um.getYeWuById(gif.getId());
		        actionContext.getRequest().setAttribute("fi",gif);
		        actionContext.getRequest().setAttribute("deptList",departList);
		        return actionContext.getMapping().findForward("YeWuManageEdit");
			}
	        if(param.equals("del"))
	        {
	        	um.deleteUserRec(gif.getId());
	        	gif.setId(0);
	        	java.util.List userList = um.getYeWuList("a.ID",gif);
	        	actionContext.getRequest().setAttribute("userList", userList);
		        actionContext.getRequest().setAttribute("gif",gif);
		        actionContext.getRequest().setAttribute("departList",departList);
		        return actionContext.getMapping().findForward("YeWuManage");	
	        }
	        else if (param.equals("save"))
			{
				if(oConvert.isEmpty(gif.getName()))
		        	throw new Exception("姓名不能为空！");
				if(oConvert.isEmpty(gif.getMobile()))
		        	throw new Exception("电话不能为空！");
				if(gif.getDeptid()==-1)
		        	throw new Exception("请选择所属分公司！");
				
		        if(gif.getId()==0)
		        	um.insertUserRec(gif);
		        else
		        	um.updateUserRec(gif);
		        gif=new YeWuInfoForm();
		        java.util.List userList = um.getYeWuList("a.ID",gif);
		        actionContext.getRequest().setAttribute("gif",gif);
		        actionContext.getRequest().setAttribute("departList",departList);
		        actionContext.getRequest().setAttribute("userList", userList);
		        return actionContext.getMapping().findForward("YeWuManage");
			}
	        else
	        {
		        java.util.List userList = um.getYeWuList("a.ID",gif);
		        actionContext.getRequest().setAttribute("userList", userList);
		        actionContext.getRequest().setAttribute("departList", departList);
		        actionContext.getRequest().setAttribute("gif", gif);
		        return actionContext.getMapping().findForward("YeWuManage");
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
