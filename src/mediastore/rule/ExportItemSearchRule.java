// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ExportItemSearchRule.java

package mediastore.rule;

import mediastore.web.form.GoodsExportGoodsInfo;


public class ExportItemSearchRule
{

	private int curpage;
    private String oper;
    private String exportTime1;
    private String exportTime2;
    private String orderStr;
    private String goodsClass;
    private String goodsType;
    GoodsExportGoodsInfo gif;
   
    public int getCurpage() {
		return curpage;
	}

	public void setCurpage(int curpage) {
		this.curpage = curpage;
	}

	public String getExportTime1() {
		return exportTime1;
	}

	public void setExportTime1(String exportTime1) {
		this.exportTime1 = exportTime1;
	}

	public String getExportTime2() {
		return exportTime2;
	}

	public void setExportTime2(String exportTime2) {
		this.exportTime2 = exportTime2;
	}

	public GoodsExportGoodsInfo getGif() {
		return gif;
	}

	public void setGif(GoodsExportGoodsInfo gif) {
		this.gif = gif;
	}

	public String getGoodsClass() {
		return goodsClass;
	}

	public void setGoodsClass(String goodsClass) {
		this.goodsClass = goodsClass;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getOrderStr() {
		return orderStr;
	}

	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}

	public ExportItemSearchRule()
    {
    }

    
}
