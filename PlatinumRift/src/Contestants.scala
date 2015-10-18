sealed abstract class Contestant(
  val isMe: Boolean,
  val isNeutral: Boolean,
  val isEnemy: Boolean) {
  val isNotMe = !isMe
  def isNotEnemy = !isEnemy
}

object Me extends Contestant(true, false, false)
object Neutral extends Contestant(false, true, false)
case class Enemy(val id: Int) extends Contestant(false, false, true)