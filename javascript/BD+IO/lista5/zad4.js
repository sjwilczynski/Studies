var http = require('http');
var express = require('express');
var bodyParser = require('body-parser');
var cookieParser = require('cookie-parser');
var session = require('express-session')

var app = express();
app.use( session({secret : 'wwuirwueui', saveUnintialized: true, resave: false }))
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cookieParser())

app.set('view engine', 'ejs');
app.set('views', './lista5/widoki');

// wymaga logowania
app.get('/' , (req,res) => {
    if(req.session.foo)
        res.render('zad4',{foo: req.session.foo}) 
    else
        res.render('zad4',{foo: 'brak'}) 
})
app.get('/addsession', (req,res) => {
    req.session.foo = "obiekt sesji"
    res.send('added')
})

app.use((req,res)=>{
    delete req.session.foo
    res.render('404',{url : req.url})
})


http.createServer(app).listen(3000);
console.log( 'serwer działa, nawiguj do http://localhost:3000' );
