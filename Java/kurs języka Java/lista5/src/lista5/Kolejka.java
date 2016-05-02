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
public class Kolejka 
{
    private Wezel first;
    private Wezel last;
    private class Wezel 
    {
        protected Symbol sym;
        protected Wezel next;
        protected Wezel prev;
        /**
         * 
         * @param s
         * 
         * tworzy nowy wezel gdzie s to przechowywany symbol
         */
        public Wezel (Symbol s)
        {
            if(s == null) throw new NullPointerException();
            sym = s;
            prev = null;
            next = null;
        }
        
        /**
         * 
         * @param s
         *  wstawia nowy wezel
         * @return 
         */
        public void wstaw (Symbol s)
        {
            Wezel w = new Wezel(s);
            this.next = w;
            w.prev = this;
        }
        
        /**
         * liczy ile wezel ma nastepnikow+1
         * @return 
         */
        public int ile ()
        {
            if(this.sym == null) return 0;
            if(next == null || (next.sym == null)) return 1;
            return 1+next.ile();
        }
     
        @Override
        public String toString ()
        {
            String s = "";
            s += "Wezel" + sym.toString();
            if(next != null) s+= " ->\n"+next.toString();
            if(next == null) s+= "\n";
            return s;
        }
        public String toS ()
        {
            String s = "";
            s += "Wezel" + sym.toString();
            if(prev != null) s+= " ->\n"+prev.toS();
            if(prev == null) s+= "\n";
            return s;
        }
    }
    /**
     * sprawdza czy kolejka jest pusta
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
    public void wstaw(Symbol s)
    {
        if(first == null)
        {
              first = new Wezel(s);
              last = first;
        }
        else
        {
            Wezel w = new Wezel(s);
            last.next = w;
            w.prev = last;
            last = w;
        }
    }
    /**
     * zwraca rozmiar kolejki
     * @return 
     */
    public int rozmiar() 
    {
        if(first == null) return 0;
        else return first.ile();   
    }    
    /**
     * pobiera pierwszy elemnt kolejki(i go usuwa)
     * @return 
     */
    public Symbol get()
    {
        if(first == null) throw new NullPointerException();
        else 
        {
           Symbol s = first.sym;
           if(first.next != null) first.next.prev = null;
           first = first.next;
           return s;
        }
    }
    @Override
    public String toString()
    {
         if(first == null || first.sym == null)return "pusta";
         return first.toString();
    }
    
}
