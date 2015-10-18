import math._
import scala.util._

/**
 * It's the survival of the biggest!
 * Propel your chips across a frictionless table top to avoid getting eaten by bigger foes.
 * Aim for smaller oil droplets for an easy size boost.
 * Tip: merging your chips will give you a sizeable advantage.
 */
class Brain(val MyId: Int) {

  val SpeedLimit = 20
  val NeutralId = -1

  def order(x: Float, y: Float, str: String = "") = {
    println(x + " " + y + " " + str)
  }

  def stand(str: String = "") = {
    println("WAIT " + str)
  }

  def doTurn(myEntities: List[Entity], drops: List[Entity], enemyEntities: Map[Int, List[Entity]]) {

    val all = drops ++ myEntities ++ enemyEntities.values.flatten
    myEntities.foreach { current =>
      val notCurrent = Utilities.removeFirst(all, current)
      val closest = notCurrent.minBy(_.distanceTo(current))

      val dangerous = closest.canEat(current) && closest.player != MyId
      val collision = closest.collisionWithPoint(current) || current.collisionWithPoint(closest)
      val canAccelerate = current.speed < SpeedLimit
      if (dangerous && (collision || canAccelerate)) {
        /* if closest element is dangerous, flee ! */
        if (closest.isLeft(current)) {
          /* move othogonally to the left */
          order(current.x + closest.v, current.y - closest.u, "Fleeing left !")
        } else {
          /* move othogonally to the left */
          order(current.x - closest.v, current.y + closest.u, "Fleeing right !")
        }

      } else if (current.canEat(closest) && (!collision || canAccelerate)) {
        /* if closest element is smaller, go get it ! */
        if (!collision) {
          order(closest.x, closest.y, "Yummy !")
        } else if (canAccelerate && current.reducedRadius > closest.radius) {
          order(closest.x, closest.y, "Yummy !")
        } else {
          stand("Yummy yum !")
        }

      } else if (closest.id == MyId && (!collision || canAccelerate)) {
        /* if closest element is one of min, merge ! */
        order(closest.x, closest.y, "Fuuuuuuusion !")

      } else {
        /* already moving, mostly*/
        stand(closest.toString)
      }
    }
  }
}
