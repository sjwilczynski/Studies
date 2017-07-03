
public class Figura implements Comparable<Figura>
{
	public double pole()
	{
		return this.pole();
	}
	public int compareTo(Figura x) 
	{
		if(pole()>x.pole())return 1;
		else return -1;
	}
	public void wypisz()
	{
		System.out.printf("%s o polu %f\n",this.getClass().getName(),this.pole());
	}
}
