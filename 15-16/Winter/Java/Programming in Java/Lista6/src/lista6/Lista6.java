/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista6;

/**
 *
 * @author stachu
 */
public class Lista6 
{
    /**
     * funkcja testujaca drzewo dla kalsy Integer
     */
    public static void testINT()
    {
        
        try 
        {
            BST<Integer> drzewo = new BST(5);
            drzewo.insert(3);
            drzewo.insert(9);
            drzewo.insert(7);
            drzewo.insert(10);
            drzewo.insert(6);
            drzewo.insert(8);
            drzewo.insert(12);
            //drzewo.insert(5);
            drzewo.insert(4);
            drzewo.insert(2);
            drzewo.insert(1);
            System.out.println(drzewo);
            System.out.println(drzewo.search(6));
            System.out.println(drzewo.search(9));
            System.out.println(drzewo.search(1));
            System.out.println(drzewo.search(13));
            System.out.println("Max to: " + drzewo.max());
            System.out.println("Min to: " + drzewo.min());
            
            System.out.println("Rozmiar to: " + drzewo.size());
            drzewo.remove(1);
            System.out.println("Rozmiar to: " + drzewo.size());
            System.out.println(drzewo);
            
            drzewo.remove(9);
            System.out.println("Rozmiar to: " + drzewo.size());
            System.out.println(drzewo);
            
            drzewo.remove(5);
            System.out.println("Rozmiar to: " + drzewo.size());
            System.out.println(drzewo);
            
            drzewo.clear();
            System.out.println(drzewo);
        } 
        catch (Wyjatek_BST ex) 
        {
            System.err.println(ex.getMessage());
        }
    }
    /**
     * funkcja testujaca drzewo dla klasy String
     */
    public static void testString()
    {
        try 
        {
            BST<String> drzewo = new BST("mama");
            drzewo.insert("moja");
            drzewo.insert("maly");
            drzewo.insert("pies");
            drzewo.insert("kot");
            drzewo.insert("problem");
            drzewo.insert("drzewa");
            drzewo.insert("drzewo");
            drzewo.insert("abecadlo");
            drzewo.insert("beka");
            
            System.out.println("Max to: " + drzewo.max());
            System.out.println("Min to: " + drzewo.min());
            System.out.println("Rozmiar to: " + drzewo.size());
            System.out.println(drzewo.search("maly"));
            System.out.println(drzewo.search("problem"));
            System.out.println(drzewo.search("zebra"));
            System.out.println(drzewo);
            
            drzewo.remove("drzewa");
            System.out.println("Rozmiar to: " + drzewo.size());
            System.out.println(drzewo);
            
            drzewo.remove("drzewo");
            System.out.println("Rozmiar to: " + drzewo.size());
            System.out.println(drzewo);
            
            drzewo.remove("mama");
            System.out.println("Rozmiar to: " + drzewo.size());
            System.out.println(drzewo);
            
            
            
        } 
        catch (Wyjatek_BST ex)
        {
            System.err.println(ex.getMessage());
        }
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        System.out.println("ROZPOCZYNAM TEST\n\n");
        //przetestowane usuwanie ze srodka drzewa wierzcholka majacego obu synow i odpowiednia zamiana rodzicow 
        testINT();
        System.out.println("/////////////////////////////////////////////////////////////////////////////////////////////////////");
        testString();
        System.out.println("\nTEST ZAKONCZONY POMYSLNIE");
    }
}
