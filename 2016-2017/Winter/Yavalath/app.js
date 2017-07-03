var http = require('http');
var socket = require('socket.io');
var express = require('express');
var cookieParser = require('cookie-parser');

var app = express();
var server = http.createServer(app);
var io = socket(server);
var bodyParser = require('body-parser');
var path = require('path');
app.use(cookieParser('sgs90890s8g90as8rg90as8g9r8a0srg8'));
app.use( bodyParser.urlencoded({extended:true}) ) ;
app.use(express.static(path.join(__dirname, 'static')));
app.set('view engine', 'ejs');
app.set('views', './views');
		
var pgp = require('pg-promise')()
var cn = "postgres://postgres:oczko@localhost:5430/postgres"
var db = pgp(process.env.DATABASE_URL || cn)

function User(login, color, socket) {
    this.login = login;
    this.color = color;
    this.socket = socket;
}

function hsh(x, y)
{
    //weryfikacja czy ktoś nie podał nam niecałkowitych wartości?
    if(x > 8 || x < 0 || y > 8 || y < 0 || (Math.abs(x - y) > 4))
        return -1;
    return x + 9 * y;
}

function verifyData(username,password, confirmedPassword)
{
    var problem = db.query("select * from users where name = $1",[username])
    .then(data =>{
        if(data.length > 0){
            return 0;
        }
        return -1;
    })
    .catch(err =>{
        console.log(err);
        return 2;
    });

    return problem.then(result => {
        if(result != -1){
            return problem;
        }
        if(password != confirmedPassword)
            return -1;
        if(password.length < 3 || username.length < 3)
            return -2;
        return 1;
    })
    
}

var socketList = {}; // socket.id - key | username - value

var N = 81; // coord = x + 9 * y 

var AllGameStates = [];
for(var i = 0; i < 10; i++){
    AllGameStates.push(new Gamestate(i));
}

function Gamestate(id){
    this.id = id
    this.board = Array.apply(0, {length: N}).map(_ => -1, Number);
    this.whoseTurn = 0;
    this.user0 = undefined;
    this.user1 = undefined;
    this.isOver = 0;
    this.areTwoPlayers = 0;
}

function findUserBySocketId(gamestate, id){
    if(gamestate.user0){
        if(gamestate.user0.socket.id == id){
           return 0;
        }
    }
    if(gamestate.user1){
        if(gamestate.user1.socket.id == id){
            return 1;
        }
    }
}
function findGameNo(login)
{
    for(var i = 0; i < AllGameStates.length; i++){
        gamestate = AllGameStates[i];
        if(gamestate.user0){
            if(gamestate.user0.login == login){
                return i;
            }
        }
        if(gamestate.user1){
            if(gamestate.user1.login == login){
                return i;
            }
        }
    }
    return -1;
}

function resetGame(id)
{
    AllGameStates[id].board = Array.apply(0, {length: N}).map(_ => -1, Number);
    AllGameStates[id].whoseTurn = 0;
    AllGameStates[id].isOver = 0;
}

function exitRoom(username, id)
{
    resetGame(id);
    if(AllGameStates[id].user1 && AllGameStates[id].user1.login == username){
        AllGameStates[id].user1 = undefined;
    }

    if(AllGameStates[id].user0 && AllGameStates[id].user0.login == username)
        AllGameStates[id].user0 = undefined;
    AllGameStates[id].areTwoPlayers = 0;
}


function fullBoard(gamestate)
{
    for(var i = 0; i <= 8; i++)
        for(var j = 0; j <= 8; j++)
            if(hsh(i , j) != -1 && gamestate.board[hsh(i, j)] == -1)
                return false;
    return true;
}

