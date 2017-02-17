// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsInfoObj.java

package mediastore.web.form;

import mediastore.util.oConvert;

import org.apache.struts.action.ActionForm;


public class ExportBillForm extends ActionForm
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int billId;
    private String salesPerson;
    private double totalNum;
    private double totalPrice;
    private int factory;
    private String factoryName;
    private String exportTime;
    private String paymentTime;
    private int fkType;
    private int fpType;
    String fkName;
    String fpName;
    private String payLimTime;
    private String payShipTime;
    private String payFpTime;
    private float yunFei;
    private String carNo;
    private double realmoney;
    private String driver; 
    private String confirmFlage;
    private double prepay;
    private int allcount;
    private String zhujima;
    private String memo;
    private int deptid;
    private String shortname;
    private String gongsi;
    private int kind;
    private String tihuo;
    private double totaljiagong;
    private String tip;
    private int yewuid;
    private String yewuname;
    public String getYewuname() {
		return yewuname;
	}

	public void setYewuname(String yewuname) {
		this.yewuname = yewuname;
	}

	public int getYewuid() {
		return yewuid;
	}

	public void setYewuid(int yewuid) {
		this.yewuid = yewuid;
	}

	public ExportBillForm()
    {
    	billId=0;
    	salesPerson="";
    	totalNum=0;
    	totalPrice=0;
    	factory=0;
    	exportTime="";
    	paymentTime="";	
    	fkType=0;
    	fpType=0;
    	payLimTime="";
    	yunFei=0.0F;
    	carNo="";
    	realmoney=0;
    	driver="";
    	prepay=0;
    	allcount=0;
    	zhujima="";
    	memo="";
    	deptid=0;
    	shortname="";
    	gongsi="";
    	kind=0;
    	factoryName="";
    	fpName="";
    	fkName="";
    	tihuo="здЬс";
    	tip="";
    	yewuid=-1;
    	yewuname="";
    }

	public int getDeptid() {
		return deptid;
	}

	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public int getFactory() {
		return factory;
	}

	public void setFactory(int factory) {
		this.factory = factory;
	}

	public String getExportTime() {
		return exportTime;
	}

	public void setExportTime(String exportTime) {
		this.exportTime = exportTime;
	}

	public String getSalesPerson() {
		return salesPerson;
	}

	public void setSalesPerson(String salesPerson) {
		this.salesPerson = salesPerson;
	}

	public String getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}

	public double getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(double totalNum) {
		this.totalNum = oConvert.getRound(totalNum,4);
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public int getFkType() {
		return fkType;
	}

	public void setFkType(int fkType) {
		this.fkType = fkType;
	}

	public int getFpType() {
		return fpType;
	}

	public void setFpType(int fpType) {
		this.fpType = fpType;
	}

	public String getPayLimTime() {
		return payLimTime;
	}

	public void setPayLimTime(String payLimTime) {
		this.payLimTime = payLimTime;
	}

	public float getYunFei() {
		return yunFei;
	}

	public void setYunFei(float yunFei) {
		this.yunFei = yunFei;
	}

	public String getFkName() {
		return fkName;
	}

	public void setFkName(String fkName) {
		this.fkName = fkName;
	}

	public String getFpName() {
		return fpName;
	}

	public void setFpName(String fpName) {
		this.fpName = fpName;
	}

	public double getRealmoney() {
		return realmoney;
	}

	public void setRealmoney(double realmoney) {
		this.realmoney = realmoney;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getConfirmFlage() {
		return confirmFlage;
	}

	public void setConfirmFlage(String confirmFlage) {
		this.confirmFlage = confirmFlage;
	}

	public String getPayFpTime() {
		return payFpTime;
	}

	public void setPayFpTime(String payFpTime) {
		this.payFpTime = payFpTime;
	}

	public String getPayShipTime() {
		return payShipTime;
	}

	public void setPayShipTime(String payShipTime) {
		this.payShipTime = payShipTime;
	}

	public double getPrepay() {
		return prepay;
	}

	public void setPrepay(double premoney) {
		this.prepay = premoney;
	}

	public int getAllcount() {
		return allcount;
	}

	public void setAllcount(int allcount) {
		this.allcount = allcount;
	}

	public String getZhujima() {
		return zhujima;
	}

	public void setZhujima(String zhujima) {
		this.zhujima = zhujima;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public String getGongsi() {
		return gongsi;
	}

	public void setGongsi(String gongsi) {
		this.gongsi = gongsi;
	}

	public String getTihuo() {
		return tihuo;
	}

	public void setTihuo(String tihuo) {
		this.tihuo = tihuo;
	}

	public double getTotaljiagong() {
		return totaljiagong;
	}

	public void setTotaljiagong(double totaljiagong) {
		this.totaljiagong = totaljiagong;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

    

}
