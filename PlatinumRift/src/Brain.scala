

/**
 * Play the pods
 */
import java.util.Collection

class Brain(val world: World, val playerCount: Int) {

  val pathfinder = new Pathfinder(world)

  def doTurn(state: WorldState) {

    Output.order(getMovements(state))
    Output.debug("Moves calculated")

    Output.order(getPurchases(state))
    Output.debug("Purchases calculated")

  }

  /** Movements of existing units */
  def getMovements(state: WorldState) = state.myUnits.foldLeft(new Moves()) { (moves, key) =>
    val (zone, troop) = key
    Output.debug("Move for unit : " + troop)
    val continentState = state.continentStates(zone.continent)
    if (!continentState.dominated) {
      val strategies: Stream[Option[Zone]] =
        nextStepTowardsClosestInCollection(state, pathfinder, zone, continentState.notMyProducers) #::
          nextStepTowardsClosestInCollection(state, pathfinder, zone, continentState.notMyZones) #::
          Some(zone) #::
          Stream[Option[Zone]]();

      val target = strategies.dropWhile(_ == None).head.get
      moves.plus(troop.pods, zone, target)
    } else {
      moves
    }
  }

  private def nextStepTowardsClosestInCollection(state: WorldState, pathfinder: Pathfinder, zone: Zone, choice: Iterable[Zone]) = {
    if (choice.isEmpty) {
      None
    } else {
      val closest = choice.minBy { world.distance(_, zone).get }
      pathfinder.shortestPath(zone, closest).map(_.head)
    }
  }

  /** Purchases of new units : first, decouple by continent */
  def getPurchases(state: WorldState): Purchases = {
    val countNewUnits = state.platinum / Rules.PodCost

    if (countNewUnits == 0) {
      return new Purchases()
    }

    val importances = if (playerCount > 2) {
      /* if multiplayer : all continents with production marked as equally important, i want to control the small ones */
      state.interestingContinentStates.map {cs => if (cs.production > 0) 1.0 else 0.0}
    } else {
      /* log mins we give more importance to small continents than they should proportionally */
      state.interestingContinentStates.map { cs => math.log(cs.production) }
    }

    val existing = state.interestingContinentStates.map { cs => cs.countMyUnits }
    val countExistingUnits = existing.reduce { _ + _ }
    val countTotalUnits = countNewUnits + countExistingUnits

    val idealAttributionsByContinent = state.interestingContinentStates.zip(Utilities.distribute(countTotalUnits, importances))

    Output.debug("Ideal attributions : " + idealAttributionsByContinent)

    /* filtering out the continents with too much stuff already */
    val workableAttributionsByContinent = idealAttributionsByContinent.filter(ca => ca._1.countMyUnits <= ca._2)
    Output.debug("Workable attributions : " + workableAttributionsByContinent)

    val (affectedContinents, workableAttributions) = workableAttributionsByContinent.unzip

    val desiredCreations = workableAttributionsByContinent.map { ca => (ca._1.countMyUnits - ca._2).toDouble }

    val realCreations = Utilities.distribute(countNewUnits, desiredCreations)

    val result = affectedContinents.zip(realCreations).foldLeft(new Purchases()) { (purchases, ctcr) =>
      val (continent, creations) = ctcr

      if (continent.notEnemyProducers.isEmpty && continent.myUnits.isEmpty) {
        val target = continent.notEnemyZones.head
        /* dump all creations together */
        purchases.plus(creations, target)

      } else if (continent.notEnemyProducers.isEmpty) {
        /* reinforce existing units */
        val targets = continent.myUnits.map { _._1 }
        Utilities.distribute(creations, targets.size).zip(targets).foldLeft(purchases) { (p, oz) =>
          p.plus(oz._1, oz._2)
        }
      } else {
        /* go on producers */
        val orders = Utilities.distribute(creations, continent.notEnemyProducers.map { _.production.toDouble }, true)
        orders.zip(continent.notEnemyProducers).foldLeft(purchases) { (p, oz) =>
          p.plus(oz._1, oz._2)
        }
      }
    }

    result

  }
}
