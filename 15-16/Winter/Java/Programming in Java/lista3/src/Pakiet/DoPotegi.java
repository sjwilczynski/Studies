package Pakiet;

public class DoPotegi extends Operator2 {

	public DoPotegi(Wyrazenie p, Wyrazenie q)
	{
		super(p,q);
	}
	/** metoda oblicza  wartosc pierwszego wyrazenia do potegi wartosc drugiego wyrazenia */
	public double oblicz() 
	{
		return Math.pow(w1.oblicz(),w2.oblicz());
	}
	public String toString()
	{
		return w1.toString() + " ^ " + w2.toString();
	}
}
