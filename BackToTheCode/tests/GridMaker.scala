

object GridMaker {
  
  def getMutable(name: String) = {
    //val source = io.Source.fromURL(getClass.getResource(name + ".txt"))
    val source = io.Source.fromFile("tests/" + name + ".txt")
    val cells = try {
      source.getLines() map { line =>
        line.toVector
      } toVector
    } finally source.close()
    new MutableGrid(cells.flatten.toArray)
  }
}