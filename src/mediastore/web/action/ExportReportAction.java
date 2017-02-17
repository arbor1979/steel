// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ExportBillSearchAction.java

package mediastore.web.action;

import mediastore.rule.ExportReportRule;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;
import mediastore.web.struts.DynamicForm;

import org.apache.struts.action.ActionForward;

public class ExportReportAction extends AbstractAction
{

    public ExportReportAction()
    {
    }
    //销售报表统计
    public ActionForward execute(ActionContext actionContext)
    {
      
        String bbtjStr = actionContext.getParameter("bbtj");  
        String exportTime1 = actionContext.getParameter("exporttime1");
        if(exportTime1 == null)
            exportTime1 = "1978-06-01 23:59:59";
        String exportTime2 = actionContext.getParameter("exporttime2");
        if(exportTime2 == null)
            exportTime2 = "2078-06-01 23:59:59";
        String paymentTime1 = actionContext.getParameter("paymenttime1");
        if(paymentTime1 == null)
            paymentTime1 = "";
        String paymentTime2 = actionContext.getParameter("paymenttime2");
        if(paymentTime2 == null)
            paymentTime2 = "";
        ExportReportRule ersr = new ExportReportRule();
     
        ersr.setExportTime1(exportTime1);
        ersr.setExportTime2(exportTime2);
        ersr.setPaymentTime1(paymentTime1);
        ersr.setPaymentTime2(paymentTime2);
        ersr.setBbtj(bbtjStr);
       
        DynamicForm dynaForm = (DynamicForm)actionContext.getForm();
        dynaForm.put("exportReportRule", ersr);
        return null;
    }
}
