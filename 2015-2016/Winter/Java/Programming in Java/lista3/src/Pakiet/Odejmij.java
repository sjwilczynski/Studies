package Pakiet;

public class Odejmij extends Operator2 {

	public Odejmij(Wyrazenie p, Wyrazenie q)
	{
		super(p,q);
	}
	/** metoda oblicza roznice wyrazen */
	public double oblicz() 
	{
		return w1.oblicz()-w2.oblicz();
	}
	public String toString()
	{
		return "("+w1.toString() + " - " + w2.toString()+")";
	}
}