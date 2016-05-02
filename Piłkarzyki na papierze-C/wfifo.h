//Stanisław Wilczyński projekt końcowy 272955 1.02.2015
//pliki wfifo.c i wfifo.h to pliki z programu gtktalk z małymi przeróbkami
#include <stdbool.h>
#include <string.h>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <windows.h>
#include <gtk/gtk.h>


#define MAKS_DL_TEKSTU 10000

struct pipes
{
    HANDLE fifo_in, fifo_out;
    int isA;
};
typedef struct pipes *PipesPtr;
PipesPtr potoki;
char *moj_id, *twoj_id;

HANDLE openOutPipe(char *name);
HANDLE openInPipe(char *name);

PipesPtr initPipes();
void     sendStringToPipe(PipesPtr channel, const char *data);
bool     getStringFromPipe(PipesPtr channel, char *buffer, size_t size);
void     closePipes(PipesPtr channel);
void zakoncz(GtkWidget *widget, gpointer data);


