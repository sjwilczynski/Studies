import java.io.Serializable;


public class Lista<T> implements Serializable 
{
	elem first = null;
    elem last = null;

    class elem implements Serializable
    {
        public elem(T val)
        {
            value = val;
        }
        public elem prev=null;
        public elem next=null;
        public T value;
    }

    public boolean empty()
    {
        return (first == null && last == null);
    }
    public void pushfront(T val)
    {
        elem e = new elem(val);
        e.next = first;
        if (first != null) first.prev = e;
        else last = e;
        first = e;
    }
    public void pushend(T val)
    {
        elem e = new elem(val);
        e.prev = last;
        if (last != null) last.next = e;
        else first = e;
        last = e;
    }
    public T popfront()
    {
        T val = first.value;
        first = first.next;
        first.prev = null;
        return val;
    }
    public T popend()
    {
        T val = last.value;
        last = last.prev;
        last.next = null;
        return val;
    }
    public void write()
    {
        if(empty())System.out.print("pusta");
        else
        {
            elem e = first;
            while (e != null)
            {
            	System.out.printf("%d ", e.value);
                e = e.next;
            }
        }
        System.out.println();
    }
}

