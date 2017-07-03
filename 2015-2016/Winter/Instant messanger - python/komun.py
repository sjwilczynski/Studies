# coding= utf-8
import gobject
import gtk
import socket
import threading
import time

class Komunikat(gtk.Window):
    """
    Klasa odpowiadająca za okno komunikacji klient - klient
    """
    def __init__(self,imie1,imie2,MYn,YOURn):
        """
        Inicjalizacja GUI oraz połączenia z serwerem
        :param imie1: nazwa użytkownika
        :param imie2: nazwa użytkownika, z którym się kontaktujemy
        :param MYn: numer użytkownika
        :param YOURn: numer użytkownika, z którym się kontaktujemy
        :return:
        """
        self.imie1 = imie1
        self.imie2 = imie2
        super(Komunikat, self).__init__()
        self.set_title("Komunikator")
        self.set_position(gtk.WIN_POS_CENTER)
        self.connect("destroy", self.koniec)
        self.text = gtk.Entry()
        self.text.connect("activate",self.wyslij)
        self.kontrolki()
        self.tag1 = self.buffer.create_tag("yellow_bg", background="yellow")
        self.tag2 = self.buffer.create_tag("violet_bg", background="violet")
        self.set_size_request(300,500)
        self.set_border_width(10)
        self.stan = 1
        self.adres = ('localhost',8888)
        self.s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.s.connect(self.adres)
        self.s.settimeout(10)
        self.w = Sluchanie(self)
        self.w.start()
        print self.s.send(MYn + ' ' + YOURn)
        self.show_all()


    def kontrolki(self):
        """
        Inicjalizacja GUI cd.
        :return:
        """
        table = gtk.Table(9,2)
        b1 = gtk.Button("Wyslij")
        b2 = gtk.Button("Zakoncz")
        b1.connect("clicked",self.wyslij)
        b2.connect("clicked",self.koniec)
        view = gtk.TextView()
        view.set_editable(False)
        view.set_cursor_visible(False)
        view.set_wrap_mode(2)
        self.buffer = view.get_buffer()
        scroll = gtk.ScrolledWindow()
        scroll.set_policy(gtk.POLICY_AUTOMATIC,gtk.POLICY_AUTOMATIC)
        self.buffer.set_text("Let's start talking")
        self.text.set_max_length(200)
        self.text.set_text("")
        scroll.add(view)
        table.attach(scroll,0,2,0,6)
        table.attach(self.text,0,2,6,7)
        table.attach(b2,0,1,7,8)
        table.attach(b1,1,2,7,8)
        self.add(table)


    def koniec(self,sender):
        """
        funkcja wywoływana w przypadku próby wyłączenia okna
        :param sender:
        :return:
        """
        self.w.koniec()
        self.s.close()
        self.destroy()


    def wyslij(self,sender):
        """
        funkcja służąca do wysłania wiadomości z kontrolki Entry - prezentacji na ekranie i przesłaniu do serwera
        :param sender:
        :return:
        """
        p = "\n" + self.imie1 + ">"
        s = " "+self.text.get_text()
        if s == " ": return
        mark = gtk.TextMark("mark",True)
        self.buffer.add_mark(mark,self.buffer.get_end_iter())
        self.buffer.insert_at_cursor(p,p.__len__())
        end = self.buffer.get_end_iter()
        self.buffer.apply_tag(self.tag1,self.buffer.get_iter_at_mark(mark),end)
        self.buffer.insert_at_cursor(s,s.__len__())
        self.buffer.delete_mark(mark)
        self.text.set_text("")
        self.s.send(s)


    def pobierz(self):
        """
        funkcja pobierająca wiadomość z serwera i wy≤świetlająca ją na ekranie
        :return:
        """
        try:
            data = self.s.recv(2048)
            if data == "": return True
            mark = gtk.TextMark("mark",True)
            self.buffer.add_mark(mark,self.buffer.get_end_iter())
            p = "\n" + self.imie2 + ">"
            self.buffer.insert_at_cursor(p,p.__len__())
            end = self.buffer.get_end_iter()
            self.buffer.apply_tag(self.tag2,self.buffer.get_iter_at_mark(mark),end)
            self.buffer.insert_at_cursor(data,data.__len__())
            print data
            self.buffer.delete_mark(mark)

        except socket.timeout: pass
        return True
        #else:
            #return False


    def pobierz_info(self):
        """
        funkcja co sekundę wywołująca funkcję pobierz()
        :return:
        """
        while True:
            time.sleep(1)
            if self.pobierz() == False: return


    #przeczytane, że jak używasz gtk i gobject to GIL powoduje, że tylko jeden wątek może pracować - czy można to naprawić
    def pobierz_info2(self):
        """
        funkcja co sekundę wywołująca funkcję pobierz
        :return:
        """
        #gtk.timeout_add(1000,self.pobierz)
        gobject.timeout_add(1000,self.pobierz)

class Sluchanie(threading.Thread):
    """
    Wątek słuchający przychodzących z serwera wiadomości
    """
    def __init__(self,x):
        self.okno = x
        self.stan = 1
        threading.Thread.__init__(self)

    def run(self):
        while True:
            if(self.stan == 1):
                self.okno.pobierz()
            else: return

    def koniec(self):
        self.stan = 0