package com.example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import route.UserRoute

//object QuickstartServer extends App {
object Main {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("helloAkkaHttpServer")
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    Http().bindAndHandle(UserRoute.routes, "localhost", 8080)
    println(s"Server online at http://localhost:8080/")
  }
}
