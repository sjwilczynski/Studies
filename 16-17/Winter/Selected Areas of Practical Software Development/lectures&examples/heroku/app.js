var http = require('http');
var express = require('express');

var app = express();


app.set('view engine', 'ejs');
app.set('views', './heroku/widoki');



app.get( '/', (req, res) => {
    res.render('app', {message: "dane"})
})


app.use((req,res)=>{
    res.render('404',{url : req.url})
})


//zeby dziala na serwerze musimy ustawic port ktory da nam heroku
//piszemy wiec
http.createServer(app).listen(process.enc.PORT || 3000);
console.log( 'serwer działa, nawiguj do http://localhost:3000' );