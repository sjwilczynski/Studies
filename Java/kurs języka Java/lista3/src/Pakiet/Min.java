package Pakiet;

public class Min extends Operator2 {

	public Min(Wyrazenie p, Wyrazenie q)
	{
		super(p,q);
	}
	/** metoda oblicza minimum dwoch wyrazen */
	public double oblicz() 
	{
		return Math.min(w1.oblicz(),w2.oblicz());
	}
	public String toString()
	{
		return "min("+w1.toString() + "," + w2.toString()+")";
	}
}
