

import org.junit.Test
import org.junit.Assert._

class GridUpdatesTest {

  @Test
  def surroundingsTest() = {
    val grid = GridMaker.getMutable("grid2")
    grid.update((34, 4), '0')
    Logger.debug("\n\n" + grid)
    assertEquals('0', grid.status(27, 4))
  }
}