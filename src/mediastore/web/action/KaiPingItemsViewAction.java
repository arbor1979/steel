// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportItemsViewAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.GoodsStoreGoods;
import mediastore.dao.GoodsStoreInfo;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.KaiPingForm;
import mediastore.web.form.KaiPingInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class KaiPingItemsViewAction extends AbstractAction
{

    public KaiPingItemsViewAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
    		GoodsStoreInfo gsi = new GoodsStoreInfo();
			GoodsStoreGoods geg = new GoodsStoreGoods();
	    	String ifprint = oConvert.getString(actionContext.getParameter("ifprint"),"0");
	    	int billId = oConvert.getInt(actionContext.getParameter("billId"),0);
	    	int deptid = oConvert.getInt(actionContext.getParameter("deptid"),0);
	    	KaiPingForm kpf=new KaiPingForm();
	    	kpf.setBillid(billId);
	    	kpf.setDeptid(deptid);
	    	KaiPingInfoForm kpif=gsi.getKaiPingBillById(billId, deptid);
			List goodsList = geg.getKaiPingGoodsList(kpf);
			actionContext.getRequest().setAttribute("ibf", kpif);
			actionContext.getRequest().setAttribute("billDetail", goodsList);
			actionContext.getRequest().setAttribute("ifprint",ifprint);
			return actionContext.getMapping().findForward("KaiPingItemsView");
    	}
    	catch(Exception e)
        {
         	ErrorMsgFB ef=new ErrorMsgFB();
 			ef.setSource(oConvert.filterSQL(e.getMessage()));
 			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
 	        return actionContext.getMapping().findForward("Errors");
        }
    }
}
