// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsExportItemInfo.java

package mediastore.web.form;


public class GoodsExportItemInfo
{

    private int billId;
    private String goodsId;
    private String goodsName;
    private int exportAmount;
    private float exportUnitPrice;
    private String salesPerson;
    private String createTime;
    private String confirmFlage;
    private int memberId;
    private float discount;
    private float profit;

    public GoodsExportItemInfo()
    {
    }

    public int getBillId()
    {
        return billId;
    }

    public String getConfirmFlage()
    {
        return confirmFlage;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public int getExportAmount()
    {
        return exportAmount;
    }

    public float getExportUnitPrice()
    {
        return exportUnitPrice;
    }

    public String getGoodsId()
    {
        return goodsId;
    }

    public String getGoodsName()
    {
        return goodsName;
    }

    public int getMemberId()
    {
        return memberId;
    }

    public float getProfit()
    {
        return profit;
    }

    public String getSalesPerson()
    {
        return salesPerson;
    }

    public void setBillId(int i)
    {
        billId = i;
    }

    public void setConfirmFlage(String string)
    {
        confirmFlage = string;
    }

    public void setCreateTime(String string)
    {
        createTime = string;
    }

    public void setExportAmount(int i)
    {
        exportAmount = i;
    }

    public void setExportUnitPrice(float f)
    {
        exportUnitPrice = f;
    }

    public void setGoodsId(String string)
    {
        goodsId = string;
    }

    public void setGoodsName(String string)
    {
        goodsName = string;
    }

    public void setMemberId(int i)
    {
        memberId = i;
    }

    public void setProfit(float f)
    {
        profit = f;
    }

    public void setSalesPerson(String string)
    {
        salesPerson = string;
    }

    public float getDiscount()
    {
        return discount;
    }

    public void setDiscount(float f)
    {
        discount = f;
    }
}
