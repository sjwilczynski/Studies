import java.awt.Color;
import java.io.Serializable;

public class Gracz implements Serializable
{
	public String nazwa;
	public Color kolor;
	public Gracz(String s, Color c)
	{
		nazwa = s;
		kolor = c;
	}
}
