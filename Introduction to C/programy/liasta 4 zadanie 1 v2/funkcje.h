//Stanis³aw Wilczyñski 272955 Lista 4 zadanie 1 7.11.2014
#include<stdio.h>

#define MAX_SET_SIZE 10001

typedef int ELEMENT;
typedef int ZBIOR[MAX_SET_SIZE];


void wczytaj(ZBIOR z1);
void wypisz(ZBIOR z1);
void wyczysc(ZBIOR z1);
void suma(const ZBIOR z1, const ZBIOR z2, ZBIOR wynik);
void przekroj(const ZBIOR z1, const ZBIOR z2, ZBIOR wynik);
void dodaj_e(ZBIOR z1, ELEMENT e);
void usun_e(ZBIOR z1, ELEMENT e);


