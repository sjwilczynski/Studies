/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista9;

import java.util.GregorianCalendar;

/**
 *
 * @author stachu
 */
public class Lista9 
{
    static int currentYear, currentMonth;
    public static void main(String[] args) 
    {
        GregorianCalendar cal = new GregorianCalendar();
        currentMonth = cal.get(GregorianCalendar.MONTH);
        currentYear = cal.get(GregorianCalendar.YEAR); 
        new Okno();
    }   
}
