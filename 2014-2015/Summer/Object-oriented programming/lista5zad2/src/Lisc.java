
public class Lisc extends Wyrazenie 
{
	Integer val = 1;
	String zmienna = "";
	Lisc(int x)
	{
		val = x;
	}
	Lisc(String c)
	{
		zmienna = c;
	}
	public String toString()
	{
		String res = ""; 
		if(zmienna == "")res += val.toString();
		else res += zmienna;
		return res;
		
	}
	public int oblicz()
	{
		return val;
	}

}
