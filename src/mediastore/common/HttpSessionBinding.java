package mediastore.common;

import java.util.Vector;

import javax.servlet.http.HttpSessionBindingListener;

import mediastore.user.UserManager;
import mediastore.web.form.UserInfoForm;
import mediastore.web.global.ApplicationContext;
import mediastore.web.global.Globals;



public class HttpSessionBinding implements HttpSessionBindingListener 
{
	public HttpSessionBinding()
	{
		super();
	}

	@SuppressWarnings("unchecked")
	public void valueBound(javax.servlet.http.HttpSessionBindingEvent e) 
	{ 
		Vector activeSessions = (Vector) ApplicationContext.getContext();
		if (activeSessions == null)
		{
			activeSessions = new Vector();
		}

		UserInfoForm sessionUser = (UserInfoForm)e.getSession().getAttribute(Globals.SESSION_CONTEXT);
		if (sessionUser != null)
		{
			activeSessions.add(e.getSession());
		}
		ApplicationContext.setContext(activeSessions);
	}

	public void valueUnbound(javax.servlet.http.HttpSessionBindingEvent e) 
	{
		try
        {
			Vector activeSessions = (Vector) ApplicationContext.getContext();
			if (activeSessions != null)
			{
				activeSessions.remove(e.getSession());
				ApplicationContext.setContext(activeSessions);
			}
			
	        UserManager um = new UserManager();
        	um.setonline(activeSessions);

        }
		catch(Exception ex)
		{
			System.out.print(ex.getMessage());
		}
	}

}
