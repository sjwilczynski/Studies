import java.util.Arrays;


public class Main 
{
	public static void main(String[] args)
	{
		rosnaca R = new rosnaca();
		for(int i = 0;i <= 10;i++)
		{
			R.push(new Elem(i));
		}
		R.push(new Elem(10,"ala"));
		R.wypisz();
		R.pop();
		R.pop();
		R.wypisz();
		Figura[] F = new Figura[3]; 
		Trojkat T = new Trojkat(10,8,45);
		Okrag O = new Okrag(5);
		Prostokat P = new Prostokat(8,5);
		F[0] = T; F[1] = O; F[2] = P;
		for(int i=0;i<=2;i++)F[i].wypisz();
		Arrays.sort(F);
		for(int i=0;i<=2;i++)F[i].wypisz();
		
		
		
	}

}
