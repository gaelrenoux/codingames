import math._
import scala.util._
import scala.io.StdIn

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
object Player extends App {
  // w: width of the building.
  // h: height of the building.
  val Array(w, h) = for (i <- readLine split " ") yield i.toInt
  val n = readInt // maximum number of turns before game over.
  var Array(x, y) = for (i <- readLine split " ") yield i.toInt

  var horizontalRange = 0 to (w - 1)
  var verticalRange = 0 to (h - 1)

  // game loop
  while (true) {
    // the direction of the bombs from batman's current location (U, UR, R, DR, D, DL, L or UL)
    val direction = StdIn.readLine

    if (direction.contains('U')) {
      verticalRange = verticalRange.head to (y - 1)
    }
    if (direction.contains('D')) {
      verticalRange = (y + 1) to verticalRange.last
    }
    if (direction.contains('L')) {
      horizontalRange = horizontalRange.head to (x - 1)
    }
    if (direction.contains('R')) {
      horizontalRange = (x + 1) to horizontalRange.last
    }

    x = (horizontalRange.last + horizontalRange.head) / 2
    y = (verticalRange.last + verticalRange.head) / 2

    // Write an action using println
    // To debug: Console.err.println("Debug messages...")
    

    Console.err.println("Horizontal range : " + horizontalRange)
    Console.err.println("Vertical range : " + verticalRange)
    println(x + " " + y) // the location of the next window Batman should jump to.
  }
}