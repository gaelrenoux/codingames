import java.util.Date

object Output {
  
  val epoch = new Date().getTime
  
  private def time() = {
    ((new Date().getTime - epoch)).toString()
  }

  def debug(msg: String) = {
    Console.err.println(time() + "|" + msg)
  }

  def order(moves: Moves) = {
    moves.list.foreach { mv =>
      val ((originId, destinationId), pods) = mv
      print(pods + " " + originId + " " + destinationId + " ")
    }
    
    if (moves.list.isEmpty) println("WAIT")
    else println()
  }

  def order(purchases: Purchases) = {
    purchases.list.foreach { p =>
      val (zoneId, pods) = p
      print(pods + " " + zoneId + " ")
    }
    
    if (purchases.list.isEmpty) println("WAIT")
    else println()
  }
}