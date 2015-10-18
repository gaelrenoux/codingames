import math._
import scala.util._

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
object Player extends App {
  val road = readInt // the length of the road before the gap.
  val gap = readInt // the length of the gap.
  val platform = readInt // the length of the landing platform.

  val targetSpeed = gap + 1

  // game loop
  while (true) {
    val speed = readInt // the motorbike's speed.
    val x = readInt // the position on the road of the motorbike.

    if (x > road) println("SLOW")
    else if (x == road - 1) println("JUMP")
    else if (speed < targetSpeed) println("SPEED")
    else if (speed > targetSpeed) println("SLOW")
    else println ("WAIT")
    // A single line containing one of 4 keywords: SPEED, SLOW, JUMP, WAIT.
  }
}