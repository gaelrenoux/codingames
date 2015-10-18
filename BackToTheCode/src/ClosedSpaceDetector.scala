import scala.collection.SortedSet
import scala.annotation.tailrec

object ClosedSpaceDetector {

  /** Renvoie l'ensemble des points neutres clos après l'ajout du point indiqué dans la Grid  */
  def closedSpaceAfterMove(grid: Grid, pointAdded: (Int, Int)): Set[(Int, Int)] = {
    
    /* Vérifier si une surface a été fermée par ce nouveau point */
    //val left = Left.of(x, y); val right = Right.of(x, y); val up = Up.of(x, y); val down = Down.of(x, y)
    val (x, y) = pointAdded
    val newStatus = grid.status(pointAdded)
    val nbs = grid.neighbours(pointAdded)
    val nbsOccupied = nbs.filter { grid.status(_) == newStatus }.toSeq
    val neighboursCount = nbsOccupied.size;
    /* on ne peut pas fermer plus d'espaces que de voisins-1 */
    if (neighboursCount < 2) {
      return Set()
    }

    val dgs = grid.diagonals(pointAdded)
    val all = nbs ++ dgs

    /* TODO optimiser un peu... */
    if (neighboursCount == 2) {
      /* on ferme un seul espace d'un côté ou de l'autre. Si on en fermait deux, alors il aurait déjà dû être fermé */
      val (x1, y1) = nbsOccupied(0)
      val (x2, y2) = nbsOccupied(1)
      if (x1 == x2 || y1 == y2) {
        /* sur la même ligne : on prend un point de chaque côté, on sait que les autres voisins sont libres */
        val (oneSide, otherSide) = if (x1 - x2 == 0) ((x - 1, y), (x + 1, y)) else ((x, y - 1), (x, y + 1))
        val firstTry = if (Grid.isOnGrid(oneSide)) closedSpace(grid, newStatus, oneSide) else Set[(Int, Int)]()
        if (!firstTry.isEmpty) return firstTry
        else if (Grid.isOnGrid(otherSide)) return closedSpace(grid, newStatus, otherSide)
        else return Set[(Int, Int)]()
      } else {
        /* en coin : on teste d'abord l'intérieur du coin */
        val inside = if ((x2, y1) == (x, y)) (x1, y2) else (x2, y1)
        if (grid.status(inside) == newStatus) return Set() //pas moyen que ça ait fermé, ça l'était avant
        
        val outside1 = (2 * x - inside._1, y)
        val outside2 = (x, 2 * y - inside._2)// on ne teste pas la diagonale qui risque d'être occupée
        val outside = if (Grid.isOnGrid(outside1)) outside1 else if (Grid.isOnGrid(outside2)) outside2 else (-1, -1)
        val firstTry = closedSpace(grid, newStatus, inside)
        if (!firstTry.isEmpty) return firstTry
        else if (Grid.isOnGrid(outside)) return closedSpace(grid, newStatus, outside)
        else return Set[(Int, Int)]()
      }
    } else {
      /* cas général, pas si fréquent */
      return all filter { grid.status(_) != newStatus } map { closedSpace(grid, newStatus, _) } flatten
    }
  }

  /** Renvoie l'ensemble des points neutres clos par le user indiqué incluant de ce point, un set vide si ce point n'appartient pas à un ensemble de points clos */
  def closedSpace(grid: Grid, owner: Char, startingPoint: (Int, Int)): Set[(Int, Int)] = try {
    Timer.start("closedSpace")
    if (grid.status(startingPoint) == owner) throw new IllegalArgumentException
    closedSpace(grid, owner, SortedSet(startingPoint))
  } finally Timer.end("closedSpace")

  @tailrec
  private def closedSpace(grid: Grid, owner: Char, frontier: SortedSet[(Int, Int)], explored: Set[(Int, Int)] = Set()): Set[(Int, Int)] = {
    if (frontier.isEmpty) return explored filter { grid.isNeutral(_) }
    val p = frontier.head
    if (Grid.isBorder(p)) {
      /* bordure : ne peut pas être un espace fermé */
      return Set()
    }
    
    val status =  grid.status(p)
    if (status != Grid.Neutral && status != owner) {
      /* des trucs ennemis au milieu : ne peut pas être un espace fermé */
      return Set()
    }

    val addedNeighbours = grid.neighboursWithDiagonals(p) filter { n => grid.status(n) != owner && !explored.contains(n) }
    val newFrontier = frontier - p ++ addedNeighbours
    val newExplored = explored + p

    closedSpace(grid, owner, newFrontier, newExplored)
  }
}