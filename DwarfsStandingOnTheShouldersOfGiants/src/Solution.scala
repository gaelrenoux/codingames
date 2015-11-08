import math._
import scala.util._
import scala.io.StdIn
import scala.collection.mutable

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
object Solution extends App {
  val phoneNumCount = StdIn.readInt

  val contacts = Trie()

  for (i <- 0 until phoneNumCount) {
    val phoneNumber = StdIn.readLine() map { _.toInt } toList;
    contacts.add(phoneNumber)
  }

  println(contacts.size) // The number of elements (referencing a number) stored in the structure.

  case class Trie(val next: mutable.Map[Int, Trie] = mutable.Map[Int, Trie]()) {
    def add(list: List[Int]): Trie = list match {
      case Nil => this
      case h :: q => next.getOrElseUpdate(h, Trie()).add(q)
    }
    
    def size :Int = {
      val subCounts = next map { _._2.size } 
      subCounts.sum + next.size 
    }
  }
}