
object Utilities {

  def removeFirst[T](list: List[T], element: T): List[T] = list match {
    case Nil => Nil
    case head :: tail if head == element => tail
    case head :: tail => head :: removeFirst(tail, element)
  }
}