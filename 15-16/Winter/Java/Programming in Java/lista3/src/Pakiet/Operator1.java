package Pakiet;

abstract public class Operator1 extends Wyrazenie 
{
	Wyrazenie w1;
	public Operator1(Wyrazenie w)
	{
		w1 = w;
	}
	public abstract double oblicz();
}
