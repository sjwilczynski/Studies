/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista5;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author stachu
 */
public class Wyrazenie implements Obliczalny
{
    private Kolejka kolejka; // kolejka symboli wyrażenia ONP (elementy typu Symbol)
    private Stos stos; // stos z wynikami pośrednimi obliczeń (elementy typu double)
    private Lista zmienne; // lista zmiennych czyli pary klucz-wartość (String-double)
    public Wyrazenie(String onp, Lista zm) throws WyjatekONP 
    {
        zmienne = zm;
        kolejka = new Kolejka();
        stos = new Stos();
        char[] c = onp.toCharArray();
        for( int i=0; i < c.length; i++ )
        {
            String s = "";
            while(i < c.length && c[i] == ' ') i++;
            while(i < c.length && c[i] != ' ')
            {
                s += c[i];
                i++;
            }
            if(c[i-1] <= '9' && c[i-1] >= '0')
            {
                Double d = Double.parseDouble(s);
                kolejka.wstaw(new Liczba(d));
                continue;
            }
            if(!zmienne.empty())
            {
                if(zmienne.zawiera(s) != null)
                {
                    kolejka.wstaw(zmienne.zawiera(s));
                    continue;
                }
            }
            if("+".equals(s))
            {
                kolejka.wstaw(new Dod());
                continue;
            }
            if("-".equals(s))
            {
                kolejka.wstaw(new Odj());
                continue;
            }
            if("*".equals(s))
            {
                kolejka.wstaw(new Pom());
                continue;
            }
            if("/".equals(s))
            {
                kolejka.wstaw(new Dziel());
                continue;
            }
            if("||".equals(s))
            {
                kolejka.wstaw(new Abs());
                continue;
            }
            if("[]".equals(s))
            {
                kolejka.wstaw(new Floor());
                continue;
            }
            if("[[]]".equals(s))
            {
                kolejka.wstaw(new Ceil());
                continue;
            }
            if("sin".equals(s))
            {
                kolejka.wstaw(new Sin());
                continue;
            }
            if("cos".equals(s))
            {
                kolejka.wstaw(new Cos());
                continue;
            }
            if("atan".equals(s))
            {
                kolejka.wstaw(new Atan());
                continue;
            }
            if("ln".equals(s))
            {
                kolejka.wstaw(new Ln());
                continue;
            }
            if("exp".equals(s))
            {
                kolejka.wstaw(new Exp());
                continue;
            }
            if("acot".equals(s))
            {
                kolejka.wstaw(new Acot());
                continue;
            }
            if("frac".equals(s))
            {
                kolejka.wstaw(new Frac());
                continue;
            }
            if("max".equals(s))
            {
                kolejka.wstaw(new Max());
                continue;
            }
            if("min".equals(s))
            {
                kolejka.wstaw(new Min());
                continue;
            }
            if("^".equals(s))
            {
                kolejka.wstaw(new Pow());
                continue;
            }
            if("log".equals(s))
            {
                kolejka.wstaw(new Log());
                continue;
            }
            if("e".equals(s))
            {
                kolejka.wstaw( new E() );
                continue;
            }
            if("pi".equals(s))
            {
                kolejka.wstaw(new Pi() );
            }
            else
            {
                throw new ONP_nieznanysymbol();
            }
        }
    }
    @Override
    public double oblicz() throws WyjatekONP
    {
            //System.out.println(kolejka);
            while(!kolejka.empty())
            {
                //System.out.println(kolejka);
                Symbol s = kolejka.get();
                if(s instanceof Operand)
                {
                    try 
                    {
                        stos.wstaw(s.oblicz());
                    } catch (WyjatekONP ex) { throw ex;}
                }
                if(s instanceof Funkcja)
                {
                    try 
                    {
                        Funkcja f = (Funkcja)s;
                        while (f.brakująceArgumenty() > 0) 
                        {
                            f.dodajArgument(stos.get());
                        }
                        stos.wstaw(f.oblicz());
                    } catch (WyjatekONP ex) {throw ex;}
                }
            }
            if(stos.rozmiar() > 1) throw new ONP_blednewyrazenie();
            return stos.get();
    }
}

