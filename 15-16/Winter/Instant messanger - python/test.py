#!/usr/bin/python
# -*- coding: utf-8 -*-
import threading
import time
import serwer
import socket
import kontakt
import shelve
import unittest


class Testy(unittest.TestCase):
    """
    Testy sprawdzające działanie naszych klas
    """

    def testProsty(self):
        """
        Proste testy
        :return:
        """
        r1 = x.wyszukaj("Ja")
        r2 = x.wyszukaj("ja")
        r3 = x.wyszukaj("TY")
        info = "info"
        s.send(info)
        r4 = s.recv(2048)
        self.assertEqual(r1, True, "ok")
        self.assertEqual(r2, False, "ok")
        self.assertEqual(r3, True, "ok")
        self.assertEqual(r4, "ok"+info, "ok")


    def testDziedzina(self):
        """
        Reakcja na błędne dane
        :return:
        """
        x.nazwa.set_text("MY")
        self.assertRaises(kontakt.NieMaKluczaError, x.usun, "lala")


if __name__ == "__main__":
        data = shelve.open("test.db",writeback=True)
        k1 = kontakt.Kontakt("Ja","123")
        k2 = kontakt.Kontakt("TY","100")
        data[k1.name] = k1
        data[k2.name] = k2
        x = kontakt.Edycja(data,0)
        x.hide()
        threading.Thread(target= serwer.test).start()
        time.sleep(2)
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.connect((serwer.HOST,serwer.PORT))
        unittest.main()