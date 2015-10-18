import math._
import scala.util._

/**
 *
 */
object Player extends App {
  // lightx: the X position of the light of power
  // lighty: the Y position of the light of power
  // initialX: Thor's starting X position
  // initialY: Thor's starting Y position
  val Array(lightX, lightY, initialX, initialY) = for (i <- readLine split " ") yield i.toInt
  val light = (lightX, lightY)
  var thor = (initialX, initialY)

  while (true) {
    val remainingturns = readInt // The remaining amount of turns Thor can move. Do not remove this line.

    val delta = (light._1 - thor._1, light._2 - thor._2)

    var move = ""
    System.err.println("Thor : " + thor)
    if (delta._2 > 0) {
      thor = (thor._1, thor._2 + 1)
      move = move + "S"
      System.err.println("Moving south : " + thor)
    } else if (delta._2 < 0) {
      move = move + "N"
      thor = (thor._1, thor._2 - 1)
      System.err.println("Moving north : " + thor)
    }
    if (delta._1 > 0) {
      thor = (thor._1 + 1, thor._2)
      move = move + "E"
      System.err.println("Moving east : " + thor)
    } else if (delta._1 < 0) {
      thor = (thor._1 - 1, thor._2)
      move = move + "W"
      System.err.println("Moving west : " + thor)
    }

    println(move) // A single line providing the move to be made: N NE E SE S SW W or NW
  }
}