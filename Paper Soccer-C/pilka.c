//Stanisław Wilczyński projekt końcowy 272955 1.02.2015
#include"pilka.h"
#define M_PI 3.14159265358979323846
//deklaracje
static gboolean pobierz_tekst(gpointer data); //pobieranie tesktu z pipe

static cairo_surface_t *surface = NULL;
int l=0; // uzywane w funkcji poczatek i pobierz teskt
int p=1;
int q=2;
int r=3; // parametry dla przycisków w ustawieniach
int a=12;//długość bosika
int b=8; //szerokość boiska
int x=25; //odległość od lewej krawędzi
int y=100; //odległość od prawej krawędzi
int bok=30; // bok boiska w pikselach
GtkWidget *darea; //arena do rysowania
GtkWidget *tek;   //teskt w menu przy otwarciu
GtkWidget *opis; //pole tekstowe
GtkWidget *window;  //głowne okno w funkcji nowa gra
GtkWidget *zwyciestwo;//okno końcowe
GtkWidget *okienko;    // okno w funkcji ustawienia
GtkWidget *okno;       //okno w funkcji nowagra
int u=1;        // ktory komunikat o zwyciestwie ma sie pokazać( funkcja tura)
int qq=0;       // antyanalfabeta

bool ruchy[30][30][10]; //tablica ruchow ruchy[i][j][k] mowi czy z pola o wspólrzędnych (i,j) możliwy jest ruch w kierunku k(kierunki jak na klawiaturze numerycznej)
bool odwiedzone[30][30]; // mowi czy pole o współrzędnych (i,j) było odwiedzone

bool stan; //czyja tura

typedef struct para
{
    int x;
    int y;
} para;
typedef struct trojka
{
    int x;
    int y;
    int z;

} trojka;
trojka a1 = {-1,1,1};
trojka a2 = {0,1,2};
trojka a3 = {1,1,3};
trojka a4 = {-1,0,4};
trojka a7 = {-1,-1,7};
trojka a6 = {1,0,6};
trojka a8 = {0,-1,8};
trojka a9 = {1,-1,9}; //trojki oznaczajace kierunki
trojka przycisk[10];
para ostatni; //współrzędne ostatniego odwiedzonego punktu (w pikselach)
para wsp;     //współrzędne osatniego odwiedzonego punktu (wspołrzędne jak w tablicy ruchy)
trojka vec;   // do przechowywania nacisnietego przycisku

void clear_surface ()
{
    cairo_t *cr;
    cr = cairo_create(surface);
    cairo_set_source_rgb (cr, 1, 1, 1); //ustawia na biało
    cairo_paint (cr);
    rysuj(cr,a,b,x,y,bok);
    cairo_destroy (cr);
}
static gboolean
configure_event_cb (GtkWidget *widget, GdkEventConfigure *event, gpointer data)
{
    if (surface!=NULL)return true;
    surface = gdk_window_create_similar_surface (gtk_widget_get_window (widget),
              CAIRO_CONTENT_COLOR,
              gtk_widget_get_allocated_width (widget),
              gtk_widget_get_allocated_height (widget));

    clear_surface ();
    return TRUE;
}
// 2 funkcje inicjujące powierzchnie do rysowania

