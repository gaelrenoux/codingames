

object Utilities {

  def removeFirst[T](list: List[T], element: T): List[T] = list match {
    case Nil => Nil
    case head :: tail if head == element => tail
    case head :: tail => head :: removeFirst(tail, element)
  }

  /** Distribute as an integer a quantity between stuff of relative importance */
  def distribute(quantity: Int, relativeImportances: Iterable[Double], favorHighest: Boolean = false) = {
   
    
    
    val totalImportance = relativeImportances.reduce { _ + _ }
    val attributions = relativeImportances.map { i => (quantity * i / totalImportance).round.toInt }.toArray

    var attributed = attributions.reduce { _ + _ }

    while (attributed > quantity) {
      val toChange = if (favorHighest) {
        attributions.filter(_ > 0).min
      } else {
        attributions.max
      }
      attributions(attributions.indexOf(toChange)) = toChange - 1;
      attributed = attributed - 1;
    }

    while (attributed < quantity) {
      val toChange = if (favorHighest) {
        attributions.max
      } else {
        attributions.min
      }
      attributions(attributions.indexOf(toChange)) = toChange + 1;
      attributed = attributed + 1;
    }

    attributions.toVector
  }

  /** Distribute as an integer a quantity between stuff of same importance */
  def distribute(quantity: Int, targetCount: Int) = {
    val base = quantity/targetCount
    val remaining = quantity % targetCount
    Vector.fill(remaining)(base + 1) ++ Vector.fill(targetCount - remaining)(base)
  }
}