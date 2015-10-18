import math._
import scala.util._

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
object Solution extends App {
  val message = readLine
  System.err.println(message.toString)

  val binaryMessage = message map { c => c.toBinaryString.reverse.padTo(7, '0').reverse } flatten;
  System.err.println(binaryMessage mkString)

  val byBlocks = split(binaryMessage.toList)
  System.err.println(byBlocks)

  var notFirst = false
  byBlocks foreach { block =>
    if (notFirst) print(" ") else { notFirst = true }
    if (block(0) == '0') print("00 ") else print("0 ")
    print("0" * block.size)
  }

  def split[T](list: List[T]): List[List[T]] = list match {
    case Nil => Nil
    case h :: t =>
      val block = list.takeWhile { h == _ }
      block :: split(list drop (block.size))
  }
}