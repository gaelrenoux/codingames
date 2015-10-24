STDOUT.sync = true # DO NOT REMOVE
# Auto-generated code below aims at helping you parse
# the standard input according to the problem statement.
# ---
# Hint: You can use the debug stream to print initialTX and initialTY, if Thor seems not follow your orders.

# light_x: the X position of the light of power
# light_y: the Y position of the light of power
# initial_tx: Thor's starting X position
# initial_ty: Thor's starting Y position
@light_x, @light_y, @initial_tx, @initial_ty = gets.split(" ").collect {|x| x.to_i}

lx = @light_x
ly = @light_y
tx = @initial_tx
ty = @initial_ty

# game loop
loop do
  remaining_turns = gets.to_i # The remaining amount of turns Thor can move. Do not remove this line.

  deltax = lx - tx
  deltay = ly - ty

  move = ""

  if deltay>0
    ty = ty + 1
    move = move + "S"
  elsif deltay<0
    ty = ty - 1
    move = move + "N"
  end

  if deltax>0
    tx = tx + 1
    move = move + "E"
  elsif deltax<0
    tx = tx- 1
    move = move + "W"
  end

  # Write an action using puts
  # To debug: STDERR.puts "Debug messages..."

  puts move # A single line providing the move to be made: N NE E SE S SW W or NW
end