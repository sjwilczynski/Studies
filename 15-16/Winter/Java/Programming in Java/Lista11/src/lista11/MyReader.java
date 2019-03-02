package lista11;
import java.io.*;

public class MyReader extends BufferedReader 
{
    int tabl;
    public MyReader(FileInputStream in, String kodowanie, int tablen) throws UnsupportedEncodingException
    {
        super(new InputStreamReader(in,kodowanie));
        tabl = tablen;
    }
    public MyReader(FileInputStream in, String kodowanie) throws UnsupportedEncodingException
    {
        super(new InputStreamReader(in,kodowanie));
        tabl = 8;
    }
    public MyReader(FileInputStream in, int tablen)
    {
        super(new InputStreamReader(in));
        tabl = tablen;
    }
    
    public MyReader(Reader in) 
    {
        super(in);
    }
    public void czysc(MyWriter out)
    {
        String line;
        try 
        {
            while( (line = this.readLine()) != null )
            {
                int i = 0;
                String s = "";
                while(i < line.length() && Character.isWhitespace(line.charAt(i)))
                {
                    if(line.charAt(i) == '\t')
                    {
                        for(int j = 0; j < tabl; j++) s += ' ';
                    }
                    else s += ' ';
                    i++;
                }
                out.write(s);
                s = line.substring(i);
                s = s.trim();
                i = 0;
                while(i < s.length())
                {
                    while(i < s.length() && !Character.isWhitespace(s.charAt(i)))
                    {
                        out.write(s.charAt(i));
                        i++;
                    }
                    while(i < s.length() && Character.isWhitespace(s.charAt(i)))i++;
                    if(Character.isWhitespace(s.charAt(i-1))) out.write(' ');
                }
                out.newLine();
            }
        }catch (IOException ex) {ex.printStackTrace();}
    }       
}
