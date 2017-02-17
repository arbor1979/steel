// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ErrorMsgFB.java

package mediastore.web.form;

import java.util.ArrayList;
import java.util.List;

import mediastore.util.oConvert;

public class ErrorMsgFB
{

    private String source;
    private String url;
    private List messages;

    public ErrorMsgFB()
    {
        messages = new ArrayList();
    }

     
	@SuppressWarnings("unchecked")
	public void addMessage(String msg)
    {
        messages.add(msg);
    }

    public void setSource(String source)
    {
        this.source = oConvert.filterSQL(source);
    }
    public String getSource()
    {
        return source;
    }

    public List getMessages()
    {
        return messages;
    }

    
	@SuppressWarnings("unchecked")
	public void setMessages(List messages)
    {
        this.messages.addAll(messages);
    }

   
	@SuppressWarnings("unchecked")
	public void addMessages(String message)
    {
        messages.add(message);
    }
	public void seturl(String source)
    {
        this.url = source;
    }
    public String geturl()
    {
        return url;
    }
}
