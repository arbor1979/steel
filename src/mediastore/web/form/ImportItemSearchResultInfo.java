// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportItemSearchResultInfo.java

package mediastore.web.form;

import java.util.List;

public class ImportItemSearchResultInfo
{

    private int totalRecNum;
    private List importItemList;

    public ImportItemSearchResultInfo()
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

    public List getImportItemList()
    {
        return importItemList;
    }

    public void setImportItemList(List list)
    {
        importItemList = list;
    }
}
