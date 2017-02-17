// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsInfoObj.java

package mediastore.web.form;

import org.apache.struts.action.ActionForm;


public class KaiPingInfoForm extends ActionForm
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int billid;
	private int deptid;
    private double totalnum1;
    private double totalnum2;
    private double totalprice;
    
    private String memo;
    
    private String createtime;
    private String createperson;
    private String shortname;
    private int kind;
    public KaiPingInfoForm()
    {
    	memo="";
    }
	public int getBillid() {
		return billid;
	}
	public void setBillid(int billid) {
		this.billid = billid;
	}
	public String getCreateperson() {
		return createperson;
	}
	public void setCreateperson(String createperson) {
		this.createperson = createperson;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public int getDeptid() {
		return deptid;
	}
	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public double getTotalnum1() {
		return totalnum1;
	}
	public void setTotalnum1(double totalnum1) {
		this.totalnum1 = totalnum1;
	}
	public double getTotalnum2() {
		return totalnum2;
	}
	public void setTotalnum2(double totalnum2) {
		this.totalnum2 = totalnum2;
	}
	public double getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(double totalprice) {
		this.totalprice = totalprice;
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

	
    

}
