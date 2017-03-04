function fib1(){
    var f0 = 0;
    var f1 = 1;
    return{
        next : function(){
            var fn = f0;
            f0 = f1 ;
            f1 = fn + f1;
            return {
                value : fn,
                done : false
            } 
        }
    }
}

function *fib2(){
    var f0 = 0;
    var f1 = 1;
    while(true){
        var fn = f0;
        f0 = f1 ;
        f1 = fn + f1;
        yield fn;
    }
}

var _it = fib1();
var f;
//for ( var _result; _result = _it.next(), !_result.done; ) {
//    console.log( _result.value );
    //f = _result.value;
//}
//console.log("Sprawdzam czy rzeczywiscie sie nie konczy");

for ( var i of fib2() ) { //ten sie da, tego drugiego nie
    console.log( i );
}