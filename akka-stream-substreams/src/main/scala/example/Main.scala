package my.stream

import java.io.{PrintWriter, StringWriter}

import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl.{Flow, Sink, Source}
import example.{IdProvider, PrintStage}

object Main {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("Main")
    implicit val materializer = ActorMaterializer()

    try {
      Source(1 to 10)
        .via(Flow.fromGraph(new PrintStage("upstream", new IdProvider)))
        .groupBy(3, _ % 3)
        .to(
          Flow.fromGraph(new PrintStage[Int]("subflow", new IdProvider)).to(Sink.seq)
        )
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
