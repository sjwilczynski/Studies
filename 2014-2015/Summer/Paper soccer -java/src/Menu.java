import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;


public class Menu extends JFrame implements ActionListener
{
	Container kontener;
	GridLayout layout;
	JLabel[] L = new JLabel[10];
	JTextField[] T = new JTextField[10];
	JFrame j1;
	JButton b;
	JButton b1;
	JButton b2;
	JButton b3;
	JButton b4;
	JButton b5;
	Color c1 = Color.RED;
	Color c2 = Color.BLUE;
	JLabel etykieta;
	public Menu()
	{
		kontener = getContentPane();
		layout = new GridLayout(4,1,10,10);
		etykieta = new JLabel("Piłkarzyki",JLabel.CENTER);
		b1 = new JButton("Nowa gra");
		b2 = new JButton("Instrukcja");
		b3 = new JButton("Wyjście");
	}
	public void start()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		kontener.setLayout(layout);
		((JComponent) kontener).setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		kontener.add(etykieta);
		kontener.add(b1);
		kontener.add(b2);
		kontener.add(b3);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		setSize(400,400);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e) 
	{	
		if(e.getSource() == b2)
		{
			JFrame j = new JFrame("Instrukcja");
			Container k = j.getContentPane();
			((JComponent) k).setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
			JLabel tekst = new JLabel("<html><br>Aby rozpocząć rozgrywkę należy najpierw wybrać ustawienia gry. Wybiera się rozmiar planszy: dlugość, szerkość boiska, wymiar boku kratki w pikselach.<br> Gracze mogą również wybrać sobie imiona i kolory <br>Uwaga! Wybór odbywa się przez wpisanie odpoweidnich danych do tabelki lub naciskanie przycisków <br><br>Rozgrywka rozpoczyna się od narysowania specjalnego 'boiska' na ekranie.<br>Gra rozgrywa się tylko na przecięciach linii planszy. Pierwszy ruch rozpoczyna pierwszy gracz od centralnego miejsca na planszy, natomiast każdy kolejny się zaczyna w miejscu na<br>którym skończył się poprzedni. Gracze wykonują ruchy naprzemiennie, 'rysując' kolejno kreski prowadząc wirtualnie przy tym tzw. piłkę. Ruch piłki polega na 'narysowaniu'<br> kolejnejlinii długości jednej kratki (linie te łączą przecięcia kratek). Celem gry jest 'strzelenie bramki przeciwnikowi' - czyli umieszczenie piłki w bramce przeciwnika.<br><br>Wspomiane 'rysowanie' polega na naciśnięciu kursorem punktu na ekranie leżącego odpowiednio blisko przecięcia kratek, do którego chcemy się udać.<br>Piłka nie może być prowadzona bezpośrednio w miejscach,po których się wcześniej przemieszczała, oraz po dokładnym szkicu planszy. <br><br>Istnieje jednak możliwość tzw. odbijania się od szkiców: <br>Odbicie polega na przyznaniu graczowi dodatkowego ruchu. Jeśli gracz kończy ruch w miejscu, <br>przez którą przechodzi wcześniej narysowana linia (krawędź boiska lub linia wcześniejszego ruchu), następuje odbicie.<br>Ruch gracza kończy się dopiero wtedy kiedy 'piłka dojdzie na puste pole gdzie nie ma już żadnego zarysu'.<br><br>Linie graczy mają kolory odpowiednio wybrane przez nich w menu(domyślnie są to czerwony i niebieski).<br>Wygrywa gracz, który pierwszy strzeli bramkę(pierwszy gracz strzela do górnej bramki, drugi do dolnej).<br>W sytuacji kiedy gracz nie może wykonać ruchu i spowodował zablokowanie gry, przegrywa.<br><br></html>");
			k.add(tekst);
			j.pack();
			j.setVisible(true);
		}
		if(e.getSource() == b3)
		{
			dispose();
		}
		if(e.getSource() == b1)
		{
			j1 = new JFrame("Ustawienia rozgrywki");
			Container k = j1.getContentPane();
			((JComponent) k).setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
			GridLayout lay = new GridLayout(2,1,10,10);
			k.setLayout(lay);
			b = new JButton("Zapisz i rozpocznij grę");
			b.addActionListener(this);
			JPanel j2 = new JPanel();
			j2.setLayout(new GridLayout(6,2,10,10));
			L[1] = new JLabel("bok");
			L[2] = new JLabel("dlugosc");
			L[3] = new JLabel("szerkosc");
			L[4] = new JLabel("imię pierwszego gracza");
			L[5] = new JLabel("imię drugiego gracza");
			T[1] = new JTextField("40");
			T[2] = new JTextField("10");
			T[3] = new JTextField("8");
			T[4] = new JTextField("Gracz1");
			T[5] = new JTextField("Gracz2");
			j2.add(L[1]);j2.add(T[1]);
			j2.add(L[2]);j2.add(T[2]);
			j2.add(L[3]);j2.add(T[3]);
			j2.add(L[4]);j2.add(T[4]);
			j2.add(L[5]);j2.add(T[5]);
			b4 = new JButton("Wybierz kolor dla pierwszego gracza");
			b5 = new JButton("Wybierz kolor dla drugiego gracza");
			b4.addActionListener(this);
			b5.addActionListener(this);
			j2.add(b4);
			j2.add(b5);
			j1.add(j2);
			j1.add(b);
			j1.pack();
			j1.setVisible(true);	
		}
		if(e.getSource() == b)
		{
			j1.dispose();
			dispose();
			Integer i1 = Integer.parseInt(T[1].getText());
			Integer i2 = Integer.parseInt(T[2].getText());
			Integer i3 = Integer.parseInt(T[3].getText());
			Gra game = new Gra(i1,i2,i3,80,70,new Gracz(T[4].getText(),c1),new Gracz(T[5].getText(),c2));
		}
		if(e.getSource() == b4)
		{
			c1 = JColorChooser.showDialog(null, "Wybierz kolor", c1);
			if( c1 == null) c1 = Color.RED;
		}
		if(e.getSource() == b5)
		{
			c2 = JColorChooser.showDialog(null, "Wybierz kolor", c1);
			if( c2 == null ) c2 = Color.RED;
		}
	}
}
