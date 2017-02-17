// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ExportBillSearchResultInfo.java

package mediastore.web.form;

import java.util.List;

public class ExportReportResultInfo
{

	private int totalRecNum;
	
    private float totalXjPrice;//现金
    private float totalQkPrice;//欠款
    private float totalFhPrice;//发货
    private List exportReportList;

    public ExportReportResultInfo()
    {
    }
    public void setTotalRecNum(int i)
    {
    	totalRecNum = i;
    }
   
    public void setExportReportList(List list)
    {
    	exportReportList = list;
    	
    }
    public void setTotalXjPrice(float i)
    {
    	totalXjPrice = i;
    }
    public void setTotalQkPrice(float i)
    {
    	totalQkPrice = i;
    }
    public void setTotalFhPrice(float i)
    {
    	totalFhPrice = i;
    }
   
   
    
    public int getTotalRecNum()
    {
        return totalRecNum;
    }
    public List getExportReportList()
    {
        return exportReportList;
    }
 
    public float getTotalXjPrice()
    {
        return totalXjPrice;
    }
    
    
    public float getTotalQkPrice()
    {
    	return totalQkPrice;
    }
   
    public float getTotalFhPrice()
    {
    	return totalFhPrice;
    }
   
   
    
    
}
