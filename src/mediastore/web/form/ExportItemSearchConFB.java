// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ExportItemSearchConFB.java

package mediastore.web.form;

import java.util.List;

import mediastore.rule.ExportItemSearchRule;

// Referenced classes of package mediastore.web.form:
//            ExportItemSearchResultInfo

public class ExportItemSearchConFB
{

    private List billIdList;
    private List salesPersonList;
    private int pageRecNum;
    private int startRecNum;
    private ExportItemSearchResultInfo eisri;
    private ExportItemSearchRule eisr;
    private float totalProfit;

    public ExportItemSearchConFB()
    {
    }

    public List getBillIdList()
    {
        return billIdList;
    }

    public List getSalesPersonList()
    {
        return salesPersonList;
    }

    public void setBillIdList(List list)
    {
        billIdList = list;
    }

    public void setSalesPersonList(List list)
    {
        salesPersonList = list;
    }

    public ExportItemSearchRule getEisr()
    {
        return eisr;
    }

    public ExportItemSearchResultInfo getEisri()
    {
        return eisri;
    }

    public int getPageRecNum()
    {
        return pageRecNum;
    }

    public int getStartRecNum()
    {
        return startRecNum;
    }

    public void setEisr(ExportItemSearchRule rule)
    {
        eisr = rule;
    }

    public void setEisri(ExportItemSearchResultInfo info)
    {
        eisri = info;
    }

    public void setPageRecNum(int i)
    {
        pageRecNum = i;
    }

    public void setStartRecNum(int i)
    {
        startRecNum = i;
    }

    public float getTotalProfit()
    {
        return totalProfit;
    }

    public void setTotalProfit(float f)
    {
        totalProfit = f;
    }
}
