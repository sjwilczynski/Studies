public class Boisko
{
	private int dlugosc;
	private int szerokosc;
	private boolean[][] odwiedzone;
	private boolean[][][] ruchy;
	protected Boisko(Gra game)
	{
		dlugosc = game.dlugosc;
		szerokosc = game.szerokosc;
		inicjuj();
	}
	private void inicjuj() 
	{
		ruchy = new boolean[szerokosc+3][dlugosc+4][10];
		odwiedzone = new boolean [szerokosc+3][dlugosc+5];
		
		ruchy[0][1][3]=true;
        odwiedzone[0][1]=true; //lewy gorny rog
        for(int i=2; i<=dlugosc; i++)
        {
            odwiedzone[0][i]=true;
            ruchy[0][i][9]=true;
            ruchy[0][i][6]=true;
            ruchy[0][i][3]=true;
        }                           //lewa krawedz
        ruchy[szerokosc][1][1]=true;
        odwiedzone[szerokosc][1]=true;   //prawy gorny rog
        for(int i=2; i<=dlugosc; i++)
        {
            odwiedzone[szerokosc][i]=true;
            ruchy[szerokosc][i][7]=true;
            ruchy[szerokosc][i][4]=true;
            ruchy[szerokosc][i][1]=true;
        }                          //prawa krawedz
        for(int i=1; i<=szerokosc-1; i++)
        {
            if(i!=szerokosc/2)odwiedzone[i][1]=true;
            ruchy[i][1][1]=true;
            ruchy[i][1][2]=true;
            ruchy[i][1][3]=true;
        }
        ruchy[szerokosc/2-1][1][9]=true;
        ruchy[szerokosc/2+1][1][7]=true;
        ruchy[szerokosc/2-1][1][6]=true;
        ruchy[szerokosc/2+1][1][4]=true;
        ruchy[szerokosc/2][1][4]=true;
        ruchy[szerokosc/2][1][6]=true;
        ruchy[szerokosc/2][1][7]=true;
        ruchy[szerokosc/2][1][8]=true;
        ruchy[szerokosc/2][1][9]=true;  //gorna krawedz
        for(int i=1; i<=szerokosc-1; i++)
        {
            if(i!=szerokosc/2)odwiedzone[i][dlugosc+1]=true;
            ruchy[i][dlugosc+1][7]=true;
            ruchy[i][dlugosc+1][8]=true;
            ruchy[i][dlugosc+1][9]=true;
        }
        ruchy[szerokosc/2-1][dlugosc+1][6]=true;
        ruchy[szerokosc/2+1][dlugosc+1][4]=true;
        ruchy[szerokosc/2-1][dlugosc+1][3]=true;
        ruchy[szerokosc/2+1][dlugosc+1][1]=true;
        ruchy[szerokosc/2][dlugosc+1][4]=true;
        ruchy[szerokosc/2][dlugosc+1][6]=true;
        ruchy[szerokosc/2][dlugosc+1][1]=true;
        ruchy[szerokosc/2][dlugosc+1][2]=true;
        ruchy[szerokosc/2][dlugosc+1][3]=true; //dolna krawedz
        ruchy[0][dlugosc+1][9]=true;
        odwiedzone[0][dlugosc+1]=true; //lewy dolny rog
        ruchy[szerokosc][dlugosc+1][7]=true;
        odwiedzone[szerokosc][dlugosc+1]=true; //prawy gorny rog
        
        odwiedzone[szerokosc/2][dlugosc/2+1] = true;
        for(int i=2; i<=dlugosc; i++)
        {
            for(int j=1; j<=szerokosc-1; j++)
            {
                for(int k=1; k<=9; k++)ruchy[j][i][k]=true;
            }
        }                           //cala reszta		
	}
	protected void cofnijruch(int x1,int y1,int x2,int y2) 
	{
		ruchy[x1][y1][kierunek(x1, y1, x2, y2)] = true;
		ruchy[x2][y2][przeciwny(kierunek(x1, y1, x2, y2))] = true;
		odwiedzone[x2][y2] = false;
		for(int i =1 ;i <= 9 ; ++i)
		{
			if(ruchy[x2][y2][i] == false)
			{
				odwiedzone[x2][y2] = true;
			}
		}
		
	}
	protected int kierunek(int x1,int y1,int x2,int y2)
	{
		if(x2 > x1 && y2 > y1) return 3;
		if(x2 > x1 && y2 == y1) return 6;
		if(x2 > x1 && y2 < y1) return 9;
		if(x2 == x1 && y2 > y1) return 2;
		if(x2 == x1 && y2 == y1) return 0;
		if(x2 == x1 && y2 < y1) return 8;
		if(x2 < x1 && y2 > y1) return 1;
		if(x2 < x1 && y2 == y1) return 4;
		return 7;
		
	}
	protected int przeciwny(int k)
	{
		if( k == 1 ) return 9;
		if( k == 2 ) return 8;
		if( k == 3 ) return 7;
		if( k == 4 ) return 6;
		if( k == 6 ) return 4;
		if( k == 7 ) return 3;
		if( k == 8 ) return 2;
		return 1;
	}
	protected boolean checkruchy(Integer x1, Integer y1, int where) 
	{
		return ruchy[x1][y1][where];
	}
	protected boolean checkodwiedzone(int nx, int ny) 
	{
		return odwiedzone[nx][ny];
	}
	protected void editruchy(Integer x1, Integer y1, int where,int nx, int ny) 
	{
		ruchy[x1][y1][where] = false;
		ruchy[nx][ny][przeciwny(where)] = false;	
	}
	protected void editodwiedzone(int nx, int ny) 
	{
		odwiedzone[nx][ny] = true;
	}
	public boolean nic(int nx, int ny) 
	{
		for(int i = 1; i < 10 ; ++ i)
		{
			if(ruchy[nx][ny][i])return false;
		}
		return true;
	}
}
