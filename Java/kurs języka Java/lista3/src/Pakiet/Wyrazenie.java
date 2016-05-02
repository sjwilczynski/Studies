package Pakiet;
abstract class Wyrazenie 
{
	// ...
	/** metoda sumująca wyrażenia */
	public abstract double oblicz();
	
	public static double sumuj (Wyrazenie... wyr)
	{
		double res = 0;
		for(Wyrazenie w: wyr) res += w.oblicz();
		return res;
	}
	/** metoda mnożąca wyrażenia */
	public static double pomnoz (Wyrazenie... wyr) 
	{
		double res = 0;
		for(Wyrazenie w: wyr) res *= w.oblicz();
		return res;
	}
}