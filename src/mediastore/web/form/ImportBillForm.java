// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsInfoObj.java

package mediastore.web.form;

import mediastore.util.oConvert;

import org.apache.struts.action.ActionForm;

public class ImportBillForm extends ActionForm
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int billId;
    private String createPerson;
    private double totalNum;
    private double totalPrice;
    private int factory;
    private String factoryName;
    private String importTime;
    private String paymentTime;
    private int fkType;
    private int fpType;
    String fkName;
    String fpName;
    private String payLimTime;
    private float yunFei;
    private String carNo;
    private String driver;
    private String PayShipTime;
    private double realMoney;
    private String PayFpTime;
    private String confirmFlage;
    private double prepay1;
    private int allcount;
    private String zhujima;
    private int deptid;
    private String shortname;
    private String gongsi;
    private int kind;
    private String memo;
    private int ieva;
    private boolean iyunfei;
    private double oddment;
   

	public double getOddment() {
		return oddment;
	}

	public void setOddment(double oddment) {
		this.oddment = oddment;
	}

	public boolean isIyunfei() {
		return iyunfei;
	}

	public void setIyunfei(boolean iyunfei) {
		this.iyunfei = iyunfei;
	}

	public ImportBillForm()
    {
    	billId=0;
    	createPerson="";
    	totalNum=0;
    	totalPrice=0;
    	factory=0;
    	importTime="";
    	paymentTime="";	
    	fkType=0;
    	fpType=0;
    	payLimTime="";
    	yunFei=0.0F;
    	carNo="";
    	prepay1=0;
    	allcount=0;
    	zhujima="";
    	deptid=0;
    	shortname="";
    	gongsi="";
    	factoryName="";
    	fpName="";
    	fkName="";
    	memo="";
    	PayFpTime="";
    }

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public int getFactory() {
		return factory;
	}

	public void setFactory(int factory) {
		this.factory = factory;
	}

	public String getImportTime() {
		return importTime;
	}

	public void setImportTime(String importTime) {
		this.importTime = importTime;
	}

	public String getConfirmFlage() {
		return confirmFlage;
	}

	public void setConfirmFlage(String confirmFlage) {
		this.confirmFlage = confirmFlage;
	}

	public String getPayFpTime() {
		return PayFpTime;
	}

	public void setPayFpTime(String payFpTime) {
		PayFpTime = payFpTime;
	}

	public String getPayShipTime() {
		return PayShipTime;
	}

	public void setPayShipTime(String payShipTime) {
		PayShipTime = payShipTime;
	}

	public double getRealMoney() {
		return realMoney;
	}

	public void setRealMoney(double realMoney) {
		this.realMoney = oConvert.getRound(realMoney,2);
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
		this.totalPrice = oConvert.getRound(totalPrice,4);
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

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public double getPrepay1() {
		return prepay1;
	}

	public void setPrepay1(double prepay1) {
		this.prepay1 = prepay1;
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

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getIeva() {
		return ieva;
	}

	public void setIeva(int ieva) {
		this.ieva = ieva;
	}

	public String getGongsi() {
		return gongsi;
	}

	public void setGongsi(String gongsi) {
		this.gongsi = gongsi;
	}




    

}
