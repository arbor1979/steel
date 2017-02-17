// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsImportGoodsInfo.java

package mediastore.web.form;

import mediastore.util.oConvert;

import org.apache.struts.action.ActionForm;


public class GoodsCheckGoodsInfo extends ActionForm
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int billId;
    private String goodsId;
    private String goodsName;
    private int checkNum;
    private double realAmount;
    private double checkAmount;
    private double checkUnitPrice;
    private String createPerson;
    private String createTime;
    private String confirmFlage;
    private double curRepertoryAmount;
    private int storeId;
    private String storeName;
    private String guige;
    private String caizhi;
    private String chandi;
    private String danwei;
    private double xishu;
    private int id;
    private int deptid;
    private String shortname;
    private int kind;
    
    public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public GoodsCheckGoodsInfo()
    {
    	billId=0;
    	goodsId="";
    	goodsName="";
    	checkNum=0;
    	checkAmount=0;
    	checkUnitPrice=0;
    	createPerson="";
    	createTime="";
    	confirmFlage="";
    	curRepertoryAmount=0;
    	storeId=0;
    	storeName="";
    	guige="";
    	caizhi="";
    	chandi="";
    	deptid=0;
    	shortname="";
    	kind=1;
    }
	public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
	public String getCaizhi() {
		return caizhi;
	}
	public void setCaizhi(String caizhi) {
		this.caizhi = caizhi;
	}
	public String getChandi() {
		return chandi;
	}
	public void setChandi(String chandi) {
		this.chandi = chandi;
	}
	public double getCheckAmount() {
		return checkAmount;
	}
	public void setCheckAmount(double checkAmount) {
		this.checkAmount = oConvert.getRound(checkAmount,4);
	}
	public int getCheckNum() {
		return checkNum;
	}
	public void setCheckNum(int checkNum) {
		this.checkNum = checkNum;
	}
	public void setCheckNum(double checkNum) {
		this.checkNum = (int)oConvert.getRound(checkNum,0);
	}
	public double getCheckUnitPrice() {
		return checkUnitPrice;
	}
	public void setCheckUnitPrice(double checkUnitPrice) {
		this.checkUnitPrice = oConvert.getRound(checkUnitPrice,4);
	}
	public String getConfirmFlage() {
		return confirmFlage;
	}
	public void setConfirmFlage(String confirmFlage) {
		this.confirmFlage = confirmFlage;
	}
	public String getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public double getCurRepertoryAmount() {
		return curRepertoryAmount;
	}
	public void setCurRepertoryAmount(double curRepertoryAmount) {
		this.curRepertoryAmount = curRepertoryAmount;
	}
	public String getDanwei() {
		return danwei;
	}
	public void setDanwei(String danwei) {
		this.danwei = danwei;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGuige() {
		return guige;
	}
	public void setGuige(String guige) {
		this.guige = guige;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public double getXishu() {
		return xishu;
	}
	public void setXishu(double xishu) {
		this.xishu = xishu;
	}
	public double getRealAmount() {
		return realAmount;
	}
	public void setRealAmount(double realAmount) {
		this.realAmount = realAmount;
	}
	public int getDeptid() {
		return deptid;
	}
	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}

    

}
