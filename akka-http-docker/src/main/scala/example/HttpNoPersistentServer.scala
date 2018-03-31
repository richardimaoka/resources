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

object HttpNoPersistentServer extends Directives {
  def main(args: Array[String]): Unit = {
    import ScoringJsonSupport._

    implicit val system: ActorSystem = ActorSystem("HttpNoPersistentServer")
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    implicit val timeout: Timeout = 3.seconds

    var averageScore: Double = 0
    var totalScore:   Double = 0
    var numberOfTrials: Long = 0

    def updateState(score: Double): Unit ={
      totalScore = totalScore + score
      numberOfTrials = numberOfTrials + 1
      averageScore = totalScore / numberOfTrials
    }

    try {
      val routes: Route =
        path("scoring") {
          post {
            entity(as[ScoringRequest]) { request =>
              updateState(request.score)
              complete {
                ScoreResponse(averageScore, totalScore, numberOfTrials)
              }
            }
          }
        }

      Http().bindAndHandle(routes, "0.0.0.0", 8095)
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
