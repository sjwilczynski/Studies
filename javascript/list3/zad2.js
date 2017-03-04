var fib1 = function(n){
    if(n < 2) return n;
    return fib1(n-1) + fib1(n-2);
}

var fib2 = function(n){
    if(n<2) return n;
    if(fib2[n]) return fib2[n];
    var result = fib2(n-1) + fib2(n-2);
    fib2[n] = result;
    return result;  
}
var fib3 = function(n){
    var a = 0;
    var b = 1;
    if( n<2 ) return n;
    for(var i = 2; i <=n; i++){
        var c = b;
        b = a + b;
        a = c;
    }
    return b;
}

console.time("Fibonacci bez spamietywania");
for(var i = 0; i < 30; i++){
    fib1(i);
}
console.timeEnd("Fibonacci bez spamietywania");
console.time("Fibonacci ze spamietywaniem");
for(var i = 0; i < 10000; i++){
    fib2(i);
}
console.timeEnd("Fibonacci ze spamietywaniem");
console.time("Fibonacci iteracyjny");
for(var i = 0; i < 10000; i++){
    fib3(i);
}
console.timeEnd("Fibonacci iteracyjny");
