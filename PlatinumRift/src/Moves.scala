class Moves(val list: Map[(Int, Int), Int] = Map()) {

  def plus(pods: Int, origin: Zone, destination: Zone) = {
    val key = (origin.id, destination.id)
    val existing = list.getOrElse(key, 0)

    new Moves(list.updated(key, existing + pods))
  }

}