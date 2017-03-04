var http = require('http');
var express = require('express');
var bodyParser = require('body-parser');
var cookieParser = require('cookie-parser');

var app = express();
var pgp = require('pg-promise')();
var db = pgp("postgres://postgres:oczko@localhost:5430/postgres");

app.get('/', async (req,res) =>{
    try {
			
			var recordset = await db.query('select * from osoba1')	
			res.contentType('text/html');
			recordset.forEach( r => {
				res.write( `${r.id}, ${r.name}, ${r.surname}, ${r.sex}, ${r.height} <br/>`);
			});
			res.end();
		}
		catch ( err ) {
			console.log( err );
		}
});


http.createServer(app).listen(3000);

now1 = async function(){
    try{
        var data = await db.query('insert into osoba2(name,surname,sex,height) values($1,$2,$3,$4) returning id',['maciej','z klanu', 'male', 198])
        console.log(data[0].id)
    } catch(err){
        console.log( err );
    }
};

now2 = async function(){
    try{
        var sequence = await db.query("select * from nextval('ids')")
        var data = await db.query('insert into osoba1(id,name,surname,sex,height) values($1,$2,$3,$4, $5)',[sequence[0].nextval,'maciek','z klanu', 'male', 198])
        console.log(sequence)
    } catch(err){
        console.log( err );
    }
};

now3 = async function(){
    try{
        var data = await db.query("update osoba1 set height = 188 where name = 'maciek'");
        console.log(data)
    } catch(err){
        console.log( err );
    }
};

now4 = async function(){
    try{
        var data = await db.query("delete from osoba1 where name = 'maciek'");
        console.log(data)
    } catch(err){
        console.log( err );
    }
};
//now4();
console.log( 'serwer działa, nawiguj do http://localhost:3000' );