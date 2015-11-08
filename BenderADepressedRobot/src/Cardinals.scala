
sealed abstract class Cardinal(val dx: Int, val dy: Int) {
  def of(x: Int, y: Int) = {
    (x + dx, y + dy)
  }
  def ofBender = of(Bender.x, Bender.y)
  def opposite: Cardinal
}
object North extends Cardinal(0, -1) {
  override lazy val opposite = North
  override def toString = "NORTH"
}
object South extends Cardinal(0, 1) {
  override lazy val opposite = South
  override def toString = "SOUTH"
}
object West extends Cardinal(-1, 0) {
  override lazy val opposite = East
  override def toString = "WEST"
}
object East extends Cardinal(1, 0) {
  override lazy val opposite = West
  override def toString = "EAST"
}

object Direction {
  def apply(str: String) = str.toUpperCase match {
    case "W" => West
    case "E" => East
    case "N" => North
    case "S" => South
  }
}