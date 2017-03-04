function forEach( a, f ) {
    for( var element of a){
        f(element);
    }
}
function map( a, f ) {
    result = [];
    for( var element of a){
        result.push( f(element) );
    }
    return result;
}
function filter( a, f ) {
    result = [];
    for( var element of a){
        if( f(element) ) result.push( element );
    }
    return result;
}
var a = [1,2,3,4];
forEach( a, _ => { console.log( _ ); } );
var b = filter( a, _ => _ < 3 );
console.log(b);
var c = map( a, _ => _ * 2 );
console.log(c);
var f1 = function(x){
    console.log(x);
}
var f2 = function(x){
    return x < 3;
}
var f3 = function(x){
    return 2*x;
}

forEach( a, f1 );
var b = filter( a, f2 );
console.log(b);
var c = map( a, f3 );
console.log(c);