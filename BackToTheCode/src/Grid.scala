abstract class Grid {
  /* Méthodes de lecture */

  /** Possession d'une case */
  def status(x: Int, y: Int): Char
  def status(c: (Int, Int)): Char = status(c._1, c._2)

  /** Methods booléennes pour tester la possession */
  def isMine(x: Int, y: Int) = status(x, y) == Grid.Mine
  def isNeutral(x: Int, y: Int) = status(x, y) == Grid.Neutral
  def isNeutral(p: (Int, Int)) = status(p) == Grid.Neutral
  def isNotMine(x: Int, y: Int) = status(x, y) != Grid.Mine
  def isEnemy(x: Int, y: Int): Boolean = {
    val s = status(x, y)
    s != Grid.Mine && s != Grid.Neutral
  }
  def isEnemy(p: (Int, Int)): Boolean = isEnemy(p._1, p._2)

  def score(player: Char): Int

  /** Renvoie les voisins présents sur la grille (ie pas en dehors) */
  def neighbours(p: (Int, Int)) = Cardinal.neighbours(p) filter { Grid.isOnGrid(_) }

  /** Renvoie les voisins diagonaux présents sur la grille (ie pas en dehors) */
  def diagonals(p: (Int, Int)) = Cardinal.diagonals(p) filter { Grid.isOnGrid(_) }

  /** Renvoie les voisins présents sur la grille (ie pas en dehors) */
  def neighboursWithDiagonals(p: (Int, Int)) = Cardinal.neighboursWithDiagonals(p) filter { Grid.isOnGrid(_) }

}

/** Quelques constantes de la grille */
object Grid {
  val RowSize = 35
  val ColSize = 20
  val MinX = 0
  val MinY = 0
  val MaxX = 34
  val MaxY = 19
  val MaxDistance = 34

  val Neutral = '.'
  val Mine = '0'

  def isOnGrid(p: (Int, Int)) = {
    val (x, y) = p
    x >= Grid.MinX && x <= Grid.MaxX && y >= Grid.MinY && y <= Grid.MaxY
  }

  def isBorder(p: (Int, Int)) = {
    val (x, y) = p
    x == Grid.MinX || x == Grid.MaxX || y == Grid.MinY || y == Grid.MaxY
  }
}