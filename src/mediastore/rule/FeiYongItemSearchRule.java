// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportBillSearchRule.java

package mediastore.rule;

import mediastore.web.form.FeiYongGoodsForm;


public class FeiYongItemSearchRule
{

    private int curpage;
    private String orderStr;
    private FeiYongGoodsForm fyf;
    private String createtime1;
    private String createtime2;
    private String paymenttime1;
    private String paymenttime2;
    private String oper;
    
    
    public FeiYongItemSearchRule()
    {
    	curpage=1;
    	orderStr="";
    	createtime1="";
    	createtime2="";
    	paymenttime1="";
    	paymenttime2="";
    	fyf=new FeiYongGoodsForm();
    }


	public String getCreatetime1() {
		return createtime1;
	}


	public void setCreatetime1(String createtime1) {
		this.createtime1 = createtime1;
	}


	public String getCreatetime2() {
		return createtime2;
	}


	public void setCreatetime2(String createtime2) {
		this.createtime2 = createtime2;
	}


	public int getCurpage() {
		return curpage;
	}


	public void setCurpage(int curpage) {
		this.curpage = curpage;
	}


	public FeiYongGoodsForm getFyf() {
		return fyf;
	}


	public void setFyf(FeiYongGoodsForm fyf) {
		this.fyf = fyf;
	}


	public String getOper() {
		return oper;
	}


	public void setOper(String oper) {
		this.oper = oper;
	}


	public String getOrderStr() {
		return orderStr;
	}


	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}


	public String getPaymenttime1() {
		return paymenttime1;
	}


	public void setPaymenttime1(String paymenttime1) {
		this.paymenttime1 = paymenttime1;
	}


	public String getPaymenttime2() {
		return paymenttime2;
	}


	public void setPaymenttime2(String paymenttime2) {
		this.paymenttime2 = paymenttime2;
	}


   
}
