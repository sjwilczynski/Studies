package Pakiet;
public class Arctan extends Operator1 {

	public Arctan(Wyrazenie w)
	{
		super(w);
	}
	/** metoda oblicza arctg z wyrazenia */
	public double oblicz() 
	{
		return Math.atan(w1.oblicz());
	}
	public String toString() {
		return "arctan(" + w1.toString()+ ")";
	}

}
