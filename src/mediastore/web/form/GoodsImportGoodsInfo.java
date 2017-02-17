// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsImportGoodsInfo.java

package mediastore.web.form;

import mediastore.util.oConvert;

import org.apache.struts.action.ActionForm;


public class GoodsImportGoodsInfo extends ActionForm
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int billId;
    private String goodsId;
    private String goodsName;
    private double importJianNum;
    private int importNum;
    private double importAmount;
    private double importUnitPrice;
    private double importMoney;
    private String createPerson;
    private String createTime;
    private String confirmFlage;
    private double curRepertoryAmount;
    private int storeId;
    private String storeName;
    private String guige;
    private String caizhi;
    private String chandi;
    private String danwei;
    private double xishu;
    private double jianxishu;
    private int id;
    private int deptid;
    private String shortname;
    private int kind;
    private double evaPrice;
    private double evaMoney;
    private String memo;
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public double getJianxishu() {
		return jianxishu;
	}

	public void setJianxishu(double jianxishu) {
		this.jianxishu = jianxishu;
	}

	public GoodsImportGoodsInfo()
    {
    	billId=0;
    	goodsId="";
    	goodsName="";
    	importJianNum=0;
    	importNum=0;
    	importAmount=0;
    	importUnitPrice=0;
    	createPerson="";
    	createTime="";
    	confirmFlage="";
    	curRepertoryAmount=0;
    	storeId=0;
    	storeName="";
    	guige="";
    	caizhi="";
    	chandi="";
    	xishu=0;
    	jianxishu=0;
    	deptid=0;
    	shortname="";
    	kind=1;
    	memo="";
    }

    public int getBillId()
    {
        return billId;
    }

    public String getConfirmFlage()
    {
        return confirmFlage;
    }

    public String getGoodsId()
    {
        return goodsId;
    }

    public String getGoodsName()
    {
        return goodsName;
    }

    public double getImportAmount()
    {
        return importAmount;
    }

    public double getImportUnitPrice()
    {
        return importUnitPrice;
    }
    public void setBillId(int i)
    {
        billId = i;
    }

    public void setConfirmFlage(String string)
    {
        confirmFlage = string;
    }

    public void setGoodsId(String string)
    {
        goodsId = string;
    }

    public void setGoodsName(String string)
    {
        goodsName = string;
    }

    public void setImportAmount(double i)
    {
        importAmount = oConvert.getRound(i,3);
    }

    public void setImportUnitPrice(double f)
    {
        importUnitPrice = oConvert.getRound(f,4);
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String string)
    {
        createTime = string;
    }

    public String getCreatePerson()
    {
        return createPerson;
    }

    public void setCreatePerson(String string)
    {
        createPerson = string;
    }

    public double getCurRepertoryAmount()
    {
        return curRepertoryAmount;
    }

    public void setCurRepertoryAmount(double i)
    {
        curRepertoryAmount = i;
    }

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getGuige() {
		return guige;
	}

	public void setGuige(String guige) {
		this.guige = guige;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCaizhi() {
		return caizhi;
	}

	public void setCaizhi(String caizhi) {
		this.caizhi = caizhi;
	}

	public String getChandi() {
		return chandi;
	}

	public void setChandi(String candi) {
		this.chandi = candi;
	}

	public int getImportNum() {
		return importNum;
	}

	public void setImportNum(double importNum) {
		this.importNum = (int)oConvert.getRound(importNum,0);
	}
	public void setImportNum(int importNum) {
		this.importNum = importNum;
	}
	public double getXishu() {
		return xishu;
	}

	public void setXishu(double xishu) {
		this.xishu = xishu;
	}

	public double getImportMoney() {
		return importMoney;
	}

	public void setImportMoney(double importMoney) {
		this.importMoney = importMoney;
	}

	public String getDanwei() {
		return danwei;
	}

	public void setDanwei(String danwei) {
		this.danwei = danwei;
	}

	public double getImportJianNum() {
		return importJianNum;
	}

	public void setImportJianNum(double importJianNum) {
		this.importJianNum = oConvert.getRound(importJianNum,1);
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

	public double getEvaPrice() {
		return evaPrice;
	}

	public void setEvaPrice(double evaPrice) {
		this.evaPrice = evaPrice;
	}

	public double getEvaMoney() {
		return evaMoney;
	}

	public void setEvaMoney(double evaMoney) {
		this.evaMoney = evaMoney;
	}

}
