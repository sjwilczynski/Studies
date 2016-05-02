/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package narzedzia;

/**
 *klasa przechowująca (public)klucz i wartość(private)
 * @author stachu
 */
public class Para implements Comparable<Para>, Cloneable
{
    public final String klucz;
    private double wartosc;
    public Para(String k, double w)
    {
        klucz = k;
	wartosc  = w;
    }
    /**
     * funkcja ustawiająca wartość
     * @param wartosc 
     */
    public void zapisz(double wartosc) {
        this.wartosc = wartosc;
    }
    /**
     * funkcja zwracająca wartość
     * @return 
     */
    public double czytaj() {
        return wartosc;
    }

    /**
     *
     * @param obj
     * porównuje dwie pary
     * @return
     */
    @Override
	public boolean equals(Object obj) 
	{
		if(obj instanceof Para)
		{
			if(((Para) obj).klucz.equals(this.klucz)) return true;
		}
		return false;
	}
	@Override
	public String toString() 
	{
		return "( "+ klucz + ", " + wartosc +" )";
	}

    @Override
    public int compareTo(Para o) 
    {
        if(wartosc < o.czytaj()) return -1;
        if(wartosc == o.czytaj()) return 0;
        return 1;
    }
    @Override
    public Para clone() 
    {
        try
            {
                Para w = (Para)super.clone();
                return w;
            }
            catch (CloneNotSupportedException ex) { return null; }
    }
    
}
