package com.example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import com.example.model.User

import scala.concurrent.{ExecutionContext, Future}
// for GET
import akka.http.scaladsl.model.HttpMethods._
// for HttpRequest, HttpResponse, Uri
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer

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
        val a = Marshal(User("Richard Imaoka", 120))
        a.to[HttpResponse]
      }
    }

    Http().bindAndHandleAsync(requestHandler, "localhost", 8080)
    println(s"Server online at http://localhost:8080/")
  }
}
