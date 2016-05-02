#include"drzewa.h"


drzewo init()
{
    return NULL;
}
bool takiesame(drzewo t1, drzewo t2)
{
    if((t1==NULL && t2!=NULL) || (t1!=NULL && t2==NULL))return false;
    else
    {
            if(t1==NULL && t2==NULL)return true;
            if(t1->x!=t2->x)return false;
            else return(takiesame(t1->prawy,t2->prawy) && takiesame(t1->lewy,t2->lewy));
    }
}
void wypiszsciezki(drzewo t,int tab[],int n)
{
    tab[n]=t->x;
    if(t->lewy && t->prawy==NULL)
    {
        for(int i=0;i<=n;i++)printf("%d ",tab[i]);
        printf("\n");
    }
    else
    {
        if(t->lewy!=NULL)wypiszsciezki(t->lewy,tab,n+1);
        if(t->prawy!=NULL)wypiszsciezki(t->prawy,tab,n+1);
    }
}

