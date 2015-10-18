/** Forces on a zone */
class Forces(private val _value: Map[Contestant, Int]) {
  
  lazy val all = _value
  def forces(c: Contestant) = _value.getOrElse(c, 0)
  lazy val mine = forces(Me)
  lazy val enemies = _value.filter(entry => entry._1 != Me && entry._2 > 0)

  override def toString = _value.toString
}