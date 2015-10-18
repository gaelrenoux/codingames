class Purchases(val list: Map[Int, Int] = Map()) {

  def plus(pods: Int, zone: Zone) = {
    if (pods == 0) {
      this
    } else {
      val existing = list.getOrElse(zone.id, 0)
      new Purchases(list.updated(zone.id, existing + pods))
    }
  }

}
