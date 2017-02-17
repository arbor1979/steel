// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportItemSearchConFB.java

package mediastore.web.form;

import java.util.List;

import mediastore.rule.ImportItemSearchRule;

// Referenced classes of package mediastore.web.form:
//            ImportItemSearchResultInfo

public class ImportItemSearchConFB
{

    private List billIdList;
    private List createPersonList;
    private List sendPersonList;
    private int pageRecNum;
    private int startRecNum;
    private ResultsetList iisri;
    private ImportItemSearchRule iisr;

    public ImportItemSearchConFB()
    {
    }

    public List getBillIdList()
    {
        return billIdList;
    }

    public List getCreatePersonList()
    {
        return createPersonList;
    }

    public void setBillIdList(List list)
    {
        billIdList = list;
    }

    public void setCreatePersonList(List list)
    {
        createPersonList = list;
    }
    public List getsendPersonList()
    {
        return sendPersonList;
    }
    public void setsendPersonList(List list)
    {
    	sendPersonList = list;
    }

    public ImportItemSearchRule getIisr()
    {
        return iisr;
    }

    public ResultsetList getIisri()
    {
        return iisri;
    }

    public int getPageRecNum()
    {
        return pageRecNum;
    }

    public int getStartRecNum()
    {
        return startRecNum;
    }

    public void setIisr(ImportItemSearchRule rule)
    {
        iisr = rule;
    }

    public void setIisri(ResultsetList info)
    {
        iisri = info;
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
