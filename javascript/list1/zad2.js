var czyDoskonala = function(liczba){
    wynik = true;
    kopia = liczba;
    suma = 0;
    while(kopia > 0){
        cyfra = kopia % 10;
        suma += cyfra;
        kopia = (kopia - cyfra)/10;
        if( cyfra != 0 && liczba % cyfra != 0 ){
            wynik = false
        }
    }
    if(liczba % suma != 0){
        wynik = false;
    }
    return wynik;
}

var liczbyDoskonale = function(range){
    tablica = [];
    for(var i=1; i < range+1; i++){
        if(czyDoskonala(i)){
            tablica.push(i);
        }
    }
    return tablica;
}

//console.log('Mod 0 to:' + 10%0)
console.log( liczbyDoskonale(10000) );