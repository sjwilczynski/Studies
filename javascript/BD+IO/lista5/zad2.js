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
app.get( '/',  (req, res) => {
    var options = [
			{ value : 1, text : 'elem1' },
			{ value : 2, text : 'elem2' },
			{ value : 3, text : 'elem3' },
		]
    var foo = 'superstring'
    res.render('zad2',{options : options, foo : foo });
});

app.use((req,res)=>{
    res.render('404',{url : req.url})
})

http.createServer(app).listen(3000);
console.log( 'serwer działa, nawiguj do http://localhost:3000' );