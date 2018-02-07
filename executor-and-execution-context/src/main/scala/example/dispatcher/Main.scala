package example.dispatcher

import java.io.{PrintWriter, StringWriter}

import akka.actor.ActorSystem

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

object Main {
  def printThreadInsideFuture(i: Int): Unit =
    println(s"[${Thread.currentThread()}] - printThreadInsideFuture($i) is executed")

  def printThreadInsideCallback(i: Int): Unit =
    println(s"[${Thread.currentThread()}] - printThreadInsideCallback($i) is executed")

  def main(args: Array[String]): Unit = {
    println(s"[${Thread.currentThread()}] - main thread")

    def callback(i: Int): Try[Unit] => Unit = {
      case Success(_) => printThreadInsideCallback(i)
      case Failure(_) => {
        println("Future failed!!")
        printThreadInsideCallback(i)
      }
    }

    val system = ActorSystem("exampleSystem")

    // Akka's Dispatcher extends ExecutonContext,
    // So you can execute Scala Future by implicitly passing the dispatcher
    implicit val dispatcher: ExecutionContext = system.dispatcher

    try {
      // Future{} is calling the following apply method of `object Future`
      //   def apply[T](body: =>T)(implicit executor: ExecutionContext): Future[T]
      // where you *implicitly* pass the executor: ExecutionContext
      // The body of Future NOT executed immediately in the current thread,
      // but executed asynchronously in another thread
      val f1  = Future{ printThreadInsideFuture(1) }
      val f2  = Future{ printThreadInsideFuture(2) }
      val f3  = Future{ printThreadInsideFuture(3) }
      val f4  = Future{ printThreadInsideFuture(4) }
      val f5  = Future{ printThreadInsideFuture(5) }
      val f6  = Future{ printThreadInsideFuture(6) }
      val f7  = Future{ printThreadInsideFuture(7) }
      val f8  = Future{ printThreadInsideFuture(8) }
      val f9  = Future{ printThreadInsideFuture(9) }
      val f10 = Future{ printThreadInsideFuture(10) }
      val f11 = Future{ printThreadInsideFuture(11) }
      val f12 = Future{ printThreadInsideFuture(12) }

      // pause the main thread so that the output is not cluttered
      // with mixed println from futures and onComplete callbacks
      Thread.sleep(2000)
      println()


      f1.onComplete(callback(1))
      f2.onComplete(callback(2))
      f3.onComplete(callback(3))
      f4.onComplete(callback(4))
      f5.onComplete(callback(5))
      f6.onComplete(callback(6))
      f7.onComplete(callback(7))
      f8.onComplete(callback(8))
      f9.onComplete(callback(9))
      f10.onComplete(callback(10))
      f11.onComplete(callback(11))
      f12.onComplete(callback(12))

      //give enough time all threads complete their `Runnable`s
      Thread.sleep(2000)
    } catch {
      case t: Throwable => {
        val sw = new StringWriter
        t.printStackTrace(new PrintWriter(sw))
        println(t.getMessage)
        println(sw)
      }
    } finally {
      system.terminate()
    }
  }
}