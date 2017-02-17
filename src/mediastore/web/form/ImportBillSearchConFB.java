// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportBillSearchConFB.java

package mediastore.web.form;

import java.util.List;

import mediastore.rule.ImportBillSearchRule;

// Referenced classes of package mediastore.web.form:
//            ImportBillSearchResultInfo

public class ImportBillSearchConFB
{

    private List billIdList;
    private List sendPersonsList;
    private List FactList;
    private List inceptPersonsList;
    private int pageRecNum;
    private int startRecNum;
    private ResultsetList ibsri;
    private ImportBillSearchRule ibsr;

    public ImportBillSearchConFB()
    {
    }

    public List getBillIdList()
    {
        return billIdList;
    }

    public void setBillIdList(List list)
    {
        billIdList = list;
    }
    public List getFactList()
    {
        return FactList;
    }

    public void setFactList(List list)
    {
        FactList = list;
    }

    public List getSendPersonsList()
    {
        return sendPersonsList;
    }

    public void setSendPersonsList(List list)
    {
        sendPersonsList = list;
    }

    public List getInceptPersonsList()
    {
        return inceptPersonsList;
    }

    public void setInceptPersonsList(List list)
    {
        inceptPersonsList = list;
    }

    public ResultsetList getIbsri()
    {
        return ibsri;
    }

    public int getStartRecNum()
    {
        return startRecNum;
    }

    public void setIbsri(ResultsetList info)
    {
        ibsri = info;
    }

    public void setStartRecNum(int i)
    {
        startRecNum = i;
    }

    public int getPageRecNum()
    {
        return pageRecNum;
    }

    public void setPageRecNum(int i)
    {
        pageRecNum = i;
    }

    public ImportBillSearchRule getIbsr()
    {
        return ibsr;
    }

    public void setIbsr(ImportBillSearchRule rule)
    {
        ibsr = rule;
    }
}
