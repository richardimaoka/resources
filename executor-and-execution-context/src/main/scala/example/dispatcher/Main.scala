package example.dispatcher

import java.io.{PrintWriter, StringWriter}

import akka.actor.ActorSystem

import scala.concurrent.{ExecutionContextExecutor, Future}

object Main {
  def printThreadName(i: Int): Unit = println(s"[${Thread.currentThread()}] - printThreadName($i) is executed")

  def main(args: Array[String]): Unit = {
    println(s"[${Thread.currentThread()}] - main thread")
    
    val system = ActorSystem("exampleSystem")
    // trait ExecutionContextExecutor extends ExecutionContext with Executor
    // so, this is an instance of ExeuctionContext, which can be implicitly used by Scala Future
    implicit val dispatcher: ExecutionContextExecutor = system.dispatcher

    try {
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