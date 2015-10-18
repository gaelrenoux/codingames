

object InputReader {

  def readInitialization = InitializationInput(readInt + 1) //Opponent count !

  def readRound(playersCount: Int) = {
    val roundNum = readInt
    val Array(x, y, myTimeFuelLeft) = for (i <- readLine split " ") yield i.toInt
    val opponents = for (i <- 0 until playersCount - 1) yield {
      val Array(opponentx, opponenty, opponentbackintimeleft) = for (i <- readLine split " ") yield i.toInt
      (opponentx, opponenty) -> opponentbackintimeleft
    }

    val lines = for (i <- 0 until 20) yield {
      readLine.toSeq
    }

    RoundInput(roundNum, (x, y), myTimeFuelLeft, opponents.map(_._1), opponents.map(_._2), lines)
  }
}

case class InitializationInput(val playersCount: Int)
case class RoundInput(
  val roundNum: Int,
  val myPosition: (Int, Int),
  val myTimeFuelLeft: Int,
  val enemyPositions: Seq[(Int, Int)],
  val enemyTimeFuelsLeft: Seq[Int],
  val lines: Seq[Seq[Char]])