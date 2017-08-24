// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   UserManageAction.java

package mediastore.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mediastore.dao.CaiWu;
import mediastore.user.DepartManager;
import mediastore.util.ExcelHelper;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.FileUploadForm;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.upload.FormFile;

public class InitBillImportAction extends AbstractAction
{

    public InitBillImportAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
	    	String param = oConvert.getString(actionContext.getParameter("param"),"list");
	    	UserInfoForm  ctx=(UserInfoForm) actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
	    	
	        DepartManager um = new DepartManager();
			if (param.equals("import"))
			{
				FileUploadForm fileUploadForm = (FileUploadForm) actionContext.getForm();  
				FormFile uploadFile = fileUploadForm.getFile();  
				String path=actionContext.getRequest().getRealPath("");
				String filePath=path+"/upload/"+uploadFile.getFileName();
				try {  
			        FileOutputStream outer = new FileOutputStream(filePath);  
			        byte[] buffer = uploadFile.getFileData();  
			        outer.write(buffer);  
			        outer.close();  
			        uploadFile.destroy();  
			    } catch (Exception e) {  
			        throw new Exception("保存文件失败");
			    }  
				HttpServletResponse response =actionContext.getResponse();
				HttpServletRequest request=actionContext.getRequest();
				response.setCharacterEncoding("utf-8");
		        response.setContentType("application/json");
		        PrintWriter out = response.getWriter();
		        FileItem item=createFileItem(filePath);
		        ExcelHelper helper = new ExcelHelper();
		        
                if (item.getName().endsWith(".xls")||item.getName().endsWith(".xlsx")) {
                    // 说明是文件,不过这里最好限制一下
                	List<HashMap> list=helper.importXlsx(item.getInputStream());
                	if(list.size()==0)
                		throw new Exception("没有可导入的数据");
                	CaiWu caiwu=new CaiWu();
                	caiwu.insertInitNeedPay(list, ctx.getDeptid(),ctx.getUserName(),fileUploadForm.getImporttype());
                    throw new Exception("成功导入"+list.size()+"行！");
                } else {
                    // 说明文件格式不符合要求
                    throw new Exception("文件格式不正确");
                }
				
			}
			
			else 
			{
				
	     	    return actionContext.getMapping().findForward("InitBillImport");
			}
			
	        
    	}
    	catch(Exception e)
        {
         	ErrorMsgFB ef=new ErrorMsgFB();
 			ef.setSource(oConvert.filterSQL(e.getMessage()));
 			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
 	        return actionContext.getMapping().findForward("Errors");
        }
		
    }
    private FileItem createFileItem(String filePath) throws Exception
    {
        FileItemFactory factory = new DiskFileItemFactory();
        String textFieldName = "textField";
        int num = filePath.lastIndexOf(".");
        String extFile = filePath.substring(num);
        FileItem item = factory.createItem(textFieldName, "text/plain", true,
            "MyFileName" + extFile);
        File newfile = new File(filePath);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try
        {
            FileInputStream fis = new FileInputStream(newfile);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192))
                != -1)
            {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        }
        catch (IOException e)
        {
            throw e;
        }

        return item;

    }
}
