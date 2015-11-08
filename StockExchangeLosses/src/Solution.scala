import math._
import scala.util._
import scala.io.StdIn

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
object Solution extends App {
  val n = StdIn.readInt
  val values = StdIn.readLine split " " map { _.toInt }

  var maxValue = values(0)
  var worstDelta = 0

  values foreach { v =>
    if (v > maxValue) maxValue = v
    val currentDelta = v - maxValue
    if (currentDelta < worstDelta) worstDelta = currentDelta
  }

  println(worstDelta)
}