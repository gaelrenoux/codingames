import scala.collection.mutable

class Pathfinder(val world: World) {

  /** Sur le retour : le premier élément existe toujours. Il s'agit soit de la 
   *  zone courante si on est déjà sur place, soit d'une nouvelle zone si l'on 
   *  doit se déplacer. */
  def shortestPath(origin: Zone, destination: Zone): Option[List[Zone]] = {

    if (origin == destination) return Some(List(origin))

    val explored = mutable.Set[Zone]()
    val frontier = mutable.Queue(List(origin))

    while (!frontier.isEmpty) {
      val path = frontier.dequeue()

      for (next <- path.head.links) {
        if (next == destination) {
          /* on enlève le premier élément qui est l'origine */
          return Some((next :: path).reverse.tail)
        } else if (!explored.contains(next)) {
          frontier.enqueue(next :: path)
          explored.add(next)
        }
      }

    }

    None
  }

}
