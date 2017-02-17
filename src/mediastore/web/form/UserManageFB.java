// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   UserManageFB.java

package mediastore.web.form;

import java.util.List;

public class UserManageFB
{

    private int totalUserNum;
    private int onlineUserNum;
    private int offlineUserNum;
    private List userList;
    private String orderStr;

    public UserManageFB()
    {
    }

    public void setTotalUserNum(int totalUserNum)
    {
        this.totalUserNum = totalUserNum;
    }

    public int getTotalUserNum()
    {
        return totalUserNum;
    }

    public void setOnlineUserNum(int onlineUserNum)
    {
        this.onlineUserNum = onlineUserNum;
    }

    public int getOnlineUserNum()
    {
        return onlineUserNum;
    }

    public void setOfflineUserNum(int offlineUserNum)
    {
        this.offlineUserNum = offlineUserNum;
    }

    public int getOfflineUserNum()
    {
        return offlineUserNum;
    }

    public void setUserList(List userList)
    {
        this.userList = userList;
    }

    public List getUserList()
    {
        return userList;
    }

    public String getOrderStr()
    {
        return orderStr;
    }

    public void setOrderStr(String string)
    {
        orderStr = string;
    }
}
