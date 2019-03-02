/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista7;
/**
 * @author stachu
 */
public class list 
{
    private Wezel first;
    private Wezel last;
    private class Wezel 
    {
        protected Kreska kr;
        protected Wezel next;
        protected Wezel prev;
        /**
         * 
         * @param p
         * 
         * tworzy nowy wezel gdzie k to przechowywana kreska
         */
        public Wezel(Kreska k)
        {
            if(k == null) throw new NullPointerException();
            kr = k;
            prev = null;
            next = null;
        }
        
        /**
         * 
         * @param p
         *  wstawia nowy wezel
         * @return 
         */
        public void wstaw (Kreska k)
        {
            Wezel w = new Wezel(k);
            this.next = w;
            w.prev = this;
        }
        
        /**
         * liczy ile wezel ma nastepnikow+1
         * @return 
         */
        public int ile ()
        {
            if(this.kr == null) return 0;
            if(next == null || (next.kr == null)) return 1;
            return 1+next.ile();
        }
        
        public String toS ()
        {
            String s = "";
            s += "Wezel" + kr.toString();
            if(next != null) s+= " ->\n"+next.toS();
            if(next == null) s+= "\n";
            return s;
        }
    }
    /**
     * sprawdza czy lista jest pusta
     * @return 
     */
    public boolean empty()
    {
        return (first == null);
    }
    /**
     * wstawia kreske k do listy
     * @param k
     */
    public void wstaw(Kreska k) 
    {
        if(first == null)
        {
              first = new Wezel(k);
              last = first;
        }
        else
        {
            
            Wezel w = new Wezel(k);
            last.next = w;
            w.prev = last;
            last = w;
        }
    }
    /**
     * usuwa wszytskie elementy z listy
     */
    void clear()
    {
        first = null;
        last = null;
    }
    /**
     * usuwa ostatni element listy
     */
    public void usunost()
    {
        if(last == first) first = null;
        last = last.prev;
        if(last != null)last.next = null;
    }
    /**
     * usuwa pierwszy element listy
     */
    public void usunp()
    {
        first = first.next;
    }
    /**
     * zwraca rozmiar listy
     * @return 
     */
    public int rozmiar() 
    {
        if(first == null) return 0;
        else return first.ile();   
    }
    /**
     * pobiera i-ty element listy
     * @param i
     * @return 
     */
    public Kreska get(int i)
    {
        if(i > rozmiar()) throw new ArrayIndexOutOfBoundsException();
        Kreska res = null;
        Wezel w = first;
        while(i>0)
        {
            res = w.kr;
            w = w.next; // w przypisuje referencje do w.next i dlatego z first nic zlego sie nie dzieje
            i--;
        }
        return res;
    }
    @Override
    public String toString()
    {
        if(first == null) return "pusta";
        else return first.toS();
    }
   
}
           

    
