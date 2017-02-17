// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   DynamicForm.java

package mediastore.web.struts;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.struts.action.ActionForm;

// Referenced classes of package mediastore.web.struts:
//            DynamicBean

public class DynamicForm extends ActionForm
    implements Map
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DynamicBean bean;

    public DynamicForm()
    {
        bean = null;
        bean = new DynamicBean();
    }

    public String toString()
    {
        return bean.toString();
    }

    public void clear()
    {
        bean.clear();
    }

    public boolean containsKey(Object key)
    {
        return bean.containsKey(key);
    }

    public boolean containsValue(Object value)
    {
        return bean.containsValue(value);
    }

    public Set entrySet()
    {
        return bean.entrySet();
    }

    public Object get(Object key)
    {
        return bean.get(key);
    }

    public boolean isEmpty()
    {
        return bean.isEmpty();
    }

    public Set keySet()
    {
        return bean.keySet();
    }

    public Object put(Object key, Object value)
    {
        return bean.put(key, value);
    }

    public void putAll(Map t)
    {
        bean.putAll(t);
    }

    public Object remove(Object key)
    {
        return bean.remove(key);
    }

    public int size()
    {
        return bean.size();
    }

    public Collection values()
    {
        return bean.values();
    }

    public int hashCode()
    {
        return bean.hashCode();
    }

    public boolean equals(Object object)
    {
        return this == object;
    }
}
