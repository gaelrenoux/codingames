
sealed abstract class Direction(val dx: Int, val dy: Int) {
  def of(x: Int, y: Int) = {
    (x + dx, y + dy)
  }
  def opposite: Direction
}
object Up extends Direction(0, -1) {
  override def opposite = Down
}
object Down extends Direction(0, 1) {
  override def opposite = Up
}
object Left extends Direction(-1, 0) {
  override def opposite = Right
}
object Right extends Direction(1, 0) {
  override def opposite = Left
}

object Direction {
  def apply(str: String) = str.toUpperCase match {
    case "LEFT" => Left
    case "RIGHT" => Right
    case "UP" => Up
    case "TOP" => Up
    case "DOWN" => Down
  }
}