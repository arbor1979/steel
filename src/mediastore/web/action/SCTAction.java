package mediastore.web.action;

import java.util.List;

import mediastore.dao.SCT;
import mediastore.util.oConvert;
import mediastore.web.struts.AbstractAction;
import mediastore.web.struts.ActionContext;

import org.apache.struts.action.ActionForward;

public class SCTAction extends AbstractAction{

	public SCTAction	()  {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ActionForward execute(ActionContext actionContext) 
	{
		String shengid =actionContext.getParameter("shengid");
		String cityid =actionContext.getParameter("cityid");
		String townid =actionContext.getParameter("townid");
		SCT sct=new SCT();
		if(oConvert.getInt(townid, 0)!=0)
		{
			cityid=sct.GetCityidByTown(townid);
		}
		if(oConvert.getInt(cityid, 0)!=0)
		{
			shengid=sct.GetShengidByCity(cityid);
		}	
		List sheng=sct.GetSheng();
		List city=sct.GetCity(shengid);
		List town=sct.GetTown(cityid);
		
		actionContext.getRequest().setAttribute("sheng",sheng);
		actionContext.getRequest().setAttribute("city",city);
		actionContext.getRequest().setAttribute("town",town);
		actionContext.getRequest().setAttribute("shengid",shengid);
		actionContext.getRequest().setAttribute("cityid",cityid);
		actionContext.getRequest().setAttribute("townid",townid);
		return actionContext.getMapping().findForward("SCT");
	}	
	

}
