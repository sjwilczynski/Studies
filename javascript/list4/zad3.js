
var counter = (function(){
    var i = 0;

    return{
        get : function(){
            return i;
        },
        set : function(ni){
            i = ni;
        },
        incr : function(){
            i++;
        }
    }
})();


console.log(counter.get())
counter.set(31)
counter.incr()
console.log(counter.get())



