package com.example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

object Main {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("Main")
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    Http().bindAndHandle(MainRoute.route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/")
  }
}
