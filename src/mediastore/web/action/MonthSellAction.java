// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportGoodsBillShowAction.java

package mediastore.web.action;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import mediastore.dao.GoodsInfo;
import mediastore.dao.ReportInfo;
import mediastore.user.DepartManager;
import mediastore.user.StoreManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.GoodsImportGoodsInfo;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class MonthSellAction extends AbstractAction
{

    public MonthSellAction()
    {
    }

   
    public ActionForward execute(ActionContext actionContext)
    {
    	try
		{
    		GoodsImportGoodsInfo gif=(GoodsImportGoodsInfo)actionContext.getForm();
			if(gif==null)
				gif=new GoodsImportGoodsInfo();
    		String qijian=oConvert.getString((String)actionContext.getParameter("qijian"),oConvert.FormDate(new Date(), "yyyy"));
    		int classid = oConvert.getInt((String)actionContext.getParameter("classid"),0);
    		int typeid = oConvert.getInt((String)actionContext.getParameter("typeid"),0);
    		UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
    		if(!ctx.isIfview() || gif.getDeptid()<=0)
    			gif.setDeptid(ctx.getDeptid());
			ReportInfo cw=new ReportInfo();
			String filename="";
			List flowList=cw.getMonthSell(qijian,gif.getDeptid(),gif.getStoreId(),classid,typeid,gif.getChandi());
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();	
			double tmp1[]=(double[])flowList.get(0);
			double tmp2[]=(double[])flowList.get(1);
			for(int i=0;i<tmp1.length;i++)
			{
				dataset.addValue(tmp1[i], "入库", i+1+"月");	
				dataset.addValue(tmp2[i], "出库", i+1+"月");	
			}
			JFreeChart chart = ChartFactory.createBarChart3D(qijian+"年各月商品进销情况", // 图表标题	
					"月份", // 目录轴的显示标签	
					"重量(吨)", // 数值轴的显示标签							
					dataset,// 数据集							
					PlotOrientation.VERTICAL, // 图表方向：水平、垂直						
					true, // 是否显示图例(对于简单的柱状图必须是false)	
					false,// 是否生成工具	
					false// 是否生成URL链接
					);	
			
			
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
				filename="monthSell"+oConvert.nextRandInt(10000)+".jpg";
				String filepath=path+"\\"+filename;
				File f=new File(filepath);
				f.deleteOnExit();
				fos_jpg = new FileOutputStream(filepath);			
				ChartUtilities.writeChartAsJPEG(fos_jpg,80,chart,900,450,null);		
			} 
			finally
			{			
				try 
				{				
					fos_jpg.close();			
				} catch (Exception e) {}		
			}		
	    	List qjList=cw.getYearList(gif.getDeptid());
	    	DepartManager dm=new DepartManager();
	        java.util.List departList=dm.getDepartList("id");
	        StoreManager sm=new StoreManager();
	        List StoreList = sm.getDepartList("id",gif.getDeptid());
	        GoodsInfo gcc = new GoodsInfo();
	        List cdList=gcc.getChanDiList();
	        actionContext.getRequest().setAttribute("flowList", flowList);
	        actionContext.getRequest().setAttribute("departList", departList);
	        actionContext.getRequest().setAttribute("cdList", cdList);
	        actionContext.getRequest().setAttribute("StoreList", StoreList);
	    	actionContext.getRequest().setAttribute("classid", String.valueOf(classid));
	    	actionContext.getRequest().setAttribute("typeid", String.valueOf(typeid));
	    	actionContext.getRequest().setAttribute("gif", gif);
	    	actionContext.getRequest().setAttribute("qjList", qjList);
	    	actionContext.getRequest().setAttribute("qijian", qijian);
	    	actionContext.getRequest().setAttribute("filename", filename);
	        return actionContext.getMapping().findForward("MonthSell");
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
