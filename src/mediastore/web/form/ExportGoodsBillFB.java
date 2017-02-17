// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsExportBillInfo.java

package mediastore.web.form;

import java.util.List;


public class ExportGoodsBillFB
{

    private ExportBillForm ebf;
    private List factList;
    private List goodsList;
    private List fpList;
    private List fkList;
    private List tkList;
    private List ywList;
    private String zhujima;
    private int kind;
    private int selectmode;
	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public String getZhujima() {
		return zhujima;
	}

	public void setZhujima(String zhujima) {
		this.zhujima = zhujima;
	}

	public ExportGoodsBillFB()
    {
    }

	public List getFactList() {
		return factList;
	}

	public void setFactList(List factList) {
		this.factList = factList;
	}

	public List getFkList() {
		return fkList;
	}

	public void setFkList(List fkList) {
		this.fkList = fkList;
	}

	public List getFpList() {
		return fpList;
	}

	public void setFpList(List fpList) {
		this.fpList = fpList;
	}

	public List getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List goodsList) {
		this.goodsList = goodsList;
	}

	public ExportBillForm getEbf() {
		return ebf;
	}

	public void setEbf(ExportBillForm ebf) {
		this.ebf = ebf;
	}

	public List getTkList() {
		return tkList;
	}

	public void setTkList(List tkList) {
		this.tkList = tkList;
	}

	public int getSelectmode() {
		return selectmode;
	}

	public void setSelectmode(int selectmode) {
		this.selectmode = selectmode;
	}

	public List getYwList() {
		return ywList;
	}

	public void setYwList(List ywList) {
		this.ywList = ywList;
	}

    
}
