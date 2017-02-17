// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MemberManageFB.java

package mediastore.web.form;

import java.util.List;

public class MemberManageFB
{

    private int totalMemberNum;
    private List memberList;
    private String orderStr;
    private int StartRecNum;
    private int PageRecNum;

    public MemberManageFB()
    {
    }

    public List getMemberList()
    {
        return memberList;
    }

    public String getOrderStr()
    {
        return orderStr;
    }

    public int getTotalMemberNum()
    {
        return totalMemberNum;
    }

    public void setMemberList(List list)
    {
        memberList = list;
    }

    public void setOrderStr(String string)
    {
        orderStr = string;
    }

    public void setTotalMemberNum(int i)
    {
        totalMemberNum = i;
    }
    public int getStartRecNum()
    {
        return StartRecNum;
    }
    public void setStartRecNum(int i)
    {
    	StartRecNum = i;
    }
    public int getPageRecNum()
    {
        return PageRecNum;
    }
    public void setPageRecNum(int i)
    {
    	PageRecNum = i;
    }

}
