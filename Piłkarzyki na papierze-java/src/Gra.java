public class Gra 
{
	final Gracz Gracz1;
	final Gracz Gracz2;
	Gracz[] tura = new Gracz[2];
	final int dlugosc;
	final int szerokosc;
	final Integer bok;
	final Integer startx;
	final Integer starty;
	final Boisko plansza;
	static Menu menu;
	final Widok widok;
	
	public Gra(int b, int d, int s,int sx,int sy,Gracz g1, Gracz g2)
	{
		dlugosc = d;
		szerokosc = s;
		bok = b;
		startx = sx;
		starty = sy;
		Gracz1 = g1;
		Gracz2 = g2;
		tura[0] = g1;
		tura[1] = g2;
		plansza = new Boisko(this);
		widok = new Widok(this);
		widok.setVisible(true);
	}
	public static void rozpocznijgre()
	{
		menu = new Menu();
		menu.start();
	}
}
