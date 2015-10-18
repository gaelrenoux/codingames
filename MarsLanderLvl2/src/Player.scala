import math._
import scala.util._

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
object Player extends App {
  val VerticalSpeedLimit = 30
  val HorizontalSpeedLimit = 15
  val TargetVerticalSpeed = 20

  val surfacePointsCount = readInt

  val ground = new Ground(for (i <- 0 until surfacePointsCount) yield {
    // landx: X coordinate of a surface point. (0 to 6999)
    // landy: Y coordinate of a surface point.
    //By linking all the points together in a sequential fashion, you form the surface of Mars.
    val Array(landX, landY) = for (i <- readLine split " ") yield i.toInt
    (landX, landY)
  })

  System.err.println("Landing place : %s, %d".format(ground.landingXRange, ground.landingAltitude))

  // game loop
  while (true) {
    // hspeed: the horizontal speed (in m/s), can be negative.
    // vspeed: the vertical speed (in m/s), can be negative.
    // fuel: the quantity of remaining fuel in liters.
    // rotate: the rotation angle in degrees (-90 to 90).
    // power: the thrust power (0 to 4).
    val Array(x, y, hSpeed, vSpeed, fuel, rotate, power) = for (i <- readLine split " ") yield i.toDouble
    val altitudeLeft = y - ground.landingAltitude
    val excessVerticalSpeed = math.max(0, math.abs(vSpeed) - VerticalSpeedLimit)
    val excessHorizontalSpeed = math.max(0, (math.abs(hSpeed) - HorizontalSpeedLimit))
    val relativeExcessHorizontalSpeed = excessHorizontalSpeed * math.signum(hSpeed)
    val hDirection = math.signum(hSpeed)

    /* Temps restant compté en considérant la vitesse actuelle */
    val timeLeftAtCurrentSpeed = -altitudeLeft / vSpeed

    if (excessVerticalSpeed > timeLeftAtCurrentSpeed / 4) {
      /* on priorise le ralentissement ; 4 est la poussée maximum */
      System.err.println("priority is : correcting vertical speed")
      System.err.println("excess speed : " + excessVerticalSpeed)
      System.err.println("time left to decelerate : " + timeLeftAtCurrentSpeed)
      val alpha = math.max(math.min(0, rotate + 15), rotate - 15)
      System.err.println("hDirection * alpha : " + timeLeftAtCurrentSpeed)
      if (hDirection * alpha > -30) {
        println(alpha.round + " 4")
      } else {
        println(alpha.round + " 0")
      }

    } else {

      /* Temps restant compté en considérant une vitesse qui reste de l'ordre de la vitesse cible en descente */
      val timeLeftAtTargetSpeed = altitudeLeft / TargetVerticalSpeed

      val futureX = x + (timeLeftAtTargetSpeed * hSpeed)
      val deltaHorizontal = if (futureX < ground.landingXRange._1) (ground.landingXRange._1 - futureX) else if (futureX > ground.landingXRange._2) (ground.landingXRange._2 - futureX) else 0

      /* la poussée horizontale est égale à -P.sinus(a) ; la poussée verticale à P.cosinus(a) */
      /* P = - horiz/sin(a) ou P = vert/cos(a) */
      /* tan(a) = - (horiz/vert) */

      val desiredVerticalThrust = math.max(0.1, -vSpeed - TargetVerticalSpeed - 1);

      val desiredHorizontalThrust = if (excessHorizontalSpeed > timeLeftAtCurrentSpeed / 2) {
        System.err.println("priority is : correcting horizontal speed")
        -relativeExcessHorizontalSpeed
      } else {
        deltaHorizontal / timeLeftAtTargetSpeed
      }

      System.err.println("aiming for vertical thrust : " + desiredVerticalThrust)
      System.err.println("aiming for horizontal thrust : " + desiredHorizontalThrust)

      val desiredAlpha = math.atan(-desiredHorizontalThrust / desiredVerticalThrust) * 180 / math.Pi
      System.err.println("aiming for alpha : " + desiredAlpha)
      val alpha = math.max(math.min(desiredAlpha, rotate + 15), rotate - 15)
      System.err.println("but limited to alpha : " + alpha)

      val desiredPower = math.abs(desiredVerticalThrust / math.max(math.cos(alpha), 0.01))
      System.err.println("aiming for power : " + desiredPower)
      val thrust = math.max(math.min(desiredPower, 4), 0)

      println(alpha.round + " " +math.ceil(thrust).round)
    }
  }
}

class Ground(pointsInit: Iterable[(Int, Int)]) {
  val points = pointsInit.toSeq.sorted

  private lazy val landingPlace = {
    val landing = points sliding (2) map { seq => (seq(0), seq(1)) } filter { cpl => cpl._1._2 == cpl._2._2 }
    landing.next()
  }

  lazy val landingAltitude = landingPlace._2._2

  lazy val landingXRange = (landingPlace._1._1, landingPlace._2._1)

  lazy val highestPoint = {
    points maxBy { _._2 }
  }
}