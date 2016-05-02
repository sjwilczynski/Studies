package Pakiet;

public class Przeciwienstwo extends Operator1 {

	public Przeciwienstwo(Wyrazenie w)
	{
		super(w);
	}
	public double oblicz() 
	{
		return -1 * w1.oblicz();
	}
	@Override
	public String toString() {
		return "-" + "(" + w1.toString() + ")";
	}
	

}
