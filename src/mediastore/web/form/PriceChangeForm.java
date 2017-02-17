// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsInfoObj.java

package mediastore.web.form;

import org.apache.struts.action.ActionForm;


public class PriceChangeForm extends ActionForm
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private String qijian;
    private String qijian1;
    private String goodsid;
    private String kind;
    private int deptid;
    
    public PriceChangeForm()
    {
    	qijian="";
    	qijian1="";
    	goodsid="";
    	deptid=0;
    	kind="month";
    }

	public int getDeptid() {
		return deptid;
	}

	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public String getQijian() {
		return qijian;
	}

	public void setQijian(String qijian) {
		this.qijian = qijian;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getQijian1() {
		return qijian1;
	}

	public void setQijian1(String qijian1) {
		if(qijian1.length()==1)
			qijian1="0"+qijian1;
		this.qijian1 = qijian1;
	}
	

}