static void test_nacisniecia( GtkWidget *widget,gpointer data)
{
    int *x;
    x = (int *)data;
    if(*x==1) nowa_gra();
    if(*x==2) instrukcja();
    if(*x==3) ustawienia();
}
void zwyciestwo1()
{
    zwyciestwo = gtk_window_new(GTK_WINDOW_TOPLEVEL);
    char wiersz[60];
    sprintf(wiersz,"\n\n\n\nZwycieżyłeś\n\nPrzegrał gracz %s\n\n\n\n",twoj_id);
    gtk_window_set_title(GTK_WINDOW(zwyciestwo),"KONIEC GRY");
    GtkWidget *komunikat;
    komunikat = gtk_label_new(wiersz);
    gtk_label_set_justify(komunikat,GTK_JUSTIFY_CENTER);
    gtk_container_add(GTK_CONTAINER(zwyciestwo), komunikat);
    gtk_widget_set_size_request(zwyciestwo,500,400);
    gtk_window_set_position(GTK_WINDOW(zwyciestwo), GTK_WIN_POS_CENTER);
    gtk_label_set_text(GTK_LABEL(opis),"KONIEC GRY");
    if(moj_id[0]=='A')gtk_window_move(GTK_WINDOW(zwyciestwo),40,100);
    else gtk_window_move(GTK_WINDOW(zwyciestwo),640,100);
    g_signal_connect(GTK_WINDOW(zwyciestwo), "destroy",G_CALLBACK(zakoncz), NULL);
    gtk_widget_show_all(zwyciestwo);
}
void porazka()
{
    zwyciestwo = gtk_window_new(GTK_WINDOW_TOPLEVEL);
    gtk_window_set_title(GTK_WINDOW(zwyciestwo),"KONIEC GRY");
    char wiersz[60];
    sprintf(wiersz,"\n\n\n\nPrzegrałeś\n\nZwyciężył gracz %s\n\n\n\n",twoj_id);
    GtkWidget *komunikat;
    komunikat = gtk_label_new(wiersz);
    gtk_label_set_justify(komunikat,GTK_JUSTIFY_CENTER);
    gtk_container_add(GTK_CONTAINER(zwyciestwo), komunikat);
    gtk_widget_set_size_request(zwyciestwo,500,400);
    gtk_window_set_position(GTK_WINDOW(zwyciestwo), GTK_WIN_POS_CENTER);
    gtk_label_set_text(GTK_LABEL(opis),"KONIEC GRY");
    if(moj_id[0]=='A')gtk_window_move(GTK_WINDOW(zwyciestwo),40,100);
    else gtk_window_move(GTK_WINDOW(zwyciestwo),640,100);
    g_signal_connect(GTK_WINDOW(zwyciestwo), "destroy",G_CALLBACK(zakoncz), NULL);
    gtk_widget_show_all(zwyciestwo);
}
void otworz_menu()
{
    okno=gtk_window_new(GTK_WINDOW_TOPLEVEL);
    gtk_window_set_default_size(GTK_WINDOW(okno),400,300);
    gtk_window_set_position(GTK_WINDOW(okno), GTK_WIN_POS_CENTER);
    gtk_window_set_title(GTK_WINDOW(okno),"Piłkarzyki na papierze");
    gtk_window_set_position(GTK_WINDOW(okno),GTK_WIN_POS_CENTER);
    gtk_container_set_border_width (GTK_CONTAINER(okno), 5);
    GtkWidget *box1 = gtk_box_new(GTK_ORIENTATION_VERTICAL, 0);
    GtkWidget *nowa = gtk_button_new_with_label("NOWA GRA");
    g_signal_connect(G_OBJECT(nowa), "clicked",G_CALLBACK(test_nacisniecia),(gpointer)&p);
    GtkWidget *instrukcja = gtk_button_new_with_label("INSTRUKCJA");
    g_signal_connect(G_OBJECT(instrukcja), "clicked",G_CALLBACK(test_nacisniecia),(gpointer)&q);
    GtkWidget *ustawienia = gtk_button_new_with_label("USTAWIENIA");
    g_signal_connect(G_OBJECT(ustawienia), "clicked",G_CALLBACK(test_nacisniecia),(gpointer)&r);
    GtkWidget *wyjscie = gtk_button_new_with_label("WYJŚCIE");
    g_signal_connect(G_OBJECT(wyjscie), "clicked",G_CALLBACK(zakoncz),NULL);
    tek = gtk_label_new("stworzone przez\nStanisław Wilczyński\n\nWYBIERZ RODZAJ ROZGRYWKI");
    gtk_label_set_justify(tek,GTK_JUSTIFY_CENTER);
    gtk_box_pack_start(GTK_BOX(box1), tek, true, true, 0);
    gtk_box_pack_start(GTK_BOX(box1), nowa, true, true, 0);
    gtk_box_pack_start(GTK_BOX(box1), ustawienia, true, true, 0);
    gtk_box_pack_start(GTK_BOX(box1), instrukcja, true, true, 0);
    gtk_box_pack_start(GTK_BOX(box1), wyjscie, true, true, 0);
    gtk_container_add(GTK_CONTAINER(okno),box1);
    gtk_widget_show_all(okno);
}
bool tura(cairo_t *cr)
{
    char wiersz[120];
    if(!stan)
    {
        sprintf(wiersz,"KOMUNIKATY:\nBŁĄD\nteraz ruch przeciwnika\n\n");
        gtk_label_set_text(GTK_LABEL(opis),wiersz);
        return false;
    }
    char c[5];
    c[0]=vec.z+'0'; // numer przycisku wysłany do pipe
    c[1]='\0';
    cairo_move_to(cr,ostatni.x,ostatni.y);
    if(ruchy[wsp.x][wsp.y][vec.z])
    {
        sendStringToPipe(potoki, c);
        if(moj_id[0]=='A')cairo_set_source_rgb(cr,0,0,1);
        else cairo_set_source_rgb(cr,1,0,0);
        cairo_rel_line_to(cr,vec.x*bok,vec.y*bok);
        ruchy[wsp.x][wsp.y][vec.z]=false;
        ostatni.x += vec.x*bok;
        ostatni.y += vec.y*bok;
        wsp.x += vec.x;
        wsp.y += vec.y;

        if(wsp.y == 0 && (wsp.x==b/2 || wsp.x == b/2+1 || wsp.x == b/2-1))
        {
            if(moj_id[0]=='A')zwyciestwo1();
            else porazka();
            u=2;
        }
        if(wsp.y == a+2 && (wsp.x==b/2 || wsp.x == b/2+1 || wsp.x == b/2-1))
        {
            if(moj_id[0]=='B')zwyciestwo1();
            else porazka();
            u=2;
        }
        if(!odwiedzone[wsp.x][wsp.y])stan = !stan;

        odwiedzone[wsp.x][wsp.y]=true;
        if(vec.z==1)ruchy[wsp.x][wsp.y][9]=false;
        if(vec.z==2)ruchy[wsp.x][wsp.y][8]=false;
        if(vec.z==3)ruchy[wsp.x][wsp.y][7]=false;
        if(vec.z==9)ruchy[wsp.x][wsp.y][1]=false;
        if(vec.z==8)ruchy[wsp.x][wsp.y][2]=false;
        if(vec.z==7)ruchy[wsp.x][wsp.y][3]=false;
        if(vec.z==4)ruchy[wsp.x][wsp.y][6]=false;
        if(vec.z==6)ruchy[wsp.x][wsp.y][4]=false; //bo po narysowanej lini nie można wrócić
        if(stan)sprintf(wiersz,"KOMUNIKATY:\nWSZYSTKO OK\nteraz twój ruch\n\n");
        else sprintf(wiersz,"KOMUNIKATY:\nWSZYSTKO OK\nteraz ruch przeciwnika\n\n");
        gtk_label_set_text(GTK_LABEL(opis),wiersz);
    }
    else
    {
        sprintf(wiersz,"KOMUNIKATY:\nBLĘDNY RUCH\nteraz twój ruch\n\n");
        gtk_label_set_text(GTK_LABEL(opis),wiersz);
    }

    if(u==1 && !ruchy[wsp.x][wsp.y][1]  && !ruchy[wsp.x][wsp.y][2]  && !ruchy[wsp.x][wsp.y][3]  && !ruchy[wsp.x][wsp.y][4] && !ruchy[wsp.x][wsp.y][6]  && !ruchy[wsp.x][wsp.y][7]  && !ruchy[wsp.x][wsp.y][8]  && !ruchy[wsp.x][wsp.y][9])
    {
        porazka(); //jeśli gracz się zablokował
    }
    cairo_stroke(cr);
    return true;
}

