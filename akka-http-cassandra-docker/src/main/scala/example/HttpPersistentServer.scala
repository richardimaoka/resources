package example

import java.io.{PrintWriter, StringWriter}

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.{Directives, Route}
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.util.Timeout
import example.ScoringActor.ScoringCommand

import scala.concurrent.Await
import scala.concurrent.duration._

object HttpPersistentServer extends Directives {
  def main(args: Array[String]): Unit = {
    import ScoringJsonSupport._

    implicit val system: ActorSystem = ActorSystem("HttpPersistentServer")
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    val scoringActor = system.actorOf(Props[ScoringActor], "scoring")

    implicit val timeout: Timeout = 3.seconds
    
    try {
      val routes: Route =
        path("scoring") {
          post {
            entity(as[ScoringRequest]) { request =>
              complete {
                (scoringActor ? ScoringCommand(request.score)).mapTo[ScoreResponse]
              }
            }
          }
        }

      Http().bindAndHandle(routes, "localhost", 8095)
      println(s"Server online at http://localhost:8095/")
      Await.result(system.whenTerminated, Duration.Inf)
    } catch {
      case t: Throwable =>
        val sw = new StringWriter
        t.printStackTrace(new PrintWriter(sw))
        println(t.getMessage)
        println(sw)
    }
  }
}
