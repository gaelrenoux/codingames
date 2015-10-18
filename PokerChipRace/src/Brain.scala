

/**
 * It's the survival of the biggest!
 * Propel your chips across a frictionless table top to avoid getting eaten by bigger foes.
 * Aim for smaller oil droplets for an easy size boost.
 * Tip: merging your chips will give you a sizeable advantage.
 */
class Brain(val MyId: Int) {

  val SpeedLimit = 5
  val NeutralId = -1

  def order(x: Float, y: Float, str: String = "") = {
    println(x + " " + y + " " + str)
  }

  def stand(str: String = "") = {
    println("WAIT " + str)
  }

  def doTurn(myEntities: List[Entity], drops: List[Entity], enemyEntities: Map[Int, List[Entity]]) {

    val myBiggest = myEntities.max

    val all = drops ++ myEntities ++ enemyEntities.values.flatten
    myEntities.foreach { current =>

      val notCurrent = Utilities.removeFirst(all, current)
      val closest = notCurrent.minBy(_.externalDistanceTo(current))

      if (closest.canEat(current) && closest.player != MyId && closest.collides(current)) {
        /* First thing : if closest element is dangerous, flee ! */
        if (closest.isLeft(current)) {
          /* move orthogonally to the left */
          order(current.x + closest.v, current.y - closest.u, current.id + "> Fleeing right !" + closest)
        } else {
          /* move othogonally to the left */
          order(current.x - closest.v, current.y + closest.u, current.id + "> Fleeing left !" + closest)
        }

      } else if (current != myBiggest) {
        /* Go toward a bigger friend */
        if (current.speed < SpeedLimit || !current.collides(myBiggest)) {
          order(myBiggest.x, myBiggest.y, current.id + "> Go to mummy !" + closest)
        } else {
          stand(current.id + "> Going to mummy !" + closest)
        }

      } else {
        /* i'm the baddest ! Eat something ! */
        val edible = notCurrent.filter(e => current.reducedRadius > e.radius && e.radius > 2 * current.dropletSize)
        val best = edible.filter(_.radius > 5 * current.dropletSize)
        val targets = if (best.isEmpty) edible else best;

        if (targets.isEmpty) {
          stand("Eeeeeeek !" + closest)
        } else {
          val target = targets.minBy(_.externalDistanceTo(current))
          if (!current.collides(target)) {
            val (targetX , targetY) = current.goto(target)
            order(targetX, targetY, current.id + "> Yummy !" + closest)
          } else if (current.speed < SpeedLimit && current.reducedRadius > target.radius) {
            val (targetX , targetY) = current.goto(target)
            order(targetX, targetY, current.id + "> Yummy yum !" + closest)
          } else {
            stand(current.id + "> Yummy yum yummy !" + closest)
          }
        }

      }
    }
  }
}
