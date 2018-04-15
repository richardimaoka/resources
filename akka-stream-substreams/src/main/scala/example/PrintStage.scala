package example

import akka.stream.ActorAttributes.SupervisionStrategy
import akka.stream._
import akka.stream.stage.{GraphStage, GraphStageLogic, InHandler, OutHandler}

import scala.util.control.NonFatal

class IdProvider {
  var currentId = 0

  def getId: Int = this.synchronized {
    currentId = currentId + 1
    currentId
  }
}

class PrintStage[T](name: String, idProvider: IdProvider) extends GraphStage[FlowShape[T, T]] {
  val in = Inlet[T]("PrintStage.in")
  val out = Outlet[T]("PrintStage.out")

  override val shape = FlowShape.of(in, out)

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) with InHandler with OutHandler {
      val id = idProvider.getId
      val prefix = s"[$name($id)]"

      def decider = inheritedAttributes.mandatoryAttribute[SupervisionStrategy].decider

      override def onPush(): Unit = {
        try {
          val elem = grab(in)
          println(f"$prefix%20s: received $elem in onPush()")
          push(out, elem)
        } catch {
          case NonFatal(ex) ⇒ decider(ex) match {
            case Supervision.Stop ⇒ failStage(ex)
            case _                ⇒ pull(in)
          }
        }
      }

      override def onPull(): Unit = {
        println(f"$prefix%20s: received demand in onPull()")
        pull(in)
      }

      setHandlers(in, out, this)
    }
}