package Pakiet;

abstract public class Operator2 extends Operator1 
{
    Wyrazenie w2;
	public Operator2(Wyrazenie p, Wyrazenie q) 
	{
		super(p);
		w2 = q;
	}
	public abstract double oblicz();

}
