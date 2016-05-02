package Pakiet;

public class Zmienna extends Wyrazenie 
{
	public static final Zbior Zmienne = new Zbior(30);
	public String nazwa;
	public Zmienna(String s)
	{
		nazwa = s;
	}
	public double oblicz() 
	{
		return Zmienne.czytaj(nazwa);
	}
	
	public boolean equals(Object obj) 
	{
		if(obj instanceof Zmienna)
		{
			if(((Zmienna) obj).nazwa.equals(nazwa)) return true;
		}
		return false;
	}
	public String toString() 
	{
		return nazwa;
	}

}
