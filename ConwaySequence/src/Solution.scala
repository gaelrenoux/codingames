import math._
import scala.util._
import scala.io.StdIn
import scala.collection.mutable.ListBuffer

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
object Solution extends App {
  val startingNum = StdIn.readInt
  val finalLineNum = StdIn.readInt

  var remainingLines = finalLineNum - 1
  var line = List(startingNum)
  while (remainingLines > 0) {
    System.err.println(line)
    line = nextLine(line)
    remainingLines = remainingLines - 1
  }

  val spacedLine = line mkString(" ")
  println(spacedLine.trim)

  def nextLine(line: List[Int]) : List[Int] = {
    val result = new ListBuffer[Int]()
    var current = line

    while (current.length > 0) {
      val num = current(0)
      val count = current.takeWhile { _ == num } length;
      result.append(count)
      result.append(num)
      current = current.dropWhile { _ == num }
    }
    
    result.toList
  }
}