/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista9;


public class Data 
{
    static String[] Dni = {"Niedziela","Poniedziałek","Wtorek","Środa","Czwartek","Piątek","Sobota"};
    static String[] Miesiace = {"Styczeń","Luty","Marzec","Kwiecień","Maj","Czerwiec","Lipiec","Sierpień","Wrzesień","Październik","Listopad","Grudzień"};
    int rok,miesiac,dzien;
    public Data(int r, int m, int d)
    {
        rok = r;
        miesiac = m;
        dzien = d;
    }
    @Override
    public String toString()
    {
        return Dni[dzien] + " " + dzien + Miesiace[miesiac] + " " + rok;
    }
}
