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
class Lista 
{
    private Wezel first;
    private Wezel last;
    private class Wezel 
    {
        protected Para para;
        protected Wezel next;
        protected Wezel prev;
        /**
         * 
         * @param p
         * 
         * tworzy nowy wezel gdzie p to przechowywana para
         */
        public Wezel (Para p)
        {
            if(p == null) throw new NullPointerException();
            para = p;
            prev = null;
            next = null;
        }
        
        /**
         * 
         * @param p
         *  wstawia nowy wezel
         * @return 
         */
        public void wstaw (Para p)
        {
            Wezel w = new Wezel(p);
            this.next = w;
            w.prev = this;
        }
        
        /**
         * liczy ile wezel ma nastepnikow+1
         * @return 
         */
        public int ile ()
        {
            if(this.para == null) return 0;
            if(next == null || (next.para == null)) return 1;
            return 1+next.ile();
        }
        /**
         * sprawdza czy para o danym kluczu jest w zbiorze
         * @param s
         * @return 
         */
        Para in(String s)
        {
            if(para.klucz.equals(s)) return para;
            if(next != null) return next.in(s);
            return null;
        }
        /**
         * usuwa pare ze zbioru
         * @param s 
         */
        void usun(String s)
        {
            if(para.klucz.equals(s))
            {
                if(next != null) this.next.prev = this.prev;
                if(prev != null) this.prev.next = this.next;
                this.para = null;
            }
            else
            {
                if(next != null) next.usun(s);
            }
        }
        @Override
        public String toString ()
        {
            String s = "";
            s += "Wezel" + para.toString();
            if(next != null) s+= " ->\n"+next.toString();
            if(next == null) s+= "\n";
            return s;
        }
        public String toS ()
        {
            String s = "";
            s += "Wezel" + para.toString();
            if(prev != null) s+= " ->\n"+prev.toS();
            if(prev == null) s+= "\n";
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
     * wstawia parę p do listy
     * @param p 
     */
    public void wstaw(Para p) throws WyjatekONP
    {
        if(first == null)
        {
              first = new Wezel(p);
              last = first;
        }
        else
        {
            if( first.in(p.klucz) != null ) throw new WyjatekONP("zmienna już istnieje");
            Wezel w = new Wezel(p);
            last.next = w;
            w.prev = last;
            last = w;
        }
    }
    /**
     * pobiera i-ty elment listy
     * @param i
     * @return 
     */
    public Para get(int i)
    {
        if(i > rozmiar()) throw new ArrayIndexOutOfBoundsException();
        Para res = null;
        Wezel w = first;
        while(i>0)
        {
            res = w.para;
            w = w.next; // w przypisuje referencje do w.next i dlatego z first nic zlego sie nie dzieje
            i--;
        }
        return res;
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
     * sparwdza czy para jest w liscie
     * @param s
     * @return 
     */
    void usun(String s)
    {
        if(first.para.klucz.equals(s)) first = first.next; 
        else first.next.usun(s);
    }
    /**
     * usuwa wszytskie elementy z listy
     */
    void clear()
    {
        first = null;
        last = null;
    }
    Para zawiera(String s) 
    {
        return first.in(s);
    }
    @Override
    public String toString()
    {
         if(first == null || first.para == null)return "pusta";
         return first.toString();
    }
}
           

    
