__author__ = 'stachu'
import gtk
import time,gobject


class Minutnik(gtk.Window):
    def __init__(self):
        super(Minutnik,self).__init__()
        self.set_title("MINUTNIK")
        self.set_position(gtk.WIN_POS_CENTER)
        self.set_default_size(300,300)
        self.czas = gtk.Entry()
        self.label = gtk.Label("CZAS")
        self.connect("destroy", gtk.mainquit)
        self.kontrolki()
        self.show_all()

    def kontrolki(self):
        vbox = gtk.VBox(spacing = 10)
        hbox = gtk.HBox(spacing = 10)
        b1 = gtk.Button("Wyjscie")
        b1.connect("clicked",lambda x: self.destroy())

        self.czas.set_text("  podaj czas do odliczania")
        b2 = gtk.Button("ODLICZAJ")
        b2.connect("clicked", self.odliczaj, 0)
        hbox2 = gtk.HBox(spacing = 10)
        vbox2 = gtk.VBox(spacing = 10)
        hbox2.pack_start(self.czas,False,False,10)
        hbox2.add(b2)
        hbox2.set_border_width(10)
        hbox.set_border_width(10)
        vbox.set_border_width(10)
        vbox2.set_border_width(10)

        b3 = gtk.Button("Gotowanie jajek")
        b4 = gtk.Button("Gotowanie makaronu")
        b5 = gtk.Button("Podgrzewanie kakao")
        b3.connect("clicked", self.odliczaj, 210)
        b4.connect("clicked", self.odliczaj, 480)
        b5.connect("clicked", self.odliczaj, 90)
        vbox2.pack_start(b3,False,False,10)
        vbox2.add(b4)
        vbox2.add(b5)

        hbox.pack_start(b1,False,False,10)
        hbox.add(vbox2)
        vbox.pack_start(self.label,False,False,0)
        vbox.add(hbox2)
        vbox.add(hbox)

        self.add(vbox)

    def odliczaj(self, sender,a):

        if(a == 0):
            x = int(float(self.czas.get_text()))
            self.x = x
        else : self.x = a
        gobject.timeout_add(1000, self.countdown)
        self.countdown()

    def countdown(self):
        if self.x > 0:
            l = ""
            if(self.x%60 < 10): l = "0"
            self.label.set_text("Pozostalo: " + str(int(self.x/60)) + ":" + l + str(self.x % 60))
            self.x -= 1
            return True
        else:
            self.label.set_text("Koniec odliczania!")
            return False

x = Minutnik()
gtk.main()