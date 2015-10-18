import scala.collection.mutable

/** World map */
class World(
  val zones: Vector[Zone],
  val continents: Vector[Continent],
  private val distances: Array[Array[Option[Int]]]) extends WorldFilter {

  def zone(i: Int) = zones(i)
  def distance(z1: Zone, z2: Zone) = distances(z1.id)(z2.id)
}

/** A zone on the map */
abstract class Zone(val id: Int, val production: Int) {

  val links: Set[Zone]
  val continent: Continent

  def linkedWith(z: Zone) = links.contains(z)

  override def equals(other: Any) = other match {
    case that: Zone => this.id == that.id
    case _ => false
  }

  override def hashCode = id

  override def toString = "Zone(" + id + ", " + production + ")"
}

/** A continent : regrouping linked zones */
class Continent(val id: Int, val zones: Vector[Zone]) extends WorldFilter

/** Filters and stuff over the world elements */
trait WorldFilter {
  val zones: Vector[Zone]
  lazy val producers = zones.filter(_.production > 0)
  lazy val production = zones.foldLeft(0) { (total, zone) => total + zone.production }
  lazy val size = zones.length
  lazy val productionDensity = 100 * production / size
}
