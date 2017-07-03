import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.JPanel;


public class MainPanel extends JPanel {

	private static int n, m, x, y, mikX, mikY;
	private static int vis[][] = new int[20][20];
	private static boolean[][] prezent = new boolean[20][20];
	public static int c1[] = {1, -1, 0, 0}, c2[] = {0, 0, -1, 1};
	public static LinkedList<Kreska> lista = new LinkedList<Kreska>();
	int nGap, mGap;
	
	public MainPanel(int n, int m, int x, int y) // n kol m wiersze x szerokosc y wysokosc
	{
		this.setSize(x - 10, y - 20);
		this.n = n;
		this.m = m;
		this.x = x - 10;
		this.y = y - 20;
		nGap = x / n; 
        mGap = y / m - 5;
		init();
	}
	
	private KeyListener keyList = new KeyAdapter()
    {
    	@Override
        public void keyPressed (KeyEvent ev)
        {
            switch (ev.getKeyCode())
            {
            case KeyEvent.VK_A:
            	System.out.println("W");
            	mikX = (mikX - 1 + L11.n) % L11.n;
                break;
            case KeyEvent.VK_D:
            	System.out.println("s");
            	mikX = (mikX + 1 + L11.n) % L11.n;
                break;
            case KeyEvent.VK_W:
            	System.out.println("a");
            	mikY = (mikY - 1 + L11.m) % L11.m;
                break;
            case KeyEvent.VK_S:
            	System.out.println("d");
            	mikY = (mikY + 1 + L11.m) % L11.m;
                break;
            case KeyEvent.VK_SPACE:
            	if(L11.lPrezentow > 0)
            	{
            		L11.lPrezentow--;
    				prezent[mikX][mikY] = true;
            	}
                break;
            case KeyEvent.VK_P:
            	L11.koniecGry = false;
            	break;
            }
        }
    };
	
	void init()
	{  
		this.addKeyListener(keyList);
		this.setFocusable(true);
        this.requestFocus();
		for(int i = 0; i < 15; i++) for(int j = 0; j < 15; j++) vis[i][j] = 0;
        for(int i = 0; i <= m; i++) // z gory na dol
        {
        	Kreska kreska = new Kreska(1, i * mGap, x, i * mGap);
    		lista.add(kreska);
        }
        
        for(int i = 0; i <= n; i++) // z prawa na lewo
        {
        	Kreska kreska = new Kreska(i * nGap, 1, i * nGap, y);
    		lista.add(kreska);
        }
        
        x = (int) Math.floor(Math.random() * L11.n);
		y = (int) Math.floor(Math.random() * L11.m);
		while((MainPanel.checkVis(x,y, 15)) == false)
		{
			x = (int) Math.floor(Math.random() * L11.n);
			y = (int) Math.floor(Math.random() * L11.m);
		}
	}
	
	@Override
    public void paint(Graphics g)
    {
		super.paint(g);
		if(L11.koniecGry == false)
		{
			for(Kreska kreska : MainPanel.lista)
				g.drawLine(kreska.x1, kreska.y1, kreska.x2, kreska.y2);
			for(Dziecko dziecko : MainFrame.dzieci)
			{
				g.drawOval(dziecko.x * nGap, dziecko.y * mGap, nGap, mGap);
				if(dziecko.state == 0)
					g.drawLine(dziecko.x * nGap, dziecko.y * mGap, dziecko.x * nGap + nGap, dziecko.y * mGap + mGap);
				if(dziecko.state == 2)
					g.drawString("ŚPI", dziecko.x * nGap + 30, dziecko.y * mGap + 40);
				g.drawString(Integer.toString(dziecko.nr), dziecko.x  * nGap, (dziecko.y + 1) * mGap);
			}
			g.drawString("MIKOŁAJ", mikX * nGap + 15, mikY * mGap + 15);
			for(int i = 0; i < n; i++)
				for(int j = 0; j < m; j++)
					if(prezent[i][j] == true)
						g.drawString("PREZENT", i * nGap + 15, j * mGap + 55);
		}
	}
	
	public static synchronized void setVis(int x, int y, int nr)
	{
		vis[x][y] = nr;
	}
	
	public static synchronized boolean checkVis(int x, int y, int nr)
	{
		if(vis[x][y] != 0)
			return false;
		else
			vis[x][y] = nr;
		return true;
	}

	public static synchronized int getMikY() {
		return MainPanel.mikY;
	}

	public static synchronized void setMikY(int mikY) {
		MainPanel.mikY = mikY;
	}

	public static synchronized int getMikX() {
		return MainPanel.mikX;
	}
	
	public static synchronized int getVis(int x, int y) 
	{
		return MainPanel.vis[x][y];
	}

	public static synchronized void setPrez(int x, int y, boolean b) 
	{
		prezent[x][y] = b;
	}

	public static synchronized boolean getPrez(int x, int y) 
	{
		return prezent[x][y];
	}

}
