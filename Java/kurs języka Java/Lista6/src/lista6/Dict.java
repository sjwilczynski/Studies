/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista6;

/**
 *
 * @author stachu
 * @param <T>
 * metody slownikowe dla elemntow typu T
 */
public interface Dict< T extends Comparable<T> > 
{
    /**
     * metoda sprawdza czy wierzcholek o val jest w drzewie
     * @param val
     * @return 
     */
    public boolean search(T val);
    /**
     * metoda wstawia wierzcholek do drzewa(jesli jeszce niewstawiony)
     * @param val 
     * @throws lista6.Wyjatek_BST 
     */
    public void insert(T val) throws Wyjatek_BST;
    /**
     * metoda usuwa wierzcholek o wartosci val z drzewa
     * @param val 
     */
    public void remove(T val) throws Wyjatek_BST;
    /**
     * zwraca najwiekszy elemnt w drzewie
     * @return 
     */
    public T max();
    /**
     * zwraca najmniejszy elemnt w drzewie
     * @return 
     */
    public T min();
}
