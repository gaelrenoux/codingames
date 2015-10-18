import scala.util.Random
import scala.annotation.tailrec

/* TODO : exploration en breadth-first de l'espace des solutions */
class Brain(val playersCount: Int, val maxDepth: Int) {

  var lastTurn = (-1, -1)

  /** Réfléchir avec grille mutable */
  def think(grid: MutableGrid, myPosition: (Int, Int), enemyPositions: Map[Char, (Int, Int)]): (Int, Int) = {
    val (x, y) = myPosition

    /* pour le forcer à clore de temps en temps */
    /*val depth = if (Random.nextInt(8) == 0) {
      Logger.info("Yeah !")
      0
    } else maxDepth*/
    val depth = maxDepth

    /* cible, score */
    Timer.start("Find Move")

    val extendedEnemyPositions = extendEnemyPositions(grid, enemyPositions mapValues { Set(_) })
    extendedEnemyPositions foreach { keyvalue => grid.update(keyvalue._2, keyvalue._1) }

    //val allMoves = (grid.neighbours(myPosition) - lastTurn) map { newP =>
    val allMoves = (grid.neighbours(myPosition)) map { newP =>
      val changed = grid.update(newP, Grid.Mine)
      val result = newP -> evaluate(newP, myPosition, extendedEnemyPositions, grid, depth, changed)
      grid.rollback()
      result
    } 
    
    //Logger.debug(allMoves)
    
    val bestMove = allMoves maxBy { x => (x._2, x._1) }
    Timer.end("Find Move")

    Timer.start("Find closest")
    val choice = if (bestMove._2._1 > grid.score(Grid.Mine)) {
      bestMove._1
    } else {
      /* N'améliore pas le score, aller vers la case libre la plus proche... */
      Logger.info("So sad")
      findClosestNeutral(grid, myPosition)
    }
    Timer.end("Find closest")


    lastTurn = myPosition
    choice
  }

  /** Evaluer une position avec grille mutable. La deuxièmr partie du score départage les ex-aequo closedAtDepth*/
  def evaluate(myPosition: (Int, Int), comingFrom: (Int, Int), enemyPositions: Map[Char, Set[(Int, Int)]], grid: MutableGrid, depth : Int, secondaryScore : Int = 0): (Int, Int) = {
    if (depth == 0 || Timer.danger()) {
      val score = grid.score(Grid.Mine)
      (score, secondaryScore)
    } else {
      val possibilities = grid.neighbours(myPosition) - comingFrom
      val extendedEnemyPositions = extendEnemyPositions(grid, enemyPositions)
      possibilities map { newP =>
        extendedEnemyPositions foreach { keyvalue => grid.update(keyvalue._2, keyvalue._1) }
        val deltaScore = grid.update(newP, Grid.Mine)
        val newSecondaryScore  = deltaScore + depth * secondaryScore
        val result = evaluate(newP, myPosition, enemyPositions, grid, depth - 1, newSecondaryScore)
        grid.rollback()
        extendedEnemyPositions foreach { keyvalue => grid.rollback() }
        result
      } max
    }
  }

  def findClosestNeutral(grid: Grid, start: (Int, Int)): (Int, Int) = findClosestNeutral(grid, Set(start))
  @tailrec
  final def findClosestNeutral(grid: Grid, frontier: Set[(Int, Int)] = Set(), explored: Set[(Int, Int)] = Set()): (Int, Int) = {
    if (frontier.isEmpty) return (-1, -1);
    val neutrals = frontier filter { grid.isNeutral(_) }
    if (!neutrals.isEmpty) return neutrals.head

    val nextFrontier = (frontier map { grid.neighbours(_) } flatten) -- explored
    val nextExplored = explored ++ frontier
    findClosestNeutral(grid, nextFrontier, nextExplored)
  }

  def extendEnemyPositions(grid: Grid, enemyPositions: Map[Char, Set[(Int, Int)]]): Map[Char, Set[(Int, Int)]] = try {    
    Timer.start("Extend enemy")
    enemyPositions mapValues { value => value.map { grid.neighbours(_) } flatten }
  } finally {
    Timer.end("Extend enemy")
  }
}