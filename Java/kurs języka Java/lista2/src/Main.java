import java.util.ArrayList;


public class Main 
{

	public static void main(String[] args) 
    { 
        for(String s : args)
        {
        	long x = new Long(s);
            System.out.print( x + " = ");
            ArrayList<Long> tab = Liczbypierwsze.naCzynnikiPierwsze(x);
            for(int j=0; j < tab.size(); j++) 
            {
                if(j != tab.size() - 1) System.out.print(tab.get(j) + " * ");
                else System.out.println(tab.get(j));
                 
            }
        }
    }
}
