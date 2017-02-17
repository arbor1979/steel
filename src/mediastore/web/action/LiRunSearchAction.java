// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportGoodsBillShowAction.java

package mediastore.web.action;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import mediastore.dao.ReportInfo;
import mediastore.user.DepartManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class LiRunSearchAction extends AbstractAction
{

    public LiRunSearchAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
		{
    		String groupby = oConvert.getString(actionContext.getParameter("groupby"),"d.goodstypename");
    		String orderby = oConvert.getString(actionContext.getParameter("orderby"),"3 desc");
    		String importTime1="";
			if(actionContext.getParameter("searchbutton")==null)
	            importTime1 = oConvert.FormDate(new java.util.Date(), "yyyy-MM-01 00:00:00");
			else
				importTime1=oConvert.getString(actionContext.getParameter("importtime1"),"");
    		String importTime2 = oConvert.getString(actionContext.getParameter("importtime2"),"");
    		int deptid = oConvert.getInt((String)actionContext.getParameter("deptid"),0);
    		UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
    		if(!ctx.isIfview() || deptid==0)
    			deptid=ctx.getDeptid();
			ReportInfo cw=new ReportInfo();
			List flowList=null;
	    	String filename="";
	    	flowList=cw.getLiRunList(importTime1, importTime2,deptid,groupby,orderby);
	    	if(flowList.size()<=20)
	    	{
	    		DefaultPieDataset dpd = new DefaultPieDataset();
	    		for(int i=0;i<flowList.size();i++)
				{
	    			String []tmp=(String[])flowList.get(i);
	    			dpd.setValue(tmp[0],oConvert.getDouble(tmp[5],0));
				}
	    		JFreeChart pieChart = ChartFactory.createPieChart("商品毛利统计",dpd,true,true,false);	
	    		FileOutputStream fos_jpg = null;		
	    		try 
	    		{	
					String path =actionContext.getRequest().getSession().getServletContext().getRealPath("\\") + "chart";
					File d = new File(path); 
					File list[] = d.listFiles(); 
					for(int i = 0; i < list.length; i++)
					{ 
						if(list[i].isFile())
						{
							Date dt=new Date(list[i].lastModified());
							if(oConvert.DateDivMinute(new Date(),dt)>=2)
								list[i].delete();
						}
					}
					filename="liRun"+oConvert.nextRandInt(10000)+".jpg";
					String filepath=path+"\\"+filename;
					File f=new File(filepath);
					f.deleteOnExit();
					fos_jpg = new FileOutputStream(filepath);			
					ChartUtilities.writeChartAsJPEG(fos_jpg,80,pieChart,900,450,null);		
				} 
				finally
				{			
					try 
					{				
						fos_jpg.close();			
					} catch (Exception e) {}		
				}	
	    	}
	    	DepartManager dm=new DepartManager();
	        java.util.List departList=dm.getDepartList("id");
	        actionContext.getRequest().setAttribute("departList", departList);
	    	actionContext.getRequest().setAttribute("flowList", flowList);
    		actionContext.getRequest().setAttribute("importTime1", importTime1);
    		actionContext.getRequest().setAttribute("importTime2", importTime2);
    		actionContext.getRequest().setAttribute("groupby", groupby);
    		actionContext.getRequest().setAttribute("orderby", orderby);
    		actionContext.getRequest().setAttribute("deptid", String.valueOf(deptid));
    		actionContext.getRequest().setAttribute("filename", filename);
	        return actionContext.getMapping().findForward("LiRunSearch");
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
