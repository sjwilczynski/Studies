const readline = require('readline');
const fs = require('fs')

stats = {}

function Para(name,value){
    this.name = name
    this.value = value
} 

const rl = readline.createInterface({
  input: fs.createReadStream('logs'),
  //output: process.stdout
});


rl.on('line', function(line) {
    ip = line.split(" ")[1]
    if( stats[ip] ) {
        stats[ip] += 1
    } else {
        stats[ip] = 1
    }
});

rl.on('close', () => {
    console.log('Finished reading')
    var res = find_3_frequent(stats)
    for(para of res){
        console.log(para.name," ",para.value)
    }
});
function change(para1,para2){
    para1.name = para2.name
    para1.value = para2.value
}

function find_3_frequent(stats){
    var pn = Object.getOwnPropertyNames(stats);
    max = [new Para(null,0), new Para(null,0), new Para(null,0)]
    for( ip of pn ){
        if(stats[ip] > max[0].value){
            change(max[2],max[1])
            change(max[1],max[0])
            max[0].name = ip
            max[0].value = stats[ip]
        } else if(stats[ip] > max[1].value){
            change(max[2],max[1])
            max[1].name = ip
            max[1].value = stats[ip]
        } else if( stats[ip] > max[2].value){
            max[2].name = ip
            max[2].value = stats[ip]
        }
    }
    return max
}

