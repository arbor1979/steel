// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportGoodsBillShowAction.java

package mediastore.web.action;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mediastore.dao.ReportInfo;
import mediastore.user.DepartManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.PriceChangeForm;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.PeriodAxisLabelInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class SellMonthRepAction extends AbstractAction
{

    public SellMonthRepAction()
    {
    }
    static Class class$org$jfree$data$time$Day; /* synthetic field */
    static Class class$org$jfree$data$time$Month; /* synthetic field */
    static Class class$org$jfree$data$time$Year; /* synthetic field */
    static Class class$(String s)
    {
    	try
    	{
    		return Class.forName(s);
    	}
    	catch(ClassNotFoundException classnotfoundexception)
    	{
    		throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    	}
    }
    public ActionForward execute(ActionContext actionContext)
    {
    	try
		{
    		String ifprint = oConvert.getString(actionContext.getParameter("ifprint"),"0");
    		PriceChangeForm pcf=new PriceChangeForm();
    		pcf=(PriceChangeForm)actionContext.getForm();
    		if(pcf.getQijian().length()==0)
    			pcf.setQijian(oConvert.FormDate(new Date(), "yyyy"));
    		if(pcf.getQijian1().length()==0)
    			pcf.setQijian1(oConvert.FormDate(new Date(), "MM"));
    		String qijian=pcf.getQijian();
    		int deptid=pcf.getDeptid();
    		UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
    		if(!ctx.isIfview() || deptid<=0)
    			deptid=ctx.getDeptid();
			ReportInfo cw=new ReportInfo();
			String filename="";
			String caizhi[]=new String[9];
	        caizhi[0]="35#";
	        caizhi[1]="45#";
	        caizhi[2]="20CrMnTi";
	        caizhi[3]="30CrMnTi";
	        caizhi[4]="35CrMo";
	        caizhi[5]="42CrMo";
	        caizhi[6]="40Cr";
	        caizhi[7]="27SiMn";
	        caizhi[8]="";
	        double tmp[][]=null;
			if(pcf.getKind().equals("month"))
				tmp=cw.getMonthChangeByCaizhi(qijian,caizhi,deptid);
			else
				tmp=cw.getDayChangeByCaizhi(qijian,pcf.getQijian1(),caizhi,deptid);
			
			TimeSeries timeseries[]=new TimeSeries[caizhi.length];
			TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		    if(pcf.getKind().equals("month"))
	    	{
		    	for(int i=0;i<caizhi.length;i++)
		    	{
		    		if(caizhi[i].length()==0)
		    			caizhi[i]="其它";
		    		timeseries[i] = new TimeSeries(caizhi[i], class$org$jfree$data$time$Month != null ? class$org$jfree$data$time$Month : (class$org$jfree$data$time$Month = class$("org.jfree.data.time.Month")));
		    		for(int j=0;j<tmp.length;j++)
		    		{
		    			timeseries[i].add(new Month(j+1, oConvert.getInt(qijian, 0)),(tmp[j][i]==0?null:tmp[j][i]));
		    		}
		    		timeseriescollection.addSeries(timeseries[i]);
		    	}			    	
			}    
		    else
		    {
		    	for(int i=0;i<caizhi.length;i++)
		    	{
		    		if(caizhi[i].length()==0)
		    			caizhi[i]="其它";
		    		timeseries[i] = new TimeSeries(caizhi[i], class$org$jfree$data$time$Day != null ? class$org$jfree$data$time$Day : (class$org$jfree$data$time$Day = class$("org.jfree.data.time.Day")));
		    		for(int j=0;j<tmp.length;j++)
		    		{
		    			timeseries[i].add(new Day(j+1, oConvert.getInt(pcf.getQijian1(), 0),oConvert.getInt(qijian, 0)),(tmp[j][i]==0?null:tmp[j][i]));
		    		}
		    		timeseriescollection.addSeries(timeseries[i]);
		    	}
		    	
		    }
		    
	        JFreeChart chart=null;
	        if(pcf.getKind().equals("month"))
	        {
	        	chart = ChartFactory.createTimeSeriesChart(qijian+"各月不同材质销售变化",
		        		"月份", 
		        		"重量(吨)",
		        		timeseriescollection, true, true, false);
	        	XYPlot xyplot = chart.getXYPlot();
		        DateAxis dateaxis = (DateAxis)xyplot.getDomainAxis();
	        	dateaxis.setTickUnit(new DateTickUnit(1, 1, new SimpleDateFormat("MM月")));
		        StandardXYItemRenderer standardxyitemrenderer = (StandardXYItemRenderer)xyplot.getRenderer();
		        standardxyitemrenderer.setPlotShapes(true);
		        standardxyitemrenderer.setSeriesShapesFilled(0, Boolean.TRUE);
		        standardxyitemrenderer.setSeriesShapesFilled(1, Boolean.FALSE);
	        }
	        else
	        {
	        	chart = ChartFactory.createTimeSeriesChart(qijian+"年"+pcf.getQijian1()+"月不同材质销售变化",
		        		"日", 
		        		"重量(吨)",
		        		timeseriescollection, true, true, false);
	        	
	            
	            XYPlot xyplot = chart.getXYPlot();
	            org.jfree.chart.renderer.xy.XYItemRenderer xyitemrenderer = xyplot.getRenderer();
	            if(xyitemrenderer instanceof StandardXYItemRenderer)
	            {
	                StandardXYItemRenderer standardxyitemrenderer = (StandardXYItemRenderer)xyitemrenderer;
	                standardxyitemrenderer.setPlotShapes(true);
	                standardxyitemrenderer.setShapesFilled(true);
	                standardxyitemrenderer.setItemLabelsVisible(true);
	            }
	            PeriodAxis periodaxis = new PeriodAxis("日");
	            periodaxis.setAutoRangeTimePeriodClass(class$org$jfree$data$time$Day != null ? class$org$jfree$data$time$Day : (class$org$jfree$data$time$Day = class$("org.jfree.data.time.Day")));
	            PeriodAxisLabelInfo aperiodaxislabelinfo[] = new PeriodAxisLabelInfo[1];
	            aperiodaxislabelinfo[0] = new PeriodAxisLabelInfo(class$org$jfree$data$time$Day != null ? class$org$jfree$data$time$Day : (class$org$jfree$data$time$Day = class$("org.jfree.data.time.Day")), "d");
	            periodaxis.setLabelInfo(aperiodaxislabelinfo);
	            xyplot.setDomainAxis(periodaxis);
	        }
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
				filename="SellChangeByCaizhi"+oConvert.nextRandInt(10000)+".jpg";
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
			
	    	List qjList=cw.getYearList(deptid);
	    	DepartManager dm=new DepartManager();
	        java.util.List departList=dm.getDepartList("id");
	        actionContext.getRequest().setAttribute("ifprint", ifprint);
	        actionContext.getRequest().setAttribute("tmp1", tmp);
	        actionContext.getRequest().setAttribute("caizhi", caizhi);
	        actionContext.getRequest().setAttribute("departList", departList);
	    	actionContext.getRequest().setAttribute("pcf",pcf);
	    	actionContext.getRequest().setAttribute("qjList", qjList);
	    	actionContext.getRequest().setAttribute("filename", filename);
	    	if(ifprint.equals("0"))
	    		return actionContext.getMapping().findForward("SellMonthRep");
	    	else if(ifprint.equals("1"))
	    		return actionContext.getMapping().findForward("SellMonthRepPrint");
	    	else
	    		return actionContext.getMapping().findForward("SellMonthRepExcel");
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
