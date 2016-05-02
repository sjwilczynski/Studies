# coding= utf-8
from datetime import date
import kontakt
import komun

__author__ = 'stachu'
import gtk

class Lista(gtk.Window):
    """
    Klasa służąca do wyboru osoby, z która chcemy się połączyć
    """
    def __init__(self,db,id):
        """
        Inicjalizacja GUI
        :param db: Baza kontaktów
        :param id: Id użytkownika
        :return:
        """
        super(Lista, self).__init__()
        self.data = db
        self.id = id
        self.set_size_request(500,300)
        self.set_border_width(10)
        self.set_title("Time to choose")
        self.set_position(gtk.WIN_POS_CENTER)
        self.connect("destroy", lambda x: self.destroy())
        self.kontrolki()
        self.show_all()

    def kontrolki(self):
        """
        Inicjalizacja GUI cd.
        :return:
        """
        table = gtk.Table(10,1)
        komunikat = gtk.Label("")
        if self.data.__len__() == 0:
            komunikat.set_text("You have no friends yet ;(")
            table.attach(komunikat,0,1,0,5,ypadding = 3)
        else:
            scroll = gtk.ScrolledWindow()
            scroll.set_policy(gtk.POLICY_AUTOMATIC,gtk.POLICY_AUTOMATIC)
            listmodel = gtk.ListStore(str,str,str)
            columns = ["Name","Number","Last conversation"]
            contacts = []
            for key in self.data.keys():
                contacts.append(self.data[key].tab)
            for i in range(len(contacts)):
                listmodel.append(contacts[i])
            view = gtk.TreeView(model=listmodel)
            for i in range(len(columns)):
                cell = gtk.CellRendererText()
                if i == 0:
                    cell.props.weight_set = True
                    cell.props.weight = 600
                col = gtk.TreeViewColumn(columns[i], cell, text = i)
                view.append_column(col)
            scroll.add(view)
            komunikat.set_text("You have some friends to choose from :)")
            table.attach(komunikat,0,1,0,1,ypadding = 3)
            table.attach(scroll,0,1,1,8,ypadding = 3)
            view.get_selection().connect("changed", self.on_changed)
            b1 = gtk.Button("Start talking")
            b1.connect("clicked",self.rozpocznij)
            table.attach(b1,0,1,8,9,ypadding = 3)
        self.add(table)


    def on_changed(self, selection):
        """
        Funkcja reagująca na wybór kontaktu z listy
        :param selection: wskazany element
        :return:
        """
        (model, iter) = selection.get_selected()
        self.wybrany =  kontakt.Kontakt(model[iter][0],  model[iter][1])
        return True

    def rozpocznij(self,sender):
        """
        Rozpoczęcie konwersacji
        :param sender: przycisk start_talking
        :return:
        """
        self.destroy()
        print self.id.name, self.id.numer, self.wybrany.name, self.wybrany.numer
        self.data[self.wybrany.name].setdate(str(date.today()))
        x = komun.Komunikat(self.id.name,self.wybrany.name,self.id.numer,self.wybrany.numer)