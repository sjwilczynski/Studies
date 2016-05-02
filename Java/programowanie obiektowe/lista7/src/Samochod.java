import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Samochod extends Pojazd implements Serializable
{
	protected Integer przebieg;
	protected String paliwo;
	protected String opony;
	public Samochod(String p, String m, int w, int prz, String pal, String o)
	{
		super(p,m,w);
		przebieg  = prz;
		paliwo = pal;
		opony = o;
	}
	public Samochod()
	{
		super();
		przebieg = 0;
		paliwo = "";
		opony = "";
	}
	public String toString()
	{
		String s = "";
		s += super.toString();
		s += "Jego przebieg to ";
		s += przebieg.toString();
		s += ", jezdzi na ";
		s += paliwo;
		s += ", a uzywa opon ";
		s += opony;
		s += ".\n";
		return s;
	}
	public void writeSamochod(String nazwa) 
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
	public void readSamochod(String nazwa)
	{
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try 
		{
		   fis = new FileInputStream(nazwa); 
		   ois = new ObjectInputStream(fis); 
		   Samochod p = (Samochod)ois.readObject();
		   producent = p.producent;
		   model = p.model;
		   wiek = p.wiek;
		   opony = p.opony;
		   paliwo = p.paliwo;
		   przebieg = p.przebieg;
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
