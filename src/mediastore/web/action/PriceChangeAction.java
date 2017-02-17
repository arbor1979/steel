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

import mediastore.dao.GoodsExportGoods;
import mediastore.dao.GoodsInfo;
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

public class PriceChangeAction extends AbstractAction
{

    public PriceChangeAction()
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
    		PriceChangeForm pcf=new PriceChangeForm();
    		pcf=(PriceChangeForm)actionContext.getForm();
    		if(pcf.getQijian().length()==0)
    			pcf.setQijian(oConvert.FormDate(new Date(), "yyyy"));
    		if(pcf.getQijian1().length()==0)
    			pcf.setQijian1(oConvert.FormDate(new Date(), "MM"));
    		String goodsid=pcf.getGoodsid();
    		String qijian=pcf.getQijian();
    		int deptid=pcf.getDeptid();
    		UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
    		if(!ctx.isIfview() || deptid<=0)
    			deptid=ctx.getDeptid();
			ReportInfo cw=new ReportInfo();
			String filename="";
			List flowList=null;
			String []nameguige=null;
			nameguige=goodsid.split(",");
			if(nameguige.length!=2)
			{
				GoodsExportGoods geg=new GoodsExportGoods();
				nameguige=geg.getMaxSellByNameGuige();
				goodsid=nameguige[0]+","+nameguige[1];
				pcf.setGoodsid(goodsid);
			}
			if(nameguige!=null && nameguige.length==2)
			{
				if(pcf.getKind().equals("month"))
					flowList=cw.getPriceChange(qijian,deptid,nameguige[0],nameguige[1]);
				else
					flowList=cw.getPriceChangeDay(qijian,pcf.getQijian1(),deptid,nameguige[0],nameguige[1]);
				double tmp1[]=(double[])flowList.get(0);
				double tmp2[]=(double[])flowList.get(1);
				TimeSeries timeseries=null;
				TimeSeries timeseries1=null;
			    if(pcf.getKind().equals("month"))
		    	{
					timeseries = new TimeSeries("入库单价", class$org$jfree$data$time$Month != null ? class$org$jfree$data$time$Month : (class$org$jfree$data$time$Month = class$("org.jfree.data.time.Month")));		        
				    timeseries1 = new TimeSeries("出库单价", class$org$jfree$data$time$Month != null ? class$org$jfree$data$time$Month : (class$org$jfree$data$time$Month = class$("org.jfree.data.time.Month")));
			    	for(int i=0;i<tmp1.length;i++)
			    	{
			    		timeseries.add(new Month(i+1, oConvert.getInt(qijian, 0)),(tmp1[i]==0?null:tmp1[i]));
			    		timeseries1.add(new Month(i+1, oConvert.getInt(qijian, 0)), (tmp2[i]==0?null:tmp2[i]));
			    	}			    	
				}    
			    else
			    {
					timeseries = new TimeSeries("入库单价", class$org$jfree$data$time$Day != null ? class$org$jfree$data$time$Day : (class$org$jfree$data$time$Day = class$("org.jfree.data.time.Day")));		        
				    timeseries1 = new TimeSeries("出库单价", class$org$jfree$data$time$Day != null ? class$org$jfree$data$time$Day : (class$org$jfree$data$time$Day = class$("org.jfree.data.time.Day")));
				    for(int i=0;i<tmp1.length;i++)
			    	{
				    	if(tmp1[i]!=0)
				    		timeseries.add(new Day(i+1, oConvert.getInt(pcf.getQijian1(), 0),oConvert.getInt(qijian, 0)), (tmp1[i]==0?null:tmp1[i]));
				    	if(tmp2[i]!=0)
				    	timeseries1.add(new Day(i+1, oConvert.getInt(pcf.getQijian1(), 0),oConvert.getInt(qijian, 0)), (tmp2[i]==0?null:tmp2[i]));
			    	}
		    	
			    }
			    TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		        timeseriescollection.addSeries(timeseries);
		        timeseriescollection.addSeries(timeseries1);
		        JFreeChart chart=null;
		        if(pcf.getKind().equals("month"))
		        {
		        	chart = ChartFactory.createTimeSeriesChart(qijian+"年各月商品价格变化",
			        		"月份", 
			        		"价格(元/吨)",
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
		        	chart = ChartFactory.createTimeSeriesChart(qijian+"年"+pcf.getQijian1()+"月商品价格变化",
			        		"日", 
			        		"价格(元/吨)",
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
					filename="priceChange"+oConvert.nextRandInt(10000)+".jpg";
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
			}
	    	List qjList=cw.getYearList(deptid);
	    	DepartManager dm=new DepartManager();
	        java.util.List departList=dm.getDepartList("id");
	        GoodsInfo gi=new GoodsInfo();
	        List ngList=gi.getNameGuigeList();
	        actionContext.getRequest().setAttribute("flowList", flowList);
	        actionContext.getRequest().setAttribute("ngList", ngList);
	        actionContext.getRequest().setAttribute("departList", departList);
	    	actionContext.getRequest().setAttribute("pcf",pcf);
	    	actionContext.getRequest().setAttribute("qjList", qjList);
	    	actionContext.getRequest().setAttribute("filename", filename);
	        return actionContext.getMapping().findForward("PriceChange");
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
