//var s  = 'qwewqeqw \ 
//eqwe\';
// backslash - string na wiele lini ale u mnie cos nie dziala

//szablonowanie
// C printf("qwiuetqwuti %d %s", 1, "qweweq");
// C# string.format("wqqewqeq {0} {0} {1}", 121, "ajsgfj")

var f = function(a){
    return a + 5;
}
var s1 = `
`; //backticki - daja nowe featury
var p = 1;
var q  = "1adsdada";

var s1 = `ala ma kota ${p} ${q.length} ${f(1)}` //niestety internet IE11 tego nie ogarnie - narzedzie babeljs transkrypcja nowego dialektu jezyka na stary zeby dzialalo
//standardowo mozna sklejac stringi operatorem +
console.log(s1);

var d = new Date(); //nie ma przeciazania funkcji - to nie sa rozne konstruktory tylko jeden ktory moze rozpoznawac jakie i ile argumentow dostal
var r = new RegExp("a", "g");
var r = /a/gi; //inny sposob zapisywania wyrazen
var s = "aaaaa";
console.log(r.test(s));
console.log(s.match(r)); // tylko pierwsze doapsowanie
// flagi i -case insensitive, g - globalne doapsowanie, m - multiline

//OBIEKTOWOSC
//slowko this

var p1 = {
    name : 'jan',
    say : function(){
        return this.name;
    }
}
console.log(p1.say()) // tutaj this dziala jak w innych jezykach
//Uwaga mozemy sami narzucic czym jest this w wywolaniu metody
var f1 = function(){
    return this.name;
}
console.log(f1());//undefined
name  = 'qweqweqwre'
console.log(f1());"to wyzej"

var q = {
    name : 'tomasz'
}
console.log("call1 ", f1.call(q)); // this bedzie q
console.log("call say ", p1.say.call(q));

var f2 = function(n1,n2){
    return `${this.name} ${n1} ${n2}`;
}
console.log(f2.call(q, 2, 3));
console.log(f2.apply(q, [2,3]));
console.log(f2.bind(q)(2,3));

var p2 = {
    name: 'jan',
    say : function(){

        var self = this;  //var that = this

        var _ = function(){
            return this.name;  //this ma zasieg dla funckji w ktorej jest zdefiniowany. jesli tu nic nie podamy bedzie undefined
            //return self.name; //naprawione 2 - self jest widoczne wewnatrz funckji nawet zagniezdzonych
        }
        //return `${this.name} ${_()}`;
        return `${this.name} ${_.bind(this)()}`; //naprawione 1
        //return `${this.name} ${_()()}`;
    }
}

var createobj = function(namen){
    return{
        name : namen,
        say : function(){
            return this.name;
        }
    }
} // obiektowosc dla ubogich - nie ma new - z punktu widzenia organizacji pamieci kazdy obiekt ma zdefiniowana swoja funckje say - marnotractwo

p3 = createobj('stachu');
console.log("create obj:" ,p3.say());

//mozna cos takeigo:
p3.say = function(){
    return "brzydkie imie";
}
// w normalnej obiektowosci w pamieci po polach jest v-table - tablice wskaznikow na funckje
//heheh nie bedzie slowka class

var obiekt = {
    name : "jan",
    address : {
        city : 'wroclaw'
    },
    say : function(){
        return this.name;
    },
    init : function(name){
        this.name = name;
    }
}

var q10 = Object.create(obiekt);

//ale super!!
// kazdy obiekt ma swoj prototyp - jesli z ktoregos powstalo to tego a jesli nie ma to Object 
//prototypy jak dziedziczenie, jesli w q nie ma name ale w p jest to walnie to z p
//Object.create - tworzy cos takiego pustego - jakk nic nie zdefiniujemy wszytskiego(funkcji,pol) szuka w prototypie
q10.name = 'tomasz';
console.log(obiekt.address.city);
console.log(q10.address.city);
q10.address.city = "poznan";
console.log(obiekt.address.city);
console.log(q10.address.city); // uwaga!! bo w q10 address nie istnieje, wiec pobiera z obiekt i je edytuje
//naprawa: q.address{
//    city : 'poznan';
//}  fix ale slaby przydalby sie konstruktor 
//jeszcze brakuje: konstruktor,dziedziczenie, prywatne,publiczne, pakiety

//z initem
q11 = Object.create(obiekt);
q11.init('tomasz')

//tworzenie podklasy
var w = Object.create(obiekt);
w.init = function(name,age){
    p.init.call(this,name);
    this.age = age;
}
//brzydko, mozna inaczej
//zaczynamy od funkcji 
function Person(name){
    this.name = name;
    this.say = function(){
        return this.name;
    }
}//funkcja konstruktorowa moze nawet zwrocic obiekt z innej klasy, ale znowu nie ma oszczednosci pamieci

var x = new Person('jan'); //new po to zeby this nie przychodzilo globalne(?) - jeszcze sobie ogarnij
//prototypem funckji jest Function

//Person.prototype - fajna sprawa
Person.prototype.say = function(){
    return this.name;
} //teraz rzeczywsicie oszczedzamy ta pamiec

//tak dziala new
function customNew( f ){
    var _ = {};
    Object.setPrototypeOf(_, f.prototype);
    //zamisat pierwszych dwoch lini: var _ = Object.create(f.prototype);
    var r = f.call(_);
    if( r ) return r;
    else return _;
}

//Object.create za pomoca new
function objectCreate(p){
    var f = function(){};
    f.prototype = p;
    return new f();
}


//ale komitet sie wystarszyl i zrobil lukier syntaktyczny:
class Personnn{

    constructor(name){
        this.name = name;
    }
    say(){
        return this.name;
    }
}//zobacz sobie w babelu jak to wyglada, mozna tez:
class Worker extends Personnn{
    constructor(name, surname){
        super(name);
        this.surname  = surname;
    }
    say(){
        return this.name;
    }
}


//dziedziczenie na funckji

function Worker_(name,surname){

}
Worker_.prototype = Object.create(Person.prototype);

//oczywsicie mozna:
Personnn.prototype.foo = function(){
    return 1;
}//hehe do niby klasy dodajemy funckje^^

//babel,uglify,bloserify
