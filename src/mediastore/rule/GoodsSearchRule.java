// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsSearchRule.java

package mediastore.rule;


public class GoodsSearchRule
{  
    private String goodsId;
    private int repertoryAmount;
    private float purchaseUnitPrice;
    private String goodsType;
    private String goodsClass;
    private String goodsShelfId;
    private int curpage;
    private int pagesize;

    public GoodsSearchRule()
    {
    }

    public String getGoodsId()
    {
        return goodsId;
    }
    public void setGoodsId(String string)
    {
        goodsId = string;
    }
    
    public String getGoodsClass()
    {
        return goodsClass;
    }
    public void setGoodsClass(String string)
    {
        goodsClass = string;
    }

    public String getGoodsShelfId()
    {
        return goodsShelfId;
    }
    public void setGoodsShelfId(String string)
    {
        goodsShelfId = string;
    }
    
    public String getGoodsType()
    {
        return goodsType;
    }
    public void setGoodsType(String string)
    {
        goodsType = string;
    }
    
    public int getrepertoryAmount()
    {
        return repertoryAmount;
    }
    public void setrepertoryAmount(int i)
    {
    	repertoryAmount=i;
    }
    
    public float getpurchaseUnitPrice()
    {
        return purchaseUnitPrice;
    }
    public void setpurchaseUnitPrice(float i)
    {
    	purchaseUnitPrice=i;
    }
    
    public int getcurpage()
    {
        return curpage;
    }
    public void setcurpage(int i)
    {
    	curpage=i;
    }
    
    public int getpagesize()
    {
        return pagesize;
    }
    public void setpagesize(int i)
    {
    	pagesize=i;
    }

}
