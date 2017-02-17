// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportBillSearchResultInfo.java

package mediastore.web.form;

import java.util.List;

public class ImportBillSearchResultInfo
{

    private int totalRecNum;
    private List importBillList;

    public ImportBillSearchResultInfo()
    {
    }

    public List getImportBillList()
    {
        return importBillList;
    }

    public int getTotalRecNum()
    {
        return totalRecNum;
    }

    public void setImportBillList(List list)
    {
        importBillList = list;
    }

    public void setTotalRecNum(int i)
    {
        totalRecNum = i;
    }
}
