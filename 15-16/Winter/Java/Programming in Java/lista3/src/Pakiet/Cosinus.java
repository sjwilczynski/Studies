package Pakiet;

public class Cosinus extends Operator1 {

	public Cosinus(Wyrazenie w)
	{
		super(w);
	}
	/** metoda oblicza cos z wyrazenia */
	public double oblicz() 
	{
		return Math.cos(w1.oblicz());
	}
	@Override
	public String toString() {
		return "cos(" + w1.toString()+ ")";
	}

}
