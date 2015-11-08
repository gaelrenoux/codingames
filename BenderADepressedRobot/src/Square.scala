sealed abstract class Square {
  /* Returns true if Bender is free to move inside that square */
  def free: Boolean = true

  def affect(): Unit = {}
}

sealed abstract class Obstacle extends Square

case class BreakableObstacle(var destroyed: Boolean = false) extends Obstacle {
  override def free() = {
    if (Bender.breaker) {
      destroyed = true
      BenderStates.clear()
    }
    destroyed
  }
}

object UnbreakableObstacle extends Obstacle {
  override val free = false
}

object CircuitInverter extends Square {
  override def affect() = {
    Bender.inverted = !Bender.inverted
  }
}

case class PathModifier(val direction: Cardinal) extends Square {
  override def affect() = {
    Bender.direction = direction
  }
}

case class Teleporter(val x: Int, val y: Int) extends Square {
  private var _other: Teleporter = null
  def connect(other: Teleporter) = {
    _other = other
    _other._other = this
  }

  lazy val target = _other
  
  override def affect() = {
    Bender.x = target.x
    Bender.y = target.y
  }
}

case class Beer(var used: Boolean = false) extends Square {
  override def affect() = {
    Bender.breaker = !Bender.breaker
  }
}

object SuicideBooth extends Square {
  override def affect() = {
    Bender.dead = true
  }
}

object Blank extends Square

object Square {
  def apply(str: Char, x: Int, y: Int): Square = str match {
    case '$' => SuicideBooth
    case '#' => UnbreakableObstacle
    case 'X' => BreakableObstacle()
    case 'B' => Beer()
    case 'S' => PathModifier(South)
    case 'N' => PathModifier(North)
    case 'E' => PathModifier(East)
    case 'W' => PathModifier(West)
    case 'I' => CircuitInverter
    case 'T' => Teleporter(x, y)
    case _ => Blank
  }
}