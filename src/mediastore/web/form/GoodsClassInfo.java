package mediastore.web.form;

import org.apache.struts.action.ActionForm;

public class GoodsClassInfo extends ActionForm
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	private int id;
    private String name;
    private int subNum;
    
    public GoodsClassInfo()
    {
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
