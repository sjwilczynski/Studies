package Pakiet;

public class WartBezwzgl extends Operator1 {

	public WartBezwzgl(Wyrazenie w)
	{
		super(w);
	}
	public double oblicz() 
	{
		double a = w1.oblicz();
		if(a < 0)return -a;
		return a;
	}
	@Override
	public String toString() {
		return "|" + w1.toString() + "|";
	}
}
