// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsSearchResultInfo.java

package mediastore.web.form;

import java.util.List;

public class GoodsSearchResultInfo
{

    private int totalRecNum;
    private List GoodsList;

    public GoodsSearchResultInfo()
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

    public List getGoodsList()
    {
        return GoodsList;
    }

    public void setGoodsList(List list)
    {
        GoodsList = list;
    }
}
