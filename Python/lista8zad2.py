__author__ = 'stachu'
import gtk
import shelve


class NieMaKluczaError(Exception): pass

class Disc():

    def __init__(self,nazwa,utwory,autorzy,wypozyczona):
        self.name = nazwa
        self.setlist = utwory
        self.autor = autorzy
        self.stan = wypozyczona
    def pokaz(self):
        s = "Plyta:" + self.name +"\n"
        s += "Lista utowrow:" + self.setlist + "\n"
        s += "Wykonawca:" + self.autor + "\n"
        if self.stan:
            s += "wypozyczona\n"
        else: s += "niewypozyczona\n"
        return s


class MojeOkno(gtk.Window):
    def __init__(self,db):
        super(MojeOkno, self).__init__()
        self.data = db
        self.set_title("baza")
        self.set_position(gtk.WIN_POS_CENTER)
        self.connect("destroy", gtk.mainquit)
        self.kontrolki()
        self.show_all()

    def kontrolki(self):
        b = gtk.Button("Wyjscie")
        b.connect("clicked",lambda x: self.destroy())
        vbox = gtk.VBox(spacing = 10)
        hbox1 = gtk.HBox(spacing = 10)
        hbox2 = gtk.HBox(spacing = 10)
        hbox3 = gtk.HBox(spacing = 10)
        hbox4 = gtk.HBox(spacing = 10)
        label1 = gtk.Label("Moje plyty")
        self.nazwa = gtk.Entry()
        self.lista = gtk.Entry()
        self.autorzy = gtk.Entry()
        self.nazwa.set_text("Nazwa")
        self.lista.set_text("Lista utworow")
        self.autorzy.set_text("autorzy")
        b1 = gtk.Button("Zapisz dane")
        b2 = gtk.Button("Pokaz moje plyty")
        b3 = gtk.Button("Usun")
        b4 = gtk.Button("Wypozycz")
        b1.connect("clicked",self.zapisz)
        b2.connect("clicked",self.wypisz)
        b3.connect("clicked",self.usun)
        b4.connect("clicked",self.wypozycz)
        label2 = gtk.Label("Usuwanie:")
        label3 = gtk.Label("Wypozyczanie:")
        self.wypozycz = gtk.Entry()
        self.wypozycz.set_text("Podaj nazwe plyty, ktora chcesz wypozyczyc")
        self.usuwanie = gtk.Entry()
        self.usuwanie.set_text("Wprowadz nazwe plyty, ktora chcesz usunac")
        hbox1.pack_start(self.nazwa,False,False,10)
        hbox1.add(self.lista)
        hbox2.pack_start(self.autorzy,False,False,10)
        hbox2.add(b1)
        hbox3.pack_start(label2,False,False,10)
        hbox3.add(self.usuwanie)
        hbox3.add(b3)
        hbox4.pack_start(label3,False,False,10)
        hbox4.add(self.wypozycz)
        hbox4.add(b4)
        vbox.pack_start(label1,False,False,10)
        vbox.add(hbox1)
        vbox.add(hbox2)
        vbox.add(b2)
        vbox.add(hbox3)
        vbox.add(hbox4)
        self.add(vbox)


    def zapisz(self,sender):
        p = Disc(self.nazwa.get_text(),self.lista.get_text(),self.autorzy.get_text(),False)
        self.data[p.name] = p

    def wypisz(self,sender):
        if self.data.__len__() == 0: print "Baza jest pusta"
        for key in self.data.keys():
            print self.data[key].pokaz()

    def wypozycz(self,sender):
        text = self.wypozycz.get_text()
        if self.wyszukaj(text):
            self.data[text].stan = True
        else:
            raise NieMaKluczaError

    def usun(self,sender):
        text = self.usuwanie.get_text()
        if self.wyszukaj(text):
            del self.data[text]
        else:
            raise NieMaKluczaError

    def wyszukaj(self,p):
        return self.data.has_key(p)



db = shelve.open("bazadanych.db",writeback=True)
w = MojeOkno(db)
gtk.main()
db.close()