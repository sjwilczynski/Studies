# coding= utf-8
import gtk


class NieMaKluczaError(Exception): pass

class Kontakt():
    """
    Klasa Kontakt służy do przechowywania kontaktów w bazie danych.
    Zawiera nazwę,numer użytkownika oraz czas ostatniego kontaktu
    """
    def __init__(self,nazwa,nr):
        """
        Konstruktor
        :param nazwa: nazwa użytkownika
        :param nr: numer użytkownika
        :return:
        """
        self.name = nazwa
        self.numer = nr
        self.ostatni = "Never"
        self.tab = [self.name,self.numer,self.ostatni]

    def setdate(self,czas):
        self.ostatni = czas
        self.tab[2] = czas

    def pokaz(self):
        s = "Contact:\n"
        s += "Name: " + self.name + "\n"
        s += "Number: " + self.numer + "\n"
        s += "Last conversation: " + self.ostatni + "\n"
        return s

class Edycja(gtk.Window):
    """
    Klasa służąca do edycji bazy danych przechowującej kontakty
    """
    def __init__(self,db,nr):
        """
        Inicjalizacja GUI
        :param db: baza danych
        :param nr: tryb edycji - dodawanie lub usuwanie
        :return:
        """
        super(Edycja, self).__init__()
        self.data = db
        self.set_size_request(300,500)
        self.set_border_width(10)
        if nr == 0: self.set_title("Add/Edit contact")
        else: self.set_title("Delete contact")
        self.nazwa = gtk.Entry()
        self.numer = gtk.Entry()
        self.set_position(gtk.WIN_POS_CENTER)
        self.connect("destroy", lambda x: self.destroy())
        self.kontrolki(nr)
        self.show_all()

    def kontrolki(self,nr):
        """
        Inicjalizacja GUI cd.
        :param nr: tryb edycji
        :return:
        """
        if nr == 0:
            vbox = gtk.VBox(spacing = 10)
            hbox1 = gtk.HBox(spacing = 10)
            hbox2 = gtk.HBox(spacing = 10)
            l1 = gtk.Label("Enter name:")
            l2 = gtk.Label("Enter number:")
            hbox1.add(l1)
            hbox1.add(self.nazwa)
            hbox2.add(l2)
            hbox2.add(self.numer)
            b1 = gtk.Button("Save contact")
            b1.connect("clicked",self.zapisz)
            vbox.add(hbox1)
            vbox.add(hbox2)
            vbox.add(b1)
            self.add(vbox)
        else:
            vbox = gtk.VBox(spacing = 10)
            hbox1 = gtk.HBox(spacing = 10)
            l1 = gtk.Label("Enter name:")
            hbox1.add(l1)
            hbox1.add(self.nazwa)
            b1 = gtk.Button("Delete contact")
            b1.connect("clicked",self.usun)
            vbox.add(hbox1)
            vbox.add(b1)
            self.add(vbox)

    def wyszukaj(self,p):
        """
        sprawdza czy dany element jest w bazie
        :param p: szukany element
        :return:
        """
        return self.data.has_key(p)

    def zapisz(self,sender):
        """
        po kliknięciu na przycisk dodaje do bazy kontakt o wartościach z kontrolek Entry
        :param sender: przycisk
        :return:
        """
        p = Kontakt(self.nazwa.get_text(),self.numer.get_text())
        self.data[p.name] = p
        self.nazwa.set_text("")
        self.numer.set_text("")

    def usun(self,sender):
        """
        Usuwa kontakt z bazy o wartościach z kontrolek Entry
        :param sender:
        :return:
        """
        text = self.nazwa.get_text()
        if self.wyszukaj(text):
            del self.data[text]
        else:
            raise NieMaKluczaError
        self.nazwa.set_text("")
