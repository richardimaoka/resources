package com.example

import akka.http.scaladsl.common.{EntityStreamingSupport, JsonEntityStreamingSupport}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.example.DataChunk._

object MainRoute {
  implicit val jsonStreamingSupport: JsonEntityStreamingSupport
    = EntityStreamingSupport.json()

  def route: Route = get {
    complete(DataSource.source)
  }

}
