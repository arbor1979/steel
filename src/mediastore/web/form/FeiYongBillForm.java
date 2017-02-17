// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsImportBillInfo.java

package mediastore.web.form;

import org.apache.struts.action.ActionForm;


public class FeiYongBillForm extends ActionForm
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5329492748993837562L;
	private int billId;
    private String operPerson;
    private int deptid;
    private double totalPrice;
    private String CreateTime;
    private String billTime;
	private String shortname;
	private int acctype;
	private int kind;
	private String accName;
	public FeiYongBillForm()
	{
		acctype=0;
		billId=0;
		deptid=0;
		kind=0;
		operPerson="";
	}
	public int getAcctype() {
		return acctype;
	}

	public void setAcctype(int acctype) {
		this.acctype = acctype;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
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

	public String getBillTime() {
		return billTime;
	}

	public void setBillTime(String billTime) {
		this.billTime = billTime;
	}
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}

    
}
