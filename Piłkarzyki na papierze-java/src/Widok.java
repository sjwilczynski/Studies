import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;


import javax.swing.*;


public class Widok extends JFrame implements ActionListener
{
	private Container kontener;
	private JPanel rest;
	private JButton button;
	private JButton b1;
	private GridLayout layout;
	private JLabel komunikat = new JLabel("Początek meczu",JLabel.CENTER);
	private final BoiskoEdit b;
	private Wyniki res;
	public Widok(Gra game)
	{
		res = new Wyniki();
		b = new BoiskoEdit(game);
		b.set(this);
		rest = new JPanel();
		rest.setLayout(new GridLayout(2,1,10,80));
		rest.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		JPanel j = new JPanel();
		layout = new GridLayout(1, 2);
		setSize(2*(game.startx + (game.szerokosc+2)*game.bok),game.starty + (game.dlugosc+3)*game.bok );
		setTitle("Piłkarzyki");
		button = new JButton("Cofnij");
		b1 = new JButton("Wyniki");
		button.setPreferredSize(new Dimension(100,100));
		b1.setPreferredSize(new Dimension(100,100));
		button.addActionListener(this);
		b1.addActionListener(this);
		kontener = getContentPane();
		kontener.setLayout(layout);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		kontener.add(b);
		rest.add(komunikat);
		j.add(button);
		j.add(b1);
		rest.add(j);
		kontener.add(rest);
	}
	
	public void pokazKomunikat(String s)
	{
		komunikat.setText(s);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == button) b.cofnijruch();
		else res.pokazWyniki();
	}

	public void zwyciestwo(Gracz g1, Gracz g2) 
	{
		res.dodajWynik(g1,g2);
		button.removeActionListener(this);
		this.setBackground(g1.kolor);
		button.setBackground(Color.WHITE);
		pokazKomunikat("Zwycieżył gracz " + g1.nazwa);
	}
	public void porazka(Gracz g1, Gracz g2)
	{
		res.dodajWynik(g2,g1);
		button.removeActionListener(this);
		this.setBackground(Color.BLACK);
		pokazKomunikat("Zablokowany. Przegrał gracz " + g1.nazwa);
	}
}
