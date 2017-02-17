// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   UserManageAction.java

package mediastore.web.action;

import mediastore.user.DepartManager;
import mediastore.user.FactoryManager;
import mediastore.user.YeWuManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.FactoryInfoForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.form.YeWuInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class FactoryManageAction extends AbstractAction
{

    public FactoryManageAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
	    	String param = oConvert.getString(actionContext.getParameter("param"),"list");
	    	String orderStr = oConvert.getString(actionContext.getParameter("orderby"),"a.id");
			int curpage = oConvert.getInt(actionContext.getParameter("curpage"),1);
	    	FactoryInfoForm fi=(FactoryInfoForm)actionContext.getForm();
			if(fi==null)
				fi=new FactoryInfoForm();
			YeWuManager yw = new YeWuManager();
			YeWuInfoForm ywif=new YeWuInfoForm();
	        java.util.List YeWuList = yw.getYeWuList("a.ID",ywif);
	        FactoryManager um = new FactoryManager();
	        DepartManager dm=new DepartManager();
	        java.util.List departList=dm.getDepartList("id");
			if (param.equals("add"))
			{
				fi.setKind("0000");
				fi.setTownid("1114");
		        actionContext.getRequest().setAttribute("fi",fi);
		        actionContext.getRequest().setAttribute("YeWuList",YeWuList);
		        actionContext.getRequest().setAttribute("departList",departList);
		        return actionContext.getMapping().findForward("FactoryManageEdit");
			}
			else if (param.equals("edit"))
			{
		        fi=um.getFactByID(fi.getId());
		        actionContext.getRequest().setAttribute("fi",fi);
		        actionContext.getRequest().setAttribute("YeWuList",YeWuList);
		        actionContext.getRequest().setAttribute("departList",departList);
		        return actionContext.getMapping().findForward("FactoryManageEdit");
			}
			else if(param.equals("export"))
			{
				ResultsetList userList = um.getFactListByPage(orderStr, fi,curpage,100000);
		    	actionContext.getRequest().setAttribute("fi",fi);
		        actionContext.getRequest().setAttribute("userList", userList);
		        return actionContext.getMapping().findForward("FactoryManageExcel");
			}
			else if (param.equals("save"))
			{
				if(oConvert.isEmpty(fi.getName()))
		        	throw new Exception("企业名称不能为空！");
				if(oConvert.isEmpty(fi.getTel()))
		        	throw new Exception("联系电话不能为空！");
				if(fi.getTownid()==null || fi.getTownid().equals("0"))
		        	throw new Exception("请选择所属区域！");
				if(fi.getKind()==null || fi.getKind().equals(""))
		        	throw new Exception("请选择类型！");
		        if(fi.getId()==0)
		        	um.insertUserRec(fi);
		        else
		        	um.updateUserRec(fi);
		        fi=new FactoryInfoForm();
		        
			}
			else if(param.equals("del"))
			{
				um.deleteUserRec(fi.getId());
				fi=new FactoryInfoForm();
				
			}
			ResultsetList userList = um.getFactListByPage(orderStr, fi,curpage,20);
	    	actionContext.getRequest().setAttribute("fi",fi);
	    	actionContext.getRequest().setAttribute("orderby",orderStr);
	        actionContext.getRequest().setAttribute("userList", userList);
	        actionContext.getRequest().setAttribute("departList",departList);
	        return actionContext.getMapping().findForward("FactoryManage");
			
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
