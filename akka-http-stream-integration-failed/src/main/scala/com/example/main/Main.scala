package com.example.main

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Merge, Sink, Source}

object Main {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("Main")       // for Akka Actor
    implicit val materializer = ActorMaterializer() // for Akka Stream


    Source.combine(
      Source(List(1,2,3)),
      Source(List(1,2,3)),
      Source(List(1,2,3))
    )(Merge(_)).runWith(Sink.foreach(x => println(x)))

//    lazy val routes: Route = get { // `get` for HTTP GET method
//      complete("Hello World")
//    }
//
//    Http().bindAndHandle(routes, "localhost", 8080)
//    println(s"Server online at http://localhost:8080/")
  }
}
