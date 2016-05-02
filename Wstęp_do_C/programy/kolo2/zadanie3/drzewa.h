#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>

typedef struct wezel{
    int x;
    struct wezel *lewy;
    struct wezel *prawy;
}wezel;
typedef wezel* drzewo;


drzewo init();
bool takiesame(drzewo t1, drzewo t2);
void wypiszsciezki(drzewo t,int tab[],int n);

