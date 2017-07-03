var isPrime = function(number){
    if( number == 2) return true;
    if(number % 2 == 0) return false;
    for(var i = 3; i*i <= number; i+=2){
        if( number % i == 0) return false;
    }
    return true;
}

var primes = function(range){
    array = [];
    for(var i = 2; i < range+1; i++ ){
        if(isPrime(i)){
            array.push(i)
        }
    }
    return array;
}
console.log(100);
console.log(primes(100000));

