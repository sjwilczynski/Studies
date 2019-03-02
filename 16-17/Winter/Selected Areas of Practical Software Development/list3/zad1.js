var p ={
    foo : 1,
    bar : function(){
    },
    get getfoo(){
        return this.foo;
    },
    set setfoo(n){
        this.foo = n;
        console.log(this.foo);
    }
}

p.f1 = function(){
    return 5;
}
p.field = 'zaraz';

console.log(p.f1());
console.log(p.field);
//propetries trzeba definiowac w ten sposob:

Object.defineProperty(p,'prop1',{
    get : function(){
        return p.bar();
    },
    set : function(f){
        p.bar = f;
    }
});

p.prop1 = function(){
    return 10000;
}
console.log(p.prop1);