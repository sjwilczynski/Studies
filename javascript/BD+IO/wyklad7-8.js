//protokol komunikacyjny - http
//protokol transportowy - tcp ip
//sockety - zeby wiedziec kiedy skonczyc nasluchiwac podaje sie rozmiar komunikatu
//proxy - forma posrednia - najpierw do proxy proxy do serwera serwer do proxy i do mnie
//narzedzia do przegladania zadan w przegladarce - firebug, telerik fiddler web debugger - windows(debugger warstwy http, lokalne
//proxy (gloablne -> kazda przegladarka je respektuje)), 


//naglowki : para klucz wartosc
//tcp ip ma potwierdzenia, najpierw naiwazuje polaczenia a serwer potwierdza 
//udp ip -> bez potwierdzenia - niezaufany
//protokol dns -> zamiana naglowkow hostow na ip i odwrotnie
//witryna ma wiele stron
//zadanie get a zadanie post:
//get : potrzebny host->zeby miec ip serwera i nie musiec parsowac adresu(w p.p jedna stona na jednym serwerze) , reszta to dodatkowe naglowki
//odpowiedzi serwera: 200 - wszystko spoko, naglowek expires - daje date i do tej daty strona jest czytana z dysku a nie z sieci
//naglowek content length
//w get odczytywanie naglowkow az do dwoch nowych lini - hehehe beka jak proste
//jesli naglowka content length nie ma to serwer zle napisany
//ale gniazdo tcp ma timeout - po tym czasie wyjatek - domysle 60sek
//naglowki odgraniczone do 64KB(adres 4KB) jak sobie poradzic jak chcemy wiecej przeslac(np plik)
//po to zadanie typu post -> wyglada jak odpowiedz serwera, tez maja naglowek content length, a potem ida dane


//html: tagi maja podtagi i atrybuty
//zeby robic posty:
//w html mamy tag <form > ,wszystko co tam bedzie jest wrzucane do post( chyba ze bardzo krotkie to doklejane do żądania)
// w tym narzedziu mozna debuggowac zadania przegladarki przechodza przez niego, w lewym dolnym rogu pole na ktorym mozna wyklikac czerwone strzalki
//ryzyko ktos moze nam zmieniac odpowiedzi serwera : modyfikuje tresci zadan lub odpowiedzi -> man in the middle
//mnostwo komputerow po drodze - wiele okazji zeby podsluchiwac
//jesli po drodze kilkanascie komputerow to spoko - wiecej to wolno
//korporcje swiatlowodowe tak keiruja zadania ze zawsze ktos moglby podsluchiwac
//po to protokol https - szyfrowany - kazdy pakiet wymieniany jest szyfrowany
//kryptografia asymetryczna -> klucz publiczny, prywatny , rsa
/*
protokol ssl

jak wiedziec ze proxxy czasem nie podmienia nam swojego certyfiaktu?
sciezka certyfikacji - certyfiakt dla danej domeny o okreslonej waznosci
skad wiadomo jakie sa certyfikaty -> sa instytucje ktore wydaja certyfikaty
certyfiakt enterprise pokazuje nazwe podmiotu
klikasz na zielona klodke zeby zobaczyc dla kogo wydac certyfikaty
odcisk palca weryfikuje czy to dobry certyfikat(ale w sumie i tak nie wiemy ktory jest dobry)

//npm init -y generuje plik package.json
//npm install express (moze lepiej globalnie)
*/

var fs = require('fs')
var http  = require('http')

/*
http.createServer(function(req,res){
    fs.readFile('text1.txt','utf-8', function(err,data){
        if(err) res.write(err)
        else res.write(data)
        res.end()  
    })
}).listen(3000)
*/
//dalej dziala tylko z konsoli

//aby uzyc wielu rdzeni -> node cluster

//port 80, 443 - http, https juz nie mozemy uzyc bo zajety
/*
//framework express
bezstanowosc, ciastka
*/
/*
var express = require('express')
var app = express()
//trzeba zainstalowac express
app.set('view engine','ejs') //??
app.set('views', '/widoki') //??
app.get('/',function(req,res){
    res.write('root')
    res.end()
})
app.get('/login',function(req,res){
    res.write('login')
    res.end()
})

http.createServer(app).listen(3001)
*/
//middleware - funkcja(req,res,next) -> lancuch kolejno wywolujacych sie funkcji
//vsc fajnie pokazuje css
//chain of responsibility

/*moj middleware
app1.use(function(req,res,next){
    res.write('1')
    next()
})
app1.use(function(req,res,next){
    res.write('2')
    res.end()
})
*/

//framewotki: ejs,jade
//pulpit zdalny - remote desktop protocol -zeby nie zajmowac sobie pamieci
//dobrze napisana aplikacja zajmuje stala ilosc pamieci
/*
kazde zadanie traktujemy w sposob niezaufany

*/

//plik package.json --> npm init -y zeby nie trzeba bylo commitowac node_modules\\
//dlaczego?? ZAPYTAJ

var http = require('http')
var express = require('express')
var morgan = require('morgan') //zobaczyc na konsolce zadania
var bodyParser = require('body-parser')

var app = express()

app.set('view engine','ejs')
app.set('views','./widoki') //moze innaczej nazwe podac
app.use(morgan('dev'))
//cookieparser
//sesisonparser
//socketio
app.use(bodyParser.urlenoded({extended:true})) // do postow
//dodatkowe info w przegladarce
/*
app.use((req,res,next) => {
    res.write('1')
    next()
    if(res.foo)
        res.write(res.foo.toString())
    //if(res.finished)
    res.end()
})
app.use((req,res,next) => {
    res.write('2')
    res.foo = 5
})
*/
app.get('/',(req,res) => {
    
    var model = {
        f1 : 1,
        f2 : 'f2'
    }
    var przelewy = [
        {kwota:123, data : '11', id : 1},
        {kwota:124, data : '12', id : 2},
        {kwota:125, data : '13', id : 3},
    ]
    res.render('app', {przelewy : przelewy, model : model })
    //res.redirect('/foo?p1=v1')
})

app.get('/foo',(req,res)=>{
    if(req.query.p1){
        res.render('foo', { p: res.query.p1})
    }   //tu parametry
     else res.render('foo')
}) //do zadania z paskiem deklaracji

app.get('/przelew/:id',(req,res)=>{
    //wyciagamy z res.params.id znak zapytania - parametr niewymagany
    var przelew = {
        id : req.params.id,
        data : 'qjgjqhwg'
    }
    //piszemy dodatkowy kod sprawdzajacy legalnosc zadania
    res.render('przelew',{przelew : przelew})
//query string tempering - atak na pasek adresowy 
//niby porprawia f haszujaca ale nastepny atak : rainbow table attack
//zabezpieczenie salt - dodatkowy losowy string znany tylko na serwerze


/*
generowanie np pdfa
Conetent-Type : text/html
Content-disposition: text/attachment ???

res.setHeader('Content-disposition','attachment',filename = 'foo.txt')
res.write('123')
res.end()
*/
})

app.get('/login', (req,res) =>{
    res.render('login.ejs')
})
app.post('/login',(req,res)=>{
    var username = req.body.txtLogin
    var passwd = req.body.txtPassword

    if(username == 'costam'){
        res.redirect('/')
    } 
    else{
        res.render('login.ejs')
    }
})

//WSZYTSKIE USE NA KONIEC
app.use((req,res)=>{
    res.render('404',{url : req.url})
})

var server  = http.createServer(app).listen(3000)