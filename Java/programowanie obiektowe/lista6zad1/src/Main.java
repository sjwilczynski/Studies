import java.io.*;


public class Main 
{
	public static void main(String[] args) 
	{
		Lista<Integer> L = new Lista<Integer>();
		for(int i=0;i<10;i++)L.pushend(i);
		for(int i=10;i<15;i++)L.pushfront(i);
		L.write();
		L.pushfront(15);
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		String name = "/home/stachu/programy/java/lista.ser"; //trzeba bedzie zamienic na /tmp/lista.ser 
		try
		{
		   fos = new FileOutputStream(name); 
		   oos = new ObjectOutputStream(fos);  
		   oos.writeObject(L); 
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
		Lista<Integer> L2 = new Lista<Integer>();
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try 
		{
		   fis = new FileInputStream(name); 
		   ois = new ObjectInputStream(fis); 
		   L2 =  (Lista<Integer>)ois.readObject();
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
		L2.write();

	}
}
