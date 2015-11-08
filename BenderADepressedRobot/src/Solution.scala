import math._
import scala.util._
import scala.io.StdIn
import collection.mutable

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
object Solution extends App {

  /* Construction de la carte */
  val Array(height, width) = for (i <- StdIn.readLine split " ") yield i.toInt
  val grid = Array.fill(width, height)(null: Square)
  var teleporter: Option[Teleporter] = None
  for (y <- 0 until height) {
    val row = StdIn.readLine
    for (x <- 0 until width) {
      val char = row(x)
      if (char == '@') {
        Bender.x = x; Bender.y = y
      }
      val square = Square(row(x), x, y)
      if (square.isInstanceOf[Teleporter]) {
        val tp = square.asInstanceOf[Teleporter]
        if (teleporter.isEmpty) teleporter = Some(tp) else teleporter.get.connect(tp)
      }
      grid(x)(y) = square
    }
  }

  //Console.err.println(grid)
  Console.err.println(Bender.x + " " + Bender.y)
  var moves = List[Cardinal]()

  var notLoop = true
  while (notLoop && !Bender.dead) {
    if (BenderStates.isLoop()) {
      notLoop = false
    } else {
      val (nextX, nextY) = Bender.direction.of(Bender.x, Bender.y)
      val nextSquare = grid(nextX)(nextY)
      if (nextSquare.free) {
        Console.err.println(Bender.direction)
        moves = Bender.direction :: moves
        Bender.x = nextX
        Bender.y = nextY
        nextSquare.affect()
      } else {
        Bender.direction = Bender.priorities.dropWhile { card =>
          val (x, y) = card.ofBender
          !(grid(x)(y).free)
        }(0)
      }
    }
  }

  if (notLoop) {
    moves.reverse foreach println
  } else {
    println("LOOP")
  }

}