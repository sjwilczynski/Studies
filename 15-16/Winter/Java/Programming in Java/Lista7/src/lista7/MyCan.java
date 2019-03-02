/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista7;

import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author stachu
 */
public class MyCan extends Canvas
{
    public Color kolor = Color.BLACK;
    
    @Override
    public void paint(Graphics g)
    {
        for(int i = 1;i <= Kreska.L.rozmiar(); i++ )
        {
            Kreska k = Kreska.L.get(i);
            g.setColor(k.kolor);
            g.drawLine(k.poczatek.x, k.poczatek.y, k.koniec.x, k.koniec.y);
        }
    }
    public MyCan()
    {
        super();
        this.addKeyListener(keyList);
        this.addMouseListener(mouseList);
        this.addMouseMotionListener(mouseList2);
    }
    private MouseListener mouseList = new MouseAdapter()
    {
        @Override
        public void mousePressed (MouseEvent ev)
        {
            Kreska.p = new Point(ev.getX(),ev.getY());
        }
        @Override
        public void mouseReleased(MouseEvent e) 
        {
            Graphics gr = MyCan.this.getGraphics();
            gr.setColor(kolor);
            gr.drawLine(Kreska.p.x, Kreska.p.y, e.getX(), e.getY());
            Kreska.L.wstaw(new Kreska(Kreska.p, new Point( e.getX(), e.getY() ), kolor));
        }
       
    };
    private MouseMotionListener mouseList2 = new MouseAdapter()
    {
        @Override
        public void mouseDragged(MouseEvent e) 
        {
            Graphics gr = MyCan.this.getGraphics();
            gr.setColor(Color.BLACK);
            MyCan.this.repaint();
            gr.drawLine(Kreska.p.x, Kreska.p.y, e.getX(), e.getY());
        }
    };
    
    private KeyListener keyList = new KeyAdapter()
    {
        @Override
        public void keyPressed (KeyEvent ev)
        {
            switch (ev.getKeyCode())
            {
            case KeyEvent.VK_BACK_SPACE: // klawisz 'backspace'
                Kreska.L.clear();
                MyCan.this.repaint();
                break;
            case KeyEvent.VK_L: // klawisz 'L'
                Kreska.L.usunost();
                MyCan.this.repaint();
                break;
            case KeyEvent.VK_F: // klawisz 'F'
                Kreska.L.usunp();
                MyCan.this.repaint();
                break;
            }
        }
    };
    
}
