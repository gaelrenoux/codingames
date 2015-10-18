import math._
import scala.util._

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
object Solution extends App {
  val associationsCount = readInt // Number of elements which make up the association table.
  val filesCount = readInt // Numberof file names to be analyzed.

  /* file extension to MIME type */
  val associations = (for (i <- 0 until associationsCount) yield {
    val Array(ext, mime) = readLine split " "
    ext.toLowerCase -> mime
  }).toMap

  System.err.println(associations)

  for (i <- 0 until filesCount) {
    val filename = readLine // One file name per line.
    System.err.println(filename)

    val lastPointIndex = filename.lastIndexOf('.')
    val extension = if (lastPointIndex == -1) "" else filename.substring(lastPointIndex + 1)
    System.err.println(extension)

    val mime = associations.getOrElse(extension.toLowerCase, "UNKNOWN")
    println(mime)
  }

}