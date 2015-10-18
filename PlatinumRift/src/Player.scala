
/**
 *
 */
object Player {

  def main(args: Array[String]) {
    
    val (world, playerCount, myId) = initialize()
    Output.debug("Initialization OK : " + playerCount + " players, I am " + myId)

    val brain = new Brain(world, playerCount)

    // game loop
    while (true) {
      val worldState = parseTurn(world, myId)
      brain.doTurn(worldState)
    }
  }

  /** Parse the initialization and set up the world */
  def initialize() = {
    // playercount: the amount of players (2 to 4)
    // myid: my player ID (0, 1, 2 or 3)
    // zonecount: the amount of zones on the map
    // linkcount: the amount of links between all zones
    val Array(playercount, myid, zonecount, linkcount) = for (i <- readLine split " ") yield i.toInt

    val builder = new WorldBuilder

    for (i <- 0 until zonecount) {
      // zoneid: this zone's ID (between 0 and zoneCount-1)
      // platinumsource: the amount of Platinum this zone can provide per game turn
      val Array(id, production) = for (i <- readLine split " ") yield i.toInt
      builder.addZone(id, production)
    }
    for (i <- 0 until linkcount) {
      val Array(zone1, zone2) = for (i <- readLine split " ") yield i.toInt
      builder.addLink(zone1, zone2)
    }

    (builder.toWorld, playercount, myid)
  }

  /** Parse the turn information and returns the world's state */
  def parseTurn(world: World, myId: Int) = {

    val platinum = readInt // my available Platinum

    val builder = new WorldStateBuilder(world, myId, platinum)

    for (i <- 0 until world.zones.length) {
      // zid: this zone's ID
      // ownerid: the player who owns this zone (-1 otherwise)
      // podsp0: player 0's PODs on this zone
      // podsp1: player 1's PODs on this zone
      // podsp2: player 2's PODs on this zone (always 0 for a two player game)
      // podsp3: player 3's PODs on this zone (always 0 for a two or three player game)
      val Array(zid, ownerid, podsp0, podsp1, podsp2, podsp3) = for (i <- readLine split " ") yield i.toInt
      builder.setupZone(zid, ownerid, podsp0, podsp1, podsp2, podsp3)
    }

    builder.toWorldState
  }

}
