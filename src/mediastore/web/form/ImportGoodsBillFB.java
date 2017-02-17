// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportGoodsBillFB.java

package mediastore.web.form;

import java.util.List;

public class ImportGoodsBillFB
{

    private ImportBillForm ibf;
    private List goodsList;
    private List YeWuList;
    private List FactList;
    private List fkList;
    private List fpctList;
    private List tkList;
    private String zhujima;
    private int kind;
    private int selectmode;
	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public List getTkList() {
		return tkList;
	}

	public void setTkList(List tkList) {
		this.tkList = tkList;
	}

	public ImportGoodsBillFB()
    {
    }

    public List getGoodsList()
    {
        return goodsList;
    }

    public void setGoodsList(List list)
    {
        goodsList = list;
    }
    
    public List getYeWuList()
    {
        return YeWuList;
    }

    public void setYewuList(List list)
    {
        YeWuList = list;
    }
    
    public List getFactList()
    {
        return FactList;
    }

    public void setFactList(List list)
    {
        FactList = list;
    }

	public List getFpctList() {
		return fpctList;
	}

	public void setFpctList(List fpctList) {
		this.fpctList = fpctList;
	}

	public void setYeWuList(List yeWuList) {
		YeWuList = yeWuList;
	}

	public List getFkList() {
		return fkList;
	}

	public void setFkList(List fkList) {
		this.fkList = fkList;
	}

	public ImportBillForm getIbf() {
		return ibf;
	}

	public void setIbf(ImportBillForm ibf) {
		this.ibf = ibf;
	}

	public String getZhujima() {
		return zhujima;
	}

	public void setZhujima(String zhujima) {
		this.zhujima = zhujima;
	}

	public int getSelectmode() {
		return selectmode;
	}

	public void setSelectmode(int selectmode) {
		this.selectmode = selectmode;
	}

}
