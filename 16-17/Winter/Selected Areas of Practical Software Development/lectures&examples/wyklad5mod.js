console.log("jestem w module")

function foo(){
    return 17
}

//nie laczay glupio plikow tylko oprawia w funkcje,ktora zwraca

//module.exports = foo
//mozemy zwracac obiekt
module.exports = {
    j1 : foo,
    j2 : 17
}

//czy mozna robic petle w require - jeden modul uzywa drugiego, drugi pierwszego??
// NIE UDA!!!!
// mozna oszukiwac wrzucajac to require do srodka funkcji wtedy to sie nie wykonuje od razu tylko dopiero jak zawolamy
// jak importujemy modul kolejny raz to jest pobierany z cache