import java.util.ArrayList;

public abstract class Liczbypierwsze
{
    protected final static int POTEGA2 = 21 ;
    protected final static int[] SITO = new int[1<<POTEGA2] ;
    static
    {
        //for(int i=0; i < 1<<POTEGA2; i++) SITO[i] = 0;
        SITO[1] = 1;
        for(int i = 2; i*i < 1<<POTEGA2; i++)
        {
            if(SITO[i] == 0) 
            {
                SITO[i] = i;
                for(int j=2*i; j < 1<<POTEGA2; j+=i) 
                {
                    if(SITO[j] == 0) SITO[j] = i;
                }
            }
        }
	for(int i = 2; i < 1<<POTEGA2; i++)
	{
		if(SITO[i] == 0) SITO[i] = i;
	}

    }
    public static boolean czyPierwsza (long x)
    { 
    	if(x < 0) 
    	{
    		if(x == Long.MIN_VALUE) return false;
    		x = -x;
    	}
    	if(x == 0 || x == 1 ) return false;
    	if(x < 1<<POTEGA2) return SITO[(int) x] == x;
    	else
    	{
    		if(x%2 == 0) return false;
    		for(long i = 3; i*i < x; i+=2)
    		{
    			if(x%i == 0) return false;
    		}
    		return true;
    	}
    }
    public static ArrayList<Long> naCzynnikiPierwsze (long x) 
    {
    	ArrayList<Long> list = new ArrayList<Long>();
    	if(x == 1 || x==0 || x==-1)
    	{
    		list.add(x);
    		return list;
    	}
    	if(x < 0) 
    	{
    		list.add((long) - 1);
    		if(x == Long.MIN_VALUE)
    		{
    			list.add((long) 2);
    			x = x/(long) 2;
    		}
    			x = -x;
    	}
    	if(x >= 1<<POTEGA2)
    	{
    		while(x%(long) 2 == 0)
    		{
    			list.add((long) 2);
    			x = x/(long) 2;
    		}
    		for(long i = 3; i*i < x; i += 2)
    		{
    			if(x < 1<<POTEGA2)break;
    			if(x%i == 0) 
    			{
    				list.add(i);
    				x = x/i;
    				i-=2;
    			}
    		}
    		if(x >= 1<<POTEGA2)
    		{
    			list.add(x);
    			return list;
    		}
    		
    	}
        int y = (int) x;
	//for (int i = 1; i < 100; i++) System.out.print(SITO[i]);
        while(y > 1)
        {
            int d = SITO[y];
	    //System.out.println(y+ " " +d);
            list.add((long) d);
            y = y/d;
        }
        return list;
    }
}
