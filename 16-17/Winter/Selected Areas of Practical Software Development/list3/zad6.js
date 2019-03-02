function createGenerator(n) {
    var state = 0;
    return {
        next : function() {
            return {
                value : state,
                done : state++ >= n
            }
        }
    }
}

var foo1 = {
    [Symbol.iterator] : function(){
        return createGenerator(10);
    }
}
var foo2 = {
    [Symbol.iterator] : function(){
        return createGenerator(20);
    }
}


for( var f of foo1 ){
    console.log(f);
}
console.log("foo2:");
for( var f of foo2 ){
    console.log(f);
}


