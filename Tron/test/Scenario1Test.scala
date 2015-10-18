import org.junit.Test
import org.junit.Assert._

class Scenario1Test {
  
  @Test
  def testNotInTheHole() = {
    val grid = GridMaker.get("grid-scenario1")
    assertEquals(Map(Up -> (0,4), Down -> (0,6)), grid.possibleMoves(0))
    
    val brain = new GenericBrain(4)
    val minimax = brain.minimax(grid.myIndex, grid)
    println(minimax)
    
    assertNotEquals(Up, minimax._1)
    assertTrue(minimax._2.get(0).get >= 0)

  }

}