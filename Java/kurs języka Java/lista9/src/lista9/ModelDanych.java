/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista9;

import java.util.GregorianCalendar;
import javax.swing.AbstractListModel;


class ModelDanych extends AbstractListModel
{
    private final int przes;
    public ModelDanych(int p)
    {
        przes = p;
    }
    @Override
    public Object getElementAt (int i)
    {
        GregorianCalendar cal = new GregorianCalendar(Lista9.currentYear, Lista9.currentMonth+przes, 1);
        int som = cal.get(GregorianCalendar.DAY_OF_WEEK);
        String s = "";
        if(Lista9.currentMonth+przes == 9 && Lista9.currentYear == 1582 && i > 3) s += i+1+10+" ";
        else s += i+1 + " ";
        int res = (Lista9.currentMonth+przes)%12;
        if(res < 0) res = 12 + res;
        s += Data.Miesiace[res] + " " + Data.Dni[(som-1+i)%7] + " " ;
        return s;
    }
    @Override
    public int getSize ()
    {
        GregorianCalendar cal = new GregorianCalendar(Lista9.currentYear, Lista9.currentMonth+przes, 1);
        int nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        if(Lista9.currentYear == 1582 && Lista9.currentMonth+przes == 9) nod = 21;
        return nod;
    }
}
