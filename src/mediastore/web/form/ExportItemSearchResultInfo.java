// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ExportItemSearchResultInfo.java

package mediastore.web.form;

import java.util.List;

public class ExportItemSearchResultInfo
{

    private int totalRecNum;
    private List exportItemList;
    private int totalSumNum;
    private float totalAmount;

    public ExportItemSearchResultInfo()
    {
    }

    public int getTotalRecNum()
    {
        return totalRecNum;
    }

    public void setTotalRecNum(int i)
    {
        totalRecNum = i;
    }

    public int getTotalSumNum()
    {
        return totalSumNum;
    }

    public void setTotalSumNum(int i)
    {
    	totalSumNum = i;
    }	
    
    public List getExportItemList()
    {
        return exportItemList;
    }

    public void setExportItemList(List list)
    {
        exportItemList = list;
    }

    public float getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(float i)
    {
        totalAmount = i;
    }
}
