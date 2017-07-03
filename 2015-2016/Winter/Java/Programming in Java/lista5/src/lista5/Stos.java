/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista5;

/**
 *
 * @author stachu
 */
public class Stos
{
    private Wezel first;
    private Wezel last;
    private class Wezel 
    {
        protected double val;
        protected Wezel next;
        protected Wezel prev;
        /**
         * 
         * @param v
         * 
         * tworzy nowy wezel gdzie v to przechowywana wartosc
         */
        public Wezel (double v)
        {
            val = v;
            prev = null;
            next = null;
        }
        
        /**
         * 
         * @param v
         *  wstawia nowy wezel
         * @return 
         */
        public void wstaw (double v)
        {
            Wezel w = new Wezel(v);
            this.next = w;
            w.prev = this;
        }
        
        /**
         * liczy ile wezel ma nastepnikow+1
         * @return 
         */
        public int ile ()
        {
            if(this == null) return 0;
            if(next == null) return 1;
            return 1+next.ile();
        }
     
        @Override
        public String toString ()
        {
            String s = "";
            s += "Wezel" + val;
            if(next != null) s+= " ->\n"+next.toString();
            if(next == null) s+= "\n";
            return s;
        }
        public String toS ()
        {
            String s = "";
            s += "Wezel" + val;
            if(prev != null) s+= " ->\n"+prev.toS();
            if(prev == null) s+= "\n";
            return s;
        }
    }
    /**
     * sprawdza czy stos jest pusty
     * @return 
     */
    public boolean empty()
    {
        return (first == null);
    }
    /**
     * wstawia symbol s do listy
     * @param s
     */
    public void wstaw(double v)
    {
        if(first == null)
        {
              first = new Wezel(v);
              last = first;
        }
        else
        {
            Wezel w = new Wezel(v);
            w.next = first;
            first.prev = w;
            first = w;
        }
    }
    /**
     * zwraca rozmiar stosu
     * @return 
     */
    public int rozmiar() 
    {
        if(first == null) return 0;
        else return first.ile();   
    }    
    /**
     * pobiera pierwszy elemnt stosu(i go usuwa)
     * @return 
     * @throws lista5.ONP_pustystos 
     */
    public double get() throws ONP_pustystos
    {
        if(first == null) throw new ONP_pustystos();
        else 
        {
           double v = first.val;
           if(first.next != null)first.next.prev = null;
           first = first.next;
           return v;
        }
    }
    @Override
    public String toString()
    {
         if(first == null)return "pusta";
         return first.toString();
    }
}
