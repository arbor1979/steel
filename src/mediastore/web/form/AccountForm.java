// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsInfoObj.java

package mediastore.web.form;

import org.apache.struts.action.ActionForm;


public class AccountForm extends ActionForm
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int accfrom;
	private int accto;
	private int acctype;
	private double curjine; 
    private String accName;
    private int deptid;
    private double jine;
    private String operPerson;
    private String typename;
    private int billid;
    private String opertime;
    private String shortname;
    private String classid;
    private String typeid;
    private String account;
    private boolean inout;
    public AccountForm()
    {
    	operPerson="";
    	typename="";
    	classid="0";
    	typeid="0";
    	acctype=0;
    	account="";
    	accName="";
    }
	public double getJine() {
		return jine;
	}
	public void setJine(double jine) {
		this.jine = jine;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public int getDeptid() {
		return deptid;
	}
	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAcctype() {
		return acctype;
	}
	public void setAcctype(int acctype) {
		this.acctype = acctype;
	}
	public int getBillid() {
		return billid;
	}
	public void setBillid(int billid) {
		this.billid = billid;
	}
	public double getCurjine() {
		return curjine;
	}
	public void setCurjine(double curjine) {
		this.curjine = curjine;
	}
	public String getOperPerson() {
		return operPerson;
	}
	public void setOperPerson(String operPerson) {
		this.operPerson = operPerson;
	}
	public String getOpertime() {
		return opertime;
	}
	public void setOpertime(String opertime) {
		this.opertime = opertime;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public int getAccfrom() {
		return accfrom;
	}
	public void setAccfrom(int accfrom) {
		this.accfrom = accfrom;
	}
	public int getAccto() {
		return accto;
	}
	public void setAccto(int accto) {
		this.accto = accto;
	}
	public String getClassid() {
		return classid;
	}
	public void setClassid(String classid) {
		this.classid = classid;
	}
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public boolean isInout() {
		return inout;
	}
	public void setInout(boolean inout) {
		this.inout = inout;
	}
  
    

}
