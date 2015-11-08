import math._
import scala.util._
import scala.io.StdIn

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
object Player extends App {
  // w: number of columns.
  // h: number of rows.
  val Array(width, height) = for (i <- StdIn.readLine split " ") yield i.toInt
  val grid = (for (i <- 0 until height) yield {
    StdIn.readLine split " " map { str => Room(str.toInt) }
  }).toArray
  val exitX = readInt // the coordinate along the X axis of the exit (not useful for this first mission, but must be read).

  // game loop
  while (true) {
    val Array(_x, _y, pos) = readLine split " "
    val x = _x.toInt
    val y = _y.toInt
    val origin = Direction(pos)

    val room = grid(y)(x)
    
    Console.err.println("Room is " + room)
    
    val exit = room.exit(origin)
    val (nX, nY) = exit.of(x, y)

    Console.err.println("Going " + exit + " of " + x + ";" + y)

    // One line containing the X Y coordinates of the room in which you believe Indy will be on the next turn.
    println(nX + " " + nY)
  }
}