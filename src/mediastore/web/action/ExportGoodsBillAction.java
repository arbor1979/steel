// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ExportGoodsBillShowAction.java

package mediastore.web.action;

import java.util.List;

import mediastore.dao.CaiWu;
import mediastore.dao.GoodsExportGoods;
import mediastore.dao.GoodsExportInfo;
import mediastore.dao.GoodsInfo;
import mediastore.user.FactoryManager;
import mediastore.user.TruckManager;
import mediastore.user.YeWuManager;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.ExportBillForm;
import mediastore.web.form.ExportGoodsBillFB;
import mediastore.web.form.FactoryInfoForm;
import mediastore.web.form.GoodsExportGoodsInfo;
import mediastore.web.form.GoodsInfoForm;
import mediastore.web.form.TruckInfoForm;
import mediastore.web.form.UserInfoForm;
import mediastore.web.form.YeWuInfoForm;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class ExportGoodsBillAction extends AbstractAction
{

    public ExportGoodsBillAction()
    {
    }
    //售货单录入
    public ActionForward execute(ActionContext actionContext)
    {
    	try
    	{
    		
    		String param = oConvert.getString(actionContext.getParameter("param"),"list");
    		String zhujima = oConvert.getString(actionContext.getParameter("zhujima"),"");
    		int selectmode = oConvert.getInt((String)actionContext.getParameter("selectmode"),1);
    		GoodsExportGoodsInfo gif=(GoodsExportGoodsInfo)actionContext.getForm();
			if(gif==null)
				gif=new GoodsExportGoodsInfo();
			UserInfoForm ctx = (UserInfoForm)actionContext.getSession().getAttribute(Globals.SESSION_CONTEXT);
	        gif.setSalesPerson(ctx.getUserName());
	        gif.setDeptid(ctx.getDeptid());
			GoodsExportInfo geinfo = new GoodsExportInfo();
			GoodsExportGoods geg = new GoodsExportGoods();
			
	    	if(param.equals("add"))
	    	{
	    		GoodsInfo gi=new GoodsInfo();
				GoodsInfoForm g=gi.getGoodsInfoByGoodsId(gif.getGoodsId());
	    		//插入入库明细表
				if(g==null)
					throw new Exception("不存在此编号的产品");
				double kucun=gi.getRepertoryAmount(gif.getGoodsId(),gif.getStoreId());
				gif.setCurRepertoryAmount(kucun);
				gif.setGoodsName(g.getGoodsTypeName());
				double sellprice=gi.getSellPrice(gif.getGoodsId(), gif.getDeptid());
				if(sellprice>0)
					gif.setExportUnitPrice(sellprice);
				else
					gif.setExportUnitPrice(g.getPurchaseUnitPrice());
				gif.setExportNum(0);
				gif.setExportAmount(0);
		        geg.insertNewGoodsRec(gif);
	    	}
	    	else if(param.equals("del"))
	    	{
	    		geg.deleteGoodsRec(gif);
	    	}
	    	else if(param.equals("updateJianNum"))
	    	{
	    		if(gif.getExportJianNum()<0)
					throw new Exception("件数必须大于等于零");
		        geg.updateExportJianNum(gif);
	    	}
	    	else if(param.equals("updateNum"))
	    	{
	    		if(gif.getExportNum()<0)
					throw new Exception("数量必须大于等于零");
		        geg.updateExportNum(gif);
	    	}
	    	else if(param.equals("updateAmount"))
	    	{
	    		if(gif.getExportAmount()<0)
					throw new Exception("重量必须大于等于零");
		        geg.updateExportAmount(gif);
	    	}
	    	else if(param.equals("updatePrice"))
	    	{
	    		if(gif.getExportUnitPrice()<=0)
					throw new Exception("单价必须大于0");
		        geg.updatePrice(gif);
	    	}
	    	else if(param.equals("updateJiaGong"))
	    	{
	    		if(gif.getJiagong()<0)
					throw new Exception("必须大于等于0");
		        geg.updateJiaGong(gif);
	    	}
	    	else if(param.equals("updateMemo"))
	    	{
		        geg.updateMemo(gif);
	    	}
	    	else if(param.equals("delAll"))
	    	{
		        geg.deleteAllGoods(gif);
	    	}
	    	int maxBillId = geinfo.getTempBillIDNum(ctx.getUserName(),ctx.getDeptid(),gif.getKind());
	    	ExportBillForm ebf=geinfo.getExportBillById(maxBillId,ctx.getDeptid());
	    	List goodsList = geg.getGoodsList(maxBillId,ctx.getDeptid());
	        
	    	ExportGoodsBillFB egbfb = new ExportGoodsBillFB();	       
	        
	        FactoryManager fum = new FactoryManager();
	        FactoryInfoForm fi=new FactoryInfoForm();
	        fi.setKind("011");
	        fi.setZhujima(zhujima);
	        java.util.List FactList = fum.getFactList("a.name",fi);
	        CaiWu cw=new CaiWu();
	        List fk=cw.getFuKuanTypeList(1);
	        List fp=cw.getFPTypeList();
	        TruckManager tm = new TruckManager();
	        TruckInfoForm tif=new TruckInfoForm();
	        java.util.List TruckList = tm.getTruckList("carno",tif);
	        YeWuManager um = new YeWuManager();
			YeWuInfoForm ywf=new YeWuInfoForm();
	        java.util.List ywList = um.getYeWuList("a.name",ywf);
	        
	        egbfb.setEbf(ebf);
	        egbfb.setFactList(FactList);
	        egbfb.setGoodsList(goodsList);
	        egbfb.setFpList(fp);
	        egbfb.setFkList(fk);
	        egbfb.setTkList(TruckList);
	        egbfb.setZhujima(zhujima);
	        egbfb.setKind(gif.getKind());
	        egbfb.setSelectmode(selectmode);
	        egbfb.setYwList(ywList);
	        actionContext.getRequest().setAttribute(Globals.REQUEST_EXPORTGOODSBILL, egbfb);
	        return actionContext.getMapping().findForward("ExportGoodsBill");
    	}
    	catch(Exception e)
        {
         	ErrorMsgFB ef=new ErrorMsgFB();
 			ef.setSource(e.getMessage());
 			ef.seturl("exportGoodsBill.do?kind="+actionContext.getParameter("kind"));
 			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
 	        return actionContext.getMapping().findForward("Errors");
        }
    }
}
