// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportBillSearchRule.java

package mediastore.rule;


public class ImportBillSearchRule
{

    private int startRecNum;
    private int recNumOfPage;
    private String orderStr;
    private int billId;
    private String sendPersons;
    private String factory;
    private String inceptPersons;
    private String importTime1;
    private String importTime2;
    private String paymentTime1;
    private String paymentTime2;

    public ImportBillSearchRule()
    {
    }

    public int getBillId()
    {
        return billId;
    }

    public String getImportTime1()
    {
        return importTime1;
    }

    public String getImportTime2()
    {
        return importTime2;
    }

    public String getInceptPersons()
    {
        return inceptPersons;
    }
    public String getFactory()
    {
        return factory;
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

    public String getSendPersons()
    {
        return sendPersons;
    }

    public int getStartRecNum()
    {
        return startRecNum;
    }

    public void setBillId(int i)
    {
        billId = i;
    }

    public void setImportTime1(String string)
    {
        importTime1 = string;
    }

    public void setImportTime2(String string)
    {
        importTime2 = string;
    }

    public void setInceptPersons(String string)
    {
        inceptPersons = string;
    }
    public void setFactory(String string)
    {
        factory = string;
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

    public void setSendPersons(String string)
    {
        sendPersons = string;
    }

    public void setStartRecNum(int i)
    {
        startRecNum = i;
    }

    public int getRecNumOfPage()
    {
        return recNumOfPage;
    }

    public void setRecNumOfPage(int i)
    {
        recNumOfPage = i;
    }
}
