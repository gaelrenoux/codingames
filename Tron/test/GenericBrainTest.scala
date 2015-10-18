import org.junit.Test
import org.junit.Assert._

class GenericBrainTest {

  @Test
  def testMinimaxVeryShort() = {
    val brain = new GenericBrain(3)
    val grid = GridMaker.get("grid-empty")
    val minimax = brain.minimax(grid.myIndex, grid)
    assertEquals(598, minimax._2.get(grid.myIndex).get)
  }

  @Test
  def testMinimaxShort() = {
    val brain = new GenericBrain(6)
    val grid = GridMaker.get("grid-empty")
    val minimax = brain.minimax(grid.myIndex, grid)
    assertEquals(595, minimax._2.get(grid.myIndex).get)
  }

  @Test
  def testMinimax() = {
    val brain = new GenericBrain(7)
    val grid = GridMaker.get("grid-empty")
    val minimax = brain.minimax(grid.myIndex, grid)
    assertEquals(594, minimax._2.get(grid.myIndex).get)
  }

  @Test
  def testMinimaxLong() = {
    val brain = new GenericBrain(11)
    val grid = GridMaker.get("grid-empty")
    val minimax = brain.minimax(grid.myIndex, grid)
    assertEquals(593, minimax._2.get(grid.myIndex).get)
    System.out.println(Timer.timeSpent)
  }

}