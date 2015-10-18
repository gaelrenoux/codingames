import scala.collection.mutable

/** Builder of the worldstate */
class WorldStateBuilder(val world: World, val myId: Int, val platinum: Int) {

  private val NeutralId = -1

  private val _owners = mutable.Map[Zone, Contestant]()
  private val _forces = mutable.Map[Zone, Map[Contestant, Troop]]()

  private def toContestant(id: Int) = id match {
    case NeutralId => Neutral
    case _ if id == myId => Me
    case _ => Enemy(id)
  }

  def setupZone(zid: Int, ownerId: Int, pods0: Int, pods1: Int, pods2: Int, pods3: Int) = {
    val zone = world.zone(zid)
    
    _owners(zone) = toContestant(ownerId)

    val forces = List(pods0, pods1, pods2, pods3).zipWithIndex.filter(_._1 != 0).map {zip =>
      val contestant = toContestant(zip._2)
      contestant -> Troop(zone, contestant, zip._1)
    }.toMap
    _forces(zone) = forces

  }

  def toWorldState = {
    new WorldState(world, platinum, _owners.toMap, _forces.toMap)
  }

}