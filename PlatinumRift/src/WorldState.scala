
/** Current state of the world */
class WorldState(
  val world: World,
  val platinum: Int,
  val owners: Map[Zone, Contestant],
  val forces: Map[Zone, Map[Contestant, Troop]]) extends WorldStateFilter {

  val zones = world.zones

  lazy val continentStates = world.continents.map { c => c -> new ContinentState(this, c) }.toMap
  
  /** dominated continents are out of the picture */
  lazy val interestingContinentStates = continentStates.values.filter(!_.dominated)
}

class ContinentState(val worldState: WorldState, val continent: Continent) extends WorldStateFilter {
  val zones = continent.zones
  val owners = worldState.owners.filterKeys(_.continent == continent)
  val forces = worldState.forces.filterKeys(_.continent == continent)
  
  override def toString = (zones.size, countMyUnits).toString()
}

/** Filters and stuff over the world elements */
trait WorldStateFilter extends WorldFilter {
  val owners: Map[Zone, Contestant]
  val forces: Map[Zone, Map[Contestant, Troop]]

  private def isMine(z: Zone) = owners(z).isMe
  private def isNotMine(z: Zone) = owners(z).isNotMe
  private def isEnemy(z: Zone) = owners(z).isEnemy
  private def isNotEnemy(z: Zone) = owners(z).isNotEnemy
  private def isNeutral(z: Zone) = owners(z).isNeutral

  lazy val myZones = zones.filter { isMine }
  lazy val myUnits = forces.flatMap { zct => zct._2.get(Me).map { zct._1 -> _ } }
  lazy val myProducers = producers.filter { isMine }

  lazy val notMyZones = zones.filter { isNotMine }
  lazy val notMyProducers = producers.filter { isNotMine }

  lazy val notEnemyZones = zones.filter { isNotEnemy }
  lazy val notEnemyProducers = producers.filter { isNotEnemy }

  lazy val neutralZones = zones.filter { isNeutral }

  lazy val enemyZones = zones.filter { isEnemy }
  lazy val enemyUnits = forces.mapValues { m => m.filterKeys(c => c != Me) }
  lazy val enemyProducers = producers.filter { isEnemy }

  lazy val countMyZones = myZones.size
  lazy val countNeutralZones = neutralZones.size
  lazy val countEnemyZones = enemyZones.size

  lazy val countMyUnits = myUnits.values.foldLeft(0) { (sum, troop) => sum + troop.pods }
  lazy val countEnemyUnits = enemyUnits.values.flatMap { _.values }.foldLeft(0) { (sum, troops) => sum + troops.pods }

  lazy val unitsDensity = countMyUnits.toDouble / size
  lazy val dominated = countNeutralZones == 0 && (countMyZones == 0 && countMyUnits == 0 || countEnemyZones == 0 && countEnemyUnits == 0)

}





