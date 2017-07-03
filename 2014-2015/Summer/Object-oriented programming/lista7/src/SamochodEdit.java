import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class SamochodEdit extends PojazdEdit
{
	protected Samochod S;
	JLabel prz_etykieta;
	JTextField prz;
	JLabel pal_etykieta;
	JTextField pal;
	JLabel op_etykieta;
	JTextField op;
	public SamochodEdit(Samochod x) 
	{
		super(x);
		S = x;
		prz_etykieta =new JLabel("Przebieg");
		prz = new JTextField(S.przebieg.toString(), 40);
		pal_etykieta = new JLabel("Paliwo");
		pal = new JTextField(S.paliwo, 40);
        op_etykieta = new JLabel("Opony");
		op = new JTextField(S.opony, 40);
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
		kontener.add(prz_etykieta);
		kontener.add(prz);
		kontener.add(pal_etykieta);
		kontener.add(pal);
		kontener.add(op_etykieta);
		kontener.add(op);
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
			String nur = prz.getText();
			String po = pal.getText();
			String ko = op.getText();
			S.producent = pr;
			S.model = m;
			S.paliwo = po;
			S.opony = ko;
			S.przebieg = Integer.parseInt(nur);
			S.wiek = Integer.parseInt(w);
		}
		if(e.getSource() == b1)
		{
			//System.out.println(e.paramString());
			System.out.print(S.toString());
		}
	}
}
