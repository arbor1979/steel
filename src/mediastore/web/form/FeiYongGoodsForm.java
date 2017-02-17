// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsImportGoodsInfo.java

package mediastore.web.form;

import java.net.URLDecoder;

import org.apache.struts.action.ActionForm;


public class FeiYongGoodsForm extends ActionForm
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4837407400852741675L;
	private int id;
    private int billid;
    private int typeid;
    private String typeName;
    private double jine;
    private String operPerson;
    private String createTime;
    private String memo;
    private int deptid;
    private String shortname;
    private String billTime;
    private String jingshouren;
    private int classid;
    private String classname;
    private int kind;

	public FeiYongGoodsForm()
    {
		billid=0;
		typeid=0;
		classid=0;
		operPerson="";
		deptid=0;
		jine=0;
		typeName="";
		jingshouren="";
		memo="";
		kind=0;
		classname="";
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


	public String getMemo() {
		return memo;
	}


	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getBillid() {
		return billid;
	}


	public void setBillid(int billid) {
		this.billid = billid;
	}


	public String getCreateTime() {
		return createTime;
	}


	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public double getJine() {
		return jine;
	}


	public void setJine(double jine) {
		this.jine = jine;
	}

	public int getTypeid() {
		return typeid;
	}


	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}


	public String getTypeName() {
		return typeName;
	}


	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	public String getOperPerson() {
		return operPerson;
	}


	public void setOperPerson(String operPerson) {
		this.operPerson = operPerson;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getBillTime() {
		return billTime;
	}


	public void setBillTime(String billTime) {
		this.billTime = billTime;
	}

	public int getClassid() {
		return classid;
	}

	public void setClassid(int classid) {
		this.classid = classid;
	}

	public String getJingshouren() {
		return jingshouren;
	}

	public void setJingshouren(String jingshouren) {
		this.jingshouren = jingshouren;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

   
}
