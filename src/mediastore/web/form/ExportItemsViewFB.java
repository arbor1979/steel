// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ExportItemsViewFB.java

package mediastore.web.form;

import java.util.List;

public class ExportItemsViewFB
{

    private List gegiList;
    private boolean ifprint;
    private boolean ifreprint;
    private float pifadiscount;
    private String billId;
    private int payornot;
    private float oddment;
    private float realmoney;
    private float useDaijin;
    private String membername; 
    private String memberId;
    private String exporttime;
    public ExportItemsViewFB()
    {
    }

    public List getGegiList()
    {
        return gegiList;
    }

    public void setGegiList(List list)
    {
        gegiList = list;
    }
    public boolean getIfprint()
    {
        return ifprint;
    }

    public void setIfprint(boolean flag)
    {
    	ifprint = flag;
    }
    
    public boolean getIfreprint()
    {
        return ifreprint;
    }

    public void setIfreprint(boolean flag)
    {
    	ifreprint = flag;
    }
    
    public float getPifadiscount()
    {
        return pifadiscount;
    }

    public void setPifadiscount(float flag)
    {
    	pifadiscount = flag;
    }
    public String getbillId()
    {
        return billId;
    }
    public String getmemberName()
    {
        return membername;
    }
    public void setmemberName(String memberNa)
    {
    	membername = memberNa;
    }
    public String getmemberId()
    {
        return memberId;
    }
    public void setmemberId(String Id)
    {
    	memberId = Id;
    }
    public void setbillId(String Id)
    {
    	billId = Id;
    }
    public int getPayornot()
    {
        return payornot;
    }

    public void setPayornot(int flag)
    {
    	payornot = flag;
    }
    public float getOddment()
    {
    	return oddment;
    }
    public void setOddment(float flag)
    {
    	oddment=flag;
    }
    public float getrealmoney()
    {
    	return realmoney;
    }
    public void setrealmoney(float flag)
    {
    	realmoney=flag;
    }
    public float getDaijin()
    {
    	return useDaijin;
    }
    public void setDaijin(float flag)
    {
    	useDaijin=flag;
    }
    public String getExporttime()
    {
    	return exporttime;
    }
    public void setExporttime(String flag)
    {
    	exporttime=flag;
    }
}
