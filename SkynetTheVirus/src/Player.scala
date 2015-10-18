import math._
import scala.util._
import scala.io.StdIn
import scala.collection.mutable

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
object Player extends App {
  // nodesCount includes the gateways
  val Array(nodesCount, linksCount, exitCounts) = for (i <- readLine split " ") yield i.toInt

  val links = mutable.Map[Int, List[Int]](Array.tabulate(nodesCount) { i => i -> List[Int]() }: _*)
  val linksList = for (i <- 0 until linksCount) yield {
    val Array(n1, n2) = for (i <- StdIn.readLine split " ") yield i.toInt
    links(n1) = n2 :: links(n1)
    links(n2) = n1 :: links(n2)
    (n1, n2)
  }

  val exits = for (i <- 0 until exitCounts) yield StdIn.readInt

  while (true) {
    val agentNode = StdIn.readInt
    pathToExit(agentNode) match {
      case Nil => println("Have I won ?")
      case agent :: Nil => println("Have I lost ?")
      case agent :: next :: _ => println("%d %d".format(agent, next))
    }
  }

  def pathToExit(sourceNode: Int) : List[Int] = {
    val explored = mutable.Set[Int]()
    val partialPathsQueue = mutable.Queue[List[Int]](sourceNode :: Nil)

    while (!partialPathsQueue.isEmpty) {
      val partialPath = partialPathsQueue.dequeue()
      val current = partialPath.head
      
      if(exits.contains(current)) return (current :: partialPath).reverse
      explored.add(current)
      
      val possibilities = links(current) filter { !explored.contains(_) } map { _ ::partialPath }
      partialPathsQueue.enqueue(possibilities : _*)
    }

    return Nil
  }
}