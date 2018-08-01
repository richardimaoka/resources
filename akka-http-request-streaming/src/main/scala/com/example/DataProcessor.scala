package com.example

import akka.NotUsed
import akka.actor.ActorSystem
import akka.event.Logging
import akka.stream.scaladsl.Source
import akka.stream.{Attributes, Materializer}

import scala.concurrent.{ExecutionContext, Future}

class DataProcessor(
  implicit
  system: ActorSystem,
  materializer: Materializer
) {
  def runDataSource(
    source: Source[DataChunk, NotUsed]
  ): Future[String] = {
    // This is needed for the last `map` method execution
    implicit val ec: ExecutionContext = system.dispatcher

    val initialCount = 0
    val countElement =
      (currentCount: Int, _: DataChunk) => currentCount + 1

    source
      .log("received").withAttributes(
        Attributes.logLevels(onElement = Logging.InfoLevel))
      // count the number of elements (chunks)
      .runFold(initialCount)(countElement)
      // map the materialized value
      .map{ count => s"You sent $count chunks" }
  }
}
