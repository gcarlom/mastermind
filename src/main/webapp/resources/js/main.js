/**
 * Mastermind
 * by G.Massari
 * (C) 2017
 */

// TODO GC: Code to refactor!

console.log("--> main.js START");

var selectedPosition = 1;
var nextMoveContainerId = "next-move";
var nextMoveArea = document.getElementById(nextMoveContainerId);
var positions = nextMoveArea.children;

// Get the element, add a click listener...
if (nextMoveArea) {
	nextMoveArea.addEventListener("click", function(e) {
		// e.target is the clicked element!
		// If it was a list item
		console.log("-->Click event fired");
		var elemClicked = e.target;
		if(elemClicked && elemClicked.getAttribute('data-pos')) { // event.target.dataset.char // <button data-char=${letter}
			var posValue = elemClicked.getAttribute('data-pos');// List item found!  Output the ID!
			var posAsInt =  parseInt(posValue, 10);
			console.log("Hole no.", posAsInt, " was clicked!");
			if (isNaN(posAsInt || posAsInt <1 )) { // TODO check if > max positionN (eg. 4)
				console.warn("Set selected position failed. Parsed 'data-pos' attribute: " + posValue + "is not a number or out of range.");
			} else {
				setSelectedPosition(posAsInt);
			}
		}
	});
} else {
	console.log("Click event listener not set. No element found with id '" + nextMoveContainerId  + "'");
}

var colorAreaId = "color-area";
var colorArea = document.getElementById(colorAreaId);
if (colorArea) {
	colorArea.addEventListener("click", function(e) {
		// e.target is the clicked element!
		// If it was a list item
		console.log("-->Click in color area");
		var elemClicked = e.target;
		if(elemClicked && elemClicked.getAttribute('data-color')) {
			var col = elemClicked.getAttribute('data-color');
			var colClass = " color-" + col + "";
			var selPos = document.getElementById("pos-" + selectedPosition);
			if (selPos) {
				selPos.className = selPos.className.replace(/(?:^|\s)color-(\S)+(?!\S)/g , colClass)
				selPos.setAttribute('data-color', col);
				selPos.textContent = col;
				console.log("Color ", col, " was selected !");
				selectNextPosition();

				var move = getMoveAsString(positions)
				console.log("--> move = "+ move);

				if(isMoveComplete(positions)) {
					var moveText = document.getElementById("move")
					moveText.value = move;
				}
			}
		}
	});
} else {
	console.log("Click event listener not set. No element found with id '" + nextMoveContainerId  + "'");
}
console.log("--> main.js END");

nextPos = function(pos) {
	pos = pos + 1;
	if (pos > 4) {
		pos = 1;
	}
	return pos;
}

setSelectedPosition = function(newPosition) {

	for (var i=0; i<positions.length; i++ ) {
		var pos = positions[i];

		console.log("--> "+ i +": '" +pos.className+ "'")

		if (newPosition !== i+1) {
			pos.className = pos.className.replace(/(?:^|\s)selected(?!\S)/g , '')
		} else {
			pos.className += " selected"
		}
	}
	selectedPosition = newPosition;

}

selectNextPosition = function() {
	var pos2 = nextPos(selectedPosition);
	selectedPosition= pos2;
	var nextMove = document.getElementById(nextMoveContainerId);
	var positions = nextMove.children;
	for (var i=0; i<positions.length; i++ ) {
		var posX = positions[i];

		console.log("--> "+ i +": '" +posX.className+ "'")

		if (selectedPosition !== i+1) {
			posX.className = posX.className.replace(/(?:^|\s)selected(?!\S)/g , '')
		} else {
			posX.className += " selected"
		}
	}
}

isMoveComplete = function(positions) {
	var move = getMoveAsString(positions);
	return (move.indexOf('?') === -1);
}

getMoveAsString = function(positions) {
	var move = "";
	for (var i=0; i<positions.length; i++ ) {
		var pos = positions[i];
		var col = pos.getAttribute('data-color');
		if (col && col.length === 1) {
			move += col;
		} else {
			move += '?'
		}
	}
	return move;
}