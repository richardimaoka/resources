package com.example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

//object QuickstartServer extends App {
object Main {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("helloAkkaHttpServer")
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    lazy val routes: Route = get {
      complete("Hello World")
    }

    Http().bindAndHandle(routes, "localhost", 8080)
    println(s"Server online at http://localhost:8080/")
  }
}

//Await.result(system.whenTerminated, Duration.Inf)
//}
