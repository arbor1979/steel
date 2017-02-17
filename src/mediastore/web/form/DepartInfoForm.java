// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   UserInfo.java

package mediastore.web.form;

import org.apache.struts.action.ActionForm;


public class DepartInfoForm extends ActionForm
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String tel;
    private String name;
    private String shortname;
    private String address;
    private String linkman;
    private int townid;
    private String fax;
    private String mobile;
    private String bank;
    private String account;
    private String taxno;
    private String zhujima;
    private int pagesize;
    private int jiagong;
    private boolean ifyunfei;
    private boolean ifyixiangjine;
    private String tip;
	public boolean isIfyunfei() {
		return ifyunfei;
	}

	public void setIfyunfei(boolean ifyunfei) {
		this.ifyunfei = ifyunfei;
	}

	public DepartInfoForm()
    {
		id=0;
		tel="";
		shortname="";
		name="";
		address="";
		linkman="";
		townid=0;
		fax="";
		mobile="";
		bank="";
		account="";
		taxno="";
		zhujima="";
		tip="";
    }

    public void setId(int userId)
    {
        this.id = userId;
    }

    public int getId()
    {
        return id;
    }
    public void setTel(String userId)
    {
        this.tel = userId;
    }

    public String getTel()
    {
        return tel;
    }

    public void setName(String userName)
    {
        this.name = userName;
    }

    public String getName()
    {
        return name;
    }

    public void setAddress(String userStatus)
    {
        this.address = userStatus;
    }

    public String getAddress()
    {
        return address;
    }
    public void setLinkman(String userStatus)
    {
        this.linkman = userStatus;
    }

    public String getLinkman()
    {
        return linkman;
    }

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getTownid() {
		return townid;
	}

	public void setTownid(int townid) {
		this.townid = townid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getTaxno() {
		return taxno;
	}

	public void setTaxno(String taxno) {
		this.taxno = taxno;
	}

	public String getZhujima() {
		return zhujima;
	}

	public void setZhujima(String zhujima) {
		if(zhujima==null)
			this.zhujima="";
		else
			this.zhujima = zhujima;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public int getJiagong() {
		return jiagong;
	}

	public void setJiagong(int jiagong) {
		this.jiagong = jiagong;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public boolean isIfyixiangjine() {
		return ifyixiangjine;
	}

	public void setIfyixiangjine(boolean ifyixiangjine) {
		this.ifyixiangjine = ifyixiangjine;
	}


  }
