// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ApplicationContext.java

package mediastore.web.global;

import java.util.Vector;


public class ApplicationContext
{

    @SuppressWarnings("unused")
	private static Vector context = null;
    private static String MacAddress;
    private static int days;
    private static int ver;
    public static Vector getContext() {
		return context;
	}


	public static void setContext(Vector context) {
		ApplicationContext.context = context;
	}


	public ApplicationContext()
    {
		MacAddress="";
    }


	public static String getMacAddress() {
		return MacAddress;
	}


	public static void setMacAddress(String macAddress) {
		MacAddress = macAddress;
	}


	public static int getDays() {
		return days;
	}


	public static void setDays(int days) {
		ApplicationContext.days = days;
	}


	public static int getVer() {
		return ver;
	}


	public static void setVer(int ver) {
		ApplicationContext.ver = ver;
	}

}
