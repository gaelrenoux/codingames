import scala.annotation.tailrec
import scala.collection.SortedSet
import scala.collection.mutable

class MutableGrid(var cellsSeq: Array[Char]) extends Grid {

  val archives = mutable.Stack[Set[(Int, Int)]]()

  private def index(x: Int, y: Int) = x + Grid.RowSize * y
  private def index(p: (Int, Int)) = p._1 + Grid.RowSize * p._2

  /** Possession d'une case */
  def status(x: Int, y: Int): Char = cellsSeq(index(x, y))

  def score(player: Char): Int = cellsSeq.count { _ == player }

  /** Renvoie true si on a fermé une zone */
  def update(point: (Int, Int), newStatus: Char): Int = try {
    Timer.start("updated")
    val (x, y) = point
    val previousStatus = status(x, y)

    if (previousStatus != Grid.Neutral) {
      archives.push(Set())
      return 0
    }

    cellsSeq(index(x, y)) = newStatus

    val closed = ClosedSpaceDetector.closedSpaceAfterMove(this, point)
    closed foreach { p => cellsSeq(index(p)) = newStatus }
    archives.push(Set(point) ++ closed)
    
    //Logger.debug("Got : " + (1 + closed.size))
    return 1 + closed.size

  } finally Timer.end("updated")

  /** Renvoie true si on a fermé une zone */
  def update(points: Set[(Int, Int)], newStatus: Char): Int = try {
    Timer.start("updated-s")

    val toChange = points filter { isNeutral(_) }
    toChange foreach { p => cellsSeq(index(p)) = newStatus }

    val closed = toChange map { p: (Int, Int) => ClosedSpaceDetector.closedSpaceAfterMove(this, p) } flatten;
    closed foreach { p => cellsSeq(index(p)) = newStatus }

    archives.push(toChange ++ closed)
    
    //Logger.debug("Got : " + (toChange.size + closed.size))
    return toChange.size + closed.size

  } finally Timer.end("updated-s")

  def rollback() = try {
    Timer.start("rollback")
    val archive = archives.pop()
    archive foreach { p => cellsSeq(index(p)) = Grid.Neutral }
  } finally Timer.end("rollback")

  /** Pour faciliter le debug */
  override def toString = {
    val lines = cellsSeq.grouped(Grid.RowSize).toSeq
    lines map { _.mkString } mkString ("\n")
  }
}