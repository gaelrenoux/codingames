import math._
import scala.util._
import scala.io.StdIn

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
object Solution extends App {
  val n = StdIn.readInt
  val coordinates = for (i <- 0 until n) yield {
    val Array(x, y) = for (i <- StdIn.readLine split " ") yield i.toLong
    (x, y)
  }

  val xs = coordinates.map(_._1)
  val ys = coordinates.map(_._2).sorted
  
  val dx = xs.max - xs.min
  System.err.println("dx " + dx)
  val medianY = ys(n/2)
  System.err.println("medianY " + medianY)
  
  val verticalL = coordinates.view map { cpl => math.abs(cpl._2 - medianY) } sum

  println(verticalL + dx)
}