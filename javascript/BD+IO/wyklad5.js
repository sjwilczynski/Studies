function Tree(val,left,right){
    this.val = val
    this.left = left
    this.right = right
}

var t = new Tree(
    1,
    new Tree(2,null,null),
    new Tree(3,null,null)
)

Tree.prototype[Symbol.iterator] = function*(){
    if( this.left ){
        yield* this.left //yield* zwraca zbior enumerowalny z tej struktury 
    }
    yield this.val;
    if( this.right ){
        yield* this.right
    }

}

for( e of t){
    console.log(e);
}
///////////////////////////////////////////////////////////////////////////////////////////////////
//namespace

N1 = {}
N2 = {}
N1.Tree = Tree
N2.Tree = function(){
    return 12
}
//mozna tez 
N1.SN1 = {}
N1.SN1.Tree = Tree

var x = new N1.SN1.Tree(1,null,null)
for (e of x){
    console.log(e)
}
///////////////////////////////////
//prywatnosc

function Person(name){
    var f = function(){ //prywatna zmienna
        return 1
    }
    this.name = name
    this.f = f   //zobacz jak to bylo z tym self - zeby glebiej miec this
}

Person.prototype.say = function(){
    return this.name
}

var p = new Person("qwe")
console.log(p.f())
////////////////////////////
//dzielenie na wiele kodu na wiele plikow - modulu

var foo = require("./wyklad5mod") // zaczyna wykonywac kod z wyklad5mod i ustawia foo na module exports z tamtego
console.log(foo.j1())