// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportItemSearchRule.java

package mediastore.rule;

import mediastore.web.form.ChangeStoreGoodsForm;


public class ChangeStoreItemSearchRule
{

    private int curpage;
    private String importTime1;
    private String importTime2;
    private String orderStr;
    private String goodsClass;
    private String goodsType;
    ChangeStoreGoodsForm gif;
    
	public ChangeStoreItemSearchRule()
    {
    }

    

    public String getOrderStr()
    {
        return orderStr;
    }

    public void setOrderStr(String string)
    {
        orderStr = string;
    }

    public void setGoodsClass(String id)
    {
        goodsClass = id;
    }
    public String getGoodsClass()
    {
        return goodsClass;
    }
    public void setGoodsType(String id)
    {
        goodsType = id;
    }
    public String getGoodsType()
    {
        return goodsType;
    }

	public int getCurpage() {
		return curpage;
	}



	public void setCurpage(int curpage) {
		this.curpage = curpage;
	}



	public String getImportTime1() {
		return importTime1;
	}



	public void setImportTime1(String importTime1) {
		this.importTime1 = importTime1;
	}



	public String getImportTime2() {
		return importTime2;
	}



	public void setImportTime2(String importTime2) {
		this.importTime2 = importTime2;
	}



	public ChangeStoreGoodsForm getGif() {
		return gif;
	}



	public void setGif(ChangeStoreGoodsForm gif) {
		this.gif = gif;
	}

}
