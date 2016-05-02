
public class rosnaca
{
	Elem first=null;
	void push(Elem x)
	{
		if(first == null)first = x;
		else
		{
			Elem a = first;
			Elem b = null;
			while(a!=null)
			{
				if(x.compareTo(a) >= 0)
				{
					
						if(a.next == null)
						{
							//System.out.print('!');
								a.next = x;
								return;
						}
						else 
						{
							b = a;
							a = a.next;
						}
				}
				else 
				{
					b.next = x;
					x.next = a;
					return;
				}
			}
		}
		
	}
	Elem pop()
	{
		Elem a = first;
		first = first.next;
		return a;
	}
	void wypisz()
	{
		Elem a = first;
		while(a != null)
		{
			System.out.printf("%s:", a.name);
			System.out.println(a.value);
			a = a.next;
		}
	}
}
