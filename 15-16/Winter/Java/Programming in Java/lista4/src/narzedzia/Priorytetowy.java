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
public interface Priorytetowy {
    /**
     * @param p
     * wstawia parę p do kolekcji chyba że już występuje 
     */
    void wstaw(Para p);
    /**
     * zwraca największy element kolekcji
     * @return emnt kolekcji
     */
    Para największy();
    /**
     * usuwa największy el
     * @return 
     */
    Para usuńNajwiększy();
    /**
     * zwraca liczbę elemtów kolekcji
     * @return 
     */
    int rozmiar();

}
