package Pakiet;

public class Dziel extends Operator2 {

	public Dziel(Wyrazenie p, Wyrazenie q)
	{
		super(p,q);
	}
	/** metoda oblicza z dzielenia dwoch wyrazen */
	public double oblicz() 
	{
		return w1.oblicz()/w2.oblicz();
	}
	public String toString()
	{
		return w1.toString() + " / " + w2.toString();
	}
}
