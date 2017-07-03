
public class Elem implements Comparable<Elem>
{
	int value;
	String name;
	Elem next = null;
	Elem(int a,String s)
	{
		value = a;
		name = s;
	}
	Elem(int a)
	{
		value = a;
		name = "default";
	}
	public int compareTo(Elem x) 
	{
		if(value > x.value) return 1;
		if(value == x.value)
		{
			return name.compareTo(x.name);
		}
		return -1;
	}

}
