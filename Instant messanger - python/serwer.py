# coding= utf-8
"""Zaimplementowana funkcjonalność serwera"""
import socket
import sys
import threading


HOST = ''
PORT = 8888
i = 0
pol = [1,2]
adres = (HOST,PORT)

def clientthread(conn):
    """
     Funkcja obsługująca klienta serwera w nowym wątku
    """
    global i,pol
    s = conn.recv(1024)
    l = s.split()
    Myn = l[0]
    YOURn = l[1]
    print Myn,YOURn
    pol[i%2] = (Myn,YOURn,conn)
    i += 1

    while True:
        data = conn.recv(2048)
        if not data:
            break
        for j in range(0,2):
            (m,y,c) = pol[j]
            if m == YOURn:
                c.send(data)

    conn.close()

def uruchomserwer():
    """
    Inicjalizacja serwera - utworzenia socketa słuchającego przychodzących połączeń
    """
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    print 'Socket created'
    try:
        s.bind((HOST, PORT)) #połączenie socketa z konkretnym adresem
    except socket.error , msg:
        print 'Bind failed. Error Code : ' + str(msg[0]) + ' Message ' + msg[1]
        sys.exit()

    print 'Socket bind complete'
    s.listen(4) #nasłuciwanie...
    print 'Socket now listening'

    while 1:
        conn, addr = s.accept() #połączenie
        print 'Connected with ' + addr[0] + ':' + str(addr[1])
        w1 = threading.Thread(target = clientthread, args = (conn,)) #wątek klienta
        w1.start()

    s.close()

def test():
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind((HOST, PORT)) #połączenie socketa z konkretnym adresem
    s.listen(2) #nasłuciwanie...
    conn, addr = s.accept() #połączenie
    info = conn.recv(2048)
    conn.send("ok" + info)
    s.close()

#uruchomserwer()



