package com.example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

object Main {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("Main")
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    val route = get {
      complete("Hello World")
    }


    Http().bindAndHandle(route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/")
  }
}
