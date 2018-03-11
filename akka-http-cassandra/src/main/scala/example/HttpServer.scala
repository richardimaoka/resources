package example

import java.io.{PrintWriter, StringWriter}

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.{Directives, Route}
import akka.persistence.PersistentActor
import akka.stream.ActorMaterializer
import akka.util.Timeout
import spray.json.DefaultJsonProtocol

import scala.concurrent.Await
import scala.concurrent.duration._

case class ScoringRequest(score: Double)

case class ScoreResponse(
  averageScore: Double,
  totalScore: Double,
  numberOfTrials: Long
)

object ScoringJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val scoringRequestFormat = jsonFormat1(ScoringRequest)
  implicit val scoreResponseFormat = jsonFormat3(ScoreResponse)
}

case class ScoringEvent(score: Double)

class ScoringActor extends PersistentActor {
  var averageScore: Double = 0
  var totalScore:   Double = 0
  var numberOfTrials: Long = 0

  def persistenceId = "scoring"

  def updateState(score: Double): Unit ={
    totalScore = totalScore + score
    numberOfTrials = numberOfTrials + 1
    averageScore = totalScore / numberOfTrials
  }

  def receiveCommand = {
    case req: ScoringRequest =>
      val _sender = sender()
      persist(ScoringEvent(req.score)) {
        evt => updateState(evt.score)
        _sender ! ScoreResponse(averageScore, totalScore, numberOfTrials)
      }
  }

  override def receiveRecover = {
    case evt: ScoringEvent =>
      updateState(evt.score)
  }
}

object HttpServer extends Directives {
  def main(args: Array[String]): Unit = {
    import ScoringJsonSupport._

    implicit val system: ActorSystem = ActorSystem("HttpServer")
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    val scoringActor = system.actorOf(Props[ScoringActor], "scoring")

    implicit val timeout: Timeout = 3.seconds
    
    try {
      val routes: Route =
        path("scoring") {
          post {
            entity(as[ScoringRequest]) { request =>
              complete {
                (scoringActor ? request).mapTo[ScoreResponse]
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
