import scala.io.StdIn
import collection.mutable

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
object Player extends App {
  // roundsCount: maximum number of rounds
  // exitFloor: floor on which the exit is found
  // exitPosition: position of the exit on its floor
  // clonesCount: total number of generated clones
  // additionalElevatorsCount: ignore (always zero)
  val Array(floorsCount, width, roundsCount, exitFloor, exitPosition, clonesCount, additionalElevatorsCount, elevatorsCount) = for (i <- readLine split " ") yield i.toInt

  val elevatorsByFloorByPosition = mutable.Map[Int, mutable.Map[Int, Boolean]]()
  for (i <- 0 until floorsCount) {
    elevatorsByFloorByPosition.put(i, new mutable.HashMap[Int, Boolean]() { override def default(key: Int) = false })
  }
  for (i <- 0 until elevatorsCount) yield {
    // elevatorfloor: floor on which this elevator is found
    // elevatorpos: position of the elevator on its floor
    val Array(floor, position) = for (i <- StdIn.readLine split " ") yield i.toInt
    elevatorsByFloorByPosition(floor).put(position, true)
  }
  
  Console.err.println("Elevators : " + elevatorsByFloorByPosition)

  val blockersByFloorByPosition = mutable.Map[Int, mutable.Map[Int, Boolean]]()
  for (i <- 0 until floorsCount) {
    blockersByFloorByPosition.put(i, new mutable.HashMap[Int, Boolean]() { override def default(key: Int) = false })
  }

  // game loop
  while (true) {
    // clonefloor: floor of the leading clone
    // clonepos: position of the leading clone on its floor
    // direction: direction of the leading clone: LEFT or RIGHT
    val Array(floor, position, direction) = StdIn.readLine split " "
    if (direction == "NONE") {
      println("WAIT")
    } else {
      val leadingClone = Position(floor.toInt, position.toInt, direction)

      val nextStuff = nextStuffFound(leadingClone) 
      
      Console.err.println("Leading clone : " + leadingClone)
      Console.err.println("Next stuff for leading clone : " + nextStuff)
      
     nextStuff match {
        case Laser(_) => println("BLOCK")
        case _ => println("WAIT")
      }
    }
  }

  def nextStuffFound(clone: Position): Stuff = {
    val floor = clone.floor
    val direction = clone.direction
    var nextPosition = clone.x
    while (nextPosition >= 0 && nextPosition < width) {
      if (elevatorsByFloorByPosition(floor)(nextPosition)) {
        return Elevator(Position(floor, nextPosition))
      }
      if (blockersByFloorByPosition(floor)(nextPosition)) {
        return Blocker(Position(floor, nextPosition))
      }
      if (floor == exitFloor && nextPosition == exitPosition) {
        return Exit(Position(floor, nextPosition))
      }
      nextPosition = nextPosition + direction
    }

    return Laser(Position(floor, nextPosition))
  }
}

sealed class Stuff
case class Elevator(val position: Position) extends Stuff
case class Laser(val position: Position) extends Stuff
case class Blocker(val position: Position) extends Stuff
case class Exit(val position: Position) extends Stuff

case class Position(val floor: Int, val x: Int, val direction: Int = 0)
object Position {
  def apply(floor: Int, x: Int, dirString: String): Position = dirString match {
    case "LEFT" => Position(floor, x, -1)
    case "RIGHT" => Position(floor, x, 1)
  }
}