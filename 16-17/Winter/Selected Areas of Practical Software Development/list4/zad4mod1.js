console.log("jestem w module 1")
module.exports = {}


function foo100(a){
    return a*a
}
module.exports.foo = foo100

var b = require("./zad4mod2")
console.log(b.x)
