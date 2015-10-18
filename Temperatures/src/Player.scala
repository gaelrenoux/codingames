import math._
import scala.util._

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
object Solution extends App {
  val n = readInt // the number of temperatures to analyse

  if (n == 0) {
    println("0")
  } else {
    val temperatures = readLine split " " map { _.toInt }
    val min = temperatures.map { t =>
      val abs = math.abs(t)
      if (t > 0) (t -> (abs.toDouble - 0.5))
      else (t -> abs.toDouble)
    } minBy { _._2 }

    println(min._1)
  }
}