// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   UserInfo.java

package mediastore.web.form;

import org.apache.struts.action.ActionForm;


public class FactoryInfoForm extends ActionForm
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String tel;
    private String name;
    private String address;
    private String linkman;
    private String yewuyuan;
    private String yewuName;
    private String faren;
    private String townid;
    private String fax;
    private String mobile;
    private String kind;
    private String bank;
    private String account;
    private String taxno;
    private String zhujima;
    private double prepay;
    private double prepay1;
    private int deptid;
    private String shortname;
    public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public FactoryInfoForm()
    {
		id=0;
		tel="";
		name="";
		address="";
		linkman="";
		yewuyuan="0";
		faren="";
		townid="";
		fax="";
		mobile="";
		kind="";
		bank="";
		account="";
		taxno="";
		zhujima="";
		prepay=0;
		prepay1=0;
		deptid=0;
		shortname="";
		yewuName="";
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

	public String getYewuyuan() {
		return yewuyuan;
	}

	public void setYewuyuan(String yewuyuan) {
		this.yewuyuan = yewuyuan;
	}

	public String getFaren() {
		return faren;
	}

	public void setFaren(String faren) {
		this.faren = faren;
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

	public String getTownid() {
		return townid;
	}

	public void setTownid(String townid) {
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

	public double getPrepay() {
		return prepay;
	}

	public void setPrepay(double prepay) {
		this.prepay = prepay;
	}

	public double getPrepay1() {
		return prepay1;
	}

	public void setPrepay1(double prepay1) {
		this.prepay1 = prepay1;
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

	public String getYewuName() {
		return yewuName;
	}

	public void setYewuName(String yewuName) {
		this.yewuName = yewuName;
	}

  }
