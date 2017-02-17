// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsInfoObj.java

package mediastore.web.form;

import org.apache.struts.action.ActionForm;


public class ChongZhiForm extends ActionForm
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String operPerson;
    private double begChuzhi;
    private double curChuzhi;
    private double addChuzhi;
    private double huokuan;
    private double payhuokuan;
    private String createTime;
    private int fkType;
    private String fkTypeName;
    private int factory;
    private String factoryName;
    private int deptid;
    private int billid;
    private int kind;
    private String typename;
    public int getFactory() {
		return factory;
	}
	public void setFactory(int factory) {
		this.factory = factory;
	}
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	public ChongZhiForm()
    {
    	id=0;
    	operPerson="";
    	begChuzhi=0;
    	curChuzhi=0;
    	addChuzhi=0;
    	huokuan=0;
    	createTime="";
    	fkType=0;
    	fkTypeName="";
    	payhuokuan=0;
    	deptid=0;
    	billid=0;
    	kind=0;
    	typename="";
    }
	public double getAddChuzhi() {
		return addChuzhi;
	}
	public void setAddChuzhi(double addChuzhi) {
		this.addChuzhi = addChuzhi;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public double getCurChuzhi() {
		return curChuzhi;
	}
	public void setCurChuzhi(double curChuzhi) {
		this.curChuzhi = curChuzhi;
	}
	public int getFkType() {
		return fkType;
	}
	public void setFkType(int fkType) {
		this.fkType = fkType;
	}
	public String getFkTypeName() {
		return fkTypeName;
	}
	public void setFkTypeName(String fkTypeName) {
		this.fkTypeName = fkTypeName;
	}
	public double getHuokuan() {
		return huokuan;
	}
	public void setHuokuan(double huokuan) {
		this.huokuan = huokuan;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOperPerson() {
		return operPerson;
	}
	public void setOperPerson(String operPerson) {
		this.operPerson = operPerson;
	}
	public double getPayhuokuan() {
		return payhuokuan;
	}
	public void setPayhuokuan(double payhuokuan) {
		this.payhuokuan = payhuokuan;
	}
	public int getDeptid() {
		return deptid;
	}
	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}
	public int getBillid() {
		return billid;
	}
	public void setBillid(int billid) {
		this.billid = billid;
	}
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public double getBegChuzhi() {
		return begChuzhi;
	}
	public void setBegChuzhi(double begChuzhi) {
		this.begChuzhi = begChuzhi;
	}


	

}