function verify(x, y, gamestate)
{
    //↔↔↔↔↔↔↔ ustalone y, przejdź po x range(max(0, y - 4), min(8, y + 4))
    var cnt = 0;
    var color = -2;
    var threeInRow = false;
    var fourInRow = false;
    var tst = {
        x : x,
        y : y
    };
    for(var i = Math.max(0, y - 4); i <= Math.min(8, y + 4); i++)
    {
        if(gamestate.board[hsh(i, y)] >= 0 && gamestate.board[hsh(i, y)] == color)
            cnt++;
        else
        {
            if(cnt == 3)
                threeInRow = true;
            color = gamestate.board[hsh(i, y)];
            cnt = 1;
        }
        
        if(cnt == 4)
            fourInRow = true;

        var info = {
            threeInRow : threeInRow,
            fourInRow : fourInRow,
            cnt : cnt,
            color : color,
            test : "fst",
            i : i
        };
    }
    if(cnt == 3)
        threeInRow = true;
    color = -2;
    //↗↗↗↗↗ ustalone x, przejdź po y range(max(0, y - 4), min(8, y + 4))
    for(var i = Math.max(0, x - 4); i <= Math.min(8, x + 4); i++)
    {
        if(gamestate.board[hsh(x, i)] >= 0 && gamestate.board[hsh(x, i)] == color)
            cnt++;
        else
        {
            color = gamestate.board[hsh(x, i)];
            if(cnt == 3)
                threeInRow = true;
            cnt = 1;
        }
        if(cnt == 4)
            fourInRow = true;

        var info = {
            threeInRow : threeInRow,
            fourInRow : fourInRow,
            cnt : cnt,
            color : color,
            test : "nd",
            i : i
        };
    }
    if(cnt == 3)
        threeInRow = true;
    color = -2;
    //↘↘↘↘↘ x++, y++ range(-min(x,y), 8 - max(x,y))
    for(var i = -Math.min(x, y); i <= 8 - Math.max(x,y); i++)
    {
        if(gamestate.board[hsh(x + i, y + i)] >= 0 && gamestate.board[hsh(x + i, y + i)] == color)
            cnt++;
        else
        {
            color = gamestate.board[hsh(x + i, y + i)];
            if(cnt == 3)
                threeInRow = true;
            cnt = 1;
        }
        if(cnt == 4)
            fourInRow = true;

        var info = {
            threeInRow : threeInRow,
            fourInRow : fourInRow,
            cnt : cnt,
            color : color,
            test : "rd",
            i : i
        };

    }
    if(cnt == 3)
        threeInRow = true;

    if((threeInRow && fourInRow) || fullBoard(gamestate))
        return 2;//remis (chyba że chcemy inaczej)
    if(threeInRow)
        return (gamestate.whoseTurn ^ 1);//przegrana
    if(fourInRow)
        return gamestate.whoseTurn;//wygrana
    return -1; // gramy dalej
}

app.get('/', function(req, res) {
    res.redirect('/rooms');
});

app.get('/login', (req,res) =>{
    res.render('login');
})

app.post('/rooms', (req,res) =>{
    var username = req.body.username;
    var password = req.body.pwd;
    db.query('select * from users where name = $1',[username])
    .then( userdata => {
        console.log(userdata);
        if( userdata.length > 0){
            extractedUsername = userdata[0].name;
            extractedPassword = userdata[0].password;
            if(username == extractedUsername && password == extractedPassword){
                res.cookie('username', username, {signed : true});
                res.render('rooms',{AllGameStates : AllGameStates, username : username});  
            } else{
                res.render('login',{ message : "Zły login lub hasło" });
            }
        } else{
            res.render('login',{ message : "Zły login lub hasło" });
        }
    	})
    	.catch( err => {
        	 res.render('login',{ message : "Coś poszło nie tak - spróbuj jeszcze raz"});
    	});		
});

app.post('/login',(req,res) =>{
    var username = req.body.username;
    var passwd = req.body.pwd;
    var passwd2 = req.body.pwd2;
    var verify = verifyData(username, passwd, passwd2)
    verify.then( ver => {
        switch (ver){
            case 1:
                db.query("INSERT INTO users VALUES ($1,$2)",[username,passwd])
                .then( _ => {
                    res.render('login', {message : "Zostałeś zarejestrowany"});
                })
                .catch( err => {
                    res.render('register',{ message : "Coś poszło nie tak - spróbuj jeszcze raz" + err.toString()});
                })       
                break;
            case -1:
                res.render('register',{ message : "Hasła nie zgadzają się" });
                break;
            case -2:
                res.render('register',{ message : "Hasło lub login są niepoprawne - za krótkie" });
                break;
            case 0:
                res.render('register',{ message : "Podana nazwa użytkownika jest zajęta" });
                break;
            case 2:
                res.render('register',{ message : "Coś poszło nie tak - spróbuj jeszcze raz"});
        }   
    })
    verify.catch( err => {
        res.render('register',{ message : "Coś poszło nie tak - spróbuj jeszcze raz"});
    })
});

