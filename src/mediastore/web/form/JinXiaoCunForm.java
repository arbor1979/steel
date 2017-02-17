// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsInfoObj.java

package mediastore.web.form;


public class JinXiaoCunForm
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String goodsId;
    private String goodsName;
    private double importAmount;
    private double checkAmount;
    private double zhuanAmount;
    private double exportAmount;
    private double leftAmount;
    
    public JinXiaoCunForm()
    {
    	goodsId="";
    	goodsName="";
    	importAmount=0;
    	checkAmount=0;
    	zhuanAmount=0;
    	exportAmount=0;
    	leftAmount=0;
    }

	public double getCheckAmount() {
		return checkAmount;
	}

	public void setCheckAmount(double checkAmount) {
		this.checkAmount = checkAmount;
	}

	public double getExportAmount() {
		return exportAmount;
	}

	public void setExportAmount(double exportAmount) {
		this.exportAmount = exportAmount;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public double getImportAmount() {
		return importAmount;
	}

	public void setImportAmount(double importAmount) {
		this.importAmount = importAmount;
	}

	public double getLeftAmount() {
		return leftAmount;
	}

	public void setLeftAmount(double leftAmount) {
		this.leftAmount = leftAmount;
	}

	public double getZhuanAmount() {
		return zhuanAmount;
	}

	public void setZhuanAmount(double zhuanAmount) {
		this.zhuanAmount = zhuanAmount;
	}


}
