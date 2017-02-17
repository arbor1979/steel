// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   UserManageAction.java

package mediastore.web.action;

import mediastore.user.DepartManager;
import mediastore.user.TruckManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.TruckInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class TruckManageAction extends AbstractAction
{

    public TruckManageAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
	    	String param = oConvert.getString(actionContext.getParameter("param"),"list");
	    	String orderStr = oConvert.getString(actionContext.getParameter("orderby"),"a.carno");
	    	TruckInfoForm fi=(TruckInfoForm)actionContext.getForm();
			if(fi==null)
				fi=new TruckInfoForm();
			TruckManager um = new TruckManager();
			DepartManager dm = new DepartManager();
	        java.util.List departList = dm.getDepartList("ID");
			if (param.equals("add"))
			{
		        actionContext.getRequest().setAttribute("fi",fi);
		        actionContext.getRequest().setAttribute("departList", departList);
		        return actionContext.getMapping().findForward("TruckManageEdit");
			}
			else if (param.equals("edit"))
			{
		        fi=um.getTruckByID(fi.getId());
		        actionContext.getRequest().setAttribute("fi",fi);
		        actionContext.getRequest().setAttribute("departList", departList);
		        return actionContext.getMapping().findForward("TruckManageEdit");
			}
			else if (param.equals("save"))
			{
				if(oConvert.isEmpty(fi.getDriver()))
		        	throw new Exception("车主名称不能为空！");
				if(oConvert.isEmpty(fi.getCarno()))
		        	throw new Exception("车牌号不能为空！");
				
		        if(fi.getId()==0)
		        	um.insertUserRec(fi);
		        else
		        	um.updateUserRec(fi);
		        fi=new TruckInfoForm();
			}
			else if(param.equals("del"))
			{
				um.deleteUserRec(fi.getId());
				fi=new TruckInfoForm();
			}
	    	java.util.List userList = um.getTruckList(orderStr, fi);
	    	actionContext.getRequest().setAttribute("orderby",orderStr);
	    	actionContext.getRequest().setAttribute("fi",fi);
	        actionContext.getRequest().setAttribute("userList", userList);
	        actionContext.getRequest().setAttribute("departList", departList);
	        return actionContext.getMapping().findForward("TruckManage");
			
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
