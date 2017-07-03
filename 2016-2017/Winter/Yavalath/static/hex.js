
// Hex math defined here: http://blog.ruslans.com/2011/02/hexagonal-grid-math.html
// po stronie serwera -> wobrazam sobie ze do serwera przesylamy wspolrzedne kliknietego hexa -> tam mamy liste hexow i ich wlasciwosci (czy pokolorowany na jaki kolor) , po kazdym poprawnie zamalowanym klocku sprawdzamy czy nie ma konca gry -> to schreiber ja plansze cyba ze bedzie sie chcial zamienic ^^ na poczatek zrobmy ze tylko 2 osoby moga grac

function Vector(x0,y0){
    this.x = x0;
    this.y = y0;
}

function GridCoordinates(coordinates, pixels){
    this.coordinates = coordinates;
    this.pixels = pixels;
}

function generateCoordinates(i,j, v1 , v2, grid0X, grid0Y){
    var coordinates = new Vector(i,j);
    var pixels = new Vector( grid0X + i * v1.x + j * v2.x, grid0Y + j * v1.y + j * v2.y);
    return new GridCoordinates(coordinates, pixels);
}

function generateBorderGridCoordinates( v1 , v2, grid0X, grid0Y){
    grids = [];
    for(var j = 4; j <= 9; j++){
        var i = 9;
        grids.push(generateCoordinates(i, j, v1 , v2, grid0X, grid0Y));
    }
    for( var i = 8; i >= 4; i--){
        var j = 9;
        grids.push(generateCoordinates(i,j, v1 , v2, grid0X, grid0Y));
    }
    grids.push(generateCoordinates(3,8, v1 , v2, grid0X, grid0Y));
    grids.push(generateCoordinates(2,7, v1 , v2, grid0X, grid0Y));
    grids.push(generateCoordinates(1,6, v1 , v2, grid0X, grid0Y));
    grids.push(generateCoordinates(0,5, v1 , v2, grid0X, grid0Y));
    grids.push(generateCoordinates(8,3, v1 , v2, grid0X, grid0Y));
    grids.push(generateCoordinates(7,2, v1 , v2, grid0X, grid0Y));
    grids.push(generateCoordinates(6,1, v1 , v2, grid0X, grid0Y));
    grids.push(generateCoordinates(5,0, v1 , v2, grid0X, grid0Y));
    for( var j = 4; j >= -1; j--){
        var i = -1;
        grids.push(generateCoordinates(i,j, v1 , v2, grid0X, grid0Y));
    }
    for( var i = 0; i <= 4; i++){
        var j = -1;
        grids.push(generateCoordinates(i,j, v1 , v2, grid0X, grid0Y));
    }
    return grids;
}

function generateGridCoordinates( v1 , v2, grid0X, grid0Y){
    var grids = [];
    var ends = {
        0 : new Vector(0,4),
        1 : new Vector(0,5),
        2 : new Vector(0,6),
        3 : new Vector(0,7),
        4 : new Vector(0,8),
        5 : new Vector(1,8),
        6 : new Vector(2,8),
        7 : new Vector(3,8),
        8 : new Vector(4,8),
    };
    for( var i = 0; i <= 8; i++){
        for( var j = ends[i].x; j <= ends[i].y; j++){
            grids.push(generateCoordinates(i,j, v1 , v2, grid0X, grid0Y));
        }
    }
    return grids;
}
function HexagonGrid(canvasId, username, id, socket, radius) {
    
    this.radius = radius;
    this.username = username;
    this.id = id;
    this.socket = socket;

    this.height = Math.sqrt(3) * radius;
    this.width = 2 * radius;
    this.side = (3 / 2) * radius;

    this.canvas = document.getElementById(canvasId);
    this.context = this.canvas.getContext('2d');

    this.offsetX = 85; //offset from canvas' borders
    this.offsetY = 0;
    
    this.grid0X = this.offsetX + 5/2 * this.height; //coordinates of point (0,0)
    this.grid0Y = this.offsetY + 5/2 * this.radius;

    this.v1 = new Vector(this.height,0);
    this.v2 = new Vector(-this.height/2, this.side);
    this.gridCoordinates = generateGridCoordinates( this.v1 , this.v2, this.grid0X, this.grid0Y); //drawn hexagons
    this.borderGridCoordinates = generateBorderGridCoordinates( this.v1 , this.v2, this.grid0X, this.grid0Y); //haxagons not drawn - for calculations pupose
    this.coordinates = this.gridCoordinates.concat(this.borderGridCoordinates);
    
    this.canvas.addEventListener("mousedown", this.clickEvent.bind(this), false);
};


