import collection.mutable

object Bender {
  var x: Int = -1
  var y: Int = -1
  var inverted: Boolean = false
  var breaker: Boolean = false
  var direction: Cardinal = South
  var dead: Boolean = false
  def priorities = if (inverted) List(West, North, East, South) else List(South, East, North, West)
}

object BenderStates {
  private val states = mutable.Set[(Int, Int, Boolean, Boolean, Cardinal)]()
  
  def isLoop() = {
    val state = (Bender.x, Bender.y, Bender.inverted, Bender.breaker, Bender.direction)
    if (states.contains(state)) {
      true
    } else {
      states.add(state)
      false
    }
  }
  
  def clear() = states.clear()
}