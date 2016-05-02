/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista7;

import java.awt.*;
import java.awt.event.*;

class MyFrame extends Frame
{
    private WindowListener frameList = new WindowAdapter()
    {
        @Override
        public void windowClosing (WindowEvent ev)
        {
            MyFrame.this.dispose();
        }
    };
    private List kolory = new List(5,false);
    private MyCan can = new MyCan();

    private ItemListener mL = new ItemListener()
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            switch (e.getItem().toString())
            {
            case "0":
                can.kolor = Color.BLACK;
                break;
            case "1":
                can.kolor = Color.RED;
                break;
            case "2": 
                can.kolor = Color.GREEN;
                break;
            case "3": 
                can.kolor = Color.YELLOW;
                break;
            case "4":
                can.kolor = Color.BLUE;
                break;
            case "5":
                can.kolor = Color.PINK;
                break;
            }
            ;
        }
        
    };
    public MyFrame()
    {
        super("kolorowe kreski");
        setSize(400,400);
        setLocation(200,200);
        Label l = new Label("Zapraszam do zabawy",Label.CENTER);
        add(l, BorderLayout.NORTH);
        add(can,BorderLayout.CENTER);
        add(kolory,BorderLayout.WEST);
        kolory.add("czarny");
        kolory.add("czerwony");
        kolory.add("zielony");
        kolory.add("żołty");
        kolory.add("niebieski");
        kolory.add("różowy");
        kolory.addItemListener(mL);
        
        can.setFocusable(true);
        can.requestFocus();
        addWindowListener(frameList);
        setVisible(true);   
    }
}
