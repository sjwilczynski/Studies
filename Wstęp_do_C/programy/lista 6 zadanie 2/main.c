//Stanislaw Wilczynski lista 6 zadanie 2 272955 22.11.2014
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>
#include "sortowanie.h"

void scalanie(int *tab, int *tablica, int pocz, int kon)
{
    for (int i = pocz; i <= kon; i++)
    {
        tab[i] = tablica[i];
    }
    int p = pocz;
    int q = (pocz + kon) / 2 + 1;
    int r = pocz;
    while (p <= (pocz + kon) / 2 && q <= kon)
    {
        if (tab[p] < tab[q])
        {
            tablica[r] = tab[p];
            r++;
            p++;
        }
        else
        {
            tablica[r] = tab[q];
            r++;
            q++;
        }
    }
    while (p <= (pocz + kon) / 2)
    {
        tablica[r] = tab[p];
        r++;
        p++;
    }
}
void sort(int *tab, int *tablica,int pocz, int kon)
{
    if (pocz < kon)
    {
        sort(tab, tablica,pocz, (pocz + kon) / 2);
        sort(tab, tablica,(pocz + kon) / 2 + 1, kon);
        scalanie(tab, tablica,pocz, kon);
    }
}
void merge_sort(void *tab,int dane, int bajty,int (*compare)(const void *, const void *))
{
    int drugat[dane];
    sort(drugat,tab,0,dane-1);
}

int main()
{
    struct wynik wqsort;
    struct wynik wmergesort;
    testuj(100, 5000, &wqsort, qsort);
    printf("Dla 100 testow rozmiaru 5000:\n");
    printf("czas dla algorytmu qsort:\n\n");
    printf("minimalny %f sekund\n",wqsort.mini/CLOCKS_PER_SEC);
    printf("maksymalny %f sekund\n",wqsort.maxi/CLOCKS_PER_SEC);
    printf("sredni %f sekund\n\n",wqsort.sred/CLOCKS_PER_SEC);

    testuj(100, 5000, &wmergesort, merge_sort);
    printf("Dla 100 testow rozmiaru 5000:\n");
    printf("czas dla algorytmu merge_sort:\n\n");
    printf("minimalny %f sekund\n",wmergesort.mini/CLOCKS_PER_SEC);
    printf("maksymalny %f sekund\n",wmergesort.maxi/CLOCKS_PER_SEC);
    printf("sredni %f sekund\n\n",wmergesort.sred/CLOCKS_PER_SEC);

    testuj(100, 10000, &wqsort, qsort);
    printf("Dla 100 testow rozmiaru 10000:\n");
    printf("czas dla algorytmu qsort:\n\n");
    printf("minimalny %f sekund\n",wqsort.mini/CLOCKS_PER_SEC);
    printf("maksymalny %f sekund\n",wqsort.maxi/CLOCKS_PER_SEC);
    printf("sredni %f sekund\n\n",wqsort.sred/CLOCKS_PER_SEC);

    testuj(100, 10000, &wmergesort, merge_sort);
    printf("Dla 100 testow rozmiaru 10000:\n");
    printf("czas dla algorytmu merge_sort:\n\n");
    printf("minimalny %f sekund\n",wmergesort.mini/CLOCKS_PER_SEC);
    printf("maksymalny %f sekund\n",wmergesort.maxi/CLOCKS_PER_SEC);
    printf("sredni %f sekund\n\n",wmergesort.sred/CLOCKS_PER_SEC);

    testuj(100, 50000, &wqsort, qsort);
    printf("Dla 100 testow rozmiaru 50000:\n");
    printf("czas dla algorytmu qsort:\n\n");
    printf("minimalny %f sekund\n",wqsort.mini/CLOCKS_PER_SEC);
    printf("maksymalny %f sekund\n",wqsort.maxi/CLOCKS_PER_SEC);
    printf("sredni %f sekund\n\n",wqsort.sred/CLOCKS_PER_SEC);

    testuj(100, 50000, &wmergesort, merge_sort);
    printf("Dla 100 testow rozmiaru 50000:\n");
    printf("czas dla algorytmu merge_sort:\n\n");
    printf("minimalny %f sekund\n",wmergesort.mini/CLOCKS_PER_SEC);
    printf("maksymalny %f sekund\n",wmergesort.maxi/CLOCKS_PER_SEC);
    printf("sredni %f sekund\n\n",wmergesort.sred/CLOCKS_PER_SEC);

    return 0;
}
