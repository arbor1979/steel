// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   UserInfo.java

package mediastore.web.form;

import org.apache.struts.action.ActionForm;


public class TruckInfoForm extends ActionForm
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String carno;
    private String driver;
    private String address;
    private String idcard;
    private String kind;
    private String mobile;
    private String chejia;
    private String fadongji;
    private float weight;
    private String oper;
    private int deptid;
    private String shortname;
    private double account;
    public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	public TruckInfoForm()
    {
    	id=0;
    	carno="";
    	driver="";
    	address="";
    	idcard="";
    	kind="";
    	mobile="";
    	chejia="";
    	fadongji="";
    	weight=0.0F;
    	oper="";
    	deptid=0;
    	shortname="";
    }
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCarno() {
		return carno;
	}
	public void setCarno(String carno) {
		this.carno = carno;
	}
	public String getChejia() {
		return chejia;
	}
	public void setChejia(String chejia) {
		this.chejia = chejia;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getFadongji() {
		return fadongji;
	}
	public void setFadongji(String fadongji) {
		this.fadongji = fadongji;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
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
	public double getAccount() {
		return account;
	}
	public void setAccount(double account) {
		this.account = account;
	}
   
    
  }
