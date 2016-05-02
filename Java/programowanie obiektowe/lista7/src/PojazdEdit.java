import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PojazdEdit implements ActionListener
{
	protected Pojazd p;
	JFrame frame;
	Container kontener;
	GridLayout layout;
	JLabel pro_etykieta;
	JTextField pro;
	JLabel mod_etykieta;
	JTextField model;
	JLabel wiek_etykieta;
	JTextField wiek;
	JButton b;
	JButton b1;
	PojazdEdit(Pojazd x)
	{
		p = x;
		frame = new JFrame("Edycja pojazdu");
	    kontener = frame.getContentPane();
	    ///layout = new GridLayout(4, 2);
	    pro_etykieta =new JLabel("Producent");
		pro = new JTextField(p.producent, 40);
		mod_etykieta = new JLabel("Model");
		model = new JTextField(p.model, 40);
        wiek_etykieta = new JLabel("Wiek");
		wiek = new JTextField(p.wiek.toString(), 40);
		b = new JButton("Zapisz");
		b1 = new JButton("WYPISZ");
	}
	public void start()
	{
		layout = new GridLayout(4, 2);
		//pro_etykieta.setFocusable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		kontener.setLayout(layout);
		kontener.add(pro_etykieta);
		kontener.add(pro);
		kontener.add(mod_etykieta);
		kontener.add(model);
		kontener.add(wiek_etykieta);
		kontener.add(wiek);
		//pro_etykieta.setFocusable(true);
		b.addActionListener(this);
		b1.addActionListener(this);
		kontener.add(b1);
		kontener.add(b);
		frame.pack();
		frame.setVisible(true);
		//while(kontener.isShowing());
	}
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == b)
		{
			//System.out.println(e.paramString());
			String w = wiek.getText();
			String pr = pro.getText();
			String m = model.getText();
			p.producent = pr;
			p.model = m;
			p.wiek = Integer.parseInt(w);
		}
		if(e.getSource() == b1)
		{
			//System.out.println(e.paramString());
			System.out.print(p.toString());
		}
	}
}