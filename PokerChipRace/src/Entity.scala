
import math._
import scala.util._
import scala.collection.mutable
import scala.annotation.tailrec

/** Something with a position on the board */
class Point(val x: Float, val y: Float) {
  def distanceTo(p: Point) = {
    val dx = x - p.x
    val dy = y - p.y
    math.sqrt(dx * dx + dy * dy).toFloat
  }
}

/** Operations about moving stuff and geometry */
class MovingElement(val radius: Float, x: Float, y: Float, val u: Float, val v: Float) extends Point(x, y) {

  lazy val speed = {
    math.sqrt(u * u + v * v)
  }

  /** Les trois coefficients de l'équation de la droite : ax+by+c=0 */
  lazy val trajectoryEquation = (v, -u, u * y - v * x)

  /** Distance à laquelle passe ou va passer l'élément courant de la cible */
  def passingDistanceToPoint(p: Point) = {
    val (a, b, c) = trajectoryEquation
    val num = math.abs(a * p.x + b * p.y + c)
    num / speed
  }

  /** La droite qui sépare le plan en deux (>0 pour les points dans le futur) */
  private lazy val demiPlaneSeparatorEquation = (u, v, -u * x - v * y)

  def isInBack(p: Point) = {
    val (a, b, c) = demiPlaneSeparatorEquation
    a * p.x + b * p.y + c <= 0
  }

  def isInFront(p: Point) = {
    val (a, b, c) = demiPlaneSeparatorEquation
    a * p.x + b * p.y + c > 0
  }

  def isLeft(p: Point) = {
    val (a, b, c) = trajectoryEquation
    a * p.x + b * p.y + c >= 0
  }

  def isRight(p: Point) = {
    val (a, b, c) = trajectoryEquation
    a * p.x + b * p.y + c < 0
  }

  def collisionWithPoint(p: Point) = passingDistanceToPoint(p) <= radius && isInFront(p)
}

/** High level functions, regarding the rules of the game */
class Entity(val id: Int, val player: Int, radius: Float, x: Float, y: Float, u: Float, v: Float)
  extends MovingElement(radius: Float, x: Float, y: Float, u: Float, v: Float) with Ordered[Entity] {

  def canEat(e: Entity) = radius > e.radius

  def externalDistanceTo(e: Entity) = {
    val dx = x - e.x
    val dy = y - e.y
    math.sqrt(dx * dx + dy * dy).toFloat - e.radius - radius
  }

  /** radius after moving */
  lazy val reducedRadius = radius * 14 / 15

  /** size of a droplet */
  lazy val dropletSize = radius / 15

  def nextPosition = new Entity(id, player, radius, x + u, y + v, u, v)

  /** returns a point to target to catch some entity */
  def goto(e: Entity): (Float, Float) = {
    val targetX = e.x + e.u - u
    val targetY = e.y + e.v - v

    //if i shoot on this target, my acceleration would be be :
    val coeff = Rules.acceleration * math.sqrt(targetX * targetX + targetY * targetY)
    val acceleration = (targetX * coeff, targetY * coeff)

    //my new vector will be :
    val newVector = (u + acceleration._1, v + acceleration._2)

    (targetX, targetY)
  }

  @tailrec
  final def collides(e: Entity, maxTurns: Int = 10): Boolean = maxTurns match {
    case -1 => false
    case _ if (this.distanceTo(e) <= this.radius + e.radius) => true
    case maxTurns => this.collides(e, maxTurns - 1)
  }

  override def compare(e: Entity) = {
    if (radius > e.radius) 1
    else if (radius < e.radius) -1
    else if (id > e.id) 1
    else if (id < e.id) -1
    else 0
  }

  override def toString = {
    id + "(p" + player + ") (" + x + ";" + y + ")"
  }
}

