import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class BoiskoEdit extends JPanel implements MouseListener
{
	private Boisko plansza;
	private Gracz[] gracze;
	private Gracz tura;
	private Gracz pretura;
	private Boolean cofanie = false;
	private ArrayList< Shape > shapes = new ArrayList< Shape >();
	private ArrayList< Gracz > nrgracza = new ArrayList< Gracz >();
	private Integer bok;
	private Integer[] last = new Integer[4];
	private Integer[] prev = new Integer[4];
	private Integer startx;
	private Integer starty;
	private Integer dlugosc;
	private Integer szerokosc;
	private Widok w;
	private BasicStroke bs = new BasicStroke(2);
	
	public BoiskoEdit(Gra g) 
	{
		plansza = g.plansza;
		gracze = g.tura;
		tura = g.tura[0];
		startx = g.startx;
		starty = g.starty;
		bok = g.bok;
		dlugosc = g.dlugosc;
		szerokosc = g.szerokosc;
		addMouseListener(this);
	}
	public void set(Widok v)
	{
		w = v;
	}

	private void doDrawing(Graphics g) 
	{
        Graphics2D g2d = (Graphics2D) g;
        rysuj(g2d,dlugosc,szerokosc);
        g2d.setStroke(bs);
        for(Integer i = 0; i < shapes.size(); ++ i)
        {
        	g2d.setColor( nrgracza.get(i).kolor );
        	Line2D.Double l = (Line2D.Double) shapes.get(i);
        	for(int j = 0; j <= 3 ; ++j)prev[j] = last[j];
        	last[2] = (int) l.x2;
        	last[3] = (int) l.y2;
        	last[0] = (last[2] - startx)/bok;
        	last[1] = (last[3] - starty)/bok + 1;
        	g2d.setStroke(bs);
        	g2d.draw( l );
        }
        g2d.setColor(Color.BLACK);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fill(new Ellipse2D.Double(last[2]-bok/10-1, last[3]-bok/10-1, bok/5+2, bok/5+2));
        
    }
	
	private void rysuj(Graphics2D g2d, int dlugosc, int szerokosc) 
	{
		g2d.setBackground(Color.WHITE);
		g2d.setStroke(new BasicStroke(3));
		Polygon p = new Polygon();
		p.addPoint(startx,starty);
		p.addPoint(startx + (szerokosc/2 -1)*bok ,starty);
		p.addPoint(startx + (szerokosc/2 -1)*bok ,starty - bok);
		p.addPoint(startx + (szerokosc/2 +1)*bok ,starty - bok);
		p.addPoint(startx + (szerokosc/2 +1)*bok ,starty);
		p.addPoint(startx + (szerokosc )*bok ,starty);
		p.addPoint(startx + (szerokosc )*bok ,starty + dlugosc*bok);
		p.addPoint(startx + (szerokosc/2 +1)*bok ,starty + dlugosc*bok);
		p.addPoint(startx + (szerokosc/2 +1)*bok ,starty + (dlugosc+1)*bok);
		p.addPoint(startx + (szerokosc/2 -1)*bok ,starty + (dlugosc+1)*bok);
		p.addPoint(startx + (szerokosc/2 -1)*bok ,starty + dlugosc*bok);
		p.addPoint(startx, starty + dlugosc*bok);
		p.addPoint(startx,starty);
		g2d.setPaint(Color.BLACK);
		g2d.draw(p);
		g2d.setStroke(new BasicStroke(1));
		g2d.setPaint(Color.GRAY);
		for(int i = 1; i <= szerokosc-1; ++ i)
			g2d.drawLine(startx + i*bok, starty, startx + i*bok, starty + dlugosc*bok);
		g2d.drawLine(startx + szerokosc/2*bok, starty - bok, startx + szerokosc/2*bok, starty + dlugosc*bok + bok);
		for(int i = 1; i <= dlugosc - 1; ++ i)
			g2d.drawLine(startx, starty + i*bok, startx + szerokosc*bok, starty + i*bok);
		g2d.drawLine(startx + (szerokosc/2 -1)*bok, starty, startx + (szerokosc/2 +1)*bok, starty);
		g2d.drawLine(startx + (szerokosc/2 -1)*bok, starty + bok*dlugosc, startx + (szerokosc/2 +1)*bok, starty + bok * dlugosc);
		last[0] = szerokosc/2; last[1] = dlugosc/2+1; last[2] = startx + szerokosc/2*bok; last[3] =  starty + dlugosc/2*bok;
		for(int i = 0; i <= 3; ++ i) prev[i] = last[i];
	}
	public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

	public void mouseClicked(MouseEvent e) 
	{
		int x = e.getX();
		int y = e.getY();
		int nstarty = starty - bok;
		if(!sprawdz(x,y))
		{
			w.pokazKomunikat("<html>Złe kliknięcie.<br> Teraz tura gracza " + tura.nazwa + "</html>");
			return;
		}
		int d = 10000000;
		int minx=0;
		int miny=0;
		int X[] = new int[2];
		int Y[] = new int[2];
		X[0] = x - ((x-startx)% bok);
		X[1] = X[0] + bok;
		Y[0] = y-((y-nstarty)%bok);
		Y[1] = Y[0] + bok;
		for(int i=0;i<=1;i++)
			for(int j=0;j<=1;++j)
			{
				if(dist(x,y,X[i],Y[j]) < d)
				{
					minx = X[i];
					miny = Y[j];
					d = dist(x,y,X[i],Y[j]);
				}
			}
		int nx = (minx-startx)/bok;
		int ny = (miny-nstarty)/bok;
		int where = plansza.kierunek(last[0],last[1],nx,ny);
		if( (dist(last[2],last[3],minx,miny) < 2*bok*bok+1) && where != 0 && plansza.checkruchy(last[0],last[1],where)  )
		{
			Shape line = new Line2D.Double(last[2],last[3],minx,miny);
			plansza.editruchy(last[0],last[1],where,nx,ny);
			pretura = tura;
			if(!plansza.checkodwiedzone(nx,ny))
			{
					plansza.editodwiedzone(nx,ny);
					if(tura == gracze[0]) tura = gracze[1];
					else tura = gracze[0];
			}
			nrgracza.add(pretura);
			shapes.add(line);
			cofanie = true;
			w.pokazKomunikat("Teraz tura gracza  " + tura.nazwa);
			if((ny == 0 || ny == dlugosc+2) && (nx == szerokosc/2 || nx == (szerokosc/2 + 1) || nx ==(szerokosc/2 - 1)))
			{
				removeMouseListener(this);
				if( ny == 0 ) w.zwyciestwo(gracze[0],gracze[1]);
				else w.zwyciestwo(gracze[1],gracze[0]);
			}
			else if(plansza.nic(nx,ny))
			{
				removeMouseListener(this);
				if(pretura == gracze[0])
					w.porazka(gracze[0],gracze[1]);
				else w.porazka(gracze[1],gracze[0]);
			}
		}
		else w.pokazKomunikat("<html>Złe kliknięcie.<br> Teraz tura gracza " + tura.nazwa + "</html>");
		repaint();
	}
	private boolean sprawdz(int x, int y) 
	{
		if(x < startx || x > startx + szerokosc*bok + bok) return false;
		if(y < starty - bok || y > starty + dlugosc * bok + bok) return false;
		return true;
	}

	private int dist(int x1,int y1,int x2,int y2)
	{
		return (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2);
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

	public void cofnijruch() 
	{
		int x1 = prev[0];
		int y1 = prev[1];
		int x2 = last[0];
		int y2 = last[1];
		if( !cofanie )
			w.pokazKomunikat("<html>Nie możesz teraz cofnąć ruchu.<br> Teraz tura gracza " + tura.nazwa + "</html>");
		else
		{
			plansza.cofnijruch(x1,y1,x2,y2);
			tura = pretura;
			shapes.remove(shapes.size()-1);
			nrgracza.remove(nrgracza.size()-1);
			cofanie = false;
			repaint();
		}
	}
}
