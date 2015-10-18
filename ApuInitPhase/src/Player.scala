import math._
import scala.util._
import scala.io.StdIn

/**
 * Don't let the machines win. You are humanity's last hope...
 */
object Player extends App {
  val width = readInt // the number of cells on the X axis
  val height = readInt // the number of cells on the Y axis
  System.err.println((width, height))

  /* Grid, careful : Y before X */
  val grid = (for (i <- 0 until height) yield {
    StdIn.readLine.toSeq
  }) toSeq

  System.err.println(grid)

  for (i <- 0 until width; j <- 0 until height) {
    if (grid(j)(i) == '0') {

      val rightNeighboursX = (i + 1 until width).dropWhile(grid(j)(_) == '.')
      val right = if (rightNeighboursX.isEmpty) (-1, -1) else (rightNeighboursX.head, j)
      
      val downNeighboursY = (j + 1 until height).dropWhile(grid(_)(i) == '.')
      val down = if (downNeighboursY.isEmpty) (-1, -1) else (i, downNeighboursY.head)

      println("%d %d %d %d %d %d".format(i, j, right._1, right._2, down._1, down._2))
    }
  }
}