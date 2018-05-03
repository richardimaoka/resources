package my.stream

import java.io.{PrintWriter, StringWriter}

import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl.{Flow, Sink, Source}
import example.{IdProvider, PrintSink}

object Main {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("Main")
    implicit val materializer = ActorMaterializer()

    try {
      Source(1 to 10)
        .groupBy(3, _ % 3)
        .to(Sink.fromGraph(new PrintSink("substream")))
        .run()

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
