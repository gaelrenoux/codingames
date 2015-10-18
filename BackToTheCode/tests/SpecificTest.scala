

import org.junit.Test

class SpecificTest {

  val grid = GridMaker.getMutable("grid-fail-2")

  @Test
  def roundAndRound() {
    Timer.setOff()
    var myPosition = (34, 1)
    val brain = new Brain(2, 5)
    val enemyPositions = Map('1' -> (1, 18))
    for (i <- 0 until 10) {
      myPosition = brain.think(grid, myPosition, enemyPositions)
      grid.update(myPosition, Grid.Mine)
      Logger.debug("Next : " + myPosition)
    }
  }
}