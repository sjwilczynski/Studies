
public class Trojkat extends Figura
{
	double a;
	double b;
	double kat;
	Trojkat(double x, double y, double z)
	{
		a = x;
		b = y;
		kat = z;
	}
	public double pole()
	{
		double rad = Math.toRadians(kat);
		return a*b*Math.sin(rad)/2;
	}
}
