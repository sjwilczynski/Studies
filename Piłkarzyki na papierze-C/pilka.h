//Stanisław Wilczyński projekt końcowy 272955 1.02.2015
#include<cairo.h>
#include "wfifo.h"

void otworz_menu();
void nowa_gra();
void ustawienia();
void instrukcja();
void rysuj(cairo_t *cr,int a, int b,int x,int y,int bok); // rysuje boisko i inicjuje tablice ruchy
bool tura(cairo_t *cr); // ruch gracza
void zwyciestwo1();  //wywoływana na koniec gry
void porazka();      //wywoływana na koniec gry
void poczatek();      // zamiast otworzmenu() dla garcza B


