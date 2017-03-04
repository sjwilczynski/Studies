#include <iostream>
#include <vector>
#include <cstdio>

using namespace std;

int main()
{

    int T;
    char c;
    scanf("%d",&T);
    //printf("%d\n",T);
    scanf("%c",&c);

    for( int tt = 0; tt < T; tt++ )
    {
        int N = 1000000;
        char znaki[N+5];
        vector<int> tab;
        int sumaKontrolna = 0;
        fgets(znaki, N+2, stdin);
        int rozmiar = 0;
        c = znaki[0];
        bool roznica[10][2];
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 2; j++)
                roznica[i][j] = false;


        while( !isspace( c ) )
        {
            tab.push_back( ( int )c - 48 );

            if( rozmiar % 2 == 1 )
            {
                sumaKontrolna -= tab[rozmiar];
                roznica[tab[rozmiar]][1] = true;
            }
            else
            {
                sumaKontrolna += tab[rozmiar];
                roznica[tab[rozmiar]][0] = true;

            }

            rozmiar++;
            c = znaki[rozmiar];
        }


        vector<int> wartosci;
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                if(roznica[i][0] && roznica[j][1])
                    wartosci.push_back(2*(i-j));
                if(roznica[i][1] && roznica[j][0])
                    wartosci.push_back(2*(j-i));
            }
        }

        bool res = false;
        if(sumaKontrolna == 0) res = true;
        for(unsigned int i = 0; i < wartosci.size(); i++){
            //printf("Wartosci dla: %d %d %d %d\n",i,roznica[i][0], roznica[i][1], wartosci[i]);
            if(sumaKontrolna == wartosci[i])res = true;
        }
        if(res) printf("TAK\n");
        else printf("NIE\n");
    }
    return 0;
}
