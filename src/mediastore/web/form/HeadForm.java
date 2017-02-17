// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsInfoObj.java

package mediastore.web.form;

import org.apache.struts.action.ActionForm;


public class HeadForm extends ActionForm
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String value;
	private int colspan;
	private int rowspan;
	public HeadForm()
    {
		id=0;
		value="";
		colspan=1;
		rowspan=1;
    }
	public HeadForm(String Value)
    {
		id=0;
		value=Value;
		colspan=1;
		rowspan=1;
    }
	public int getColspan() {
		return colspan;
	}
	public void setColspan(int colspan) {
		this.colspan = colspan;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String name) {
		this.value = name;
	}
	public int getRowspan() {
		return rowspan;
	}
	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}
	

}
