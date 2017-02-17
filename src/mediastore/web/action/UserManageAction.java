// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   UserManageAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.user.DepartManager;
import mediastore.user.UserManager;
import mediastore.util.oConvert;
import mediastore.web.form.DepartInfoForm;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class UserManageAction extends AbstractAction
{

    public UserManageAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
	    	String param = oConvert.getString(actionContext.getParameter("param"),"search");
	    	UserInfoForm  ctx=(UserInfoForm) actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
	    	UserInfoForm gif=(UserInfoForm)actionContext.getForm();
			if(gif==null)
				gif=new UserInfoForm();
			UserManager um = new UserManager();
			DepartManager dm = new DepartManager();
	        DepartInfoForm fi=new DepartInfoForm();
	        java.util.List deptList = dm.getDepartList("ID",fi);
			if(param.equals("add") || param.equals("edit"))
			{
				List menu=null;
				List menusub=null;
				if(param.equals("edit"))
				{
					gif=um.getUserbyID(gif.getId());
					menu=um.getMenuList(true);
					menusub=um.getMenuSubList(true);
				}
				else
				{
					menu=um.getMenuList(true);
					menusub=um.getMenuSubList(true);
				}
				actionContext.getRequest().setAttribute("deptList", deptList);
				actionContext.getRequest().setAttribute("gif",gif);
				actionContext.getRequest().setAttribute("menu",menu);
				actionContext.getRequest().setAttribute("menusub",menusub);
				return actionContext.getMapping().findForward("UserManageEdit");
			}
			if(param.equals("del"))
			{
				gif=um.getUserbyID(gif.getId());
				if(gif.getUserName().equals("admin"))
					throw new Exception("不能删除管理员帐户！");
				um.deleteUserRec(gif);
			}
			if(param.equals("save"))
			{
				um.insertUserRec(gif);
			}
			
	        java.util.List userList=null;
	        if(ctx.isIfview())
	        	userList = um.getUserList("createtime",gif.getDeptid());
	        else
	        	userList = um.getUserList("createtime",ctx.getDeptid());
			actionContext.getRequest().setAttribute("userList", userList);
			actionContext.getRequest().setAttribute("departList", deptList);
			actionContext.getRequest().setAttribute("gif", gif);
	        return actionContext.getMapping().findForward("UserManage");
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
