import math._
import scala.util._
import scala.io.StdIn

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
object Solution extends App {
  val n = StdIn.readInt

  val strengths = (for (i <- 0 until n) yield StdIn.readInt).toSeq.sorted
  System.err.println("Sorted !")

  val smallest = strengths.sliding(2) map { seq => math.abs(seq(0) - seq(1)) } min

  println(smallest)
}