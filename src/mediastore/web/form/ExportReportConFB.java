// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ExportReportConFB.java

package mediastore.web.form;


import mediastore.rule.ExportReportRule;


public class ExportReportConFB
{

   
    
    private ExportReportResultInfo ersri;
    private ExportReportRule ersr;

    public ExportReportConFB()
    {
    }

    public ExportReportRule getErsr()
    {
        return ersr;
    }

    public ExportReportResultInfo getErsri()
    {
        return ersri;
    }
    public void setErsr(ExportReportRule rule)
    {
        ersr = rule;
    }

    public void setErsri(ExportReportResultInfo info)
    {
        ersri = info;
    }
 
}
