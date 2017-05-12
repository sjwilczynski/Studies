# coding= utf-8
import threading
import lista
import kontakt
import gtk
import shelve

class Menu(gtk.Window):
    """
    Klasa służąca do wyświetlenie menu głównego komunikatora - z niej można przejść do rozmawiania lub edytowania kontaktów
    """
    def __init__(self,db,kontakt):
        """
        Iinicjowanie GUI cz.1
        :param db: baza kontaktów
        :param kontakt: id osoby używającej komunikatora
        :return:
        """
        super(Menu, self).__init__()
        self.data = db
        self.id = kontakt
        self.set_title("Welcome to KeepInTouch")
        self.set_position(gtk.WIN_POS_CENTER)
        self.set_border_width(10)
        self.set_size_request(300,500)
        self.connect("destroy", self.koniec)
        self.kontrolki()
        self.show_all()

    def kontrolki(self):
        """
        Inicjowanie GUI cz.2
        :return:
        """
        vbox = gtk.VBox(spacing = 10)
        rozmowa = gtk.Button("Contact someone!!")
        rozmowa.connect("clicked",self.list)
        opcje1 = gtk.Button("Add/Edit contact")
        opcje1.connect("clicked",self.dodaj)
        opcje2 = gtk.Button("Delete contact")
        opcje2.connect("clicked",self.usun)
        wyjscie = gtk.Button("Exit")
        wyjscie.connect("clicked",self.koniec)
        vbox.add(rozmowa)
        vbox.add(opcje1)
        vbox.add(opcje2)
        vbox.add(wyjscie)
        self.add(vbox)

    def koniec(self,sender):
        """
        funkcja wywoływana przy próbie zamknięcia menu
        :param sender:
        :return:
        """
        gtk.main_quit()
        self.destroy()

    def dodaj(self,sender):
        """
        Funkcja otwierająca okno do edycji kontaków
        :param sender:
        :return:
        """
        edit = kontakt.Edycja(self.data,0)
    def usun(self,sender):
        """
        Funkcja otwierająca okno do usuwania kontaków
        :param sender:
        :return:
        """
        edit = kontakt.Edycja(self.data,1)
    def list(self,sender):
        """
        Funkcja otwierająca okno do wyboru nowego połączenia
        :param sender:
        :return:
        """
        okno = lista.Lista(self.data,self.id)



