var http = require('http');
var express = require('express');
var bodyParser = require('body-parser');
var cookieParser = require('cookie-parser');
var session = require('express-session')

var app = express();

app.use(bodyParser.urlencoded({ extended: true }));
app.use(cookieParser('sgs90890s8g90as8rg90as8g9r8a0srg8'));
app.use( session({
    secret : 'wwuirwueui', 
    saveUnintialized: true, 
    resave: false })
    )

app.set('view engine', 'ejs');
app.set('views', './lista5/widoki');

// wymaga logowania
app.get( '/', authorize, (req, res) => {
    res.render('zad7', { user : req.user } );
});

app.get( '/logout', authorize, (req, res) => {
    //usuniecie informacji z sesji
    delete req.session.user
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
        // zapisanie w sesji
        req.session.user = username
        // przekierowanie
        var returnUrl = req.query.returnUrl;
        res.redirect(returnUrl);
    } else {
        res.render( 'login', { message : "Zła nazwa logowania lub hasło" } );
    }
});

// middleware autentykacji
function authorize(req, res, next) {
    if ( req.session.user ) {
        req.user = req.session.user;
        next();
    } else {
        res.redirect('/login?returnUrl='+req.url);
    }
}

app.use((req,res)=>{
    delete req.session.user
    res.render('404',{url : req.url})
})

http.createServer(app).listen(3000);
console.log( 'serwer działa, nawiguj do http://localhost:3000' );