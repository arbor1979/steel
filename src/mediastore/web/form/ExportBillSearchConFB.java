// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ExportBillSearchConFB.java

package mediastore.web.form;

import java.util.List;

import mediastore.rule.ExportBillSearchRule;

// Referenced classes of package mediastore.web.form:
//            ExportBillSearchResultInfo

public class ExportBillSearchConFB
{

    private List billIdList;
    private List personBillIdList;
    private List MemberIdList;
    private List salesPersonsList;
    private int pageRecNum;
    private int startRecNum;
    private ExportBillSearchResultInfo ebsri;
    private ExportBillSearchRule ebsr;

    public ExportBillSearchConFB()
    {
    }

    public List getBillIdList()
    {
    	
        return billIdList;
    }
    public List getPersonBillIdList(int role)
    {
  
        return personBillIdList;
    }
    public List getMemberIdList()
    {
        return MemberIdList;
    }

    public List getSalesPersonsList()
    {
        return salesPersonsList;
    }

    public void setBillIdList(List list)
    {
        billIdList = list;
    }

    public void setMemberIdList(List list)
    {
        MemberIdList = list;
    }

    public void setSalesPersonsList(List list)
    {
        salesPersonsList = list;
    }

    public ExportBillSearchRule getEbsr()
    {
        return ebsr;
    }

    public ExportBillSearchResultInfo getEbsri()
    {
        return ebsri;
    }

    public int getPageRecNum()
    {
        return pageRecNum;
    }

    public int getStartRecNum()
    {
        return startRecNum;
    }

    public void setEbsr(ExportBillSearchRule rule)
    {
        ebsr = rule;
    }

    public void setEbsri(ExportBillSearchResultInfo info)
    {
        ebsri = info;
    }

    public void setPageRecNum(int i)
    {
        pageRecNum = i;
    }

    public void setStartRecNum(int i)
    {
        startRecNum = i;
    }
}
