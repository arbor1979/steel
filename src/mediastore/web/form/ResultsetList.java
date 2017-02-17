package mediastore.web.form;

import java.util.ArrayList;
import java.util.List;

public class ResultsetList {
	
	public List rslist;
	public int allnum;
	public double allsumnum;
	public double allmoney;
	public double realmoney;
	public double oddmoney;
	public int pagesize;
	public int curpage;
	public int pagecount;
	public ResultsetList() 
	{
		this.rslist=new ArrayList();
		this.allnum=0;
		this.allsumnum=0.0F;
		this.allmoney=0;
		this.realmoney=0;
		this.oddmoney=0;
		this.pagesize=20;
		this.curpage=1;
		this.pagecount=1;
	}
	
}
