import lista4zad1
import unittest


class TestPierwsze(unittest.TestCase):
    "Testy - zbior prostych testow do sprawdzenia"
    Testy = [(1, []), (2, [2]), (3, [2, 3]),
            (20, [2, 3, 5, 7, 11, 13, 17, 19]),
            (50, [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47]),
            (200, [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199])]

    def testProsty(self):
        "Proste testy"
        for test, odp in self.Testy:
            r1 = lista4zad1.pierwsze_funkcyjna(test)
            r2 = lista4zad1.pierwsze_skladana(test)
            r3 = lista4zad1.pierwsze_iterator(test)
            self.assertEqual(r1, odp, "ok")
            self.assertEqual(r2, odp, "ok")
            self.assertEqual(r3, odp, "ok")


    def testDziedzina(self):
        "Zle dane wejsciowe"
        self.assertRaises(lista4zad1.ArgumentNotIntegerError, lista4zad1.pierwsze_funkcyjna, "lala")
        self.assertRaises(lista4zad1.ArgumentNotIntegerError, lista4zad1.pierwsze_skladana, "l")
        self.assertRaises(lista4zad1.ArgumentNotIntegerError, lista4zad1.pierwsze_iterator, "1")


if __name__ == "__main__":
        unittest.main()
