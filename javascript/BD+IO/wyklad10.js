/*
heroku do deployowania aplikacji:
potrzebujemy hostowac apke i miec repozytorium(git,svn)

svn -> lokalnie to tylko obraz tego co na serwerze, zeby miec zmiany trzeba zrobic commit na serwer,
git -> lokalnie mam repozytorium do ktorego coomituje zmiany, push na serwer(wyslanie zmiany), tak naprawde 
serwer jest opcjonalany, może byc nawet wiecej niz jeden
git ma lepsze wsparcie techniczne
----------------------------------------------------------------
heroku
zdalny hosting:
    - upload skompresowanego obrazu aplikacji  
    - zdalny serwer potrzebuje wskazania repozytorium svn/git
    - usluga hostingu od razu jest repozytorium kodu

na heroku mam sciezke do tego repozytorium

piszemy apke

jak daje npm init -y w terminalu to szytko super (prawy na nazwa folderu)

potem ustawiamy port i tak dalej odpowiednie ustawienia -> plik .gitignore

teraz inicjujemy gita:
 git init


potrzebujemy heroku terminalu ( na stronie jest instrukcja -> potrzebny do logowania)
git add
git commit -m "commit"

teraz uzyjemy tego heroku terminal do uwierzytelnienia na heroku
heroku login
heroku create

i super teraz git push heroku master(glowny branch) //heroku to teraz alias na sciezke mojej apki

z visual studio mozemy fajnie commity robic i pushe 

udostepnianie komus
zakladka acces collaborators ->
ziomek robi heroku login
a potem git clone i zajebiscie 
---------------------------------------------------------------------------------------
biblioteka socket.io

dlaczego serwer nie moze nic wywslac do klienta???
klient jest routowalny tylko z sieci wlasnej lokalnej( nie ma adresu IP zeby skomunikowac sie z klientem)

jest jednak pewne oszustwo:
klient moze otworzyc gniazdo sieciowe z maksymalnie mozliwym dlugim timeout
klient wykonuje recv i czeka na odpowiedz od serwera, jesli serwer tylko utrzymuje polaczenia to jest to co chcielismy uzyskac


websocket, https://
przegladarka nawizauje zwykle polaczenie https:// upgrade ( i od tego momentu nawiazuja polaczenie socketowe)
-----------------------------------------------------------------------------------------------
jest technologia ktora nam to ulatwia
fajny socket.io bo klient i serwer wygladaja tak samo
na kliencie jest cala duza biblioteka ktora musi dolaczyc - bo stare przeladarki nie ogarniaja socketowe
na kliencie obietk window reprezentujacy obiekt przegladarki
window ma obiekt document

--------------------------------
chat: nadawca nadaje na serwer, serwer broadcastuje do wszytskich, nadawca obiera jak pozostali

mozna edyowac aly czas innerHtml albo dodac div( oszczedzanie wymiany pamieci)
append message oddatkowa funkcja
----------------------------------------------
takie aplikacje duzo bardziej skalowalne bo nie trzeba co chwile odpytywac serwera
------------------------------------------------------------------------------------------------
AJAX
Single Page Application - jeden widok typu html
dolaczamy javascript :
    -renderwoanie zawartosci aplikacja
        frameworki do genererowania  zawartosci:
        *AngularJS 
        *react
        *vue
        *extjs - wytwarzanie aplikacji 
                -duzo kontrolek
    - komunikacja z serwerem (przesylanie danych)
        * asynchronous javascript and xml , teraz juz sa JSON, JON.parse
        * tak naprawde uzywa sie AJAJ ------- and JSON
        * AHAH - asynchronous http and html
        

*/