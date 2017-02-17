package mediastore.web.form;

import org.apache.struts.action.ActionForm;

public class GoodsStoreForm extends ActionForm
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	private int id;
    private String name;
    private int subnum;
    public GoodsStoreForm()
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
	public int getSubnum() {
		return subnum;
	}
	public void setSubnum(int subnum) {
		this.subnum = subnum;
	}

}
