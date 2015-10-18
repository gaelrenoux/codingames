import math._
import scala.util._

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
object Solution extends App {
  val Width = readInt
  val Height = readInt
  val text = readLine

  val alphabetSlices = for (i <- 0 until Height) yield {
    val row = readLine
    row grouped (Width)
  }.toSeq

  val codes = text map { _.toUpper } map { char =>
    if (char >= 'A' && char <= 'Z') (char - 'A').toInt
    else 26
  }

  for (i <- 0 until Height) {
    codes foreach { c =>
      print(alphabetSlices(i)(c))
    }
    println
  }
}