//niestety przegladarka nie ma np. require
// co w takim razie zrobic
// np uzyc commonjs
//mozna napisac w node a potem uzyc technologii browserify do polaczaenia plikow przed wrzuczeniem na serwer
//lepiej jedno zadanie po duzy plik niz wiele po maly
var fs = require('fs') //filesystem
//teraz mamy dostep do kodu z tego modulu

//co zrobic zeby widziec funkcje w tym fs - zeby podpowiadal tak fajnie jak w javie
//typescript - tam sa typy i moze fajnie by bylo jakby srodowisko patrzylo na odpowiadacjacy kod typescriptowy
//projekt definitely typed(github) - tam technologie sa napisane w typescirpcie
//jak w takim jazie powiedziec VSC zeby uzywalo tego typesciptu
//npm install typings -g -zainstaluj sobie
//mozliwe ze beda porzebowal jsconfig.json
//wpisuje typings install dt~node --global --save

//zrestartowac visual studio i juz powinnismy widziec fs. podpowiedzi

//w innych jezylach fread(f,..) - blokuje -> kod ponizej sie nie wykonuje
//mechanizm completion portd - mechanizm przerwan
//w inncyh jezykach tak naprawde przz przerwania te funkcje sa asynchroniczne, ale z pewnych
//powodow interfejs jezyka programowania udaje ze jest to wywolanie synchroniczne
//dlaczego? -> skaldnia -> nie bylo dobrego pomyslu jak opisac wywolania asynchroniczne
//sa funckcje zwrotne -> AsyncCallaback jak w javie

//problem w js -  callback hell