import math._
import scala.util._

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
object Player extends App {

    // game loop
    while(true) {
        val Array(x, y) = for(i <- readLine split " ") yield i.toInt
        
        /* represents the height of one mountain, from 9 to 0. Mountain heights are provided from left to right. */
        val mountains = for(i <- 0 until 8) yield readInt
        System.err.println(mountains)
        
        val highest = mountains.zipWithIndex.maxBy { _._1 }._2
        
        if (x == highest) println("FIRE")
        else println("HOLD")
    }
}