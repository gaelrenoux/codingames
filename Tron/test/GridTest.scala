import org.junit.Test
import org.junit.Assert._

class GridTest {

  @Test
  def possibleMoves() = {
    val grid = GridMaker.get("grid-testgrid")
    val moves = grid.possibleMoves(0)
    assertEquals(Map(Right -> (1, 5)), moves)
  }

}