package Pakiet;
public class Zbior 
{
	Para[] tab;
	int last;
	{
		tab = new Para[20];
		last = -1;
	}
	public Zbior(int n)
	{
		tab = new Para[n];
	}

	/** metoda szuka pary z określonym kluczem */
	public Para szukaj (String kl) 
	{
		Para x = null;
		for(int i = 0; i <= last; i++ ) //tu tez z tym for z : sie wywalalo
		{
			if(tab[i].klucz == kl)x = tab[i];
		}
		return x;
	}

	/** metoda wstawia do zbioru nową parę */
	public void wstaw (Para p) throws IllegalArgumentException 
	{
		for(int i = 0; i <= last; i++)
		{
			if(tab[i].equals(p))return;
		}
		last++;
		tab[last] = p;
	}
	/** metoda odszukuje parę i zwraca wartość związaną z kluczem */
	public double czytaj (String kl) throws IllegalArgumentException 
	{
		Para p = szukaj(kl);
		return p.getWartosc();
	}
	/** metoda wstawia do zbioru nową albo aktualizuje istniejącą parę */
	public void ustal (Para p) throws IllegalArgumentException 
	{
		for(int i = 0; i <= last; i++) //dlaczego jak bylo for(Para q: tab) to sie wywalal
		{
			if(tab[i].equals(p)) 
			{
				tab[i].setWartosc( p.getWartosc() );
				return;
			}
			last++;
			tab[last] = p;
		}
	}
	/** metoda podaje ile par jest przechowywanych w zbiorze */
	public int ile () 
	{
		return last+1;
	}
	/** metoda usuwa wszystkie pary ze zbioru */
	public void czysc () 
	{
		for(int i = 0; i <= last; i++)tab[i] = null;
		last = -1;
	}
}