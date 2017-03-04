var http = require('http');
var express = require('express');
var bodyParser = require('body-parser');
var cookieParser = require('cookie-parser');

var app = express();

app.use(bodyParser.urlencoded({ extended: true }));
app.use(cookieParser('sgs90890s8g90as8rg90as8g9r8a0srg8'));

app.set('view engine', 'ejs');
app.set('views', './lista5/widoki');

// wymaga logowania
app.get('/' , (req,res) => {
    if(req.signedCookies.foo)
        res.render('zad3',{foo: req.signedCookies.foo}) 
    else
        res.render('zad3',{foo: 'brak'}) 
})
app.get('/addcookie', (req,res) => {
    res.cookie('foo','bar', {signed : true})
    res.send('addcookie')
})

app.use((req,res)=>{
    res.cookie('foo', '', { maxAge : -1 } ); 
    res.render('404',{url : req.url})
})


http.createServer(app).listen(3000);
console.log( 'serwer działa, nawiguj do http://localhost:3000' );

/*
Google Chrome dla systemu Windows
Wybierz klucz, ikona w prawym górnym rogu
Wybierz: Ustawienia
Kliknij na: Pokaż ustawienia zaawansowane...
Kliknij przycisk: Ustawienia treści w sekcji Prywatność
Zaznacz: Zezwalaj na przechowywanie danych lokalnie i kliknij: OK
*/