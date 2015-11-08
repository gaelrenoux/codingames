import math._
import scala.util._
import scala.io.StdIn
import scala.collection.mutable

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
object Solution extends App {
  val n = StdIn.readInt
  val dictionary = for (i <- 0 until n) yield StdIn.readLine
  val letters = StdIn.readLine

  val lettersCount = letters map { l => (l, letters.count(_ == l)) } toMap

  val acceptable = dictionary.view filter { word =>
    word.length <= 7 && !word.exists { !letters.contains(_) } && canBeFormedFrom(word, lettersCount)
  }
  val withPoints = acceptable map { word => (word, points(word)) }
  val max = withPoints.maxBy { _._2 }

  println(max._1)

  def points(word: String): Int = {
    word map { points(_) } sum
  }

  def points(char: Char): Int = char match {
    case 'd' | 'g' => 2
    case 'b' | 'c' | 'm' | 'p' => 3
    case 'f' | 'h' | 'v' | 'w' | 'y' => 4
    case 'k' => 5
    case 'j' | 'x' => 8
    case 'q' | 'z' => 10
    case _ => 1
  }

  def canBeFormedFrom(word: String, lettersCount: Map[Char, Int]): Boolean = {
    val worldLettersCount = word map { l => (l, word.count(_ == l)) }
    val impossible = worldLettersCount exists { cpl =>
      val (letter, count) = cpl
      count > lettersCount.getOrElse(letter, 0)
    }
    !impossible
  }
}