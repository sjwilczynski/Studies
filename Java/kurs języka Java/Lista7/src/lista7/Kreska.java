/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista7;

import java.awt.*;

/**
 *
 * @author stachu
 */
public class Kreska 
{
    protected Point poczatek, koniec;
    public static Point p;
    public static list L = new list();
    public final Color kolor;
    public Kreska(Point pocz, Point kon, Color kol) 
    {
        poczatek = pocz;
        koniec = kon;
        kolor = kol;
    }
}
