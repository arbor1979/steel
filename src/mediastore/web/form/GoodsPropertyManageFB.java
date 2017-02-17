// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsPropertyManageFB.java

package mediastore.web.form;

import java.util.List;

public class GoodsPropertyManageFB
{

    private List goodsTypeList; //子类
    private List goodsClassList;//大类
    private List goodsShelfList;//分组
    private String classid;		//大类ID

    public GoodsPropertyManageFB()
    {
    }

    public List getGoodsClassList()
    {
        return goodsClassList;
    }

    public String getclassid()
    {
        return classid;
    }

    public List getGoodsTypeList()
    {
        return goodsTypeList;
    }

    public void setGoodsClassList(List list)
    {
        goodsClassList = list;
    }

    public void setclassid(String list)
    {
    	classid = list;
    }

    public void setGoodsTypeList(List list)
    {
        goodsTypeList = list;
    }
    
    public void setgoodsShelfList(List list)
    {
    	goodsShelfList = list;
    }
    public List getgoodsShelfList()
    {
        return goodsShelfList;
    }
}
