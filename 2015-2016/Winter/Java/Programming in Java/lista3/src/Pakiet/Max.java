package Pakiet;

public class Max extends Operator2 {

	public Max(Wyrazenie p, Wyrazenie q)
	{
		super(p,q);
	}
	/** metoda oblicza maximum dwoch wyrazen */
	public double oblicz() 
	{
		return Math.max(w1.oblicz(),w2.oblicz());
	}
	public String toString()
	{
		return "max("+w1.toString() + "," + w2.toString()+")";
	}
}