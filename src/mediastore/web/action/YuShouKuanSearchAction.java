// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   UserManageAction.java

package mediastore.web.action;

import mediastore.dao.CaiWu;
import mediastore.user.DepartManager;
import mediastore.user.FactoryManager;
import mediastore.user.TruckManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.FactoryInfoForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.form.TruckInfoForm;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class YuShouKuanSearchAction extends AbstractAction
{

    public YuShouKuanSearchAction()
    {
    }

    @SuppressWarnings("unchecked")
	public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
    		String param = oConvert.getString(actionContext.getParameter("param"),"group");
    		int kind= oConvert.getInt((String)actionContext.getParameter("kind"),1);
			int factory=oConvert.getInt((String)actionContext.getParameter("factory"),0);
			int deptid=oConvert.getInt((String)actionContext.getParameter("deptid"),-1);
			String importTime1="";
	        String importTime2 = actionContext.getParameter("importtime2");
	        if(importTime2 == null)
	            importTime2 = "";
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
			if(!ctx.isIfview() || deptid==-1)
				deptid=ctx.getDeptid();
    		CaiWu cw=new CaiWu();
    		if(param.equals("group"))
    		{
    			if(actionContext.getParameter("searchbutton")==null)
    	            importTime1 = oConvert.FormDate(new java.util.Date(), "yyyy-MM-01 00:00:00");
    			else
    				importTime1=oConvert.getString(actionContext.getParameter("importtime1"),"");
    			String zhujima = oConvert.getString(actionContext.getParameter("zhujima"),"");
    			ResultsetList ys = cw.getChongZhiGroupList(kind,factory,deptid,importTime1,importTime2);
    			if(kind==1 || kind==-1)
    			{
	    			FactoryManager fum = new FactoryManager();
	    			FactoryInfoForm fi=new FactoryInfoForm();
	    			if(kind==1)
	    				fi.setKind("011");
	    			else if(kind==-1)
	    				fi.setKind("100");
	    			fi.setZhujima(zhujima);
	    	        java.util.List FactList = fum.getFactList("a.name",fi);
	    	        actionContext.getRequest().setAttribute("FactList", FactList);
    			}
    			else
    			{
    				TruckManager um = new TruckManager();
    				TruckInfoForm fi=new TruckInfoForm();
    				java.util.List truckList = um.getTruckList("a.carno", fi);
    				actionContext.getRequest().setAttribute("truckList", truckList);
    			}
    	        DepartManager dm=new DepartManager();
    	        java.util.List departList=dm.getDepartList("id");
    	        
    	        actionContext.getRequest().setAttribute("deptid", String.valueOf(deptid));
    	        actionContext.getRequest().setAttribute("departList", departList);
    			
    			actionContext.getRequest().setAttribute("zhujima", zhujima);
		        actionContext.getRequest().setAttribute("factory", String.valueOf(factory));
		        actionContext.getRequest().setAttribute("ys", ys);
				actionContext.getRequest().setAttribute("kind", String.valueOf(kind));
				actionContext.getRequest().setAttribute("importTime1", importTime1);
				actionContext.getRequest().setAttribute("importTime2", importTime2);
		        return actionContext.getMapping().findForward("YuShouKuanGroupSearch");
    		}
    		else if(param.equals("detail"))
    		{
		    	
    			int curpage= oConvert.getInt((String)actionContext.getParameter("curpage"),1);
    			importTime1=oConvert.getString(actionContext.getParameter("importtime1"),"");
    			ResultsetList ys=null;
    			String fName="";
    			if(kind==-1 || kind==1)
    			{
    	        	ys = cw.getChongZhiList(factory,deptid,kind,curpage,importTime1,importTime2);
    	        	FactoryManager fm=new FactoryManager();
    		        FactoryInfoForm fif=fm.getFactByID(factory);
    		        fName=fif.getName();
    			}
    	        else
    	        {
    	        	ys = cw.getYunFeiYuFuList(factory,deptid,kind,curpage,importTime1,importTime2);
    	        	TruckManager um = new TruckManager();
    				TruckInfoForm fi=new TruckInfoForm();
    				fi=um.getTruckByID(factory);
    				fName=fi.getCarno()+"("+fi.getDriver()+")";
    	        }
		        
		        actionContext.getRequest().setAttribute("ys", ys);
		        actionContext.getRequest().setAttribute("fName", fName);
		        actionContext.getRequest().setAttribute("factory", String.valueOf(factory));
				actionContext.getRequest().setAttribute("kind", String.valueOf(kind));
				actionContext.getRequest().setAttribute("opertime1", importTime1);
				actionContext.getRequest().setAttribute("opertime2", importTime2);
		        return actionContext.getMapping().findForward("YuShouKuanSearch");
    		}
    		return null;
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
