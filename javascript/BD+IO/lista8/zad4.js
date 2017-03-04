var http = require('http');
var express = require('express');
var bodyParser = require('body-parser');
var cookieParser = require('cookie-parser');

var app = express();
var pgp = require('pg-promise')();
var db = pgp("postgres://postgres:oczko@localhost:5430/postgres");
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cookieParser('sgs90890s8g90as8rg90as8g9r8a0srg8'));

app.set('view engine', 'ejs');
app.set('views', './lista8/views');

app.get('/', (req,res) => {
    res.render('main');
})

app.post('/', async (req,res) =>{
    try {
            res.contentType('text/html');
			//console.log(req.body.miejsce_name);
            var result = await db.query("select id from miejsce_pracy where name = $1", [req.body.miejsce_name]);
            if(result.length != 0){
                console.log(result);
                var miejsce_pracy_id = result[0].id;
                var res1 = await db.query('INSERT INTO OSOBA(name,surname,height, id_miejsce_pracy) VALUES ($1,$2,$3,$4)',
                [req.body.osoba_name, req.body.osoba_surname, req.body.osoba_height, miejsce_pracy_id]);
            } else{
                console.log('drugi flow')        
                var data = await db.tx(async function(t){
                    var q1 = await t.query('insert into miejsce_pracy(name) values ($1) returning id',[req.body.miejsce_name]);
                    var id = q1[0].id;
                    console.log('id',id);
                    var q2 = await t.query('insert into osoba(name,surname,height,id_miejsce_pracy) values($1,$2,$3,$4)',
                    [req.body.osoba_name, req.body.osoba_surname, req.body.height, id]);
                });                
            }
            res.write('Koniec');
            res.end();
		}
		catch ( err ) {
			console.log( err );
		}
});


http.createServer(app).listen(3000);

console.log( 'serwer działa, nawiguj do http://localhost:3000' );