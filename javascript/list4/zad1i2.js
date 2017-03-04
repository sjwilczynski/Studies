function Tree(val,left,right){
    this.val = val
    this.left = left
    this.right = right
}

var t = new Tree(
    1,
    new Tree(2,
    new Tree(17,null,null),
    null),
    new Tree(3,null,null)
)

Tree.prototype[Symbol.iterator] = function*(){
    if( this.left ){
        yield* this.left //yield* zwraca zbior enumerowalny z tej struktury 
    }
    yield this.val;
    if( this.right ){
        yield* this.right
    }

}

for( e of t){
    console.log(e);
}
//implementacja bez yielda ze stosem