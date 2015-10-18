import org.junit.Test
import org.junit.Assert._

class EvaluationTest {

  @Test
  def testScoreMutable() = {
    val grid = GridMaker.getMutable("grid2")
    assertEquals(2 * 28 + 1, grid.score('0'))
  }

  @Test
  def testSimpleMutable() = {
    Timer.restart()
    val grid = GridMaker.getMutable("grid2")
    val score = (new Brain(2, 0)).evaluate((34, 5), (33, 5), Map(), grid, 1)
    assertEquals(3 * 28, score)
  }

}