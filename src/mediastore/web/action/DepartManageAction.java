// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   UserManageAction.java

package mediastore.web.action;

import java.io.FileWriter;

import mediastore.user.DepartManager;
import mediastore.util.oConvert;
import mediastore.web.form.DepartInfoForm;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class DepartManageAction extends AbstractAction
{

    public DepartManageAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
	    	String param = oConvert.getString(actionContext.getParameter("param"),"list");
	    	UserInfoForm  ctx=(UserInfoForm) actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
	    	DepartInfoForm fi=(DepartInfoForm)actionContext.getForm();
			if(fi==null)
				fi=new DepartInfoForm();
		
	        DepartManager um = new DepartManager();
			if (param.equals("add") && ctx.isIfview())
			{
		        actionContext.getRequest().setAttribute("fi",fi);
		        return actionContext.getMapping().findForward("DepartManageEdit");
			}
			else if (param.equals("edit") && ctx.isIfview())
			{
				
				fi=um.getFactByID(fi.getId());
		        actionContext.getRequest().setAttribute("fi",fi);
		        return actionContext.getMapping().findForward("DepartManageEdit");
			}
			else if (param.equals("save"))
			{
				if(oConvert.isEmpty(fi.getName()))
		        	throw new Exception("分公司名称不能为空！");
				if(oConvert.isEmpty(fi.getShortname()))
		        	throw new Exception("分公司简称不能为空！");
				if(fi.getShortname().length()>5)
		        	throw new Exception("分公司简称不能大于5个字！");
				if(oConvert.isEmpty(fi.getAddress()))
		        	throw new Exception("分公司地址不能为空！");
				if(oConvert.isEmpty(fi.getTel()))
		        	throw new Exception("分公司电话不能为空！");
				if(oConvert.isEmpty(fi.getLinkman()))
		        	throw new Exception("分公司联系人不能为空！");
				if(fi.getTownid()==0)
		        	throw new Exception("请选择所属区域！");
		        if(fi.getId()==0)
		        	um.insertUserRec(fi);
		        else
		        	um.updateUserRec(fi);
		        if(ctx.getDeptid()==fi.getId())
		        {
		        	java.io.File f=new java.io.File(actionContext.getRequest().getSession().getServletContext().getRealPath("\\html\\DeptName.htm"));
				     if(f.exists())
				    	 f.delete();
				     f.createNewFile();
				     FileWriter fw=new FileWriter(f);
				     fw.write("<body bgcolor='#F2F2F2'><div align=center><b>"+fi.getName()+"</b></div>");
				     fw.close();
		        }
		        fi=new DepartInfoForm();
		        java.util.List userList = um.getDepartList("ID",fi);
		        fi=um.getFactByID(ctx.getDeptid());
		        actionContext.getRequest().setAttribute("fi",fi);
		        actionContext.getRequest().setAttribute("userList", userList);
		        if(ctx.isIfview())
		        	return actionContext.getMapping().findForward("DepartManage");
		        else
		        {
		        	return actionContext.getMapping().findForward("DepartManageEdit");
		        	
		        }
		        
		        	
			}
			else if(param.equals("del") && ctx.isIfview())
			{
				um.deleteUserRec(fi.getId());
				fi=new DepartInfoForm();
		    	java.util.List userList = um.getDepartList("ID");
		    	actionContext.getRequest().setAttribute("fi",fi);
		        actionContext.getRequest().setAttribute("userList", userList);
		        return actionContext.getMapping().findForward("DepartManage");	
			}
			else 
			{
				
				if(param.equals("list"))
				{
					if(ctx.isIfview())
					{
				    	java.util.List userList = um.getDepartList("ID", fi);
				    	actionContext.getRequest().setAttribute("fi",fi);
				        actionContext.getRequest().setAttribute("userList", userList);
				        return actionContext.getMapping().findForward("DepartManage");
					}
					else
					{
						fi=um.getFactByID(ctx.getDeptid());
						actionContext.getRequest().setAttribute("fi",fi);
				        return actionContext.getMapping().findForward("DepartManageEdit");
					}
				}
				else
				{
					ErrorMsgFB ef=new ErrorMsgFB();
					if(param.equals("bakup"))
					{
						um.dataBakup("D:\\DataBak");
						ef.setSource("备份成功！");
		    			
					}
					if(param.equals("huifu"))
					{
						um.dataRestore("D:\\DataBak");
						ef.setSource("恢复成功！");
					}
					if(param.equals("clear"))
					{
						um.dataClear();
						ef.setSource("清除成功！");
					}
					if(param.equals("delete"))
					{
						java.util.Date begintime = oConvert.parseDate((String)actionContext.getParameter("begintime"));
						if(begintime==null)
							begintime = oConvert.parseDate("2007-01-01 00:00:00");
						java.util.Date endtime = oConvert.parseDate((String)actionContext.getParameter("endtime"));
						if(endtime==null)
							endtime=new java.util.Date();
						um.dataDelete(begintime,endtime,fi.getId());
						ef.setSource("清除出入库记录成功！");
					}
					actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
	     	        return actionContext.getMapping().findForward("Infors");
				}
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
