// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   StrUtility.java

package mediastore.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import mediastore.web.global.ApplicationContext;


public class StrUtility
{
	
    public StrUtility()
    {
    	
    }

    public static String replaceString(String strSource, String strMatch, String strReplace)
    {
        String strRet = "";
        int nLen = strSource.length();
        int nHead = 0;
        int nTail = nLen - 1;
        int nMatLen = strMatch.length();
        for(; nHead < nLen; nHead = nTail + nMatLen)
        {
            nTail = strSource.indexOf(strMatch, nHead);
            if(nTail == -1)
            {
                strRet = strRet + strSource.substring(nHead, nLen);
                break;
            }
            strRet = strRet + strSource.substring(nHead, nTail);
            strRet = strRet + strReplace;
        }

        return strRet;
    }
    public static String getMacAddress() throws IOException 
	{
	   String address = "";
	   String os = System.getProperty("os.name");
	   if (os != null && os.startsWith("Windows")) 
	   {
	      try 
		  {
		     String command = "cmd.exe /c ipconfig /all";
		     Process p = Runtime.getRuntime().exec(command);
		     BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		     String line;
		     while ((line = br.readLine()) != null) 
			 {
			      if (line.indexOf("Physical Address") > 0 || line.indexOf("ÎïÀíµØÖ·") > 0) 
				  {
				       int index = line.indexOf(":");
				       index += 2;
				       address = line.substring(index).replaceAll("-", "");
				       break;
			      }
		     }
		     br.close();
		     address=address.trim();
	      } 
		  catch (IOException e) 
		  {
			  throw e;
	      }
	   }
	   return address;
	}
	public static String hexString2binaryString(String hexString) 
	{
	    if (hexString == null || hexString.length() % 2 != 0)
	      return null;
	    String bString = "", tmp;
	    for (int i = 0; i < hexString.length(); i++) {
	      tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
	      bString += tmp.substring(tmp.length() - 4);
	    }
	    return bString;
	}
	
	public static String binaryString2hexString(String bString) 
	{
		    if (bString == null || bString.equals("") || bString.length() % 8 != 0)
		      return null;
		    StringBuffer tmp=new StringBuffer();
		    int iTmp = 0;
		    for (int i = 0; i < bString.length(); i += 4) {
		      iTmp = 0;
		      for (int j = 0; j < 4; j++) {
		        iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
		      }
		      tmp.append(Integer.toHexString(iTmp));
		    }
		    return tmp.toString();
	}
	public static String qufan(String s)
	{
		
		 s=hexString2binaryString(s.trim());
	     String tmp="";
	     for(int i=0;i<s.length();i++)
	     {
	    	 tmp=tmp+(s.substring(i,i+1).equals("0")?"1":"0");
	     }
	     s=binaryString2hexString(tmp).toUpperCase();
	     return s;
	}
	public static int CheckRegcode(String mac,int hz,String regcode) throws Exception
	{
		DesEncrypt de=new DesEncrypt();
		de.getKey("qiao1234");
		regcode=de.getDesString(regcode);
	  	if(regcode.length()<26)
		{
	  		return -1;
    	}
		String mac1="";
		int ver=0;
		String sdt="";
		int shz=0;
		for(int i=0;i<12;i++)
		{
			mac1=mac1+regcode.substring(2*i,2*i+1);
			sdt=sdt+regcode.substring(2*i+1,2*i+2);
		}
		sdt=StrUtility.qufan(sdt);
		ver=oConvert.getInt(regcode.substring(24,25),0);
		shz=Integer.parseInt(regcode.substring(25,regcode.length()),16);
		
		sdt=sdt.substring(0,4)+"-"+sdt.substring(4,6)+"-"+sdt.substring(6,8)+" "+sdt.substring(8,10)+":"+sdt.substring(10,12)+":00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		java.util.Date dt=sdf.parse(sdt); 
		java.util.Date dt1=new java.util.Date();
		if(!mac.substring(0,12).equals(String.valueOf(mac1)) || hz!=shz || (ver!=1 && ver!=2 && ver!=3))
		{
    		return -2;
    	}
		else if(dt==null || dt1.getTime()-dt.getTime()>0)
		{
			return -3;
		}
		int i=oConvert.DateDivDate(dt, dt1)+1;
		ApplicationContext.setVer(ver);
		return i;
	}
	public static void CopyFile(File in, File out) throws Exception 
	{
	     FileInputStream fis  = new FileInputStream(in);
	     FileOutputStream fos = new FileOutputStream(out);
	     byte[] buf = new byte[1024];
	     int i = 0;
	     while((i=fis.read(buf))!=-1) 
	     {
	       fos.write(buf, 0, i);
	     }
	     fis.close();
	     fos.close();
	} 

}
