package Pakiet;
import java.lang.Math;

public class Sinus extends Operator1 {

	public Sinus(Wyrazenie w)
	{
		super(w);
	}
	public double oblicz() 
	{
		return Math.sin(w1.oblicz());
	}
	@Override
	public String toString() {
		return "sin(" + w1.toString()+ ")";
	}

}
