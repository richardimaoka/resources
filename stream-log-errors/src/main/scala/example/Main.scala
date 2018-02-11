package example

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.Attributes.LogLevels
import akka.stream.scaladsl.{Sink, Source}

object Main {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("exampleSystem")
    implicit val materializer = ActorMaterializer()

    try{
      Source(-5 to 5).addAttributes(LogLevels.).map(1 / _)/*.log("")*/.runWith(Sink.ignore)
      Thread.sleep(2000)
    } finally {
     system.terminate()
    }
  }
}
