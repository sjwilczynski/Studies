import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Pojazd implements Serializable
{
	protected String producent;
	protected String model;
	protected Integer wiek;
	public Pojazd(String p, String m, int w)
	{
		producent = p;
		model = m;
		wiek = w;
	}
	public Pojazd()
	{
		producent = "";
		model = "";
		wiek = 0;
	}
	public String toString()
	{
		String s = "";
		s += "Nasza maszyna to ";
		s += this.getClass().getName();
		s += " wyprodukowana przez ";
		s += producent;
		s += ". Model to ";
		s += model;
		s += ", ma lat ";
		s += wiek.toString();
		s +=".\n";
		return s;
	}
	public void writePojazd(String nazwa) 
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
	public void readPojazd(String nazwa)
	{
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try 
		{
		   fis = new FileInputStream(nazwa); 
		   ois = new ObjectInputStream(fis); 
		   Pojazd p = (Pojazd)ois.readObject();
		   producent = p.producent;
		   model = p.model;
		   wiek = p.wiek;
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
