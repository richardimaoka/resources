package com.example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.stream.{ActorMaterializer, ThrottleMode}
import akka.stream.scaladsl.Source
import akka.http.scaladsl.server.Directives._
import akka.util.ByteString

import scala.util.Random
import scala.concurrent.duration._

object Main {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("helloAkkaHttpServer")
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    val numbers = Source.fromIterator(() =>
      Iterator.continually(Random.nextInt()))
      .throttle(1, 1.second, 10, ThrottleMode.Shaping)
      .take(20)

    val route =
      path("random") {
        get {
          complete(
            HttpEntity(
              ContentTypes.`text/plain(UTF-8)`,
              // transform each number to a chunk of bytes
              numbers.map(n => {
                println("emitted: " + n)
                ByteString(s"$n\n")
              })
            )
          )
        }
      }

    Http().bindAndHandle(route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/")
  }
}
