package Pakiet;

public class Odwrotnosc extends Operator1 
{
	public Odwrotnosc(Wyrazenie w)
	{
		super(w);
	}
	public double oblicz() 
	{
		return 1/ w1.oblicz();
	}
	@Override
	public String toString() 
	{
		return "1/"+"("+w1.toString()+")";
	}
}
