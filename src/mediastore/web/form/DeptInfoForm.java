// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   UserInfo.java

package mediastore.web.form;

import org.apache.struts.action.ActionForm;


public class DeptInfoForm extends ActionForm
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String name;
    private String shortname;
    private String address;
    private String tel;
    private String linkman;
    private String mobile;
    
    

	public DeptInfoForm()
    {
    	id=0;
    	name="";
    	shortname="";
    	address="";
    	tel="";
    	linkman="";
    	mobile="";
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



	public void setId(int id) {
		this.id = id;
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



	public String getShortname() {
		return shortname;
	}



	public void setShortname(String shortname) {
		this.shortname = shortname;
	}



	public String getTel() {
		return tel;
	}



	public void setTel(String tel) {
		this.tel = tel;
	}

  
}
