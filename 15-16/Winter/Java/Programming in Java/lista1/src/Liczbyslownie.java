public class Liczbyslownie
{
	public static void main(String[] args) 
	{ 
		
		for(String s : args)
		{
			int x = new Integer(s);
			System.out.println(naslowo(x));
		}
		
		//System.out.println(naslowo(1100100999));
	}
	static String[] jednosci = {"zero","jeden","dwa","trzy","cztery","pięć","sześć","siedem","osiem","dziewięć"};
	static String[] dziesiec = {"dziesięć","jedynaście","dwanaście","trzynaście","czternaście","piętnaście","szesnaście","siedemnaście","osiemnaście","dziewiętnaście"};
	static String[] dziesiatki = {"zero","dziesięć","dwadzieścia","trzydzieści","czterdzieści","pięćdziesiąt","sześćdziesiąt","siedemdziesiąt","osiemdziesiąt","dziewięćdziesiąt"};
	static String[] setki = {"zero","sto","dwieście","trzysta","czterysta","pięćset","sześćset","siedemset","osiemset","dziewięćset"};
	static String[] potegi = {"zero"," tysiąc"," milion"," miliard"};
	public static String mniejniz1000(int x)
	{
		String s = " ";
		if(x >= 100) s += setki[(x/100)%10] + " ";
		x = x%100;
		if(x < 10 && x!=0) s += jednosci[x%10]+ " ";
		if(x == 10) s+= "dziesięć ";
		if(x > 10 && x < 20) s += dziesiec[x%10]+" ";
		if(x >= 20)
		{
			s += dziesiatki[(x/10)%10]+" ";
			if(x%10 != 0 ) s+=jednosci[x%10];
		}
		return s;
	}
	public static String naslowo(int x) 	
	{
		int res;
		if(x == 0) return "zero";
		String[] napisy =  {"","","",""};
		String s = "";
		String znak = "";
		if(x < 0) 
		{
			znak = "minus";
			x = -x;
		}
		for(int i = 0; i < 4; i++)
		{
			if(i == 0)
			{
				res = x%1000;
				napisy[i] = mniejniz1000(res);
				x /= 1000;
			}
			else
			{
				res = x%1000;
				if(res == 0) napisy[i] = "";
				if(res == 1) napisy[i] += potegi[i];
				if(res != 0 && res != 1) 
				{
					napisy[i] = mniejniz1000(res);
					if(res%10 != 1 && res%10 < 5 && res%100 != 0)
					{
						if(i == 1)napisy[i] += potegi[i]+"e";
						else napisy[i] += potegi[i]+"y";
					}
					else 
					{
						if(i == 1)napisy[i] += " tysięcy";
						else napisy[i] += potegi[i]+"ów";
					}
				}
				x /= 1000;
			}
		}
		for(int i = 3; i >= 0; i--) s += napisy[i];
		return znak+s;
	}
}
