/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista5;
import java.lang.Math;
        
/**
 *
 * @author stachu
 */
public class E extends Funkcja
{
    @Override
    public double oblicz() throws WyjatekONP 
    {
        return Math.E;
    }

    @Override
    public int arność() 
    {
        return 0;
    }
    @Override
    public int brakująceArgumenty() 
    {
        return 0;
    }

    @Override
    public void dodajArgument(double d) throws WyjatekONP 
    {
        if(this.brakująceArgumenty() == 0) throw new WyjatekONP();
    }
    @Override
    public String toString()
    {
        return "e";
    }
}
