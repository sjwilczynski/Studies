/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package narzedzia;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author stachu
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        ListaNieuporz L = new ListaNieuporz();
        ListaUporz L1 = new ListaUporz();
        
        
        String s;
        String w;
        double d;
        Scanner input = new Scanner(System.in);
        
        while(true)
        {
            try
            {
                s = input.nextLine();
                if(s.equals("eof")) break;
                w = input.nextLine();
                d = Double.parseDouble(w);
                L.wstaw(new Para(s,d));
            }
            catch(NoSuchElementException e){break;}
        }
        
               
        
        ListaNieuporz L2 = L.clone();
        while(L2.rozmiar() > 0) 
        {
           
            L1.wstaw(L2.usuńNajwiększy());
            
        }
                
        System.out.println(L1);
        System.out.println(L);
    }
    
}
