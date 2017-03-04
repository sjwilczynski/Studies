

var http = require('http')
var express = require('express')
var morgan = require('morgan') //zobaczyc na konsolce zadania
var bodyParser = require('body-parser')
var cookieParser = require('cookie-parser')
var session = require('express-session')
var app = express()

app.set('view engine','ejs')
app.set('views','./widoki') //moze innaczej nazwe podac
app.use(morgan('dev'))
app.use(express.static('./static')) // zadania do satycznyhc plikow w folderze static
app.use(cookieParser())
app.use( session({secret : 'wwuirwueui', saveUnintialized: true}))
//cookieparser
//sesisonparser
//socketio
app.use(bodyParser.urlenoded({extended:true})) // do postow
//dodatkowe info w przegladarce

app.get('/',authorize , (req,res) => {
    if(req.cookies.foo)
        res.render('app1',{foo: req.user}) //req.session.foo
    else
        res.render('app1',{foo: 'brak'}) 
})
app.get('/addcookie', (req,res) => {
    res.cookie('foo','bar')
    req.session.foo = 'bar5'
    res.send('addcookie')
})
//ciastka sa wspoldzielone miedzy kartami oknami
//raz udostepnione ciastko , jest wysylane prez przegladarke caly czas
//atak : cross site request forgery
//ograniczenie czasu zycia ciastka
//zeby podawac parametry w wartosci ciastka trzeba je zabezpieczyc przed szyfrowanie:
//szyfrowanie,podpisywanie -> mozna dac ten klucz w konstruktorze cookie parsera
//req.signedCookies
//maksymalna dl ciastka - 4kb
//liczba ciastek - 300-900/1000 ale duzo ciastek znacznie obniza performance aplikacji

app.get('/login', (req,res) =>{
    var options = [
        {value : 1, name : ble}
    ]
    res.render('login.ejs', {options : options})
})
app.post('/login',(req,res)=>{
    var username = req.body.txtLogin
    var passwd = req.body.txtPassword

    if(username == 'costam'){ //sprawdzanie poprawnosci
        res.cookie('user',username) //wypadaloby podpisac
        var returnUrl = req.query.returnUrl
        res.redirect('returnUrl')
    } 
    else{
        res.render('login.ejs', {message: 'zle'})
    }
})

//----------------------------------------
function authorize( req,res,next ){
    if(req.cookies.user){
        req.user = req.cookies.user //konwencja zeby ulatwic uzywanie user przez inne middlleware
        next()
    } else{
        res.redirect('/login?retrunUrl='+ req.url)
    } 
}

//-----------------------------

//WSZYTSKIE USE NA KONIEC
app.use((req,res)=>{
    res.render('404',{url : req.url})
})

var server  = http.createServer(app).listen(3000)


//mechanizm sesji na serwerze
//cookie z id sejsi czyli slowniku dla uzytkownika
//atak : session highjacking
//sesje mozemy przechowywac w bazach, plikach, zewnetrznych serwerach bo inaczej za duzo pamieci zajete u nas


//jak automatycznie przekierowan na strone logowania?
//np glowna storna wymaga logowania lub z palca wpisal link ktory wymaga

//skad wiemy ze zalogowany -> ma ciastko, albo jest cos w konterze sesji

//ciastko usuwamy wydajac nowe ciatko z ta sama nazwa ale data z przeszlosci
//przekierowanie -> res.redirect albo wlasny middleware