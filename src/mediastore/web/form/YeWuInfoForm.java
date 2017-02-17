// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   UserInfo.java

package mediastore.web.form;

import org.apache.struts.action.ActionForm;


public class YeWuInfoForm extends ActionForm
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3195027763303984935L;
	private int id;
    private String name;
    private String address;
    private String idcard;
    private String mobile;
    private String createtime;
    private int deptid;
    private String shortname;
    public YeWuInfoForm()
    {
    	id=0;
    	name="";
    	address="";
    	idcard="";
    	mobile="";
    	createtime="";
    	deptid=0;
    	shortname="";
    }

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
