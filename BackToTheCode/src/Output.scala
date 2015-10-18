

object Output {
  def goto(x: Int, y: Int) = println(x + " " + y)
  def goto(p: (Int, Int)) = println(p._1 + " " + p._2)
}