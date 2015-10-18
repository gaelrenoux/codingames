case class Header(val playersCount: Int, val myIndex: Int)
case class Line(val x0: Int, val y0: Int, val x: Int, val y: Int)

object InputReader {

  /** Ignore le header */
  def ignoreHeader() = readLine

  /** Renvoie le header */
  def readHeader() = {
    val Array(playersCount, myIndex) = for (i <- readLine split " ") yield i.toInt
    Header(playersCount, myIndex)
  }

  /** Renvoie les positions des joueurs. */
  def readPositions(playersCount: Int) : Seq[Option[Line]] = {
    for (i <- 0 until playersCount) yield {
      val Array(x0, y0, x, y) = for (i <- readLine split " ") yield i.toInt
      if (x == -1) None else Some(Line(x0, y0, x, y))
    }
  }
}