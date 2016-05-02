import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;


public class MainFrame extends JFrame 
{
	MainPanel mainPanel = new MainPanel(L11.n, L11.m, L11.x, L11.y);
	static public Dziecko dzieci[] = new Dziecko[12];
	
	
	public MainFrame() {
		this.setTitle("Mikołaj i dzieciaki");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setSize(L11.x, L11.y);
        this.setResizable(false);
        
        initComponents();
        this.setVisible(true);
	}
	
	private void initComponents() 
	{
		add(mainPanel);
		ActionListener listener = new ActionListener(){
			  public void actionPerformed(ActionEvent event){
			    repaint();
			  }
			};
			Timer displayTimer = new Timer(50, listener);
		displayTimer.start();
		for(int i = 0; i < 12; i++)
		{
			dzieci[i] = new Dziecko(i);
			new Thread(dzieci[i]).start();
		}
	}

	

}
