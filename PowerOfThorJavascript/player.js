/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 * ---
 * Hint: You can use the debug stream to print initialTX and initialTY, if Thor seems not follow your orders.
 **/

var inputs = readline().split(' ');
var lightX = parseInt(inputs[0]); // the X position of the light of power
var lightY = parseInt(inputs[1]); // the Y position of the light of power
var thorX = parseInt(inputs[2]); // Thor's starting X position
var thorY = parseInt(inputs[3]); // Thor's starting Y position

// game loop
while (true) {
    var remainingTurns = parseInt(readline()); // The remaining amount of turns Thor can move. Do not remove this line.

    var deltaX = lightX - thorX;
	var deltaY = lightY - thorY;

	var move = "";

	if (deltaY > 0) {
		thorY = thorY + 1;
		move = move + "S";
	} else if (deltaY < 0) {
		thorY = thorY - 1;
		move = move + "N";
	}

	if (deltaX > 0) {
		thorX = thorX + 1;
		move = move + "E";
	} else if (deltaX < 0) {
		thorX = thorX - 1;
		move = move + "W";
	}

	print(move);
}