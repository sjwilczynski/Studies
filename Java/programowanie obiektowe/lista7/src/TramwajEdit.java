import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class TramwajEdit extends PojazdEdit
{
	protected Tramwaj T;
	JLabel nr_etykieta;
	JTextField nr;
	JLabel pocz_etykieta;
	JTextField pocz;
	JLabel kon_etykieta;
	JTextField kon;
	public TramwajEdit(Tramwaj x) 
	{
		super(x);
		T = x;
		nr_etykieta =new JLabel("Numer");
		nr = new JTextField(T.numer.toString(), 40);
		pocz_etykieta = new JLabel("Początek trasy");
		pocz = new JTextField(T.poczatek, 40);
        kon_etykieta = new JLabel("Koniec");
		kon = new JTextField(T.koniec, 40);
	}
	public void start()
	{
		layout = new GridLayout(7, 2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		kontener.setLayout(layout);
		kontener.add(pro_etykieta);
		kontener.add(pro);
		kontener.add(mod_etykieta);
		kontener.add(model);
		kontener.add(wiek_etykieta);
		kontener.add(wiek);
		kontener.add(nr_etykieta);
		kontener.add(nr);
		kontener.add(pocz_etykieta);
		kontener.add(pocz);
		kontener.add(kon_etykieta);
		kontener.add(kon);
		b.addActionListener(this);
		b1.addActionListener(this);
		kontener.add(b1);
		kontener.add(b);
		frame.pack();
		frame.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == b)
		{
			//System.out.println(e.paramString());
			String w = wiek.getText();
			String pr = pro.getText();
			String m = model.getText();
			String nur = nr.getText();
			String po = pocz.getText();
			String ko = kon.getText();
			T.producent = pr;
			T.model = m;
			T.poczatek = po;
			T.koniec = ko;
			T.numer = Integer.parseInt(nur);
			T.wiek = Integer.parseInt(w);
		}
		if(e.getSource() == b1)
		{
			//System.out.println(e.paramString());
			System.out.print(T.toString());
		}
	}
}
