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

/*
plik chyba latwo na stronce otworzyc, a wiec chcemy chyba zwrocic attachment (i od razu go gdzie zapisac??)
na princie tylko otwieramy plik i tyle -> PDF NIE TRZEBA
*/

function checkAllFields(body){
    var bool = true
    var ps = Object.getOwnPropertyNames(body);
    for( i = 0; i < ps.length; i++){
        obj = body[ps[i]]
        if(!obj){
            bool = false
        }
    }
    return bool
}


app.get( '/', (req, res) => {
    res.render('zad6')
})

app.post( '/print', (req, res) => {
    var bool = checkAllFields(req.body)
    if( bool ){
        var params = req.body
        //res.render('print', { params : params })
        //res.setHeader("Content-disposition", "")
        res.write('qwewqewqeqwe');
        res.end();
    }
    else{
        res.render( 'zad6', { message : "Niepoprawne dane" } )
    }
})
app.post('/download',(req,res) =>{
    //res.write("Pobieranie")
    //zapytaj jak zrobic Pobieranie
    // i jak zrobic tabelke ladnijesza?
    // inputy zeby byly krotsze
    res.setHeader('Content-disposition','attachment',filename = 'foo.txt')
    res.end()
})


app.use((req,res)=>{
    res.render('404',{url : req.url})
})

http.createServer(app).listen(3000);
console.log( 'serwer działa, nawiguj do http://localhost:3000' );