import org.junit.Test
import org.junit.Assert._

class ClosedSpaceTest {

  val grid : Grid = GridMaker.getMutable("grid1")

  @Test
  def testSimpleClosed() = {
    assertEquals(Set((2,1),(3,1),(4,1)), ClosedSpaceDetector.closedSpace(grid, '0', (3, 1)))
  }

  @Test
  def testSimpleOpen() = {
    assertEquals(Set(), ClosedSpaceDetector.closedSpace(grid, '0', (19, 7)))
  }

  @Test
  def testWithEnemyHole() = {
    assertEquals(Set(), ClosedSpaceDetector.closedSpace(grid, '0', (8, 1)))
  }

  @Test
  def testWithEnemyInside() = {
    assertEquals(Set(), ClosedSpaceDetector.closedSpace(grid, '0', (27, 7)))
  }

  @Test
  def testEnemyClosed() = {
    assertEquals(Set((11,5), (10,5), (12,5)), ClosedSpaceDetector.closedSpace(grid, '1', (11, 5)))
  }

  @Test
  def testNarrowOpen() = {
    assertEquals(Set(), ClosedSpaceDetector.closedSpace(grid, '0', (15, 10)))
  }

  @Test
  def testDiagonals() = {
    assertEquals(Set(), ClosedSpaceDetector.closedSpace(grid, '0', (18, 1)))
  }

  @Test
  def testComplicated() = {
    assertEquals(Set((6,14), (13,15), (11,14), (7,14), (16,15), (6,13), (15,15), (19,14), (10,14), (18,14), (14,15), (9,14), (18,15), (6,12), (17,15), (13,14), (19,15), (12,14), (8,14)), ClosedSpaceDetector.closedSpace(grid, '0', (16, 15)))
  }

  @Test
  def testAddedBorder() = {
    assertEquals(Set((2,1),(3,1),(4,1)), ClosedSpaceDetector.closedSpaceAfterMove(grid, (3, 0)))
  }
}