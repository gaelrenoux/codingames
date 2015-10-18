

class GenericBrain(val maxDepth: Int = 15) {

  /** Détermine le prochain mouvement lorsque c'est mon tour */
  def nextMove(grid: Grid): Direction = minimax(grid.myIndex, grid)._1

  /** Renvoie le score de la situation courante et le meilleur coup à jouer pour le joueur en paramètre. Un score inférieur à zéro veut dire qu'on a perdu. */
  def minimax(nextPlayer: Int, grid: Grid, depth: Int = maxDepth): (Direction, Map[Int, Int]) = {
    // Logger.debug("minimax(" + nextPlayer + ", " + grid + ", " + depth + ")")

    if (depth == 0) {
      return (Up, evaluate(grid))
    }

    if (grid.eliminated(nextPlayer)) {
      if (nextPlayer == grid.myIndex) {
        /* c'est moi, inutile de continuer */
        return (Boom, scoreForPlayerLoss(grid.myIndex))
      }

      /* on saute le tour du joueur éliminé */
      val playerNextTurn = (nextPlayer + 1) % grid.playersCount
      val nextMinimax = minimax(playerNextTurn, grid, depth)
      return (Boom, nextMinimax._2)
    }

    val currentPosition = grid.position(nextPlayer).get
    val possibilities = grid.possibleMoves(nextPlayer)

    val chosen: (Direction, Map[Int, Int]) =

      if (possibilities.isEmpty) {
        /* premier cas : aucun mouvement disponible, le joueur courant a perdu */
        if (nextPlayer == grid.myIndex) {
          /* c'est moi, inutile de continuer */
          return (Boom, scoreForPlayerLoss(grid.myIndex))
        }
        /* Le prochain joueur n'a aucune possibilité :on saute son tour, et tous les autres joueurs marquent 100 points */
        grid.kill(nextPlayer)
        val playerNextTurn = (nextPlayer + 1) % grid.playersCount
        val (move, score) = minimax(playerNextTurn, grid, depth - 1)
        grid.update(nextPlayer, currentPosition)

        score map { keyValue =>
          val (key, value) = keyValue
          if (key == nextPlayer) -1 else value + 100
        }
        (Boom, score)

      } else {
        /* Sinon, on parcourt les possibilités offertes */

        val best = possibilities mapValues { value =>

          grid.update(nextPlayer, value)

          val newPossibleMoves = grid.possibleMoves(nextPlayer)
          val score = if (newPossibleMoves.isEmpty) {
            scoreForPlayerLoss(nextPlayer)
          } else {
            val playerNextTurn = (nextPlayer + 1) % grid.playersCount
            val result = minimax(playerNextTurn, grid, depth - 1)
            /* on renvoie juste le score */
            result._2
          }

          grid.update(Grid.Neutral, value)
          grid.update(nextPlayer, currentPosition)
          score
          
        } maxBy { keyValue => keyValue._2.get(nextPlayer) }

        best
      }

    Logger.debug("Player " + nextPlayer + " plays " + chosen)

    chosen
  }

  /** Evalue un score de la grille pour chaque joueur : basé sur le nombre de cases dispos */
  def evaluate(grid: Grid): Map[Int, Int] = try {
    Timer.start("evaluate")
    val results = for (i <- 0 until grid.playersCount) yield {
      (i, evaluatePlayer(grid, i))
    }
    results.toMap
  } finally {
    Timer.end("evaluate")
  }

  /** Evalue un score de la grille pour un joueur : trucs plus rapides */
  def evaluatePlayer(grid: Grid, player: Int): Int = grid.position(player) match {
    case None => -1
    case Some(p) => Direction.all map { grid.straightLineSize(p, _) } sum
  }

  /** Evalue un score de la grille pour un joueur : son nombre de cases dispos */
  def oldEvaluatePlayer(grid: Grid, player: Int): Int = grid.position(player) match {
    case None => -1
    case Some(p) => grid.availableZoneSize(p)
  }

  def scoreForPlayerLoss(player: Int) = {
    val seq = for (i <- 0 until 4) yield {
      if (i == player) (i, -1) else (i, 1000)
    }
    seq.toMap
  }

}