//Stanislaw Wilczynski lista 10 zadanie 1 272955 25.12.2014
#include <stdio.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>

int main(int argc,char *argv[]) //argv[0] - nazwa programu [1] - sciezka pliku zapisana jako string(tablica charow) [2] - na ile czesci dzielimy [3] - czy tekstowy czy nie

// wzor programu jest mniej wiecej w programie 'kopiuj' z wykladu 9
// najpierw wersje binarna, potem wersja tekstowa a na koniec gtk
// zastanowic sie nad projektem
{
    FILE *plik;
    FILE *koniec;
    int dziel,liczba,rozmiar,bw,dod;
    bool tekstowy=false;
    char str[10000];
    char nazwa[1025];
    char rozsz[10];
    char wyjscie[800];
    char nazwakon[1025];
    if(argc<3)
    {
        fprintf(stderr,"\nZa malo parametrow", argv[0]);
        exit(1);
    }
    else
    {
        sscanf(argv[2],"%d",&dziel);
        if(argc>= 3 && argv[3][0]=='-' && argv[3][1] == 't')tekstowy = true;
        sscanf(argv[1],"%s",&nazwa);
    }
    for(int i=0; i<strlen(nazwa); i++)
    {
        //printf("%c",nazwa[i]);
        //printf("\n");     sprawdzam czy nazwa sie zgadza
    }
    char *x = strrchr(nazwa,'.');
    char *y = strrchr(nazwa,'\\');
    if(x<y)
    {
        //printf("PLIK NIE MA ROZSZERZENIA\n");
        rozsz[0]='\0';
        for(int i=0; i<strlen(nazwa); i++)wyjscie[i]=nazwa[i];
    }
    else
    {
        //printf("ROZSZEZRENIE PLIKU TO %s ",x+1);
        for(int i=0; i<strlen(nazwa)-strlen(x); i++)wyjscie[i] = nazwa[i];
        wyjscie[strlen(nazwa)-strlen(x)]='\0';
        for(int i=0; i<strlen(x); i++)rozsz[i]=x[i];
        rozsz[strlen(x)]='\0';
    }
    //for(int i=0; i<strlen(wyjscie); i++)printf("%c",wyjscie[i]);
    //for(int i=0; i<strlen(rozsz); i++)printf("%c",rozsz[i]); sprawdzam czy tak wygenerowana nazwa jest zgodna z wyjsciowa

    plik = fopen(nazwa,(tekstowy ? "r" : "rb"));
    if(tekstowy)
    {
        char c;
        liczba=0;
        while((c=getc(plik))!=EOF)
        {
            if(c=='\n')liczba++;
        }
    }
    else
    {
        fseek(plik,0,SEEK_END);
        rozmiar = ftell(plik);
    }
    rewind(plik);
    if(tekstowy)
    {
        bw = liczba/dziel;
        dod = liczba%dziel;
    }
    else
    {
        bw = rozmiar/dziel;
        dod = rozmiar%dziel;
    }
    for(int i=1; i<=dziel; i++)
    {
        if(i<10)sprintf(nazwakon,"%s-0%d%s",wyjscie,i,rozsz);
        else sprintf(nazwakon,"%s-%d%s",wyjscie,i,rozsz);
        koniec = fopen(nazwakon,(tekstowy ? "w" : "wb"));
        int ile=bw;
        if(dod>0)
        {
            ile++;
            dod--;
        }
        if(tekstowy)
        {
            for(int j=1; j<=ile; j++)
            {
                fgets(str,10000,plik);
                if(!feof(plik))fputs(str,koniec);
            }
        }
        else
        {
            for(int j=1; j<=ile; j++)
            {
                int znak = getc(plik);
                if(znak!=EOF)putc(znak,koniec);
            }
        }
        for(int i=0; i<strlen(nazwakon); i++) printf("%c",nazwakon[i]);
        printf(" ");
        printf("%d\n",ile);
        fclose(koniec);
    }
    fclose(plik);
}

