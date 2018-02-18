package example

import akka.actor.ActorSystem
import akka.stream.Attributes.LogLevels
import akka.stream.scaladsl.{Sink, Source}
import akka.stream.{ActorMaterializer, Attributes}

object Main {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("exampleSystem")
    implicit val materializer = ActorMaterializer()

    try{
      Source(-5 to 5).map(1 / _).runWith(Sink.ignore)

      Source(-5 to 5).map(1 / _).runWith(Sink.foreach(println))

      Source(-5 to 5).map(1 / _).runWith(
        Sink.ignore
          .withAttributes(
            Attributes.logLevels(onFailure = LogLevels.Off)
          )
      )

      Thread.sleep(1000)
    } finally {
     system.terminate()
    }
  }
}
