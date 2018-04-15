package example

import akka.stream.stage.{GraphStageLogic, GraphStageWithMaterializedValue, InHandler}
import akka.stream.{Attributes, Inlet, SinkShape}
import akka.{Done, NotUsed}

import scala.concurrent.Future

class PrintSink(name: String) extends GraphStageWithMaterializedValue[SinkShape[Any], NotUsed] {
  val in = Inlet[Any]("PrintSink.in")
  val shape = SinkShape(in)

  val idProvider = new IdProvider

  override def createLogicAndMaterializedValue(inheritedAttributes: Attributes): (GraphStageLogic, NotUsed) = {
    val logic = new GraphStageLogic(shape) with InHandler {
      val id = idProvider.getId
      val prefix = s"[$name($id)]"

      override def preStart(): Unit = {
        println(f"$prefix%20s pull()")
        pull(in)
      }

      override def onPush(): Unit = {
        val elem = grab(in)
        println(f"$prefix%20s onPush($elem)")
        println(f"$prefix%20s pull()")
        pull(in)
      }

      setHandler(in, this)
    }

    (logic, NotUsed)
  }
}