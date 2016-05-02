import time


class ArgumentNotIntegerError(Exception):
    """Prosty wyjatek"""
    pass


def isPrime(n):
    """Sprawdza czy liczba jest pierwsza"""
    if(n == 2):
        return True
    if(n % 2 == 0):
        return False
    for i in range(3, n, 2):
        if(i * i > n):
            break
        if(n % i == 0):
            return False
    return True


def pierwsze_skladana(n):
    """Generuje liste liczb pierwszych <= n uzywajac list skladanych"""
    if not(isinstance(n, int)):
        raise ArgumentNotIntegerError
    lp = [x for x in range(2, n + 1) if len([y for y in range(2, x // 2 + 1) if x % y == 0]) == 0]
    return lp


def pierwsze_funkcyjna(n):
    """Generuje liste liczb pierwszych <= n funkcyjnie"""
    if not(isinstance(n, int)):
        raise ArgumentNotIntegerError
    return filter(lambda x: len([y for y in range(2, x // 2 + 1) if x % y == 0]) == 0, range(2, n+1))


class PrimeIter(object):
    """iterator zwracajacy koljene liczby pierwsze"""
    def __init__(self):
        self.licznik = 2

    def __next__(self):
        wynik = self.licznik
        while(isPrime(wynik) == False):
            wynik += 1
        self.licznik = wynik + 1
        return wynik


class PrimeCol(object):
    """kolekcja liczb pierwszych"""
    def __iter__(self):
        return PrimeIter()


def pierwsze_iterator(n):
    """Generuje liste liczb pierwszych <= n uzywajac iteratora"""
    if not(isinstance(n, int)):
        raise ArgumentNotIntegerError
    x = PrimeCol()
    y = x.__iter__()
    tab = []
    if n < 2:
        return tab
    p = y.__next__()
    while p <= n:
        tab += [p]
        p = y.__next__()
    return tab


start1 = time.clock()
pierwsze_skladana(10000)
stop1 = time.clock()
pierwsze_funkcyjna(10000)
stop2 = time.clock()
pierwsze_iterator(10000)
stop3 = time.clock()
