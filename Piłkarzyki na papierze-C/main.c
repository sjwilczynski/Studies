//Stanisław Wilczyński projekt końcowy 272955 1.02.2015
#include"pilka.h"
GtkWidget *cos1;
int p1=1;
int q1=2;

void test( GtkWidget *widget,gpointer data)
{
    int *x = (int* )data;
    if(*x==1)
    {
        moj_id = "A";
        twoj_id = "B";
        if ((potoki=initPipes()) == NULL) exit(666);
        gtk_widget_destroy(cos1);
        otworz_menu();
    }
    else
    {
        moj_id = "B";
        twoj_id = "A";
        if ((potoki=initPipes()) == NULL) exit(666);
        gtk_widget_destroy(cos1);
        poczatek();
    }
}

int main(int argc,char *argv[])
{

    gtk_init (&argc, &argv);
    cos1 = gtk_window_new(GTK_WINDOW_TOPLEVEL);
    gtk_window_set_title(GTK_WINDOW(cos1),"WYBIERZ GRACZA");
    GtkWidget *b1 = gtk_button_new_with_label("A");
    g_signal_connect(G_OBJECT(b1), "clicked",G_CALLBACK(test),&p1);
    GtkWidget *b2 = gtk_button_new_with_label("B");
    g_signal_connect(G_OBJECT(b2), "clicked",G_CALLBACK(test),&q1);
    GtkWidget *PP = gtk_box_new(GTK_ORIENTATION_HORIZONTAL, 0);
    gtk_widget_set_size_request(cos1,500,400);
    gtk_widget_set_size_request(b1,100,100);
    gtk_widget_set_size_request(b2,100,100);
    gtk_box_pack_start(GTK_BOX(PP),b1,true,true,0);
    gtk_box_pack_start(GTK_BOX(PP),b2,true,true,0);
    gtk_container_add(GTK_CONTAINER(cos1),PP);
    gtk_window_set_position(GTK_WINDOW(cos1), GTK_WIN_POS_CENTER);
    gtk_widget_show_all(cos1);
    gtk_main ();
    return 0;
}










