import org.junit.Assert

import org.junit.Test;

class RoomTest {

  @Test
  def testLvl1Test2() {
    val x = 2
    val y = 1
    Assert.assertEquals(Right, Left.opposite)
    Assert.assertEquals(Right, Type2Room.exit(Left))
  }
}
