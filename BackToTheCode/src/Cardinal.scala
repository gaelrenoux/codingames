

class Cardinal(dx: Int, dy: Int) {
  def of(x: Int, y: Int): (Int, Int) = (x + dx, y + dy)
  def of(p: (Int, Int)): (Int, Int) = of(p._1, p._2)
  val all = Set(Left, Up, Right, Down)
}
object Left extends Cardinal(-1, 0)
object Up extends Cardinal(0, -1)
object Right extends Cardinal(+1, 0)
object Down extends Cardinal(0, +1)
object UpLeft extends Cardinal(-1, -1)
object UpRight extends Cardinal(+1, -1)
object DownLeft extends Cardinal(-1, +1)
object DownRight extends Cardinal(+1, +1)

object Cardinal {
  def neighbours(x: Int, y: Int) : Set[(Int, Int)]= neighbours((x,y))
  def neighbours(p: (Int, Int)): Set[(Int, Int)] = Set(Left.of(p), Right.of(p), Down.of(p), Up.of(p))
  def diagonals(x: Int, y: Int) : Set[(Int, Int)]= diagonals((x,y))
  def diagonals(p: (Int, Int)) = Set(UpLeft.of(p), UpRight.of(p), DownLeft.of(p), DownRight.of(p))
  def neighboursWithDiagonals(x: Int, y: Int) : Set[(Int, Int)]= neighboursWithDiagonals((x,y))
  def neighboursWithDiagonals(p: (Int, Int)) = neighbours(p) ++ diagonals(p)
}