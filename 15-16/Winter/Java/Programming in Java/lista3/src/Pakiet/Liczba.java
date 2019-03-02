package Pakiet;
/** klasa przechowujaca liczby */
public class Liczba extends Wyrazenie {

	double liczba;
	public Liczba(double x)
	{
		liczba = x;
	}
	public double oblicz() 
	{
		return liczba;
	}
	@Override
	public String toString() 
	{
		return "" + liczba + "";
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Liczba)
		{
			if(((Liczba) obj).liczba == liczba) return true;
		}
		return false;
	}

}
