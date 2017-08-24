// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsInfoObj.java

package mediastore.web.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;


public class FileUploadForm extends ActionForm
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6196471935789170586L;
	private FormFile file;
	private String importtype;
	public String getImporttype() {
		return importtype;
	}
	public void setImporttype(String importtype) {
		this.importtype = importtype;
	}
	public FormFile getFile() {
		return file;
	}
	public void setFile(FormFile file) {
		this.file = file;
	}
	
	
    

}
