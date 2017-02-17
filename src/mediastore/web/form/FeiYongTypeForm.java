// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsInfoObj.java

package mediastore.web.form;

import org.apache.struts.action.ActionForm;


public class FeiYongTypeForm extends ActionForm
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String classname;
    private String typename;
    private int kind;
    private int classid;
    private int subcount;
    private boolean iflock;
    public FeiYongTypeForm()
    {
    	id=0;
    	classname="";
    	kind=1;
    	classid=0;
    	typename="";
    	subcount=0;
    	iflock=false;
    }

	public int getClassid() {
		return classid;
	}

	public void setClassid(int classid) {
		this.classid = classid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public int getSubcount() {
		return subcount;
	}

	public void setSubcount(int subcount) {
		this.subcount = subcount;
	}

	public boolean isIflock() {
		return iflock;
	}

	public void setIflock(boolean iflock) {
		this.iflock = iflock;
	}

    

}
