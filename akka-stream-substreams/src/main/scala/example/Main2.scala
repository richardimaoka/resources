package example

import java.io.{PrintWriter, StringWriter}

import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl.{Sink, Source}

object Main2 {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("Main")
    implicit val materializer = ActorMaterializer()

    try {
      Source(1 to 10)
        .groupBy(2, _ % 2)
        .mergeSubstreams
        .runWith(new PrintSink("merged"))

      Thread.sleep(1000)
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
    system.terminate()
  }
}