
public class Main {

	public static void main(String[] args) 
	{
		Wyrazenie x;
		x = new Wezel(new Lisc(4),new Lisc(5),'+');
		Wyrazenie y = new Wezel(new Lisc(2),new Lisc(3),'*');
		Wyrazenie z = new Wezel(x,y,'/');
		System.out.println(z.getClass().getName());
		System.out.print(z.toString());
		//System.out.print(y.oblicz());
		System.out.printf(" = %d",z.oblicz());
	}
}
