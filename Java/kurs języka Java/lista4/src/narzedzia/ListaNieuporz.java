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
public class ListaNieuporz implements Priorytetowy,Cloneable
{
    protected class Wezel implements Cloneable, Comparable<Wezel>
    {
        protected Para para;
        protected Wezel next;
        /**
         * 
         * @param p
         * @param nast
         * tworzy nowy wezel gdzie p to para w nim przechowywana a nast to kolejny wezel
         */
        public Wezel (Para p, Wezel nast)
        {
            if (p==null) throw new NullPointerException();
            para = p;
            next = nast;
        }
        /**
         * 
         * @param p
         * tworzy nowy wezel bez nastepnika
         */
        public Wezel (Para p)
        {
            this(p,null);
        }
        /**
         * 
         * @param p
         *  wstawia pare lsity wezlow(pod warunkiem ze nie ma jzu pary o kluczu p)
         * @return 
         */
        public Wezel wstaw (Para p)
        {
            Wezel x = this.clone();
            while(x != null)
            {
                if(x.para.equals(p))return this;
                x = x.next;
            }
            if(para == null) return new Wezel(p);
            else return new Wezel(p,this);
        }
        /**
         * wstawia pare uwzgledniajac porzadek
         * @param p
         * @return 
         */
        public Wezel wstaw2 (Para p)
        {
             Wezel x = this.clone();
            while(x != null)
            {
                if(x.para.equals(p))return this;
                x = x.next;
            }
            if(para == null) return new Wezel(p);
            if(p.compareTo(para) >= 0) return new Wezel(p,this);
            if(next == null) return new Wezel(para,new Wezel(p));
            else return new Wezel(para,next.wstaw2(p));
        }
        /**
         * wyszukuje pare o maksymalnej wartosci
         * @return 
         */
        public Para szukajMax ()
        {
            if(next == null)return para;
            if(para.compareTo(next.szukajMax()) >= 0) return para;
            return next.szukajMax();
        }
        /**
         * usuwam pare o maksymalnej wartosci i zwraca ja jako wynik
         * @param prev - poprzedni wezel
         * @return 
         */
        public Para UsunMax(Wezel prev)
        {
            Para wyn;
            if(next == null)
            {
                wyn = (Para) para.clone();
                if(prev == null) this.para = null;
                else
                {
                    prev.next = null;
                }
            }
            else
            {
                if(para.compareTo(next.szukajMax()) >= 0) 
                {   
                    wyn = this.para.clone();
                    this.para = this.next.para;
                    this.next = this.next.next;
                }
                else wyn = next.UsunMax(this);
            }
            return wyn;
            
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
        public Wezel clone ()
        {
            try
            {
                Wezel w = (Wezel)super.clone();
                if (next!=null) w.next = next.clone();
                return w;
            }
            catch (CloneNotSupportedException ex) { return null; }
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

        @Override
        public int compareTo(Wezel o) 
        {
            if (o==null) throw new NullPointerException();
           return para.compareTo(o.para);
        }
    }
    
    Wezel pocz;
    /**
     * wstawia pare do listy
     * @param p 
     */
    @Override
    public void wstaw(Para p) 
    {  
        if(pocz == null)pocz = new Wezel(p);
        else pocz = pocz.wstaw(p);
    }

    @Override
    public Para największy() 
    {
        if(pocz == null)return null;
        return pocz.szukajMax();
    }

    @Override
    public Para usuńNajwiększy() 
    {
        if(pocz == null)return null;
        Para wyn = pocz.UsunMax(null);
        if(this.rozmiar() == 0) pocz = null;
        return wyn;
    }

    @Override
    public int rozmiar() 
    {
        if(pocz == null) return 0;
        else return pocz.ile();   
    }    
    @Override
    public String toString()
    {
         if(pocz == null || pocz.para == null)return "pusta";
         return pocz.toString();
    }
    @Override
    public ListaNieuporz clone()
    {
        try
        {
            ListaNieuporz l = (ListaNieuporz)super.clone();
            if (pocz!=null) l.pocz = pocz.clone();
            return l;
        }
        catch (CloneNotSupportedException ex) { return null; }
        
    }
}
