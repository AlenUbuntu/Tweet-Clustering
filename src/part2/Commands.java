package part2;

public class Commands {

	private int kNum;
	private String inUrl;
	private String kInUrl;
	private String outUrl;
	
	public Commands(int k, String kIn, String in, String out)
	{
		this.kNum = k;
		this.kInUrl = kIn;
		this.inUrl = in;
		this.outUrl = out;
	}
	
	public int getK(){
		return this.kNum;
	}
	
	public String getKUrl()
	{
		return this.kInUrl;
	}
	
	public String getInUrl()
	{
		return this.inUrl;
	}
	
	public String getOutUrl()
	{
		return this.outUrl;
	}
}
