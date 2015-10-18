import math._
import scala.util._
import scala.io.StdIn

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
object Solution extends App {
  val myLongitude = StdIn.readLine.replace(",", ".").toDouble
  val myLatitude = StdIn.readLine.replace(",", ".").toDouble
  val defibsCount = StdIn.readInt
  val defibs = for (i <- 0 until defibsCount) yield {
    val Array(id, name, address, phone, longitude, latitude) = readLine split ";"
    Defib(id.toInt, name, address, phone, longitude.replace(",", ".").toDouble, latitude.replace(",", ".").toDouble)
  }

  val closest = defibs map { defib =>
    val name = defib.name
    val x = (defib.longitude - myLongitude) * math.cos((defib.latitude + myLatitude) / 2)
    val y = (defib.latitude - myLatitude)
    val distanceSquared = (x * x) + (y * y)
    name -> distanceSquared
  } minBy {_._2}

  println(closest._1)
}

case class Defib(id: Int, name: String, address: String, phone: String, longitude: Double, latitude: Double) {

}