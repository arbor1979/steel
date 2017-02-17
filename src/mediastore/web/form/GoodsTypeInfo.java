package mediastore.web.form;

import org.apache.struts.action.ActionForm;

public class GoodsTypeInfo extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	private int id;
    private String name;
    private int classid;
    private int subNum;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSubNum() {
		return subNum;
	}
	public void setSubNum(int subNum) {
		this.subNum = subNum;
	}
}
