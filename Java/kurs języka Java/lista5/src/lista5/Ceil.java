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
public class Ceil extends Funkcja
{
    Double arg1;
    @Override
    public double oblicz() throws WyjatekONP 
    {
        return (int)(double)arg1 + 1;
    }

    @Override
    public int arność() 
    {
        return 1;
    }
    @Override
    public int brakująceArgumenty() 
    {
        if(arg1 == null)return 1;
        return 0;
    }

    @Override
    public void dodajArgument(double d) throws WyjatekONP 
    {
        if(this.brakująceArgumenty() == 0) throw new WyjatekONP();
        if(arg1 == null)
        {
            arg1 = d;
            return;
        }
    }
    @Override
    public String toString()
    {
        return "[[ ]]";
    }
}
