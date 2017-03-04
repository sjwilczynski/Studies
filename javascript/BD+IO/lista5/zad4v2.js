var http = require('http');
var express = require('express');
var bodyParser = require('body-parser');
var cookieParser = require('cookie-parser');
var session = require('express-session')
var FileStore = require('session-file-store')(session);
 

var app = express();
app.use(session({
    store: new FileStore,
    secret: 'keyboard cat',
    saveUnintialized: true, 
    resave: false
}));
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cookieParser())

app.set('view engine', 'ejs');
app.set('views', './lista5/widoki');


app.get('/', function (req, res) {
  if (req.session.views) {
    req.session.views++;
    res.setHeader('Content-Type', 'text/html');
    res.write('<p>views: ' + req.session.views + '</p>');
    res.end();
  } else {
    req.session.views = 1;
    res.end('Welcome to the file session demo. Refresh page!');
  }
});

app.use((req,res)=>{
    delete req.session.foo
    res.render('404',{url : req.url})
})


http.createServer(app).listen(3000);
console.log( 'serwer działa, nawiguj do http://localhost:3000' );
