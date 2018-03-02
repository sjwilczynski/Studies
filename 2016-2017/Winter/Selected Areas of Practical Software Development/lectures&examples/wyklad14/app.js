//selektor
//unobstrusive

var http = require('http')
var express = require('express')
var bodyParser = require('body-parser')
var app = express()

app.set('view engine','ejs')
app.set('views','./wyklad14/views') //moze innaczej nazwe podac
app.use(express.static('./static'))

app.get('/',(req,res) =>{
    res.render('app');
});

var server  = http.createServer(app).listen(3000)

/*
position
static,
relative - wzgledem wyjsciowego polozenia
absolute - wzgledem rodzica pierwszego ktory ma inne niz static
fixed
*/