package com.example.main

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.HttpMethods.GET
// for HttpRequest, HttpResponse, Uri
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import com.example.model.User

import scala.concurrent.{ExecutionContext, Future}

object MainLowLevelAPI {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("helloAkkaHttpServer")
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val ec: ExecutionContext = system.dispatcher

    val requestHandler: HttpRequest => Future[HttpResponse] = {
      case HttpRequest(
        GET,
        Uri.Path("/"),
        _, // matches any headers
        _, // matches any HTTP entity (HTTP body)
        _  // matches any HTTP protocol
      ) => {
        val m = Marshal(User("Richard Imaoka", 120))
        m.to[HttpResponse]
      }
    }

    Http().bindAndHandleAsync(requestHandler, "localhost", 8080)
    println(s"Server online at http://localhost:8080/")
  }
}
