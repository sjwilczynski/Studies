
public class Wezel extends Wyrazenie 
{
	Wyrazenie l;
	Wyrazenie p;
	char znak;
	
	Wezel(Wyrazenie A, Wyrazenie B, char c)
	{
		l =   A;
		p =   B;
		znak = c;
	}
	public String toString()
	{
		String s = "";
		if(znak == '*' || znak == '/')
		{
			boolean a = false;
			boolean b = false;
			if(l.getClass().getName() == "Lisc")a = true;
			if(p.getClass().getName() == "Lisc")b = true;
			if(!a) s += "(";
			s += l.toString();
			if(!a) s += ")";
			s += znak;
			if(!b) s += "(";
			s += p.toString();
			if(!b) s += ")";
		}
		else
		{
			s += l.toString();
			s += znak;
			s += p.toString();
		}
		return s;
	}
	public int oblicz()
	{
		int a = l.oblicz();
		int b = p.oblicz();
		int res = 0;
		if(znak == '+') res = a+b;
		if(znak == '*') res = a*b;
		if(znak == '/') res = a/b;
		if(znak == '-') res = a-b;
		return res;
	}
}
