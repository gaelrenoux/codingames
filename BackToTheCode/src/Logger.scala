

object Logger {
  
  
   def debug(str : String) =  Console.err.println(str)
   def debug(obj : Any) =  Console.err.println(obj.toString())
   
   
   def info(str : String) =  Console.err.println(str)
}