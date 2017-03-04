const fs = require('mz/fs')

fs.readFile('logs', 'utf8', function(err,data){
    console.log("Przeczytane z callbackiem:")
    if( !err ){
        var f = data.split(' ')
        console.log(f[0],f[1],f[2],f[3]);
    }
    else console.log(err)
})


function getFileContentAsync(path){
    return new Promise( function(res,rej) {
        fs.readFile(path, function(err,data){ 
        if(err) rej(err)
        else res(data.toString())
        })
    })
}

var p1 = getFileContentAsync('text1.txt')
var p2 = getFileContentAsync('text2.txt')


var res1 = Promise.all([p1,p2])
res1.then(function(data){
    console.log("Z PROMISAMI:")
})
res1.then(function(data){
    console.log(data)
})
res1.catch(function(err){
    console.log(err)
})

//roznica  - nie ma nieczytelnych zaglebien wszytsko wyglada asynchronicznie - latwiejsze do zrozumienia

var p3 = new Promise(function(resolve, reject) { 
    setTimeout(resolve, 50, "Resolve byl szybszy"); 
});
var p4 = new Promise(function(resolve, reject) { 
    setTimeout(reject, 100, "Reject byl szybszy");
});

var res2 = Promise.race([p3, p4])
res2.then(function(value){
    console.log("RACE:")
})
res2.then(function(value) {
    console.log(value)
}, function(reason) {
    console.log(reason); 
});
//odpalane z konsoli dziala inaczej nie barzdzo ;(