app.post('/register',(req,res) =>{
    res.render('register');
});

app.get('/register', authorize, (req,res) =>{
    res.render('register');
});

app.get('/rooms', authorize, (req,res) =>{
    res.render('rooms', {AllGameStates : AllGameStates, username : req.signedCookies.username});
});

app.get('/game:id', authorize, (req,res) =>{
    var id = req.params.id;
    console.log("request na game z parametrami:",id, req.signedCookies.username)
    if( AllGameStates[id].areTwoPlayers ){
        res.render('rooms', {AllGameStates : AllGameStates, username : req.signedCookies.username, message : "W tym pokoju nie ma miejsca"})
    } else{
        res.render('hexagon', {id:id, username : req.signedCookies.username});
    }
});

app.get('/results', authorize, (req,res) =>{
    db.query('select * from history where user1 = $1 or user2 = $1',[req.signedCookies.username])
    .then( data =>{
        res.render('results', { history : data });
    })
    .catch( err => {
        res.render('results', { message : "Coś poszło nie tak - spróbuj jeszcze raz" });
    });
    
});

app.get('/logout', authorize, (req,res) =>{
    res.cookie('username', '', {signed : true, maxAge : -1});
    res.render('login',{message : 'Wylogowano'})
});


function authorize(req, res, next) {
    if ( req.signedCookies.username != undefined ) {
        next();
    } else {
        console.log('nie bylo ciacha');
        res.redirect('/login');
    }
}


server.listen( process.env.PORT || 3000 );

