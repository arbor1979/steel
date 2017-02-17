// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsImportGoodsInfo.java

package mediastore.web.form;

import mediastore.util.oConvert;

import org.apache.struts.action.ActionForm;


public class ChangeStoreGoodsForm extends ActionForm
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int billId;
    private String goodsId;
    private String goodsName;
    private int changeNum;
    private double changeAmount;
    private double changeUnitPrice;
    private String createPerson;
    private String createTime;
    private String confirmFlage;
    private double curRepertory;
    private String guige;
    private String caizhi;
    private String chandi;
    private String danwei;
    private double xishu;
    private int id;
    private int deptid;
    private String shortname;
    private String memo;
    private int fromstore;
    private int tostore;
    private String fromname;
    private String toname;
    private int storeId;
    
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public ChangeStoreGoodsForm()
    {
    	billId=0;
    	goodsId="";
    	goodsName="";
    	changeNum=0;
    	changeAmount=0;
    	changeUnitPrice=0;
    	createPerson="";
    	createTime="";
    	confirmFlage="";
    	curRepertory=0;
    	guige="";
    	caizhi="";
    	chandi="";
    	deptid=0;
    	shortname="";
    	fromstore=0;
    	tostore=0;
    	memo="";
    	fromname="";
    	toname="";
    	
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
	public double getChangeAmount() {
		return changeAmount;
	}
	public void setChangeAmount(double changeAmount) {
		this.changeAmount = oConvert.getRound(changeAmount,3);
	}
	public int getChangeNum() {
		return changeNum;
	}
	public void setChangeNum(double changeNum) {
		this.changeNum = (int)oConvert.getRound(changeNum,0);
	}
	public double getChangeUnitPrice() {
		return changeUnitPrice;
	}
	public void setChangeUnitPrice(double changeUnitPrice) {
		this.changeUnitPrice = oConvert.getRound(changeUnitPrice,4);
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
	public double getCurRepertory() {
		return curRepertory;
	}
	public void setCurRepertory(double curRepertory) {
		this.curRepertory = curRepertory;
	}
	public String getDanwei() {
		return danwei;
	}
	public void setDanwei(String danwei) {
		this.danwei = danwei;
	}
	public int getDeptid() {
		return deptid;
	}
	public void setDeptid(int deptid) {
		this.deptid = deptid;
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
	public double getXishu() {
		return xishu;
	}
	public void setXishu(double xishu) {
		this.xishu = xishu;
	}

	public String getFromname() {
		return fromname;
	}
	public void setFromname(String fromname) {
		this.fromname = fromname;
	}
	public String getToname() {
		return toname;
	}
	public void setToname(String toname) {
		this.toname = toname;
	}
	public void setChangeNum(int changeNum) {
		this.changeNum = changeNum;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getFromstore() {
		return fromstore;
	}
	public void setFromstore(int fromstore) {
		this.fromstore = fromstore;
	}
	public int getTostore() {
		return tostore;
	}
	public void setTostore(int tostore) {
		this.tostore = tostore;
	}
	
}
