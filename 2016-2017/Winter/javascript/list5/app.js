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
app.get( '/', authorize, (req, res) => {
    res.render('app', { user : req.user } );
});

app.get( '/logout', authorize, (req, res) => {
    res.cookie('user', '', { maxAge: -1 } );
    res.redirect('/')
});

// strona logowania
app.get( '/login', (req, res) => {
    res.render('login');
});

app.post( '/login', (req, res) => {
    var username = req.body.txtUser;
    var pwd = req.body.txtPwd;

    if ( username == pwd ) {
        // wydanie ciastka
        res.cookie('user', username);
        // przekierowanie
        var returnUrl = req.query.returnUrl;
        res.redirect(returnUrl);
    } else {
        res.render( 'login', { message : "Zła nazwa logowania lub hasło" } );
    }
});

// middleware autentykacji
function authorize(req, res, next) {
    if ( req.cookies.user ) {
        req.user = req.cookies.user;
        next();
    } else {
        res.redirect('/login?returnUrl='+req.url);
    }
}

http.createServer(app).listen(3000);
console.log( 'serwer działa, nawiguj do http://localhost:3000' );