// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ActionContext.java

package mediastore.web.struts;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ActionContext
{

    private Action action;
    private ActionMapping mapping;
    private ActionForm form;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private Locale locale;
    public ActionContext(Action action, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, Locale locale)
    {
        this.action = null;
        this.mapping = null;
        this.form = null;
        this.request = null;
        this.response = null;
        session = null;
        this.locale = null;
        this.action = action;
        this.mapping = mapping;
        this.form = form;
        this.request = request;
        this.response = response;
        session = request.getSession();
        this.locale = locale;
    }

    public Action getAction()
    {
        return action;
    }

    public ActionMapping getMapping()
    {
        return mapping;
    }

    public ActionForm getForm()
    {
        return form;
    }

    public HttpServletRequest getRequest()
    {
        return request;
    }

    public HttpServletResponse getResponse()
    {
        return response;
    }

    public Locale getLocale()
    {
        return locale;
    }

    public HttpSession getSession()
    {
        return session;
    }

    public String getParameter(String parameterName)
    {
        String value = request.getParameter(parameterName);
        try
        {
            byte tmp[] = value.getBytes("ISO8859_1");
            value = new String(tmp);
        }
        catch(Exception exception) { }
        if("".equals(value))
            value = null;
        return value;
    }

    public String[] getParameterValues(String parameterName)
    {
        String value[] = request.getParameterValues(parameterName);
        if(value != null)
        {
            for(int i = 0; i < value.length; i++)
            {
                String tmpStr = value[i];
                try
                {
                    byte tmp[] = tmpStr.getBytes("ISO8859_1");
                    tmpStr = new String(tmp);
                }
                catch(Exception exception) { }
                value[i] = tmpStr;
            }

        }
        return value;
    }

    public boolean hasSession()
    {
        return session != null;
    }

    public void invalidateSession()
    {
        if(session != null)
        {
            session.invalidate();
            session = null;
        }
    }
}
