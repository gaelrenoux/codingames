

object Logger {

  val Debug = false;

  def debug(obj: => Any) = if (Debug) Console.err.println(obj.toString())

  def info(str: String) = Console.err.println(str)
}