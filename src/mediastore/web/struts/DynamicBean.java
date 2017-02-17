// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   DynamicBean.java

package mediastore.web.struts;

import java.io.Serializable;
import java.text.Format;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DynamicBean
    implements Map, Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map map;
    private Map formatters;
    public DynamicBean()
    {
        map = null;
        formatters = null;
        map = new HashMap();
    }

    public DynamicBean(Map map)
    {
        this.map = null;
        formatters = null;
        this.map = map;
    }

    
	@SuppressWarnings("unchecked")
	public void setFormat(String key, Format format)
    {
        if(formatters == null)
            formatters = new HashMap();
        formatters.put(key, format);
    }

    public Format getFormat(String key)
    {
        return (Format)formatters.get(key);
    }

    public void removeFormat(String key)
    {
        formatters.remove(key);
    }

    public void clear()
    {
        map.clear();
    }

    public boolean containsKey(Object key)
    {
        return map.containsKey(key);
    }

    public boolean containsValue(Object value)
    {
        return map.containsValue(value);
    }

    public Set entrySet()
    {
        return map.entrySet();
    }

    public Object get(Object key)
    {
        return map.get(key);
    }

    public boolean isEmpty()
    {
        return map.isEmpty();
    }

    public Set keySet()
    {
        return map.keySet();
    }

   
	@SuppressWarnings("unchecked")
	public Object put(Object key, Object value)
    {
        Format format = null;
        if(formatters != null)
            format = (Format)formatters.get(key);
        if(format != null)
            try
            {
                return map.put(key, format.parseObject((String)value));
            }
            catch(ClassCastException e)
            {
                throw new ClassCastException("Can't put a non-String-value into a DynamicMap for which a formatter has been set. key=" + key + ", value=" + value + ", DynamicMap=" + this + " because property " + key + " has an associated formatter " + format + " : " + e.getMessage());
            }
            catch(ParseException e)
            {
                return null;
            }
        else
            return map.put(key, value);
    }

    
	@SuppressWarnings("unchecked")
	public void putAll(Map t)
    {
        map.putAll(t);
    }

    public Object remove(Object key)
    {
        return map.remove(key);
    }

    public int size()
    {
        return map.size();
    }

    public Collection values()
    {
        return map.values();
    }

    public String toString()
    {
        return "DynamicMap" + map.toString();
    }

    public int hashCode()
    {
        return map.hashCode();
    }

    public boolean equals(Object object)
    {
        return map.equals(object);
    }
}
