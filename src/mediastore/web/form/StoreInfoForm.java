package mediastore.web.form;

import org.apache.struts.action.ActionForm;

public class StoreInfoForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String address;
	private String linkman;
	private String tel;
	private String mobile;
	private float num;
	private int deptid;
	private String shortname;
	public float getNum() {
		return num;
	}
	public void setNum(float num) {
		this.num = num;
	}
	public StoreInfoForm()
	{
		id=0;
		name="";
		address="";
		linkman="";
		tel="";
		mobile="";
		deptid=0;
		shortname="";
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getId() {
		return id;
	}
	public void setId(int deptid) {
		this.id = deptid;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
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

}
