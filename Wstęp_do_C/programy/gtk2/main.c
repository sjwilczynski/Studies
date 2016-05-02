#include <gtk/gtk.h>

int main(int argc,char *argv[])
{
    gtk_init (&argc, &argv);

    GtkWidget *window=gtk_window_new(GTK_WINDOW_TOPLEVEL);

    gtk_window_set_title(GTK_WINDOW(window),"Zaczynamy z GTK+ 3.0");
    gtk_window_set_position(GTK_WINDOW(window),GTK_WIN_POS_CENTER);
    gtk_container_set_border_width(GTK_CONTAINER(window), 30);

    g_signal_connect(G_OBJECT(window),"destroy",G_CALLBACK(gtk_main_quit),NULL);

    GtkWidget *button=gtk_button_new_with_label("Witamy w świecie interfejsów GTK+");
    gtk_container_add(GTK_CONTAINER(window), button);
    gdk_cairo_region ();
    gtk_widget_show(button);

    gtk_widget_show(window);

    gtk_main ();

    return 0;
}
