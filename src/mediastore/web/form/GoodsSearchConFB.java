// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsSearchConFB.java

package mediastore.web.form;

import java.util.List;

import mediastore.rule.GoodsSearchRule;

// Referenced classes of package mediastore.web.form:
//            GoodsSearchResultInfo

public class GoodsSearchConFB
{

    private List goodsTypeList;
    private List goodsClassList;
    private List goodsShelfList;
    private List goodsCreatorsList;
    private int pageRecNum;
    private int startRecNum;
    private GoodsSearchResultInfo gsri;
    private GoodsSearchRule gsr;
    private List allGoodsTypeList;
    private List allGoodsClassList;
    private List allGoodsShelfIdList;

    public GoodsSearchConFB()
    {
    }

    public List getGoodsTypeList()
    {
        return goodsTypeList;
    }

    public void setGoodsTypeList(List list)
    {
        goodsTypeList = list;
    }

    public List getGoodsClassList()
    {
        return goodsClassList;
    }

    public void setGoodsClassList(List list)
    {
        goodsClassList = list;
    }

    public List getGoodsShelfList()
    {
        return goodsShelfList;
    }

    public void setGoodsShelfList(List list)
    {
        goodsShelfList = list;
    }

    public List getGoodsCreatorsList()
    {
        return goodsCreatorsList;
    }

    public void setGoodsCreatorsList(List list)
    {
        goodsCreatorsList = list;
    }

    public GoodsSearchRule getGsr()
    {
        return gsr;
    }

    public GoodsSearchResultInfo getGsri()
    {
        return gsri;
    }

    public int getPageRecNum()
    {
        return pageRecNum;
    }

    public int getStartRecNum()
    {
        return startRecNum;
    }

    public void setGsr(GoodsSearchRule rule)
    {
        gsr = rule;
    }

    public void setGsri(GoodsSearchResultInfo info)
    {
        gsri = info;
    }

    public void setPageRecNum(int i)
    {
        pageRecNum = i;
    }

    public void setStartRecNum(int i)
    {
        startRecNum = i;
    }

    public List getAllGoodsTypeList()
    {
        return allGoodsTypeList;
    }

    public void setAllGoodsTypeList(List list)
    {
        allGoodsTypeList = list;
    }

    public List getAllGoodsClassList()
    {
        return allGoodsClassList;
    }

    public List getAllGoodsShelfIdList()
    {
        return allGoodsShelfIdList;
    }

    public void setAllGoodsClassList(List list)
    {
        allGoodsClassList = list;
    }

    public void setAllGoodsShelfIdList(List list)
    {
        allGoodsShelfIdList = list;
    }
}
