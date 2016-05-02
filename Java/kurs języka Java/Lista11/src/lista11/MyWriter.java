package lista11;
import java.io.*;


public class MyWriter extends BufferedWriter 
{
    int tabl;
    public MyWriter(Writer out) 
    {
        super(out);
    }

    MyWriter(FileOutputStream fos, String kodowanie, int tablen) throws UnsupportedEncodingException 
    {
        super(new OutputStreamWriter(fos,kodowanie));
        tabl = tablen;
    }
    MyWriter(FileOutputStream fos, String kodowanie) throws UnsupportedEncodingException 
    {
        super(new OutputStreamWriter(fos,kodowanie));
        tabl = 8;
    }
    MyWriter(FileOutputStream fos, int tablen)
    {
        super(new OutputStreamWriter(fos));
        tabl = tablen;
    }
}
