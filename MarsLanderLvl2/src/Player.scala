import math._
import scala.util._

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
object Player extends App {
  val VerticalSpeedLimit = 40

  val surfacePointsCount = readInt

  val ground = new Ground(for (i <- 0 until surfacePointsCount) yield {
    // landx: X coordinate of a surface point. (0 to 6999)
    // landy: Y coordinate of a surface point.
    //By linking all the points together in a sequential fashion, you form the surface of Mars.
    val Array(landX, landY) = for (i <- readLine split " ") yield i.toInt
    (landX, landY)
  })
  val landingPlace = ground.landingPlace
  val highestPoint = ground.highestPoint
  System.err.println("Landing place : " + landingPlace)

  // game loop
  while (true) {
    // hspeed: the horizontal speed (in m/s), can be negative.
    // vspeed: the vertical speed (in m/s), can be negative.
    // fuel: the quantity of remaining fuel in liters.
    // rotate: the rotation angle in degrees (-90 to 90).
    // power: the thrust power (0 to 4).
    val Array(x, y, hSpeed, vSpeed, fuel, rotate, power) = for (i <- readLine split " ") yield i.toInt
    var newRotate = 0
    var newPower = 0

    val thrust = if (vSpeed <= -VerticalSpeedLimit) 4 else 3
    val distanceCovered = hSpeed * math.max(0, y - highestPoint._2) / math.max(-vSpeed, 1)

    if ((landingPlace._1._1 - x > distanceCovered) && rotate < 15) {
      val newRotate = math.max(rotate - 15, -15)
      println("%d %d".format(newRotate, thrust))

    } else if (landingPlace._2._1 - x < distanceCovered && rotate > -15) {
      val newRotate = math.min(rotate + 15, 15)
      println("%d %d".format(newRotate, thrust))

    } else if (y - landingPlace._1._2 > 40) {
      if (hSpeed > 5) {
        val newRotate = math.min(rotate + 15, 15)
        println("%d %d".format(newRotate, thrust))

      } else if (hSpeed < -5) {
        val newRotate = math.max(rotate - 15, -15)
        println("%d %d".format(newRotate, thrust))

      }
    } else if (vSpeed <= -VerticalSpeedLimit) {
      println("0 4")

    } else {
      println("0 0")
    }
  }
}

class Ground(pointsInit: Iterable[(Int, Int)]) {
  val points = pointsInit.toSeq.sorted

  lazy val landingPlace = {
    val landing = points sliding (2) map { seq => (seq(0), seq(1)) } filter { cpl => cpl._1._2 == cpl._2._2 }
    landing.next()
  }

  lazy val highestPoint = {
    points maxBy { _._2 }
  }
}