import math._
import scala.util._

object CoopPlayer extends App {
  val playersCount = InputReader.readInitialization.playersCount

  var firstTurn = true
  var myNumber = 0

  // game loop
  while (true) {
    val round = InputReader.readRound(playersCount)
    val myPosition = round.myPosition
    val myTimeFuelLeft = round.myTimeFuelLeft
    val enemies = round.enemyPositions
    val enemyTimeFuelsLeft = round.enemyTimeFuelsLeft
    val grid = new MutableGrid(round.lines.flatten.toArray)

    val (x, y) = myPosition

    val x1 = math.max(0, x - 1)
    val y1 = math.max(0, y - 1)
    val x2 = math.min(Grid.MaxX, x + 1)
    val y2 = math.min(Grid.MaxY, y + 1)

    val firstEnemy = enemies(0)

    if (firstTurn) {
      val myPriority = 1000 * x + y
      val enemyPriority = 1000 * firstEnemy._1 + firstEnemy._2
      if (myPriority > enemyPriority) myNumber = 1
      else myNumber = 2
    }

    if (myNumber == 1) {
      if (firstEnemy == (1, 0)) Output.goto(0, 0)
      else Output.goto(0, 1)
    } else {
      if (firstEnemy == (0, 1)) Output.goto(0, 0)
      else Output.goto(1, 0)

    }

    /*
    if (grid.isNeutral(x1, y1)) Output.goto(x1, y1)
    else Output.goto(x2, y2)
		*/
    firstTurn = false
  }
}