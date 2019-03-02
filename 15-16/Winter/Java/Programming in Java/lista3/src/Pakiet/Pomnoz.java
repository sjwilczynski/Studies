package Pakiet;

public class Pomnoz extends Operator2 {

	public Pomnoz(Wyrazenie p, Wyrazenie q)
	{
		super(p,q);
	}
	public double oblicz() 
	{
		return w1.oblicz()*w2.oblicz();
	}
	public String toString()
	{
		return w1.toString() + " * " + w2.toString();
	}
}
