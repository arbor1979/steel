// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsExportGoodsInfo.java

package mediastore.web.form;

import mediastore.util.oConvert;

import org.apache.struts.action.ActionForm;


public class GoodsExportGoodsInfo extends ActionForm
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8201691993685306246L;
	private int id;
	private int billId;
    private String goodsId;
    private String goodsName;
    private double exportJianNum;
    private int exportNum;
    private double exportAmount;
    private double exportUnitPrice;
    private String salesPerson;
    private String createTime;
    private String confirmFlage;
    private String membername;
    private double curRepertoryAmount;
    private String storeName;
    private int storeId;
    private String guige;
    private String caizhi;
    private String chandi;
    private float xishu;
    private float jianxishu;
    private String danwei;
    private int deptid;
    private String shortname;
    private int kind;
    private double avgprice;
    private double jiagong;
    private String memo;
    public GoodsExportGoodsInfo()
    {
    	billId=0;
    	goodsId="";
    	goodsName="";
    	exportJianNum=0;
    	exportNum=0;
    	exportAmount=0;
    	exportUnitPrice=0;
    	salesPerson="";
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
    	avgprice=0;
    	jiagong=0;
    	memo="";
    }

    public double getAvgprice() {
		return avgprice;
	}

	public void setAvgprice(double avgprice) {
		this.avgprice = avgprice;
	}

	public int getBillId()
    {
        return billId;
    }

    public String getConfirmFlage()
    {
        return confirmFlage;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public double getExportUnitPrice()
    {
        return exportUnitPrice;
    }

    public String getGoodsId()
    {
        return goodsId;
    }

    public String getGoodsName()
    {
        return goodsName;
    }

    public String getSalesPerson()
    {
        return salesPerson;
    }

    public void setBillId(int i)
    {
        billId = i;
    }

    public void setConfirmFlage(String string)
    {
        confirmFlage = string;
    }

    public void setCreateTime(String string)
    {
        createTime = string;
    }

    public void setCurRepertoryAmount(double i)
    {
        curRepertoryAmount = i;
    }

    public void setExportUnitPrice(double f)
    {
        exportUnitPrice = oConvert.getRound(f,2);
    }

    public void setGoodsId(String string)
    {
        goodsId = string;
    }

    public void setGoodsName(String string)
    {
        goodsName = string;
    }

    public void setSalesPerson(String string)
    {
        salesPerson = string;
    }

    
    public String getmembername()
    {
        return membername;
    }

    public void setmembername(String i)
    {
    	membername = i;
    }

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
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

	public void setExportAmount(double exportAmount) {
		this.exportAmount = oConvert.getRound(exportAmount,3);
	}

	public double getCurRepertoryAmount() {
		return curRepertoryAmount;
	}

	public double getExportAmount() {
		return exportAmount;
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

	public void setChandi(String chandi) {
		this.chandi = chandi;
	}

	public String getGuige() {
		return guige;
	}

	public void setGuige(String guige) {
		this.guige = guige;
	}

	public int getExportNum() {
		return exportNum;
	}

	public void setExportNum(double exportNum) {
		this.exportNum = (int)oConvert.getRound(exportNum,0);
	}
	public void setExportNum(int exportNum) {
		this.exportNum = exportNum;
	}
	public float getXishu() {
		return xishu;
	}

	public void setXishu(float xishu) {
		this.xishu = xishu;
	}

	public String getDanwei() {
		return danwei;
	}

	public void setDanwei(String danwei) {
		this.danwei = danwei;
	}

	public float getJianxishu() {
		return jianxishu;
	}

	public void setJianxishu(float jianxishu) {
		this.jianxishu = jianxishu;
	}

	public double getExportJianNum() {
		return exportJianNum;
	}

	public void setExportJianNum(double exportJianNum) {
		this.exportJianNum = oConvert.getRound(exportJianNum,1);
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

	public double getJiagong() {
		return jiagong;
	}

	public void setJiagong(double jiagong) {
		this.jiagong = oConvert.getRound(jiagong,2);
	}



    
}
