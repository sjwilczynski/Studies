import java.awt.Container;
import java.awt.Shape;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Wyniki implements Serializable
{
	private ArrayList< Gracz > wygrani;
	private ArrayList< Gracz > przegrani;
	String nazwa = "/home/stachu/programy/java/wyniki.ser";
	public Wyniki()
	{
		readWyniki();
	}
	
	public void dodajWynik(Gracz g1, Gracz g2) 
	{
		wygrani.add(g1);
		przegrani.add(g2);
		writeWyniki();
	}
	public void pokazWyniki()
	{
		JFrame j = new JFrame("Wyniki");
		String s = "<html> Oto wyniki(po lewej zwycięzcy, po prawej przegrani):";
		Container kontener = j.getContentPane();
		((JComponent) kontener).setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		JLabel label = new JLabel("",JLabel.CENTER);
		for(int i = 0; i < wygrani.size(); ++ i)
		{
			s += ("<br> "+ wygrani.get(i).nazwa + "     " + przegrani.get(i).nazwa);
		}
		s += "</html>";
		if(wygrani.isEmpty())label.setText("<html>Do tej pory żaden mecz nie został rozegrany</html>");
		else label.setText(s);
		kontener.add(label);
		j.setSize(300,300);
		j.setVisible(true);
	}
	
	public void writeWyniki() 
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
	public void readWyniki()
	{
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try 
		{
		   fis = new FileInputStream(nazwa); 
		   ois = new ObjectInputStream(fis); 
		   Wyniki w = (Wyniki)ois.readObject();
		   wygrani = w.wygrani;
		   przegrani = w.przegrani;
		} 
		catch (FileNotFoundException e) 
		{
			wygrani = new ArrayList< Gracz >();
			przegrani = new ArrayList< Gracz >();
		}
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
