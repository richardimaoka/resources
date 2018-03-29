package my.stream

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source

object Main {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("MyActorSystem")
    implicit val materializer = ActorMaterializer()

    val sourceFromRange = Source(1 to 10)
    sourceFromRange
        .map(
          x =>
            throw new Exception("boomer")
        )
        .runForeach{ i => println(i) }

    system.terminate()
  }
}
