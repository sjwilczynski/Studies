const readline = require('readline');

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});


rl.question('What is your name? \n', (answer) => {
  //console.log('Hello', answer);
  rl.write('Hello ' + answer + "!\n")
  rl.close();
});


