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
public class Liczba extends Operand
{
    private double val;
    public Liczba(double v)
    {
        val = v;
    }
    @Override
    public double oblicz() throws WyjatekONP 
    {
        return val;
    }
    
}
