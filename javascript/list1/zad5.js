var iterFib = function(n){
    a = 0, b = 1;
    if( n == 0) return 0;
    if( n == 1) return 1;
    for(var i = 2; i <= n; i++){
        c = b;
        b = a+b;
        a = c;
    }
    return b;
}

var rekurFib = function(n){
    if(n == 0) return 0;
    if(n == 1) return 1;
    return rekurFib(n-1) + rekurFib(n-2);
}



for(var i = 10; i < 40; i++){
    console.time(i + ' - rekurencyjnie:');
    rekurFib(i);
    console.timeEnd(i + ' - rekurencyjnie:');
    console.time(i + ' - iteracyjnie:');
    iterFib(i);
    console.timeEnd(i + ' - iteracyjnie:');
}

/*
for(var i = 10; i < 40; i++){
    var start1 = Date.now();
    rekurFib(i);
    var end1 = Date.now();
    iterFib(i);
    var end2 = Date.now();
    console.log(i + ' - rekurencyjnie:' + (end1 - start1).toPrecision(5).toString() + '   ' + ',iteracyjnie:' + (end2 - end1).toPrecision().toString());
}
*/
