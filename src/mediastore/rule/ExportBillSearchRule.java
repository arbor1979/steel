// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ExportBillSearchRule.java

package mediastore.rule;


public class ExportBillSearchRule
{

    private int startRecNum;
    private int recNumOfPage;
    private String orderStr;
    private int billId;
    private int role;
    private int memberId;
    private String salesPersons;
    private String exportTime1;
    private String exportTime2;
    private String paymentTime1;
    private String paymentTime2;
    private String paytype;

    public ExportBillSearchRule()
    {
    }

    public int getBillId()
    {
        return billId;
    }
    public int getRole()
    {
        return role;
    }
    public String getExportTime1()
    {
        return exportTime1;
    }

    public String getExportTime2()
    {
        return exportTime2;
    }

    public String getOrderStr()
    {
        return orderStr;
    }

    public String getPaymentTime1()
    {
        return paymentTime1;
    }

    public String getPaymentTime2()
    {
        return paymentTime2;
    }

    public int getRecNumOfPage()
    {
        return recNumOfPage;
    }

    public String getSalesPersons()
    {
        return salesPersons;
    }

    public int getStartRecNum()
    {
        return startRecNum;
    }

    public void setBillId(int i)
    {
        billId = i;
    }
    public void setRole(int i)
    {
        role = i;
    }
    public void setExportTime1(String string)
    {
        exportTime1 = string;
    }

    public void setExportTime2(String string)
    {
        exportTime2 = string;
    }

    public void setOrderStr(String string)
    {
        orderStr = string;
    }

    public void setPaymentTime1(String string)
    {
        paymentTime1 = string;
    }

    public void setPaymentTime2(String string)
    {
        paymentTime2 = string;
    }

    public void setRecNumOfPage(int i)
    {
        recNumOfPage = i;
    }

    public void setSalesPersons(String string)
    {
        salesPersons = string;
    }

    public void setStartRecNum(int i)
    {
        startRecNum = i;
    }

    public int getMemberId()
    {
        return memberId;
    }

    public void setMemberId(int i)
    {
        memberId = i;
    }
    public String getPaytype()
    {
        return paytype;
    }

    public void setpaytype(String i)
    {
    	paytype = i;
    }
}
