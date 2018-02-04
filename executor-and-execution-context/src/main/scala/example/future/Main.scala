package example.future

import scala.concurrent.{ExecutionContext, Future}

object Main {
  def printThreadName(i: Int): Unit = println(s"[${Thread.currentThread()}] - printThreadName($i) is executed")

  def main(args: Array[String]): Unit = {
    println(s"[${Thread.currentThread()}] - main thread")
    
    // The "default" ExecutionContext which you wouldn't want to use in production,
    // It is `implicit`, so that Scala's `Future` uses it
    implicit val executionContext: ExecutionContext = ExecutionContext.Implicits.global

    //The signature of this `Future`'s `apply` method is
    //  def apply[T](body: =>T)(implicit executor: ExecutionContext): Future[T]
    Future{ printThreadName(1) }
    Future{ printThreadName(2) }
    Future{ printThreadName(3) }
    Future{ printThreadName(4) }
    Future{ printThreadName(5) }
    Future{ printThreadName(6) }
    Future{ printThreadName(7) }
    Future{ printThreadName(8) }
    Future{ printThreadName(9) }
    Future{ printThreadName(10) }
    Future{ printThreadName(11) }
    Future{ printThreadName(12) }
    //give enough time all threads complete their `Runnable`s
    Thread.sleep(2000)
  }
}