// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ExportBillSearchResultInfo.java

package mediastore.web.form;

import java.util.List;

public class ExportBillSearchResultInfo
{

    private int totalRecNum;
    private float totalPrice;//现金
    private float totalPrice1;//欠款预交
    private float totalPrice2;//发货预交
    private float totalPrice3;//欠款合计
    private float totalPrice4;//去零合计
    private float totalPrice5;//代金券
    private List exportBillList;

    public ExportBillSearchResultInfo()
    {
    }

    public List getExportBillList()
    {
        return exportBillList;
    }

    public int getTotalRecNum()
    {
        return totalRecNum;
    }
    
    public float getTotalPrice()
    {
        return totalPrice;
    }
    
    public void setExportBillList(List list)
    {
        exportBillList = list;
    }

    public void setTotalRecNum(int i)
    {
    	totalRecNum = i;
    }
    public void setTotalPrice(float i)
    {
    	totalPrice = i;
    }
    public void setTotalPrice1(float i)
    {
    	totalPrice1 = i;
    }
    public float getTotalPrice1()
    {
    	return totalPrice1;
    }
    public void setTotalPrice2(float i)
    {
    	totalPrice2 = i;
    }
    public float getTotalPrice2()
    {
    	return totalPrice2;
    }
    public void setTotalPrice3(float i)
    {
    	totalPrice3 = i;
    }
    public float getTotalPrice3()
    {
    	return totalPrice3;
    }
    public void setTotalPrice4(float i)
    {
    	totalPrice4 = i;
    }
    public float getTotalPrice4()
    {
    	return totalPrice4;
    }
    public void setTotalPrice5(float i)
    {
    	totalPrice5 = i;
    }
    public float getTotalPrice5()
    {
    	return totalPrice5;
    }
}
