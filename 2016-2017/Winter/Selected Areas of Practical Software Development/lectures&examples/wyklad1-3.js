/* 
// wyklad 2
console.log('qme');

//z var lokalme bez globalne
var s = 5;
console.log(s);

s = 'qme';
console.log(s);

var a = {
    city : 'wroclaw'
}

var p = {
    name : 'jan',
    age : 23,
    address : a,
    say : function(){
        console.log('My name is ' + this.name);
    },
    //dwie zarezerowane
    toString : function(){

    },
    valueOf : function(){

    } 
}

//zarezerwoane zeby pisac : p+q, !p ,p[q], itp

var q = {
    
}
var r = {

}

var q1 = {
    valueOf : function() {
        return 3;
    },
    toString : function(){
        return 'q1';
    }
}

var q2 = {
    valueOf : function() {
        return 2
    }
}


console.log( q + r ); // ciekawe
//wzielo sie z 
p.toString() 

console.log( p.say() )
console.log( p.address.city );

// null != undefined

console.log(p.name);
console.log(p.nmae);


var tab = [1,2,3,4];

//jak haszmapa
p.name;
p['na' + 'me'];

//jeszcze jest instanceof - operator


a = 'qwe';
if( a == 'qwe' ) ;    //if(false == 0) -> true
if( a === 'qwe' ) ;   // if( false === 0) -> false

if( !p.name ); //?? zastepuje (p.name != null)
*/

//wyklad 3

//zawsze mozna sprobowac wywolac pole jako funckje

var p ={
    foo : 1,
    bar : function(){
    }
}

try{
    //throw 1;
    throw new Error('qwe'); //komentuj iodkomentuj rozne
    //p.foo();
} catch(e){
    if(e instanceof Error){
        console.log('dupa ma dla cb info: ' + e.message);
    }
}
finally{
    //wykonuje sie niezaleznie czy zlapal wyjatek czy nie
}

//properties (wlasciwosci - cos miedzy metoda a polem)
// akcesor get - p.qux ,set - p.qux = 1 

var p ={
    foo : 1,
    bar : function(){
    },
    get qux(){
        return 17;
    },
    set qux(n){
        console.log(n);
    }
}
 console.log(p.qux);
 p.qux = 123;
 console.log(p.qux);

//dyanmiczne dodawanie pol i metod
 p.foo2 = 123;
 p.barbar = function(){
     return p.foo2;
 };

 //dynamiczne dodawanie properties?? - chyba nie;

 //console.log(Object.keys(p));
 //console.log(Object.getOwnPropertyNames(p));

var ps = Object.getOwnPropertyNames(p);
 //for( o : Object.getOwnPropertyDescriptor(p) )
 for(var i = 0; i < ps.lenght; i++){
     console.log( ps[i] + ' ' + p[ps[i]]);
 }

 // W JS wszytsko jest obiektem - funkcje tez nie, nie ma czegos takeigo jak static - sa wlasciwosci obiektow
//Object to singleton
 Object.defineProperty(p,'qux2',{
     value : 123,
 });

  Object.defineProperty(p,'qux3',{
     get : function(){
         return 199;
    },
 });


// iterowanie po obiektach ^^
a = [8,9,10];
// do zwiekszania tablicy uzywaj pusha
a.foo = 1;

for( var i in a ){  // Object.keys
    console.log(i); // po kluczach
}
for (var i of a){
    console.log(i); // po wartosciach
}

// jesli teraz damy
//a[200000000] = 5;
//to sie uda i zarezerwujemy w pizdu pamieci bez sensu a[123] to undefined

//mapa

a.map(function(n){
    return 2*n;
})
.forEach(function(n){
    console.log(n);
});
console.log(a);

/* lamba wyrazenia
a
.map( (x,t) => x*2+t)
.forEach(x => console.log(x));
*/

//modyfikowanie tablicy: slice, splice

a.splice(1,1,17,18);
console.log(a);

//list comprehension z pythona - moze jeszce nie dzialac - bo np stary node czy cos
//var b = [for (x of a) x*2];

//Funckje
//typy proste sa przekazywane przez wartosci, ale:
// obiekty tez, tzn wartoscia obiektu jest jego referencja
var q = {
    name : 'jan',
}

function foo(a){
    a.name = 'tomasz';
}

foo(q);
console.log(q);

//function scope

function hehehhe(a){
    {
        {
            {
                var i = 0// ta zmienna jest widoczna w calej funckji
                let j = 0 // ta widoczna tylko w najblizszych wasach
                k = 0 // zmienna globalna ( w node przez global.k, w przegladarce window.k)
            }
        }
    }
}
//zalecane: pisz let zamiast var zeby nie miec problemu ze scopami
//funkcyjnosc - mozna przekazywac funkcje do funckji i z funkcji zwracac funkcje

function f(g){
//    g();
}
f(function(){
    console.log(123);
})

function h(){
    return function(n){
        console.log(n);
    }
}
h()(5);


//fajna silnia
//memorization

function silnia(n){
    if(n<2) return 1;
    else{
        if(silnia[n]) return silnia[n];
        else{
            var result = n * silnia(n-1);
            silnia[n] = result;
            return result;
        }
    }
}
console.log(silnia(20));

function f(a,b,c){
    //domyslne wartosci dla argumentow - jesli undefined przypisze to po lewej stronie - problem dla przekazywani 0 i pustej listy
    a = a || 1;
    b = b || 2;
    c = c || 3;
}
console.log( f.toString() ); //hheehhehehehehhehe - czy tak w przegladarce mozna wszytsko podgladac??
var funkcja = new Function('a','b','return a+b');

//domkniecie - http://blog.nebula.us/13-javascript-closures-czyli-zrozumiec-i-wykorzystac-domkniecia
function sumujaca(a){
    return function(b){
        return a+b;
    }
}
//function sum(...a) - moze byc dowolna liczba argumentow

var sum4 = sumujaca(4);
console.log(sum4(7));

function sum2(a){
    var _acc = a;

    var f =  function(n){
        _acc += n;
        return f;
    }
    f.valueOf = function(){
        return _acc;
    }
    return f;
}

console.log(sum2(3)(5)(7)+1);

//funkja bedaca kreatorem funkcji - przyklad ze domkniecia nie zawsze dzialaja tak jak trzena

/*
var ppp = {
    foo : 1;
    bar : 2; 
}
for(var i of ppp) cos - wyjatek bo of tylko dla enumerowalnych
*/
var gen = function(n){
    var state = 0;
    return {
        next : function(){
            return{
                value : state,
                done : state++ > n
            }
        }
    }
}

var pppp = {
    [Symbol.iterator] : function(){
        return gen(10);
    }
}  // dodaje enumerator
/*
var _g = gen(10);
for( var result ; result = _g.next(),
  !result.done; ){
      console.log(result.value);
  }
*/
 //przepisanie paskudy - definicji gen
 var gen1 = function*(n){
     for(var i =0 ; i < n; i++)
        yield i;
 } // ogarnij jak dziala - * zezwala na yield