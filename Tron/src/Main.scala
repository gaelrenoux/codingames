import math._
import scala.util._

/**
 * To debug: Console.err.println("Debug messages...")
 */
object Player extends App {
  
  val brain = new GenericBrain(9)

  /* 1er tour */
  val Header(playersCount, myIndex) = InputReader.readHeader()
  implicit val grid = new Grid(playersCount, myIndex)
  gameLoop()

  /* game loop */
  while (true) {
    /* On ignore la première ligne (nb de joueurs et position) */
    InputReader.ignoreHeader()
    gameLoop()
  }

  def gameLoop() {
    val positions = InputReader.readPositions(playersCount)
    grid.update(positions)
    brain.nextMove(grid) match {
      case Boom => println("I'm dead")
      case direction => println(direction)
    }
  }
}




