// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ExportBillSearchConAction.java

package mediastore.web.action;

import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;
import mediastore.web.struts.DynamicForm;

import org.apache.struts.action.ActionForward;

public class ExportReportConAction extends AbstractAction
{

    public ExportReportConAction()
    {
    }
    //销售报表统计
    public ActionForward execute(ActionContext actionContext)
    {

        DynamicForm dynaForm = (DynamicForm)actionContext.getForm();
        dynaForm.put("exportReportRule", null);
        return null;
    }
}
