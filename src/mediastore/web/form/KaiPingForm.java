// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsInfoObj.java

package mediastore.web.form;

import mediastore.util.oConvert;

import org.apache.struts.action.ActionForm;


public class KaiPingForm extends ActionForm
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int billid;
	private String fromGoodsId;
    private int fromStore;
    private String toGoodsId;
    private int toStore;
    private int deptid;
    private String shortname;
    private String memo;
    private String fromname;
    private String toname;
    private int fromNum;
    private int toNum;
    private double fromAmount;
    private double toAmount;
    private double fromPrice;
    private double toPrice;
    private double jine;
    private String createPerson;
    private double curRepertory;
    private String fromGoodsName;
    private String toGoodsName;
    private String fromGuige;
    private String toGuige;
    private String createtime;
    private int kind;
    private String guige;
    private String chandi;
    private String danwei;
    private String caizhi;
    private String goodsId;
    private int storeId;
    public int getKind() {
		return kind;
	}




	public void setKind(int kind) {
		this.kind = kind;
	}




	public String getFromGuige() {
		return fromGuige;
	}




	public void setFromGuige(String fromGuige) {
		this.fromGuige = fromGuige;
	}




	public String getToGuige() {
		return toGuige;
	}




	public void setToGuige(String toGuige) {
		this.toGuige = toGuige;
	}




	public KaiPingForm()
    {
    	deptid=0;
    	shortname="";
    	fromGoodsId="";
    	toGoodsId="";
    	fromStore=0;;
    	toStore=0;
    	memo="";
    	fromname="";
    	toname="";
    	createPerson="";
    	fromGoodsName="";
    	toGoodsName="";
    	kind=0;
    }

	

	
	public String getFromGoodsId() {
		return fromGoodsId;
	}



	public void setFromGoodsId(String fromGoodsId) {
		this.fromGoodsId = fromGoodsId;
	}



	public double getJine() {
		return jine;
	}



	public void setJine(double jine) {
		this.jine = jine;
	}


	public String getToGoodsId() {
		return toGoodsId;
	}


	public void setToGoodsId(String toGoodsId) {
		this.toGoodsId = toGoodsId;
	}

	public double getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(double fromAmount) {
		this.fromAmount = oConvert.getRound(fromAmount,3);
	}




	public int getFromNum() {
		return fromNum;
	}




	public void setFromNum(int fromNum) {
		this.fromNum = fromNum;
	}




	public double getFromPrice() {
		return fromPrice;
	}




	public void setFromPrice(double fromPrice) {
		this.fromPrice = fromPrice;
	}




	public double getToAmount() {
		return toAmount;
	}




	public void setToAmount(double toAmount) {
		this.toAmount = toAmount;
	}




	public int getToNum() {
		return toNum;
	}




	public void setToNum(int toNum) {
		this.toNum = toNum;
	}




	public double getToPrice() {
		return toPrice;
	}




	public void setToPrice(double toPrice) {
		this.toPrice = toPrice;
	}




	public int getFromStore() {
		return fromStore;
	}

	public void setFromStore(int fromStore) {
		this.fromStore = fromStore;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getToStore() {
		return toStore;
	}

	public void setToStore(int toStore) {
		this.toStore = toStore;
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



	public String getCreatePerson() {
		return createPerson;
	}



	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}



	public double getCurRepertory() {
		return curRepertory;
	}



	public void setCurRepertory(double curRepertory) {
		this.curRepertory = curRepertory;
	}



	public String getFromGoodsName() {
		return fromGoodsName;
	}



	public void setFromGoodsName(String fromGoodsName) {
		this.fromGoodsName = fromGoodsName;
	}



	public String getToGoodsName() {
		return toGoodsName;
	}



	public void setToGoodsName(String toGoodsName) {
		this.toGoodsName = toGoodsName;
	}




	public int getBillid() {
		return billid;
	}




	public void setBillid(int billid) {
		this.billid = billid;
	}




	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public String getCreatetime() {
		return createtime;
	}




	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}




	public String getChandi() {
		return chandi;
	}




	public void setChandi(String chandi) {
		this.chandi = chandi;
	}




	public String getGuige() {
		return guige;
	}




	public void setGuige(String guige) {
		this.guige = guige;
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




	public int getStoreId() {
		return storeId;
	}




	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}




	public String getCaizhi() {
		return caizhi;
	}




	public void setCaizhi(String caizhi) {
		this.caizhi = caizhi;
	}

	

    

}