static void test_nacisniecia1( GtkWidget *widget,gpointer data) //przycisk ruchu
{
    trojka *p;
    p = (trojka *)data;
    vec.x=p->x;
    vec.y=p->y;
    vec.z=p->z;
    cairo_t *cr = cairo_create(surface);
    if(tura(cr))gtk_widget_queue_draw(darea);
    cairo_destroy(cr);
}
static void test_nacisniecia2( GtkWidget *widget,gpointer data) //ustawienia
{
    int *p;
    if(moj_id[0]=='A' && qq==1)
    {
        printf("ZMIANA RODZAJU ROZGRYWKI JEST TERAZ NIEDOZWOLONA\n");
        return;
    }
    p = (int *)data;
    qq=1;
    if(*p==1)
    {
        a = 10;
        b = 6;
        bok = 40;
    }
    if(*p==2)
    {
        a = 12;
        b = 8;
        bok = 30;
    }
    if(*p==3)
    {
        a = 18;
        b = 12;
        bok = 20;
    } // zmiana wymiarów boiska
    char c[5];
    if(*p==1)c[0]='m';
    if(*p==2)c[0]='s';
    if(*p==3)c[0]='d';
    c[1]='\0';
    sendStringToPipe(potoki,c);
    gtk_label_set_text(tek,"stworzone przez\nStanisław Wilczyński\n\n MOŻESZ ROZPOCZĄĆ MECZ\n");
    gtk_widget_destroy(okienko);
}
void rysuj(cairo_t *cr,int a, int b,int x,int y,int bok)
{
    // rysowanie boiska i inicjalizacja tablicy ruchy
    cairo_set_source_rgba(cr,0,0,0,1);
    cairo_set_line_width(cr,1);
    cairo_move_to(cr,x,y);
    cairo_rel_line_to(cr,(b/2-1)*bok,0);
    cairo_rel_line_to(cr,0,-bok);
    cairo_rel_line_to(cr,2*bok,0);
    cairo_rel_line_to(cr,0,bok);
    cairo_rel_line_to(cr,(b/2-1)*bok,0);
    cairo_rel_line_to(cr,0,a*bok);
    cairo_rel_line_to(cr,-(b/2-1)*bok,0);
    cairo_rel_line_to(cr,0,bok);
    cairo_rel_line_to(cr,-2*bok,0);
    cairo_rel_line_to(cr,0,-bok);
    cairo_rel_line_to(cr,-(b/2-1)*bok,0);
    cairo_rel_line_to(cr,0,-a*bok);
    cairo_stroke(cr);
    //kontury boiska

    cairo_set_source_rgba(cr,0,0,0,0.25);
    cairo_move_to(cr,x,y+bok);
    for(int i=2; i<=a; i++)
    {
        cairo_rel_line_to(cr,b*bok,0);
        cairo_move_to(cr,x,y+i*bok);
    }
    cairo_move_to(cr,x+bok,y);
    for(int i=2; i<=b; i++)
    {
        cairo_rel_line_to(cr,0,a*bok);
        cairo_move_to(cr,x+i*bok,y);
    }
    cairo_move_to(cr,x+b/2*bok-bok,y);
    cairo_rel_line_to(cr,2*bok,0);
    cairo_rel_move_to(cr,-bok,0);
    cairo_rel_line_to(cr,0,-bok);
    cairo_move_to(cr,x+b/2*bok-bok,y+a*bok);
    cairo_rel_line_to(cr,2*bok,0);
    cairo_rel_move_to(cr,-bok,0);
    cairo_rel_line_to(cr,0,bok);
    //droga2 = cairo_copy_path(cr);
    cairo_stroke(cr);
    ruchy[0][1][3]=true;
    odwiedzone[0][1]=true; //lewy gorny rog
    for(int i=2; i<=a; i++)
    {
        odwiedzone[0][i]=true;
        ruchy[0][i][9]=true;
        ruchy[0][i][6]=true;
        ruchy[0][i][3]=true;
    }                           //lewa granica
    ruchy[b][1][1]=true;
    odwiedzone[b][1]=true;   //prawy gorny rog
    for(int i=2; i<=a; i++)
    {
        odwiedzone[b][i]=true;
        ruchy[b][i][7]=true;
        ruchy[b][i][4]=true;
        ruchy[b][i][1]=true;
    }                          //prawa krawedz
    for(int i=1; i<=b-1; i++)
    {
        if(i!=b/2)odwiedzone[i][1]=true;
        ruchy[i][1][1]=true;
        ruchy[i][1][2]=true;
        ruchy[i][1][3]=true;
    }
    ruchy[b/2-1][1][9]=true;
    ruchy[b/2+1][1][7]=true;
    ruchy[b/2-1][1][6]=true;
    ruchy[b/2+1][1][4]=true;
    ruchy[b/2][1][4]=true;
    ruchy[b/2][1][6]=true;
    ruchy[b/2][1][7]=true;
    ruchy[b/2][1][8]=true;
    ruchy[b/2][1][9]=true;  //gorna krawedz
    for(int i=1; i<=b-1; i++)
    {
        if(i!=b/2)odwiedzone[i][a+1]=true;
        ruchy[i][a+1][7]=true;
        ruchy[i][a+1][8]=true;
        ruchy[i][a+1][9]=true;
    }
    ruchy[b/2-1][a+1][6]=true;
    ruchy[b/2+1][a+1][4]=true;
    ruchy[b/2-1][a+1][3]=true;
    ruchy[b/2+1][a+1][1]=true;
    ruchy[b/2][a+1][4]=true;
    ruchy[b/2][a+1][6]=true;
    ruchy[b/2][a+1][1]=true;
    ruchy[b/2][a+1][2]=true;
    ruchy[b/2][a+1][3]=true; //dolna krawedz
    ruchy[0][a+1][9]=true;
    odwiedzone[0][a+1]=true; //lewy dolny rog
    ruchy[b][a+1][7]=true;
    odwiedzone[b][a+1]=true; //prawy gorny rog

    for(int i=2; i<=a; i++)
    {
        for(int j=1; j<=b-1; j++)
        {
            for(int k=1; k<=9; k++)ruchy[j][i][k]=true;
        }
    }                           //cala reszta
}
static gboolean on_draw_event(GtkWidget *widget, cairo_t *cr,gpointer user_data)
{
    cairo_set_source_surface (cr, surface, 0, 0);
    cairo_paint (cr);
    cairo_set_source_rgb(cr,0,0,0);
    cairo_arc(cr,ostatni.x,ostatni.y,3,0,2*M_PI); //małe kółeczko jako piłka
    cairo_fill(cr);
    return FALSE;
}
void nowa_gra()
{
    if(moj_id[0]=='A' && qq==0)
    {
        printf("WYBIERZ USTAWIENIA ROZGRYWKI!!\n");
        return;
    }
    if(l==0)gtk_widget_destroy(okno);
    przycisk[1]=a1;
    przycisk[2]=a2;
    przycisk[3]=a3;
    przycisk[4]=a4;
    przycisk[6]=a6;
    przycisk[7]=a7;
    przycisk[8]=a8;
    przycisk[9]=a9;
    if(moj_id[0]=='A')stan = true;
    else stan = false;
    ostatni.x=x+b/2*bok;
    ostatni.y=y+a/2*bok;
    wsp.x = b/2;
    wsp.y = a/2+1;
    odwiedzone[b/2][a/2+1]=true;
    window = gtk_window_new(GTK_WINDOW_TOPLEVEL);
    char slowo[10];
    sprintf(slowo,"Gracz %c",moj_id[0]);
    gtk_window_set_title(GTK_WINDOW(window),slowo);
    darea = gtk_drawing_area_new();
    g_signal_connect (darea,"configure-event",G_CALLBACK (configure_event_cb), NULL);
    GtkWidget *P = gtk_box_new(GTK_ORIENTATION_VERTICAL, 0);
    GtkWidget *PP = gtk_box_new(GTK_ORIENTATION_HORIZONTAL, 0);
    GtkWidget *p1 = gtk_box_new(GTK_ORIENTATION_HORIZONTAL, 0);
    GtkWidget *p2 = gtk_box_new(GTK_ORIENTATION_HORIZONTAL, 0);
    GtkWidget *p3 = gtk_box_new(GTK_ORIENTATION_HORIZONTAL, 0);
    GtkWidget *PPP = gtk_box_new(GTK_ORIENTATION_VERTICAL,0);
    GtkWidget *b1 = gtk_button_new_with_label("1");
    g_signal_connect(G_OBJECT(b1), "clicked",G_CALLBACK(test_nacisniecia1),(gpointer)&a1);
    GtkWidget *b2 = gtk_button_new_with_label("2");
    g_signal_connect(G_OBJECT(b2), "clicked",G_CALLBACK(test_nacisniecia1),(gpointer)&a2);
    GtkWidget *b3 = gtk_button_new_with_label("3");
    g_signal_connect(G_OBJECT(b3), "clicked",G_CALLBACK(test_nacisniecia1),(gpointer)&a3);
    GtkWidget *b4 = gtk_button_new_with_label("4");
    g_signal_connect(G_OBJECT(b4), "clicked",G_CALLBACK(test_nacisniecia1),(gpointer)&a4);
    GtkWidget *b9 = gtk_button_new_with_label("9");
    g_signal_connect(G_OBJECT(b9), "clicked",G_CALLBACK(test_nacisniecia1),(gpointer)&a9);
    GtkWidget *b6 = gtk_button_new_with_label("6");
    g_signal_connect(G_OBJECT(b6), "clicked",G_CALLBACK(test_nacisniecia1),(gpointer)&a6);
    GtkWidget *b7 = gtk_button_new_with_label("7");
    g_signal_connect(G_OBJECT(b7), "clicked",G_CALLBACK(test_nacisniecia1),(gpointer)&a7);
    GtkWidget *b8 = gtk_button_new_with_label("8");
    g_signal_connect(G_OBJECT(b8), "clicked",G_CALLBACK(test_nacisniecia1),(gpointer)&a8);
    GtkWidget *tekst = gtk_label_new(" WYBIERZ\n KIERUNEK");
    gtk_widget_set_size_request(b1,70,70);
    gtk_widget_set_size_request(b2,70,70);
    gtk_widget_set_size_request(b3,70,70);
    gtk_widget_set_size_request(b4,70,70);
    gtk_widget_set_size_request(b6,70,70);
    gtk_widget_set_size_request(b7,70,70);
    gtk_widget_set_size_request(b8,70,70);
    gtk_widget_set_size_request(b9,70,70);
    gtk_widget_set_size_request(tekst,70,70);
    gtk_box_pack_start(GTK_BOX(p1), b7, true, false, 0);
    gtk_box_pack_start(GTK_BOX(p1), b8, true, false, 0);
    gtk_box_pack_start(GTK_BOX(p1), b9, true, false, 0);
    gtk_box_pack_start(GTK_BOX(p2), b4, true, false, 0);
    gtk_box_pack_start(GTK_BOX(p2), tekst, true, false, 0);
    gtk_box_pack_start(GTK_BOX(p2), b6, true, false, 0);
    gtk_box_pack_start(GTK_BOX(p3), b1, true, false, 0);
    gtk_box_pack_start(GTK_BOX(p3), b2, true, false, 0);
    gtk_box_pack_start(GTK_BOX(p3), b3, true, false, 0);
    gtk_box_pack_start(GTK_BOX(P), p1,true,false,0);
    gtk_box_pack_start(GTK_BOX(P), p2,true,false,0);
    gtk_box_pack_start(GTK_BOX(P), p3,true,false,0);
    gtk_box_pack_start(GTK_BOX(PP), darea,true,true,0);
    gtk_box_pack_start(GTK_BOX(PP), P,true,true,0);
    if(stan)opis = gtk_label_new("KOMUNIKATY:\n\nteraz twoj ruch\n\n");
    else opis = gtk_label_new("KOMUNIKATY:\n\nteraz ruch przeciwnika\n\n");
    gtk_label_set_justify(opis,GTK_JUSTIFY_CENTER);
    gtk_box_pack_start(GTK_BOX(PPP), opis,false,false,0);
    gtk_box_pack_start(GTK_BOX(PPP), PP,true,true,0);
    gtk_container_add(GTK_CONTAINER(window), PPP);
    g_signal_connect(G_OBJECT(darea), "draw",G_CALLBACK(on_draw_event), NULL);
    g_signal_connect(window, "destroy",G_CALLBACK(zakoncz), NULL);
    gtk_window_set_position(GTK_WINDOW(window), GTK_WIN_POS_CENTER);
    gtk_widget_set_size_request(window, 580, 500);
    if(moj_id[0]=='A')gtk_window_move(GTK_WINDOW(window),0,0);
    else gtk_window_move(GTK_WINDOW(window),600,0);
    gtk_widget_set_size_request (darea, 220, 600);
    g_timeout_add(300,pobierz_tekst,NULL);
    gtk_widget_show_all(window);
}
void ustawienia()
{
    okienko=gtk_window_new(GTK_WINDOW_TOPLEVEL);
    gtk_widget_set_size_request(okienko,400,300);
    gtk_window_set_position(GTK_WINDOW(okienko), GTK_WIN_POS_CENTER);
    gtk_window_set_title(GTK_WINDOW(okienko),"USTAWIENIA");
    GtkWidget *tekst = gtk_label_new("Wybierz rozmiar boiska:");
    GtkWidget *b1 = gtk_button_new_with_label("MAŁE");
    g_signal_connect(G_OBJECT(b1), "clicked",G_CALLBACK(test_nacisniecia2),(gpointer)&p);
    GtkWidget *b2 = gtk_button_new_with_label("ŚREDNIE");
    g_signal_connect(G_OBJECT(b2), "clicked",G_CALLBACK(test_nacisniecia2),(gpointer)&q);
    GtkWidget *b3 = gtk_button_new_with_label("DUŻE");
    g_signal_connect(G_OBJECT(b3), "clicked",G_CALLBACK(test_nacisniecia2),(gpointer)&r);
    g_signal_connect(okienko, "destroy",G_CALLBACK(gtk_main_quit), NULL);
    GtkWidget *P = gtk_box_new(GTK_ORIENTATION_VERTICAL, 0);
    gtk_box_pack_start(GTK_BOX(P), tekst,true,true,0);
    gtk_box_pack_start(GTK_BOX(P), b1,true,true,0);
    gtk_box_pack_start(GTK_BOX(P), b2,true,true,0);
    gtk_box_pack_start(GTK_BOX(P), b3,true,true,0);
    gtk_container_add(GTK_CONTAINER(okienko), P);
    gtk_widget_show_all(okienko);
    gtk_main();
}
void instrukcja()
{
    GtkWidget *okno1=gtk_window_new(GTK_WINDOW_TOPLEVEL);
    gtk_widget_set_size_request(okno1,400,300);
    gtk_window_set_position(GTK_WINDOW(okno1), GTK_WIN_POS_CENTER);
    gtk_window_set_title(GTK_WINDOW(okno1),"INSTRUKCJA");
    g_signal_connect(okno1, "destroy",G_CALLBACK(gtk_main_quit), NULL);
    GtkWidget *text = gtk_label_new("Aby rozpocząć rozgrywkę gracz A powinien wybrać w ustawieniach rodzaj rozgrywki. Dostępne są trzy opcje: 'MAŁE','ŚREDNIE','DUŻE'\n(odpowiednio o wymiarach 10x6,12x8 i 18x12). Dłuższy z tych odcinków to krawędź boczna boiska.\nUwaga! Gracz A nie powinien zmieniać raz wybranych ustawień!\n\nRozgrywka rozpoczyna się od narysowania specjalnego 'boiska' na ekranie.\nGra rozgrywa się tylko na przecięciach linii planszy. Pierwszy ruch rozpoczyna gracz A od centralnego miejsca na planszy, natomiast każdy kolejny się zaczyna w miejscu na\nktórym skończył się poprzedni. Gracze wykonują ruchy naprzemiennie, 'rysując' kolejno kreski prowadząc wirtualnie przy tym tzw. piłkę. Ruch piłki polega na 'narysowaniu'\n kolejnejlinii długości jednej kratki (linie te łączą przecięcia kratek). Celem gry jest 'strzelenie bramki przeciwnikowi' - czyli umieszczenie piłki w bramce przeciwnika.\n\nWspomiane 'rysowanie' polega na naciśnięciu przycisku wyboru jednego z kierunków oznaczonych liczbami z klawiatury numerycznej.\nPiłka nie może być prowadzona bezpośrednio w miejscach po których się wcześniej przemieszczała, oraz po dokładnym szkicu planszy. \n\nIstnieje jednak możliwość tzw. odbijania się od szkiców: \nOdbicie polega na przyznaniu graczowi dodatkowego ruchu. Jeśli gracz kończy ruch w miejscu, \nprzez którą przechodzi wcześniej narysowana linia (krawędź boiska lub linia wcześniejszego ruchu), następuje odbicie.\nRuch gracza kończy się dopiero wtedy kiedy 'piłka dojdzie na puste pole gdzie nie ma już żadnego zarysu'.\n\nGracze A i B są oznaczeni różnymi kolorami(odpowiednio niebieskim i czerwonym).\nWygrywa gracz, który pierwszy strzeli bramkę(Gracz A strzela do górnej bramki, gracz B do dolnej).\nW sytuacji kiedy gracz nie może wykonać ruchu i spowodował zablokowanie gry, przegrywa.\n\n");
    gtk_container_add(GTK_CONTAINER(okno1),text);
    gtk_widget_show_all(okno1);
    gtk_main();
}
static gboolean pobierz_tekst(gpointer data)
{
    gchar wejscie[MAKS_DL_TEKSTU+5];

    if (getStringFromPipe(potoki,wejscie,MAKS_DL_TEKSTU))
    {
        // pierwszy wysłany komunikat jest o zmianie ustawień gry
        if(wejscie[0]=='m')
        {
            a = 10;
            b = 6;
            bok = 40;
            l=1;
            return true;
        }
        if(wejscie[0]=='s')
        {
            a = 12;
            b = 8;
            bok = 30;
            l=1;
            return true;
        }
        if(wejscie[0]=='d')
        {
            a = 18;
            b = 12;
            bok = 20;
            l=1;
            return true;
        }
        char wiersz[100];
        int s = wejscie[0]-'0';
        int x1=przycisk[s].x;
        int y1=przycisk[s].y;
        int z1=przycisk[s].z;
        vec.x=x1;
        vec.y=y1;
        vec.z=z1;
        cairo_t *cr = cairo_create(surface);
        cairo_move_to(cr,ostatni.x,ostatni.y);

        if(twoj_id[0]=='A')cairo_set_source_rgb(cr,0,0,1);
        else cairo_set_source_rgb(cr,1,0,0);
        cairo_rel_line_to(cr,vec.x*bok,vec.y*bok);
        ruchy[wsp.x][wsp.y][vec.z]=false;
        ostatni.x += vec.x*bok;
        ostatni.y += vec.y*bok;
        wsp.x += vec.x;
        wsp.y += vec.y;

        if(wsp.y == 0 && (wsp.x==b/2 || wsp.x == b/2+1 || wsp.x == b/2-1))
        {
            if(moj_id[0]=='A')zwyciestwo1();
            else porazka();
            u=2;
        }
        if(wsp.y == a+2 && (wsp.x==b/2 || wsp.x == b/2+1 || wsp.x == b/2-1))
        {
            if(moj_id[0]=='B')zwyciestwo1();
            else porazka();
            u=2;
        }
        if(!odwiedzone[wsp.x][wsp.y]) stan = !stan;
        odwiedzone[wsp.x][wsp.y]=true;
        if(vec.z==1)ruchy[wsp.x][wsp.y][9]=false;
        if(vec.z==2)ruchy[wsp.x][wsp.y][8]=false;
        if(vec.z==3)ruchy[wsp.x][wsp.y][7]=false;
        if(vec.z==9)ruchy[wsp.x][wsp.y][1]=false;
        if(vec.z==8)ruchy[wsp.x][wsp.y][2]=false;
        if(vec.z==7)ruchy[wsp.x][wsp.y][3]=false;
        if(vec.z==4)ruchy[wsp.x][wsp.y][6]=false;
        if(vec.z==6)ruchy[wsp.x][wsp.y][4]=false;
        if(stan)sprintf(wiersz,"    KOMUNIKATY:\nWSZYSTKO OK\nteraz twoj ruch\n\n");
        else sprintf(wiersz,"    KOMUNIKATY:\nWSZYSTKO OK\nteraz ruch przeciwnika\n\n");
        gtk_label_set_text(GTK_LABEL(opis),wiersz);
        cairo_stroke(cr);
        if(u==1 && !ruchy[wsp.x][wsp.y][1]  && !ruchy[wsp.x][wsp.y][2]  && !ruchy[wsp.x][wsp.y][3]  && !ruchy[wsp.x][wsp.y][4] && !ruchy[wsp.x][wsp.y][6]  && !ruchy[wsp.x][wsp.y][7]  && !ruchy[wsp.x][wsp.y][8]  && !ruchy[wsp.x][wsp.y][9])
        {
            zwyciestwo1();
        }
        gtk_widget_queue_draw(darea);
        cairo_destroy(cr);
    }
    return TRUE;
}
void poczatek()
{
    // gracz B nie ma możliwości wyboru ustawień gry
    printf("\nZACZEKAJ : GRACZ A WYBIERA RODZAJ GRY\n");
    while(pobierz_tekst(NULL))
    {
        if(l==1)break;
    }
    nowa_gra();
}


