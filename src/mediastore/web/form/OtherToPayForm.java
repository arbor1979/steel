// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsInfoObj.java

package mediastore.web.form;

import org.apache.struts.action.ActionForm;


public class OtherToPayForm extends ActionForm
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int billid;
	private int factory;
    private String factname;
    private double jine;
    private double jine1;
    private double curjine;
    private String createTime;
    private int acctype;
    private int deptid;
    private int kind;
    private int type;
    private String kindName;
    private String shortname;
    private String memo;
    private String operPerson;
    private String accName;
    
	public OtherToPayForm()
    {
    	memo="";
    	operPerson="";
    	factory=0;
    	kind=0;
    	type=0;
    }

	public double getCurjine() {
		return curjine;
	}

	public void setCurjine(double curjine) {
		this.curjine = curjine;
	}

	public int getAcctype() {
		return acctype;
	}

	public void setAcctype(int acctype) {
		this.acctype = acctype;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getDeptid() {
		return deptid;
	}

	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}

	public String getFactname() {
		return factname;
	}

	public void setFactname(String factname) {
		this.factname = factname;
	}

	public int getFactory() {
		return factory;
	}

	public void setFactory(int factory) {
		this.factory = factory;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getJine() {
		return jine;
	}

	public void setJine(double jine) {
		this.jine = jine;
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

	public String getOperPerson() {
		return operPerson;
	}

	public void setOperPerson(String operPerson) {
		this.operPerson = operPerson;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public int getBillid() {
		return billid;
	}

	public void setBillid(int billid) {
		this.billid = billid;
	}

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public double getJine1() {
		return jine1;
	}

	public void setJine1(double jine1) {
		this.jine1 = jine1;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	

}
