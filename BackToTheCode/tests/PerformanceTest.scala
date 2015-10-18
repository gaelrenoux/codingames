
import org.junit.Test

class PerformanceTest {

  @Test
  def startingMutable() {
    //for (i <- 0 until 100) {
      Timer.restart()
      Timer.start("Test")
      val brain = new Brain(2, 5)
      val grid = new MutableGrid(Array.fill(Grid.RowSize * Grid.ColSize)(Grid.Neutral))
      val myPosition = (8, 5)
      val enemyPositions = Map('1' -> (25, 15))
      Timer.start("Thinking")
      brain.think(grid, myPosition, enemyPositions)
      Timer.end("Thinking")
      Timer.end("Test")
    //}
    println("Mutable : " + Timer.timeSpent)
  }
}