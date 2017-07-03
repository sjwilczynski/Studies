/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista11;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class Okno extends JFrame implements ActionListener,ListSelectionListener
{
    public Okno()
    {
        this.setTitle("Edytor plików");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setSize(400, 700);
        initComponents();      
    }
    String kodwe = "UTF8";
    String kodwy = "UTF8";
    String[] encodings = {"Cp858","Cp850","Cp855","ISO8859_2","ISO8859_1","ASCII","UTF8","Cp1250","Unicode"};
    int tab = 8;
    JList list1;
    JList list2;
    File file;
    JTextArea arena = new JTextArea("Wybierz plik do edycji");
    JScrollPane scroll = new JScrollPane(arena);
    JPanel panelN = new JPanel();
    JToolBar panelS = new JToolBar();
    Container pane = this.getContentPane();
    JFileChooser fc = new JFileChooser();
    JButton open = new JButton("Otwórz plik...");
    JButton save = new JButton("Zapisz plik...");
    JButton czysc = new JButton("Wyczyść plik");
    JButton wyb = new JButton("Wybierz strony kodowe");
    JLabel l1 = new JLabel("Atr1");
    JLabel l2  = new JLabel("Atr2");
    //JLabel l3 = new JLabel("Atr3");
    JLabel l4  = new JLabel("Atr3");
    
    
    public final void initComponents()
    {
        open.addActionListener(this);
        save.addActionListener(this);
        wyb.addActionListener(this);
        czysc.addActionListener(this);
        panelS.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        panelN.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        //scroll.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelS.setLayout(new GridLayout(2,2,3,3));
        panelS.add(open);
        panelS.add(save);
        panelS.add(wyb);
        panelS.add(czysc);
        panelN.setLayout(new GridLayout(3,1));
        panelN.add(l1);
        panelN.add(l2);
        //panelN.add(l3);
        panelN.add(l4);
        arena.setEditable(false);
        arena.setMargin(new Insets(5,5,5,5));
        pane.add(scroll,BorderLayout.CENTER);
        pane.add(panelN,BorderLayout.NORTH);
        pane.add(panelS,BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) 
    {
       if(e.getSource() == open)
       {
            int returnVal = fc.showOpenDialog(panelN);
            if (returnVal == JFileChooser.APPROVE_OPTION) 
            {
               file = fc.getSelectedFile();
               l1.setText("Nazwa: " + file.getName());
               l2.setText("Ścieżka: " + file.getPath());
               //l3.setText(String.valueOf(file.lastModified()));
               l4.setText("Długość pliku:" + String.valueOf(file.length()));
               FileInputStream fis;
               try 
               {
                   fis = new FileInputStream(file);
                   MyReader in = new MyReader(fis,kodwe,tab);
                   String line;
                   String s = "";
                   while( (line = in.readLine()) != null ) s += line + "\n";
                   arena.setText(s);
                   in.close();
               } 
               catch (FileNotFoundException ex) {} 
               catch (IOException ex) {}
            } 
            else 
            {
                arena.setText("Nieudany wybór - spróbuj jeszcze raz");
            }
            
       }
       if(e.getSource() == save)
       {
           FileInputStream fis;
           FileOutputStream fos;
           try 
           {
                fis = new FileInputStream(file);
                MyReader in = new MyReader(fis,kodwe,tab);
                fos = new FileOutputStream(file.getParent() + "/po_edycji.txt");
                MyWriter out = new MyWriter(fos,kodwy,tab);
                in.czysc(out);
                in.close();
                out.close();
            } 
            catch (FileNotFoundException ex) {ex.printStackTrace();} 
            catch (IOException ex) {ex.printStackTrace();}
       }
       if(e.getSource() == wyb)
       {
           JFrame rama = new JFrame();
           rama.setTitle("Wybierz kodowanie");
           rama.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
           rama.setSize(500, 200);
           Container p = rama.getContentPane();
           JPanel p1 = new JPanel();
           JPanel p2 = new JPanel();
           p1.setLayout(new GridLayout(1,2));
           p1.add(new JLabel("Wybierz kodowanie dla wejścia:"));
           p1.add(new JLabel("Wybierz kodowanie dla wyjścia:"));
           p2.setLayout(new GridLayout(1,2));
           DefaultListModel model = new DefaultListModel();
           for(int i = 0; i < encodings.length; i++) model.add(i, encodings[i]);
           list1 = new JList(model);
           list2 = new JList(model);
           list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
           list1.addListSelectionListener(this);
           list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
           list2.addListSelectionListener(this);
           p2.add(list1);
           p2.add(list2);
           p.add(p1,BorderLayout.NORTH);
           p.add(p2,BorderLayout.CENTER);
           rama.setVisible(true);
       }
       if(e.getSource() == czysc)
       {
           FileInputStream fis;
           FileOutputStream fos;
           try 
           {
                fis = new FileInputStream(file);
                MyReader in = new MyReader(fis,kodwe,tab);
                fos = new FileOutputStream("nplik.txt");
                MyWriter out = new MyWriter(fos,kodwy,tab);
                in.czysc(out);
                in.close();
                out.close();
                FileInputStream fis2 = new FileInputStream("nplik.txt");
                MyReader in2 = new MyReader(fis2,kodwy,tab);
                String line;
                String s = "";
                while( (line = in2.readLine()) != null ) s += line + "\n";
                arena.setText(s);
                File f = new File("nplik.txt");
                f.delete();
                in2.close();
            } 
            catch (FileNotFoundException ex) {ex.printStackTrace();} 
            catch (IOException ex) {ex.printStackTrace();}
       }
    }
    @Override
    public void valueChanged(ListSelectionEvent e) 
    {
        if(e.getSource() == list1)
            kodwe = (String)list1.getSelectedValue();
        if(e.getSource() == list2)
            kodwy = (String)list2.getSelectedValue();
    }
}

