

object GridMaker {

  def get(name: String) = {

    val source = io.Source.fromFile("test/" + name + ".txt")

    val itLines = source.getLines()

    val Array(playersCount, myIndex) = itLines.next split " " map { _.toInt }
    val positions = for (i <- 0 until playersCount) yield {
      val Array(x0, y0, x, y) = for (i <- itLines.next split " ") yield i.toInt
      if (x == -1) None else Some((x, y))
    }

    val cells = itLines.toArray map { _.toArray map { ch => if (ch=='.') -1 else ch.toInt } } flatten

    source.close()
    new Grid(playersCount, myIndex, positions.toArray, cells)
  }
}