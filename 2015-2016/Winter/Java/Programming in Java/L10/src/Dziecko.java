
public class Dziecko implements Runnable 
{
	public int x, y, state = 1;
	int nr;
	boolean running = true;
	
	public Dziecko(int nr)
	{
		this.nr = nr;
		x = (int) Math.floor(Math.random() * L11.n);
		y = (int) Math.floor(Math.random() * L11.m);
		while((MainPanel.checkVis(x,y, nr)) == false)
		{
			x = (int) Math.floor(Math.random() * L11.n);
			y = (int) Math.floor(Math.random() * L11.m);
		}
	}
	
	@Override
	public void run() 
	{
		while (running)
            try 
			{
            	System.out.println(Thread.currentThread().getName());
            	
            	if(lenMik(x, y) <=  5 && state == 1)
            	{
            		if(Math.random() > 0.3)
            		{	
            			movMik();
            			Thread.currentThread().sleep((int)(Math.random()* 1000 + 1000));
            		}
            		
            		else
            		{
            			int rand = (int) (Math.random() * 4);
    					int nx = (x + MainPanel.c1[rand] + L11.n) % L11.n;
    					int ny = (y + MainPanel.c2[rand] + L11.m) % L11.m;
    					while((MainPanel.checkVis(nx,ny, nr)) == false)
    					{
    						rand = (int) Math.floor(Math.random() * 4);
    						nx = (x + MainPanel.c1[rand] + L11.n) % L11.n;
        					ny = (y + MainPanel.c2[rand] + L11.m) % L11.m;
    					}
    					state = 2;
            			Thread.currentThread().sleep((int)(Math.random()* 5000 + 3000));
            			state = 1;
            			for(int px : MainPanel.c1)
                			for(int py : MainPanel.c2)
                				if(MainPanel.getPrez((x + px + L11.n) % L11.n, (y + py + L11.m) % L11.m) == true)
                				{
                					MainPanel.setVis(x, y, 0);
                					MainPanel.setPrez((x + px + L11.n) % L11.n, (y + py + L11.m) % L11.m, false);
                					x = (x + px + L11.n) % L11.n;
                					y = (y + py + L11.m) % L11.m;
                					state = 0;
                					running = false;
                					continue;
                				}
            		}
            	}
            	else
            		Thread.currentThread().sleep((int)(Math.random()*1000 + 3000));
            	
			}
            catch(InterruptedException ex) 
            {
                System.out.println(ex);
                return;
            }
	}

	private synchronized void movMik() 
	{
		if(lenMik(x, y) == 1)
			L11.koniecGry = true;
		boolean ruch = true;
		for(int px : MainPanel.c1)
			for(int py : MainPanel.c2)
				if(ruch == true)
				{
					int nx = (x + px + L11.n) % L11.n;
					int ny = (y + py + L11.m) % L11.m;
					if(lenMik(nx, ny) < lenMik(x, y) && MainPanel.getVis(nx, ny) == 0)
					{
						MainPanel.setVis(x, y, 0);
						x = nx;
						y = ny;
						MainPanel.setVis(nx, ny, nr);
						ruch = false;
					}
				}
	}

	private synchronized int lenMik(int x, int y) {
		return Math.min(Math.abs(x - MainPanel.getMikX()), L11.n - Math.abs(x - MainPanel.getMikX())) 
				+ Math.min(Math.abs(y - MainPanel.getMikY()), L11.m - Math.abs(y - MainPanel.getMikY()));
	}

}
