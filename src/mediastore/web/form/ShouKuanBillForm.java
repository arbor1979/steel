// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsImportBillInfo.java

package mediastore.web.form;


public class ShouKuanBillForm
{

    private int billId;
    private String operPerson;
    private int deptid;
    private int factory;
    private double totalPrice;
    private String CreateTime;
    private int kind;
    private int fktype;
    private double curAccount;
    private String shortname;
    private String factname;
    private String fkname;
    
	public String getFkname() {
		return fkname;
	}
	public void setFkname(String fkname) {
		this.fkname = fkname;
	}
	public String getFactname() {
		return factname;
	}
	public void setFactname(String factname) {
		this.factname = factname;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public double getCurAccount() {
		return curAccount;
	}
	public void setCurAccount(double curAccount) {
		this.curAccount = curAccount;
	}
	public int getDeptid() {
		return deptid;
	}
	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}
	public int getFactory() {
		return factory;
	}
	public void setFactory(int factory) {
		this.factory = factory;
	}
	public int getFktype() {
		return fktype;
	}
	public void setFktype(int fktype) {
		this.fktype = fktype;
	}
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public String getOperPerson() {
		return operPerson;
	}
	public void setOperPerson(String operPerson) {
		this.operPerson = operPerson;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
    
	
}
