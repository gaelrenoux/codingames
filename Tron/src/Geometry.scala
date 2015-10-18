

object Geometry {

  def distance(p1: (Int, Int), p2: (Int, Int)) = {
    val dx = math.abs(p1._1 - p2._1)
    val dy = math.abs(p1._2 - p2._2)
    dx + dy
  }

}