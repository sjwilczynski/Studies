/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista5;

/**
 *
 * @author stachu
 */
public class Dziel extends Funkcja
{
    Double arg1;
    Double arg2;
    @Override
    public double oblicz() throws WyjatekONP 
    {
        if(arg1 == 0) throw new ONP_przez0();
        return arg2/arg1;
    }

    @Override
    public int arność() 
    {
        return 2;
    }
    @Override
    public int brakująceArgumenty() 
    {
        if(arg2 == null)
        {
            if(arg1 == null) return 2;
            return 1;
        }
        return 0;
    }

    @Override
    public void dodajArgument(double d) throws WyjatekONP 
    {
        if(this.brakująceArgumenty() == 0) throw new WyjatekONP(" za dużo argumentów");
        if(arg1 == null)
        {
            arg1 = d;
            return;
        }
        if(arg2 == null) arg2 = d;
    }
    @Override
    public String toString()
    {
        return "/";
    }
}
