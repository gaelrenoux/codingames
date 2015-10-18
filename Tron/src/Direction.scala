abstract class Direction(val strValue: String, val next: Direction) {
  override def toString = strValue
  def of(p: (Int, Int)): (Int, Int) = of(p._1, p._2)
  def of(x: Int, y: Int): (Int, Int)
}

object Left extends Direction("LEFT", Down) {
  override def of(x: Int, y: Int) = (x - 1, y)
}

object Down extends Direction("DOWN", Right) {
  override def of(x: Int, y: Int) = (x, y + 1)
}

object Right extends Direction("RIGHT", Up) {
  override def of(x: Int, y: Int) = (x + 1, y)
}

object Up extends Direction("UP", Left) {
  override def of(x: Int, y: Int) = (x, y - 1)
}

/** Pour quand aucun coup ne peut être joué */
object Boom extends Direction("BOOM", null) {
  override def of(x: Int, y: Int) = (-1, -1)
}

object Direction {
  val all = List(Left, Down, Up, Right)
}