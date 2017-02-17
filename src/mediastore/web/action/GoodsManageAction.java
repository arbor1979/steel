// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   GoodsSearchAction.java

package mediastore.web.action;

import mediastore.dao.GoodsInfo;
import mediastore.util.oConvert;
import mediastore.web.form.ErrorMsgFB;
import mediastore.web.form.GoodsInfoForm;
import mediastore.web.form.ResultsetList;
import mediastore.web.global.Globals;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class GoodsManageAction extends AbstractAction
{

    public GoodsManageAction()
    {
    }

    public ActionForward execute(ActionContext actionContext)
    {
    	try
        {
    		String param = oConvert.getString(actionContext.getParameter("param"),"list");
    		GoodsInfoForm gif=(GoodsInfoForm)actionContext.getForm();
			GoodsInfo um = new GoodsInfo();
			int curpage = oConvert.getInt(actionContext.getParameter("curpage"),1);
			
    		if(param.equals("add"))
    		{
    			
    			actionContext.getRequest().setAttribute("gif",gif);
		        return actionContext.getMapping().findForward("GoodsManageEdit");
    		}
    		else if(param.equals("edit"))
    		{
    			
		        gif=um.getGoodsInfoByGoodsId(gif.getGoodsId());
    			actionContext.getRequest().setAttribute("gif",gif);
		        return actionContext.getMapping().findForward("GoodsManageEdit");
    		}
    		else
    		{
    			if(param.equals("save"))
    			{
	    			if(gif.getGoodsId().length()==0)
			        	throw new Exception("产品编号不能为空！");
	    			if(gif.getGoodsType().length()==0)
			        	throw new Exception("产品类别不能为空！");
	    			if(gif.getGuige().length()==0)
			        	throw new Exception("产品规格不能为空！");
	    			if(gif.getCaizhi().length()==0)
			        	throw new Exception("产品材质不能为空！");
	    			if(gif.getPurchaseUnitPrice()<0)
			        	throw new Exception("产品价格必须大于等于0！");
	    		
	    			if(gif.getXishu()<0)
			        	throw new Exception("产品理算系数不能小于0！");
	    			if(gif.getJianxishu()<0)
			        	throw new Exception("件重量不能小于0！");
	    			String filter=oConvert.CheckFileName(gif.getGoodsId());
	    			if(filter.length()>0)
			        	throw new Exception("产品编号不能包含字符 "+filter);
	    			
	    			if(gif.getOldGoodsId().length()>0)
	    				um.UpdateGoodsInfo(gif);
	    			else
	    				um.insertGoodsInfo(gif);
	    			gif=new GoodsInfoForm();
	    			ErrorMsgFB ef=new ErrorMsgFB();
	    			ef.setSource("保存成功！");
	    			actionContext.getRequest().setAttribute(Globals.REQUEST_ERRORS, ef);
	     	        return actionContext.getMapping().findForward("Infors");
    			}
    			else if(param.equals("del"))
        		{	
        			
        			um.DeleteGoodsInfo(gif.getGoodsId());
        			gif.setGoodsId("");
        		}
    			else if(param.equals("updateprice"))
        		{
        			
        			um.UpdateGoodsPrice(gif);
        			gif.setGoodsId("");
        		}
    			String btn=(String)actionContext.getParameter("searchbutton");
    			String orderby = oConvert.getString(actionContext.getParameter("orderstr"),"a.goodsid");
    			if(btn==null)
    			{
    				gif=(GoodsInfoForm)actionContext.getSession().getAttribute("goodsManage_gif");
    				if(gif==null)
    					gif= new GoodsInfoForm();
    				curpage=oConvert.getInt((String)actionContext.getSession().getAttribute("goodsManage_curpage"),1);
    				orderby=oConvert.getString((String)actionContext.getSession().getAttribute("goodsManage_orderby"),"a.goodsid");
    			}
    			else
    			{
    				actionContext.getSession().setAttribute("goodsManage_gif",gif);
    				actionContext.getSession().setAttribute("goodsManage_curpage",String.valueOf(curpage));
    				actionContext.getSession().setAttribute("goodsManage_orderby",orderby);
    			}
    			ResultsetList gsri = um.getGoodsListByRule(gif,curpage,Globals.REC_NUM_OF_PAGE,orderby);
		        actionContext.getRequest().setAttribute("result",gsri);
		        actionContext.getRequest().setAttribute("gif",gif);
		        actionContext.getRequest().setAttribute("orderby",orderby);
		        return actionContext.getMapping().findForward("GoodsManage");
    		
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
}
