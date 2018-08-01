package com.example

import akka.http.scaladsl.common.{EntityStreamingSupport, JsonEntityStreamingSupport}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.example.DataChunk._

import scala.concurrent.Future

class MainRoute(dataProcessor: DataProcessor) {
  implicit val jsonStreamingSupport: JsonEntityStreamingSupport =
    EntityStreamingSupport.json()

  def route(): Route = post {
    entity(asSourceOf[DataChunk]) { source => //Source[DataChunk, NotUsed]
      val result: Future[String] =
        dataProcessor.runDataSource(source)

      complete(result)
    }
  }
}
