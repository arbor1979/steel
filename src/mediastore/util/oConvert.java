 /*
 *说明：主要实现字符串处理功能
 *作者：道讯公司Guo
 */
package mediastore.util;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class oConvert {

    
    /**
     *   判断字符串是否为空
     */
    public static boolean isEmpty(String s) {
        if (s == null) {
            return (true);
        }
        if (s.equals("")) {
            return (true);
        }
        return (false);
    }

    /**
     *  字符串转化各种数据类型
     */
    public static short getShort(String strN, short defVal) {
        if (strN == null) {
            return (defVal);
        }
        try {
            return (Short.parseShort(strN));
        } catch (NumberFormatException e) {
            return (defVal);
        }
    }

    public static int getInt(String s, int defval) {
        if (s == null) {
            return (defval);
        }
        try {
            return (Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return (defval);
        }
    }

    public static float getFloat(String strN, float defVal) {
        if (strN == null) {
            return (defVal);
        }
        float retval;
        try {
            retval = Float.parseFloat(strN);
        } catch (NumberFormatException e) {
            retval = defVal;
        }
        return (retval);
    }
    public static double getDouble(String strN, double defVal) {
        if (strN == null) {
            return (defVal);
        }
        double retval;
        try {
            retval = Double.parseDouble(strN);
        } catch (NumberFormatException e) {
            retval = defVal;
        }
        return (retval);
    }
    public static long getLong(String strN, long defVal) {
        if (strN == null) {
            return (defVal);
        }
        long retval;
        try {
            retval = Long.parseLong(strN);
        } catch (NumberFormatException e) {
            retval = defVal;
        }
        return (retval);
    }

    public static String getString(String s) {
        return (getString(s, ""));
    }

    public static String getString(int i) {
        return (String.valueOf(i));
    }

    public static String getString(float i){
        return (String.valueOf(i));
    }

    public static String getString(String s, String defval) {
        if (isEmpty(s)) {
            return (defval);
        }
        return (s.trim());
    }

    public static boolean getBoolean(String b, boolean defval) {
        if (b == null) {
            return (defval);
        }
        String upperB = b.toUpperCase();
        if ("1".equals(b)) {
            return (true);
        }
        if ("0".equals(b)) {
            return (false);
        }
        if ("TRUE".equals(upperB)) {
            return (true);
        }
        if ("FALSE".equals(upperB)) {
            return (false);
        }
        return (defval);
    }

    public static boolean getBoolean(Boolean b) {
        if (b == null) {
            return (false);
        } else {
            return (b.booleanValue());
        }
    }

    /**
     *  将字符串加引号后返回，用于数据库操作,将字符串中的'转化成"
     */
    public static String dbString(String s) {
        if (s == null) {
            return ("NULL");
        } else {
            s = s.trim();
        }
        if ("".equals(s)) {
            return ("NULL");
        }
        return ("'" + s.replaceAll("'", "''") + "'");
    }

    /**
     *将iso的编码转换成GB2312编码
     */
    public static String iso2gb(String strIn) {
        if (strIn == null) {
            return ("");
        }
        try {
            return (new String(strIn.getBytes("ISO-8859-1"), "gb2312"));
        } catch (UnsupportedEncodingException e) {
            return ("");
        }
    }

    /**
     *将GB2312的编码转换成utf-8编码
     */
    public static String gb2iso(String strIn) {
        if (strIn == null) {
            return "";
        }
        try {
            return (new String(strIn.getBytes("gb2312"), "ISO-8859-1"));
        } catch (UnsupportedEncodingException e) {
            return ("");
        }
    }

    /**
     * 取得随机数
     */
    public static Random rand = new Random(System.currentTimeMillis());
    public static Random getRandom() {
        return (rand);
    }

    /**
     *共用一份随机数
     */
    public synchronized static int nextRandInt() {
        return rand.nextInt();
    }

    /**
     *返回一个指定范围的随机数
     */
    public synchronized static int nextRandInt(int n) {
        return rand.nextInt(n);
    }

    /**
     *  n 为一个整数， 返回的为 根据随数机判断  相当于几率为 n%
     */
    public static boolean isHit(int n) {
        if (n <= 0) {
            return false;
        } else {
            int s = nextRandInt(100);
            if (s > n) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     *   jsp线程延时
     */
    public static void delay(long n) {
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
        }
    }

    /**
     *   jsp线程延时100毫秒
     */

    public static void delay() {
        delay(3000);
    }
    /*
     *  获取时间类型为:yyyy:mm:dd
     */
     public static String FormDate(Date DateTime){
     	 
     	 SimpleDateFormat sf =new SimpleDateFormat("yyyy年MM月dd日");
     	 return sf.format(DateTime);
     }
	 public static String FormDateShort(Date DateTime){
     	 
     	 SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd");
     	 return sf.format(DateTime);
     }
	 public static String FormDate(Date DateTime,String format)
	 {
		try
		{
     	   	 SimpleDateFormat sf =new SimpleDateFormat(format);
			 return sf.format(DateTime);
		}
		catch(Exception E)
		{
			System.out.print(E.getMessage());
			return ("");
		}
     }
         /*
     *  获取时间类型为:yyyy:mm:dd:
     */
     public static String FormDateTime(Date DateTime){
     	 
     	 SimpleDateFormat sf =new SimpleDateFormat("yyyy年MM月dd日HH点mm分");
     	 return sf.format(DateTime);
     }
	 public static String FormDateTimeShort(Date DateTime){
     	 
     	 SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     	 return sf.format(DateTime);
     }
         /**
     *   判断字符于另外一个字符是否相等
     */
    public static boolean isEmptys(String x,String y) {
        if (x == y) {
            return (true);
        }
        if (x.equals(y)) {
            return (true);
        }
        return (false);
    }
	/**
     *   两个日期相差几天
     */
	 public static int DateDivDate(Date Now,Date Last)
	 {
		Long l;
		l=new Long((Now.getTime() - Last.getTime())/(1000*3600*24));
		return l.intValue();
	 }
	  /**
     *   两个日期相差几小时
     */
	 public static int DateDivHour(Date Now,Date Last)
	 {
		Long l;
		l=new Long((Now.getTime() - Last.getTime())/(1000*3600));
		return l.intValue();
	 }
	  /**
     *   两个日期相差几分钟
     */
	 public static int DateDivMinute(Date Now,Date Last)
	 {
		Long l;
		l=new Long((Now.getTime() - Last.getTime())/(1000*60));
		return l.intValue();
	 }
	 //格式化浮点型的小数位
	 public static String FormatDigit(double num,String digit)
	 {
		DecimalFormat nf = new DecimalFormat("0.00");
		return nf.format(num);
	 }
	 //	自动回行，将\r\n转化为<br>
	 public static String filterLine(String m_content) 
	 {
        String result="";
		int i=0;
		while(m_content.indexOf("\r\n")>=0)
		{
			
			i=m_content.indexOf("\r\n");
			result=result+m_content.substring(0,i)+"<br>";
			m_content=m_content.substring(i+2);
		}
		result=result+m_content;
		return(result);
     }
	 //将字符型转换为日期型
	 public  static  java.util.Date parseDate(String  dateStr)  
	 {  
		 SimpleDateFormat sdf  =   new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		 Date date=null;
         try
         {  
        	 date = sdf.parse(dateStr); 
         }
         catch  (Exception  e)  
         {
        	 System.out.println(e.getMessage());
         }  
         return  date;  
	 } 
	 
	 //将字符型转换为日期型
	 public  static  java.util.Date parseDateShort(String  dateStr)  
	 {  
		 SimpleDateFormat sdf  =   new  SimpleDateFormat("yyyy-MM-dd");  
		 Date date=null;
         try
         {  
        	 date = sdf.parse(dateStr); 
         }
         catch  (Exception  e)  
         {
        	 System.out.println(e.getMessage());
         }  
         return  date;  
	 }
	 //	过滤转换SQL中的非法字符(')
	public static String filterSQL(String m_content)
	{
		String result="";
		if(m_content==null)
			m_content="";
		int i=0;
		while(m_content.indexOf("'")>=0)
		{
			
			i=m_content.indexOf("'");
			result=result+m_content.substring(0,i)+"\"";
			m_content=m_content.substring(i+1);
		}
		result=result+m_content;
		return(result);
	}
	//	过滤转换文件名中的非法字符(')
	public static String CheckFileName(String m_content)
	{
		String filter[]={"*","?",":","|","<",">","/","\\"};
		String result="";
		int i=0;
		for(i=0;i<filter.length;i++)
		{
			if(m_content.indexOf(filter[i])>=0)
				return filter[i];
		}
		return(result);
	}
	//过滤转换查询关键字中的非法字符(",<,>,'')
	public static String filterKey(String m_content)
	{
		String result="";
		int i=0;
		while(m_content.indexOf("\"")>=0)
		{
			
			i=m_content.indexOf("\"");
			result=result+m_content.substring(0,i)+"&quot;";
			m_content=m_content.substring(i+1);
		}
		result=result+m_content;
		m_content=result;
		result="";
		while(m_content.indexOf("<")>=0)
		{
			
			i=m_content.indexOf("<");
			result=result+m_content.substring(0,i)+"&lt;";
			m_content=m_content.substring(i+1);
		}
		result=result+m_content;
		m_content=result;
		result="";
		while(m_content.indexOf(">")>=0)
		{
			
			i=m_content.indexOf(">");
			result=result+m_content.substring(0,i)+"&gt;";
			m_content=m_content.substring(i+1);
		}
		result=result+m_content;
		m_content=result;
		result="";
		while(m_content.indexOf("''")>=0)
		{
			
			i=m_content.indexOf("''");
			result=result+m_content.substring(0,i)+"'";
			m_content=m_content.substring(i+2);
		}
		result=result+m_content;
		m_content=result;
		result="";
		return(m_content);
	}
	//约束字符串长度
	public static String limitStr(String str ,int n)
	{
		while(str.getBytes().length>n)
			str=str.substring(0,str.length()-1);
		while(str.getBytes().length<n)
			str=str+" ";
		return str;
	}
	public static String HtmlSpace(String str)
	{
		String str1="";
		for(int j=0;j<str.length();j++)
		{
			if(str.substring(j,j+1).equals(" "))
				str1=str1+"&nbsp;";
			else
				str1=str1+str.substring(j,j+1);		
		}
		return str1;
	}
	/*日期加减
	  gc.add(1,-1)表示年份减一.
	  gc.add(2,-1)表示月份减一.
	  gc.add(3.-1)表示周减一.
	  gc.add(5,-1)表示天减一.
	*/
	public static Date DateAdd(Date dt,int m,int n)
	{
		GregorianCalendar gc=new GregorianCalendar();
		gc.setTime(dt);
		gc.add(m, n);
		return  gc.getTime();
	}
	public static float getRound(float f,int d)
	{
		BigDecimal bd=new BigDecimal(getString(f)).setScale(d, BigDecimal.ROUND_HALF_UP); 
		return bd.floatValue();
	}
	public static double getRound(double f,int d)
	{
		BigDecimal bd=new BigDecimal(String.valueOf(f)).setScale(d, BigDecimal.ROUND_HALF_UP); 
		return bd.doubleValue();
	}
	public static double getRound(BigDecimal f,int d)
	{
		BigDecimal bd=new BigDecimal(String.valueOf(f)).setScale(d, BigDecimal.ROUND_HALF_UP); 
		return bd.doubleValue();
	}
	public static double mul(double v1,double v2)
	{
	    BigDecimal b1 = new BigDecimal(Double.toString(v1));
	    BigDecimal b2 = new BigDecimal(Double.toString(v2));
	    return b1.multiply(b2).doubleValue();
	}
	
	public static double div(double v1,double v2,int scale){
	    if(scale<0){
	      throw new IllegalArgumentException(
	      "The scale must be a positive integer or zero");
	    }
	    BigDecimal b1 = new BigDecimal(Double.toString(v1));
	    BigDecimal b2 = new BigDecimal(Double.toString(v2));
	    return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	  }
	public static double add(double v1,double v2){
	    BigDecimal b1 = new BigDecimal(Double.toString(v1));
	    BigDecimal b2 = new BigDecimal(Double.toString(v2));
	    return b1.add(b2).doubleValue();
	  } 
	public static double sub(double v1,double v2){
	    BigDecimal b1 = new BigDecimal(Double.toString(v1));
	    BigDecimal b2 = new BigDecimal(Double.toString(v2));
	    return b1.subtract(b2).doubleValue();
	  }
	//当月第一天
	public static String getFirstDay()
	{
		String str=FormDate(new Date(),"yyyy-MM-1 0:00:00");
		return str;
	}
	//当月最后一天
	public static String getLastDay()
	{
		Calendar cal = Calendar.getInstance(); 
		int maxDay=cal.getActualMaximum(Calendar.DAY_OF_MONTH); 
		String str=FormDate(new Date(),"yyyy-MM-"+maxDay+" 23:59:59");
		return str;
	}
	public static int getYear(Date dt)
	{
		Calendar   cal   =   Calendar.getInstance();   
		cal.setTime(dt);   
		return cal.get(Calendar.YEAR);   
	}
	public static int getMonth(Date dt)
	{
		Calendar   cal   =   Calendar.getInstance();   
		cal.setTime(dt);   
		return cal.get(Calendar.MONTH);   
	}
 }

