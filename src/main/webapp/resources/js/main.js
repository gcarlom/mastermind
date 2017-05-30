/**
 * Mastermind
 * by G.Massari
 * (C) 2017
 */

// TODO GC: remove debug logging, more refactoring

var Main = (function() {
	var selectedPosition = 1;
	var EMPTY = '?';

	var elementIds = {
		nextMove: 'next-move',
		colorArea: 'color-area',
		sendMoveBtn: 'send-move',
		moveTextField: 'move'
	};

	var noOfPositions;
	var positions;
	var colorArea;
	var nextMoveArea;
	var sendMoveBtn;
	var moveTextField;

	var bindDomElements = function(elemIds) {
		// TODO check here if all elements are found, null is stored in no element. (if so -> warning)
		colorArea = document.getElementById(elemIds["colorArea"]);
		nextMoveArea = document.getElementById(elemIds["nextMove"]);
		if (nextMoveArea) {
				positions = nextMoveArea.children;
				noOfPositions = positions.length;
		}
		sendMoveBtn = document.getElementById(elemIds["sendMoveBtn"]);
		moveTextField = document.getElementById(elemIds["moveTextField"]);
	};

	var nextPos = function(pos) {
		pos = pos + 1;
		if (pos > noOfPositions) {
			pos = 1;
		}
		return pos;
	}

	var setSelectedPosition = function(newPosition) {
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

	var selectNextPosition = function() {
		var pos2 = nextPos(selectedPosition);
		selectedPosition= pos2;
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

	var isMoveComplete = function(positions) {
		var move = getMoveAsString(positions);
		return (move.indexOf(EMPTY) === -1);
	}

	var getMoveAsString = function(positions) {
		var move = "";
		for (var i=0; i<positions.length; i++ ) {
			var pos = positions[i];
			var col = pos.getAttribute('data-color');
			if (col && col.length === 1) {
				move += col;
			} else {
				move += EMPTY;
			}
		}
		return move;
	}

	var setupBindings = function () {
		// Set UI bindings for move area
		if (nextMoveArea) {
			nextMoveArea.addEventListener("click", function(e) {
				// e.target is the clicked element
				console.log("-->Click event fired");
				var elemClicked = e.target;
				if(elemClicked && elemClicked.getAttribute('data-pos')) {
					var posValue = elemClicked.getAttribute('data-pos');
					var posAsInt =  parseInt(posValue, 10);
					console.log("Hole no.", posAsInt, " was clicked!");
					if (isNaN(posAsInt || posAsInt <1 || posAsInt > noOfPositions)) {
						console.warn("Set selected position failed. Parsed 'data-pos' attribute: " + posValue + "is not a number or out of range.");
					} else {
						setSelectedPosition(posAsInt);
					}
				}
			});
		} else {
			console.log("Click event listener not set. No element found with id '" + nextMoveContainerId  + "'");
		}

		// Set UI Bindings for color area
		if (colorArea) {
			colorArea.addEventListener("click", function(e) {
				// e.target is the clicked element
				console.log("-->Click in color area");
				var elemClicked = e.target;
				if(elemClicked && elemClicked.getAttribute('data-color')) {
					var col = elemClicked.getAttribute('data-color');
					var colClass = " color-" + col + "";
					var selPos = positions[selectedPosition-1];
					selPos.className = selPos.className.replace(/(?:^|\s)color-(\S)+(?!\S)/g , colClass)
					selPos.setAttribute('data-color', col);
					selPos.textContent = col;
					console.log("Color ", col, " was selected !");
					selectNextPosition();

					var move = getMoveAsString(positions)
					console.log("--> move = "+ move);

					if(isMoveComplete(positions)) {
						moveTextField.value = move;
						sendMoveBtn.disabled=false;
					} else {
						sendMoveBtn.disabled=true;
					}
				}
			});
		} else {
			console.log("Click event listener not set. No element found with id '" + nextMoveContainerId  + "'");
		}
	};

	var init = function() {
		console.log("--> init() START");
		bindDomElements(elementIds);
		setupBindings();
		console.log("--> init() END");
	};

	return {
		init: init
	};
})();


console.log("--> main.js START");
Main.init();
console.log("--> main.js END");

