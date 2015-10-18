import collection.mutable
import scala.annotation.tailrec

/** Terrain de jeu. Classe mutable. */
class Grid(
    val playersCount: Int,
    val myIndex: Int,
    private var _positions: Array[Option[(Int, Int)]] = Array(Some((0, 0)), Some((0, 0)), Some((0, 0)), Some((0, 0))),
    private val trails: Array[Int] = Array.fill(30 * 20) { -1 }) {

  final private def index(x: Int, y: Int): Int = x + Grid.RowSize * y
  final private def index(p: (Int, Int)): Int = index(p._1, p._2)

  /** Supprime la traînée d'une moto (parce qu'elle est morte) */
  private def clearTrail(player: Int) = {
    for (x <- 0 until Grid.CellsCount) {
      if (trails(x) == player) trails(x) = -1
    }
  }

  def update(player: Int, p: (Int, Int)) = {
    trails(index(p)) = player
    if (player >= 0) _positions(player) = Some(p)
  }

  def kill(player: Int) = {
    _positions(player) = None
  }

  def update(positions: Seq[Option[Line]]) = for (i <- 0 until playersCount) positions(i) match {
    case None if _positions(i).isDefined =>
      /* La moto en cours vient de mourir, on vire son trail et sa position */
      clearTrail(i)
      _positions(i) = None

    case Some(Line(x0, y0, x, y)) =>
      trails(index(x0, y0)) = i
      trails(index(x, y)) = i
      _positions(i) = Some((x, y))

    case _ => //Rien à faire
  }

  def position = _positions
  def myPosition = _positions(myIndex).get

  /* Two players only */
  val enemyIndex = if (myIndex == 0) 1 else 0
  def enemyPosition = _positions(enemyIndex).get

  def free(p: (Int, Int)): Boolean = free(p._1, p._2)
  def free(x: Int, y: Int): Boolean = { Grid.isOnGrid(x, y) && trails(index(x, y)) == Grid.Neutral }

  def occupied(p: (Int, Int)): Boolean = occupied(p._1, p._2)
  def occupied(x: Int, y: Int): Boolean = { Grid.isOffGrid(x, y) || trails(index(x, y)) >= 0 }

  def eliminated(player: Int) = _positions(player).isEmpty

  def distance(player1: Int, player2: Int) = (_positions(player1), _positions(player2)) match {
    case (_, None) => None
    case (None, _) => None
    case (Some(p1), Some(p2)) => Geometry.distance(p1, p2)
  }

  /** Renvoie une map associant toutes les directions possibles au point d'arrivée. */
  def possibleMoves(player: Int): Map[Direction, (Int, Int)] = {
    _positions(player) match {
      case None => Map()
      case Some(position) => Direction.all map { d => d -> d.of(position) } filter { keyValue => free(keyValue._2) } toMap
    }
  }

  def availableZoneSize(start: (Int, Int)): Int = availableZoneSize(start :: Nil)
  @tailrec
  private def availableZoneSize(frontier: List[(Int, Int)] = Nil, explored: mutable.Set[(Int, Int)] = mutable.Set()): Int = {
    if (frontier.isEmpty) return explored.size

    val next = frontier.head
    val newSquares = Direction.all map { _.of(next) } filter { sq => free(sq) && !explored.contains(sq) }
    explored.add(next)

    availableZoneSize(newSquares ::: frontier.tail, explored)
  }

  @tailrec
  final def straightLineSize(start: (Int, Int), direction: Direction, accumulated: Int = 0): Int = {
    val next = direction.of(start)
    if (free(next)) straightLineSize(next, direction, accumulated + 1)
    else accumulated
  }

}

object Grid {
  val RowSize = 30
  val ColSize = 20
  val MinX = 0
  val MinY = 0
  val MaxX = 29
  val MaxY = 19
  val MaxDistance = 29
  val CellsCount = RowSize * ColSize

  val Neutral = -1

  final def isOnGrid(x: Int, y: Int): Boolean = { x >= Grid.MinX && x <= Grid.MaxX && y >= Grid.MinY && y <= Grid.MaxY }
  final def isOnGrid(p: (Int, Int)): Boolean = isOnGrid(p._1, p._2)
  final def isOffGrid(x: Int, y: Int): Boolean = { x < Grid.MinX || x > Grid.MaxX || y < Grid.MinY || y > Grid.MaxY }
  final def isOffGrid(p: (Int, Int)): Boolean = isOffGrid(p._1, p._2)

  def isBorder(x: Int, y: Int): Boolean = { x == Grid.MinX || x == Grid.MaxX || y == Grid.MinY || y == Grid.MaxY }
  def isBorder(p: (Int, Int)): Boolean = isBorder(p._1, p._2)

}