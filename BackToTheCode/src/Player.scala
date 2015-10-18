import math._
import scala.util._

object Player extends App {
  val MaxDepth = 5
  val playersCount = InputReader.readInitialization.playersCount
  val brain = new Brain(playersCount, MaxDepth);
  
  Timer.setAlert()

  // game loop
  while (true) {
    val round = InputReader.readRound(playersCount)
    Timer.restart()
    val myPosition = round.myPosition
    val myTimeFuelLeft = round.myTimeFuelLeft
    val enemies = round.enemyPositions.zipWithIndex map { posIx =>
      val key = posIx._2.toString()(0)
      key -> posIx._1
    } toMap
    val enemyTimeFuelsLeft = round.enemyTimeFuelsLeft
    val grid = new MutableGrid(round.lines.flatten.toArray)

    val order = brain.think(grid, myPosition, enemies)
    Output.goto(order)
  }
}