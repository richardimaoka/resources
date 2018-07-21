package com.example.main

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
// importing akka...Directives._ makes `get` and `complete` avaialable in scope
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

object MainHelloWorld {
  def main(args: Array[String]): Unit = {
    // remember to make them implicit!!
    implicit val system = ActorSystem("MainHelloWorld") // for Akka Actor
    implicit val materializer = ActorMaterializer()     // for Akka Stream

    lazy val routes: Route = get { // `get` for HTTP GET method
      complete("Hello World")
    }

    Http().bindAndHandle(routes, "localhost", 8080)
    println(s"Server online at http://localhost:8080/")
  }
}
