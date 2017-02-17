// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsInfoObj.java

package mediastore.web.form;

import org.apache.struts.action.ActionForm;


public class PayHuoKuanForm extends ActionForm
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int factory;
    private String zhujima;
    private int kind;
    private int deptid;
    private String orderby;
    private int viewtype;
    public PayHuoKuanForm()
    {
    	factory=0;
    	zhujima="";
    	deptid=0;
    	kind=0;
    	orderby="";
    }

	public int getDeptid() {
		return deptid;
	}

	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}

	public int getFactory() {
		return factory;
	}

	public void setFactory(int factory) {
		this.factory = factory;
	}

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

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public int getViewtype() {
		return viewtype;
	}

	public void setViewtype(int viewtype) {
		this.viewtype = viewtype;
	}

	
}
