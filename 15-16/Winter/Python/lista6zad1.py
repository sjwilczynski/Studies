import urllib
import re
import threading
from HTMLParser import HTMLParser
import Queue


class MyHtmlP(HTMLParser):
    def handle_starttag(self,tag,attrs):
        L = []
        if tag == "a":
            for(atr,val) in attrs:
                if atr == 'href':  self.s += val + " "
        return L
    def feed(self,data):
        self.s = " "
        HTMLParser.feed(self,data)

def f(adres):
    strona = urllib.urlopen(adres)
    tekst = strona.read()
    strona.close()
    automat = re.compile("Python")
    return len([ x.start() for x in automat.finditer(tekst)])


def znajdzlink(adres):
    strona = urllib.urlopen(adres)
    tekst = strona.read()
    mp = MyHtmlP()
    mp.feed(tekst)
    strona.close()
    link = "([a-zA-Z]+\.)*[a-zA-Z]*"
    #print tekst
    automat = re.compile("http://"+ link)
    return [ url.group() for url in automat.finditer(mp.s)]




class WWWiter():
    def __init__(self,link,f):
        self.f = f
        self.Q = Queue.Queue()
        self.Q.put((link,8))
        self.k = 0

    def __call__(self,*args):
        (n,(adres,k)) = args
        if(n==0):
            if(k > 0):
                for url in znajdzlink(adres):
                    self.Q.put((url,(k-1)),True)
        else:
            self.k += self.f(adres)
            print "znaleziono do tej pory %d wystapien" %self.k

    def __next__(self):
        if(self.Q.empty()):
            raise StopIteration
        (adres,k) = self.Q.get()
        w1 = threading.Thread(target = self,args = (0,(adres,k)))
        w2 = threading.Thread(target = self,args = (1,(adres,k)))
        w1.start()
        w2.start()
        w1.join()
        w2.join()

#print f("https://www.ii.uni.wroc.pl/~marcinm/dyd/python/")
#print znajdzlink("https://www.ii.uni.wroc.pl/~marcinm/dyd/python/")

x = WWWiter("https://www.python.org/",f)
for i in range (1,15):
    x.__next__()


