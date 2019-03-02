package Pakiet;

public class Para 
{
	public final String klucz;
	private double wartosc;
	public Para(String k, double w)
	{
		klucz = k;
		wartosc  = w;
	}
	public double getWartosc() 
	{
		return wartosc;
	}
	public void setWartosc(double w) 
	{
		wartosc = w;
	}
	@Override
	public boolean equals(Object obj) 
	{
		if(obj instanceof Para)
		{
			if(((Para) obj).klucz.equals(this.klucz)) return true;
		}
		return false;
	}
	@Override
	public String toString() 
	{
		return "Para [klucz=" + klucz + ", wartosc=" + wartosc + "]";
	}
	
}
