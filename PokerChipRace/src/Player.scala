
import scala.collection.mutable

/**
 * It's the survival of the biggest!
 * Propel your chips across a frictionless table top to avoid getting eaten by bigger foes.
 * Aim for smaller oil droplets for an easy size boost.
 * Tip: merging your chips will give you a sizeable advantage.
 */
object Player {

  def order(x: Float, y: Float, str: String = "") = {
    println(x + " " + y + " " + str)
  }

  def stand(str: String = "") = {
    println("WAIT " + str)
  }

  def main(args: Array[String]) {
    val myId = readInt // your id (0 to 4)
    val SpeedLimit = 20

    val brain = new Brain(myId)

    // game loop
    while (true) {
      val playerchipcount = readInt // The number of chips under your control
      val entitycount = readInt // The total number of entities on the table, including your chips

      val drops = mutable.ListBuffer[Entity]()
      val mines = mutable.ListBuffer[Entity]()
      val enemies = mutable.Map[Int, mutable.ListBuffer[Entity]]()
      for (i <- 0 until entitycount) {
        // id: Unique identifier for this entity
        // player: The owner of this entity (-1 for neutral droplets)
        // radius: the radius of this entity
        // x: the X coordinate (0 to 799)
        // y: the Y coordinate (0 to 514)
        // vx: the speed of this entity along the X axis
        // vy: the speed of this entity along the Y axis
        val Array(_id, _player, _radius, _x, _y, _vx, _vy) = readLine split " "
        val id = _id.toInt
        val player = _player.toInt
        val radius = _radius.toFloat
        val x = _x.toFloat
        val y = _y.toFloat
        val vx = _vx.toFloat
        val vy = _vy.toFloat
        if (player == -1) {
          drops += new Entity(id, player, radius, x, y, vx, vy)
        } else if (player == myId) {
          mines += new Entity(id, player, radius, x, y, vx, vy)
        } else {
          enemies.getOrElseUpdate(id, mutable.ListBuffer[Entity]()) += new Entity(id, player, radius, x, y, vx, vy)
        }
      }

      brain.doTurn(mines.toList, drops.toList, enemies.mapValues(_.toList).toMap)
    }
  }
}
