/*
console.log("przed")
setTimeout(function(){
    console.log("po 3 sek")
    setTimeout(function (){
        console.log("po kolejnych 2 sek")
    },2000)
}, 3000)
console.log("po")

function f1(){
    console.log("po 7")
}
function f2(){
    console.log("po 6")
}

setTimeout(f1,7000)
setTimeout(f2,6000)
*/
//event loop - kolejka fifo wskaznikow na kolejne fuckje ktore sa wykonywane
//////////////////////////////////////////////////////////////////////////////////////////////
//async/await

var fs = require("fs")
/*
fs.readFile("./text1.txt",'utf-8', function(err,data){
    if( err ) console.log(err)
    else console.log(data.toString())
})

var res = fs.readFileSync("./text1.txt", "utf-8")
console.log("res: " + res) //-uzywanie tej wersji obniza wydajnosc
console.log("po operacji plikowej")
*/

/// PROMISE
/*
var p = new Promise(function(resolve,reject){
    console.log("promise zaczyna sie wykonywac")
    setTimeout(function(){
        //resolve(17) //wynik
        reject(5)
    },2000)
})

p.then(function(data){
    console.log("then1: "+ data)
})   //wykonaj kiedy zakonczy sie caly promise ,moze byc takich duzo

p.then(function(data){
    console.log("then2: "+ data)
})
p.catch(function(data){
    console.log("blad: " + data)
})
*/
/*
function getFileContent(path,f){
    fs.readFile(path,'utf-8',function(err,data){
        if( err ) console.log(err)
        else f(data.toString())
    })
}
getFileContent("./text1.txt",function(data){
    console.log(data)
})
*/ //to samo z promise:
/*
function getFileContentAsync(path){
    return new Promise( function(res,rej) {
        fs.readFile(path, function(err,data){ 
        if(err) rej(err)
        else res(data.toString())
        })
    })
}

getFileContentAsync("./text1.txt")
    .then(function(data){
        console.log(data)
    })
    .catch(function(err){
        console.log(err)
    })


var p1 = getFileContentAsync("./text1.txt")
var p2 = getFileContentAsync("./text2.txt")
Promise.all([p1,p2])
    .then(function(data){
        console.log(data)
    })

*/

var http = require("http")
//http.createClient()
http.get('http://www.wp.pl',function(res){
    /*
    var buf = ''
    //eventemitter - rozne zdarzenia
    res.on('data',function(data){
        buf += data.toString()
    })
    res.on('end',function(){
        console.log(buf)
    })*/
    res.pipe(process.stdout) //hehehe ten sam efekt
})

var server = http.createServer(function(req,res){
    //request,response
    res.write("witam, zadanie do :" + req.url)
    res.end();
    //w przegladarce wpiasac localhost:3000/sciezka
}).listen(3000)