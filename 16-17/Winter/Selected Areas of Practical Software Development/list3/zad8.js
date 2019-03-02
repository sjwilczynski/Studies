"use strict"

function *fib(){
    var f0 = 0;
    var f1 = 1;
    while(true){
        var fn = f0;
        f0 = f1 ;
        f1 = fn + f1;
        yield fn;
    }
}

function *take(it, top){
    var state = 0;
    while( state < top ){
        state++;
        yield it.next().value;
    }
}

var iterator = take(fib(), 10)
for ( let num of take( fib(), 10 ) ) {
    console.log( num );
}