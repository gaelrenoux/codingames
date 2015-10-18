import scala.collection.mutable

/** Class to build the world */
class WorldBuilder {

  private val _zones = mutable.ArrayBuffer[ZoneBuilder]()
  def addZone(id: Int, production: Int) = _zones += new ZoneBuilder(id, production)

  private lazy val _zoneCount = _zones.length

  /** links are set up only after the zones are set up */
  private lazy val _pathDistances: Array[Array[Option[Int]]] = Array.fill(_zoneCount) { Array.fill(_zoneCount)(None) }

  def addLink(z1Id: Int, z2Id: Int) = {
    val z1 = _zones(z1Id);
    val z2 = _zones(z2Id);
    z1.addLink(z2)
    z2.addLink(z1)

    _pathDistances(z1Id)(z2Id) = Some(1)
    _pathDistances(z2Id)(z1Id) = Some(1)
  }

  /** Zone with incrementing links */
  class ZoneBuilder(id: Int, production: Int) extends Zone(id, production) {

    private val _links = mutable.Set[Zone]()
    private[WorldBuilder] def addLink(z: Zone) = _links += z

    lazy val links = _links.toSet

    var continentId: Int = -1
    var continentVar: Continent = null
    lazy val continent = continentVar
  }

  def toWorld = {

    /* finish initializing shortest distances with Floyd's algorithm*/
    for (i <- 0 until _zoneCount) _pathDistances(i)(i) = Some(0)
    for (k <- 0 until _zoneCount) {
      for (i <- 0 until _zoneCount; j <- 0 until _zoneCount) {
        val ik = _pathDistances(i)(k)
        val kj = _pathDistances(k)(j)
        if (ik != None && kj != None) {
          val throughK = ik.get + kj.get
          val ij = _pathDistances(i)(j)
          if (ij == None || ij.get > throughK) {
            _pathDistances(i)(j) = Some(throughK)
            _pathDistances(j)(i) = Some(throughK)
          }
        }
      }
    }

    /* find connected components */
    var currentContinentId = 0
    val unaffectedZones = mutable.Set(_zones: _*)
    while (!unaffectedZones.isEmpty) {
      val starter = unaffectedZones.head
      for (i <- 0 until _zoneCount) if (_pathDistances(i)(starter.id).isDefined) {
        val z = _zones(i)
        z.continentId = currentContinentId
        unaffectedZones.remove(z)
      }
      currentContinentId = currentContinentId + 1;
    }

    val continents = Vector.tabulate(currentContinentId) { i => new Continent(i, _zones.filter(_.continentId == i).toVector) }
    _zones.foreach { z => z.continentVar = continents(z.continentId) }

    new World(_zones.toVector, continents, _pathDistances)
  }

}