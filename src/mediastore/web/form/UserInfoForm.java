// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   UserInfo.java

package mediastore.web.form;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.apache.struts.action.ActionForm;


public class UserInfoForm extends ActionForm
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String userName;
    private String password;
    private String newpwd;
    private String role;
    private int userStatus;
    private int online;
    private String createTime;
    private String lastStatusChangeTime;
    private String lastLoginTime;
    private int deptid;
    private String deptName;
    private String deptShortName;
    private List menu;
    private List menusub;
    private boolean ifview;
    private boolean ifjiagong;
    private int pagesize;
    private boolean ifyunfei;
    private boolean ifyixiangjine;
    private String remoteip;
    private String logintime;
	public boolean isIfyixiangjine() {
		return ifyixiangjine;
	}

	public void setIfyixiangjine(boolean ifyixiangjine) {
		this.ifyixiangjine = ifyixiangjine;
	}

	public int getDeptid() {
		return deptid;
	}

	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}

	public UserInfoForm()
    {
    	id=0;
    	userName="";
    	password="";
    	newpwd="";
    	role="";
    	userStatus=1;
    	deptid=0;
    	deptName="";
    	deptShortName="";
    	menu=null;
    	menusub=null;
    	online=0;
    	ifview=false;
    	ifjiagong=false;
    	remoteip="";
    	logintime="";
    }

    public void setUserName(String userName) 
    {
        this.userName = userName.trim();
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserStatus(int userStatus)
    {
        this.userStatus = userStatus;
    }

    public int getUserStatus()
    {
        return userStatus;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setLastStatusChangeTime(String lastStatusChangeTime)
    {
        this.lastStatusChangeTime = lastStatusChangeTime;
    }

    public String getLastStatusChangeTime()
    {
        return lastStatusChangeTime;
    }

    public void setLastLoginTime(String lastLoginTime)
    {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginTime()
    {
        return lastLoginTime;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password.trim();
	}

	

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		if(role.length()>0)
		{
			if(role.substring(role.length()-1,role.length()).equals(","))
				role=role.substring(0, role.length()-1);
		}
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptShortName() {
		return deptShortName;
	}

	public void setDeptShortName(String deptShortName) {
		this.deptShortName = deptShortName;
	}

	public List getMenu() {
		return menu;
	}

	public void setMenu(List menu) {
		this.menu = menu;
	}

	public List getMenusub() {
		return menusub;
	}

	public void setMenusub(List menusub) {
		this.menusub = menusub;
	}

	public String getNewpwd() {
		return newpwd;
	}

	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd.trim();
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public boolean isIfview() {
		return ifview;
	}

	public void setIfview(boolean ifview) {
		this.ifview = ifview;
	}

	public boolean isIfjiagong() {
		return ifjiagong;
	}

	public void setIfjiagong(boolean ifjiagong) {
		this.ifjiagong = ifjiagong;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public boolean isIfyunfei() {
		return ifyunfei;
	}

	public void setIfyunfei(boolean ifyunfei) {
		this.ifyunfei = ifyunfei;
	}

	public String getRemoteip() {
		return remoteip;
	}

	public void setRemoteip(String remoteip) {
		this.remoteip = remoteip;
	}

	public String getLogintime() {
		return logintime;
	}

	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}

}
