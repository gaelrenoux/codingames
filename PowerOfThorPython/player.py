import sys
import math

# Auto-generated code below aims at helping you parse
# the standard input according to the problem statement.
# ---
# Hint: You can use the debug stream to print initialTX and initialTY, if Thor seems not follow your orders.

# light_x: the X position of the light of power
# light_y: the Y position of the light of power
# initial_tx: Thor's starting X position
# initial_ty: Thor's starting Y position
light_x, light_y, thor_x, thor_y = [int(i) for i in raw_input().split()]

# game loop
while 1:
	remaining_turns = int(raw_input()) # The remaining amount of turns Thor can move. Do not remove this line.

	delta_x = light_x - thor_x
	delta_y = light_y - thor_y
	
	move = ""
	
	if delta_y > 0 :
		thor_y = thor_y + 1
		move = move + "S"
	elif delta_y < 0 :
		thor_y = thor_y - 1
		move = move + "N"
	
	if delta_x > 0 :
		thor_x = thor_x + 1
		move = move + "E"
	elif delta_x <0 :
		thor_x = thor_x - 1
		move = move + "W"
		
	# A single line providing the move to be made: N NE E SE S SW W or NW
	print move
