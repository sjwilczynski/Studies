#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>
#include<string.h>

typedef struct osoba{
    char imie[20];
    char nazwisko[30];
    int dzien;
    int miesiac;
    int rok;
    struct osoba *ojciec;
    struct osoba *matka;
}osoba;

osoba *przodek(osoba *ja,char *str)
{
    if(ja==NULL)return NULL;
    if(ja->matka==NULL && ja->ojciec==NULL)return NULL;
    else
    {
        if(ja->matka!=NULL || ja->ojciec!=NULL)
        {
            if(strcmp(ja->matka->nazwisko,str)==0)return ja->matka;
            if(strcmp(ja->ojciec->nazwisko,str)==0)return ja->ojciec;
            else
            {
                osoba *typ1;
                osoba *typ2;
                typ1 = przodek(ja->matka,str);
                typ2 = przodek(ja->ojciec,str);
                if(typ1!=NULL)return typ1;
                if(typ2!=NULL)return typ2;
                return NULL;
            }
        }
    }
}

int main()
{
    return 0;
}
