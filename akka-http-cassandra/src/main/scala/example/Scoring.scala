package example

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.persistence.PersistentActor
import spray.json.DefaultJsonProtocol

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

class ScoringActor extends PersistentActor {
  import ScoringActor._

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
    case ScoringCommand(score) =>
      val _sender = sender()
      persist(ScoringEvent(score)) {
        evt => updateState(evt.score)
          _sender ! ScoreResponse(averageScore, totalScore, numberOfTrials)
      }
  }

  override def receiveRecover = {
    case evt: ScoringEvent =>
      updateState(evt.score)
  }
}

object ScoringActor {
  case class ScoringCommand(score: Double)
  case class ScoringEvent(score: Double)
}