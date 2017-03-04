"use strict"
var sum = function(){ //- moze byc dowolna liczba argumentow
    let result = 0;
    for( var arg of arguments){
        result += arg;
    }
    return result;
}

function sum1(...a){
    let result = 0;
    for(var arg of a){
        result += a
    }
    return result
}

console.log(sum(1));
console.log(sum(1,2,3,4,5));
console.log(sum1(1,6,78));
console.log(sum(100,32,1421,3464,5685685));