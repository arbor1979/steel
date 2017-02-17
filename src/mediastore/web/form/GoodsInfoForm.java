// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsInfoObj.java

package mediastore.web.form;

import mediastore.util.oConvert;

import org.apache.struts.action.ActionForm;


public class GoodsInfoForm extends ActionForm
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String goodsId;
    private String goodsType;
    private String goodsTypeName;
    private String goodsClass;
    private String goodsClassName;
    private double purchaseUnitPrice;
    private double avgprice;
    private double curRepertory;
    private int curRepertoryNum;
    private String guige;
    private String caizhi;
    private String danwei;
    private float xishu;
    private String chandi;
    private int storeid;
    private String storeName;
    private float jianxishu;
    private int deptid;
    private String shortname;
    private String oldGoodsId;
    private String memo;
    
    private String lasttime;
    private double lastamount;
    private double lastprice;
    public float getJianxishu() {
		return jianxishu;
	}

	public void setJianxishu(float jianxishu) {
		this.jianxishu = oConvert.getRound(jianxishu,3);;
	}

	public String getChandi() {
		return chandi;
	}

	public void setChandi(String chandi) {
		this.chandi = chandi;
	}

	public GoodsInfoForm()
    {
        goodsId="";
        guige="";
        caizhi="";
        danwei="";
        xishu=0.0F;
        goodsType="0";
        goodsClass="0";
        chandi="";
        storeid=0;
        jianxishu=0.0F;
        deptid=0;
        shortname="";
        avgprice=0;
        oldGoodsId="";
        memo="";
    }

    public void setGoodsId(String string)
    {
        goodsId = oConvert.filterSQL(string);
    }
    public String getGoodsId()
    {
        return goodsId;
    }

    public String getGoodsType()
    {
        return goodsType;
    }
    public void setGoodsType(String string)
    {
        goodsType = string;
    }
    
    public void setGoodsTypeName(String string)
    {
        goodsTypeName = string;
    }
    public String getGoodsTypeName()
    {
        return goodsTypeName;
    }
    
    public String getGoodsClass()
    {
        return goodsClass;
    }
    public void setGoodsClass(String string)
    {
        goodsClass = string;
    }
    
    public String getGoodsClassName()
    {
        return goodsClassName;
    }
    public void setGoodsClassName(String string)
    {
        goodsClassName = string;
    }
    
    public double getPurchaseUnitPrice()
    {
        return purchaseUnitPrice;
    }
    public void setPurchaseUnitPrice(float f)
    {
        purchaseUnitPrice = f;
    }
    public void setPurchaseUnitPrice(double f)
    {
        purchaseUnitPrice = f;
    }
        
	public String getCaizhi() {
		return caizhi;
	}

	public void setCaizhi(String caizhi) {
		this.caizhi = oConvert.filterSQL(caizhi);
	}

	public String getDanwei() {
		return danwei;
	}

	public void setDanwei(String danwei) {
		this.danwei = oConvert.filterSQL(danwei);
	}

	public String getGuige() {
		return guige;
	}

	public void setGuige(String guige) {
		this.guige = oConvert.filterSQL(guige);
	}

	public float getXishu() {
		return xishu;
	}

	public void setXishu(float xishu) {
		this.xishu = xishu;
	}

	public double getCurRepertory() {
		return curRepertory;
	}

	public void setCurRepertory(double curRepertory) {
		this.curRepertory = oConvert.getRound(curRepertory,4);
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public int getStoreid() {
		return storeid;
	}

	public void setStoreid(int storeid) {
		this.storeid = storeid;
	}

	public int getDeptid() {
		return deptid;
	}

	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public double getAvgprice() {
		return avgprice;
	}

	public void setAvgprice(float avgprice) {
		this.avgprice = avgprice;
	}
	public void setAvgprice(double avgprice) {
		this.avgprice = avgprice;
	}

	public String getOldGoodsId() {
		return oldGoodsId;
	}

	public void setOldGoodsId(String oldGoodsId) {
		this.oldGoodsId = oConvert.getString(oldGoodsId,"");
	}

	public int getCurRepertoryNum() {
		return curRepertoryNum;
	}

	public void setCurRepertoryNum(int curRepertoryNum) {
		this.curRepertoryNum = curRepertoryNum;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public double getLastamount() {
		return lastamount;
	}

	public void setLastamount(double lastamount) {
		this.lastamount = lastamount;
	}

	public double getLastprice() {
		return lastprice;
	}

	public void setLastprice(double lastprice) {
		this.lastprice = lastprice;
	}

	public String getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}
    

}