io.on('connection', function(socket) {
    console.log('client connected:' + socket.id);
    
    socket.on('MyConnection', function(data) {
        var id = data.id;
        var username = data.username;
        console.log('in MyConnection with params:',id,username);
        socketList[socket.id] = username;
        gamestate = AllGameStates[id];
        if(gamestate.user0 == undefined){
            gamestate.user0 = new User(username, "#84ba7a", socket);
            socket.emit('firstPlayer');
        }
        else if(gamestate.user1 == undefined){
            gamestate.user1 = new User(username, "#f28237", socket);
            gamestate.areTwoPlayers = 1;
            gamestate.user0.socket.emit('secondPlayer', { user : gamestate.user0.login});
            socket.emit('secondPlayer',{ user : gamestate.user0.login});
        } 
    })
    socket.on('move',function(data){
        console.log('in move:');
        console.log(data.username);
        console.log(data.hex.coordinates.x, data.hex.coordinates.y);
        var x = data.hex.coordinates.x; 
        var y = data.hex.coordinates.y;
        var username = data.username;
        var userNo;
        var id = data.id
        var gamestate = AllGameStates[id];
        if(gamestate.areTwoPlayers == 1){
            if(username == gamestate.user1.login)
                userNo = 1;
            else if(username == gamestate.user0.login)
                userNo = 0;
            var i = hsh(x, y);
            if(i >= 0 && gamestate.board[i] == -1 && gamestate.whoseTurn == userNo && !gamestate.isOver)
            {
                gamestate.board[i] = userNo;
                var isEnded = verify(x, y, gamestate); // -1 gramy dalej | 0 - user0 win | 1 - u1 w | 2 - remis
                var opSocket = gamestate['user' + (userNo ^ 1)].socket;
                
                socket.emit('response', {isValid : true, hex: data.hex, color : gamestate['user' + userNo].color, 
                    user : gamestate['user'+ (userNo ^ 1)].login});
                opSocket.emit('response', {isValid : true, hex: data.hex, color : gamestate['user' + userNo].color,
                    user : gamestate['user'+ (userNo ^ 1)].login});
                if(isEnded == -1){
                    gamestate.whoseTurn = (gamestate.whoseTurn ^ 1);
                }
                if(isEnded == 0){
                    db.query("INSERT INTO history(user1, user2, winner) VALUES ($1,$2,$3)",
                    [ gamestate.user0.login, gamestate.user1.login, gamestate.user0.login])
                    .catch(err =>{
                        console.log("Problem przy zapisie wyniku do bazy")
                    });
                    socket.emit('endGame', {winner : gamestate.user0.login, looser : gamestate.user1.login});
                    opSocket.emit('endGame', {winner : gamestate.user0.login, looser : gamestate.user1.login});
                    gamestate.isOver = 1;
                }
                if(isEnded == 1){
                    db.query("INSERT INTO history(user1, user2, winner) VALUES ($1,$2,$3)",
                    [ gamestate.user0.login, gamestate.user1.login, gamestate.user1.login])
                    .catch(err =>{
                        console.log("Problem przy zapisie wyniku do bazy")
                    });
                    socket.emit('endGame', {winner : gamestate.user1.login, looser : gamestate.user0.login});
                    opSocket.emit('endGame', {winner : gamestate.user1.login, looser : gamestate.user0.login});
                    gamestate.isOver = 1;
                }
                if(isEnded == 2){
                    db.query("INSERT INTO history(user1, user2, winner) VALUES ($1,$2,$3)",
                    [ gamestate.user0.login, gamestate.user1.login, 'draw'])
                    .catch(err =>{
                        console.log("Problem przy zapisie wyniku do bazy")
                    });
                    socket.emit('draw', {});
                    opSocket.emit('draw', {});
                    gamestate.isOver = 1;
                }
            }
            else{
                socket.emit('response', {isValid : false});
            }
        }
    });
    socket.on('reset', function(data){
        console.log('jestem w reset z params:', data.id, data.username);
        var id = findGameNo(data.username);
        if(id != -1 && AllGameStates[id].areTwoPlayers)
        {
            resetGame(id); 
            var userNo;
            if(AllGameStates[id].user0.socket.id == socket.id) 
                userNo = 0;
            else
                userNo = 1;
            var opSocket = AllGameStates[id]['user' + (userNo ^ 1)].socket;
            opSocket.emit('reset', {user : AllGameStates[id].user0.login});
            AllGameStates[id]['user'+ userNo].socket.emit('reset', {user : AllGameStates[id].user0.login});
        }
    });
    socket.on('disconnect', function() {
        console.log('Disconnect!');
        var username = socketList[socket.id];
        var id = findGameNo(username);
        if(id != -1)
        {
            delete socketList[socket.id];
            if(AllGameStates[id].areTwoPlayers){
                var userNo = findUserBySocketId(AllGameStates[id], socket.id);
                var opSocket = AllGameStates[id]['user' + (userNo ^ 1)].socket;
                opSocket.emit('user disconnected');
                if(!AllGameStates[id].isOver){
                    db.query("INSERT INTO history(user1, user2, winner) VALUES ($1,$2,$3)",
                    [ AllGameStates[id]['user' + userNo].login, AllGameStates[id]['user' + (userNo ^ 1)].login, AllGameStates[id]['user' + (userNo ^ 1)].login])
                    .catch(err =>{
                        console.log("Problem przy zapisie wyniku do bazy")
                    });
                }
            }
            exitRoom(username, id);
        }
    });
});

console.log( 'server listens' );

/*
Problem:
request na url wykonuje sie przed disconnectem wiec na rooms widzi sie stan gry jakby sie bylo w tej z ktorej sie wyszlo
glupie rozwiazanie: dodac sztuczny url przed getem na rooms
*/


/*
Dodatkowe:
    1.czat moze byc fajny - na prawo od planszy jest miejsce
    2.podswietlenie ostatniego ruchu - przydatne
    3.czyja tura - jeszcze kwadracik z kolorem goscia
    4.dorobic te kolorowe hexagony na stronach poza game
    5. losowanie gracza zaczynającego rozgrywkę
*/