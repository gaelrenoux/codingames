

sealed abstract class Room(val typeNum: Int) {
  def exit(entrance: Direction): Direction
}

object Room {
  def apply(num: Int) = num match {
    case 0 => Type0Room
    case 1 => Type1Room
    case 2 => Type2Room
    case 3 => Type3Room
    case 4 => Type4Room
    case 5 => Type5Room
    case 6 => Type6Room
    case 7 => Type7Room
    case 8 => Type8Room
    case 9 => Type9Room
    case 10 => Type10Room
    case 11 => Type11Room
    case 12 => Type12Room
    case 13 => Type13Room
    case _ => throw new IllegalArgumentException
  }
}

object Type0Room extends Room(0) {
  override def exit(entrance: Direction) = throw new UnsupportedOperationException
}

sealed abstract class ConstantExitRoom(val exit: Direction, typeNum: Int) extends Room(typeNum) {
  override def exit(entrance: Direction) = exit
}

object Type1Room extends ConstantExitRoom(Down, 1)
object Type3Room extends ConstantExitRoom(Down, 3)
object Type7Room extends ConstantExitRoom(Down, 7)
object Type8Room extends ConstantExitRoom(Down, 8)
object Type9Room extends ConstantExitRoom(Down, 9)
object Type10Room extends ConstantExitRoom(Left, 10)
object Type11Room extends ConstantExitRoom(Right, 11)
object Type12Room extends ConstantExitRoom(Down, 12)
object Type13Room extends ConstantExitRoom(Down, 13)

object Type2Room extends Room(2) {
  override def exit(entrance: Direction) = entrance.opposite
}
object Type4Room extends Room(4) {
  override def exit(entrance: Direction) = entrance match {
    case Up => Left
    case Right => Down
  }
}
object Type5Room extends Room(5) {
  override def exit(entrance: Direction) = entrance match {
    case Up => Right
    case Left => Down
  }
}
object Type6Room extends Room(6) {
  override def exit(entrance: Direction) = entrance.opposite
}