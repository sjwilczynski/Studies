/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author stachu
 */
public class Lista5 {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException 
    {
        Lista L = new Lista();
        
        
        Logger logger = Logger.getLogger("calc");
        Handler handler = new FileHandler("calc.log",true);
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
        System.out.println("Wpisuj tylko odpowiednie polecenia i wyrażenie w ONP!!");
        
        BufferedReader we = new BufferedReader(new InputStreamReader(System.in));
        logger.entering(Lista5.class.getName(), "main");
        for(String s = we.readLine(); s != null; s = we.readLine())
        {
            assert (s.startsWith("clear") || s.startsWith("calc") || s.startsWith("exit") ) : "nieznane polecenie";
            try
            {
                if(s.startsWith("calc"))
                {
                   int b = 4;
                   int e = s.length();
                   if( s.contains("(")) e = s.indexOf('(');
                   String wyr = s.substring(b, e);
                   String zm = "";
                   if(e != s.length() )zm = s.substring(e);
                   char[] c = zm.toCharArray();
                   for( int i=1; i < c.length-1; i++ )
                   {
                        String s1 = "";
                        String s2 = "";
                        while(i < c.length-1 && c[i] == ' ')i++;
                        while(i < c.length-1 && (c[i] != '=' && c[i] != ' '))
                        {
                            s1 += c[i];
                            i++;
                        }
                        i++;
                        while(i < c.length-1 && (c[i] == ' ' || c[i] == '='))i++;
                        while(i < c.length-1 && ((c[i] <= '9' && c[i] >= '0') || c[i] == '.') )
                        {
                            s2 += c[i];
                            i++;
                        }
                        L.wstaw(new Para(s1,Double.parseDouble(s2)));
                   }
                   Wyrazenie w = new Wyrazenie(wyr,L);
                   double d;
                   d = w.oblicz();
                   System.out.println(d);
                   logger.log(Level.INFO, "wynik:" + d );
                }
                if(s.startsWith("clear"))
                {
                    if(s.equals("clear"))
                    {
                        L.clear();
                        continue;
                    }
                    int b = 5;
                    String zm = s.substring(b);
                    char[] c = zm.toCharArray();
                    for( int i=0; i < c.length; i++ )
                    {
                        while(i < c.length && c[i] == ' ')i++;
                        String z = "";
                        while(i < c.length && c[i] != ' ')
                        {
                            z += c[i];
                            i++;
                        }
                        L.usun(z);
                    }
                }
                if (s.startsWith("exit")) return;
            }
            catch(WyjatekONP ex){ System.err.println(ex.getMessage());}
        }
    }
}
