/*
const readline = require('readline');
const fs = require('fs')

const rl = readline.createInterface({
  input: fs.createReadStream('tekst.txt'),
  output: process.stdout
});


rl.on('line', function(line) {
    console.log(line);
});

//tu nie dziala ale w konsoli zwyklej dziala
*/

const fs = require('fs')
//fs.createWriteStream(process.stdout)
fs.readFile('tekst.txt', (err, data) => {
  if (err) throw err;1
  process.stdout.write(data)
  //console.log(data);
});