console.log("jestem w module 2")

module.exports = {}

var a = require("./zad4mod1")
console.log(a.foo(2))

module.exports.x = 1

//dependency injection?
//http://selfcontained.us/2012/05/08/node-js-circular-dependencies/