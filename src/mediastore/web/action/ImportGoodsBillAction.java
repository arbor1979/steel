// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ImportGoodsBillShowAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.CaiWu;
import mediastore.dao.GoodsImportGoods;
import mediastore.dao.GoodsImportInfo;
import mediastore.dao.GoodsInfo;
import mediastore.user.FactoryManager;
import mediastore.user.TruckManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.FactoryInfoForm;
import mediastore.web.form.GoodsImportGoodsInfo;
import mediastore.web.form.GoodsInfoForm;
import mediastore.web.form.ImportBillForm;
import mediastore.web.form.ImportGoodsBillFB;
import mediastore.web.form.TruckInfoForm;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class ImportGoodsBillAction extends AbstractAction
{

    public ImportGoodsBillAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	
    	try
    	{
	    	String param = oConvert.getString(actionContext.getParameter("param"),"list");
	    	String zhujima = oConvert.getString(actionContext.getParameter("zhujima"),"");
	    	int selectmode = oConvert.getInt((String)actionContext.getParameter("selectmode"),0);
	    	GoodsImportGoodsInfo gif=(GoodsImportGoodsInfo)actionContext.getForm();
			if(gif==null)
				gif=new GoodsImportGoodsInfo();
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
	        gif.setCreatePerson(ctx.getUserName());
	        gif.setDeptid(ctx.getDeptid());
	        
			GoodsImportInfo giinfo = new GoodsImportInfo();
			GoodsImportGoods gig = new GoodsImportGoods();
			
	    	if(param.equals("add"))
	    	{
	    		//插入入库明细表
	    		GoodsInfo gi=new GoodsInfo();
	    		double kucun=gi.getRepertoryAmount(gif.getGoodsId(),gif.getStoreId());
	    		gif.setCurRepertoryAmount(kucun);
				GoodsInfoForm g=gi.getGoodsInfoByGoodsId(gif.getGoodsId());
				if(g==null)
					throw new Exception("不存在此编号的产品");
				gif.setGoodsName(g.getGoodsTypeName());
				gif.setImportUnitPrice(gig.getLastImportUnitPrice(gif.getDeptid(),gif.getGoodsId()));
				gif.setImportNum(0);
		        gig.insertNewGoodsRec(gif);
	    	}
	    	else if(param.equals("del"))
	    	{
		        gig.deleteGoodsRec(gif);
	    	}
	    	else if(param.equals("updateJianNum"))
	    	{
	    		if(gif.getImportJianNum()<=0)
					throw new Exception("件数必须大于零");
	    		gig.updateImportJianNum(gif);
	    	}
	    	else if(param.equals("updateNum"))
	    	{
	    		if(gif.getImportNum()<0)
					throw new Exception("数量必须大于零");
	    		gig.updateImportNum(gif);
	    	}
	    	else if(param.equals("updatePrice"))
	    	{
	    		if(gif.getImportUnitPrice()<=0)
					throw new Exception("单价必须大于零");
		        gig.updatePrice(gif);
	    	}
	    	else if(param.equals("updateMoney"))
	    	{
	    		if(gif.getImportMoney()<=0)
					throw new Exception("金额必须大于零");
		        gig.updateMoney(gif);
	    	}
	    	else if(param.equals("updateAmount"))
	    	{
	    		if(gif.getImportAmount()<=0)
					throw new Exception("重量必须大于零");
		        gig.updateAmount(gif);
	    	}
	    	else if(param.equals("updateMemo"))
	    	{
		        gig.updateMemo(gif);
	    	}
	    	
	    	else if(param.equals("delall"))
	    	{
		        gig.deleteAllGoods(gif);
	    	}
			int maxBillId = giinfo.getTempBillIDNum(ctx.getUserName(),ctx.getDeptid(),gif.getKind());
			ImportBillForm ibf=giinfo.getImportBillById(maxBillId,ctx.getDeptid());
	        List goodsList = gig.getGoodsList(maxBillId,ctx.getDeptid());
	        ImportGoodsBillFB igbfb = new ImportGoodsBillFB();
	       	 
	        FactoryManager fum = new FactoryManager();
	        FactoryInfoForm fi=new FactoryInfoForm();
	        fi.setKind("100");
	        fi.setZhujima(zhujima);
	        java.util.List FactList = fum.getFactList("a.name",fi);
	        CaiWu cw=new CaiWu();
	        List fk=cw.getFuKuanTypeList(-1);
	        List fp=cw.getFPTypeList();
	        TruckManager tm = new TruckManager();
	        TruckInfoForm tif=new TruckInfoForm();
	        java.util.List TruckList = tm.getTruckList("carno",tif);
	        igbfb.setIbf(ibf);
	        igbfb.setGoodsList(goodsList);
	        igbfb.setFactList(FactList);
	        igbfb.setFkList(fk);
	        igbfb.setFpctList(fp);
	        igbfb.setTkList(TruckList);
	        igbfb.setZhujima(zhujima);
	        igbfb.setKind(gif.getKind());
	        igbfb.setSelectmode(selectmode);
	        actionContext.getRequest().setAttribute(Globals.REQUEST_IMPORTGOODSBILL, igbfb);
	        return actionContext.getMapping().findForward("ImportGoodsBill");
    	}
    	catch(Exception e)
        {
         	ErrorMsgFB ef=new ErrorMsgFB();
 			ef.setSource(oConvert.filterSQL(e.getMessage()));
 			ef.seturl("importGoodsBill.do?kind="+actionContext.getParameter("kind"));
 			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
 	        return actionContext.getMapping().findForward("Errors");
        }
    }
}
