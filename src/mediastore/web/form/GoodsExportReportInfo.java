

package mediastore.web.form;



public class GoodsExportReportInfo
{

    
    private int totalRecNum;//�ܼ�¼��
    private int SalesPersonsQkNum;
	private int SalesPersonsFhNum;
	private int SalesPersonsHKNum;//������
    private float SalesPersonsYs;//����Ӧ�տ�
    private float SalesPersonsQl;//����ȥ���
    private float SalesPersonsSs;//����ʵ�տ�
    private float SalesPersonsQk;//����Ƿ�տ�
    private float SalesPersonsFh;//���˷�����
    private float SalesPersonsHK;//������
    private String SalesPersonsNa;//�ۻ�������
    private String MemberNa;//��Ա����
    private String ExportTime;//�۳�ʱ��
    private float SalesPersonsQkSs;//����Ƿ��ʵ�տ�
    private float SalesPersonsFhSs;//���˷���ʵ�տ�
    private String huoyunbu;//���˲�
    private String goodname;
    private int sellnum;
    private int backnum;
    private float sellprice;
    private float backprice;
    private float totalprice;
    private float daijin;
    public GoodsExportReportInfo()
    {
    }
    
    public void setgoodname(String i)
    {
    	goodname = i;
    }
    public void setsellnum(int i)
    {
    	sellnum = i;
    }
    public void setbacknum(int i)
    {
    	backnum = i;
    }
    public void setsellprice(float i)
    {
    	sellprice = i;
    }
    public void setbackprice(float i)
    {
    	backprice = i;
    }
    public void settotalprice(float i)
    {
    	totalprice = i;
    }
    public String getgoodname()
    {
        return goodname;
    }
    public int getsellnum()
    {
        return sellnum;
    }
    public int getbacknum()
    {
        return backnum;
    }
    public float getsellprice()
    {
        return sellprice;
    }
    public float getbackprice()
    {
        return backprice;
    }
    public float gettotalprice()
    {
        return totalprice;
    }
    public String getHuoyunbu()
    {
        return huoyunbu;
    }
    
    public void setTotalRecNum(int i)
    {
    	totalRecNum = i;
    }
    public void setSalesPersonsQkNum(int i)
    {
    	SalesPersonsQkNum = i;
    }
    public void setSalesPersonsFhNum(int i)
    {
    	SalesPersonsFhNum = i;
    }
    public void setSalesPersonsHKNum(int i)
    {
    	SalesPersonsHKNum = i;
    }
    public void setSalesPersonsYs(float i)
    {
    	SalesPersonsYs = i;
    }
    public void setSalesPersonsQl(float i)
    {
    	SalesPersonsQl = i;
    }
    public void setSalesPersonsSs(float i)
    {
    	SalesPersonsSs = i;
    }
    public void setSalesPersonsQk(float i)
    {
    	SalesPersonsQk = i;
    }
    public void setSalesPersonsFh(float i)
    {
    	SalesPersonsFh = i;
    }
    public void setSalesPersonsHK(float i)
    {
    	SalesPersonsHK = i;
    }
    public void setSalesPersonsQkSs(float i)
    {
    	SalesPersonsQkSs = i;
    }
    public void setSalesPersonsFhSs(float i)
    {
    	SalesPersonsFhSs = i;
    }
    public void setSalesPersonsNa(String s)
    {
    	SalesPersonsNa = s;
    }
    public void setMemberNa(String s)
    {
    	MemberNa = s;
    }
    public void setExportTime(String s)
    {
    	ExportTime = s;
    }
    public void setHuoyunbu(String s)
    {
    	huoyunbu = s;
    }
    public void setDaijin(float s)
    {
    	daijin = s;
    }
    public int getTotalRecNum()
    {
        return totalRecNum;
    }
    public int getSalesPersonsQkNum()
    {
        return SalesPersonsQkNum;
    }
    public int getSalesPersonsFhNum()
    {
        return SalesPersonsFhNum;
    }
    public int getSalesPersonsHKNum()
    {
        return SalesPersonsHKNum;
    }
    public float getSalesPersonsYs()
    {
    	return SalesPersonsYs;
    }
    public float getSalesPersonsQl()
    {
    	return SalesPersonsQl;
    }
    public float getSalesPersonsSs()
    {
    	return SalesPersonsSs;
    }
    public float getSalesPersonsQk()
    {
    	return SalesPersonsQk;
    }
    public float getSalesPersonsFh()
    {
    	return SalesPersonsFh;
    }
    public float getSalesPersonsHK()
    {
    	return SalesPersonsHK;
    }
    public String getSalesPersonsNa()
    {
    	return SalesPersonsNa;
    }
    public String getMemberNa()
    {
    	return MemberNa;
    }
    public String getExportTime()
    {
    	return ExportTime;
    }
    public float getSalesPersonsQkSs()
    {
    	return SalesPersonsQkSs;
    }
    public float getSalesPersonsFhSs()
    {
    	return SalesPersonsFhSs;
    }
    public float getDaijin()
    {
    	return daijin;
    }
}
