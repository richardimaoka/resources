package com.example.main

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.example.route.UserRoute

object MainHighLevelAPI {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("helloAkkaHttpServer")
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    Http().bindAndHandle(UserRoute.routes, "localhost", 8080)
    println(s"Server online at http://localhost:8080/")
  }
}
