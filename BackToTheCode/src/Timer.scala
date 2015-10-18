import scala.collection.mutable
import java.util.Date

/* Il déconne sur les appels récursifs */
object Timer {
  
  private var mode = 2
  def setModePerformance() = mode = 2
  def setAlert() = mode = 1
  def setOff() = mode = 0
  

  val methods = mutable.Stack[(String, Long)]()
  val timeSpent = mutable.Map[String, Long]()

  var tick = System.currentTimeMillis()

  final def start(methodName: String) = if (mode >1) {
       methods.push((methodName, new Date().getTime))
  }

  final def end(methodName: String) = if (mode >1) {
    val (actualName, start) = methods.pop()
    if (actualName != methodName) throw new IllegalStateException
    val end = new Date().getTime

    val currentTimeSpent = timeSpent.getOrElse(methodName, 0L)
    timeSpent.put(methodName, currentTimeSpent + (end - start))
  }

  final def restart() = {
    tick = System.currentTimeMillis()
  }

  final def danger(): Boolean = if (mode>0 && System.currentTimeMillis() - tick > 90) {
    Logger.info("Hurry !")
    true
  } else false
}