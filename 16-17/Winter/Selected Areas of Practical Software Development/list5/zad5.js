var http = require('http');
var express = require('express');
var bodyParser = require('body-parser');


var app = express();

app.use(bodyParser.urlencoded({ extended: true }));
app.use( express.static('./lista5/static'));

app.set('view engine', 'ejs');
app.set('views', './lista5/widoki');

app.get( '/GET',  (req, res) => {
    res.render('get');
});

app.get( '/POST',  (req, res) => {
    res.render('post');
});

app.get( '/GET/result',  (req, res) => {
    var say = req.query.say
    var to = req.query.to
    res.render('getResult', {say : say, to : to});
});

app.post( '/POST/result',  (req, res) => {
    var p1 = req.body.p1
    var p2 = req.body.p2
    res.render('postResult', {p1 : p1, p2 : p2})
});

app.use((req,res)=>{
    res.render('404',{url : req.url})
})

http.createServer(app).listen(3000);
console.log( 'serwer działa, nawiguj do http://localhost:3000' );