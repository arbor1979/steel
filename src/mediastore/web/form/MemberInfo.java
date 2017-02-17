// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MemberInfo.java

package mediastore.web.form;


public class MemberInfo
{

    private int memberId;
    private String memberName;
    private float discount;
    private String memberTel;
    private String memberAddr;
    private String createTime;
    private float totalPayment;
    private float persent;
    private String recentBuyTime;

    public MemberInfo()
    {
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public float getDiscount()
    {
        return discount;
    }

    public int getMemberId()
    {
        return memberId;
    }

    public String getMemberName()
    {
        return memberName;
    }
    public String getMemberAddr()
    {
        return memberAddr;
    }public String getMemberTel()
    {
        return memberTel;
    }
    public String getRecentBuyTime()
    {
        return recentBuyTime;
    }

    public float getpersent()
    {
        return persent;
    }
    public float getTotalPayment()
    {
        return totalPayment;
    }

    public void setCreateTime(String string)
    {
        createTime = string;
    }

    public void setDiscount(float f)
    {
        discount = f;
    }

    public void setMemberId(int i)
    {
        memberId = i;
    }

    public void setMemberName(String string)
    {
        memberName = string;
    }
    public void setMemberTel(String string)
    {
        memberTel = string;
    }
    public void setMemberAddr(String string)
    {
        memberAddr = string;
    }
    public void setRecentBuyTime(String string)
    {
        recentBuyTime = string;
    }

    public void setTotalPayment(float f)
    {
        totalPayment = f;
    }
    public void setpersent(float f)
    {
    	persent = f;
    }
}
