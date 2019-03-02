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
class WyjatekONP extends Exception 
{
    public WyjatekONP()
    {
        super("Błąd");
    }
    public WyjatekONP(String s)
    {
        super("Błąd :"+s);
    }
}
