import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Tramwaj extends Pojazd implements Serializable
{
	protected Integer numer;
	protected String poczatek;
	protected String koniec;
	public Tramwaj(String p, String m, int w, int n, String pocz, String kon)
	{
		super(p,m,w);
		numer = n;
		poczatek = pocz;
		koniec =kon;
	}
	public Tramwaj()
	{
		super();
		numer = 0;
		poczatek = "";
		koniec = "";
	}
	public String toString()
	{
		String s = "";
		s += super.toString();
		s += "Jego numer to ";
		s += numer.toString();
		s += ", trase zaczyna na przystanku ";
		s += poczatek;
		s += ", a konczy na przystanku ";
		s += koniec;
		s += ".\n";
		return s;
	}
	public void writeTramwaj(String nazwa) 
	{
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try
		{
		   fos = new FileOutputStream(nazwa); 
		   oos = new ObjectOutputStream(fos);  
		   oos.writeObject(this); 
		}
		catch (FileNotFoundException e) {System.out.print("1");}
		catch (IOException e) {System.out.print("2");}
		finally
		{
			   try 
			   {
			     if (oos != null) oos.close();
			   } 
			   catch (IOException e) {System.out.print("3");}
			   try 
			   {
			     if (fos != null) fos.close();
			   } 
			   catch (IOException e) {System.out.print("4");}
		}
	}
	public void readTramwaj(String nazwa)
	{
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try 
		{
		   fis = new FileInputStream(nazwa); 
		   ois = new ObjectInputStream(fis); 
		   Tramwaj p = (Tramwaj)ois.readObject();
		   producent = p.producent;
		   model = p.model;
		   wiek = p.wiek;
		   numer = p.numer;
		   poczatek = p.poczatek;
		   koniec = p.koniec;
		} 
		catch (FileNotFoundException e) {System.out.print("!");}
		catch (IOException e) {e.printStackTrace();}
		catch (ClassNotFoundException e) {System.out.print("!!!");}
		finally 
		{
		   try 
		   {
		     if (ois != null) ois.close();
		   } 
		   catch (IOException e) {}
		   try 
		   {
		     if (fis != null) fis.close();
		   } 
		   catch (IOException e) {}
		}
	}

}