HexagonGrid.prototype.drawHexGrid = function (isDebug) {
       
    var debugText = "";
    for(var coord of this.gridCoordinates){
        debugText = "( " + coord.coordinates.x + ", " + coord.coordinates.y + " )"; 
        this.drawHex(coord, "", debugText, isDebug);
    }
    for(coord of this.coordinates){
        console.log('wspolrzedne:',coord.coordinates.x, coord.coordinates.y)
        console.log('pixele:',coord.pixels.x, coord.pixels.y)
    }

};


HexagonGrid.prototype.drawHex = function(coord, fillColor, debugText, isDebug) { 

    if(!fillColor){
        fillColor = "white";
    }
    var x0 = coord.pixels.x;
    var y0 = coord.pixels.y;
    console.log("funkcja drawHex z parametrami:",coord.coordinates.x,coord.coordinates.y,fillColor,isDebug);
    this.context.strokeStyle = "#000";
    var angle = Math.PI / 6;
    this.context.beginPath(); //bez beginPath zamalowywal na niebiesko takze ostatni narysowany - glupie
    this.context.moveTo (x0 +  this.radius * Math.cos(angle), y0 +  this.radius *  Math.sin(angle));       
 
    for (var i = 1; i <= 6; i++) {
        this.context.lineTo (x0 + this.radius * Math.cos(i * 2 * angle + angle), y0 + this.radius * Math.sin(i * 2 * angle + angle));
    }
 
    this.context.fillStyle = fillColor;
    this.context.fill(); //wypelnianie kolorem

    this.context.stroke(); //faktyczne rysowanie
    if(isDebug){
        this.context.beginPath();
        //this.context.closePath(); polacz z punktem poczatkowym sciezki
        this.context.arc(x0, y0, 2, 0, 2 * Math.PI, false);
        this.context.fillStyle = 'black';
        this.context.fill();
        this.context.strokeStyle = '#003300'; //color in rgb
        this.context.stroke();
    }

    if(isDebug){
        if (debugText) {
            this.context.font = "8px";
            this.context.fillStyle = "#000";
            this.context.fillText(debugText, x0 , y0 + this.radius/3);
        }
    }
};

//return coordinates of selected hex
HexagonGrid.prototype.getSelectedHex = function(mouseCoordinates) {

    var distance = 99999999;
    var closestCenter;
    for( var coord of this.coordinates ){
        var newDistance = distanceFromHex(mouseCoordinates,coord);
        if(newDistance < distance){
            closestCenter = coord;
            distance = newDistance;
        }
    }
    if(distance > this.radius || this.borderGridCoordinates.indexOf(closestCenter) != -1){
        console.log("Nie trafiłeś")
        return undefined;
    }
    else{
        return closestCenter;
    }
    
};

HexagonGrid.prototype.clickEvent = function (e) {
	//zamiast od razu rysowac bedziemy wysylac przez socket info ze klikniete i wspolrzedne kliknietego
    var offset = this.getRelativeCanvasOffset();
    var mouseX = e.pageX - offset.x;
    var mouseY = e.pageY - offset.y;

    var center = this.getSelectedHex(new Vector(mouseX, mouseY));
    if(center){
        console.log("znalezione dla:")
        console.log(center.coordinates.x, center.coordinates.y)
        this.socket.emit('move', {username : username, id : id, hex : center})
    }
};

//calculate distance form mouse event to hex with given coordinates
function distanceFromHex(mouseCoordinates,coord){
    return Math.sqrt( (coord.pixels.x - mouseCoordinates.x)*(coord.pixels.x - mouseCoordinates.x) + (coord.pixels.y - mouseCoordinates.y)*(coord.pixels.y - mouseCoordinates.y) );
}

//Recusivly step up to the body to calculate canvas offset
HexagonGrid.prototype.getRelativeCanvasOffset = function() {
	var x = 0, y = 0;
	var layoutElement = this.canvas;
    if (layoutElement.offsetParent) {
        do {
            x += layoutElement.offsetLeft;
            y += layoutElement.offsetTop;
        } while (layoutElement = layoutElement.offsetParent);
        
        return { x: x, y: y };
    }
}