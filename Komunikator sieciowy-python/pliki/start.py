#!/usr/bin/python
# -*- coding: utf-8 -*-
import gobject
import gtk
import shelve
import kontakt
import menu

class Start(gtk.Window):
    """
    Klasa służąca do uruchomienia programu  - wprowadzenie nazwy i numeru użytkownika
    """
    def __init__(self,db):
        """
        Inicjalizacja GUI
        :param db: Baza kontaktow
        :return:
        """
        super(Start, self).__init__()
        self.set_title("Start window")
        self.set_position(gtk.WIN_POS_CENTER)
        self.set_border_width(10)
        self.set_size_request(200,400)
        self.connect("destroy", gtk.mainquit)
        self.data = db
        self.kontrolki1()
        self.show_all()

    def kontrolki1(self):
        """
        Inicjalizacja GUI cd.
        :return:
        """
        vbox = gtk.VBox(spacing = 10)
        label = gtk.Label("Enter your name and number:")
        self.e1 = gtk.Entry()
        self.e2 = gtk.Entry()
        self.e1.set_text("NAME")
        self.e2.set_text("NUMBER")
        b = gtk.Button("Save")
        b.connect("clicked",self.save)
        vbox.add(label)
        vbox.add(self.e1)
        vbox.add(self.e2)
        vbox.add(b)
        self.add(vbox)
        #self.show_all()

    def save(self,sender):
        """
        Potwierdzenie wprowadzonych do kontrolek Entry danych
        :param sender:
        :return:
        """
        k = kontakt.Kontakt(self.e1.get_text(),self.e2.get_text())
        if( not self.data.has_key(k.name) ): self.data[k.name] = k
        self.w = menu.Menu(self.data,k)
        self.hide()

    def czyzyje(self):
        print menu.stan
        if menu.stan == 0:
            self.destroy()
            gtk.mainquit()
            return False
        return True


"""
Rozpoczęcie działania programu
"""

gobject.threads_init()
gtk.gdk.threads_init()
db = shelve.open("kontakty.db",writeback=True)
x = Start(db)

gtk.main()

#w1 = threading.Thread(target = gtk.main)
#w1.start()
#w1.join()
db.close()
