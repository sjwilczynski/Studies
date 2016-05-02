/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package narzedzia;

/**
 *
 * @author stachu
 */
public class ListaUporz extends ListaNieuporz
{
    @Override
    public void wstaw(Para p)
    {
        if(pocz == null)pocz = new Wezel(p);
        else pocz = pocz.wstaw2(p);
    }
    @Override
    public Para największy() 
    {
        if(pocz == null)return null;
        return pocz.para;
    }

    @Override
    public Para usuńNajwiększy() 
    {
        if(pocz == null)return null;
        else
        {
            Para wyn = pocz.para.clone();
            pocz = pocz.next;
            return wyn;
        }
    }
}
